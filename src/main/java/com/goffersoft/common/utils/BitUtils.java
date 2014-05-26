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

        public void
                iterate(int maxbits, BitUtils.BitmapIterator it, Object var) {
            BitUtils.bitmapIterator(getBitmap(), maxbits, it, var);
        }

        public void iterate(int startbitpos, int endbitpos,
                BitUtils.BitmapIterator it, Object var) {
            BitUtils.bitmapIterator(getBitmap(), startbitpos, endbitpos, it,
                    var);
        }

        public boolean isBitSet(int bitpos) {
            return BitUtils.isBitSet(getBitmap(), bitpos);
        }

        public void setBit(int bitpos) {
            setBitmap(BitUtils.setBit(getBitmap(), bitpos));
        }

        public void setAllBits() {
            setBitmap(BitUtils.setAllBits(getBitmap()));
        }

        public void clearBit(int bitpos) {
            setBitmap(BitUtils.clearBit(getBitmap(), bitpos));
        }

        public void clearAllBits() {
            setBitmap(BitUtils.clearAllBits(getBitmap()));
        }

        public void flipBit(int bitpos) {
            setBitmap(BitUtils.flipBit(getBitmap(), bitpos));
        }

        public void flipAllBits() {
            setBitmap(BitUtils.flipAllBits(getBitmap()));
        }

        public int getNumBitsSet() {
            return BitUtils.getNumBitsSet(getBitmap());
        }

        public int getFirstBitPos() {
            return BitUtils.getFirstBitPos(getBitmap());
        }

        public int getFirstBitPos(int maxbits) {
            return BitUtils.getFirstBitPos(getBitmap(), maxbits);
        }

        public int getLastBitPos() {
            return BitUtils.getLastBitPos(getBitmap());
        }

        public int getLastBitPos(int maxbits) {
            return BitUtils.getLastBitPos(getBitmap(), maxbits);
        }

        public void rotateLeft(int num_bytes_to_rotate) {
            setBitmap(BitUtils.rotateLeft(getBitmap(), num_bytes_to_rotate));
        }

        public void rotateLeft(int maxbytes, int num_bytes_to_rotate) {
            setBitmap(BitUtils.rotateLeft(
                    getBitmap(),
                    maxbytes,
                    num_bytes_to_rotate));
        }

        public void rotateRight(int num_bytes_to_rotate) {
            setBitmap(BitUtils.rotateRight(getBitmap(), num_bytes_to_rotate));
        }

        public void rotateRight(int maxbytes, int num_bytes_to_rotate) {
            setBitmap(BitUtils.rotateRight(
                    getBitmap(),
                    maxbytes,
                    num_bytes_to_rotate));
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

        public void
                iterate(int maxbits, BitUtils.BitmapIterator it, Object var) {
            BitUtils.bitmapIterator(getBitmap(), maxbits, it, var);
        }

        public void iterate(int startbitpos, int endbitpos,
                BitUtils.BitmapIterator it, Object var) {
            BitUtils.bitmapIterator(getBitmap(), startbitpos, endbitpos, it,
                    var);
        }

        public boolean isBitSet(int bitpos) {
            return BitUtils.isBitSet(getBitmap(), bitpos);
        }

        public void setBit(int bitpos) {
            setBitmap(BitUtils.setBit(getBitmap(), bitpos));
        }

        public void setAllBits() {
            setBitmap(BitUtils.setAllBits(getBitmap()));
        }

        public void clearBit(int bitpos) {
            setBitmap(BitUtils.clearBit(getBitmap(), bitpos));
        }

        public void clearAllBits() {
            setBitmap(BitUtils.clearAllBits(getBitmap()));
        }

        public void flipBit(int bitpos) {
            setBitmap(BitUtils.flipBit(getBitmap(), bitpos));
        }

        public void flipAllBits() {
            setBitmap(BitUtils.flipAllBits(getBitmap()));
        }

        public int getNumBitsSet() {
            return BitUtils.getNumBitsSet(getBitmap());
        }

        public int getFirstBitPos() {
            return BitUtils.getFirstBitPos(getBitmap());
        }

        public int getFirstBitPos(int maxbits) {
            return BitUtils.getFirstBitPos(getBitmap(), maxbits);
        }

        public int getLastBitPos() {
            return BitUtils.getLastBitPos(getBitmap());
        }

        public int getLastBitPos(int maxbits) {
            return BitUtils.getLastBitPos(getBitmap(), maxbits);
        }

        public void rotateLeft(int num_bytes_to_rotate) {
            setBitmap(BitUtils.rotateLeft(getBitmap(), num_bytes_to_rotate));
        }

        public void rotateLeft(int maxbytes, int num_bytes_to_rotate) {
            setBitmap(BitUtils.rotateLeft(
                    getBitmap(),
                    maxbytes,
                    num_bytes_to_rotate));
        }

        public void rotateRight(int num_bytes_to_rotate) {
            setBitmap(BitUtils.rotateRight(getBitmap(), num_bytes_to_rotate));
        }

        public void rotateRight(int maxbytes, int num_bytes_to_rotate) {
            setBitmap(BitUtils.rotateRight(
                    getBitmap(),
                    maxbytes,
                    num_bytes_to_rotate));
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

        public void
                iterate(int maxbits, BitUtils.BitmapIterator it, Object var) {
            BitUtils.bitmapIterator(getBitmap(), maxbits, it, var);
        }

        public void iterate(int startbitpos, int endbitpos,
                BitUtils.BitmapIterator it, Object var) {
            BitUtils.bitmapIterator(getBitmap(), startbitpos, endbitpos, it,
                    var);
        }

        public boolean isBitSet(int bitpos) {
            return BitUtils.isBitSet(getBitmap(), bitpos);
        }

        public void setBit(int bitpos) {
            setBitmap(BitUtils.setBit(getBitmap(), bitpos));
        }

        public void setAllBits() {
            setBitmap(BitUtils.setAllBits(getBitmap()));
        }

        public void clearBit(int bitpos) {
            setBitmap(BitUtils.clearBit(getBitmap(), bitpos));
        }

        public void clearAllBits() {
            setBitmap(BitUtils.clearAllBits(getBitmap()));
        }

        public void flipBit(int bitpos) {
            setBitmap(BitUtils.flipBit(getBitmap(), bitpos));
        }

        public void flipAllBits() {
            setBitmap(BitUtils.flipAllBits(getBitmap()));
        }

        public int getNumBitsSet() {
            return BitUtils.getNumBitsSet(getBitmap());
        }

        public int getFirstBitPos() {
            return BitUtils.getFirstBitPos(getBitmap());
        }

        public int getFirstBitPos(int maxbits) {
            return BitUtils.getFirstBitPos(getBitmap(), maxbits);
        }

        public int getLastBitPos() {
            return BitUtils.getLastBitPos(getBitmap());
        }

        public int getLastBitPos(int maxbits) {
            return BitUtils.getLastBitPos(getBitmap(), maxbits);
        }

        public void rotateLeft() {
            setBitmap(BitUtils.rotateLeft(getBitmap(), 1));
        }

        public void rotateRight() {
            setBitmap(BitUtils.rotateRight(getBitmap(), 1));
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

        public void
                iterate(int maxbits, BitUtils.BitmapIterator it, Object var) {
            BitUtils.bitmapIterator(getBitmap(), maxbits, it, var);
        }

        public void iterate(int startbitpos, int endbitpos,
                BitUtils.BitmapIterator it, Object var) {
            BitUtils.bitmapIterator(getBitmap(), startbitpos, endbitpos, it,
                    var);
        }

        public boolean isBitSet(int bitpos) {
            return BitUtils.isBitSet(getBitmap(), bitpos);
        }

        public void setBit(int bitpos) {
            setBitmap(BitUtils.setBit(getBitmap(), bitpos));
        }

        public void setAllBits() {
            setBitmap(BitUtils.setAllBits(getBitmap()));
        }

        public void clearBit(int bitpos) {
            setBitmap(BitUtils.clearBit(getBitmap(), bitpos));
        }

        public void clearAllBits() {
            setBitmap(BitUtils.clearAllBits(getBitmap()));
        }

        public void flipBit(int bitpos) {
            setBitmap(BitUtils.flipBit(getBitmap(), bitpos));
        }

        public void flipAllBits() {
            setBitmap(BitUtils.flipAllBits(getBitmap()));
        }

        public int getNumBitsSet() {
            return BitUtils.getNumBitsSet(getBitmap());
        }

        public int getFirstBitPos() {
            return BitUtils.getFirstBitPos(getBitmap());
        }

        public int getFirstBitPos(int maxbits) {
            return BitUtils.getFirstBitPos(getBitmap(), maxbits);
        }

        public int getLastBitPos() {
            return BitUtils.getLastBitPos(getBitmap());
        }

        public int getLastBitPos(int maxbits) {
            return BitUtils.getLastBitPos(getBitmap(), maxbits);
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

    public static byte setAllBits(byte bitmap) {
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
        long tmpBitmap = ~bitmap;
        long mask;
        if (maxbits == BITUTILS_NUM_BITS_IN_LONG) {
            mask = 0xffffffffffffffffL;
        } else {
            mask = (1L << maxbits) - 1;
        }
        return (tmpBitmap & mask);
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

    private static int getNumBitsSetInternal(long bitmap, int maxbits) {
        int numbits = 0;

        for (int i = 0; i < maxbits; i++) {
            if (isBitSetInternal(bitmap, i)) {
                numbits++;
            }
        }

        return numbits;
    }

    private static boolean isBitSetInternal(long bitmap, int bitpos) {
        return ((bitmap & (1L << bitpos)) == (1L << bitpos));
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

    private static int getFirstBitPosInternal(long bitmask, int maxbits) {
        for (int i = 0; i < maxbits; i++) {
            if (isBitSetInternal(bitmask, i)) {
                return i;
            }
        }
        return -1;
    }

    private static int getLastBitPosInternal(long bitmask, int maxbits) {
        for (int i = maxbits - 1; i >= 0; i--) {
            if (isBitSetInternal(bitmask, i)) {
                return i;
            }
        }
        return -1;
    }

    private static int getFirstBitPosInternal(int bitmask, int maxbits) {
        for (int i = 0; i < maxbits; i++) {
            if (isBitSetInternal(bitmask, i)) {
                return i;
            }
        }
        return -1;
    }

    private static int getLastBitPosInternal(int bitmask, int maxbits) {
        for (int i = maxbits - 1; i >= 0; i--) {
            if (isBitSetInternal(bitmask, i)) {
                return i;
            }
        }
        return -1;
    }

    public static int getFirstBitPos(long bitmask, int maxbits) {
        if (maxbits < 0 || maxbits > BITUTILS_NUM_BITS_IN_LONG) {
            maxbits = BITUTILS_NUM_BITS_IN_LONG;
        }
        return getFirstBitPosInternal(bitmask, maxbits);
    }

    public static int getLastBitPos(long bitmask, int maxbits) {
        if (maxbits < 0 || maxbits > BITUTILS_NUM_BITS_IN_LONG) {
            maxbits = BITUTILS_NUM_BITS_IN_LONG;
        }
        return getLastBitPosInternal(bitmask, maxbits);
    }

    public static int getLastBitPos(long bitmask) {
        return getLastBitPosInternal(bitmask, BITUTILS_NUM_BITS_IN_LONG);
    }

    public static int getFirstBitPos(long bitmask) {
        return getFirstBitPosInternal(bitmask, BITUTILS_NUM_BITS_IN_LONG);
    }

    public static int getFirstBitPos(int bitmask, int maxbits) {
        if (maxbits < 0 || maxbits > BITUTILS_NUM_BITS_IN_INT) {
            maxbits = BITUTILS_NUM_BITS_IN_INT;
        }
        return getFirstBitPosInternal(bitmask, maxbits);
    }

    public static int getFirstBitPos(int bitmask) {
        return getFirstBitPosInternal(bitmask, BITUTILS_NUM_BITS_IN_INT);
    }

    public static int getLastBitPos(int bitmask, int maxbits) {
        if (maxbits < 0 || maxbits > BITUTILS_NUM_BITS_IN_INT) {
            maxbits = BITUTILS_NUM_BITS_IN_INT;
        }
        return getLastBitPosInternal(bitmask, maxbits);
    }

    public static int getLastBitPos(int bitmask) {
        return getLastBitPosInternal(bitmask, BITUTILS_NUM_BITS_IN_INT);
    }

    public static int getFirstBitPos(short bitmask, int maxbits) {
        if (maxbits < 0 || maxbits > BITUTILS_NUM_BITS_IN_SHORT) {
            maxbits = BITUTILS_NUM_BITS_IN_SHORT;
        }
        return getFirstBitPosInternal(bitmask, maxbits);
    }

    public static int getFirstBitPos(short bitmask) {
        return getFirstBitPosInternal(bitmask, BITUTILS_NUM_BITS_IN_SHORT);
    }

    public static int getLastBitPos(short bitmask, int maxbits) {
        if (maxbits < 0 || maxbits > BITUTILS_NUM_BITS_IN_SHORT) {
            maxbits = BITUTILS_NUM_BITS_IN_SHORT;
        }
        return getLastBitPosInternal(bitmask, maxbits);
    }

    public static int getLastBitPos(short bitmask) {
        return getLastBitPosInternal(bitmask, BITUTILS_NUM_BITS_IN_SHORT);
    }

    public static int getFirstBitPos(byte bitmask, int maxbits) {
        if (maxbits < 0 || maxbits > BITUTILS_NUM_BITS_IN_BYTE) {
            maxbits = BITUTILS_NUM_BITS_IN_BYTE;
        }
        return getFirstBitPosInternal(bitmask, maxbits);
    }

    public static int getFirstBitPos(byte bitmask) {
        return getFirstBitPosInternal(bitmask, BITUTILS_NUM_BITS_IN_BYTE);
    }

    public static int getLastBitPos(byte bitmask, int maxbits) {
        if (maxbits < 0 || maxbits > BITUTILS_NUM_BITS_IN_BYTE) {
            maxbits = BITUTILS_NUM_BITS_IN_BYTE;
        }
        return getLastBitPosInternal(bitmask, maxbits);
    }

    public static int getLastBitPos(byte bitmask) {
        return getLastBitPosInternal(bitmask, BITUTILS_NUM_BITS_IN_BYTE);
    }

    static private long rotateLeftInternal(
            long bitmap,
            int maxbits,
            int num_bytes_to_rotate) {
        int tmpMaxBits = maxbits + maxbits % BITUTILS_NUM_BITS_IN_BYTE;
        long tmpBitmap = bitmap;
        if (tmpMaxBits == BITUTILS_NUM_BITS_IN_BYTE) {
            return bitmap;
        }

        if (num_bytes_to_rotate == (tmpMaxBits / BITUTILS_NUM_BITS_IN_BYTE)) {
            return bitmap;
        }

        long bitmask;
        if (tmpMaxBits == BITUTILS_NUM_BITS_IN_LONG) {
            bitmask = 0xffffffffffffffffL;
        } else {
            bitmask = (long) Math.pow(2, tmpMaxBits) - 1L;
        }

        for (int i = 0; i < num_bytes_to_rotate; i++) {
            tmpBitmap =
                    ((tmpBitmap << BITUTILS_NUM_BITS_IN_BYTE) & bitmask)
                            | ((tmpBitmap >> (tmpMaxBits - BITUTILS_NUM_BITS_IN_BYTE)) & (long) 0xFF);
        }

        return tmpBitmap | (bitmap & ~bitmask);
    }

    static private int rotateLeftInternal(
            int bitmap,
            int maxbits,
            int num_bytes_to_rotate) {
        int tmpMaxBits = maxbits + maxbits % BITUTILS_NUM_BITS_IN_BYTE;
        int tmpBitmap = bitmap;

        if (tmpMaxBits == BITUTILS_NUM_BITS_IN_BYTE) {
            return bitmap;
        }

        if (num_bytes_to_rotate % (tmpMaxBits / BITUTILS_NUM_BITS_IN_BYTE) == 0) {
            return bitmap;
        }

        int bitmask;
        if (tmpMaxBits == BITUTILS_NUM_BITS_IN_INT) {
            bitmask = 0xffffffff;
        } else {
            bitmask = (int) Math.pow(2, tmpMaxBits) - 1;
        }

        for (int i = 0; i < num_bytes_to_rotate; i++) {
            tmpBitmap =
                    ((tmpBitmap << BITUTILS_NUM_BITS_IN_BYTE) & bitmask)
                            | ((tmpBitmap >> (tmpMaxBits - BITUTILS_NUM_BITS_IN_BYTE)) & 0xFF);
        }

        return tmpBitmap | (bitmap & ~bitmask);
    }

    static private long rotateRightInternal(
            long bitmap,
            int maxbits,
            int num_bytes_to_rotate) {
        int tmpMaxBits = maxbits + maxbits % BITUTILS_NUM_BITS_IN_BYTE;
        long tmpBitmap = bitmap;
        if (tmpMaxBits == BITUTILS_NUM_BITS_IN_BYTE) {
            return bitmap;
        }

        if (num_bytes_to_rotate % (tmpMaxBits / BITUTILS_NUM_BITS_IN_BYTE) == 0) {
            return bitmap;
        }

        long bitmask;
        if (tmpMaxBits == BITUTILS_NUM_BITS_IN_LONG) {
            bitmask = 0xffffffffffffffffL;
        } else {
            bitmask = (long) Math.pow(2, tmpMaxBits) - 1L;
        }

        for (int i = 0; i < num_bytes_to_rotate; i++) {
            tmpBitmap =
                    (((tmpBitmap & bitmask) >>> BITUTILS_NUM_BITS_IN_BYTE))
                            | ((tmpBitmap & 0xFF) << (tmpMaxBits - BITUTILS_NUM_BITS_IN_BYTE));
        }

        return tmpBitmap | (bitmap & ~bitmask);
    }

    static private int rotateRightInternal(
            int bitmap,
            int maxbits,
            int num_bytes_to_rotate) {
        int tmpMaxBits = maxbits + maxbits % BITUTILS_NUM_BITS_IN_BYTE;
        int tmpBitmap = bitmap;
        if (tmpMaxBits == BITUTILS_NUM_BITS_IN_BYTE) {
            return bitmap;
        }

        if (num_bytes_to_rotate % (tmpMaxBits / BITUTILS_NUM_BITS_IN_BYTE) == 0) {
            return bitmap;
        }

        int bitmask;
        if (tmpMaxBits == BITUTILS_NUM_BITS_IN_INT) {
            bitmask = 0xffffffff;
        } else {
            bitmask = (int) Math.pow(2, tmpMaxBits) - 1;
        }

        for (int i = 0; i < num_bytes_to_rotate; i++) {
            tmpBitmap =
                    (((tmpBitmap & bitmask) >>> BITUTILS_NUM_BITS_IN_BYTE))
                            | ((tmpBitmap & 0xFF) << (tmpMaxBits - BITUTILS_NUM_BITS_IN_BYTE));
        }

        return tmpBitmap | (bitmap & ~bitmask);
    }

    static public long rotateLeft(long bitmap, int num_bytes_to_rotate) {
        if (num_bytes_to_rotate < 0) {
            return bitmap;
        }
        return rotateLeftInternal(
                bitmap,
                BITUTILS_NUM_BITS_IN_LONG,
                num_bytes_to_rotate);
    }

    static public long rotateLeft(
            long bitmap,
            int maxbytes,
            int num_bytes_to_rotate) {
        if (num_bytes_to_rotate < 0) {
            return bitmap;
        }
        if (maxbytes < 0
                || maxbytes > (BITUTILS_NUM_BITS_IN_LONG / BITUTILS_NUM_BITS_IN_BYTE)) {
            return bitmap;
        }
        return rotateLeftInternal(
                bitmap,
                maxbytes * BITUTILS_NUM_BITS_IN_BYTE,
                num_bytes_to_rotate);
    }

    static public int rotateLeft(int bitmap, int num_bytes_to_rotate) {
        if (num_bytes_to_rotate < 0) {
            return bitmap;
        }
        return rotateLeftInternal(
                bitmap,
                BITUTILS_NUM_BITS_IN_INT,
                num_bytes_to_rotate);
    }

    static public int rotateLeft(
            int bitmap,
            int maxbytes,
            int num_bytes_to_rotate) {
        if (num_bytes_to_rotate < 0) {
            return bitmap;
        }
        if (maxbytes < 0
                || maxbytes > (BITUTILS_NUM_BITS_IN_INT / BITUTILS_NUM_BITS_IN_BYTE)) {
            return bitmap;
        }
        return rotateLeftInternal(
                bitmap,
                maxbytes * BITUTILS_NUM_BITS_IN_BYTE,
                num_bytes_to_rotate);
    }

    static public short rotateLeft(short bitmap, int num_bytes_to_rotate) {
        if (num_bytes_to_rotate < 0) {
            return bitmap;
        }
        return (short) rotateLeftInternal(
                bitmap,
                BITUTILS_NUM_BITS_IN_SHORT,
                num_bytes_to_rotate);
    }

    static public short rotateLeft(
            short bitmap,
            int maxbytes,
            int num_bytes_to_rotate) {
        if (num_bytes_to_rotate < 0) {
            return bitmap;
        }
        if (maxbytes != (BITUTILS_NUM_BITS_IN_SHORT / BITUTILS_NUM_BITS_IN_BYTE)) {
            return bitmap;
        }
        return (short) rotateLeftInternal(
                bitmap,
                BITUTILS_NUM_BITS_IN_SHORT,
                num_bytes_to_rotate);
    }

    static public byte rotateLeft(byte bitmap, int num_bytes_to_rotate) {
        return bitmap;
    }

    static public byte rotateLeft(
            byte bitmap,
            int maxbytes,
            int num_bytes_to_rotate) {
        return bitmap;
    }

    static public long rotateRight(long bitmap, int num_bytes_to_rotate) {
        if (num_bytes_to_rotate < 0) {
            return bitmap;
        }
        return rotateRightInternal(
                bitmap,
                BITUTILS_NUM_BITS_IN_LONG,
                num_bytes_to_rotate);
    }

    static public long rotateRight(
            long bitmap,
            int maxbytes,
            int num_bytes_to_rotate) {
        if (num_bytes_to_rotate < 0) {
            return bitmap;
        }
        if (maxbytes < 0
                || maxbytes > (BITUTILS_NUM_BITS_IN_LONG / BITUTILS_NUM_BITS_IN_BYTE)) {
            return bitmap;
        }
        return rotateRightInternal(
                bitmap,
                maxbytes * BITUTILS_NUM_BITS_IN_BYTE,
                num_bytes_to_rotate);
    }

    static public int rotateRight(int bitmap, int num_bytes_to_rotate) {
        if (num_bytes_to_rotate < 0) {
            return bitmap;
        }
        return rotateRightInternal(
                bitmap,
                BITUTILS_NUM_BITS_IN_INT,
                num_bytes_to_rotate);
    }

    static public int rotateRight(
            int bitmap,
            int maxbytes,
            int num_bytes_to_rotate) {
        if (num_bytes_to_rotate < 0) {
            return bitmap;
        }
        if (maxbytes < 0
                || maxbytes > (BITUTILS_NUM_BITS_IN_INT / BITUTILS_NUM_BITS_IN_BYTE)) {
            return bitmap;
        }
        return rotateRightInternal(
                bitmap,
                maxbytes * BITUTILS_NUM_BITS_IN_BYTE,
                num_bytes_to_rotate);
    }

    static public short rotateRight(short bitmap, int num_bytes_to_rotate) {
        if (num_bytes_to_rotate < 0) {
            return bitmap;
        }
        return (short) rotateRightInternal(
                bitmap,
                BITUTILS_NUM_BITS_IN_SHORT,
                num_bytes_to_rotate);
    }

    static public short rotateRight(
            short bitmap,
            int maxbytes,
            int num_bytes_to_rotate) {
        if (num_bytes_to_rotate < 0) {
            return bitmap;
        }
        if (maxbytes != (BITUTILS_NUM_BITS_IN_SHORT / BITUTILS_NUM_BITS_IN_BYTE)) {
            return bitmap;
        }
        return (short) rotateRightInternal(
                bitmap,
                BITUTILS_NUM_BITS_IN_SHORT,
                num_bytes_to_rotate);
    }

    static public byte rotateRight(byte bitmap, int num_bytes_to_rotate) {
        return bitmap;
    }

    static public byte rotateRight(
            byte bitmap,
            int maxbytes,
            int num_bytes_to_rotate) {
        return bitmap;
    }

    private static void bitmapIteratorInternal(long bitmap, int startbitpos,
            int maxbits, BitmapIterator it, Object var) {
        for (int i = startbitpos; i < (startbitpos + maxbits); i++) {
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
            throw new IllegalArgumentException("maxbits:" + maxbits
                    + " : maxbits <= 0 || maxbits > "
                    + BITUTILS_NUM_BITS_IN_LONG);
        }
        bitmapIteratorInternal(bitmap, 0, maxbits, it, var);
    }

    public static void bitmapIterator(long bitmap, int startbitpos,
            int endbitpos, BitmapIterator it, Object var) {
        if (startbitpos < 0 || startbitpos >= BITUTILS_NUM_BITS_IN_LONG
                || endbitpos < 0 || endbitpos >= BITUTILS_NUM_BITS_IN_LONG
                || startbitpos > endbitpos) {
            throw new IllegalArgumentException("startbitpos : " + startbitpos
                    + " : endbitpos: " + endbitpos
                    + " : startbitpos < 0 || startbitpos >= "
                    + BITUTILS_NUM_BITS_IN_LONG
                    + " || endbitpos < 0 || endbitpos >= "
                    + BITUTILS_NUM_BITS_IN_LONG + " || startbitpos > endbitpos");
        }

        bitmapIteratorInternal(bitmap, startbitpos,
                endbitpos - startbitpos + 1, it, var);
    }

    public static void
            bitmapIterator(long bitmap, BitmapIterator it, Object var) {
        bitmapIteratorInternal(bitmap, 0, BITUTILS_NUM_BITS_IN_LONG, it, var);
    }

    public static void bitmapIterator(int bitmap, int maxbits,
            BitmapIterator it, Object var) {
        if (maxbits < 0 || maxbits > BITUTILS_NUM_BITS_IN_INT) {
            throw new IllegalArgumentException("maxbits:" + maxbits
                    + " : maxbits <= 0 || maxbits > "
                    + BITUTILS_NUM_BITS_IN_INT);
        }
        bitmapIteratorInternal(bitmap, 0, maxbits, it, var);
    }

    public static void bitmapIterator(int bitmap, int startbitpos,
            int endbitpos, BitmapIterator it, Object var) {
        if (startbitpos < 0 || startbitpos >= BITUTILS_NUM_BITS_IN_INT
                || endbitpos < 0 || endbitpos >= BITUTILS_NUM_BITS_IN_INT
                || startbitpos > endbitpos) {
            throw new IllegalArgumentException("startbitpos : " + startbitpos
                    + " : endbitpos: " + endbitpos
                    + " : startbitpos < 0 || startbitpos >= "
                    + BITUTILS_NUM_BITS_IN_INT
                    + " || endbitpos < 0 || endbitpos >= "
                    + BITUTILS_NUM_BITS_IN_INT + " || startbitpos > endbitpos");
        }

        bitmapIteratorInternal(bitmap, startbitpos,
                endbitpos - startbitpos + 1, it, var);
    }

    public static void
            bitmapIterator(int bitmap, BitmapIterator it, Object var) {
        bitmapIteratorInternal(bitmap, 0, BITUTILS_NUM_BITS_IN_INT, it, var);
    }

    public static void bitmapIterator(short bitmap, int maxbits,
            BitmapIterator it, Object var) {
        if (maxbits < 0 || maxbits > BITUTILS_NUM_BITS_IN_SHORT) {
            throw new IllegalArgumentException("maxbits:" + maxbits
                    + " : maxbits <= 0 || maxbits > "
                    + BITUTILS_NUM_BITS_IN_SHORT);
        }
        bitmapIteratorInternal(bitmap, 0, maxbits, it, var);
    }

    public static void bitmapIterator(short bitmap, int startbitpos,
            int endbitpos, BitmapIterator it, Object var) {
        if (startbitpos < 0 || startbitpos >= BITUTILS_NUM_BITS_IN_SHORT
                || endbitpos < 0 || endbitpos >= BITUTILS_NUM_BITS_IN_SHORT
                || startbitpos > endbitpos) {
            throw new IllegalArgumentException("startbitpos : " + startbitpos
                    + " : endbitpos: " + endbitpos
                    + " : startbitpos < 0 || startbitpos >= "
                    + BITUTILS_NUM_BITS_IN_SHORT
                    + " || endbitpos < 0 || endbitpos >= "
                    + BITUTILS_NUM_BITS_IN_SHORT
                    + " || startbitpos > endbitpos");
        }

        bitmapIteratorInternal(bitmap, startbitpos,
                endbitpos - startbitpos + 1, it, var);
    }

    public static void bitmapIterator(short bitmap, BitmapIterator it,
            Object var) {
        bitmapIteratorInternal(bitmap, 0, BITUTILS_NUM_BITS_IN_SHORT, it, var);
    }

    public static void bitmapIterator(byte bitmap, int maxbits,
            BitmapIterator it, Object var) {
        if (maxbits < 0 || maxbits > BITUTILS_NUM_BITS_IN_BYTE) {
            throw new IllegalArgumentException("maxbits:" + maxbits
                    + " : maxbits <= 0 || maxbits > "
                    + BITUTILS_NUM_BITS_IN_BYTE);
        }
        bitmapIteratorInternal(bitmap, 0, maxbits, it, var);
    }

    public static void bitmapIterator(byte bitmap, int startbitpos,
            int endbitpos, BitmapIterator it, Object var) {
        if (startbitpos < 0 || startbitpos >= BITUTILS_NUM_BITS_IN_BYTE
                || endbitpos < 0 || endbitpos >= BITUTILS_NUM_BITS_IN_BYTE
                || startbitpos > endbitpos) {
            throw new IllegalArgumentException("startbitpos : " + startbitpos
                    + " : endbitpos: " + endbitpos
                    + " : startbitpos < 0 || startbitpos >= "
                    + BITUTILS_NUM_BITS_IN_BYTE
                    + " || endbitpos < 0 || endbitpos >= "
                    + BITUTILS_NUM_BITS_IN_BYTE + " || startbitpos > endbitpos");
        }

        bitmapIteratorInternal(bitmap, startbitpos,
                endbitpos - startbitpos + 1, it, var);
    }

    public static void
            bitmapIterator(byte bitmap, BitmapIterator it, Object var) {
        bitmapIteratorInternal(bitmap, 0, BITUTILS_NUM_BITS_IN_BYTE, it, var);
    }

    public static long bitwiseAndInternal(long bitmap1, long bitmap2,
            int maxbits) {
        long mask = (1L << maxbits) - 1L;
        long bitmap = (bitmap1 & bitmap2) & mask;

        return ((bitmap1 & ~mask) | bitmap);
    }

    public static long bitwiseAndInternal(long bitmap1, long bitmap2,
            int startbitpos, int endbitpos) {
        long bitmap;
        int maxbits = endbitpos - startbitpos + 1;
        long mask1 = (1L << (startbitpos)) - 1L;
        long mask2 = ((1L << maxbits) - 1L) << (startbitpos);
        long mask3 = ~(mask1 | mask2);

        bitmap = (bitmap1 & bitmap2) & mask2;

        return (((bitmap1 & (mask1 | mask3))) | bitmap);
    }

    public static int bitwiseAndInternal(int bitmap1, int bitmap2, int maxbits) {
        int mask = (1 << maxbits) - 1;
        int bitmap = (bitmap1 & bitmap2) & mask;

        return ((bitmap1 & ~mask) | bitmap);
    }

    public static int bitwiseAndInternal(int bitmap1, int bitmap2,
            int startbitpos, int endbitpos) {
        int bitmap;
        int maxbits = endbitpos - startbitpos + 1;
        int mask1 = (1 << (startbitpos)) - 1;
        int mask2 = ((1 << maxbits) - 1) << (startbitpos);
        int mask3 = ~(mask1 | mask2);

        bitmap = (bitmap1 & bitmap2) & mask2;

        return (((bitmap1 & (mask1 | mask3))) | bitmap);
    }

    public static long bitwiseAnd(long bitmap1, long bitmap2, int maxbits) {
        if (maxbits <= 0 || maxbits > BITUTILS_NUM_BITS_IN_LONG) {
            throw new IllegalArgumentException("maxbits:" + maxbits
                    + " : maxbits <= 0 || maxbits > "
                    + BITUTILS_NUM_BITS_IN_LONG);
        }

        return bitwiseAndInternal(bitmap1, bitmap2, maxbits);
    }

    public static long bitwiseAnd(long bitmap1, long bitmap2, int startbitpos,
            int endbitpos) {
        if (startbitpos < 0 || startbitpos >= BITUTILS_NUM_BITS_IN_LONG
                || endbitpos < 0 || endbitpos >= BITUTILS_NUM_BITS_IN_LONG
                || startbitpos > endbitpos) {
            throw new IllegalArgumentException("startbitpos : " + startbitpos
                    + " : endbitpos: " + endbitpos
                    + " : startbitpos < 0 || startbitpos >= "
                    + BITUTILS_NUM_BITS_IN_LONG
                    + " || endbitpos < 0 || endbitpos >= "
                    + BITUTILS_NUM_BITS_IN_LONG + " || startbitpos > endbitpos");
        }

        return bitwiseAndInternal(bitmap1, bitmap2, startbitpos, endbitpos);
    }

    public static long bitwiseAnd(long bitmap1, long bitmap2) {
        return (bitmap1 & bitmap2);
    }

    public static int bitwiseAnd(int bitmap1, int bitmap2, int maxbits) {
        if (maxbits <= 0 || maxbits > BITUTILS_NUM_BITS_IN_INT) {
            throw new IllegalArgumentException("maxbits:" + maxbits
                    + " : maxbits <= 0 || maxbits > "
                    + BITUTILS_NUM_BITS_IN_INT);
        }

        return bitwiseAndInternal(bitmap1, bitmap2, maxbits);
    }

    public static int bitwiseAnd(int bitmap1, int bitmap2, int startbitpos,
            int endbitpos) {
        if (startbitpos < 0 || startbitpos >= BITUTILS_NUM_BITS_IN_INT
                || endbitpos < 0 || endbitpos >= BITUTILS_NUM_BITS_IN_INT
                || startbitpos > endbitpos) {
            throw new IllegalArgumentException("startbitpos : " + startbitpos
                    + " : endbitpos: " + endbitpos
                    + " : startbitpos < 0 || startbitpos >= "
                    + BITUTILS_NUM_BITS_IN_INT
                    + " || endbitpos < 0 || endbitpos >= "
                    + BITUTILS_NUM_BITS_IN_INT + " || startbitpos > endbitpos");
        }

        return bitwiseAndInternal(bitmap1, bitmap2, startbitpos, endbitpos);
    }

    public static int bitwiseAnd(int bitmap1, int bitmap2) {
        return (bitmap1 & bitmap2);
    }

    public static short bitwiseAnd(short bitmap1, short bitmap2, int maxbits) {
        if (maxbits <= 0 || maxbits > BITUTILS_NUM_BITS_IN_SHORT) {
            throw new IllegalArgumentException("maxbits:" + maxbits
                    + " : maxbits <= 0 || maxbits > "
                    + BITUTILS_NUM_BITS_IN_SHORT);
        }
        return (short) (bitwiseAndInternal((int) bitmap1, (int) bitmap2,
                maxbits) & 0xffff);
    }

    public static short bitwiseAnd(short bitmap1, short bitmap2,
            int startbitpos, int endbitpos) {
        if (startbitpos < 0 || startbitpos >= BITUTILS_NUM_BITS_IN_SHORT
                || endbitpos < 0 || endbitpos >= BITUTILS_NUM_BITS_IN_SHORT
                || startbitpos > endbitpos) {
            throw new IllegalArgumentException("startbitpos : " + startbitpos
                    + " : endbitpos: " + endbitpos
                    + " : startbitpos < 0 || startbitpos >= "
                    + BITUTILS_NUM_BITS_IN_SHORT
                    + " || endbitpos < 0 || endbitpos >= "
                    + BITUTILS_NUM_BITS_IN_SHORT
                    + " || startbitpos > endbitpos");
        }

        return (short) (bitwiseAndInternal((int) bitmap1, (int) bitmap2,
                startbitpos, endbitpos) & 0xffff);
    }

    public static short bitwiseAnd(short bitmap1, short bitmap2) {
        return (short) ((bitmap1 & bitmap2) & 0xffff);
    }

    public static byte bitwiseAnd(byte bitmap1, byte bitmap2, int maxbits) {
        if (maxbits <= 0 || maxbits > BITUTILS_NUM_BITS_IN_BYTE) {
            throw new IllegalArgumentException("maxbits:" + maxbits
                    + " : maxbits <= 0 || maxbits > "
                    + BITUTILS_NUM_BITS_IN_BYTE);
        }
        return (byte) (bitwiseAndInternal((int) bitmap1, (int) bitmap2, maxbits) & 0xff);
    }

    public static byte bitwiseAnd(byte bitmap1, byte bitmap2, int startbitpos,
            int endbitpos) {
        if (startbitpos < 0 || startbitpos >= BITUTILS_NUM_BITS_IN_BYTE
                || endbitpos < 0 || endbitpos >= BITUTILS_NUM_BITS_IN_BYTE
                || startbitpos > endbitpos) {
            throw new IllegalArgumentException("startbitpos : " + startbitpos
                    + " : endbitpos: " + endbitpos
                    + " : startbitpos < 0 || startbitpos >= "
                    + BITUTILS_NUM_BITS_IN_BYTE
                    + " || endbitpos < 0 || endbitpos >= "
                    + BITUTILS_NUM_BITS_IN_BYTE + " || startbitpos > endbitpos");
        }

        return (byte) (bitwiseAndInternal((int) bitmap1, (int) bitmap2,
                startbitpos, endbitpos) & 0xff);
    }

    public static byte bitwiseAnd(byte bitmap1, byte bitmap2) {
        return (byte) ((bitmap1 & bitmap2) & 0xff);
    }

    public static long
            bitwiseOrInternal(long bitmap1, long bitmap2, int maxbits) {
        long mask = (1L << maxbits) - 1L;
        long bitmap = (bitmap1 | bitmap2) & mask;

        return ((bitmap1 & ~mask) | bitmap);
    }

    public static long bitwiseOrInternal(long bitmap1, long bitmap2,
            int startbitpos, int endbitpos) {
        long bitmap;
        int maxbits = endbitpos - startbitpos + 1;
        long mask1 = (1L << (startbitpos)) - 1L;
        long mask2 = ((1L << maxbits) - 1L) << (startbitpos);
        long mask3 = ~(mask1 | mask2);

        bitmap = (bitmap1 | bitmap2) & mask2;

        return (((bitmap1 & (mask1 | mask3))) | bitmap);
    }

    public static int bitwiseOrInternal(int bitmap1, int bitmap2, int maxbits) {
        int mask = (1 << maxbits) - 1;
        int bitmap = (bitmap1 | bitmap2) & mask;

        return ((bitmap1 & ~mask) | bitmap);
    }

    public static int bitwiseOrInternal(int bitmap1, int bitmap2,
            int startbitpos, int endbitpos) {
        int bitmap;
        int maxbits = endbitpos - startbitpos + 1;
        int mask1 = (1 << (startbitpos)) - 1;
        int mask2 = ((1 << maxbits) - 1) << (startbitpos);
        int mask3 = ~(mask1 | mask2);

        bitmap = (bitmap1 | bitmap2) & mask2;

        return (((bitmap1 & (mask1 | mask3))) | bitmap);
    }

    public static long bitwiseOr(long bitmap1, long bitmap2, int maxbits) {
        if (maxbits <= 0 || maxbits > BITUTILS_NUM_BITS_IN_LONG) {
            throw new IllegalArgumentException("maxbits:" + maxbits
                    + " : maxbits <= 0 || maxbits > "
                    + BITUTILS_NUM_BITS_IN_LONG);
        }

        return bitwiseOrInternal(bitmap1, bitmap2, maxbits);
    }

    public static long bitwiseOr(long bitmap1, long bitmap2, int startbitpos,
            int endbitpos) {
        if (startbitpos < 0 || startbitpos >= BITUTILS_NUM_BITS_IN_LONG
                || endbitpos < 0 || endbitpos >= BITUTILS_NUM_BITS_IN_LONG
                || startbitpos > endbitpos) {
            throw new IllegalArgumentException("startbitpos : " + startbitpos
                    + " : endbitpos: " + endbitpos
                    + " : startbitpos < 0 || startbitpos >= "
                    + BITUTILS_NUM_BITS_IN_LONG
                    + " || endbitpos < 0 || endbitpos >= "
                    + BITUTILS_NUM_BITS_IN_LONG + " || startbitpos > endbitpos");
        }

        return bitwiseOrInternal(bitmap1, bitmap2, startbitpos, endbitpos);
    }

    public static long bitwiseOr(long bitmap1, long bitmap2) {
        return (bitmap1 | bitmap2);
    }

    public static int bitwiseOr(int bitmap1, int bitmap2, int maxbits) {
        if (maxbits <= 0 || maxbits > BITUTILS_NUM_BITS_IN_INT) {
            throw new IllegalArgumentException("maxbits:" + maxbits
                    + " : maxbits <= 0 || maxbits > "
                    + BITUTILS_NUM_BITS_IN_INT);
        }

        return bitwiseOrInternal(bitmap1, bitmap2, maxbits);
    }

    public static int bitwiseOr(int bitmap1, int bitmap2, int startbitpos,
            int endbitpos) {
        if (startbitpos < 0 || startbitpos >= BITUTILS_NUM_BITS_IN_INT
                || endbitpos < 0 || endbitpos >= BITUTILS_NUM_BITS_IN_INT
                || startbitpos > endbitpos) {
            throw new IllegalArgumentException("startbitpos : " + startbitpos
                    + " : endbitpos: " + endbitpos
                    + " : startbitpos < 0 || startbitpos >= "
                    + BITUTILS_NUM_BITS_IN_INT
                    + " || endbitpos < 0 || endbitpos >= "
                    + BITUTILS_NUM_BITS_IN_INT + " || startbitpos > endbitpos");
        }

        return bitwiseOrInternal(bitmap1, bitmap2, startbitpos, endbitpos);
    }

    public static int bitwiseOr(int bitmap1, int bitmap2) {
        return (bitmap1 | bitmap2);
    }

    public static short bitwiseOr(short bitmap1, short bitmap2, int maxbits) {
        if (maxbits <= 0 || maxbits > BITUTILS_NUM_BITS_IN_SHORT) {
            throw new IllegalArgumentException("maxbits:" + maxbits
                    + " : maxbits <= 0 || maxbits > "
                    + BITUTILS_NUM_BITS_IN_SHORT);
        }

        return (short) (bitwiseOrInternal(bitmap1, bitmap2, maxbits) & 0xffff);
    }

    public static short bitwiseOr(short bitmap1, short bitmap2,
            int startbitpos, int endbitpos) {
        if (startbitpos < 0 || startbitpos >= BITUTILS_NUM_BITS_IN_SHORT
                || endbitpos < 0 || endbitpos >= BITUTILS_NUM_BITS_IN_SHORT
                || startbitpos > endbitpos) {
            throw new IllegalArgumentException("startbitpos : " + startbitpos
                    + " : endbitpos: " + endbitpos
                    + " : startbitpos < 0 || startbitpos >= "
                    + BITUTILS_NUM_BITS_IN_SHORT
                    + " || endbitpos < 0 || endbitpos >= "
                    + BITUTILS_NUM_BITS_IN_SHORT
                    + " || startbitpos > endbitpos");
        }

        return (short) (bitwiseOrInternal(bitmap1, bitmap2, startbitpos,
                endbitpos) & 0xffff);
    }

    public static short bitwiseOr(short bitmap1, short bitmap2) {
        return (short) ((bitmap1 | bitmap2) & 0xffff);
    }

    public static byte bitwiseOr(byte bitmap1, byte bitmap2, int maxbits) {
        if (maxbits <= 0 || maxbits > BITUTILS_NUM_BITS_IN_BYTE) {
            throw new IllegalArgumentException("maxbits:" + maxbits
                    + " : maxbits <= 0 || maxbits > "
                    + BITUTILS_NUM_BITS_IN_BYTE);
        }

        return (byte) (bitwiseOrInternal(bitmap1, bitmap2, maxbits) & 0xff);
    }

    public static byte bitwiseOr(byte bitmap1, byte bitmap2, int startbitpos,
            int endbitpos) {
        if (startbitpos < 0 || startbitpos >= BITUTILS_NUM_BITS_IN_BYTE
                || endbitpos < 0 || endbitpos >= BITUTILS_NUM_BITS_IN_BYTE
                || startbitpos > endbitpos) {
            throw new IllegalArgumentException("startbitpos : " + startbitpos
                    + " : endbitpos: " + endbitpos
                    + " : startbitpos < 0 || startbitpos >= "
                    + BITUTILS_NUM_BITS_IN_BYTE
                    + " || endbitpos < 0 || endbitpos >= "
                    + BITUTILS_NUM_BITS_IN_BYTE + " || startbitpos > endbitpos");
        }

        return (byte) (bitwiseOrInternal(bitmap1, bitmap2, startbitpos,
                endbitpos) & 0xff);
    }

    public static byte bitwiseOr(byte bitmap1, byte bitmap2) {
        return (byte) ((bitmap1 | bitmap2) & 0xff);
    }

    public static long bitwiseXorInternal(long bitmap1, long bitmap2,
            int maxbits) {
        long mask = (1L << maxbits) - 1L;
        long bitmap = (bitmap1 ^ bitmap2) & mask;

        return ((bitmap1 & ~mask) | bitmap);
    }

    public static long bitwiseXorInternal(long bitmap1, long bitmap2,
            int startbitpos, int endbitpos) {
        long bitmap;
        int maxbits = endbitpos - startbitpos + 1;
        long mask1 = (1L << (startbitpos)) - 1L;
        long mask2 = ((1L << maxbits) - 1L) << (startbitpos);
        long mask3 = ~(mask1 | mask2);

        bitmap = (bitmap1 ^ bitmap2) & mask2;

        return (((bitmap1 & (mask1 | mask3))) | bitmap);
    }

    public static int bitwiseXorInternal(int bitmap1, int bitmap2, int maxbits) {
        int mask = (1 << maxbits) - 1;
        int bitmap = (bitmap1 ^ bitmap2) & mask;

        return ((bitmap1 & ~mask) | bitmap);
    }

    public static int bitwiseXorInternal(int bitmap1, int bitmap2,
            int startbitpos, int endbitpos) {
        int bitmap;
        int maxbits = endbitpos - startbitpos + 1;
        int mask1 = (1 << (startbitpos)) - 1;
        int mask2 = ((1 << maxbits) - 1) << (startbitpos);
        int mask3 = ~(mask1 | mask2);

        bitmap = (bitmap1 ^ bitmap2) & mask2;

        return (((bitmap1 & (mask1 | mask3))) | bitmap);
    }

    public static long bitwiseXor(long bitmap1, long bitmap2, int maxbits) {
        if (maxbits <= 0 || maxbits > BITUTILS_NUM_BITS_IN_LONG) {
            throw new IllegalArgumentException("maxbits:" + maxbits
                    + " : maxbits <= 0 || maxbits > "
                    + BITUTILS_NUM_BITS_IN_LONG);
        }

        return bitwiseXorInternal(bitmap1, bitmap2, maxbits);
    }

    public static long bitwiseXor(long bitmap1, long bitmap2, int startbitpos,
            int endbitpos) {
        if (startbitpos < 0 || startbitpos >= BITUTILS_NUM_BITS_IN_LONG
                || endbitpos < 0 || endbitpos >= BITUTILS_NUM_BITS_IN_LONG
                || startbitpos > endbitpos) {
            throw new IllegalArgumentException("startbitpos : " + startbitpos
                    + " : endbitpos: " + endbitpos
                    + " : startbitpos < 0 || startbitpos >= "
                    + BITUTILS_NUM_BITS_IN_LONG
                    + " || endbitpos < 0 || endbitpos >= "
                    + BITUTILS_NUM_BITS_IN_LONG + " || startbitpos > endbitpos");
        }

        return bitwiseXorInternal(bitmap1, bitmap2, startbitpos, endbitpos);
    }

    public static long bitwiseXor(long bitmap1, long bitmap2) {
        return (bitmap1 ^ bitmap2);
    }

    public static int bitwiseXor(int bitmap1, int bitmap2, int maxbits) {
        if (maxbits <= 0 || maxbits > BITUTILS_NUM_BITS_IN_INT) {
            throw new IllegalArgumentException("maxbits:" + maxbits
                    + " : maxbits <= 0 || maxbits > "
                    + BITUTILS_NUM_BITS_IN_INT);
        }

        return bitwiseXorInternal(bitmap1, bitmap2, maxbits);
    }

    public static int bitwiseXor(int bitmap1, int bitmap2, int startbitpos,
            int endbitpos) {
        if (startbitpos < 0 || startbitpos >= BITUTILS_NUM_BITS_IN_INT
                || endbitpos < 0 || endbitpos >= BITUTILS_NUM_BITS_IN_INT
                || startbitpos > endbitpos) {
            throw new IllegalArgumentException("startbitpos : " + startbitpos
                    + " : endbitpos: " + endbitpos
                    + " : startbitpos < 0 || startbitpos >= "
                    + BITUTILS_NUM_BITS_IN_INT
                    + " || endbitpos < 0 || endbitpos >= "
                    + BITUTILS_NUM_BITS_IN_INT + " || startbitpos > endbitpos");
        }

        return bitwiseXorInternal(bitmap1, bitmap2, startbitpos, endbitpos);
    }

    public static int bitwiseXor(int bitmap1, int bitmap2) {
        return (bitmap1 ^ bitmap2);
    }

    public static short bitwiseXor(short bitmap1, short bitmap2, int maxbits) {
        if (maxbits <= 0 || maxbits > BITUTILS_NUM_BITS_IN_SHORT) {
            throw new IllegalArgumentException("maxbits:" + maxbits
                    + " : maxbits <= 0 || maxbits > "
                    + BITUTILS_NUM_BITS_IN_SHORT);
        }

        return (short) (bitwiseXorInternal(bitmap1, bitmap2, maxbits) & 0xffff);
    }

    public static short bitwiseXor(short bitmap1, short bitmap2,
            int startbitpos, int endbitpos) {
        if (startbitpos < 0 || startbitpos >= BITUTILS_NUM_BITS_IN_SHORT
                || endbitpos < 0 || endbitpos >= BITUTILS_NUM_BITS_IN_SHORT
                || startbitpos > endbitpos) {
            throw new IllegalArgumentException("startbitpos : " + startbitpos
                    + " : endbitpos: " + endbitpos
                    + " : startbitpos < 0 || startbitpos >= "
                    + BITUTILS_NUM_BITS_IN_SHORT
                    + " || endbitpos < 0 || endbitpos >= "
                    + BITUTILS_NUM_BITS_IN_SHORT
                    + " || startbitpos > endbitpos");
        }

        return (short) (bitwiseXorInternal(bitmap1, bitmap2, startbitpos,
                endbitpos) & 0xffff);
    }

    public static short bitwiseXor(short bitmap1, short bitmap2) {
        return (short) ((bitmap1 ^ bitmap2) & 0xffff);
    }

    public static byte bitwiseXor(byte bitmap1, byte bitmap2, int maxbits) {
        if (maxbits <= 0 || maxbits > BITUTILS_NUM_BITS_IN_BYTE) {
            throw new IllegalArgumentException("maxbits:" + maxbits
                    + " : maxbits <= 0 || maxbits > "
                    + BITUTILS_NUM_BITS_IN_BYTE);
        }

        return (byte) (bitwiseXorInternal(bitmap1, bitmap2, maxbits) & 0xff);
    }

    public static byte bitwiseXor(byte bitmap1, byte bitmap2, int startbitpos,
            int endbitpos) {
        if (startbitpos < 0 || startbitpos >= BITUTILS_NUM_BITS_IN_BYTE
                || endbitpos < 0 || endbitpos >= BITUTILS_NUM_BITS_IN_BYTE
                || startbitpos > endbitpos) {
            throw new IllegalArgumentException("startbitpos : " + startbitpos
                    + " : endbitpos: " + endbitpos
                    + " : startbitpos < 0 || startbitpos >= "
                    + BITUTILS_NUM_BITS_IN_BYTE
                    + " || endbitpos < 0 || endbitpos >= "
                    + BITUTILS_NUM_BITS_IN_BYTE + " || startbitpos > endbitpos");
        }

        return (byte) (bitwiseXorInternal(bitmap1, bitmap2, startbitpos,
                endbitpos) & 0xff);
    }

    public static byte bitwiseXor(byte bitmap1, byte bitmap2) {
        return (byte) ((bitmap1 ^ bitmap2) & 0xff);
    }

    public static long bitwiseNotInternal(long bitmap, int maxbits) {
        long mask = (1L << maxbits) - 1L;
        long tmpBitmap = (~bitmap) & mask;

        return ((bitmap & ~mask) | tmpBitmap);
    }

    public static long bitwiseNotInternal(long bitmap, int startbitpos,
            int endbitpos) {
        int maxbits = endbitpos - startbitpos + 1;
        long mask1 = (1L << (startbitpos)) - 1L;
        long mask2 = ((1L << maxbits) - 1L) << startbitpos;
        long mask3 = ~(mask1 | mask2);

        long tmpBitmap = (~bitmap) & mask2;

        return ((bitmap & (mask1 | mask3)) | tmpBitmap);
    }

    public static int bitwiseNotInternal(int bitmap, int maxbits) {
        int mask = (1 << maxbits) - 1;
        int tmpBitmap = (~bitmap) & mask;

        return ((bitmap & ~mask) | tmpBitmap);
    }

    public static int bitwiseNotInternal(int bitmap, int startbitpos,
            int endbitpos) {
        int maxbits = endbitpos - startbitpos + 1;
        int mask1 = (1 << (startbitpos)) - 1;
        int mask2 = ((1 << maxbits) - 1) << startbitpos;
        int mask3 = ~(mask1 | mask2);

        int tmpBitmap = (~bitmap) & mask2;

        return ((bitmap & (mask1 | mask3)) | tmpBitmap);
    }

    public static long bitwiseNot(long bitmap, int maxbits) {
        if (maxbits <= 0 || maxbits > BITUTILS_NUM_BITS_IN_LONG) {
            throw new IllegalArgumentException("maxbits:" + maxbits
                    + " : maxbits <= 0 || maxbits > "
                    + BITUTILS_NUM_BITS_IN_LONG);
        }

        return bitwiseNotInternal(bitmap, maxbits);

    }

    public static long bitwiseNot(long bitmap, int startbitpos, int endbitpos) {
        if (startbitpos < 0 || startbitpos >= BITUTILS_NUM_BITS_IN_LONG
                || endbitpos < 0 || endbitpos >= BITUTILS_NUM_BITS_IN_LONG
                || startbitpos > endbitpos) {
            throw new IllegalArgumentException("startbitpos : " + startbitpos
                    + " : endbitpos: " + endbitpos
                    + " : startbitpos < 0 || startbitpos >= "
                    + BITUTILS_NUM_BITS_IN_LONG
                    + " || endbitpos < 0 || endbitpos >= "
                    + BITUTILS_NUM_BITS_IN_LONG + " || startbitpos > endbitpos");
        }

        return bitwiseNotInternal(bitmap, startbitpos, endbitpos);
    }

    public static long bitwiseNot(long bitmap) {
        return (~bitmap);
    }

    public static int bitwiseNot(int bitmap, int maxbits) {
        if (maxbits <= 0 || maxbits > BITUTILS_NUM_BITS_IN_INT) {
            throw new IllegalArgumentException("maxbits:" + maxbits
                    + " : maxbits <= 0 || maxbits > "
                    + BITUTILS_NUM_BITS_IN_INT);
        }

        return bitwiseNotInternal(bitmap, maxbits);

    }

    public static int bitwiseNot(int bitmap, int startbitpos, int endbitpos) {
        if (startbitpos < 0 || startbitpos >= BITUTILS_NUM_BITS_IN_INT
                || endbitpos < 0 || endbitpos >= BITUTILS_NUM_BITS_IN_INT
                || startbitpos > endbitpos) {
            throw new IllegalArgumentException("startbitpos : " + startbitpos
                    + " : endbitpos: " + endbitpos
                    + " : startbitpos < 0 || startbitpos >= "
                    + BITUTILS_NUM_BITS_IN_INT
                    + " || endbitpos < 0 || endbitpos >= "
                    + BITUTILS_NUM_BITS_IN_INT + " || startbitpos > endbitpos");
        }

        return bitwiseNotInternal(bitmap, startbitpos, endbitpos);
    }

    public static int bitwiseNot(int bitmap) {
        return (~bitmap);
    }

    public static short bitwiseNot(short bitmap, int maxbits) {
        if (maxbits <= 0 || maxbits > BITUTILS_NUM_BITS_IN_SHORT) {
            throw new IllegalArgumentException("maxbits:" + maxbits
                    + " : maxbits <= 0 || maxbits > "
                    + BITUTILS_NUM_BITS_IN_SHORT);
        }

        return (short) (bitwiseNotInternal((int) bitmap, maxbits) & 0xffff);

    }

    public static short
            bitwiseNot(short bitmap, int startbitpos, int endbitpos) {
        if (startbitpos < 0 || startbitpos >= BITUTILS_NUM_BITS_IN_SHORT
                || endbitpos < 0 || endbitpos >= BITUTILS_NUM_BITS_IN_SHORT
                || startbitpos > endbitpos) {
            throw new IllegalArgumentException("startbitpos : " + startbitpos
                    + " : endbitpos: " + endbitpos
                    + " : startbitpos < 0 || startbitpos >= "
                    + BITUTILS_NUM_BITS_IN_SHORT
                    + " || endbitpos < 0 || endbitpos >= "
                    + BITUTILS_NUM_BITS_IN_SHORT
                    + " || startbitpos > endbitpos");
        }

        return (short) (bitwiseNotInternal((int) bitmap, startbitpos, endbitpos) & 0xffff);
    }

    public static short bitwiseNot(short bitmap) {
        return (short) ((~bitmap) & 0xffff);
    }

    public static byte bitwiseNot(byte bitmap, int maxbits) {
        if (maxbits <= 0 || maxbits > BITUTILS_NUM_BITS_IN_BYTE) {
            throw new IllegalArgumentException("maxbits:" + maxbits
                    + " : maxbits <= 0 || maxbits > "
                    + BITUTILS_NUM_BITS_IN_BYTE);
        }

        return (byte) (bitwiseNotInternal((int) bitmap, maxbits) & 0xffff);

    }

    public static byte bitwiseNot(byte bitmap, int startbitpos, int endbitpos) {
        if (startbitpos < 0 || startbitpos >= BITUTILS_NUM_BITS_IN_BYTE
                || endbitpos < 0 || endbitpos >= BITUTILS_NUM_BITS_IN_BYTE
                || startbitpos > endbitpos) {
            throw new IllegalArgumentException("startbitpos : " + startbitpos
                    + " : endbitpos: " + endbitpos
                    + " : startbitpos < 0 || startbitpos >= "
                    + BITUTILS_NUM_BITS_IN_BYTE
                    + " || endbitpos < 0 || endbitpos >= "
                    + BITUTILS_NUM_BITS_IN_BYTE + " || startbitpos > endbitpos");
        }

        return (byte) (bitwiseNotInternal((int) bitmap, startbitpos, endbitpos) & 0xffff);
    }

    public static byte bitwiseNot(byte bitmap) {
        return (byte) ((~bitmap) & 0xff);
    }
}
