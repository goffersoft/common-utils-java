/**
 ** File: TcpServerListener.java
 **
 ** Description : TcpServerListener interface - listener interface
 **               implement this class and register with tcp server
 **
 ** Date           Author                          Comments
 ** 08/30/2013     Prakash Easwar                  Created  
 **/
package com.goffersoft.common.net;

public interface TcpServerListener
        extends
        GenericTcpServerListener<TcpServer, TcpConnection>
{
}