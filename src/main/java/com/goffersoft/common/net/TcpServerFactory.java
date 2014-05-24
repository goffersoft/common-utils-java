package com.goffersoft.common.net;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;

//@formatter:off
public class TcpServerFactory
    extends
    GenericServerFactory<
        ServerSocket,
        TcpServerListener,
        TcpServer,
        TcpServerContext,
        TcpConnectionListener,
        TcpConnectionContext> {
//@formatter:on

    public TcpServerFactory(
            TcpServerContext serverContext,
            TcpConnectionContext connectionContext) {
        super(serverContext, connectionContext);
    }

    @Override
    public TcpServer createServer(int local_port, InetAddress local_addr)
            throws IOException {
        return new TcpServer(
                local_port,
                local_addr,
                getServerContext().getBacklog(),
                getServerContext().getSocketTimeout(),
                getServerContext().getInactivityTimeout(),
                getServerContext().getDefaultListener(),
                getServerContext().getListOfListeners(),
                getConnectionContext(),
                getServerContext().isAutoStart());

    }

    @Override
    public TcpServer createServer(ServerSocket socket) throws IOException {
        return new TcpServer(
                socket,
                getServerContext().getBacklog(),
                getServerContext().getSocketTimeout(),
                getServerContext().getInactivityTimeout(),
                getServerContext().getDefaultListener(),
                getServerContext().getListOfListeners(),
                getConnectionContext(),
                getServerContext().isAutoStart());
    }
}
