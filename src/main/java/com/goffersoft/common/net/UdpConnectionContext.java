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

import org.apache.log4j.Logger;

public class UdpConnectionContext
        extends
        GenericConnectionContext<UdpConnectionListener> {
    private static final Logger log = Logger
            .getLogger(UdpConnectionContext.class);

    public UdpConnectionContext() {

    }
}
