/**
 ** File: BitUtils.java
 **
 ** Description : Bit manipulation Utility Class for 
 **               long, int, short, byte
 **
 ** Date           Author                          Comments
 ** 02/12/2014     Prakash Easwar                  Created  
 */
package com.goffersoft.common.utils;


import org.apache.log4j.Logger;

public class BitUtils {
	private static final Logger log = Logger.getLogger(BitUtils.class);
	
	public static final int BITUTILS_NUM_BITS_IN_LONG = Long.SIZE;
	public static final int BITUTILS_NUM_BITS_IN_INT = Integer.SIZE;
	public static final int BITUTILS_NUM_BITS_IN_SHORT = Short.SIZE;
	public static final int BITUTILS_NUM_BITS_IN_BYTE = Byte.SIZE;
	
	public static interface BitmapIterator {
		public void executeIfBitIsSet(int bitpos, Object var);
		public void executeIfBitIsNotSet(int bitpos, Object var);
		
	}
	
	public static class LongBitmap {
		private static final Logger log = Logger.getLogger(LongBitmap.class);
		private long bitmap;
		
		public LongBitmap() {
			this(0x0);
		}
		
		LongBitmap(long bmap) {
			setBitmap(bmap);
		}
		
		public long getBitmap() {
			return bitmap;
		}

		public void setBitmap(long bitmap) {
			this.bitmap = bitmap;
		}
		
		
		public  void iterate(BitUtils.BitmapIterator it, Object var) {
			BitUtils.bitmapIterator(getBitmap(), it, var);
		}
		
		public boolean isBitSet(int bitpos) {
			return BitUtils.isBitSet(getBitmap(), bitpos);
		}

		public void setBit(int bitpos) {
			setBitmap(BitUtils.setBit(getBitmap(), bitpos));
		}
		
		public void clearBit(int bitpos) {
			setBitmap(BitUtils.clearBit(getBitmap(), bitpos));
		}
		
		public int getNumBitsSet() {
			return BitUtils.getNumBitsSet(getBitmap());
		}
		
		@Override
		public String toString() {
			return String.format("Bitmap : 0x%04x\n", getBitmap());
		}
		
		@Override
		public boolean equals(Object o) {
			if(this == o) {
				return true;
			}
			if(o instanceof LongBitmap) {
				LongBitmap bm = (LongBitmap)o;
				if(getBitmap() == bm.getBitmap()){
					return true;
				}
			}
			return false;
		}
	}
	
	public static class IntBitmap {
		private static final Logger log = Logger.getLogger(IntBitmap.class);
		private int bitmap;
		
		public IntBitmap() {
			this(0x0);
		}
		
		public IntBitmap(int bmap) {
			bitmap = bmap;
		}
		
		public int getBitmap() {
			return bitmap;
		}
		
		public void setBitmap(int bmap) {
			bitmap = bmap;
		}

		public  void iterate(BitUtils.BitmapIterator it, Object var) {
			BitUtils.bitmapIterator(getBitmap(), it, var);
		}
		
		public boolean isBitSet(int bitpos) {
			return BitUtils.isBitSet(getBitmap(), bitpos);
		}

		public void setBit(int bitpos) {
			setBitmap(BitUtils.setBit(getBitmap(), bitpos));
		}
		
		public void clearBit(int bitpos) {
			setBitmap(BitUtils.clearBit(getBitmap(), bitpos));
		}
		
		public int getNumBitsSet() {
			return BitUtils.getNumBitsSet(getBitmap());
		}
		
		@Override
		public String toString() {
			return String.format("Bitmap : 0x%04x\n", getBitmap());
		}
		
		@Override
		public boolean equals(Object o) {
			if(this == o) {
				return true;
			}
			if(o instanceof IntBitmap) {
				IntBitmap bm = (IntBitmap)o;
				if(getBitmap() == bm.getBitmap()){
					return true;
				}
			}
			return false;
		}
	}
	
