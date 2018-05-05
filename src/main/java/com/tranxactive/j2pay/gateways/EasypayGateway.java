/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tranxactive.j2pay.gateways;

import com.tranxactive.j2pay.gateways.core.Gateway;
import com.tranxactive.j2pay.gateways.parameters.Currency;
import com.tranxactive.j2pay.gateways.parameters.Customer;
import com.tranxactive.j2pay.gateways.parameters.CustomerCard;
import com.tranxactive.j2pay.gateways.parameters.ParamList;
import com.tranxactive.j2pay.gateways.responses.ErrorResponse;
import com.tranxactive.j2pay.gateways.responses.PurchaseResponse;
import com.tranxactive.j2pay.gateways.responses.RebillResponse;
import com.tranxactive.j2pay.gateways.responses.RefundResponse;
import com.tranxactive.j2pay.gateways.responses.VoidResponse;
import com.tranxactive.j2pay.net.HTTPClient;
import com.tranxactive.j2pay.net.HTTPResponse;
import com.tranxactive.j2pay.net.JSONHelper;
import com.tranxactive.j2pay.net.QueryStringHelper;

import java.util.Random;

import org.apache.http.entity.ContentType;
import org.json.JSONObject;

/**
 * @author Tkhan
 */
public class EasypayGateway extends Gateway {

    private final String url = "https://secure.easypaydirectgateway.com/api/transact.php";

    @Override
    public HTTPResponse purchase(JSONObject apiParameters, Customer customer, CustomerCard customerCard, Currency currency, float amount) {

        String customerVaultId = this.getUniqueVaultId();
        String requestString = QueryStringHelper.toQueryString(JSONHelper.encode(this.buildPurchaseParameters(apiParameters, customerVaultId, customer, customerCard, currency, amount)));

        JSONObject responseObject;
        HTTPResponse httpResponse;

        PurchaseResponse successResponse = null;
        ErrorResponse errorResponse = new ErrorResponse();

        httpResponse = HTTPClient.httpPost(url, requestString, ContentType.APPLICATION_FORM_URLENCODED);

        if (httpResponse.getStatusCode() == -1) {
            return httpResponse;
        }

        responseObject = JSONHelper.decode(QueryStringHelper.toJson(httpResponse.getContent()));

        if (responseObject.getString("response").equals("1")) {

            httpResponse.setSuccessful(true);
            successResponse = new PurchaseResponse();

            successResponse.setMessage(responseObject.getString("responsetext"));
            successResponse.setTransactionId(responseObject.get("transactionid").toString());
            successResponse.setCardValuesFrom(customerCard);
            successResponse.setAmount(amount);
            successResponse.setCurrencyCode(currency);

            successResponse.setRebillParams(new JSONObject()
                    .put("customerVaultId", customerVaultId)
            );

            successResponse.setRefundParams(new JSONObject()
                    .put(ParamList.TRANSACTION_ID.getName(), responseObject.get("transactionid").toString())
            );
            successResponse.setVoidParams(new JSONObject()
                    .put(ParamList.TRANSACTION_ID.getName(), responseObject.get("transactionid").toString())
            );

        } else {
            errorResponse.setMessage(responseObject.getString("responsetext"));
        }

        //final response.
        if (successResponse != null) {
            successResponse.setGatewayResponse(responseObject);
            httpResponse.setContent(successResponse.getResponse().toString());
        } else {
            errorResponse.setGatewayResponse(responseObject);
            httpResponse.setContent(errorResponse.getResponse().toString());
        }

        return httpResponse;
    }

