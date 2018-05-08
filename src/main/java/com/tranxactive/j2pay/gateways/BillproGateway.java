/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tranxactive.j2pay.gateways;

import com.tranxactive.j2pay.gateways.core.Gateway;
import com.tranxactive.j2pay.gateways.parameters.Constants;
import com.tranxactive.j2pay.gateways.parameters.Currency;
import com.tranxactive.j2pay.gateways.parameters.Customer;
import com.tranxactive.j2pay.gateways.parameters.CustomerCard;
import com.tranxactive.j2pay.gateways.responses.*;
import com.tranxactive.j2pay.net.HTTPResponse;
import com.tranxactive.j2pay.net.XMLHelper;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.tranxactive.j2pay.gateways.parameters.Constants.Gateway.Billpro.RequestParameters.*;
import static com.tranxactive.j2pay.gateways.parameters.Constants.Gateway.Billpro.RequestParameters.REFERENCE;
import static com.tranxactive.j2pay.gateways.parameters.Constants.Gateway.Billpro.ResponseParameters.*;
import static com.tranxactive.j2pay.gateways.parameters.Constants.Gateway.Billpro.URL;
import static com.tranxactive.j2pay.gateways.parameters.ParamList.AMOUNT;
import static com.tranxactive.j2pay.gateways.parameters.ParamList.TRANSACTION_ID;
import static com.tranxactive.j2pay.gateways.util.RequestCreator.createRequest;
import static com.tranxactive.j2pay.gateways.util.ResponseProcessor.processFinalResponse;
import static com.tranxactive.j2pay.gateways.util.UniqueCustomerIdGenerator.getUniqueCustomerId;
import static com.tranxactive.j2pay.net.HTTPClient.httpPost;
import static org.apache.http.entity.ContentType.APPLICATION_XML;

/**
 * @author tkhan
 */
public class BillproGateway extends Gateway {

