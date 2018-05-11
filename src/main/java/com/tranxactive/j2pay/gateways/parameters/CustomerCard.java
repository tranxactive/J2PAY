/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tranxactive.j2pay.gateways.parameters;

/**
 *
 * @author ilyas
 */
public class CustomerCard {

    private String name;
    private String number;
    private String cvv;
    private String expiryMonth;
    private String expiryYear;

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
    public String getCvv() {
        return cvv;
    }

    /**
     * @param cvv the cvv to set
     * @return this class object for chaining
     */
    public CustomerCard setCvv(String cvv) {
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
     * This method populates test values for this customer. Use this method for
     * test purpose only.
     *
     * @return this class object for chaining
     */
    public CustomerCard populateTestValues() {
        this.setName("test cardname");
        this.setNumber("5424000000000015");
        this.setCvv("123");
        this.setExpiryMonth("12");
        this.setExpiryYear("2017");

        return this;
    }
    
    /**
     *
     * @return first 6 digits of card.
     */
    public String getFirst6(){
        
        return this.getNumber().substring(0, 6);
    }
    
    /**
     *
     * @return last 4 digits of card.
     */
    public String getLast4(){
        
        return this.getNumber().substring(this.getNumber().length() - 4, this.getNumber().length());
    }
    
    /**
     *
     * @return masked card number
     */
    public String getMaskedCard(){
        
        StringBuilder maskedCard = new StringBuilder();
        maskedCard.append(this.getFirst6());
        
        for (int i = 0; i < this.getNumber().length()-10; i++) {
            maskedCard.append("*");
        }
        
        maskedCard.append(this.getLast4());
        
        return maskedCard.toString();
        
    }
}
