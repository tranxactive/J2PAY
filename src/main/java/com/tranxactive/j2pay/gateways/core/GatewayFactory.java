/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tranxactive.j2pay.gateways.core;

import static java.lang.Class.forName;

/**
 *
 * @author ilyas
 */
public class GatewayFactory {
    
    public static Gateway getGateway(AvailableGateways availableGateways) {
        
        try {
            return (Gateway) forName(availableGateways.getPaymentGatewayClassPath()).newInstance();
        } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;     
    }

}
