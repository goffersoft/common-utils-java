/**
 ** File: TcpSSLConnectionListener.java
 **
 ** Description : TcpSSLConnectionListener class - implements TcpSSLConnectionListener
 **               factory default listener installed by Tcp SSL connection in case no listeners
 **               are installed
 **
 ** Date           Author                          Comments
 ** 08/30/2013     Prakash Easwar                  Created  
 **/
package com.goffersoft.common.net;

import javax.net.ssl.HandshakeCompletedEvent;

public interface TcpSSLConnectionListener
        extends
        GenericTcpConnectionListener<TcpSSLConnection> {
    void onHandshakeCompleted(
            TcpSSLConnection tcp,
            HandshakeCompletedEvent event);
}
