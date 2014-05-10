/**
 ** File: EndianConversion.java
 **
 ** Description : Endian Conversion Utility Class
 **
 ** Date           Author                          Comments
 ** 0MAX_BYTES_IN_SHORT/1MAX_BYTES_IN_SHORT/MAX_BYTES_IN_SHORT013     Prakash Easwar                  Created  
 */
package com.goffersoft.common.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.log4j.Logger;

public class EndianConversion {

    public static final int MAX_BITS_PER_BYTE = 8;
    public static final int MAX_BYTES_IN_LONG = Long.SIZE / MAX_BITS_PER_BYTE;
    public static final int MAX_BYTES_IN_INT = Integer.SIZE / MAX_BITS_PER_BYTE;
    public static final int MAX_BYTES_IN_SHORT = Short.SIZE / MAX_BITS_PER_BYTE;

    private static final Logger log = Logger.getLogger(EndianConversion.class);

    static private byte[] toByteArrayLE(byte[] byteArray, int offset, int len,
            int num_bytes_in_long, long value) {
        int i = 0;

        int end = Math.min(offset + num_bytes_in_long, len);

        while ((offset + i) < end) {
            byteArray[offset + i] = (byte) (value >> (i * MAX_BITS_PER_BYTE));
            i++;
        }

        return byteArray;
    }

    static private byte[] toByteArrayLE(byte[] byteArray, int offset, int len,
            int num_bytes_in_int, int value) {
        int i = 0;

        int end = Math.min(offset + num_bytes_in_int, len);

        while ((offset + i) < end) {
            byteArray[offset + i] = (byte) (value >> (i * MAX_BITS_PER_BYTE));
            i++;
        }

        return byteArray;
    }

    static private byte toByteLE(int bytePos, long value) {
        return (byte) (value >> ((bytePos) * MAX_BITS_PER_BYTE));
    }

    static private byte toByteLE(int bytePos, int value) {
        return (byte) (value >> ((bytePos) * MAX_BITS_PER_BYTE));
    }

    static private long toLongLE(byte[] byteArray, int offset, int len,
            int num_bytes_in_long) {
        long value = 0L;
        int i = 0;
        int end = Math.min(offset + num_bytes_in_long, len);

        while ((offset + i) < end) {
            value |= ((byteArray[offset + i] & 0xFF) << (i * MAX_BITS_PER_BYTE));
            i++;
        }
        return value;
    }

    static private int toIntLE(byte[] byteArray, int offset, int len,
            int num_bytes_in_int) {
        int value = 0;
        int i = 0;
        int end = Math.min(offset + num_bytes_in_int, len);

        while ((offset + i) < end) {
            value |= ((byteArray[offset + i] & 0xFF) << (i * MAX_BITS_PER_BYTE));
            i++;
        }
        return value;
    }

    static private long toLongLE(long value, byte byteVal, int bytePos) {
        value |= ((byteVal & 0xFF) << (bytePos * MAX_BITS_PER_BYTE));

        return value;
    }

    static private int toIntLE(int value, byte byteVal, int bytePos) {
        value |= ((byteVal & 0xFF) << (bytePos * MAX_BITS_PER_BYTE));

        return value;
    }

    static private byte[] toByteArrayBE(byte[] byteArray, int offset, int len,
            int num_bytes_in_long, long value) {
        int i = 0;
        int end = Math.min(offset + num_bytes_in_long, byteArray.length);

        while ((offset + i) < end) {
            byteArray[offset + i] = (byte) (value >> ((num_bytes_in_long - 1 - i) * MAX_BITS_PER_BYTE));
            i++;
        }
        return byteArray;
    }

    static private byte[] toByteArrayBE(byte[] byteArray, int offset, int len,
            int num_bytes_in_long, int value) {
        int i = 0;
        int end = Math.min(offset + num_bytes_in_long, byteArray.length);

        while ((offset + i) < end) {
            byteArray[offset + i] = (byte) (value >> ((num_bytes_in_long - 1 - i) * MAX_BITS_PER_BYTE));
            i++;
        }
        return byteArray;
    }

    static private byte toByteBE(int bytePos, int num_bytes_in_long, long value) {
        return (byte) (value >> ((num_bytes_in_long - 1 - bytePos) * MAX_BITS_PER_BYTE));
    }

