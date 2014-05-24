/**
 ** File: TcpConnectionExample.java
 ** 
 ** Description : Tcp Connection Example
 ** 
 ** Date           Author                          Comments
 ** 08/30/2013     Prakash Easwar                  Created  
 **/

package com.goffersoft.common.net.example;

import java.net.InetAddress;

import org.apache.log4j.Logger;

import com.goffersoft.common.net.GenericConnectionContext;
import com.goffersoft.common.net.GenericConnectionFactory;
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
            tcpctxt.setDefaultListener(new TcpConnectionExampleListenerImpl());
            TcpConnectionFactory tcpfactory =
                    (TcpConnectionFactory) GenericConnectionFactory
                            .getFactory(TcpConnectionFactory.class
                                    .getName());
            tcpfactory.setContext(tcpctxt);
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
