package com.tranxactive.j2pay.gateways.responses;

import com.tranxactive.j2pay.gateways.parameters.Currency;
import com.tranxactive.j2pay.gateways.parameters.ParamList;
import org.json.JSONObject;

/**
 *
 * @author dwamara
 */
public class CaptureResponse extends CoreResponse {

    private float amount = 0.0f;
    private JSONObject voidParams = null;

    private JSONObject refundParams = null;

    public CaptureResponse() {
    }

    public CaptureResponse(
            String message,
            float amount,
            Currency currencyCode,
            String cardExpiryYear,
            String cardExpiryMonth,
            String cardFirst6,
            String cardLast4,
            String maskedCard,
            String transactionId,
            JSONObject voidParams,
            JSONObject refundParams,
            JSONObject gatewayResponse
    ) {
        this.message = message;
        this.amount = amount;
        this.voidParams = voidParams;
        this.refundParams = refundParams;
        this.gatewayResponse = gatewayResponse;
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
                        .put(ParamList.REFUND_PARAMS.getName(), this.refundParams != null ? this.refundParams : JSONObject.NULL)
                )
                .put(ParamList.GATEWAY_RESPONSE.getName(), this.gatewayResponse != null ? this.gatewayResponse : JSONObject.NULL);
    }
}
