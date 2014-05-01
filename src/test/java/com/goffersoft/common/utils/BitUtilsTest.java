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
		
		longBitmap = BitUtils.setBit(longBitmap, BitUtils.BITUTILS_NUM_BITS_IN_LONG - 1);
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
		
		intBitmap = BitUtils.setBit(intBitmap, BitUtils.BITUTILS_NUM_BITS_IN_INT - 1);
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
		
		shortBitmap = BitUtils.setBit(shortBitmap, BitUtils.BITUTILS_NUM_BITS_IN_SHORT - 1);
		assertTrue(shortBitmap == ((short)0x8001));
		
		shortBitmap = BitUtils.setBit(shortBitmap, 9);
		assertTrue(shortBitmap == ((short)0x8201));
		
		bitpos = BitUtils.BITUTILS_NUM_BITS_IN_SHORT;
		shortBitmap = BitUtils.setBit(shortBitmap, bitpos);
		assertTrue(shortBitmap == ((short)0x8201));
	}
	
	
	
	@Test
	public void testSetBitByteBitmap() {
		byteBitmap = BitUtils.setBit(byteBitmap, bitpos);
		assertTrue(byteBitmap == 0x01);
		
		byteBitmap = BitUtils.setBit(byteBitmap, BitUtils.BITUTILS_NUM_BITS_IN_BYTE - 1);
		assertTrue(byteBitmap == ((byte)0x81));
		
		byteBitmap = BitUtils.setBit(byteBitmap, 5);
		assertTrue(byteBitmap == ((byte)0xa1));
		
		bitpos = BitUtils.BITUTILS_NUM_BITS_IN_BYTE;
		byteBitmap = BitUtils.setBit(byteBitmap, bitpos);
		assertTrue(byteBitmap == ((byte)0xa1));
	}
}