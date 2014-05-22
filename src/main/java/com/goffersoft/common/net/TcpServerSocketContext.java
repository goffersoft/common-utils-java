/**
 ** File: TcpServerSocketContext.java
 **
 ** Description : TcpServerSocketContext used by the 
 ** SocketFactory to instantiate new sockets of type TcpServer
 **
 ** Date           Author                          Comments
 ** 08/30/2013     Prakash Easwar                  Created  
 **/
package com.goffersoft.common.net;

public class TcpServerSocketContext
        extends
        GenericServerContext<TcpServerListener> {
    public TcpServerSocketContext() {

    }
}
