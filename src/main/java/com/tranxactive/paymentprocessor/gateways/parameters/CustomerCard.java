/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tranxactive.paymentprocessor.gateways.parameters;

/**
 *
 * @author ilyas <m.ilyas@live.com>
 */
public class CustomerCard {

    private String name;
    private String number;
    private int cvv;
    private String expiryMonth;
    private String expiryYear;
    private Currency currency;
    private float amout;

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     * @return this class object for chaining
     */
    public CustomerCard setName(String name) {
        this.name = name;
        return this;
    }

    /**
     * @return the number
     */
    public String getNumber() {
        return number;
    }

    /**
     * @param number the number to set
     * @return this class object for chaining
     */
    public CustomerCard setNumber(String number) {
        this.number = number;
        return this;
    }

    /**
     * @return the cvv
     */
    public int getCvv() {
        return cvv;
    }

    /**
     * @param cvv the cvv to set
     * @return this class object for chaining
     */
    public CustomerCard setCvv(int cvv) {
        this.cvv = cvv;
        return this;
    }

    /**
     * @return the expiryMonth
     */
    public String getExpiryMonth() {
        return expiryMonth;
    }

    /**
     * @param expiryMonth the 2 digits expiryMonth to set i.e 01 for january
     * @return this class object for chaining
     */
    public CustomerCard setExpiryMonth(String expiryMonth) {
        this.expiryMonth = expiryMonth;
        return this;
    }

    /**
     * @return the expiryYear
     */
    public String getExpiryYear() {
        return expiryYear;
    }

    /**
     * 
     * @param expiryYear the 4 digit expiryYear to set i.e 2017
     * @return this class object for chaining
     */
    public CustomerCard setExpiryYear(String expiryYear) {
        this.expiryYear = expiryYear;
        return this;
    }

    /**
     * @return the amout
     */
    public float getAmout() {
        return amout;
    }

    /**
     * @param amout the amout to set
     * @return this class object for chaining
     */
    public CustomerCard setAmout(float amout) {
        this.amout = amout;
        return this;
    }

    /**
     * @return the currency
     */
    public Currency getCurrency() {
        return currency;
    }

    /**
     * @param currency the currency to set
     */
    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    /**
     * This method populates test values for this customer. Use this method for
     * test purpose only.
     *
     * @return
     */
    public CustomerCard populateTestValues() {
        this.setName("test cardname");
        this.setNumber("5424000000000015");
        this.setCvv(123);
        this.setExpiryMonth("12");
        this.setExpiryYear("2017");
        this.setAmout(2.24f);
        this.setCurrency(Currency.USD);

        return this;
    }
}
