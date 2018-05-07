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
public class RebillResponse extends CoreResponse {

    private String transactionId = null;
    private float amount = 0.0f;
    private JSONObject voidParams = null;

    private JSONObject rebillParams = null;
    private JSONObject refundParams = null;

    public RebillResponse() {
        this.success = true;
    }

    public RebillResponse(
            String message,
            float amount,
            String transactionId,
            JSONObject rebillParams,
            JSONObject voidParams,
            JSONObject refundParams,
            JSONObject gatewayResponse
    ) {
        this.success = true;
        this.message = message;
        this.amount = amount;
        this.transactionId = transactionId;
        this.rebillParams = rebillParams;
        this.voidParams = voidParams;
        this.refundParams = refundParams;
        this.gatewayResponse = gatewayResponse;
    }

    /**
     * @return the rebillParams
     */
    public JSONObject getRebillParams() {
        return rebillParams;
    }

    /**
     * @param rebillParams the rebillParams to set
     */
    public void setRebillParams(JSONObject rebillParams) {
        this.rebillParams = rebillParams;
    }

    /**
     * @return the refundParams
     */
    public JSONObject getRefundParams() {
        return refundParams;
    }

    /**
     * @param refundParams the refundParams to set
     */
    public void setRefundParams(JSONObject refundParams) {
        this.refundParams = refundParams;
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
                        .put(ParamList.REBILL_PARAMS.getName(), this.rebillParams != null ? this.rebillParams : JSONObject.NULL)
                        .put(ParamList.VOID_PARAMS.getName(), this.getVoidParams() != null ? this.getVoidParams() : JSONObject.NULL)
                        .put(ParamList.REFUND_PARAMS.getName(), this.refundParams != null ? this.refundParams : JSONObject.NULL)
                )
                .put(ParamList.GATEWAY_RESPONSE.getName(), this.gatewayResponse != null ? this.gatewayResponse : JSONObject.NULL);
    }

}
