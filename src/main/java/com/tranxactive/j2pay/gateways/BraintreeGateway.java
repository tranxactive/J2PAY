/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tranxactive.j2pay.gateways;

import com.braintreegateway.CreditCardAddressRequest;
import com.braintreegateway.CreditCardRequest;
import com.braintreegateway.CustomerRequest;
import com.braintreegateway.Environment;
import com.braintreegateway.Result;
import com.braintreegateway.Transaction;
import com.braintreegateway.TransactionRequest;
import com.braintreegateway.exceptions.AuthenticationException;
import com.braintreegateway.exceptions.AuthorizationException;
import com.braintreegateway.exceptions.ConfigurationException;
import com.braintreegateway.exceptions.DownForMaintenanceException;
import com.braintreegateway.exceptions.ForgedQueryStringException;
import com.braintreegateway.exceptions.InvalidChallengeException;
import com.braintreegateway.exceptions.InvalidSignatureException;
import com.braintreegateway.exceptions.NotFoundException;
import com.braintreegateway.exceptions.ServerException;
import com.braintreegateway.exceptions.TimeoutException;
import com.braintreegateway.exceptions.TooManyRequestsException;
import com.braintreegateway.exceptions.UnexpectedException;
import com.braintreegateway.exceptions.UpgradeRequiredException;
import com.tranxactive.j2pay.gateways.core.Gateway;
import com.tranxactive.j2pay.gateways.parameters.Currency;
import com.tranxactive.j2pay.gateways.parameters.Customer;
import com.tranxactive.j2pay.gateways.parameters.CustomerCard;
import com.tranxactive.j2pay.gateways.parameters.ParamList;
import com.tranxactive.j2pay.gateways.responses.AuthResponse;
import com.tranxactive.j2pay.gateways.responses.CaptureResponse;
import com.tranxactive.j2pay.gateways.responses.CoreResponse;
import com.tranxactive.j2pay.gateways.responses.ErrorResponse;
import com.tranxactive.j2pay.gateways.responses.PurchaseResponse;
import com.tranxactive.j2pay.gateways.responses.RebillResponse;
import com.tranxactive.j2pay.gateways.responses.RefundResponse;
import com.tranxactive.j2pay.gateways.responses.VoidResponse;
import com.tranxactive.j2pay.net.HTTPResponse;
import org.json.JSONObject;
import java.math.BigDecimal;

import static com.tranxactive.j2pay.gateways.util.ResponseProcessor.processFinalResponse;

/**
 *
 * @author ilyas
 */
public class BraintreeGateway extends Gateway {

    private static final String MERCHANT_ID = "merchantId";
    private static final String PUBLIC_KEY = "publicKey";
    private static final String PRIVATE_KEY = "privateKey";
    private static final String CUSTOMER_ID = "customerId";

    private com.braintreegateway.BraintreeGateway getGatewayObject(JSONObject apiParameters) {

        return new com.braintreegateway.BraintreeGateway(
                this.isTestMode() ? Environment.SANDBOX : Environment.PRODUCTION,
                apiParameters.getString(MERCHANT_ID),
                apiParameters.getString(PUBLIC_KEY),
                apiParameters.getString(PRIVATE_KEY)
        );
    }

