/**
 ** File: OfpSwitchConfigSetMsg.java
 **
 ** Description : OpenFlow OfpSwitchConfigSetMsg class
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

public class OfpSwitchConfigSetMsg
        extends
        OfpHeader {
    private static final Logger log = Logger
            .getLogger(OfpSwitchConfigSetMsg.class);

    private OfpSwitchConfig swCfg;

    OfpSwitchConfigSetMsg(int xid) {
        super(OfpType.OFPT_SET_CONFIG, xid);
        setSwitchConfig(new OfpSwitchConfig());
        setLength((short) (getLength() + OfpSwitchConfig.OFP_SWITCH_CONFIG_LEN));
    }

    OfpSwitchConfigSetMsg(byte version, int xid) {
        super(version, OfpType.OFPT_SET_CONFIG, xid);
        setSwitchConfig(new OfpSwitchConfig());
        setLength((short) (getLength() + OfpSwitchConfig.OFP_SWITCH_CONFIG_LEN));
    }

    OfpSwitchConfigSetMsg(int xid, OfpSwitchConfig swcfg) {
        super(OfpType.OFPT_SET_CONFIG, xid);
        setSwitchConfig(swcfg);
        setLength((short) (getLength() + OfpSwitchConfig.OFP_SWITCH_CONFIG_LEN));
    }

    OfpSwitchConfigSetMsg(byte version, int xid,
            OfpSwitchConfig swcfg) {
        super(version, OfpType.OFPT_SET_CONFIG, xid);
        setSwitchConfig(swcfg);
        setLength((short) (getLength() + OfpSwitchConfig.OFP_SWITCH_CONFIG_LEN));
    }

    OfpSwitchConfigSetMsg(byte[] data) {
        this(data, 0);
    }

    OfpSwitchConfigSetMsg(byte[] data, int offset) {
        super(data, offset);

        if (getType() != OfpType.OFPT_SET_CONFIG) {
            throw new IllegalArgumentException("Bad Data : Expected  : 0x"
                    + Integer.toHexString(OfpType.OFPT_SET_CONFIG
                            .getValue())
                    + " Got : 0x" + Integer.toHexString(getType().getValue()));
        }

        setSwitchConfig(new OfpSwitchConfig(data, offset + OFP_HDR_LEN));
    }

    OfpSwitchConfigSetMsg(InputStream is) throws IOException {
        super(is);

        if (getType() != OfpType.OFPT_SET_CONFIG) {
            throw new IOException("Bad Data : Expected  : 0x"
                    + Integer.toHexString(OfpType.OFPT_SET_CONFIG
                            .getValue())
                    + " Got : 0x" + Integer.toHexString(getType().getValue()));
        }

        setSwitchConfig(new OfpSwitchConfig(is));
    }

    public OfpSwitchConfig getSwitchConfig() {
        return swCfg;
    }

    public void setSwitchConfig(OfpSwitchConfig swCfg) {
        this.swCfg = swCfg;
    }

    @Override
    public byte[] toByteArray() {
        byte[] data = new byte[getLength()];
        return toByteArray(data, 0);
    }

    @Override
    public byte[] toByteArray(byte[] data) {
        return toByteArray(data, 0);
    }

    @Override
    public byte[] toByteArray(byte[] data, int offset) {
        super.toByteArray(data, offset);
        return getSwitchConfig().toByteArray(data, offset + OFP_HDR_LEN);
    }

    @Override
    public OutputStream toOutputStream(OutputStream os) throws IOException {
        super.toOutputStream(os);
        getSwitchConfig().toOutputStream(os);
        return os;
    }

    @Override
    public OfpSwitchConfigSetMsg fromInputStream(InputStream is)
            throws IOException {
        return readFromInputStream(is);
    }

    @Override
    public OfpSwitchConfigSetMsg fromByteArray(byte[] data) {
        return fromByteArray(data, 0);
    }

    @Override
    public OfpSwitchConfigSetMsg fromByteArray(byte[] data, int offset) {
        return new OfpSwitchConfigSetMsg(data, offset);
    }

    public static OfpSwitchConfigSetMsg readFromInputStream(InputStream is)
            throws IOException {
        return new OfpSwitchConfigSetMsg(is);
    }

    public static byte[] readOfpSwitchConfigRequestMsg(InputStream is)
            throws IOException {
        return readOfpSwitchConfigSetMsg(is, null, 0);
    }

    public static byte[]
            readOfpSwitchConfigRequestMsg(InputStream is, byte[] data)
                    throws IOException {
        return readOfpSwitchConfigSetMsg(is, data, 0);
    }

    public static byte[] readOfpSwitchConfigSetMsg(
            InputStream is,
            byte[] data,
            int offset)
            throws IOException {
        data = readOfpHeader(is, data, offset);
        data =
                OfpSwitchConfig.readOfpSwitchConfig(is, data, offset
                        + OFP_HDR_LEN);
        return data;
    }

    @Override
    public String toString() {
        return String.format(
                "OfpHeader->%s : SwitchConfig->%s\n",
                super.toString(), getSwitchConfig().toString());
    }

    public boolean equals(OfpSwitchConfigSetMsg hdr) {
        if (super.equals(hdr) &&
                (getSwitchConfig() == hdr.getSwitchConfig()))
        {
            return true;
        }
        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o instanceof OfpSwitchConfigSetMsg) {
            return equals((OfpSwitchConfigSetMsg) o);
        }
        return false;
    }
}
