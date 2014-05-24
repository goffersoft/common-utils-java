/**
 ** File: TcpSSLConnectionExampleListenerImpl.java
 **
 ** Description : SSL Client Side Listener Tcp Connection Example
 **
 ** Date           Author                          Comments
 ** 08/30/2013     Prakash Easwar                  Created  
 **/
package com.goffersoft.common.net.example;

import org.apache.log4j.Logger;

import com.goffersoft.common.net.TcpSSLConnection;
import com.goffersoft.common.net.TcpSSLConnectionListenerImpl;
import com.goffersoft.common.utils.PrintUtils;

public class TcpSSLConnectionExampleListenerImpl
        extends
        TcpSSLConnectionListenerImpl {
    private static final Logger log = Logger
            .getLogger(TcpSSLConnectionExampleListenerImpl.class);

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
