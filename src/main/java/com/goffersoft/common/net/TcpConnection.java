/**
 ** File: TcpConnection.java
 **
 ** Description : TcpConnection class - code to create a TCP connection
 **
 ** Date           Author                          Comments
 ** 08/31/2013     Prakash Easwar                  Created  
 **/
package com.goffersoft.common.net;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;

import org.apache.log4j.Logger;

public class TcpConnection extends
        GenericTcpConnection<Socket, TcpConnection, TcpConnectionListener>
        implements TcpConnectionListener {

    private static final Logger log = Logger.getLogger(TcpConnection.class);

    /**
     * if there are no listeners in the connectinMap or if a match is not found
     * send all packets to this listener
     */
    static {
        setFactoryDefaultListener(new TcpConnectionListenerImpl());
    }

    /** Costructs a new TcpConnection */
    public TcpConnection(Socket socket, TcpConnectionListener defaultListener) {
        super(null, null);
        init(
                socket,
                DEFAULT_SOCKET_TIMEOUT, // so timeout
                DEFAULT_RX_BUFFER_SIZE, // default rx buffer size
                DEFAULT_INACTIVITY_TIMEOUT_VALUE, // inactivity timeout value in
                                                  // milliseconds 0 ==> disable
                DEFAULT_MINIMUM_RX_PACKET_LENGTH, // minimum receive packet
                                                  // length
                DEFAULT_MAXIMUM_RX_PACKET_LENGTH, // maximum receive packet
                                                  // length
                null, // pattern associated with the listener
                SearchType.NONE, // search type associated with this pattern
                null, // listener associated with pattern
                defaultListener, // default listener in case no match or no
                                 // listener installed
                false // immediately start server after initialization
        );
    }

    /** Costructs a new TcpConnection */
    public TcpConnection(Socket socket, long inactivity_time,
            TcpConnectionListener defaultListener) {
        super(null, null);
        init(
                socket,
                DEFAULT_SOCKET_TIMEOUT, // so timeout
                DEFAULT_RX_BUFFER_SIZE, // default rx buffer size
                inactivity_time, // inactivity timeout value in milliseconds 0
                                 // ==> disable
                DEFAULT_MINIMUM_RX_PACKET_LENGTH, // minimum receive packet
                                                  // length
                DEFAULT_MAXIMUM_RX_PACKET_LENGTH, // maximum receive packet
                                                  // length
                null, // pattern associated with the listener
                SearchType.NONE, // search type associated with this pattern
                null, // listener associated with pattern
                defaultListener, // default listener in case no match or no
                                 // listener installed
                false // immediately start server after initialization
        );
    }

    /** Costructs a new TcpConnection */
    public TcpConnection(Socket socket, TcpConnectionListener defaultListener,
            boolean startOnInit) {
        super(null, null);
        init(socket, DEFAULT_SOCKET_TIMEOUT, // so timeout
                DEFAULT_RX_BUFFER_SIZE, // default rx buffer size
                DEFAULT_INACTIVITY_TIMEOUT_VALUE, // inactivity timeout value in
                                                  // milliseconds 0 ==> disable
                DEFAULT_MINIMUM_RX_PACKET_LENGTH, // minimum receive packet
                                                  // length
                DEFAULT_MAXIMUM_RX_PACKET_LENGTH, // maximum receive packet
                                                  // length
                null, // pattern associated with the listener
                SearchType.NONE, // search type associated with this pattern
                null, // listener associated with pattern
                defaultListener, // default listener in case no match or no
                                 // listener installed
                startOnInit // immediately start server after initialization
        );
    }

    /** Costructs a new TcpConnection */
    public TcpConnection(Socket socket, long inactivity_time,
            TcpConnectionListener defaultListener, boolean startOnInit) {
        super(null, null);
        init(socket, DEFAULT_SOCKET_TIMEOUT, // so timeout
                DEFAULT_RX_BUFFER_SIZE, // default rx buffer size
                inactivity_time, // inactivity timeout value in milliseconds 0
                                 // ==> disable
                DEFAULT_MINIMUM_RX_PACKET_LENGTH, // minimum receive packet
                                                  // length
                DEFAULT_MAXIMUM_RX_PACKET_LENGTH, // maximum receive packet
                                                  // length
                null, // pattern associated with the listener
                SearchType.NONE, // search type associated with this pattern
                null, // listener associated with pattern
                defaultListener, // default listener in case no match or no
                                 // listener installed
                startOnInit // immediately start server after initialization
        );
    }

    public TcpConnection(Socket socket, byte[] pattern,
            TcpConnectionListener listener, SearchType stype) {
        super(null, null);
        init(socket, DEFAULT_SOCKET_TIMEOUT, // so timeout
                DEFAULT_RX_BUFFER_SIZE, // default rx buffer size
                DEFAULT_INACTIVITY_TIMEOUT_VALUE, // inactivity timeout value in
                                                  // milliseconds 0 ==> disable
                DEFAULT_MINIMUM_RX_PACKET_LENGTH, // minimum receive packet
                                                  // length
                DEFAULT_MAXIMUM_RX_PACKET_LENGTH, // maximum receive packet
                                                  // length
                pattern, // pattern associated with the listener
                stype, // search type associated with this pattern
                listener, // listener associated with pattern
                null, // default listener in case no match or no listener
                      // installed
                false // immediately start server after initialization
        );
    }

    public TcpConnection(Socket socket, long inactivity_time, byte[] pattern,
            TcpConnectionListener listener, SearchType stype) {
        super(null, null);
        init(socket, DEFAULT_SOCKET_TIMEOUT, // so timeout
                DEFAULT_RX_BUFFER_SIZE, // default rx buffer size
                inactivity_time, // inactivity timeout value in milliseconds 0
                                 // ==> disable
                DEFAULT_MINIMUM_RX_PACKET_LENGTH, // minimum receive packet
                                                  // length
                DEFAULT_MAXIMUM_RX_PACKET_LENGTH, // maximum receive packet
                                                  // length
                pattern, // pattern associated with the listener
                stype, // search type associated with this pattern
                listener, // listener associated with pattern
                null, // default listener in case no match or no listener
                      // installed
                false // immediately start server after initialization
        );
    }

    /** Costructs a new TcpConnection */
    public TcpConnection(Socket socket, byte[] pattern,
            TcpConnectionListener listener, SearchType stype,
            boolean startOnInit) {
        super(null, null);
        init(socket, DEFAULT_SOCKET_TIMEOUT, // so timeout
                DEFAULT_RX_BUFFER_SIZE, // default rx buffer size
                DEFAULT_INACTIVITY_TIMEOUT_VALUE, // inactivity timeout value in
                                                  // milliseconds 0 ==> disable
                DEFAULT_MINIMUM_RX_PACKET_LENGTH, // minimum receive packet
                                                  // length
                DEFAULT_MAXIMUM_RX_PACKET_LENGTH, // maximum receive packet
                                                  // length
                pattern, // pattern associated with the listener
                stype, // search type associated with this pattern
                listener, // listener associated with pattern
                null, // default listener in case no match or no listener
                      // installed
                startOnInit // immediately start server after initialization
        );
    }

    /** Costructs a new TcpConnection */
    public TcpConnection(Socket socket, long inactivity_time, byte[] pattern,
            TcpConnectionListener listener, SearchType stype,
            boolean startOnInit) {
        super(null, null);
        init(socket,
                DEFAULT_SOCKET_TIMEOUT, // so timeout
                DEFAULT_RX_BUFFER_SIZE, // default rx buffer size
                inactivity_time, // inactivity timeout value in milliseconds 0
                                 // ==> disable
                DEFAULT_MINIMUM_RX_PACKET_LENGTH, // minimum receive packet
                                                  // length
                DEFAULT_MAXIMUM_RX_PACKET_LENGTH, // maximum receive packet
                                                  // length
                pattern, // pattern associated with the listener
                stype, // search type associated with this pattern
                listener, // listener associated with pattern
                null, // default listener in case no match or no listener
                      // installed
                startOnInit // immediately start server after initialization
        );
    }

    /** Costructs a new TcpConnection */
    public TcpConnection(Socket socket, byte[] pattern,
            TcpConnectionListener listener, SearchType stype,
            TcpConnectionListener defaultListener) {
        super(null, null);
        init(socket, DEFAULT_SOCKET_TIMEOUT, // so timeout
                DEFAULT_RX_BUFFER_SIZE, // default rx buffer size
                DEFAULT_INACTIVITY_TIMEOUT_VALUE, // inactivity timeout value in
                                                  // milliseconds 0 ==> disable
                DEFAULT_MINIMUM_RX_PACKET_LENGTH, // minimum receive packet
                                                  // length
                DEFAULT_MAXIMUM_RX_PACKET_LENGTH, // maximum receive packet
                                                  // length
                pattern, // pattern associated with the listener
                stype, // search type associated with this pattern
                listener, // listener associated with pattern
                defaultListener, // default listener in case no match or no
                                 // listener installed
                false // immediately start server after initialization
        );
    }

    /** Costructs a new TcpConnection */
    public TcpConnection(Socket socket, long inactivity_time, byte[] pattern,
            TcpConnectionListener listener, SearchType stype,
            TcpConnectionListener defaultListener) {
        super(null, null);
        init(socket, DEFAULT_SOCKET_TIMEOUT, // so timeout
                DEFAULT_RX_BUFFER_SIZE, // default rx buffer size
                inactivity_time, // inactivity timeout value in milliseconds 0
                                 // ==> disable
                DEFAULT_MINIMUM_RX_PACKET_LENGTH, // minimum receive packet
                                                  // length
                DEFAULT_MAXIMUM_RX_PACKET_LENGTH, // maximum receive packet
                                                  // length
                pattern, // pattern associated with the listener
                stype, // search type associated with this pattern
                listener, // listener associated with pattern
                defaultListener, // default listener in case no match or no
                                 // listener installed
                false // immediately start server after initialization
        );
    }

    /** Costructs a new TcpConnection */
    public TcpConnection(Socket socket, byte[] pattern,
            TcpConnectionListener listener, SearchType stype,
            TcpConnectionListener defaultListener, boolean startOnInit) {
        super(null, null);
        init(socket, DEFAULT_SOCKET_TIMEOUT, // so timeout
                DEFAULT_RX_BUFFER_SIZE, // default rx buffer size
                DEFAULT_INACTIVITY_TIMEOUT_VALUE, // inactivity timeout value in
                                                  // milliseconds 0 ==> disable
                DEFAULT_MINIMUM_RX_PACKET_LENGTH, // minimum receive packet
                                                  // length
                DEFAULT_MAXIMUM_RX_PACKET_LENGTH, // maximum receive packet
                                                  // length
                pattern, // pattern associated with the listener
                stype, // search type associated with this pattern
                listener, // listener associated with pattern
                defaultListener, // default listener in case no match or no
                                 // listener installed
                startOnInit // immediately start server after initialization
        );
    }

    /** Costructs a new TcpConnection */
    public TcpConnection(Socket socket, long inactivity_time, byte[] pattern,
            TcpConnectionListener listener, SearchType stype,
            TcpConnectionListener defaultListener, boolean startOnInit) {
        super(null, null);
        init(socket, DEFAULT_SOCKET_TIMEOUT, // so timeout
                DEFAULT_RX_BUFFER_SIZE, // default rx buffer size
                inactivity_time, // inactivity timeout value in milliseconds 0
                                 // ==> disable
                DEFAULT_MINIMUM_RX_PACKET_LENGTH, // minimum receive packet
                                                  // length
                DEFAULT_MAXIMUM_RX_PACKET_LENGTH, // maximum receive packet
                                                  // length
                pattern, // pattern associated with the listener
                stype, // search type associated with this pattern
                listener, // listener associated with pattern
                defaultListener, // default listener in case no match or no
                                 // listener installed
                startOnInit // immediately start server after initialization
        );
    }

    /** Costructs a new TcpConnection */
    public TcpConnection(Socket socket, int sotimeout, int rxBufferSize,
            long inactivity_time, int minRxPacketLength, int maxRxPacketLength,
            byte[] pattern, TcpConnectionListener listener, SearchType stype,
            TcpConnectionListener defaultListener, boolean startOnInit) {
        super(null, null);
        init(socket, sotimeout, // so timeout
                rxBufferSize, // default rx buffer size
                inactivity_time, // inactivity timeout value in milliseconds 0
                                 // ==> disable
                minRxPacketLength, // minimum receive packet length
                maxRxPacketLength, // maximum receive packet length
                pattern, // pattern associated with the listener
                stype, // search type associated with this pattern
                listener, // listener associated with pattern
                defaultListener, // default listener in case no match or no
                                 // listener installed
                startOnInit // immediately start server after initialization
        );
    }

    /**
     * Costructs a new TcpConnection
     * 
     * @throws IOException
     */
    public TcpConnection(int remote_port, InetAddress remote_addr,
            TcpConnectionListener defaultListener) throws IOException {
        super(null, null);
        init(0, null, remote_port, remote_addr, DEFAULT_SOCKET_TIMEOUT, // so
                                                                        // timeout
                DEFAULT_RX_BUFFER_SIZE, // default rx buffer size
                DEFAULT_INACTIVITY_TIMEOUT_VALUE, // inactivity timeout value in
                                                  // milliseconds 0 ==> disable
                DEFAULT_MINIMUM_RX_PACKET_LENGTH, // minimum receive packet
                                                  // length
                DEFAULT_MAXIMUM_RX_PACKET_LENGTH, // maximum receive packet
                                                  // length
                null, // pattern associated with the listener
                SearchType.NONE, // search type associated with this pattern
                null, // listener associated with pattern
                null, // default listener in case no match or no listener
                      // installed
                false // immediately start server after initialization
        );
    }

    /**
     * Costructs a new TcpConnection
     * 
     * @throws IOException
     */
    public TcpConnection(int remote_port, InetAddress remote_addr,
            int local_port, InetAddress local_addr, int sotimeout,
            int rxBufferSize, long inactivity_time, int minRxPacketLength,
            int maxRxPacketLength, byte[] pattern,
            TcpConnectionListener listener, SearchType stype,
            TcpConnectionListener defaultListener, boolean startOnInit)
            throws IOException {
        super(null, null);
        init(local_port, local_addr, remote_port, remote_addr, sotimeout, // so
                                                                          // timeout
                rxBufferSize, // default rx buffer size
                inactivity_time, // inactivity timeout value in milliseconds 0
                                 // ==> disable
                minRxPacketLength, // minimum receive packet length
                maxRxPacketLength, // maximum receive packet length
                pattern, // pattern associated with the listener
                stype, // search type associated with this pattern
                listener, // listener associated with pattern
                defaultListener, // default listener in case no match or no
                                 // listener installed
                startOnInit // immediately start server after initialization
        );
    }

    /** Inits the TcpConnection */
    protected void init(Socket conn_socket, int sotimeout, int bufferSize,
            long inactivity_time, int minRxPacketLength, int maxRxPacketLength,
            byte[] pattern, SearchType stype, TcpConnectionListener listener,
            TcpConnectionListener defaultListener, boolean startOnInit) {
        setSocket(conn_socket);
        setLocalPortInternal(conn_socket.getLocalPort());
        setLocalAddressInternal(conn_socket.getLocalAddress());
        setRemotePortInternal(conn_socket.getPort());
        setRemoteAddressInternal(conn_socket.getInetAddress());

        setSoTimeout(sotimeout);
        setReceiveBufferSize(bufferSize);
        setMinimumReceivePacketLength(minRxPacketLength);
        setMaximumReceivePacketLength(maxRxPacketLength);
        setInactivityTime(inactivity_time);
        setDefaultListener(defaultListener);
        if ((pattern != null) && (listener != null))
            addListener(pattern, listener, stype);
        try {
            setInputStream(new BufferedInputStream(getSocket().getInputStream()));
            setOutputStream(new BufferedOutputStream(getSocket()
                    .getOutputStream()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (startOnInit == true) {
            start();
        }
    }

    /**
     * Inits the TcpConnection
     * 
     * @throws IOException
     */
    protected void init(int local_port, InetAddress local_addr,
            int remote_port, InetAddress remote_addr, int sotimeout,
            int bufferSize, long inactivity_time, int minRxPacketLength,
            int maxRxPacketLength, byte[] pattern, SearchType stype,
            TcpConnectionListener listener,
            TcpConnectionListener defaultListener, boolean startOnInit)
            throws IOException {
        Socket sock = new Socket(remote_addr, remote_port, local_addr,
                local_port);

        init(
                sock,
                sotimeout,
                bufferSize,
                inactivity_time,
                minRxPacketLength,
                maxRxPacketLength,
                pattern,
                stype,
                listener,
                defaultListener,
                startOnInit);
    }

    @Override
    public InetSocketAddress getLocalSocketAddress() {
        return new InetSocketAddress(getSocket().getLocalAddress(), getSocket()
                .getLocalPort());
    }

    @Override
    public InetAddress getLocalIpAddress() {
        return getSocket().getLocalAddress();
    }

    @Override
    public int getLocalPort() {
        return getSocket().getLocalPort();
    }

    @Override
    public InetSocketAddress getRemoteSocketAddress() {
        return new InetSocketAddress(getSocket().getInetAddress(), getSocket()
                .getPort());
    }

    @Override
    public InetAddress getRemoteIpAddress() {
        return getSocket().getInetAddress();
    }

    @Override
    public int getRemotePort() {
        return getSocket().getPort();
    }

    @Override
    public boolean setSocketAddress(InetSocketAddress local_sa,
            InetSocketAddress remote_sa) throws IOException {
        boolean restart = false;
        Socket tmpSocket;

        if ((local_sa == null) && (remote_sa == null)) {
            return false;
        }

        if ((getSocket() != null) && (local_sa != null)) {
            if ((local_sa.getAddress() != null)
                    && (getSocket().getLocalAddress() != null)) {
                if ((local_sa.getPort() == getSocket().getLocalPort())
                        && local_sa.getAddress().equals(
                                getSocket().getLocalAddress())) {
                    local_sa = null;
                }
            } else if ((local_sa.getAddress() == null)
                    && (getSocket().getLocalAddress() == null)) {
                if (local_sa.getPort() == getSocket().getLocalPort()) {
                    local_sa = null;
                }
            }
        }

        if ((getSocket() != null) && (remote_sa != null)) {
            if ((remote_sa.getAddress() != null)
                    && (getSocket().getInetAddress() != null)) {
                if ((remote_sa.getPort() == getSocket().getPort())
                        && remote_sa.getAddress().equals(
                                getSocket().getInetAddress())) {
                    remote_sa = null;
                }
            } else if ((remote_sa.getAddress() == null)
                    && (getSocket().getInetAddress() == null)) {
                if (remote_sa.getPort() == getSocket().getPort()) {
                    remote_sa = null;
                }
            }
        }

        if ((local_sa == null) && (remote_sa == null)) {
            return true;
        }

        InetSocketAddress old_local_sa = null;
        InetSocketAddress old_remote_sa = null;
        if (isRunning()) {
            old_local_sa = new InetSocketAddress(getSocket().getLocalAddress(),
                    getSocket().getLocalPort());
            old_remote_sa = new InetSocketAddress(getSocket().getInetAddress(),
                    getSocket().getPort());
            restart = true;

            stop();
        }

        try {
            if ((local_sa != null) && (remote_sa != null)) {
                tmpSocket = new Socket(remote_sa.getAddress(),
                        remote_sa.getPort(), local_sa.getAddress(),
                        local_sa.getPort());
            } else if ((local_sa != null) && (remote_sa == null)) {
                log.debug(String
                        .format(
                                "remote_ip=%s, remote_port=%d, local_ip=%s, local_port=%d",
                                getRemoteAddressInternal().toString(),
                                getRemotePortInternal(),
                                local_sa.getAddress().toString(),
                                local_sa.getPort()));
                tmpSocket = new Socket(getRemoteAddressInternal(),
                        getRemotePortInternal(), local_sa.getAddress(),
                        local_sa.getPort());
            } else {
                tmpSocket = new Socket(remote_sa.getAddress(),
                        remote_sa.getPort(), null, 0);
                local_sa = null;
            }
            setSocket(tmpSocket);
            setInputStream(new BufferedInputStream(getSocket().getInputStream()));
            setOutputStream(new BufferedOutputStream(getSocket()
                    .getOutputStream()));
            if (restart == true) {
                start();
            }
            if (local_sa != null) {
                setLocalPortInternal(local_sa.getPort());
                setLocalAddressInternal(local_sa.getAddress());
            }
            if (remote_sa != null) {
                setRemotePortInternal(remote_sa.getPort());
                setRemoteAddressInternal(remote_sa.getAddress());
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            if (old_local_sa != null) {
                tmpSocket = new Socket(old_remote_sa.getAddress(),
                        old_remote_sa.getPort(), old_local_sa.getAddress(),
                        old_local_sa.getPort());
                setSocket(tmpSocket);
                setInputStream(new BufferedInputStream(getSocket()
                        .getInputStream()));
                setOutputStream(new BufferedOutputStream(getSocket()
                        .getOutputStream()));
                if (restart == true) {
                    start();
                }
            } else {
                throw (IOException) e;
            }
            return false;
        }
    }

    @Override
    protected void socketClose() throws IOException {
        getSocket().close();
    }

    @Override
    public void run() {
        byte[] buff = null;
        Exception error = null;
        int len = 0;
        long expire = 0;
        boolean intr = false;

        if (getSocket() == null) {
            try {
                setSocket(new Socket(getRemoteAddressInternal(),
                        getRemotePortInternal(), getLocalAddressInternal(),
                        getLocalPortInternal()));
                setInputStream(new BufferedInputStream(getSocket()
                        .getInputStream()));
                setOutputStream(new BufferedOutputStream(getSocket()
                        .getOutputStream()));
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
                    buff = new byte[getReceiveBufferSize()];
                    len = getInputStream().read(buff);
                } catch (SocketTimeoutException ie) {
                    if (getInactivityTime() > 0
                            && System.currentTimeMillis() > expire) {
                        stop();
                        error = ie;
                    }
                    intr = true;
                    continue;
                }
                if (len < 0) {
                    stop();
                } else if (len < getMinimumReceivePacketLength()) {
                    onErrorReceivedSmallPacket(this, buff, 0, len);
                    if (getInactivityTime() > 0)
                        expire = System.currentTimeMillis()
                                + getInactivityTime();
                } else if (len > getMaximumReceivePacketLength()) {
                    onErrorReceivedLargePacket(this, buff, 0, len);
                    if (getInactivityTime() > 0)
                        expire = System.currentTimeMillis()
                                + getInactivityTime();
                } else {
                    onReceivedData(this, buff, 0, len);
                    if (getInactivityTime() > 0)
                        expire = System.currentTimeMillis()
                                + getInactivityTime();
                }
            }
        } catch (Exception e) {
            error = e;
            stop();
        }

        try {
            getInputStream().close();
            getOutputStream().close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        onConnectionTerminated(this, error);
        clearIsRunningFlag();
    }

    /** Gets a String representation of the Object */
    @Override
    public String toString() {
        return ("TCP Connection:" + getSocket().getLocalAddress().toString()
                + ":" + getSocket().getLocalPort() + "\n" + super.toString());
    }
}