	public static class ShortBitmap {
		private static final Logger log = Logger.getLogger(ShortBitmap.class);
		private short bitmap;
		
		public ShortBitmap() {
			this((short)0x0);
		}
		
		ShortBitmap(short bmap) {
			bitmap = bmap;
		}
		
		public short getBitmap() {
			return bitmap;
		}

		public void setBitmap(short bitmap) {
			this.bitmap = bitmap;
		}
		
		public  void iterate(BitUtils.BitmapIterator it, Object var) {
			BitUtils.bitmapIterator(getBitmap(), it, var);
		}
		
		public boolean isBitSet(int bitpos) {
			return BitUtils.isBitSet(getBitmap(), bitpos);
		}

		public void setBit(int bitpos) {
			setBitmap(BitUtils.setBit(getBitmap(), bitpos));
		}
		
		public void clearBit(int bitpos) {
			setBitmap(BitUtils.clearBit(getBitmap(), bitpos));
		}
		
		public int getNumBitsSet() {
			return BitUtils.getNumBitsSet(getBitmap());
		}
		
		@Override
		public String toString() {
			return String.format("Bitmap : 0x%04x\n", getBitmap());
		}
		
		@Override
		public boolean equals(Object o) {
			if(this == o) {
				return true;
			}
			if(o instanceof ShortBitmap) {
				ShortBitmap bm = (ShortBitmap)o;
				if(getBitmap() == bm.getBitmap()){
					return true;
				}
			}
			return false;
		}
	}
	
	public static class ByteBitmap {
		private static final Logger log = Logger.getLogger(ByteBitmap.class);
		private byte bitmap;
		
		public ByteBitmap() {
			this((byte)0x0);
		}
		
		ByteBitmap(byte bmap) {
			bitmap = bmap;
		}
		
		public byte getBitmap() {
			return bitmap;
		}

		public void setBitmap(byte bitmap) {
			this.bitmap = bitmap;
		}
		
		public  void iterate(BitUtils.BitmapIterator it, Object var) {
			BitUtils.bitmapIterator(getBitmap(), it, var);
		}
		
		public boolean isBitSet(int bitpos) {
			return BitUtils.isBitSet(getBitmap(), bitpos);
		}

		public void setBit(int bitpos) {
			setBitmap(BitUtils.setBit(getBitmap(), bitpos));
		}
		
		public void clearBit(int bitpos) {
			setBitmap(BitUtils.clearBit(getBitmap(), bitpos));
		}
		
		public int getNumBitsSet() {
			return BitUtils.getNumBitsSet(getBitmap());
		}
		
		@Override
		public String toString() {
			return String.format("Bitmap : 0x%04x\n", getBitmap());
		}
		
		@Override
		public boolean equals(Object o) {
			if(this == o) {
				return true;
			}
			if(o instanceof ByteBitmap) {
				ByteBitmap bm = (ByteBitmap)o;
				if(getBitmap() == bm.getBitmap()){
					return true;
				}
			}
			return false;
		}
	}
	
	private static boolean isBitSetInternal(long bitmap, int bitpos) {
		return ( (bitmap & (1L << bitpos)) == (1 << bitpos));
	}
	
	public static boolean isBitSet(long bitmap, int bitpos) {
		if(bitpos < 0 || bitpos >= BITUTILS_NUM_BITS_IN_LONG) {
			return false;
		}
		
		return isBitSetInternal(bitmap, bitpos);
	}
	
	public static boolean isBitSet(int bitmap, int bitpos) {
		if(bitpos < 0 || bitpos >= BITUTILS_NUM_BITS_IN_INT) {
			return false;
		}
		
		return isBitSetInternal(bitmap, bitpos);
	}
	
	public static boolean isBitSet(short bitmap, int bitpos) {
		if(bitpos < 0 || bitpos >= BITUTILS_NUM_BITS_IN_SHORT) {
			return false;
		}
		
		return isBitSetInternal(bitmap, bitpos);
	}
	
