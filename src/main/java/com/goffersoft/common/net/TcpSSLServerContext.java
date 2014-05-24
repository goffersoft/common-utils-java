/**
 ** File: TcpSSLServerContext.java
 **
 ** Description : Context for all TCP SSL Servers 
 ** 
 **
 ** Date           Author                          Comments
 ** 08/30/2013     Prakash Easwar                  Created  
 **/
package com.goffersoft.common.net;

import javax.net.ssl.SSLServerSocketFactory;

import org.apache.log4j.Logger;

public class TcpSSLServerContext
        extends
        GenericServerContext<TcpSSLServerListener> {
    private static final Logger log = Logger
            .getLogger(TcpSSLServerContext.class);

    private SSLServerSocketFactory sslFactory;

    public TcpSSLServerContext() {
        setSSLFactory(null);
    }

    public TcpSSLServerContext(SSLServerSocketFactory sslFactory) {
        setSSLFactory(sslFactory);
    }

    public SSLServerSocketFactory getSSLFactory() {
        return sslFactory;
    }

    public void setSSLFactory(SSLServerSocketFactory sslFactory) {
        this.sslFactory = sslFactory;
    }
}
