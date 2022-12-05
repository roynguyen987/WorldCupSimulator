package edu.ucdenver.clientproject1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
import java.net.*;
import java.io.*;
import java.util.Scanner;

public class Client {
    private final int serverPort;
    private final String serverIp;
    private boolean isConnected = true;

    private Socket serverConnection;
    private PrintWriter output;
    private BufferedReader input;

    /**
     * Client Constructor
     *
     * @param ip   ip
     * @param port port
     */
    public Client(String ip, int port) {
        this.serverPort = port;
        this.serverIp = ip;
        this.isConnected = false;
    }

    /**
     * Basic Client Constructor
     */
    public Client() {
        this("127.0.0.1", 10003);
    }


    /**
     * isConnected
     * Checks if client is connected
     *
     * @return isConnected
     */
    public boolean isConnected() {
        return this.isConnected;
    }

    /**
     * getOutputStream
     *
     * @return outputStream
     * @throws IOException Throws IOException
     */
    private PrintWriter getOutputStream() throws IOException {
        return new PrintWriter(this.serverConnection.getOutputStream(), true);
    }

    /**
     * getInputStream
     *
     * @return inputStream
     * @throws IOException Throws IOException
     */
    private BufferedReader getInputStream() throws IOException {
        return new BufferedReader(new InputStreamReader(this.serverConnection.getInputStream()));
    }

    /**
     * Connect function
     * Connects the client to the server
     */
    public void connect() {
        displayMessage("Attempting Connection to Server");
        try {
            this.serverConnection = new Socket(this.serverIp, this.serverPort);
            this.isConnected = true;
            this.output = this.getOutputStream();
            this.input = this.getInputStream();

            getServerInitialResponse();

        } catch (IOException e) {
            this.input = null;
            this.output = null;
            this.serverConnection = null;
            this.isConnected = false;
        }
    }

    /**
     * disconnect function
     * disconnects the client from the server
     */
    public void disconnect() {
        displayMessage("\n>> Terminating Client Connection to Server");
        try {
            this.input.close();
        } catch (IOException | NullPointerException e) {
            e.printStackTrace();
        }
        try {
            this.output.close();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        try {
            this.serverConnection.close();
        } catch (IOException | NullPointerException e) {
            e.printStackTrace();
        }
    }


    /**
     * gets the servers initial response
     *
     * @throws IOException
     */
    public void getServerInitialResponse() throws IOException {
        String srvResponse = this.input.readLine();
        displayMessage("SERVER >> " + srvResponse);
    }

    /**
     * gets the server response
     *
     * @return srvResponse
     * @throws IOException Throws IOException
     */

    public String getResponse() throws IOException {
        String srvResponse = this.input.readLine();
        displayMessage("SERVER >> " + srvResponse);
        return srvResponse;
    }

    /**
     * sendRequest Function
     * Sends a request to the server
     *
     * @param request the request
     * @throws IOException Throws IOException
     */
    public void sendRequest(String request) throws IOException {
        this.output.println(request);
        displayMessage("CLIENT >> " + request);
    }

    /**
     * displayMessage
     *
     * @param message the message to be displayed
     */
    private void displayMessage(String message) {
        System.out.println(message);
    }

}

