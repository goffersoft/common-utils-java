/**
 ** File: SocketContext.java
 **
 ** Description : SocketContext used by the SocketFactory to instantiate new sockets
 **
 ** Date           Author                          Comments
 ** 08/30/2013     Prakash Easwar                  Created  
 **/
package com.goffersoft.common.net;

import java.lang.reflect.InvocationTargetException;
import java.util.Iterator;
import java.util.LinkedList;

import org.apache.log4j.Logger;

import com.goffersoft.common.net.GenericConnectionMap.ListenerInfo;
import com.goffersoft.common.net.GenericConnectionMap.SearchType;
import com.goffersoft.common.utils.PatternUtils;
import com.goffersoft.common.utils.PrintUtils;

public class GenericConnectionContext< ListenerType extends GenericConnectionListener > {
    private static final Logger log = Logger
            .getLogger(GenericConnectionContext.class);

    private int rxBufferSize = GenericConnection.DEFAULT_RX_BUFFER_SIZE;
    private long inactivityTimeout =
            GenericConnection.DEFAULT_INACTIVITY_TIMEOUT_VALUE;
    private int socketTimeout = GenericConnection.DEFAULT_SOCKET_TIMEOUT;
    private int minRxPktLength =
            GenericConnection.DEFAULT_MINIMUM_RX_PACKET_LENGTH;
    private int maxRxPktLength =
            GenericConnection.DEFAULT_MAXIMUM_RX_PACKET_LENGTH;
    private ListenerType defaultListener = null;
    private LinkedList<GenericConnectionMap.ListenerInfo<ListenerType>> listOfListeners =
            null;

    private static LinkedList<String> contextProviderList;
    public static final String PROVIDER_LIST_PROP_KEY;
    static {
        PROVIDER_LIST_PROP_KEY = "com.goffersoft.common.net.socketProviderList";
        contextProviderList = new LinkedList<String>();
        contextProviderList.add(TcpConnectionSocketContext.class.getName());
        contextProviderList.add(TcpSSLConnectionSocketContext.class.getName());
        contextProviderList.add(UdpConnectionSocketContext.class.getName());
        contextProviderList.add(TcpServerSocketContext.class.getName());
        contextProviderList.add(TcpSSLServerSocketContext.class.getName());

        String props;
        String[] propsList = null;
        if ((props = System.getProperty(PROVIDER_LIST_PROP_KEY)) != null) {
            propsList = props.split(",");
            if (propsList != null) {
                for (int i = 0; i < propsList.length; i++) {
                    contextProviderList.add(propsList[i]);
                }
            }
        }
    };

    public int getRxBufferSize() {
        return rxBufferSize;
    }

    public void setRxBufferSize(int rxBufferSize) {
        this.rxBufferSize = rxBufferSize;
    }

    public long getInactivityTimeout() {
        return inactivityTimeout;
    }

    public void setInactivityTimeout(long inactivityTimeout) {
        this.inactivityTimeout = inactivityTimeout;
    }

    public int getSocketTimeout() {
        return socketTimeout;
    }

    public void setSocketTimeout(int socketTimeout) {
        this.socketTimeout = socketTimeout;
    }

    public int getMinRxPktLength() {
        return minRxPktLength;
    }

    public void setMinRxPktLength(int minRxPktLength) {
        this.minRxPktLength = minRxPktLength;
    }

    public int getMaxRxPktLength() {
        return maxRxPktLength;
    }

    public void setMaxRxPktLength(int maxRxPktLength) {
        this.maxRxPktLength = maxRxPktLength;
    }

    public ListenerType getDefaultListener() {
        return defaultListener;
    }

    public void setDefaultListener(ListenerType defaultListener) {
        this.defaultListener = defaultListener;
    }

    public LinkedList<GenericConnectionMap.ListenerInfo<ListenerType>>
            getListOfListeners() {
        return listOfListeners;
    }

