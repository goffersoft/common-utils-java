/**
 ** File: OfpHeader.java
 **
 ** Description : OpenFlow Header class
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

public class OfpHeader implements OfpSerializable<OfpHeader> {

    private static final Logger log = Logger.getLogger(OfpHeader.class);

    private byte version = OFP_VERSION;
    private OfpType type;
    private short length;
    private int xid;

    /**
     * Version Number Non-Experimental Versions Released 0x01 = 1.0 0x02 = 1.1
     * 0x03 = 1.2 0x04 = 1.3.X 0x05 = 1.4.X
     **/
    public static final int OFP_VERSION = 0x05;
    public static final int OFP_HDR_LEN = 8;
    public static final int OFP_HDR_VERSION_OFFSET = 0;
    public static final int OFP_HDR_TYPE_OFFSET = 1;
    public static final int OFP_HDR_LENGTH_OFFSET = 2;
    public static final int OFP_HDR_XID_OFFSET = 4;

    OfpHeader(OfpHeader hdr) {
        setVersion(hdr.getVersion());
        setType(hdr.getType());
        setLength(hdr.getLength());
        setTransactionId(hdr.getTransactionId());
    }

    OfpHeader(byte version, OfpType type, short length, int xid) {
        setVersion(version);
        setType(type);
        setLength(length);
        setTransactionId(xid);
    }

    OfpHeader(byte version, OfpType type, int xid) {
        setVersion(version);
        setType(type);
        setLength((short) OFP_HDR_LEN);
        setTransactionId(xid);
    }

    OfpHeader(OfpType type, short length, int xid) {
        setVersion((byte) OFP_VERSION);
        setType(type);
        setLength(length);
        setTransactionId(xid);
    }

    OfpHeader(OfpType type, int xid) {
        setVersion((byte) OFP_VERSION);
        setType(type);
        setLength((short) OFP_HDR_LEN);
        setTransactionId(xid);
    }

    OfpHeader(byte[] data) {
        this(data, 0);
    }

    OfpHeader(byte[] data, int offset) {
        setVersion(readVersion(data, offset));
        setType(readType(data, offset));
        setLength(readLength(data, offset));
        setTransactionId(readTransactionId(data, offset));
    }

    OfpHeader(InputStream is) throws IOException {
        setVersion(readVersion(is));
        setType(readType(is));
        setLength(readLength(is));
        setTransactionId(readTransactionId(is));
    }

    public byte getVersion() {
        return version;
    }

    public String getVersionAsString() {
        switch (version) {
            case 0x01:
                return "1.0";
            case 0x02:
                return "1.1";
            case 0x03:
                return "1.2";
            case 0x04:
                return "1.3.X";
            case 0x05:
                return "1.4.X";
            default:
                return ("Unknown Version : bytevalue=" + String.format(
                        "0x%02x", version));
        }
    }

    public void setVersion(byte version) {
        this.version = version;
    }

    public OfpType getType() {
        return type;
    }

    public void setType(OfpType type) {
        this.type = type;
    }

    public short getLength() {
        return length;
    }

    public void setLength(short length) {
        this.length = length;
    }

    public int getTransactionId() {
        return xid;
    }

    public void setTransactionId(int xid) {
        this.xid = xid;
    }

    public byte[] toByteArray() {
        byte[] hdr = new byte[OFP_HDR_LEN];
        return toByteArray(hdr, 0);
    }

    public byte[] toByteArray(byte[] data) {
        return toByteArray(data, 0);
    }

    public byte[] toByteArray(byte[] data, int offset) {
        writeVersion(data, offset);
        writeType(data, offset);
        writeLength(data, offset);
        writeTransactionId(data, offset);

        return data;
    }

    public OutputStream toOutputStream(OutputStream os) throws IOException {
        writeVersion(os);
        writeType(os);
        writeLength(os);
        writeTransactionId(os);

        return os;
    }

    public OfpHeader fromInputStream(InputStream is) throws IOException {
        return readFromInputStream(is);
    }

    public OfpHeader fromByteArray(byte[] data) {
        return new OfpHeader(data);
    }

    public OfpHeader fromByteArray(byte[] data, int offset) {
        return new OfpHeader(data, offset);
    }

    public byte[] writeVersion(byte[] data) {
        return writeVersion(data, 0);
    }

    public byte[] writeVersion(byte[] data, int offset) {
        data[offset + OFP_HDR_VERSION_OFFSET] = getVersion();
        return data;
    }

    public OutputStream writeVersion(OutputStream os) throws IOException {
        os.write(getVersion());
        return os;
    }

    public byte[] writeType(byte[] data) {
        return writeType(data, 0);
    }

    public byte[] writeType(byte[] data, int offset) {
        data[offset + OFP_HDR_TYPE_OFFSET] = getType().getValue();
        return data;
    }

    public OutputStream writeType(OutputStream os) throws IOException {
        os.write(getType().getValue());
        return os;
    }

    public byte[] writeLength(byte[] data) {
        return writeLength(data, 0);
    }

    public byte[] writeLength(byte[] data, int offset) {
        EndianConversion.shortToByteArrayBE(data, offset
                + OFP_HDR_LENGTH_OFFSET, getLength());
        return data;
    }

    public OutputStream writeLength(OutputStream os) throws IOException {
        EndianConversion.shortToOutputStreamBE(os, getLength());
        return os;
    }

    public byte[] writeTransactionId(byte[] data) {
        return writeTransactionId(data, 0);
    }

    public byte[] writeTransactionId(byte[] data, int offset) {
        EndianConversion.intToByteArrayBE(data, offset + OFP_HDR_XID_OFFSET,
                getTransactionId());
        return data;
    }

    public OutputStream writeTransactionId(OutputStream os) throws IOException {
        EndianConversion.intToOutputStreamBE(os, getTransactionId());
        return os;
    }

    public static byte[] readOfpHeader(InputStream is) throws IOException {
        return readOfpHeader(is, null, 0);
    }

    public static byte[] readOfpHeader(InputStream is, byte[] data)
            throws IOException {
        return readOfpHeader(is, data, 0);
    }

    public static byte[] readOfpHeader(InputStream is, byte[] data, int offset)
            throws IOException {
        return ReadUtils.readInput(is, data, offset, OFP_HDR_LEN);
    }

    public static OfpHeader readFromInputStream(InputStream is)
            throws IOException {
        return new OfpHeader(is);
    }

    public static byte readVersion(byte[] ofphdr) {
        return readVersion(ofphdr, 0);
    }

    public static byte readVersion(byte[] ofphdr, int offset) {
        return ofphdr[offset + OFP_HDR_VERSION_OFFSET];
    }

    public static byte readVersion(InputStream is) throws IOException {
        return (byte) is.read();
    }

    public static OfpType readType(byte[] ofphdr) {
        return readType(ofphdr, 0);
    }

    public static OfpType readType(byte[] ofphdr, int offset) {
        return OfpType.getEnum(ofphdr[offset + OFP_HDR_TYPE_OFFSET]);
    }

    public static OfpType readType(InputStream is) throws IOException {
        return OfpType.getEnum((byte) is.read());
    }

    public static short readLength(byte[] ofphdr) {
        return readLength(ofphdr, 0);
    }

    public static short readLength(byte[] ofphdr, int offset) {
        return EndianConversion.byteArrayToShortBE(ofphdr, offset
                + OFP_HDR_LENGTH_OFFSET);
    }

    public static short readLength(InputStream is) throws IOException {
        return EndianConversion.inputStreamToShortBE(is);
    }

    public static int readTransactionId(byte[] ofphdr) {
        return readTransactionId(ofphdr, 0);
    }

    public static int readTransactionId(byte[] ofphdr, int offset) {
        return EndianConversion.byteArrayToIntBE(ofphdr, offset
                + OFP_HDR_XID_OFFSET);
    }

    public static int readTransactionId(InputStream is) throws IOException {
        return EndianConversion.inputStreamToIntBE(is);
    }

    @Override
    public String toString() {
        return String.format(
                "Version=%s : Type=%s : Length=%d(0x%04x) : XID=%d(0x%08x)\n",
                getVersionAsString(), getType().toString(), getLength(),
                getLength(), getTransactionId(), getTransactionId());
    }

    public boolean equals(OfpHeader hdr) {
        if ((getVersion() == hdr.getVersion()) && (getType() == hdr.getType())
                && (getTransactionId() == hdr.getTransactionId())
                && (getLength() == hdr.getLength())) {
            return true;
        }
        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o instanceof OfpHeader) {
            return equals((OfpHeader) o);
        }
        return false;
    }
}
