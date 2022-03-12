package com.example.networktest;


import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;


public class Server implements Runnable {
    String matrNumber;
    String serverMassage;

    @Override
    public void run() {

        String domain = "se2-isys.aau.at";
        int port = 53212;
        BufferedReader bfr;
        DataOutputStream out;
        Socket sock;

        try {
            sock = new Socket(domain, port);
            bfr = new BufferedReader(new InputStreamReader(sock.getInputStream()));
            out = new DataOutputStream(sock.getOutputStream());
            out.writeBytes(matrNumber);
            serverMassage = bfr.readLine();
            sock.close();
        } catch (IOException e) {
            System.out.println("failed");
            e.printStackTrace();
        }
    }

    public void setMatrNumber(String matrNumber) {
        this.matrNumber = matrNumber;
    }

    public String getServerMassage() {
        return serverMassage;
    }
}
