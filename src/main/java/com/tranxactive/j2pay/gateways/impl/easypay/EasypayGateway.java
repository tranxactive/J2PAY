/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tranxactive.j2pay.gateways.impl.easypay;

import com.tranxactive.j2pay.gateways.core.Gateway;
import com.tranxactive.j2pay.helpers.entities.Currency;
import com.tranxactive.j2pay.helpers.entities.Customer;
import com.tranxactive.j2pay.helpers.entities.CustomerCard;
import com.tranxactive.j2pay.gateways.parameters.ParamList;
import com.tranxactive.j2pay.gateways.responses.*;
import com.tranxactive.j2pay.helpers.net.HTTPClient;
import com.tranxactive.j2pay.helpers.net.HTTPResponse;
import com.tranxactive.j2pay.helpers.net.JSONHelper;
import com.tranxactive.j2pay.helpers.net.QueryStringHelper;
import org.json.JSONObject;

import static com.tranxactive.j2pay.gateways.impl.easypay.Constants.RequestParameters.*;
import static com.tranxactive.j2pay.gateways.impl.easypay.Constants.ResponseParameters.CUSTOMER_VAULT_ID;
import static com.tranxactive.j2pay.gateways.impl.easypay.Constants.ResponseParameters.RESPONSE;
import static com.tranxactive.j2pay.gateways.impl.easypay.Constants.ResponseParameters.TRANSACTION_ID;
import static com.tranxactive.j2pay.gateways.impl.easypay.Constants.URL;
import static com.tranxactive.j2pay.util.ResponseProcessor.processFinalResponse;
import static com.tranxactive.j2pay.util.UniqueCustomerIdGenerator.getUniqueVaultId;
import static org.apache.http.entity.ContentType.APPLICATION_FORM_URLENCODED;

/**
 * @author Tkhan
 */
public class EasypayGateway extends Gateway {

    @Override
    public HTTPResponse purchase(JSONObject apiParameters, Customer customer, CustomerCard customerCard, Currency currency, float amount) {

        String customerVaultId = getUniqueVaultId();
        String requestString = QueryStringHelper.toQueryString(JSONHelper.encode(this.buildPurchaseParameters(apiParameters, customerVaultId, customer, customerCard, currency, amount)));

        JSONObject responseObject;
        HTTPResponse httpResponse;

        PurchaseResponse successResponse = null;
        ErrorResponse errorResponse = new ErrorResponse();

        httpResponse = HTTPClient.httpPost(URL, requestString, APPLICATION_FORM_URLENCODED);

        if (httpResponse.getStatusCode() == -1) {
            return httpResponse;
        }

        responseObject = JSONHelper.decode(QueryStringHelper.toJson(httpResponse.getContent()));

        if ("1".equals(responseObject.getString(RESPONSE))) {

            httpResponse.setSuccessful(true);
            successResponse = new PurchaseResponse();

            successResponse.setMessage(responseObject.getString(RESPONSE_TEXT));
            successResponse.setTransactionId(responseObject.get(TRANSACTION_ID).toString());
            successResponse.setCardValuesFrom(customerCard);
            successResponse.setAmount(amount);
            successResponse.setCurrencyCode(currency);

            successResponse.setRebillParams(new JSONObject().put(CUSTOMER_VAULT_ID, customerVaultId));
            successResponse.setRefundParams(new JSONObject().put(ParamList.TRANSACTION_ID.getName(), responseObject.get(TRANSACTION_ID).toString()));
            successResponse.setVoidParams(new JSONObject().put(ParamList.TRANSACTION_ID.getName(), responseObject.get(TRANSACTION_ID).toString()));

        } else {
            errorResponse.setMessage(responseObject.getString(RESPONSE_TEXT));
        }

        //final response.
        processFinalResponse(responseObject, httpResponse, successResponse, errorResponse);
        return httpResponse;
    }

