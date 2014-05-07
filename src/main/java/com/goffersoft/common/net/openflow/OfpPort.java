/**
 ** File: OfpPort.java
 **
 ** Description : OpenFlow Port class
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

import com.goffersoft.common.utils.BitUtils;
import com.goffersoft.common.utils.EndianConversion;
import com.goffersoft.common.utils.ReadUtils;
import com.goffersoft.common.utils.StringUtils;
import com.goffersoft.common.utils.net.MacAddress;

public class OfpPort implements OfpSerializable<OfpPort> {

    private static final Logger log = Logger.getLogger(OfpPort.class);

    static public enum OfpPortState {
        OFPPS_LINK_DOWN(0), OFPPS_BLOCKED(1), OFPPS_LIVE(2), OFPPS_UNK(0), ;

        static final OfpPortState psBit[] = { OFPPS_LINK_DOWN, OFPPS_BLOCKED,
                OFPPS_LIVE, OFPPS_UNK, };

        static final String psDescr[] = { "No physical link present",
                "Port is blocked", "Live for Fast Failover Group",
                "Unknown State - User Defined", };

        private int psBitPos;

        private OfpPortState(int psBitPos) {
            setPortStateInternal(psBitPos);
        }

        public int getValue() {
            return (1 << psBitPos);
        }

        public int getBitPos() {
            return psBitPos;
        }

        public void setValue(int psBitMask) {
            if (this == OFPPS_UNK) {
                int psBitPos = BitUtils.getBitPos(psBitMask);
                if (psBitPos != -1) {
                    this.psBitPos = psBitPos;
                }
            }
        }

        public void setBitPos(int psBitPos) {
            if (this == OFPPS_UNK) {
                if (psBitPos > 0
                        && psBitPos < BitUtils.BITUTILS_NUM_BITS_IN_INT) {
                    this.psBitPos = psBitPos;
                }
            }
        }

        private void setPortStateInternal(int psBitPos) {
            this.psBitPos = psBitPos;
        }

        public static OfpPortState getEnum(int psBitPos) {
            if (psBitPos < (psBit.length - 1)) {
                return psBit[psBitPos];
            }

            OfpPortState unk = OFPPS_UNK;
            unk.setValue(psBitPos);
            return unk;
        }

        public String getDescr() {
            if (this == OFPPS_UNK) {
                return String.format("%s : bit=%d(%08x)", psDescr[ordinal()],
                        (1 << psBitPos), (1 << psBitPos));
            } else {
                return psDescr[ordinal()];
            }
        }

        @Override
        public String toString() {
            return getDescr();
        }
    };

    static public enum OfpPortConfig {
        OFPPC_PORT_DOWN(0), OFPPC_NO_RECV(1), OFPPC_NO_FWD(2), OFPPC_NO_PACKET_IN(
                3), OFPPC_UNK(0), ;

        static final OfpPortConfig pcBit[] = { OFPPC_PORT_DOWN, OFPPC_NO_RECV,
                OFPPC_NO_FWD, OFPPC_NO_PACKET_IN, OFPPC_UNK, };

        static final String pcDescr[] = { "Port is administratively down",
                "Drop all packets received by the port",
                "Drop packets forwarded to port",
                "Do not send packet-in msgs for port",
                "Unknown State - User Defined", };

        private int pcBitPos;

        private OfpPortConfig(int pcBitPos) {
            setPortStateInternal(pcBitPos);
        }

        public int getValue() {
            return (1 << pcBitPos);
        }

        public int getBitPos() {
            return pcBitPos;
        }

        public void setValue(int pcBitMask) {
            if (this == OFPPC_UNK) {
                int pcBitPos = BitUtils.getBitPos(pcBitMask);
                if (pcBitPos != -1) {
                    this.pcBitPos = pcBitPos;
                }
            }
        }

        public void setBitPos(int pcBitPos) {
            if (this == OFPPC_UNK) {
                if (pcBitPos > 0
                        && pcBitPos < BitUtils.BITUTILS_NUM_BITS_IN_INT) {
                    this.pcBitPos = pcBitPos;
                }
            }
        }

        private void setPortStateInternal(int pcBitPos) {
            this.pcBitPos = pcBitPos;
        }

        public static OfpPortConfig getEnum(int pcBitPos) {
            if (pcBitPos < (pcBit.length - 1)) {
                return pcBit[pcBitPos];
            }

            OfpPortConfig unk = OFPPC_UNK;
            unk.setValue(pcBitPos);
            return unk;
        }

        public String getDescr() {
            if (this == OFPPC_UNK) {
                return String.format("%s : bit=%d(%08x)", pcDescr[ordinal()],
                        (1 << pcBitPos), (1 << pcBitPos));
            } else {
                return pcDescr[ordinal()];
            }
        }

        @Override
        public String toString() {
            return getDescr();
        }
    };

    public static final int OFP_PORT_PORTNUM_OFFSET = 0;
    public static final int OFP_PORT_LENGTH_OFFSET = 4;
    public static final int OFP_PORT_PADDING1_OFFSET = 6;
    public static final int OFP_PORT_PADDING1_LEN = 2;
    public static final int OFP_PORT_HWADDR_OFFSET = 8;
    public static final int OFP_PORT_PADDING2_OFFSET = 14;
    public static final int OFP_PORT_PADDING2_LEN = 2;
    public static final int OFP_PORT_PORTNAME_OFFSET = 16;
    public static final int OFP_PORT_CONFIG_BITMAP_OFFSET = 32;
    public static final int OFP_PORT_STATE_BITMAP_OFFSET = 36;
    public static final int OFP_PORT_PROPERTY_LIST_OFFSET = 40;

    private OfpPortNum portNum;
    private short length;
    // 2 bytes of padding
    private MacAddress hwAddr;
    // 2 bytes of padding
    private String name;
    private BitUtils.IntBitmap configBitmap;
    private BitUtils.IntBitmap stateBitmap;

    private LinkedList<OfpPortDescGenericProp> props;

    public static final int OFP_MAX_HWADDR_LEN = 6;
    public static final int OFP_MAX_PORTNAME_LEN = 16;
    public static final int OFP_PORT_LEN = 40;
    public static final String OFP_PORT_DEFAULT_NAME = "Port 0x%04x";
    public static final String OFP_PORT_DEFAULT_HWADDR = "00:00:00:00:00:00";

    public OfpPort(OfpPort hdr) {
        setPortNum(hdr.getPortNum());
        setLength(hdr.getLength());
        setHwAddr(hdr.getHwAddr());
        setName(hdr.getName());
        setConfigBitmap(hdr.getConfigBitmap());
        setStateBitmap(hdr.getStateBitmap());
        setPropertyList(hdr.getPropertyList());
    }

    public OfpPort(OfpPortNum portNum, MacAddress hwAddr, String portName,
            BitUtils.IntBitmap portState, BitUtils.IntBitmap portConfig,
            LinkedList<OfpPortDescGenericProp> props) {
        setPortNum(portNum);
        setHwAddr(hwAddr);
        setName(portName);
        setStateBitmap(portState);
        setConfigBitmap(portState);
        setPropertyList(props);
    }

    public OfpPort(OfpPortNum portNum, MacAddress hwAddr, String portName,
            int portState, int portConfig,
            LinkedList<OfpPortDescGenericProp> props) {
        setPortNum(portNum);
        setHwAddr(hwAddr);
        setName(portName);
        setStateBitmap(portState);
        setConfigBitmap(portState);
        setPropertyList(props);
    }

    public OfpPort(byte[] data) {
        this(data, 0);
    }

    public OfpPort(byte[] data, int offset) {
        setPortNum(readPortNum(data, offset));
        setLength(readLength(data, offset));
        setHwAddr(readHwAddr(data, offset));
        setName(readName(data, offset));
        setConfigBitmap(readConfigBitmap(data, offset));
        setStateBitmap(readStateBitmap(data, offset));
        setPropertyListInternal(readPropertyList(data, offset));
    }

    public OfpPort(InputStream is) throws IOException {
        setPortNum(readPortNum(is));
        setLength(readLength(is));
        setHwAddr(readHwAddr(is));
        setName(readName(is));
        setConfigBitmap(readConfigBitmap(is));
        setStateBitmap(readStateBitmap(is));
        setPropertyListInternal(readPropertyList(is, getLength()));
    }

    public OfpPort(OfpPortNum pnum, MacAddress maddr,
            LinkedList<OfpPortDescGenericProp> props) {

    }

    private void addProps(LinkedList<OfpPortDescGenericProp> props) {
        Iterator<OfpPortDescGenericProp> it = getPropertyList().iterator();
        while (it.hasNext()) {
            OfpPortDescGenericProp elem = it.next();
            addProperty(elem);
        }
    }

    public LinkedList<OfpPortDescGenericProp> getPropertyList() {
        if (props == null) {
            props = new LinkedList<OfpPortDescGenericProp>();
        }
        return props;
    }

    private void setPropertyListInternal(
            LinkedList<OfpPortDescGenericProp> props) {
        this.props = props;
    }

    public void setPropertyList(LinkedList<OfpPortDescGenericProp> props) {
        setLength((short) OFP_PORT_LEN);
        if (props == null) {
            props = new LinkedList<OfpPortDescGenericProp>();
            return;
        }
        addProps(props);
    }

    private void addPropertyInternal(OfpPortDescGenericProp prop,
            boolean adjustLength) {
        if (prop == null) {
            return;
        }
        getPropertyList().add(prop);

        if (adjustLength == true) {
            setLength((short) (getLength() + prop.getAlignedLength()));
        }
    }

    public void addProperty(OfpPortDescGenericProp prop) {
        addPropertyInternal(prop, true);
    }

    private void removeProperty(OfpPortDescGenericProp prop,
            boolean adjustLength) {
        if (prop == null) {
            return;
        }
        getPropertyList().remove(prop);

        if (adjustLength == true) {
            setLength((short) (getLength() - prop.getAlignedLength()));
        }
    }

    public void removeProperty(OfpPortDescGenericProp prop) {
        removeProperty(prop, true);
    }

    public OfpPortNum getPortNum() {
        return portNum;
    }

    public void setPortNum(OfpPortNum portnum) {
        this.portNum = portnum;
    }

    public short getLength() {
        return length;
    }

    public void setLength(short length) {
        this.length = length;
    }

    public MacAddress getHwAddr() {
        return hwAddr;
    }

    public void setHwAddr(MacAddress hwAddr) {
        if (hwAddr == null) {
            if (this.hwAddr == null) {
                this.hwAddr = new MacAddress(OFP_PORT_DEFAULT_HWADDR);
            } else {
                this.hwAddr.setMacAddress(OFP_PORT_DEFAULT_HWADDR);
            }
        } else {
            this.hwAddr = hwAddr;
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null) {
            name = String
                    .format(OFP_PORT_DEFAULT_NAME, getPortNum().getValue());
        } else {
            this.name = name;
        }
    }

    public BitUtils.IntBitmap getConfigBitmap() {
        return configBitmap;
    }

    public void setConfigBitmap(int bmap) {
        if (configBitmap == null) {
            configBitmap = new BitUtils.IntBitmap(bmap);
        } else {
            configBitmap.setBitmap(bmap);
        }
    }

    public void setConfigBitmap(BitUtils.IntBitmap bmap) {
        if (bmap == null) {
            if (configBitmap == null) {
                configBitmap = new BitUtils.IntBitmap(0x0);
            }
            return;
        }
        configBitmap = bmap;
    }

    public OfpPortConfig[] getConfigBitSet() {
        int numbits = getConfigBitmap().getNumBitsSet();

        if (numbits == 0) {
            return null;
        }

        OfpPortConfig[] tmp = new OfpPortConfig[numbits];

        getConfigBitmap().iterate(new BitUtils.BitmapIterator() {
            private int i;

            public void executeIfBitIsSet(int bitpos, Object var) {
                OfpPortConfig[] tmp = (OfpPortConfig[]) var;
                tmp[i++] = OfpPortConfig.getEnum(bitpos);
            }

            public void executeIfBitIsNotSet(int bitpos, Object var) {

            }
        }, tmp);

        return tmp;
    }

    public boolean configBitmapIsBitSet(OfpPortConfig pfbit) {
        return getConfigBitmap().isBitSet(pfbit.getBitPos());
    }

    public void configBitmapSetBit(OfpPortConfig pfbit) {
        getConfigBitmap().setBit(pfbit.getBitPos());
    }

    public void configBitmapClearBit(OfpPortConfig pfbit) {
        getConfigBitmap().clearBit(pfbit.getBitPos());
    }

    public BitUtils.IntBitmap getStateBitmap() {
        return stateBitmap;
    }

    public void setStateBitmap(int bmap) {
        if (stateBitmap == null) {
            stateBitmap = new BitUtils.IntBitmap(bmap);
        } else {
            stateBitmap.setBitmap(bmap);
        }
    }

    public void setStateBitmap(BitUtils.IntBitmap bmap) {
        if (bmap == null) {
            if (stateBitmap == null) {
                stateBitmap = new BitUtils.IntBitmap(0x0);
            }
            return;
        }
        stateBitmap = bmap;
    }

    public OfpPortState[] getStateBitSet() {
        int numbits = getStateBitmap().getNumBitsSet();

        if (numbits == 0) {
            return null;
        }

        OfpPortState[] tmp = new OfpPortState[numbits];

        getStateBitmap().iterate(new BitUtils.BitmapIterator() {
            private int i;

            public void executeIfBitIsSet(int bitpos, Object var) {
                OfpPortState[] tmp = (OfpPortState[]) var;
                tmp[i++] = OfpPortState.getEnum(bitpos);
            }

            public void executeIfBitIsNotSet(int bitpos, Object var) {

            }
        }, tmp);

        return tmp;
    }

    public boolean stateBitmapIsBitSet(OfpPortState pfbit) {
        return getStateBitmap().isBitSet(pfbit.getBitPos());
    }

    public void stateBitmapSetBit(OfpPortConfig pfbit) {
        getStateBitmap().setBit(pfbit.getBitPos());
    }

    public void stateBitmapClearBit(OfpPortConfig pfbit) {
        getStateBitmap().clearBit(pfbit.getBitPos());
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
        writePortNum(data, offset);
        writeLength(data, offset);
        writeHwAddr(data, offset);
        writeName(data, offset);
        writeConfigBitmap(data, offset);
        writeStateBitmap(data, offset);
        return writePropertyList(data, offset + OFP_PORT_LEN);
    }

    @Override
    public OutputStream toOutputStream(OutputStream os) throws IOException {
        writePortNum(os);
        writeLength(os);
        writeHwAddr(os);
        writeName(os);
        writeConfigBitmap(os);
        writeStateBitmap(os);
        return writePropertyList(os);
    }

    @Override
    public OfpPort fromInputStream(InputStream is) throws IOException {
        return readFromInputStream(is);
    }

    @Override
    public OfpPort fromByteArray(byte[] data) {
        return new OfpPort(data);
    }

    @Override
    public OfpPort fromByteArray(byte[] data, int offset) {
        return new OfpPort(data, offset);
    }

    public byte[] writePortNum(byte[] data) {
        return writePortNum(data, 0);
    }

    public byte[] writePortNum(byte[] data, int offset) {
        EndianConversion.intToByteArrayBE(data, offset
                + OFP_PORT_PORTNUM_OFFSET, getPortNum().getValue());
        return data;
    }

    public OutputStream writePortNum(OutputStream os) throws IOException {
        EndianConversion.intToOutputStreamBE(os, getPortNum().getValue());
        return os;
    }

    public byte[] writeLength(byte[] data) {
        return writeLength(data, 0);
    }

    public byte[] writeLength(byte[] data, int offset) {
        EndianConversion.intToByteArrayBE(data, offset, (getLength() & 0xFFFF));
        return data;
    }

    public OutputStream writeLength(OutputStream os) throws IOException {
        EndianConversion.intToOutputStreamBE(os, (getLength() & 0xFFFF));
        return os;
    }

    public byte[] writeHwAddr(byte[] data) {
        return writeHwAddr(data, 0);
    }

    public byte[] writeHwAddr(byte[] data, int offset) {
        getHwAddr().toByteArray(data, offset + OFP_PORT_HWADDR_OFFSET);
        data[offset + OFP_PORT_HWADDR_OFFSET + 0] = 0x0; // padding
        data[offset + OFP_PORT_HWADDR_OFFSET + 1] = 0x0; // padding
        return data;
    }

    public OutputStream writeHwAddr(OutputStream os) throws IOException {
        getHwAddr().toOutputStream(os);
        os.write(0x0); // padding
        os.write(0x0); // padding
        return os;
    }

    public byte[] writeName(byte[] data) {
        return writeName(data, 0);
    }

    public byte[] writeName(byte[] data, int offset) {
        byte[] name = getName().getBytes();
        int len = (name.length > OFP_MAX_PORTNAME_LEN) ? OFP_MAX_PORTNAME_LEN
                : name.length;
        System.arraycopy(name, 0, data, offset + OFP_PORT_PORTNAME_OFFSET, len);

        for (int i = len; i < OFP_MAX_PORTNAME_LEN; i++) {
            data[offset + OFP_PORT_PORTNAME_OFFSET + i] = 0x0; // padding
        }

        return data;
    }

    public OutputStream writeName(OutputStream os) throws IOException {
        byte[] name = getName().getBytes();
        int len = (name.length > OFP_MAX_PORTNAME_LEN) ? OFP_MAX_PORTNAME_LEN
                : name.length;
        os.write(name, 0, len);

        for (int i = len; i < OFP_MAX_PORTNAME_LEN; i++) {
            os.write(0x0); // padding
        }

        return os;
    }

    public byte[] writeConfigBitmap(byte[] data) {
        return writeConfigBitmap(data, 0);
    }

    public byte[] writeConfigBitmap(byte[] data, int offset) {
        EndianConversion.intToByteArrayBE(data, offset
                + OFP_PORT_CONFIG_BITMAP_OFFSET, getConfigBitmap().getBitmap());

        return data;
    }

    public OutputStream writeConfigBitmap(OutputStream os) throws IOException {
        EndianConversion.intToOutputStreamBE(os, getConfigBitmap().getBitmap());
        return os;
    }

    public byte[] writeStateBitmap(byte[] data) {
        return writeStateBitmap(data, 0);
    }

    public byte[] writeStateBitmap(byte[] data, int offset) {
        EndianConversion.intToByteArrayBE(data, offset
                + OFP_PORT_STATE_BITMAP_OFFSET, getStateBitmap().getBitmap());

        return data;
    }

    public OutputStream writeStateBitmap(OutputStream os) throws IOException {
        EndianConversion.intToOutputStreamBE(os, getStateBitmap().getBitmap());
        return os;
    }

    public byte[] writePropertyList(byte[] data) {
        return writePropertyList(data, 0);
    }

    public byte[] writePropertyList(byte[] data, int offset) {
        int start = offset;
        Iterator<OfpPortDescGenericProp> it = getPropertyList().iterator();
        while (it.hasNext()) {
            OfpPortDescGenericProp prop = it.next();
            prop.toByteArray(data, start);
            start += prop.getAlignedLength();
        }
        return data;
    }

    public OutputStream writePropertyList(OutputStream os) throws IOException {
        Iterator<OfpPortDescGenericProp> it = getPropertyList().iterator();
        while (it.hasNext()) {
            OfpPortDescGenericProp prop = it.next();
            prop.toOutputStream(os);
        }
        return os;
    }

    public static byte[] readOfpPort(InputStream is) throws IOException {
        return readOfpPort(is, null, 0);
    }

    public static byte[] readOfpPort(InputStream is, byte[] data)
            throws IOException {
        return readOfpPort(is, data, 0);
    }

    public static byte[] readOfpPort(InputStream is, byte[] data, int offset)
            throws IOException {
        short len = readLength(data, offset + OFP_PORT_LENGTH_OFFSET);

        if (len > OFP_PORT_LEN) {
            data = ReadUtils.readInput(is, data, offset + OFP_PORT_LEN, len
                    - OFP_PORT_LEN);
        }
        return data;
    }

    public static OfpPort readFromInputStream(InputStream is)
            throws IOException {
        return new OfpPort(is);
    }

    public static OfpPortNum readPortNum(byte[] data) {
        return readPortNum(data, 0);
    }

    public static OfpPortNum readPortNum(byte[] data, int offset) {
        return OfpPortNum.getEnum(data, offset + OFP_PORT_PORTNUM_OFFSET);
    }

    public static OfpPortNum readPortNum(InputStream is) throws IOException {
        return OfpPortNum.getEnum(is);
    }

    public static short readLength(byte[] data) {
        return readLength(data, 0);
    }

    public static short readLength(byte[] data, int offset) {
        return EndianConversion.byteArrayToShortBE(data, offset
                + OFP_PORT_LENGTH_OFFSET);
    }

    public static short readLength(InputStream is) throws IOException {
        return (short) EndianConversion.inputStreamToIntBE(is);
    }

    public static MacAddress readHwAddr(byte[] data) {
        return readHwAddr(data, 0);
    }

    public static MacAddress readHwAddr(byte[] data, int offset) {
        return new MacAddress(data, offset);
    }

    public static MacAddress readHwAddr(InputStream is) throws IOException {
        MacAddress mac = MacAddress.getMacAddress(is);
        is.read(); // padding
        is.read(); // padding
        return mac;
    }

    public static String readName(byte[] data) {
        return readName(data, 0);
    }

    public static String readName(byte[] data, int offset) {
        return StringUtils.byteArrayToString(data, offset);
    }

    public static String readName(InputStream is) throws IOException {
        return ReadUtils.readInputAsString(is, OFP_MAX_PORTNAME_LEN);
    }

    public static BitUtils.IntBitmap readConfigBitmap(byte[] data) {
        return readConfigBitmap(data, 0);
    }

    public static BitUtils.IntBitmap readConfigBitmap(byte[] data, int offset) {
        return new BitUtils.IntBitmap(EndianConversion.byteArrayToIntBE(data,
                offset + OFP_PORT_CONFIG_BITMAP_OFFSET));
    }

    public static BitUtils.IntBitmap readConfigBitmap(InputStream is)
            throws IOException {
        return new BitUtils.IntBitmap(EndianConversion.inputStreamToIntBE(is));
    }

    public static BitUtils.IntBitmap readStateBitmap(byte[] data) {
        return readStateBitmap(data, 0);
    }

    public static BitUtils.IntBitmap readStateBitmap(byte[] data, int offset) {
        return new BitUtils.IntBitmap(EndianConversion.byteArrayToIntBE(data,
                offset + OFP_PORT_STATE_BITMAP_OFFSET));
    }

    public static BitUtils.IntBitmap readStateBitmap(InputStream is)
            throws IOException {
        return new BitUtils.IntBitmap(EndianConversion.inputStreamToIntBE(is));
    }

    public static LinkedList<OfpPortDescGenericProp> readPropertyList(
            byte[] data) {
        return readPropertyList(data, 0);
    }

    public static LinkedList<OfpPortDescGenericProp> readPropertyList(
            byte[] data, int offset) {
        int pktlen = readLength(data, offset);
        return readPropertyList(data, offset, pktlen);
    }

    public static LinkedList<OfpPortDescGenericProp> readPropertyList(
            byte[] data, int offset, int pktlen) {
        int start = offset + OFP_PORT_PROPERTY_LIST_OFFSET;
        int end = start + pktlen - OFP_PORT_LEN;
        OfpPortDescGenericProp tmp;
        LinkedList<OfpPortDescGenericProp> propList = new LinkedList<OfpPortDescGenericProp>();

        while (start < end) {
            OfpPortDescGenericProp.OfpPortDescPropType type = OfpPortDescGenericProp
                    .readType(data, start);

            if (type == OfpPortDescGenericProp.OfpPortDescPropType.OFPPDPT_ETHERNET) {
                tmp = new OfpPortDescEthernetProp(data, start);
            } else if (type == OfpPortDescGenericProp.OfpPortDescPropType.OFPPDPT_OPTICAL) {
                tmp = new OfpPortDescOpticalProp(data, start);
            } else if (type == OfpPortDescGenericProp.OfpPortDescPropType.OFPPDPT_EXPERIMENTER) {
                tmp = new OfpPortDescExperimenterProp(data, start);
            } else {
                tmp = new OfpPortDescUnknownProp(data, start);
            }
            propList.add(tmp);
            start += tmp.getAlignedLength();
        }

        return propList;
    }

    public static LinkedList<OfpPortDescGenericProp> readPropertyList(
            InputStream is, int pktlen) throws IOException {
        int start = OFP_PORT_PROPERTY_LIST_OFFSET;
        int end = start + pktlen - OFP_PORT_LEN;
        OfpPortDescGenericProp tmp;
        LinkedList<OfpPortDescGenericProp> propList = new LinkedList<OfpPortDescGenericProp>();

        while (start < end) {
            tmp = new OfpPortDescGenericProp(is);

            if (tmp.getType() == OfpPortDescGenericProp.OfpPortDescPropType.OFPPDPT_ETHERNET) {
                tmp = new OfpPortDescEthernetProp(is);
            } else if (tmp.getType() == OfpPortDescGenericProp.OfpPortDescPropType.OFPPDPT_OPTICAL) {
                tmp = new OfpPortDescOpticalProp(is);
            } else if (tmp.getType() == OfpPortDescGenericProp.OfpPortDescPropType.OFPPDPT_EXPERIMENTER) {
                tmp = new OfpPortDescExperimenterProp(is);
            } else {
                tmp = new OfpPortDescUnknownProp(is);
            }
            propList.add(tmp);
            start += tmp.getAlignedLength();
        }

        return propList;
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();

        sb.append(String
                .format("PortNum=%s : Length=%d(0x%04x) : MacAddress=%s : PortName=%s \n",
                        getPortNum().toString().replace('\n', ' '),
                        getLength(), getLength(), getHwAddr().toString()
                                .replace('\n', ' '), getName()));

        sb.append(String.format("Port Config %s\n", getConfigBitmap()
                .toString()));

        getConfigBitmap().iterate(new BitUtils.BitmapIterator() {
            private int i;

            public void executeIfBitIsSet(int bitpos, Object var) {
                StringBuffer sb = (StringBuffer) var;
                sb.append(String.format("[%d] : %s\n", i++, OfpPortConfig
                        .getEnum(bitpos).toString()));
            }

            public void executeIfBitIsNotSet(int bitpos, Object var) {

            }
        }, sb);

        sb.append(String.format("Port State %s", getStateBitmap().toString()));

        getStateBitmap().iterate(new BitUtils.BitmapIterator() {
            private int i;

            public void executeIfBitIsSet(int bitpos, Object var) {
                StringBuffer sb = (StringBuffer) var;
                sb.append(String.format("[%d] : %s\n", i++, OfpPortState
                        .getEnum(bitpos).toString()));
            }

            public void executeIfBitIsNotSet(int bitpos, Object var) {

            }
        }, sb);

        Iterator<OfpPortDescGenericProp> it = getPropertyList().iterator();
        while (it.hasNext()) {
            OfpPortDescGenericProp elem = it.next();
            sb.append(elem.toString());
        }

        return sb.toString();
    }

    public boolean equals(OfpPort hdr) {
        if ((getPortNum() == hdr.getPortNum())
                && (getLength() == hdr.getLength())
                && (getHwAddr() == hdr.getHwAddr())
                && (getName().equals(hdr.getName()))
                && (getConfigBitmap() == hdr.getConfigBitmap())
                && (getStateBitmap() == hdr.getStateBitmap())
                && (getPropertyList().equals(hdr.getPropertyList()))) {
            return true;
        }
        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o instanceof OfpPort) {
            return equals((OfpPort) o);
        }
        return false;
    }
}
