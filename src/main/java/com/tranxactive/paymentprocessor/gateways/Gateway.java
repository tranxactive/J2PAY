/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tranxactive.paymentprocessor.gateways;

import com.tranxactive.paymentprocessor.gateways.parameters.Currency;
import com.tranxactive.paymentprocessor.gateways.parameters.Customer;
import com.tranxactive.paymentprocessor.gateways.parameters.CustomerCard;
import com.tranxactive.paymentprocessor.net.HTTPResponse;
import org.bson.Document;

/**
 *
 * @author ilyas <m.ilyas@live.com>
 */
public abstract class Gateway implements GatewaySampleParameters {

    private boolean testMode;

    public Gateway() {
        this.testMode = false;
    }

    /**
     * This method Authorize and Charge the credit card in one step.
     *
     * @param apiParameters the gateway specific parameters required in all kind
     * of merchant interactions. you can get its sample by calling
     * getApiSampleParameters method of this class and then pass the same object
     * after populating values.<br>
     * @param customer the Customer information. i.e. first name, last name ,
     * zip.<br>
     * @param customerCard the Customer credit card info like card number, cvv.
     * currency in which card will be charged and amount which will be charged.
     * @param currency the currency in which amount will be charged.
     * @param amount the amount which will be charged.
     * @return the HTTPResponse class.
     *
     * @see Document
     * @see Customer
     * @see CustomerCard
     * @see HTTPResponse
     *
     */
    public abstract HTTPResponse purchase(Document apiParameters, Customer customer, CustomerCard customerCard, Currency currency, float amount);

    /**
     * This method is used to refund the previously captured amount
     * full/partial.
     *
     * @param apiParameters the gateway specific parameters required in all kind
     * of merchant interactions. you can get its sample by calling
     * getApiSampleParameters method of this class and then pass the same object
     * after populating values.<br>
     * @param refundParameters the refund parameters you can get its sample by
     * calling getRefundSampleParameters method of this class and then pass the
     * same object after populating values.<br>
     * @param amount the amount which will be refund.
     *
     * @return the HTTPResponse class.
     *
     * @see Document
     * @see HTTPResponse
     *
     */
    public abstract HTTPResponse refund(Document apiParameters, Document refundParameters, float amount);

    /**
     * This method is used to rebill the previously charged transaction.
     *
     * @param apiParameters the gateway specific parameters required in all kind
     * of merchant interactions. you can get its sample by calling
     * getApiSampleParameters method of this class and then pass the same object
     * after populating values.<br>
     *
     * @param rebillParameters the rebill parameters you can get its sample by
     * calling getRebillSampleParameters method of this class and then pass the
     * same object after populating values.<br>
     *
     * @param amount the amount which will be charged.<br>
     *
     * @return the HTTPResponse class.
     *
     * @see Document
     * @see HTTPResponse
     */
    public abstract HTTPResponse rebill(Document apiParameters, Document rebillParameters, float amount);

    /**
     * This method is used to cancel the transaction that is not yet
     * settled.<br>
     *
     * @param apiParameters the gateway specific parameters required in all kind
     * of merchant interactions. you can get its sample by calling
     * getApiSampleParameters method of this class and then pass the same object
     * after populating values.<br>
     * @param voidParameters the void parameters you can get its sample by
     * calling getVoidSampleParameters method of this class and then pass the
     * same object after populating values.<br>
     * @return the HTTPResponse class
     *
     * @see Document
     * @see HTTPResponse
     */
    public abstract HTTPResponse voidTransaction(Document apiParameters, Document voidParameters);

    /**
     * This method is used to check the test mode status.
     * 
     * @return the testMode
     */
    public boolean isTestMode() {
        return testMode;
    }

    /**
     * To enable test mode. By default test mode is disabled
     *
     * @param testMode the testMode to set
     */
    public void setTestMode(boolean testMode) {
        this.testMode = testMode;
    }

}
