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
import com.tranxactive.j2pay.gateways.responses.*;
import com.tranxactive.j2pay.net.HTTPClient;
import com.tranxactive.j2pay.net.HTTPResponse;
import com.tranxactive.j2pay.net.JSONHelper;
import org.apache.http.entity.ContentType;
import org.json.JSONObject;

import static com.tranxactive.j2pay.gateways.util.ResponseProcessor.processFinalResponse;

import java.util.HashMap;

import static com.tranxactive.j2pay.gateways.util.UniqueCustomerIdGenerator.getUniqueVaultId;

/**
 * @author Tkhan
 */
public class CheckoutGateway extends Gateway {

    @Override
    public HTTPResponse purchase(JSONObject apiParameters, Customer customer, CustomerCard customerCard, Currency currency, float amount) {

        String trackId = getUniqueVaultId();
        JSONObject requestObject = this.buildPurchaseParameters(trackId, customer, customerCard, currency, amount);
        JSONObject responseObject;
        HTTPResponse httpResponse;

        PurchaseResponse successResponse = null;
        ErrorResponse errorResponse = new ErrorResponse();

        HashMap headers = new HashMap<String, String>();
        headers.put("Authorization", apiParameters.getString("Authorization"));

        httpResponse = HTTPClient.httpPost(this.getApiURL() + "/card", requestObject.toString(), ContentType.APPLICATION_JSON, headers);

        if (httpResponse.getStatusCode() == -1) {
            return httpResponse;
        }

        responseObject = httpResponse.getJSONResponse();

        if (responseObject.has("responseCode") && (responseObject.get("responseCode").toString().startsWith("10"))) {

            httpResponse.setSuccessful(true);
            successResponse = new PurchaseResponse();
            successResponse.setMessage(responseObject.get("responseMessage").toString());
            successResponse.setTransactionId(responseObject.get("id").toString());
            successResponse.setCardValuesFrom(customerCard);
            successResponse.setAmount(amount);
            successResponse.setCurrencyCode(currency);

            successResponse.setRebillParams(new JSONObject()
                    .put("customerId", responseObject.getJSONObject("card").get("customerId").toString())
                    .put("currency", currency)
                    .put("cardId", responseObject.getJSONObject("card").get("id").toString())
                    .put("trackId", trackId)
            );

            successResponse.setRefundParams(new JSONObject()
                    .put(ParamList.TRANSACTION_ID.getName(), responseObject.get("id").toString())
                    .put("trackId", trackId)
            );

            successResponse.setVoidParams(new JSONObject()
                    .put(ParamList.TRANSACTION_ID.getName(), responseObject.get("id").toString())
                    .put("trackId", trackId)
            );

        } else {
            errorResponse.setMessage(responseObject.get("message").toString());
        }

        //final response.
        processFinalResponse(responseObject, httpResponse, successResponse, errorResponse);
        return httpResponse;

    }

    @Override
    public HTTPResponse refund(JSONObject apiParameters, JSONObject refundParameters, float amount) {

        JSONObject requestObject = this.buildRefundParameters(amount);
        JSONObject responseObject;

        requestObject = JSONHelper.encode(requestObject);
        HTTPResponse httpResponse;

        RefundResponse successResponse = null;
        ErrorResponse errorResponse = new ErrorResponse();

        HashMap headers = new HashMap<String, String>();
        headers.put("Authorization", apiParameters.getString("Authorization"));

        httpResponse = HTTPClient.httpPost(this.getApiURL() + "/" + refundParameters.get("transactionId") + "/refund", requestObject.toString(), ContentType.APPLICATION_JSON, headers);

        if (httpResponse.getStatusCode() == -1) {
            return httpResponse;
        }

        responseObject = httpResponse.getJSONResponse();

        if (responseObject.has("responseCode") && (responseObject.get("responseCode").toString().startsWith("10"))) {
            successResponse = new RefundResponse();
            successResponse.setMessage(responseObject.get("responseMessage").toString());
            successResponse.setTransactionId(responseObject.get("id").toString());
            successResponse.setAmount(amount);

            successResponse.setVoidParams(new JSONObject()
                    .put(ParamList.TRANSACTION_ID.getName(), responseObject.get("id").toString())
                    .put("trackId", refundParameters.get("trackId").toString())
            );
        } else {
            errorResponse.setMessage(responseObject.get("message").toString());
        }

        //final response.
        processFinalResponse(responseObject, httpResponse, successResponse, errorResponse);
        return httpResponse;

    }

    @Override
    public HTTPResponse rebill(JSONObject apiParameters, JSONObject rebillParameters, float amount) {

        JSONObject requestObject = this.buildRebillParameters(rebillParameters, amount);
        JSONObject responseObject;

        requestObject = JSONHelper.encode(requestObject);
        HTTPResponse httpResponse;

        RebillResponse successResponse = null;
        ErrorResponse errorResponse = new ErrorResponse();

        HashMap headers = new HashMap<String, String>();
        headers.put("Authorization", apiParameters.getString("Authorization"));

        httpResponse = HTTPClient.httpPost(this.getApiURL() + "/card", requestObject.toString(), ContentType.APPLICATION_JSON, headers);

        if (httpResponse.getStatusCode() == -1) {
            return httpResponse;
        }

        responseObject = httpResponse.getJSONResponse();

        if (responseObject.has("responseCode") && (responseObject.get("responseCode").toString().startsWith("10"))) {
            httpResponse.setSuccessful(true);
            successResponse = new RebillResponse();

            successResponse.setMessage(responseObject.get("responseMessage").toString());
            successResponse.setTransactionId(responseObject.get("id").toString());
            successResponse.setAmount(amount);
            successResponse.setRebillParams(rebillParameters);

            successResponse.setRefundParams(new JSONObject()
                    .put(ParamList.TRANSACTION_ID.getName(), responseObject.get("id").toString())
                    .put("trackId", rebillParameters.get("trackId").toString())
            );

            successResponse.setVoidParams(new JSONObject()
                    .put(ParamList.TRANSACTION_ID.getName(), responseObject.get("id").toString())
                    .put("trackId", rebillParameters.get("trackId").toString())
            );

        } else {
            errorResponse.setMessage(responseObject.get("message").toString());
        }

        //final response.
        processFinalResponse(responseObject, httpResponse, successResponse, errorResponse);
        return httpResponse;
    }

