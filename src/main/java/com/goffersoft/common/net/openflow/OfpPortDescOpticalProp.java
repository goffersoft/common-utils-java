/**
 ** File: OfpPortDescOpticalProp.java
 **
 ** Description : OpenFlow Optical Port Properties class
 **               (base class for all port properties classes)
 **               -- OpenFlow Switch Specification Version 1.1.0 - February 28th, 2011
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

public class OfpPortDescOpticalProp
        extends
        OfpPortDescGenericProp {

    private static final Logger log = Logger
            .getLogger(OfpPortDescOpticalProp.class);

    static public enum OfpPortFeatures {
        OFPOPF_RX_TUNE(0),
        OFPOPF_TX_TUNE(1),
        OFPOPF_TX_PWR(2),
        OFPOPF_USE_FREQ(3),
        OFPOPF_UNK(0), ;

        public static final OfpPortFeatures pfBit[] = {
                OFPOPF_RX_TUNE,
                OFPOPF_TX_TUNE,
                OFPOPF_TX_PWR,
                OFPOPF_USE_FREQ,
                OFPOPF_UNK,
        };

        public static final String pfDescr[] = {
                "Receiver is tunable",
                "Transmit is tunable",
                "Power is configurable",
                "Use Frequency, not wavelength",
                "Unknown Value - User defined",
        };

        private int pfBitPos;

        private OfpPortFeatures(int pfBitPos) {
            setValueInternal(pfBitPos);
        }

        public int getValue() {
            return (1 << pfBitPos);
        }

        public int getBitPos() {
            return pfBitPos;
        }

        private void setValueInternal(int pfBitPos) {
            this.pfBitPos = pfBitPos;
        }

        public void setValue(int pfBitMask) {
            if (this == OFPOPF_UNK) {
                int pfBitPos = BitUtils.getFirstBitPos(pfBitMask);
                if (pfBitPos != -1) {
                    this.pfBitPos = pfBitPos;
                }
            }
        }

        public void setBitPos(int pfBitPos) {
            if (this == OFPOPF_UNK) {
                if (pfBitPos > 0
                        && pfBitPos < BitUtils.BITUTILS_NUM_BITS_IN_INT) {
                    this.pfBitPos = pfBitPos;
                }
            }
        }

        public static OfpPortFeatures getEnum(int pfBitPos) {
            if (pfBitPos < (pfBit.length - 1)) {
                return pfBit[pfBitPos];
            }

            OfpPortFeatures unk = OFPOPF_UNK;
            unk.setValue(pfBitPos);
            return unk;
        }

        public String getDescr() {
            if (this == OFPOPF_UNK) {
                return String.format("%s : bit=%d(%08x)", pfDescr[ordinal()],
                        (1 << pfBitPos), (1 << pfBitPos));
            } else {
                return pfDescr[ordinal()];
            }
        }

        @Override
        public String toString() {
            return getDescr();
        }
    }

    public static final int OFP_PORT_DESC_OPTICAL_HDR_LEN = 40;
    public static final int OFP_PORT_DESC_OPTICAL_PAD_OFFSET = 0;
    public static final int OFP_PORT_DESC_OPTICAL_PAD_LEN = 4;
    public static final int OFP_PORT_DESC_OPTICAL_SUPPORTED_BITMAP_OFFSET = 4;
    public static final int OFP_PORT_DESC_OPTICAL_TX_MIN_FREQ_LMDA_OFFSET = 8;
    public static final int OFP_PORT_DESC_OPTICAL_TX_MAX_FREQ_LMDA_OFFSET = 12;
    public static final int OFP_PORT_DESC_OPTICAL_TX_GRID_FREQ_LMDA_OFFSET = 16;
    public static final int OFP_PORT_DESC_OPTICAL_RX_MIN_FREQ_LMDA_OFFSET = 20;
    public static final int OFP_PORT_DESC_OPTICAL_RX_MAX_FREQ_LMDA_OFFSET = 24;
    public static final int OFP_PORT_DESC_OPTICAL_RX_GRID_FREQ_LMDA_OFFSET = 28;
    public static final int OFP_PORT_DESC_OPTICAL_TX_PWR_MIN_OFFSET = 32;
    public static final int OFP_PORT_DESC_OPTICAL_TX_PWR_MAX_OFFSET = 36;

    // 4 byte padding
    private BitUtils.IntBitmap supportedBitmap;
    private int txMinFreqLmda;
    private int txMaxFreqLmda;
    private int txGridFreqLmda;
    private int rxMinFreqLmda;
    private int rxMaxFreqLmda;
    private int rxGridFreqLmda;
    private int txPwrMin;
    private int txPwrMax;

    public OfpPortDescOpticalProp() {
        this(0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0);
    }

    public OfpPortDescOpticalProp(int txMinFreqLmda, int txMaxFreqLmda,
            int txGridFreqLmda, int rxMinFreqLmda, int rxMaxFreqLmda,
            int rxGridFreqLmda, int txPwrMin, int txPwrMax) {
        this(0x0, txMinFreqLmda, txMaxFreqLmda, txGridFreqLmda, rxMinFreqLmda,
                rxMaxFreqLmda, rxGridFreqLmda, txPwrMin, txPwrMax);
    }

    public OfpPortDescOpticalProp(int supportedBitmap, int txMinFreqLmda,
            int txMaxFreqLmda, int txGridFreqLmda, int rxMinFreqLmda,
            int rxMaxFreqLmda, int rxGridFreqLmda, int txPwrMin, int txPwrMax) {
        super(
                OfpPortDescPropType.OFPPDPT_OPTICAL,
                (short) (OFP_PORT_DESC_PROP_BASE_LEN + OFP_PORT_DESC_OPTICAL_HDR_LEN));
        setSupportedBitmap(supportedBitmap);
        setTxMinFreqLmda(txMinFreqLmda);
        setTxMaxFreqLmda(txMaxFreqLmda);
        setTxGridFreqLmda(txGridFreqLmda);
        setRxMinFreqLmda(rxMinFreqLmda);
        setRxMaxFreqLmda(rxMaxFreqLmda);
        setRxGridFreqLmda(rxGridFreqLmda);
        setTxPwrMin(txPwrMin);
        setTxPwrMax(txPwrMax);
    }

    public OfpPortDescOpticalProp(BitUtils.IntBitmap supportedBitmap,
            int txMinFreqLmda, int txMaxFreqLmda, int txGridFreqLmda,
            int rxMinFreqLmda, int rxMaxFreqLmda, int rxGridFreqLmda,
            int txPwrMin, int txPwrMax) {
        super(
                OfpPortDescPropType.OFPPDPT_OPTICAL,
                (short) (OFP_PORT_DESC_PROP_BASE_LEN + OFP_PORT_DESC_OPTICAL_HDR_LEN));
        setSupportedBitmap(supportedBitmap);
        setTxMinFreqLmda(txMinFreqLmda);
        setTxMaxFreqLmda(txMaxFreqLmda);
        setTxGridFreqLmda(txGridFreqLmda);
        setRxMinFreqLmda(rxMinFreqLmda);
        setRxMaxFreqLmda(rxMaxFreqLmda);
        setRxGridFreqLmda(rxGridFreqLmda);
        setTxPwrMin(txPwrMin);
        setTxPwrMax(txPwrMax);
    }

    public OfpPortDescOpticalProp(byte[] data) {
        this(data, 0);
    }

    public OfpPortDescOpticalProp(byte[] data, int offset) {
        super(data, offset);

        if (getType() != OfpPortDescPropType.OFPPDPT_OPTICAL) {
            throw new IllegalArgumentException("Bad Data : Expected  : 0x"
                    + Integer.toHexString(OfpPortDescPropType.OFPPDPT_OPTICAL
                            .getValue()) + " Got : 0x"
                    + Integer.toHexString(getType().getValue()));
        }

        setSupportedBitmap(readSupportedBitmap(data, offset));
        setTxMinFreqLmda(readTxMinFreqLmda(data, offset));
        setTxMaxFreqLmda(readTxMaxFreqLmda(data, offset));
        setTxGridFreqLmda(readTxGridFreqLmda(data, offset));
        setRxMinFreqLmda(readRxMinFreqLmda(data, offset));
        setRxMaxFreqLmda(readRxMaxFreqLmda(data, offset));
        setRxGridFreqLmda(readRxGridFreqLmda(data, offset));
        setTxPwrMin(readTxPwrMin(data, offset));
        setTxPwrMax(readTxPwrMax(data, offset));
    }

    public OfpPortDescOpticalProp(InputStream is) throws IOException {
        super(is);
        if (getType() != OfpPortDescPropType.OFPPDPT_OPTICAL) {
            throw new IllegalArgumentException("Bad Data : Expected  : 0x"
                    + Integer.toHexString(OfpPortDescPropType.OFPPDPT_OPTICAL
                            .getValue()) + " Got : 0x"
                    + Integer.toHexString(getType().getValue()));
        }
        setSupportedBitmap(readSupportedBitmap(is));
        setTxMinFreqLmda(readTxMinFreqLmda(is));
        setTxMaxFreqLmda(readTxMaxFreqLmda(is));
        setTxGridFreqLmda(readTxGridFreqLmda(is));
        setRxMinFreqLmda(readRxMinFreqLmda(is));
        setRxMaxFreqLmda(readRxMaxFreqLmda(is));
        setRxGridFreqLmda(readRxGridFreqLmda(is));
        setTxPwrMin(readTxPwrMin(is));
        setTxPwrMax(readTxPwrMax(is));
    }

    public OfpPortDescOpticalProp(OfpPortDescGenericProp base, InputStream is)
            throws IOException {
        super(base);
        if (getType() != OfpPortDescPropType.OFPPDPT_OPTICAL) {
            throw new IllegalArgumentException("Bad Data : Expected  : 0x"
                    + Integer.toHexString(OfpPortDescPropType.OFPPDPT_OPTICAL
                            .getValue()) + " Got : 0x"
                    + Integer.toHexString(getType().getValue()));
        }
        setSupportedBitmap(readSupportedBitmap(is));
        setTxMinFreqLmda(readTxMinFreqLmda(is));
        setTxMaxFreqLmda(readTxMaxFreqLmda(is));
        setTxGridFreqLmda(readTxGridFreqLmda(is));
        setRxMinFreqLmda(readRxMinFreqLmda(is));
        setRxMaxFreqLmda(readRxMaxFreqLmda(is));
        setRxGridFreqLmda(readRxGridFreqLmda(is));
        setTxPwrMin(readTxPwrMin(is));
        setTxPwrMax(readTxPwrMax(is));
    }

    public int getTxMinFreqLmda() {
        return txMinFreqLmda;
    }

    public void setTxMinFreqLmda(int txMinFreqLmda) {
        this.txMinFreqLmda = txMinFreqLmda;
    }

    public int getTxMaxFreqLmda() {
        return txMaxFreqLmda;
    }

    public void setTxMaxFreqLmda(int txMaxFreqLmda) {
        this.txMaxFreqLmda = txMaxFreqLmda;
    }

    public int getTxGridFreqLmda() {
        return txGridFreqLmda;
    }

    public void setTxGridFreqLmda(int txGridFreqLmda) {
        this.txGridFreqLmda = txGridFreqLmda;
    }

    public int getRxMinFreqLmda() {
        return rxMinFreqLmda;
    }

    public void setRxMinFreqLmda(int rxMinFreqLmda) {
        this.rxMinFreqLmda = rxMinFreqLmda;
    }

    public int getRxMaxFreqLmda() {
        return rxMaxFreqLmda;
    }

    public void setRxMaxFreqLmda(int rxMaxFreqLmda) {
        this.rxMaxFreqLmda = rxMaxFreqLmda;
    }

    public int getRxGridFreqLmda() {
        return rxGridFreqLmda;
    }

    public void setRxGridFreqLmda(int rxGridFreqLmda) {
        this.rxGridFreqLmda = rxGridFreqLmda;
    }

    public int getTxPwrMin() {
        return txPwrMin;
    }

    public void setTxPwrMin(int txPwrMin) {
        this.txPwrMin = txPwrMin;
    }

    public int getTxPwrMax() {
        return txPwrMax;
    }

    public void setTxPwrMax(int txPwrMax) {
        this.txPwrMax = txPwrMax;
    }

    public BitUtils.IntBitmap getSupportedBitmap() {
        return supportedBitmap;
    }

    public void setSupportedBitmap(int bmap) {
        if (supportedBitmap == null) {
            supportedBitmap = new BitUtils.IntBitmap(bmap);
        } else {
            supportedBitmap.setBitmap(bmap);
        }
    }

    public void setSupportedBitmap(BitUtils.IntBitmap bmap) {
        if (bmap == null) {
            if (supportedBitmap == null) {
                supportedBitmap = new BitUtils.IntBitmap(0x0);
            }
            return;
        }
        supportedBitmap = bmap;
    }

    public OfpPortFeatures[] getSupportedBitSet() {
        int numbits = getSupportedBitmap().getNumBitsSet();

        if (numbits == 0) {
            return null;
        }

        OfpPortFeatures[] tmp = new OfpPortFeatures[numbits];

        getSupportedBitmap().iterate(new BitUtils.BitmapIterator() {
            private int i;

            public void executeIfBitIsSet(int bitpos, Object var) {
                OfpPortFeatures[] tmp = (OfpPortFeatures[]) var;
                tmp[i++] = OfpPortFeatures.getEnum(bitpos);
            }

            public void executeIfBitIsNotSet(int bitpos, Object var) {

            }
        }, tmp);

        return tmp;
    }

    public boolean supportedBitmapIsBitSet(OfpPortFeatures pfbit) {
        return getSupportedBitmap().isBitSet(pfbit.getBitPos());
    }

    public void supportedBitmapSetBit(OfpPortFeatures pfbit) {
        getSupportedBitmap().setBit(pfbit.getBitPos());
    }

    public void supportedBitmapClearBit(OfpPortFeatures pfbit) {
        getSupportedBitmap().clearBit(pfbit.getBitPos());
    }

    @Override
    public byte[] toByteArray() {
        byte[] prophdr = new byte[OFP_PORT_DESC_OPTICAL_HDR_LEN
                + OFP_PORT_DESC_PROP_BASE_LEN];
        return toByteArray(prophdr, 0);
    }

    @Override
    public byte[] toByteArray(byte[] data) {
        return toByteArray(data, 0);
    }

    @Override
    public byte[] toByteArray(byte[] data, int offset) {
        super.toByteArray(data, offset);

        writeSupportedBitmap(data, offset);
        writeTxMinFreqLmda(data, offset);
        writeTxMaxFreqLmda(data, offset);
        writeTxGridFreqLmda(data, offset);
        writeRxMinFreqLmda(data, offset);
        writeRxMaxFreqLmda(data, offset);
        writeRxGridFreqLmda(data, offset);
        writeTxPwrMin(data, offset);
        writeTxPwrMax(data, offset);

        return data;
    }

    @Override
    public OutputStream toOutputStream(OutputStream os) throws IOException {
        super.toOutputStream(os);

        writeSupportedBitmap(os);
        writeTxMinFreqLmda(os);
        writeTxMaxFreqLmda(os);
        writeTxGridFreqLmda(os);
        writeRxMinFreqLmda(os);
        writeRxMaxFreqLmda(os);
        writeRxGridFreqLmda(os);
        writeTxPwrMin(os);
        writeTxPwrMax(os);

        return os;
    }

    @Override
    public OfpPortDescOpticalProp fromInputStream(InputStream is)
            throws IOException {
        return readFromInputStream(is);
    }

    @Override
    public OfpPortDescOpticalProp fromByteArray(byte[] data) {
        return new OfpPortDescOpticalProp(data);
    }

    @Override
    public OfpPortDescOpticalProp fromByteArray(byte[] data, int offset) {
        return new OfpPortDescOpticalProp(data, offset);
    }

    public byte[] writeSupportedBitmap(byte[] data) {
        return writeSupportedBitmap(data, 0);
    }

    public byte[] writeSupportedBitmap(byte[] data, int offset) {
        writePadding(data, offset + OFP_PORT_DESC_OPTICAL_PAD_OFFSET,
                OFP_PORT_DESC_OPTICAL_PAD_LEN);
        EndianConversion.intToByteArrayBE(data, offset
                + OFP_PORT_DESC_PROP_BASE_LEN
                + OFP_PORT_DESC_OPTICAL_SUPPORTED_BITMAP_OFFSET,
                getSupportedBitmap().getBitmap());

        return data;
    }

    public OutputStream writeSupportedBitmap(OutputStream os)
            throws IOException {
        writePadding(os, OFP_PORT_DESC_OPTICAL_PAD_LEN);
        EndianConversion.intToOutputStreamBE(os, getSupportedBitmap()
                .getBitmap());
        return os;
    }

    public byte[] writeTxMinFreqLmda(byte[] data) {
        return writeTxMinFreqLmda(data, 0);
    }

    public byte[] writeTxMinFreqLmda(byte[] data, int offset) {
        EndianConversion.intToByteArrayBE(data, offset
                + OFP_PORT_DESC_PROP_BASE_LEN
                + OFP_PORT_DESC_OPTICAL_TX_MIN_FREQ_LMDA_OFFSET,
                getTxMinFreqLmda());
        return data;
    }

    public OutputStream writeTxMinFreqLmda(OutputStream os) throws IOException {
        EndianConversion.intToOutputStreamBE(os, getTxMinFreqLmda());
        return os;
    }

    public byte[] writeTxMaxFreqLmda(byte[] data) {
        return writeTxMaxFreqLmda(data, 0);
    }

    public byte[] writeTxMaxFreqLmda(byte[] data, int offset) {
        EndianConversion.intToByteArrayBE(data, offset
                + OFP_PORT_DESC_PROP_BASE_LEN
                + OFP_PORT_DESC_OPTICAL_TX_MAX_FREQ_LMDA_OFFSET,
                getTxMaxFreqLmda());
        return data;
    }

    public OutputStream writeTxMaxFreqLmda(OutputStream os) throws IOException {
        EndianConversion.intToOutputStreamBE(os, getTxMaxFreqLmda());
        return os;
    }

    public byte[] writeTxGridFreqLmda(byte[] data) {
        return writeTxGridFreqLmda(data, 0);
    }

    public byte[] writeTxGridFreqLmda(byte[] data, int offset) {
        EndianConversion.intToByteArrayBE(data, offset
                + OFP_PORT_DESC_PROP_BASE_LEN
                + OFP_PORT_DESC_OPTICAL_TX_GRID_FREQ_LMDA_OFFSET,
                getTxGridFreqLmda());
        return data;
    }

    public OutputStream writeTxGridFreqLmda(OutputStream os) throws IOException {
        EndianConversion.intToOutputStreamBE(os, getTxGridFreqLmda());
        return os;
    }

    public byte[] writeRxMinFreqLmda(byte[] data) {
        return writeRxMinFreqLmda(data, 0);
    }

    public byte[] writeRxMinFreqLmda(byte[] data, int offset) {
        EndianConversion.intToByteArrayBE(data, offset
                + OFP_PORT_DESC_PROP_BASE_LEN
                + OFP_PORT_DESC_OPTICAL_RX_MIN_FREQ_LMDA_OFFSET,
                getRxMinFreqLmda());
        return data;
    }

    public OutputStream writeRxMinFreqLmda(OutputStream os) throws IOException {
        EndianConversion.intToOutputStreamBE(os, getRxMinFreqLmda());
        return os;
    }

    public byte[] writeRxMaxFreqLmda(byte[] data) {
        return writeRxMaxFreqLmda(data, 0);
    }

    public byte[] writeRxMaxFreqLmda(byte[] data, int offset) {
        EndianConversion.intToByteArrayBE(data, offset
                + OFP_PORT_DESC_PROP_BASE_LEN
                + OFP_PORT_DESC_OPTICAL_RX_MAX_FREQ_LMDA_OFFSET,
                getRxMaxFreqLmda());
        return data;
    }

    public OutputStream writeRxMaxFreqLmda(OutputStream os) throws IOException {
        EndianConversion.intToOutputStreamBE(os, getRxMaxFreqLmda());
        return os;
    }

    public byte[] writeRxGridFreqLmda(byte[] data) {
        return writeRxGridFreqLmda(data, 0);
    }

    public byte[] writeRxGridFreqLmda(byte[] data, int offset) {
        EndianConversion.intToByteArrayBE(data, offset
                + OFP_PORT_DESC_PROP_BASE_LEN
                + OFP_PORT_DESC_OPTICAL_RX_GRID_FREQ_LMDA_OFFSET,
                getRxGridFreqLmda());
        return data;
    }

    public OutputStream writeRxGridFreqLmda(OutputStream os) throws IOException {
        EndianConversion.intToOutputStreamBE(os, getRxGridFreqLmda());
        return os;
    }

    public byte[] writeTxPwrMin(byte[] data) {
        return writeTxPwrMin(data, 0);
    }

    public byte[] writeTxPwrMin(byte[] data, int offset) {
        EndianConversion.intToByteArrayBE(data, offset
                + OFP_PORT_DESC_PROP_BASE_LEN
                + OFP_PORT_DESC_OPTICAL_TX_PWR_MIN_OFFSET, getTxPwrMin());
        return data;
    }

    public OutputStream writeTxPwrMin(OutputStream os) throws IOException {
        EndianConversion.intToOutputStreamBE(os, getTxPwrMin());
        return os;
    }

    public byte[] writeTxPwrMax(byte[] data) {
        return writeTxPwrMax(data, 0);
    }

    public byte[] writeTxPwrMax(byte[] data, int offset) {
        EndianConversion.intToByteArrayBE(data, offset
                + OFP_PORT_DESC_PROP_BASE_LEN
                + OFP_PORT_DESC_OPTICAL_TX_PWR_MAX_OFFSET, getTxPwrMax());
        return data;
    }

    public OutputStream writeTxPwrMax(OutputStream os) throws IOException {
        EndianConversion.intToOutputStreamBE(os, getTxPwrMax());
        return os;
    }

    public static byte[] readOfpPortDescOpticalProp(InputStream is)
            throws IOException {
        return readOfpPortDescOpticalProp(is, null, 0);
    }

    public static
            byte[]
            readOfpPortDescOpticalProp(InputStream is, byte[] data)
                    throws IOException {
        return readOfpPortDescOpticalProp(is, data, 0);
    }

    public static byte[] readOfpPortDescOpticalProp(InputStream is,
            byte[] data, int offset) throws IOException {
        byte[] tmpData = OfpPortDescGenericProp.readOfpPortDescGenericProp(is,
                data, offset);
        int tmpOffset = ((tmpData != data) ? 0 : offset);
        OfpPortDescPropType type = readType(tmpData, tmpOffset);
        short len = readLength(tmpData, tmpOffset);

        if (type != OfpPortDescPropType.OFPPDPT_OPTICAL) {
            throw new IOException("Bad Data : Expected  : 0x"
                    + Integer.toHexString(OfpPortDescPropType.OFPPDPT_OPTICAL
                            .getValue()) + " Got : 0x"
                    + Integer.toHexString(type.getValue()));
        }
        byte[] tmp1Data = ReadUtils.readInput(is, tmpData, tmpOffset
                + OFP_PORT_DESC_PROP_BASE_LEN, len);
        if (tmp1Data != tmpData) {
            System.arraycopy(tmpData, tmpOffset, tmp1Data, 0,
                    OFP_PORT_DESC_PROP_BASE_LEN);
            return tmp1Data;
        }
        return tmpData;
    }

    public static OfpPortDescOpticalProp readFromInputStream(InputStream is)
            throws IOException {
        return new OfpPortDescOpticalProp(is);
    }

    public static BitUtils.IntBitmap readSupportedBitmap(byte[] portdescprop) {
        return readSupportedBitmap(portdescprop, 0);
    }

    public static BitUtils.IntBitmap readSupportedBitmap(byte[] portdescprop,
            int offset) {
        return new BitUtils.IntBitmap(EndianConversion.byteArrayToIntBE(
                portdescprop, offset + OFP_PORT_DESC_PROP_BASE_LEN
                        + OFP_PORT_DESC_OPTICAL_SUPPORTED_BITMAP_OFFSET));
    }

    public static BitUtils.IntBitmap readSupportedBitmap(InputStream is)
            throws IOException {
        readPadding(is, OFP_PORT_DESC_OPTICAL_PAD_LEN);
        return new BitUtils.IntBitmap(EndianConversion.inputStreamToIntBE(is));
    }

    public static int readTxMinFreqLmda(byte[] portdescprop) {
        return readTxMinFreqLmda(portdescprop, 0);
    }

    public static int readTxMinFreqLmda(byte[] portdescprop, int offset) {
        return EndianConversion.byteArrayToIntBE(portdescprop, offset
                + OFP_PORT_DESC_PROP_BASE_LEN
                + OFP_PORT_DESC_OPTICAL_TX_MIN_FREQ_LMDA_OFFSET);
    }

    public static int readTxMinFreqLmda(InputStream is) throws IOException {
        return EndianConversion.inputStreamToIntBE(is);
    }

    public static int readTxMaxFreqLmda(byte[] portdescprop) {
        return readTxMaxFreqLmda(portdescprop, 0);
    }

    public static int readTxMaxFreqLmda(byte[] portdescprop, int offset) {
        return EndianConversion.byteArrayToIntBE(portdescprop, offset
                + OFP_PORT_DESC_PROP_BASE_LEN
                + OFP_PORT_DESC_OPTICAL_TX_MAX_FREQ_LMDA_OFFSET);
    }

    public static int readTxMaxFreqLmda(InputStream is) throws IOException {
        return EndianConversion.inputStreamToIntBE(is);
    }

    public static int readTxGridFreqLmda(byte[] portdescprop) {
        return readTxGridFreqLmda(portdescprop, 0);
    }

    public static int readTxGridFreqLmda(byte[] portdescprop, int offset) {
        return EndianConversion.byteArrayToIntBE(portdescprop, offset
                + OFP_PORT_DESC_PROP_BASE_LEN
                + OFP_PORT_DESC_OPTICAL_TX_GRID_FREQ_LMDA_OFFSET);
    }

    public static int readTxGridFreqLmda(InputStream is) throws IOException {
        return EndianConversion.inputStreamToIntBE(is);
    }

    public static int readRxMinFreqLmda(byte[] portdescprop) {
        return readRxMinFreqLmda(portdescprop, 0);
    }

    public static int readRxMinFreqLmda(byte[] portdescprop, int offset) {
        return EndianConversion.byteArrayToIntBE(portdescprop, offset
                + OFP_PORT_DESC_PROP_BASE_LEN
                + OFP_PORT_DESC_OPTICAL_RX_MIN_FREQ_LMDA_OFFSET);
    }

    public static int readRxMinFreqLmda(InputStream is) throws IOException {
        return EndianConversion.inputStreamToIntBE(is);
    }

    public static int readRxMaxFreqLmda(byte[] portdescprop) {
        return readRxMaxFreqLmda(portdescprop, 0);
    }

    public static int readRxMaxFreqLmda(byte[] portdescprop, int offset) {
        return EndianConversion.byteArrayToIntBE(portdescprop, offset
                + OFP_PORT_DESC_PROP_BASE_LEN
                + OFP_PORT_DESC_OPTICAL_RX_MAX_FREQ_LMDA_OFFSET);
    }

    public static int readRxMaxFreqLmda(InputStream is) throws IOException {
        return EndianConversion.inputStreamToIntBE(is);
    }

    public static int readRxGridFreqLmda(byte[] portdescprop) {
        return readRxGridFreqLmda(portdescprop, 0);
    }

    public static int readRxGridFreqLmda(byte[] portdescprop, int offset) {
        return EndianConversion.byteArrayToIntBE(portdescprop, offset
                + OFP_PORT_DESC_PROP_BASE_LEN
                + OFP_PORT_DESC_OPTICAL_RX_GRID_FREQ_LMDA_OFFSET);
    }

    public static int readRxGridFreqLmda(InputStream is) throws IOException {
        return EndianConversion.inputStreamToIntBE(is);
    }

    public static int readTxPwrMin(byte[] portdescprop) {
        return readTxPwrMin(portdescprop, 0);
    }

    public static int readTxPwrMin(byte[] portdescprop, int offset) {
        return EndianConversion.byteArrayToIntBE(portdescprop, offset
                + OFP_PORT_DESC_PROP_BASE_LEN
                + OFP_PORT_DESC_OPTICAL_TX_PWR_MIN_OFFSET);
    }

    public static int readTxPwrMin(InputStream is) throws IOException {
        return EndianConversion.inputStreamToIntBE(is);
    }

    public static int readTxPwrMax(byte[] portdescprop) {
        return readTxPwrMin(portdescprop, 0);
    }

    public static int readTxPwrMax(byte[] portdescprop, int offset) {
        return EndianConversion.byteArrayToIntBE(portdescprop, offset
                + OFP_PORT_DESC_PROP_BASE_LEN
                + OFP_PORT_DESC_OPTICAL_TX_PWR_MAX_OFFSET);
    }

    public static int readTxPwrMax(InputStream is) throws IOException {
        return EndianConversion.inputStreamToIntBE(is);
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();

        sb
                .append(String
                        .format(
                                "%s : TxMinFreqLmda=%d(0x%04x) : TxMaxFreqLmda=%d(0x%04x) : TxGridFreqLmda=%d(0x%04x)\n"
                                        + " : RxMinFreqLmda=%d(0x%04x) : RxMaxFreqLmda=%d(0x%04x) : RxGridFreqLmda=%d(0x%04x)\n"
                                        + " : TxPwrMin=%d(0x%04x) : TxPwrMax=%d(0x%04x)\n",
                                super.toString(),
                                getTxMinFreqLmda(),
                                getTxMinFreqLmda(),
                                getTxMaxFreqLmda(),
                                getTxMaxFreqLmda(),
                                getTxGridFreqLmda(),
                                getTxGridFreqLmda(),
                                getRxMinFreqLmda(),
                                getRxMinFreqLmda(),
                                getRxMaxFreqLmda(),
                                getRxMaxFreqLmda(),
                                getRxGridFreqLmda(),
                                getRxGridFreqLmda(),
                                getTxPwrMin(),
                                getTxPwrMax()));

        sb.append(String
                .format("Supported %s", getSupportedBitmap().toString()));

        getSupportedBitmap().iterate(new BitUtils.BitmapIterator() {
            private int i;

            public void executeIfBitIsSet(int bitpos, Object var) {
                StringBuffer sb = (StringBuffer) var;
                sb.append(String.format("[%d] : %s\n", i++, OfpPortFeatures
                        .getEnum(bitpos).toString()));
            }

            public void executeIfBitIsNotSet(int bitpos, Object var) {

            }
        }, sb);

        return sb.toString();
    }

    public boolean equals(OfpPortDescOpticalProp hdr) {
        if (super.equals(hdr)) {
            if ((getSupportedBitmap() == hdr.getSupportedBitmap())
                    && (getTxMinFreqLmda() == hdr.getTxMinFreqLmda())
                    && (getTxMaxFreqLmda() == hdr.getTxMaxFreqLmda())
                    && (getTxGridFreqLmda() == hdr.getTxGridFreqLmda())
                    && (getRxMinFreqLmda() == hdr.getRxMinFreqLmda())
                    && (getRxMaxFreqLmda() == hdr.getRxMaxFreqLmda())
                    && (getRxGridFreqLmda() == hdr.getRxGridFreqLmda())
                    && (getTxPwrMin() == hdr.getTxPwrMin())
                    && (getTxPwrMax() == hdr.getTxPwrMax())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o instanceof OfpPortDescOpticalProp) {
            return equals((OfpPortDescOpticalProp) o);
        }
        return false;
    }
}