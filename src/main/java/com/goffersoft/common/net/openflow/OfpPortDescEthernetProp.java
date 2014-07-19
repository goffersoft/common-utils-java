/**
 ** File: OfpPortDescEthernetProp.java
 **
 ** Description : OpenFlow Ethernet Port Properties class
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

public class OfpPortDescEthernetProp
        extends
        OfpPortDescGenericProp {

    private static final Logger log = Logger
            .getLogger(OfpPortDescEthernetProp.class);

    static public enum OfpPortFeatures {
        OFPPF_10MB_HD(0),
        OFPPF_10MB_FD(1),
        OFPPF_100MB_HD(2),
        OFPPF_100MB_FD(3),
        OFPPF_1GB_HD(4),
        OFPPF_1GB_FD(5),
        OFPPF_10GB_FD(6),
        OFPPF_40GB_FD(7),
        OFPPF_100GB_FD(8),
        OFPPF_1TB_FD(9),
        OFPPF_OTHER(10),
        OFPPF_COPPER(11),
        OFPPF_FIBER(12),
        OFPPF_AUTONEG(13),
        OFPPF_PAUSE(14),
        OFPPF_PAUSE_ASYM(15),
        OFPPF_UNK(0), ;

        public static final OfpPortFeatures pfBit[] = {
                OFPPF_10MB_HD,
                OFPPF_10MB_FD,
                OFPPF_100MB_HD,
                OFPPF_100MB_FD,
                OFPPF_1GB_HD,
                OFPPF_1GB_FD,
                OFPPF_10GB_FD,
                OFPPF_40GB_FD,
                OFPPF_100GB_FD,
                OFPPF_1TB_FD,
                OFPPF_OTHER,
                OFPPF_COPPER,
                OFPPF_FIBER,
                OFPPF_AUTONEG,
                OFPPF_PAUSE,
                OFPPF_PAUSE_ASYM,
                OFPPF_UNK,
        };

        public static final String pfDescr[] = {
                "10 Mb half-duplex rate support",
                "10 Mb full-duplex rate support",
                "100 Mb half-duplex rate support",
                "100 Mb full-duplex rate support",
                "1 Gb half-duplex rate support",
                "1 Gb full-duplex rate support",
                "10 Gb full-duplex rate support",
                "40 Gb full-duplex rate support",
                "100 Gb full-duplex rate support",
                "1 Tb full-duplex rate support",
                "Other rate, not in the list",
                "Copper medium",
                "Fiber medium",
                "Auto-negotiation",
                "Pause",
                "Asymmetric pause",
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
            if (this == OFPPF_UNK) {
                int bitpos = BitUtils.getFirstBitPos(pfBitMask);
                if (bitpos != -1) {
                    this.pfBitPos = bitpos;
                }
            }
        }

        public void setBitPos(int pfBitPos) {
            if (this == OFPPF_UNK) {
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

            OfpPortFeatures unk = OFPPF_UNK;
            unk.setValue(pfBitPos);
            return unk;
        }

        public String getDescr() {
            if (this == OFPPF_UNK) {
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

    public static final int OFP_PORT_DESC_ETHERNET_HDR_LEN = 28;
    public static final int OFP_PORT_DESC_ETHERNET_PAD_OFFSET = 0;
    public static final int OFP_PORT_DESC_ETHERNET_PAD_LEN = 4;
    public static final int OFP_PORT_DESC_ETHERNET_CURRENT_BITMAP_OFFSET = 4;
    public static final int OFP_PORT_DESC_ETHERNET_ADVERTISED_BITMAP_OFFSET = 8;
    public static final int OFP_PORT_DESC_ETHERNET_SUPPORTED_BITMAP_OFFSET = 12;
    public static final int OFP_PORT_DESC_ETHERNET_PEER_BITMAP_OFFSET = 16;
    public static final int OFP_PORT_DESC_ETHERNET_CURRENT_SPEED_OFFSET = 20;
    public static final int OFP_PORT_DESC_ETHERNET_MAX_SPEED_OFFSET = 24;

    // 4 byte padding
    private BitUtils.IntBitmap currentBitmap;
    private BitUtils.IntBitmap advertisedBitmap;
    private BitUtils.IntBitmap supportedBitmap;
    private BitUtils.IntBitmap peerBitmap;
    private int currentSpeed;
    private int maxSpeed;

    public OfpPortDescEthernetProp() {
        this(0x0, 0x0, 0x0, 0x0, 0x0, 0x0);
    }

    public OfpPortDescEthernetProp(int currentSpeed, int maxSpeed) {
        this(0x0, 0x0, 0x0, 0x0, currentSpeed, maxSpeed);
    }

    public OfpPortDescEthernetProp(int currentBitmap, int advertisedBitmap,
            int supportedBitmap, int peerBitmap, int currentSpeed, int maxSpeed) {
        super(
                OfpPortDescPropType.OFPPDPT_ETHERNET,
                (short) (OFP_PORT_DESC_PROP_BASE_LEN + OFP_PORT_DESC_ETHERNET_HDR_LEN));
        setCurrentBitmap(currentBitmap);
        setAdvertisedBitmap(advertisedBitmap);
        setSupportedBitmap(supportedBitmap);
        setPeerBitmap(peerBitmap);
        setCurrentSpeed(currentSpeed);
        setMaxSpeed(maxSpeed);
    }

    public OfpPortDescEthernetProp(BitUtils.IntBitmap currentBitmap,
            BitUtils.IntBitmap advertisedBitmap,
            BitUtils.IntBitmap supportedBitmap, BitUtils.IntBitmap peerBitmap,
            int currentSpeed, int maxSpeed) {
        super(
                OfpPortDescPropType.OFPPDPT_ETHERNET,
                (short) (OFP_PORT_DESC_PROP_BASE_LEN + OFP_PORT_DESC_ETHERNET_HDR_LEN));
        setCurrentBitmap(currentBitmap);
        setAdvertisedBitmap(advertisedBitmap);
        setSupportedBitmap(supportedBitmap);
        setPeerBitmap(peerBitmap);
        setCurrentSpeed(currentSpeed);
        setMaxSpeed(maxSpeed);
    }

    public OfpPortDescEthernetProp(byte[] data) {
        this(data, 0);
    }

    public OfpPortDescEthernetProp(byte[] data, int offset) {
        super(data, offset);

        if (getType() != OfpPortDescPropType.OFPPDPT_ETHERNET) {
            throw new IllegalArgumentException("Bad Data : Expected  : 0x"
                    + Integer.toHexString(OfpPortDescPropType.OFPPDPT_ETHERNET
                            .getValue()) + " Got : 0x"
                    + Integer.toHexString(getType().getValue()));
        }

        setCurrentBitmap(readCurrentBitmap(data, offset));
        setAdvertisedBitmap(readAdvertisedBitmap(data, offset));
        setSupportedBitmap(readSupportedBitmap(data, offset));
        setPeerBitmap(readPeerBitmap(data, offset));
        setMaxSpeed(readMaxSpeed(data, offset));
    }

    public OfpPortDescEthernetProp(InputStream is) throws IOException {
        super(is);
        if (getType() != OfpPortDescPropType.OFPPDPT_ETHERNET) {
            throw new IllegalArgumentException("Bad Data : Expected  : 0x"
                    + Integer.toHexString(OfpPortDescPropType.OFPPDPT_ETHERNET
                            .getValue()) + " Got : 0x"
                    + Integer.toHexString(getType().getValue()));
        }
        setCurrentBitmap(readCurrentBitmap(is));
        setAdvertisedBitmap(readAdvertisedBitmap(is));
        setSupportedBitmap(readSupportedBitmap(is));
        setPeerBitmap(readPeerBitmap(is));
        setMaxSpeed(readMaxSpeed(is));
    }

    public OfpPortDescEthernetProp(OfpPortDescGenericProp base, InputStream is)
            throws IOException {
        super(base);
        if (getType() != OfpPortDescPropType.OFPPDPT_ETHERNET) {
            throw new IllegalArgumentException("Bad Data : Expected  : 0x"
                    + Integer.toHexString(OfpPortDescPropType.OFPPDPT_ETHERNET
                            .getValue()) + " Got : 0x"
                    + Integer.toHexString(getType().getValue()));
        }
        setCurrentBitmap(readCurrentBitmap(is));
        setAdvertisedBitmap(readAdvertisedBitmap(is));
        setSupportedBitmap(readSupportedBitmap(is));
        setPeerBitmap(readPeerBitmap(is));
        setMaxSpeed(readMaxSpeed(is));
    }

    public BitUtils.IntBitmap getCurrentBitmap() {
        return currentBitmap;
    }

    public void setCurrentBitmap(int bmap) {
        if (currentBitmap == null) {
            currentBitmap = new BitUtils.IntBitmap(bmap);
        } else {
            currentBitmap.setBitmap(bmap);
        }
    }

    public void setCurrentBitmap(BitUtils.IntBitmap bmap) {
        if (bmap == null) {
            if (currentBitmap == null) {
                currentBitmap = new BitUtils.IntBitmap(0x0);
            }
            return;
        }
        currentBitmap = bmap;
    }

    public OfpPortFeatures[] getCurrentBitSet() {
        int numbits = getCurrentBitmap().getNumBitsSet();

        if (numbits == 0) {
            return null;
        }

        OfpPortFeatures[] tmp = new OfpPortFeatures[numbits];

        getCurrentBitmap().iterate(new BitUtils.BitmapIterator() {
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

    public boolean currentBitmapIsBitSet(OfpPortFeatures pfbit) {
        return getCurrentBitmap().isBitSet(pfbit.getBitPos());
    }

    public void currentBitmapSetBit(OfpPortFeatures pfbit) {
        getCurrentBitmap().setBit(pfbit.getBitPos());
    }

    public void currentBitmapClearBit(OfpPortFeatures pfbit) {
        getCurrentBitmap().clearBit(pfbit.getBitPos());
    }

    public BitUtils.IntBitmap getAdvertisedBitmap() {
        return advertisedBitmap;
    }

    public void setAdvertisedBitmap(int bmap) {
        if (advertisedBitmap == null) {
            advertisedBitmap = new BitUtils.IntBitmap(bmap);
        } else {
            advertisedBitmap.setBitmap(bmap);
        }
    }

    public void setAdvertisedBitmap(BitUtils.IntBitmap bmap) {
        if (bmap == null) {
            if (advertisedBitmap == null) {
                advertisedBitmap = new BitUtils.IntBitmap(0x0);
            }
            return;
        }
        advertisedBitmap = bmap;
    }

    public OfpPortFeatures[] getAdvertisedBitSet() {
        int numbits = getAdvertisedBitmap().getNumBitsSet();

        if (numbits == 0) {
            return null;
        }

        OfpPortFeatures[] tmp = new OfpPortFeatures[numbits];

        getAdvertisedBitmap().iterate(new BitUtils.BitmapIterator() {
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

    public boolean advertisedBitmapIsBitSet(OfpPortFeatures pfbit) {
        return getAdvertisedBitmap().isBitSet(pfbit.getBitPos());
    }

    public void advertisedBitmapSetBit(OfpPortFeatures pfbit) {
        getAdvertisedBitmap().setBit(pfbit.getBitPos());
    }

    public void advertisedBitmapClearBit(OfpPortFeatures pfbit) {
        getAdvertisedBitmap().clearBit(pfbit.getBitPos());
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

    public BitUtils.IntBitmap getPeerBitmap() {
        return peerBitmap;
    }

    public void setPeerBitmap(BitUtils.IntBitmap bmap) {
        if (bmap == null) {
            if (peerBitmap == null) {
                peerBitmap = new BitUtils.IntBitmap(0x0);
            }
            return;
        }
        peerBitmap = bmap;
    }

    public void setPeerBitmap(int bmap) {
        if (peerBitmap == null) {
            peerBitmap = new BitUtils.IntBitmap(bmap);
        } else {
            peerBitmap.setBitmap(bmap);
        }
    }

    public OfpPortFeatures[] getPeerBitSet() {
        int numbits = getPeerBitmap().getNumBitsSet();

        if (numbits == 0) {
            return null;
        }

        OfpPortFeatures[] tmp = new OfpPortFeatures[numbits];

        getPeerBitmap().iterate(new BitUtils.BitmapIterator() {
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

    public boolean peerBitmapIsBitSet(OfpPortFeatures pfbit) {
        return getPeerBitmap().isBitSet(pfbit.getBitPos());
    }

    public void peerBitmapSetBit(OfpPortFeatures pfbit) {
        getPeerBitmap().setBit(pfbit.getBitPos());
    }

    public void peerBitmapClearBit(OfpPortFeatures pfbit) {
        getPeerBitmap().clearBit(pfbit.getBitPos());
    }

    public int getCurrentSpeed() {
        return currentSpeed;
    }

    public void setCurrentSpeed(int currentSpeed) {
        this.currentSpeed = currentSpeed;
    }

    public int getMaxSpeed() {
        return maxSpeed;
    }

    public void setMaxSpeed(int maxSpeed) {
        this.maxSpeed = maxSpeed;
    }

    @Override
    public byte[] toByteArray() {
        byte[] prophdr = new byte[OFP_PORT_DESC_ETHERNET_HDR_LEN
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

        writeCurrentBitmap(data, offset);
        writeAdvertisedBitmap(data, offset);
        writeSupportedBitmap(data, offset);
        writePeerBitmap(data, offset);
        writeCurrentSpeed(data, offset);
        writeMaxSpeed(data, offset);

        return data;
    }

    @Override
    public OutputStream toOutputStream(OutputStream os) throws IOException {
        super.toOutputStream(os);
        writeCurrentBitmap(os);
        writeAdvertisedBitmap(os);
        writeSupportedBitmap(os);
        writePeerBitmap(os);
        writeCurrentSpeed(os);
        writeMaxSpeed(os);
        return os;
    }

    @Override
    public OfpPortDescEthernetProp fromInputStream(InputStream is)
            throws IOException {
        return readFromInputStream(is);
    }

    @Override
    public OfpPortDescEthernetProp fromByteArray(byte[] data) {
        return new OfpPortDescEthernetProp(data);
    }

    @Override
    public OfpPortDescEthernetProp fromByteArray(byte[] data, int offset) {
        return new OfpPortDescEthernetProp(data, offset);
    }

    public byte[] writeCurrentBitmap(byte[] data) {
        return writeCurrentBitmap(data, 0);
    }

    public byte[] writeCurrentBitmap(byte[] data, int offset) {
        writePadding(data, offset + OFP_PORT_DESC_ETHERNET_PAD_OFFSET,
                OFP_PORT_DESC_ETHERNET_PAD_LEN);
        EndianConversion.intToByteArrayBE(data, offset
                + OFP_PORT_DESC_PROP_BASE_LEN
                + OFP_PORT_DESC_ETHERNET_CURRENT_BITMAP_OFFSET,
                getCurrentBitmap().getBitmap());

        return data;
    }

    public OutputStream writeCurrentBitmap(OutputStream os) throws IOException {
        writePadding(os, OFP_PORT_DESC_ETHERNET_PAD_LEN);
        EndianConversion
                .intToOutputStreamBE(os, getCurrentBitmap().getBitmap());
        return os;
    }

    public byte[] writeAdvertisedBitmap(byte[] data) {
        return writeAdvertisedBitmap(data, 0);
    }

    public byte[] writeAdvertisedBitmap(byte[] data, int offset) {
        EndianConversion.intToByteArrayBE(data, offset
                + OFP_PORT_DESC_PROP_BASE_LEN
                + OFP_PORT_DESC_ETHERNET_ADVERTISED_BITMAP_OFFSET,
                getAdvertisedBitmap().getBitmap());

        return data;
    }

    public OutputStream writeAdvertisedBitmap(OutputStream os)
            throws IOException {
        EndianConversion.intToOutputStreamBE(os, getAdvertisedBitmap()
                .getBitmap());
        return os;
    }

    public byte[] writeSupportedBitmap(byte[] data) {
        return writeSupportedBitmap(data, 0);
    }

    public byte[] writeSupportedBitmap(byte[] data, int offset) {
        EndianConversion.intToByteArrayBE(data, offset
                + OFP_PORT_DESC_PROP_BASE_LEN
                + OFP_PORT_DESC_ETHERNET_SUPPORTED_BITMAP_OFFSET,
                getSupportedBitmap().getBitmap());

        return data;
    }

    public OutputStream writeSupportedBitmap(OutputStream os)
            throws IOException {
        EndianConversion.intToOutputStreamBE(os, getSupportedBitmap()
                .getBitmap());
        return os;
    }

    public byte[] writePeerBitmap(byte[] data) {
        return writePeerBitmap(data, 0);
    }

    public byte[] writePeerBitmap(byte[] data, int offset) {
        EndianConversion.intToByteArrayBE(data, offset
                + OFP_PORT_DESC_PROP_BASE_LEN
                + OFP_PORT_DESC_ETHERNET_PEER_BITMAP_OFFSET, getPeerBitmap()
                .getBitmap());

        return data;
    }

    public OutputStream writePeerBitmap(OutputStream os) throws IOException {
        EndianConversion.intToOutputStreamBE(os, getPeerBitmap().getBitmap());
        return os;
    }

    public byte[] writeCurrentSpeed(byte[] data) {
        return writeCurrentSpeed(data, 0);
    }

    public byte[] writeCurrentSpeed(byte[] data, int offset) {
        EndianConversion.intToByteArrayBE(data, offset
                + OFP_PORT_DESC_PROP_BASE_LEN
                + OFP_PORT_DESC_ETHERNET_CURRENT_SPEED_OFFSET,
                getCurrentSpeed());

        return data;
    }

    public OutputStream writeCurrentSpeed(OutputStream os) throws IOException {
        EndianConversion.intToOutputStreamBE(os, getCurrentSpeed());
        return os;
    }

    public byte[] writeMaxSpeed(byte[] data) {
        return writeMaxSpeed(data, 0);
    }

    public byte[] writeMaxSpeed(byte[] data, int offset) {
        EndianConversion.intToByteArrayBE(data, offset
                + OFP_PORT_DESC_PROP_BASE_LEN
                + OFP_PORT_DESC_ETHERNET_MAX_SPEED_OFFSET, getMaxSpeed());
        return data;
    }

    public OutputStream writeMaxSpeed(OutputStream os) throws IOException {
        EndianConversion.intToOutputStreamBE(os, getMaxSpeed());
        return os;
    }

    public static byte[] readOfpPortDescEthernetProp(InputStream is)
            throws IOException {
        return readOfpPortDescEthernetProp(is, null, 0);
    }

    public static byte[]
            readOfpPortDescEthernetProp(InputStream is, byte[] data)
                    throws IOException {
        return readOfpPortDescEthernetProp(is, data, 0);
    }

    public static byte[] readOfpPortDescEthernetProp(InputStream is,
            byte[] data, int offset) throws IOException {
        byte[] tmpData = OfpPortDescGenericProp.readOfpPortDescGenericProp(is,
                data, offset);
        int tmpOffset = ((tmpData != data) ? 0 : offset);
        OfpPortDescPropType type = readType(tmpData, tmpOffset);
        short len = readLength(tmpData, tmpOffset);

        if (type != OfpPortDescPropType.OFPPDPT_ETHERNET) {
            throw new IOException("Bad Data : Expected  : 0x"
                    + Integer.toHexString(OfpPortDescPropType.OFPPDPT_ETHERNET
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

    public static OfpPortDescEthernetProp readFromInputStream(InputStream is)
            throws IOException {
        return new OfpPortDescEthernetProp(is);
    }

    public static BitUtils.IntBitmap readCurrentBitmap(byte[] portdescprop) {
        return readCurrentBitmap(portdescprop, 0);
    }

    public static BitUtils.IntBitmap readCurrentBitmap(byte[] portdescprop,
            int offset) {
        return new BitUtils.IntBitmap(EndianConversion.byteArrayToIntBE(
                portdescprop, offset + OFP_PORT_DESC_PROP_BASE_LEN
                        + OFP_PORT_DESC_ETHERNET_CURRENT_BITMAP_OFFSET));
    }

    public static BitUtils.IntBitmap readCurrentBitmap(InputStream is)
            throws IOException {
        readPadding(is, OFP_PORT_DESC_ETHERNET_PAD_LEN);
        return new BitUtils.IntBitmap(EndianConversion.inputStreamToIntBE(is));
    }

    public static BitUtils.IntBitmap readAdvertisedBitmap(byte[] portdescprop) {
        return readAdvertisedBitmap(portdescprop, 0);
    }

    public static BitUtils.IntBitmap readAdvertisedBitmap(byte[] portdescprop,
            int offset) {
        return new BitUtils.IntBitmap(EndianConversion.byteArrayToIntBE(
                portdescprop, offset + OFP_PORT_DESC_PROP_BASE_LEN
                        + OFP_PORT_DESC_ETHERNET_ADVERTISED_BITMAP_OFFSET));
    }

    public static BitUtils.IntBitmap readAdvertisedBitmap(InputStream is)
            throws IOException {
        return new BitUtils.IntBitmap(EndianConversion.inputStreamToIntBE(is));
    }

    public static BitUtils.IntBitmap readSupportedBitmap(byte[] portdescprop) {
        return readSupportedBitmap(portdescprop, 0);
    }

    public static BitUtils.IntBitmap readSupportedBitmap(byte[] portdescprop,
            int offset) {
        return new BitUtils.IntBitmap(EndianConversion.byteArrayToIntBE(
                portdescprop, offset + OFP_PORT_DESC_PROP_BASE_LEN
                        + OFP_PORT_DESC_ETHERNET_SUPPORTED_BITMAP_OFFSET));
    }

    public static BitUtils.IntBitmap readSupportedBitmap(InputStream is)
            throws IOException {
        return new BitUtils.IntBitmap(EndianConversion.inputStreamToIntBE(is));
    }

    public static BitUtils.IntBitmap readPeerBitmap(byte[] portdescprop) {
        return readPeerBitmap(portdescprop, 0);
    }

    public static BitUtils.IntBitmap readPeerBitmap(byte[] portdescprop,
            int offset) {
        return new BitUtils.IntBitmap(EndianConversion.byteArrayToIntBE(
                portdescprop, offset + OFP_PORT_DESC_PROP_BASE_LEN
                        + OFP_PORT_DESC_ETHERNET_PEER_BITMAP_OFFSET));
    }

    public static BitUtils.IntBitmap readPeerBitmap(InputStream is)
            throws IOException {
        return new BitUtils.IntBitmap(EndianConversion.inputStreamToIntBE(is));
    }

    public static int readCurrentSpeed(byte[] portdescprop) {
        return readCurrentSpeed(portdescprop, 0);
    }

    public static int readCurrentSpeed(byte[] portdescprop, int offset) {
        return EndianConversion.byteArrayToIntBE(portdescprop, offset
                + OFP_PORT_DESC_PROP_BASE_LEN
                + OFP_PORT_DESC_ETHERNET_CURRENT_SPEED_OFFSET);
    }

    public static int readCurrentSpeed(InputStream is) throws IOException {
        return EndianConversion.inputStreamToIntBE(is);
    }

    public static int readMaxSpeed(byte[] portdescprop) {
        return readMaxSpeed(portdescprop, 0);
    }

    public static int readMaxSpeed(byte[] portdescprop, int offset) {
        return EndianConversion.byteArrayToIntBE(portdescprop, offset
                + OFP_PORT_DESC_PROP_BASE_LEN
                + OFP_PORT_DESC_ETHERNET_CURRENT_SPEED_OFFSET);
    }

    public static int readMaxSpeed(InputStream is) throws IOException {
        return EndianConversion.inputStreamToIntBE(is);
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();

        sb.append(String.format(
                "%s : CurrentSpeed=%d(0x%04x) : MaxSpeed=%d(0x%04x)\n",
                super.toString(), getCurrentSpeed(), getCurrentSpeed(),
                getMaxSpeed(), getMaxSpeed()));

        sb.append(String.format("Current %s", getCurrentBitmap().toString()));

        getCurrentBitmap().iterate(new BitUtils.BitmapIterator() {
            private int i;

            public void executeIfBitIsSet(int bitpos, Object var) {
                StringBuffer sb = (StringBuffer) var;
                sb.append(String.format("[%d] : %s\n", i++, OfpPortFeatures
                        .getEnum(bitpos).toString()));
            }

            public void executeIfBitIsNotSet(int bitpos, Object var) {

            }
        }, sb);

        sb.append(String.format("Advertised %s", getAdvertisedBitmap()
                .toString()));

        getAdvertisedBitmap().iterate(new BitUtils.BitmapIterator() {
            private int i;

            public void executeIfBitIsSet(int bitpos, Object var) {
                StringBuffer sb = (StringBuffer) var;
                sb.append(String.format("[%d] : %s\n", i++, OfpPortFeatures
                        .getEnum(bitpos).toString()));
            }

            public void executeIfBitIsNotSet(int bitpos, Object var) {

            }
        }, sb);

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

        sb.append(String.format("Peer %s", getPeerBitmap().toString()));

        getPeerBitmap().iterate(new BitUtils.BitmapIterator() {
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

    public boolean equals(OfpPortDescEthernetProp hdr) {
        if (super.equals(hdr)) {
            if ((getCurrentBitmap() == hdr.getCurrentBitmap())
                    && (getAdvertisedBitmap() == hdr.getAdvertisedBitmap())
                    && (getSupportedBitmap() == hdr.getSupportedBitmap())
                    && (getPeerBitmap() == hdr.getPeerBitmap())
                    && (getCurrentSpeed() == hdr.getCurrentSpeed())
                    && (getMaxSpeed() == hdr.getMaxSpeed())) {
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
        if (o instanceof OfpPortDescEthernetProp) {
            return equals((OfpPortDescEthernetProp) o);
        }
        return false;
    }
}
