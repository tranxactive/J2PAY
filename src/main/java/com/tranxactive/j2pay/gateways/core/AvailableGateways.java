/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tranxactive.j2pay.gateways.core;

import com.tranxactive.j2pay.gateways.AuthorizeGateway;

import com.tranxactive.j2pay.gateways.NMIGateway;

import com.tranxactive.j2pay.gateways.PayeezyGateway;

import com.tranxactive.j2pay.gateways.PayflowProGateway;

import com.tranxactive.j2pay.gateways.BillproGateway;

import com.tranxactive.j2pay.gateways.EasypayGateway;

import com.tranxactive.j2pay.gateways.CheckoutGateway;




/**
 *
 * @author ilyas
 */
public enum AvailableGateways {

    AUTHORIZE(AuthorizeGateway.class.getName()),
    NMI(NMIGateway.class.getName()),
    PAYEEZY(PayeezyGateway.class.getName()),
    PAYFLOWPRO(PayflowProGateway.class.getName()),
    BILLPRO(BillproGateway.class.getName()),
    EASYPAY(EasypayGateway.class.getName()),
    CHECKOUT(CheckoutGateway.class.getName());

    private final String paymentGateWayClassPath;
    
    AvailableGateways(String classPath) {
        this.paymentGateWayClassPath = classPath;
    }

    public String getPaymentGatewayClassPath() {
        return paymentGateWayClassPath;
    }
}
