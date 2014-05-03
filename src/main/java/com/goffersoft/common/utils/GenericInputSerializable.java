/**
 ** File: GenericInputSerializable.java
 **
 ** Description : Interface Defining methods to serialize input into any class type
 **
 ** Date           Author                          Comments
 ** 08/31/2013     Prakash Easwar                  Created  
 **/

package com.goffersoft.common.utils;

import java.io.IOException;
import java.io.InputStream;

public interface GenericInputSerializable<Type> {
    public Type fromInputStream(InputStream is) throws IOException;

    public Type fromByteArray(byte[] data);

    public Type fromByteArray(byte[] data, int offset);
}
