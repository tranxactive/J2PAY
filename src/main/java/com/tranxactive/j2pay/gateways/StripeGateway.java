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
import org.apache.http.entity.ContentType;
import org.json.JSONObject;

import static com.tranxactive.j2pay.gateways.util.ResponseProcessor.processFinalResponse;

/**
 *
 * @author ilyas
 */
public class StripeGateway extends Gateway {

    private final String apiURL = "https://api.stripe.com/v1";

    @Override
    public HTTPResponse purchase(JSONObject apiParameters, Customer customer, CustomerCard customerCard, Currency currency, float amount) {

        HTTPResponse tokenHTTPResponse = HTTPClient.httpPostWithBasicAuth(
                apiURL + "/tokens",
                QueryStringHelper.toQueryString(JSONHelper.encode(this.buildTokenParameters(customer, customerCard, currency))),
                ContentType.APPLICATION_FORM_URLENCODED,
                null,
                apiParameters.getString("userName"),
                "");

        PurchaseResponse successResponse = null;
        ErrorResponse errorResponse = new ErrorResponse();

        if (tokenHTTPResponse.getStatusCode() == -1) {
            return tokenHTTPResponse;
        }

        JSONObject tokenJSONResponse = tokenHTTPResponse.getJSONResponse();

        if (!(tokenHTTPResponse.getStatusCode() >= 200 && tokenHTTPResponse.getStatusCode() < 300)) {
            errorResponse.setMessage(tokenJSONResponse.getJSONObject("error").getString("message"));
            processFinalResponse(tokenJSONResponse, tokenHTTPResponse, successResponse, errorResponse);
            return tokenHTTPResponse;
        }

        HTTPResponse saveCustomerHTTPResponse = HTTPClient.httpPostWithBasicAuth(
                apiURL + "/customers",
                QueryStringHelper.toQueryString(JSONHelper.encode(this.buildSaveCustomerParameters(tokenJSONResponse.getString("id")))),
                ContentType.APPLICATION_FORM_URLENCODED,
                null,
                apiParameters.getString("userName"),
                "");

        if (saveCustomerHTTPResponse.getStatusCode() == -1) {
            return saveCustomerHTTPResponse;
        }

        JSONObject saveCustomerJSONResponse = saveCustomerHTTPResponse.getJSONResponse();
        if (!(saveCustomerHTTPResponse.getStatusCode() >= 200 && saveCustomerHTTPResponse.getStatusCode() < 300)) {
            errorResponse.setMessage(saveCustomerJSONResponse.getJSONObject("error").getString("message"));
            processFinalResponse(saveCustomerJSONResponse, saveCustomerHTTPResponse, successResponse, errorResponse);
            return saveCustomerHTTPResponse;
        }

        JSONObject requestObject = this.buildPurchaseParameters(saveCustomerJSONResponse.getString("id"), currency, amount);
        JSONObject responseObject;
        String requestString;

        requestObject = JSONHelper.encode(requestObject);
        requestString = QueryStringHelper.toQueryString(requestObject);
        HTTPResponse httpResponse;

        httpResponse = HTTPClient.httpPostWithBasicAuth(
                this.apiURL + "/charges",
                requestString,
                ContentType.APPLICATION_FORM_URLENCODED,
                null,
                apiParameters.getString("userName"),
                ""
        );

        if (httpResponse.getStatusCode() == -1) {
            return httpResponse;
        }

        responseObject = httpResponse.getJSONResponse();

        if (httpResponse.getStatusCode() >= 200 && httpResponse.getStatusCode() < 300) {
            successResponse = new PurchaseResponse();
            successResponse.setMessage("Success");
            successResponse.setTransactionId(responseObject.getString("id"));
            successResponse.setCardValuesFrom(customerCard);
            successResponse.setAmount(amount);
            successResponse.setCurrencyCode(currency);

            successResponse.setRebillParams(new JSONObject()
                    .put("customerId", saveCustomerJSONResponse.getString("id"))
                    .put("currency", currency)
            );

            successResponse.setVoidParams(new JSONObject()
                    .put("chargeId", responseObject.getString("id"))
                    .put("amount", amount)
            );

            successResponse.setRefundParams(new JSONObject()
                    .put("chargeId", responseObject.getString("id"))
            );

        } else {
            errorResponse.setMessage(responseObject.getJSONObject("error").getString("message"));
            errorResponse.setTransactionId(null);
        }

        //final response.
        processFinalResponse(responseObject, httpResponse, successResponse, errorResponse);
        return httpResponse;

    }

