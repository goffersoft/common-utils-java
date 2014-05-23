package com.goffersoft.common.net;

import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

import org.apache.log4j.Logger;

//@formatter:off
public class UdpConnectionFactory
        extends
        GenericConnectionFactory<
                         DatagramSocket,
                         UdpConnectionListener,
                         UdpConnection,
                         UdpConnectionContext> {
//@formatter:on

    private static final Logger log = Logger
            .getLogger(UdpConnectionFactory.class);

    public UdpConnectionFactory(UdpConnectionContext socketContext) {
        super(socketContext);
    }

    @Override
    public UdpConnection createConnection(int local_port)
            throws SocketException {
        return createConnection(local_port, null);
    }

    @Override
    public UdpConnection createConnection(int local_port, int remote_port)
            throws SocketException {
        return createConnection(local_port, null);
    }

    @Override
    public UdpConnection createConnection(InetAddress local_addr)
            throws SocketException {
        return createConnection(0, local_addr);
    }

    @Override
    public UdpConnection createConnection(
            InetAddress local_addr,
            InetAddress remote_addr) throws SocketException {
        return createConnection(0, local_addr);
    }

    @Override
    public UdpConnection createConnection(
            int local_port,
            InetAddress local_addr) throws SocketException {

        return new UdpConnection(local_port, local_addr,
                getContext().getSocketTimeout(),
                getContext().getRxBufferSize(),
                getContext().getInactivityTimeout(),
                getContext().getMinRxPktLength(),
                getContext().getMaxRxPktLength(),
                getContext().getListOfListeners(),
                getContext().getDefaultListener(),
                getContext().isAutoStart());
    }

    @Override
    public UdpConnection createConnection(
            int local_port,
            InetAddress local_addr,
            int remote_port,
            InetAddress remote_addr) throws SocketException {
        return createConnection(local_port, local_addr);
    }

    @Override
    public UdpConnection createConnection(DatagramSocket socket)
            throws SocketException {
        return new UdpConnection(socket,
                getContext().getSocketTimeout(),
                getContext().getRxBufferSize(),
                getContext().getInactivityTimeout(),
                getContext().getMinRxPktLength(),
                getContext().getMaxRxPktLength(),
                getContext().getListOfListeners(),
                getContext().getDefaultListener(),
                getContext().isAutoStart());
    }
}
