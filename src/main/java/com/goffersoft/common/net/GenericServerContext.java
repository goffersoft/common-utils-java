/**
 ** File: GenericServerContext.java
 **
 ** Description : Generic Context For all Servers
 **
 ** Date           Author                          Comments
 ** 08/30/2013     Prakash Easwar                  Created  
 **/
package com.goffersoft.common.net;

import java.lang.reflect.InvocationTargetException;
import java.util.Iterator;
import java.util.LinkedList;

import org.apache.log4j.Logger;

import com.goffersoft.common.utils.PrintUtils;

public class GenericServerContext< ListenerType extends GenericConnectionListener >
        extends
        GenericServerConnectionContext<ListenerType> {
    private static final Logger log = Logger
            .getLogger(GenericServerContext.class);
    private int backlog;
    private LinkedList<ListenerType> listOfListeners =
            null;

    public static final String PROVIDER_LIST_PROP_KEY;
    private static LinkedList<String> contextProviderList;
    static {
        PROVIDER_LIST_PROP_KEY =
                "com.goffersoft.common.net.serverProviderList";
        getContextProviderList().add(TcpServerContext.class.getName());
        getContextProviderList().add(TcpSSLServerContext.class.getName());

        String props;
        String[] propsList = null;
        if ((props = System.getProperty(PROVIDER_LIST_PROP_KEY)) != null) {
            propsList = props.split(",");
            if (propsList != null) {
                for (int i = 0; i < propsList.length; i++) {
                    getContextProviderList().add(propsList[i]);
                }
            }
        }
    };

    protected static void setContextProviderList(LinkedList<String> cplist) {
        contextProviderList = cplist;
    }

    protected static LinkedList<String> getContextProviderList() {
        if (contextProviderList == null) {
            contextProviderList = new LinkedList<String>();
        }
        return contextProviderList;
    }

    public int getBacklog() {
        return backlog;
    }

    public void setBacklog(int backlog) {
        this.backlog = backlog;
    }

    public LinkedList<ListenerType> getListOfListeners() {
        return listOfListeners;
    }

    public void setListOfListeners(LinkedList<ListenerType> listOfListeners) {
        this.listOfListeners = listOfListeners;
    }

    public boolean addListener(ListenerType listener) {
        if (listener == null) {
            return false;
        }

        if (listOfListeners == null) {
            listOfListeners = new LinkedList<ListenerType>();
            return listOfListeners.add(listener);
        }

        Iterator<ListenerType> it = listOfListeners.iterator();

        while (it.hasNext()) {
            if (it.next() == listener) {
                return true;
            }
        }

        return listOfListeners.add(listener);
    }

    public boolean removeListener(ListenerType listener) {
        if (listener == null) {
            return false;
        }

        if (listOfListeners == null) {
            return true;
        }

        Iterator<ListenerType> it = listOfListeners.iterator();
        int i = 0;
        while (it.hasNext()) {
            if (it.next() == listener) {
                break;
            }
            i++;
        }

        listOfListeners.remove(i);

        return true;
    }

    public static GenericServerContext<?> getContext(
            String provider)
            throws ClassNotFoundException,
            NoSuchMethodException,
            InvocationTargetException,
            IllegalAccessException,
            InstantiationException {
        Iterator<String> it = getContextProviderList().iterator();

        String tmpProvider;
        while (it.hasNext()) {
            tmpProvider = it.next();
            if (provider.equals(tmpProvider) == true) {
                Class<?> clazz = Class.forName(provider);
                return (GenericServerContext<?>) clazz
                        .getConstructor()
                        .newInstance();
            }
        }

        return null;
    }

    @Override
    public String toString() {
        StringBuffer str = new StringBuffer();

        str.append(super.toString() + " Server Backlog=" + backlog);

        if (getListOfListeners() == null) {
            return str.toString();
        }

        if (listOfListeners != null) {
            Iterator<ListenerType> it =
                    getListOfListeners().iterator();

            ListenerType listener;

            int i = 1;
            while (it.hasNext()) {
                listener = it.next();
                byte[] hashStr =
                        Integer.toString(listener.hashCode()).getBytes();
                str.append("["
                        + i
                        + "]"
                        + "Pattern: "
                        + PrintUtils.byteArrayToHexString(
                                hashStr,
                                0,
                                hashStr.length,
                                16) + " SearchType: "
                        + GenericConnectionMap.SearchType.NONE
                        + " Listener: " + listener.toString() + "\n");
                i++;
            }
        }

        if (getContextProviderList() != null) {
            Iterator<String> it = getContextProviderList().iterator();

            String prov;
            int i = 1;
            while (it.hasNext()) {
                prov = it.next();
                str.append("["
                        + i
                        + "]"
                        + "Context Provider: "
                        + prov + "\n");
                i++;
            }
            str
                    .append("\n **** Use Property Key"
                            + PROVIDER_LIST_PROP_KEY
                            + "(properties separated by a comma) to define additional providers ****");
        }

        return str.toString();
    }

    public boolean equals(GenericServerContext<?> o) {
        if (getBacklog() == o.getBacklog() &&
                (getListOfListeners().equals(o.getListOfListeners()))) {
            return super.equals(o);
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
