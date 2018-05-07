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
import com.tranxactive.j2pay.net.XMLHelper;
import org.apache.http.entity.ContentType;
import org.json.JSONObject;

import static com.tranxactive.j2pay.gateways.util.ResponseProcessor.processFinalResponse;
import static com.tranxactive.j2pay.gateways.util.UniqueCustomerIdGenerator.getUniqueCustomerId;

/**
 * @author tkhan
 */
public class BillproGateway extends Gateway {

    private final String url = "https://gateway.billpro.com";

    @Override
    public HTTPResponse purchase(JSONObject apiParameters, Customer customer, CustomerCard customerCard, Currency currency, float amount) {

        JSONObject resp;
        int result;

        String reference = getUniqueCustomerId();
        String requestString = this.buildPurchaseParameters(apiParameters, reference, customer, customerCard, currency, amount);

        PurchaseResponse successResponse = null;
        ErrorResponse errorResponse = new ErrorResponse();

        HTTPResponse httpResponse = HTTPClient.httpPost(url, requestString, ContentType.APPLICATION_XML);

        if (httpResponse.getStatusCode() == -1) {
            return httpResponse;
        }

        resp = XMLHelper.toJson(httpResponse.getContent());
        result = resp.getJSONObject("Response").getInt("ResponseCode");

        if (result == 100) {
            httpResponse.setSuccessful(true);
            successResponse = new PurchaseResponse();

            successResponse.setMessage(resp.getJSONObject("Response").getString("Description"));
            successResponse.setTransactionId(resp.getJSONObject("Response").get("TransactionID").toString());
            successResponse.setAmount(amount);
            successResponse.setCurrencyCode(currency);
            successResponse.setCardValuesFrom(customerCard);

            successResponse.setRebillParams(new JSONObject()
                    .put(ParamList.TRANSACTION_ID.getName(), successResponse.getTransactionId())
                    .put("Reference", reference)
            );

            successResponse.setRefundParams(new JSONObject()
                    .put(ParamList.TRANSACTION_ID.getName(), successResponse.getTransactionId())
            );

            successResponse.setVoidParams(new JSONObject()
                    .put(ParamList.TRANSACTION_ID.getName(), successResponse.getTransactionId())
            );

        } else {
            errorResponse.setMessage(resp.getJSONObject("Response").get("Description").toString());
        }

        //final response.
        processFinalResponse(resp, httpResponse, successResponse, errorResponse);
        return httpResponse;
    }

    @Override
    public HTTPResponse refund(JSONObject apiParameters, JSONObject refundParameters, float amount) {
        JSONObject resp;
        int result;

        String requestString = this.buildRefundParameters(apiParameters, refundParameters, amount);

        RefundResponse successResponse = null;
        ErrorResponse errorResponse = new ErrorResponse();

        HTTPResponse httpResponse = HTTPClient.httpPost(url, requestString, ContentType.APPLICATION_XML);

        if (httpResponse.getStatusCode() == -1) {
            return httpResponse;
        }

        resp = XMLHelper.toJson(httpResponse.getContent());
        result = resp.getJSONObject("Response").getInt("ResponseCode");

        if (result == 100) {
            httpResponse.setSuccessful(true);
            successResponse = new RefundResponse();

            successResponse.setMessage(resp.getJSONObject("Response").getString("Description"));
            successResponse.setTransactionId(resp.getJSONObject("Response").get("TransactionID").toString());
            successResponse.setAmount(amount);

            successResponse.setVoidParams(new JSONObject()
                    .put(ParamList.TRANSACTION_ID.getName(), successResponse.getTransactionId())
            );

        } else {
            errorResponse.setMessage(resp.getJSONObject("Response").get("Description").toString());
        }

        //final response.
        processFinalResponse(resp, httpResponse, successResponse, errorResponse);
        return httpResponse;

    }

