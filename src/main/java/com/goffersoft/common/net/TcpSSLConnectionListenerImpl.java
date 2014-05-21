/**
 ** File: TcpSSLConnectionListenerImpl.java
 **
 ** Description : TcpSSLConnectionListenerImpl class - implements TcpSSLConnectionListener
 **               factory default listener installed by tcp ssl connection incase no listeners
 **               are installed
 **
 ** Date           Author                          Comments
 ** 08/30/2013     Prakash Easwar                  Created  
 **/
package com.goffersoft.common.net;

import javax.net.ssl.HandshakeCompletedEvent;

import org.apache.log4j.Logger;

import com.goffersoft.common.utils.PrintUtils;

public class TcpSSLConnectionListenerImpl
        extends
        GenericConnectionListenerImpl
        implements
        TcpSSLConnectionListener {
    private static final Logger log = Logger
            .getLogger(TcpSSLConnectionListenerImpl.class);

    @Override
    public void onReceivedData(
            TcpSSLConnection tcp,
            byte[] data,
            int offset,
            int length) {
        log
                .info("Factory Default Listener Installed: Received TCP(SSL) Packet: Length="
                        + length);

        log.debug("Packet Details : \n"
                + PrintUtils.byteArrayToHexString(data, offset, length, 16));
    }

    @Override
    public void onConnectionTerminated(TcpSSLConnection tcp, Exception error) {
        log
                .debug("Factory Default Listener Installed : TCP(SSL) Connection Terminated ");
        if (error != null) {
            error.printStackTrace();
        }
    }

    @Override
    public void onErrorReceivedSmallPacket(
            TcpSSLConnection tcp,
            byte[] data,
            int offset,
            int length) {
        log
                .info("Factory Default Listener Installed: Received Small TCP(SSL) Packet: Length="
                        + length);

        log.debug("Packet Details : \n"
                + PrintUtils.byteArrayToHexString(data, offset, length, 16));
    }

    @Override
    public void onErrorReceivedLargePacket(
            TcpSSLConnection tcp,
            byte[] data,
            int offset,
            int length) {
        log
                .info("Factory Default Listener Installed: Received Large TCP(SSL) Packet: Length="
                        + length);

        log.debug("Packet Details : \n"
                + PrintUtils.byteArrayToHexString(data, offset, length, 16));
    }

    @Override
    public void onHandshakeCompleted(
            TcpSSLConnection tcp,
            HandshakeCompletedEvent event) {
        log
                .info("Factory Default Listener Installed: TCP(SSL) Handshake Completed");
    }

}
