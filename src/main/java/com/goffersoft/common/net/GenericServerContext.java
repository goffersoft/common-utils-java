/**
 ** File: SocketServerContext.java
 **
 ** Description : SocketServerContext used by the SocketFactory to instantiate new sockets
 **
 ** Date           Author                          Comments
 ** 08/30/2013     Prakash Easwar                  Created  
 **/
package com.goffersoft.common.net;

public class GenericServerContext< ListenerType extends GenericConnectionListener >
        extends
        GenericConnectionContext<ListenerType> {
    private int backlog;

    public int getBacklog() {
        return backlog;
    }

    public void setBacklog(int backlog) {
        this.backlog = backlog;
    }

    @Override
    public String toString() {
        return super.toString() + "\n Server Backlog=" + backlog;
    }

    public boolean equals(GenericServerContext<?> o) {
        if (getBacklog() == o.getBacklog()) {
            return super.equals((GenericConnectionContext<?>) o);
        }
        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o instanceof GenericServerContext<?>) {
            return equals((GenericServerContext<?>) o);
        }
        return false;
    }
}
