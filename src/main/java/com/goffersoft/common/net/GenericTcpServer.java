/**
 ** File: GenericTcpServer.java
 **
 ** Description : GenericTcpServer class - code common to all TCP Server types
 **
 ** Date           Author                          Comments
 ** 08/31/2013     Prakash Easwar                  Created  
 **/
package com.goffersoft.common.net;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;

abstract public class GenericTcpServer<TcpServerSocketType, TcpConnectionSocketType, TcpServerType extends GenericTcpServer<TcpServerSocketType, TcpConnectionSocketType, TcpServerType, TcpServerListenerType, TcpConnectionType, TcpConnectionListenerType>, TcpServerListenerType extends GenericTcpServerListener<TcpServerType, TcpConnectionType>, TcpConnectionType extends GenericTcpConnection<TcpConnectionSocketType, TcpConnectionType, TcpConnectionListenerType>, TcpConnectionListenerType extends GenericTcpConnectionListener<TcpConnectionType>>

        extends
        GenericConnection<TcpServerSocketType, TcpServerListenerType>
        implements
        GenericTcpServerListener<TcpServerType, TcpConnectionType>,
        GenericTcpConnectionListener<TcpConnectionType> {

    private static final Logger log = Logger.getLogger(GenericTcpServer.class);

    static final public int DEFAULT_SERVER_SOCKET_BACKLOG = 50;

    volatile private List<TcpConnectionType> tcpConnectionList;
    volatile private TcpConnectionListenerType defaultTcpConnectionListener;

    volatile private int local_port;

    volatile private InetAddress local_addr;

    /** Default ServerSocket backlog value */
    volatile private int socket_backlog;

    volatile private boolean force_update = false;

    /** TCP Connection properties */
    volatile private int tcpconn_sotimeout = DEFAULT_SOCKET_TIMEOUT;
    volatile private long tcpconn_inactivity_time =
            DEFAULT_INACTIVITY_TIMEOUT_VALUE;
    volatile private int tcpconn_rx_buffer_size = DEFAULT_RX_BUFFER_SIZE;
    volatile private int tcpconn_min_rx_pkt_size =
            DEFAULT_MINIMUM_RX_PACKET_LENGTH;
    volatile private int tcpconn_max_rx_pkt_size =
            DEFAULT_MAXIMUM_RX_PACKET_LENGTH;

    protected GenericTcpServer() {
        super(null, null);
        tcpConnectionList = new LinkedList<TcpConnectionType>();
    }

    protected GenericTcpServer(TcpServerSocketType s, Thread t) {
        super(s, t);
    }

    protected Iterator<TcpConnectionType> getTcpConnectionListIterator() {
        return tcpConnectionList.iterator();
    }

    protected List<TcpConnectionType> getTcpConnectionList() {
        return tcpConnectionList;
    }

    protected void setTcpConnectionList(
            List<TcpConnectionType> tcpConnectionList) {
        this.tcpConnectionList = tcpConnectionList;
    }

    protected TcpConnectionType getNextTcpConnectionListEntry(
            Iterator<TcpConnectionType> it) {
        if (it == null) {
            return null;
        }
        if (it.hasNext() == false) {
            return null;
        }
        return it.next();
    }

    protected void setConfigChangedFlag() {
        force_update = true;
    }

    protected void clearConfigChangedFlag() {
        force_update = false;
    }

    protected boolean isConfigChanged() {
        return force_update;
    }

    protected int getLocalPortInternal() {
        return local_port;
    }

    protected void setLocalPortInternal(int local_port) {
        this.local_port = local_port;
    }

    protected InetAddress getLocalAddressInternal() {
        return local_addr;
    }

    protected void setLocalAddressInternal(InetAddress local_addr) {
        this.local_addr = local_addr;
    }

    public int getDefaultTcpConnectionSoTimeout() {
        return tcpconn_sotimeout;
    }

    public int getDefaultTcpConnectionRxBufferSize() {
        return tcpconn_rx_buffer_size;
    }

    public long getDefaultTcpConnectionInactivityTime() {
        return tcpconn_inactivity_time;
    }

    public int getDefaultTcpConnectionMinRxPacketLength() {
        return tcpconn_min_rx_pkt_size;
    }

    public int getDefaultTcpConnectionMaxRxPacketLength() {
        return tcpconn_max_rx_pkt_size;
    }

    public void setDefaultTcpConnectionSoTimeout(int sotimeout_in_miliseconds) {
        tcpconn_sotimeout = sotimeout_in_miliseconds;
    }

    public void setDefaultTcpConnectionRxBufferSize(int rxbuffersize) {
        tcpconn_rx_buffer_size = rxbuffersize;
    }

    public void setDefaultTcpConnectionInactivityTime(
            long inactivity_time_in_milliseconds) {
        tcpconn_inactivity_time = inactivity_time_in_milliseconds;
    }

    public void setDefaultTcpConnectionMinRxPacketLength(int min_rx_pktlen) {
        tcpconn_min_rx_pkt_size = min_rx_pktlen;
    }

    public void setDefaultTcpConnectionMaxRxPacketLength(int max_rx_pktlen) {
        tcpconn_max_rx_pkt_size = max_rx_pktlen;
    }

    public TcpConnectionListenerType getDefaultTcpConnectionListener() {
        return defaultTcpConnectionListener;
    }

    public void setDefaultTcpConnectionListener(
            TcpConnectionListenerType tcpConnectionDefaultListener) {
        this.defaultTcpConnectionListener = defaultTcpConnectionListener;
    }

    protected final void setBacklogInternal(int backlog) {
        socket_backlog = backlog;
    }

    public void setBacklog(int backlog) throws IOException {
        setLocalSocketAddress(
                new InetSocketAddress(local_addr, local_port),
                backlog);
    }

    public void setBacklog(int backlog, boolean restart_flag)
            throws IOException {
        if (restart_flag == true)
            setLocalSocketAddress(
                    new InetSocketAddress(local_addr, local_port),
                    backlog);
        else {
            setBacklogInternal(backlog);
            force_update = true;
        }
    }

    public int getBacklog() {
        return socket_backlog;
    }

    public boolean setLocalSocketAddress(InetSocketAddress sa)
            throws IOException {
        return setLocalSocketAddress(sa, getBacklog());
    }

    public boolean setLocalSocketAddress(InetAddress ipaddr, int port)
            throws IOException {
        return setLocalSocketAddress(
                new InetSocketAddress(ipaddr, port),
                getBacklog());
    }

    public boolean setLocalSocketAddress(
            InetAddress ipaddr,
            int port,
            int backlog) throws IOException {
        return setLocalSocketAddress(
                new InetSocketAddress(ipaddr, port),
                backlog);
    }

    @Override
    public void onReceivedData(
            TcpConnectionType tcp,
            byte[] data,
            int offset,
            int length) {
    }

    @Override
    public void onConnectionTerminated(TcpConnectionType tcp, Exception error) {
        getTcpConnectionList().remove(tcp);
    }

    @Override
    public void onErrorReceivedSmallPacket(
            TcpConnectionType tcp,
            byte[] data,
            int offset,
            int length) {

    }

    @Override
    public void onErrorReceivedLargePacket(
            TcpConnectionType tcp,
            byte[] data,
            int offset,
            int length) {

    }

    @Override
    public void onServerTerminated(TcpServerType tcp_server, Exception error) {
        Iterator<TcpConnectionType> it_conn = getTcpConnectionListIterator();

        while (it_conn.hasNext()) {
            TcpConnectionType tcpConn = it_conn.next();
            if (tcpConn != null) {
                tcpConn.stop();
            }
        }

        Iterator<ListenerInfo<TcpServerListenerType>> it =
                getConnectionMapListIterator();

        ListenerInfo<TcpServerListenerType> info;
        while ((info = getNextConnectionMapListEntry(it)) != null) {
            info.getListener().onServerTerminated(tcp_server, error);
        }
        getDefaultListener().onServerTerminated(tcp_server, error);
    }

    @Override
    public void onIncomingConnection(
            TcpServerType tcp_server,
            TcpConnectionType tcp_conn) {
        Iterator<ListenerInfo<TcpServerListenerType>> it =
                getConnectionMapListIterator();

        ListenerInfo<TcpServerListenerType> info;
        while ((info = getNextConnectionMapListEntry(it)) != null) {
            info.getListener().onIncomingConnection(tcp_server, tcp_conn);
        }
        getDefaultListener().onIncomingConnection(tcp_server, tcp_conn);
    }

    abstract public boolean setLocalSocketAddress(
            InetSocketAddress sa,
            int backlog) throws IOException;

    @Override
    abstract public void run();

    @Override
    abstract protected void socketClose() throws IOException;

    abstract protected void onIncomingConnection(
            TcpServerType tcp_server,
            TcpConnectionSocketType socket);

    abstract public InetSocketAddress getLocalSocketAddress();

    abstract public InetAddress getLocalIpAddress();

    abstract public int getLocalPort();
}
