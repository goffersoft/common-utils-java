/**
 ** File: UdpServer.java
 **
 ** Description : UdpServer class - test udp server
 **               dumps all received messages to console
 ** Date           Author                          Comments
 ** 08/31/2013     Prakash Easwar                  Created  
 **/
package com.goffersoft.common.net.example;

import org.apache.log4j.Logger;

import com.goffersoft.common.net.UdpConnection;

public class UdpServer {
    private static final Logger log = Logger.getLogger(UdpServer.class);

    /**
     * @param args
     */
    public static void main(String[] args) {
        try {
            UdpConnection udp = new UdpConnection(7777, null);
            udp.setInactivityTime(60000);
            udp.start();

            Thread.sleep(30000);

            log.info("changing port number to 6667");

            udp.setLocalSocketAddress(null, 6667);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
