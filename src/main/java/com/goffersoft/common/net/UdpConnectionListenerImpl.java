/**
 ** File: UdpDefaultConnectionListener.java
 **
 ** Description : UdpDefaultConnectionListener class - implements UdpConnectionListener
 **               factory default listener installed by udp server incase no listeners
 **               are installed
 **
 ** Date           Author                          Comments
 ** 08/30/2013     Prakash Easwar                  Created  
 **/
package com.goffersoft.common.net;

import java.net.DatagramPacket;

import org.apache.log4j.Logger;

import com.goffersoft.common.utils.PrintUtils;

public class UdpConnectionListenerImpl
        extends
        GenericConnectionListenerImpl
        implements
        UdpConnectionListener {

    private static final Logger log = Logger
            .getLogger(UdpConnectionListenerImpl.class);

    @Override
    public void onReceivedPacket(UdpConnection udp, DatagramPacket packet) {
        byte[] data = packet.getData();
        int len = packet.getLength();

        log
                .info("Factory Default Listener Installed: Received UDP Packet: Length="
                        + len);

        log.debug("Packet Details : \n"
                + PrintUtils.byteArrayToHexString(data, 0, packet.getLength(),
                        16));
    }

    @Override
    public void onServiceTerminated(UdpConnection udp, Exception error) {
        log.debug("Factory Default Listener Installed : Service Terminated ");
        if (error != null) {
            error.printStackTrace();
        }
    }

    @Override
    public void onErrorReceivedSmallPacket(UdpConnection udp,
            DatagramPacket packet) {
        byte[] data = packet.getData();
        int len = packet.getLength();

        log
                .info("Factory Default Listener Installed: Received Small UDP Packet: Length="
                        + len);

        log.debug("Packet Details : \n"
                + PrintUtils.byteArrayToHexString(data, 0, packet.getLength(),
                        16));
    }

    @Override
    public void onErrorReceivedLargePacket(UdpConnection udp,
            DatagramPacket packet) {
        byte[] data = packet.getData();
        int len = packet.getLength();

        log
                .info("Factory Default Listener Installed: Received Large UDP Packet: Length="
                        + len);

        log.debug("Packet Details : \n"
                + PrintUtils.byteArrayToHexString(data, 0, packet.getLength(),
                        16));
    }
}
