/**
 ** File: TcpSSLConnection.java
 **
 ** Description : TcpSSLConnection class - code to create a SSL TCP Client
 **
 ** Date           Author                          Comments
 ** 08/31/2013     Prakash Easwar                  Created
 **/
package com.goffersoft.common.net;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.Iterator;
import java.util.List;

import javax.net.ssl.HandshakeCompletedEvent;
import javax.net.ssl.HandshakeCompletedListener;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

import org.apache.log4j.Logger;

//@formatter:off
public class TcpSSLConnection
        extends
        GenericTcpConnection<
                        SSLSocket,
                        TcpSSLConnection,
                        TcpSSLConnectionListener>
        implements
        TcpSSLConnectionListener,
        HandshakeCompletedListener {
//@formatter:on

    private static final Logger log = Logger.getLogger(TcpSSLConnection.class);
    private SSLSocketFactory sslFactory;
    /**
     * if there are no listeners installed
     * send all new connections to this listener
     */
    static {
        setFactoryDefaultListener(new TcpSSLConnectionListenerImpl());
    }

    /** Costructs a new TcpSSLConnection */
    public TcpSSLConnection(
            SSLSocket socket,
            int sotimeout,
            int rxBufferSize,
            long inactivity_time,
            int minRxPacketLength,
            int maxRxPacketLength,
            TcpSSLConnectionListener defaultListener,
            List<GenericConnectionMap.ListenerInfo<TcpSSLConnectionListener>>
            listOfListeners,
            boolean startOnInit) {
        super(null, null);
        init(socket,
                sotimeout, // so timeout
                rxBufferSize, // default rx buffer size
                inactivity_time, // inactivity timeout value in milliseconds 0
                                 // ==> disable
                minRxPacketLength, // minimum receive packet length
                maxRxPacketLength, // maximum receive packet length
                defaultListener, // default listener in case no match or no
                                 // listener installed
                listOfListeners,
                startOnInit // immediately start server after initialization
        );
    }

    /**
     * Costructs a new TcpConnection
     * 
     * @throws IOException
     */
    public TcpSSLConnection(
            int remote_port,
            InetAddress remote_addr,
            int local_port,
            InetAddress local_addr,
            SSLSocketFactory sslFactory,
            int sotimeout,
            int rxBufferSize,
            long inactivity_time,
            int minRxPacketLength,
            int maxRxPacketLength,
            TcpSSLConnectionListener defaultListener,
            List<GenericConnectionMap.ListenerInfo<TcpSSLConnectionListener>>
            listOfListeners,
            boolean startOnInit) throws IOException {
        super(null, null);
        init(local_port,
                local_addr,
                remote_port,
                remote_addr,
                sslFactory,
                sotimeout, // sotimeout
                rxBufferSize, // default rx buffer size
                inactivity_time, // inactivity timeout value in milliseconds 0
                                 // ==> disable
                minRxPacketLength, // minimum receive packet length
                maxRxPacketLength, // maximum receive packet length
                defaultListener, // default listener in case no match or no
                                 // listener installed
                listOfListeners,
                startOnInit // immediately start server after initialization
        );
    }

    /** Inits the TcpConnection */
    protected void init(
            SSLSocket conn_socket,
            int sotimeout,
            int bufferSize,
            long inactivity_time,
            int minRxPacketLength,
            int maxRxPacketLength,
            TcpSSLConnectionListener defaultListener,
            List<GenericConnectionMap.ListenerInfo<TcpSSLConnectionListener>>
            listOfListeners,
            boolean startOnInit) {
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
        if (listOfListeners != null) {
            Iterator<GenericConnectionMap.ListenerInfo<TcpSSLConnectionListener>> it =
                    listOfListeners.iterator();
            while (it.hasNext()) {
                GenericConnectionMap.ListenerInfo<TcpSSLConnectionListener> info =
                        it.next();
                if ((info.getPattern() != null) && (info.getListener() != null))
                    addListener(
                            info.getPattern(),
                            info.getListener(),
                            info.getType());
            }
        }
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
    protected void init(
            int local_port,
            InetAddress local_addr,
            int remote_port,
            InetAddress remote_addr,
            SSLSocketFactory sslFactory,
            int sotimeout,
            int bufferSize,
            long inactivity_time,
            int minRxPacketLength,
            int maxRxPacketLength,
            TcpSSLConnectionListener defaultListener,
            List<GenericConnectionMap.ListenerInfo<TcpSSLConnectionListener>>
            listOfListeners,
            boolean startOnInit) throws IOException {
        if (sslFactory == null) {
            setSSLFactory((SSLSocketFactory) SSLSocketFactory.getDefault());
        } else {
            setSSLFactory(sslFactory);
        }

        SSLSocket sock =
                (SSLSocket) getSSLFactory().createSocket(
                        remote_addr,
                        remote_port,
                        local_addr,
                        local_port);
        sock.addHandshakeCompletedListener(this);
        init(
                sock,
                sotimeout,
                bufferSize,
                inactivity_time,
                minRxPacketLength,
                maxRxPacketLength,
                defaultListener,
                listOfListeners,
                startOnInit);
    }

    public SSLSocketFactory getSSLFactory() {
        return sslFactory;
    }

    public void setSSLFactory(SSLSocketFactory sslFactory) {
        this.sslFactory = sslFactory;
    }

    public void onHandshakeCompleted(
            TcpSSLConnection tcp,
            HandshakeCompletedEvent event) {
        getDefaultListener().onHandshakeCompleted(tcp, event);
    }

    @Override
    public void handshakeCompleted(HandshakeCompletedEvent event) {
        // TODO Auto-generated method stub

    }

    @Override
    public void run() {
        byte[] buff = null;
        Exception error = null;
        int len = 0;
        long expire = 0;
        boolean intr = false;

        SSLSocketFactory sslsocketfactory =
                (SSLSocketFactory) SSLSocketFactory.getDefault();

        if (getSocket() == null) {
            try {

                SSLSocket sock =
                        (SSLSocket) sslsocketfactory.createSocket(
                                getRemoteAddressInternal(),
                                getRemotePortInternal(),
                                getLocalAddressInternal(),
                                getLocalPortInternal());
                sock.addHandshakeCompletedListener(this);
                setSocket(sock);
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
                            expire =
                                    System.currentTimeMillis()
                                            + getInactivityTime();
                        }
                    }
                    getSocket().setSoTimeout(getSoTimeout());
                    buff = new byte[getReceiveBufferSize()];
                    len = getInputStream().read(buff);
                } catch (InterruptedIOException ie) {
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
                        expire =
                                System.currentTimeMillis()
                                        + getInactivityTime();
                } else if (len > getMaximumReceivePacketLength()) {
                    onErrorReceivedLargePacket(this, buff, 0, len);
                    if (getInactivityTime() > 0)
                        expire =
                                System.currentTimeMillis()
                                        + getInactivityTime();
                } else {
                    onReceivedData(this, buff, 0, len);
                    if (getInactivityTime() > 0)
                        expire =
                                System.currentTimeMillis()
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
    public boolean setSocketAddress(
            InetSocketAddress local_sa,
            InetSocketAddress remote_sa) throws IOException {
        SSLSocket tmpSocket;

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
            old_local_sa =
                    new InetSocketAddress(
                            getSocket().getLocalAddress(),
                            getSocket().getLocalPort());
            old_remote_sa =
                    new InetSocketAddress(
                            getSocket().getInetAddress(),
                            getSocket().getPort());
            stop();
        }
        SSLSocketFactory sslsocketfactory =
                (SSLSocketFactory) SSLSocketFactory.getDefault();

        try {
            if ((local_sa != null) && (remote_sa != null)) {
                tmpSocket =
                        (SSLSocket) sslsocketfactory.createSocket(
                                remote_sa.getAddress(),
                                remote_sa.getPort(),
                                local_sa.getAddress(),
                                local_sa.getPort());
            } else if ((local_sa != null) && (remote_sa == null)) {
                tmpSocket =
                        (SSLSocket) sslsocketfactory.createSocket(
                                getRemoteAddressInternal(),
                                getRemotePortInternal(),
                                local_sa.getAddress(),
                                local_sa.getPort());
            } else {
                tmpSocket =
                        (SSLSocket) sslsocketfactory.createSocket(
                                remote_sa.getAddress(),
                                remote_sa.getPort(),
                                getLocalAddressInternal(),
                                getLocalPortInternal());
            }
            tmpSocket.addHandshakeCompletedListener(this);
            setSocket(tmpSocket);
            setInputStream(new BufferedInputStream(getSocket().getInputStream()));
            setOutputStream(new BufferedOutputStream(getSocket()
                    .getOutputStream()));
            start();
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
            if (old_local_sa != null) {
                tmpSocket =
                        (SSLSocket) sslsocketfactory.createSocket(
                                old_remote_sa.getAddress(),
                                old_remote_sa.getPort(),
                                old_local_sa.getAddress(),
                                old_local_sa.getPort());
                tmpSocket.addHandshakeCompletedListener(this);
                setSocket(tmpSocket);
                setInputStream(new BufferedInputStream(getSocket()
                        .getInputStream()));
                setOutputStream(new BufferedOutputStream(getSocket()
                        .getOutputStream()));
                start();
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

    /** Gets a String representation of the Object */
    @Override
    public String toString() {
        return ("TCP SSL Connection:"
                + getSocket().getLocalAddress().toString() + ":"
                + getSocket().getLocalPort() + "\n" + super.toString());
    }
}
