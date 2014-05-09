/**
 ** File: GenericConnection.java
 **
 ** Description : GenericConnection class - code common to all kinds of socket connections
 **
 ** Date           Author                          Comments
 ** 08/31/2013     Prakash Easwar                  Created  
 **/
package com.goffersoft.common.net;

import java.io.IOException;
import java.util.Iterator;

import org.apache.log4j.Logger;

import com.goffersoft.common.utils.PrintUtils;

abstract public class GenericConnection<SocketType, ListenerType extends GenericConnectionListener>
        extends GenericConnectionMap<ListenerType> implements Runnable {

    private static final Logger log = Logger.getLogger(GenericConnection.class);

    /** The default receive buffer size */
    public static final int DEFAULT_RX_BUFFER_SIZE = 65535;

    /** The default inactivity timeout value in milliseconds. 0 ==> disabled */
    public static final long DEFAULT_INACTIVITY_TIMEOUT_VALUE = 0;

    /**
     * Default value for the maximum time that the receiver can remain active
     * after been halted (in milliseconds)
     */
    public static final int DEFAULT_SOCKET_TIMEOUT = 2000; // 2sec

    /** Default value for the minimum receive packet length in bytes */
    public static final int DEFAULT_MINIMUM_RX_PACKET_LENGTH = 0;

    /** Default value for the maximum receive packet length in bytes */
    public static final int DEFAULT_MAXIMUM_RX_PACKET_LENGTH = 65535;

    /**
     * Maximum time that the receiver can remain active after been halted (in
     * milliseconds)
     */
    volatile private int socket_timeout = DEFAULT_SOCKET_TIMEOUT;

    /** the buffer size of incoming packets */
    volatile private int bufferSize = DEFAULT_RX_BUFFER_SIZE;

    /**
     * Maximum time that the receiver remains active without receiving datagrams
     * (in milliseconds)
     */
    volatile private long inactivity_time = DEFAULT_INACTIVITY_TIMEOUT_VALUE;

    /** Whether it has been halted */
    volatile private boolean start;

    /** Whether the thread is running or not */
    volatile private boolean isRunning;

    /**
     * Minimum size for received packets. Packets shorter than that are routed
     * to the onErrorReceivedSmallPacket listener method
     */
    volatile private int minimum_rx_length = DEFAULT_MINIMUM_RX_PACKET_LENGTH;

    /**
     * Maximum size for received packets Packets larger than that are routed to
     * the onErrorReceivedLargePacket listener method
     */
    volatile private int maximum_rx_length = DEFAULT_MAXIMUM_RX_PACKET_LENGTH;

    /** connection thread */
    volatile protected Thread connectionThread;

    /** socket */
    volatile protected SocketType connectionSocket;

    protected GenericConnection() {
        super();
        connectionThread = null;
        connectionSocket = null;
    }

    protected GenericConnection(SocketType s, Thread t) {
        super();
        connectionSocket = s;
        connectionThread = t;
    }

    /** Gets the buffer size */
    public int getReceiveBufferSize() {
        return bufferSize;
    }

    /** Sets the receive buffer size */
    public void setReceiveBufferSize(int bufSize) {
        bufferSize = bufSize;
    }

    /** Gets the socket timeout in milliseconds */
    public int getSoTimeout() {
        return socket_timeout;
    }

    /** Sets the socket timeout in milliseconds */
    public void setSoTimeout(int timeout) {
        socket_timeout = timeout;
    }

    /** Gets Server Inactivity time in milliseconds before initiating a shutdown */
    public long getInactivityTime() {
        return inactivity_time;
    }

    /** Sets Server Inactivity time in milliseconds before initiating a shutdown */
    public void setInactivityTime(long itimeinms) {
        inactivity_time = itimeinms;
    }

    /** Whether the service is running */
    public boolean isRunning() {
        return isRunning;
    }

    /** Whether the service has been started */
    public boolean isStarted() {
        return start;
    }

    /**
     * Sets the minimum size for received packets. Packets shorter than that are
     * routed to the onErrorReceivedSmallPacket listener method
     */
    public void setMinimumReceivePacketLength(int len) {
        minimum_rx_length = len;
    }

    /**
     * Gets the minimum size for received packets. Packets shorter than that are
     * routed to the onErrorReceivedSmallPacket listener method
     */
    public int getMinimumReceivePacketLength() {
        return minimum_rx_length;
    }

    /**
     * Sets the maximum size for received packets. Packets larger than that are
     * routed to the onErrorReceivedLargePacket listener method
     */
    public void setMaximumReceivePacketLength(int len) {
        maximum_rx_length = len;
    }

    /**
     * Sets the maximum size for received packets. Packets larger than that are
     * routed to the onErrorReceivedLargePacket listener method
     */
    public int getMaximumReceivePacketLength() {
        return maximum_rx_length;
    }

    /** Stops running */
    public void stop() {
        start = false;

        if (connectionThread != Thread.currentThread()) {
            while (isRunning()) {
                try {
                    connectionThread.join();
                } catch (InterruptedException e) {
                }
            }
        }
        try {
            socketClose();
        } catch (IOException e) {
            e.printStackTrace();
        }
        connectionSocket = null;
        connectionThread = null;
    }

    /** starts running */
    public void start() {
        if (start == false) {
            connectionThread = new Thread(this);
            connectionThread.setName(connectionThread.getClass().getName()
                    + ".T"
                    + connectionThread.getName().replaceAll("Thread-", ""));
            connectionThread.start();
        }
        start = true;
    }

    public SocketType getSocket() {
        return connectionSocket;
    }

    protected void setSocket(SocketType s) {
        connectionSocket = s;
    }

    public Thread getThread() {
        return connectionThread;
    }

    protected void setThread(Thread t) {
        connectionThread = t;
    }

    protected void setIsRunningFlag() {
        isRunning = true;
    }

    protected void clearIsRunningFlag() {
        isRunning = false;
    }

    @Override
    abstract public void run();

    abstract protected void socketClose() throws IOException;

    /** Gets a String representation of the Object */
    @Override
    public String toString() {
        StringBuffer str = new StringBuffer();
        Iterator<ListenerInfo> it = getConnectionMapListIterator();

        ListenerInfo info;
        int i = 1;
        while ((info = getNextConnectionMapListEntry(it)) != null) {
            str.append("["
                    + i
                    + "]"
                    + "Pattern: "
                    + PrintUtils.byteArrayToHexString(info.getPattern(), 0,
                            info.getPattern().length, 16) + " SearchType: "
                    + info.getType().toString() + " Listener: "
                    + info.getListener().toString() + "\n");
            i++;
        }

        return str.toString();
    }
}