    @Override
    public HTTPResponse purchase(JSONObject apiParameters, Customer customer, CustomerCard customerCard, Currency currency, float amount) {

        JSONObject resp;
        int result;

        String reference = getUniqueCustomerId();
        String requestString = this.buildPurchaseParameters(apiParameters, reference, customer, customerCard, currency, amount);

        PurchaseResponse successResponse = null;
        ErrorResponse errorResponse = new ErrorResponse();

        HTTPResponse httpResponse = httpPost(URL, requestString, APPLICATION_XML);

        if (httpResponse.getStatusCode() == -1) {
            return httpResponse;
        }

        resp = XMLHelper.toJson(httpResponse.getContent());
        result = resp.getJSONObject(RESPONSE).getInt(RESPONSE_CODE);

        if (result == 100) {
            httpResponse.setSuccessful(true);
            successResponse = new PurchaseResponse();

            successResponse.setMessage(resp.getJSONObject(RESPONSE).getString(DESCRIPTION));
            successResponse.setTransactionId(resp.getJSONObject(RESPONSE).get(Constants.Gateway.Billpro.ResponseParameters.TRANSACTION_ID).toString());
            successResponse.setAmount(amount);
            successResponse.setCurrencyCode(currency);
            successResponse.setCardValuesFrom(customerCard);

            successResponse.setRebillParams(new JSONObject()
                    .put(TRANSACTION_ID.getName(), successResponse.getTransactionId())
                    .put(REFERENCE, reference)
            );
            successResponse.setRefundParams(new JSONObject().put(TRANSACTION_ID.getName(), successResponse.getTransactionId()));
            successResponse.setVoidParams(new JSONObject().put(TRANSACTION_ID.getName(), successResponse.getTransactionId()));
        } else {
            errorResponse.setMessage(resp.getJSONObject(RESPONSE).get(DESCRIPTION).toString());
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

        HTTPResponse httpResponse = httpPost(URL, requestString, APPLICATION_XML);

        if (httpResponse.getStatusCode() == -1) {
            return httpResponse;
        }

        resp = XMLHelper.toJson(httpResponse.getContent());
        result = resp.getJSONObject(RESPONSE).getInt(RESPONSE_CODE);

        if (result == 100) {
            httpResponse.setSuccessful(true);
            successResponse = new RefundResponse();

            successResponse.setMessage(resp.getJSONObject(RESPONSE).getString(DESCRIPTION));
            successResponse.setTransactionId(resp.getJSONObject(RESPONSE).get(Constants.Gateway.Billpro.ResponseParameters.TRANSACTION_ID).toString());
            successResponse.setAmount(amount);
            successResponse.setVoidParams(new JSONObject().put(TRANSACTION_ID.getName(), successResponse.getTransactionId()));

        } else {
            errorResponse.setMessage(resp.getJSONObject(RESPONSE).get(DESCRIPTION).toString());
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

        httpResponse = httpPost(URL, requestString, APPLICATION_XML);

        if (httpResponse.getStatusCode() == -1) {
            return httpResponse;
        }

        resp = XMLHelper.toJson(httpResponse.getContent());
        result = resp.getJSONObject(RESPONSE).getInt(RESPONSE_CODE);

        if (result == 100) {

            httpResponse.setSuccessful(true);
            successResponse = new RebillResponse();

            successResponse.setMessage(resp.getJSONObject(RESPONSE).getString(DESCRIPTION));
            successResponse.setTransactionId(resp.getJSONObject(RESPONSE).get(Constants.Gateway.Billpro.ResponseParameters.TRANSACTION_ID).toString());
            successResponse.setAmount(amount);

            successResponse.setRebillParams(new JSONObject()
                    .put(TRANSACTION_ID.getName(), successResponse.getTransactionId())
                    .put(REFERENCE, rebillParameters.getString(REFERENCE))
            );

            successResponse.setRefundParams(new JSONObject().put(TRANSACTION_ID.getName(), successResponse.getTransactionId()));
            successResponse.setVoidParams(new JSONObject().put(TRANSACTION_ID.getName(), successResponse.getTransactionId()));
        } else {
            errorResponse.setMessage(resp.getJSONObject(RESPONSE).get(DESCRIPTION).toString());
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

        HTTPResponse httpResponse = httpPost(URL, requestString, APPLICATION_XML);

        if (httpResponse.getStatusCode() == -1) {
            return httpResponse;
        }

        resp = XMLHelper.toJson(httpResponse.getContent());
        result = resp.getJSONObject(RESPONSE).getInt(RESPONSE_CODE);

        if (result == 100) {
            httpResponse.setSuccessful(true);
            successResponse = new VoidResponse();

            successResponse.setMessage(resp.getJSONObject(RESPONSE).getString(DESCRIPTION));
            successResponse.setTransactionId(resp.getJSONObject(RESPONSE).get(Constants.Gateway.Billpro.ResponseParameters.TRANSACTION_ID).toString());
        } else {
            errorResponse.setMessage(resp.getJSONObject(RESPONSE).get(DESCRIPTION).toString());
        }

        //final response.
        processFinalResponse(resp, httpResponse, successResponse, errorResponse);
        return httpResponse;
    }

    @Override
    public JSONObject getApiSampleParameters() {
        return new JSONObject()
                .put(ACCOUNT_ID, "the gateway Account id")
                .put(ACCOUNT_AUTHORIZATION, "the gateway alpha-numeric Auth");
    }

    @Override
    public JSONObject getRefundSampleParameters() {
        return new JSONObject().put(TRANSACTION_ID.getName(), "the transaction id which will be refunded");
    }

    @Override
    public JSONObject getRebillSampleParameters() {
        return new JSONObject()
                .put(TRANSACTION_ID.getName(), "the transaction id of last successfull charge")
                .put(REFERENCE, "reference of last successfull charge transaction");
    }

    @Override
    public JSONObject getVoidSampleParameters() {

        return new JSONObject().put(TRANSACTION_ID.getName(), "the transaction id which will be refunded");

    }

    //private methods are starting below.
    private String buildPurchaseParameters(JSONObject apiParameters, String reference, Customer customer, CustomerCard customerCard, Currency currency, float amount) {
	    final Map<String, Object> input = new HashMap<>();
	    input.put(ACCOUNT_ID, apiParameters.getString(ACCOUNT_ID));
	    input.put(ACCOUNT_AUTHORIZATION, apiParameters.getString(ACCOUNT_AUTHORIZATION));
	    input.put(REFERENCE, reference);
	    input.put(AMOUNT.getName(), Float.toString(amount));
	    input.put(CURRENCY, currency);
	    input.put(EMAIL_ADDRESS, customer.getEmail());
	    input.put(IP_ADDRESS, customer.getIp());
	    input.put(PHONE_NUMBER, customer.getPhoneNumber());
	    input.put(FIRSTNAME, customer.getFirstName());
	    input.put(LASTNAME, customer.getLastName());
	    input.put(ADDRESS, customer.getAddress());
	    input.put(CITY, customer.getCity());
	    input.put(STATE, customer.getState());
	    input.put(ZIPCODE, customer.getZip());
	    input.put(COUNTRY, customer.getCountry().getCodeISO2());
	    input.put(CREDIT_CARD_NUMBER, customerCard.getNumber());
	    input.put(CREDIT_CARD_EXPIRY_MONTH, customerCard.getExpiryMonth());
	    input.put(CREDIT_CARD_EXPIRY_YEAR, customerCard.getExpiryYear());
	    input.put(CREDIT_CARD_CVV, customerCard.getCvv());

	    return createRequest(input, "Billpro/PurchaseRequest.ftl");
    }

    private String buildRefundParameters(JSONObject apiParameters, JSONObject refundParameters, float amount) {
	    final Map<String, Object> input = new HashMap<>();
	    input.put(ACCOUNT_ID, apiParameters.getString(ACCOUNT_ID));
	    input.put(ACCOUNT_AUTHORIZATION, apiParameters.getString(ACCOUNT_AUTHORIZATION));
	    input.put(AMOUNT.getName(), Float.toString(amount));
	    input.put(TRANSACTION_ID.getName(), refundParameters.get(TRANSACTION_ID.getName()).toString());

	    return createRequest(input, "Billpro/RefundRequest.ftl");
    }

    private String buildVoidParameters(JSONObject apiParameters, JSONObject voidParameters) {
	    final Map<String, Object> input = new HashMap<>();
	    input.put(ACCOUNT_ID, apiParameters.getString(ACCOUNT_ID));
	    input.put(ACCOUNT_AUTHORIZATION, apiParameters.getString(ACCOUNT_AUTHORIZATION));
	    input.put(TRANSACTION_ID.getName(), voidParameters.get(TRANSACTION_ID.getName()).toString());

	    return createRequest(input, "Billpro/VoidRequest.ftl");
    }

    private String buildRebillParameters(JSONObject apiParameters, JSONObject rebillParameters, float amount) {
	    final Map<String, Object> input = new HashMap<>();
	    input.put(ACCOUNT_ID, apiParameters.getString(ACCOUNT_ID));
	    input.put(ACCOUNT_AUTHORIZATION, apiParameters.getString(ACCOUNT_AUTHORIZATION));
	    input.put(REFERENCE, rebillParameters.getString(REFERENCE));
	    input.put(AMOUNT.getName(), Float.toString(amount));
	    input.put(TRANSACTION_ID.getName(), rebillParameters.get(TRANSACTION_ID.getName()).toString());

	    return createRequest(input, "Billpro/RebillRequest.ftl");
    }
}