    static private byte toByteBE(int bytePos, int num_bytes_in_int, int value) {
        return (byte) (value >> ((num_bytes_in_int - 1 - bytePos) * MAX_BITS_PER_BYTE));
    }

    static private long toLongBE(byte[] byteArray, int offset, int len,
            int num_bytes_in_long) {
        long value = 0L;
        int i = 0;
        int end = Math.min(offset + num_bytes_in_long, byteArray.length);

        while ((offset + i) < end) {
            value |= ((byteArray[offset + i] & 0xFF) << ((num_bytes_in_long - 1 - i) * MAX_BITS_PER_BYTE));
            i++;
        }
        return value;
    }

    static private int toIntBE(byte[] byteArray, int offset, int len,
            int num_bytes_in_long) {
        int value = 0;
        int i = 0;
        int end = Math.min(offset + num_bytes_in_long, byteArray.length);

        while ((offset + i) < end) {
            value |= ((byteArray[offset + i] & 0xFF) << ((num_bytes_in_long - 1 - i) * MAX_BITS_PER_BYTE));
            i++;
        }
        return value;
    }

    static private long toLongBE(long value, byte byteVal, int bytePos,
            int num_bytes_in_long) {
        value |= ((byteVal & 0xFF) << ((num_bytes_in_long - 1 - bytePos) * MAX_BITS_PER_BYTE));

        return value;
    }

    static private int toIntBE(int value, byte byteVal, int bytePos,
            int num_bytes_in_long) {
        value |= ((byteVal & 0xFF) << ((num_bytes_in_long - 1 - bytePos) * MAX_BITS_PER_BYTE));

        return value;
    }

    public static byte[] intToByteArrayLE(byte[] byteArray, int value) {
        if (byteArray == null) {
            return byteArray;
        }

        return toByteArrayLE(byteArray, 0, byteArray.length, MAX_BYTES_IN_INT,
                value);
    }

    public static byte[] intToByteArrayLE(byte[] byteArray, int offset,
            int value) {
        if (byteArray == null || offset < 0 || offset >= byteArray.length) {
            return byteArray;
        }

        return toByteArrayLE(byteArray, offset, byteArray.length,
                MAX_BYTES_IN_INT, value);
    }

    public static byte[] intToByteArrayLE(byte[] byteArray, int offset,
            int len, int value) {
        if (byteArray == null || len <= 0 || offset < 0) {
            return byteArray;
        }

        int tmp_len = Math.min(len, byteArray.length);

        if (offset >= tmp_len) {
            return byteArray;
        }

        return toByteArrayLE(byteArray, offset, tmp_len, MAX_BYTES_IN_INT,
                value);
    }

    public static byte[] intToByteArrayLE(byte[] byteArray, int offset,
            int len, int value, int num_bytes_in_int) {
        if (byteArray == null || len <= 0 || offset < 0
                || num_bytes_in_int <= 0) {
            return byteArray;
        }

        int tmp_len = Math.min(len, byteArray.length);

        if (offset >= tmp_len) {
            return byteArray;
        }

        return toByteArrayLE(byteArray, offset, tmp_len,
                Math.min(num_bytes_in_int, MAX_BYTES_IN_INT), value);
    }

    public static OutputStream intToOutputStreamLE(OutputStream os,
            int num_bytes_in_int, int value) throws IOException {
        if (num_bytes_in_int <= 0) {
            return os;
        }

        int num_bytes = 0;
        int num_bytes_to_write = Math.min(num_bytes_in_int, MAX_BYTES_IN_INT);

        while (num_bytes != num_bytes_to_write) {
            os.write(toByteLE(num_bytes, value));
            num_bytes++;
        }
        return os;
    }

    public static OutputStream intToOutputStreamLE(OutputStream os, int value)
            throws IOException {
        return intToOutputStreamLE(os, MAX_BYTES_IN_INT, value);

    }

    public static int byteArrayToIntLE(byte[] byteArray) {
        if (byteArray == null)
            return 0;

        return toIntLE(byteArray, 0, byteArray.length, MAX_BYTES_IN_INT);
    }

