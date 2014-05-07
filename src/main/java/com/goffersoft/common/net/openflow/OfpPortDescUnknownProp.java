/**
 ** File: OfpPortDescUnknownProp.java
 **
 ** Description : OpenFlow Port Properties class - handler for unknown properties
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

import com.goffersoft.common.utils.PatternUtils;
import com.goffersoft.common.utils.PrintUtils;
import com.goffersoft.common.utils.ReadUtils;
import com.goffersoft.common.utils.WriteUtils;

public class OfpPortDescUnknownProp extends OfpPortDescGenericProp {

    private static final Logger log = Logger
            .getLogger(OfpPortDescUnknownProp.class);

    private byte[] data;

    public static final int OFP_PORT_DESC_UNKNOWN_HDR_LEN = 0;
    public static final int OFP_PORT_DESC_UNKNOWN_PROP_DATA_OFFSET = 0;

    public OfpPortDescUnknownProp(byte[] data, int offset, int length) {
        super(OfpPortDescPropType.OFPPDPT_UNK, (short) (length
                + OFP_PORT_DESC_PROP_BASE_LEN + OFP_PORT_DESC_UNKNOWN_HDR_LEN));
        if (getLength() > (OFP_PORT_DESC_PROP_BASE_LEN + OFP_PORT_DESC_UNKNOWN_HDR_LEN)) {
            setDataInternal(data, offset, length, false);
        }
    }

    public OfpPortDescUnknownProp(byte[] data) {
        this(data, 0);
    }

    OfpPortDescUnknownProp(byte[] data, int offset) {
        super(data, offset);
        if (getType() != OfpPortDescPropType.OFPPDPT_UNK) {
            throw new IllegalArgumentException("Bad Data : Expected  : 0x"
                    + Integer.toHexString(OfpPortDescPropType.OFPPDPT_UNK
                            .getValue()) + " Got : 0x"
                    + Integer.toHexString(getType().getValue()));
        }
        if (getLength() > (OFP_PORT_DESC_PROP_BASE_LEN + OFP_PORT_DESC_UNKNOWN_HDR_LEN)) {
            setDataInternal(
                    data,
                    offset + OFP_PORT_DESC_PROP_BASE_LEN
                            + OFP_PORT_DESC_UNKNOWN_HDR_LEN,
                    getLength()
                            - (OFP_PORT_DESC_PROP_BASE_LEN + OFP_PORT_DESC_UNKNOWN_HDR_LEN),
                    false);
        }
    }

    public OfpPortDescUnknownProp(InputStream is) throws IOException {
        super(is);
        if (getType() != OfpPortDescPropType.OFPPDPT_UNK) {
            throw new IOException("Bad Data : Expected  : 0x"
                    + Integer.toHexString(OfpPortDescPropType.OFPPDPT_UNK
                            .getValue()) + " Got : 0x"
                    + Integer.toHexString(getType().getValue()));
        }
        if (getLength() > (OFP_PORT_DESC_PROP_BASE_LEN + OFP_PORT_DESC_UNKNOWN_HDR_LEN)) {
            setDataInternal(
                    readData(
                            is,
                            getLength()
                                    - (OFP_PORT_DESC_PROP_BASE_LEN + OFP_PORT_DESC_UNKNOWN_HDR_LEN),
                            getData()),
                    0,
                    getLength()
                            - (OFP_PORT_DESC_PROP_BASE_LEN + OFP_PORT_DESC_UNKNOWN_HDR_LEN),
                    false);
            readPadding(is);
        }
    }

    public OfpPortDescUnknownProp(OfpPortDescGenericProp base, InputStream is)
            throws IOException {
        super(base);
        if (getType() != OfpPortDescPropType.OFPPDPT_UNK) {
            throw new IOException("Bad Data : Expected  : 0x"
                    + Integer.toHexString(OfpPortDescPropType.OFPPDPT_UNK
                            .getValue()) + " Got : 0x"
                    + Integer.toHexString(getType().getValue()));
        }
        if (getLength() > (OFP_PORT_DESC_PROP_BASE_LEN + OFP_PORT_DESC_UNKNOWN_HDR_LEN)) {
            setDataInternal(
                    readData(
                            is,
                            getLength()
                                    - (OFP_PORT_DESC_PROP_BASE_LEN + OFP_PORT_DESC_UNKNOWN_HDR_LEN),
                            getData()),
                    0,
                    getLength()
                            - (OFP_PORT_DESC_PROP_BASE_LEN + OFP_PORT_DESC_UNKNOWN_HDR_LEN),
                    false);
            readPadding(is);
        }
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
                setLength((short) (OFP_PORT_DESC_PROP_BASE_LEN + OFP_PORT_DESC_UNKNOWN_HDR_LEN));
            }
        } else {
            int len = ((offset + length) > data.length) ? (data.length - offset)
                    : length;
            this.data = new byte[len];
            System.arraycopy(data, offset, this.data, 0, len);
            if (adjustLength == true) {
                setLength((short) (OFP_PORT_DESC_PROP_BASE_LEN
                        + OFP_PORT_DESC_UNKNOWN_HDR_LEN + len));
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

        writeData(data, offset);

        return data;
    }

    @Override
    public OutputStream toOutputStream(OutputStream os) throws IOException {
        super.toOutputStream(os);
        writeData(os);

        return os;
    }

    @Override
    public OfpPortDescUnknownProp fromInputStream(InputStream is)
            throws IOException {
        return readFromInputStream(is);
    }

    @Override
    public OfpPortDescUnknownProp fromByteArray(byte[] data) {
        return new OfpPortDescUnknownProp(data);
    }

    @Override
    public OfpPortDescUnknownProp fromByteArray(byte[] data, int offset) {
        return new OfpPortDescUnknownProp(data, offset);
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
        return (offset + OFP_PORT_DESC_PROP_BASE_LEN + OFP_PORT_DESC_UNKNOWN_PROP_DATA_OFFSET);
    }

    public static byte[] readOfpPortDescUnknownProp(InputStream is)
            throws IOException {
        return readOfpPortDescUnknownProp(is, null, 0);
    }

    public static byte[] readOfpPortDescUnknownProp(InputStream is, byte[] data)
            throws IOException {
        return readOfpPortDescUnknownProp(is, data, 0);
    }

    public static byte[] readOfpPortDescUnknownProp(InputStream is,
            byte[] data, int offset) throws IOException {
        byte[] tmpData = OfpPortDescGenericProp.readOfpPortDescGenericProp(is,
                data, offset);
        int tmpOffset = ((tmpData != data) ? 0 : offset);
        OfpPortDescPropType type = readType(tmpData, tmpOffset);
        short len = readLength(tmpData, tmpOffset);

        if (type != OfpPortDescPropType.OFPPDPT_UNK) {
            throw new IOException("Bad Data : Expected  : 0x"
                    + Integer.toHexString(OfpPortDescPropType.OFPPDPT_UNK
                            .getValue()) + " Got : 0x"
                    + Integer.toHexString(type.getValue()));
        }
        byte[] tmp1Data = ReadUtils.readInput(is, tmpData, tmpOffset
                + OFP_PORT_DESC_PROP_BASE_LEN, len
                - OFP_PORT_DESC_PROP_BASE_LEN);
        if (tmp1Data != tmpData) {
            System.arraycopy(tmpData, tmpOffset, tmp1Data, 0,
                    OFP_PORT_DESC_PROP_BASE_LEN);
            return tmp1Data;
        }
        readPadding(is, len);
        return tmpData;
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

    public static OfpPortDescUnknownProp readFromInputStream(InputStream is)
            throws IOException {
        return new OfpPortDescUnknownProp(readOfpPortDescUnknownProp(is));
    }

    @Override
    public String toString() {
        return String.format(
                "%s : Data=%s\n",
                super.toString(),
                (getData() == null) ? "null" : PrintUtils.byteArrayToHexString(
                        getData(), 0, getData().length, 8));
    }

    public boolean equals(OfpPortDescUnknownProp hdr) {
        if (this == hdr) {
            return true;
        }
        if (super.equals(hdr) == true) {
            if (PatternUtils.byteCompare(data, 0, hdr.data, 0, data.length) == true) {
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
        if (o instanceof OfpPortDescUnknownProp) {
            return equals((OfpPortDescUnknownProp) o);
        }
        return false;
    }
}
