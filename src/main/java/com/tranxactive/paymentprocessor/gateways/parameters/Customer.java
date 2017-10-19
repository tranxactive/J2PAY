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
public class Customer {

    private String firstName;
    private String lastName;
    private String address;
    private String city;
    private String state;
    private String zip;
    private Country country;
    private String phoneNumber;
    private String email;

    /**
     * @return the firstName
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * @param firstName the firstName to set
     * @return this class object for chaining.
     */
    public Customer setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    /**
     * @return the lastName
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * @param lastName the lastName to set
     * @return this class Object for chaining
     */
    public Customer setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    /**
     * @return the address
     */
    public String getAddress() {
        return address;
    }

    /**
     * @param address the address to set
     * @return this class Object for chaining
     */
    public Customer setAddress(String address) {
        this.address = address;
        return this;
    }

    /**
     * @return the city
     */
    public String getCity() {
        return city;
    }

    /**
     * @param city the city to set
     * @return this class Object for chaining
     */
    public Customer setCity(String city) {
        this.city = city;
        return this;
    }

    /**
     * @return the state
     */
    public String getState() {
        return state;
    }

    /**
     * @param state the state to set
     * @return this class Object for chaining
     */
    public Customer setState(String state) {
        this.state = state;
        return this;
    }

    /**
     * @return the zip
     */
    public String getZip() {
        return zip;
    }

    /**
     * @param zip the zip to set
     * @return this class Object for chaining
     */
    public Customer setZip(String zip) {
        this.zip = zip;
        return this;
    }

    /**
     * @return the country
     */
    public Country getCountry() {
        return country;
    }

    /**
     * @param country the country to set
     * @return this class Object for chaining
     */
    public Customer setCountry(Country country) {
        this.country = country;
        return this;
    }

    /**
     * @return the phoneNumber
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * @param phoneNumber the phoneNumber to set
     * @return this class Object for chaining
     */
    public Customer setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * This method populates test values for this customer. Use this method for
     * test purpose only.
     *
     * @return this class Object for chaining
     */
    public Customer populateTestValues() {
        this.setFirstName("first name");
        this.setLastName("last name");
        this.setAddress("test address");
        this.setCity("test city");
        this.setState("TX");
        this.setZip("12345");
        this.setCountry(Country.US);
        this.setPhoneNumber("1254685478");
        this.setEmail("testEmail@domain.com");

        return this;
    }

}