    @Override
    public HTTPResponse refund(JSONObject apiParameters, JSONObject refundParameters, float amount) {
        String requestString = QueryStringHelper.toQueryString(JSONHelper.encode(this.buildRefundParameters(apiParameters, refundParameters, amount)));

        JSONObject responseObject;
        HTTPResponse httpResponse;

        RefundResponse successResponse = null;
        ErrorResponse errorResponse = new ErrorResponse();

        httpResponse = HTTPClient.httpPost(url, requestString, ContentType.APPLICATION_FORM_URLENCODED);

        if (httpResponse.getStatusCode() == -1) {
            return httpResponse;
        }

        responseObject = JSONHelper.decode(QueryStringHelper.toJson(httpResponse.getContent()));

        if (responseObject.getString("response").equals("1")) {
            httpResponse.setSuccessful(true);
            successResponse = new RefundResponse();

            successResponse.setMessage(responseObject.getString("responsetext"));
            successResponse.setTransactionId(responseObject.get("transactionid").toString());
            successResponse.setAmount(amount);

            successResponse.setVoidParams(new JSONObject()
                    .put(ParamList.TRANSACTION_ID.getName(), responseObject.get("transactionid").toString())
            );

        } else {
            errorResponse.setMessage(responseObject.getString("responsetext"));
        }

        //final response.
        if (successResponse != null) {
            successResponse.setGatewayResponse(responseObject);
            httpResponse.setContent(successResponse.getResponse().toString());
        } else {
            errorResponse.setGatewayResponse(responseObject);
            httpResponse.setContent(errorResponse.getResponse().toString());
        }

        return httpResponse;
    }

    @Override
    public HTTPResponse rebill(JSONObject apiParameters, JSONObject rebillParameters, float amount) {

        String requestString = QueryStringHelper.toQueryString(JSONHelper.encode(this.buildRebillParameters(apiParameters, rebillParameters, amount)));

        JSONObject responseObject;
        HTTPResponse httpResponse;

        RebillResponse successResponse = null;
        ErrorResponse errorResponse = new ErrorResponse();

        httpResponse = HTTPClient.httpPost(url, requestString, ContentType.APPLICATION_FORM_URLENCODED);

        if (httpResponse.getStatusCode() == -1) {
            return httpResponse;
        }

        responseObject = JSONHelper.decode(QueryStringHelper.toJson(httpResponse.getContent()));

        if (responseObject.getString("response").equals("1")) {
            httpResponse.setSuccessful(true);
            successResponse = new RebillResponse();

            successResponse.setMessage(responseObject.getString("responsetext"));
            successResponse.setTransactionId(responseObject.get("transactionid").toString());
            successResponse.setAmount(amount);

            successResponse.setRebillParams(new JSONObject()
                    .put("customerVaultId", rebillParameters.getString("customerVaultId"))
            );

            successResponse.setRefundParams(new JSONObject()
                    .put(ParamList.TRANSACTION_ID.getName(), responseObject.get("transactionid").toString())
            );
            successResponse.setVoidParams(new JSONObject()
                    .put(ParamList.TRANSACTION_ID.getName(), responseObject.get("transactionid").toString())
            );

        } else {
            errorResponse.setMessage(responseObject.getString("responsetext"));
        }

        //final response.
        if (successResponse != null) {
            successResponse.setGatewayResponse(responseObject);
            httpResponse.setContent(successResponse.getResponse().toString());
        } else {
            errorResponse.setGatewayResponse(responseObject);
            httpResponse.setContent(errorResponse.getResponse().toString());
        }

        return httpResponse;
    }

    @Override
    public HTTPResponse voidTransaction(JSONObject apiParameters, JSONObject voidParameters) {

        String requestString = QueryStringHelper.toQueryString(JSONHelper.encode(this.buildVoidParameters(apiParameters, voidParameters)));

        JSONObject responseObject;
        HTTPResponse httpResponse;

        VoidResponse successResponse = null;
        ErrorResponse errorResponse = new ErrorResponse();

        httpResponse = HTTPClient.httpPost(url, requestString, ContentType.APPLICATION_FORM_URLENCODED);
        if (httpResponse.getStatusCode() == -1) {
            return httpResponse;
        }

        responseObject = JSONHelper.decode(QueryStringHelper.toJson(httpResponse.getContent()));

        if (responseObject.getString("response").equals("1")) {
            httpResponse.setSuccessful(true);
            successResponse = new VoidResponse();

            successResponse.setMessage(responseObject.getString("responsetext"));
            successResponse.setTransactionId(responseObject.get("transactionid").toString());

        } else {
            errorResponse.setMessage(responseObject.getString("responsetext"));
        }

        //final response.
        if (successResponse != null) {
            successResponse.setGatewayResponse(responseObject);
            httpResponse.setContent(successResponse.getResponse().toString());
        } else {
            errorResponse.setGatewayResponse(responseObject);
            httpResponse.setContent(errorResponse.getResponse().toString());
        }

        return httpResponse;
    }

