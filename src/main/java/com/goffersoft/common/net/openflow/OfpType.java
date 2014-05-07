/**
 ** File: OfpType.java
 **
 ** Description : OpenFlow Type Enumeration class
 **               -- OpenFlow Switch Specification Version 1.1.0 - February 28th, 2011
 **
 ** Date           Author                          Comments
 ** 08/31/2013     Prakash Easwar                  Created  
 **/
package com.goffersoft.common.net.openflow;

import org.apache.log4j.Logger;

public enum OfpType {
	OFPT_HELLO(0),
	OFPT_ERROR(1),
	OFPT_ECHO_REQUEST(2),
	OFPT_ECHO_REPLY(3),
	OFPT_EXPERIMENTER(4),
	OFPT_FEATURES_REQUEST(5),
	OFPT_FEATURES_REPLY(6),
	OFPT_GET_CONFIG_REQUEST(7),
	OFPT_GET_CONFIG_REPLY(8),
	OFPT_SET_CONFIG(9),
	OFPT_PACKET_IN(10),
	OFPT_FLOW_REMOVED(11),
	OFPT_PORT_STATUS(12),
	OFPT_PACKET_OUT(13),
	OFPT_FLOW_MOD(14),
	OFPT_GROUP_MOD(15),
	OFPT_PORT_MOD(16),
	OFPT_TABLE_MOD(17),
	OFPT_MULTIPART_REQUEST(18),
	OFPT_MULTIPART_REPLY(19),
	OFPT_BARRIER_REQUEST(20),
	OFPT_BARRIER_REPLY(21),
	OFPT_ROLE_REQUEST(24),
	OFPT_ROLE_REPLY(25),
	OFPT_GET_ASYNC_REQUEST(26),
	OFPT_GET_ASYNC_REPLY(27),
	OFPT_SET_ASYNC(28),
	OFPT_METER_MOD(29),
	OFPT_ROLE_STATUS(30),
	OFPT_TABLE_STATUS(31),
	OFPT_REQUEST_FORWARD(32),
	OFPT_BUNDLE_CONTROL(33),
	OFPT_BUNDLE_ADD_MESSAGE(34),
	OFPT_UNK_MESSAGE(255)
	;
	
	private static final Logger log = Logger.getLogger(OfpType.class);
	
	public static final OfpType type[] = {
		OFPT_HELLO,
		OFPT_ERROR,
		OFPT_ECHO_REQUEST,
		OFPT_ECHO_REPLY,
		OFPT_EXPERIMENTER,
		OFPT_FEATURES_REQUEST,
		OFPT_FEATURES_REPLY,
		OFPT_GET_CONFIG_REQUEST,
		OFPT_GET_CONFIG_REPLY,
		OFPT_SET_CONFIG,
		OFPT_PACKET_IN,
		OFPT_FLOW_REMOVED,
		OFPT_PORT_STATUS,
		OFPT_PACKET_OUT,
		OFPT_FLOW_MOD,
		OFPT_GROUP_MOD,
		OFPT_PORT_MOD,
		OFPT_TABLE_MOD,
		OFPT_MULTIPART_REQUEST,
		OFPT_MULTIPART_REPLY,
		OFPT_BARRIER_REQUEST,
		OFPT_BARRIER_REPLY,
		OFPT_ROLE_REQUEST,
		OFPT_ROLE_REPLY,
		OFPT_GET_ASYNC_REQUEST,
		OFPT_GET_ASYNC_REPLY,
		OFPT_SET_ASYNC,
		OFPT_METER_MOD,
		OFPT_ROLE_STATUS,
		OFPT_TABLE_STATUS,
		OFPT_REQUEST_FORWARD,
		OFPT_BUNDLE_CONTROL,
		OFPT_BUNDLE_ADD_MESSAGE,
		OFPT_UNK_MESSAGE,
	};
	
	public static final String typeDescr[] = {
		"Hello - Immutable - symmetric message",
		"Error - Immutable - symmetric message",
		"Echo Request - Immutable - symmetric message",
		"Echo Reply - Immutable - symmetric message",
		"Experimenter - Immutable - symmetric message",
		"Feature Request - Switch Config - Controller/switch message",
		"Feature Reply - Switch Config - Controller/switch message",
		"Get Config Request - Switch Config - Controller/switch message",
		"Get Config Reply - Switch Config - Controller/switch message",
		"Set Config - Switch Config - Controller/switch message",
		"Packet In - Async message",
		"Flow Removed - Async message",
		"Port Status - Async message",
		"Packet Out - Controller command message - Controller/switch message",
		"Flow Mod - Controller command message - Controller/switch message",
		"Group Mod - Controller command message - Controller/switch message",
		"Port Mod - Controller command message - Controller/switch message",
		"Table Mod - Controller command message - Controller/switch message",
		"Multipart Request - multipart message - Controller/switch message",
		"Multipart Reply - multipart message - Controller/switch message",
		"Barrier Request - Barrier message - Controller/switch message",
		"Barrier Reply - Barrier message - Controller/switch message",
		"Role Request - Controller role change request message - Controller/switch message",
		"Role Reply - Controller role change request message - Controller/switch message",
		"Get Async Request - Async message config - Controller/switch message",
		"Get Async Reply - Async message config - Controller/switch message",
		"Set Async - Async message config - Controller/switch message",
		"Meter Mod - Meter and rate limiters config message - Controller/switch message",
		"Role Status - Controller role change event messages",
		"Table Status - Async Message",
		"Request Forward - Request forwarding by switch",
		"Bundle Control - Bundle operations (multiple messages as a single operation)",
		"Bundle Add Message - Bundle operations (multiple messages as a single operation)",
		"Unknown Message Id - This message id has not been defined!"
	};
	
