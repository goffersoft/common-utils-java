/**
 ** File: OfpSwitchPortConfig.java
 **
 ** Description : OpenFlow Port class
 **               -- OpenFlow Switch Specification Version 1.4.0 - October 14th, 2013
 **
 ** Date           Author                          Comments
 ** 08/31/2013     Prakash Easwar                  Created  
 **/
package com.goffersoft.common.net.openflow;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.log4j.Logger;

import com.goffersoft.common.utils.BitUtils;
import com.goffersoft.common.utils.EndianConversion;
import com.goffersoft.common.utils.ReadUtils;

public class OfpSwitchConfig
        implements
        OfpSerializable<OfpSwitchConfig> {
    private static final Logger log = Logger.getLogger(OfpSwitchConfig.class);

    static public enum OfpSwitchConfigFlags {
        OFPC_FRAG_DROP((short) 1),
        OFPC_FRAG_REASM((short) 2),
        OFPC_UNK((short) 0);

        static final int OFPC_FRAG_MASK = 3;
        static final int OFPC_MASK = 3;

        // No special handling for fragments
        static final int OFPC_FRAG_NORMAL = 0x0;

        static final OfpSwitchConfigFlags flagBit[] = {
                OFPC_FRAG_DROP,
                OFPC_FRAG_REASM,
                OFPC_UNK,
        };

        static final String flagDescr[] = {

                "Drop fragments",
                "Reassemble (only if OFPC_IP_REASM set)",
                "Unknown Flag - User Defined",
        };

        private int flagBitPos;

        private OfpSwitchConfigFlags(short bitpos) {
            this.flagBitPos = bitpos;
        }

        public int getBitPos() {
            return flagBitPos;
        }

        public short getValue() {
            return (short) (1 << flagBitPos);
        }

        public void setValue(int flagBitMask) {
            if (this == OFPC_UNK) {
                int flagBitPos = BitUtils.getFirstBitPos(flagBitMask);
                if (flagBitPos != -1) {
                    this.flagBitPos = flagBitPos;
                }
            }
        }

        public void setBitPos(int flagBitPos) {
            if (this == OFPC_UNK) {
                if (flagBitPos > 0
                        && flagBitPos < BitUtils.BITUTILS_NUM_BITS_IN_SHORT) {
                    this.flagBitPos = flagBitPos;
                }
            }
        }

        public static OfpSwitchConfigFlags getEnum(int flagBitPos) {
            if (flagBitPos < (flagBit.length - 1)) {
                return flagBit[flagBitPos];
            }

            OfpSwitchConfigFlags unk = OFPC_UNK;
            if (flagBitPos < BitUtils.BITUTILS_NUM_BITS_IN_SHORT) {
                unk.setValue(flagBitPos);
            }
            return unk;
        }

        public static int setAllBits() {
            return OFPC_MASK;
        }

        public static void setAllBits(BitUtils.ShortBitmap bitmap) {
            bitmap.setBitmap((short) OFPC_MASK);
        }

        public static int setAllFragBits() {
            return OFPC_FRAG_MASK;
        }

        public static void setAllFragBits(BitUtils.ShortBitmap bitmap) {
            bitmap.setBitmap((short) OFPC_FRAG_MASK);
        }

        public static int clearAllFragBits() {
            return OFPC_FRAG_NORMAL;
        }

        public static void clearAllFragBits(BitUtils.ShortBitmap bitmap) {
            bitmap.setBitmap((short) OFPC_FRAG_NORMAL);
        }

        public String getDescr() {
            if (this == OFPC_UNK) {
                return String.format(
                        "%s : value=%d(%08x)",
                        flagDescr[ordinal()],
                        flagBitPos,
                        flagBitPos);
            } else {
                return flagDescr[ordinal()];
            }
        }

        @Override
        public String toString() {
            return getDescr();
        }
    };

    public static final int OFP_SWITCH_CONFIG_LEN = 4;

    public static final int OFP_SWITCH_CONFIG_MISS_SEND_LEN_OFFSET = 0;
    public static final int OFP_SWITCH_CONFIG_FLAGS_BITMAP_OFFSET = 2;

    public static final short OFP_DEFAULT_MISS_SEND_LEN = 128;
    private static final OfpControllerMaxLenType def_miss_send_len;

    static {
        def_miss_send_len = OfpControllerMaxLenType.OFPCML_CUSTOM_LEN;
        def_miss_send_len.setValue(OFP_DEFAULT_MISS_SEND_LEN);
    };

    private BitUtils.ShortBitmap flagBitmap;

    /*
     * Max bytes of packet that datapath
     * should send to the controller. See
     * ofp_controller_max_len for valid values.
     */
    private OfpControllerMaxLenType miss_send_len;

    OfpSwitchConfig() {
        setMissSendLength(def_miss_send_len);
        flagBitmap.setBitmap((short) OfpSwitchConfigFlags.OFPC_FRAG_NORMAL);
    }

    OfpSwitchConfig(short flagBitmap, OfpControllerMaxLenType max_len) {
        setMissSendLength(max_len);
        setFlagsBitmap(flagBitmap);
    }

    OfpSwitchConfig(
            BitUtils.ShortBitmap flagBitmap,
            OfpControllerMaxLenType max_len) {
        setMissSendLength(max_len);
        setFlagsBitmap(flagBitmap);
    }

    OfpSwitchConfig(OfpControllerMaxLenType max_len) {
        setMissSendLength(max_len);
        flagBitmap.setBitmap((short) OfpSwitchConfigFlags.OFPC_FRAG_NORMAL);
    }

    OfpSwitchConfig(BitUtils.ShortBitmap flagBitmap) {
        setMissSendLength(def_miss_send_len);
        setFlagsBitmap(flagBitmap);
    }

    OfpSwitchConfig(byte[] data) {
        this(data, 0);
    }

    OfpSwitchConfig(byte[] data, int offset) {
        setMissSendLength(readMissSendLength(data, offset));
        setFlagsBitmap(readFlagsBitmap(data, offset));
    }

    OfpSwitchConfig(InputStream is) throws IOException {
        setMissSendLength(readMissSendLength(is));
        setFlagsBitmap(readFlagsBitmap(is));
    }

    public BitUtils.ShortBitmap getFlagsBitmap() {
        return flagBitmap;
    }

    public void setFlagsBitmap(int bmap) {
        if (flagBitmap == null) {
            flagBitmap =
                    new BitUtils.ShortBitmap(
                            (short) (bmap & OfpSwitchConfigFlags.OFPC_MASK));
        } else {
            flagBitmap
                    .setBitmap((short) (bmap & OfpSwitchConfigFlags.OFPC_MASK));
        }
    }

    public void setFlagsBitmap(BitUtils.ShortBitmap bmap) {
        if (flagBitmap == null) {
            flagBitmap = new BitUtils.ShortBitmap((short) 0x0);
        }
        if (bmap == null) {
            return;
        }
        flagBitmap
                .setBitmap((short) (bmap.getBitmap() & OfpSwitchConfigFlags.OFPC_MASK));
    }

    public OfpSwitchConfigFlags[] getFlagsBitSet() {
        int numbits = getFlagsBitmap().getNumBitsSet();

        if (numbits == 0) {
            return null;
        }

        OfpSwitchConfigFlags[] tmp = new OfpSwitchConfigFlags[numbits];

        getFlagsBitmap().iterate(new BitUtils.BitmapIterator() {
            private int i;

            public void executeIfBitIsSet(int bitpos, Object var) {
                OfpSwitchConfigFlags[] tmp = (OfpSwitchConfigFlags[]) var;
                tmp[i++] = OfpSwitchConfigFlags.getEnum(bitpos);
            }

            public void executeIfBitIsNotSet(int bitpos, Object var) {

            }
        }, tmp);

        return tmp;
    }

    public void setFlagsBitSet(OfpSwitchConfigFlags[] bitset) {
        if (bitset == null) {
            setFlagsBitmap(OfpSwitchConfigFlags.setAllBits());
        }

        for (int i = 0; i < bitset.length; i++) {
            if (bitset[i] != null) {
                flagsBitmapSetBit(bitset[i]);
            }
        }
    }

    public void clearFlagsBitSet(OfpSwitchConfigFlags[] bitset) {
        if (bitset == null) {
            setFlagsBitmap(0x0);
        }

        for (int i = 0; i < bitset.length; i++) {
            if (bitset[i] != null) {
                flagsBitmapClearBit(bitset[i]);
            }
        }
    }

    public boolean flagsBitmapIsBitSet(OfpSwitchConfigFlags pfbit) {
        return getFlagsBitmap().isBitSet(pfbit.getBitPos());
    }

    public void flagsBitmapSetBit(OfpSwitchConfigFlags pfbit) {
        getFlagsBitmap().setBit(pfbit.getBitPos());
    }

    public void flagsBitmapClearBit(OfpSwitchConfigFlags pfbit) {
        getFlagsBitmap().clearBit(pfbit.getBitPos());
    }

    public OfpControllerMaxLenType getMissSendLength() {
        return miss_send_len;
    }

    public void setMissSendLength(OfpControllerMaxLenType miss_send_len) {
        this.miss_send_len = miss_send_len;
    }

    @Override
    public OfpSwitchConfig fromByteArray(byte[] data) {
        return fromByteArray(data, 0);
    }

    @Override
    public OfpSwitchConfig fromByteArray(byte[] data, int offset) {
        return new OfpSwitchConfig(data, offset);
    }

    @Override
    public OfpSwitchConfig fromInputStream(InputStream is)
            throws IOException {
        return readFromInputStream(is);
    }

    @Override
    public byte[] toByteArray() {
        byte[] data = new byte[OFP_SWITCH_CONFIG_LEN];
        return toByteArray(data, 0);
    }

    @Override
    public byte[] toByteArray(byte[] data) {
        toByteArray(data, 0);
        return null;
    }

    @Override
    public byte[] toByteArray(byte[] data, int offset) {
        data = writeFlagsBitmap(data, offset);
        data = writeMissSendLength(data, offset);
        return data;
    }

    @Override
    public OutputStream toOutputStream(OutputStream os) throws IOException {
        writeFlagsBitmap(os);
        writeMissSendLength(os);
        return os;
    }

    public byte[] writeMissSendLength(byte[] data) {
        return writeFlagsBitmap(data, 0);
    }

    public byte[] writeMissSendLength(byte[] data, int offset) {
        EndianConversion.shortToByteArrayBE(data, offset
                + OFP_SWITCH_CONFIG_MISS_SEND_LEN_OFFSET, getMissSendLength()
                .getValue());

        return data;
    }

    public OutputStream writeMissSendLength(OutputStream os) throws IOException {
        EndianConversion
                .shortToOutputStreamBE(os, getMissSendLength().getValue());
        return os;
    }

    public byte[] writeFlagsBitmap(byte[] data) {
        return writeFlagsBitmap(data, 0);
    }

    public byte[] writeFlagsBitmap(byte[] data, int offset) {
        EndianConversion
                .shortToByteArrayBE(
                        data,
                        offset
                                + OFP_SWITCH_CONFIG_FLAGS_BITMAP_OFFSET,
                        getFlagsBitmap()
                                .getBitmap());

        return data;
    }

    public OutputStream writeFlagsBitmap(OutputStream os) throws IOException {
        EndianConversion
                .shortToOutputStreamBE(os, getFlagsBitmap().getBitmap());
        return os;
    }

    public static byte[] readOfpSwitchConfig(InputStream is) throws IOException {
        return readOfpSwitchConfig(is, null, 0);
    }

    public static byte[] readOfpSwitchConfig(InputStream is, byte[] data)
            throws IOException {
        return readOfpSwitchConfig(is, data, 0);
    }

    public static byte[] readOfpSwitchConfig(
            InputStream is,
            byte[] data,
            int offset)
            throws IOException {
        data = ReadUtils.readInput(is, data, offset, Short.BYTES);
        data =
                ReadUtils.readInput(is, data, offset
                        + OFP_SWITCH_CONFIG_FLAGS_BITMAP_OFFSET, Short.BYTES);
        return data;
    }

    public static OfpSwitchConfig readFromInputStream(InputStream is)
            throws IOException {
        return new OfpSwitchConfig(is);
    }

    public static OfpControllerMaxLenType readMissSendLength(InputStream is)
            throws IOException {
        return OfpControllerMaxLenType.getEnum(
                EndianConversion.inputStreamToShortBE(is));
    }

    public static OfpControllerMaxLenType readMissSendLength(byte[] data) {
        return readMissSendLength(data, 0);
    }

    public static OfpControllerMaxLenType readMissSendLength(
            byte[] data,
            int offset) {
        return OfpControllerMaxLenType.getEnum(EndianConversion
                .byteArrayToShortBE(
                        data,
                        offset + OFP_SWITCH_CONFIG_MISS_SEND_LEN_OFFSET));
    }

    public static BitUtils.ShortBitmap readFlagsBitmap(InputStream is)
            throws IOException {
        return new BitUtils.ShortBitmap(EndianConversion.inputStreamToShortBE(
                is));
    }

    public static BitUtils.ShortBitmap readFlagsBitmap(byte[] data) {
        return readFlagsBitmap(data, 0);
    }

    public static BitUtils.ShortBitmap readFlagsBitmap(byte[] data, int offset) {
        return new BitUtils.ShortBitmap(EndianConversion.byteArrayToShortBE(
                data,
                offset + OFP_SWITCH_CONFIG_FLAGS_BITMAP_OFFSET));
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();

        sb
                .append(String
                        .format(
                                "MissSendLen=%s(%d)\n",
                                getMissSendLength().toString(),
                                getMissSendLength().getValue()));

        sb.append(String.format("Flag Bitmap %s\n", getFlagsBitmap()
                .toString()));

        getFlagsBitmap().iterate(new BitUtils.BitmapIterator() {
            private int i;

            public void executeIfBitIsSet(int bitpos, Object var) {
                StringBuffer sb = (StringBuffer) var;
                sb.append(String.format(
                        "[%d] : %s\n",
                        i++,
                        OfpSwitchConfigFlags.getEnum(bitpos).toString()));
            }

            public void executeIfBitIsNotSet(int bitpos, Object var) {

            }
        },
                sb);

        return sb.toString();
    }

    public boolean equals(OfpSwitchConfig hdr) {
        if ((getMissSendLength() == hdr.getMissSendLength())
                && (getFlagsBitmap() == hdr.getFlagsBitmap())) {
            return true;
        }
        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o instanceof OfpSwitchConfig) {
            return equals((OfpSwitchConfig) o);
        }
        return false;
    }
}
