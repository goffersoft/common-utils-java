/**
 ** File: GenericSerializable.java
 **
 ** Description : Interface Defining methods to serialize input and output
 **
 ** Date           Author                          Comments
 ** 08/31/2013     Prakash Easwar                  Created  
 **/
package com.goffersoft.common.utils;

public interface GenericSerializable<Type>
					extends 
						GenericInputSerializable<Type>,
						GenericOutputSerializable {

}
