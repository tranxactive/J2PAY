package com.tranxactive.j2pay.gateways.util;

import org.json.JSONObject;

import static com.tranxactive.j2pay.gateways.parameters.ParamList.TRANSACTION_ID;

/**
 *
 * @author dwamara
 */
public class ParametersBuilder {
    public static JSONObject buildParameters(JSONObject apiParameters, JSONObject parameters, Float amount) {
        String transactionId;
        String transactionType;

        if (amount != null) {
            transactionId = parameters.getString("transactionId");
            transactionType = "refund";
        } else {
            transactionId = parameters.getString(TRANSACTION_ID.getName());
            transactionType = "void";
        }

        JSONObject object = new JSONObject();
        object
                .put("type", transactionType)
                .put("username", apiParameters.getString("username"))
                .put("password", apiParameters.getString("password"))
                .put("transactionid", transactionId);

        if (amount != null) {
            object.put("amount", Float.toString(amount));
        }

        return object;
    }

}
