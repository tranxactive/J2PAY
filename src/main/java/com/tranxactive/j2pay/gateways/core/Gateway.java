/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tranxactive.j2pay.gateways.core;

import com.tranxactive.j2pay.gateways.parameters.Currency;
import com.tranxactive.j2pay.gateways.parameters.Customer;
import com.tranxactive.j2pay.gateways.parameters.CustomerCard;
import com.tranxactive.j2pay.net.HTTPResponse;
import org.json.JSONObject;

/**
 *
 * @author ilyas
 */
public abstract class Gateway implements GatewaySampleParameters {

    private boolean testMode = false;
    private boolean rebillSupport = true;

    public Gateway() {
    }
    
    public Gateway(boolean rebillSupport){
        this.rebillSupport = rebillSupport;
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
     * @see JSONObject
     * @see Customer
     * @see CustomerCard
     * @see HTTPResponse
     *
     */
    public abstract HTTPResponse purchase(JSONObject apiParameters, Customer customer, CustomerCard customerCard, Currency currency, float amount);

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
     * @see JSONObject
     * @see HTTPResponse
     *
     */
    public abstract HTTPResponse refund(JSONObject apiParameters, JSONObject refundParameters, float amount);

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
     * @see JSONObject
     * @see HTTPResponse
     */
    public abstract HTTPResponse rebill(JSONObject apiParameters, JSONObject rebillParameters, float amount);

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
     * @see JSONObject
     * @see HTTPResponse
     */
    public abstract HTTPResponse voidTransaction(JSONObject apiParameters, JSONObject voidParameters);

    /**
     * This method is used to check the test mode status.
     *
     * @return the testMode
     */
    public boolean isTestMode() {
        return testMode;
    }

    /**
     * To enable test mode. By default test mode is disabled most of the
     * gateways do not support test mode so this method will have no effect on
     * that gateways.
     *
     * @param testMode if true is passed test mode will be activated.
     */
    public void setTestMode(boolean testMode) {
        this.testMode = testMode;
    }

    /**
     * This method is used to check if rebill is supported by this gateway.
     * @return the rebillSupport
     */
    public boolean isRebillSupport() {
        return rebillSupport;
    }

    /**
     * This method is internally used by gateways to enable if rebill is supported.
     * @param rebillSupport the rebillSupport to set
     */
    protected void setRebillSupport(boolean rebillSupport) {
        this.rebillSupport = rebillSupport;
    }

}
