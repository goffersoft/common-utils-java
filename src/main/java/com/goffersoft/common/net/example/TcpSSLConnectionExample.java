package com.goffersoft.common.net.example;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

import org.apache.log4j.Logger;

public class TcpSSLConnectionExample {
    private static final Logger log = Logger
            .getLogger(TcpSSLConnectionExample.class);

    public static void main(String[] arstring) {
        try {
            SSLSocketFactory sslsocketfactory =
                    (SSLSocketFactory) SSLSocketFactory.getDefault();
            log.debug("here- start");
            SSLSocket sslsocket =
                    (SSLSocket) sslsocketfactory
                            .createSocket("localhost", 6666);
            log.debug("here- start");
            InputStream inputstream = System.in;
            InputStreamReader inputstreamreader =
                    new InputStreamReader(inputstream);
            BufferedReader bufferedreader =
                    new BufferedReader(inputstreamreader);

            OutputStream outputstream = sslsocket.getOutputStream();
            OutputStreamWriter outputstreamwriter =
                    new OutputStreamWriter(outputstream);
            BufferedWriter bufferedwriter =
                    new BufferedWriter(outputstreamwriter);

            String string = null;
            log.debug("here");
            int i = 0;
            while ((string = bufferedreader.readLine()) != null) {
                // while ((string = arstring[i++]) != null) {
                log.debug(string);
                bufferedwriter.write(string + '\n');
                bufferedwriter.flush();
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}