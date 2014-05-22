/**
 ** File: SocketServerContext.java
 **
 ** Description : SocketServerContext used by the SocketFactory to instantiate new sockets
 **
 ** Date           Author                          Comments
 ** 08/30/2013     Prakash Easwar                  Created  
 **/
package com.goffersoft.common.net;

public class SocketServerContext<ListenerType extends GenericConnectionListener>
        extends
        SocketContext<ListenerType> {
    private int backlog;

    public int getBacklog() {
        return backlog;
    }

    public void setBacklog(int backlog) {
        this.backlog = backlog;
    }
}
