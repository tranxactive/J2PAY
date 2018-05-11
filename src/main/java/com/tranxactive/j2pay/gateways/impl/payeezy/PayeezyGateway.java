/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tranxactive.j2pay.gateways.impl.payeezy;

import com.tranxactive.j2pay.gateways.core.Gateway;
import com.tranxactive.j2pay.helpers.entities.Currency;
import com.tranxactive.j2pay.helpers.entities.Customer;
import com.tranxactive.j2pay.helpers.entities.CustomerCard;
import com.tranxactive.j2pay.gateways.responses.ErrorResponse;
import com.tranxactive.j2pay.gateways.responses.PurchaseResponse;
import com.tranxactive.j2pay.gateways.responses.RefundResponse;
import com.tranxactive.j2pay.gateways.responses.VoidResponse;
import com.tranxactive.j2pay.helpers.net.HTTPClient;
import com.tranxactive.j2pay.helpers.net.HTTPResponse;
import com.tranxactive.j2pay.helpers.net.XMLHelper;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.tranxactive.j2pay.gateways.parameters.ParamList.AMOUNT;
import static com.tranxactive.j2pay.gateways.parameters.ParamList.TRANSACTION_ID;
import static com.tranxactive.j2pay.gateways.impl.payeezy.Constants.*;
import static com.tranxactive.j2pay.gateways.impl.payeezy.Constants.RequestParameters.TRANSACTION_KEY;
import static com.tranxactive.j2pay.gateways.impl.payeezy.Constants.ResponseParameters.*;
import static com.tranxactive.j2pay.util.RequestCreator.createRequest;
import static com.tranxactive.j2pay.util.ResponseProcessor.processFinalResponse;
import static org.apache.http.entity.ContentType.APPLICATION_XML;

/**
 *
 * @author ilyas
 */
public class PayeezyGateway extends Gateway {

    public PayeezyGateway() {
        super(false);
    }

    @Override
    public HTTPResponse purchase(JSONObject apiParameters, Customer customer, CustomerCard customerCard, Currency currency, float amount) {
        JSONObject resp = null;
        String requestString = this.buildPurchaseParameters(apiParameters, customer, customerCard, currency, amount);

        PurchaseResponse successResponse = null;
        ErrorResponse errorResponse = new ErrorResponse();

        HTTPResponse httpResponse = HTTPClient.httpPost(this.getApiURL(), requestString, APPLICATION_XML);

        if (httpResponse.getStatusCode() == -1) {
            return httpResponse;
        }

        if (httpResponse.getContent().trim().startsWith("<")) {
            resp = XMLHelper.toJson(httpResponse.getContent());
            boolean transactionError = resp.getJSONObject(TRANSACTION_RESULT).getBoolean(TRANSACTION_ERROR);
            boolean transactionApproved = resp.getJSONObject(TRANSACTION_RESULT).getBoolean(TRANSACTION_APPROVED);

            if (transactionApproved) {
                httpResponse.setSuccessful(true);
                successResponse = new PurchaseResponse();

                successResponse.setMessage(resp.getJSONObject(TRANSACTION_RESULT).getString(BANK_MESSAGE));
                successResponse.setTransactionId(resp.getJSONObject(TRANSACTION_RESULT).get(TRANSACTION_TAG).toString());
                successResponse.setAmount(amount);
                successResponse.setCurrencyCode(currency);
                successResponse.setCardValuesFrom(customerCard);

                successResponse.setRefundParams(new JSONObject()
                        .put(TRANSACTION_ID.getName(), successResponse.getTransactionId())
                        .put(J2PAY_AUTHORIZATION_NUMBER, resp.getJSONObject(TRANSACTION_RESULT).get(AUTHORIZATION_NUMBER).toString())
                );

                successResponse.setVoidParams(new JSONObject()
                        .put(TRANSACTION_ID.getName(), successResponse.getTransactionId())
                        .put(J2PAY_AUTHORIZATION_NUMBER, resp.getJSONObject(TRANSACTION_RESULT).get(AUTHORIZATION_NUMBER).toString())
                        .put(AMOUNT.getName(), amount)
                );

            } else {
                errorResponse.setMessage(resp.getJSONObject(TRANSACTION_RESULT).get(transactionError ? EXACT_MESSAGE : BANK_MESSAGE).toString());
            }

        } else {
            errorResponse.setMessage(httpResponse.getContent());
        }

        //final response.
        processFinalResponse(resp, httpResponse, successResponse, errorResponse);
        return httpResponse;
    }

