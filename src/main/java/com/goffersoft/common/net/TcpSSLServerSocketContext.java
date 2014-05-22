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

public class TcpSSLServerSocketContext
        extends
        SocketSSLServerContext<TcpSSLServerListener> {
    public TcpSSLServerSocketContext() {

    }
}
