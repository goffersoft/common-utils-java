package com.goffersoft.common.net.example;

import java.net.InetAddress;

import org.apache.log4j.Logger;

import com.goffersoft.common.net.GenericServerContext;
import com.goffersoft.common.net.TcpServer;
import com.goffersoft.common.net.TcpServerContext;
import com.goffersoft.common.net.TcpServerFactory;

public class TcpServerExample {
    private static final Logger log = Logger.getLogger(TcpServerExample.class);

    /**
     * @param args
     */
    public static void main(String[] args) {
        try {
            TcpServerContext tcpsctxt =
                    (TcpServerContext) GenericServerContext
                            .getContext(TcpServerContext.class
                                    .getName());
            tcpsctxt.setInactivityTimeout(60000);
            TcpServerFactory tcpfactory = new TcpServerFactory(tcpsctxt, null);
            TcpServer tcp =
                    tcpfactory.createServer(
                            6666,
                            InetAddress.getByName("127.0.0.1"));
            tcp.setInactivityTime(60000);
            tcp.start();

            Thread.sleep(10000);

            log.info("changing port number to 9998");

            tcp.setLocalSocketAddress(InetAddress.getByName("127.0.0.1"), 9998);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
