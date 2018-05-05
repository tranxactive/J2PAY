/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tranxactive.j2pay.gateways.responses;

import com.tranxactive.j2pay.gateways.parameters.ParamList;
import org.json.JSONObject;

/**
 *
 * @author ilyas
 */
public class RefundResponse implements Responseable {

    private final boolean success = true;
    private String message = null;

    private float amount = 0.0f;

    private String transactionId = null;

    private JSONObject voidParams = null;

    private JSONObject gatewayResponse = null;

    public RefundResponse() {

    }

    public RefundResponse(
            String message,
            float amount,
            String transactionId,
            JSONObject voidParams,
            JSONObject gatewayResponse
    ) {
        this.message = message;
        this.amount = amount;
        this.transactionId = transactionId;
        this.voidParams = voidParams;
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
     * @return the amount
     */
    public float getAmount() {
        return amount;
    }

    /**
     * @param amount the amount to set
     */
    public void setAmount(float amount) {
        this.amount = amount;
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
     * @return the voidParams
     */
    public JSONObject getVoidParams() {
        return voidParams;
    }

    /**
     * @param voidParams the voidParams to set
     */
    public void setVoidParams(JSONObject voidParams) {
        this.voidParams = voidParams;
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
                        .put(ParamList.AMOUNT.getName(), this.amount)
                        .put(ParamList.TRANSACTION_ID.getName(), this.transactionId)
                        .put(ParamList.VOID_PARAMS.getName(), this.voidParams != null ? this.voidParams : JSONObject.NULL)
                )
                .put(ParamList.GATEWAY_RESPONSE.getName(), this.gatewayResponse != null ? this.gatewayResponse : JSONObject.NULL);
    }
}
