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
public enum Country {

    US("US", "USA", "United States"),
    USA("US", "USA", "United States"),
    GB("GB", "GBR", "United Kingdom"),
    GBR("GB", "GBR", "United Kingdom")
    
    ;

    private String codeAlpha2, codeAlpha3, name;

    Country(String codeAplha2, String codeAlpha3, String fullName) {
        this.codeAlpha2 = codeAplha2;
        this.codeAlpha3 = codeAlpha3;
        this.name = fullName;
    }

    /**
     * @return the codeAlpha2
     */
    public String getCodeAlpha2() {
        return codeAlpha2;
    }

    /**
     * @param codeAlpha2 the codeAlpha2 to set
     */
    public void setCodeAlpha2(String codeAlpha2) {
        this.codeAlpha2 = codeAlpha2;
    }

    /**
     * @return the codeAlpha3
     */
    public String getCodeAlpha3() {
        return codeAlpha3;
    }

    /**
     * @param codeAlpha3 the codeAlpha3 to set
     */
    public void setCodeAlpha3(String codeAlpha3) {
        this.codeAlpha3 = codeAlpha3;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

}
