/**
 ** File: EndianConversion.java
 **
 ** Description : Endian Conversion Utility Class
 **
 ** Date           Author                          Comments
 ** 02/12/2013     Prakash Easwar                  Created  
 */

package com.goffersoft.common.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.apache.log4j.Logger;

public class EndianConversion {
	
	private static final Logger log = Logger.getLogger(EndianConversion.class);
	
	static private byte[] toByteArrayLE(byte[] byteArray, int offset, 
								int len, int num_bytes_in_long, long value) {
		int i = 0;

		int end =  Math.min(offset+num_bytes_in_long, len);

		while( (offset + i) < end) {
			byteArray[offset+i] = (byte)(value>> (i*8));
			i++;
		}
		
		return byteArray;
	}
	
	static private byte toByteLE(int bytePos, long value) {
		return (byte)(value>>((bytePos)*8));
	}
	
	static private long toLongLE(byte[] byteArray, int offset, 
						int len, int num_bytes_in_long) {
		long value = 0;
		int i = 0;
		int end =  Math.min(offset+num_bytes_in_long, len);
		
		while( (offset + i) < end) {
			value |= ((byteArray[offset + i]&0xFF)<<(i*8));
			i++;
		}
		return value;
	}
	
	static private long toLongLE(long longVal, byte byteVal, int bytePos) {
		longVal |= ( (byteVal&0xFF) << (bytePos*8));
			
		return longVal;
	}
	
	static private byte[] toByteArrayBE(byte[] byteArray, int offset, 
								int len, int num_bytes_in_long, long value) {
		int i = 0;
		int end =  Math.min(offset+num_bytes_in_long, byteArray.length);
		
		while( (offset + i) < end) {
			byteArray[offset+i] = (byte)(value>>((num_bytes_in_long - 1 - i)*8));
			i++;
		}
		return byteArray;
	}
	
	static private byte toByteBE(int bytePos, int num_bytes_in_long, long value) {
		return (byte)(value>>((num_bytes_in_long - 1 - bytePos)*8));
	}
	
	static private long toLongBE(byte[] byteArray, int offset, 
						int len, int num_bytes_in_long) {
		long value = 0;
		int i = 0;
		int end =  Math.min(offset+num_bytes_in_long, byteArray.length);
		
		while( (offset + i) < end) {
			value |= ((byteArray[offset + i]&0xFF)<<((num_bytes_in_long - 1 - i)*8));
			i++;
		}
		return value;
	}
	
	static private long toLongBE(long longVal, byte byteVal, int bytePos, int num_bytes_in_long) {
		longVal |= ( (byteVal&0xFF) << ((num_bytes_in_long - 1 - bytePos)*8));
			
		return longVal;
	}

	public static byte[] intToByteArrayLE(byte[] byteArray, int offset, int value) {
		if(byteArray == null)
			return byteArray;
		
		if(offset >= byteArray.length)
			return byteArray;
	
		return toByteArrayLE(byteArray, offset, byteArray.length, 4, value);
	}
	
	public static byte[] intToByteArrayLE(byte[] byteArray, int offset, int len, int value) {
		if(byteArray == null)
			return byteArray;
		
		if((len <= 0) || 
			(len > byteArray.length))
			return byteArray;
		
		if(offset >= len)
			return byteArray;
	
		return toByteArrayLE(byteArray, offset, len, 4, value);
	}
	
	public static OutputStream intToOutputStreamLE(OutputStream os, long value) throws IOException{
		int num_bytes_to_write = 4;
		int num_bytes = 0;
		
		do {
			os.write(toByteLE(num_bytes, value));
			num_bytes++;
		} while(num_bytes != num_bytes_to_write);
		
		return os;
	}
	
	public static int byteArrayToIntLE(byte[] byteArray, int offset) {
		if(byteArray == null)
			return 0;
		
		if(offset >= byteArray.length )
			return 0;
		
		return (int)toLongLE(byteArray, offset, byteArray.length, 4);
	}

	public static int byteArrayToIntLE(byte[] byteArray, int offset, int len) {
		if(byteArray == null)
			return 0;
		
		if((len <= 0) || 
			(len > byteArray.length))
			return 0;
		
		if(offset >= len)
			return 0;
		
		return (int)toLongLE(byteArray, offset, len, 4);
	}
	
