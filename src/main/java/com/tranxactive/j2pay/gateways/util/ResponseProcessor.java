package com.tranxactive.j2pay.gateways.util;

import com.tranxactive.j2pay.gateways.responses.CoreResponse;
import com.tranxactive.j2pay.net.HTTPResponse;
import org.json.JSONObject;

/**
 *
 * @author dwamara
 */
public class ResponseProcessor {
    public static void processFinalResponse(JSONObject gatewayResponseObject, HTTPResponse httpResponse, CoreResponse successResponse, CoreResponse errorResponse) {
        if (successResponse != null) {
            httpResponse.setSuccessful(true);
            successResponse.setGatewayResponse(gatewayResponseObject);
            httpResponse.setContent(successResponse.getResponse().toString());
        } else {
            errorResponse.setGatewayResponse(gatewayResponseObject);
            httpResponse.setContent(errorResponse.getResponse().toString());
        }
    }
}
