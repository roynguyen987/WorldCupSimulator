package edu.ucdenver.app;
import edu.ucdenver.server.Server;
import edu.ucdenver.tournament.Tournament;

import java.util.Scanner;

public class ServerApplication {
    public static void main(String[] args) {
        boolean continueApp = true;
        Tournament tournament = new Tournament("", null, null);
        Server server = new Server(10003, 10);
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter A Corresponding Letter Option:\n(A) - Start the Server");
        String option = sc.nextLine();
            if (option.equalsIgnoreCase("A")) {
                server.runServer();
                System.out.println("(B) - Stop the Server");
                String secondOption = sc.nextLine();
                if(secondOption.equalsIgnoreCase("B")){
                    server.getClientWorker().closeClientConnection();
                }
                else{
                    System.out.println("Incorrect Menu Option");
                }
            } else {
                System.out.println("Incorrect Menu Option");
            }
        }
}