    public static int byteArrayToIntLE(byte[] byteArray, int offset) {
        if (byteArray == null || offset < 0 || offset >= byteArray.length) {
            return 0;
        }

        return toIntLE(byteArray, offset, byteArray.length, MAX_BYTES_IN_INT);
    }

    public static int byteArrayToIntLE(byte[] byteArray, int offset, int len) {
        if (byteArray == null || offset < 0 || offset >= byteArray.length
                || len <= 0) {
            return 0;
        }

        int tmp_len = Math.min(len, byteArray.length);

        if (offset >= tmp_len) {
            return 0;
        }

        return toIntLE(byteArray, offset, tmp_len, MAX_BYTES_IN_INT);
    }

    public static int inputStreamToIntLE(InputStream is, int num_bytes_in_int)
            throws IOException {
        if (num_bytes_in_int <= 0) {
            return 0;
        }

        int num_bytes_to_read = Math.min(num_bytes_in_int, MAX_BYTES_IN_INT);
        int num_bytes = 0;
        int retVal = 0;

        while (num_bytes != num_bytes_to_read) {
            byte val = (byte) is.read();
            if (val == -1) {
                throw new IOException("End Of Stream Reached! Expected "
                        + num_bytes_to_read + " bytes of data"
                        + "but got only " + num_bytes + " bytes of data");
            }
            retVal = toIntLE(retVal, val, num_bytes);
            num_bytes++;
        }

        return retVal;
    }

    public static int inputStreamToIntLE(InputStream is) throws IOException {
        return inputStreamToIntLE(is, MAX_BYTES_IN_INT);
    }

    public static byte[] shortToByteArrayLE(byte[] byteArray, short value) {
        if (byteArray == null) {
            return byteArray;
        }

        return toByteArrayLE(byteArray, 0, byteArray.length,
                MAX_BYTES_IN_SHORT, value);
    }

    public static byte[] shortToByteArrayLE(byte[] byteArray, int offset,
            short value) {
        if (byteArray == null || offset < 0 || offset >= byteArray.length) {
            return byteArray;
        }

        return toByteArrayLE(byteArray, offset, byteArray.length,
                MAX_BYTES_IN_SHORT, value);
    }

    public static byte[] shortToByteArrayLE(byte[] byteArray, int offset,
            int len, short value) {
        if (byteArray == null || offset < 0 || offset >= byteArray.length
                || len <= 0) {
            return byteArray;
        }

        int tmp_len = Math.min(len, byteArray.length);

        if (offset >= tmp_len) {
            return byteArray;
        }

        return toByteArrayLE(byteArray, offset, tmp_len, MAX_BYTES_IN_SHORT,
                value);
    }

    public static OutputStream shortToOutputStreamLE(OutputStream os,
            short value) throws IOException {
        return intToOutputStreamLE(os, value, MAX_BYTES_IN_SHORT);
    }

    public static short byteArrayToShortLE(byte[] byteArray) {
        if (byteArray == null) {
            return 0;
        }

        return (short) toIntLE(byteArray, 0, byteArray.length,
                MAX_BYTES_IN_SHORT);
    }

    public static short byteArrayToShortLE(byte[] byteArray, int offset) {
        if (byteArray == null || offset < 0 || offset >= byteArray.length) {
            return 0;
        }

        return (short) toIntLE(byteArray, offset, byteArray.length,
                MAX_BYTES_IN_SHORT);
    }

    public static short byteArrayToShortLE(byte[] byteArray, int offset, int len) {
        if (byteArray == null || offset < 0 || offset >= byteArray.length
                || len <= 0) {
            return 0;
        }

        int tmp_len = Math.min(len, byteArray.length);

        if (offset >= tmp_len) {
            return 0;
        }

        return (short) toIntLE(byteArray, offset, tmp_len, MAX_BYTES_IN_SHORT);
    }

    public static short inputStreamToShortLE(InputStream is) throws IOException {
        return (short) inputStreamToIntLE(is, MAX_BYTES_IN_SHORT);
    }

    public static byte[] longToByteArrayLE(byte[] byteArray, long value) {
        if (byteArray == null) {
            return byteArray;
        }

        return toByteArrayLE(byteArray, 0, byteArray.length, MAX_BYTES_IN_LONG,
                value);
    }

