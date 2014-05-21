package com.goffersoft.common.utils;

import static org.junit.Assert.assertTrue;

import java.util.Arrays;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class EndianConversionTest {

    static public byte[] byteArray16Bytes;
    static public byte[] byteArray8Bytes;
    static public byte[] byteArray4Bytes;
    static public byte[] byteArray2Bytes;
    static public byte singleByte;
    static public long longValue;
    static public int intValue;
    static public short shortValue;
    static public byte byteAvalue;

    private static final Logger log = Logger
            .getLogger(EndianConversionTest.class);

    @BeforeClass
    static public void setUpClass() {
        byteArray16Bytes = new byte[16];
        byteArray8Bytes = new byte[8];
        byteArray4Bytes = new byte[4];
        byteArray2Bytes = new byte[2];
    }

    @AfterClass
    static public void tearDownClass() {
    }

    @Before
    public void setUp() {
        Arrays.fill(byteArray16Bytes, (byte) 0);
        Arrays.fill(byteArray8Bytes, (byte) 0);
        Arrays.fill(byteArray4Bytes, (byte) 0);
        Arrays.fill(byteArray4Bytes, (byte) 0);
        singleByte = 0;
        longValue = 0L;
        intValue = 0;
        shortValue = 0;
        byteAvalue = 0;
    }

    @After
    public void tearDown() {

    }

    @Test
    public void testLongToByteArray() {
        longValue = 0x1102030405060708L;
        EndianConversion.longToByteArrayLE(byteArray8Bytes, 0, longValue);

        assertTrue(byteArray8Bytes[0] == 0x08 && byteArray8Bytes[1] == 0x07
                && byteArray8Bytes[2] == 0x06 && byteArray8Bytes[3] == 0x05
                && byteArray8Bytes[4] == 0x04 && byteArray8Bytes[5] == 0x03
                && byteArray8Bytes[6] == 0x02 && byteArray8Bytes[7] == 0x11);

        EndianConversion.longToByteArrayBE(byteArray8Bytes, 0, longValue, 8);

        assertTrue(byteArray8Bytes[0] == 0x11 && byteArray8Bytes[1] == 0x02
                && byteArray8Bytes[2] == 0x03 && byteArray8Bytes[3] == 0x04
                && byteArray8Bytes[4] == 0x05 && byteArray8Bytes[5] == 0x06
                && byteArray8Bytes[6] == 0x07 && byteArray8Bytes[7] == 0x08);
    }

    @Test
    public void testIntToByteArrayLE() {
        intValue = 0x11020304;
        EndianConversion.intToByteArrayLE(byteArray4Bytes, 0, intValue);

        assertTrue(byteArray4Bytes[0] == 0x04 && byteArray4Bytes[1] == 0x03
                && byteArray4Bytes[2] == 0x02 && byteArray4Bytes[3] == 0x11);

        EndianConversion.intToByteArrayBE(byteArray4Bytes, 0, intValue);

        assertTrue(byteArray4Bytes[0] == 0x11 && byteArray4Bytes[1] == 0x02
                && byteArray4Bytes[2] == 0x03 && byteArray4Bytes[3] == 0x04);

        short y = 0xfff;
        short z = (short) (0x8000);
        long x = y & 0xffff;

        log.debug(Long.toHexString(x));
        x = z & 0xffff;
        log.debug(Long.toHexString(x));
    }
}
