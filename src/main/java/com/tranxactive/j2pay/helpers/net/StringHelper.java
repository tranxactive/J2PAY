/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tranxactive.j2pay.helpers.net;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import static java.util.logging.Level.SEVERE;
import static java.util.logging.Logger.getLogger;

/**
 *
 * @author ilyas
 */
public class StringHelper {
    public static final String ENCODING_UTF8 = "UTF-8";

    private StringHelper() {
    }
    /**
     * This method encodes the string.
     * @param str the string that will be encoded.
     * @return the encoded string.
     */
    public static String encode(String str){
    
        try {
            return URLEncoder.encode(str, ENCODING_UTF8);
        } catch (UnsupportedEncodingException ex) {
            getLogger(StringHelper.class.getName()).log(SEVERE, null, ex);
            return null;
        }
    }
    
    /**
     * This method decodes the string.
     * @param str the string that will be decoded.
     * @return the decoded string.
     */
    public static String decode(String str){
    
        try {
            return URLDecoder.decode(str, ENCODING_UTF8);
        } catch (UnsupportedEncodingException ex) {
            getLogger(StringHelper.class.getName()).log(SEVERE, null, ex);
            return null;
        }
    }
    
}
