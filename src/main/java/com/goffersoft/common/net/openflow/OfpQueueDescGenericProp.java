/**
 ** File: OfpQueueDescGenericProp.java
 **
 ** Description : Open Packet Queue Generic Properties
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

public class OfpQueueDescGenericProp implements
        OfpSerializable<OfpQueueDescGenericProp> {

    private static final Logger log = Logger
            .getLogger(OfpQueueDescGenericProp.class);

    public static enum OfpQueueDescPropType {
        OFPQDPT_MIN_RATE(1), OFPQDPT_MAX_RATE(2), OFPQDPT_EXPERIMENTER(0xffff), OFPQDPT_UNK(
                2), ;

        public static final String propDescr[] = {
                "No Property defined for queue (default)",
                "Minimum datarate guaranteed", "Unknown property type", };

        private short type;

        private OfpQueueDescPropType(int ptype) {
            setValueInternal((short) ptype);
        }

        public short getValue() {
            return type;
        }

        private void setValueInternal(short type) {
            this.type = type;
        }

        public void setValue(short type) {
            if (this == OFPQDPT_UNK) {
                this.type = type;
            }
        }

        public String getDescr() {
            if (this == OFPQDPT_UNK) {
                return String.format("%s Type=%d(0x%04x)",
                        propDescr[ordinal()], type & 0xffff, type & 0xffff);
            }
            return propDescr[ordinal()];
        }

        @Override
        public String toString() {
            return getDescr();
        }

        public static OfpQueueDescPropType getEnum(short ptype) {
            int tmp = ptype & 0xffff;
            if (tmp == 1) {
                return OFPQDPT_MIN_RATE;
            } else if (tmp == 2) {
                return OFPQDPT_MAX_RATE;
            } else if (tmp == 0xffff) {
                return OFPQDPT_EXPERIMENTER;
            } else {
                OfpQueueDescPropType tmp1 = OFPQDPT_UNK;
                tmp1.setValueInternal(ptype);
                return tmp1;
            }
        }
    }

    private OfpQueueDescPropType type;
    private short length;

    public static final int OFP_QUEUE_DESC_PROP_TYPE_OFFSET = 0;
    public static final int OFP_QUEUE_DESC_PROP_LENGTH_OFFSET = 2;
    public static final int OFP_QUEUE_DESC_PROP_BASE_LEN = 4;

    public OfpQueueDescGenericProp(OfpQueueDescGenericProp hdr) {
        type = hdr.type;
        length = hdr.length;
    }

    public OfpQueueDescGenericProp(OfpQueueDescPropType ptype, short len) {
        type = ptype;
        length = len;
    }

    public OfpQueueDescGenericProp(byte[] data) {
        this(data, 0);
    }

    public OfpQueueDescGenericProp(byte[] data, int offset) {
        type = readType(data, offset);
        length = readLength(data, offset);
    }

    public OfpQueueDescGenericProp(InputStream is) throws IOException {
        type = readType(is);
        length = readLength(is);
    }

    public OfpQueueDescPropType getType() {
        return type;
    }

    public void setType(OfpQueueDescPropType type) {
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

    public static void writePadding(byte[] data, int offset, int len) {
        for (int i = offset; i < len; i++) {
            data[i] = 0x00;
        }
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
    public OfpQueueDescGenericProp fromInputStream(InputStream is)
            throws IOException {
        return readFromInputStream(is);
    }

    @Override
    public OfpQueueDescGenericProp fromByteArray(byte[] data) {
        return new OfpQueueDescGenericProp(data, 0);
    }

    @Override
    public OfpQueueDescGenericProp fromByteArray(byte[] data, int offset) {
        return new OfpQueueDescGenericProp(data, offset);
    }

    @Override
    public byte[] toByteArray() {
        byte[] hdr = new byte[OFP_QUEUE_DESC_PROP_BASE_LEN];
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

    public static byte[] readOfpQueueDescGenericProp(InputStream is)
            throws IOException {
        return readOfpQueueDescGenericProp(is, null, 0);
    }

    public static byte[] readOfpQueueDescGenericProp(InputStream is, byte[] data)
            throws IOException {
        return readOfpQueueDescGenericProp(is, data, 0);
    }

    public static byte[] readOfpQueueDescGenericProp(InputStream is,
            byte[] data, int offset) throws IOException {
        return ReadUtils.readInput(is, null, 0, OFP_QUEUE_DESC_PROP_BASE_LEN);
    }

    public static OfpQueueDescGenericProp readFromInputStream(InputStream is)
            throws IOException {
        return new OfpQueueDescGenericProp(is);
    }

    public static OfpQueueDescPropType readType(byte[] queuedescprop) {
        return readType(queuedescprop, 0);
    }

    public static OfpQueueDescPropType readType(byte[] queuedescprop, int offset) {
        return OfpQueueDescPropType.getEnum(EndianConversion
                .byteArrayToShortBE(queuedescprop, offset
                        + OFP_QUEUE_DESC_PROP_TYPE_OFFSET));
    }

    public static OfpQueueDescPropType readType(InputStream is)
            throws IOException {
        return OfpQueueDescPropType.getEnum(EndianConversion
                .inputStreamToShortBE(is));
    }

    public static short readLength(byte[] queuedescprop) {
        return readLength(queuedescprop, 0);
    }

    public static short readLength(byte[] queuedescprop, int offset) {
        return EndianConversion.byteArrayToShortBE(queuedescprop, offset
                + OFP_QUEUE_DESC_PROP_LENGTH_OFFSET);
    }

    public static short readLength(InputStream is) throws IOException {
        return EndianConversion.inputStreamToShortBE(is);
    }

    @Override
    public String toString() {
        return String.format("Type=%s : Length=%d(0x%04x)\n", getType()
                .toString(), getLength(), getLength());
    }

    public boolean equals(OfpQueueDescGenericProp hdr) {
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
        if (o instanceof OfpQueueDescGenericProp) {
            return equals((OfpQueueDescGenericProp) o);
        }
        return false;
    }
}
