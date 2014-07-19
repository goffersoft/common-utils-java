/**
 /**
 ** File: OfpQueueDescMinRateProp.java
 **
 ** Description : Open Packet Queue Min Rate Properties
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

import com.goffersoft.common.utils.EndianConversion;
import com.goffersoft.common.utils.ReadUtils;

public class OfpQueueDescMinRateProp
        extends
        OfpQueueDescGenericProp {
    private static final Logger log = Logger
            .getLogger(OfpQueueDescMinRateProp.class);

    private short rate;
    // 2 bytes of padding

    public static final int OFP_QUEUE_DESC_MIN_RATE_PROP_RATE_OFFSET = 0;
    public static final int OFP_QUEUE_DESC_MIN_RATE_PROP_PAD_OFFSET = 2;
    public static final int OFP_QUEUE_DESC_MIN_RATE_HDR_LEN = 2;
    public static final int OFP_QUEUE_DESC_MIN_RATE_PAD_LEN = 2;

    public OfpQueueDescMinRateProp() {
        super(
                OfpQueueDescPropType.OFPQDPT_MIN_RATE,
                (short) (OFP_QUEUE_DESC_PROP_BASE_LEN + OFP_QUEUE_DESC_MIN_RATE_HDR_LEN));
        setRate((short) 0);
    }

    public OfpQueueDescMinRateProp(short maxrate) {
        super(
                OfpQueueDescPropType.OFPQDPT_MIN_RATE,
                (short) (OFP_QUEUE_DESC_PROP_BASE_LEN + OFP_QUEUE_DESC_MIN_RATE_HDR_LEN));
        setRate(maxrate);
    }

    public OfpQueueDescMinRateProp(byte[] data) {
        this(data, 0);
    }

    public OfpQueueDescMinRateProp(byte[] data, int offset) {
        super(data, offset);
        if (getType() != OfpQueueDescPropType.OFPQDPT_MIN_RATE) {
            throw new IllegalArgumentException("Bad Data : Expected  : 0x"
                    + Integer.toHexString(OfpQueueDescPropType.OFPQDPT_MIN_RATE
                            .getValue()) + " Got : 0x"
                    + Integer.toHexString(getType().getValue()));
        }
        setRate(readRate(data, offset));
    }

    public OfpQueueDescMinRateProp(InputStream is) throws IOException {
        super(is);
        if (getType() != OfpQueueDescPropType.OFPQDPT_MIN_RATE) {
            throw new IllegalArgumentException("Bad Data : Expected  : 0x"
                    + Integer.toHexString(OfpQueueDescPropType.OFPQDPT_MIN_RATE
                            .getValue()) + " Got : 0x"
                    + Integer.toHexString(getType().getValue()));
        }
        setRate(readRate(is));
    }

    public OfpQueueDescMinRateProp(OfpQueueDescGenericProp base, InputStream is)
            throws IOException {
        super(base);
        if (getType() != OfpQueueDescPropType.OFPQDPT_MIN_RATE) {
            throw new IllegalArgumentException("Bad Data : Expected  : 0x"
                    + Integer.toHexString(OfpQueueDescPropType.OFPQDPT_MIN_RATE
                            .getValue()) + " Got : 0x"
                    + Integer.toHexString(getType().getValue()));
        }
        setRate(readRate(is));
    }

    public short getRate() {
        return rate;
    }

    public void setRate(short rate) {
        this.rate = rate;
    }

    @Override
    public OfpQueueDescMinRateProp fromInputStream(InputStream is)
            throws IOException {
        return readFromInputStream(is);
    }

    @Override
    public OfpQueueDescMinRateProp fromByteArray(byte[] data) {
        return new OfpQueueDescMinRateProp(data, 0);
    }

    @Override
    public OfpQueueDescMinRateProp fromByteArray(byte[] data, int offset) {
        return new OfpQueueDescMinRateProp(data, offset);
    }

    @Override
    public byte[] toByteArray() {
        byte[] hdr = new byte[OFP_QUEUE_DESC_PROP_BASE_LEN
                + OFP_QUEUE_DESC_MIN_RATE_HDR_LEN];
        return toByteArray(hdr, 0);
    }

    @Override
    public byte[] toByteArray(byte[] data) {
        return toByteArray(data, 0);
    }

    @Override
    public byte[] toByteArray(byte[] data, int offset) {
        super.toByteArray(data, offset);
        writeRate(data, offset);
        return data;
    }

    @Override
    public OutputStream toOutputStream(OutputStream os) throws IOException {
        super.toOutputStream(os);
        writeRate(os);
        return os;
    }

    public byte[] writeRate(byte[] data) {
        return writeRate(data, 0);
    }

    public byte[] writeRate(byte[] data, int offset) {
        EndianConversion.shortToByteArrayBE(data, offset
                + OFP_QUEUE_DESC_PROP_BASE_LEN
                + OFP_QUEUE_DESC_MIN_RATE_PROP_RATE_OFFSET, getRate());

        for (int i = offset + OFP_QUEUE_DESC_PROP_BASE_LEN
                + OFP_QUEUE_DESC_MIN_RATE_PROP_PAD_OFFSET; i < offset
                + OFP_QUEUE_DESC_PROP_BASE_LEN
                + OFP_QUEUE_DESC_MIN_RATE_PROP_PAD_OFFSET
                + OFP_QUEUE_DESC_MIN_RATE_PAD_LEN; i++) {
            data[i] = 0x00;
        }

        return data;
    }

    public OutputStream writeRate(OutputStream os) throws IOException {
        EndianConversion.shortToOutputStreamBE(os, getRate());
        for (int i = 0; i < OFP_QUEUE_DESC_MIN_RATE_PAD_LEN; i++) {
            os.write(0x00);
        }
        return os;
    }

    public static byte[] readOfpQueueDescMinRateProp(InputStream is)
            throws IOException {
        return readOfpQueueDescMinRateProp(is, null, 0);
    }

    public static
            byte[]
            readOfpQueueDescMinRateProp(InputStream is, byte[] data)
                    throws IOException {
        return readOfpQueueDescMinRateProp(is, data, 0);
    }

    public static byte[] readOfpQueueDescMinRateProp(InputStream is,
            byte[] data, int offset) throws IOException {
        byte[] tmpData = OfpQueueDescGenericProp.readOfpQueueDescGenericProp(
                is, data, offset);
        int tmpOffset = ((tmpData != data) ? 0 : offset);
        OfpQueueDescPropType type = readType(tmpData, tmpOffset);
        short len = readLength(tmpData, tmpOffset);

        if (type != OfpQueueDescPropType.OFPQDPT_MIN_RATE) {
            throw new IOException("Bad Data : Expected  : 0x"
                    + Integer.toHexString(OfpQueueDescPropType.OFPQDPT_MIN_RATE
                            .getValue()) + " Got : 0x"
                    + Integer.toHexString(type.getValue()));
        }
        byte[] tmp1Data = ReadUtils.readInput(is, tmpData, tmpOffset
                + OFP_QUEUE_DESC_PROP_BASE_LEN, len);
        if (tmp1Data != tmpData) {
            System.arraycopy(tmpData, tmpOffset, tmp1Data, 0,
                    OFP_QUEUE_DESC_PROP_BASE_LEN);
            return tmp1Data;
        }
        return tmpData;
    }

    public static OfpQueueDescMinRateProp readFromInputStream(InputStream is)
            throws IOException {
        return new OfpQueueDescMinRateProp(is);
    }

    public static short readRate(byte[] qdeschdr) {
        return readRate(qdeschdr, 0);
    }

    public static short readRate(byte[] qdeschdr, int offset) {
        return (short) EndianConversion.byteArrayToIntBE(qdeschdr, offset
                + OFP_QUEUE_DESC_PROP_BASE_LEN
                + OFP_QUEUE_DESC_MIN_RATE_PROP_RATE_OFFSET);
    }

    public static short readRate(InputStream is) throws IOException {
        return (short) EndianConversion.inputStreamToIntBE(is);
    }

    @Override
    public String toString() {

        return String.format("%s : MinRate=%d(0x%04x)\n", super.toString(),
                getRate(), getRate());
    }

    public boolean equals(OfpQueueDescMinRateProp hdr) {
        if (super.equals(hdr) == true) {
            if (getRate() == hdr.getRate()) {
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
        if (o instanceof OfpQueueDescMinRateProp) {
            return equals((OfpQueueDescMinRateProp) o);
        }
        return false;
    }
}