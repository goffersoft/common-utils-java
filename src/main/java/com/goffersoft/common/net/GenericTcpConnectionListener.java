package com.goffersoft.common.net;

public interface GenericTcpConnectionListener<TcpConnectionType> extends GenericConnectionListener {
	/** When new TCP data is received. */
	public void onReceivedData(TcpConnectionType tcp, byte[] data, int offset, int length);
	
	/** when the tcp connection is terminated */
	public void onConnectionTerminated(TcpConnectionType tcp, Exception error);
	
	/** when a tcp data length is less than the minimum configured 
	 *  use the following methods to configure 
	 *  minimum packet sizes. 
	 *  setMinimumReceivePacketLength
	 *  getMinimumReceivePacketLength   
	 */
	public void onErrorReceivedSmallPacket(TcpConnectionType tcp, byte[] data, int offset, int length);
	
	/** when a tcp data length is greater than the maximum configured 
	 *  use the following methods to configure 
	 *  maximum packet sizes. 
	 *  setMaximumReceivePacketLength
	 *  getMaximumReceivePacketLength   
	 */
	public void onErrorReceivedLargePacket(TcpConnectionType tcp, byte[] data, int offset, int length);
}
