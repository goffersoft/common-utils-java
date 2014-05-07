/**
 ** File: PatternUtils.java
 **
 ** Description : This File contains Pattern related helper functions
 **
 ** Date           Author                          Comments
 ** 01/30/2013     Prakash Easwar                  Created  
 */

package com.goffersoft.common.utils;

import org.apache.log4j.Logger;

public class PatternUtils {
	private static final Logger log = Logger.getLogger(PatternUtils.class);
	
	static public boolean memset(byte[] s, int offset, int length, byte pattern) {
		if(s == null) {
		return false;
		}
		
		if((offset + length) > s.length) {
			return false;
		}
		
		for(int i = offset; i < length; i++) {
			s[i] = pattern;
		}
		return true;
	}
	
	static public boolean byteCompare(byte[] s1, int s1offset, 
								byte[] s2, int s2offset, int length) {
		if(s1 == null) {
    		return false;
    	}
    	
    	if(s2 == null) {
    		return false;
    	}
    	
    	if((s1offset + length) > s1.length) {
    		return false;
    	}
    	
    	if((s2offset + length) > s2.length) {
    		return false;
    	}
    	
    	for(int i = 0, j = s1offset, k = s2offset; i < length; i++, j++, k++) {
    		if(s1[j] != s2[k]) {
    			return false;
    		}
    	}
    	return true;
	}
	
	static public int startsWith(byte[] data, int dataoffset, int datalength, 
									byte[] pattern, int patternoffset, int patternlength) {
		if(data == null) {
    		return -1;
    	}
    	
    	if(pattern == null) {
    		return -1;
    	}
    	
    	if((dataoffset + datalength) > data.length) {
    		return -1;
    	}
    	
    	if((patternoffset + patternlength) > pattern.length) {
    		return -1;
    	}
    	
    	if(datalength < patternlength) {
    		return -1;
    	}
    	
    	if(byteCompare(data, dataoffset, pattern, 
    						patternoffset, patternlength ) == false) {
    		return -1;
    	}
    	
    	return dataoffset;
	}
	
	static public int startsWith(byte[] data, byte[] pattern) {
		if(data == null) {
    		return -1;
    	}
    	
    	if(pattern == null) {
    		return -1;
    	}
    	return startsWith(data,0,data.length, pattern,0,pattern.length);
    }
    
    static public int startsWith(byte[] data, int dataoffset, byte[] pattern) {
    	if(data == null) {
    		return -1;
    	}
    	
    	if(pattern == null) {
    		return -1;
    	}
    	return startsWith(data,dataoffset,data.length, pattern,0,pattern.length);
    }
    
    static public int startsWith(byte[] data, int dataoffset, int datalength, byte[] pattern) {
    	if(data == null) {
    		return -1;
    	}
    	
    	if(pattern == null) {
    		return -1;
    	}
    	return startsWith(data,dataoffset,datalength, pattern,0,pattern.length);
    }
	
	static public int endsWith(byte[] data, int dataoffset, int datalength, 
									byte[] pattern, int patternoffset, int patternlength) {
		if(data == null) {
    		return -1;
    	}
    	
    	if(pattern == null) {
    		return -1;
    	}
    	
    	if((dataoffset + datalength) > data.length) {
    		return -1;
    	}
    	
    	if((patternoffset + patternlength) > pattern.length) {
    		return -1;
    	}
    	
    	if(datalength < patternlength) {
    		return -1;
    	}
    	
    	if(byteCompare(data, dataoffset + datalength - patternlength, pattern, 
				patternoffset, patternlength ) == false) {
    		return -1;
    	}
    	
    	return dataoffset + datalength - patternlength;
	}
	
    static public int endsWith(byte[] data, byte[] pattern) {
    	if(data == null) {
    		return -1;
    	}
    	
    	if(pattern == null) {
    		return -1;
    	}
    	return endsWith(data,0,data.length, pattern,0,pattern.length);
    }
    
    static public int endsWith(byte[] data, int dataoffset, byte[] pattern) {
    	if(data == null) {
    		return -1;
    	}
    	
    	if(pattern == null) {
    		return -1;
    	}
    	return endsWith(data,dataoffset,data.length, pattern,0,pattern.length);
    }
    
    static public int endsWith(byte[] data, int dataoffset, int datalength, byte[] pattern) {
    	if(data == null) {
    		return -1;
    	}
    	
    	if(pattern == null) {
    		return -1;
    	}
    	return endsWith(data,dataoffset,datalength, pattern,0,pattern.length);
    }
	
	/**
	 * Knuth-Morris-Pratt Algorithm for Pattern Matching
	 *
     * Finds the first occurrence of the pattern in the text.
     */
    static public int contains(byte[] data, int dataoffset, int datalength, 
    							byte[] pattern, int patternoffset, int patternlength) {
    	if(data == null) {
    		return -1;
    	}
    	
    	if(pattern == null) {
    		return -1;
    	}
    	
    	if((dataoffset + datalength) > data.length) {
    		return -1;
    	}
    	
    	if((patternoffset + patternlength) > pattern.length) {
    		return -1;
    	}
    	
    	if(datalength < patternlength) {
    		return -1;
    	}
    	
        int[] failure = computeFailure(pattern, patternoffset, patternlength);

        int j = patternoffset;

        for (int i = dataoffset; i < (dataoffset+datalength); i++) {
            while (j > patternoffset && pattern[j] != data[i]) {
                j = failure[j - patternoffset - 1];
            }
            if (pattern[j] == data[i]) { j++; }
            if (j == (patternoffset+patternlength)) {
                return i - pattern.length + 1;
            }
        }
        return -1;
    }
    
    static public int contains(byte[] data, byte[] pattern) {
    	if(data == null) {
    		return -1;
    	}
    	
    	if(pattern == null) {
    		return -1;
    	}
    	return contains(data,0,data.length, pattern,0,pattern.length);
    }
    
    static public int contains(byte[] data, int dataoffset, byte[] pattern) {
    	if(data == null) {
    		return -1;
    	}
    	
    	if(pattern == null) {
    		return -1;
    	}
    	return contains(data,dataoffset,data.length, pattern,0,pattern.length);
    }
    
    static public int contains(byte[] data, int dataoffset, int datalength, byte[] pattern) {
    	if(data == null) {
    		return -1;
    	}
    	
    	if(pattern == null) {
    		return -1;
    	}
    	return contains(data,dataoffset,datalength, pattern,0,pattern.length);
    }

    /**
     *
     * Knuth-Morris-Pratt Algorithm for Pattern Matching
     *
     * Computes the failure function using a boot-strapping process,
     * where the pattern is matched against itself.
     */
    static private int[] computeFailure(byte[] pattern, int patternoffset, int patternlength) {
        int[] failure = new int[patternlength];

        int j = patternoffset;
        for (int i = j+1; i < (patternoffset+patternlength); i++) {
            while (j > patternoffset && pattern[j] != pattern[i]) {
                j = failure[j - 1];
            }
            if (pattern[j] == pattern[i]) {
                j++;
            }
            failure[i - patternoffset] = j;
        }

        return failure;
    }
}
