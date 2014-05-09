/**
 ** File: MacAddress.java
 **
 ** Description : MACAddress Utility Class
 **
 ** Date           Author                          Comments
 ** 08/31/2013     Prakash Easwar                  Created  
 **/

package com.goffersoft.common.utils.net;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Scanner;

import com.goffersoft.common.utils.GenericSerializable;
import com.goffersoft.common.utils.PatternUtils;
import com.goffersoft.common.utils.ReadUtils;

public class MacAddress implements GenericSerializable<MacAddress> {
    private byte[] macaddr;

    public static final int MAC_ADDRESS_LEN = 6;

    public MacAddress() {
        macaddr = new byte[MAC_ADDRESS_LEN];
    }

    public MacAddress(byte[] mac) {
        this();
        setMacAddress(mac);
    }

    public MacAddress(byte[] mac, int offset) {
        this();
        setMacAddress(mac, offset);
    }

    // Construct a new MAC address object with a given MAC
    // address represented as a string of 6 bytes
    // in hex delimited by semicolons.
    public MacAddress(String mac) {
        this();
        setMacAddress(mac);
    }

    // Construct a new MAC address object with a given MAC
    // address represented as a string of 6 bytes
    // in hex delimited by 'sep'.
    public MacAddress(String mac, char sep) {
        this();
        setMacAddress(mac, sep);
    }

    // Construct a new MAC address object with a given MAC
    // address represented as a string of 6 bytes
    // in hex delimited by 'sep'.
    public MacAddress(String mac, String sep) {
        this();
        setMacAddress(mac, sep);
    }

    public String toString() {
        return String.format("%02x:%02x:%02x:%02x:%02x:%02x ",
                macaddr[0] & 0xff, macaddr[1] & 0xff, macaddr[2] & 0xff,
                macaddr[3] & 0xff, macaddr[4] & 0xff, macaddr[5] & 0xff);
    }

    public void setMacAddress(byte[] mac) {
        setMacAddress(mac, 0);
    }

    public void setMacAddress(byte[] mac, int offset) {
        System.arraycopy(mac, offset, macaddr, 0, MAC_ADDRESS_LEN);
    }

    // Construct a new MAC address object with a given MAC
    // address represented as a string of 6 bytes
    // in hex delimited by semicolons.
    public void setMacAddress(String mac) {
        setMacAddress(mac, ':');
    }

    // Construct a new MAC address object with a given MAC
    // address represented as a string of 6 bytes
    // in hex delimited by 'sep'.
    public void setMacAddress(String mac, char sep) {
        setMacAddress(mac, Character.toString(sep));
    }

    // Construct a new MAC address object with a given MAC
    // address represented as a string of 6 bytes
    // in hex delimited by 'sep'.
    public void setMacAddress(String mac, String sep) {
        Scanner s = new Scanner(mac);
        s.useDelimiter(sep);
        macaddr[0] = s.nextByte();
        macaddr[1] = s.nextByte();
        macaddr[2] = s.nextByte();
        macaddr[3] = s.nextByte();
        macaddr[4] = s.nextByte();
        macaddr[5] = s.nextByte();
        s.close();
    }

    public MacAddress getMacAddress() {
        return new MacAddress(macaddr);
    }

    public static MacAddress getMacAddress(InputStream is) throws IOException {
        byte[] maddr = new byte[MAC_ADDRESS_LEN];
        ReadUtils.readInput(is, maddr, 0, MAC_ADDRESS_LEN);
        return new MacAddress(maddr);
    }

    public static MacAddress getMacAddress(byte[] data) {
        return getMacAddress(data, 0);
    }

    public static MacAddress getMacAddress(byte[] data, int offset) {
        return new MacAddress(data, offset);
    }

    public boolean isBroadcast() {
        return (((macaddr[0] & 0xFF) == 0xFF) && ((macaddr[1] & 0xFF) == 0xFF)
                && ((macaddr[2] & 0xFF) == 0xFF)
                && ((macaddr[3] & 0xFF) == 0xFF)
                && ((macaddr[4] & 0xFF) == 0xFF) && ((macaddr[5] & 0xFF) == 0xFF));
    }

    public boolean isUnicast() {
        return ((macaddr[0] & 0x01) == 0x00);
    }

    public boolean isMulticast() {
        return ((macaddr[0] & 0x01) == 0x01);
    }

    public boolean isGlobal() {
        return ((macaddr[0] & 0x02) == 0x00);
    }

    public boolean isLocal() {
        return ((macaddr[0] & 0x02) == 0x02);
    }

    public boolean equals(MacAddress mac) {
        return PatternUtils.byteCompare(macaddr, 0, mac.toByteArray(), 0,
                MAC_ADDRESS_LEN);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof MacAddress) {
            return equals((MacAddress) obj);
        }
        return false;
    }

    @Override
    public MacAddress fromInputStream(InputStream is) throws IOException {
        return getMacAddress(is);
    }

    @Override
    public MacAddress fromByteArray(byte[] ba) {
        return new MacAddress(ba);
    }

    public MacAddress fromByteArray(byte[] ba, int offset) {
        return new MacAddress(ba, offset);
    }

    @Override
    public byte[] toByteArray() {
        return macaddr;
    }

    @Override
    public OutputStream toOutputStream(OutputStream os) throws IOException {
        os.write(macaddr);
        return os;
    }

    @Override
    public byte[] toByteArray(byte[] ba) {
        return toByteArray(ba, 0);
    }

    @Override
    public byte[] toByteArray(byte[] ba, int offset) {
        System.arraycopy(macaddr, 0, ba, offset, macaddr.length);
        return ba;
    }
}
