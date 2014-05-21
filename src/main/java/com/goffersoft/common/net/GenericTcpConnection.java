package com.goffersoft.common.net;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.Iterator;

import org.apache.log4j.Logger;

abstract public class GenericTcpConnection<TcpConnectionSocketType, TcpConnectionType extends GenericTcpConnection<TcpConnectionSocketType, TcpConnectionType, TcpConnectionListenerType>, TcpConnectionListenerType extends GenericTcpConnectionListener<TcpConnectionType>>
        extends
        GenericConnection<TcpConnectionSocketType, TcpConnectionListenerType>
        implements
        GenericTcpConnectionListener<TcpConnectionType> {

    private static final Logger log = Logger
            .getLogger(GenericTcpConnection.class);

    volatile private InputStream istream;
    volatile private OutputStream ostream;
    volatile private int local_port;
    volatile private InetAddress local_addr;
    volatile private int remote_port;
    volatile private InetAddress remote_addr;

    protected GenericTcpConnection() {
        super(null, null);
    }

    protected GenericTcpConnection(TcpConnectionSocketType s, Thread t) {
        super(s, t);
    }

    protected InputStream getInputStream() {
        return istream;
    }

    protected void setInputStream(InputStream istream) {
        this.istream = istream;
    }

    public OutputStream getOutputStream() {
        return ostream;
    }

    protected void setOutputStream(OutputStream ostream) {
        this.ostream = ostream;
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

    protected int getRemotePortInternal() {
        return remote_port;
    }

    protected void setRemotePortInternal(int remote_port) {
        this.remote_port = remote_port;
    }

    protected InetAddress getRemoteAddressInternal() {
        return remote_addr;
    }

    protected void setRemoteAddressInternal(InetAddress remote_addr) {
        this.remote_addr = remote_addr;
    }

    public boolean setLocalSocketAddress(InetSocketAddress sa)
            throws IOException {
        return setSocketAddress(sa, null);
    }

    public boolean setLocalSocketAddress(InetAddress ipaddr, int port)
            throws IOException {
        return setSocketAddress(new InetSocketAddress(ipaddr, port), null);
    }

    public boolean setRemoteSocketAddress(InetSocketAddress sa)
            throws IOException {
        return setSocketAddress(null, sa);
    }

    public boolean setRemoteSocketAddress(InetAddress ipaddr, int port)
            throws IOException {
        return setSocketAddress(null, new InetSocketAddress(ipaddr, port));
    }

    /** Sends data */
    public void send(byte[] buff, int offset, int len) throws IOException {
        if (isStarted() && ostream != null) {
            ostream.write(buff, offset, len);
            ostream.flush();
        }
    }

    /** Sends data */
    public void send(byte[] buff) throws IOException {
        send(buff, 0, buff.length);
    }

    /** Sends data */
    public void send(byte[] buff, int length) throws IOException {
        send(buff, 0, length);
    }

    @Override
    public void onReceivedData(
            TcpConnectionType tcp,
            byte[] data,
            int offset,
            int length) {
        TcpConnectionListenerType listener =
                getConnectionListener(data, 0, length);

        if (listener == null) {
            getDefaultListener().onReceivedData(tcp, data, offset, length);
        } else {
            listener.onReceivedData(tcp, data, offset, length);
        }

    }

    @Override
    public void onConnectionTerminated(TcpConnectionType tcp, Exception error) {
        Iterator<ListenerInfo<TcpConnectionListenerType>> it =
                getConnectionMapListIterator();
        ListenerInfo<TcpConnectionListenerType> linfo;

        while ((linfo = getNextConnectionMapListEntry(it)) != null) {
            linfo.getListener().onConnectionTerminated(tcp, error);
        }
        getDefaultListener().onConnectionTerminated(tcp, error);
    }

    @Override
    public void onErrorReceivedSmallPacket(
            TcpConnectionType tcp,
            byte[] data,
            int offset,
            int length) {
        TcpConnectionListenerType listener =
                getConnectionListener(data, 0, length);

        if (listener == null) {
            getDefaultListener().onErrorReceivedSmallPacket(
                    tcp,
                    data,
                    offset,
                    length);
        } else {
            listener.onErrorReceivedSmallPacket(tcp, data, offset, length);
        }
    }

    @Override
    public void onErrorReceivedLargePacket(
            TcpConnectionType tcp,
            byte[] data,
            int offset,
            int length) {
        TcpConnectionListenerType listener =
                getConnectionListener(data, 0, length);

        if (listener == null) {
            getDefaultListener().onErrorReceivedLargePacket(
                    tcp,
                    data,
                    offset,
                    length);
        } else {
            listener.onErrorReceivedLargePacket(tcp, data, offset, length);
        }
    }

    abstract public InetSocketAddress getLocalSocketAddress();

    abstract public InetAddress getLocalIpAddress();

    abstract public int getLocalPort();

    abstract public InetSocketAddress getRemoteSocketAddress();

    abstract public InetAddress getRemoteIpAddress();

    abstract public int getRemotePort();

    abstract public boolean setSocketAddress(
            InetSocketAddress local_sa,
            InetSocketAddress remote_sa) throws IOException;

}