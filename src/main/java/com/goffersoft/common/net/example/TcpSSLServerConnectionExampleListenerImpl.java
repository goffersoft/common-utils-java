/**
 ** File: TcpSSLServerConnectionExampleListenerImpl.java
 **
 ** Description : SSL Server Side Listener for Tcp Connection Example
 **
 ** Date           Author                          Comments
 ** 08/31/2013     Prakash Easwar                  Created
 **/
package com.goffersoft.common.net.example;

import org.apache.log4j.Logger;

import com.goffersoft.common.net.TcpSSLConnection;
import com.goffersoft.common.net.TcpSSLConnectionListenerImpl;
import com.goffersoft.common.utils.PrintUtils;

public class TcpSSLServerConnectionExampleListenerImpl
        extends
        TcpSSLConnectionListenerImpl {
    private static final Logger log = Logger
            .getLogger(TcpSSLServerConnectionExampleListenerImpl.class);

    @Override
    public void onReceivedData(TcpSSLConnection tcp, byte[] data, int offset,
            int length) {
        log
                .info("***Default Client Listener Installed: Received TCP Packet: Length="
                        + length);

        log.debug("Packet Details : \n"
                + PrintUtils.byteArrayToHexString(data, offset, length, 16));
    }
}
