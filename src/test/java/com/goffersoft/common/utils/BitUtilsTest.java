/**
 ** File: BitUtilsTest.java
 **
 ** Description : Test Cases For BitUtils.java
 **
 ** Date           Author                          Comments
 ** 02/12/2014     Prakash Easwar                  Created  
 */
package com.goffersoft.common.utils;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class BitUtilsTest {

    private static final Logger log = Logger.getLogger(BitUtilsTest.class);

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private long longBitmap;
    private short shortBitmap;
    private int intBitmap;
    private byte byteBitmap;
    private long longBitmap1;
    private short shortBitmap1;
    private int intBitmap1;
    private byte byteBitmap1;
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

        longBitmap = BitUtils.setAllBits(longBitmap);
        assertTrue(longBitmap == 0xffffffffffffffffL);
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

        intBitmap = BitUtils.setAllBits(intBitmap);
        assertTrue(intBitmap == 0xffffffff);
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

        shortBitmap = BitUtils.setAllBits(shortBitmap);
        assertTrue(shortBitmap == (short) 0xffff);
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

        byteBitmap = BitUtils.setAllBits(byteBitmap);
        assertTrue(byteBitmap == (byte) 0xffff);
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

        longBitmap = BitUtils.clearAllBits(longBitmap);
        assertTrue(longBitmap == 0x0L);
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

        intBitmap = BitUtils.clearAllBits(intBitmap);
        assertTrue(intBitmap == 0x0);
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

        shortBitmap = BitUtils.clearAllBits(shortBitmap);
        assertTrue(shortBitmap == (short) 0x0);
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

        shortBitmap = BitUtils.clearAllBits(shortBitmap);
        assertTrue(shortBitmap == (byte) 0x0);
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

        longBitmap = 0x1010101010101010L;
        longBitmap = BitUtils.flipAllBits(longBitmap);
        assertTrue(longBitmap == 0xefefefefefefefefL);
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

        intBitmap = 0x10101010;
        intBitmap = BitUtils.flipAllBits(intBitmap);
        assertTrue(intBitmap == 0xefefefef);
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

        shortBitmap = 0x1010;
        shortBitmap = BitUtils.flipAllBits(shortBitmap);
        assertTrue(shortBitmap == (short) 0xefef);
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

        byteBitmap = 0x10;
        byteBitmap = BitUtils.flipAllBits(byteBitmap);
        assertTrue(byteBitmap == (byte) 0xef);
    }

    @Test
    public void testIsBitSetLongBitmap() {
        longBitmap = 0x700000000L;
        assertTrue(BitUtils.isBitSet(longBitmap, 32));
        assertFalse(BitUtils.isBitSet(longBitmap, 35));
    }

    @Test
    public void testIsBitSetIntBitmap() {
        intBitmap = 0x70000;

        assertTrue(BitUtils.isBitSet(intBitmap, 16));
        assertFalse(BitUtils.isBitSet(intBitmap, 19));
    }

    @Test
    public void testIsBitSetShortBitmap() {
        shortBitmap = 0x700;

        assertTrue(BitUtils.isBitSet(shortBitmap, 8));
        assertFalse(BitUtils.isBitSet(shortBitmap, 11));
    }

    @Test
    public void testIsBitSetByteBitmap() {
        byteBitmap = 0x70;

        assertTrue(BitUtils.isBitSet(byteBitmap, 4));
        assertFalse(BitUtils.isBitSet(byteBitmap, 7));
    }

    @Test
    public void testNumBitsSetLongBitmap() {
        longBitmap = 0x1010101010101010L;
        assertTrue(BitUtils.getNumBitsSet(longBitmap) == 8);
        assertTrue(BitUtils.getNumBitsSet(longBitmap, 40) == 5);
        assertTrue(BitUtils.getNumBitsSet(longBitmap,
                BitUtils.BITUTILS_NUM_BITS_IN_LONG + 100) == 8);
    }

    @Test
    public void testNumBitsSetIntBitmap() {
        intBitmap = 0x10101010;
        assertTrue(BitUtils.getNumBitsSet(intBitmap) == 4);
        assertTrue(BitUtils.getNumBitsSet(intBitmap, 20) == 2);
        assertTrue(BitUtils.getNumBitsSet(intBitmap,
                BitUtils.BITUTILS_NUM_BITS_IN_INT + 40) == 4);
    }

    @Test
    public void testNumBitsSetShortBitmap() {
        shortBitmap = 0x0101;
        assertTrue(BitUtils.getNumBitsSet(shortBitmap) == 2);
        assertTrue(BitUtils.getNumBitsSet(shortBitmap, 8) == 1);
        assertTrue(BitUtils.getNumBitsSet(shortBitmap,
                BitUtils.BITUTILS_NUM_BITS_IN_SHORT + 10) == 2);
    }

    @Test
    public void testNumBitsSetByteBitmap() {
        byteBitmap = 0x73;
        assertTrue(BitUtils.getNumBitsSet(byteBitmap) == 5);
        assertTrue(BitUtils.getNumBitsSet(byteBitmap, 5) == 3);
        assertTrue(BitUtils.getNumBitsSet(byteBitmap,
                BitUtils.BITUTILS_NUM_BITS_IN_BYTE + 10) == 5);
    }

    @Test
    public void testGetBitPosLongBitmap() {
        longBitmap = 0x8000000000000000L;
        assertTrue(BitUtils.getBitPos(longBitmap) == BitUtils.BITUTILS_NUM_BITS_IN_LONG - 1);
        assertTrue(BitUtils.getBitPos(longBitmap, 40) == -1);
    }

    @Test
    public void testGetBitPosIntBitmap() {
        intBitmap = 0x80000000;
        assertTrue(BitUtils.getBitPos(intBitmap) == BitUtils.BITUTILS_NUM_BITS_IN_INT - 1);
        assertTrue(BitUtils.getBitPos(intBitmap, 16) == -1);
    }

    @Test
    public void testGetBitPosShortBitmap() {
        shortBitmap = (short) 0x8000;
        assertTrue(BitUtils.getBitPos(shortBitmap) == BitUtils.BITUTILS_NUM_BITS_IN_SHORT - 1);
        assertTrue(BitUtils.getBitPos(shortBitmap, 8) == -1);
    }

    @Test
    public void testGetBitPosByteBitmap() {
        byteBitmap = (byte) 0x80;
        assertTrue(BitUtils.getBitPos(byteBitmap) == BitUtils.BITUTILS_NUM_BITS_IN_BYTE - 1);
        assertTrue(BitUtils.getBitPos(byteBitmap, 4) == -1);
    }

    @Test
    public void testBitmapIteratorLongBitmap() {

        int[] var = new int[2];

        BitUtils.BitmapIterator it = new BitUtils.BitmapIterator() {
            public void executeIfBitIsSet(int bitpos, Object var) {
                int[] tmp = (int[]) var;
                tmp[0]++;
            }

            public void executeIfBitIsNotSet(int bitpos, Object var) {
                int[] tmp = (int[]) var;
                tmp[1]++;
            }
        };

        longBitmap = 0x1010101010101010L;

        BitUtils.bitmapIterator(longBitmap, it, var);

        assertTrue(var[0] == 8);
        assertTrue(var[1] == 56);

        var[0] = 0;
        var[1] = 0;
        BitUtils.bitmapIterator(longBitmap, 40, it, var);
        assertTrue(var[0] == 5);
        assertTrue(var[1] == 35);

        var[0] = 0;
        var[1] = 0;
        BitUtils.bitmapIterator(longBitmap, 9, 40, it, var);
        assertTrue(var[0] == 4);
        assertTrue(var[1] == 28);

    }

    @Test
    public void testBitmapIteratorIntBitmap() {

        int[] var = new int[2];

        BitUtils.BitmapIterator it = new BitUtils.BitmapIterator() {
            public void executeIfBitIsSet(int bitpos, Object var) {
                int[] tmp = (int[]) var;
                tmp[0]++;
            }

            public void executeIfBitIsNotSet(int bitpos, Object var) {
                int[] tmp = (int[]) var;
                tmp[1]++;
            }
        };

        intBitmap = 0x10101010;

        BitUtils.bitmapIterator(intBitmap, it, var);
        assertTrue(var[0] == 4);
        assertTrue(var[1] == 28);

        var[0] = 0;
        var[1] = 0;
        BitUtils.bitmapIterator(intBitmap, 19, it, var);
        assertTrue(var[0] == 2);
        assertTrue(var[1] == 17);

        var[0] = 0;
        var[1] = 0;
        BitUtils.bitmapIterator(intBitmap, 3, 27, it, var);
        assertTrue(var[0] == 3);
        assertTrue(var[1] == 22);
    }

    @Test
    public void testBitmapIteratorShortBitmap() {

        int[] var = new int[2];

        BitUtils.BitmapIterator it = new BitUtils.BitmapIterator() {
            public void executeIfBitIsSet(int bitpos, Object var) {
                int[] tmp = (int[]) var;
                tmp[0]++;
            }

            public void executeIfBitIsNotSet(int bitpos, Object var) {
                int[] tmp = (int[]) var;
                tmp[1]++;
            }
        };

        shortBitmap = 0x1010;

        BitUtils.bitmapIterator(shortBitmap, it, var);
        assertTrue(var[0] == 2);
        assertTrue(var[1] == 14);

        var[0] = 0;
        var[1] = 0;
        BitUtils.bitmapIterator(shortBitmap, 13, it, var);
        assertTrue(var[0] == 2);
        assertTrue(var[1] == 11);

        var[0] = 0;
        var[1] = 0;
        BitUtils.bitmapIterator(shortBitmap, 5, 13, it, var);
        assertTrue(var[0] == 1);
        assertTrue(var[1] == 8);
    }

    @Test
    public void testBitmapIteratorByteBitmap() {

        int[] var = new int[2];

        BitUtils.BitmapIterator it = new BitUtils.BitmapIterator() {
            public void executeIfBitIsSet(int bitpos, Object var) {
                int[] tmp = (int[]) var;
                tmp[0]++;
            }

            public void executeIfBitIsNotSet(int bitpos, Object var) {
                int[] tmp = (int[]) var;
                tmp[1]++;
            }
        };

        byteBitmap = 0x70;

        BitUtils.bitmapIterator(byteBitmap, it, var);
        assertTrue(var[0] == 3);
        assertTrue(var[1] == 5);

        var[0] = 0;
        var[1] = 0;
        BitUtils.bitmapIterator(byteBitmap, 6, it, var);
        assertTrue(var[0] == 2);
        assertTrue(var[1] == 4);

        var[0] = 0;
        var[1] = 0;
        BitUtils.bitmapIterator(byteBitmap, 2, 7, it, var);
        assertTrue(var[0] == 3);
        assertTrue(var[1] == 3);
    }

    @Test()
    public void testLongBitmapIteratorLowerMaxBitsException() {
        thrown.expect(IllegalArgumentException.class);
        BitUtils.bitmapIterator(longBitmap, -1, null, null);
    }

    @Test()
    public void testLongBitmapIteratorUpperMaxBitsException() {
        thrown.expect(IllegalArgumentException.class);
        BitUtils.bitmapIterator(longBitmap, 65, null, null);
    }

    @Test()
    public void testLongBitmapIteratorLowerRangeException() {
        thrown.expect(IllegalArgumentException.class);
        BitUtils.bitmapIterator(longBitmap, -1, 20, null, null);
    }

    @Test()
    public void testLongBitmapIteratorUpperRangeException() {
        thrown.expect(IllegalArgumentException.class);
        BitUtils.bitmapIterator(longBitmap, 20, 64, null, null);
    }

    @Test()
    public void testIntBitmapIteratorLowerMaxBitsException() {
        thrown.expect(IllegalArgumentException.class);
        BitUtils.bitmapIterator(intBitmap, -1, null, null);
    }

    @Test()
    public void testIntBitmapIteratorUpperMaxBitsException() {
        thrown.expect(IllegalArgumentException.class);
        BitUtils.bitmapIterator(intBitmap, 33, null, null);
    }

    @Test()
    public void testIntBitmapIteratorLowerRangeException() {
        thrown.expect(IllegalArgumentException.class);
        BitUtils.bitmapIterator(intBitmap, -1, 20, null, null);
    }

    @Test()
    public void testIntBitmapIteratorUpperRangeException() {
        thrown.expect(IllegalArgumentException.class);
        BitUtils.bitmapIterator(intBitmap, 20, 32, null, null);
    }

    @Test()
    public void testShortBitmapIteratorLowerMaxBitsException() {
        thrown.expect(IllegalArgumentException.class);
        BitUtils.bitmapIterator(shortBitmap, -1, null, null);
    }

    @Test()
    public void testShortBitmapIteratorUpperMaxBitsException() {
        thrown.expect(IllegalArgumentException.class);
        BitUtils.bitmapIterator(shortBitmap, 17, null, null);
    }

    @Test()
    public void testShortBitmapIteratorLowerRangeException() {
        thrown.expect(IllegalArgumentException.class);
        BitUtils.bitmapIterator(shortBitmap, -1, 16, null, null);
    }

    @Test()
    public void testShortBitmapIteratorUpperRangeException() {
        thrown.expect(IllegalArgumentException.class);
        BitUtils.bitmapIterator(shortBitmap, 0, 16, null, null);
    }

    @Test()
    public void testByteBitmapIteratorLowerMaxBitsException() {
        thrown.expect(IllegalArgumentException.class);
        BitUtils.bitmapIterator(byteBitmap, -1, null, null);
    }

    @Test()
    public void testByteBitmapIteratorUpperMaxBitsException() {
        thrown.expect(IllegalArgumentException.class);
        BitUtils.bitmapIterator(byteBitmap, 9, null, null);
    }

    @Test()
    public void testByteBitmapIteratorLowerRangeException() {
        thrown.expect(IllegalArgumentException.class);
        BitUtils.bitmapIterator(byteBitmap, -1, 8, null, null);
    }

    @Test()
    public void testByteBitmapIteratorUpperRangeException() {
        thrown.expect(IllegalArgumentException.class);
        BitUtils.bitmapIterator(byteBitmap, 0, 8, null, null);
    }

    @Test
    public void testBitwiseAndLongBitmap() {
        longBitmap = 0x9010101011001100L;
        longBitmap1 = 0x0111010110101010L;

        assertTrue(BitUtils.bitwiseAnd(longBitmap, longBitmap1) == 0x0010000010001000L);
        assertTrue(BitUtils.bitwiseAnd(longBitmap, longBitmap1, 33) == 0x9010101010001000L);
        assertTrue(BitUtils.bitwiseAnd(longBitmap, longBitmap1, 9, 60) == 0x8010000010001100L);
    }

    @Test()
    public void testLongBitwiseAndLowerMaxBitsException() {
        thrown.expect(IllegalArgumentException.class);
        BitUtils.bitwiseAnd(longBitmap, longBitmap1, -1);
    }

    @Test()
    public void testLongBitwiseAndUpperMaxBitsException() {
        thrown.expect(IllegalArgumentException.class);
        BitUtils.bitwiseAnd(longBitmap, longBitmap1, 65);
    }

    @Test()
    public void testLongBitwiseAndLowerRangeException() {
        thrown.expect(IllegalArgumentException.class);
        BitUtils.bitwiseAnd(longBitmap, longBitmap1, -1, 20);
    }

    @Test()
    public void testLongBitwiseAndUpperRangeException() {
        thrown.expect(IllegalArgumentException.class);
        BitUtils.bitwiseAnd(longBitmap, longBitmap1, 0, 64);
    }

    @Test
    public void testBitwiseAndIntBitmap() {
        intBitmap = 0x90101010;
        intBitmap1 = 0x01110101;

        assertTrue(BitUtils.bitwiseAnd(intBitmap, intBitmap1) == 0x00100000);
        assertTrue(BitUtils.bitwiseAnd(intBitmap, intBitmap1, 23) == 0x90100000);
        assertTrue(BitUtils.bitwiseAnd(intBitmap, intBitmap1, 9, 28) == 0x80100010);
    }

    @Test()
    public void testIntBitwiseAndLowerMaxBitsException() {
        thrown.expect(IllegalArgumentException.class);
        BitUtils.bitwiseAnd(intBitmap, intBitmap1, -1);
    }

    @Test()
    public void testIntBitwiseAndUpperMaxBitsException() {
        thrown.expect(IllegalArgumentException.class);
        BitUtils.bitwiseAnd(intBitmap, intBitmap1, 33);
    }

    @Test()
    public void testIntBitwiseAndLowerRangeException() {
        thrown.expect(IllegalArgumentException.class);
        BitUtils.bitwiseAnd(intBitmap, intBitmap1, -1, 31);
    }

    @Test()
    public void testIntBitwiseAndUpperRangeException() {
        thrown.expect(IllegalArgumentException.class);
        BitUtils.bitwiseAnd(intBitmap, intBitmap1, 0, 32);
    }

    @Test
    public void testBitwiseAndShortBitmap() {
        shortBitmap = (short) 0x9109;
        shortBitmap1 = (short) 0x0111;

        assertTrue(BitUtils.bitwiseAnd(shortBitmap, shortBitmap1) == (short) 0x0101);
        assertTrue(BitUtils.bitwiseAnd(shortBitmap, shortBitmap1, 12) == (short) 0x9101);
        assertTrue(BitUtils.bitwiseAnd(shortBitmap, shortBitmap1, 3, 12) == (short) 0x8101);
    }

    @Test()
    public void testShortBitwiseAndLowerMaxBitsException() {
        thrown.expect(IllegalArgumentException.class);
        BitUtils.bitwiseAnd(shortBitmap, shortBitmap1, -1);
    }

    @Test()
    public void testShortBitwiseAndUpperMaxBitsException() {
        thrown.expect(IllegalArgumentException.class);
        BitUtils.bitwiseAnd(shortBitmap, shortBitmap1, 17);
    }

    @Test()
    public void testShortBitwiseAndLowerRangeException() {
        thrown.expect(IllegalArgumentException.class);
        BitUtils.bitwiseAnd(shortBitmap, shortBitmap1, -1, 8);
    }

    @Test()
    public void testShortBitwiseAndUpperRangeException() {
        thrown.expect(IllegalArgumentException.class);
        BitUtils.bitwiseAnd(shortBitmap, shortBitmap1, 0, 16);
    }

    @Test
    public void testBitwiseAndByteBitmap() {
        byteBitmap = (byte) 0xff;
        byteBitmap1 = (byte) 0x24;

        assertTrue(BitUtils.bitwiseAnd(byteBitmap, byteBitmap1) == (byte) 0x24);
        assertTrue(BitUtils.bitwiseAnd(byteBitmap, byteBitmap1, 5) == (byte) 0xe4);
        assertTrue(BitUtils.bitwiseAnd(byteBitmap, byteBitmap1, 3, 5) == (byte) 0xe7);
    }

    @Test()
    public void testByteBitwiseAndLowerMaxBitsException() {
        thrown.expect(IllegalArgumentException.class);
        BitUtils.bitwiseAnd(byteBitmap, byteBitmap1, -1);
    }

    @Test()
    public void testByteBitwiseAndUpperMaxBitsException() {
        thrown.expect(IllegalArgumentException.class);
        BitUtils.bitwiseAnd(byteBitmap, byteBitmap1, 9);
    }

    @Test()
    public void testByteBitwiseAndLowerRangeException() {
        thrown.expect(IllegalArgumentException.class);
        BitUtils.bitwiseAnd(byteBitmap, byteBitmap1, -1, 8);
    }

    @Test()
    public void testByteBitwiseAndUpperRangeException() {
        thrown.expect(IllegalArgumentException.class);
        BitUtils.bitwiseAnd(byteBitmap, byteBitmap1, 0, 8);
    }

    @Test
    public void testBitwiseOrLongBitmap() {
        longBitmap = 0xffff0000ffff0000L;
        longBitmap1 = 0x0000ffffffff0000L;

        assertTrue(BitUtils.bitwiseOr(longBitmap, longBitmap1) == 0xffffffffffff0000L);
        assertTrue(BitUtils.bitwiseOr(longBitmap, longBitmap1, 33) == 0xffff0001ffff0000L);
        assertTrue(BitUtils.bitwiseOr(longBitmap, longBitmap1, 17, 43) == 0xffff0fffffff0000L);
    }

    @Test()
    public void testLongBitwiseOrLowerMaxBitsException() {
        thrown.expect(IllegalArgumentException.class);
        BitUtils.bitwiseOr(longBitmap, longBitmap1, -1);
    }

    @Test()
    public void testLongBitwiseOrUpperMaxBitsException() {
        thrown.expect(IllegalArgumentException.class);
        BitUtils.bitwiseOr(longBitmap, longBitmap1, 65);
    }

    @Test()
    public void testLongBitwiseOrLowerRangeException() {
        thrown.expect(IllegalArgumentException.class);
        BitUtils.bitwiseOr(longBitmap, longBitmap1, -1, 40);
    }

    @Test()
    public void testLongBitwiseOrUpperRangeException() {
        thrown.expect(IllegalArgumentException.class);
        BitUtils.bitwiseOr(longBitmap, longBitmap1, 0, 64);
    }

    @Test
    public void testBitwiseOrIntBitmap() {
        intBitmap = 0x10101010;
        intBitmap1 = 0x01010101;

        assertTrue(BitUtils.bitwiseOr(intBitmap, intBitmap1) == 0x11111111);
        assertTrue(BitUtils.bitwiseOr(intBitmap, intBitmap1, 23) == 0x10111111);
        assertTrue(BitUtils.bitwiseOr(intBitmap, intBitmap1, 9, 23) == 0x10111010);
    }

    @Test()
    public void testIntBitwiseOrLowerMaxBitsException() {
        thrown.expect(IllegalArgumentException.class);
        BitUtils.bitwiseOr(intBitmap, intBitmap1, -1);
    }

    @Test()
    public void testIntBitwiseOrUpperMaxBitsException() {
        thrown.expect(IllegalArgumentException.class);
        BitUtils.bitwiseOr(intBitmap, intBitmap1, 33);
    }

    @Test()
    public void testIntBitwiseOrLowerRangeException() {
        thrown.expect(IllegalArgumentException.class);
        BitUtils.bitwiseOr(intBitmap, intBitmap1, -1, 23);
    }

    @Test()
    public void testIntBitwiseOrUpperRangeException() {
        thrown.expect(IllegalArgumentException.class);
        BitUtils.bitwiseOr(intBitmap, intBitmap1, 0, 32);
    }

    @Test
    public void testBitwiseOrShortBitmap() {
        shortBitmap = (short) 0x1701;
        shortBitmap1 = (short) 0x0801;

        assertTrue(BitUtils.bitwiseOr(shortBitmap, shortBitmap1) == (short) 0x1f01);
        assertTrue(BitUtils.bitwiseOr(shortBitmap, shortBitmap1, 9) == (short) 0x1701);
        assertTrue(BitUtils.bitwiseOr(shortBitmap, shortBitmap1, 3, 10) == (short) 0x1701);
    }

    @Test()
    public void testShortBitwiseOrLowerMaxBitsException() {
        thrown.expect(IllegalArgumentException.class);
        BitUtils.bitwiseOr(shortBitmap, shortBitmap1, -1);
    }

    @Test()
    public void testShortBitwiseOrUpperMaxBitsException() {
        thrown.expect(IllegalArgumentException.class);
        BitUtils.bitwiseOr(shortBitmap, shortBitmap1, 17);
    }

    @Test()
    public void testShortBitwiseOrLowerRangeException() {
        thrown.expect(IllegalArgumentException.class);
        BitUtils.bitwiseOr(shortBitmap, shortBitmap1, -1, 8);
    }

    @Test()
    public void testShortBitwiseOrUpperRangeException() {
        thrown.expect(IllegalArgumentException.class);
        BitUtils.bitwiseOr(shortBitmap, shortBitmap1, 0, 16);
    }

    @Test
    public void testBitwiseOrByteBitmap() {
        byteBitmap = (byte) 0x01;
        byteBitmap1 = (byte) 0x24;

        assertTrue(BitUtils.bitwiseOr(byteBitmap, byteBitmap1) == (byte) 0x25);
        assertTrue(BitUtils.bitwiseOr(byteBitmap, byteBitmap1, 4) == (byte) 0x05);
        assertTrue(BitUtils.bitwiseOr(byteBitmap, byteBitmap1, 3, 5) == (byte) 0x21);
    }

    @Test()
    public void testByteBitwiseOrLowerMaxBitsException() {
        thrown.expect(IllegalArgumentException.class);
        BitUtils.bitwiseOr(byteBitmap, byteBitmap1, -1);
    }

    @Test()
    public void testByteBitwiseOrUpperMaxBitsException() {
        thrown.expect(IllegalArgumentException.class);
        BitUtils.bitwiseOr(byteBitmap, byteBitmap1, 9);
    }

    @Test()
    public void testByteBitwiseOrLowerRangeException() {
        thrown.expect(IllegalArgumentException.class);
        BitUtils.bitwiseOr(byteBitmap, byteBitmap1, -1, 7);
    }

    @Test()
    public void testByteBitwiseOrUpperRangeException() {
        thrown.expect(IllegalArgumentException.class);
        BitUtils.bitwiseOr(byteBitmap, byteBitmap1, 0, 8);
    }

    @Test
    public void testBitwiseXorLongBitmap() {
        longBitmap = 0x9010101011001100L;
        longBitmap1 = 0x0111010110101010L;

        assertTrue(BitUtils.bitwiseXor(longBitmap, longBitmap1) == 0x9101111101100110L);
        assertTrue(BitUtils.bitwiseXor(longBitmap, longBitmap1, 33) == 0x9010101101100110L);
        assertTrue(BitUtils.bitwiseXor(longBitmap, longBitmap1, 9, 60) == 0x9101111101100100L);
    }

    @Test()
    public void testLongBitwiseXorLowerMaxBitsException() {
        thrown.expect(IllegalArgumentException.class);
        BitUtils.bitwiseXor(longBitmap, longBitmap1, -1);
    }

    @Test()
    public void testLongBitwiseXorUpperMaxBitsException() {
        thrown.expect(IllegalArgumentException.class);
        BitUtils.bitwiseXor(longBitmap, longBitmap1, 65);
    }

    @Test()
    public void testLongBitwiseXorLowerRangeException() {
        thrown.expect(IllegalArgumentException.class);
        BitUtils.bitwiseXor(longBitmap, longBitmap1, -1, 40);
    }

    @Test()
    public void testLongBitwiseXorUpperRangeException() {
        thrown.expect(IllegalArgumentException.class);
        BitUtils.bitwiseXor(longBitmap, longBitmap1, 0, 64);
    }

    @Test
    public void testBitwiseXorIntBitmap() {
        intBitmap = 0x90101010;
        intBitmap1 = 0x01110101;

        assertTrue(BitUtils.bitwiseXor(intBitmap, intBitmap1) == 0x91011111);
        assertTrue(BitUtils.bitwiseXor(intBitmap, intBitmap1, 23) == 0x90011111);
        assertTrue(BitUtils.bitwiseXor(intBitmap, intBitmap1, 9, 28) == 0x91011010);
    }

    @Test()
    public void testIntBitwiseXorLowerMaxBitsException() {
        thrown.expect(IllegalArgumentException.class);
        BitUtils.bitwiseXor(intBitmap, intBitmap1, -1);
    }

    @Test()
    public void testIntBitwiseXorUpperMaxBitsException() {
        thrown.expect(IllegalArgumentException.class);
        BitUtils.bitwiseXor(intBitmap, intBitmap1, 33);
    }

    @Test()
    public void testIntBitwiseXorLowerRangeException() {
        thrown.expect(IllegalArgumentException.class);
        BitUtils.bitwiseXor(intBitmap, intBitmap1, -1, 20);
    }

    @Test()
    public void testIntBitwiseXorUpperRangeException() {
        thrown.expect(IllegalArgumentException.class);
        BitUtils.bitwiseXor(intBitmap, intBitmap1, 0, 32);
    }

    @Test
    public void testBitwiseXorShortBitmap() {
        shortBitmap = (short) 0x9109;
        shortBitmap1 = (short) 0x0111;

        assertTrue(BitUtils.bitwiseXor(shortBitmap, shortBitmap1) == (short) 0x9018);
        assertTrue(BitUtils.bitwiseXor(shortBitmap, shortBitmap1, 12) == (short) 0x9018);
        assertTrue(BitUtils.bitwiseXor(shortBitmap, shortBitmap1, 3, 12) == (short) 0x9019);
    }

    @Test()
    public void testShortBitwiseXorLowerMaxBitsException() {
        thrown.expect(IllegalArgumentException.class);
        BitUtils.bitwiseXor(shortBitmap, shortBitmap1, -1);
    }

    @Test()
    public void testShortBitwiseXorUpperMaxBitsException() {
        thrown.expect(IllegalArgumentException.class);
        BitUtils.bitwiseXor(shortBitmap, shortBitmap1, 17);
    }

    @Test()
    public void testShortBitwiseXorLowerRangeException() {
        thrown.expect(IllegalArgumentException.class);
        BitUtils.bitwiseXor(shortBitmap, shortBitmap1, -1, 12);
    }

    @Test()
    public void testShortBitwiseXorUpperRangeException() {
        thrown.expect(IllegalArgumentException.class);
        BitUtils.bitwiseXor(shortBitmap, shortBitmap1, 0, 16);
    }

    @Test
    public void testBitwiseXorByteBitmap() {
        byteBitmap = (byte) 0xff;
        byteBitmap1 = (byte) 0x24;

        assertTrue(BitUtils.bitwiseXor(byteBitmap, byteBitmap1) == (byte) 0xdb);
        assertTrue(BitUtils.bitwiseXor(byteBitmap, byteBitmap1, 5) == (byte) 0xfb);
        assertTrue(BitUtils.bitwiseXor(byteBitmap, byteBitmap1, 3, 5) == (byte) 0xdf);
    }

    @Test()
    public void testByteBitwiseXorLowerMaxBitsException() {
        thrown.expect(IllegalArgumentException.class);
        BitUtils.bitwiseXor(byteBitmap, byteBitmap1, -1);
    }

    @Test()
    public void testByteBitwiseXorUpperMaxBitsException() {
        thrown.expect(IllegalArgumentException.class);
        BitUtils.bitwiseXor(byteBitmap, byteBitmap1, 9);
    }

    @Test()
    public void testByteBitwiseXorLowerRangeException() {
        thrown.expect(IllegalArgumentException.class);
        BitUtils.bitwiseXor(byteBitmap, byteBitmap1, -1, 5);
    }

    @Test()
    public void testByteBitwiseXorUpperRangeException() {
        thrown.expect(IllegalArgumentException.class);
        BitUtils.bitwiseXor(byteBitmap, byteBitmap1, 0, 8);
    }

    @Test
    public void testBitwiseNotLongBitmap() {
        longBitmap = 0xffffffffffffffffL;

        assertTrue(BitUtils.bitwiseNot(longBitmap) == 0x0L);
        assertTrue(BitUtils.bitwiseNot(longBitmap, 33) == 0xfffffffe00000000L);
        assertTrue(BitUtils.bitwiseNot(longBitmap, 9, 60) == 0xe0000000000001ffL);

    }

    @Test()
    public void testLongBitwiseNotLowerMaxBitsException() {
        thrown.expect(IllegalArgumentException.class);
        BitUtils.bitwiseNot(longBitmap, -1);
    }

    @Test()
    public void testLongBitwiseNotUpperMaxBitsException() {
        thrown.expect(IllegalArgumentException.class);
        BitUtils.bitwiseNot(longBitmap, 65);
    }

    @Test()
    public void testLongBitwiseNotLowerRangeException() {
        thrown.expect(IllegalArgumentException.class);
        BitUtils.bitwiseNot(longBitmap, -1, 40);
    }

    @Test()
    public void testLongBitwiseNotUpperRangeException() {
        thrown.expect(IllegalArgumentException.class);
        BitUtils.bitwiseNot(longBitmap, 0, 64);
    }

    @Test
    public void testBitwiseNotIntBitmap() {
        intBitmap = 0xffffffff;

        assertTrue(BitUtils.bitwiseNot(intBitmap) == 0x0L);
        assertTrue(BitUtils.bitwiseNot(intBitmap, 23) == 0xff800000);
        assertTrue(BitUtils.bitwiseNot(intBitmap, 9, 28) == 0xe00001ff);
    }

    @Test()
    public void testIntBitwiseNotLowerMaxBitsException() {
        thrown.expect(IllegalArgumentException.class);
        BitUtils.bitwiseNot(intBitmap, -1);
    }

    @Test()
    public void testIntBitwiseNotUpperMaxBitsException() {
        thrown.expect(IllegalArgumentException.class);
        BitUtils.bitwiseNot(intBitmap, 33);
    }

    @Test()
    public void testIntBitwiseNotLowerRangeException() {
        thrown.expect(IllegalArgumentException.class);
        BitUtils.bitwiseNot(intBitmap, -1, 20);
    }

    @Test()
    public void testIntBitwiseNotUpperRangeException() {
        thrown.expect(IllegalArgumentException.class);
        BitUtils.bitwiseNot(intBitmap, 0, 32);
    }

    @Test
    public void testBitwiseNotShortBitmap() {
        shortBitmap = (short) 0xffff;

        assertTrue(BitUtils.bitwiseNot(shortBitmap) == (short) 0x0);
        assertTrue(BitUtils.bitwiseNot(shortBitmap, 12) == (short) 0xf000);
        assertTrue(BitUtils.bitwiseNot(shortBitmap, 3, 12) == (short) 0xe007);
    }

    @Test()
    public void testShortBitwiseNotLowerMaxBitsException() {
        thrown.expect(IllegalArgumentException.class);
        BitUtils.bitwiseNot(shortBitmap, -1);
    }

    @Test()
    public void testShortBitwiseNotUpperMaxBitsException() {
        thrown.expect(IllegalArgumentException.class);
        BitUtils.bitwiseNot(shortBitmap, 17);
    }

    @Test()
    public void testShortBitwiseNotLowerRangeException() {
        thrown.expect(IllegalArgumentException.class);
        BitUtils.bitwiseNot(shortBitmap, -1, 12);
    }

    @Test()
    public void testShortBitwiseNotUpperRangeException() {
        thrown.expect(IllegalArgumentException.class);
        BitUtils.bitwiseNot(shortBitmap, 0, 16);
    }

    @Test
    public void testBitwiseNotByteBitmap() {
        byteBitmap = (byte) 0xff;

        assertTrue(BitUtils.bitwiseNot(byteBitmap) == (byte) 0x0);
        assertTrue(BitUtils.bitwiseNot(byteBitmap, 5) == (byte) 0xe0);
        assertTrue(BitUtils.bitwiseNot(byteBitmap, 3, 5) == (byte) 0xc7);
    }

    @Test()
    public void testByteBitwiseNotLowerMaxBitsException() {
        thrown.expect(IllegalArgumentException.class);
        BitUtils.bitwiseNot(byteBitmap, -1);
    }

    @Test()
    public void testByteBitwiseNotUpperMaxBitsException() {
        thrown.expect(IllegalArgumentException.class);
        BitUtils.bitwiseNot(byteBitmap, 9);
    }

    @Test()
    public void testByteBitwiseNotLowerRangeException() {
        thrown.expect(IllegalArgumentException.class);
        BitUtils.bitwiseNot(byteBitmap, -1, 5);
    }

    @Test()
    public void testByteBitwiseNotUpperRangeException() {
        thrown.expect(IllegalArgumentException.class);
        BitUtils.bitwiseNot(byteBitmap, 0, 8);
    }

}