    private HTTPResponse createCustomer(JSONObject apiParameters, Customer customer, CustomerCard customerCard, Currency currency, float amount) {

        boolean hasException = false;
        CustomerRequest customerRequest = new CustomerRequest();

        customerRequest
                .firstName(customer.getFirstName())
                .lastName(customer.getLastName())
                .email(customer.getEmail())
                .phone(customer.getPhoneNumber());

        CreditCardRequest card = customerRequest.creditCard();
        card
                .cardholderName(customerCard.getName())
                .number(customerCard.getNumber())
                .cvv(customerCard.getCvv())
                .expirationDate(customerCard.getExpiryMonth() + "/" + customerCard.getExpiryYear());

        CreditCardAddressRequest address = card.billingAddress();
        address
                .countryCodeAlpha3(customer.getCountry().getCodeISO3())
                .postalCode(customer.getZip())
                .streetAddress(customer.getAddress())
                .region(customer.getState())
                .locality(customer.getCity());

        Result<com.braintreegateway.Customer> createCustomer = null;

        GeneralResponse successResponse = null;
        ErrorResponse errorResponse = new ErrorResponse();
        HTTPResponse httpResponse = new HTTPResponse();

        try {
            createCustomer = getGatewayObject(apiParameters).customer().create(customerRequest);
        } catch (NotFoundException e) {
            errorResponse.setMessage("No record found");
            hasException = true;
        } catch (AuthenticationException e) {
            errorResponse.setMessage("Invalid credentials");
            hasException = true;
        } catch (AuthorizationException e) {
            errorResponse.setMessage("Not authorized to perform this action");
            hasException = true;
        } catch (ConfigurationException e) {
            errorResponse.setMessage("Configuratoin error");
            hasException = true;
        } catch (DownForMaintenanceException e) {
            errorResponse.setMessage("Server is under maintenance");
            hasException = true;
        } catch (TimeoutException e) {
            errorResponse.setMessage("Request time out");
            hasException = true;
        } catch (ForgedQueryStringException | InvalidChallengeException | InvalidSignatureException | UnexpectedException e) {
            errorResponse.setMessage("Unexpected exception occured");
            hasException = true;
        } catch (ServerException e) {
            errorResponse.setMessage("Server error");
            hasException = true;
        } catch (TooManyRequestsException e) {
            errorResponse.setMessage("Too many requests");
            hasException = true;
        } catch (UpgradeRequiredException e) {
            errorResponse.setMessage("Need to upgrade");
            hasException = true;
        }

        if (hasException) {
            processFinalResponse(null, httpResponse, successResponse, errorResponse);
            return httpResponse;
        }

        if (createCustomer.isSuccess()) {
            successResponse = new GeneralResponse();
            successResponse.setMessage("Success");
            successResponse.setTransactionId(createCustomer.getTarget().getId());
        } else {
            errorResponse.setMessage(createCustomer.getMessage());
        }

        processFinalResponse(null, httpResponse, successResponse, errorResponse);
        return httpResponse;

    }

    @Override
    public HTTPResponse authorize(JSONObject apiParameters, Customer customer, CustomerCard customerCard, Currency currency, float amount) {

        boolean hasException = false;
        HTTPResponse createCustomer = createCustomer(apiParameters, customer, customerCard, currency, amount);

        if (!createCustomer.isSuccessful()) {
            return createCustomer;
        }

        AuthResponse successResponse = null;
        ErrorResponse errorResponse = new ErrorResponse();
        HTTPResponse httpResponse = new HTTPResponse();

        String customerId = createCustomer.getJSONResponse().getJSONObject(ParamList.LIBRARY_RESPONSE.getName()).getString(ParamList.TRANSACTION_ID.getName());
        TransactionRequest transactionRequest = new TransactionRequest();
        transactionRequest.
                customerId(customerId)
                .amount(new BigDecimal(Float.toString(amount)));

        transactionRequest.options().submitForSettlement(false);

        Result<Transaction> sale = null;

        try {
            sale = getGatewayObject(apiParameters).transaction().sale(transactionRequest);
        } catch (NotFoundException e) {
            errorResponse.setMessage("No record found");
            hasException = true;
        } catch (AuthenticationException e) {
            errorResponse.setMessage("Invalid credentials");
            hasException = true;
        } catch (AuthorizationException e) {
            errorResponse.setMessage("Not authorized to perform this action");
            hasException = true;
        } catch (ConfigurationException e) {
            errorResponse.setMessage("Configuratoin error");
            hasException = true;
        } catch (DownForMaintenanceException e) {
            errorResponse.setMessage("Server is under maintenance");
            hasException = true;
        } catch (TimeoutException e) {
            errorResponse.setMessage("Request time out");
            hasException = true;
        } catch (ForgedQueryStringException | InvalidChallengeException | InvalidSignatureException | UnexpectedException e) {
            errorResponse.setMessage("Unexpected exception occured");
            hasException = true;
        } catch (ServerException e) {
            errorResponse.setMessage("Server error");
            hasException = true;
        } catch (TooManyRequestsException e) {
            errorResponse.setMessage("Too many requests");
            hasException = true;
        } catch (UpgradeRequiredException e) {
            errorResponse.setMessage("Need to upgrade");
            hasException = true;
        }

        if (hasException) {
            processFinalResponse(null, httpResponse, successResponse, errorResponse);
            return httpResponse;
        }

        if (sale.isSuccess()) {
            successResponse = new AuthResponse();
            successResponse.setMessage("Success");
            successResponse.setTransactionId(sale.getTarget().getId());
            successResponse.setCardValuesFrom(customerCard);
            successResponse.setAmount(amount);
            successResponse.setCurrencyCode(currency);

            successResponse.setRebillParams(
                    new JSONObject().put(CUSTOMER_ID, customerId)
            );

            successResponse.setVoidParams(
                    new JSONObject().put(ParamList.TRANSACTION_ID.getName(), sale.getTarget().getId())
            );

            successResponse.setCaptureParams(
                    new JSONObject().put(ParamList.TRANSACTION_ID.getName(), sale.getTarget().getId())
            );
        } else {
            errorResponse.setMessage(sale.getMessage());
        }

        processFinalResponse(null, httpResponse, successResponse, errorResponse);

        return httpResponse;
    }

