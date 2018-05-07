/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tranxactive.j2pay.gateways.responses;

import org.json.JSONObject;

/**
 *
 * @author ilyas
 */
public class ErrorResponse extends CoreResponse {
    public ErrorResponse() {
    }

    public ErrorResponse(String message, JSONObject gatewayResponse) {
        super(false, message, gatewayResponse);
    }
}
