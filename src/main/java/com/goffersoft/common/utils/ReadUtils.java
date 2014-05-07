/**
 ** File: ReadUtils.java
 **
 ** Description : This File contains functions to read content from an 
 **               input stream and convert to a suitable format. 
 **               
 **
 ** Date           Author                          Comments
 ** 01/30/2013     Prakash Easwar                  Created  
 */

package com.goffersoft.common.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ReadUtils {

    static public String readInputAsString(InputStream is) {
        try {
            StringBuffer data = new StringBuffer();
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(is));
            String line;
            while ((line = reader.readLine()) != null) {
                data.append(line);
            }
            reader.close();
            return data.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String readInputAsString(InputStream is, int strlen)
            throws IOException {
        StringBuffer sb = new StringBuffer();

        int len = strlen;

        while (len > 0) {
            sb.append(is.read() & 0xFF);
            len--;
        }
        return sb.toString();
    }

    static public byte[] readInput(InputStream is, byte[] data, int offset,
            int length) throws IOException {
        int retval = 0;
        byte[] tmp = data;

        if (length <= 0 || offset < 0) {
            return tmp;
        }

        if ((tmp == null) || ((offset + length) > data.length)) {
            tmp = new byte[length + offset];
        }

        do {
            retval += is.read(tmp, offset + retval, length - retval);
            if (retval == -1) {
                throw new IOException("End Of Stream Reached! Expected "
                        + length + " bytes of data" + "but got only " + retval
                        + " bytes of data");
            }
        } while (retval != length);

        return tmp;
    }
}