    @Override
    public HTTPResponse voidTransaction(JSONObject apiParameters, JSONObject voidParameters) {

        JSONObject requestObject = this.buildVoidParameters(voidParameters);
        JSONObject responseObject;

        requestObject = JSONHelper.encode(requestObject);
        HTTPResponse httpResponse;

        HashMap headers = new HashMap<String, String>();
        headers.put("Authorization", apiParameters.getString("Authorization"));

        VoidResponse successResponse = null;
        ErrorResponse errorResponse = new ErrorResponse();
        httpResponse = HTTPClient.httpPost(this.getApiURL() + "/" + voidParameters.get("transactionId") + "/void", requestObject.toString(), ContentType.APPLICATION_JSON, headers);

        if (httpResponse.getStatusCode() == -1) {
            return httpResponse;
        }

        responseObject = httpResponse.getJSONResponse();

        if (responseObject.has("responseCode") && (responseObject.get("responseCode").toString().startsWith("10"))) {
            httpResponse.setSuccessful(true);
            successResponse = new VoidResponse();

            successResponse.setMessage(responseObject.get("responseMessage").toString());
            successResponse.setTransactionId(responseObject.get("id").toString());

        } else {
            errorResponse.setMessage(responseObject.get("message").toString());
        }

        //final response.
        processFinalResponse(responseObject, httpResponse, successResponse, errorResponse);
        return httpResponse;
    }

    @Override
    public JSONObject getApiSampleParameters() {
        return new JSONObject()
                .put("Authorization", "Your Authorization key");
    }

    @Override
    public JSONObject getRefundSampleParameters() {
        return new JSONObject()
                .put(ParamList.TRANSACTION_ID.getName(), "Transaction ID to refer which")
                .put("trackId", "Optional Tracking ID provided by our system on successful purchase response");
    }

    @Override
    public JSONObject getRebillSampleParameters() {
        return new JSONObject()
                .put("autoCapTime", "1")
                .put("autoCapture", "Y")
                .put("customerId", "Customer ID provided by our system on successful purchase response")
                .put("currency", "Amount Currency")
                .put("cardId", "Card ID provided by our system on successful purchase response")
                .put("trackId", "Optional Tracking ID provided by our system on successful purchase response");
    }

    @Override
    public JSONObject getVoidSampleParameters() {
        return new JSONObject()
                .put(ParamList.TRANSACTION_ID.getName(), "Transaction ID to refer which")
                .put("trackId", "Optional Tracking ID provided by our system on successful purchase response");
    }

    //private methods are starting below.
    private JSONObject buildPurchaseParameters(String trackId, Customer customer, CustomerCard customerCard, Currency currency, float amount) {

        JSONObject object = new JSONObject();

        object
                .put("autoCapTime", "1")
                .put("autoCapture", "Y")
                .put("email", customer.getEmail())
                .put("customerName", customer.getFirstName() + " " + customer.getLastName())
                .put("value", amount * 100)
                .put("currency", currency)
                .put("trackId", trackId)
                .put("transactionIndicator", "2")
                .put("customerIp", customer.getIp())
                .put("card", new JSONObject()
                        .put("name", customer.getFirstName() + " " + customer.getLastName())
                        .put("number", customerCard.getNumber())
                        .put("expiryMonth", customerCard.getExpiryMonth())
                        .put("expiryYear", customerCard.getExpiryYear())
                        .put("cvv", customerCard.getCvv())
                        .put("billingDetails", new JSONObject()
                                .put("addressLine1", customer.getAddress())
                                .put("postcode", customer.getZip())
                                .put("country", customer.getCountry().getCodeISO2())
                                .put("city", customer.getCity())
                                .put("state", customer.getState())
                                .put("phone", new JSONObject()
                                        .put("number", customer.getPhoneNumber())
                                )
                        )
                );

        return object;

    }

    private JSONObject buildVoidParameters(JSONObject voidParameters) {

        JSONObject object = new JSONObject()
                .put("trackId", voidParameters.get("trackId").toString());

        return object;
    }

    private JSONObject buildRefundParameters(float amount) {

        JSONObject object = new JSONObject()
                .put("value", amount * 100);

        return object;
    }

    private JSONObject buildRebillParameters(JSONObject rebillParameters, float amount) {

        JSONObject object = new JSONObject()
                .put("autoCapTime", "1")
                .put("autoCapture", "Y")
                .put("customerId", rebillParameters.get("customerId").toString())
                .put("value", amount * 100)
                .put("currency", rebillParameters.get("currency").toString())
                .put("trackId", rebillParameters.get("trackId").toString())
                .put("cardId", rebillParameters.get("cardId").toString());


        return object;
    }

    private String getApiURL() {
        return "https://" + (isTestMode() ? "sandbox" : "api2") + ".checkout.com/" + (isTestMode() ? "api2/" : "") + "v2/charges";
    }

}
