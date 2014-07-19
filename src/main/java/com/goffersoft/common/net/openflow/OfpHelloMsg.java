/**
 ** File: OfpHelloMsg.java
 **
 ** Description : OfpHelloMsg Header class
 **               -- OpenFlow Switch Specification Version 1.4.0 - October 14th, 2013
 **
 ** Date           Author                          Comments
 ** 08/31/2013     Prakash Easwar                  Created  
 **/
package com.goffersoft.common.net.openflow;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.LinkedList;

import org.apache.log4j.Logger;

import com.goffersoft.common.utils.ReadUtils;

public class OfpHelloMsg
        extends
        OfpHeader {

    private static final Logger log = Logger.getLogger(OfpHelloMsg.class);

    public static final int OFP_HELLO_MSG_HDR_LEN = 0;

    public static final int OFP_HELLO_MSG_PROP_OFFSET = 0;

    private LinkedList<OfpHelloGenericElement> elemHdrs;

    OfpHelloMsg(byte version, int xid) {
        super(version, OfpType.OFPT_HELLO, xid);
    }

    OfpHelloMsg(int xid) {
        super(OfpType.OFPT_HELLO, xid);
    }

    OfpHelloMsg(int xid, LinkedList<OfpHelloGenericElement> elemHdrs) {
        super(OfpType.OFPT_HELLO, xid);
        if (elemHdrs != null) {
            addElements(elemHdrs);
        }
    }

    OfpHelloMsg(byte version, int xid,
            LinkedList<OfpHelloGenericElement> elemHdrs) {
        super(version, OfpType.OFPT_HELLO, xid);
        if (elemHdrs != null) {
            addElements(elemHdrs);
        }
    }

    OfpHelloMsg(byte[] data) {
        this(data, 0);
    }

    OfpHelloMsg(byte[] data, int offset) {
        super(data, offset);

        if (getType() != OfpType.OFPT_HELLO) {
            throw new IllegalArgumentException("Bad Data : Expected  : 0x"
                    + Integer.toHexString(OfpType.OFPT_HELLO.getValue())
                    + " Got : 0x" + Integer.toHexString(getType().getValue()));
        }

        setElementListInternal(readElementList(data, offset, getLength()));
    }

    OfpHelloMsg(InputStream is) throws IOException {
        super(is);

        if (getType() != OfpType.OFPT_HELLO) {
            throw new IOException("Bad Data : Expected  : 0x"
                    + Integer.toHexString(OfpType.OFPT_HELLO.getValue())
                    + " Got : 0x" + Integer.toHexString(getType().getValue()));
        }

        setElementListInternal(readElementList(is, getLength()));
    }

    private void addElements(LinkedList<OfpHelloGenericElement> elemHdrs) {
        Iterator<OfpHelloGenericElement> it = elemHdrs.iterator();
        while (it.hasNext()) {
            OfpHelloGenericElement elem = it.next();
            addElement(elem);
        }
    }

    public LinkedList<OfpHelloGenericElement> getElementList() {
        if (elemHdrs == null) {
            elemHdrs = new LinkedList<OfpHelloGenericElement>();
        }
        return elemHdrs;
    }

    private void setElementListInternal(
            LinkedList<OfpHelloGenericElement> elemHdrs) {
        this.elemHdrs = elemHdrs;
    }

    public void setElementList(LinkedList<OfpHelloGenericElement> elemHdrs) {
        setLength((short) OFP_HDR_LEN);
        if (elemHdrs == null) {
            elemHdrs = new LinkedList<OfpHelloGenericElement>();
            return;
        }
        addElements(elemHdrs);
    }

    private
            void
            addElement(OfpHelloGenericElement elemHdr, boolean adjustLength) {
        if (elemHdr == null) {
            return;
        }
        getElementList().add(elemHdr);

        if (adjustLength == true) {
            setLength((short) (getLength() + elemHdr.getAlignedLength()));
        }
    }

    public void addElement(OfpHelloGenericElement elemHdr) {
        addElement(elemHdr, true);
    }

    private void removeElement(OfpHelloGenericElement elemHdr,
            boolean adjustLength) {
        if (elemHdr == null) {
            return;
        }
        getElementList().remove(elemHdr);

        if (adjustLength == true) {
            setLength((short) (getLength() - elemHdr.getAlignedLength()));
        }
    }

    public void removeElement(OfpHelloGenericElement elemHdr) {
        removeElement(elemHdr, true);
    }

    @Override
    public OutputStream toOutputStream(OutputStream os) throws IOException {
        super.toOutputStream(os);
        return writeElementList(os);
    }

    @Override
    public byte[] toByteArray() {
        byte[] hdr = new byte[getLength()];
        return toByteArray(hdr, 0);
    }

    @Override
    public byte[] toByteArray(byte[] data) {
        return toByteArray(data, 0);
    }

    @Override
    public byte[] toByteArray(byte[] data, int offset) {
        super.toByteArray(data, offset);
        return writeElementList(data, offset + OFP_HDR_LEN);
    }

    @Override
    public OfpHelloMsg fromInputStream(InputStream is) throws IOException {
        return readFromInputStream(is);
    }

    @Override
    public OfpHelloMsg fromByteArray(byte[] data) {
        return new OfpHelloMsg(data);
    }

    @Override
    public OfpHelloMsg fromByteArray(byte[] data, int offset) {
        return new OfpHelloMsg(data, offset);
    }

    public byte[] writeElementList(byte[] data) {
        return writeElementList(data, 0);
    }

    public byte[] writeElementList(byte[] data, int offset) {
        int start = offset;
        Iterator<OfpHelloGenericElement> it = getElementList().iterator();
        while (it.hasNext()) {
            OfpHelloGenericElement elem = it.next();
            elem.toByteArray(data, start);
            start += elem.getAlignedLength();
        }
        return data;
    }

    public OutputStream writeElementList(OutputStream os) throws IOException {
        Iterator<OfpHelloGenericElement> it = getElementList().iterator();
        while (it.hasNext()) {
            OfpHelloGenericElement elem = it.next();
            elem.toOutputStream(os);
        }
        return os;
    }

    public static byte[] readOfpHelloMsg(InputStream is) throws IOException {
        return readOfpHelloMsg(is, null, 0);
    }

    public static byte[] readOfpHelloMsg(InputStream is, byte[] data)
            throws IOException {
        return readOfpHelloMsg(is, data, 0);
    }

    public static
            byte[]
            readOfpHelloMsg(InputStream is, byte[] data, int offset)
                    throws IOException {
        byte[] tmpData = OfpHeader.readOfpHeader(is, data, offset);
        int tmpOffset = ((tmpData != data) ? 0 : offset);
        OfpType type = readType(tmpData, tmpOffset);
        short len = readLength(tmpData, tmpOffset);

        if (type != OfpType.OFPT_HELLO) {
            throw new IOException("Bad Data : Expected  : 0x"
                    + Integer.toHexString(OfpType.OFPT_HELLO.getValue())
                    + " Got : 0x" + Integer.toHexString(type.getValue()));
        }
        if (len > OFP_HDR_LEN) {
            byte[] tmp1Data = ReadUtils.readInput(is, tmpData, tmpOffset
                    + OFP_HDR_LEN, len - OFP_HDR_LEN);
            if (tmp1Data != tmpData) {
                System.arraycopy(tmpData, tmpOffset, tmp1Data, 0, OFP_HDR_LEN);
                return tmp1Data;
            }
        }
        return tmpData;
    }

    public static
            LinkedList<OfpHelloGenericElement>
            readElementList(byte[] data) {
        return readElementList(data, 0);
    }

    public static LinkedList<OfpHelloGenericElement> readElementList(
            byte[] data, int offset) {
        OfpType type = readType(data, offset);
        if (type != OfpType.OFPT_HELLO) {
            throw new IllegalArgumentException("Bad Data : Expected  : 0x"
                    + Integer.toHexString(OfpType.OFPT_HELLO.getValue())
                    + " Got : 0x" + Integer.toHexString(type.getValue()));
        }
        int pktlen = readLength(data, offset);
        return readElementList(data, offset, pktlen);
    }

    public static LinkedList<OfpHelloGenericElement> readElementList(
            byte[] data, int offset, int pktlen) {
        int start = offset + OFP_HDR_LEN;
        int end = start + pktlen - OFP_HDR_LEN;
        OfpHelloGenericElement tmp;
        LinkedList<OfpHelloGenericElement> elemList =
                new LinkedList<OfpHelloGenericElement>();

        while (start < end) {
            OfpHelloGenericElement.OfpHelloElementType type =
                    OfpHelloGenericElement
                            .readType(data, start);

            if (type == OfpHelloGenericElement.OfpHelloElementType.OFPHET_VERSIONBITMAP) {
                tmp = new OfpHelloVersionBitmapElement(data, start);
            } else {
                tmp = new OfpHelloUnknownElement(data, start);
            }
            elemList.add(tmp);
            start += tmp.getAlignedLength();
        }

        return elemList;
    }

    public static LinkedList<OfpHelloGenericElement> readElementList(
            InputStream is, int pktlen) throws IOException {
        int start = 0;
        int end = start + pktlen - OFP_HDR_LEN;
        OfpHelloGenericElement tmp;
        LinkedList<OfpHelloGenericElement> elemList =
                new LinkedList<OfpHelloGenericElement>();

        while (start < end) {
            tmp = new OfpHelloGenericElement(is);

            if (tmp.getType() == OfpHelloGenericElement.OfpHelloElementType.OFPHET_VERSIONBITMAP) {
                tmp = new OfpHelloVersionBitmapElement(tmp, is);
            } else {
                tmp = new OfpHelloUnknownElement(tmp, is);
            }
            elemList.add(tmp);
            start += tmp.getAlignedLength();
        }

        return elemList;
    }

    public static OfpHelloMsg readFromInputStream(InputStream is)
            throws IOException {
        return new OfpHelloMsg(is);
    }

    @Override
    public String toString() {

        StringBuffer sb = new StringBuffer();

        sb.append(String.format("%s \n", super.toString()));

        Iterator<OfpHelloGenericElement> it = getElementList().iterator();
        while (it.hasNext()) {
            OfpHelloGenericElement elem = it.next();
            sb.append(elem.toString());
        }
        return sb.toString();
    }

    public boolean equals(OfpHelloMsg hdr) {
        if ((super.equals(hdr) == true) && (getElementList().equals(hdr))) {
            return true;
        }
        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o instanceof OfpHelloMsg) {
            return equals((OfpHelloMsg) o);
        }
        return false;
    }
}
