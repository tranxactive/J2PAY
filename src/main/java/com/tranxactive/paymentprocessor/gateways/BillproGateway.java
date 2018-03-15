/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tranxactive.paymentprocessor.gateways;

import com.tranxactive.paymentprocessor.gateways.core.Gateway;
import com.tranxactive.paymentprocessor.gateways.parameters.Currency;
import com.tranxactive.paymentprocessor.gateways.parameters.Customer;
import com.tranxactive.paymentprocessor.gateways.parameters.CustomerCard;
import com.tranxactive.paymentprocessor.gateways.parameters.ParamList;
import com.tranxactive.paymentprocessor.gateways.responses.*;
import com.tranxactive.paymentprocessor.net.HTTPClient;
import com.tranxactive.paymentprocessor.net.HTTPResponse;
import com.tranxactive.paymentprocessor.net.XMLHelper;
import org.apache.http.entity.ContentType;
import org.json.JSONObject;

import java.util.Random;

/**
 * @author tkhan
 */
public class BillproGateway extends Gateway {

    private final String url = "https://gateway.billpro.com";

//    public BillproGateway() {
//        super(false);
//    }

    @Override
    public HTTPResponse purchase(JSONObject apiParameters, Customer customer, CustomerCard customerCard, Currency currency, float amount) {
        JSONObject resp = null;
        int result;

        String reference = this.getUniqueCustomerId();
        apiParameters.put("Reference", reference);
        String requestString = this.buildPurchaseParameters(apiParameters, customer, customerCard, currency, amount);

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
                    .put(ParamList.AMOUNT.getName(), amount)
            );

        } else {
            errorResponse.setMessage(resp.getJSONObject("Response").get("Description").toString());
        }


        //final response.
        if (successResponse != null) {
            successResponse.setGatewayResponse(resp);
            httpResponse.setContent(successResponse.getResponse().toString());
        } else {
            errorResponse.setGatewayResponse(resp);
            httpResponse.setContent(errorResponse.getResponse().toString());
        }

        return httpResponse;
    }

    @Override
    public HTTPResponse refund(JSONObject apiParameters, JSONObject refundParameters, float amount) {
        JSONObject resp = null;
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
                    .put(ParamList.AMOUNT.getName(), amount)
            );

        } else {
            errorResponse.setMessage(resp.getJSONObject("Response").get("Description").toString());
        }


        //final response.
        if (successResponse != null) {
            successResponse.setGatewayResponse(resp);
            httpResponse.setContent(successResponse.getResponse().toString());
        } else {
            errorResponse.setGatewayResponse(resp);
            httpResponse.setContent(errorResponse.getResponse().toString());
        }

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
                    .put(ParamList.AMOUNT.getName(), amount)
            );

        } else {
            errorResponse.setMessage(resp.getJSONObject("Response").get("Description").toString());
        }

        //final response.
        if (successResponse != null) {
            successResponse.setGatewayResponse(resp);
            httpResponse.setContent(successResponse.getResponse().toString());
        } else {
            errorResponse.setGatewayResponse(resp);
            httpResponse.setContent(errorResponse.getResponse().toString());
        }

        return httpResponse;
    }

    @Override
    public HTTPResponse voidTransaction(JSONObject apiParameters, JSONObject voidParameters) {
        JSONObject resp = null;
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
        if (successResponse != null) {
            successResponse.setGatewayResponse(resp);
            httpResponse.setContent(successResponse.getResponse().toString());
        } else {
            errorResponse.setGatewayResponse(resp);
            httpResponse.setContent(errorResponse.getResponse().toString());
        }

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
                .put(ParamList.TRANSACTION_ID.getName(), "the transaction id which will be refunded")
                .put(ParamList.AMOUNT.getName(), "the actual amount of original transaction");

    }

    //private methods are starting below.
    private String buildPurchaseParameters(JSONObject apiParameters, Customer customer, CustomerCard customerCard, Currency currency, float amount) {

        StringBuilder finalParams = new StringBuilder();

        finalParams
                .append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>")
                .append("<Request type='AuthorizeCapture'>")
                .append("<AccountID>").append(apiParameters.getString("AccountID")).append("</AccountID>")
                .append("<AccountAuth>").append(apiParameters.getString("AccountAuth")).append("</AccountAuth>")
                .append("<Transaction>")
                .append("<Reference>").append(apiParameters.getString("Reference")).append("</Reference>")
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
                .append("<Country>").append(customer.getCountry()).append("</Country>")
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
                .append("<Request type='Refund'>")
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

    private String getUniqueCustomerId() {
        String str = "123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        StringBuilder uniqueString = new StringBuilder(String.valueOf(System.currentTimeMillis()));

        Random random = new Random();

        while (uniqueString.length() < 20) {
            uniqueString.append(str.charAt(random.nextInt(str.length() - 1)));
        }

        return uniqueString.toString();
    }

}
