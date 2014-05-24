/**
 ** File: GenericServerFactory.java
 **
 ** Description : GenericServerFactory class - Generic Server Factory
 **
 ** Date           Author                          Comments
 ** 08/31/2013     Prakash Easwar                  Created  
 **/
package com.goffersoft.common.net;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.InetAddress;
import java.util.Iterator;
import java.util.LinkedList;

import org.apache.log4j.Logger;

//@formatter:off
public abstract class GenericServerFactory< 
                    ServerSocketType, 
                    ServerListenerType 
                        extends 
                        GenericConnectionListener, 
                    ServerType 
                        extends 
                        GenericConnection<ServerSocketType, ServerListenerType>,
                    ServerContextType 
                        extends 
                        GenericServerContext<ServerListenerType>,
                    ConnectionListenerType
                        extends 
                        GenericConnectionListener,
                    ConnectionContextType
                        extends 
                        GenericConnectionContext<ConnectionListenerType>> {
//@formatter:on
    private static final Logger log = Logger
            .getLogger(GenericServerFactory.class);

    private ConnectionContextType connectionContext;
    private ServerContextType serverContext;

    private static LinkedList<String> factoryProviderList;
    public static final String FACTORY_LIST_PROP_KEY;
    static {
        FACTORY_LIST_PROP_KEY =
                "com.goffersoft.common.net.serverFactoryList";
        factoryProviderList = new LinkedList<String>();
        factoryProviderList.add(TcpServerFactory.class.getName());
        factoryProviderList.add(TcpSSLServerFactory.class.getName());

        String props;
        String[] propsList = null;
        if ((props = System.getProperty(FACTORY_LIST_PROP_KEY)) != null) {
            propsList = props.split(",");
            if (propsList != null) {
                for (int i = 0; i < propsList.length; i++) {
                    factoryProviderList.add(propsList[i]);
                }
            }
        }
    };

    protected GenericServerFactory(
            ServerContextType serverContext,
            ConnectionContextType connectionContext) {
        this.setServerContext(serverContext);
        this.setConnectionContext(connectionContext);
    }

    public ServerContextType getServerContext() {
        return serverContext;
    }

    public void setServerContext(ServerContextType serverContext) {
        this.serverContext = serverContext;
    }

    public ConnectionContextType
            getConnectionContext() {
        return connectionContext;
    }

    public void
            setConnectionContext(
                    ConnectionContextType connectionContext) {
        this.connectionContext =
                connectionContext;
    }

    public abstract ServerType
            createServer(
                    int local_port,
                    InetAddress local_addr) throws IOException;

    public abstract ServerType createServer(ServerSocketType socket)
            throws IOException;

    public static GenericServerFactory<?, ?, ?, ?, ?, ?> getFactory(
            String provider)
            throws ClassNotFoundException,
            NoSuchMethodException,
            InvocationTargetException,
            IllegalAccessException,
            InstantiationException {
        Iterator<String> it = factoryProviderList.iterator();

        String tmpProvider;
        while (it.hasNext()) {
            tmpProvider = it.next();
            if (provider.equals(tmpProvider) == true) {
                Class<?> clazz = Class.forName(provider);
                return (GenericServerFactory<?, ?, ?, ?, ?, ?>) clazz
                        .getConstructor()
                        .newInstance();
            }
        }

        return null;
    }

    @Override
    public String toString() {
        StringBuffer str = new StringBuffer();
        str.append(getServerContext().toString());
        str.append("\n" + getConnectionContext().toString());
        str.append("\n **** Use Property Key"
                + FACTORY_LIST_PROP_KEY
                + "(properties separated by a comma) to + "
                + "define additional factories ****");

        return str.toString();
    }

    public boolean equals(
            GenericServerFactory<
            ServerSocketType,
            ServerListenerType,
            ServerType,
            ServerContextType,
            ConnectionListenerType,
            ConnectionContextType> fac) {
        return (getServerContext() == fac.getServerContext() && getConnectionContext() == fac
                .getConnectionContext());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o instanceof GenericServerFactory<?, ?, ?, ?, ?, ?>) {
            return equals((GenericServerFactory<?, ?, ?, ?, ?, ?>) o);
        }
        return false;
    }

}