	public static boolean isBitSet(byte bitmap, int bitpos) {
		if(bitpos < 0 || bitpos >= BITUTILS_NUM_BITS_IN_BYTE) {
			return false;
		}
		
		return isBitSetInternal(bitmap, bitpos);
	}
	
	private static int getNumBitsSetInternal(long bitmap, int maxbits) {
		int numbits = 0;
		
		for(int i = 0; i < maxbits; i++) {
			if( isBitSetInternal(bitmap, i) ) {
				numbits++;
			}
		}
		
		return numbits;
	}
	
	public static int getNumBitsSet(long bitmap, int maxbits) {	
		if(maxbits < 0 || maxbits > BITUTILS_NUM_BITS_IN_LONG) {
			maxbits = BITUTILS_NUM_BITS_IN_LONG;
		}
		
		return getNumBitsSetInternal(bitmap, maxbits);
	}
	
	public static int getNumBitsSet(long bitmap) {
		return getNumBitsSetInternal(bitmap, BITUTILS_NUM_BITS_IN_LONG);
	}
	
	public static int getNumBitsSet(int bitmap) {	
		return getNumBitsSetInternal(bitmap, BITUTILS_NUM_BITS_IN_INT);
	}
	
	public static int getNumBitsSet(short bitmap) {	
		return getNumBitsSetInternal(bitmap, BITUTILS_NUM_BITS_IN_SHORT);
	}
	
	public static int getNumBitsSet(byte bitmap) {	
		return getNumBitsSetInternal(bitmap, BITUTILS_NUM_BITS_IN_BYTE);
	}
	
	private static long setBitInternal(long bitmap, int bitpos) {
		return (bitmap |= (1L<<bitpos));
	}
	
	public static long setBit(long bitmap, int bitpos) {
		if(bitpos < 0 || bitpos >= BITUTILS_NUM_BITS_IN_LONG) {
			return bitmap;
		}
		
		return setBitInternal(bitmap, bitpos);
	}
	
	public static int setBit(int bitmap, int bitpos) {
		if(bitpos < 0 || bitpos >= BITUTILS_NUM_BITS_IN_INT) {
			return bitmap;
		}
		
		return (int)setBitInternal(bitmap, bitpos);
	}
	
	public static short setBit(short bitmap, int bitpos) {
		if(bitpos < 0 || bitpos >= BITUTILS_NUM_BITS_IN_SHORT) {
			return bitmap;
		}
		
		return (short)setBitInternal(bitmap, bitpos);
	}
	
	public static byte setBit(byte bitmap, int bitpos) {
		if(bitpos < 0 || bitpos >= BITUTILS_NUM_BITS_IN_BYTE) {
			return bitmap;
		}
		
		return (byte)setBitInternal(bitmap, bitpos);
	}
	
	private static long clearBitInternal(long bitmap, int bitpos) {
		return (bitmap &= ~(1L<<bitpos));
	}
	
	public static long clearBit(long bitmap, int bitpos) {
		if(bitpos < 0 || bitpos >= BITUTILS_NUM_BITS_IN_LONG) {
			return bitmap;
		}
		
		return clearBitInternal(bitmap, bitpos);
	}
	
	public static int clearBit(int bitmap, int bitpos) {
		if(bitpos < 0 || bitpos >= BITUTILS_NUM_BITS_IN_INT) {
			return bitmap;
		}
		
		return (int)clearBitInternal(bitmap, bitpos);
	}
	
	public static short clearBit(short bitmap, int bitpos) {
		if(bitpos < 0 || bitpos >= BITUTILS_NUM_BITS_IN_SHORT) {
			return bitmap;
		}
		
		return (short)clearBitInternal(bitmap, bitpos);
	}
	
	public static byte clearBit(byte bitmap, int bitpos) {
		if(bitpos < 0 || bitpos >= BITUTILS_NUM_BITS_IN_BYTE) {
			return bitmap;
		}
		
		return (byte)clearBitInternal(bitmap, bitpos);
	}
	