    @Override
    public HTTPResponse capture(JSONObject apiParameters, JSONObject captureParameters, float amount) {

        boolean hasException = false;
        CaptureResponse successResponse = null;
        ErrorResponse errorResponse = new ErrorResponse();
        HTTPResponse httpResponse = new HTTPResponse();

        Result<Transaction> capture = null;

        try {
            capture = getGatewayObject(apiParameters).transaction().
                    submitForSettlement(
                            captureParameters.getString(ParamList.TRANSACTION_ID.getName()),
                            new BigDecimal(Float.toString(amount)));
        } catch (NotFoundException e) {
            errorResponse.setMessage("No record found");
            hasException = true;
        } catch (AuthenticationException e) {
            errorResponse.setMessage("Invalid credentials");
            hasException = true;
        } catch (AuthorizationException e) {
            errorResponse.setMessage("Not authorized to perform this action");
            hasException = true;
        } catch (ConfigurationException e) {
            errorResponse.setMessage("Configuratoin error");
            hasException = true;
        } catch (DownForMaintenanceException e) {
            errorResponse.setMessage("Server is under maintenance");
            hasException = true;
        } catch (TimeoutException e) {
            errorResponse.setMessage("Request time out");
            hasException = true;
        } catch (ForgedQueryStringException | InvalidChallengeException | InvalidSignatureException | UnexpectedException e) {
            errorResponse.setMessage("Unexpected exception occured");
            hasException = true;
        } catch (ServerException e) {
            errorResponse.setMessage("Server error");
            hasException = true;
        } catch (TooManyRequestsException e) {
            errorResponse.setMessage("Too many requests");
            hasException = true;
        } catch (UpgradeRequiredException e) {
            errorResponse.setMessage("Need to upgrade");
            hasException = true;
        }

        if (hasException) {
            processFinalResponse(null, httpResponse, successResponse, errorResponse);
            return httpResponse;
        }

        if (capture.isSuccess()) {
            successResponse = new CaptureResponse();
            successResponse.setMessage("Success");
            successResponse.setTransactionId(capture.getTarget().getId());
            successResponse.setAmount(amount);

            successResponse.setVoidParams(
                    new JSONObject().put(ParamList.TRANSACTION_ID.getName(), capture.getTarget().getId())
            );

            successResponse.setRefundParams(
                    new JSONObject().put(ParamList.TRANSACTION_ID.getName(), capture.getTarget().getId())
            );

        } else {
            errorResponse.setMessage(capture.getMessage());
        }

        processFinalResponse(null, httpResponse, successResponse, errorResponse);

        return httpResponse;
    }

