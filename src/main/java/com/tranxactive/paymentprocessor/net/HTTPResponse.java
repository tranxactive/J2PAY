/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tranxactive.paymentprocessor.net;

import org.json.JSONObject;

/**
 * This class provides simplest handling of http response.
 *
 * @author ilyas
 */
public class HTTPResponse {

    private int statusCode;
    private String content;
    private String requestString;

    private long responseTime;

    private boolean successful = false;

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
     * @param content to set the content/body of http response <b>will be used by
     * http client to update the info</b>
     */
    public void setContent(String content) {
        this.content = content;
    }
    
    /**
     *
     * @return the JSON response
     */
    public JSONObject getJSONResponse(){
        return new JSONObject(this.content);
    }

    /**
     * @return the responseTime in milliseconds that http request took to
     * complete execution.
     */
    public long getResponseTime() {
        return responseTime;
    }

    /**
     * @param responseTime to set the response time in milliseconds that http
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
        return successful;
    }

    /**
     * @param successfull the successful to set
     */
    public void setSuccessful(boolean successfull) {
        this.successful = successfull;
    }

    /**
     * @return the requestString
     */
    public String getRequestString() {
        return requestString;
    }

    /**
     * @param requestString the requestString to set
     */
    public void setRequestString(String requestString) {
        this.requestString = requestString;
    }

}