	public static int inputStreamToIntLE(InputStream is) throws IOException {
		int num_bytes_expected = 4;
		int num_bytes = 0;
		long retVal = 0;
		
		do {
			byte val = (byte)is.read();
			if(val == -1) {
				throw new IOException("End Of Stream Reached! Expected " + 
										num_bytes_expected + " bytes of data" + 
										"but got only " + num_bytes + " bytes of data" );
			}
			retVal = toLongLE(retVal, val, num_bytes);
			num_bytes++;
		} while(num_bytes != num_bytes_expected);
		
		return (int)retVal;
	}
	
	public static byte[] shortToByteArrayLE(byte[] byteArray, int offset, short value) {
		if(byteArray == null)
			return byteArray;
		
		if(offset >= byteArray.length )
			return byteArray;
		
		return toByteArrayLE(byteArray, offset, byteArray.length, 2, value);
	}
	
	public static byte[] shortToByteArrayLE(byte[] byteArray, int offset, int len, short value) {
		if(byteArray == null)
			return byteArray;
		
		if((len <= 0) || 
			(len > byteArray.length))
			return byteArray;
		
		if(offset >= len)
			return byteArray;
		
		return toByteArrayLE(byteArray, offset, len, 2, value);
	}
	
	public static OutputStream shortToOutputStreamLE(OutputStream os, long value) throws IOException{
		int num_bytes_to_write = 2;
		int num_bytes = 0;
		
		do {
			os.write(toByteLE(num_bytes, value));
			num_bytes++;
		} while(num_bytes != num_bytes_to_write);
		
		return os;
	}

	public static short byteArrayToShortLE(byte[] byteArray, int offset) {
		if(byteArray == null)
			return 0;
		
		if( offset >= byteArray.length )
			return 0;

		return (short)toLongLE(byteArray, offset, byteArray.length, 2);
	}
	
	public static short byteArrayToShortLE(byte[] byteArray, int offset, int len) {
		if(byteArray == null)
			return 0;
		
		if((len <= 0) || 
			(len > byteArray.length))
			return 0;
		
		if( offset >= len)
			return 0;

		return (short)toLongLE(byteArray, offset, len, 2);
	}
	
	public static short inputStreamToShortLE(InputStream is) throws IOException {
		int num_bytes_expected = 2;
		int num_bytes = 0;
		long retVal = 0;
		
		do {
			byte val = (byte)is.read();
			if(val == -1) {
				throw new IOException("End Of Stream Reached! Expected " + 
										num_bytes_expected + " bytes of data" + 
										"but got only " + num_bytes + " bytes of data" );
			}
			retVal = toLongLE(retVal, val, num_bytes);
			num_bytes++;
		} while(num_bytes != num_bytes_expected);
		
		return (short)retVal;
	}
	
	public static byte[] longToByteArrayLE(byte[] byteArray, int offset, 
											long value, int num_bytes_in_long) {
		int num_bytes = Math.min(num_bytes_in_long, 8);

		if(byteArray == null)
			return byteArray;
		
		if(num_bytes < 1)
			return byteArray;
		
		if(offset >= byteArray.length)
			return byteArray;
		
		return toByteArrayLE(byteArray, offset, byteArray.length, num_bytes, value);
	}
	
	public static byte[] longToByteArrayLE(byte[] byteArray, int offset, int len,
											long value, int num_bytes_in_long) {
		int num_bytes = Math.min(num_bytes_in_long, 8);

		if(byteArray == null)
			return byteArray;
		
		if((len <= 0) || 
			(len > byteArray.length))
			return byteArray;
		
		if(num_bytes < 1)
			return byteArray;
		
		if(offset >= len)
			return byteArray;
		
		return toByteArrayLE(byteArray, offset, len, num_bytes, value);
	}
	
	public static OutputStream longToOutputStreamLE(OutputStream os, long value, int num_bytes_in_long) throws IOException{
		int num_bytes_to_write = Math.min(num_bytes_in_long, 8);
		int num_bytes = 0;
		
		do {
			os.write(toByteLE(num_bytes, value));
			num_bytes++;
		} while(num_bytes != num_bytes_to_write);
		
		return os;
	}
	
	public static long byteArrayToLongLE(byte[] byteArray, 
										int offset, int num_bytes_in_long) {
		int num_bytes = Math.min(num_bytes_in_long, 8);
		
		if(byteArray == null)
			return 0;
		
		if(num_bytes < 1)
			return 0;
		
		if(offset >= byteArray.length )
			return 0;
		
		return toLongLE(byteArray, offset, byteArray.length, num_bytes);
	}
	
