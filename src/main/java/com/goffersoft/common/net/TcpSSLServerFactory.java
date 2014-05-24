/**
 ** File: TcpSSLServerFactory.java
 **
 ** Description : TcpSSLServerFactory class - Tcp SSL Server Factory
 **
 ** Date           Author                          Comments
 ** 08/31/2013     Prakash Easwar                  Created  
 **/
package com.goffersoft.common.net;

import java.io.IOException;
import java.net.InetAddress;

import javax.net.ssl.SSLServerSocket;

//@formatter:off
public class TcpSSLServerFactory
  extends
  GenericServerFactory<
      SSLServerSocket,
      TcpSSLServerListener,
      TcpSSLServer,
      TcpSSLServerContext,
      TcpSSLConnectionListener,
      TcpSSLConnectionContext> {
//@formatter:on

    public TcpSSLServerFactory() {
        super(null, null);
    }

    public TcpSSLServerFactory(
            TcpSSLServerContext serverContext,
            TcpSSLConnectionContext connectionContext) {
        super(serverContext, connectionContext);
    }

    @Override
    public TcpSSLServer createServer(int local_port, InetAddress local_addr)
            throws IOException {
        return new TcpSSLServer(
                local_port,
                local_addr,
                getServerContext().getSSLFactory(),
                getServerContext().getBacklog(),
                getServerContext().getSocketTimeout(),
                getServerContext().getInactivityTimeout(),
                getServerContext().getDefaultListener(),
                getServerContext().getListOfListeners(),
                getConnectionContext(),
                getServerContext().isAutoStart());

    }

    @Override
    public TcpSSLServer createServer(SSLServerSocket socket) throws IOException {
        return new TcpSSLServer(
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
