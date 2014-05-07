/**
 ** File: OfpMatch.java
 **
 ** Description : OpenFlow Match class
 **               -- OpenFlow Switch Specification Version 1.1.0 - February 28th, 2011
 **
 ** Date           Author                          Comments
 ** 08/31/2013     Prakash Easwar                  Created  
 **/
package com.goffersoft.common.net.openflow;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.log4j.Logger;

public class OfpMatch implements OfpSerializable<OfpMatch>{
	
	private static final Logger log = Logger.getLogger(OfpMatch.class);
	
	static public enum OfpMatchType {
		OFPMT_STANDARD(0),
		OFPMT_OXM(1),
		OFPMT_UNK(0),
		;
		
		static final String mdescr[] = {
			"Openflow Standard Match - Deprecated",
			"Openflow Extensible Match",
			"Unknown Match Type,"
		};
		
		private short mtype;
		
		private OfpMatchType(int type) {
			setMatchTypeInternal((short)type);
		}

		public short getValue() {
			return mtype;
		}

		public void setValue(short mtype) {
			if(this == OFPMT_UNK) {
				this.mtype = mtype;
			}
		}
		
		private void setMatchTypeInternal(short mtype) {
			this.mtype = mtype;
		}
		
		public OfpMatchType getEnum(short type) {
			int tmp = type&0xffff;
			
			if(tmp == 0) {
				return OFPMT_STANDARD;
			} else if (tmp == 1) {
				return OFPMT_OXM;
			} else {
				OfpMatchType tmp1 = OFPMT_UNK;
				setMatchTypeInternal(type);
				return tmp1;
			}
		}
		
		public boolean isDeprecated() {
			if(this == OFPMT_STANDARD) {
				return true;
			}
			return false;
		}
		
		public String getDescr() {
			return mdescr[ordinal()];
		}
		
		@Override
		public String toString() {
			return getDescr();
		}
	}
	
	private OfpMatchType type;
	private short length; 
	
	public OfpMatchType getType() {
		return type;
	}

	public void setType(OfpMatchType type) {
		this.type = type;
	}

	public short getLength() {
		return length;
	}
	
	public short getAlignedLength() {
		return (short)(((length+7)/8)*8);
	}

	public void setLength(short length) {
		this.length = length;
	}

	@Override
	public OfpMatch fromInputStream(InputStream is) throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public OfpMatch fromByteArray(byte[] ba) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public OfpMatch fromByteArray(byte[] ba, int offset) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public byte[] toByteArray() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public byte[] toByteArray(byte[] ba) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public byte[] toByteArray(byte[] ba, int offset) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public OutputStream toOutputStream(OutputStream os) throws IOException {
		// TODO Auto-generated method stub
		return null;
	}
}