	private byte reqid;
	
	private OfpType(int id) {
		setValueInternal((byte)id);
	}

	public byte getValue() {
		return reqid;
	}

	public void setValue(byte reqid) {
		if(this == OFPT_UNK_MESSAGE) {
			setValueInternal(reqid);
		}
	}
	
	private void setValueInternal(byte reqid) {
		this.reqid = reqid;
	}
	
	public String getDescr() {
		if(this == OFPT_UNK_MESSAGE) {
			return String.format("%s msgId=%d(%02x)", 
									typeDescr[ordinal()],
									(reqid&0xff),
									(reqid&0xff));
		} else {
			return typeDescr[ordinal()];
		}
	}
	
	@Override
	public String toString() {
		return getDescr();
	}
	
	public static OfpType getEnum(byte id) {
		if(id >= 0 && id <= 21) {
			return type[id];
		} else if (id >=24 && id <= 34) {
			return type[id - 2];
		} else {
			OfpType tmp = OFPT_UNK_MESSAGE;
			tmp.setValueInternal(id);
			return tmp;
		}
	}
	
	public boolean isBunbleMsg() {
		switch(this) {
			case OFPT_BUNDLE_CONTROL :
			case OFPT_BUNDLE_ADD_MESSAGE :
				return true;
			default :
				return false;
		}
	}
	
	public boolean isSymmetricMsg() {
		switch(this) {
			case OFPT_HELLO :
			case OFPT_ERROR :
			case OFPT_ECHO_REQUEST :
			case OFPT_ECHO_REPLY :
			case OFPT_EXPERIMENTER :
				return true;
			default :
				return false;
		}
	}
	
	public boolean isAsyncMsg() {
		switch(this) {
			case OFPT_PACKET_IN :
			case OFPT_FLOW_REMOVED :
			case OFPT_PORT_STATUS :
			case OFPT_ROLE_STATUS :
			case OFPT_TABLE_STATUS :
			case OFPT_REQUEST_FORWARD :
				return true;
			default :
				return false;
		}
	}
	
	public boolean isSwitchConfigMsg() {
		switch(this) {
			case OFPT_FEATURES_REQUEST :
			case OFPT_FEATURES_REPLY :
			case OFPT_GET_CONFIG_REQUEST :
			case OFPT_GET_CONFIG_REPLY :
			case OFPT_SET_CONFIG :
				return true;
			default :
				return false;
		}
	}
	
	public boolean isAsyncConfigMsg() {
		switch(this) {
			case OFPT_GET_ASYNC_REQUEST :
			case OFPT_GET_ASYNC_REPLY :
			case OFPT_SET_ASYNC :
				return true;
			default :
				return false;
		}
	}
	
	public boolean isMeterConfigMsg() {
		switch(this) {
			case OFPT_METER_MOD :
				return true;
			default :
				return false;
		}
	}
	
	public boolean isControllerCmdMsg() {
		switch(this) {
			case OFPT_PACKET_OUT :
			case OFPT_FLOW_MOD :
			case OFPT_GROUP_MOD :
			case OFPT_PORT_MOD :
			case OFPT_TABLE_MOD : 
			case OFPT_METER_MOD :
				return true;
			default :
				return false;
		}
	}
	
	public boolean isMultipartMsg() {
		switch(this) {
			case OFPT_MULTIPART_REQUEST :
			case OFPT_MULTIPART_REPLY :
				return true;
			default :
				return false;
		}
	}
	
	public boolean isBarrierMsg() {
		switch(this) {
			case OFPT_BARRIER_REQUEST :
			case OFPT_BARRIER_REPLY :
				return true;
			default :
				return false;
		}
	}
	
	public boolean isRoleChgMsg() {
		switch(this) {
			case OFPT_ROLE_REQUEST :
			case OFPT_ROLE_REPLY :
				return true;
			default :
				return false;
		}
	}
	
	public boolean isControllerToSwitchMsg() {
		return (isSymmetricMsg() || (!isAsyncMsg()));
	}
	
	public boolean isSwitchToControllerMsg() {
		return (isSymmetricMsg() || isAsyncMsg());
	}
	
	public boolean isUnknownMsgType() {
		if(this == OFPT_UNK_MESSAGE) {
			return true;
		}
		return false;
	}
}