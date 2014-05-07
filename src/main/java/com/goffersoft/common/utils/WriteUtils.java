/**
 ** File: WriteUtils.java
 **
 ** Description : This File contains functions to write content to an 
 **               output stream and convert to a suitable format. 
 **               
 **
 ** Date           Author                          Comments
 ** 01/30/2013     Prakash Easwar                  Created  
 */
package com.goffersoft.common.utils;

import java.io.IOException;
import java.io.OutputStream;

public class WriteUtils {

    static public byte[] writeOutput(OutputStream os, byte[] data, int offset,
            int length) throws IOException {
        byte[] tmp = data;

        if ((tmp == null) || ((offset + length) > data.length)) {
            tmp = new byte[length + offset];
        }

        os.write(tmp, offset, length);

        return tmp;
    }
}
