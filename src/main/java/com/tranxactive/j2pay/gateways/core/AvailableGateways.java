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

    AUTHORIZE ("com.tranxactive.j2pay.gateways.impl.authorize.AuthorizeGateway"),
    NMI       ("com.tranxactive.j2pay.gateways.impl.nmi.NMIGateway"),
    PAYEEZY   ("com.tranxactive.j2pay.gateways.impl.payeezy.PayeezyGateway"),
    PAYFLOWPRO("com.tranxactive.j2pay.gateways.impl.payflow.PayflowProGateway"),
    BILLPRO   ("com.tranxactive.j2pay.gateways.impl.billpro.BillproGateway"),
    EASYPAY   ("com.tranxactive.j2pay.gateways.impl.easypay.EasypayGateway");

    private String paymentGateWayClassPath;
    
    AvailableGateways(String classPath) {
        this.paymentGateWayClassPath = classPath;
    }

    public String getPaymentGatewayClassPath() {
        return paymentGateWayClassPath;
    }
}
