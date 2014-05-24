/**
 ** File: TcpSSLServerExample.java
 ** 
 ** Description : SSL Tcp Server Example
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
import com.goffersoft.common.net.TcpSSLConnectionContext;
import com.goffersoft.common.net.TcpSSLServer;
import com.goffersoft.common.net.TcpSSLServerContext;
import com.goffersoft.common.net.TcpSSLServerFactory;

public class TcpSSLServerExample {
    private static final Logger log = Logger
            .getLogger(TcpSSLServerExample.class);

    /**
     * @param args
     */
    public static void main(String[] args) {
        try {
            try {
                TcpSSLServerContext tcpsctxt =
                        (TcpSSLServerContext) GenericServerContext
                                .getContext(TcpSSLServerContext.class
                                        .getName());
                TcpSSLConnectionContext tcpcctxt =
                        (TcpSSLConnectionContext) GenericConnectionContext
                                .getContext(TcpSSLConnectionContext.class
                                        .getName());
                tcpsctxt.setInactivityTimeout(60000);
                tcpcctxt
                        .setDefaultListener(new TcpSSLServerConnectionExampleListenerImpl());
                TcpSSLServerFactory tcpfactory =
                        (TcpSSLServerFactory) GenericServerFactory
                                .getFactory(TcpSSLServerFactory.class
                                        .getName());
                tcpfactory.setServerContext(tcpsctxt);
                tcpfactory.setConnectionContext(tcpcctxt);
                TcpSSLServer tcp =
                        tcpfactory.createServer(
                                6666,
                                InetAddress.getByName("127.0.0.1"));
                tcp.setInactivityTime(60000);
                tcp.start();

                Thread.sleep(10000);

                log.info("changing port number to 9998");

                tcp.setLocalSocketAddress(
                        InetAddress.getByName("127.0.0.1"),
                        9998);

            } catch (Exception e) {
                e.printStackTrace();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}