    @Override
    public HTTPResponse rebill(JSONObject apiParameters, JSONObject rebillParameters, float amount) {

        JSONObject resp;
        int result;
        RebillResponse successResponse = null;
        ErrorResponse errorResponse = new ErrorResponse();
        HTTPResponse httpResponse;

        String requestString = this.buildRebillParameters(apiParameters, rebillParameters, amount);

        httpResponse = HTTPClient.httpPost(url, requestString, ContentType.APPLICATION_XML);

        if (httpResponse.getStatusCode() == -1) {
            return httpResponse;
        }

        resp = XMLHelper.toJson(httpResponse.getContent());
        result = resp.getJSONObject("Response").getInt("ResponseCode");

        if (result == 100) {

            httpResponse.setSuccessful(true);
            successResponse = new RebillResponse();

            successResponse.setMessage(resp.getJSONObject("Response").getString("Description"));
            successResponse.setTransactionId(resp.getJSONObject("Response").get("TransactionID").toString());
            successResponse.setAmount(amount);

            successResponse.setRebillParams(new JSONObject()
                    .put(ParamList.TRANSACTION_ID.getName(), successResponse.getTransactionId())
                    .put("Reference", rebillParameters.getString("Reference"))
            );

            successResponse.setRefundParams(new JSONObject()
                    .put(ParamList.TRANSACTION_ID.getName(), successResponse.getTransactionId())
            );

            successResponse.setVoidParams(new JSONObject()
                    .put(ParamList.TRANSACTION_ID.getName(), successResponse.getTransactionId())
            );

        } else {
            errorResponse.setMessage(resp.getJSONObject("Response").get("Description").toString());
        }

        //final response.
        processFinalResponse(resp, httpResponse, successResponse, errorResponse);
        return httpResponse;
    }

    @Override
    public HTTPResponse voidTransaction(JSONObject apiParameters, JSONObject voidParameters) {
        JSONObject resp;
        int result;

        String requestString = this.buildVoidParameters(apiParameters, voidParameters);

        VoidResponse successResponse = null;
        ErrorResponse errorResponse = new ErrorResponse();

        HTTPResponse httpResponse = HTTPClient.httpPost(url, requestString, ContentType.APPLICATION_XML);

        if (httpResponse.getStatusCode() == -1) {
            return httpResponse;
        }

        resp = XMLHelper.toJson(httpResponse.getContent());
        result = resp.getJSONObject("Response").getInt("ResponseCode");

        if (result == 100) {
            httpResponse.setSuccessful(true);
            successResponse = new VoidResponse();

            successResponse.setMessage(resp.getJSONObject("Response").getString("Description"));
            successResponse.setTransactionId(resp.getJSONObject("Response").get("TransactionID").toString());

        } else {
            errorResponse.setMessage(resp.getJSONObject("Response").get("Description").toString());
        }

        //final response.
        processFinalResponse(resp, httpResponse, successResponse, errorResponse);
        return httpResponse;
    }

    @Override
    public JSONObject getApiSampleParameters() {
        return new JSONObject()
                .put("AccountID", "the gateway Account id")
                .put("AccountAuth", "the gateway alpha-numeric Auth");
    }

    @Override
    public JSONObject getRefundSampleParameters() {
        return new JSONObject()
                .put(ParamList.TRANSACTION_ID.getName(), "the transaction id which will be refunded");
    }

    @Override
    public JSONObject getRebillSampleParameters() {
        return new JSONObject()
                .put(ParamList.TRANSACTION_ID.getName(), "the transaction id of last successfull charge")
                .put("Reference", "reference of last successfull charge transaction");
    }

    @Override
    public JSONObject getVoidSampleParameters() {

        return new JSONObject()
                .put(ParamList.TRANSACTION_ID.getName(), "the transaction id which will be refunded");

    }

