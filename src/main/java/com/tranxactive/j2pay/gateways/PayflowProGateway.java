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
import com.tranxactive.j2pay.gateways.responses.*;
import com.tranxactive.j2pay.net.HTTPClient;
import com.tranxactive.j2pay.net.HTTPResponse;
import com.tranxactive.j2pay.net.JSONHelper;
import com.tranxactive.j2pay.net.QueryStringHelper;
import org.json.JSONObject;

import static com.tranxactive.j2pay.gateways.parameters.ParamList.TRANSACTION_ID;
import static com.tranxactive.j2pay.gateways.util.ResponseProcessor.processFinalResponse;
import static com.tranxactive.j2pay.gateways.util.UniqueCustomerIdGenerator.getUniqueCustomerId;
import static org.apache.http.entity.ContentType.APPLICATION_FORM_URLENCODED;

/**
 *
 * @author ilyas
 */
public class PayflowProGateway extends Gateway {

    @Override
    public HTTPResponse purchase(JSONObject apiParameters, Customer customer, CustomerCard customerCard, Currency currency, float amount) {

        String requestString = QueryStringHelper.toQueryString(JSONHelper.encode(this.buildPurchaseParameters(apiParameters, customer, customerCard, currency, amount)));
        JSONObject responseObject;
        HTTPResponse httpResponse;

        PurchaseResponse successResponse = null;
        ErrorResponse errorResponse = new ErrorResponse();

        httpResponse = HTTPClient.httpPost(this.getApiURL(), requestString, APPLICATION_FORM_URLENCODED);

        if (httpResponse.getStatusCode() == -1) {
            return httpResponse;
        }

        responseObject = JSONHelper.decode(QueryStringHelper.toJson(httpResponse.getContent()));

        if (responseObject.getInt("RESULT") == 0) {
            successResponse = new PurchaseResponse();

            successResponse.setMessage(responseObject.getString("RESPMSG"));
            successResponse.setTransactionId(responseObject.get("PNREF").toString());
            successResponse.setCardValuesFrom(customerCard);
            successResponse.setAmount(amount);
            successResponse.setCurrencyCode(currency);

            successResponse.setRebillParams(new JSONObject().put(TRANSACTION_ID.getName(), responseObject.get("PNREF").toString())
            );
            successResponse.setRefundParams(new JSONObject().put(TRANSACTION_ID.getName(), responseObject.get("PNREF").toString())
            );
            successResponse.setVoidParams(new JSONObject().put(TRANSACTION_ID.getName(), responseObject.get("PNREF").toString())
            );

        } else {
            errorResponse.setMessage(responseObject.getString("RESPMSG"));
            errorResponse.setTransactionId(responseObject.optString("PNREF"));
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

        httpResponse = HTTPClient.httpPost(this.getApiURL(), requestString, APPLICATION_FORM_URLENCODED);

        if (httpResponse.getStatusCode() == -1) {
            return httpResponse;
        }

        responseObject = JSONHelper.decode(QueryStringHelper.toJson(httpResponse.getContent()));

        if (responseObject.getInt("RESULT") == 0) {
            successResponse = new RefundResponse();

            successResponse.setMessage(responseObject.getString("RESPMSG"));
            successResponse.setTransactionId(responseObject.get("PNREF").toString());
            successResponse.setAmount(amount);

            successResponse.setVoidParams(new JSONObject()
                    .put(TRANSACTION_ID.getName(), responseObject.get("PNREF").toString())
            );

        } else {
            errorResponse.setMessage(responseObject.getString("RESPMSG"));
            errorResponse.setTransactionId(responseObject.optString("PNREF"));
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

        httpResponse = HTTPClient.httpPost(this.getApiURL(), requestString, APPLICATION_FORM_URLENCODED);

        if (httpResponse.getStatusCode() == -1) {
            return httpResponse;
        }

        responseObject = JSONHelper.decode(QueryStringHelper.toJson(httpResponse.getContent()));

        if (responseObject.getInt("RESULT") == 0) {
            successResponse = new RebillResponse();

            successResponse.setMessage(responseObject.getString("RESPMSG"));
            successResponse.setTransactionId(responseObject.get("PNREF").toString());
            successResponse.setAmount(amount);

            successResponse.setRebillParams(new JSONObject()
                    .put(TRANSACTION_ID.getName(), responseObject.get("PNREF").toString())
            );
            
            successResponse.setRefundParams(new JSONObject()
                    .put(TRANSACTION_ID.getName(), responseObject.get("PNREF").toString())
            );
            successResponse.setVoidParams(new JSONObject()
                    .put(TRANSACTION_ID.getName(), responseObject.get("PNREF").toString())
            );

        } else {
            errorResponse.setMessage(responseObject.getString("RESPMSG"));
            errorResponse.setTransactionId(responseObject.optString("PNREF"));
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

        httpResponse = HTTPClient.httpPost(this.getApiURL(), requestString, APPLICATION_FORM_URLENCODED);

        if (httpResponse.getStatusCode() == -1) {
            return httpResponse;
        }

        responseObject = JSONHelper.decode(QueryStringHelper.toJson(httpResponse.getContent()));

        if (responseObject.getInt("RESULT") == 0) {
            successResponse = new VoidResponse();

            successResponse.setMessage(responseObject.getString("RESPMSG"));
            successResponse.setTransactionId(responseObject.get("PNREF").toString());

        } else {
            errorResponse.setMessage(responseObject.getString("RESPMSG"));
            errorResponse.setTransactionId(responseObject.optString("PNREF"));
        }

        //final response.
        processFinalResponse(responseObject, httpResponse, successResponse, errorResponse);
        return httpResponse;
    }

    @Override
    public JSONObject getApiSampleParameters() {
        return new JSONObject()
                .put("USER", "the user name")
                .put("VENDOR", "Your merchant login ID created when you registered for the account.")
                .put("PWD", "The password you defined while registering for the account.")
                .put("PARTNER", "PayPal or the ID provided to you by the authorized PayPal reseller who registered you for the Gateway.");
    }

    @Override
    public JSONObject getRefundSampleParameters() {
        return new JSONObject()
                .put(TRANSACTION_ID.getName(), "the transaction id also known as ORIGID/PNREF");
    }

    @Override
    public JSONObject getRebillSampleParameters() {
        return new JSONObject()
                .put(TRANSACTION_ID.getName(), "the transaction id also known as ORIGID/PNREF");
    }

    @Override
    public JSONObject getVoidSampleParameters() {
        return new JSONObject()
                .put(TRANSACTION_ID.getName(), "the transaction id also known as ORIGID/PNREF");
    }

    private JSONObject buildPurchaseParameters(JSONObject apiParameters, Customer customer, CustomerCard customerCard, Currency currency, float amount) {

        return new JSONObject()
                .put("TRXTYPE", "S")
                .put("TENDER", "C")
                .put("USER", apiParameters.getString("USER"))
                .put("VENDOR", apiParameters.getString("VENDOR"))
                .put("PWD", apiParameters.getString("PWD"))
                .put("PARTNER", apiParameters.getString("PARTNER"))
                .put("ACCT", customerCard.getNumber())
                .put("CVV2", customerCard.getCvv())
                .put("EXPDATE", customerCard.getExpiryMonth() + customerCard.getExpiryYear().substring(2))
                .put("AMT", amount)
                .put("BILLTOFIRSTNAME", customer.getFirstName())
                .put("BILLTOLASTNAME", customer.getLastName())
                .put("BILLTOSTREET", customer.getAddress())
                .put("BILLTOCITY", customer.getCity())
                .put("BILLTOSTATE", customer.getState())
                .put("BILLTOZIP", customer.getZip())
                .put("BILLTOCOUNTRY", customer.getCountry().getNumericCode())
                .put("CUSTIP", customer.getIp())
                .put("VERBOSITY", "HIGH")
                .put("RECURRING", "Y")
                .put("PROFILENAME", getUniqueCustomerId())
                .put("ACTION", "A")
                ;
    }

    private JSONObject buildVoidParameters(JSONObject apiParameters, JSONObject voidParameters) {
        return new JSONObject()
                .put("TRXTYPE", "V")
                .put("USER", apiParameters.getString("USER"))
                .put("VENDOR", apiParameters.getString("VENDOR"))
                .put("PWD", apiParameters.getString("PWD"))
                .put("PARTNER", apiParameters.getString("PARTNER"))
                .put("ORIGID", voidParameters.getString(TRANSACTION_ID.getName()))
                .put("VERBOSITY", "HIGH");
    }

    private JSONObject buildRefundParameters(JSONObject apiParameters, JSONObject refundParameters, float amount) {
        return new JSONObject()
                .put("TRXTYPE", "C")
                .put("USER", apiParameters.getString("USER"))
                .put("VENDOR", apiParameters.getString("VENDOR"))
                .put("PWD", apiParameters.getString("PWD"))
                .put("PARTNER", apiParameters.getString("PARTNER"))
                .put("ORIGID", refundParameters.getString(TRANSACTION_ID.getName()))
                .put("AMT", amount)
                .put("VERBOSITY", "HIGH");
    }

    private JSONObject buildRebillParameters(JSONObject apiParameters, JSONObject rebillParameters, float amount) {
        return new JSONObject()
                .put("TRXTYPE", "S")
                .put("TENDER", "C")
                .put("USER", apiParameters.getString("USER"))
                .put("VENDOR", apiParameters.getString("VENDOR"))
                .put("PWD", apiParameters.getString("PWD"))
                .put("PARTNER", apiParameters.getString("PARTNER"))
                .put("ORIGID", rebillParameters.getString(TRANSACTION_ID.getName()))
                .put("AMT", amount)
                .put("VERBOSITY", "HIGH")
                .put("RECURRING", "Y");
    }

    private String getApiURL() {
            return "https://" + (this.isTestMode() ? "pilot-" : "") + "payflowpro.paypal.com";
    }

}
