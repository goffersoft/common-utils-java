/**
 ** File: OfpHelloVersionBitmapElement.java
 **
 ** Description : OfpHelloVersionBitmap Header class
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

public class OfpHelloVersionBitmapElement extends OfpHelloGenericElement {
    private static final Logger log = Logger
            .getLogger(OfpHelloGenericElement.class);
    private byte[] data;

    public static final int OFP_HELLO_VERSION_BITMAP_ELEMENT_DATA_OFFSET = 0;

    public OfpHelloVersionBitmapElement(byte[] data, int offset, int datalength) {
        super(OfpHelloElementType.OFPHET_VERSIONBITMAP,
                (short) (datalength + OFP_HELLO_ELEMENT_BASE_LEN));
        if (getLength() > OFP_HELLO_ELEMENT_BASE_LEN) {
            setDataInternal(data, offset, datalength, false);
        }
    }

    public OfpHelloVersionBitmapElement(byte[] data) {
        this(data, 0);
    }

    public OfpHelloVersionBitmapElement(byte[] data, int offset) {
        super(data, offset);
        if (getType() != OfpHelloElementType.OFPHET_VERSIONBITMAP) {
            throw new IllegalArgumentException(
                    "Bad Data : Expected  : 0x"
                            + Integer
                                    .toHexString(OfpHelloElementType.OFPHET_VERSIONBITMAP
                                            .getValue()) + " Got : 0x"
                            + Integer.toHexString(getType().getValue()));
        }
        if (getLength() > OFP_HELLO_ELEMENT_BASE_LEN) {
            setDataInternal(data, offset + OFP_HELLO_ELEMENT_BASE_LEN,
                    getLength() - OFP_HELLO_ELEMENT_BASE_LEN, false);
        }
    }

    public OfpHelloVersionBitmapElement(InputStream is) throws IOException {
        super(is);
        if (getType() != OfpHelloElementType.OFPHET_VERSIONBITMAP) {
            throw new IOException("Bad Data : Expected  : 0x"
                    + Integer.toHexString(OfpHelloElementType.OFPHET_UNK
                            .getValue()) + " Got : 0x"
                    + Integer.toHexString(getType().getValue()));
        }
        if (getLength() > OFP_HELLO_ELEMENT_BASE_LEN) {
            readData(is, getLength() - OFP_HELLO_ELEMENT_BASE_LEN, getData());
            readPadding(is);
        }
    }

    public OfpHelloVersionBitmapElement(OfpHelloGenericElement base,
            InputStream is) throws IOException {
        super(base);
        if (getType() != OfpHelloElementType.OFPHET_VERSIONBITMAP) {
            throw new IOException(
                    "Bad Data : Expected  : 0x"
                            + Integer
                                    .toHexString(OfpHelloElementType.OFPHET_VERSIONBITMAP
                                            .getValue()) + " Got : 0x"
                            + Integer.toHexString(getType().getValue()));
        }
        if (getLength() > OFP_HELLO_ELEMENT_BASE_LEN) {
            readData(is, getLength() - OFP_HELLO_ELEMENT_BASE_LEN, getData());
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

    private void setDataInternal(byte[] data, int offset, int length,
            boolean adjustLength) {
        if (data == null) {
            this.data = null;
            if (adjustLength == true) {
                setLength((short) OFP_HELLO_ELEMENT_BASE_LEN);
            }
        } else {
            this.data = new byte[length];
            System.arraycopy(data, offset, this.data, 0, length);
            if (adjustLength == true) {
                setLength((short) (OFP_HELLO_ELEMENT_BASE_LEN + length));
            }
        }
    }

    public void setData(byte[] data, int offset, int length) {
        setDataInternal(data, offset, length, true);
    }

    @Override
    public byte[] toByteArray() {
        byte[] data = new byte[getAlignedLength()];
        return toByteArray(data, 0);
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
    public OfpHelloVersionBitmapElement fromInputStream(InputStream is)
            throws IOException {
        return readFromInputStream(is);
    }

    public byte[] writeData(byte[] data) {
        return writeData(data, 0);
    }

    @Override
    public OfpHelloVersionBitmapElement fromByteArray(byte[] data) {
        return new OfpHelloVersionBitmapElement(data);
    }

    @Override
    public OfpHelloVersionBitmapElement fromByteArray(byte[] data, int offset) {
        return new OfpHelloVersionBitmapElement(data, offset);
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
        return (offset + OFP_HELLO_ELEMENT_BASE_LEN + OFP_HELLO_VERSION_BITMAP_ELEMENT_DATA_OFFSET);
    }

    public static byte[] readOfpHelloVersionBitmapElement(InputStream is)
            throws IOException {
        return readOfpHelloVersionBitmapElement(is, null, 0);
    }

    public static byte[] readOfpHelloVersionBitmapElement(InputStream is,
            byte[] data) throws IOException {
        return readOfpHelloVersionBitmapElement(is, data, 0);
    }

    public static byte[] readOfpHelloVersionBitmapElement(InputStream is,
            byte[] data, int offset) throws IOException {
        byte[] tmpData = OfpHelloGenericElement.readOfpHelloGenericElement(is,
                data, offset);
        int tmpOffset = ((tmpData != data) ? 0 : offset);
        OfpHelloElementType type = readType(tmpData, tmpOffset);
        short len = readLength(tmpData, tmpOffset);

        if (type != OfpHelloElementType.OFPHET_VERSIONBITMAP) {
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

    public static OfpHelloVersionBitmapElement readFromInputStream(
            InputStream is) throws IOException {
        return new OfpHelloVersionBitmapElement(is);
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
                "%s : Data=%s\n",
                super.toString(),
                (getData() == null) ? "null" : PrintUtils.byteArrayToHexString(
                        getData(), 0, getData().length, 8));
    }

    public boolean equals(OfpHelloVersionBitmapElement hdr) {
        if (super.equals(hdr) == true) {
            if ((getData().length == hdr.getData().length)
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
        if (o instanceof OfpHelloVersionBitmapElement) {
            return equals((OfpHelloVersionBitmapElement) o);
        }
        return false;
    }
}
