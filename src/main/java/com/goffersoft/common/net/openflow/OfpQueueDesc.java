/**
 ** File: OfpQueueDesc.java
 **
 ** Description : Open Packet Queue class
 **               -- OpenFlow Switch Specification Version 1.1.0 - February 28th, 2011
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

import com.goffersoft.common.utils.EndianConversion;
import com.goffersoft.common.utils.ReadUtils;

public class OfpQueueDesc implements OfpSerializable<OfpQueueDesc> {
    private OfpPortNum portNum;
    private int queueId;
    private short len;
    // 6 bytes of padding
    private LinkedList<OfpQueueDescGenericProp> props;

    public static final int OFP_QUEUE_DESC_HDR_LEN = 16;
    public static final int OFP_QUEUE_DESC_PORTNUM_OFFSET = 0;
    public static final int OFP_QUEUE_DESC_QUEUEID_OFFSET = 4;
    public static final int OFP_QUEUE_DESC_LENGTH_OFFSET = 8;
    public static final int OFP_QUEUE_DESC_PAD_OFFSET = 10;
    public static final int OFP_QUEUE_DESC_PAD_LEN = 6;

    private static final Logger log = Logger.getLogger(OfpQueueDesc.class);

    OfpQueueDesc(OfpQueueDesc hdr) {
        setPortNum(hdr.getPortNum());
        queueId = hdr.queueId;
        len = hdr.len;
        props = hdr.getPropertyList();
    }

    OfpQueueDesc(OfpPortNum portnum, int qId,
            LinkedList<OfpQueueDescGenericProp> qprops) {
        portNum = portnum;
        queueId = qId;
        setPropertyList(qprops);
    }

    OfpQueueDesc(byte[] hdr) {
        this(hdr, 0);
    }

    OfpQueueDesc(byte[] data, int offset) {
        portNum = readPortNum(data, offset);
        queueId = readQueueId(data, offset);
        len = readLength(data, offset);

        int start = offset + OFP_QUEUE_DESC_HDR_LEN;
        int end = start + getLength() - OFP_QUEUE_DESC_HDR_LEN;

        OfpQueueDescGenericProp.OfpQueueDescPropType proptype;
        int proplen;
        OfpQueueDescGenericProp tmp;
        while (start < end) {
            tmp = null;
            proptype = OfpQueueDescGenericProp.readType(data, start);
            proplen = OfpQueueDescGenericProp.readLength(data, start);

            if (proptype == OfpQueueDescGenericProp.OfpQueueDescPropType.OFPQDPT_MIN_RATE) {
                if (proplen == OfpQueueDescGenericProp.OFP_QUEUE_DESC_PROP_BASE_LEN
                        + OfpQueueDescMinRateProp.OFP_MIN_RATE_HDR_LEN) {
                    tmp = new OfpQueueDescMinRateProp(data, start);
                    addProperty(tmp);
                }
            } else if (proptype == OfpQueueDescGenericProp.OfpQueueDescPropType.OFPQDPT_MAX_RATE) {
                if (proplen == OfpQueueDescGenericProp.OFP_QUEUE_DESC_PROP_BASE_LEN
                        + OfpQueueDescMaxRateProp.OFP_MAX_RATE_HDR_LEN) {
                    tmp = new OfpQueueDescMaxRateProp(data, start);
                    addProperty(tmp);
                }
            } else if (proptype == OfpQueueDescGenericProp.OfpQueueDescPropType.OFPQDPT_EXPERIMENTER) {
                tmp = new OfpQueueDescExperimenterProp(data, start);
                addProperty(tmp);
            }
            if (tmp == null) {
                tmp = new OfpQueueDescUnknownProp(data, start);
                addProperty(tmp);
            }
            start += tmp.getAlignedLength();
        }
    }

    OfpQueueDesc(InputStream is) throws IOException {
        portNum = readPortNum(is);
        queueId = readQueueId(is);
        len = readLength(is);

        int start = OFP_QUEUE_DESC_HDR_LEN;
        int end = start + getLength() - OFP_QUEUE_DESC_HDR_LEN;

        OfpQueueDescGenericProp tmp;
        boolean unk;
        while (start < end) {
            unk = false;
            tmp = new OfpQueueDescGenericProp(is);

            if (tmp.getType() == OfpQueueDescGenericProp.OfpQueueDescPropType.OFPQDPT_MIN_RATE) {
                if (tmp.getLength() == OfpQueueDescGenericProp.OFP_QUEUE_DESC_PROP_BASE_LEN
                        + OfpQueueDescMinRateProp.OFP_MIN_RATE_HDR_LEN) {
                    tmp = new OfpQueueDescMinRateProp(tmp, is);
                    addProperty(tmp);
                } else {
                    unk = true;
                }
            } else if (tmp.getType() == OfpQueueDescGenericProp.OfpQueueDescPropType.OFPQDPT_MAX_RATE) {
                if (tmp.getLength() == OfpQueueDescGenericProp.OFP_QUEUE_DESC_PROP_BASE_LEN
                        + OfpQueueDescMaxRateProp.OFP_MAX_RATE_HDR_LEN) {
                    tmp = new OfpQueueDescMaxRateProp(tmp, is);
                    addProperty(tmp);
                } else {
                    unk = true;
                }
            } else if (tmp.getType() == OfpQueueDescGenericProp.OfpQueueDescPropType.OFPQDPT_EXPERIMENTER) {
                tmp = new OfpQueueDescExperimenterProp(tmp, is);
                addProperty(tmp);
            }
            if (unk == true) {
                tmp = new OfpQueueDescUnknownProp(tmp, is);
                addProperty(tmp);
            }
            start += tmp.getAlignedLength();
        }
    }

    private void addProps(LinkedList<OfpQueueDescGenericProp> props) {
        Iterator<OfpQueueDescGenericProp> it = props.iterator();
        while (it.hasNext()) {
            OfpQueueDescGenericProp elem = it.next();
            addProperty(elem);
        }
    }

    public LinkedList<OfpQueueDescGenericProp> getPropertyList() {
        if (props == null) {
            props = new LinkedList<OfpQueueDescGenericProp>();
        }
        return props;
    }

    public void setPropertyList(LinkedList<OfpQueueDescGenericProp> props) {
        setLength((short) OFP_QUEUE_DESC_HDR_LEN);
        if (props == null) {
            props = new LinkedList<OfpQueueDescGenericProp>();
            return;
        }
        addProps(props);
    }

    private void addProperty(OfpQueueDescGenericProp prop, boolean adjustLength) {
        if (prop == null) {
            return;
        }
        getPropertyList().add(prop);

        if (adjustLength == true) {
            setLength((short) (getLength() + prop.getAlignedLength()));
        }
    }

    public void addProperty(OfpQueueDescGenericProp prop) {
        addProperty(prop, true);
    }

    private void removeProperty(OfpQueueDescGenericProp prop,
            boolean adjustLength) {
        if (prop == null) {
            return;
        }
        getPropertyList().remove(prop);

        if (adjustLength == true) {
            setLength((short) (getLength() - prop.getAlignedLength()));
        }
    }

    public void removeProperty(OfpQueueDescGenericProp prop) {
        removeProperty(prop, true);
    }

    public int getQueueId() {
        return queueId;
    }

    public void setQueueId(int queueId) {
        this.queueId = queueId;
    }

    public short getLength() {
        return len;
    }

    public void setLength(short len) {
        this.len = len;
    }

    public OfpPortNum getPortNum() {
        return portNum;
    }

    public void setPortNum(OfpPortNum portNum) {
        this.portNum = portNum;
    }

    public byte[] toByteArray() {
        byte[] hdr = new byte[getLength()];
        return toByteArray(hdr, 0);
    }

    public byte[] toByteArray(byte[] data) {
        return toByteArray(data, 0);
    }

    public byte[] toByteArray(byte[] data, int offset) {
        writePortNum(data, offset);
        writeQueueId(data, offset);
        writeLength(data, offset);
        return writePropertyList(data, offset + OFP_QUEUE_DESC_HDR_LEN);
    }

    public OutputStream toOutputStream(OutputStream os) throws IOException {
        writePortNum(os);
        writeQueueId(os);
        writeLength(os);
        return writePropertyList(os);
    }

    public OfpQueueDesc fromInputStream(InputStream is) throws IOException {
        return readFromInputStream(is);
    }

    public OfpQueueDesc fromByteArray(byte[] data) {
        return new OfpQueueDesc(data);
    }

    public OfpQueueDesc fromByteArray(byte[] data, int offset) {
        return new OfpQueueDesc(data, offset);
    }

    public byte[] writePortNum(byte[] data) {
        return writePortNum(data, 0);
    }

    public byte[] writePortNum(byte[] data, int offset) {
        EndianConversion.intToByteArrayBE(data, offset
                + OFP_QUEUE_DESC_PORTNUM_OFFSET, getPortNum().getValue());
        return data;
    }

    public OutputStream writePortNum(OutputStream os) throws IOException {
        EndianConversion.intToOutputStreamBE(os, getPortNum().getValue());
        return os;
    }

    public byte[] writeQueueId(byte[] data) {
        return writeQueueId(data, 0);
    }

    public byte[] writeQueueId(byte[] data, int offset) {
        EndianConversion.intToByteArrayBE(data, offset
                + OFP_QUEUE_DESC_QUEUEID_OFFSET, getQueueId());
        return data;
    }

    public OutputStream writeQueueId(OutputStream os) throws IOException {
        EndianConversion.intToOutputStreamBE(os, getQueueId());
        return os;
    }

    public byte[] writeLength(byte[] data) {
        return writeLength(data, 0);
    }

    public byte[] writeLength(byte[] data, int offset) {
        EndianConversion.longToByteArrayBE(data, offset
                + OFP_QUEUE_DESC_LENGTH_OFFSET, getLength(),
                2 + OFP_QUEUE_DESC_PAD_LEN);
        return data;
    }

    public OutputStream writeLength(OutputStream os) throws IOException {
        EndianConversion.longToOutputStreamBE(os, getLength(),
                2 + OFP_QUEUE_DESC_PAD_LEN);
        return os;
    }

    public byte[] writePropertyList(byte[] data) {
        return writePropertyList(data, 0);
    }

    public byte[] writePropertyList(byte[] data, int offset) {
        int start = offset;
        Iterator<OfpQueueDescGenericProp> it = getPropertyList().iterator();
        while (it.hasNext()) {
            OfpQueueDescGenericProp prop = it.next();
            prop.toByteArray(data, start);
            start += prop.getAlignedLength();
        }
        return data;
    }

    public OutputStream writePropertyList(OutputStream os) throws IOException {
        Iterator<OfpQueueDescGenericProp> it = getPropertyList().iterator();
        while (it.hasNext()) {
            OfpQueueDescGenericProp prop = it.next();
            prop.toOutputStream(os);
        }
        return os;
    }

    public static byte[] readOfpQueueDesc(InputStream is) throws IOException {
        return readOfpQueueDesc(is, null, 0);
    }

    public static byte[] readOfpQueueDesc(InputStream is, byte[] data)
            throws IOException {
        return readOfpQueueDesc(is, data, 0);
    }

    public static byte[] readOfpQueueDesc(InputStream is, byte[] data,
            int offset) throws IOException {
        byte[] tmpData = ReadUtils.readInput(is, data, offset,
                OFP_QUEUE_DESC_HDR_LEN);
        int tmpOffset = ((tmpData != data) ? 0 : offset);
        short len = readLength(tmpData, tmpOffset);

        if (len > OFP_QUEUE_DESC_HDR_LEN) {
            byte[] tmp1Data = ReadUtils.readInput(is, tmpData, tmpOffset
                    + OFP_QUEUE_DESC_HDR_LEN, len - OFP_QUEUE_DESC_HDR_LEN);
            if (tmp1Data != tmpData) {
                System.arraycopy(tmpData, tmpOffset, tmp1Data, 0,
                        OFP_QUEUE_DESC_HDR_LEN);
                return tmp1Data;
            }
        }
        return tmpData;
    }

    public static OfpQueueDesc readFromInputStream(InputStream is)
            throws IOException {
        return new OfpQueueDesc(is);
    }

    public static OfpPortNum readPortNum(byte[] qdeschdr) {
        return readPortNum(qdeschdr, 0);
    }

    public static OfpPortNum readPortNum(byte[] qdeschdr, int offset) {
        return OfpPortNum.getEnum(qdeschdr, offset
                + OFP_QUEUE_DESC_PORTNUM_OFFSET);
    }

    public static OfpPortNum readPortNum(InputStream is) throws IOException {
        return OfpPortNum.getEnum(is);
    }

    public static int readQueueId(byte[] qdeschdr) {
        return readQueueId(qdeschdr, 0);
    }

    public static int readQueueId(byte[] qdeschdr, int offset) {
        return EndianConversion.byteArrayToIntBE(qdeschdr, offset
                + OFP_QUEUE_DESC_QUEUEID_OFFSET);
    }

    public static int readQueueId(InputStream is) throws IOException {
        return EndianConversion.inputStreamToIntBE(is);
    }

    public static short readLength(byte[] qdeschdr) {
        return readLength(qdeschdr, 0);
    }

    public static short readLength(byte[] qdeschdr, int offset) {
        return EndianConversion.byteArrayToShortBE(qdeschdr, offset
                + OFP_QUEUE_DESC_LENGTH_OFFSET);
    }

    public static short readLength(InputStream is) throws IOException {
        return (short) EndianConversion.inputStreamToLongBE(is,
                2 + OFP_QUEUE_DESC_PAD_LEN);
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();

        sb.append(String.format(
                "PortNum=%s : QueueId=%d(0x%04x) : Length=%d(0x%04x)\n",
                getPortNum().toString(), getQueueId(), getQueueId(),
                getLength(), getLength()));

        Iterator<OfpQueueDescGenericProp> it = getPropertyList().iterator();
        while (it.hasNext()) {
            OfpQueueDescGenericProp prop = it.next();
            sb.append(prop.toString());
        }
        return sb.toString();
    }

    public boolean equals(OfpQueueDesc hdr) {
        if ((getPortNum() == hdr.getPortNum())
                && (getQueueId() == hdr.getQueueId())
                && (getLength() == hdr.getLength())
                && (getPropertyList().equals(hdr))) {
            return true;
        }
        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o instanceof OfpQueueDesc) {
            return equals((OfpQueueDesc) o);
        }
        return false;
    }
}
