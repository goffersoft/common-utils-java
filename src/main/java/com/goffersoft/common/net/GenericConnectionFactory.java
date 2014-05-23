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
import java.net.SocketException;

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
                                       ConnectionContextType 
                                           extends 
                                           GenericConnectionContext<ListenerType>> {
//@formatter:on
    private static final Logger log = Logger
            .getLogger(GenericConnectionFactory.class);

    private ConnectionContextType connectionContext;

    protected GenericConnectionFactory(
            ConnectionContextType socketContext) {
        this.connectionContext =
                socketContext;
    }

    public ConnectionContextType
            getContext() {
        return connectionContext;
    }

    public void
            setContext(
                    ConnectionContextType connectionContext) {
        this.connectionContext =
                connectionContext;
    }

    public abstract ConnectionType
            createConnection(
                    int local_port) throws SocketException;

    public abstract ConnectionType
            createConnection(
                    int local_port,
                    int remote_port) throws SocketException;

    public abstract ConnectionType
            createConnection(
                    InetAddress local_addr) throws SocketException;

    public abstract ConnectionType
            createConnection(
                    InetAddress local_addr,
                    InetAddress remote_addr) throws SocketException;

    public abstract ConnectionType
            createConnection(
                    int local_port,
                    InetAddress local_addr) throws SocketException;

    public abstract ConnectionType
            createConnection(
                    int local_port,
                    InetAddress local_addr,
                    int remote_port,
                    InetAddress remote_addr) throws SocketException;

    public abstract ConnectionType createConnection(SocketType socket)
            throws SocketException;

}