    @Override
    public HTTPResponse refund(JSONObject apiParameters, JSONObject refundParameters, float amount) {
        JSONObject resp = null;
        String requestString = this.buildRefundParameters(apiParameters, refundParameters, amount);

        RefundResponse successResponse = null;
        ErrorResponse errorResponse = new ErrorResponse();

        HTTPResponse httpResponse = HTTPClient.httpPost(this.getApiURL(), requestString, APPLICATION_XML);

        if (httpResponse.getStatusCode() == -1) {
            return httpResponse;
        }

        if (httpResponse.getContent().trim().startsWith("<")) {
            resp = XMLHelper.toJson(httpResponse.getContent());
            boolean transactionError = resp.getJSONObject(TRANSACTION_RESULT).getBoolean(TRANSACTION_ERROR);
            boolean transactionApproved = resp.getJSONObject(TRANSACTION_RESULT).getBoolean(TRANSACTION_APPROVED);

            if (transactionApproved) {
                httpResponse.setSuccessful(true);
                successResponse = new RefundResponse();

                successResponse.setMessage(resp.getJSONObject(TRANSACTION_RESULT).getString(BANK_MESSAGE));
                successResponse.setTransactionId(resp.getJSONObject(TRANSACTION_RESULT).get(TRANSACTION_TAG).toString());
                successResponse.setAmount(amount);

                successResponse.setVoidParams(new JSONObject()
                        .put(TRANSACTION_ID.getName(), successResponse.getTransactionId())
                        .put(J2PAY_AUTHORIZATION_NUMBER, resp.getJSONObject(TRANSACTION_RESULT).get(AUTHORIZATION_NUMBER).toString())
                        .put(AMOUNT.getName(), amount)
                );

            } else {
                errorResponse.setMessage(resp.getJSONObject(TRANSACTION_RESULT).get(transactionError ? EXACT_MESSAGE : BANK_MESSAGE).toString());
            }

        } else {
            errorResponse.setMessage(httpResponse.getContent());
        }

        //final response.
        processFinalResponse(resp, httpResponse, successResponse, errorResponse);
        return httpResponse;

    }

    @Override
    public HTTPResponse rebill(JSONObject apiParameters, JSONObject rebillParameters, float amount) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public HTTPResponse voidTransaction(JSONObject apiParameters, JSONObject voidParameters) {
        JSONObject resp = null;
        String requestString = this.buildVoidParameters(apiParameters, voidParameters);

        VoidResponse successResponse = null;
        ErrorResponse errorResponse = new ErrorResponse();

        HTTPResponse httpResponse = HTTPClient.httpPost(this.getApiURL(), requestString, APPLICATION_XML);

        if (httpResponse.getStatusCode() == -1) {
            return httpResponse;
        }

        if (httpResponse.getContent().trim().startsWith("<")) {
            resp = XMLHelper.toJson(httpResponse.getContent());
            boolean transactionError = resp.getJSONObject(TRANSACTION_RESULT).getBoolean(TRANSACTION_ERROR);
            boolean transactionApproved = resp.getJSONObject(TRANSACTION_RESULT).getBoolean(TRANSACTION_APPROVED);

            if (transactionApproved) {
                httpResponse.setSuccessful(true);
                successResponse = new VoidResponse();

                successResponse.setMessage(resp.getJSONObject(TRANSACTION_RESULT).getString(BANK_MESSAGE));
                successResponse.setTransactionId(resp.getJSONObject(TRANSACTION_RESULT).get(TRANSACTION_TAG).toString());

            } else {
               errorResponse.setMessage(resp.getJSONObject(TRANSACTION_RESULT).get(transactionError ? EXACT_MESSAGE : BANK_MESSAGE).toString());
            }

        } else {
            errorResponse.setMessage(httpResponse.getContent());
        }

        //final response.
        processFinalResponse(resp, httpResponse, successResponse, errorResponse);
        return httpResponse;
    }

