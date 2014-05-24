/**
 ** File: TcpServerExample.java
 ** 
 ** Description : Tcp Server Example
 ** 
 ** Date           Author                          Comments
 ** 08/30/2013     Prakash Easwar                  Created  
 **/
package com.goffersoft.common.net.example;

import java.net.InetAddress;

import org.apache.log4j.Logger;

import com.goffersoft.common.net.GenericConnectionContext;
import com.goffersoft.common.net.GenericServerContext;
import com.goffersoft.common.net.GenericServerFactory;
import com.goffersoft.common.net.TcpConnectionContext;
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
            TcpConnectionContext tcpcctxt =
                    (TcpConnectionContext) GenericConnectionContext
                            .getContext(TcpConnectionContext.class
                                    .getName());
            tcpsctxt.setInactivityTimeout(60000);
            tcpcctxt
                    .setDefaultListener(new TcpServerConnectionExampleListenerImpl());
            TcpServerFactory tcpfactory =
                    (TcpServerFactory) GenericServerFactory
                            .getFactory(TcpServerFactory.class
                                    .getName());
            tcpfactory.setServerContext(tcpsctxt);
            tcpfactory.setConnectionContext(tcpcctxt);
            TcpServer tcp =
                    tcpfactory.createServer(
                            6666,
                            InetAddress.getByName("127.0.0.1"));
            tcp.setInactivityTime(60000);
            tcp.start();

            Thread.sleep(20000);

            log.info("changing port number to 9998");

            tcp.setLocalSocketAddress(InetAddress.getByName("127.0.0.1"), 9998);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
