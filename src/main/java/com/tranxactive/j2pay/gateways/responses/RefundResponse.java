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
public class RefundResponse extends TransactionResponse {

    public RefundResponse() {

    }

    public RefundResponse(
            String message,
            float amount,
            String transactionId,
            JSONObject voidParams,
            JSONObject gatewayResponse
    ) {
        this.success = true;
        this.message = message;
        this.amount = amount;
        this.transactionId = transactionId;
        this.voidParams = voidParams;
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
