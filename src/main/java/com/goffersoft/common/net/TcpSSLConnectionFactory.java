/**
 ** File: TcpSSLConnectionFactory.java
 **
 ** Description : TcpSSLConnectionFactory class - Tcp SSL Connection Factory
 **
 ** Date           Author                          Comments
 ** 08/31/2013     Prakash Easwar                  Created  
 **/
package com.goffersoft.common.net;

import java.io.IOException;
import java.net.InetAddress;

import javax.net.ssl.SSLSocket;

//@formatter:off
public class TcpSSLConnectionFactory
      extends
      GenericConnectionFactory<
          SSLSocket,
          TcpSSLConnectionListener,
          TcpSSLConnection,
          TcpSSLConnectionContext> {
//@formatter:on

    public TcpSSLConnectionFactory() {
        super(null);
    }

    public TcpSSLConnectionFactory(TcpSSLConnectionContext socketContext) {
        super(socketContext);
    }

    @Override
    public TcpSSLConnection createConnection(int local_port) throws IOException {
        throw new UnsupportedOperationException();
    }

    @Override
    public TcpSSLConnection createConnection(
            int local_port,
            InetAddress local_addr) throws IOException {
        throw new UnsupportedOperationException();
    }

    @Override
    public TcpSSLConnection createConnection(
            int local_port,
            InetAddress local_addr,
            int remote_port,
            InetAddress remote_addr) throws IOException {
        return new TcpSSLConnection(
                remote_port,
                remote_addr,
                local_port,
                local_addr,
                getContext().getSSLFactory(),
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
    public TcpSSLConnection createConnection(SSLSocket socket)
            throws IOException {
        return new TcpSSLConnection(socket,
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