    @Override
    public HTTPResponse refund(JSONObject apiParameters, JSONObject refundParameters, float amount) {
        String requestString = QueryStringHelper.toQueryString(JSONHelper.encode(this.buildRefundParameters(apiParameters, refundParameters, amount)));

        JSONObject responseObject;
        HTTPResponse httpResponse;

        RefundResponse successResponse = null;
        ErrorResponse errorResponse = new ErrorResponse();

        httpResponse = HTTPClient.httpPost(URL, requestString, APPLICATION_FORM_URLENCODED);

        if (httpResponse.getStatusCode() == -1) {
            return httpResponse;
        }

        responseObject = JSONHelper.decode(QueryStringHelper.toJson(httpResponse.getContent()));

        if ("1".equals(responseObject.getString(RESPONSE))) {
            httpResponse.setSuccessful(true);
            successResponse = new RefundResponse();

            successResponse.setMessage(responseObject.getString(RESPONSE_TEXT));
            successResponse.setTransactionId(responseObject.get(TRANSACTION_ID).toString());
            successResponse.setAmount(amount);

            successResponse.setVoidParams(new JSONObject()
                    .put(ParamList.TRANSACTION_ID.getName(), responseObject.get(TRANSACTION_ID).toString())
            );

        } else {
            errorResponse.setMessage(responseObject.getString(RESPONSE_TEXT));
        }

        //final response.
        processFinalResponse(responseObject, httpResponse, successResponse, errorResponse);
        return httpResponse;
    }

    @Override
    public HTTPResponse rebill(JSONObject apiParameters, JSONObject rebillParameters, float amount) {

        String requestString = QueryStringHelper.toQueryString(JSONHelper.encode(this.buildRebillParameters(apiParameters, rebillParameters, amount)));

        JSONObject responseObject;
        HTTPResponse httpResponse;

        RebillResponse successResponse = null;
        ErrorResponse errorResponse = new ErrorResponse();

        httpResponse = HTTPClient.httpPost(URL, requestString, APPLICATION_FORM_URLENCODED);

        if (httpResponse.getStatusCode() == -1) {
            return httpResponse;
        }

        responseObject = JSONHelper.decode(QueryStringHelper.toJson(httpResponse.getContent()));

        if ("1".equals(responseObject.getString(RESPONSE))) {
            httpResponse.setSuccessful(true);
            successResponse = new RebillResponse();

            successResponse.setMessage(responseObject.getString(RESPONSE_TEXT));
            successResponse.setTransactionId(responseObject.get(TRANSACTION_ID).toString());
            successResponse.setAmount(amount);
            successResponse.setRebillParams(new JSONObject().put(CUSTOMER_VAULT_ID, rebillParameters.getString(CUSTOMER_VAULT_ID)));
            successResponse.setRefundParams(new JSONObject().put(ParamList.TRANSACTION_ID.getName(), responseObject.get(TRANSACTION_ID).toString()));
            successResponse.setVoidParams(new JSONObject().put(ParamList.TRANSACTION_ID.getName(), responseObject.get(TRANSACTION_ID).toString()));
        } else {
            errorResponse.setMessage(responseObject.getString(RESPONSE_TEXT));
        }

        //final response.
        processFinalResponse(responseObject, httpResponse, successResponse, errorResponse);
        return httpResponse;
    }

    @Override
    public HTTPResponse voidTransaction(JSONObject apiParameters, JSONObject voidParameters) {

        String requestString = QueryStringHelper.toQueryString(JSONHelper.encode(this.buildVoidParameters(apiParameters, voidParameters)));

        JSONObject responseObject;
        HTTPResponse httpResponse;

        VoidResponse successResponse = null;
        ErrorResponse errorResponse = new ErrorResponse();

        httpResponse = HTTPClient.httpPost(URL, requestString, APPLICATION_FORM_URLENCODED);
        if (httpResponse.getStatusCode() == -1) {
            return httpResponse;
        }

        responseObject = JSONHelper.decode(QueryStringHelper.toJson(httpResponse.getContent()));

        if ("1".equals(responseObject.getString(RESPONSE))) {
            httpResponse.setSuccessful(true);
            successResponse = new VoidResponse();

            successResponse.setMessage(responseObject.getString(RESPONSE_TEXT));
            successResponse.setTransactionId(responseObject.get(TRANSACTION_ID).toString());

        } else {
            errorResponse.setMessage(responseObject.getString(RESPONSE_TEXT));
        }

        //final response.
        processFinalResponse(responseObject, httpResponse, successResponse, errorResponse);
        return httpResponse;
    }

