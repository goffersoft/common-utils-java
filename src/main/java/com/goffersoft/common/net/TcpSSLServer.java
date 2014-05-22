/**
 ** File: TcpSSLServer.java
 **
 ** Description : TcpSSLServer class - code to create a SSL TCP Server
 **
 ** Date           Author                          Comments
 ** 08/31/2013     Prakash Easwar                  Created
 **/
package com.goffersoft.common.net;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.util.Iterator;

import javax.net.ssl.HandshakeCompletedEvent;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocket;

import org.apache.log4j.Logger;

//@formatter:off
public class TcpSSLServer
        extends
        GenericTcpServer<
            SSLServerSocket, 
            SSLSocket, 
            TcpSSLServer, 
            TcpSSLServerListener, 
            TcpSSLConnection, 
            TcpSSLConnectionListener>
        implements
        TcpSSLServerListener,
        TcpSSLConnectionListener {
//@formatter:on
    private static final Logger log = Logger.getLogger(TcpSSLServer.class);

    /**
     * if there are no listeners installed
     * send all new connections to this listener
     */
    static {
        setFactoryDefaultListener(new TcpSSLServerListenerImpl());
    }

    /**
     * Constructs a new TcpServer
     * 
     * @throws IOException
     */
    public TcpSSLServer(int local_port, // local port number 0 ==> system
                                        // chooses
                                        // from ephemeral port range
            InetAddress local_addr,// local inte address null ==> INADDR_ANY
            TcpSSLServerListener defaultServerListener,// default tcp server
                                                       // listener
            boolean startOnInit) throws IOException {
        init(
                local_port,
                local_addr,
                DEFAULT_SERVER_SOCKET_BACKLOG,
                DEFAULT_SOCKET_TIMEOUT,
                DEFAULT_INACTIVITY_TIMEOUT_VALUE,
                null,
                defaultServerListener,
                null,
                DEFAULT_SOCKET_TIMEOUT, // so
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
     * Constructs a new TcpSSLServer
     * 
     * @throws IOException
     */
    public TcpSSLServer(int local_port, // local port number 0 ==> system
                                        // chooses from ephemeral port range
            InetAddress local_addr, // local inte address null ==> INADDR_ANY
            int backlog, // server baclog ==> maximum quqe length for incoming
                         // connections
            int sotimeout, // server socket sotimeout value
            long inactivity_time, // inactivity timeout value in milliseconds 0
                                  // ==> disable
            TcpSSLServerListener defaultServerListener, // default tcp server
                                                        // listener
            boolean startOnInit) throws IOException {
        init(
                local_port,
                local_addr,
                backlog,
                sotimeout,
                inactivity_time,
                null,
                defaultServerListener,
                null,
                DEFAULT_SOCKET_TIMEOUT, // so
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
     * Constructs a new TcpSSLServer
     * 
     * @throws IOException
     */
    public TcpSSLServer(
            int local_port,
            InetAddress local_addr,
            int backlog,
            int sotimeout,
            long inactivity_time,
            TcpSSLServerListener serverListener,
            TcpSSLServerListener defaultServerListener,
            boolean startOnInit) throws IOException {
        init(
                local_port,
                local_addr,
                backlog,
                sotimeout,
                inactivity_time,
                serverListener,
                defaultServerListener,
                null,
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
     * Constructs a new TcpSSLServer
     * 
     * @throws IOException
     */
    public TcpSSLServer(
            int local_port,
            InetAddress local_addr,
            int backlog,
            int sotimeout,
            long inactivity_time,
            TcpSSLServerListener serverListener,
            TcpSSLServerListener defaultServerListener,
            TcpSSLConnectionListener defaultConnListener,
            boolean startOnInit) throws IOException {
        init(
                local_port,
                local_addr,
                backlog,
                sotimeout,
                inactivity_time,
                serverListener,
                defaultServerListener,
                defaultConnListener,
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
     * Constructs a new TcpSSLServer
     * 
     * @throws IOException
     */
    public TcpSSLServer(
            int local_port,
            InetAddress local_addr,
            int backlog,
            int sotimeout,
            long inactivity_time,
            TcpSSLServerListener serverListener,
            TcpSSLServerListener defaultServerListener,
            TcpSSLConnectionListener defaultConnListener,
            int tcpConnDefault_sotimeout,
            int tcpConnDefault_rxBufferSize,
            long tcpConnDefault_inactivityTime,
            int tcpConnDefault_minRxPacketLength,
            int tcpConnDefault_maxRxPacketLength,
            boolean startOnInit) throws IOException {
        init(
                local_port,
                local_addr,
                backlog,
                sotimeout,
                inactivity_time,
                serverListener,
                defaultServerListener,
                defaultConnListener,
                tcpConnDefault_sotimeout,
                tcpConnDefault_rxBufferSize,
                tcpConnDefault_inactivityTime,
                tcpConnDefault_minRxPacketLength,
                tcpConnDefault_maxRxPacketLength,
                startOnInit);
    }

    /** Inits the Tcp Server Connection */
    protected void init(
            int local_port,
            InetAddress local_addr,
            int backlog,
            int sotimeout,
            long inactivity_time,
            TcpSSLServerListener serverListener,
            TcpSSLServerListener defaultServerListener,
            TcpSSLConnectionListener defaultConnListener,
            int tcpConnDefault_sotimeout,
            int tcpConnDefault_rxBufferSize,
            long tcpConnDefault_inactivityTime,
            int tcpConnDefault_minRxPacketLength,
            int tcpConnDefault_maxRxPacketLength,
            boolean startOnInit) throws IOException {
        SSLServerSocketFactory sslserversocketfactory =
                (SSLServerSocketFactory) SSLServerSocketFactory.getDefault();
        SSLServerSocket sock =
                (SSLServerSocket) sslserversocketfactory.createServerSocket(
                        local_port,
                        backlog,
                        local_addr);
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
            addListener(
                    serverListener.toString().getBytes(),
                    serverListener,
                    SearchType.NONE);

        if (startOnInit == true) {
            start();
        }
    }

    @Override
    public boolean setLocalSocketAddress(InetSocketAddress sa, int backlog)
            throws IOException {
        boolean restart = false;
        SSLServerSocket tmpSocket;

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
            old_sa =
                    new InetSocketAddress(
                            getSocket().getInetAddress(),
                            getSocket().getLocalPort());

            restart = true;

            stop();
        }
        SSLServerSocketFactory sslserversocketfactory =
                (SSLServerSocketFactory) SSLServerSocketFactory.getDefault();
        try {
            tmpSocket =
                    (SSLServerSocket) sslserversocketfactory
                            .createServerSocket(
                                    getLocalPortInternal(),
                                    getBacklog(),
                                    getLocalAddressInternal());
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
                tmpSocket =
                        (SSLServerSocket) sslserversocketfactory
                                .createServerSocket(
                                        old_sa.getPort(),
                                        getBacklog(),
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
    public void onHandshakeCompleted(
            TcpSSLConnection tcp,
            HandshakeCompletedEvent event) {

    }

    @Override
    public void run() {
        SSLSocket socket;
        Exception error = null;
        long expire = 0;
        boolean intr = false;

        if (getSocket() == null) {
            try {
                SSLServerSocketFactory sslserversocketfactory =
                        (SSLServerSocketFactory) SSLServerSocketFactory
                                .getDefault();
                SSLServerSocket sock =
                        (SSLServerSocket) sslserversocketfactory
                                .createServerSocket(
                                        getLocalPortInternal(),
                                        getBacklog(),
                                        getLocalAddressInternal());
                setSocket(sock);
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
                            expire =
                                    System.currentTimeMillis()
                                            + getInactivityTime();
                        }
                    }
                    getSocket().setSoTimeout(getSoTimeout());
                    socket = (SSLSocket) getSocket().accept();
                } catch (InterruptedIOException ie) {
                    if (getInactivityTime() > 0
                            && System.currentTimeMillis() > expire) {

                        stop();
                    }
                    intr = true;
                    continue;
                }
                onIncomingConnection(this, socket);
            }
        } catch (Exception e) {
            error = e;
            stop();
        }
        onServerTerminated(this, error);
        clearIsRunningFlag();

    }

    @Override
    protected void socketClose() throws IOException {
        getSocket().close();
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

    @Override
    protected void onIncomingConnection(
            TcpSSLServer tcp_server,
            SSLSocket socket) {
        TcpSSLConnection tcpConn =
                new TcpSSLConnection(
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
        tcpConn.addListener(
                (new Integer(hashCode())).toString().getBytes(),
                this,
                SearchType.NONE);
        onIncomingConnection(tcp_server, tcpConn);
    }

    private boolean recurse_flag = false;

    /** Gets a String representation of the Object */
    @Override
    public String toString() {
        StringBuffer sb =
                new StringBuffer("TCP SSL Server:"
                        + getSocket().getLocalSocketAddress().toString() + ":"
                        + getSocket().getLocalPort() + "\n" + super.toString());

        if (recurse_flag == false) {
            Iterator<TcpSSLConnection> it = getTcpConnectionListIterator();
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
}