    @Override
    public HTTPResponse purchase(JSONObject apiParameters, Customer customer, CustomerCard customerCard, Currency currency, float amount) {

        boolean hasException = false;
        HTTPResponse createCustomer = createCustomer(apiParameters, customer, customerCard, currency, amount);

        if (!createCustomer.isSuccessful()) {
            return createCustomer;
        }

        PurchaseResponse successResponse = null;
        ErrorResponse errorResponse = new ErrorResponse();
        HTTPResponse httpResponse = new HTTPResponse();

        String customerId = createCustomer.getJSONResponse().getJSONObject(ParamList.LIBRARY_RESPONSE.getName()).getString(ParamList.TRANSACTION_ID.getName());
        TransactionRequest transactionRequest = new TransactionRequest();
        transactionRequest.
                customerId(customerId)
                .amount(new BigDecimal(Float.toString(amount)));

        transactionRequest.options().submitForSettlement(true);

        Result<Transaction> sale = null;

        try {
            sale = getGatewayObject(apiParameters).transaction().sale(transactionRequest);
        } catch (NotFoundException e) {
            errorResponse.setMessage("No record found");
            hasException = true;
        } catch (AuthenticationException e) {
            errorResponse.setMessage("Invalid credentials");
            hasException = true;
        } catch (AuthorizationException e) {
            errorResponse.setMessage("Not authorized to perform this action");
            hasException = true;
        } catch (ConfigurationException e) {
            errorResponse.setMessage("Configuratoin error");
            hasException = true;
        } catch (DownForMaintenanceException e) {
            errorResponse.setMessage("Server is under maintenance");
            hasException = true;
        } catch (TimeoutException e) {
            errorResponse.setMessage("Request time out");
            hasException = true;
        } catch (ForgedQueryStringException | InvalidChallengeException | InvalidSignatureException | UnexpectedException e) {
            errorResponse.setMessage("Unexpected exception occured");
            hasException = true;
        } catch (ServerException e) {
            errorResponse.setMessage("Server error");
            hasException = true;
        } catch (TooManyRequestsException e) {
            errorResponse.setMessage("Too many requests");
            hasException = true;
        } catch (UpgradeRequiredException e) {
            errorResponse.setMessage("Need to upgrade");
            hasException = true;
        }

        if (hasException) {
            processFinalResponse(null, httpResponse, successResponse, errorResponse);
            return httpResponse;
        }

        if (sale.isSuccess()) {
            successResponse = new PurchaseResponse();
            successResponse.setMessage("Success");
            successResponse.setTransactionId(sale.getTarget().getId());
            successResponse.setCardValuesFrom(customerCard);
            successResponse.setAmount(amount);
            successResponse.setCurrencyCode(currency);

            successResponse.setVoidParams(
                    new JSONObject().put(ParamList.TRANSACTION_ID.getName(), sale.getTarget().getId())
            );

            successResponse.setRebillParams(
                    new JSONObject().put(CUSTOMER_ID, customerId)
            );

            successResponse.setVoidParams(
                    new JSONObject().put(ParamList.TRANSACTION_ID.getName(), sale.getTarget().getId())
            );

        } else {
            errorResponse.setMessage(sale.getMessage());
        }

        processFinalResponse(null, httpResponse, successResponse, errorResponse);

        return httpResponse;
    }