    @Override
    public HTTPResponse authorize(JSONObject apiParameters, Customer customer, CustomerCard customerCard, Currency currency, float amount) {

        HTTPResponse tokenHTTPResponse = HTTPClient.httpPostWithBasicAuth(
                apiURL + "/tokens",
                QueryStringHelper.toQueryString(JSONHelper.encode(this.buildTokenParameters(customer, customerCard, currency))),
                ContentType.APPLICATION_FORM_URLENCODED,
                null,
                apiParameters.getString("userName"),
                "");

        AuthResponse successResponse = null;
        ErrorResponse errorResponse = new ErrorResponse();

        if (tokenHTTPResponse.getStatusCode() == -1) {
            return tokenHTTPResponse;
        }

        JSONObject tokenJSONResponse = tokenHTTPResponse.getJSONResponse();

        if (!(tokenHTTPResponse.getStatusCode() >= 200 && tokenHTTPResponse.getStatusCode() < 300)) {
            errorResponse.setMessage(tokenJSONResponse.getJSONObject("error").getString("message"));
            processFinalResponse(tokenJSONResponse, tokenHTTPResponse, successResponse, errorResponse);
            return tokenHTTPResponse;
        }

        HTTPResponse saveCustomerHTTPResponse = HTTPClient.httpPostWithBasicAuth(
                apiURL + "/customers",
                QueryStringHelper.toQueryString(JSONHelper.encode(this.buildSaveCustomerParameters(tokenJSONResponse.getString("id")))),
                ContentType.APPLICATION_FORM_URLENCODED,
                null,
                apiParameters.getString("userName"),
                "");

        if (saveCustomerHTTPResponse.getStatusCode() == -1) {
            return saveCustomerHTTPResponse;
        }

        JSONObject saveCustomerJSONResponse = saveCustomerHTTPResponse.getJSONResponse();
        if (!(saveCustomerHTTPResponse.getStatusCode() >= 200 && saveCustomerHTTPResponse.getStatusCode() < 300)) {
            errorResponse.setMessage(saveCustomerJSONResponse.getJSONObject("error").getString("message"));
            processFinalResponse(saveCustomerJSONResponse, saveCustomerHTTPResponse, successResponse, errorResponse);
            return saveCustomerHTTPResponse;
        }

        JSONObject requestObject = this.buildAuthorizeParameters(saveCustomerJSONResponse.getString("id"), currency, amount);
        JSONObject responseObject;
        String requestString;

        requestObject = JSONHelper.encode(requestObject);
        requestString = QueryStringHelper.toQueryString(requestObject);
        HTTPResponse httpResponse;

        httpResponse = HTTPClient.httpPostWithBasicAuth(
                this.apiURL + "/charges",
                requestString,
                ContentType.APPLICATION_FORM_URLENCODED,
                null,
                apiParameters.getString("userName"),
                ""
        );

        if (httpResponse.getStatusCode() == -1) {
            return httpResponse;
        }

        responseObject = httpResponse.getJSONResponse();

        if (httpResponse.getStatusCode() >= 200 && httpResponse.getStatusCode() < 300) {
            successResponse = new AuthResponse();
            successResponse.setMessage("Success");
            successResponse.setTransactionId(responseObject.getString("id"));
            successResponse.setCardValuesFrom(customerCard);
            successResponse.setAmount(amount);
            successResponse.setCurrencyCode(currency);

            successResponse.setRebillParams(new JSONObject()
                    .put("customerId", saveCustomerJSONResponse.getString("id"))
                    .put("currency", currency)
            );

            successResponse.setVoidParams(new JSONObject()
                    .put("chargeId", responseObject.getString("id"))
                    .put("amount", amount)
            );

            successResponse.setCaptureParams(new JSONObject()
                    .put("chargeId", responseObject.getString("id"))
            );

        } else {
            errorResponse.setMessage(responseObject.getJSONObject("error").getString("message"));
            errorResponse.setTransactionId(null);
        }

        //final response.
        processFinalResponse(responseObject, httpResponse, successResponse, errorResponse);
        return httpResponse;

    }