    public static byte[] longToByteArrayLE(byte[] byteArray, int offset,
            long value) {

        if (byteArray == null || offset < 0 || offset >= byteArray.length) {
            return byteArray;
        }

        return toByteArrayLE(byteArray, offset, byteArray.length,
                MAX_BYTES_IN_LONG, value);
    }

    public static byte[] longToByteArrayLE(byte[] byteArray, int offset,
            long value, int num_bytes_in_long) {
        if (byteArray == null || offset < 0 || num_bytes_in_long <= 0
                || offset >= byteArray.length) {
            return byteArray;
        }

        int num_bytes = Math.min(num_bytes_in_long, MAX_BYTES_IN_LONG);

        return toByteArrayLE(byteArray, offset, byteArray.length, num_bytes,
                value);
    }

    public static byte[] longToByteArrayLE(byte[] byteArray, int offset,
            int len, long value) {
        if (byteArray == null || offset < 0 || len <= 0) {
            return byteArray;
        }

        int tmp_len = Math.min(byteArray.length, len);

        if (offset >= tmp_len) {
            return byteArray;
        }

        return toByteArrayLE(byteArray, offset, tmp_len, MAX_BYTES_IN_LONG,
                value);
    }

    public static byte[] longToByteArrayLE(byte[] byteArray, int offset,
            int len, long value, int num_bytes_in_long) {
        if (byteArray == null || offset < 0 || len <= 0
                || num_bytes_in_long <= 0) {
            return byteArray;
        }

        int num_bytes = Math.min(num_bytes_in_long, MAX_BYTES_IN_LONG);
        int tmp_len = Math.min(byteArray.length, len);

        if (offset >= tmp_len) {
            return byteArray;
        }

        return toByteArrayLE(byteArray, offset, tmp_len, num_bytes, value);
    }

    private static OutputStream longToOutputStreamLE(OutputStream os,
            int num_bytes_in_long, long value) throws IOException {
        if (num_bytes_in_long < 0) {
            return os;
        }
        int num_bytes = 0;
        int num_bytes_to_write = Math.min(num_bytes_in_long, MAX_BYTES_IN_LONG);

        while (num_bytes != num_bytes_to_write) {
            os.write(toByteLE(num_bytes, value));
            num_bytes++;
        }
        return os;
    }

    public static OutputStream longToOutputStreamLE(OutputStream os, long value)
            throws IOException {
        return longToOutputStreamLE(os, MAX_BYTES_IN_LONG, value);

    }

    public static long byteArrayToLongLE(byte[] byteArray) {
        if (byteArray == null) {
            return 0;
        }

        return toLongLE(byteArray, 0, byteArray.length, MAX_BYTES_IN_LONG);
    }

    public static long byteArrayToLongLE(byte[] byteArray, int offset) {
        if (byteArray == null || offset < 0 || offset >= byteArray.length) {
            return 0;
        }

        return toLongLE(byteArray, offset, byteArray.length, MAX_BYTES_IN_LONG);
    }

    public static long byteArrayToLongLE(byte[] byteArray, int offset,
            int num_bytes_in_long) {
        if (byteArray == null || offset < 0 || num_bytes_in_long <= 0
                || offset >= byteArray.length) {
            return 0;
        }

        int num_bytes = Math.min(num_bytes_in_long, MAX_BYTES_IN_LONG);

        return toLongLE(byteArray, offset, byteArray.length, num_bytes);
    }

    public static long byteArrayToLongLE(byte[] byteArray, int offset, int len,
            int num_bytes_in_long) {
        if (byteArray == null || offset < 0 || num_bytes_in_long <= 0
                || len <= 0) {
            return 0;
        }
        int num_bytes = Math.min(num_bytes_in_long, MAX_BYTES_IN_LONG);

        int tmp_len = Math.min(byteArray.length, len);

        if (offset >= tmp_len) {
            return 0;
        }

        return toLongLE(byteArray, offset, tmp_len, num_bytes);
    }

    public static long inputStreamToLongLE(InputStream is) throws IOException {
        return inputStreamToLongLE(is, MAX_BYTES_IN_LONG);
    }