    @Override
    public JSONObject getApiSampleParameters() {
        return new JSONObject().put(USERNAME, "the user name").put(PASSWORD, "merchant password.");
    }

    @Override
    public JSONObject getRefundSampleParameters() {
        return new JSONObject().put(ParamList.TRANSACTION_ID.getName(), "the transaction id can also be used to refer this transaction later");
    }

    @Override
    public JSONObject getRebillSampleParameters() {
        return new JSONObject().put(CUSTOMER_VAULT_ID, "customer Vault ID required for recurring");
    }

    @Override
    public JSONObject getVoidSampleParameters() {
        return new JSONObject().put(ParamList.TRANSACTION_ID.getName(), "the transaction id can also be used to refer this transaction later");
    }

    private JSONObject buildPurchaseParameters(JSONObject apiParameters, String customerVaultId, Customer customer, CustomerCard customerCard, Currency currency, float amount) {

        return new JSONObject()
                .put(TRANSACTION_TYPE, TRANSACTION_TYPE_PURCHASE)
                .put(USERNAME, apiParameters.getString(USERNAME))
                .put(PASSWORD, apiParameters.getString(PASSWORD))
                .put(CREDIT_CARD_NUMBER, customerCard.getNumber())
                .put(CREDIT_CARD_CVV, customerCard.getCvv())
                .put(CREDIT_CARD_EXPIRY_DATE, customerCard.getExpiryMonth() + customerCard.getExpiryYear().substring(2))
                .put(AMOUNT, amount)
                .put(CURRENCY, currency)
                .put(BILLING_METHOD, RECURRING)
                .put(IP_ADDRESS, customer.getIp())
                .put(FIRSTNAME, customer.getFirstName())
                .put(LASTNAME, customer.getLastName())
                .put(ADDRESS, customer.getAddress())
                .put(CITY, customer.getCity())
                .put(STATE, customer.getState())
                .put(ZIPCODE, customer.getZip())
                .put(COUNTRY, customer.getCountry().getCodeISO2())
                .put(PHONE_NUMBER, customer.getPhoneNumber())
                .put(EMAIL, customer.getEmail())
                .put(CUSTOMER_VAULT, ADD_CUSTOMER)
                .put(CUSTOMER_VAULT_ID, customerVaultId);
    }

    private JSONObject buildVoidParameters(JSONObject apiParameters, JSONObject voidParameters) {
        return new JSONObject()
                .put(TRANSACTION_TYPE, TRANSACTION_TYPE_VOID)
                .put(USERNAME, apiParameters.getString(USERNAME))
                .put(PASSWORD, apiParameters.getString(PASSWORD))
                .put(TRANSACTION_ID, voidParameters.getString(TRANSACTION_ID));
    }

    private JSONObject buildRefundParameters(JSONObject apiParameters, JSONObject refundParameters, float amount) {
        return new JSONObject()
                .put(TRANSACTION_TYPE, TRANSACTION_TYPE_REFUND)
                .put(USERNAME, apiParameters.getString(USERNAME))
                .put(PASSWORD, apiParameters.getString(PASSWORD))
                .put(TRANSACTION_ID, refundParameters.getString(TRANSACTION_ID))
                .put(AMOUNT, amount);
    }

    private JSONObject buildRebillParameters(JSONObject apiParameters, JSONObject rebillParameters, float amount) {
        return new JSONObject()
                .put(USERNAME, apiParameters.getString(USERNAME))
                .put(PASSWORD, apiParameters.getString(PASSWORD))
                .put(CUSTOMER_VAULT_ID, rebillParameters.getString(CUSTOMER_VAULT_ID))
                .put(AMOUNT, amount);
    }
}