    @Override
    public JSONObject getApiSampleParameters() {
        return new JSONObject()
                .put(EXACT_ID, "the gateway id looks like xxxxxx-xx")
                .put(PASSWORD, "the gateway api password");
    }

    @Override
    public JSONObject getRefundSampleParameters() {
        return new JSONObject()
                .put(TRANSACTION_ID.getName(), "the transaction id which will be refunded")
                .put(J2PAY_AUTHORIZATION_NUMBER, "the authorization number provided in purchase transaction");
    }

    @Override
    public JSONObject getRebillSampleParameters() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public JSONObject getVoidSampleParameters() {

        return new JSONObject()
                .put(TRANSACTION_ID.getName(), "the transaction id which will be refunded")
                .put(J2PAY_AUTHORIZATION_NUMBER, "the authorization number provided in purchase transaction")
                .put(AMOUNT.getName(), "the actual amount of original transaction");

    }

    //private methods are starting below.
    private String buildPurchaseParameters(JSONObject apiParameters, Customer customer, CustomerCard customerCard, Currency currency, float amount) {
	    final Map<String, Object> input = new HashMap<>();
	    input.put(EXACT_ID, apiParameters.getString(NAME));
	    input.put(PASSWORD, apiParameters.getString(TRANSACTION_KEY));
	    input.put(AMOUNT.getName(), Float.toString(amount));
	    input.put("CURRENCY", currency);
	    input.put("cardholder", customerCard.getName());
	    input.put("creditCardNumber", apiParameters.getString(NAME));
	    input.put("creditCardCvv", apiParameters.getString(NAME));
	    input.put("creditCardExpiryDate", apiParameters.getString(NAME));
	    input.put("ipAddress", customer.getIp());
	    input.put("emailAddress", customer.getEmail());
	    input.put("verification", customer.getAddress() + "|" + customer.getZip() + "|" + customer.getCity() + "|" + customer.getState() + "|" + customer.getCountry().getCodeISO2());

	    return createRequest(input, "Payeezy/PurchaseRRequest.ftl");

    }

    private String buildRefundParameters(JSONObject apiParameters, JSONObject refundParameters, float amount) {
        final Map<String, Object> input = new HashMap<>();
        input.put(EXACT_ID, apiParameters.getString(NAME));
        input.put(PASSWORD, apiParameters.getString(TRANSACTION_KEY));
        input.put(AMOUNT.getName(), Float.toString(amount));
	    input.put(TRANSACTION_ID.getName(), refundParameters.get(TRANSACTION_ID.getName()).toString());
        input.put("authorizationNumber", refundParameters.get("authorizationNumber").toString());

        return createRequest(input, "Payeezy/RefundRequest.ftl");
    }

    private String buildVoidParameters(JSONObject apiParameters, JSONObject voidParameters) {
	    final Map<String, Object> input = new HashMap<>();
	    input.put(EXACT_ID, apiParameters.getString(NAME));
	    input.put(PASSWORD, apiParameters.getString(TRANSACTION_KEY));
	    input.put(TRANSACTION_ID.getName(), voidParameters.get(TRANSACTION_ID.getName()).toString());
	    input.put("authorizationNumber", voidParameters.get("authorizationNumber").toString());

	    return createRequest(input, "Payeezy/VoidRequest.ftl");
    }

    private String getApiURL() {
    	return isTestMode() ? TEST_URL : LIVE_URL;
    }
}
