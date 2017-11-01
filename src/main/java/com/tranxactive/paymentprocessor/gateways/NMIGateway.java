/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tranxactive.paymentprocessor.gateways;

import com.tranxactive.paymentprocessor.gateways.parameters.Currency;
import com.tranxactive.paymentprocessor.gateways.parameters.Customer;
import com.tranxactive.paymentprocessor.gateways.parameters.CustomerCard;
import com.tranxactive.paymentprocessor.net.HTTPClient;
import com.tranxactive.paymentprocessor.net.HTTPResponse;
import com.tranxactive.paymentprocessor.net.JSONHelper;
import com.tranxactive.paymentprocessor.net.QueryStringHelper;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.http.entity.ContentType;
import org.json.JSONObject;

/**
 *
 * @author ilyas <m.ilyas@live.com>
 */
public class NMIGateway extends Gateway {

    private final String apiURL = "https://secure.networkmerchants.com/api/transact.php";

    @Override
    public HTTPResponse purchase(JSONObject apiParameters, Customer customer, CustomerCard customerCard, Currency currency, float amount) {

        JSONObject requestObject = this.buildPurchaseParameters(apiParameters, customer, customerCard, currency, amount);
        JSONObject responseObject;
        String requestString;
        String responseString;
        int responseCode;
        requestObject = JSONHelper.encode(requestObject);
        requestString = QueryStringHelper.toQueryString(requestObject);
        HTTPResponse httpResponse;
        JSONObject lr = new JSONObject();

        try {
            httpResponse = HTTPClient.httpPost(this.apiURL, requestString, ContentType.APPLICATION_FORM_URLENCODED);
        } catch (IOException ex) {
            Logger.getLogger(AuthorizeGateway.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }

        responseString = httpResponse.getContent();
        responseObject = JSONHelper.decode(QueryStringHelper.toJson(responseString));
        responseCode = responseObject.getInt("response_code");

        if (responseCode == 100) {
            lr.put("success", true);
            lr.put("transactionId", responseObject.get("transactionid").toString());
            lr.put("message", responseObject.getString("responsetext"));
            lr.put("cardFirst6", customerCard.getFirst6());
            lr.put("cardLast4", customerCard.getLast4());
            lr.put("maskedCard", customerCard.getMaskedCard());
            lr.put("cardExpiryMonth", customerCard.getExpiryMonth());
            lr.put("cardExpiryYear", customerCard.getExpiryYear());
            lr.put("customerVaultId", responseObject.get("customer_vault_id").toString());
        } else {
            httpResponse.setSuccessful(false);
            lr.put("success", false);
            lr.put("message", responseObject.getString("responsetext"));
        }

        httpResponse.setContent(new JSONObject().put("lr", lr).put("gr", responseObject).toString());
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
        JSONObject lr = new JSONObject();
        int responseCode;

        try {
            httpResponse = HTTPClient.httpPost(this.apiURL, requestString, ContentType.APPLICATION_FORM_URLENCODED);
        } catch (IOException ex) {
            Logger.getLogger(AuthorizeGateway.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }

        responseString = httpResponse.getContent();
        responseObject = JSONHelper.decode(QueryStringHelper.toJson(responseString));
        responseCode = responseObject.getInt("response_code");

        if (responseCode == 100) {
            lr.put("success", true);
            lr.put("transactionId", responseObject.get("transactionid").toString());
            lr.put("message", responseObject.getString("responsetext"));
        } else {
            httpResponse.setSuccessful(false);
            lr.put("success", false);
            lr.put("message", responseObject.getString("responsetext"));
        }

        httpResponse.setContent(new JSONObject().put("lr", lr).put("gr", responseObject).toString());
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
        JSONObject lr = new JSONObject();
        int responseCode;

        try {
            httpResponse = HTTPClient.httpPost(this.apiURL, requestString, ContentType.APPLICATION_FORM_URLENCODED);
        } catch (IOException ex) {
            Logger.getLogger(AuthorizeGateway.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }

        responseString = httpResponse.getContent();
        responseObject = JSONHelper.decode(QueryStringHelper.toJson(responseString));
        responseCode = responseObject.getInt("response_code");

        if (responseCode == 100) {
            lr.put("success", true);
            lr.put("transactionId", responseObject.get("transactionid").toString());
            lr.put("message", responseObject.getString("responsetext"));
        } else {
            httpResponse.setSuccessful(false);
            lr.put("success", false);
            lr.put("message", responseObject.getString("responsetext"));
        }

        httpResponse.setContent(new JSONObject().put("lr", lr).put("gr", responseObject).toString());
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
        JSONObject lr = new JSONObject();
        int responseCode;

        try {
            httpResponse = HTTPClient.httpPost(this.apiURL, requestString, ContentType.APPLICATION_FORM_URLENCODED);
        } catch (IOException ex) {
            Logger.getLogger(AuthorizeGateway.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }

        responseString = httpResponse.getContent();
        responseObject = JSONHelper.decode(QueryStringHelper.toJson(responseString));
        responseCode = responseObject.getInt("response_code");

        if (responseCode == 100) {
            lr.put("success", true);
            lr.put("transactionId", responseObject.get("transactionid").toString());
            lr.put("message", responseObject.getString("responsetext"));
        } else {
            httpResponse.setSuccessful(false);
            lr.put("success", false);
            lr.put("message", responseObject.getString("responsetext"));
        }

        httpResponse.setContent(new JSONObject().put("lr", lr).put("gr", responseObject).toString());
        return httpResponse;
    }

    @Override
    public JSONObject getApiSampleParameters() {
        return new JSONObject()
                .put("username", "the api user name use demo for testing")
                .put("password", "the api password use password for testing");
    }

    @Override
    public JSONObject getRefundSampleParameters() {
        return new JSONObject()
                .put("transactionId", "the transaction id which will be refunded");
    }

    @Override
    public JSONObject getRebillSampleParameters() {
        return new JSONObject()
                .put("customerVaultId", "the customer vault id");
    }

    @Override
    public JSONObject getVoidSampleParameters() {
        return new JSONObject()
                .put("transactionId", "the transaction id which will be void");
    }

    //private methods are starting below.
    private JSONObject buildPurchaseParameters(JSONObject apiParameters, Customer customer, CustomerCard customerCard, Currency currency, float amount) {

        JSONObject object = new JSONObject();
        object
                .put("type", "sale")
                .put("username", apiParameters.getString("username"))
                .put("password", apiParameters.getString("password"))
                .put("ccnumber", customerCard.getNumber())
                .put("ccexp", customerCard.getExpiryMonth() + customerCard.getExpiryYear().substring(2))
                .put("cvv", customerCard.getCvv())
                .put("amount", amount)
                .put("currency", currency)
                .put("first_name", customer.getFirstName())
                .put("last_name", customer.getLastName())
                .put("address1", customer.getAddress())
                .put("city", customer.getCity())
                .put("state", customer.getState())
                .put("zip", customer.getZip())
                .put("country", customer.getCountry().getCodeAlpha2())
                .put("phone", customer.getPhoneNumber())
                .put("email", customer.getEmail())
                .put("ipaddress", customer.getIp())
                .put("customer_vault", "add_customer");

        return object;

    }

    private JSONObject buildVoidParameters(JSONObject apiParameters, JSONObject voidParameters) {

        JSONObject object = new JSONObject();
        object
                .put("type", "void")
                .put("username", apiParameters.getString("username"))
                .put("password", apiParameters.getString("password"))
                .put("transactionid", voidParameters.getString("transactionId"));

        return object;
    }

    private JSONObject buildRefundParameters(JSONObject apiParameters, JSONObject voidParameters, float amount) {

        JSONObject object = new JSONObject();
        object
                .put("type", "refund")
                .put("username", apiParameters.getString("username"))
                .put("password", apiParameters.getString("password"))
                .put("transactionid", voidParameters.getString("transactionId"))
                .put("amount", Float.toString(amount));

        return object;
    }

    private JSONObject buildRebillParameters(JSONObject apiParameters, JSONObject rebillParameters, float amount) {

        JSONObject object = new JSONObject();
        object
                .put("username", apiParameters.getString("username"))
                .put("password", apiParameters.getString("password"))
                .put("customer_vault_id", rebillParameters.getString("customerVaultId"))
                .put("amount", Float.toString(amount));

        return object;
    }

}
