package com.example.networktest;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class TCPClient extends Thread {

    private String matrNumber;
    private String serverMassage;

    public TCPClient(String matrNumber) {
        this.matrNumber = matrNumber;
    }

    @Override
        public void run() {

            String domain = "143.205.174.165";
            int port = 53212;
            BufferedReader bfr;
            DataOutputStream out;
            Socket sock;

            try {
                sock = new Socket(domain, port);
                bfr = new BufferedReader(new InputStreamReader(sock.getInputStream()));
                out = new DataOutputStream(sock.getOutputStream());
                out.writeBytes(matrNumber +'\n');
                out.flush();
                serverMassage = bfr.readLine();
                sock.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    public String getOutput(){
        return serverMassage;
    }

}