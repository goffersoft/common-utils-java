/**
 ** File: OfpPortNum.java
 **
 ** Description : OpenFlow Ethernet Port Number Enumeration class
 **               -- OpenFlow Switch Specification Version 1.1.0 - February 28th, 2011
 **
 ** Date           Author                          Comments
 ** 08/31/2013     Prakash Easwar                  Created  
 **/
package com.goffersoft.common.net.openflow;

import java.io.IOException;
import java.io.InputStream;

import org.apache.log4j.Logger;

import com.goffersoft.common.utils.EndianConversion;

public enum OfpPortNum {
    OFPP_SWITCH_PORT(getNextPortNum()), OFPP_IN_PORT(0xfffffff8), OFPP_TABLE(
            0xfffffff9), OFPP_NORMAL(0xfffffffa), OFPP_FLOOD(0xfffffffb), OFPP_ALL(
            0xfffffffc), OFPP_CONTROLLER(0xfffffffd), OFPP_LOCAL(0xfffffffe), OFPP_ANY(
            0xffffffff), OFPP_MAX(0xffffff00), ;

    private static final Logger log = Logger.getLogger(OfpPortNum.class);

    public static final OfpPortNum portNum[] = { OFPP_SWITCH_PORT,
            OFPP_IN_PORT, OFPP_TABLE, OFPP_NORMAL, OFPP_FLOOD, OFPP_ALL,
            OFPP_CONTROLLER, OFPP_LOCAL, OFPP_ANY, OFPP_MAX, };

    public static final String portName[] = {
            "Switch Port (Port Range : 1 - 0xFFFFFF00)",
            "Reserved OpenFlow Port(fake output port) - IN_PORT    - 0xfffffff8",
            "Reserved OpenFlow Port(fake output port) - TABLE      - 0xfffffff9",
            "Reserved OpenFlow Port(fake output port) - NORMAL     - 0xfffffffa",
            "Reserved OpenFlow Port(fake output port) - FLOOD      - 0xfffffffb",
            "Reserved OpenFlow Port(fake output port) - ALL        - 0xfffffffc",
            "Reserved OpenFlow Port(fake output port) - CONTROLLER - 0xfffffffd",
            "Reserved OpenFlow Port(fake output port) - LOCAL      - 0xfffffffe",
            "Reserved OpenFlow Port(fake output port) - ANY        - 0xffffffff",
            "Maximum Port Number" };

    private static final int portId[] = { 0, 0xfffffff8, 0xfffffff9,
            0xfffffffa, 0xfffffffb, 0xfffffffc, 0xfffffffd, 0xfffffffe,
            0xffffffff, 0xffffff00, };

    public static final String portDescr[] = {
            "Pysical/Logical Switch Port Number",
            "Send the packet out the input port. "
                    + "This reserved port must be explicitly used in "
                    + "order to send back out of the input port.",
            "Submit the packet to the first flow table "
                    + "NB : This destinatin port can only be used "
                    + "in packet-out messages.",
            "Process with Normal L2/L3 Switching",
            "All physical ports in VLAN, except input port "
                    + "and those blocked or link down.",
            "All physical ports except input port.",
            "Send to controller.",
            "Local Openflow 'port'.",
            "Wildcard port used only for flow and (delete) "
                    + "and flow stats requests. Selects all flows "
                    + "regardless of output port (including flows with "
                    + "no output port).",
            "Maximum Number of physical and/or logical switch ports "
                    + "- MAX - 0xffffff00", };

    static public final int OFPP_MAX_PORTNUM = 0xffffff00;

    private int portnum;
    private static long nextPortNum = 1;

    private OfpPortNum(int port) {
        portnum = port;
    }

    public int getPortnum() {
        return (portnum & OFPP_MAX_PORTNUM);
    }

    public void setPortnum(int portnum) {
        int tmp = portnum;
        if (this == OFPP_SWITCH_PORT) {
            if (tmp == 0) {
                this.portnum = getNextPortNum();
            } else {
                this.portnum = (tmp & OFPP_MAX_PORTNUM);
            }
        }
    }

    synchronized public static int getNextPortNum() {
        long portno = nextPortNum;
        nextPortNum++;
        if (nextPortNum > OFPP_MAX_PORTNUM) {
            nextPortNum = 1;
        }
        return (int) (portno & OFPP_MAX_PORTNUM);
    }

    public int getValue() {
        return portnum;
    }

    public String getName() {
        return portName[ordinal()];
    }

    public String getDescr() {
        return portDescr[ordinal()];
    }

    @Override
    public String toString() {
        return getName() + " : " + getDescr() + " : " + getValue() + "\n";
    }

    public static OfpPortNum getEnum(int portnum) {
        long tmp = portnum & 0xffffffff;
        if (tmp > OFPP_MAX_PORTNUM) {
            for (int i = 0; i < portId.length; i++) {
                if (portId[i] == tmp) {
                    return portNum[i];
                }
            }
        }
        OfpPortNum tmp1 = OFPP_SWITCH_PORT;
        if ((tmp >= 1) && (tmp <= OFPP_MAX_PORTNUM)) {
            tmp1.setPortnum((int) tmp);
        }
        return tmp1;
    }

    public static OfpPortNum getEnum(byte[] data) {
        return getEnum(EndianConversion.byteArrayToIntBE(data, 0));
    }

    public static OfpPortNum getEnum(byte[] data, int offset) {
        return getEnum(EndianConversion.byteArrayToIntBE(data, offset));
    }

    public static OfpPortNum getEnum(InputStream is) throws IOException {
        return getEnum(EndianConversion.inputStreamToIntBE(is));
    }
}