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
    public TcpServer(int local_port, // local port number 0 ==> system chooses
                                     // from ephemeral port range
            InetAddress local_addr,// local inte address null ==> INADDR_ANY
            TcpServerListener defaultServerListener,// default tcp server
                                                    // listener
            boolean startOnInit) throws IOException {
        init(local_port, local_addr, DEFAULT_SERVER_SOCKET_BACKLOG,
                DEFAULT_SOCKET_TIMEOUT, DEFAULT_INACTIVITY_TIMEOUT_VALUE, null,
                defaultServerListener, null, DEFAULT_SOCKET_TIMEOUT, // so
                                                                     // timeout
                DEFAULT_RX_BUFFER_SIZE, // default rx buffer size
                DEFAULT_INACTIVITY_TIMEOUT_VALUE, // inactivity timeout value in
                                                  // milliseconds 0 ==> disable
                DEFAULT_MINIMUM_RX_PACKET_LENGTH, // minimum receive packet
                                                  // length
                DEFAULT_MAXIMUM_RX_PACKET_LENGTH, // maximum receive packet
                                                  // length
                startOnInit);
    }

    /**
     * Constructs a new TcpServer
     * 
     * @throws IOException
     */
    public TcpServer(int local_port, // local port number 0 ==> system chooses
                                     // from ephemeral port range
            InetAddress local_addr, // local inte address null ==> INADDR_ANY
            int backlog, // server baclog ==> maximum quqe length for incoming
                         // connections
            int sotimeout, // server socket sotimeout value
            long inactivity_time, // inactivity timeout value in milliseconds 0
                                  // ==> disable
            TcpServerListener defaultServerListener, // default tcp server
                                                     // listener
            boolean startOnInit) throws IOException {
        init(local_port, local_addr, backlog, sotimeout, inactivity_time, null,
                defaultServerListener, null, DEFAULT_SOCKET_TIMEOUT, // so
                                                                     // timeout
                DEFAULT_RX_BUFFER_SIZE, // default rx buffer size
                DEFAULT_INACTIVITY_TIMEOUT_VALUE, // inactivity timeout value in
                                                  // milliseconds 0 ==> disable
                DEFAULT_MINIMUM_RX_PACKET_LENGTH, // minimum receive packet
                                                  // length
                DEFAULT_MAXIMUM_RX_PACKET_LENGTH, // maximum receive packet
                                                  // length
                startOnInit);
    }

    /**
     * Constructs a new TcpServer
     * 
     * @throws IOException
     */
    public TcpServer(int local_port, InetAddress local_addr, int backlog,
            int sotimeout, long inactivity_time,
            TcpServerListener serverListener,
            TcpServerListener defaultServerListener, boolean startOnInit)
            throws IOException {
        init(local_port, local_addr, backlog, sotimeout, inactivity_time,
                serverListener, defaultServerListener, null,
                DEFAULT_SOCKET_TIMEOUT, // so timeout
                DEFAULT_RX_BUFFER_SIZE, // default rx buffer size
                DEFAULT_INACTIVITY_TIMEOUT_VALUE, // inactivity timeout value in
                                                  // milliseconds 0 ==> disable
                DEFAULT_MINIMUM_RX_PACKET_LENGTH, // minimum receive packet
                                                  // length
                DEFAULT_MAXIMUM_RX_PACKET_LENGTH, // maximum receive packet
                                                  // length
                startOnInit);
    }

    /**
     * Constructs a new TcpServer
     * 
     * @throws IOException
     */
    public TcpServer(int local_port, InetAddress local_addr, int backlog,
            int sotimeout, long inactivity_time,
            TcpServerListener serverListener,
            TcpServerListener defaultServerListener,
            TcpConnectionListener defaultConnListener, boolean startOnInit)
            throws IOException {
        init(local_port, local_addr, backlog, sotimeout, inactivity_time,
                serverListener, defaultServerListener, defaultConnListener,
                DEFAULT_SOCKET_TIMEOUT, // so timeout
                DEFAULT_RX_BUFFER_SIZE, // default rx buffer size
                DEFAULT_INACTIVITY_TIMEOUT_VALUE, // inactivity timeout value in
                                                  // milliseconds 0 ==> disable
                DEFAULT_MINIMUM_RX_PACKET_LENGTH, // minimum receive packet
                                                  // length
                DEFAULT_MAXIMUM_RX_PACKET_LENGTH, // maximum receive packet
                                                  // length
                startOnInit);
    }

    /**
     * Constructs a new TcpServer
     * 
     * @throws IOException
     */
    public TcpServer(int local_port, InetAddress local_addr, int backlog,
            int sotimeout, long inactivity_time,
            TcpServerListener serverListener,
            TcpServerListener defaultServerListener,
            TcpConnectionListener defaultConnListener,
            int tcpConnDefault_sotimeout, int tcpConnDefault_rxBufferSize,
            long tcpConnDefault_inactivityTime,
            int tcpConnDefault_minRxPacketLength,
            int tcpConnDefault_maxRxPacketLength, boolean startOnInit)
            throws IOException {
        init(local_port, local_addr, backlog, sotimeout, inactivity_time,
                serverListener, defaultServerListener, defaultConnListener,
                tcpConnDefault_sotimeout, tcpConnDefault_rxBufferSize,
                tcpConnDefault_inactivityTime,
                tcpConnDefault_minRxPacketLength,
                tcpConnDefault_maxRxPacketLength, startOnInit);
    }

    /** Inits the Tcp Server Connection */
    protected void init(int local_port, InetAddress local_addr, int backlog,
            int sotimeout, long inactivity_time,
            TcpServerListener serverListener,
            TcpServerListener defaultServerListener,
            TcpConnectionListener defaultConnListener,
            int tcpConnDefault_sotimeout, int tcpConnDefault_rxBufferSize,
            long tcpConnDefault_inactivityTime,
            int tcpConnDefault_minRxPacketLength,
            int tcpConnDefault_maxRxPacketLength, boolean startOnInit)
            throws IOException {
        ServerSocket sock = new ServerSocket(local_port, backlog, local_addr);
        setSocket(sock);

        setLocalPortInternal(sock.getLocalPort());
        setLocalAddressInternal(local_addr = sock.getInetAddress());

        setSoTimeout(sotimeout);
        setBacklogInternal(backlog);
        setInactivityTime(inactivity_time);

        setDefaultListener(defaultServerListener);

        setDefaultTcpConnectionListener(defaultConnListener);
        setDefaultTcpConnectionMinRxPacketLength(tcpConnDefault_minRxPacketLength);
        setDefaultTcpConnectionMaxRxPacketLength(tcpConnDefault_maxRxPacketLength);
        setDefaultTcpConnectionRxBufferSize(tcpConnDefault_rxBufferSize);
        setDefaultTcpConnectionSoTimeout(tcpConnDefault_sotimeout);
        setDefaultTcpConnectionInactivityTime(tcpConnDefault_inactivityTime);
        if (serverListener != null)
            addListener(serverListener.toString().getBytes(), serverListener,
                    SearchType.NONE);

        if (startOnInit == true) {
            start();
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
        boolean restart = false;
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

            restart = true;

            stop();
        }

        try {

            tmpSocket = new ServerSocket(sa.getPort(), getBacklog(),
                    sa.getAddress());
            setSocket(tmpSocket);
            if (restart == true) {
                start();
            }
            setLocalPortInternal(sa.getPort());
            setLocalAddressInternal(sa.getAddress());
            clearConfigChangedFlag();
            return true;
        } catch (SocketException e) {
            if (old_sa != null) {
                tmpSocket = new ServerSocket(old_sa.getPort(), getBacklog(),
                        old_sa.getAddress());
                setSocket(tmpSocket);
                if (restart == true) {
                    start();
                }
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
                + getSocket().getLocalSocketAddress().toString() + ":"
                + getSocket().getLocalPort() + "\n" + super.toString());

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
        TcpConnection tcpConn = new TcpConnection(
                socket,
                getDefaultTcpConnectionSoTimeout(),
                getDefaultTcpConnectionRxBufferSize(),
                getDefaultTcpConnectionInactivityTime(),
                getDefaultTcpConnectionMinRxPacketLength(),
                getDefaultTcpConnectionMaxRxPacketLength(),
                null, // pattern
                null, // tcp connection listener
                SearchType.NONE,
                getDefaultTcpConnectionListener(),
                true // startOnInit
                );
        getTcpConnectionList().add(tcpConn);
        tcpConn.addListener((new Integer(hashCode())).toString().getBytes(),
                this, SearchType.NONE);
        onIncomingConnection(tcp_server, tcpConn);
    }

    @Override
    protected void socketClose() throws IOException {
        getSocket().close();
    }
}