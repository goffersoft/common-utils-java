/**
 ** File: TcpServerConnectionExampleListenerImpl.java
 **
 ** Description : Server Side Listener for Tcp Connection Example
 **
 ** Date           Author                          Comments
 ** 08/31/2013     Prakash Easwar                  Created
 **/
package com.goffersoft.common.net.example;

import java.io.IOException;

import org.apache.log4j.Logger;

import com.goffersoft.common.net.TcpConnection;
import com.goffersoft.common.net.TcpConnectionListenerImpl;
import com.goffersoft.common.utils.PrintUtils;

public class TcpServerConnectionExampleListenerImpl
        extends
        TcpConnectionListenerImpl {

    private static final Logger log = Logger
            .getLogger(TcpServerConnectionExampleListenerImpl.class);

    @Override
    public void onReceivedData(TcpConnection tcp, byte[] data, int offset,
            int length) {
        log
                .info("***Default Server Listener Installed: Received TCP Packet: Length="
                        + length);

        log.debug("Packet Details : \n"
                + PrintUtils.byteArrayToHexString(data, offset, length, 16));

        try {
            tcp.send("OK".getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