    @Override
    public HTTPResponse refund(JSONObject apiParameters, JSONObject refundParameters, float amount) {

        boolean hasException = false;
        RefundResponse successResponse = null;
        ErrorResponse errorResponse = new ErrorResponse();
        HTTPResponse httpResponse = new HTTPResponse();

        Result<Transaction> refund = null;

        try {
            refund = getGatewayObject(apiParameters).transaction().
                    refund(
                            refundParameters.getString(ParamList.TRANSACTION_ID.getName()),
                            new BigDecimal(Float.toString(amount)));
        } catch (NotFoundException e) {
            errorResponse.setMessage("No record found");
            hasException = true;
        } catch (AuthenticationException e) {
            errorResponse.setMessage("Invalid credentials");
            hasException = true;
        } catch (AuthorizationException e) {
            errorResponse.setMessage("Not authorized to perform this action");
            hasException = true;
        } catch (ConfigurationException e) {
            errorResponse.setMessage("Configuratoin error");
            hasException = true;
        } catch (DownForMaintenanceException e) {
            errorResponse.setMessage("Server is under maintenance");
            hasException = true;
        } catch (TimeoutException e) {
            errorResponse.setMessage("Request time out");
            hasException = true;
        } catch (ForgedQueryStringException | InvalidChallengeException | InvalidSignatureException | UnexpectedException e) {
            errorResponse.setMessage("Unexpected exception occured");
            hasException = true;
        } catch (ServerException e) {
            errorResponse.setMessage("Server error");
            hasException = true;
        } catch (TooManyRequestsException e) {
            errorResponse.setMessage("Too many requests");
            hasException = true;
        } catch (UpgradeRequiredException e) {
            errorResponse.setMessage("Need to upgrade");
            hasException = true;
        }

        if (hasException) {
            processFinalResponse(null, httpResponse, successResponse, errorResponse);
            return httpResponse;
        }

        if (refund.isSuccess()) {
            successResponse = new RefundResponse();
            successResponse.setMessage("Success");
            successResponse.setTransactionId(refund.getTarget().getId());
            successResponse.setAmount(amount);

            successResponse.setVoidParams(
                    new JSONObject().put(ParamList.TRANSACTION_ID.getName(), refund.getTarget().getId())
            );

        } else {
            errorResponse.setMessage(refund.getMessage());
        }

        processFinalResponse(null, httpResponse, successResponse, errorResponse);

        return httpResponse;
    }

    @Override
    public HTTPResponse rebill(JSONObject apiParameters, JSONObject rebillParameters, float amount) {

        boolean hasException = false;

        RebillResponse successResponse = null;
        ErrorResponse errorResponse = new ErrorResponse();
        HTTPResponse httpResponse = new HTTPResponse();

        TransactionRequest transactionRequest = new TransactionRequest();
        transactionRequest.
                customerId(rebillParameters.getString(CUSTOMER_ID))
                .amount(new BigDecimal(Float.toString(amount)));

        transactionRequest.options().submitForSettlement(true);

        Result<Transaction> sale = null;

        try {
            sale = getGatewayObject(apiParameters).transaction().sale(transactionRequest);
        } catch (NotFoundException e) {
            errorResponse.setMessage("No record found");
            hasException = true;
        } catch (AuthenticationException e) {
            errorResponse.setMessage("Invalid credentials");
            hasException = true;
        } catch (AuthorizationException e) {
            errorResponse.setMessage("Not authorized to perform this action");
            hasException = true;
        } catch (ConfigurationException e) {
            errorResponse.setMessage("Configuratoin error");
            hasException = true;
        } catch (DownForMaintenanceException e) {
            errorResponse.setMessage("Server is under maintenance");
            hasException = true;
        } catch (TimeoutException e) {
            errorResponse.setMessage("Request time out");
            hasException = true;
        } catch (ForgedQueryStringException | InvalidChallengeException | InvalidSignatureException | UnexpectedException e) {
            errorResponse.setMessage("Unexpected exception occured");
            hasException = true;
        } catch (ServerException e) {
            errorResponse.setMessage("Server error");
            hasException = true;
        } catch (TooManyRequestsException e) {
            errorResponse.setMessage("Too many requests");
            hasException = true;
        } catch (UpgradeRequiredException e) {
            errorResponse.setMessage("Need to upgrade");
            hasException = true;
        }

        if (hasException) {
            processFinalResponse(null, httpResponse, successResponse, errorResponse);
            return httpResponse;
        }

        if (sale.isSuccess()) {
            successResponse = new RebillResponse();
            successResponse.setMessage("Success");
            successResponse.setTransactionId(sale.getTarget().getId());
            successResponse.setAmount(amount);

            successResponse.setVoidParams(
                    new JSONObject().put(ParamList.TRANSACTION_ID.getName(), sale.getTarget().getId())
            );

            successResponse.setRebillParams(
                    new JSONObject().put(CUSTOMER_ID, rebillParameters.getString(CUSTOMER_ID))
            );

            successResponse.setVoidParams(
                    new JSONObject().put(ParamList.TRANSACTION_ID.getName(), sale.getTarget().getId())
            );

        } else {
            errorResponse.setMessage(sale.getMessage());
        }

        processFinalResponse(null, httpResponse, successResponse, errorResponse);

        return httpResponse;
    }

