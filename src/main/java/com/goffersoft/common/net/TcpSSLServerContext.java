/**
 ** File: TcpSSLServerSocketContext.java
 **
 ** Description : TcpSSLServerSocketContext used by the 
 ** SocketFactory to instantiate new sockets of type TcpSSLServer
 **
 ** Date           Author                          Comments
 ** 08/30/2013     Prakash Easwar                  Created  
 **/
package com.goffersoft.common.net;

import org.apache.log4j.Logger;

public class TcpSSLServerContext
        extends
        GenericSSLServerContext<TcpSSLServerListener> {
    private static final Logger log = Logger
            .getLogger(TcpSSLServerContext.class);

    public TcpSSLServerContext() {

    }
}
