/**
 ** File: UdpConnectionListener.java
 **
 ** Description : UdpConnectionListener interface - listener interface
 **               implement this class and register with udp server
 **
 ** Date           Author                          Comments
 ** 08/30/2013     Prakash Easwar                  Created  
 **/
package com.goffersoft.common.net;

import java.net.DatagramPacket;

public interface UdpConnectionListener  extends GenericConnectionListener
{
	/** When a new UDP datagram is received. */
	public void onReceivedPacket(UdpConnection udp, DatagramPacket packet);
	
	/** when the service is terminated */
	public void onServiceTerminated(UdpConnection udp, Exception error);
	
	/** when a small packet is received 
	 *  use the following methods to configure 
	 *  minimum packet sizes. 
	 *  setMinimumReceivePacketLength
	 *  getMinimumReceivePacketLength   
	 */
	public void onErrorReceivedSmallPacket(UdpConnection udp, DatagramPacket packet);
	
	/** when a large packet is received 
	 *  use the following methods to configure 
	 *  maximum packet sizes. 
	 *  setMaximumReceivePacketLength
	 *  getMaximumReceivePacketLength   
	 */
	public void onErrorReceivedLargePacket(UdpConnection udp, DatagramPacket packet);
}