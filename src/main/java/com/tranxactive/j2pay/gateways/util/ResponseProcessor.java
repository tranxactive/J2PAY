package com.tranxactive.j2pay.gateways.util;

import com.tranxactive.j2pay.gateways.responses.CoreResponse;
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
}
