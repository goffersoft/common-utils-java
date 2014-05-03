/**
 ** File: GenericOutputSerializable.java
 **
 ** Description : Interface Defining methods to serialize output from any class type
 **
 ** Date           Author                          Comments
 ** 08/31/2013     Prakash Easwar                  Created  
 **/

package com.goffersoft.common.utils;

import java.io.IOException;
import java.io.OutputStream;

public interface GenericOutputSerializable {
	public byte[] toByteArray();
	
	public byte[] toByteArray(byte[] ba);
	
	public byte[] toByteArray(byte[] ba, int offset);
	
	public OutputStream toOutputStream(OutputStream os) throws IOException;
}
