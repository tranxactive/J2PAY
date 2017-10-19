/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tranxactive.paymentprocessor.net;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Map;
import java.util.Set;
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
     * This method converts json Document to application/x-www-form-urlencoded.
     * Do not use this method to parse nested json.
     * </p>
     * <b>example</b><br>
     * It converts the below json Object {key1:val1, key2:val2} to
     * key1=val1&key2=v2
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
     * This method converts queryString to json Document.
     *
     * @param queryString the query string that nedd to be parsed
     * @return Document object the json representation of data
     */
    public static Document toJson(String queryString) {

        String[] params = queryString.split("&");
        Document document = new Document();

        for (String param : params) {
            String[] p = param.split("=");
            String name = p[0];
            if (p.length > 1) {
                String value = p[1];
                document.append(name, value);
            }
        }

        return document;

    }

    /**
     * This method encodes String into application/x-www-form-urlencoded.
     *
     * @param params Document to be encoded
     * @return encoded Document
     * @throws java.io.UnsupportedEncodingException in case if encode failed
     */
    public static Document encode(Document params) throws UnsupportedEncodingException {

        Document finalDocument = new Document();

        Set<Map.Entry<String, Object>> entrySet = params.entrySet();

        for (Map.Entry<String, Object> entry : entrySet) {
            finalDocument.append(entry.getKey(), entry.getValue() instanceof Document ? encode((Document) entry.getValue()) : URLEncoder.encode(String.valueOf(entry.getValue()), "UTF-8"));
        }

        return finalDocument;
    }

    /**
     * This method decodes String into application/x-www-form-urlencoded.
     *
     * @param params Document to be decoded
     * @return decoded Document
     * @throws java.io.UnsupportedEncodingException in case if decode failed
     */
    public static Document decode(Document params) throws UnsupportedEncodingException {

        Document finalDocument = new Document();

        Set<Map.Entry<String, Object>> entrySet = params.entrySet();

        for (Map.Entry<String, Object> entry : entrySet) {
            finalDocument.append(entry.getKey(), entry.getValue() instanceof Document ? decode((Document) entry.getValue()) : URLDecoder.decode((String) entry.getValue(), "UTF-8"));
        }

        return finalDocument;
    }
}
