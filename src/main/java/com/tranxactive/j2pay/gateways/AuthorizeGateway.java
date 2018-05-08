/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 
*/
package com.tranxactive.j2pay.gateways;

import com.tranxactive.j2pay.gateways.core.Gateway;
import com.tranxactive.j2pay.gateways.parameters.*;
import com.tranxactive.j2pay.gateways.responses.*;
import com.tranxactive.j2pay.net.HTTPResponse;
import org.json.JSONObject;

import static com.tranxactive.j2pay.gateways.parameters.Constants.Gateway.Authorize.LIVE_URL;
import static com.tranxactive.j2pay.gateways.parameters.Constants.Gateway.Authorize.RequestParameters.CUSTOMER_PROFILE_ID;
import static com.tranxactive.j2pay.gateways.parameters.Constants.Gateway.Authorize.RequestParameters.PAYMENT_PROFILE_ID;
import static com.tranxactive.j2pay.gateways.parameters.Constants.Gateway.Authorize.RequestParameters.TRANSACTION_KEY;
import static com.tranxactive.j2pay.gateways.parameters.Constants.Gateway.Authorize.RequestParameters.*;
import static com.tranxactive.j2pay.gateways.parameters.Constants.Gateway.Authorize.ResponseParameters.*;
import static com.tranxactive.j2pay.gateways.parameters.Constants.Gateway.Authorize.TEST_URL;
import static com.tranxactive.j2pay.gateways.parameters.ParamList.CARD_LAST_4;
import static com.tranxactive.j2pay.gateways.parameters.ParamList.TRANSACTION_ID;
import static com.tranxactive.j2pay.gateways.util.ResponseProcessor.processFinalResponse;
import static com.tranxactive.j2pay.gateways.util.UniqueCustomerIdGenerator.getUniqueCustomerId;
import static com.tranxactive.j2pay.net.HTTPClient.httpPost;
import static com.tranxactive.j2pay.net.XMLHelper.toJson;
import static org.apache.http.entity.ContentType.APPLICATION_XML;

/**
 *
 * @author ilyas
 */
public class AuthorizeGateway extends Gateway {

    @Override
    public HTTPResponse purchase(JSONObject apiParameters, Customer customer, CustomerCard customerCard, Currency currency, float amount) {

        JSONObject resp;
        String requestString = this.buildPurchaseParameters(apiParameters, customer, customerCard, currency, amount);

        PurchaseResponse successResponse = null;
        ErrorResponse errorResponse = new ErrorResponse();

        HTTPResponse httpResponse;

        int result;

        httpResponse = httpPost(this.getApiURL(), requestString, APPLICATION_XML);

        if (httpResponse.getStatusCode() == -1) {
            return httpResponse;
        }

        resp = toJson(httpResponse.getContent());

        if (resp.has(CREATE_TRANSACTION_RESPONSE)) {

            if (resp.getJSONObject(CREATE_TRANSACTION_RESPONSE).get(TRANSACTION_RESPONSE) instanceof JSONObject) {
                result = resp.getJSONObject(CREATE_TRANSACTION_RESPONSE).getJSONObject(TRANSACTION_RESPONSE).getInt(RESPONSE_CODE);
                if (result == 1) {
                    httpResponse.setSuccessful(true);
                    successResponse = new PurchaseResponse();
                    successResponse.setMessage(resp.getJSONObject(CREATE_TRANSACTION_RESPONSE).getJSONObject(TRANSACTION_RESPONSE).getJSONObject(MESSAGES).getJSONObject(MESSAGE).getString(DESCRIPTION));
                    successResponse.setTransactionId(resp.getJSONObject(CREATE_TRANSACTION_RESPONSE).getJSONObject(TRANSACTION_RESPONSE).get(Constants.Gateway.Authorize.ResponseParameters.TRANSACTION_ID).toString());
                    successResponse.setCardValuesFrom(customerCard);
                    successResponse.setAmount(amount);
                    successResponse.setCurrencyCode(currency);

                    if (resp.getJSONObject(CREATE_TRANSACTION_RESPONSE).getJSONObject(PROFILE_RESPONSE).has(CUSTOMER_PROFILE_ID)) {
                        successResponse.setRebillParams(new JSONObject()
                                .put(CUSTOMER_PROFILE_ID, resp.getJSONObject(CREATE_TRANSACTION_RESPONSE).getJSONObject(PROFILE_RESPONSE).get(CUSTOMER_PROFILE_ID).toString())
                                .put(PAYMENT_PROFILE_ID, resp.getJSONObject(CREATE_TRANSACTION_RESPONSE).getJSONObject(PROFILE_RESPONSE).getJSONObject(CUSTOMER_PAYMENT_PROFILE_ID_LIST).get(NUMERIC_STRING).toString())
                        );
                    }else{
                        successResponse.setRebillParams(null);
                    }

                    successResponse.setRefundParams(new JSONObject()
                            .put(TRANSACTION_ID.getName(), resp.getJSONObject(CREATE_TRANSACTION_RESPONSE).getJSONObject(TRANSACTION_RESPONSE).get(Constants.Gateway.Authorize.ResponseParameters.TRANSACTION_ID).toString())
                            .put(CARD_LAST_4.getName(), customerCard.getLast4())
                    );

                    successResponse.setVoidParams(new JSONObject()
                            .put(TRANSACTION_ID.getName(), resp.getJSONObject(CREATE_TRANSACTION_RESPONSE).getJSONObject(TRANSACTION_RESPONSE).get(Constants.Gateway.Authorize.ResponseParameters.TRANSACTION_ID).toString())
                    );

                } else {
                    errorResponse.setMessage(resp.getJSONObject(CREATE_TRANSACTION_RESPONSE).getJSONObject(TRANSACTION_RESPONSE).getJSONObject(ERRORS).getJSONObject(ERROR).getString(ERROR_TEXT));
                }
            } else {
                errorResponse.setMessage(resp.getJSONObject(CREATE_TRANSACTION_RESPONSE).getJSONObject(MESSAGES).getJSONObject(MESSAGE).getString(TEXT));
            }
        } else {
            errorResponse.setMessage(resp.getJSONObject(ERROR_RESPONSE).getJSONObject(MESSAGES).getJSONObject(MESSAGE).getString(TEXT));
        }

        //final response.
        processFinalResponse(resp, httpResponse, successResponse, errorResponse);
        return httpResponse;
    }

