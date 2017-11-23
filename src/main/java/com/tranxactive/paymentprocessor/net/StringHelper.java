/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tranxactive.paymentprocessor.net;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ilyas
 */
public class StringHelper {
    
    /**
     * This method encodes the string.
     * @param str the string that will be encoded.
     * @return the encoded string.
     */
    public static String encode(String str){
    
        try {
            return URLEncoder.encode(str, "UTF-8");
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(StringHelper.class.getName()).log(Level.SEVERE, null, ex);
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
            return URLDecoder.decode(str, "UTF-8");
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(StringHelper.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
}
