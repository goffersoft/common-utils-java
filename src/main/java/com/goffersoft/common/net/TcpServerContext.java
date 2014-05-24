/**
 ** File: TcpServerContext.java
 **
 ** Description : Context for all TCP servers 
 ** 
 **
 ** Date           Author                          Comments
 ** 08/30/2013     Prakash Easwar                  Created  
 **/
package com.goffersoft.common.net;

import org.apache.log4j.Logger;

public class TcpServerContext
        extends
        GenericServerContext<TcpServerListener> {
    private static final Logger log = Logger
            .getLogger(TcpServerContext.class);

    public TcpServerContext() {

    }
}
