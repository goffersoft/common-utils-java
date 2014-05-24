/**
 ** File: TcpSSLConnectionExample.java
 ** 
 ** Description : Tcp SSL Connection Example
 ** 
 ** Date           Author                          Comments
 ** 08/30/2013     Prakash Easwar                  Created  
 **/
package com.goffersoft.common.net.example;

import java.net.InetAddress;

import org.apache.log4j.Logger;

import com.goffersoft.common.net.GenericConnectionContext;
import com.goffersoft.common.net.GenericConnectionFactory;
import com.goffersoft.common.net.TcpSSLConnection;
import com.goffersoft.common.net.TcpSSLConnectionContext;
import com.goffersoft.common.net.TcpSSLConnectionFactory;

public class TcpSSLConnectionExample {
    private static final Logger log = Logger
            .getLogger(TcpSSLConnectionExample.class);

    public static void main(String[] arstring) {
        try {
            TcpSSLConnectionContext tcpctxt =
                    (TcpSSLConnectionContext) GenericConnectionContext
                            .getContext(TcpSSLConnectionContext.class
                                    .getName());
            tcpctxt.setInactivityTimeout(60000);
            tcpctxt
                    .setDefaultListener(new TcpSSLConnectionExampleListenerImpl());
            TcpSSLConnectionFactory tcpfactory =
                    (TcpSSLConnectionFactory) GenericConnectionFactory
                            .getFactory(TcpSSLConnectionFactory.class
                                    .getName());
            tcpfactory.setContext(tcpctxt);
            TcpSSLConnection tcp =
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
                Thread.sleep(1000);
                i++;
            }
            Thread.sleep(20000);

            log.info("changing port number to 9998");

            // tcp.setLocalSocketAddress(InetAddress.getByName("127.0.0.1"),40000);
            tcp.setRemoteSocketAddress(
                    InetAddress.getByName("127.0.0.1"),
                    9998);

            while (i < 20) {
                tcp
                        .send(("Hello World : " + i)
                                .getBytes());
                Thread.sleep(1000);
                i++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}