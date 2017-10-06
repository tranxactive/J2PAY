/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tranxactive.paymentprocessor.net;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import org.bson.Document;

/**
 * <p>
 * This class provides helper methods that could be useful while dealing with
 * http POST/GET requests.
 *
 * @author ilyas <m.ilyas@live.com>
 */
public class RequestResponseHelper {

    /**
     * <p>
     * This method converts json Document to application/x-www-form-urlencoded. Do
     * not use this method to parse nested json.
     * </p>
     * <b>example</b><br>
     * It converts the below json Object {key1:val1, key2:val2} to
     * key1=val1&key2=v2 do not forget to encode parsed parameters using encode
     * method of this class
     *
     * @param document Document Object
     * @return application/x-www-form-urlencoded representation of JsonObject
     */
    public static String toFormData(Document document) throws NullPointerException {

        if (document == null) {
            throw new NullPointerException("null JsonObject provided");
        }

        StringBuilder stringBuilder = new StringBuilder();
        document.forEach((key, val) -> {
            stringBuilder.append(key).append("=").append(val).append("&");
        });

        stringBuilder.replace(stringBuilder.length() - 1, stringBuilder.length(), "");
        return stringBuilder.toString();
    }

    /**
     * This method encodes String into application/x-www-form-urlencoded.
     *
     * @param params string to be encoded
     * @return encoded String
     * @throws java.io.UnsupportedEncodingException in case if encode failed
     */
    public static String encode(String params) throws UnsupportedEncodingException {
        return URLEncoder.encode(params, "UTF-8");
    }

    /**
     * This method decodes String into application/x-www-form-urlencoded.
     *
     * @param params string to be decoded
     * @return decoded String
     * @throws java.io.UnsupportedEncodingException in case if decode failed
     */
    public static String decode(String params) throws UnsupportedEncodingException {
        return URLDecoder.decode(params, "UTF-8");
    }
}