	public static long byteArrayToLongLE(byte[] byteArray, 
								int offset, int len, int num_bytes_in_long) {
		int num_bytes = Math.min(num_bytes_in_long, 8);
		
		if(byteArray == null)
			return 0;
		
		if((len <= 0) || 
			(len > byteArray.length))
			return 0;
		
		if(num_bytes < 1)
			return 0;
		
		if(offset >= len)
			return 0;
		
		return toLongLE(byteArray, offset, len, num_bytes);
	}
	
	public static long inputStreamToLongLE(InputStream is, int num_bytes_in_long) throws IOException {
		int num_bytes_expected = Math.min(num_bytes_in_long, 8);
		int num_bytes = 0;
		long retVal = 0;
		
		do {
			byte val = (byte)is.read();
			if(val == -1) {
				throw new IOException("End Of Stream Reached! Expected " + 
										num_bytes_expected + " bytes of data" + 
										"but got only " + num_bytes + " bytes of data" );
			}
			retVal = toLongLE(retVal, val, num_bytes);
			num_bytes++;
		} while(num_bytes != num_bytes_expected);
		
		return retVal;
	}
	
	public static byte[] intToByteArrayBE(byte[] byteArray, int offset, int value) {
		if(byteArray == null)
			return byteArray;

		if(offset >= byteArray.length)
			return byteArray;

		return toByteArrayBE(byteArray, offset, byteArray.length, 4, value);
	}
	
	public static byte[] intToByteArrayBE(byte[] byteArray, int offset, 
										int len, int value) {
		if(byteArray == null)
			return byteArray;

		if((len <= 0) || 
			(len > byteArray.length))
			return byteArray;
		
		if(offset >= len)
			return byteArray;

		return toByteArrayBE(byteArray, offset, len, 4, value);
	}
	
	public static OutputStream intToOutputStreamBE(OutputStream os, int value) throws IOException {
		int num_bytes_to_write = 4;
		int num_bytes = 0;
		
		do {
			os.write(toByteBE(num_bytes, num_bytes_to_write, value));
			num_bytes++;
		} while(num_bytes != num_bytes_to_write);
		
		return os;
	}
	
	public static int byteArrayToIntBE(byte[] byteArray, int offset) {
		if(byteArray == null)
			return 0;

		if(offset >= byteArray.length)
			return 0;

		return (int)toLongBE(byteArray, offset, byteArray.length, 4);
	}
	
	public static int byteArrayToIntBE(byte[] byteArray, int offset, int len) {
		if(byteArray == null)
			return 0;

		if((len <= 0) || 
			(len > byteArray.length))
			return 0;
		
		if(offset >= len)
			return 0;

		return (int)toLongBE(byteArray, offset, len, 4);
	}
	
	public static int inputStreamToIntBE(InputStream is) throws IOException {
		int num_bytes_expected = 4;
		int num_bytes = 0;
		long retVal = 0;
		
		do {
			byte val = (byte)is.read();
			if(val == -1) {
				throw new IOException("End Of Stream Reached! Expected " + 
										num_bytes_expected + " bytes of data" + 
										"but got only " + num_bytes + " bytes of data" );
			}
			retVal = toLongBE(retVal, val, num_bytes, num_bytes_expected);
			num_bytes++;
		} while(num_bytes != num_bytes_expected);
		
		return (int)retVal;
	}
	
	public static byte[] shortToByteArrayBE(byte[] byteArray, int offset, short value) {
		if(byteArray == null) 
			return byteArray;

		if(offset >= byteArray.length)
			return byteArray;
		
		return toByteArrayBE(byteArray, offset, byteArray.length, 2, value);
	}
	
	public static byte[] shortToByteArrayBE(byte[] byteArray, int offset, int len, short value) {
		if(byteArray == null) 
			return byteArray;

		if((len <= 0) || 
			(len > byteArray.length))
			return byteArray;
		
		if(offset >= len)
			return byteArray;
		
		return toByteArrayBE(byteArray, offset, len, 2, value);
	}
	
	public static OutputStream shortToOutputStreamBE(OutputStream os, short value) throws IOException {
		int num_bytes_to_write = 2;
		int num_bytes = 0;
		
		do {
			os.write(toByteBE(num_bytes, num_bytes_to_write, value));
			num_bytes++;
		} while(num_bytes != num_bytes_to_write);
		
		return os;
	}
	
