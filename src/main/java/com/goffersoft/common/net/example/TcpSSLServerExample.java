package com.goffersoft.common.net.example;

import org.apache.log4j.Logger;

import com.goffersoft.common.net.TcpSSLServer;

public class TcpSSLServerExample {
    private static final Logger log = Logger.getLogger(TcpServerExample.class);

    /**
     * @param args
     */
    public static void main(String[] args) {
        try {
            TcpSSLServer tcpssl = new TcpSSLServer(6666, null, null, false);
            tcpssl.setInactivityTime(60000);
            tcpssl.start();

            // Thread.sleep(30000);

            // log.info("changing port number to 8889");

            // tcp.setLocalSocketAddress((InetAddress)null,8889);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}