package com.goffersoft.common.net.example;

import java.net.InetAddress;

import org.apache.log4j.Logger;

import com.goffersoft.common.net.GenericConnectionContext;
import com.goffersoft.common.net.TcpConnection;
import com.goffersoft.common.net.TcpConnectionContext;
import com.goffersoft.common.net.TcpConnectionFactory;

public class TcpConnectionExample {
    private static final Logger log = Logger
            .getLogger(TcpConnectionExample.class);

    /**
     * @param args
     */
    public static void main(String[] args) {
        try {
            TcpConnectionContext tcpctxt =
                    (TcpConnectionContext) GenericConnectionContext
                            .getContext(TcpConnectionContext.class
                                    .getName());
            tcpctxt.setInactivityTimeout(60000);
            TcpConnectionFactory tcpfactory = new TcpConnectionFactory(tcpctxt);
            TcpConnection tcp =
                    tcpfactory.createConnection(
                            0,
                            null,
                            6666,
                            InetAddress.getByName("127.0.0.1"));

            int i = 0;
            while (i < 10) {
                tcp
                        .send(("Hello World : " + i)
                                .getBytes());
                i++;
            }
            Thread.sleep(30000);

            log.info("changing port number to 9998");

            // tcp.setLocalSocketAddress(InetAddress.getByName("127.0.0.1"),40000);
            tcp.setRemoteSocketAddress(
                    InetAddress.getByName("127.0.0.1"),
                    9998);

            Thread.sleep(1000);
            log.debug("here-1");
            while (i < 20) {
                log.debug("here");
                tcp
                        .send(("Hello World : " + i)
                                .getBytes());
                i++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