	public static short byteArrayToShortBE(byte[] byteArray, int offset) {
	
		if(byteArray == null)
			return 0;

		if(offset >= byteArray.length )
			return 0;

		return (short)toLongBE(byteArray, offset, byteArray.length, 2);
	}
	
	public static short byteArrayToShortBE(byte[] byteArray, int offset, int len) {
	
		if(byteArray == null)
			return 0;

		if((len <= 0) || 
			(len > byteArray.length))
			return 0;
		
		if(offset >= len)
			return 0;

		return (short)toLongBE(byteArray, offset, len, 2);
	}
	
	public static short inputStreamToShortBE(InputStream is) throws IOException {
		int num_bytes_expected = 2;
		int num_bytes = 0;
		long retVal = 0;
		
		do {
			byte val = (byte)is.read();
			if(val == -1) {
				throw new IOException("End Of Stream Reached! Expected " + 
										num_bytes_expected + " bytes of data" + 
										"but got only " + num_bytes + " bytes of data" );
			}
			retVal = toLongBE(retVal, val, num_bytes, num_bytes_expected);
			num_bytes++;
		} while(num_bytes != num_bytes_expected);
		
		return (short)retVal;
	}
	
	public static byte[] longToByteArrayBE(byte[] byteArray, int offset, 
							long value, int num_bytes_in_long) {
		
		int num_bytes = Math.min(num_bytes_in_long, 8);

		if(byteArray == null)
			return byteArray;

		if(num_bytes < 1)
			return byteArray;
		
		if(offset >= byteArray.length)
			return byteArray;
		
		return toByteArrayBE(byteArray, offset, byteArray.length, num_bytes, value);
	}
	
	public static byte[] longToByteArrayBE(byte[] byteArray, int offset, int len,
							long value, int num_bytes_in_long) {
		
		int num_bytes = Math.min(num_bytes_in_long, 8);

		if(byteArray == null)
			return byteArray;

		if((len <= 0) || 
			(len > byteArray.length))
			return byteArray;
		
		if(num_bytes < 1)
			return byteArray;
		
		if(offset >= len)
			return byteArray;
		
		return toByteArrayBE(byteArray, offset, len, num_bytes, value);
	}
	
	public static OutputStream longToOutputStreamBE(OutputStream os, long value, int num_bytes_in_long) throws IOException{
		int num_bytes_to_write = Math.min(num_bytes_in_long, 8);
		int num_bytes = 0;
		
		do {
			os.write(toByteBE(num_bytes, num_bytes_to_write, value));
			num_bytes++;
		} while(num_bytes != num_bytes_to_write);
		
		return os;
	}
	
	public static long byteArrayToLongBE(byte[] byteArray, 
									int offset, int num_bytes_in_long) {
		int num_bytes = Math.min(num_bytes_in_long, 8);

		if(byteArray == null)
			return 0;

		if(num_bytes < 1)
			return 0;
		
		if(offset >= byteArray.length)
			return 0;

		return toLongBE(byteArray, offset, byteArray.length, num_bytes);
	}
	
	public static long byteArrayToLongBE(byte[] byteArray, 
									int offset, int len, int num_bytes_in_long) {
		int num_bytes = Math.min(num_bytes_in_long, 8);

		if(byteArray == null)
			return 0;

		if((len <= 0) || 
			(len > byteArray.length))
			return 0;
		
		if(num_bytes < 1)
			return 0;
		
		if(offset >= len)
			return 0;

		return toLongBE(byteArray, offset, len, num_bytes);
	}
	
	public static long inputStreamToLongBE(InputStream is, int num_bytes_in_long) throws IOException {
		int num_bytes_expected = Math.min(num_bytes_in_long, 8);
		int num_bytes = 0;
		long retVal = 0;
		
		do {
			byte val = (byte)is.read();
			if(val == -1) {
				throw new IOException("End Of Stream Reached! Expected " + 
										num_bytes_expected + " bytes of data" + 
										"but got only " + num_bytes + " bytes of data" );
			}
			retVal = toLongBE(retVal, val, num_bytes, num_bytes_expected);
			num_bytes++;
		} while(num_bytes != num_bytes_expected);
		
		return retVal;
	}
}