    //private methods are starting below.
    private String buildPurchaseParameters(JSONObject apiParameters, String reference, Customer customer, CustomerCard customerCard, Currency currency, float amount) {

        StringBuilder finalParams = new StringBuilder();

        finalParams
                .append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>")
                .append("<Request type='AuthorizeCapture'>")
                .append("<AccountID>").append(apiParameters.getString("AccountID")).append("</AccountID>")
                .append("<AccountAuth>").append(apiParameters.getString("AccountAuth")).append("</AccountAuth>")
                .append("<Transaction>")
                .append("<Reference>").append(reference).append("</Reference>")
                .append("<Amount>").append(Float.toString(amount)).append("</Amount>")
                .append("<Currency>").append(currency).append("</Currency>")
                .append("<Email>").append(customer.getEmail()).append("</Email>")
                .append("<IPAddress>").append(customer.getIp()).append("</IPAddress>")
                .append("<Phone>").append(customer.getPhoneNumber()).append("</Phone>")
                .append("<FirstName>").append(customer.getFirstName()).append("</FirstName>")
                .append("<LastName>").append(customer.getLastName()).append("</LastName>")
                .append("<Address>").append(customer.getAddress()).append("</Address>")
                .append("<City>").append(customer.getCity()).append("</City>")
                .append("<State>").append(customer.getState()).append("</State>")
                .append("<PostCode>").append(customer.getZip()).append("</PostCode>")
                .append("<Country>").append(customer.getCountry().getCodeISO2()).append("</Country>")
                .append("<CardNumber>").append(customerCard.getNumber()).append("</CardNumber>")
                .append("<CardExpMonth>").append(customerCard.getExpiryMonth()).append("</CardExpMonth>")
                .append("<CardExpYear>").append(customerCard.getExpiryYear()).append("</CardExpYear>")
                .append("<CardCVV>").append(customerCard.getCvv()).append("</CardCVV>")
                .append("</Transaction>")
                .append("</Request>");

        return finalParams.toString();

    }

    private String buildRefundParameters(JSONObject apiParameters, JSONObject refundParameters, float amount) {

        StringBuilder finalParams = new StringBuilder();

        finalParams
                .append("<?xml version='1.0' encoding='UTF-8'?>")
                .append("<Request type='Refund'>")
                .append("<AccountID>").append(apiParameters.getString("AccountID")).append("</AccountID>")
                .append("<AccountAuth>").append(apiParameters.getString("AccountAuth")).append("</AccountAuth>")
                .append("<Transaction>")
                .append("<Amount>").append(Float.toString(amount)).append("</Amount>")
                .append("<TransactionID>").append(refundParameters.get(ParamList.TRANSACTION_ID.getName()).toString()).append("</TransactionID>")
                .append("</Transaction>")
                .append("</Request>");

        return finalParams.toString();

    }

    private String buildVoidParameters(JSONObject apiParameters, JSONObject voidParameters) {

        StringBuilder finalParams = new StringBuilder();

        finalParams
                .append("<?xml version='1.0' encoding='UTF-8'?>")
                .append("<Request type='Void'>")
                .append("<AccountID>").append(apiParameters.getString("AccountID")).append("</AccountID>")
                .append("<AccountAuth>").append(apiParameters.getString("AccountAuth")).append("</AccountAuth>")
                .append("<Transaction>")
                .append("<TransactionID>").append(voidParameters.get(ParamList.TRANSACTION_ID.getName()).toString()).append("</TransactionID>")
                .append("</Transaction>")
                .append("</Request>");

        return finalParams.toString();
    }

    private String buildRebillParameters(JSONObject apiParameters, JSONObject rebillParameters, float amount) {

        StringBuilder finalParams = new StringBuilder();

        finalParams
                .append("<?xml version='1.0' encoding='utf-8'?>")
                .append("<Request type='Recur'>")
                .append("<AccountID>").append(apiParameters.getString("AccountID")).append("</AccountID>")
                .append("<AccountAuth>").append(apiParameters.getString("AccountAuth")).append("</AccountAuth>")
                .append("<Transaction>")
                .append("<Reference>").append(rebillParameters.getString("Reference")).append("</Reference>")
                .append("<Amount>").append(Float.toString(amount)).append("</Amount>")
                .append("<TransactionID>").append(rebillParameters.get(ParamList.TRANSACTION_ID.getName()).toString()).append("</TransactionID>")
                .append("</Transaction>")
                .append("</Request>");

        return finalParams.toString();
    }
}
