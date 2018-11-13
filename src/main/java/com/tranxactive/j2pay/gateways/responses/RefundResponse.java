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
public class RefundResponse extends CoreResponse {

    private float amount = 0.0f;
    private JSONObject voidParams = null;

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

    @Override
    public JSONObject getResponse() {
        return new JSONObject()
                .put(ParamList.LIBRARY_RESPONSE.getName(), new JSONObject()
                        .put(ParamList.SUCCESS.getName(), this.success)
                        .put(ParamList.MESSAGE.getName(), this.message)
                        .put(ParamList.AMOUNT.getName(), this.getAmount())
                        .put(ParamList.TRANSACTION_ID.getName(), this.getTransactionId())
                        .put(ParamList.VOID_PARAMS.getName(), this.getVoidParams() != null ? this.getVoidParams() : JSONObject.NULL)
                )
                .put(ParamList.GATEWAY_RESPONSE.getName(), this.gatewayResponse != null ? this.gatewayResponse : JSONObject.NULL);
    }
}