    public static long inputStreamToLongLE(InputStream is, int num_bytes_in_long)
            throws IOException {
        if (num_bytes_in_long <= 0) {
            return 0L;
        }
        int num_bytes_to_read = Math.min(num_bytes_in_long, MAX_BYTES_IN_LONG);
        int num_bytes = 0;
        long retVal = 0;

        do {
            byte val = (byte) is.read();
            if (val == -1) {
                throw new IOException("End Of Stream Reached! Expected "
                        + num_bytes_to_read + " bytes of data"
                        + "but got only " + num_bytes + " bytes of data");
            }
            retVal = toLongLE(retVal, val, num_bytes);
            num_bytes++;
        } while (num_bytes != num_bytes_to_read);

        return retVal;
    }

    public static byte[] intToByteArrayBE(byte[] byteArray, int value) {
        if (byteArray == null) {
            return byteArray;
        }

        return toByteArrayBE(byteArray, 0, byteArray.length, MAX_BYTES_IN_INT,
                value);
    }

    public static byte[] intToByteArrayBE(byte[] byteArray, int offset,
            int value) {
        if (byteArray == null || offset >= byteArray.length) {
            return byteArray;
        }

        return toByteArrayBE(byteArray, offset, byteArray.length,
                MAX_BYTES_IN_INT, value);
    }

    public static byte[] intToByteArrayBE(byte[] byteArray, int offset,
            int len, int value) {
        if (byteArray == null || offset < 0 || len <= 0) {
            return byteArray;
        }

        int tmp_len = Math.min(byteArray.length, len);

        if (offset >= tmp_len) {
            return byteArray;
        }

        return toByteArrayBE(byteArray, offset, tmp_len, MAX_BYTES_IN_INT,
                value);
    }

    public static byte[] intToByteArrayBE(byte[] byteArray, int offset,
            int len, int value, int num_bytes_in_int) {
        if (byteArray == null || offset < 0 || len <= 0
                || num_bytes_in_int <= 0) {
            return byteArray;
        }

        int tmp_len = Math.min(byteArray.length, len);

        if (offset >= tmp_len) {
            return byteArray;
        }

        return toByteArrayBE(byteArray, offset, tmp_len,
                Math.min(num_bytes_in_int, MAX_BYTES_IN_INT), value);
    }

    public static OutputStream intToOutputStreamBE(OutputStream os, int value,
            int num_bytes_in_int) throws IOException {
        if (num_bytes_in_int <= 0) {
            return os;
        }

        int num_bytes_to_write = Math.min(num_bytes_in_int, MAX_BYTES_IN_INT);
        int num_bytes = 0;

        do {
            os.write(toByteBE(num_bytes, num_bytes_to_write, value));
            num_bytes++;
        } while (num_bytes != num_bytes_to_write);

        return os;
    }

    public static OutputStream intToOutputStreamBE(OutputStream os, int value)
            throws IOException {
        return intToOutputStreamBE(os, value, MAX_BYTES_IN_INT);
    }

    public static int byteArrayToIntBE(byte[] byteArray) {
        if (byteArray == null) {
            return 0;
        }

        return toIntBE(byteArray, 0, byteArray.length, MAX_BYTES_IN_INT);
    }

    public static int byteArrayToIntBE(byte[] byteArray, int offset) {
        if (byteArray == null || offset < 0 || offset >= byteArray.length) {
            return 0;
        }

        return toIntBE(byteArray, offset, byteArray.length, MAX_BYTES_IN_INT);
    }

    public static int byteArrayToIntBE(byte[] byteArray, int offset, int len) {
        if (byteArray == null || len <= 0) {
            return 0;
        }

        int tmp_len = Math.min(byteArray.length, len);

        if (offset >= tmp_len) {
            return 0;
        }

        return toIntBE(byteArray, offset, tmp_len, MAX_BYTES_IN_INT);
    }

