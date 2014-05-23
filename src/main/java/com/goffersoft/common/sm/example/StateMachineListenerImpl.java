/**
 ** File: StateMachineListenerImpl.java
 **
 ** Description : StateMachineListenerImpl class - extends the UdpConnectionListenerImpl
 **               factory default listener
 **
 ** Date           Author                          Comments
 ** 08/30/2013     Prakash Easwar                  Created  
 **/
package com.goffersoft.common.sm.example;

import java.io.IOException;
import java.net.DatagramPacket;

import org.apache.log4j.Logger;

import com.goffersoft.common.net.UdpConnection;
import com.goffersoft.common.net.UdpConnectionListenerImpl;
import com.goffersoft.common.utils.PrintUtils;

public class StateMachineListenerImpl
        extends
        UdpConnectionListenerImpl {

    private ExampleStateMachine sm = new ExampleStateMachine();
    private static final Logger log = Logger
            .getLogger(StateMachineListenerImpl.class);
    String saveCmd;

    private String getCurrentCmdStr() {
        if (saveCmd != null) {
            return saveCmd;
        }
        return "";
    }

    private String getNextCmdStr() {
        if (saveCmd == null) {
            return "Begin";
        }
        if (saveCmd.equals("Begin")) {
            return "Begin/End/Pause/Resume";
        } else if (saveCmd.equals("End")) {
            return "Begin/End/Exit";
        } else if (saveCmd.equals("Pause")) {
            return "Pause/Resume/End";
        } else if (saveCmd.equals("Resume")) {
            return "Resume/End/Pause";
        } else if (saveCmd.equals("Exit")) {
            return "Exit/Begin";
        } else {
            return "Unknown";
        }
    }

    @Override
    public void onReceivedPacket(UdpConnection udp, DatagramPacket packet) {
        byte[] data = packet.getData();
        int len = packet.getLength();
        boolean retVal = false;
        String errStr = null;
        String cmdStr = null;

        log
                .info("State Machine Listener Installed: Received UDP Packet: Length="
                        + len);

        log.debug("Packet Details : \n"
                + PrintUtils.byteArrayToHexString(data, 0, packet.getLength(),
                        16));

        String cmd = new String(data, 0, packet.getLength());

        // if(sm.getCurrentState() != null) {
        // log.info("Current State : "+sm.getCurrentState().getState().toString());
        // } else {
        // log.info("Current State : null");
        // }

        if (cmd.contains("Begin")) {
            cmdStr = "Begin";
            retVal = sm.changeState(
                    sm.getState(ExampleStateMachine.States.ACTIVE), null);
        } else if (cmd.contains("End")) {
            cmdStr = "End";
            retVal = sm.changeState(
                    sm.getState(ExampleStateMachine.States.INACTIVE), null);
        } else if (cmd.contains("Pause")) {
            cmdStr = "Pause";
            retVal = sm.changeState(
                    sm.getState(ExampleStateMachine.States.PAUSED), null);
        } else if (cmd.contains("Resume")) {
            cmdStr = "Resume";
            retVal = sm.changeState(
                    sm.getState(ExampleStateMachine.States.ACTIVE), null);
        } else if (cmd.contains("Exit")) {
            cmdStr = "Exit";
            retVal = sm.changeState(
                    sm.getState(ExampleStateMachine.States.EXIT), null);
            if (retVal == true) {
                sm.stop();
            }
        } else {
            if (cmd.contains("Help") == false) {
                errStr =
                        new String(
                                "Invalid Command - cmd="
                                        + cmd
                                        + " Valid Commands are : Begin/End/Pause/Resume/Exit/Help");
            } else {
                errStr =
                        new String(
                                "Help : Valid Commands are : Begin/End/Pause/Resume/Exit/Help");
            }
        }

        if (errStr == null) {
            if (retVal == false) {
                if (saveCmd == null) {
                    errStr = new String("Cannot Execute Command(-->" + cmdStr
                            + "<--)" + " Invalid State Transition."
                            + "Valid Commands From The Initial State are: "
                            + getNextCmdStr());
                } else {
                    errStr = new String("Cannot Execute Command(-->" + cmdStr
                            + "<--)" + " Invalid State Transition."
                            + "Valid Commands From The CurrentState(-->"
                            + getCurrentCmdStr() + "<--) are: "
                            + getNextCmdStr());
                }
            }
        }

        if (errStr != null) {
            log.info(errStr);

            try {
                udp.send(errStr.getBytes(), 0, errStr.length(),
                        packet.getAddress(), 6666);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            if (cmd.contains("Help") == false) {
                saveCmd = cmdStr;
                try {
                    String out =
                            String
                                    .format(
                                            "Received Valid Inout \"Cmd:%s\" : Changed State to \"%s\"",
                                            cmdStr,
                                            cmdStr);
                    udp.send(out.getBytes(), 0, out.length(),
                            packet.getAddress(), 6666);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void onServiceTerminated(UdpConnection udp, Exception error) {
        log.debug("State Machine Listener Installed : Service Terminated ");
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
                .info("State Machine Listener Installed: Received Small UDP Packet: Length="
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
                .info("State Machine Listener Installed: Received Large UDP Packet: Length="
                        + len);

        log.debug("Packet Details : \n"
                + PrintUtils.byteArrayToHexString(data, 0, packet.getLength(),
                        16));
    }
}
