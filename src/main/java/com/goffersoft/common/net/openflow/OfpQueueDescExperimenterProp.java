/**
 ** File: OfpQueueDescExperimenterProp.java
 **
 ** Description : OpenFlow Queue Experimental Properties class
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

import com.goffersoft.common.utils.EndianConversion;
import com.goffersoft.common.utils.PatternUtils;
import com.goffersoft.common.utils.PrintUtils;
import com.goffersoft.common.utils.ReadUtils;
import com.goffersoft.common.utils.WriteUtils;

public class OfpQueueDescExperimenterProp extends OfpQueueDescGenericProp {
    private int expid;
    private int exptype;
    private byte[] data;

    private static final Logger log = Logger
            .getLogger(OfpQueueDescExperimenterProp.class);

    public static final int OFP_QUEUE_DESC_EXP_HDR_LEN = 8;
    public static final int OFP_QUEUE_DESC_EXP_HDR_ID_OFFSET = 0;
    public static final int OFP_QUEUE_DESC_EXP_HDR_TYPE_OFFSET = 4;
    public static final int OFP_QUEUE_DESC_EXP_HDR_DATA_OFFSET = 8;

    public OfpQueueDescExperimenterProp(int experimenterId,
            int experimenterType, byte[] experimenterData) {
        super(OfpQueueDescPropType.OFPQDPT_EXPERIMENTER, (short) 0);
        setExpId(experimenterId);
        setExpType(experimenterType);
        setData(experimenterData);
    }

    public OfpQueueDescExperimenterProp(int experimenterId,
            int experimenterType, byte[] experimenterData, int offset) {
        super(OfpQueueDescPropType.OFPQDPT_EXPERIMENTER, (short) 0);
        setExpId(experimenterId);
        setExpType(experimenterType);
        setData(experimenterData, offset);
    }

    public OfpQueueDescExperimenterProp(int experimenterId,
            int experimenterType, byte[] experimenterData, int offset,
            int length) {
        super(OfpQueueDescPropType.OFPQDPT_EXPERIMENTER, (short) 0);
        setExpId(experimenterId);
        setExpType(experimenterType);
        setData(experimenterData, offset, length);
    }

    public OfpQueueDescExperimenterProp(byte[] data) {
        this(data, 0);
    }

    public OfpQueueDescExperimenterProp(byte[] data, int offset) {
        super(data, offset);
        if (getType() != OfpQueueDescPropType.OFPQDPT_EXPERIMENTER) {
            throw new IllegalArgumentException(
                    "Bad Data : Expected  : 0x"
                            + Integer
                                    .toHexString(OfpQueueDescPropType.OFPQDPT_EXPERIMENTER
                                            .getValue()) + " Got : 0x"
                            + Integer.toHexString(getType().getValue()));
        }
        setExpId(readExpId(data, offset));
        setExpType(readExpType(data, offset));
        if (getLength() > (OFP_QUEUE_DESC_PROP_BASE_LEN + OFP_QUEUE_DESC_EXP_HDR_LEN)) {
            setDataInternal(
                    data,
                    offset + OFP_QUEUE_DESC_PROP_BASE_LEN
                            + OFP_QUEUE_DESC_EXP_HDR_LEN,
                    getLength()
                            - (OFP_QUEUE_DESC_PROP_BASE_LEN + OFP_QUEUE_DESC_EXP_HDR_LEN),
                    false);
        }
    }

    public OfpQueueDescExperimenterProp(OfpQueueDescGenericProp base,
            InputStream is) throws IOException {
        super(base);

        if (getType() != OfpQueueDescPropType.OFPQDPT_EXPERIMENTER) {
            throw new IOException(
                    "Bad Data : Expected  : 0x"
                            + Integer
                                    .toHexString(OfpQueueDescPropType.OFPQDPT_EXPERIMENTER
                                            .getValue()) + " Got : 0x"
                            + Integer.toHexString(getType().getValue()));
        }
        setExpId(readExpId(is));
        setExpType(readExpType(is));
        if (getLength() > (OFP_QUEUE_DESC_PROP_BASE_LEN + OFP_QUEUE_DESC_EXP_HDR_LEN)) {
            setDataInternal(
                    readData(
                            is,
                            getLength()
                                    - (OFP_QUEUE_DESC_PROP_BASE_LEN + OFP_QUEUE_DESC_EXP_HDR_LEN),
                            getData()),
                    0,
                    getLength()
                            - (OFP_QUEUE_DESC_PROP_BASE_LEN + OFP_QUEUE_DESC_EXP_HDR_LEN),
                    false);
            readPadding(is);
        }
    }

    public OfpQueueDescExperimenterProp(InputStream is) throws IOException {
        super(is);
        if (getType() != OfpQueueDescPropType.OFPQDPT_EXPERIMENTER) {
            throw new IOException(
                    "Bad Data : Expected  : 0x"
                            + Integer
                                    .toHexString(OfpQueueDescPropType.OFPQDPT_EXPERIMENTER
                                            .getValue()) + " Got : 0x"
                            + Integer.toHexString(getType().getValue()));
        }
        setExpId(readExpId(is));
        setExpType(readExpType(is));
        if (getLength() > (OFP_QUEUE_DESC_PROP_BASE_LEN + OFP_QUEUE_DESC_EXP_HDR_LEN)) {
            setDataInternal(
                    readData(
                            is,
                            getLength()
                                    - (OFP_QUEUE_DESC_PROP_BASE_LEN + OFP_QUEUE_DESC_EXP_HDR_LEN),
                            getData()),
                    0,
                    getLength()
                            - (OFP_QUEUE_DESC_PROP_BASE_LEN + OFP_QUEUE_DESC_EXP_HDR_LEN),
                    false);
            readPadding(is);
        }
    }

    public int getExpId() {
        return expid;
    }

    public void setExpId(int expid) {
        this.expid = expid;
    }

    public int getExpType() {
        return exptype;
    }

    public void setExpType(int exptype) {
        this.exptype = exptype;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        setData(data, 0, data.length);
    }

    public void setData(byte[] data, int offset) {
        setData(data, offset, ((data == null) ? 0 : (data.length - offset)));
    }

    public void setData(byte[] data, int offset, int length) {
        setDataInternal(data, offset, length, true);
    }

    private void setDataInternal(byte[] data, int offset, int length,
            boolean adjustLength) {
        if (data == null || length <= 0 || offset < 0 || offset >= data.length) {
            this.data = null;
            if (adjustLength == true) {
                setLength((short) (OFP_QUEUE_DESC_PROP_BASE_LEN + OFP_QUEUE_DESC_EXP_HDR_LEN));
            }
        } else {
            int len = ((offset + length) > data.length) ? (data.length - offset)
                    : length;
            this.data = new byte[len];
            System.arraycopy(data, offset, this.data, 0, len);
            if (adjustLength == true) {
                setLength((short) (OFP_QUEUE_DESC_PROP_BASE_LEN
                        + OFP_QUEUE_DESC_EXP_HDR_LEN + len));
            }
        }
    }

    @Override
    public byte[] toByteArray() {
        byte[] prophdr = new byte[getAlignedLength()];

        return toByteArray(prophdr, 0);
    }

    @Override
    public byte[] toByteArray(byte[] data) {
        return toByteArray(data, 0);
    }

    @Override
    public byte[] toByteArray(byte[] data, int offset) {
        super.toByteArray(data, offset);

        writeId(data, offset);
        writeExpType(data, offset);
        writeData(data, offset);

        return data;
    }

    @Override
    public OutputStream toOutputStream(OutputStream os) throws IOException {
        super.toOutputStream(os);

        writeId(os);
        writeExpType(os);
        writeData(os);

        return os;
    }

    @Override
    public OfpQueueDescExperimenterProp fromInputStream(InputStream is)
            throws IOException {
        return readFromInputStream(is);
    }

    @Override
    public OfpQueueDescExperimenterProp fromByteArray(byte[] data) {
        return new OfpQueueDescExperimenterProp(data);
    }

    @Override
    public OfpQueueDescExperimenterProp fromByteArray(byte[] data, int offset) {
        return new OfpQueueDescExperimenterProp(data, offset);
    }

    public byte[] writeExpType(byte[] data) {
        return writeExpType(data, 0);
    }

    public byte[] writeExpType(byte[] data, int offset) {
        EndianConversion.intToByteArrayBE(data, offset
                + OFP_QUEUE_DESC_PROP_BASE_LEN
                + OFP_QUEUE_DESC_EXP_HDR_TYPE_OFFSET, getExpType());
        return data;
    }

    public OutputStream writeExpType(OutputStream os) throws IOException {
        EndianConversion.intToOutputStreamBE(os, getExpId());
        return os;
    }

    public byte[] writeId(byte[] data) {
        return writeId(data, 0);
    }

    public byte[] writeId(byte[] data, int offset) {
        EndianConversion.intToByteArrayBE(data, offset
                + OFP_QUEUE_DESC_PROP_BASE_LEN
                + OFP_QUEUE_DESC_EXP_HDR_ID_OFFSET, getExpId());
        return data;
    }

    public OutputStream writeId(OutputStream os) throws IOException {
        EndianConversion.intToOutputStreamBE(os, getExpId());
        return os;
    }

    public byte[] writeData(byte[] data) {
        return writeData(data, 0);
    }

    public byte[] writeData(byte[] data, int offset) {
        if (getData() != null) {
            System.arraycopy(getData(), 0, data, getDataOffset(offset),
                    getData().length);
            writePadding(data, offset);
        }
        return data;
    }

    public OutputStream writeData(OutputStream os) throws IOException {
        if (getData() != null) {
            WriteUtils.writeOutput(os, getData(), 0, getData().length);
            writePadding(os);
        }
        return os;
    }

    public static int getDataOffset() {
        return getDataOffset(0);
    }

    public static int getDataOffset(int offset) {
        return (offset + OFP_QUEUE_DESC_PROP_BASE_LEN + OFP_QUEUE_DESC_EXP_HDR_DATA_OFFSET);
    }

    public static byte[] readOfpQueueDescExperimenterProp(InputStream is)
            throws IOException {
        return readOfpQueueDescExperimenterProp(is, null, 0);
    }

    public static byte[] readOfpQueueDescExperimenterProp(InputStream is,
            byte[] data) throws IOException {
        return readOfpQueueDescExperimenterProp(is, data, 0);
    }

    public static byte[] readOfpQueueDescExperimenterProp(InputStream is,
            byte[] data, int offset) throws IOException {
        byte[] tmpData = OfpQueueDescGenericProp.readOfpQueueDescGenericProp(
                is, data, offset);
        int tmpOffset = ((tmpData != data) ? 0 : offset);
        OfpQueueDescPropType type = readType(tmpData, tmpOffset);
        short len = readLength(tmpData, tmpOffset);

        if (type != OfpQueueDescPropType.OFPQDPT_EXPERIMENTER) {
            throw new IOException(
                    "Bad Data : Expected  : 0x"
                            + Integer
                                    .toHexString(OfpQueueDescPropType.OFPQDPT_EXPERIMENTER
                                            .getValue()) + " Got : 0x"
                            + Integer.toHexString(type.getValue()));
        }
        byte[] tmp1Data = ReadUtils.readInput(is, tmpData, tmpOffset
                + OFP_QUEUE_DESC_PROP_BASE_LEN, len
                - OFP_QUEUE_DESC_PROP_BASE_LEN);
        if (tmp1Data != tmpData) {
            System.arraycopy(tmpData, tmpOffset, tmp1Data, 0,
                    OFP_QUEUE_DESC_PROP_BASE_LEN);
            return tmp1Data;
        }
        readPadding(is, len);
        return tmpData;
    }

    public static OfpQueueDescExperimenterProp readFromInputStream(
            InputStream is) throws IOException {
        return new OfpQueueDescExperimenterProp(is);
    }

    public static int readExpId(byte[] exphdr) {
        return readExpId(exphdr, 0);
    }

    public static int readExpId(byte[] exphdr, int offset) {
        return EndianConversion.byteArrayToIntBE(exphdr, offset
                + OFP_QUEUE_DESC_PROP_BASE_LEN
                + OFP_QUEUE_DESC_EXP_HDR_ID_OFFSET);
    }

    public static int readExpId(InputStream is) throws IOException {
        return EndianConversion.inputStreamToIntBE(is);
    }

    public static int readExpType(byte[] exphdr) {
        return readExpType(exphdr, 0);
    }

    public static int readExpType(byte[] exphdr, int offset) {
        return EndianConversion.byteArrayToIntBE(exphdr, offset
                + OFP_QUEUE_DESC_PROP_BASE_LEN
                + OFP_QUEUE_DESC_EXP_HDR_TYPE_OFFSET);
    }

    public static int readExpType(InputStream is) throws IOException {
        return EndianConversion.inputStreamToIntBE(is);
    }

    public static byte[] readData(InputStream is, int datalen)
            throws IOException {
        return readData(is, datalen, null, 0);
    }

    public static byte[] readData(InputStream is, int datalen, byte[] data)
            throws IOException {
        return readData(is, datalen, data, 0);
    }

    public static byte[] readData(InputStream is, int datalen, byte[] data,
            int offset) throws IOException {
        return ReadUtils.readInput(is, data, offset, datalen);
    }

    @Override
    public String toString() {
        return String.format(
                "%s : ExpType=%d(0x%04x) ExpId=%d(0x%04x) : Data=%s\n",
                super.toString(),
                getExpType(),
                getExpType(),
                getExpId(),
                getExpId(),
                (getData() == null) ? "null" : PrintUtils.byteArrayToHexString(
                        getData(), 0, getData().length, 8));
    }

    public boolean equals(OfpQueueDescExperimenterProp hdr) {
        if (super.equals(hdr) == true) {
            if ((getExpId() == hdr.getExpId())
                    && (getExpType() == hdr.getExpType())
                    && (getData().length == hdr.getData().length)
                    && PatternUtils.byteCompare(getData(), 0, hdr.getData(), 0,
                            getData().length) == true) {
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
        if (o instanceof OfpQueueDescExperimenterProp) {
            return equals((OfpQueueDescExperimenterProp) o);
        }
        return false;
    }
}
