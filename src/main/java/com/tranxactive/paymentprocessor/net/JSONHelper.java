/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tranxactive.paymentprocessor.net;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author ilyas <m.ilyas@live.com>
 */
public class JSONHelper {

    /**
     * This method encodes JSONObject.
     *
     * @param object JSONObject to be encoded
     * @return encoded JSONObject
     */
    public static JSONObject encode(JSONObject object) {

        JSONObject finalJSONObject = new JSONObject();

        Iterator<String> keys = object.keys();

        while (keys.hasNext()) {

            String key = keys.next();

            try {
                if (object.get(key) instanceof String) {
                    finalJSONObject.put(key, URLEncoder.encode(object.getString(key), "UTF-8"));
                } else if (object.get(key) instanceof JSONObject) {
                    finalJSONObject.put(key, encode(object.getJSONObject(key)));
                } else if (object.get(key) instanceof JSONArray) {
                    finalJSONObject.put(key, encode(object.getJSONArray(key)));
                } else {
                    finalJSONObject.put(key, object.get(key));
                }
            } catch (UnsupportedEncodingException ex) {
                Logger.getLogger(JSONHelper.class.getName()).log(Level.SEVERE, null, ex);
                return null;
            }
        }

        return finalJSONObject;
    }

    /**
     * This method encodes JSONArray.
     *
     * @param arr JSONArray to be encoded
     * @return encoded JSONArray
     */
    public static JSONArray encode(JSONArray arr) {

        JSONArray finalJSONArray = new JSONArray();

        for (Object object : arr) {
            try {
                if (object instanceof String) {
                    finalJSONArray.put(URLEncoder.encode(String.valueOf(object), "UTF-8"));
                } else if (object instanceof JSONObject) {
                    finalJSONArray.put(encode((JSONObject) object));
                } else if (object instanceof JSONArray) {
                    finalJSONArray.put(encode((JSONArray) object));
                } else {
                    finalJSONArray.put(object);
                }
            } catch (UnsupportedEncodingException ex) {
                Logger.getLogger(JSONHelper.class.getName()).log(Level.SEVERE, null, ex);
                return null;
            }
        }

        return finalJSONArray;

    }

    /**
     * This method decode JSONObject.
     *
     * @param object JSONObject to be decoded
     * @return decoded JSONObject
     */
    public static JSONObject decode(JSONObject object) {

        JSONObject finalJSONObject = new JSONObject();

        Iterator<String> keys = object.keys();

        while (keys.hasNext()) {

            String key = keys.next();

            try {
                if (object.get(key) instanceof String) {
                    finalJSONObject.put(key, URLDecoder.decode(object.getString(key), "UTF-8"));
                } else if (object.get(key) instanceof JSONObject) {
                    finalJSONObject.put(key, decode(object.getJSONObject(key)));
                } else if (object.get(key) instanceof JSONArray) {
                    finalJSONObject.put(key, decode(object.getJSONArray(key)));
                } else {
                    finalJSONObject.put(key, object.get(key));
                }
            } catch (UnsupportedEncodingException ex) {
                Logger.getLogger(JSONHelper.class.getName()).log(Level.SEVERE, null, ex);
                return null;
            }
        }

        return finalJSONObject;
    }

    /**
     * This method decodes JSONArray.
     *
     * @param arr JSONAray to be decoded
     * @return decoded JSONArray
     */
    public static JSONArray decode(JSONArray arr) {

        JSONArray finalJSONArray = new JSONArray();

        for (Object object : arr) {
            try {
                if (object instanceof String) {
                    finalJSONArray.put(URLDecoder.decode(String.valueOf(object), "UTF-8"));
                } else if (object instanceof JSONObject) {
                    finalJSONArray.put(decode((JSONObject) object));
                } else if (object instanceof JSONArray) {
                    finalJSONArray.put(decode((JSONArray) object));
                } else {
                    finalJSONArray.put(object);
                }
            } catch (UnsupportedEncodingException ex) {
                Logger.getLogger(JSONHelper.class.getName()).log(Level.SEVERE, null, ex);
                return null;
            }
        }

        return finalJSONArray;

    }

}
