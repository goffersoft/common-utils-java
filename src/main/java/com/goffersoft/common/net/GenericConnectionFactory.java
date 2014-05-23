/**
 ** File: SocketFactory.java
 **
 ** Description : Generic SocketFactory to instantiate new sockets
 **
 ** Date           Author                          Comments
 ** 08/30/2013     Prakash Easwar                  Created  
 **/
package com.goffersoft.common.net;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.InetAddress;
import java.util.Iterator;
import java.util.LinkedList;

import org.apache.log4j.Logger;

//@formatter:off
public abstract class GenericConnectionFactory < 
                                       SocketType, 
                                       ListenerType 
                                           extends 
                                           GenericConnectionListener, 
                                       ConnectionType 
                                           extends 
                                           GenericConnection<SocketType, ListenerType>,
                                       ConnectionContextType 
                                           extends 
                                           GenericConnectionContext<ListenerType>> {
//@formatter:on
    private static final Logger log = Logger
            .getLogger(GenericConnectionFactory.class);

    private ConnectionContextType connectionContext;

    private static LinkedList<String> factoryProviderList;
    public static final String FACTORY_LIST_PROP_KEY;
    static {
        FACTORY_LIST_PROP_KEY =
                "com.goffersoft.common.net.connectionFactoryList";
        factoryProviderList = new LinkedList<String>();
        factoryProviderList.add(UdpConnectionFactory.class.getName());

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

    protected GenericConnectionFactory(
            ConnectionContextType socketContext) {
        this.connectionContext =
                socketContext;
    }

    public ConnectionContextType
            getContext() {
        return connectionContext;
    }

    public void
            setContext(
                    ConnectionContextType connectionContext) {
        this.connectionContext =
                connectionContext;
    }

    public abstract ConnectionType
            createConnection(
                    int local_port) throws IOException;

    public abstract ConnectionType
            createConnection(
                    int local_port,
                    InetAddress local_addr) throws IOException;

    public abstract ConnectionType
            createConnection(
                    int local_port,
                    InetAddress local_addr,
                    int remote_port,
                    InetAddress remote_addr) throws IOException;

    public abstract ConnectionType createConnection(SocketType socket)
            throws IOException;

    public static GenericConnectionFactory<?, ?, ?, ?> getFactory(
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
                return (GenericConnectionFactory<?, ?, ?, ?>) clazz
                        .getConstructor()
                        .newInstance();
            }
        }

        return null;
    }

    @Override
    public String toString() {
        StringBuffer str = new StringBuffer();
        str.append(getContext().toString());
        str
                .append("\n **** Use Property Key"
                        + FACTORY_LIST_PROP_KEY
                        + "(properties separated by a comma) to define additional factories ****");

        return str.toString();
    }

    public boolean equals(
            GenericConnectionFactory<
            SocketType,
            ListenerType,
            ConnectionType,
            ConnectionContextType> fac) {
        return (getContext() == fac.getContext());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o instanceof GenericConnectionFactory<?, ?, ?, ?>) {
            return equals((GenericConnectionFactory<?, ?, ?, ?>) o);
        }
        return false;
    }
}