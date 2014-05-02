package com.goffersoft.common.utils;

import static org.junit.Assert.assertTrue;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class BitUtilsTest {

    private static final Logger log = Logger.getLogger(BitUtilsTest.class);

    private long longBitmap;
    private short shortBitmap;
    private int intBitmap;
    private byte byteBitmap = 0;
    private int bitpos;

    @BeforeClass
    static public void setUpClass() {
    }

    @AfterClass
    static public void tearDownClass() {

    }

    @Before
    public void setUp() {
        longBitmap = 0;
        shortBitmap = 0;
        intBitmap = 0;
        byteBitmap = 0;
        bitpos = 0;
    }

    @After
    public void tearDown() {

    }

    @Test
    public void testSetBitLongBitmap() {
        longBitmap = BitUtils.setBit(longBitmap, bitpos);
        assertTrue(longBitmap == 0x01);

        longBitmap = BitUtils.setBit(longBitmap,
                BitUtils.BITUTILS_NUM_BITS_IN_LONG - 1);
        assertTrue(longBitmap == 0x8000000000000001L);

        longBitmap = BitUtils.setBit(longBitmap, 33);
        assertTrue(longBitmap == 0x8000000200000001L);

        bitpos = BitUtils.BITUTILS_NUM_BITS_IN_LONG;
        longBitmap = BitUtils.setBit(longBitmap, bitpos);
        assertTrue(longBitmap == 0x8000000200000001L);
    }

    @Test
    public void testSetBitIntBitmap() {
        intBitmap = BitUtils.setBit(intBitmap, bitpos);
        assertTrue(intBitmap == 0x01);

        intBitmap = BitUtils.setBit(intBitmap,
                BitUtils.BITUTILS_NUM_BITS_IN_INT - 1);
        assertTrue(intBitmap == 0x80000001);

        intBitmap = BitUtils.setBit(intBitmap, 17);
        assertTrue(intBitmap == 0x80020001);

        bitpos = BitUtils.BITUTILS_NUM_BITS_IN_INT;
        intBitmap = BitUtils.setBit(intBitmap, bitpos);
        assertTrue(intBitmap == 0x80020001);
    }

    @Test
    public void testSetBitShortBitmap() {
        shortBitmap = BitUtils.setBit(shortBitmap, bitpos);
        assertTrue(shortBitmap == 0x01);

        shortBitmap = BitUtils.setBit(shortBitmap,
                BitUtils.BITUTILS_NUM_BITS_IN_SHORT - 1);
        assertTrue(shortBitmap == (short) 0x8001);

        shortBitmap = BitUtils.setBit(shortBitmap, 9);
        assertTrue(shortBitmap == (short) 0x8201);

        bitpos = BitUtils.BITUTILS_NUM_BITS_IN_SHORT;
        shortBitmap = BitUtils.setBit(shortBitmap, bitpos);
        assertTrue(shortBitmap == (short) 0x8201);
    }

    @Test
    public void testSetBitByteBitmap() {
        byteBitmap = BitUtils.setBit(byteBitmap, bitpos);
        assertTrue(byteBitmap == 0x01);

        byteBitmap = BitUtils.setBit(byteBitmap,
                BitUtils.BITUTILS_NUM_BITS_IN_BYTE - 1);
        assertTrue(byteBitmap == (byte) 0x81);

        byteBitmap = BitUtils.setBit(byteBitmap, 5);
        assertTrue(byteBitmap == (byte) 0xa1);

        bitpos = BitUtils.BITUTILS_NUM_BITS_IN_BYTE;
        byteBitmap = BitUtils.setBit(byteBitmap, bitpos);
        assertTrue(byteBitmap == (byte) 0xa1);
    }

    @Test
    public void testClearBitLongBitmap() {
        longBitmap = 0xffffffffffffffffL;

        longBitmap = BitUtils.clearBit(longBitmap, bitpos);
        assertTrue(longBitmap == 0xfffffffffffffffeL);

        longBitmap = BitUtils.clearBit(longBitmap,
                BitUtils.BITUTILS_NUM_BITS_IN_LONG - 1);
        assertTrue(longBitmap == 0x7ffffffffffffffeL);

        longBitmap = BitUtils.clearBit(longBitmap, 33);
        assertTrue(longBitmap == 0x7ffffffdfffffffeL);

        bitpos = BitUtils.BITUTILS_NUM_BITS_IN_LONG;
        longBitmap = BitUtils.clearBit(longBitmap, bitpos);
        assertTrue(longBitmap == 0x7ffffffdfffffffeL);
    }

    @Test
    public void testClearBitIntBitmap() {
        intBitmap = 0xffffffff;

        intBitmap = BitUtils.clearBit(intBitmap, bitpos);
        assertTrue(intBitmap == 0xfffffffe);

        intBitmap = BitUtils.clearBit(intBitmap,
                BitUtils.BITUTILS_NUM_BITS_IN_INT - 1);
        assertTrue(intBitmap == 0x7ffffffe);

        intBitmap = BitUtils.clearBit(intBitmap, 17);
        assertTrue(intBitmap == 0x7ffdfffe);

        bitpos = BitUtils.BITUTILS_NUM_BITS_IN_INT;
        intBitmap = BitUtils.clearBit(intBitmap, bitpos);
        assertTrue(intBitmap == 0x7ffdfffe);
    }

    @Test
    public void testClearBitShortBitmap() {
        shortBitmap = (short) 0xffff;

        shortBitmap = BitUtils.clearBit(shortBitmap, bitpos);
        assertTrue(shortBitmap == (short) 0xfffe);

        shortBitmap = BitUtils.clearBit(shortBitmap,
                BitUtils.BITUTILS_NUM_BITS_IN_SHORT - 1);
        assertTrue(shortBitmap == (short) 0x7ffe);

        shortBitmap = BitUtils.clearBit(shortBitmap, 9);
        assertTrue(shortBitmap == (short) 0x7dfe);

        bitpos = BitUtils.BITUTILS_NUM_BITS_IN_SHORT;
        shortBitmap = BitUtils.clearBit(shortBitmap, bitpos);
        assertTrue(shortBitmap == (short) 0x7dfe);
    }

    @Test
    public void testClearBitByteBitmap() {
        byteBitmap = (byte) 0xff;

        byteBitmap = BitUtils.clearBit(byteBitmap, bitpos);
        assertTrue(byteBitmap == (byte) 0xfe);

        byteBitmap = BitUtils.clearBit(byteBitmap,
                BitUtils.BITUTILS_NUM_BITS_IN_BYTE - 1);
        assertTrue(byteBitmap == (byte) 0x7e);

        byteBitmap = BitUtils.clearBit(byteBitmap, 5);
        assertTrue(byteBitmap == (byte) 0x5e);

        bitpos = BitUtils.BITUTILS_NUM_BITS_IN_BYTE;
        byteBitmap = BitUtils.clearBit(byteBitmap, bitpos);
        assertTrue(byteBitmap == (byte) 0x5e);
    }

    @Test
    public void testFlipBitLongBitmap() {
        longBitmap = 0xffffffffffffffffL;

        longBitmap = BitUtils.flipBit(longBitmap, bitpos);
        assertTrue(longBitmap == 0xfffffffffffffffeL);

        longBitmap = BitUtils.flipBit(longBitmap, bitpos);
        assertTrue(longBitmap == 0xffffffffffffffffL);

        bitpos = BitUtils.BITUTILS_NUM_BITS_IN_LONG;
        longBitmap = BitUtils.flipBit(longBitmap, bitpos);
        assertTrue(longBitmap == 0xffffffffffffffffL);
    }

    @Test
    public void testFlipBitIntBitmap() {
        intBitmap = 0xffffffff;

        intBitmap = BitUtils.flipBit(intBitmap, bitpos);
        assertTrue(intBitmap == 0xfffffffe);

        intBitmap = BitUtils.flipBit(intBitmap, bitpos);
        assertTrue(intBitmap == 0xffffffff);

        bitpos = BitUtils.BITUTILS_NUM_BITS_IN_INT;
        intBitmap = BitUtils.clearBit(intBitmap, bitpos);
        assertTrue(intBitmap == 0xffffffff);
    }

    @Test
    public void testFlipBitShortBitmap() {
        shortBitmap = (short) 0xffff;

        shortBitmap = BitUtils.flipBit(shortBitmap, bitpos);
        assertTrue(shortBitmap == (short) 0xfffe);

        shortBitmap = BitUtils.flipBit(shortBitmap, bitpos);
        assertTrue(shortBitmap == (short) 0xffff);

        bitpos = BitUtils.BITUTILS_NUM_BITS_IN_SHORT;
        shortBitmap = BitUtils.flipBit(shortBitmap, bitpos);
        assertTrue(shortBitmap == (short) 0xffff);
    }

    @Test
    public void testFlipBitByteBitmap() {
        byteBitmap = (short) 0xffff;

        byteBitmap = BitUtils.flipBit(byteBitmap, bitpos);
        assertTrue(byteBitmap == (byte) 0xfe);

        byteBitmap = BitUtils.flipBit(byteBitmap, bitpos);
        assertTrue(byteBitmap == (byte) 0xff);

        bitpos = BitUtils.BITUTILS_NUM_BITS_IN_BYTE;
        byteBitmap = BitUtils.flipBit(byteBitmap, bitpos);
        assertTrue(byteBitmap == (byte) 0xff);
    }
}