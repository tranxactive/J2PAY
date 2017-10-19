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
import com.tranxactive.paymentprocessor.net.RequestResponseHelper;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.http.entity.ContentType;
import org.bson.Document;

/**
 *
 * @author ilyas <m.ilyas@live.com>
 */
public class NMIGateway extends Gateway {

    private final String apiURL = "https://secure.networkmerchants.com/api/transact.php";

    @Override
    public HTTPResponse purchase(Document apiParameters, Customer customer, CustomerCard customerCard, Currency currency, float amount) {

        Document requestDocument = this.buildPurchaseParameters(apiParameters, customer, customerCard, currency, amount);
        Document responseDocument;
        String requestString;
        String responseString;

        try {
            requestDocument = RequestResponseHelper.encode(requestDocument);
            requestString = RequestResponseHelper.toFormData(requestDocument);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(NMIGateway.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }

        try {
            HTTPResponse httpResponse = HTTPClient.httpPost(this.apiURL, requestString, ContentType.APPLICATION_FORM_URLENCODED);
            responseString = httpResponse.getContent();
            responseDocument = RequestResponseHelper.decode(RequestResponseHelper.toJson(responseString));
            httpResponse.setContent(responseDocument.toJson());

            if (!responseDocument.getString("response_code").equals("100")) {
                httpResponse.setSuccessful(false);
            }

            return httpResponse;
        } catch (IOException ex) {
            Logger.getLogger(AuthorizeGateway.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public HTTPResponse refund(Document apiParameters, Document refundParameters, float amount) {
        
        Document requestDocument = this.buildRefundParameters(apiParameters, refundParameters, amount);
        Document responseDocument;
        String requestString;
        String responseString;

        try {
            requestDocument = RequestResponseHelper.encode(requestDocument);
            requestString = RequestResponseHelper.toFormData(requestDocument);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(NMIGateway.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }

        try {
            HTTPResponse httpResponse = HTTPClient.httpPost(this.apiURL, requestString, ContentType.APPLICATION_FORM_URLENCODED);
            responseString = httpResponse.getContent();
            responseDocument = RequestResponseHelper.decode(RequestResponseHelper.toJson(responseString));
            httpResponse.setContent(responseDocument.toJson());

            if (!responseDocument.getString("response_code").equals("100")) {
                httpResponse.setSuccessful(false);
            }

            return httpResponse;
        } catch (IOException ex) {
            Logger.getLogger(AuthorizeGateway.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public HTTPResponse rebill(Document apiParameters, Document rebillParameters, float amount) {
        
        Document requestDocument = this.buildRebillParameters(apiParameters, rebillParameters, amount);
        Document responseDocument;
        String requestString;
        String responseString;

        try {
            requestDocument = RequestResponseHelper.encode(requestDocument);
            requestString = RequestResponseHelper.toFormData(requestDocument);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(NMIGateway.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }

        try {
            HTTPResponse httpResponse = HTTPClient.httpPost(this.apiURL, requestString, ContentType.APPLICATION_FORM_URLENCODED);
            responseString = httpResponse.getContent();
            responseDocument = RequestResponseHelper.decode(RequestResponseHelper.toJson(responseString));
            httpResponse.setContent(responseDocument.toJson());

            if (!responseDocument.getString("response_code").equals("100")) {
                httpResponse.setSuccessful(false);
            }

            return httpResponse;
        } catch (IOException ex) {
            Logger.getLogger(AuthorizeGateway.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public HTTPResponse voidTransaction(Document apiParameters, Document voidParameters) {

        Document requestDocument = this.buildVoidParameters(apiParameters, voidParameters);
        Document responseDocument;
        String requestString;
        String responseString;

        try {
            requestDocument = RequestResponseHelper.encode(requestDocument);
            requestString = RequestResponseHelper.toFormData(requestDocument);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(NMIGateway.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }

        try {
            HTTPResponse httpResponse = HTTPClient.httpPost(this.apiURL, requestString, ContentType.APPLICATION_FORM_URLENCODED);
            responseString = httpResponse.getContent();
            responseDocument = RequestResponseHelper.decode(RequestResponseHelper.toJson(responseString));
            httpResponse.setContent(responseDocument.toJson());

            if (!responseDocument.getString("response_code").equals("100")) {
                httpResponse.setSuccessful(false);
            }

            return httpResponse;
        } catch (IOException ex) {
            Logger.getLogger(AuthorizeGateway.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public Document getApiSampleParameters() {
        return new Document().append("username", "the api user name use demo for testing accout").append("password", "the api password use password for testing");
    }

    @Override
    public Document getRefundSampleParameters() {
        Document document = new Document();
        document
                .append("transactionid", "the transaction id which will be refunded");
        
        return document;
    }

    @Override
    public Document getRebillSampleParameters() {
        
        Document document = new Document();
        document
                .append("customer_vault_id", "the customer vault id");
        
        return document;
    }

    @Override
    public Document getVoidSampleParameters() {
        Document document = new Document();
        document
                .append("transactionid", "the transaction id which will be void");

        return document;
    }

    //private methods are starting below.
    private Document buildPurchaseParameters(Document apiParameters, Customer customer, CustomerCard customerCard, Currency currency, float amount) {

        Document document = new Document();
        document
                .append("type", "sale")
                .append("username", apiParameters.getString("username"))
                .append("password", apiParameters.getString("password"))
                .append("ccnumber", customerCard.getNumber())
                .append("ccexp", customerCard.getExpiryMonth() + customerCard.getExpiryYear().substring(2))
                .append("cvv", customerCard.getCvv())
                .append("amount", amount)
                .append("first_name", customer.getFirstName())
                .append("last_name", customer.getLastName())
                .append("address1", customer.getAddress())
                .append("city", customer.getCity())
                .append("state", customer.getState())
                .append("zip", customer.getZip())
                .append("country", customer.getCountry().getCodeAlpha2())
                .append("phone", customer.getPhoneNumber())
                .append("email", customer.getEmail())
                .append("customer_vault", "add_customer");

        return document;

    }

    private Document buildVoidParameters(Document apiParameters, Document voidParameters) {

        Document document = new Document();
        document
                .append("type", "void")
                .append("username", apiParameters.getString("username"))
                .append("password", apiParameters.getString("password"))
                .append("transactionid", voidParameters.getString("transactionid"));

        return document;
    }
    
    private Document buildRefundParameters(Document apiParameters, Document voidParameters, float amount) {

        Document document = new Document();
        document
                .append("type", "refund")
                .append("username", apiParameters.getString("username"))
                .append("password", apiParameters.getString("password"))
                .append("transactionid", voidParameters.getString("transactionid"))
                .append("amount", Float.toString(amount));

        return document;
    }
    
    private Document buildRebillParameters(Document apiParameters, Document rebillParameters, float amount) {

        Document document = new Document();
        document
                .append("username", apiParameters.getString("username"))
                .append("password", apiParameters.getString("password"))
                .append("customer_vault_id", rebillParameters.getString("customer_vault_id"))
                .append("amount", Float.toString(amount));

        return document;
    }

}
