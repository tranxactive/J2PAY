/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tranxactive.j2pay.net;

import com.tranxactive.j2pay.gateways.responses.ErrorResponse;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.HashMap;
import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpHeaders;

import static org.apache.http.entity.ContentType.create;

/**
 *
 * @author ilyas
 */
public class HTTPClient {

    /**
     * This method is the wrapper of apache http client get request.
     *
     * @param url The url to hit request with/without get parameters i.e
     * http://127.0.0.1/index.php?param1=val1&amp;param2=val2
     * @return The HTTPResponse
     * @throws java.io.IOException in case of any failure to communicating the
     * server
     * @see com.tranxactive.j2pay.net.HTTPResponse
     */
    public static HTTPResponse httpGet(String url) throws IOException {

        HTTPResponse hTTPResponse;
        long startTime, endTime;
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpGet httpGet = new HttpGet(url);
            startTime = System.currentTimeMillis();
            CloseableHttpResponse closeableHttpResponse = httpClient.execute(httpGet);
            endTime = System.currentTimeMillis();
            hTTPResponse = new HTTPResponse(closeableHttpResponse.getStatusLine().getStatusCode(), EntityUtils.toString(closeableHttpResponse.getEntity()), endTime - startTime);
            EntityUtils.consume(closeableHttpResponse.getEntity());
        }

        return hTTPResponse;

    }

    /**
     * This method is the wrapper of apache http client post request.
     *
     * @param url The url on which the request will be hit.
     * @param postParams parameters which will be passed for post.
     * @param contentType content-type in which data will be posted.
     * @param charset the charset in which request will be posted if null is
     * provided contentType charset will be used.
     * @param headers To add additional headers otherwise pass null
     * @return The HTTPResponse class.
     * @see com.tranxactive.j2pay.net.HTTPResponse
     * @see ContentType
     */
    public static HTTPResponse httpPost(String url, String postParams, ContentType contentType, Charset charset, HashMap<String, String> headers) {

        HTTPResponse hTTPResponse;

        long startTime = System.currentTimeMillis(), endTime;
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpPost httpPost = new HttpPost(url);
            httpPost.setEntity(new StringEntity(postParams));
            httpPost.addHeader("Content-Type", charset == null ? contentType.toString() : create(contentType.getMimeType(), charset).toString());

            if (headers != null) {
                headers.keySet().forEach((key) -> {
                    httpPost.addHeader(key, headers.get(key));
                });
            }

            startTime = System.currentTimeMillis();

            CloseableHttpResponse closeableHttpResponse = httpClient.execute(httpPost);

            endTime = System.currentTimeMillis();

            hTTPResponse = new HTTPResponse(closeableHttpResponse.getStatusLine().getStatusCode(), EntityUtils.toString(closeableHttpResponse.getEntity()).replaceFirst("^\uFEFF", ""), endTime - startTime);
            hTTPResponse.setRequestString(postParams);
            EntityUtils.consume(closeableHttpResponse.getEntity());
        } catch (IOException e) {
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.setMessage("could not connect to host");
            return new HTTPResponse(-1, errorResponse.getResponse().toString(), System.currentTimeMillis() - startTime);
        }
        return hTTPResponse;

    }
    
    /**
     * This method is the wrapper of apache http client post request with basic Auth.
     *
     * @param url The url on which the request will be hit.
     * @param postParams parameters which will be passed for post.
     * @param contentType content-type in which data will be posted.
     * @param charset the charset in which request will be posted if null is
     * provided contentType charset will be used.
     * @param authUserName the Auth username
     * @param authPassword the Auth passord
     * @return The HTTPResponse class.
     * @see com.tranxactive.j2pay.net.HTTPResponse
     * @see ContentType
     */
    public static HTTPResponse httpPostWithBasicAuth(String url, String postParams, ContentType contentType, Charset charset, String authUserName, String authPassword) {

        HTTPResponse hTTPResponse;

        long startTime = System.currentTimeMillis(), endTime;
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpPost httpPost = new HttpPost(url);
            httpPost.setEntity(new StringEntity(postParams));
            httpPost.addHeader("Content-Type", charset == null ? contentType.toString() : create(contentType.getMimeType(), charset).toString());

            String auth = authUserName + ":" + authPassword;
            byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(Charset.forName("US-ASCII")));
            String authHeader = "Basic " + new String(encodedAuth);
            httpPost.addHeader(HttpHeaders.AUTHORIZATION, authHeader);
            
            startTime = System.currentTimeMillis();

            CloseableHttpResponse closeableHttpResponse = httpClient.execute(httpPost);

            endTime = System.currentTimeMillis();

            hTTPResponse = new HTTPResponse(closeableHttpResponse.getStatusLine().getStatusCode(), EntityUtils.toString(closeableHttpResponse.getEntity()).replaceFirst("^\uFEFF", ""), endTime - startTime);
            hTTPResponse.setRequestString(postParams);
            EntityUtils.consume(closeableHttpResponse.getEntity());
        } catch (IOException e) {
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.setMessage("could not connect to host");
            return new HTTPResponse(-1, errorResponse.getResponse().toString(), System.currentTimeMillis() - startTime);
        }
        return hTTPResponse;

    }

    /**
     * This method is the wrapper of apache http client post request.
     *
     * @param url The url on which the request will be hit.
     * @param postParams parameters which will be passed for post.
     * @param contentType content-type in which data will be posted.
     * @return The HTTPResponse class.
     * @see com.tranxactive.j2pay.net.HTTPResponse
     * @see ContentType
     */
    public static HTTPResponse httpPost(String url, String postParams, ContentType contentType) {
        return httpPost(url, postParams, contentType, null, null);

    }

    /**
     * This method is the wrapper of apache http client post request.
     *
     * @param url The url on which the request will be hit.
     * @param postParams parameters which will be passed for post.
     * @param contentType content-type in which data will be posted.
     * @param headers To add additional headers otherwise pass null.
     * @return The HTTPResponse class.
     * @see com.tranxactive.j2pay.net.HTTPResponse
     * @see ContentType
     */
    public static HTTPResponse httpPost(String url, String postParams, ContentType contentType, HashMap<String, String> headers) {
        return httpPost(url, postParams, contentType, null, headers);

    }

}
