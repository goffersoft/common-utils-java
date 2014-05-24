/**
 ** File: TcpServer.java
 **
 ** Description : TcpServer class - code to create a TCP Server
 **
 ** Date           Author                          Comments
 ** 08/31/2013     Prakash Easwar                  Created  
 **/
package com.goffersoft.common.net;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.Iterator;
import java.util.LinkedList;

import org.apache.log4j.Logger;

/**
 * TcpServer implements a TCP server waiting for incoming connection.
 */
//@formatter:off
public class TcpServer
        extends
        GenericTcpServer<
            ServerSocket, 
            Socket, 
            TcpServer, 
            TcpServerListener, 
            TcpConnection, 
            TcpConnectionListener>
         implements TcpConnectionListener {
//@formatter:on
    private static final Logger log = Logger.getLogger(TcpServer.class);
    /**
     * if there are no listeners installed send all new connections to this
     * listener
     */
    static {
        setFactoryDefaultListener(new TcpServerListenerImpl());
    }

    /**
     * Constructs a new TcpServer
     * 
     * @throws IOException
     */

    public TcpServer(
            ServerSocket socket,
            int backlog,
            int sotimeout,
            long inactivity_time,
            TcpServerListener defaultServerListener,
            LinkedList<TcpServerListener> listOfServerListeners,
            TcpConnectionContext connectionContext,
            boolean startOnInit)
            throws IOException {
        init(socket,
                backlog,
                sotimeout,
                inactivity_time,
                defaultServerListener,
                listOfServerListeners,
                connectionContext,
                startOnInit);
    }

    public TcpServer(
            int local_port,
            InetAddress local_addr,
            int backlog,
            int sotimeout,
            long inactivity_time,
            TcpServerListener defaultServerListener,
            LinkedList<TcpServerListener> listOfServerListeners,
            TcpConnectionContext connectionContext,
            boolean startOnInit)
            throws IOException {
        ServerSocket sock = new ServerSocket(local_port, backlog, local_addr);
        init(sock,
                backlog,
                sotimeout,
                inactivity_time,
                defaultServerListener,
                listOfServerListeners,
                connectionContext,
                startOnInit);
    }

    public TcpServer(
            ServerSocket socket,
            int backlog,
            int sotimeout,
            long inactivity_time,
            TcpServerListener defaultServerListener,
            LinkedList<TcpServerListener> listOfServerListeners,
            TcpConnectionListener defaultConnectionListener,
            LinkedList<GenericConnectionMap.ListenerInfo<TcpConnectionListener>>
            listOfConnectionListeners,
            int tcpConnDefault_sotimeout,
            int tcpConnDefault_rxBufferSize,
            long tcpConnDefault_inactivityTime,
            int tcpConnDefault_minRxPacketLength,
            int tcpConnDefault_maxRxPacketLength,
            boolean startOnInit)
            throws IOException {
        init(socket,
                backlog,
                sotimeout,
                inactivity_time,
                defaultServerListener,
                listOfServerListeners,
                defaultConnectionListener,
                listOfConnectionListeners,
                tcpConnDefault_sotimeout,
                tcpConnDefault_rxBufferSize,
                tcpConnDefault_inactivityTime,
                tcpConnDefault_minRxPacketLength,
                tcpConnDefault_maxRxPacketLength,
                startOnInit);
    }

    /**
     * Constructs a new TcpServer
     * 
     * @throws IOException
     */
    public TcpServer(
            int local_port,
            InetAddress local_addr,
            int backlog,
            int sotimeout,
            long inactivity_time,
            TcpServerListener defaultServerListener,
            LinkedList<TcpServerListener> listOfServerListeners,
            TcpConnectionListener defaultConnectionListener,
            LinkedList<GenericConnectionMap.ListenerInfo<TcpConnectionListener>>
            listOfConnectionListeners,
            int tcpConnDefault_sotimeout,
            int tcpConnDefault_rxBufferSize,
            long tcpConnDefault_inactivityTime,
            int tcpConnDefault_minRxPacketLength,
            int tcpConnDefault_maxRxPacketLength,
            boolean startOnInit)
            throws IOException {
        ServerSocket sock = new ServerSocket(local_port, backlog, local_addr);
        init(sock,
                backlog,
                sotimeout,
                inactivity_time,
                defaultServerListener,
                listOfServerListeners,
                defaultConnectionListener,
                listOfConnectionListeners,
                tcpConnDefault_sotimeout,
                tcpConnDefault_rxBufferSize,
                tcpConnDefault_inactivityTime,
                tcpConnDefault_minRxPacketLength,
                tcpConnDefault_maxRxPacketLength,
                startOnInit);
    }

    /** Inits the Tcp Server Connection */
    protected void init(
            ServerSocket sock,
            int backlog,
            int sotimeout,
            long inactivity_time,
            TcpServerListener defaultServerListener,
            LinkedList<TcpServerListener> listOfServerListeners,
            TcpConnectionContext connectionContext,
            boolean startOnInit)
            throws IOException {
        if (connectionContext == null) {
            init(sock,
                    backlog,
                    sotimeout,
                    inactivity_time,
                    defaultServerListener,
                    listOfServerListeners,
                    null,
                    null,
                    DEFAULT_SOCKET_TIMEOUT,
                    DEFAULT_RX_BUFFER_SIZE,
                    DEFAULT_INACTIVITY_TIMEOUT_VALUE,
                    DEFAULT_MINIMUM_RX_PACKET_LENGTH,
                    DEFAULT_MAXIMUM_RX_PACKET_LENGTH,
                    startOnInit);
        } else {
            setConnectionContext(connectionContext);
            TcpConnectionFactory connectionFactory =
                    new TcpConnectionFactory(getConnectionContext());
            setConnectionFactory(connectionFactory);
            setSocket(sock);

            setLocalPortInternal(sock.getLocalPort());
            setLocalAddressInternal(sock.getInetAddress());

            setSoTimeout(sotimeout);
            setBacklogInternal(backlog);
            setInactivityTime(inactivity_time);

            setDefaultListener(defaultServerListener);

            if (listOfServerListeners != null) {
                Iterator<TcpServerListener> it =
                        listOfServerListeners.iterator();
                while (it.hasNext()) {
                    TcpServerListener listener = it.next();
                    addListener(
                            Integer.toString(listener.hashCode()).getBytes(),
                            listener,
                            SearchType.NONE);
                }
            }
        }

        if (startOnInit == true) {
            start();
        }
    }

    /** Inits the Tcp Server Connection */
    protected
            void
            init(
                    ServerSocket sock,
                    int backlog,
                    int sotimeout,
                    long inactivity_time,
                    TcpServerListener defaultServerListener,
                    LinkedList<TcpServerListener> listOfServerListeners,
                    TcpConnectionListener defaultConnectionListener,
                    LinkedList<GenericConnectionMap.ListenerInfo<TcpConnectionListener>>
                    listOfConnectionListeners,
                    int tcpConnDefault_sotimeout,
                    int tcpConnDefault_rxBufferSize,
                    long tcpConnDefault_inactivityTime,
                    int tcpConnDefault_minRxPacketLength,
                    int tcpConnDefault_maxRxPacketLength,
                    boolean startOnInit)
                    throws IOException {
        try {
            TcpConnectionContext connectionContext = new TcpConnectionContext();
            connectionContext =
                    (TcpConnectionContext) GenericConnectionContext
                            .getContext(TcpConnectionContext.class
                                    .getName());
            connectionContext.clearAutoStart();
            connectionContext.setDefaultListener(defaultConnectionListener);
            connectionContext.setListOfListeners(listOfConnectionListeners);
            connectionContext.setSocketTimeout(tcpConnDefault_sotimeout);
            connectionContext.setRxBufferSize(tcpConnDefault_rxBufferSize);
            connectionContext
                    .setInactivityTimeout(tcpConnDefault_inactivityTime);
            connectionContext
                    .setMinRxPktLength(tcpConnDefault_minRxPacketLength);
            connectionContext
                    .setMaxRxPktLength(tcpConnDefault_maxRxPacketLength);
            init(sock,
                    backlog,
                    sotimeout,
                    inactivity_time,
                    defaultServerListener,
                    listOfServerListeners,
                    connectionContext,
                    startOnInit);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public InetSocketAddress getLocalSocketAddress() {
        return new InetSocketAddress(getSocket().getInetAddress(), getSocket()
                .getLocalPort());
    }

    @Override
    public InetAddress getLocalIpAddress() {
        return getSocket().getInetAddress();
    }

    @Override
    public int getLocalPort() {
        return getSocket().getLocalPort();
    }

    public boolean setLocalSocketAddress(InetSocketAddress sa, int backlog)
            throws IOException {
        ServerSocket tmpSocket;

        if (sa == null) {
            return false;
        }

        if (getSocket() != null) {
            if ((sa.getAddress() != null)
                    && (getSocket().getInetAddress() != null)) {
                if ((sa.getPort() == getSocket().getLocalPort())
                        && sa.getAddress().equals(getSocket().getInetAddress())) {
                    if (backlog == getBacklog()) {
                        if (isConfigChanged() == false)
                            return true;
                    }
                }
            } else if ((sa.getAddress() == null)
                    && (getSocket().getInetAddress() == null)) {
                if (sa.getPort() == getSocket().getLocalPort()) {
                    if (backlog == getBacklog()) {
                        if (isConfigChanged() == false)
                            return true;
                    }
                }
            }
        }
        setBacklogInternal(backlog);
        clearConfigChangedFlag();
        InetSocketAddress old_sa = null;
        if (isRunning()) {
            old_sa = new InetSocketAddress(getSocket().getInetAddress(),
                    getSocket().getLocalPort());

            stop();
        }

        try {

            tmpSocket = new ServerSocket(sa.getPort(), getBacklog(),
                    sa.getAddress());
            setSocket(tmpSocket);
            start();
            setLocalPortInternal(sa.getPort());
            setLocalAddressInternal(sa.getAddress());
            clearConfigChangedFlag();
            return true;
        } catch (SocketException e) {
            if (old_sa != null) {
                tmpSocket = new ServerSocket(old_sa.getPort(), getBacklog(),
                        old_sa.getAddress());
                setSocket(tmpSocket);
                start();
            } else {
                throw e;
            }
            return false;
        }
    }

    @Override
    public void run() {
        Socket socket;
        Exception error = null;
        long expire = 0;
        boolean intr = false;

        if (getSocket() == null) {
            try {
                setSocket(new ServerSocket(getLocalPortInternal(),
                        getBacklog(), getLocalAddressInternal()));
                clearConfigChangedFlag();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        setIsRunningFlag();
        try {
            // loop
            while (isStarted()) {
                try {
                    if (intr == false) {
                        if (getInactivityTime() > 0) {
                            expire = System.currentTimeMillis()
                                    + getInactivityTime();
                        }
                    }
                    getSocket().setSoTimeout(getSoTimeout());
                    socket = getSocket().accept();
                    onIncomingConnection(this, socket);
                } catch (SocketTimeoutException ie) {

                    if (getInactivityTime() > 0
                            && System.currentTimeMillis() > expire) {

                        stop();
                    }
                    intr = true;
                    continue;
                }

            }
        } catch (Exception e) {
            error = e;
            error.printStackTrace();
            stop();
        }
        onServerTerminated(this, error);
        clearIsRunningFlag();
    }

    private boolean recurse_flag = false;

    /** Gets a String representation of the Object */
    @Override
    public synchronized String toString() {
        StringBuffer sb = new StringBuffer("TCP Server:"
                + getSocket().getLocalSocketAddress().toString()
                + "\n" + super.toString());

        if (recurse_flag == false) {
            Iterator<TcpConnection> it = getTcpConnectionListIterator();
            sb.append("\nList Of Open TCP Connections : \n");
            int i = 1;
            recurse_flag = true;
            while (it.hasNext()) {
                sb.append("[TCP-Connection " + i + "]" + it.next().toString()
                        + "\n\n");
                i++;
            }
            recurse_flag = false;
        }
        return sb.toString();
    }

    @Override
    protected void onIncomingConnection(TcpServer tcp_server, Socket socket) {
        try {
            TcpConnection tcpConn =
                    (TcpConnection) getConnectionFactory().createConnection(
                            socket);
            getTcpConnectionList().add(tcpConn);
            tcpConn.addListener(
                    Integer.toString(hashCode()).getBytes(),
                    this,
                    SearchType.NONE);
            tcpConn.start();
            onIncomingConnection(tcp_server, tcpConn);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void socketClose() throws IOException {
        getSocket().close();
    }
}