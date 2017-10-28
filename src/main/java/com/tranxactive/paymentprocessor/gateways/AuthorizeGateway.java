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
import com.tranxactive.paymentprocessor.net.XMLHelper;
import java.io.IOException;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.http.entity.ContentType;
import org.json.JSONObject;

/**
 *
 * @author ilyas <m.ilyas@live.com>
 */
public class AuthorizeGateway extends Gateway {

    @Override
    public HTTPResponse purchase(JSONObject apiParameters, Customer customer, CustomerCard customerCard, Currency currency, float amount) {
        JSONObject response;
        JSONObject lr = new JSONObject();
        JSONObject finalResponse = new JSONObject();
        String requestString = this.buildPurchaseParameters(apiParameters, customer, customerCard, currency, amount);

        String result;
        try {
            HTTPResponse httpResponse = HTTPClient.httpPost(this.getApiURL(), requestString, ContentType.APPLICATION_XML);
            response = XMLHelper.toJson(httpResponse.getContent());

            if (response.has("createTransactionResponse")) {

                result = response.getJSONObject("createTransactionResponse").getJSONObject("messages").getString("resultCode");

                if (result.equalsIgnoreCase("ok")) {
                    lr.put("success", true);
                    lr.put("transactionId", response.getJSONObject("createTransactionResponse").getJSONObject("transactionResponse").get("transId").toString());
                    lr.put("infoText", response.getJSONObject("createTransactionResponse").getJSONObject("transactionResponse").getJSONObject("messages").getJSONObject("message").getString("description"));
                    lr.put("cardFirst6", customerCard.getFirst6());
                    lr.put("cardLast4", customerCard.getLast4());
                    lr.put("maskedCard", customerCard.getMaskedCard());

                    if (response.getJSONObject("createTransactionResponse").getJSONObject("profileResponse").has("customerProfileId")) {
                        lr.put("customerProfileId", response.getJSONObject("createTransactionResponse").getJSONObject("profileResponse").get("customerProfileId").toString());
                        lr.put("paymentProfileId", response.getJSONObject("createTransactionResponse").getJSONObject("profileResponse").getJSONObject("customerPaymentProfileIdList").get("numericString").toString());
                    }

                } else {
                    httpResponse.setSuccessful(false);
                    lr.put("success", false);
                    lr.put("errorText", response.getJSONObject("createTransactionResponse").getJSONObject("transactionResponse").getJSONObject("errors").getJSONObject("error").getString("errorText"));
                }
            } else {
                httpResponse.setSuccessful(false);
                lr.put("success", false);
                lr.put("errorText", response.getJSONObject("ErrorResponse").getJSONObject("messages").getJSONObject("message").getString("text"));
            }

            finalResponse.put("lr", lr);
            finalResponse.put("gr", response);
            httpResponse.setContent(finalResponse.toString());

            return httpResponse;

        } catch (IOException ex) {
            Logger.getLogger(AuthorizeGateway.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public HTTPResponse refund(JSONObject apiParameters, JSONObject refundParameters, float amount) {

        JSONObject response;
        JSONObject lr = new JSONObject();
        String result;
        JSONObject finalResponse = new JSONObject();
        String requestString = this.buildRefundParameters(apiParameters, refundParameters, amount);
        try {
            HTTPResponse httpResponse = HTTPClient.httpPost(this.getApiURL(), requestString, ContentType.APPLICATION_XML);
            response = XMLHelper.toJson(httpResponse.getContent());

            if (response.has("createTransactionResponse")) {

                result = response.getJSONObject("createTransactionResponse").getJSONObject("messages").getString("resultCode");

                if (result.equalsIgnoreCase("ok")) {
                    lr.put("success", true);
                    lr.put("transactionId", response.getJSONObject("createTransactionResponse").getJSONObject("transactionResponse").get("transId").toString());
                    lr.put("infoText", response.getJSONObject("createTransactionResponse").getJSONObject("transactionResponse").getJSONObject("messages").getJSONObject("message").getString("description"));

                } else {
                    httpResponse.setSuccessful(false);
                    lr.put("success", false);
                    lr.put("errorText", response.getJSONObject("createTransactionResponse").getJSONObject("transactionResponse").getJSONObject("errors").getJSONObject("error").getString("errorText"));
                }
            } else {
                httpResponse.setSuccessful(false);
                lr.put("success", false);
                lr.put("errorText", response.getJSONObject("ErrorResponse").getJSONObject("messages").getJSONObject("message").getString("text"));
            }

            finalResponse.put("lr", lr);
            finalResponse.put("gr", response);
            httpResponse.setContent(finalResponse.toString());

            return httpResponse;

        } catch (IOException ex) {
            Logger.getLogger(AuthorizeGateway.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public HTTPResponse rebill(JSONObject apiParameters, JSONObject rebillParameters, float amount) {

        JSONObject response;
        JSONObject lr = new JSONObject();
        String result;
        JSONObject finalResponse = new JSONObject();
        String requestString = this.buildRebillParameters(apiParameters, rebillParameters, amount);

        try {
            HTTPResponse httpResponse = HTTPClient.httpPost(this.getApiURL(), requestString, ContentType.APPLICATION_XML);
            response = XMLHelper.toJson(httpResponse.getContent());

            if (response.has("createTransactionResponse")) {

                result = response.getJSONObject("createTransactionResponse").getJSONObject("messages").getString("resultCode");

                if (result.equalsIgnoreCase("ok")) {
                    lr.put("success", true);
                    lr.put("transactionId", response.getJSONObject("createTransactionResponse").getJSONObject("transactionResponse").get("transId").toString());
                    lr.put("infoText", response.getJSONObject("createTransactionResponse").getJSONObject("transactionResponse").getJSONObject("messages").getJSONObject("message").getString("description"));

                } else {
                    httpResponse.setSuccessful(false);
                    lr.put("success", false);
                    lr.put("errorText", response.getJSONObject("createTransactionResponse").getJSONObject("messages").getJSONObject("message").getString("text"));
                }
            } else {
                httpResponse.setSuccessful(false);
                lr.put("success", false);
                lr.put("errorText", response.getJSONObject("ErrorResponse").getJSONObject("messages").getJSONObject("message").getString("text"));
            }

            finalResponse.put("lr", lr);
            finalResponse.put("gr", response);
            httpResponse.setContent(finalResponse.toString());

            return httpResponse;
        } catch (IOException ex) {
            Logger.getLogger(AuthorizeGateway.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public HTTPResponse voidTransaction(JSONObject apiParameters, JSONObject voidParameters) {

        JSONObject response;
        JSONObject lr = new JSONObject();
        String result;
        JSONObject finalResponse = new JSONObject();
        String requestString = this.buildVoidParameters(apiParameters, voidParameters);

        try {
            HTTPResponse httpResponse = HTTPClient.httpPost(this.getApiURL(), requestString, ContentType.APPLICATION_XML);
            response = XMLHelper.toJson(httpResponse.getContent());

            if (response.has("createTransactionResponse")) {

                result = response.getJSONObject("createTransactionResponse").getJSONObject("messages").getString("resultCode");

                if (result.equalsIgnoreCase("ok")) {
                    lr.put("success", true);
                    lr.put("transactionId", response.getJSONObject("createTransactionResponse").getJSONObject("transactionResponse").get("transId").toString());
                    lr.put("infoText", response.getJSONObject("createTransactionResponse").getJSONObject("transactionResponse").getJSONObject("messages").getJSONObject("message").getString("description"));

                } else {
                    httpResponse.setSuccessful(false);
                    lr.put("success", false);
                    lr.put("errorText", response.getJSONObject("createTransactionResponse").getJSONObject("transactionResponse").getJSONObject("errors").getJSONObject("error").getString("errorText"));
                }
            } else {
                httpResponse.setSuccessful(false);
                lr.put("success", false);
                lr.put("errorText", response.getJSONObject("ErrorResponse").getJSONObject("messages").getJSONObject("message").getString("text"));
            }

            finalResponse.put("lr", lr);
            finalResponse.put("gr", response);
            httpResponse.setContent(finalResponse.toString());

            return httpResponse;
        } catch (IOException ex) {
            Logger.getLogger(AuthorizeGateway.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;

    }

    @Override
    public JSONObject getApiSampleParameters() {
        return new JSONObject()
                .put("name", "also called api user name / api login id")
                .put("transactionKey", "the transaction key");
    }

    @Override
    public JSONObject getRefundSampleParameters() {
        return new JSONObject()
                .put("transactionId", "the transaction id which will be refunded")
                .put("cardLast4", "last 4 digits of card")
                .put("cardExpiryMonth", "must be 2 digits expiry month of card i.e for jan 01")
                .put("cardExpiryYear", "must be 4 digits expiry year of card i.e 2017");
    }

    @Override
    public JSONObject getRebillSampleParameters() {
        return new JSONObject()
                .put("customerProfileId", "the customer profile id")
                .put("paymentProfileId", "the customer payment profile id");
    }

    @Override
    public JSONObject getVoidSampleParameters() {
        return new JSONObject()
                .put("transactionId", "the transaction id which will be void");
    }

    //private methods are starting below.
    private String buildPurchaseParameters(JSONObject apiParameters, Customer customer, CustomerCard customerCard, Currency currency, float amount) {

        StringBuilder finalParams = new StringBuilder();

        finalParams
                .append("<createTransactionRequest xmlns='AnetApi/xml/v1/schema/AnetApiSchema.xsd'>")
                .append("<merchantAuthentication>")
                .append("<name>").append(apiParameters.getString("name")).append("</name>")
                .append("<transactionKey>").append(apiParameters.getString("transactionKey")).append("</transactionKey>")
                .append("</merchantAuthentication>")
                .append("<transactionRequest>")
                .append("<transactionType>").append("authCaptureTransaction").append("</transactionType>")
                .append("<amount>").append(Float.toString(amount)).append("</amount>")
                .append("<payment>")
                .append("<creditCard>")
                .append("<cardNumber>").append(customerCard.getNumber()).append("</cardNumber>")
                .append("<expirationDate>").append(customerCard.getExpiryMonth()).append(customerCard.getExpiryYear().substring(2)).append("</expirationDate>")
                .append("<cardCode>").append(customerCard.getCvv()).append("</cardCode>")
                .append("</creditCard>")
                .append("</payment>")
                .append("<profile>")
                .append("<createProfile>").append(true).append("</createProfile>")
                .append("</profile>")
                .append("<customer>")
                .append("<id>").append(this.getUniqueCustomerId()).append("</id>")
                .append("</customer>")
                .append("<billTo>")
                .append("<firstName>").append(customer.getFirstName()).append("</firstName>")
                .append("<lastName>").append(customer.getLastName()).append("</lastName>")
                .append("<address>").append(customer.getAddress()).append("</address>")
                .append("<city>").append(customer.getCity()).append("</city>")
                .append("<state>").append(customer.getState()).append("</state>")
                .append("<zip>").append(customer.getZip()).append("</zip>")
                .append("<country>").append(customer.getCountry().getCodeAlpha3()).append("</country>")
                .append("</billTo>")
                .append("<customerIP>").append(customer.getIp()).append("</customerIP>")
                .append("</transactionRequest>")
                .append("</createTransactionRequest>");

        return finalParams.toString();

    }

    private String buildRefundParameters(JSONObject apiParameters, JSONObject refundParameters, float amount) {

        StringBuilder finalParams = new StringBuilder();

        finalParams
                .append("<createTransactionRequest xmlns='AnetApi/xml/v1/schema/AnetApiSchema.xsd'>")
                .append("<merchantAuthentication>")
                .append("<name>").append(apiParameters.getString("name")).append("</name>")
                .append("<transactionKey>").append(apiParameters.getString("transactionKey")).append("</transactionKey>")
                .append("</merchantAuthentication>")
                .append("<transactionRequest>")
                .append("<transactionType>").append("refundTransaction").append("</transactionType>")
                .append("<amount>").append(Float.toString(amount)).append("</amount>")
                .append("<payment>")
                .append("<creditCard>")
                .append("<cardNumber>").append(refundParameters.getString("cardLast4")).append("</cardNumber>")
                .append("<expirationDate>").append(refundParameters.getString("cardExpiryMonth")).append(refundParameters.getString("cardExpiryYear").substring(2)).append("</expirationDate>")
                .append("</creditCard>")
                .append("</payment>")
                .append("<refTransId>").append(refundParameters.getString("transactionId")).append("</refTransId>")
                .append("</transactionRequest>")
                .append("</createTransactionRequest>");

        return finalParams.toString();

    }

    private String buildVoidParameters(JSONObject apiParameters, JSONObject voidParameters) {

        StringBuilder finalParams = new StringBuilder();

        finalParams
                .append("<createTransactionRequest xmlns='AnetApi/xml/v1/schema/AnetApiSchema.xsd'>")
                .append("<merchantAuthentication>")
                .append("<name>").append(apiParameters.getString("name")).append("</name>")
                .append("<transactionKey>").append(apiParameters.getString("transactionKey")).append("</transactionKey>")
                .append("</merchantAuthentication>")
                .append("<transactionRequest>")
                .append("<transactionType>").append("voidTransaction").append("</transactionType>")
                .append("<refTransId>").append(voidParameters.getString("transactionId")).append("</refTransId>")
                .append("</transactionRequest>")
                .append("</createTransactionRequest>");

        return finalParams.toString();
    }

    private String buildRebillParameters(JSONObject apiParameters, JSONObject rebillParameters, float amount) {

        StringBuilder finalParams = new StringBuilder();

        finalParams
                .append("<createTransactionRequest xmlns='AnetApi/xml/v1/schema/AnetApiSchema.xsd'>")
                .append("<merchantAuthentication>")
                .append("<name>").append(apiParameters.getString("name")).append("</name>")
                .append("<transactionKey>").append(apiParameters.getString("transactionKey")).append("</transactionKey>")
                .append("</merchantAuthentication>")
                .append("<transactionRequest>")
                .append("<transactionType>").append("authCaptureTransaction").append("</transactionType>")
                .append("<amount>").append(Float.toString(amount)).append("</amount>")
                .append("<profile>")
                .append("<customerProfileId>").append(rebillParameters.getString("customerProfileId")).append("</customerProfileId>")
                .append("<paymentProfile>")
                .append("<paymentProfileId>").append(rebillParameters.getString("paymentProfileId")).append("</paymentProfileId>")
                .append("</paymentProfile>")
                .append("</profile>")
                .append("</transactionRequest>")
                .append("</createTransactionRequest>");

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

    private String getApiURL() {

        if (isTestMode()) {
            return "https://apitest.authorize.net/xml/v1/request.api";
        } else {
            return "https://api.authorize.net/xml/v1/request.api";
        }
    }

}
