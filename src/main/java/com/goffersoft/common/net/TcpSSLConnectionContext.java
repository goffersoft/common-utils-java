/**
 ** File: TcpSSLConnectionContext.java
 **
 ** Description : Context for all TCP SSL Connections 
 ** 
 **
 ** Date           Author                          Comments
 ** 08/30/2013     Prakash Easwar                  Created  
 **/
package com.goffersoft.common.net;

import javax.net.ssl.SSLSocketFactory;

public class TcpSSLConnectionContext
        extends
        GenericConnectionContext<TcpSSLConnectionListener> {
    private SSLSocketFactory sslFactory;

    public TcpSSLConnectionContext() {
        setSSLFactory(null);
    }

    public TcpSSLConnectionContext(SSLSocketFactory sslFactory) {
        setSSLFactory(sslFactory);
    }

    public SSLSocketFactory getSSLFactory() {
        return sslFactory;
    }

    public void setSSLFactory(SSLSocketFactory sslFactory) {
        this.sslFactory = sslFactory;
    }
}
