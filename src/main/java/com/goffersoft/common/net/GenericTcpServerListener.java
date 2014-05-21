package com.goffersoft.common.net;

public interface GenericTcpServerListener<TcpServerType, TcpConnectionType> extends GenericConnectionListener {
	/** When the TCP server is terminated */
	public void onServerTerminated(TcpServerType tcp_server, Exception error);
	
	/** When a new incoming connection is established */
	public void onIncomingConnection(TcpServerType tcp_server, TcpConnectionType tcp_conn);
}
