/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tranxactive.paymentprocessor.gateways.responses;

import org.json.JSONObject;

/**
 *
 * @author ilyas
 */
public interface Responseable {

    public abstract JSONObject getResponse();

}