    @Override
    public HTTPResponse refund(JSONObject apiParameters, JSONObject refundParameters, float amount) {

        JSONObject resp;

        String requestString = this.buildRefundParameters(apiParameters, refundParameters, amount);

        RefundResponse successResponse = null;
        ErrorResponse errorResponse = new ErrorResponse();

        HTTPResponse httpResponse;

        int result;

        httpResponse = httpPost(this.getApiURL(), requestString, APPLICATION_XML);

        if (httpResponse.getStatusCode() == -1) {
            return httpResponse;
        }

        resp = toJson(httpResponse.getContent());

        if (resp.has(CREATE_TRANSACTION_RESPONSE)) {

            if (resp.getJSONObject(CREATE_TRANSACTION_RESPONSE).get(TRANSACTION_RESPONSE) instanceof JSONObject) {
                result = resp.getJSONObject(CREATE_TRANSACTION_RESPONSE).getJSONObject(TRANSACTION_RESPONSE).getInt(RESPONSE_CODE);
                if (result == 1) {
                    httpResponse.setSuccessful(true);
                    successResponse = new RefundResponse();
                    
                    successResponse.setMessage(resp.getJSONObject(CREATE_TRANSACTION_RESPONSE).getJSONObject(TRANSACTION_RESPONSE).getJSONObject(MESSAGES).getJSONObject(MESSAGE).getString(DESCRIPTION));
                    successResponse.setTransactionId(resp.getJSONObject(CREATE_TRANSACTION_RESPONSE).getJSONObject(TRANSACTION_RESPONSE).get(Constants.Gateway.Authorize.ResponseParameters.TRANSACTION_ID).toString());
                    successResponse.setAmount(amount);
                    
                    successResponse.setVoidParams(new JSONObject()
                            .put(TRANSACTION_ID.getName(), resp.getJSONObject(CREATE_TRANSACTION_RESPONSE).getJSONObject(TRANSACTION_RESPONSE).get(Constants.Gateway.Authorize.ResponseParameters.TRANSACTION_ID).toString())
                    );
                } else {
                    errorResponse.setMessage(resp.getJSONObject(CREATE_TRANSACTION_RESPONSE).getJSONObject(TRANSACTION_RESPONSE).getJSONObject(ERRORS).getJSONObject(ERROR).getString(ERROR_TEXT));
                }
            } else {
                errorResponse.setMessage(resp.getJSONObject(CREATE_TRANSACTION_RESPONSE).getJSONObject(MESSAGES).getJSONObject(MESSAGE).getString(TEXT));
            }
        } else {
            errorResponse.setMessage(resp.getJSONObject(ERROR_RESPONSE).getJSONObject(MESSAGES).getJSONObject(MESSAGE).getString(TEXT));
        }

        //final response.
        processFinalResponse(resp, httpResponse, successResponse, errorResponse);
        return httpResponse;
    }

