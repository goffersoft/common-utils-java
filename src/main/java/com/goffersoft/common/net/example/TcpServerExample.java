package com.goffersoft.common.net.example;

import org.apache.log4j.Logger;

import com.goffersoft.common.net.TcpServer;

public class TcpServerExample {
    private static final Logger log = Logger.getLogger(TcpServerExample.class);

    /**
     * @param args
     */
    public static void main(String[] args) {
        try {
            TcpServer tcp = new TcpServer(8888, null, null, false);
            tcp.setInactivityTime(60000);
            tcp.start();

            Thread.sleep(30000);

            log.info("changing port number to 8889");

            // tcp.setLocalSocketAddress((InetAddress)null,8889);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
