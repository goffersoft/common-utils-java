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

import org.apache.log4j.Logger;

//@formatter:off
public abstract class GenericConnectionFactory < 
                                       SocketType, 
                                       ListenerType 
                                           extends 
                                           GenericConnectionListener, 
                                       ConnectionType 
                                           extends 
                                           GenericConnection<SocketType, ListenerType>,
                                       SocketContextType 
                                           extends 
                                           GenericConnectionContext<ListenerType>> {
//@formatter:on
    private static final Logger log = Logger
            .getLogger(GenericConnectionFactory.class);

    private SocketContextType connectionContext;

    protected GenericConnectionFactory(
            SocketContextType socketContext) {
        this.connectionContext =
                socketContext;
    }

    public SocketContextType
            getSocketContext() {
        return connectionContext;
    }

    public void
            setSocketContext(
                    SocketContextType connectionContext) {
        this.connectionContext =
                connectionContext;
    }

    public abstract ConnectionType
            createConnection(
                    int remote_port);

    public abstract ConnectionType
            createConnection(
                    int local_port,
                    int remote_port);

    public abstract ConnectionType
            createConnection(
                    InetAddress remote_addr);

    public abstract ConnectionType
            createConnection(
                    int remote_port,
                    InetAddress remote_addr);

    public abstract ConnectionType createConnection(SocketType socket);

}