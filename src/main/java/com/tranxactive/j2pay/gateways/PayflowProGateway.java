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

        httpResponse = HTTPClient.httpPost(this.getApiURL(), requestString, ContentType.APPLICATION_FORM_URLENCODED);

        if (httpResponse.getStatusCode() == -1) {
            return httpResponse;
        }

        responseObject = JSONHelper.decode(QueryStringHelper.toJson(httpResponse.getContent()));

        if (responseObject.getInt("RESULT") == 0) {
            httpResponse.setSuccessful(true);
            successResponse = new PurchaseResponse();

            successResponse.setMessage(responseObject.getString("RESPMSG"));
            successResponse.setTransactionId(responseObject.get("PNREF").toString());
            successResponse.setCardValuesFrom(customerCard);
            successResponse.setAmount(amount);
            successResponse.setCurrencyCode(currency);

            successResponse.setRebillParams(new JSONObject()
                    .put(ParamList.TRANSACTION_ID.getName(), responseObject.get("PNREF").toString())
            );
            
            successResponse.setRefundParams(new JSONObject()
                    .put(ParamList.TRANSACTION_ID.getName(), responseObject.get("PNREF").toString())
            );
            successResponse.setVoidParams(new JSONObject()
                    .put(ParamList.TRANSACTION_ID.getName(), responseObject.get("PNREF").toString())
            );

        } else {
            errorResponse.setMessage(responseObject.getString("RESPMSG"));
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

        httpResponse = HTTPClient.httpPost(this.getApiURL(), requestString, ContentType.APPLICATION_FORM_URLENCODED);

        if (httpResponse.getStatusCode() == -1) {
            return httpResponse;
        }

        responseObject = JSONHelper.decode(QueryStringHelper.toJson(httpResponse.getContent()));

        if (responseObject.getInt("RESULT") == 0) {
            httpResponse.setSuccessful(true);
            successResponse = new RefundResponse();

            successResponse.setMessage(responseObject.getString("RESPMSG"));
            successResponse.setTransactionId(responseObject.get("PNREF").toString());
            successResponse.setAmount(amount);

            successResponse.setVoidParams(new JSONObject()
                    .put(ParamList.TRANSACTION_ID.getName(), responseObject.get("PNREF").toString())
            );

        } else {
            errorResponse.setMessage(responseObject.getString("RESPMSG"));
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

        httpResponse = HTTPClient.httpPost(this.getApiURL(), requestString, ContentType.APPLICATION_FORM_URLENCODED);

        if (httpResponse.getStatusCode() == -1) {
            return httpResponse;
        }

        responseObject = JSONHelper.decode(QueryStringHelper.toJson(httpResponse.getContent()));

        if (responseObject.getInt("RESULT") == 0) {
            httpResponse.setSuccessful(true);
            successResponse = new RebillResponse();

            successResponse.setMessage(responseObject.getString("RESPMSG"));
            successResponse.setTransactionId(responseObject.get("PNREF").toString());
            successResponse.setAmount(amount);

            successResponse.setRebillParams(new JSONObject()
                    .put(ParamList.TRANSACTION_ID.getName(), responseObject.get("PNREF").toString())
            );
            
            successResponse.setRefundParams(new JSONObject()
                    .put(ParamList.TRANSACTION_ID.getName(), responseObject.get("PNREF").toString())
            );
            successResponse.setVoidParams(new JSONObject()
                    .put(ParamList.TRANSACTION_ID.getName(), responseObject.get("PNREF").toString())
            );

        } else {
            errorResponse.setMessage(responseObject.getString("RESPMSG"));
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

        httpResponse = HTTPClient.httpPost(this.getApiURL(), requestString, ContentType.APPLICATION_FORM_URLENCODED);

        if (httpResponse.getStatusCode() == -1) {
            return httpResponse;
        }

        responseObject = JSONHelper.decode(QueryStringHelper.toJson(httpResponse.getContent()));

        if (responseObject.getInt("RESULT") == 0) {
            httpResponse.setSuccessful(true);
            successResponse = new VoidResponse();

            successResponse.setMessage(responseObject.getString("RESPMSG"));
            successResponse.setTransactionId(responseObject.get("PNREF").toString());

        } else {
            errorResponse.setMessage(responseObject.getString("RESPMSG"));
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
                .put("USER", "the user name")
                .put("VENDOR", "Your merchant login ID created when you registered for the account.")
                .put("PWD", "The password you defined while registering for the account.")
                .put("PARTNER", "PayPal or the ID provided to you by the authorized PayPal reseller who registered you for the Gateway.");
    }

    @Override
    public JSONObject getRefundSampleParameters() {
        return new JSONObject()
                .put(ParamList.TRANSACTION_ID.getName(), "the transaction id also known as ORIGID/PNREF");
    }

    @Override
    public JSONObject getRebillSampleParameters() {
        return new JSONObject()
                .put(ParamList.TRANSACTION_ID.getName(), "the transaction id also known as ORIGID/PNREF");
    }

    @Override
    public JSONObject getVoidSampleParameters() {
        return new JSONObject()
                .put(ParamList.TRANSACTION_ID.getName(), "the transaction id also known as ORIGID/PNREF");
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
                .put("PROFILENAME", this.getUniqueProfileName())
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
                .put("ORIGID", voidParameters.getString(ParamList.TRANSACTION_ID.getName()))
                .put("VERBOSITY", "HIGH");
    }

    private JSONObject buildRefundParameters(JSONObject apiParameters, JSONObject refundParameters, float amount) {
        return new JSONObject()
                .put("TRXTYPE", "C")
                .put("USER", apiParameters.getString("USER"))
                .put("VENDOR", apiParameters.getString("VENDOR"))
                .put("PWD", apiParameters.getString("PWD"))
                .put("PARTNER", apiParameters.getString("PARTNER"))
                .put("ORIGID", refundParameters.getString(ParamList.TRANSACTION_ID.getName()))
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
                .put("ORIGID", rebillParameters.getString(ParamList.TRANSACTION_ID.getName()))
                .put("AMT", amount)
                .put("VERBOSITY", "HIGH")
                .put("RECURRING", "Y");
    }
    
    private String getUniqueProfileName() {
        
        String str = "123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        StringBuilder uniqueString = new StringBuilder(String.valueOf(System.currentTimeMillis()));

        Random random = new Random();

        while (uniqueString.length() < 32) {
            uniqueString.append(str.charAt(random.nextInt(str.length() - 1)));
        }

        return uniqueString.toString();
    }

    private String getApiURL() {

        if (this.isTestMode()) {
            return "https://pilot-payflowpro.paypal.com";
        } else {
            return "https://payflowpro.paypal.com";
        }
    }

}