    @Override
    public HTTPResponse voidTransaction(JSONObject apiParameters, JSONObject voidParameters) {

        boolean hasException = false;
        VoidResponse successResponse = null;
        ErrorResponse errorResponse = new ErrorResponse();
        HTTPResponse httpResponse = new HTTPResponse();

        Result<Transaction> voidTransaction = null;

        try {
            voidTransaction = getGatewayObject(apiParameters).transaction().
                    voidTransaction(voidParameters.getString(ParamList.TRANSACTION_ID.getName()));
        } catch (NotFoundException e) {
            errorResponse.setMessage("No record found");
            hasException = true;
        } catch (AuthenticationException e) {
            errorResponse.setMessage("Invalid credentials");
            hasException = true;
        } catch (AuthorizationException e) {
            errorResponse.setMessage("Not authorized to perform this action");
            hasException = true;
        } catch (ConfigurationException e) {
            errorResponse.setMessage("Configuratoin error");
            hasException = true;
        } catch (DownForMaintenanceException e) {
            errorResponse.setMessage("Server is under maintenance");
            hasException = true;
        } catch (TimeoutException e) {
            errorResponse.setMessage("Request time out");
            hasException = true;
        } catch (ForgedQueryStringException | InvalidChallengeException | InvalidSignatureException | UnexpectedException e) {
            errorResponse.setMessage("Unexpected exception occured");
            hasException = true;
        } catch (ServerException e) {
            errorResponse.setMessage("Server error");
            hasException = true;
        } catch (TooManyRequestsException e) {
            errorResponse.setMessage("Too many requests");
            hasException = true;
        } catch (UpgradeRequiredException e) {
            errorResponse.setMessage("Need to upgrade");
            hasException = true;
        }

        if (hasException) {
            processFinalResponse(null, httpResponse, successResponse, errorResponse);
            return httpResponse;
        }

        if (voidTransaction.isSuccess()) {
            successResponse = new VoidResponse();
            successResponse.setMessage("Success");
            successResponse.setTransactionId(voidTransaction.getTarget().getId());

        } else {
            errorResponse.setMessage(voidTransaction.getMessage());
        }

        processFinalResponse(null, httpResponse, successResponse, errorResponse);

        return httpResponse;
    }

    @Override
    public JSONObject getApiSampleParameters() {
        return new JSONObject()
                .put(MERCHANT_ID, "the merchant id")
                .put(PUBLIC_KEY, "the public key")
                .put(PRIVATE_KEY, "the private key");
    }

    @Override
    public JSONObject getRefundSampleParameters() {
        return new JSONObject().put(ParamList.TRANSACTION_ID.getName(), "the transaction id");
    }

    @Override
    public JSONObject getRebillSampleParameters() {
        return new JSONObject().put(CUSTOMER_ID, "the customer id");
    }

    @Override
    public JSONObject getVoidSampleParameters() {
        return new JSONObject().put(ParamList.TRANSACTION_ID.getName(), "the transaction id");
    }

    @Override
    public JSONObject getCaptureSampleParameters() {
        return new JSONObject().put(ParamList.TRANSACTION_ID.getName(), "the transaction id");
    }

    private class GeneralResponse extends CoreResponse {

        @Override
        public JSONObject getResponse() {

            return new JSONObject()
                    .put(ParamList.LIBRARY_RESPONSE.getName(), new JSONObject()
                            .put(ParamList.SUCCESS.getName(), this.success)
                            .put(ParamList.MESSAGE.getName(), this.message)
                            .put(ParamList.TRANSACTION_ID.getName(), this.getTransactionId())
                    )
                    .put(ParamList.GATEWAY_RESPONSE.getName(), this.gatewayResponse != null ? this.gatewayResponse : JSONObject.NULL);
        }

    }

}
