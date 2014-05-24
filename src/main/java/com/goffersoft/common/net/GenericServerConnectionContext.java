/**
 ** File: GenericServerConnectionContext.java
 **
 ** Description : Base Class for all Server and Connection Contexts
 **
 ** Date           Author                          Comments
 ** 08/31/2013     Prakash Easwar                  Created  
 **/
package com.goffersoft.common.net;

import org.apache.log4j.Logger;

public class GenericServerConnectionContext< ListenerType > {
    private static final Logger log = Logger
            .getLogger(GenericServerConnectionContext.class);
    private long inactivityTimeout =
            GenericConnection.DEFAULT_INACTIVITY_TIMEOUT_VALUE;
    private int socketTimeout = GenericConnection.DEFAULT_SOCKET_TIMEOUT;
    private boolean autoStart = true;
    private ListenerType defaultListener = null;

    public ListenerType getDefaultListener() {
        return defaultListener;
    }

    public void setDefaultListener(ListenerType defaultListener) {
        this.defaultListener = defaultListener;
    }

    public long getInactivityTimeout() {
        return inactivityTimeout;
    }

    public void setInactivityTimeout(long inactivityTimeout) {
        this.inactivityTimeout = inactivityTimeout;
    }

    public int getSocketTimeout() {
        return socketTimeout;
    }

    public void setSocketTimeout(int socketTimeout) {
        this.socketTimeout = socketTimeout;
    }

    public boolean isAutoStart() {
        return autoStart;
    }

    public void setAutoStart() {
        this.autoStart = true;
    }

    public void clearAutoStart() {
        this.autoStart = false;
    }

    @Override
    public String toString() {
        StringBuffer str = new StringBuffer();
        str
                .append(String
                        .format(
                                "SocketTimeout=%d, InactivityTimeout=%d, StartType=%s",
                                getSocketTimeout(),
                                getInactivityTimeout(),
                                isAutoStart() ? "Auto Start" : "Manual Start"));

        if (getDefaultListener() != null) {
            str.append("DefaultListener: \n"
                    + getDefaultListener().toString()
                    + "\n");
        }

        return str.toString();
    }

    public boolean equals(GenericServerConnectionContext<ListenerType> o) {
        if (getDefaultListener() == o.getDefaultListener()
                && isAutoStart() == o.isAutoStart()
                && getInactivityTimeout() == o.getInactivityTimeout()
                && getSocketTimeout() == o.getSocketTimeout()
                && isAutoStart() == o.isAutoStart()) {
            return true;
        }
        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o instanceof GenericServerConnectionContext<?>) {
            return equals((GenericServerConnectionContext<?>) o);
        }
        return false;
    }
}
