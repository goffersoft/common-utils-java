/**
 ** File: TcpSSLConnectionSocketContext.java
 **
 ** Description : TcpSSLConnectionSocketContext used by the 
 ** SocketFactory to instantiate new sockets of type TcpSSLConnection
 **
 ** Date           Author                          Comments
 ** 08/30/2013     Prakash Easwar                  Created  
 **/
package com.goffersoft.common.net;

public class TcpSSLConnectionSocketContext
        extends
        SocketContext<TcpSSLConnectionListener> {
    public TcpSSLConnectionSocketContext() {

    }
}
