/**
 ** File: OfpPortDescGenericProp.java
 **
 ** Description : OpenFlow Generic Port Properties class
 **               (base class for all port properties classes)
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

public class OfpPortDescGenericProp
        implements
        OfpSerializable<OfpPortDescGenericProp> {

    private static final Logger log = Logger
            .getLogger(OfpPortDescGenericProp.class);

    public static enum OfpPortDescPropType {
        OFPPDPT_ETHERNET(0),
        OFPPDPT_OPTICAL(1),
        OFPPDPT_EXPERIMENTER(0xffff),
        OFPPDPT_UNK(0), ;

        public static final String propDescr[] = {
                "Ethernet Property type",
                "Optical proeprty type",
                "Experimental property type",
                "Unknown property type",
        };

        private short type;

        private OfpPortDescPropType(int ptype) {
            setValueInternal((short) ptype);
        }

        public short getValue() {
            return type;
        }

        private void setValueInternal(short type) {
            this.type = type;
        }

        public void setValue(short type) {
            if (this == OFPPDPT_UNK) {
                this.type = type;
            }
        }

        public String getDescr() {
            if (this == OFPPDPT_UNK) {
                return String.format("%s Type=%d(0x%04x)",
                        propDescr[ordinal()], type & 0xffff, type & 0xffff);
            }
            return propDescr[ordinal()];
        }

        @Override
        public String toString() {
            return getDescr();
        }

        public static OfpPortDescPropType getEnum(short ptype) {
            int tmp = ptype & 0xffff;
            if (tmp == 0) {
                return OFPPDPT_ETHERNET;
            } else if (tmp == 1) {
                return OFPPDPT_OPTICAL;
            } else if (tmp == 0xffff) {
                return OFPPDPT_EXPERIMENTER;
            } else {
                OfpPortDescPropType tmp1 = OFPPDPT_UNK;
                tmp1.setValueInternal(ptype);
                return tmp1;
            }
        }
    }

    private OfpPortDescPropType type;
    private short length;

    public static final int OFP_PORT_DESC_PROP_TYPE_OFFSET = 0;
    public static final int OFP_PORT_DESC_PROP_LENGTH_OFFSET = 2;
    public static final int OFP_PORT_DESC_PROP_BASE_LEN = 4;

    public OfpPortDescGenericProp(OfpPortDescGenericProp hdr) {
        setType(hdr.getType());
        setLength(hdr.getLength());
    }

    public OfpPortDescGenericProp(OfpPortDescPropType type, short length) {
        setType(type);
        setLength(length);
    }

    public OfpPortDescGenericProp(byte[] data) {
        this(data, 0);
    }

    public OfpPortDescGenericProp(byte[] data, int offset) {
        setType(readType(data, offset));
        setLength(readLength(data, offset));
    }

    public OfpPortDescGenericProp(InputStream is) throws IOException {
        setType(readType(is));
        setLength(readLength(is));
    }

    public OfpPortDescPropType getType() {
        return type;
    }

    public void setType(OfpPortDescPropType type) {
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
    public OfpPortDescGenericProp fromInputStream(InputStream is)
            throws IOException {
        return readFromInputStream(is);
    }

    @Override
    public OfpPortDescGenericProp fromByteArray(byte[] data) {
        return new OfpPortDescGenericProp(data);
    }

    @Override
    public OfpPortDescGenericProp fromByteArray(byte[] data, int offset) {
        return new OfpPortDescGenericProp(data, offset);
    }

    @Override
    public byte[] toByteArray() {
        byte[] hdr = new byte[OFP_PORT_DESC_PROP_BASE_LEN];
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

    public static byte[] readOfpPortDescGenericProp(InputStream is)
            throws IOException {
        return readOfpPortDescGenericProp(is, null, 0);
    }

    public static
            byte[]
            readOfpPortDescGenericProp(InputStream is, byte[] data)
                    throws IOException {
        return readOfpPortDescGenericProp(is, data, 0);
    }

    public static byte[] readOfpPortDescGenericProp(InputStream is,
            byte[] data, int offset) throws IOException {
        return ReadUtils.readInput(is, null, 0, OFP_PORT_DESC_PROP_BASE_LEN);
    }

    public static OfpPortDescGenericProp readFromInputStream(InputStream is)
            throws IOException {
        return new OfpPortDescGenericProp(is);
    }

    public static OfpPortDescPropType readType(byte[] portdescprop) {
        return readType(portdescprop, 0);
    }

    public static OfpPortDescPropType readType(byte[] portdescprop, int offset) {
        return OfpPortDescPropType.getEnum(EndianConversion.byteArrayToShortBE(
                portdescprop, offset + OFP_PORT_DESC_PROP_TYPE_OFFSET));
    }

    public static OfpPortDescPropType readType(InputStream is)
            throws IOException {
        return OfpPortDescPropType.getEnum(EndianConversion
                .inputStreamToShortBE(is));
    }

    public static short readLength(byte[] portdescprop) {
        return readLength(portdescprop, 0);
    }

    public static short readLength(byte[] portdescprop, int offset) {
        return EndianConversion.byteArrayToShortBE(portdescprop, offset
                + OFP_PORT_DESC_PROP_LENGTH_OFFSET);
    }

    public static short readLength(InputStream is) throws IOException {
        return EndianConversion.inputStreamToShortBE(is);
    }

    @Override
    public String toString() {
        return String.format("Type=%s : Length=%d(0x%04x)\n", getType()
                .toString(), getLength(), getLength());
    }

    public boolean equals(OfpPortDescGenericProp hdr) {
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
        if (o instanceof OfpPortDescGenericProp) {
            return equals((OfpPortDescGenericProp) o);
        }
        return false;
    }
}