    @Override
    public HTTPResponse rebill(JSONObject apiParameters, JSONObject rebillParameters, float amount) {

        JSONObject resp;

        String requestString = this.buildRebillParameters(apiParameters, rebillParameters, amount);

        RebillResponse successResponse = null;
        ErrorResponse errorResponse = new ErrorResponse();

        HTTPResponse httpResponse;

        int result;

        httpResponse = httpPost(this.getApiURL(), requestString, APPLICATION_XML);

        if (httpResponse.getStatusCode() == -1) {
            return httpResponse;
        }

        resp = toJson(httpResponse.getContent());

        if (resp.has(CREATE_TRANSACTION_RESPONSE)) {

            if (resp.getJSONObject(CREATE_TRANSACTION_RESPONSE).get(TRANSACTION_RESPONSE) instanceof JSONObject) {
                result = resp.getJSONObject(CREATE_TRANSACTION_RESPONSE).getJSONObject(TRANSACTION_RESPONSE).getInt(RESPONSE_CODE);
                if (result == 1) {
                    String cardLast4 = resp.getJSONObject(CREATE_TRANSACTION_RESPONSE).getJSONObject(TRANSACTION_RESPONSE).get(ACCOUNT_NUMBER).toString();
                    cardLast4 = cardLast4.substring(cardLast4.length() - 4, cardLast4.length());

                    httpResponse.setSuccessful(true);
                    successResponse = new RebillResponse();

                    successResponse.setMessage(resp.getJSONObject(CREATE_TRANSACTION_RESPONSE).getJSONObject(TRANSACTION_RESPONSE).getJSONObject(MESSAGES).getJSONObject(MESSAGE).getString(DESCRIPTION));
                    successResponse.setTransactionId(resp.getJSONObject(CREATE_TRANSACTION_RESPONSE).getJSONObject(TRANSACTION_RESPONSE).get(Constants.Gateway.Authorize.ResponseParameters.TRANSACTION_ID).toString());
                    successResponse.setAmount(amount);

                    successResponse.setRebillParams(rebillParameters);

                    successResponse.setRefundParams(new JSONObject()
                            .put(TRANSACTION_ID.getName(), resp.getJSONObject(CREATE_TRANSACTION_RESPONSE).getJSONObject(TRANSACTION_RESPONSE).get(Constants.Gateway.Authorize.ResponseParameters.TRANSACTION_ID).toString())
                            .put(CARD_LAST_4.getName(), cardLast4)
                    );

                    successResponse.setVoidParams(new JSONObject()
                            .put(TRANSACTION_ID.getName(), resp.getJSONObject(CREATE_TRANSACTION_RESPONSE).getJSONObject(TRANSACTION_RESPONSE).get(Constants.Gateway.Authorize.ResponseParameters.TRANSACTION_ID).toString())
                    );

                } else {
                    errorResponse.setMessage(resp.getJSONObject(CREATE_TRANSACTION_RESPONSE).getJSONObject(TRANSACTION_RESPONSE).getJSONObject(ERRORS).getJSONObject(ERROR).getString(ERROR_TEXT));
                }
            } else {
                errorResponse.setMessage(resp.getJSONObject(CREATE_TRANSACTION_RESPONSE).getJSONObject(MESSAGES).getJSONObject(MESSAGE).getString(TEXT));
            }
        } else {
            errorResponse.setMessage(resp.getJSONObject(ERROR_RESPONSE).getJSONObject(MESSAGES).getJSONObject(MESSAGE).getString(TEXT));
        }

        //final response.
        processFinalResponse(resp, httpResponse, successResponse, errorResponse);
        return httpResponse;

    }

