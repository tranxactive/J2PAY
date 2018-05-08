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
import com.tranxactive.j2pay.gateways.responses.ErrorResponse;
import com.tranxactive.j2pay.gateways.responses.PurchaseResponse;
import com.tranxactive.j2pay.gateways.responses.RefundResponse;
import com.tranxactive.j2pay.gateways.responses.VoidResponse;
import com.tranxactive.j2pay.net.HTTPClient;
import com.tranxactive.j2pay.net.HTTPResponse;
import com.tranxactive.j2pay.net.XMLHelper;
import org.json.JSONObject;

import static com.tranxactive.j2pay.gateways.parameters.Constants.Gateway.Payeezy.ResponseParameters.*;
import static com.tranxactive.j2pay.gateways.parameters.Constants.Gateway.Payeezy.TRANSACTION_RESULT;
import static com.tranxactive.j2pay.gateways.parameters.ParamList.AMOUNT;
import static com.tranxactive.j2pay.gateways.parameters.ParamList.TRANSACTION_ID;
import static com.tranxactive.j2pay.gateways.util.ResponseProcessor.processFinalResponse;
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

        StringBuilder finalParams = new StringBuilder();

        finalParams
                .append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>")
                .append("<Transaction>")
                .append("<ExactID>").append(apiParameters.getString("exactId")).append("</ExactID>")
                .append("<Password>").append(apiParameters.getString("password")).append("</Password>")
                .append("<Transaction_Type>00</Transaction_Type>")
                .append("<DollarAmount>").append(Float.toString(amount)).append("</DollarAmount>")
                .append("<Currency>").append(currency).append("</Currency>")
                .append("<CardHoldersName>").append(customerCard.getName()).append("</CardHoldersName>")
                .append("<Card_Number>").append(customerCard.getNumber()).append("</Card_Number>")
                .append("<VerificationStr2>").append(customerCard.getCvv()).append("</VerificationStr2>")
                .append("<CVD_Presence_Ind>1</CVD_Presence_Ind>")
                .append("<Expiry_Date>").append(customerCard.getExpiryMonth()).append(customerCard.getExpiryYear().substring(2)).append("</Expiry_Date>")
                .append("<Client_IP>").append(customer.getIp()).append("</Client_IP>")
                .append("<Client_Email>").append(customer.getEmail()).append("</Client_Email>")
                .append("<VerificationStr1>").append(customer.getAddress()).append("|").append(customer.getZip()).append("|").append(customer.getCity()).append("|").append(customer.getState()).append("|").append(customer.getCountry().getCodeISO2()).append("</VerificationStr1>")
                .append("</Transaction>");

        return finalParams.toString();

    }

    private String buildRefundParameters(JSONObject apiParameters, JSONObject refundParameters, float amount) {
        return buildParameters(TRANSACTION_TYPE_REFUND, apiParameters, refundParameters, Float.toString(amount));
    }

    private String buildVoidParameters(JSONObject apiParameters, JSONObject voidParameters) {
        return buildParameters(TRANSACTION_TYPE_VOID, apiParameters, voidParameters, voidParameters.get(TRANSACTION_ID.getName()).toString());
    }

    private String buildParameters(final String transactionType, JSONObject apiParameters, JSONObject voidParameters, String amount) {
        StringBuilder finalParams = new StringBuilder();

        finalParams
                .append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>")
                .append("<Transaction>")
                .append("<ExactID>").append(apiParameters.getString("exactId")).append("</ExactID>")
                .append("<Password>").append(apiParameters.getString("password")).append("</Password>")
                .append("<Transaction_Type>" + transactionType + "</Transaction_Type>")
                .append("<DollarAmount>").append(amount).append("</DollarAmount>")
                .append("<Transaction_Tag>").append(voidParameters.get(TRANSACTION_ID.getName()).toString()).append("</Transaction_Tag>")
                .append("<Authorization_Num>").append(voidParameters.get("authorizationNumber").toString()).append("</Authorization_Num>")
                .append("</Transaction>");

        return finalParams.toString();
    }



    private String getApiURL() {
        return "https://api." + (isTestMode()? "demo." : "") + "globalgatewaye4.firstdata.com/transaction/v11";
    }

}
