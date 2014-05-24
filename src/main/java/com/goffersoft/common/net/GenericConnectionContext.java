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

public class GenericConnectionContext< ListenerType extends GenericConnectionListener >
        extends
        GenericServerConnectionContext<ListenerType> {
    private static final Logger log = Logger
            .getLogger(GenericConnectionContext.class);

    private int rxBufferSize = GenericConnection.DEFAULT_RX_BUFFER_SIZE;
    private int minRxPktLength =
            GenericConnection.DEFAULT_MINIMUM_RX_PACKET_LENGTH;
    private int maxRxPktLength =
            GenericConnection.DEFAULT_MAXIMUM_RX_PACKET_LENGTH;
    private LinkedList<ListenerInfo<ListenerType>> listOfListeners =
            null;
    public static final String PROVIDER_LIST_PROP_KEY;
    private static LinkedList<String> contextProviderList;
    static {
        PROVIDER_LIST_PROP_KEY =
                "com.goffersoft.common.net.connectionProviderList";
        getContextProviderList().add(TcpConnectionContext.class.getName());
        getContextProviderList().add(TcpSSLConnectionContext.class.getName());
        getContextProviderList().add(UdpConnectionContext.class.getName());

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

    public int getRxBufferSize() {
        return rxBufferSize;
    }

    public void setRxBufferSize(int rxBufferSize) {
        this.rxBufferSize = rxBufferSize;
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

    public LinkedList<ListenerInfo<ListenerType>>
            getListOfListeners() {
        return listOfListeners;
    }

    public void
            setListOfListeners(
                    LinkedList<ListenerInfo<ListenerType>> listOfListeners) {
        this.listOfListeners = listOfListeners;
    }

    public boolean addListener(
            ListenerInfo<ListenerType> listenerInfo) {
        if (listenerInfo == null || listenerInfo.getPattern() == null
                || listenerInfo.getPattern().length == 0
                || listenerInfo.getListener() == null) {
            return false;
        }

        if (getListOfListeners() == null) {
            listOfListeners =
                    new LinkedList<ListenerInfo<ListenerType>>();
        }

        Iterator<ListenerInfo<ListenerType>> it =
                listOfListeners.iterator();
        int i = 0;
        while (it.hasNext()) {
            ListenerInfo<ListenerType> tmpListenerInfo =
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
            SearchType searchType) {

        return addListener(new ListenerInfo<ListenerType>(
                pattern,
                listener,
                searchType));
    }

    public boolean addListener(
            String pattern,
            ListenerType listener,
            SearchType searchType) {
        if (pattern == null) {
            return false;
        }
        return addListener(new ListenerInfo<ListenerType>(
                pattern.getBytes(),
                listener,
                searchType));
    }

    public boolean removeListener(
            ListenerInfo<ListenerType> listenerInfo) {
        if (listenerInfo == null || listenerInfo.getPattern() == null
                || listenerInfo.getPattern().length == 0) {
            return true;
        }

        Iterator<ListenerInfo<ListenerType>> it =
                listOfListeners.iterator();

        int i = 0;
        while (it.hasNext()) {
            ListenerInfo<ListenerType> tmpListenerInfo =
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
        return removeListener(new ListenerInfo<ListenerType>(
                pattern,
                null,
                SearchType.NONE));
    }

    public boolean removeListener(String pattern) {
        if (pattern == null) {
            return false;
        }
        return removeListener(new ListenerInfo<ListenerType>(
                pattern.getBytes(),
                null,
                SearchType.NONE));
    }

    public boolean isListenerPresent(
            ListenerInfo<ListenerType> listenerInfo) {
        if (listenerInfo == null || listenerInfo.getPattern() == null
                || listenerInfo.getPattern().length == 0) {
            return false;
        }

        Iterator<ListenerInfo<ListenerType>> it =
                listOfListeners.iterator();

        int i = 0;
        while (it.hasNext()) {
            ListenerInfo<ListenerType> tmpListenerInfo =
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
        return isListenerPresent(new ListenerInfo<ListenerType>(
                pattern,
                null,
                SearchType.NONE));
    }

    public boolean isListenerPresent(String pattern) {
        if (pattern == null) {
            return false;
        }
        return isListenerPresent(new ListenerInfo<ListenerType>(
                pattern.getBytes(),
                null,
                SearchType.NONE));
    }

    public static GenericConnectionContext<?> getContext(
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
                return (GenericConnectionContext<?>) clazz
                        .getConstructor()
                        .newInstance();
            }
        }

        return null;
    }

    @Override
    public String toString() {
        StringBuffer str = new StringBuffer();
        str.append(super.toString());
        str.append(String.format(
                " ,RxBufferSize=%d, MinRxPktLength=%d, MaxRxPktLength=%d \n",
                getRxBufferSize(),
                getMinRxPktLength(),
                getMaxRxPktLength()
                ));

        if (getListOfListeners() == null) {
            return str.toString();
        }

        if (getListOfListeners() != null) {
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
                                16) + " SearchType: "
                        + info.getType().toString()
                        + " Listener: " + info.getListener().toString() + "\n");
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

    public boolean equals(GenericConnectionContext<ListenerType> ctxt) {
        return (getListOfListeners() == ctxt.getListOfListeners()
                && getMaxRxPktLength() == ctxt.getMaxRxPktLength()
                && getMinRxPktLength() == ctxt.getMinRxPktLength()
                && getRxBufferSize() == ctxt.getRxBufferSize()
                && getListOfListeners().equals(ctxt.getListOfListeners())
                && super.equals(ctxt));
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
}