	private static long flipBitInternal(long bitmap, int bitpos) {
		return (bitmap ^= (1L<<bitpos));
	}
	
	public static long flipBit(long bitmap, int bitpos) {
		if(bitpos < 0 || bitpos >= BITUTILS_NUM_BITS_IN_LONG) {
			return bitmap;
		}
		
		return flipBitInternal(bitmap, bitpos);
	}
	
	public static int flipBit(int bitmap, int bitpos) {
		if(bitpos < 0 || bitpos >= BITUTILS_NUM_BITS_IN_INT) {
			return bitmap;
		}
		
		return (int)flipBitInternal(bitmap, bitpos);
	}
	
	public static short flipBit(short bitmap, int bitpos) {
		if(bitpos < 0 || bitpos >= BITUTILS_NUM_BITS_IN_SHORT) {
			return bitmap;
		}
		
		return (short)flipBitInternal(bitmap, bitpos);
	}
	
	public static byte flipBit(byte bitmap, int bitpos) {
		if(bitpos < 0 || bitpos >= BITUTILS_NUM_BITS_IN_BYTE) {
			return bitmap;
		}
		
		return (byte)flipBitInternal(bitmap, bitpos);
	}
	
	private static int getBitPosInternal(long bitmask, int maxbits) {
		for(int i = 0; i < maxbits; i++) {
			if( (bitmask & (1L << i))  == bitmask) {
				return i;
			}
		}
		return -1;
	}
	
	public static int getBitPos(long bitmask, int maxbits) {
		if(maxbits < 0 || maxbits > BITUTILS_NUM_BITS_IN_LONG) {
			maxbits = BITUTILS_NUM_BITS_IN_LONG;
		}
		return getBitPosInternal(bitmask, maxbits);
	}
	
	public static int getBitPos(long bitmask) {
		return getBitPosInternal(bitmask, BITUTILS_NUM_BITS_IN_LONG);
	}
	
	public static int getBitPos(int bitmask) {
		return getBitPosInternal(bitmask, BITUTILS_NUM_BITS_IN_INT);
	}
	
	public static int getBitPos(short bitmask) {
		return getBitPosInternal(bitmask, BITUTILS_NUM_BITS_IN_SHORT);
	}
	
	public static int getBitPos(byte bitmask) {
		return getBitPosInternal(bitmask, BITUTILS_NUM_BITS_IN_BYTE);
	}
	
	private static void bitmapIteratorInternal(long bitmap, int maxbits, BitmapIterator it, Object var) {
		
		for(int i = 0; i < maxbits; i++) {
			if(isBitSetInternal(bitmap,i)) {
				it.executeIfBitIsSet(i,var);
			} else {
				it.executeIfBitIsNotSet(i,var);
			}
		}
	}
	
	public static void bitmapIterator(long bitmap, int maxbits, BitmapIterator it, Object var) {
		if(maxbits < 0 || maxbits > BITUTILS_NUM_BITS_IN_LONG) {
			maxbits = BITUTILS_NUM_BITS_IN_LONG;
		}
		bitmapIteratorInternal(bitmap, maxbits, it, var);
	}
	
	public static void bitmapIterator(long bitmap, BitmapIterator it, Object var) {
		bitmapIteratorInternal(bitmap, BITUTILS_NUM_BITS_IN_LONG, it, var);
	}
	
	public static void bitmapIterator(int bitmap, BitmapIterator it, Object var) {
		bitmapIteratorInternal(bitmap, BITUTILS_NUM_BITS_IN_INT, it, var);
	}
	
	public static void bitmapIterator(short bitmap, BitmapIterator it, Object var) {
		bitmapIteratorInternal(bitmap, BITUTILS_NUM_BITS_IN_SHORT, it, var);
	}
	
	public static void bitmapIterator(byte bitmap, BitmapIterator it, Object var) {
		bitmapIteratorInternal(bitmap, BITUTILS_NUM_BITS_IN_BYTE, it, var);
	}
}
