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
import java.util.LinkedList;

import javax.net.ssl.HandshakeCompletedEvent;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

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
            TcpSSLConnectionListener,
            TcpSSLConnectionContext,
            TcpSSLConnectionFactory>
        implements
        TcpSSLServerListener,
        TcpSSLConnectionListener {
//@formatter:on
    private static final Logger log = Logger.getLogger(TcpSSLServer.class);
    private SSLServerSocketFactory sslServerFactory;

    /**
     * if there are no listeners installed
     * send all new connections to this listener
     */
    static {
        setFactoryDefaultListener(new TcpSSLServerListenerImpl());
    }

    /**
     * Constructs a new TcpSSLServer
     * 
     * @throws IOException
     */
    public TcpSSLServer(
            int local_port,
            InetAddress local_addr,
            SSLServerSocketFactory sslServerFactory,
            int backlog,
            int sotimeout,
            long inactivity_time,
            TcpSSLServerListener defaultServerListener,
            LinkedList<TcpSSLServerListener> listOfServerListeners,
            TcpSSLConnectionListener defaultConnectionListener,
            LinkedList<GenericConnectionMap.ListenerInfo<TcpSSLConnectionListener>>
            listOfConnectionListeners,
            SSLSocketFactory sslSocketFactory,
            int tcpConnDefault_sotimeout,
            int tcpConnDefault_rxBufferSize,
            long tcpConnDefault_inactivityTime,
            int tcpConnDefault_minRxPacketLength,
            int tcpConnDefault_maxRxPacketLength,
            boolean startOnInit) throws IOException {
        if (sslServerFactory == null) {
            setSSLServerFactory((SSLServerSocketFactory) SSLServerSocketFactory
                    .getDefault());
        } else {
            setSSLServerFactory(sslServerFactory);
        }
        SSLServerSocket sock =
                (SSLServerSocket) getSSLServerFactory().createServerSocket(
                        local_port,
                        backlog,
                        local_addr);

        init(sock,
                backlog,
                sotimeout,
                inactivity_time,
                defaultServerListener,
                listOfServerListeners,
                defaultConnectionListener,
                listOfConnectionListeners,
                sslSocketFactory,
                tcpConnDefault_sotimeout,
                tcpConnDefault_rxBufferSize,
                tcpConnDefault_inactivityTime,
                tcpConnDefault_minRxPacketLength,
                tcpConnDefault_maxRxPacketLength,
                startOnInit);

    }

    /** Inits the Tcp Server Connection */
    public TcpSSLServer(
            int local_port,
            InetAddress local_addr,
            SSLServerSocketFactory sslServerFactory,
            int backlog,
            int sotimeout,
            long inactivity_time,
            TcpSSLServerListener defaultServerListener,
            LinkedList<TcpSSLServerListener> listOfServerListeners,
            TcpSSLConnectionContext connectionContext,
            boolean startOnInit)
            throws IOException {
        if (sslServerFactory == null) {
            setSSLServerFactory((SSLServerSocketFactory) SSLServerSocketFactory
                    .getDefault());
        } else {
            setSSLServerFactory(sslServerFactory);
        }
        SSLServerSocket sock =
                (SSLServerSocket) getSSLServerFactory().createServerSocket(
                        local_port,
                        backlog,
                        local_addr);
        init(sock,
                backlog,
                sotimeout,
                inactivity_time,
                defaultServerListener,
                listOfServerListeners,
                connectionContext,
                startOnInit);
    }

    public TcpSSLServer(
            SSLServerSocket socket,
            int backlog,
            int sotimeout,
            long inactivity_time,
            TcpSSLServerListener defaultServerListener,
            LinkedList<TcpSSLServerListener> listOfServerListeners,
            TcpSSLConnectionContext connectionContext,
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

    public TcpSSLServer(
            SSLServerSocket socket,
            int backlog,
            int sotimeout,
            long inactivity_time,
            TcpSSLServerListener defaultServerListener,
            LinkedList<TcpSSLServerListener> listOfServerListeners,
            TcpSSLConnectionListener defaultConnectionListener,
            LinkedList<GenericConnectionMap.ListenerInfo<TcpSSLConnectionListener>>
            listOfConnectionListeners,
            SSLSocketFactory sslSocketFactory,
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
                sslSocketFactory,
                tcpConnDefault_sotimeout,
                tcpConnDefault_rxBufferSize,
                tcpConnDefault_inactivityTime,
                tcpConnDefault_minRxPacketLength,
                tcpConnDefault_maxRxPacketLength,
                startOnInit);
    }

    /** Inits the Tcp Server Connection */
    protected void init(
            SSLServerSocket sock,
            int backlog,
            int sotimeout,
            long inactivity_time,
            TcpSSLServerListener defaultServerListener,
            LinkedList<TcpSSLServerListener> listOfServerListeners,
            TcpSSLConnectionContext connectionContext,
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
                    null,
                    DEFAULT_SOCKET_TIMEOUT,
                    DEFAULT_RX_BUFFER_SIZE,
                    DEFAULT_INACTIVITY_TIMEOUT_VALUE,
                    DEFAULT_MINIMUM_RX_PACKET_LENGTH,
                    DEFAULT_MAXIMUM_RX_PACKET_LENGTH,
                    startOnInit);
        } else {
            setConnectionContext(connectionContext);
            TcpSSLConnectionFactory connectionFactory =
                    new TcpSSLConnectionFactory(getConnectionContext());
            setConnectionFactory(connectionFactory);
            setSocket(sock);

            setLocalPortInternal(sock.getLocalPort());
            setLocalAddressInternal(sock.getInetAddress());

            setSoTimeout(sotimeout);
            setBacklogInternal(backlog);
            setInactivityTime(inactivity_time);

            setDefaultListener(defaultServerListener);

            if (listOfServerListeners != null) {
                Iterator<TcpSSLServerListener> it =
                        listOfServerListeners.iterator();
                while (it.hasNext()) {
                    TcpSSLServerListener listener = it.next();
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
                    SSLServerSocket sock,
                    int backlog,
                    int sotimeout,
                    long inactivity_time,
                    TcpSSLServerListener defaultServerListener,
                    LinkedList<TcpSSLServerListener> listOfServerListeners,
                    TcpSSLConnectionListener defaultConnectionListener,
                    LinkedList<GenericConnectionMap.ListenerInfo<TcpSSLConnectionListener>>
                    listOfConnectionListeners,
                    SSLSocketFactory sslSocketFactory,
                    int tcpConnDefault_sotimeout,
                    int tcpConnDefault_rxBufferSize,
                    long tcpConnDefault_inactivityTime,
                    int tcpConnDefault_minRxPacketLength,
                    int tcpConnDefault_maxRxPacketLength,
                    boolean startOnInit)
                    throws IOException {
        try {
            TcpSSLConnectionContext connectionContext =
                    new TcpSSLConnectionContext(sslSocketFactory);
            connectionContext =
                    (TcpSSLConnectionContext) GenericConnectionContext
                            .getContext(TcpSSLConnectionContext.class
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

    public SSLServerSocketFactory getSSLServerFactory() {
        return sslServerFactory;
    }

    public void setSSLServerFactory(SSLServerSocketFactory sslServerFactory) {
        this.sslServerFactory = sslServerFactory;
    }

    @Override
    public boolean setLocalSocketAddress(InetSocketAddress sa, int backlog)
            throws IOException {
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
            stop();
        }

        try {
            tmpSocket =
                    (SSLServerSocket) getSSLServerFactory().
                            createServerSocket(
                                    getLocalPortInternal(),
                                    getBacklog(),
                                    getLocalAddressInternal());
            setSocket(tmpSocket);
            start();
            setLocalPortInternal(sa.getPort());
            setLocalAddressInternal(sa.getAddress());
            clearConfigChangedFlag();
            return true;
        } catch (SocketException e) {
            if (old_sa != null) {
                tmpSocket =
                        (SSLServerSocket) getSSLServerFactory().
                                createServerSocket(
                                        old_sa.getPort(),
                                        getBacklog(),
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

                SSLServerSocket sock =
                        (SSLServerSocket) getSSLServerFactory().
                                createServerSocket(
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
        try {
            TcpSSLConnection tcpConn =
                    getConnectionFactory().createConnection(socket);
            getTcpConnectionList().add(tcpConn);
            tcpConn.addListener(
                    Integer.toString(hashCode()).getBytes(),
                    this,
                    SearchType.NONE);
            onIncomingConnection(tcp_server, tcpConn);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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
