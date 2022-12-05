package edu.ucdenver.server;

import java.io.*;
import java.net.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import edu.ucdenver.tournament.Tournament;


public class Server {
    private final int port;
    private final int backlog;
    private int connectionCounter;

    private Tournament tournament;
    private ServerSocket serverSocket;

    public ClientWorker cw;


    /**
     * Server constructor
     * Initializes the port, backlog, connectionCounter, and the new tournament
     *
     * @param port
     * port used for connection
     * @param backlog
     * backlog
     */
    public Server(int port, int backlog) {
        this.port = port;
        this.backlog = backlog;
        this.connectionCounter = 0;
        this.tournament = new Tournament("", null, null);
    }

    /**
     * Basic Server Constructor
     */
    public Server() {
        this(10003, 10);
    }

    /**
     * waitForClientConnection Function
     * Waits for the client connection to the server
     *
     * @return clientConnection
     * @throws IOException
     * throws IOException
     */
    private Socket waitForClientConnection() throws IOException {
        System.out.println("Waiting for connection...\n");
        Socket clientConnection = this.serverSocket.accept();
        this.connectionCounter++;
        displayMessage("Connection " + this.connectionCounter + " accepted from: " + clientConnection.getInetAddress().getHostName());
        return clientConnection;
    }

    /**
     * runServer function
     * Runs the server
     */
    public void runServer() {
        ExecutorService executorService = Executors.newCachedThreadPool();
        try {
            this.serverSocket = new ServerSocket(this.port, this.backlog);

            while (true) {
                try {
                    Socket clientConnection = this.waitForClientConnection();

                    this.cw = new ClientWorker(clientConnection, this.tournament, this.connectionCounter);

                    executorService.execute(cw);

                } catch (IOException ioe) {
                    displayMessage("\nServer Terminated");
                    ioe.printStackTrace();
                }
            }
        } catch (IOException ioe) {
            System.out.println("\n Cannot open the server \n");
            executorService.shutdown();
            ioe.printStackTrace();
        }

    }

    /**
     * displayMessage Function
     * Displays a message
     *
     * @param message
     * message to be displayed
     */
    public void displayMessage(String message) {
        System.out.println("[SER]" + message);

    }

    /**
     * getClientWorker function
     *
     * @return cw
     */
    public ClientWorker getClientWorker() {
        return this.cw;
    }


}
