package com.goffersoft.common.net.example;

import java.net.InetAddress;

import org.apache.log4j.Logger;

import com.goffersoft.common.net.TcpConnection;

public class TcpConnectionExample {
    private static final Logger log = Logger
            .getLogger(TcpConnectionExample.class);

    /**
     * @param args
     */
    public static void main(String[] args) {
        try {
            TcpConnection tcp = new TcpConnection(6666,
                    InetAddress.getByName("127.0.0.1"), null);
            tcp.setInactivityTime(60000);
            tcp.start();

            Thread.sleep(30000);

            log.info("changing port number to 9998");

            // tcp.setLocalSocketAddress(InetAddress.getByName("127.0.0.1"),40000);
            tcp.setRemoteSocketAddress(InetAddress.getByName("127.0.0.1"), 9998);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