    @Override
    public HTTPResponse capture(JSONObject apiParameters, JSONObject captureParameters, float amount) {

        CaptureResponse successResponse = null;
        ErrorResponse errorResponse = new ErrorResponse();

        JSONObject requestObject = this.buildCaptureParameters(amount);
        JSONObject responseObject;
        String requestString;

        requestObject = JSONHelper.encode(requestObject);
        requestString = QueryStringHelper.toQueryString(requestObject);
        HTTPResponse httpResponse;

        httpResponse = HTTPClient.httpPostWithBasicAuth(
                this.apiURL + "/charges/" + captureParameters.getString("chargeId") + "/capture",
                requestString,
                ContentType.APPLICATION_FORM_URLENCODED,
                null,
                apiParameters.getString("userName"),
                ""
        );

        if (httpResponse.getStatusCode() == -1) {
            return httpResponse;
        }

        responseObject = httpResponse.getJSONResponse();

        if (httpResponse.getStatusCode() >= 200 && httpResponse.getStatusCode() < 300) {
            successResponse = new CaptureResponse();
            successResponse.setMessage("Success");
            successResponse.setTransactionId(responseObject.getString("id"));
            successResponse.setAmount(amount);

            successResponse.setVoidParams(new JSONObject()
                    .put("chargeId", responseObject.getString("id"))
                    .put("amount", amount)
            );

            successResponse.setRefundParams(new JSONObject()
                    .put("chargeId", responseObject.getString("id"))
            );

        } else {
            errorResponse.setMessage(responseObject.getJSONObject("error").getString("message"));
            errorResponse.setTransactionId(null);
        }

        //final response.
        processFinalResponse(responseObject, httpResponse, successResponse, errorResponse);
        return httpResponse;
    }

    @Override
    public HTTPResponse refund(JSONObject apiParameters, JSONObject refundParameters, float amount) {

        RefundResponse successResponse = null;
        ErrorResponse errorResponse = new ErrorResponse();

        JSONObject requestObject = this.buildRefundParameters(refundParameters, amount);
        JSONObject responseObject;
        String requestString;

        requestObject = JSONHelper.encode(requestObject);
        requestString = QueryStringHelper.toQueryString(requestObject);
        HTTPResponse httpResponse;

        httpResponse = HTTPClient.httpPostWithBasicAuth(
                this.apiURL + "/refunds",
                requestString,
                ContentType.APPLICATION_FORM_URLENCODED,
                null,
                apiParameters.getString("userName"),
                ""
        );

        if (httpResponse.getStatusCode() == -1) {
            return httpResponse;
        }

        responseObject = httpResponse.getJSONResponse();

        if (httpResponse.getStatusCode() >= 200 && httpResponse.getStatusCode() < 300) {
            successResponse = new RefundResponse();
            successResponse.setMessage("Success");
            successResponse.setTransactionId(responseObject.getString("id"));
            successResponse.setAmount(amount);

            successResponse.setVoidParams(null);

        } else {
            errorResponse.setMessage(responseObject.getJSONObject("error").getString("message"));
            errorResponse.setTransactionId(null);
        }

        //final response.
        processFinalResponse(responseObject, httpResponse, successResponse, errorResponse);
        return httpResponse;

    }

    @Override
    public HTTPResponse rebill(JSONObject apiParameters, JSONObject rebillParameters, float amount) {

        RebillResponse successResponse = null;
        ErrorResponse errorResponse = new ErrorResponse();

        JSONObject requestObject = this.buildRebillParameters(rebillParameters.getString("customerId"), Currency.valueOf(rebillParameters.getString("currency")), amount);
        JSONObject responseObject;
        String requestString;

        requestObject = JSONHelper.encode(requestObject);
        requestString = QueryStringHelper.toQueryString(requestObject);
        HTTPResponse httpResponse;

        httpResponse = HTTPClient.httpPostWithBasicAuth(
                this.apiURL + "/charges",
                requestString,
                ContentType.APPLICATION_FORM_URLENCODED,
                null,
                apiParameters.getString("userName"),
                ""
        );

        if (httpResponse.getStatusCode() == -1) {
            return httpResponse;
        }

        responseObject = httpResponse.getJSONResponse();

        if (httpResponse.getStatusCode() >= 200 && httpResponse.getStatusCode() < 300) {
            successResponse = new RebillResponse();
            successResponse.setMessage("Success");
            successResponse.setTransactionId(responseObject.getString("id"));
            successResponse.setAmount(amount);

            successResponse.setRebillParams(new JSONObject()
                    .put("customerId", rebillParameters.getString("customerId"))
                    .put("currency", rebillParameters.getString("currency"))
            );

            successResponse.setVoidParams(new JSONObject()
                    .put("chargeId", responseObject.getString("id"))
                    .put("amount", (int) (amount * 100))
            );

            successResponse.setRefundParams(new JSONObject()
                    .put("chargeId", responseObject.getString("id"))
            );

        } else {
            errorResponse.setMessage(responseObject.getJSONObject("error").getString("message"));
            errorResponse.setTransactionId(null);
        }

        //final response.
        processFinalResponse(responseObject, httpResponse, successResponse, errorResponse);
        return httpResponse;
    }

