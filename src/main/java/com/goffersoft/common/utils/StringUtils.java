/**
 ** File: StringUtils.java
 **
 ** Description : This file contains String Utilitiy Functions. 
 **               
 **
 ** Date           Author                          Comments
 ** 01/30/2013     Prakash Easwar                  Created  
 */
package com.goffersoft.common.utils;

import org.apache.log4j.Logger;

public class StringUtils {

    private static final Logger log = Logger.getLogger(StringUtils.class);

    public static String byteArrayToString(byte[] byteArray) {
        return new String(byteArray);
    }

    public static String byteArrayToString(byte[] byteArray, int offset) {
        return new String(byteArray, offset, byteArray.length - offset);
    }

    public static String byteArrayToString(byte[] byteArray, int offset,
            int length) {
        return new String(byteArray, offset, length);
    }

    public static String charArrayToString(char[] charArray) {
        return new String(charArray);
    }

    public static String charArrayToString(char[] charArray, int offset) {
        return new String(charArray, offset, charArray.length - offset);
    }

    public static String charArrayToString(char[] charArray, int offset,
            int length) {
        return new String(charArray, offset, length);
    }
}
