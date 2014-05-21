/**
 ** File: TcpSSLServerListenerImpl.java
 **
 ** Description : TcpSSLServerListenerImpl class - implements TcpSSLServerListener
 **               factory default listener installed by Tcp SSL server in case no listeners
 **               are installed
 **
 ** Date           Author                          Comments
 ** 08/30/2013     Prakash Easwar                  Created  
 **/
package com.goffersoft.common.net;

import org.apache.log4j.Logger;

public class TcpSSLServerListenerImpl
        extends
        GenericConnectionListenerImpl
        implements
        TcpSSLServerListener {

    private static final Logger log = Logger
            .getLogger(TcpSSLServerListenerImpl.class);

    @Override
    public void onServerTerminated(TcpSSLServer tcp_server, Exception error) {

        log
                .info("Factory Default Listener Installed : TCP(SSL) Server Terminated ");
        if (error != null) {
            error.printStackTrace();
        }
    }

    @Override
    public void onIncomingConnection(
            TcpSSLServer tcp_server,
            TcpSSLConnection tcp_conn) {

        log
                .info("Factory Default Listener Installed: New Incoming TCP(SSL) Connection : \n"
                        + tcp_conn.toString());

    }

}