    @Override
    public JSONObject getApiSampleParameters() {
        return new JSONObject()
                .put("username", "the user name")
                .put("password", "merchant password.");
    }

    @Override
    public JSONObject getRefundSampleParameters() {
        return new JSONObject()
                .put(ParamList.TRANSACTION_ID.getName(), "the transaction id can also be used to refer this transaction later");
    }

    @Override
    public JSONObject getRebillSampleParameters() {
        return new JSONObject()
                .put("customerVaultId", "customer Vault ID required for recurring");
    }

    @Override
    public JSONObject getVoidSampleParameters() {
        return new JSONObject()
                .put(ParamList.TRANSACTION_ID.getName(), "the transaction id can also be used to refer this transaction later");
    }

    private JSONObject buildPurchaseParameters(JSONObject apiParameters, String customerVaultId, Customer customer, CustomerCard customerCard, Currency currency, float amount) {

        return new JSONObject()
                .put("type", "sale")
                .put("username", apiParameters.getString("username"))
                .put("password", apiParameters.getString("password"))
                .put("ccnumber", customerCard.getNumber())
                .put("cvv", customerCard.getCvv())
                .put("ccexp", customerCard.getExpiryMonth() + customerCard.getExpiryYear().substring(2))
                .put("amount", amount)
                .put("currency", currency)
                .put("billing_method", "recurring")
                .put("ipaddress", customer.getIp())
                .put("first_name", customer.getFirstName())
                .put("last_name", customer.getLastName())
                .put("address1", customer.getAddress())
                .put("city", customer.getCity())
                .put("state", customer.getState())
                .put("zip", customer.getZip())
                .put("country", customer.getCountry().getCodeISO2())
                .put("phone", customer.getPhoneNumber())
                .put("email", customer.getEmail())
                .put("customer_vault", "add_customer")
                .put("customer_vault_id", customerVaultId);
    }

    private JSONObject buildVoidParameters(JSONObject apiParameters, JSONObject voidParameters) {
        return new JSONObject()
                .put("type", "void")
                .put("username", apiParameters.getString("username"))
                .put("password", apiParameters.getString("password"))
                .put("transactionid", voidParameters.getString("transactionId"));
    }

    private JSONObject buildRefundParameters(JSONObject apiParameters, JSONObject refundParameters, float amount) {
        return new JSONObject()
                .put("type", "refund")
                .put("username", apiParameters.getString("username"))
                .put("password", apiParameters.getString("password"))
                .put("transactionid", refundParameters.getString("transactionId"))
                .put("amount", amount);
    }

    private JSONObject buildRebillParameters(JSONObject apiParameters, JSONObject rebillParameters, float amount) {
        return new JSONObject()
                .put("username", apiParameters.getString("username"))
                .put("password", apiParameters.getString("password"))
                .put("customer_vault_id", rebillParameters.getString("customerVaultId"))
                .put("amount", amount);
    }

    private String getUniqueVaultId() {
        String str = "123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        StringBuilder uniqueString = new StringBuilder(String.valueOf(System.currentTimeMillis()));

        Random random = new Random();

        while (uniqueString.length() < 20) {
            uniqueString.append(str.charAt(random.nextInt(str.length() - 1)));
        }

        return uniqueString.toString();
    }

}
