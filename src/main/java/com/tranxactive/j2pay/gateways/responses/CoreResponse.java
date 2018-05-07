package com.tranxactive.j2pay.gateways.responses;

import com.tranxactive.j2pay.gateways.parameters.ParamList;
import org.json.JSONObject;

/**
 *
 * @author dwamara
 */
public class CoreResponse implements Responseable {

    protected boolean success = false;
    protected String message = null;
    protected JSONObject gatewayResponse = null;

    public CoreResponse() {
    }

    public CoreResponse(final boolean success, String message, JSONObject gatewayResponse) {
        this.success = success;
        this.message = message;
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
     * @return the success
     */
    public boolean isSuccess() {
        return success;
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
                )
                .put(ParamList.GATEWAY_RESPONSE.getName(), this.gatewayResponse != null ? this.gatewayResponse : JSONObject.NULL);
    }
}
