/**
 ** File: TcpSSLServerListener.java
 **
 ** Description : TcpSSLServerListener interface - listener interface
 **               implement this class and register with tcp SSL server
 **
 ** Date           Author                          Comments
 ** 08/30/2013     Prakash Easwar                  Created  
 **/
package com.goffersoft.common.net;

public interface TcpSSLServerListener
        extends
        GenericTcpServerListener<TcpSSLServer, TcpSSLConnection> {
}
