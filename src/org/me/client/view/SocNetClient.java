package org.me.client.view;

import org.me.server.SocNetProtocol;

import java.io.*;
import java.net.Socket;


public class SocNetClient {

    private Socket socNetSocket;
    private PrintWriter outToServer;
    private BufferedReader inFromServer;


    public SocNetClient() {
        String hostName = SocNetProtocol.address;
        int portNumber = SocNetProtocol.portNo;


        try {
            socNetSocket = new Socket(hostName, portNumber);
        } catch (IOException e) {
            System.err.println("Unable to create the client socket!");
        }
        try {
            outToServer = new PrintWriter(socNetSocket.getOutputStream(), true);
        } catch (IOException e) {
            System.err.println("Unable to create the output stream!");
        }
        try {
            inFromServer = new BufferedReader(new InputStreamReader(socNetSocket.getInputStream()));
        } catch (IOException e) {
            System.err.println("Unable to create the input stream!");
        }

    }

    public static void main(String[] args) {
        SocNetClient client = new SocNetClient();

        BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
        String fromUser;
        String fromServer;

        try {
            while ((fromServer = client.inFromServer.readLine())!= null) {
                String[] res = fromServer.split("\t");
                System.out.println("******************** server says : ");
                for (String re : res) {
                    System.out.println(re);
                }
                System.out.println("********************");

                System.out.print(">>> ");
                fromUser = stdIn.readLine();
                if (fromUser != null) {
                    System.out.println("----- Client To server : \"" + fromUser + "\" -----");
                    client.outToServer.println(fromUser);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            if (client.inFromServer != null)
                client.inFromServer.close();
            if (client.outToServer != null)
                client.outToServer.close();
            if (client.socNetSocket != null)
                client.socNetSocket.close();
        } catch (Exception e) {
            System.out.println("Unable to close the streams, very bad exception!");
            e.printStackTrace();
        }
    }
}
