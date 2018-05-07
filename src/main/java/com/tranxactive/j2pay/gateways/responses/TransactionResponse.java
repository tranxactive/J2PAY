package com.tranxactive.j2pay.gateways.responses;

import org.json.JSONObject;

/**
 *
 * @author dwamara
 */
public class TransactionResponse extends VoidResponse {

    protected float amount = 0.0f;
    protected JSONObject voidParams = null;

    public TransactionResponse() {
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

}
