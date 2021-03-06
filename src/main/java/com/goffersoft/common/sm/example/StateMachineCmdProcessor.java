/**
 ** File: StateMachineCmdProcessor.java
 **
 ** Description : StateMachineCmdProcessor class - State Machine 
 **               receives  commands over udp :7777
 **               responds bck to udp 6666
 ** Date           Author                          Comments
 ** 08/31/2013     Prakash Easwar                  Created  
 **/
package com.goffersoft.common.sm.example;

import org.apache.log4j.Logger;

import com.goffersoft.common.net.GenericConnection;
import com.goffersoft.common.net.UdpConnection;
import com.goffersoft.common.net.UdpConnectionContext;
import com.goffersoft.common.net.UdpConnectionFactory;

public class StateMachineCmdProcessor {
    private static final Logger log = Logger
            .getLogger(StateMachineCmdProcessor.class);

    /**
     * @param args
     */
    public static void main(String[] args) {
        try {
            UdpConnectionContext udpctxt = new UdpConnectionContext();
            udpctxt.addListener(
                    "Cmd".getBytes(),
                    new StateMachineListenerImpl(),
                    GenericConnection.SearchType.STARTSWITH);
            UdpConnectionFactory udpfactory = new UdpConnectionFactory(udpctxt);
            UdpConnection udp = udpfactory.createConnection(7777);
            log.info(udp.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}