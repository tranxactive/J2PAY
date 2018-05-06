/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tranxactive.j2pay.gateways.core;
/**
 *
 * @author ilyas
 */
public class GatewayFactory {
    
    public static Gateway getGateway(AvailableGateways availableGateways) {
        
        try {
            return (Gateway) Class.forName(availableGateways.getPaymentGatewayClassPath()).newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } 
        return null;     
    }

}