    public
            void
            setListOfListeners(
                    LinkedList<GenericConnectionMap.ListenerInfo<ListenerType>> listOfListeners) {
        this.listOfListeners = listOfListeners;
    }

    public boolean addListener(
            GenericConnectionMap.ListenerInfo<ListenerType> listenerInfo) {
        if (listenerInfo == null || listenerInfo.getPattern() == null
                || listenerInfo.getPattern().length == 0
                || listenerInfo.getListener() == null) {
            return false;
        }

        if (getListOfListeners() == null) {
            listOfListeners =
                    new LinkedList<GenericConnectionMap.ListenerInfo<ListenerType>>();
        }

        Iterator<GenericConnectionMap.ListenerInfo<ListenerType>> it =
                listOfListeners.iterator();
        int i = 0;
        while (it.hasNext()) {
            GenericConnectionMap.ListenerInfo<ListenerType> tmpListenerInfo =
                    it.next();
            if (listenerInfo == tmpListenerInfo) {
                return true;
            } else if (listenerInfo.getPattern() == tmpListenerInfo
                    .getPattern()) {
                listOfListeners.remove(i);
            } else if (listenerInfo.getPattern().length == tmpListenerInfo
                    .getPattern().length
                    && PatternUtils.byteCompare(
                            listenerInfo.getPattern(),
                            0,
                            tmpListenerInfo.getPattern(),
                            0,
                            tmpListenerInfo.getPattern().length)) {
                listOfListeners.remove(i);
                break;
            }
            i++;
        }
        return listOfListeners.add(listenerInfo);
    }

    public boolean addListener(
            byte[] pattern,
            ListenerType listener,
            GenericConnectionMap.SearchType searchType) {

        return addListener(new GenericConnectionMap.ListenerInfo<ListenerType>(
                pattern,
                listener,
                searchType));
    }

    public boolean addListener(
            String pattern,
            ListenerType listener,
            GenericConnectionMap.SearchType searchType) {
        if (pattern == null) {
            return false;
        }
        return addListener(new GenericConnectionMap.ListenerInfo<ListenerType>(
                pattern.getBytes(),
                listener,
                searchType));
    }

    public boolean removeListener(
            GenericConnectionMap.ListenerInfo<ListenerType> listenerInfo) {
        if (listenerInfo == null || listenerInfo.getPattern() == null
                || listenerInfo.getPattern().length == 0) {
            return true;
        }

        Iterator<GenericConnectionMap.ListenerInfo<ListenerType>> it =
                listOfListeners.iterator();

        int i = 0;
        while (it.hasNext()) {
            GenericConnectionMap.ListenerInfo<ListenerType> tmpListenerInfo =
                    it.next();
            if (listenerInfo == tmpListenerInfo) {
                listOfListeners.remove(i);
                break;
            } else if (listenerInfo.getPattern() == tmpListenerInfo
                    .getPattern()) {
                listOfListeners.remove(i);
                break;
            } else if (listenerInfo.getPattern().length == tmpListenerInfo
                    .getPattern().length
                    && PatternUtils.byteCompare(
                            listenerInfo.getPattern(),
                            0,
                            tmpListenerInfo.getPattern(),
                            0,
                            tmpListenerInfo.getPattern().length)) {
                listOfListeners.remove(i);
                break;
            }
            i++;
        }
        return true;
    }

    public boolean removeListener(byte[] pattern) {
        return removeListener(new GenericConnectionMap.ListenerInfo<ListenerType>(
                pattern,
                null,
                SearchType.NONE));
    }

    public boolean removeListener(String pattern) {
        if (pattern == null) {
            return false;
        }
        return removeListener(new GenericConnectionMap.ListenerInfo<ListenerType>(
                pattern.getBytes(),
                null,
                SearchType.NONE));
    }

