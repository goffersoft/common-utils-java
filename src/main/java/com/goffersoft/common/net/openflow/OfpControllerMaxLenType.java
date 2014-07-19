/**
 ** File: OfpControllerMaxLenType.java
 **
 ** Description : OpenFlow OfpControllerMaxLenType Enumeration class
 **               -- OpenFlow Switch Specification Version 1.4.0 - October 14th, 2013
 **
 ** Date           Author                          Comments
 ** 08/31/2013     Prakash Easwar                  Created  
 **/
package com.goffersoft.common.net.openflow;

public enum OfpControllerMaxLenType {
    OFPCML_MAX((short) 0xffe5),
    OFPCML_NO_BUFFER((short) 0xffff),
    OFPCML_CUSTOM_LEN((short) 0);

    private short ofpCmlValue;

    public static final OfpControllerMaxLenType ofpControllerMaxLen[] = {
            OFPCML_MAX,
            OFPCML_NO_BUFFER,
            OFPCML_CUSTOM_LEN,
    };

    public static final String ofpControllerMaxLenDescr[] = {
            "0xffe5 - maximum max_len value which can be used " +
                    "to request a specific byte length",
            "0xffff - indicates that no buffering should be " +
                    "applied and the whole packet is to be " +
                    "sent to the controller",
            "custom length configured by the user"
    };

    private OfpControllerMaxLenType(short value) {
        ofpCmlValue = value;
    }

    public short getValue() {
        return ofpCmlValue;
    }

    public void setValue(short value) {
        if (this == OFPCML_CUSTOM_LEN) {
            ofpCmlValue = (short) (value & OFPCML_MAX.getValue());
        }
    }

    public String getDescr() {
        if (this == OFPCML_CUSTOM_LEN) {
            return String.format(
                    "%s : len=%d(%04x)",
                    ofpControllerMaxLenDescr[ordinal()],
                    (ofpCmlValue & 0xffff),
                    (ofpCmlValue & 0xffff));
        } else {
            return ofpControllerMaxLenDescr[ordinal()];
        }
    }

    @Override
    public String toString() {
        return getDescr();
    }

    public static OfpControllerMaxLenType getEnum(short len) {
        if (len == 0xffff) {
            return OFPCML_MAX;
        } else if (len == 0xffe5) {
            return OFPCML_NO_BUFFER;
        } else {
            OfpControllerMaxLenType tmp = OFPCML_CUSTOM_LEN;
            tmp.setValue(len);
            return tmp;
        }
    }
}
