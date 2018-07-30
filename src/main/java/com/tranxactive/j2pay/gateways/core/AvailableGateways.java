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
public enum AvailableGateways {

    AUTHORIZE("com.tranxactive.j2pay.gateways.AuthorizeGateway"),
    NMI("com.tranxactive.j2pay.gateways.NMIGateway"),
    PAYEEZY("com.tranxactive.j2pay.gateways.PayeezyGateway"),
    PAYFLOWPRO("com.tranxactive.j2pay.gateways.PayflowProGateway"),
    BILLPRO("com.tranxactive.j2pay.gateways.BillproGateway"),
    EASYPAY("com.tranxactive.j2pay.gateways.EasypayGateway"),
    CHECKOUT("com.tranxactive.j2pay.gateways.CheckoutGateway");

    private final String paymentGateWayClassPath;
    
    AvailableGateways(String classPath) {
        this.paymentGateWayClassPath = classPath;
    }

    public String getPaymentGatewayClassPath() {
        return paymentGateWayClassPath;
    }
}
