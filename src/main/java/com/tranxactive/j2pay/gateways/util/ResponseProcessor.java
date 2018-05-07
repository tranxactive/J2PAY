package com.tranxactive.j2pay.gateways.util;

import com.tranxactive.j2pay.gateways.parameters.ParamList;
import com.tranxactive.j2pay.gateways.responses.CoreResponse;
import com.tranxactive.j2pay.gateways.responses.TransactionResponse;
import com.tranxactive.j2pay.net.HTTPResponse;
import org.json.JSONObject;

/**
 *
 * @author dwamara
 */
public class ResponseProcessor {
    public static void processFinalResponse(JSONObject responseObject, HTTPResponse httpResponse, CoreResponse successResponse, CoreResponse errorResponse) {
        if (successResponse != null) {
            successResponse.setGatewayResponse(responseObject);
            httpResponse.setContent(successResponse.getResponse().toString());
        } else {
            errorResponse.setGatewayResponse(responseObject);
            httpResponse.setContent(errorResponse.getResponse().toString());
        }
    }

    public static void processResponse(JSONObject responseObject, HTTPResponse httpResponse, TransactionResponse successResponse, float amount) {
        httpResponse.setSuccessful(true);

        successResponse.setMessage(responseObject.getString("responsetext"));
        successResponse.setTransactionId(responseObject.get("transactionid").toString());
        successResponse.setAmount(amount);

        successResponse.setVoidParams(new JSONObject()
                .put(ParamList.TRANSACTION_ID.getName(), responseObject.get("transactionid").toString())
        );
    }
}
