/**
 ** File: SocketFactory.java
 **
 ** Description : Generic SocketFactory to instantiate new sockets
 **
 ** Date           Author                          Comments
 ** 08/30/2013     Prakash Easwar                  Created  
 **/
package com.goffersoft.common.net;

import java.net.InetAddress;

//@formatter:off
public abstract class GenericConnectionFactory < 
                                       SocketType, 
                                       ListenerType 
                                           extends 
                                           GenericConnectionListener, 
                                       ConnectionType 
                                           extends 
                                           GenericConnection<SocketType, ListenerType>>
//@formatter:on
{

    private SocketContext<ListenerType> socketContext;

    protected GenericConnectionFactory(
            SocketContext<ListenerType> socketContext) {
        this.socketContext =
                socketContext;
    }

    public SocketContext<ListenerType>
            getSocketContext() {
        return socketContext;
    }

    public void
            setSocketContext(
                    SocketContext<ListenerType> socketContext) {
        this.socketContext =
                socketContext;
    }

    public abstract ConnectionType
            createConnection(
                    int remote_port);

    public abstract ConnectionType
            createConnection(
                    int local_port,
                    int remote_port);

    public abstract ConnectionType
            createSocket(
                    int remote_port,
                    InetAddress remote_addr);

}
