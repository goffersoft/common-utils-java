/**
 ** File: PrintUtils.java
 **
 ** Description : This File contains helper functions read input and
 **               print in a suitable format either to a output stream
 **               or to a string
 **
 ** Date           Author                          Comments
 ** 01/30/2013     Prakash Easwar                  Created  
 */

package com.goffersoft.common.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.log4j.Logger;
import org.apache.xml.serialize.OutputFormat;
import org.apache.xml.serialize.XMLSerializer;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonWriter;
import javax.json.JsonWriterFactory;
import javax.json.stream.JsonGenerator;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

public class PrintUtils {
	private static final Logger log = Logger.getLogger(PrintUtils.class);
	static private Map<String, Boolean> jsonConfig;
	static private JsonWriterFactory jsonWriterFactory;
	
	static {
		// config Map is created for pretty printing.
		jsonConfig  = new HashMap<String, Boolean>();
		// Pretty printing feature is added.
		jsonConfig.put(JsonGenerator.PRETTY_PRINTING, true);
		jsonWriterFactory = Json.createWriterFactory(jsonConfig);
	}
	
	static public void prettyPrintXML(InputStream is, OutputStream os, String outputPrefix, String outputSuffix) {
		try{ 
		  DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		   dbFactory.setNamespaceAware(true);
		   Document doc = dbFactory.newDocumentBuilder().parse(is);
		   OutputFormat format = new OutputFormat();
		   format.setLineWidth(120);
		   format.setIndenting(true);
		   format.setIndent(2);
		   format.setEncoding("UTF-8");
		   
		   StringWriter stringOut = new StringWriter();
		   XMLSerializer serial = new XMLSerializer(stringOut, format);
		   serial.serialize(doc);
		   if(os == null) {
			   log.info((((outputPrefix == null)?"":outputPrefix) + 
					   	stringOut.toString() +
					   	((outputSuffix == null)?"":outputSuffix)));
		   }
		   else {
			   os.write( (((outputPrefix == null)?"":outputPrefix) +
					   		stringOut.toString().getBytes() +
					   		((outputSuffix == null)?"":outputSuffix)).getBytes());
		   }
        } 
        catch (Exception e) { 
            e.printStackTrace(); 
        } 
	}
	
	static public void prettyPrintXML(InputStream is, OutputStream os) {
		prettyPrintXML(is, os, null, null);
	}
	
	static public void prettyPrintXML(InputStream is, OutputStream os, String outputPrefix) {
		prettyPrintXML(is, os, outputPrefix, null);
	}
	
	static public void prettyPrintXML(String xmldoc, OutputStream os, String outputPrefix, String outputSuffix) {
		try{ 
		  DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		   dbFactory.setNamespaceAware(true);
		   Document doc = dbFactory.newDocumentBuilder().parse(new InputSource(new StringReader(xmldoc)));
		   
		   OutputFormat format = new OutputFormat();
		   format.setLineWidth(120);
		   format.setIndenting(true);
		   format.setIndent(2);
		   format.setEncoding("UTF-8");
		   
		   StringWriter stringOut = new StringWriter();
		   XMLSerializer serial = new XMLSerializer(stringOut, format);
		   serial.serialize(doc);
		   if(os == null) {
			   log.info((((outputPrefix == null)?"":outputPrefix) + 
					   	stringOut.toString() +
					   	((outputSuffix == null)?"":outputSuffix)));
		   }
		   else {
			   os.write( (((outputPrefix == null)?"":outputPrefix) +
					   		stringOut.toString().getBytes() +
					   		((outputSuffix == null)?"":outputSuffix)).getBytes());
		   }
        } 
        catch (Exception e) { 
            e.printStackTrace(); 
        } 
	}
	
	static public void prettyPrintXML(String xmldoc, OutputStream os) {
		prettyPrintXML(xmldoc, os, null, null);
	}
	
	static public void prettyPrintXML(String xmldoc, OutputStream os, String outputPrefix) {
		prettyPrintXML(xmldoc, os, outputPrefix, null);
	}
	
	static public String byteArrayToHexString(byte[] data, int offset, int len, int rowsize) {
		StringBuffer sb = new StringBuffer();
		
		for(int i = offset; i < Math.min((offset + len), data.length); i++) {
			if( (i != 0) && ((i%rowsize) == 0)) {
				sb.append("\n");
			}
			sb.append(String.format("%02x ", data[i]&0xff));
		}
		
		return sb.toString();
	}
	
	static public void prettyPrintJSON(String jsondoc, OutputStream os, String outputPrefix, String outputSuffix) {
		 
		try {
			JsonReader jsonReader = Json.createReader(new StringReader(jsondoc));
			JsonObject jsonObject = jsonReader.readObject();
			
			StringWriter jsonFormattedOutput = new StringWriter();
			JsonWriter jsonWriter = jsonWriterFactory.createWriter(jsonFormattedOutput);
			jsonWriter.write(jsonObject);
			
			if(os == null) {
			   log.info((((outputPrefix == null)?"":outputPrefix) + 
					   	jsonFormattedOutput.toString() +
					   	((outputSuffix == null)?"":outputSuffix)));
			}
			else {
				os.write( (((outputPrefix == null)?"":outputPrefix) +
				   		jsonFormattedOutput.toString().getBytes() +
				   		((outputSuffix == null)?"":outputSuffix)).getBytes());
			}
		} catch (IOException e) {
			e.printStackTrace();
		};
	}
	
	static public void prettyPrintJSON(String jsondoc, OutputStream os) {
		prettyPrintJSON(jsondoc, os, null, null);
	}
	
	static public void prettyPrintJSON(String jsondoc, OutputStream os, String outputPrefix) {
		prettyPrintJSON(jsondoc, os, outputPrefix, null);
	}
	
	static public void prettyPrintJSON(JsonObject jsonObject, OutputStream os, String outputPrefix, String outputSuffix) {
		 
		try {
			
			StringWriter jsonFormattedOutput = new StringWriter();
			JsonWriter jsonWriter = jsonWriterFactory.createWriter(jsonFormattedOutput);
			jsonWriter.write(jsonObject);
			
			if(os == null) {
			   log.info((((outputPrefix == null)?"":outputPrefix) + 
					   	jsonFormattedOutput.toString() +
					   	((outputSuffix == null)?"":outputSuffix)));
			}
			else {
				os.write( (((outputPrefix == null)?"":outputPrefix) +
				   		jsonFormattedOutput.toString().getBytes() +
				   		((outputSuffix == null)?"":outputSuffix)).getBytes());
			}
		} catch (IOException e) {
			e.printStackTrace();
		};
	}
	
	static public void prettyPrintJSON(JsonObject jsonObject, OutputStream os) {
		prettyPrintJSON(jsonObject, os, null, null);
	}
	
	static public void prettyPrintJSON(JsonObject jsonObject, OutputStream os, String outputPrefix) {
		prettyPrintJSON(jsonObject, os, outputPrefix, null);
	}
}
