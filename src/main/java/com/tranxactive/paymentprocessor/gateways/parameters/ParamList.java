/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tranxactive.paymentprocessor.gateways.parameters;

/**
 *
 * @author ilyas
 */
public enum ParamList {
    
    SUCCESS("success"),
    TRANSACTION_ID("transactionId"),
    MESSAGE("message"),
    
    AMOUNT("amount"),
    
    CURRENCY_CODE("currencyCode"),
    
    CARD_EXPIRY_MONTH("cardExpiryMonth"),
    CARD_EXPIRY_YEAR("cardExpiryYear"),
    CARD_FIRST_6("cardFirst6"),
    CARD_LAST_4("cardLast4"),
    MASKED_CARD("maskedCard"),
    
    REFUND_PARAMS("refundParams"),
    VOID_PARAMS("voidParams"),
    REBILL_PARAMS("rebillParams"),
    
    LIBRARY_RESPONSE("lr"),
    GATEWAY_RESPONSE("gr")
    ;

    private final String name;
    
    private ParamList(String name) {
        this.name = name;
    }
    
    /**
     * This method returns the usable name of current parameter 
     * @return the name of this parameter
     */
    public String getName(){
        return name;
    }
    
    
    
}
