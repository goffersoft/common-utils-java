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

        public void iterate(BitUtils.BitmapIterator it, Object var) {
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

        public LongBitmap bitwiseAnd(LongBitmap bitmap) {
            return new LongBitmap(BitUtils.bitwiseAnd(this.bitmap,
                    bitmap.getBitmap()));
        }

        public LongBitmap bitwiseAnd(LongBitmap bitmap, int maxbits) {
            return new LongBitmap(BitUtils.bitwiseAnd(this.bitmap,
                    bitmap.getBitmap(), maxbits));
        }

        public LongBitmap bitwiseAnd(LongBitmap bitmap, int startbitpos,
                int endbitpos) {
            return new LongBitmap(BitUtils.bitwiseAnd(this.bitmap,
                    bitmap.getBitmap(), startbitpos, endbitpos));
        }

        public LongBitmap bitwiseOr(LongBitmap bitmap) {
            return new LongBitmap(BitUtils.bitwiseOr(this.bitmap,
                    bitmap.getBitmap()));
        }

        public LongBitmap bitwiseOr(LongBitmap bitmap, int maxbits) {
            return new LongBitmap(BitUtils.bitwiseOr(this.bitmap,
                    bitmap.getBitmap(), maxbits));
        }

        public LongBitmap bitwiseOr(LongBitmap bitmap, int startbitpos,
                int endbitpos) {
            return new LongBitmap(BitUtils.bitwiseOr(this.bitmap,
                    bitmap.getBitmap(), startbitpos, endbitpos));
        }

        public LongBitmap bitwiseXor(LongBitmap bitmap) {
            return new LongBitmap(BitUtils.bitwiseXor(this.bitmap,
                    bitmap.getBitmap()));
        }

        public LongBitmap bitwiseXor(LongBitmap bitmap, int maxbits) {
            return new LongBitmap(BitUtils.bitwiseXor(this.bitmap,
                    bitmap.getBitmap(), maxbits));
        }

        public LongBitmap bitwiseXor(LongBitmap bitmap, int startbitpos,
                int endbitpos) {
            return new LongBitmap(BitUtils.bitwiseXor(this.bitmap,
                    bitmap.getBitmap(), startbitpos, endbitpos));
        }

        public LongBitmap bitwiseNot() {
            return new LongBitmap(BitUtils.bitwiseNot(bitmap));
        }

        public LongBitmap bitwiseNot(int maxbits) {
            return new LongBitmap(BitUtils.bitwiseNot(bitmap, maxbits));
        }

        public LongBitmap bitwiseNot(int startbitpos, int endbitpos) {
            return new LongBitmap(BitUtils.bitwiseNot(bitmap, startbitpos,
                    endbitpos));
        }

        @Override
        public String toString() {
            return String.format("Bitmap : 0x%04x\n", getBitmap());
        }

        public boolean equals(LongBitmap bm) {
            if (getBitmap() == bm.getBitmap()) {
                return true;
            }
            return false;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o instanceof LongBitmap) {
                LongBitmap bm = (LongBitmap) o;
                return equals(bm);
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

        public void iterate(BitUtils.BitmapIterator it, Object var) {
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

        public IntBitmap bitwiseAnd(IntBitmap bitmap) {
            return new IntBitmap(BitUtils.bitwiseAnd(this.bitmap,
                    bitmap.getBitmap()));
        }

        public IntBitmap bitwiseAnd(IntBitmap bitmap, int maxbits) {
            return new IntBitmap((int) BitUtils.bitwiseAnd(this.bitmap,
                    bitmap.getBitmap(), maxbits));
        }

        public IntBitmap bitwiseAnd(IntBitmap bitmap, int startbitpos,
                int endbitpos) {
            return new IntBitmap((int) BitUtils.bitwiseAnd(this.bitmap,
                    bitmap.getBitmap(), startbitpos, endbitpos));
        }

        public IntBitmap bitwiseOr(IntBitmap bitmap) {
            return new IntBitmap(BitUtils.bitwiseOr(this.bitmap,
                    bitmap.getBitmap()));
        }

        public IntBitmap bitwiseOr(IntBitmap bitmap, int maxbits) {
            return new IntBitmap((int) BitUtils.bitwiseOr(this.bitmap,
                    bitmap.getBitmap(), maxbits));
        }

        public IntBitmap bitwiseOr(IntBitmap bitmap, int startbitpos,
                int endbitpos) {
            return new IntBitmap((int) BitUtils.bitwiseOr(this.bitmap,
                    bitmap.getBitmap(), startbitpos, endbitpos));
        }

        public IntBitmap bitwiseXor(IntBitmap bitmap) {
            return new IntBitmap(BitUtils.bitwiseXor(this.bitmap,
                    bitmap.getBitmap()));
        }

        public IntBitmap bitwiseXor(IntBitmap bitmap, int maxbits) {
            return new IntBitmap((int) BitUtils.bitwiseXor(this.bitmap,
                    bitmap.getBitmap(), maxbits));
        }

        public IntBitmap bitwiseXor(IntBitmap bitmap, int startbitpos,
                int endbitpos) {
            return new IntBitmap((int) BitUtils.bitwiseXor(this.bitmap,
                    bitmap.getBitmap(), startbitpos, endbitpos));
        }

        public IntBitmap bitwiseNot() {
            return new IntBitmap(BitUtils.bitwiseNot(bitmap));
        }

        public IntBitmap bitwiseNot(int maxbits) {
            return new IntBitmap((int) BitUtils.bitwiseNot(bitmap, maxbits));
        }

        public IntBitmap bitwiseNot(int startbitpos, int endbitpos) {
            return new IntBitmap((int) BitUtils.bitwiseNot(bitmap, startbitpos,
                    endbitpos));
        }

        @Override
        public String toString() {
            return String.format("Bitmap : 0x%04x\n", getBitmap());
        }

        public boolean equals(IntBitmap bm) {
            if (getBitmap() == bm.getBitmap()) {
                return true;
            }
            return false;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o instanceof IntBitmap) {
                IntBitmap bm = (IntBitmap) o;
                return equals(bm);
            }
            return false;
        }
    }

    public static class ShortBitmap {
        private static final Logger log = Logger.getLogger(ShortBitmap.class);
        private short bitmap;

        public ShortBitmap() {
            this((short) 0x0);
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

        public void iterate(BitUtils.BitmapIterator it, Object var) {
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

        public ShortBitmap bitwiseAnd(ShortBitmap bitmap) {
            return new ShortBitmap(BitUtils.bitwiseAnd(this.bitmap,
                    bitmap.getBitmap()));
        }

        public ShortBitmap bitwiseAnd(ShortBitmap bitmap, Short maxbits) {
            return new ShortBitmap((short) BitUtils.bitwiseAnd(this.bitmap,
                    bitmap.getBitmap(), maxbits));
        }

        public ShortBitmap bitwiseAnd(ShortBitmap bitmap, Short startbitpos,
                Short endbitpos) {
            return new ShortBitmap((short) BitUtils.bitwiseAnd(this.bitmap,
                    bitmap.getBitmap(), startbitpos, endbitpos));
        }

        public ShortBitmap bitwiseOr(ShortBitmap bitmap) {
            return new ShortBitmap(BitUtils.bitwiseOr(this.bitmap,
                    bitmap.getBitmap()));
        }

        public ShortBitmap bitwiseOr(ShortBitmap bitmap, Short maxbits) {
            return new ShortBitmap((short) BitUtils.bitwiseOr(this.bitmap,
                    bitmap.getBitmap(), maxbits));
        }

        public ShortBitmap bitwiseOr(ShortBitmap bitmap, Short startbitpos,
                Short endbitpos) {
            return new ShortBitmap((short) BitUtils.bitwiseOr(this.bitmap,
                    bitmap.getBitmap(), startbitpos, endbitpos));
        }

        public ShortBitmap bitwiseXor(ShortBitmap bitmap) {
            return new ShortBitmap(BitUtils.bitwiseXor(this.bitmap,
                    bitmap.getBitmap()));
        }

        public ShortBitmap bitwiseXor(ShortBitmap bitmap, Short maxbits) {
            return new ShortBitmap((short) BitUtils.bitwiseXor(this.bitmap,
                    bitmap.getBitmap(), maxbits));
        }

        public ShortBitmap bitwiseXor(ShortBitmap bitmap, Short startbitpos,
                Short endbitpos) {
            return new ShortBitmap((short) BitUtils.bitwiseXor(this.bitmap,
                    bitmap.getBitmap(), startbitpos, endbitpos));
        }

        public ShortBitmap bitwiseNot() {
            return new ShortBitmap(BitUtils.bitwiseNot(bitmap));
        }

        public ShortBitmap bitwiseNot(Short maxbits) {
            return new ShortBitmap((short) BitUtils.bitwiseNot(bitmap, maxbits));
        }

        public ShortBitmap bitwiseNot(Short startbitpos, Short endbitpos) {
            return new ShortBitmap((short) BitUtils.bitwiseNot(bitmap,
                    startbitpos, endbitpos));
        }

        @Override
        public String toString() {
            return String.format("Bitmap : 0x%04x\n", getBitmap());
        }

        public boolean equals(ShortBitmap bm) {
            if (getBitmap() == bm.getBitmap()) {
                return true;
            }
            return false;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o instanceof ShortBitmap) {
                ShortBitmap bm = (ShortBitmap) o;
                return equals(bm);
            }
            return false;
        }
    }

    public static class ByteBitmap {
        private static final Logger log = Logger.getLogger(ByteBitmap.class);
        private byte bitmap;

        public ByteBitmap() {
            this((byte) 0x0);
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

        public void iterate(BitUtils.BitmapIterator it, Object var) {
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

        public ByteBitmap bitwiseAnd(ByteBitmap bitmap) {
            return new ByteBitmap(BitUtils.bitwiseAnd(this.bitmap,
                    bitmap.getBitmap()));
        }

        public ByteBitmap bitwiseAnd(ByteBitmap bitmap, byte maxbits) {
            return new ByteBitmap((byte) BitUtils.bitwiseAnd(this.bitmap,
                    bitmap.getBitmap(), maxbits));
        }

        public ByteBitmap bitwiseAnd(ByteBitmap bitmap, byte startbitpos,
                byte endbitpos) {
            return new ByteBitmap((byte) BitUtils.bitwiseAnd(this.bitmap,
                    bitmap.getBitmap(), startbitpos, endbitpos));
        }

        public ByteBitmap bitwiseOr(ByteBitmap bitmap) {
            return new ByteBitmap(BitUtils.bitwiseOr(this.bitmap,
                    bitmap.getBitmap()));
        }

        public ByteBitmap bitwiseOr(ByteBitmap bitmap, byte maxbits) {
            return new ByteBitmap((byte) BitUtils.bitwiseOr(this.bitmap,
                    bitmap.getBitmap(), maxbits));
        }

        public ByteBitmap bitwiseOr(ByteBitmap bitmap, byte startbitpos,
                byte endbitpos) {
            return new ByteBitmap((byte) BitUtils.bitwiseOr(this.bitmap,
                    bitmap.getBitmap(), startbitpos, endbitpos));
        }

        public ByteBitmap bitwiseXor(ByteBitmap bitmap) {
            return new ByteBitmap(BitUtils.bitwiseXor(this.bitmap,
                    bitmap.getBitmap()));
        }

        public ByteBitmap bitwiseXor(ByteBitmap bitmap, byte maxbits) {
            return new ByteBitmap((byte) BitUtils.bitwiseXor(this.bitmap,
                    bitmap.getBitmap(), maxbits));
        }

        public ByteBitmap bitwiseXor(ByteBitmap bitmap, byte startbitpos,
                byte endbitpos) {
            return new ByteBitmap((byte) BitUtils.bitwiseXor(this.bitmap,
                    bitmap.getBitmap(), startbitpos, endbitpos));
        }

        public ByteBitmap bitwiseNot() {
            return new ByteBitmap(BitUtils.bitwiseNot(bitmap));
        }

        public ByteBitmap bitwiseNot(byte maxbits) {
            return new ByteBitmap((byte) BitUtils.bitwiseNot(bitmap, maxbits));
        }

        public ByteBitmap bitwiseNot(byte startbitpos, byte endbitpos) {
            return new ByteBitmap((byte) BitUtils.bitwiseNot(bitmap,
                    startbitpos, endbitpos));
        }

        @Override
        public String toString() {
            return String.format("Bitmap : 0x%04x\n", getBitmap());
        }

        public boolean equals(ByteBitmap bm) {
            if (getBitmap() == bm.getBitmap()) {
                return true;
            }
            return false;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o instanceof ByteBitmap) {
                ByteBitmap bm = (ByteBitmap) o;
                return equals(bm);
            }
            return false;
        }
    }

    private static boolean isBitSetInternal(long bitmap, int bitpos) {
        return ((bitmap & (1L << bitpos)) == (1 << bitpos));
    }

    public static boolean isBitSet(long bitmap, int bitpos) {
        if (bitpos < 0 || bitpos >= BITUTILS_NUM_BITS_IN_LONG) {
            return false;
        }

        return isBitSetInternal(bitmap, bitpos);
    }

    public static boolean isBitSet(int bitmap, int bitpos) {
        if (bitpos < 0 || bitpos >= BITUTILS_NUM_BITS_IN_INT) {
            return false;
        }

        return isBitSetInternal(bitmap, bitpos);
    }

    public static boolean isBitSet(short bitmap, int bitpos) {
        if (bitpos < 0 || bitpos >= BITUTILS_NUM_BITS_IN_SHORT) {
            return false;
        }

        return isBitSetInternal(bitmap, bitpos);
    }

    public static boolean isBitSet(byte bitmap, int bitpos) {
        if (bitpos < 0 || bitpos >= BITUTILS_NUM_BITS_IN_BYTE) {
            return false;
        }

        return isBitSetInternal(bitmap, bitpos);
    }

    private static int getNumBitsSetInternal(long bitmap, int maxbits) {
        int numbits = 0;

        for (int i = 0; i < maxbits; i++) {
            if (isBitSetInternal(bitmap, i)) {
                numbits++;
            }
        }

        return numbits;
    }

    public static int getNumBitsSet(long bitmap, int maxbits) {
        if (maxbits < 0 || maxbits > BITUTILS_NUM_BITS_IN_LONG) {
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
        return (bitmap |= (1L << bitpos));
    }

    public static long setBit(long bitmap, int bitpos) {
        if (bitpos < 0 || bitpos >= BITUTILS_NUM_BITS_IN_LONG) {
            return bitmap;
        }

        return setBitInternal(bitmap, bitpos);
    }

    public static long setAllBits(long bitmap) {
        return 0xffffffffffffffffL;
    }

    public static int setBit(int bitmap, int bitpos) {
        if (bitpos < 0 || bitpos >= BITUTILS_NUM_BITS_IN_INT) {
            return bitmap;
        }

        return (int) setBitInternal(bitmap, bitpos);
    }

    public static int setAllBits(int bitmap) {
        return 0xffffffff;
    }

    public static short setBit(short bitmap, int bitpos) {
        if (bitpos < 0 || bitpos >= BITUTILS_NUM_BITS_IN_SHORT) {
            return bitmap;
        }

        return (short) setBitInternal(bitmap, bitpos);
    }

    public static short setAllBits(short bitmap) {
        return (short) 0xffff;
    }

    public static byte setBit(byte bitmap, int bitpos) {
        if (bitpos < 0 || bitpos >= BITUTILS_NUM_BITS_IN_BYTE) {
            return bitmap;
        }

        return (byte) setBitInternal(bitmap, bitpos);
    }

    public static short setAllBits(byte bitmap) {
        return (byte) 0xff;
    }

    private static long clearBitInternal(long bitmap, int bitpos) {
        return (bitmap &= ~(1L << bitpos));
    }

    public static long clearBit(long bitmap, int bitpos) {
        if (bitpos < 0 || bitpos >= BITUTILS_NUM_BITS_IN_LONG) {
            return bitmap;
        }

        return clearBitInternal(bitmap, bitpos);
    }

    public static long clearAllBits(long bitmap) {
        return 0x0L;
    }

    public static int clearBit(int bitmap, int bitpos) {
        if (bitpos < 0 || bitpos >= BITUTILS_NUM_BITS_IN_INT) {
            return bitmap;
        }

        return (int) clearBitInternal(bitmap, bitpos);
    }

    public static int clearAllBits(int bitmap) {
        return 0x0;
    }

    public static short clearBit(short bitmap, int bitpos) {
        if (bitpos < 0 || bitpos >= BITUTILS_NUM_BITS_IN_SHORT) {
            return bitmap;
        }

        return (short) clearBitInternal(bitmap, bitpos);
    }

    public static short clearAllBits(short bitmap) {
        return 0x0;
    }

    public static byte clearBit(byte bitmap, int bitpos) {
        if (bitpos < 0 || bitpos >= BITUTILS_NUM_BITS_IN_BYTE) {
            return bitmap;
        }

        return (byte) clearBitInternal(bitmap, bitpos);
    }

    public static byte clearAllBits(byte bitmap) {
        return 0x0;
    }

    private static long flipBitInternal(long bitmap, int bitpos) {
        return (bitmap ^= (1L << bitpos));
    }

    private static long flipAllBitsInternal(long bitmap, int maxbits) {
        long tmpBitmap = bitmap;

        for (int i = 0; i < maxbits; i++) {
            tmpBitmap = flipBit(tmpBitmap, i);
        }

        return tmpBitmap;
    }

    public static long flipBit(long bitmap, int bitpos) {
        if (bitpos < 0 || bitpos >= BITUTILS_NUM_BITS_IN_LONG) {
            return bitmap;
        }

        return flipBitInternal(bitmap, bitpos);
    }

    public static long flipAllBits(long bitmap) {
        return flipAllBitsInternal(bitmap, BITUTILS_NUM_BITS_IN_LONG);
    }

    public static int flipBit(int bitmap, int bitpos) {
        if (bitpos < 0 || bitpos >= BITUTILS_NUM_BITS_IN_INT) {
            return bitmap;
        }

        return (int) flipBitInternal(bitmap, bitpos);
    }

    public static int flipAllBits(int bitmap) {
        return (int) flipAllBitsInternal(bitmap, BITUTILS_NUM_BITS_IN_INT);
    }

    public static short flipBit(short bitmap, int bitpos) {
        if (bitpos < 0 || bitpos >= BITUTILS_NUM_BITS_IN_SHORT) {
            return bitmap;
        }

        return (short) flipBitInternal(bitmap, bitpos);
    }

    public static short flipAllBits(short bitmap) {
        return (short) flipAllBitsInternal(bitmap, BITUTILS_NUM_BITS_IN_SHORT);
    }

    public static byte flipBit(byte bitmap, int bitpos) {
        if (bitpos < 0 || bitpos >= BITUTILS_NUM_BITS_IN_BYTE) {
            return bitmap;
        }

        return (byte) flipBitInternal(bitmap, bitpos);
    }

    public static byte flipAllBits(byte bitmap) {
        return (byte) flipAllBitsInternal(bitmap, BITUTILS_NUM_BITS_IN_BYTE);
    }

    private static int getBitPosInternal(long bitmask, int maxbits) {
        for (int i = 0; i < maxbits; i++) {
            if ((bitmask & (1L << i)) == bitmask) {
                return i;
            }
        }
        return -1;
    }

    public static int getBitPos(long bitmask, int maxbits) {
        if (maxbits < 0 || maxbits > BITUTILS_NUM_BITS_IN_LONG) {
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

    private static void bitmapIteratorInternal(long bitmap, int maxbits,
            BitmapIterator it, Object var) {

        for (int i = 0; i < maxbits; i++) {
            if (isBitSetInternal(bitmap, i)) {
                it.executeIfBitIsSet(i, var);
            } else {
                it.executeIfBitIsNotSet(i, var);
            }
        }
    }

    public static void bitmapIterator(long bitmap, int maxbits,
            BitmapIterator it, Object var) {
        if (maxbits < 0 || maxbits > BITUTILS_NUM_BITS_IN_LONG) {
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

    public static void bitmapIterator(short bitmap, BitmapIterator it,
            Object var) {
        bitmapIteratorInternal(bitmap, BITUTILS_NUM_BITS_IN_SHORT, it, var);
    }

    public static void bitmapIterator(byte bitmap, BitmapIterator it, Object var) {
        bitmapIteratorInternal(bitmap, BITUTILS_NUM_BITS_IN_BYTE, it, var);
    }

    public static long bitwiseAnd(long bitmap1, long bitmap2, int maxbits) {
        if (maxbits <= 0 || maxbits > BITUTILS_NUM_BITS_IN_LONG) {
            throw new IllegalArgumentException("maxbits:" + maxbits
                    + " : maxbits <= 0 || maxbits > BITUTILS_NUM_BITS_IN_LONG");
        }

        long mask = (1L << maxbits) - 1L;
        long bitmap = (bitmap1 & bitmap2) & mask;

        return ((bitmap1 & ~mask) | bitmap);
    }

    public static long bitwiseAnd(long bitmap1, long bitmap2, int startbitpos,
            int endbitpos) {
        if (startbitpos < 0 || startbitpos >= BITUTILS_NUM_BITS_IN_LONG
                || endbitpos < 0 || endbitpos >= BITUTILS_NUM_BITS_IN_LONG
                || startbitpos > endbitpos) {
            throw new IllegalArgumentException(
                    "startbitpos : "
                            + startbitpos
                            + "endbitpos: "
                            + endbitpos
                            + " : startbitpos < 0 || startbitpos >= BITUTILS_NUM_BITS_IN_LONG ||"
                            + "endbitpos < 0 || endbitpos >= BITUTILS_NUM_BITS_IN_LONG ||"
                            + "startbitpos > endbitpos");
        }

        long bitmap;
        int maxbits = endbitpos - startbitpos + 1;
        long mask1 = (1L << (startbitpos + 1)) - 1L;
        long mask2 = ((1L << maxbits) - 1L) << startbitpos;
        long mask3 = ~(mask1 | mask2);

        bitmap = (bitmap1 & bitmap2) & mask2;

        return ((bitmap1 & (mask1 | mask3)) | bitmap);
    }

    public static long bitwiseAnd(long bitmap1, long bitmap2) {
        return (bitmap1 & bitmap2);
    }

    public static int bitwiseAnd(int bitmap1, int bitmap2) {
        return (bitmap1 & bitmap2);
    }

    public static short bitwiseAnd(short bitmap1, short bitmap2) {
        return (short) (bitmap1 & bitmap2);
    }

    public static byte bitwiseAnd(byte bitmap1, byte bitmap2) {
        return (byte) (bitmap1 & bitmap2);
    }

    public static long bitwiseOr(long bitmap1, long bitmap2, int maxbits) {
        if (maxbits <= 0 || maxbits > BITUTILS_NUM_BITS_IN_LONG) {
            throw new IllegalArgumentException("maxbits:" + maxbits
                    + " : maxbits <= 0 || maxbits > BITUTILS_NUM_BITS_IN_LONG");
        }

        long mask = (1L << maxbits) - 1L;
        long bitmap = (bitmap1 | bitmap2) & mask;

        return ((bitmap1 & ~mask) | bitmap);
    }

    public static long bitwiseOr(long bitmap1, long bitmap2, int startbitpos,
            int endbitpos) {
        if (startbitpos < 0 || startbitpos >= BITUTILS_NUM_BITS_IN_LONG
                || endbitpos < 0 || endbitpos >= BITUTILS_NUM_BITS_IN_LONG
                || startbitpos > endbitpos) {
            throw new IllegalArgumentException(
                    "startbitpos : "
                            + startbitpos
                            + "endbitpos: "
                            + endbitpos
                            + " : startbitpos < 0 || startbitpos >= BITUTILS_NUM_BITS_IN_LONG ||"
                            + "endbitpos < 0 || endbitpos >= BITUTILS_NUM_BITS_IN_LONG ||"
                            + "startbitpos > endbitpos");
        }

        long bitmap;
        int maxbits = endbitpos - startbitpos + 1;
        long mask1 = (1L << (startbitpos + 1)) - 1L;
        long mask2 = ((1L << maxbits) - 1L) << startbitpos;
        long mask3 = ~(mask1 | mask2);

        bitmap = (bitmap1 | bitmap2) & mask2;

        return ((bitmap1 & (mask1 | mask3)) | bitmap);
    }

    public static long bitwiseOr(long bitmap1, long bitmap2) {
        return (bitmap1 | bitmap2);
    }

    public static int bitwiseOr(int bitmap1, int bitmap2) {
        return (bitmap1 | bitmap2);
    }

    public static short bitwiseOr(short bitmap1, short bitmap2) {
        return (short) (bitmap1 | bitmap2);
    }

    public static byte bitwiseOr(byte bitmap1, byte bitmap2) {
        return (byte) (bitmap1 | bitmap2);
    }

    public static long bitwiseXor(long bitmap1, long bitmap2, int maxbits) {
        if (maxbits <= 0 || maxbits > BITUTILS_NUM_BITS_IN_LONG) {
            throw new IllegalArgumentException("maxbits:" + maxbits
                    + " : maxbits <= 0 || maxbits > BITUTILS_NUM_BITS_IN_LONG");
        }

        long mask = (1L << maxbits) - 1L;
        long bitmap = (bitmap1 ^ bitmap2) & mask;

        return ((bitmap1 & ~mask) | bitmap);
    }

    public static long bitwiseXor(long bitmap1, long bitmap2, int startbitpos,
            int endbitpos) {
        if (startbitpos < 0 || startbitpos >= BITUTILS_NUM_BITS_IN_LONG
                || endbitpos < 0 || endbitpos >= BITUTILS_NUM_BITS_IN_LONG
                || startbitpos > endbitpos) {
            throw new IllegalArgumentException(
                    "startbitpos : "
                            + startbitpos
                            + "endbitpos: "
                            + endbitpos
                            + " : startbitpos < 0 || startbitpos >= BITUTILS_NUM_BITS_IN_LONG ||"
                            + "endbitpos < 0 || endbitpos >= BITUTILS_NUM_BITS_IN_LONG ||"
                            + "startbitpos > endbitpos");
        }

        long bitmap;
        int maxbits = endbitpos - startbitpos + 1;
        long mask1 = (1L << (startbitpos + 1)) - 1L;
        long mask2 = ((1L << maxbits) - 1L) << startbitpos;
        long mask3 = ~(mask1 | mask2);

        bitmap = (bitmap1 ^ bitmap2) & mask2;

        return ((bitmap1 & (mask1 | mask3)) | bitmap);
    }

    public static long bitwiseXor(long bitmap1, long bitmap2) {
        return (bitmap1 ^ bitmap2);
    }

    public static int bitwiseXor(int bitmap1, int bitmap2) {
        return (bitmap1 ^ bitmap2);
    }

    public static short bitwiseXor(short bitmap1, short bitmap2) {
        return (short) (bitmap1 ^ bitmap2);
    }

    public static byte bitwiseXor(byte bitmap1, byte bitmap2) {
        return (byte) (bitmap1 ^ bitmap2);
    }

    public static long bitwiseNot(long bitmap, int maxbits) {
        if (maxbits <= 0 || maxbits > BITUTILS_NUM_BITS_IN_LONG) {
            throw new IllegalArgumentException("maxbits:" + maxbits
                    + " : maxbits <= 0 || maxbits > BITUTILS_NUM_BITS_IN_LONG");
        }

        long mask = (1L << maxbits) - 1L;
        long tmpBitmap = (~bitmap) & mask;

        return ((bitmap & ~mask) | tmpBitmap);
    }

    public static long bitwiseNot(long bitmap, int startbitpos, int endbitpos) {
        if (startbitpos < 0 || startbitpos >= BITUTILS_NUM_BITS_IN_LONG
                || endbitpos < 0 || endbitpos >= BITUTILS_NUM_BITS_IN_LONG
                || startbitpos > endbitpos) {
            throw new IllegalArgumentException(
                    "startbitpos : "
                            + startbitpos
                            + "endbitpos: "
                            + endbitpos
                            + " : startbitpos < 0 || startbitpos >= BITUTILS_NUM_BITS_IN_LONG ||"
                            + "endbitpos < 0 || endbitpos >= BITUTILS_NUM_BITS_IN_LONG ||"
                            + "startbitpos > endbitpos");
        }

        int maxbits = endbitpos - startbitpos + 1;
        long mask1 = (1L << (startbitpos + 1)) - 1L;
        long mask2 = ((1L << maxbits) - 1L) << startbitpos;
        long mask3 = ~(mask1 | mask2);

        long tmpBitmap = (~bitmap) & mask2;

        return ((bitmap & (mask1 | mask3)) | tmpBitmap);
    }

    public static long bitwiseNot(long bitmap) {
        return (~bitmap);
    }

    public static int bitwiseNot(int bitmap) {
        return (~bitmap);
    }

    public static short bitwiseNot(short bitmap) {
        return (short) (~bitmap);
    }

    public static byte bitwiseNot(byte bitmap) {
        return (byte) (~bitmap);
    }
}
