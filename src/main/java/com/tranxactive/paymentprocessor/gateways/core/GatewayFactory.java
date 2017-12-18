/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tranxactive.paymentprocessor.gateways.core;

import com.tranxactive.paymentprocessor.gateways.AuthorizeGateway;
import com.tranxactive.paymentprocessor.gateways.NMIGateway;
import com.tranxactive.paymentprocessor.gateways.PayeezyGateway;
import com.tranxactive.paymentprocessor.gateways.PayflowProGateway;

/**
 *
 * @author ilyas
 */
public class GatewayFactory {

    public static Gateway getGateway(AvailableGateways availableGateways) {

        switch (availableGateways) {
            case AUTHORIZE:
                return new AuthorizeGateway();
            case NMI:
                return new NMIGateway();                
            case PAYEEZY:
                return new PayeezyGateway();
            case PAYFLOWPRO:
                return new PayflowProGateway();
            default:
                return null;
        }        
    }

}
