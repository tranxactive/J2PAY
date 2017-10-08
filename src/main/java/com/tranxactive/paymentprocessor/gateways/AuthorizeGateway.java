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
import java.io.IOException;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.http.entity.ContentType;
import org.bson.Document;

/**
 *
 * @author ilyas <m.ilyas@live.com>
 */
public class AuthorizeGateway extends Gateway {

    @Override
    public HTTPResponse purchase(Document apiParameters, Customer customer, CustomerCard customerCard, Currency currency, float amount) {
        Document document;
        String result;
        try {
            HTTPResponse httpResponse = HTTPClient.httpPost(this.getApiURL(), this.buildPurchaseParameters(apiParameters, customer, customerCard, currency, amount).toJson(), ContentType.APPLICATION_JSON);
            document = Document.parse(httpResponse.getContent());
            result = ((Document) document.get("messages")).getString("resultCode");
            if (!result.equalsIgnoreCase("ok")) {
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

        Document document;
        String result;

        try {
            HTTPResponse httpResponse = HTTPClient.httpPost(this.getApiURL(), this.buildRefundParameters(apiParameters, refundParameters, amount).toJson(), ContentType.APPLICATION_JSON);
            document = Document.parse(httpResponse.getContent());
            result = ((Document) document.get("messages")).getString("resultCode");
            if (!result.equalsIgnoreCase("ok")) {
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

        Document document;
        String result;

        try {
            HTTPResponse httpResponse = HTTPClient.httpPost(this.getApiURL(), this.buildRebillParameters(apiParameters, rebillParameters, amount).toJson(), ContentType.APPLICATION_JSON);
            document = Document.parse(httpResponse.getContent());
            result = ((Document) document.get("messages")).getString("resultCode");
            if (!result.equalsIgnoreCase("ok")) {
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

        Document document;
        String result;

        try {
            HTTPResponse httpResponse = HTTPClient.httpPost(this.getApiURL(), this.buildVoidParameters(apiParameters, voidParameters).toJson(), ContentType.APPLICATION_JSON);
            document = Document.parse(httpResponse.getContent());
            result = ((Document) document.get("messages")).getString("resultCode");
            if (!result.equalsIgnoreCase("ok")) {
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
        return new Document()
                .append("name", "also called api user name / api login id")
                .append("transactionKey", "the transaction key");
    }

    @Override
    public Document getRefundSampleParameters() {
        return new Document()
                .append("refTransId", "the transaction id which will be refunded")
                .append("cardNumber", "last 4 digits of card")
                .append("expiryMonth", "must be 2 digits expiry month of card i.e for jan 01")
                .append("expiryYear", "must be 4 digits expiry year of card i.e 2017");
    }

    @Override
    public Document getRebillSampleParameters() {
        return new Document()
                .append("customerProfileId", "the customer profile id")
                .append("paymentProfileId", "the customer payment profile id");
    }

    @Override
    public Document getVoidSampleParameters() {
        return new Document()
                .append("refTransId", "the transaction id which will be void");
    }

    //private methods are starting below.
    private Document buildPurchaseParameters(Document apiParameters, Customer customer, CustomerCard customerCard, Currency currency, float amount) {

        Document document = new Document();
        document
                .append("createTransactionRequest", new Document()
                        .append("merchantAuthentication", apiParameters)
                        .append("transactionRequest", new Document()
                                .append("transactionType", "authCaptureTransaction")
                                .append("amount", Float.toString(amount))
                                .append("payment", new Document()
                                        .append("creditCard", new Document()
                                                .append("cardNumber", customerCard.getNumber())
                                                .append("expirationDate", customerCard.getExpiryMonth() + customerCard.getExpiryYear().substring(2))
                                                .append("cardCode", customerCard.getCvv())
                                        )
                                )
                                .append("profile", new Document()
                                        .append("createProfile", true)
                                )
                                .append("customer", new Document()
                                        .append("id", this.getUniqueCustomerId())
                                )
                                .append("billTo", new Document()
                                        .append("firstName", customer.getFirstName())
                                        .append("lastName", customer.getLastName())
                                        .append("address", customer.getAddress())
                                        .append("city", customer.getCity())
                                        .append("state", customer.getState())
                                        .append("zip", customer.getZip())
                                        .append("country", customer.getCountry().getCodeAlpha3())
                                )
                        )
                );

        return document;

    }

    private Document buildRefundParameters(Document apiParameters, Document refundParameters, float amount) {
        Document document = new Document();
        document
                .append("createTransactionRequest", new Document()
                        .append("merchantAuthentication", apiParameters)
                        .append("transactionRequest", new Document()
                                .append("transactionType", "refundTransaction")
                                .append("amount", Float.toString(amount))
                                .append("payment", new Document()
                                        .append("creditCard", new Document()
                                                .append("cardNumber", refundParameters.getString("cardNumber"))
                                                .append("expirationDate", refundParameters.getString("expiryMonth") + refundParameters.getString("expiryYear").substring(2))
                                        )
                                )
                                .append("refTransId", refundParameters.getString("refTransId"))
                        )
                );

        return document;
    }

    private Document buildVoidParameters(Document apiParameters, Document voidParameters) {
        Document document = new Document();
        document
                .append("createTransactionRequest", new Document()
                        .append("merchantAuthentication", apiParameters)
                        .append("transactionRequest", new Document()
                                .append("transactionType", "voidTransaction")
                                .append("refTransId", voidParameters.getString("refTransId"))
                        )
                );

        return document;
    }

    private Document buildRebillParameters(Document apiParameters, Document rebillParameters, float amount) {
        Document document = new Document();

        document.append("createTransactionRequest", new Document()
                .append("merchantAuthentication", apiParameters)
                .append("transactionRequest", new Document()
                        .append("transactionType", "authCaptureTransaction")
                        .append("amount", Float.toString(amount))
                        .append("profile", new Document()
                                .append("customerProfileId", rebillParameters.getString("customerProfileId"))
                                .append("paymentProfile", new Document()
                                        .append("paymentProfileId", rebillParameters.getString("paymentProfileId"))
                                )
                        )
                )
        );

        return document;
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
