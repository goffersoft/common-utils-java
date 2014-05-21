/**
 ** File: UdpConnectionSocketContext.java
 **
 ** Description : UdpConnectionSocketContext used by the 
 ** SocketFactory to instantiate new sockets of type UdpConnection
 **
 ** Date           Author                          Comments
 ** 08/30/2013     Prakash Easwar                  Created  
 **/
package com.goffersoft.common.net;

public class UdpConnectionSocketContext
        extends
        SocketContext<UdpConnectionListener> {
    public UdpConnectionSocketContext() {

    }
}
