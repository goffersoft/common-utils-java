/**
 ** File: OfpHelloUnknownElement.java
 **
 ** Description : OfpHelloUnknownElement Header class
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

import com.goffersoft.common.utils.PatternUtils;
import com.goffersoft.common.utils.PrintUtils;
import com.goffersoft.common.utils.ReadUtils;
import com.goffersoft.common.utils.WriteUtils;

public class OfpHelloUnknownElement
        extends
        OfpHelloGenericElement {
    private static final Logger log = Logger
            .getLogger(OfpHelloUnknownElement.class);

    private byte[] data;

    public static final int OFP_HELLO_UNKNOWN_HDR_LEN = 0;
    public static final int OFP_HELLO_UNKNOWN_ELEMENT_DATA_OFFSET = 0;

    public OfpHelloUnknownElement(byte[] data, int offset, int datalength) {
        super(OfpHelloElementType.OFPHET_UNK, (short) (datalength
                + OFP_HELLO_ELEMENT_BASE_LEN + OFP_HELLO_UNKNOWN_HDR_LEN));
        if (getLength() > (OFP_HELLO_ELEMENT_BASE_LEN + OFP_HELLO_UNKNOWN_HDR_LEN)) {
            setDataInternal(data, offset, datalength, false);
        }
    }

    public OfpHelloUnknownElement(byte[] data) {
        this(data, 0);
    }

    public OfpHelloUnknownElement(byte[] data, int offset) {
        super(data, offset);
        if (getType() != OfpHelloElementType.OFPHET_UNK) {
            throw new IllegalArgumentException("Bad Data : Expected  : 0x"
                    + Integer.toHexString(OfpHelloElementType.OFPHET_UNK
                            .getValue()) + " Got : 0x"
                    + Integer.toHexString(getType().getValue()));
        }
        if (getLength() > (OFP_HELLO_ELEMENT_BASE_LEN + OFP_HELLO_UNKNOWN_HDR_LEN)) {
            setDataInternal(data, offset + OFP_HELLO_ELEMENT_BASE_LEN
                    + OFP_HELLO_UNKNOWN_HDR_LEN, getLength()
                    - (OFP_HELLO_ELEMENT_BASE_LEN + OFP_HELLO_UNKNOWN_HDR_LEN),
                    false);
        }
    }

    public OfpHelloUnknownElement(InputStream is) throws IOException {
        super(is);
        if (getType() != OfpHelloElementType.OFPHET_UNK) {
            throw new IOException("Bad Data : Expected  : 0x"
                    + Integer.toHexString(OfpHelloElementType.OFPHET_UNK
                            .getValue()) + " Got : 0x"
                    + Integer.toHexString(getType().getValue()));
        }
        if (getLength() > (OFP_HELLO_ELEMENT_BASE_LEN + OFP_HELLO_UNKNOWN_HDR_LEN)) {
            setDataInternal(
                    readData(
                            is,
                            getLength()
                                    - (OFP_HELLO_ELEMENT_BASE_LEN + OFP_HELLO_UNKNOWN_HDR_LEN),
                            getData()),
                    0,
                    getLength()
                            - (OFP_HELLO_ELEMENT_BASE_LEN + OFP_HELLO_UNKNOWN_HDR_LEN),
                    false);
            readPadding(is);
        }
    }

    public OfpHelloUnknownElement(OfpHelloGenericElement base, InputStream is)
            throws IOException {
        super(base);
        if (getType() != OfpHelloElementType.OFPHET_UNK) {
            throw new IOException("Bad Data : Expected  : 0x"
                    + Integer.toHexString(OfpHelloElementType.OFPHET_UNK
                            .getValue()) + " Got : 0x"
                    + Integer.toHexString(getType().getValue()));
        }
        if (getLength() > (OFP_HELLO_ELEMENT_BASE_LEN + OFP_HELLO_UNKNOWN_HDR_LEN)) {
            setDataInternal(
                    readData(
                            is,
                            getLength()
                                    - (OFP_HELLO_ELEMENT_BASE_LEN + OFP_HELLO_UNKNOWN_HDR_LEN),
                            getData()),
                    0,
                    getLength()
                            - (OFP_HELLO_ELEMENT_BASE_LEN + OFP_HELLO_UNKNOWN_HDR_LEN),
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
                setLength((short) (OFP_HELLO_ELEMENT_BASE_LEN + OFP_HELLO_UNKNOWN_HDR_LEN));
            }
        } else {
            int len =
                    ((offset + length) > data.length) ? (data.length - offset)
                            : length;
            this.data = new byte[len];
            System.arraycopy(data, offset, this.data, 0, len);
            if (adjustLength == true) {
                setLength((short) (OFP_HELLO_ELEMENT_BASE_LEN
                        + OFP_HELLO_UNKNOWN_HDR_LEN + len));
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

        return writeData(data, offset);
    }

    @Override
    public OutputStream toOutputStream(OutputStream os) throws IOException {
        super.toOutputStream(os);
        writeData(os);
        return os;
    }

    @Override
    public OfpHelloUnknownElement fromInputStream(InputStream is)
            throws IOException {
        return readFromInputStream(is);
    }

    @Override
    public OfpHelloUnknownElement fromByteArray(byte[] data) {
        return new OfpHelloUnknownElement(data);
    }

    @Override
    public OfpHelloUnknownElement fromByteArray(byte[] data, int offset) {
        return new OfpHelloUnknownElement(data, offset);
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
        return (offset + OFP_HELLO_ELEMENT_BASE_LEN + OFP_HELLO_UNKNOWN_ELEMENT_DATA_OFFSET);
    }

    public static byte[] readOfpHelloUnknownElement(InputStream is)
            throws IOException {
        return readOfpHelloUnknownElement(is, null, 0);
    }

    public static
            byte[]
            readOfpHelloUnknownElement(InputStream is, byte[] data)
                    throws IOException {
        return readOfpHelloUnknownElement(is, data, 0);
    }

    public static byte[] readOfpHelloUnknownElement(InputStream is,
            byte[] data, int offset) throws IOException {
        byte[] tmpData = OfpHelloGenericElement.readOfpHelloGenericElement(is,
                data, offset);
        int tmpOffset = ((tmpData != data) ? 0 : offset);
        OfpHelloElementType type = readType(tmpData, tmpOffset);
        short len = readLength(tmpData, tmpOffset);

        if (type != OfpHelloElementType.OFPHET_UNK) {
            throw new IOException("Bad Data : Expected  : 0x"
                    + Integer.toHexString(OfpHelloElementType.OFPHET_UNK
                            .getValue()) + " Got : 0x"
                    + Integer.toHexString(type.getValue()));
        }
        byte[] tmp1Data = ReadUtils.readInput(is, tmpData, tmpOffset
                + OFP_HELLO_ELEMENT_BASE_LEN, len - OFP_HELLO_ELEMENT_BASE_LEN);
        if (tmp1Data != tmpData) {
            System.arraycopy(tmpData, tmpOffset, tmp1Data, 0,
                    OFP_HELLO_ELEMENT_BASE_LEN);
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

    public static OfpHelloUnknownElement readFromInputStream(InputStream is)
            throws IOException {
        return new OfpHelloUnknownElement(readOfpHelloUnknownElement(is));
    }

    @Override
    public String toString() {

        return String.format(
                "%s : Data=%s\n",
                super.toString(),
                (getData() == null) ? "null" : PrintUtils.byteArrayToHexString(
                        getData(), 0, getData().length, 8));
    }

    public boolean equals(OfpHelloUnknownElement hdr) {
        if ((super.equals(hdr) == true)) {
            if (PatternUtils.byteCompare(getData(), 0, hdr.getData(), 0,
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
        if (o instanceof OfpHelloUnknownElement) {
            return equals((OfpHelloUnknownElement) o);
        }
        return false;
    }
}