    @Override
    public HTTPResponse voidTransaction(JSONObject apiParameters, JSONObject voidParameters) {

        VoidResponse successResponse = null;
        ErrorResponse errorResponse = new ErrorResponse();

        JSONObject requestObject = this.buildVoidParameters(voidParameters, voidParameters.getInt("amount"));
        JSONObject responseObject;
        String requestString;

        requestObject = JSONHelper.encode(requestObject);
        requestString = QueryStringHelper.toQueryString(requestObject);
        HTTPResponse httpResponse;

        httpResponse = HTTPClient.httpPostWithBasicAuth(
                this.apiURL + "/refunds",
                requestString,
                ContentType.APPLICATION_FORM_URLENCODED,
                null,
                apiParameters.getString("userName"),
                ""
        );

        if (httpResponse.getStatusCode() == -1) {
            return httpResponse;
        }

        responseObject = httpResponse.getJSONResponse();

        if (httpResponse.getStatusCode() >= 200 && httpResponse.getStatusCode() < 300) {
            successResponse = new VoidResponse();
            successResponse.setMessage("Success");
            successResponse.setTransactionId(responseObject.getString("id"));

        } else {
            errorResponse.setMessage(responseObject.getJSONObject("error").getString("message"));
            errorResponse.setTransactionId(null);
        }

        //final response.
        processFinalResponse(responseObject, httpResponse, successResponse, errorResponse);
        return httpResponse;
    }

    @Override
    public JSONObject getApiSampleParameters() {
        return new JSONObject()
                .put("userName", "the auth user name also called secret key");
    }

    @Override
    public JSONObject getRefundSampleParameters() {
        return new JSONObject()
                .put("chargeId", "the charge id");
    }

    @Override
    public JSONObject getRebillSampleParameters() {
        return new JSONObject()
                .put("customerId", "the customer id")
                .put("currency", "the currency");
    }

    @Override
    public JSONObject getVoidSampleParameters() {
        return new JSONObject()
                .put("chargeId", "the charged transaction id")
                .put("amount", "the amount");
    }

    @Override
    public JSONObject getCaptureSampleParameters() {
        return new JSONObject()
                .put("chargeId", "The captured transaction id");
    }

    //private methods are starting below.
    private JSONObject buildTokenParameters(Customer customer, CustomerCard customerCard, Currency currency) {

        JSONObject object = new JSONObject();
        object
                .put("card[number]", customerCard.getNumber())
                .put("card[exp_month]", customerCard.getExpiryMonth())
                .put("card[exp_year]", customerCard.getExpiryYear().substring(2))
                .put("card[cvc]", customerCard.getCvv())
                .put("card[address_country]", customer.getCountry().getCodeISO2())
                .put("card[address_city]", customer.getCity())
                .put("card[address_line1]", customer.getAddress())
                .put("card[address_zip]", customer.getZip())
                .put("card[name]", customerCard.getName())
                .put("card[currency]", currency)
                .put("card[address_state]", customer.getState());

        return object;
    }

    private JSONObject buildSaveCustomerParameters(String source) {

        JSONObject object = new JSONObject();
        object
                .put("source", source);

        return object;
    }

    private JSONObject buildPurchaseParameters(String customer, Currency currency, float amount) {

        JSONObject object = new JSONObject();
        object
                .put("amount", (int) (amount * 100))
                .put("currency", currency)
                .put("customer", customer);

        return object;

    }

    private JSONObject buildVoidParameters(JSONObject voidParameters, float amount) {

        JSONObject object = new JSONObject();
        object
                .put("amount", (int) (amount * 100))
                .put("charge", voidParameters.getString("chargeId"));

        return object;
    }

    private JSONObject buildRefundParameters(JSONObject refundParameters, float amount) {

        JSONObject object = new JSONObject();
        object
                .put("amount", (int) (amount * 100))
                .put("charge", refundParameters.getString("chargeId"));

        return object;
    }

    private JSONObject buildRebillParameters(String customer, Currency currency, float amount) {

        JSONObject object = new JSONObject();
        object
                .put("amount", (int) (amount * 100))
                .put("currency", currency)
                .put("customer", customer);

        return object;
    }

    private JSONObject buildAuthorizeParameters(String customer, Currency currency, float amount) {

        JSONObject object = new JSONObject();
        object
                .put("amount", (int) (amount * 100))
                .put("currency", currency)
                .put("customer", customer)
                .put("capture", false);

        return object;

    }

    private JSONObject buildCaptureParameters(float amount) {

        JSONObject object = new JSONObject();
        object
                .put("amount", (int) (amount * 100));

        return object;
    }
}