    public static int inputStreamToIntBE(InputStream is, int num_bytes_in_int)
            throws IOException {
        if (num_bytes_in_int <= 0) {
            return 0;
        }
        int num_bytes_to_read = Math.min(num_bytes_in_int, MAX_BYTES_IN_INT);
        int num_bytes = 0;
        int retVal = 0;

        do {
            byte val = (byte) is.read();
            if (val == -1) {
                throw new IOException("End Of Stream Reached! Expected "
                        + num_bytes_to_read + " bytes of data"
                        + "but got only " + num_bytes + " bytes of data");
            }
            retVal = toIntBE(retVal, val, num_bytes, num_bytes_to_read);
            num_bytes++;
        } while (num_bytes != num_bytes_to_read);

        return retVal;
    }

    public static int inputStreamToIntBE(InputStream is) throws IOException {
        return inputStreamToIntBE(is, MAX_BYTES_IN_INT);
    }

    public static byte[] shortToByteArrayBE(byte[] byteArray, short value) {
        if (byteArray == null) {
            return byteArray;
        }

        return toByteArrayBE(byteArray, 0, byteArray.length,
                MAX_BYTES_IN_SHORT, value);
    }

    public static byte[] shortToByteArrayBE(byte[] byteArray, int offset,
            short value) {
        if (byteArray == null || offset < 0 || offset >= byteArray.length) {
            return byteArray;
        }

        return toByteArrayBE(byteArray, offset, byteArray.length,
                MAX_BYTES_IN_SHORT, value);
    }

    public static byte[] shortToByteArrayBE(byte[] byteArray, int offset,
            int len, short value) {
        if (byteArray == null || offset < 0 || offset >= byteArray.length
                || len <= 0) {
            return byteArray;
        }

        int tmp_len = Math.min(len, byteArray.length);

        if (offset >= tmp_len) {
            return byteArray;
        }

        return toByteArrayBE(byteArray, offset, tmp_len, MAX_BYTES_IN_SHORT,
                value);
    }

    public static OutputStream shortToOutputStreamBE(OutputStream os,
            short value) throws IOException {
        int num_bytes_to_write = MAX_BYTES_IN_SHORT;
        int num_bytes = 0;

        do {
            os.write(toByteBE(num_bytes, num_bytes_to_write, value));
            num_bytes++;
        } while (num_bytes != num_bytes_to_write);

        return os;
    }

    public static short byteArrayToShortBE(byte[] byteArray, int offset) {
        if (byteArray == null || offset < 0 || offset >= byteArray.length) {
            return 0;
        }

        return (short) toIntBE(byteArray, offset, byteArray.length,
                MAX_BYTES_IN_SHORT);
    }

    public static short byteArrayToShortBE(byte[] byteArray, int offset, int len) {
        if (byteArray == null || offset < 0 || len <= 0) {
            return 0;
        }

        int tmp_len = Math.min(byteArray.length, len);

        if (offset >= tmp_len) {
            return 0;
        }

        return (short) toIntBE(byteArray, offset, tmp_len, MAX_BYTES_IN_SHORT);
    }

    public static short inputStreamToShortBE(InputStream is) throws IOException {
        int num_bytes_to_read = MAX_BYTES_IN_SHORT;
        int num_bytes = 0;
        int retVal = 0;

        do {
            byte val = (byte) is.read();
            if (val == -1) {
                throw new IOException("End Of Stream Reached! Expected "
                        + num_bytes_to_read + " bytes of data"
                        + "but got only " + num_bytes + " bytes of data");
            }
            retVal = toIntBE(retVal, val, num_bytes, num_bytes_to_read);
            num_bytes++;
        } while (num_bytes != num_bytes_to_read);

        return (short) retVal;
    }

    public static byte[] longToByteArrayBE(byte[] byteArray, long value) {
        if (byteArray == null) {
            return byteArray;
        }

        return toByteArrayBE(byteArray, 0, byteArray.length, MAX_BYTES_IN_LONG,
                value);
    }

    public static byte[] longToByteArrayBE(byte[] byteArray, int offset,
            long value) {
        if (byteArray == null || offset < 0 || offset >= byteArray.length) {
            return byteArray;
        }

        return toByteArrayBE(byteArray, offset, byteArray.length,
                MAX_BYTES_IN_LONG, value);
    }

    public static byte[] longToByteArrayBE(byte[] byteArray, int offset,
            long value, int num_bytes_in_long) {
        if (byteArray == null || offset < 0 || offset >= byteArray.length
                || num_bytes_in_long <= 0) {
            return byteArray;
        }

        return toByteArrayBE(byteArray, offset, byteArray.length,
                Math.min(num_bytes_in_long, MAX_BYTES_IN_LONG), value);
    }