    @Override
    public HTTPResponse voidTransaction(JSONObject apiParameters, JSONObject voidParameters) {

        String requestString = this.buildVoidParameters(apiParameters, voidParameters);

        VoidResponse successResponse = null;
        ErrorResponse errorResponse = new ErrorResponse();

        HTTPResponse httpResponse = httpPost(this.getApiURL(), requestString, APPLICATION_XML);

        if (httpResponse.getStatusCode() == -1) {
            return httpResponse;
        }

        JSONObject resp = toJson(httpResponse.getContent());

        if (resp.has(CREATE_TRANSACTION_RESPONSE)) {

            if (resp.getJSONObject(CREATE_TRANSACTION_RESPONSE).get(TRANSACTION_RESPONSE) instanceof JSONObject) {
               int result = resp.getJSONObject(CREATE_TRANSACTION_RESPONSE).getJSONObject(TRANSACTION_RESPONSE).getInt(RESPONSE_CODE);
                if (result == 1) {
                    httpResponse.setSuccessful(true);
                    successResponse = new VoidResponse();

                    successResponse.setMessage(resp.getJSONObject(CREATE_TRANSACTION_RESPONSE).getJSONObject(TRANSACTION_RESPONSE).getJSONObject(MESSAGES).getJSONObject(MESSAGE).getString(DESCRIPTION));
                    successResponse.setTransactionId(resp.getJSONObject(CREATE_TRANSACTION_RESPONSE).getJSONObject(TRANSACTION_RESPONSE).get(Constants.Gateway.Authorize.ResponseParameters.TRANSACTION_ID).toString());

                } else {
                    errorResponse.setMessage(resp.getJSONObject(CREATE_TRANSACTION_RESPONSE).getJSONObject(TRANSACTION_RESPONSE).getJSONObject(ERRORS).getJSONObject(ERROR).getString(ERROR_TEXT));
                }
            } else {
                errorResponse.setMessage(resp.getJSONObject(CREATE_TRANSACTION_RESPONSE).getJSONObject(MESSAGES).getJSONObject(MESSAGE).getString(TEXT));
            }
        } else {
            errorResponse.setMessage(resp.getJSONObject(ERROR_RESPONSE).getJSONObject(MESSAGES).getJSONObject(MESSAGE).getString(TEXT));
        }

        //final response.
        processFinalResponse(resp, httpResponse, successResponse, errorResponse);
        return httpResponse;

    }

    @Override
    public JSONObject getApiSampleParameters() {
        return new JSONObject()
                .put(NAME, "also called api user name / api login id")
                .put(TRANSACTION_KEY, "the transaction key");
    }

    @Override
    public JSONObject getRefundSampleParameters() {
        return new JSONObject()
                .put(TRANSACTION_ID.getName(), "the transaction id which will be refunded")
                .put(CARD_LAST_4.getName(), "last 4 digits of card")
                .put(ParamList.CARD_EXPIRY_MONTH.getName(), "must be 2 digits expiry month of card i.e for jan 01")
                .put(ParamList.CARD_EXPIRY_YEAR.getName(), "must be 4 digits expiry year of card i.e 2017");
    }

    @Override
    public JSONObject getRebillSampleParameters() {
        return new JSONObject()
                .put(CUSTOMER_PROFILE_ID, "the customer profile id")
                .put(PAYMENT_PROFILE_ID, "the customer payment profile id");
    }

    @Override
    public JSONObject getVoidSampleParameters() {
        return new JSONObject().put(TRANSACTION_ID.getName(), "the transaction id which will be void");
    }