    public boolean isListenerPresent(
            GenericConnectionMap.ListenerInfo<ListenerType> listenerInfo) {
        if (listenerInfo == null || listenerInfo.getPattern() == null
                || listenerInfo.getPattern().length == 0) {
            return false;
        }

        Iterator<GenericConnectionMap.ListenerInfo<ListenerType>> it =
                listOfListeners.iterator();

        int i = 0;
        while (it.hasNext()) {
            GenericConnectionMap.ListenerInfo<ListenerType> tmpListenerInfo =
                    it.next();
            if (listenerInfo == tmpListenerInfo) {
                listOfListeners.remove(i);
                return true;
            } else if (listenerInfo.getPattern() == tmpListenerInfo
                    .getPattern()) {
                return true;
            } else if (listenerInfo.getPattern().length == tmpListenerInfo
                    .getPattern().length
                    && PatternUtils.byteCompare(
                            listenerInfo.getPattern(),
                            0,
                            tmpListenerInfo.getPattern(),
                            0,
                            tmpListenerInfo.getPattern().length)) {
                return true;
            }
            i++;
        }
        return false;
    }

    public boolean isListenerPresent(byte[] pattern) {
        return isListenerPresent(new GenericConnectionMap.ListenerInfo<ListenerType>(
                pattern,
                null,
                SearchType.NONE));
    }

    public boolean isListenerPresent(String pattern) {
        if (pattern == null) {
            return false;
        }
        return isListenerPresent(new GenericConnectionMap.ListenerInfo<ListenerType>(
                pattern.getBytes(),
                null,
                SearchType.NONE));
    }

    @Override
    public String toString() {
        StringBuffer str = new StringBuffer();
        str
                .append(String
                        .format(
                                "RxBufferSize=%d, MinRxPktLength=%d, MaxRxPktLength=%d, SocketTimeout=%d, InactivityTimeout=%d\n",
                                getRxBufferSize(),
                                getMinRxPktLength(),
                                getMaxRxPktLength(),
                                getSocketTimeout(),
                                getInactivityTimeout()));

        Iterator<ListenerInfo<ListenerType>> it =
                getListOfListeners().iterator();

        ListenerInfo<ListenerType> info;
        int i = 1;
        while (it.hasNext()) {
            info = it.next();
            str.append("["
                    + i
                    + "]"
                    + "Pattern: "
                    + PrintUtils.byteArrayToHexString(
                            info.getPattern(),
                            0,
                            info.getPattern().length,
                            16) + " SearchType: " + info.getType().toString()
                    + " Listener: " + info.getListener().toString() + "\n");
            i++;
        }

        str
                .append("\n **** Use Property Key"
                        + PROVIDER_LIST_PROP_KEY
                        + "(properties separated by a comma) to define additional providers ****");

        return str.toString();
    }

    public boolean equals(GenericConnectionContext<ListenerType> ctxt) {
        return (getDefaultListener() == ctxt.getDefaultListener()
                && getInactivityTimeout() == ctxt.getInactivityTimeout()
                && getListOfListeners() == ctxt.getListOfListeners()
                && getMaxRxPktLength() == ctxt.getMaxRxPktLength()
                && getMinRxPktLength() == ctxt.getMinRxPktLength()
                && getRxBufferSize() == ctxt.getRxBufferSize() && getSocketTimeout() == ctxt
                    .getSocketTimeout());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o instanceof GenericConnectionContext<?>) {
            return equals((GenericConnectionContext<?>) o);
        }
        return false;
    }

    public static GenericConnectionContext<?> getSocketContext(String provider)
            throws ClassNotFoundException,
            NoSuchMethodException,
            InvocationTargetException,
            IllegalAccessException,
            InstantiationException {
        Iterator<String> it = contextProviderList.iterator();

        String tmpProvider;
        while (it.hasNext()) {
            tmpProvider = it.next();
            if (provider.equals(tmpProvider) == true) {
                Class<?> clazz = Class.forName(provider);
                return (GenericConnectionContext<?>) clazz
                        .getConstructor()
                        .newInstance();
            }
        }

        return null;
    }
}
