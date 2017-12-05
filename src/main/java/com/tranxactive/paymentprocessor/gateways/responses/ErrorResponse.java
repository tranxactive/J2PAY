/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tranxactive.paymentprocessor.gateways.responses;

import com.tranxactive.paymentprocessor.gateways.parameters.ParamList;
import org.json.JSONObject;

/**
 *
 * @author ilyas <m.ilyas@live.com>
 */
public class ErrorResponse implements Responseable {

    private final boolean success = false;
    private String message = null;

    private JSONObject gatewayResponse = null;

    public ErrorResponse() {
    }

    public ErrorResponse(String message, JSONObject gatewayResponse) {
        this.message = message;
        this.gatewayResponse = gatewayResponse;

    }

    /**
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * @param message the message to set
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * @return the success
     */
    public boolean isSuccess() {
        return success;
    }

    /**
     * @return the gatewayResponse
     */
    public JSONObject getGatewayResponse() {
        return gatewayResponse;
    }

    /**
     * @param gatewayResponse the gatewayResponse to set
     */
    public void setGatewayResponse(JSONObject gatewayResponse) {
        this.gatewayResponse = gatewayResponse;
    }

    @Override
    public JSONObject getResponse() {
        return new JSONObject()
                .put(ParamList.LIBRARY_RESPONSE.getName(), new JSONObject()
                        .put(ParamList.SUCCESS.getName(), this.success)
                        .put(ParamList.MESSAGE.getName(), this.message)
                )
                .put(ParamList.GATEWAY_RESPONSE.getName(), this.gatewayResponse != null ? this.gatewayResponse : JSONObject.NULL);
    }
}