    //private methods are starting below.
    private String buildPurchaseParameters(JSONObject apiParameters, Customer customer, CustomerCard customerCard, Currency currency, float amount) {

        StringBuilder finalParams = new StringBuilder();

        finalParams
                .append("<createTransactionRequest xmlns='AnetApi/xml/v1/schema/AnetApiSchema.xsd'>")
                .append("<merchantAuthentication>")
                .append("<name>").append(apiParameters.getString(NAME)).append("</name>")
                .append("<transactionKey>").append(apiParameters.getString(TRANSACTION_KEY)).append("</transactionKey>")
                .append("</merchantAuthentication>")
                .append("<transactionRequest>")
                .append("<transactionType>").append(TRANSACTION_TYPE_PURCHASE).append("</transactionType>")
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
                .append("<id>").append(getUniqueCustomerId()).append("</id>")
                .append("<email>").append(customer.getEmail()).append("</email>")
                .append("</customer>")
                .append("<billTo>")
                .append("<firstName>").append(customer.getFirstName()).append("</firstName>")
                .append("<lastName>").append(customer.getLastName()).append("</lastName>")
                .append("<address>").append(customer.getAddress()).append("</address>")
                .append("<city>").append(customer.getCity()).append("</city>")
                .append("<state>").append(customer.getState()).append("</state>")
                .append("<zip>").append(customer.getZip()).append("</zip>")
                .append("<country>").append(customer.getCountry().getCodeISO3()).append("</country>")
                .append("<phoneNumber>").append(customer.getPhoneNumber()).append("</phoneNumber>")
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
                .append("<name>").append(apiParameters.getString(NAME)).append("</name>")
                .append("<transactionKey>").append(apiParameters.getString(TRANSACTION_KEY)).append("</transactionKey>")
                .append("</merchantAuthentication>")
                .append("<transactionRequest>")
                .append("<transactionType>").append(TRANSACTION_TYPE_REFUND).append("</transactionType>")
                .append("<amount>").append(Float.toString(amount)).append("</amount>")
                .append("<payment>")
                .append("<creditCard>")
                .append("<cardNumber>").append(refundParameters.getString(CARD_LAST_4.getName())).append("</cardNumber>")
                .append("<expirationDate>XXXX")/*.append(refundParameters.getString(ParamList.CARD_EXPIRY_MONTH.getName())).append(refundParameters.getString(ParamList.CARD_EXPIRY_YEAR.getName()).substring(2))*/
                .append("</expirationDate>")
                .append("</creditCard>")
                .append("</payment>")
                .append("<refTransId>").append(refundParameters.getString(TRANSACTION_ID.getName())).append("</refTransId>")
                .append("</transactionRequest>")
                .append("</createTransactionRequest>");

        return finalParams.toString();

    }

    private String buildVoidParameters(JSONObject apiParameters, JSONObject voidParameters) {

        StringBuilder finalParams = new StringBuilder();

        finalParams
                .append("<createTransactionRequest xmlns='AnetApi/xml/v1/schema/AnetApiSchema.xsd'>")
                .append("<merchantAuthentication>")
                .append("<name>").append(apiParameters.getString(NAME)).append("</name>")
                .append("<transactionKey>").append(apiParameters.getString(TRANSACTION_KEY)).append("</transactionKey>")
                .append("</merchantAuthentication>")
                .append("<transactionRequest>")
                .append("<transactionType>").append(TRANSACTION_TYPE_VOID).append("</transactionType>")
                .append("<refTransId>").append(voidParameters.getString(TRANSACTION_ID.getName())).append("</refTransId>")
                .append("</transactionRequest>")
                .append("</createTransactionRequest>");

        return finalParams.toString();
    }

    private String buildRebillParameters(JSONObject apiParameters, JSONObject rebillParameters, float amount) {

        StringBuilder finalParams = new StringBuilder();

        finalParams
                .append("<createTransactionRequest xmlns='AnetApi/xml/v1/schema/AnetApiSchema.xsd'>")
                .append("<merchantAuthentication>")
                .append("<name>").append(apiParameters.getString(NAME)).append("</name>")
                .append("<transactionKey>").append(apiParameters.getString(TRANSACTION_KEY)).append("</transactionKey>")
                .append("</merchantAuthentication>")
                .append("<transactionRequest>")
                .append("<transactionType>").append(TRANSACTION_TYPE_REBILL).append("</transactionType>")
                .append("<amount>").append(Float.toString(amount)).append("</amount>")
                .append("<profile>")
                .append("<customerProfileId>").append(rebillParameters.getString(CUSTOMER_PROFILE_ID)).append("</customerProfileId>")
                .append("<paymentProfile>")
                .append("<paymentProfileId>").append(rebillParameters.getString(PAYMENT_PROFILE_ID)).append("</paymentProfileId>")
                .append("</paymentProfile>")
                .append("</profile>")
                .append("</transactionRequest>")
                .append("</createTransactionRequest>");

        return finalParams.toString();
    }

    private String getApiURL() {
        return isTestMode() ? TEST_URL : LIVE_URL;
    }

}
