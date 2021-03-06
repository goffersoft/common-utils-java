/**
 ** File: TcpConnectionFactory.java
 **
 ** Description : TcpConnectionFactory class - Tcp Connection Factory
 **
 ** Date           Author                          Comments
 ** 08/31/2013     Prakash Easwar                  Created  
 **/
package com.goffersoft.common.net;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;

//@formatter:off
public class TcpConnectionFactory
        extends
        GenericConnectionFactory<
            Socket,
            TcpConnectionListener,
            TcpConnection,
            TcpConnectionContext> {
//@formatter:on

    public TcpConnectionFactory() {
        super(null);
    }

    public TcpConnectionFactory(TcpConnectionContext socketContext) {
        super(socketContext);
    }

    @Override
    public TcpConnection createConnection(int local_port)
            throws SocketException {
        throw new UnsupportedOperationException();
    }

    @Override
    public TcpConnection
            createConnection(int local_port,
                    InetAddress local_addr)
                    throws SocketException {
        throw new UnsupportedOperationException();
    }

    @Override
    public TcpConnection createConnection(
            int local_port,
            InetAddress local_addr,
            int remote_port,
            InetAddress remote_addr) throws IOException {
        return new TcpConnection(
                remote_port,
                remote_addr,
                local_port,
                local_addr,
                getContext().getSocketTimeout(),
                getContext().getRxBufferSize(),
                getContext().getInactivityTimeout(),
                getContext().getMinRxPktLength(),
                getContext().getMaxRxPktLength(),
                getContext().getDefaultListener(),
                getContext().getListOfListeners(),
                getContext().isAutoStart());
    }

    @Override
    public TcpConnection createConnection(Socket socket) throws SocketException {
        return new TcpConnection(socket,
                getContext().getSocketTimeout(),
                getContext().getRxBufferSize(),
                getContext().getInactivityTimeout(),
                getContext().getMinRxPktLength(),
                getContext().getMaxRxPktLength(),
                getContext().getDefaultListener(),
                getContext().getListOfListeners(),
                getContext().isAutoStart());
    }
}
