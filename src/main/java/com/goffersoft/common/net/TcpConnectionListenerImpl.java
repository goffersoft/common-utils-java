/**
 ** File: TcpDefaultConnectionListener.java
 **
 ** Description : TcpDefaultConnectionListener class - implements TcpConnectionListener
 **               factory default listener installed by Tcp connection in case no listeners
 **               are installed
 **
 ** Date           Author                          Comments
 ** 08/30/2013     Prakash Easwar                  Created  
 **/
package com.goffersoft.common.net;

import org.apache.log4j.Logger;

import com.goffersoft.common.utils.PrintUtils;

public class TcpConnectionListenerImpl
        extends
        GenericConnectionListenerImpl
        implements
        TcpConnectionListener {
    private static final Logger log = Logger
            .getLogger(TcpConnectionListenerImpl.class);

    @Override
    public void onReceivedData(TcpConnection tcp, byte[] data, int offset,
            int length) {
        log
                .info("Factory Default Listener Installed: Received TCP Packet: Length="
                        + length);

        log.debug("Packet Details : \n"
                + PrintUtils.byteArrayToHexString(data, offset, length, 16));
    }

    @Override
    public void onConnectionTerminated(TcpConnection tcp, Exception error) {
        log
                .debug("Factory Default Listener Installed : TCP Connection Terminated ");
        if (error != null) {
            error.printStackTrace();
        }
    }

    @Override
    public void onErrorReceivedSmallPacket(TcpConnection tcp, byte[] data,
            int offset, int length) {
        log
                .info("Factory Default Listener Installed: Received Small TCP Packet: Length="
                        + length);

        log.debug("Packet Details : \n"
                + PrintUtils.byteArrayToHexString(data, offset, length, 16));
    }

    @Override
    public void onErrorReceivedLargePacket(TcpConnection tcp, byte[] data,
            int offset, int length) {
        log
                .info("Factory Default Listener Installed: Received Large TCP Packet: Length="
                        + length);

        log.debug("Packet Details : \n"
                + PrintUtils.byteArrayToHexString(data, offset, length, 16));
    }
}