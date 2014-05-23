/**
 ** File: TcpConnectionSocketContext.java
 **
 ** Description : TcpConnectionSocketContext used by the 
 ** SocketFactory to instantiate new sockets of type TcpConnection
 **
 ** Date           Author                          Comments
 ** 08/30/2013     Prakash Easwar                  Created  
 **/
package com.goffersoft.common.net;

public class TcpConnectionContext
        extends
        GenericConnectionContext<TcpConnectionListener> {
    public TcpConnectionContext() {

    }
}
