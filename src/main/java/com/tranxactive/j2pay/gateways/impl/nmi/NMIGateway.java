/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tranxactive.j2pay.gateways.impl.nmi;

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

import static com.tranxactive.j2pay.gateways.impl.nmi.Constants.API_URL;
import static com.tranxactive.j2pay.gateways.impl.nmi.Constants.RequestParameters.*;
import static com.tranxactive.j2pay.gateways.impl.nmi.Constants.ResponseParameters.RESPONSE_CODE;
import static com.tranxactive.j2pay.gateways.impl.nmi.Constants.ResponseParameters.RESPONSE_TEXT;
import static com.tranxactive.j2pay.gateways.impl.nmi.Constants.ResponseParameters.TRANSACTION_ID;
import static com.tranxactive.j2pay.util.ResponseProcessor.processFinalResponse;
import static org.apache.http.entity.ContentType.APPLICATION_FORM_URLENCODED;

/**
 *
 * @author ilyas
 */
public class NMIGateway extends Gateway {


    @Override
    public HTTPResponse purchase(JSONObject apiParameters, Customer customer, CustomerCard customerCard, Currency currency, float amount) {

        JSONObject requestObject = this.buildPurchaseParameters(apiParameters, customer, customerCard, currency, amount);
        JSONObject responseObject;
        String requestString;
        String responseString;

        requestObject = JSONHelper.encode(requestObject);
        requestString = QueryStringHelper.toQueryString(requestObject);
        HTTPResponse httpResponse;

        PurchaseResponse successResponse = null;
        ErrorResponse errorResponse = new ErrorResponse();

        httpResponse = HTTPClient.httpPost(API_URL, requestString, APPLICATION_FORM_URLENCODED);

        if (httpResponse.getStatusCode() == -1) {
            return httpResponse;
        }

        responseString = httpResponse.getContent();
        responseObject = JSONHelper.decode(QueryStringHelper.toJson(responseString));

        if (responseObject.getInt(RESPONSE_CODE) == 100) {
            httpResponse.setSuccessful(true);
            successResponse = new PurchaseResponse();
            successResponse.setMessage(responseObject.getString(RESPONSE_TEXT));
            successResponse.setTransactionId(responseObject.get(TRANSACTION_ID).toString());
            successResponse.setCardValuesFrom(customerCard);
            successResponse.setAmount(amount);
            successResponse.setCurrencyCode(currency);

            successResponse.setRebillParams(new JSONObject().put(CUSTOMER_VAULT_ID, responseObject.get(CUSTOMER_VAULT_ID).toString()));
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

        JSONObject requestObject = this.buildRefundParameters(apiParameters, refundParameters, amount);
        JSONObject responseObject;
        String requestString;
        String responseString;

        requestObject = JSONHelper.encode(requestObject);
        requestString = QueryStringHelper.toQueryString(requestObject);

        HTTPResponse httpResponse;

        RefundResponse successResponse = null;
        ErrorResponse errorResponse = new ErrorResponse();

        httpResponse = HTTPClient.httpPost(API_URL, requestString, APPLICATION_FORM_URLENCODED);

        if (httpResponse.getStatusCode() == -1) {
            return httpResponse;
        }

        responseString = httpResponse.getContent();
        responseObject = JSONHelper.decode(QueryStringHelper.toJson(responseString));

        if (responseObject.getInt(RESPONSE_CODE) == 100) {
            successResponse = new RefundResponse();
            successResponse.setMessage(responseObject.getString(RESPONSE_TEXT));
            successResponse.setTransactionId(responseObject.get(TRANSACTION_ID).toString());
            successResponse.setAmount(amount);

            successResponse.setVoidParams(new JSONObject().put(ParamList.TRANSACTION_ID.getName(), responseObject.get(TRANSACTION_ID).toString()));
        } else {
            errorResponse.setMessage(responseObject.getString(RESPONSE_TEXT));
        }

        //final response.
        processFinalResponse(responseObject, httpResponse, successResponse, errorResponse);
        return httpResponse;

    }

    @Override
    public HTTPResponse rebill(JSONObject apiParameters, JSONObject rebillParameters, float amount) {

        JSONObject requestObject = this.buildRebillParameters(apiParameters, rebillParameters, amount);
        JSONObject responseObject;
        String requestString;
        String responseString;
        requestObject = JSONHelper.encode(requestObject);
        requestString = QueryStringHelper.toQueryString(requestObject);
        HTTPResponse httpResponse;

        RebillResponse successResponse = null;
        ErrorResponse errorResponse = new ErrorResponse();

        httpResponse = HTTPClient.httpPost(API_URL, requestString, APPLICATION_FORM_URLENCODED);

        if (httpResponse.getStatusCode() == -1) {
            return httpResponse;
        }

        responseString = httpResponse.getContent();
        responseObject = JSONHelper.decode(QueryStringHelper.toJson(responseString));

        if (responseObject.getInt(RESPONSE_CODE) == 100) {
            httpResponse.setSuccessful(true);
            successResponse = new RebillResponse();

            successResponse.setMessage(responseObject.getString(RESPONSE_TEXT));
            successResponse.setTransactionId(responseObject.get(TRANSACTION_ID).toString());
            successResponse.setAmount(amount);
            successResponse.setRebillParams(rebillParameters);
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

        JSONObject requestObject = this.buildVoidParameters(apiParameters, voidParameters);
        JSONObject responseObject;
        String requestString;
        String responseString;

        requestObject = JSONHelper.encode(requestObject);
        requestString = QueryStringHelper.toQueryString(requestObject);
        HTTPResponse httpResponse;

        VoidResponse successResponse = null;
        ErrorResponse errorResponse = new ErrorResponse();

        httpResponse = HTTPClient.httpPost(API_URL, requestString, APPLICATION_FORM_URLENCODED);

        if (httpResponse.getStatusCode() == -1) {
            return httpResponse;
        }

        responseString = httpResponse.getContent();
        responseObject = JSONHelper.decode(QueryStringHelper.toJson(responseString));

        if (responseObject.getInt(RESPONSE_CODE) == 100) {
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
        return new JSONObject()
                .put(USERNAME, "the api user name use demo for testing")
                .put(PASSWORD, "the api password use password for testing");
    }

    @Override
    public JSONObject getRefundSampleParameters() {
        return new JSONObject().put(ParamList.TRANSACTION_ID.getName(), "the transaction id which will be refunded");
    }

    @Override
    public JSONObject getRebillSampleParameters() {
        return new JSONObject().put(CUSTOMER_VAULT_ID, "the customer vault id");
    }

    @Override
    public JSONObject getVoidSampleParameters() {
        return new JSONObject().put(ParamList.TRANSACTION_ID.getName(), "the transaction id which will be void");
    }

    //private methods are starting below.
    private JSONObject buildPurchaseParameters(JSONObject apiParameters, Customer customer, CustomerCard customerCard, Currency currency, float amount) {

        JSONObject object = new JSONObject();
        object
                .put(TRANSACTION_TYPE, TRANSACTION_TYPE_PURCHASE)
                .put(USERNAME, apiParameters.getString(USERNAME))
                .put(PASSWORD, apiParameters.getString(PASSWORD))
                .put(CREDIT_CARD_NUMBER, customerCard.getNumber())
                .put(CREDIT_CARD_EXPIRY_DATE, customerCard.getExpiryMonth() + customerCard.getExpiryYear().substring(2))
                .put(CREDIT_CARD_CVV, customerCard.getCvv())
                .put(AMOUNT, amount)
                .put(CURRENCY, currency)
                .put(FIRSTNAME, customer.getFirstName())
                .put(LASTNAME, customer.getLastName())
                .put(ADDRESS, customer.getAddress())
                .put(CITY, customer.getCity())
                .put(STATE, customer.getState())
                .put(ZIPCODE, customer.getZip())
                .put(COUNTRY, customer.getCountry().getCodeISO2())
                .put(PHONE_NUMBER, customer.getPhoneNumber())
                .put(EMAIL, customer.getEmail())
                .put(IP_ADDRESS, customer.getIp())
                .put(CUSTOMER_VAULT, ADD_CUSTOMER);

        return object;

    }

    private JSONObject buildVoidParameters(JSONObject apiParameters, JSONObject voidParameters) {

        JSONObject object = new JSONObject();
        object
                .put(TRANSACTION_TYPE, TRANSACTION_TYPE_VOID)
                .put(USERNAME, apiParameters.getString(USERNAME))
                .put(PASSWORD, apiParameters.getString(PASSWORD))
                .put(TRANSACTION_ID, voidParameters.getString(ParamList.TRANSACTION_ID.getName()));

        return object;
    }

    private JSONObject buildRefundParameters(JSONObject apiParameters, JSONObject voidParameters, float amount) {

        JSONObject object = new JSONObject();
        object
                .put(TRANSACTION_TYPE, TRANSACTION_TYPE_REFUND)
                .put(USERNAME, apiParameters.getString(USERNAME))
                .put(PASSWORD, apiParameters.getString(PASSWORD))
                .put(TRANSACTION_ID, voidParameters.getString(ParamList.TRANSACTION_ID.getName()))
                .put(AMOUNT, Float.toString(amount));

        return object;
    }

    private JSONObject buildRebillParameters(JSONObject apiParameters, JSONObject rebillParameters, float amount) {

        JSONObject object = new JSONObject();
        object
                .put(USERNAME, apiParameters.getString(USERNAME))
                .put(PASSWORD, apiParameters.getString(PASSWORD))
                .put(CUSTOMER_VAULT_ID, rebillParameters.getString(CUSTOMER_VAULT_ID))
                .put(AMOUNT, Float.toString(amount));

        return object;
    }

}
