/**
 ** File: OfpHelloGenericElement.java
 **
 ** Description : OfpHelloGenericElement Header class
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
import com.goffersoft.common.utils.ReadUtils;

public class OfpHelloGenericElement implements
        OfpSerializable<OfpHelloGenericElement> {

    private static final Logger log = Logger
            .getLogger(OfpHelloGenericElement.class);

    public static enum OfpHelloElementType {
        OFPHET_VERSIONBITMAP(1),
        OFPHET_UNK(0), ;

        public static final String elemTypeDescr[] = {
                "Bitmap of version supported",
                "Unknown hello element type",
        };

        private short type;

        private OfpHelloElementType(int htype) {
            setValueInternal((short) htype);
        }

        public short getValue() {
            return type;
        }

        private void setValueInternal(short type) {
            this.type = type;
        }

        public void setValue(short type) {
            if (this == OFPHET_UNK) {
                this.type = type;
            }
        }

        public String getDescr() {
            if (this == OFPHET_UNK) {
                return String.format("%s Type=%d(0x%04x)",
                        elemTypeDescr[ordinal()], type & 0xffff, type & 0xffff);
            }
            return elemTypeDescr[ordinal()];
        }

        @Override
        public String toString() {
            return getDescr();
        }

        public static OfpHelloElementType getEnum(short htype) {
            int tmp = htype & 0xffff;
            if (tmp == 1) {
                return OFPHET_VERSIONBITMAP;
            } else {
                OfpHelloElementType tmp1 = OFPHET_UNK;
                tmp1.setValueInternal(htype);
                return tmp1;
            }
        }
    }

    private OfpHelloElementType type;
    private short length;

    public static final int OFP_HELLO_ELEMENT_TYPE_OFFSET = 0;
    public static final int OFP_HELLO_ELEMENT_LENGTH_OFFSET = 2;
    public static final int OFP_HELLO_ELEMENT_BASE_LEN = 4;

    public OfpHelloGenericElement(OfpHelloGenericElement hdr) {
        setType(hdr.getType());
        setLength(hdr.getLength());
    }

    public OfpHelloGenericElement(OfpHelloElementType type, short length) {
        setType(type);
        setLength(length);
    }

    public OfpHelloGenericElement(byte[] data) {
        this(data, 0);
    }

    public OfpHelloGenericElement(byte[] data, int offset) {
        setType(readType(data, offset));
        setLength(readLength(data, offset));
    }

    public OfpHelloGenericElement(InputStream is) throws IOException {
        setType(readType(is));
        setLength(readLength(is));
    }

    public OfpHelloElementType getType() {
        return type;
    }

    public void setType(OfpHelloElementType type) {
        this.type = type;
    }

    public short getLength() {
        return length;
    }

    public short getAlignedLength() {
        return (short) (((length + 7) / 8) * 8);
    }

    public static short getAlignedLength(int len) {
        return (short) (((len + 7) / 8) * 8);
    }

    public int getPadLength() {
        return getAlignedLength() - getLength();
    }

    public static int getPadLength(int len) {
        return getAlignedLength(len) - len;
    }

    public void readPadding(InputStream is) throws IOException {
        for (int i = 0; i < (getAlignedLength() - getLength()); i++) {
            is.read();
        }
    }

    public void writePadding(OutputStream os) throws IOException {
        for (int i = 0; i < (getAlignedLength() - getLength()); i++) {
            os.write(0x0);
        }
    }

    public void writePadding(byte[] data) {
        writePadding(data, 0);
    }

    public void writePadding(byte[] data, int offset) {
        for (int i = offset; i < (getAlignedLength() - getLength()); i++) {
            data[i] = 0x00;
        }
    }

    public static void writePadding(byte[] data, int offset, int len) {
        for (int i = offset; i < len; i++) {
            data[i] = 0x00;
        }
    }

    public static void readPadding(InputStream is, int len) throws IOException {
        for (int i = 0; i < getAlignedLength(len) - len; i++) {
            is.read();
        }
    }

    public static void writePadding(OutputStream os, int len)
            throws IOException {
        for (int i = 0; i < getAlignedLength(len) - len; i++) {
            os.write(0x0);
        }
    }

    public void setLength(short length) {
        this.length = length;
    }

    @Override
    public OfpHelloGenericElement fromInputStream(InputStream is)
            throws IOException {
        return readFromInputStream(is);
    }

    @Override
    public OfpHelloGenericElement fromByteArray(byte[] data) {
        return new OfpHelloGenericElement(data, 0);
    }

    @Override
    public OfpHelloGenericElement fromByteArray(byte[] data, int offset) {
        return new OfpHelloGenericElement(data, offset);
    }

    @Override
    public byte[] toByteArray() {
        byte[] hdr = new byte[OFP_HELLO_ELEMENT_BASE_LEN];
        return toByteArray(hdr, 0);
    }

    @Override
    public byte[] toByteArray(byte[] data) {
        return toByteArray(data, 0);
    }

    @Override
    public byte[] toByteArray(byte[] data, int offset) {
        writeType(data, offset);
        writeLength(data, offset);

        return data;
    }

    @Override
    public OutputStream toOutputStream(OutputStream os) throws IOException {
        writeType(os);
        writeLength(os);
        return os;
    }

    public byte[] writeType(byte[] data) {
        return writeType(data, 0);
    }

    public byte[] writeType(byte[] data, int offset) {
        EndianConversion.shortToByteArrayBE(data, offset, getType().getValue());
        return data;
    }

    public OutputStream writeType(OutputStream os) throws IOException {
        EndianConversion.shortToOutputStreamBE(os, getType().getValue());
        return os;
    }

    public byte[] writeLength(byte[] data) {
        return writeLength(data, 0);
    }

    public byte[] writeLength(byte[] data, int offset) {
        EndianConversion.shortToByteArrayBE(data, offset, getLength());
        return data;
    }

    public OutputStream writeLength(OutputStream os) throws IOException {
        EndianConversion.shortToOutputStreamBE(os, getLength());
        return os;
    }

    public static byte[] readOfpHelloGenericElement(InputStream is)
            throws IOException {
        return readOfpHelloGenericElement(is, null, 0);
    }

    public static byte[] readOfpHelloGenericElement(InputStream is, byte[] data)
            throws IOException {
        return readOfpHelloGenericElement(is, data, 0);
    }

    public static byte[] readOfpHelloGenericElement(InputStream is,
            byte[] data, int offset) throws IOException {
        return ReadUtils.readInput(is, null, 0, OFP_HELLO_ELEMENT_BASE_LEN);
    }

    public static OfpHelloGenericElement readFromInputStream(InputStream is)
            throws IOException {
        return new OfpHelloGenericElement(is);
    }

    public static OfpHelloElementType readType(byte[] helloelem) {
        return readType(helloelem, 0);
    }

    public static OfpHelloElementType readType(byte[] helloelem, int offset) {
        return OfpHelloElementType.getEnum(EndianConversion.byteArrayToShortBE(
                helloelem, offset + OFP_HELLO_ELEMENT_TYPE_OFFSET));
    }

    public static OfpHelloElementType readType(InputStream is)
            throws IOException {
        return OfpHelloElementType.getEnum(EndianConversion
                .inputStreamToShortBE(is));
    }

    public static short readLength(byte[] helloelem) {
        return readLength(helloelem, 0);
    }

    public static short readLength(byte[] helloelem, int offset) {
        return EndianConversion.byteArrayToShortBE(helloelem, offset
                + OFP_HELLO_ELEMENT_LENGTH_OFFSET);
    }

    public static short readLength(InputStream is) throws IOException {
        return EndianConversion.inputStreamToShortBE(is);
    }

    @Override
    public String toString() {
        return String.format("Type=%s : Length=%d(0x%04x)\n", getType()
                .toString(), getLength(), getLength());
    }

    public boolean equals(OfpHelloGenericElement hdr) {
        if ((getType() == hdr.getType()) && (getLength() == hdr.getLength())) {
            return true;
        }
        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o instanceof OfpHelloGenericElement) {
            return equals((OfpHelloGenericElement) o);
        }
        return false;
    }
}
