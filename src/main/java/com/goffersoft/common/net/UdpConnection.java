/**
 ** File: UdpConnection.java
 **
 ** Description : UdpConnection class - code to create a udp client/server
 **
 ** Date           Author                          Comments
 ** 08/31/2013     Prakash Easwar                  Created  
 **/

package com.goffersoft.common.net;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.net.SocketException;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

public class UdpConnection
        extends
        GenericConnection<DatagramSocket, UdpConnectionListener>
        implements
        UdpConnectionListener {
    private static final Logger log = Logger.getLogger(UdpConnection.class);

    /**
     * if there are no listeners in the connectinMap or if a match is not found
     * send all packets to this listener
     */

    volatile private int local_port;
    volatile private InetAddress local_addr;

    static {
        setFactoryDefaultListener(new UdpConnectionListenerImpl());
    }

    /** Creates a new UdpConnection */
    public UdpConnection(
            int port,
            InetAddress bind_ipaddr,
            int sotimeout,
            int bufferSize,
            long inactivity_time,
            int minRxPacketLength,
            int maxRxPacketLength,
            List<GenericConnectionMap.ListenerInfo<UdpConnectionListener>> listOfListeners,
            UdpConnectionListener defaultListener,
            boolean startOnInit) throws SocketException {
        init(port, // udp rx port number
                bind_ipaddr, // ip address, null ==> INADDR_ANY
                sotimeout, // so timeout
                bufferSize, // default rx buffer size
                inactivity_time, // inactivity timeout value in milliseconds 0
                                 // ==> disable
                minRxPacketLength, // minimum receive packet length
                maxRxPacketLength, // maximum receive packet length
                listOfListeners,
                defaultListener, // default listener in case no match or no
                                 // listener installed
                startOnInit // immediately start server after initialization
        );
    }

    /** Creates a new UdpConnection */
    public UdpConnection(
            DatagramSocket sock,
            int sotimeout,
            int bufferSize,
            long inactivity_time,
            int minRxPacketLength,
            int maxRxPacketLength,
            List<GenericConnectionMap.ListenerInfo<UdpConnectionListener>> listOfListeners,
            UdpConnectionListener defaultListener,
            boolean startOnInit) throws SocketException {
        init(sock,
                sotimeout, // so timeout
                bufferSize, // default rx buffer size
                inactivity_time, // inactivity timeout value in milliseconds 0
                                 // ==> disable
                minRxPacketLength, // minimum receive packet length
                maxRxPacketLength, // maximum receive packet length
                listOfListeners,
                defaultListener, // default listener in case no match or no
                                 // listener installed
                startOnInit // immediately start server after initialization
        );
    }

    /** Inits the UdpConnection */

    protected void init(
            DatagramSocket sock,
            int sotimeout,
            int bufferSize,
            long inactivity_time,
            int minRxPacketLength,
            int maxRxPacketLength,
            List<GenericConnectionMap.ListenerInfo<UdpConnectionListener>>
            listOfListeners,
            UdpConnectionListener defaultListener,
            boolean startOnInit) throws SocketException {
        setSocket(sock);
        local_addr = sock.getLocalAddress();
        local_port = sock.getLocalPort();

        setSoTimeout(sotimeout);
        setReceiveBufferSize(bufferSize);
        setMinimumReceivePacketLength(minRxPacketLength);
        setMaximumReceivePacketLength(maxRxPacketLength);
        setInactivityTime(inactivity_time);
        setDefaultListener(defaultListener);

        if (listOfListeners != null) {
            Iterator<GenericConnectionMap.ListenerInfo<UdpConnectionListener>> it =
                    listOfListeners.iterator();
            while (it.hasNext()) {
                GenericConnectionMap.ListenerInfo<UdpConnectionListener> info =
                        it.next();
                if ((info.getPattern() != null) && (info.getListener() != null))
                    addListener(
                            info.getPattern(),
                            info.getListener(),
                            info.getType());
            }
        }

        if (startOnInit == true) {
            start();
        }
    }

    protected void init(
            int port,
            InetAddress bind_ipaddr,
            int sotimeout,
            int bufferSize,
            long inactivity_time,
            int minRxPacketLength,
            int maxRxPacketLength,
            List<GenericConnectionMap.ListenerInfo<UdpConnectionListener>>
            listOfListeners,
            UdpConnectionListener defaultListener,
            boolean startOnInit) throws SocketException {
        DatagramSocket sock;
        if (bind_ipaddr == null || bind_ipaddr.getAddress() == null) {
            sock = new DatagramSocket(port);
        } else {
            sock = new DatagramSocket(port, bind_ipaddr);
        }

        init(sock,
                sotimeout, // so timeout
                bufferSize, // default rx buffer size
                inactivity_time, // inactivity timeout value in milliseconds 0
                                 // ==> disable
                minRxPacketLength, // minimum receive packet length
                maxRxPacketLength, // maximum receive packet length
                listOfListeners,
                defaultListener, // default listener in case no match or no
                                 // listener installed
                startOnInit // immediately start server after initialization
        );
    }

    public InetSocketAddress getLocalSocketAddress() {
        return new InetSocketAddress(getSocket().getLocalAddress(), getSocket()
                .getLocalPort());
    }

    public InetAddress getLocalIpAddress() {
        return getSocket().getLocalAddress();
    }

    public int getLocalPort() {
        return getSocket().getLocalPort();
    }

    public boolean setLocalSocketAddress(InetSocketAddress sa)
            throws SocketException {
        boolean restart = false;
        DatagramSocket tmpSocket = null;

        if (sa == null) {
            return false;
        }

        if (getSocket() != null) {
            if ((sa.getAddress() != null)
                    && (getSocket().getLocalAddress() != null)) {
                if ((sa.getPort() == getSocket().getLocalPort())
                        && sa
                                .getAddress()
                                .equals(getSocket().getLocalAddress())) {
                    return true;
                }
            } else if ((sa.getAddress() == null)
                    && (getSocket().getLocalAddress() == null)) {
                if (sa.getPort() == getSocket().getLocalPort()) {
                    return true;
                }
            }
        }
        InetSocketAddress old_sa = null;
        if (isRunning()) {
            old_sa =
                    new InetSocketAddress(
                            getSocket().getLocalAddress(),
                            getSocket().getLocalPort());

            restart = true;

            stop();
        }

        try {
            if (sa.getAddress() == null) {
                tmpSocket = new DatagramSocket(sa.getPort());
            } else {
                tmpSocket = new DatagramSocket(sa.getPort(), sa.getAddress());
            }
            setSocket(tmpSocket);
            if (restart == true) {
                start();
            }
            local_port = sa.getPort();
            local_addr = sa.getAddress();
            return true;
        } catch (SocketException e) {
            if (old_sa != null) {
                if (old_sa.getAddress() == null) {
                    tmpSocket = new DatagramSocket(old_sa.getPort());
                } else {
                    tmpSocket =
                            new DatagramSocket(
                                    old_sa.getPort(),
                                    old_sa.getAddress());
                }
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

    public boolean setLocalSocketAddress(InetAddress ipaddr, int port)
            throws SocketException {
        return setLocalSocketAddress(new InetSocketAddress(ipaddr, port));
    }

    @Override
    protected void socketClose() throws IOException {
        getSocket().close();
    }

    @Override
    public void onReceivedPacket(UdpConnection udp, DatagramPacket packet) {
        UdpConnectionListener listener =
                getConnectionListener(packet.getData(), 0, packet.getLength());

        if (listener == null) {
            getDefaultListener().onReceivedPacket(udp, packet);
        } else {
            listener.onReceivedPacket(udp, packet);
        }
    }

    @Override
    public void onServiceTerminated(UdpConnection udp, Exception error) {
        Iterator<ListenerInfo<UdpConnectionListener>> it =
                getConnectionMapListIterator();

        ListenerInfo<UdpConnectionListener> info;
        while ((info = getNextConnectionMapListEntry(it)) != null) {
            info.getListener().onServiceTerminated(udp, error);
        }
        getDefaultListener().onServiceTerminated(udp, error);
    }

    @Override
    public void onErrorReceivedSmallPacket(
            UdpConnection udp,
            DatagramPacket packet) {
        UdpConnectionListener listener =
                getConnectionListener(packet.getData(), 0, packet.getLength());

        if (listener == null) {
            getDefaultListener().onErrorReceivedSmallPacket(udp, packet);
        } else {
            listener.onErrorReceivedSmallPacket(udp, packet);
        }
    }

    @Override
    public void onErrorReceivedLargePacket(
            UdpConnection udp,
            DatagramPacket packet) {
        UdpConnectionListener listener =
                getConnectionListener(packet.getData(), 0, packet.getLength());

        if (listener == null) {
            getDefaultListener().onErrorReceivedLargePacket(udp, packet);
        } else {
            listener.onErrorReceivedLargePacket(udp, packet);
        }
    }

    /** The main thread */
    @Override
    public void run() {
        DatagramPacket packet = null;
        Exception error = null;
        long expire = 0;
        DatagramSocket tmpSocket;
        boolean intr = false;

        if (getSocket() == null) {
            try {
                if (local_addr == null) {
                    tmpSocket = new DatagramSocket(local_port);
                } else {
                    tmpSocket = new DatagramSocket(local_port, local_addr);
                }
                setSocket(tmpSocket);
            } catch (SocketException e) {
                e.printStackTrace();
            }
        }

        setIsRunningFlag();

        try {
            // loop
            while (isStarted()) {

                try {
                    if (getInactivityTime() > 0) {
                        if (intr == false) {
                            expire =
                                    System.currentTimeMillis()
                                            + getInactivityTime();
                        }
                    }

                    if ((packet == null)
                            || (packet.getLength() < getReceiveBufferSize())) {
                        packet =
                                new DatagramPacket(
                                        new byte[getReceiveBufferSize()],
                                        getReceiveBufferSize());
                    }
                    packet.setLength(getReceiveBufferSize());
                    getSocket().setSoTimeout(getSoTimeout());
                    getSocket().receive(packet);
                } catch (InterruptedIOException ie) {
                    if (getInactivityTime() > 0
                            && System.currentTimeMillis() > expire) {
                        error = ie;
                        stop();
                    }
                    intr = true;
                    continue;
                }

                if (packet.getLength() < getMinimumReceivePacketLength()) {
                    onErrorReceivedSmallPacket(this, packet);
                    if (getInactivityTime() > 0)
                        expire =
                                System.currentTimeMillis()
                                        + getInactivityTime();
                } else if (packet.getLength() > getMaximumReceivePacketLength()) {
                    onErrorReceivedLargePacket(this, packet);
                    if (getInactivityTime() > 0)
                        expire =
                                System.currentTimeMillis()
                                        + getInactivityTime();
                } else {
                    onReceivedPacket(this, packet);
                    if (getInactivityTime() > 0)
                        expire =
                                System.currentTimeMillis()
                                        + getInactivityTime();
                }
                packet = null;
            }
        } catch (Exception e) {
            error = e;
            stop();
        }
        onServiceTerminated(this, error);
        clearIsRunningFlag();
    }

    /** Sends a UdpPacket */
    public void send(DatagramPacket packet) throws java.io.IOException {
        if (isRunning())
            getSocket().send(packet);
    }

    /** Sends a UdpPacket */
    public void send(byte[] data, int offset, int datalen, SocketAddress sa)
            throws java.io.IOException {
        DatagramPacket packet = new DatagramPacket(data, offset, datalen, sa);

        if (isRunning())
            getSocket().send(packet);
    }

    /** Sends a UdpPacket */
    public void send(
            byte[] data,
            int offset,
            int datalen,
            InetAddress ipaddr,
            int port) throws java.io.IOException {
        DatagramPacket packet =
                new DatagramPacket(data, offset, datalen, ipaddr, port);

        if (isRunning())
            getSocket().send(packet);
    }

    /** Gets a String representation of the Object */
    @Override
    public String toString() {
        return ("udp:" + getSocket().getLocalAddress().toString() + ":"
                + getSocket().getLocalPort() + "\n" + super.toString());
    }
}