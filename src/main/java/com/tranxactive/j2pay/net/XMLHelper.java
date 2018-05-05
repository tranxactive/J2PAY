/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tranxactive.j2pay.net;

import org.json.JSONObject;
import org.json.XML;

/**
 *
 * @author ilyas
 */
public class XMLHelper {

    /**
     * This method converts xml into JSONObject.
     *
     * @param xml the xml string than will be parsed.
     * @return the JSONObject json representation of xml
     */
    public static JSONObject toJson(String xml) {

        return XML.toJSONObject(xml);
    }

    /**
     * This method converts JSONObject to xml
     *
     * @param object the JSONObject that will be parsed.
     * @return the xml representation of JSONObject.
     */
    public static String fromJson(JSONObject object) {

        return XML.toString(object);
    }

}
