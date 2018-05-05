/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tranxactive.j2pay.net;

import java.util.Iterator;
import org.json.JSONObject;

/**
 *
 * @author ilyas
 */
public class QueryStringHelper {

    /**
     * <p>
     * This method converts JSONObject to query string. Do not use this method
     * to parse nested json.
     * </p>
     * <b>example</b><br>
     * It converts the below JSONObject {key1:val1, key2:val2} to
     * key1=val1&amp;key2=v2
     *
     * @param object the JSONObject that need to be parsed.
     * @return query string representation of JSONObject
     */
    public static String toQueryString(JSONObject object) {

        if (object == null) {
            throw new NullPointerException("null JsonObject provided");
        }

        StringBuilder stringBuilder = new StringBuilder();

        Iterator<String> keys = object.keys();

        while (keys.hasNext()) {
            String key = keys.next();
            stringBuilder.append(key).append("=").append(object.get(key)).append("&");
        }

        stringBuilder.replace(stringBuilder.length() - 1, stringBuilder.length(), "");
        return stringBuilder.toString();
    }

    /**
     * This method converts queryString to JSONObject.
     *
     * @param queryString the query string that nedd to be parsed
     * @return JSONObject the json representation of query string.
     */
    public static JSONObject toJson(String queryString) {

        String[] params = queryString.split("&");

        JSONObject jsonObject = new JSONObject();

        for (String param : params) {
            String[] p = param.split("=");
            String name = p[0];
            if (p.length > 1) {
                String value = p[1];
                jsonObject.put(name, value);
            }
        }

        return jsonObject;

    }

}
