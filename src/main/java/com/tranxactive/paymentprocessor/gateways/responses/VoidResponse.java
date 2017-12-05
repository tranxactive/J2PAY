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
 * @author ilyas
 */
public class VoidResponse implements Responseable {

    private final boolean success = true;
    private String message = null;

    private String transactionId = null;

    private JSONObject gatewayResponse = null;

    public VoidResponse() {
    }

    public VoidResponse(
            String message,
            String transactionId,
            JSONObject gatewayResponse
    ) {
        this.message = message;
        this.transactionId = transactionId;
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
     * @return the transactionId
     */
    public String getTransactionId() {
        return transactionId;
    }

    /**
     * @param transactionId the transactionId to set
     */
    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
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
                        .put(ParamList.TRANSACTION_ID.getName(), this.transactionId)
                )
                .put(ParamList.GATEWAY_RESPONSE.getName(), this.gatewayResponse != null ? this.gatewayResponse : JSONObject.NULL);
    }

}
