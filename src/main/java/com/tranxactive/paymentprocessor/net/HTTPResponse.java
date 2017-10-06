/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tranxactive.paymentprocessor.net;

/**
 * This class provides simplest handling of http response.
 *
 * @author ilyas <m.ilyas@live.com>
 */
public class HTTPResponse {

    private int statusCode;
    private String content;

    private long responseTime;

    private boolean successfull = true;

    public HTTPResponse() {
    }

    /**
     * <p>
     * This class is the wrapper for http response. HTTPClient request method
     * will return this class`s object.
     *
     * @param statusCode the staus code which is returned by http request.
     * @param content the complete body returned by http request.
     * @param responseTime the response time that http request took to
     * completely execute.
     */
    public HTTPResponse(int statusCode, String content, long responseTime) {
        this.statusCode = statusCode;
        this.content = content;
        this.responseTime = responseTime;
    }

    /**
     * @return the http request status code i.e. 200, 500
     */
    public int getStatusCode() {
        return statusCode;
    }

    /**
     * @param statusCode to set status code <b>will be used by http client to
     * update the info</b>
     */
    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    /**
     * @return the content/body of http response
     */
    public String getContent() {
        return content;
    }

    /**
     * @param content to set the content/body of http reponse <b>will be used by
     * http client to update the info</b>
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * @return the responseTime in milliseconds that http request took to
     * complete execution.
     */
    public long getResponseTime() {
        return responseTime;
    }

    /**
     * @param responseTime to set the reponse time in milliseconds that http
     * request took to complete execution.
     */
    public void setResponseTime(long responseTime) {
        this.responseTime = responseTime;
    }

    /**
     *
     * @return the status of Response.
     */
    public boolean isSuccessful() {
        return successfull;
    }

    /**
     * @param successfull the successfull to set
     */
    public void setSuccessful(boolean successfull) {
        this.successfull = successfull;
    }

}
