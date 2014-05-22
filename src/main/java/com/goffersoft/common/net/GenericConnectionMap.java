/**
 ** File: GenericConnectionMap.java
 **
 ** Description : GenericConnectionMap class - code common for registering 
 **               listeners associated with any class
 **
 ** Date           Author                          Comments
 ** 08/31/2013     Prakash Easwar                  Created  
 **/

package com.goffersoft.common.net;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map.Entry;

import org.apache.log4j.Logger;

import com.goffersoft.common.utils.PatternUtils;

//@formatter:off
public class GenericConnectionMap<
                            ListenerType 
                                extends 
                                GenericConnectionListener> {
//@formatter:on
    static public enum SearchType {
        CONTAINS,
        STARTSWITH,
        ENDSWITH,
        NONE,
    }

    public static class ListenerInfo < LisType extends GenericConnectionListener > {
        private byte[] pattern;
        private SearchType type;
        private LisType listener;

        ListenerInfo(byte[] pattern, LisType listener, SearchType type) {
            this.pattern = pattern;
            this.type = type;
            this.listener = listener;
        }

        public SearchType getType() {
            return type;
        }

        public void setType(SearchType type) {
            this.type = type;
        }

        public LisType getListener() {
            return listener;
        }

        public void setListener(LisType listener) {
            this.listener = listener;
        }

        public byte[] getPattern() {
            return pattern;
        }

        public void setPattern(byte[] pattern) {
            this.pattern = pattern;
        }
    }

    private static final Logger log = Logger
            .getLogger(GenericConnectionMap.class);

    volatile private LinkedList<ListenerInfo<ListenerType>> connectionMapList;

    volatile private HashMap<byte[], ListenerInfo<ListenerType>> connectionMapHash;

    /**
     * if there are no listeners in the connectinMap or if a match is not found
     * send all packets to this listener
     */
    volatile private ListenerType defaultListener;

    static private GenericConnectionListener factoryDefaultListener;

    static protected < ListenerType extends GenericConnectionListener >
            void
            setFactoryDefaultListener(ListenerType listener) {
        factoryDefaultListener = listener;
    }

    @SuppressWarnings("unchecked")
    static protected < ListenerType extends GenericConnectionListener >
            ListenerType
            getFactoryDefaultListener() {
        return (ListenerType) factoryDefaultListener;
    }

    public GenericConnectionMap() {
        connectionMapList = new LinkedList<ListenerInfo<ListenerType>>();
        connectionMapHash = new HashMap<byte[], ListenerInfo<ListenerType>>();
        setDefaultListener(null);
    }

    public void addListener(
            byte[] pattern,
            ListenerType listener,
            SearchType type) {
        if (listener == this) {
            return;
        }

        if (pattern == null) {
            return;
        }

        ListenerInfo<ListenerType> info = connectionMapHash.get(pattern);

        if (info == null) {
            info = new ListenerInfo<ListenerType>(pattern, listener, type);
            connectionMapList.add(info);
            connectionMapHash.put(pattern, info);
        } else {
            info.setListener(listener);
            info.setType(type);
        }
    }

    public void removeListener(String pattern) {
        ListenerInfo<ListenerType> info = connectionMapHash.get(pattern);
        if (info != null) {
            connectionMapHash.remove(pattern);
            connectionMapList.remove(info);
        }
    }

    public void setDefaultListener(ListenerType listener) {
        if (listener == null) {
            // defaultListener = (ListenerType) factoryDefaultListener;
            defaultListener = getFactoryDefaultListener();
        } else {
            defaultListener = listener;
        }
    }

    public void removeDefaultListener() {
        defaultListener = getFactoryDefaultListener();
    }

    public ListenerType getDefaultListener() {
        return defaultListener;
    }

    protected Iterator<ListenerInfo<ListenerType>>
            getConnectionMapListIterator() {
        return connectionMapList.iterator();
    }

    protected Iterator<Entry<byte[], ListenerInfo<ListenerType>>>
            getConnectionMapHashIterator() {
        return connectionMapHash.entrySet().iterator();
    }

    protected HashMap<byte[], ListenerInfo<ListenerType>>
            getConnectionMapHash() {
        return connectionMapHash;
    }

    protected LinkedList<ListenerInfo<ListenerType>> getConnectionMapList() {
        return connectionMapList;
    }

    protected ListenerInfo<ListenerType> getNextConnectionMapHashEntry(
            Iterator<Entry<byte[], ListenerInfo<ListenerType>>> it) {
        if (it == null) {
            return null;
        }
        if (it.hasNext() == false) {
            return null;
        }
        return it.next().getValue();
    }

    protected ListenerInfo<ListenerType> getNextConnectionMapListEntry(
            Iterator<ListenerInfo<ListenerType>> it) {
        if (it == null) {
            return null;
        }
        if (it.hasNext() == false) {
            return null;
        }
        return it.next();
    }

    protected ListenerInfo<ListenerType> getConnectionMapEntry(
            byte[] data,
            int dataoffset,
            int datalength) {
        Iterator<ListenerInfo<ListenerType>> it = connectionMapList.iterator();

        if (data == null) {
            return null;
        }

        while (it.hasNext()) {
            ListenerInfo<ListenerType> entry = it.next();

            switch (entry.getType()) {
                case CONTAINS:
                    if (PatternUtils.contains(
                            data,
                            dataoffset,
                            datalength,
                            entry.getPattern()) != -1) {
                        return entry;
                    }
                    break;
                case ENDSWITH:
                    if (PatternUtils.endsWith(
                            data,
                            dataoffset,
                            datalength,
                            entry.getPattern()) != -1) {
                        return entry;
                    }
                    break;
                case STARTSWITH:
                    if (PatternUtils.startsWith(
                            data,
                            dataoffset,
                            datalength,
                            entry.getPattern()) != -1) {
                        return entry;
                    }
                    break;
                case NONE:
                    break;
            }
        }

        return null;
    }

    protected ListenerType getConnectionListener(
            byte[] data,
            int dataoffset,
            int datalength) {
        ListenerInfo<ListenerType> info =
                getConnectionMapEntry(data, dataoffset, datalength);

        if (info == null) {
            return null;
        }

        return info.getListener();
    }
}