    public static byte[] longToByteArrayBE(byte[] byteArray, int offset,
            int len, long value) {
        if (byteArray == null || offset < 0 || len <= 0) {
            return byteArray;
        }

        int tmp_len = Math.min(len, byteArray.length);

        if (offset >= tmp_len) {
            return byteArray;
        }

        return toByteArrayBE(byteArray, offset, tmp_len, MAX_BYTES_IN_LONG,
                value);
    }

    public static byte[] longToByteArrayBE(byte[] byteArray, int offset,
            int len, long value, int num_bytes_in_long) {
        if (byteArray == null || offset < 0 || len <= 0
                || num_bytes_in_long <= 0) {
            return byteArray;
        }

        int tmp_len = Math.min(len, byteArray.length);

        if (offset >= tmp_len) {
            return byteArray;
        }

        return toByteArrayBE(byteArray, offset, tmp_len,
                Math.min(num_bytes_in_long, MAX_BYTES_IN_LONG), value);
    }

    public static OutputStream longToOutputStreamBE(OutputStream os,
            long value, int num_bytes_in_long) throws IOException {
        if (num_bytes_in_long <= 0) {
            return os;
        }
        int num_bytes_to_write = Math.min(num_bytes_in_long, MAX_BYTES_IN_LONG);
        int num_bytes = 0;

        do {
            os.write(toByteBE(num_bytes, num_bytes_to_write, value));
            num_bytes++;
        } while (num_bytes != num_bytes_to_write);

        return os;
    }

    public static OutputStream longToOutputStreamBE(OutputStream os, long value)
            throws IOException {
        return longToOutputStreamBE(os, value, MAX_BYTES_IN_LONG);
    }

    public static long byteArrayToLongBE(byte[] byteArray) {
        if (byteArray == null)
            return 0;

        return toLongBE(byteArray, 0, byteArray.length, MAX_BYTES_IN_LONG);
    }

    public static long byteArrayToLongBE(byte[] byteArray, int offset) {
        if (byteArray == null || offset < 0 || offset >= byteArray.length)
            return 0L;

        return toLongBE(byteArray, offset, byteArray.length, MAX_BYTES_IN_LONG);
    }

    public static long byteArrayToLongBE(byte[] byteArray, int offset,
            int num_bytes_in_long) {
        if (byteArray == null || offset < 0 || offset >= byteArray.length
                || num_bytes_in_long <= 0)
            return 0L;

        return toLongBE(byteArray, offset, byteArray.length,
                Math.min(num_bytes_in_long, MAX_BYTES_IN_LONG));
    }

    public static long byteArrayToLongBE(byte[] byteArray, int offset, int len,
            int num_bytes_in_long) {
        if (byteArray == null || offset < 0 || len <= 0
                || num_bytes_in_long <= 0)
            return 0L;

        int tmp_len = Math.min(len, byteArray.length);

        if (offset >= tmp_len)
            return 0;

        return toLongBE(byteArray, offset, tmp_len,
                Math.min(num_bytes_in_long, MAX_BYTES_IN_LONG));
    }

    public static long inputStreamToLongBE(InputStream is, int num_bytes_in_long)
            throws IOException {
        if (num_bytes_in_long <= 0) {
            return 0L;
        }
        int num_bytes_to_read = Math.min(num_bytes_in_long, MAX_BYTES_IN_LONG);
        int num_bytes = 0;
        long retVal = 0;

        do {
            byte val = (byte) is.read();
            if (val == -1) {
                throw new IOException("End Of Stream Reached! Expected "
                        + num_bytes_to_read + " bytes of data"
                        + "but got only " + num_bytes + " bytes of data");
            }
            retVal = toLongBE(retVal, val, num_bytes, num_bytes_to_read);
            num_bytes++;
        } while (num_bytes != num_bytes_to_read);

        return retVal;
    }

    public static long inputStreamToLongBE(InputStream is) throws IOException {
        return inputStreamToLongBE(is, MAX_BYTES_IN_LONG);
    }
}
