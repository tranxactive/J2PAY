/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tranxactive.paymentprocessor.net;

import java.io.IOException;
import java.nio.charset.Charset;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

/**
 *
 * @author ilyas <m.ilyas@live.com>
 */
public class HTTPClient {

    /**
     * This method is the wrapper of apache http cleint get request.
     *
     * @param url The url to hit request with/without get parameters i.e
     * http://127.0.0.1/index.php?param1=val1&param2=val2
     * @return The HTTPResponse
     * @throws java.io.IOException
     * @see com.tranxactive.paymentprocessor.net.HTTPResponse
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
     * This method is the wrapper of apache http cleint post request.
     *
     * @param url The url on which the request will be hit.
     * @param postParams parameters which will be passed for post.
     * @param contentType content-type in which data will be posted.
     * @param charset the charset in which request will be posted if null is provided contentType charset will be used.
     * @return The HTTPResponse class.
     * @throws java.io.IOException
     * @see com.tranxactive.paymentprocessor.net.HTTPResponse
     * @see ContentType
     */
    public static HTTPResponse httpPost(String url, String postParams, ContentType contentType, Charset charset) throws IOException {

        HTTPResponse hTTPResponse;
        long startTime, endTime;
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpPost httpPost = new HttpPost(url);
            httpPost.setEntity(new StringEntity(postParams));
            if(charset == null){
                httpPost.addHeader("Content-Type", contentType.toString());
            }else {
                httpPost.addHeader("Content-Type", ContentType.create(contentType.getMimeType(), charset).toString());
            }

            startTime = System.currentTimeMillis();

            CloseableHttpResponse closeableHttpResponse = httpClient.execute(httpPost);
            
            endTime = System.currentTimeMillis();
            
            hTTPResponse = new HTTPResponse(closeableHttpResponse.getStatusLine().getStatusCode(), EntityUtils.toString(closeableHttpResponse.getEntity()).replaceFirst("^\uFEFF", ""), endTime - startTime);
            EntityUtils.consume(closeableHttpResponse.getEntity());
        }
        return hTTPResponse;

    }
    
    /**
     * This method is the wrapper of apache http cleint post request.
     *
     * @param url The url on which the request will be hit.
     * @param postParams parameters which will be passed for post.
     * @param contentType content-type in which data will be posted.
     * @return The HTTPResponse class.
     * @throws java.io.IOException
     * @see com.tranxactive.paymentprocessor.net.HTTPResponse
     * @see ContentType
     */
    public static HTTPResponse httpPost(String url, String postParams, ContentType contentType) throws IOException {
        return httpPost(url, postParams, contentType, null);
    
    }

}
