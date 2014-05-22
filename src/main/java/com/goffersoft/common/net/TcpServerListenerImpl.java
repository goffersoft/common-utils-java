/**
 ** File: TcpServerListenerImpl.java
 **
 ** Description : TcpServerListenerImpl class - implements TcpServerListener
 **               factory default listener installed by Tcp server in case no listeners
 **               are installed
 **
 ** Date           Author                          Comments
 ** 08/30/2013     Prakash Easwar                  Created  
 **/
package com.goffersoft.common.net;

import org.apache.log4j.Logger;

public class TcpServerListenerImpl
        extends
        GenericConnectionListenerImpl
        implements
        TcpServerListener {

    private static final Logger log = Logger
            .getLogger(TcpServerListenerImpl.class);

    @Override
    public void onServerTerminated(TcpServer tcp_server, Exception error) {

        log.info("Factory Default Listener Installed : TCP Server Terminated ");
        if (error != null) {
            error.printStackTrace();
        }
    }

    @Override
    public void onIncomingConnection(TcpServer tcp_server,
            TcpConnection tcp_conn) {

        log
                .info("Factory Default Listener Installed: New Incoming TCP Connection : \n"
                        + tcp_conn.toString());

    }

}
