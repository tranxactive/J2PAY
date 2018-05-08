/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tranxactive.j2pay.net;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Iterator;

import static com.tranxactive.j2pay.gateways.parameters.Constants.ENCODING_UTF8;
import static java.util.logging.Level.SEVERE;
import static java.util.logging.Logger.getLogger;

/**
 *
 * @author ilyas
 */
public class JSONHelper {

    private JSONHelper(){
        
    }

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
                    finalJSONObject.put(key, URLEncoder.encode(object.getString(key), ENCODING_UTF8));
                } else if (object.get(key) instanceof JSONObject) {
                    finalJSONObject.put(key, encode(object.getJSONObject(key)));
                } else if (object.get(key) instanceof JSONArray) {
                    finalJSONObject.put(key, encode(object.getJSONArray(key)));
                } else {
                    finalJSONObject.put(key, object.get(key));
                }
            } catch (UnsupportedEncodingException ex) {
                getLogger(JSONHelper.class.getName()).log(SEVERE, null, ex);
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
                    finalJSONArray.put(URLEncoder.encode(String.valueOf(object), ENCODING_UTF8));
                } else if (object instanceof JSONObject) {
                    finalJSONArray.put(encode((JSONObject) object));
                } else if (object instanceof JSONArray) {
                    finalJSONArray.put(encode((JSONArray) object));
                } else {
                    finalJSONArray.put(object);
                }
            } catch (UnsupportedEncodingException ex) {
                getLogger(JSONHelper.class.getName()).log(SEVERE, null, ex);
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
                    finalJSONObject.put(key, URLDecoder.decode(object.getString(key), ENCODING_UTF8));
                } else if (object.get(key) instanceof JSONObject) {
                    finalJSONObject.put(key, decode(object.getJSONObject(key)));
                } else if (object.get(key) instanceof JSONArray) {
                    finalJSONObject.put(key, decode(object.getJSONArray(key)));
                } else {
                    finalJSONObject.put(key, object.get(key));
                }
            } catch (UnsupportedEncodingException ex) {
                getLogger(JSONHelper.class.getName()).log(SEVERE, null, ex);
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
                    finalJSONArray.put(URLDecoder.decode(String.valueOf(object), ENCODING_UTF8));
                } else if (object instanceof JSONObject) {
                    finalJSONArray.put(decode((JSONObject) object));
                } else if (object instanceof JSONArray) {
                    finalJSONArray.put(decode((JSONArray) object));
                } else {
                    finalJSONArray.put(object);
                }
            } catch (UnsupportedEncodingException ex) {
                getLogger(JSONHelper.class.getName()).log(SEVERE, null, ex);
                return null;
            }
        }

        return finalJSONArray;

    }

}
