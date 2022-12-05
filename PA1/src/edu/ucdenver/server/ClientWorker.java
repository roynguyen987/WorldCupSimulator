package edu.ucdenver.server;

import java.io.*;
import java.net.Socket;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

import edu.ucdenver.tournament.*;

public class ClientWorker implements Runnable {
    private final Socket clientConnection;
    private final Tournament tournament;
    private PrintWriter output;
    private BufferedReader input;
    private boolean keepRunningClient;
    private final int id;

    /**
     * ClientWorker constructor, initializes the client connection,
     * id, keepRunningCliet, and the tournament
     *
     * @param connection
     * client connection
     * @param tournament
     * tournament object
     * @param id
     * id of client
     */
    public ClientWorker(Socket connection, Tournament tournament, int id) {
        this.clientConnection = connection;
        this.id = id;
        this.keepRunningClient = true;
        this.tournament = tournament;
    }

    /**
     * getOutputStream function, gets the output stream
     *
     * @param clientConnection
     * client connection
     * @throws IOException
     * throws IOException
     */
    private void getOutputStream(Socket clientConnection) throws IOException {
        this.output = new PrintWriter(this.clientConnection.getOutputStream(), true);
    }

    /**
     * getInputStream function, gets the input stream
     *
     * @param clientConnection
     * client connection
     * @throws IOException
     * throws IOException
     */
    private void getInputStream(Socket clientConnection) throws IOException {
        this.input = new BufferedReader(new InputStreamReader(clientConnection.getInputStream()));
    }

    /**
     * sendMessage function, sends a message to the client
     *
     * @param message
     * message to be sent
     */
    private void sendMessage(String message) {
        System.out.println("Server >> " + message);
        this.output.println(message);
    }

    /**
     * processClientRequest function, processes all of the
     * requests coming in from the client-side of the program.
     * Determines what the clientMessage starts with, then executes
     * code based on that from the client.
     *
     * @throws IOException
     * throws IOException
     * @throws ClassNotFoundException
     * throws ClassNotFoundException
     */
    private void processClientRequest() throws IOException, ClassNotFoundException {
        // Receiving clientMessage
        String clientMessage = this.input.readLine();
        System.out.println(clientMessage);

        // Create Tournament
        // Creates a tournament based on information sent from the
        // client message
        if (clientMessage.startsWith("CREATE,")) {
            clientMessage = clientMessage.substring(7);
            String[] tokens = clientMessage.split(",");
            LocalDate startDate = LocalDate.parse(tokens[1]);
            LocalDate endDate = LocalDate.parse(tokens[2]);
            tournament.setName(tokens[0]);
            tournament.setStartDate(startDate);
            tournament.setEndDate(endDate);
            sendMessage("Successfully Created Tournament");
        }

        // ADMIN APPLICATION
        //----------------------------------------------------------------------------------------

        // Load From File
        // Loads a tournament object from a file sent in
        // by the clientMessage
        if (clientMessage.startsWith("LOAD,")) {
            clientMessage = clientMessage.substring(5);
            File file = new File(clientMessage);
            if (file.exists()) {
                tournament.loadFromFile(clientMessage);
                sendMessage("Successfully Loaded File.");
                return;
            } else {
                sendMessage("[ERROR] - File Could Not Be Found");
                return;
            }

        }

        // Save To File
        // Saves a tournament object to a file sent in
        // by the clientMessage
        if (clientMessage.startsWith("SAVE,")) {
            if (tournament.getName() == "") {
                sendMessage("[ERROR] - Must Create a Tournament Before Saving To File");
                return;
            }
            clientMessage = clientMessage.substring(5);
            System.out.println(tournament.getCountries());
            tournament.saveToFile(clientMessage, this.tournament);
            sendMessage("Successfully Saved File.");
            return;
        }

        // Adds a Country
        // Adds a country to the tournament based on
        // the country name sent in with the clientMessage
        if (clientMessage.startsWith("ADDCOUNTRY,")) {
            // parse clientmessage into array
            // add to tournament
            boolean alreadyThere = false;
            clientMessage = clientMessage.substring(11);
            String[] tokens = clientMessage.split(",");
            for (Country c : tournament.getCountries()) {
                if (c.getCountryName().equalsIgnoreCase(tokens[0])) {
                    alreadyThere = true;
                    sendMessage("[ERROR] - Country Is Already In System");
                    return;
                }
            }
            if (alreadyThere == false) {
                tournament.addCountry(tokens[0]);
                sendMessage("Successfully Added Country");
                return;
            }
        }

        // Adds a Team
        // Adds a team to the tournament based on
        // the team name and team country sent in with the clientMessage
        if (clientMessage.startsWith("ADDTEAM,")) {
            boolean countryFound = false;
            boolean sameName = false;
            clientMessage = clientMessage.substring(8);
            String[] tokens = clientMessage.split(",");
            for (Country c : tournament.getCountries()) {
                if (c.getCountryName().equalsIgnoreCase(tokens[1])) {
                    countryFound = true;
                    for (Team t : tournament.getTeams()) {
                        if (t.getName().equalsIgnoreCase(tokens[0])) {
                            sameName = true;
                            sendMessage("[ERROR] - Team With Name: " + tokens[0] + " Already Exists");
                            return;
                        }
                    }
                    if (countryFound == true && sameName == false) {
                        tournament.addTeam(tokens[0], c);
                        sendMessage("Successfully Added Team");
                        return;
                    }
                }
            }
            if (countryFound == false) {
                sendMessage("[ERROR] - Must Add Country Before Adding Team");
                return;
            }
        }

        // Adds a Referee
        // Adds a referee to the tournament based on
        // the referee name and country sent in with the clientMessage
        if (clientMessage.startsWith("ADDREFEREE,")) {
            boolean countryFound = false;
            boolean sameName = false;
            if (tournament.getName() == "") {
                sendMessage("[ERROR] - Must Create a Tournament Before Adding A Referee");
                return;
            }
            clientMessage = clientMessage.substring(11);
            String[] tokens = clientMessage.split(",");
            for (Country c : tournament.getCountries()) {
                if (c.getCountryName().equalsIgnoreCase(tokens[1])) {
                    countryFound = true;
                    for (Referee r : tournament.getReferees()) {
                        if (r.getName().equalsIgnoreCase(tokens[0])) {
                            sameName = true;
                            sendMessage("[ERROR] - Referee With Name: " + tokens[0] + " Already Exists");
                            return;
                        }
                    }
                    if (countryFound == true && sameName == false) {
                        tournament.addReferee(tokens[0], c);
                        sendMessage("Successfully Added Referee");
                        return;
                    }
                }
            }
            if (countryFound == false) {
                sendMessage("[ERROR] - Must Add Country Before Adding Referee");
                return;
            }
        }

        // Adds a Match
        // Adds a match to the tournament based on
        // the match date, match time, team1 name, and team2 name sent in with the clientMessage
        if (clientMessage.startsWith("ADDMATCH,")) {
            clientMessage = clientMessage.substring(9);
            String[] tokens = clientMessage.split(",");
            String mTime = tokens[0] + tokens[1];
            boolean found1 = false;
            boolean found2 = false;
            LocalDateTime matchTime = LocalDateTime.parse(mTime);
            for (Match match : tournament.getMatches()) {
                if (match.getDateTime().equals(matchTime)) {
                    sendMessage("[ERROR]: There Is Already A Match At This Time");
                    return;
                }
            }
            Team teamA = new Team("", null);
            Team teamB = new Team("", null);
            for (Team team : tournament.getTeams()) {
                if (team.getName().equalsIgnoreCase(tokens[2])) {
                    teamA.setName(team.getName());
                    teamA.setCountry(team.getCountry());
                    found1 = true;
                }
            }
            for (Team team : tournament.getTeams()) {
                if (team.getName().equalsIgnoreCase(tokens[3])) {
                    teamB.setName(team.getName());
                    teamB.setCountry(team.getCountry());
                    found2 = true;
                }
            }
            if (teamA.getName().equalsIgnoreCase(teamB.getName()) && teamA.getName() != "" && teamB.getName() != "") {
                System.out.println(teamA.getName());
                System.out.println(teamB.getName());
                sendMessage("[ERROR]: Both Teams In Match Are The Same.");
                return;
            }
            if (found1 == false || found2 == false) {
                sendMessage("[ERROR]: Team Could Not Be Found");
            } else {
                tournament.addMatch(matchTime, teamA, teamB);
                sendMessage("Match Added");
            }

        }

        // Assigns a Referee
        // Assigns a referee to a match based on
        // the 4 referee names, the match date, and the match time sent in with the clientMessage
        if (clientMessage.startsWith("ASSIGNREF,")) {
            clientMessage = clientMessage.substring(10);
            String[] tokens = clientMessage.split(",");

            String teamACountryName = "";
            String teamBCountryName = "";

            boolean matchGood = false;
            boolean found1 = false;
            boolean nationalityCheck1 = false;
            boolean found2 = false;
            boolean nationalityCheck2 = false;
            boolean found3 = false;
            boolean nationalityCheck3 = false;
            boolean found4 = false;
            boolean nationalityCheck4 = false;

            String mTime = tokens[4] + tokens[5];
            LocalDateTime matchTime = LocalDateTime.parse(mTime);

            // Finding match
            for (Match match : tournament.getUpcomingMatches()) {
                if (match.getDateTime().equals(matchTime)) {
                    teamACountryName = match.getTeamA().getCountry().getCountryName();
                    teamBCountryName = match.getTeamB().getCountry().getCountryName();
                    matchGood = true;
                }
            }

            // Checking each referee
            for (Referee ref : tournament.getReferees()) {
                // Checking referee1
                if (ref.getName().equalsIgnoreCase(tokens[0])) {
                    found1 = true;
                    if (ref.getCountry().getCountryName().equalsIgnoreCase(teamACountryName)) {
                        nationalityCheck1 = false;
                    } else if (ref.getCountry().getCountryName().equalsIgnoreCase(teamBCountryName)) {
                        nationalityCheck1 = false;
                    } else {
                        nationalityCheck1 = true;
                    }
                }
                // Checking referee2
                if (ref.getName().equalsIgnoreCase(tokens[1])) {
                    found2 = true;
                    if (ref.getCountry().getCountryName().equalsIgnoreCase(teamACountryName)) {
                        nationalityCheck2 = false;
                    } else if (ref.getCountry().getCountryName().equalsIgnoreCase(teamBCountryName)) {
                        nationalityCheck2 = false;
                    } else {
                        nationalityCheck2 = true;
                    }
                }
                // Checking referee3
                if (ref.getName().equalsIgnoreCase(tokens[2])) {
                    found3 = true;
                    if (ref.getCountry().getCountryName().equalsIgnoreCase(teamACountryName)) {
                        nationalityCheck3 = false;
                    } else if (ref.getCountry().getCountryName().equalsIgnoreCase(teamBCountryName)) {
                        nationalityCheck3 = false;
                    } else {
                        nationalityCheck3 = true;
                    }
                }
                // Checking referee4
                if (ref.getName().equalsIgnoreCase(tokens[3])) {
                    found4 = true;
                    if (ref.getCountry().getCountryName().equalsIgnoreCase(teamACountryName)) {
                        nationalityCheck4 = false;
                    } else if (ref.getCountry().getCountryName().equalsIgnoreCase(teamBCountryName)) {
                        nationalityCheck4 = false;
                    } else {
                        nationalityCheck4 = true;
                    }
                }
            }
            // Could not find one referee
            if (found1 == false || found2 == false || found3 == false || found4 == false) {
                sendMessage("[ERROR]: One Of The Referees Could Not Be Found");
                return;
            }
            // One of referees nationality matches that of a team
            if (nationalityCheck1 == false || nationalityCheck2 == false || nationalityCheck3 == false || nationalityCheck4 == false) {
                sendMessage("[ERROR]: One Of The Referees Nationality Matches That of A Team");
                return;
            }
            // Match could not be found
            if (matchGood == false) {
                sendMessage("[ERROR]: Match Could Not Be Found");
                return;
            }
            // Match was found, and referees were checked
            if (found1 && found2 && found3 && found4 && nationalityCheck1 && nationalityCheck2 && nationalityCheck3 && nationalityCheck4 && matchGood == true) {
                tournament.addRefereeToMatch(matchTime, tokens[0]);
                tournament.addRefereeToMatch(matchTime, tokens[1]);
                tournament.addRefereeToMatch(matchTime, tokens[2]);
                tournament.addRefereeToMatch(matchTime, tokens[3]);
                sendMessage("Referees Assigned.");
                return;
            }

        }


        // Adds a Player
        // Adds a player to a team based on
        // the player name, age, height, weight, and the team name sent in with the clientMessage
        if (clientMessage.startsWith("ADDPLAYER,")) {
            boolean found = false;
            // Checking if tournament exists
            if (tournament.getName() == "") {
                sendMessage("[ERROR] - Must Create a Tournament Before Adding A Player");
                return;
            }
            clientMessage = clientMessage.substring(10);
            String[] tokens = clientMessage.split(",");

            // Going through each team
            for (Team t : tournament.getTeams()) {
                // Matching team name with that entered from client
                if (t.getName().equalsIgnoreCase(tokens[4])) {
                    // Going through each player on team
                    for (Player p : t.getSquad()) {
                        // Checking if players name already exists on team
                        if (p.getName().equalsIgnoreCase(tokens[0])) {
                            sendMessage("[ERROR] - Player Already Exists In Team");
                            return;
                        }
                    }
                    // Adds player to team
                    tournament.addPlayer(tokens[0], Integer.parseInt(tokens[1]), Double.parseDouble(tokens[2]), Double.parseDouble(tokens[3]));
                    t.addPlayer(tokens[0], Integer.parseInt(tokens[1]), Double.parseDouble(tokens[2]), Double.parseDouble(tokens[3]));
                    found = true;
                    sendMessage("Successfully Added Player To Team: " + t.getName());
                    return;
                }
            }
            // Could not find team
            if (found == false) {
                sendMessage("[ERROR] - Could Not Find Team");
                return;
            }
        }

        // Records Score of a Match
        // Records the score of a match based on
        // the date of the match, the time, the score of teamA, and the score of teamB sent in with the clientMessage
        if (clientMessage.startsWith("RECORDSCORE,")) {
            boolean found = false;
            // Checking if tournament exists
            if (tournament.getName() == "") {
                sendMessage("[ERROR] - Must Create a Tournament Before Adding A Score");
                return;
            }
            clientMessage = clientMessage.substring(12);
            String[] tokens = clientMessage.split(",");
            String mTime = tokens[0] + tokens[1];
            LocalDateTime matchTime = LocalDateTime.parse(mTime);

            // Going through each match
            for (Match m : tournament.getMatches()) {
                // Checking time
                if (m.getDateTime().isEqual(matchTime)) {
                    LocalDateTime lt = LocalDateTime.now();
                    // If match is before current time, set the score
                    if (m.getDateTime().isBefore(lt)) {
                        tournament.setMatchScore(matchTime, Integer.parseInt(tokens[2]), Integer.parseInt(tokens[3]));
                        m.setScoreTeamA(Integer.parseInt(tokens[2]));
                        m.setScoreTeamB(Integer.parseInt(tokens[3]));
                        found = true;
                        sendMessage("Successfully Recorded Score For Match on: " + matchTime);
                        return;
                    }
                    // Match is not a past match
                    else {
                        sendMessage("[ERROR] - Match Is Not A Past Match");
                        return;
                    }
                }
            }
            // Match could not be found
            if (found == false) {
                sendMessage("[ERROR] - Match Could Not Be Found");
                return;
            }

        }

        // Adds Player to a Lineup
        if (clientMessage.startsWith("ADDPLAYERTOLINEUP,")) {
            System.out.println();
            boolean playerFound = false;
            boolean teamFound = false;
            String[] tokens = clientMessage.split(",");
            for (Team t : tournament.getTeams()) {
                if (t.getName().equalsIgnoreCase(tokens[2])) {
                    teamFound = true;
                    for (Player p : tournament.getPlayers()) {
                        if (p.getName().equalsIgnoreCase(tokens[1])) {
                            playerFound = true;
                            tournament.addPlayerToLineup(p,t);
                            sendMessage("Successfully Added Player to Lineup.");
                            }
                        }
                    }
            }
            if(!teamFound){
                sendMessage("[ERROR] - Team Does Not Exist");
                return;
            }
            if(!playerFound){
                sendMessage("[ERROR] - Player Does Not Exist");
                return;
            }
        }


        //----------------------------------------------------------------------------------------


        // PUBLIC APPLICATION
        //----------------------------------------------------------------------------------------

        // Updates Upcoming Matches
        // Updates upcoming matches based on
        // the match date and the current date
        if (clientMessage.startsWith("UPDATEUPCOMING,")) {
            ArrayList<Match> upcomingMatches = new ArrayList<Match>(100);
            LocalDateTime lt = LocalDateTime.now();

            // Going through each match
            for (Match m : tournament.getMatches()) {
                // If match is after current time, add it to list
                if (m.getDateTime().isAfter(lt)) {
                    upcomingMatches.add(m);
                }
            }
            sendMessage(upcomingMatches.toString());
        }


        // Updates Matches On Date
        // Updates matches for a specific date based on
        // the match date sent in with the client message
        if (clientMessage.startsWith("UPDATEDATE,")) {
            clientMessage = clientMessage.substring(11);
            String[] tokens = clientMessage.split(",");

            LocalDate localDate = LocalDate.parse(tokens[0]);
            sendMessage(tournament.getMatchesOn(localDate).toString());
        }

        // Updates Matches For Team
        // Updates matches for a given team based on
        // the team name given with the client message
        if (clientMessage.startsWith("UPDATETEAM,")) {
            clientMessage = clientMessage.substring(11);
            String[] tokens = clientMessage.split(",");
            System.out.println(tournament.getMatchesFor(tokens[0]));
            sendMessage(tournament.getMatchesFor(tokens[0]).toString());
        }

        // Updates Lineups For a Match
        // Updates the lineups for a match based on
        if (clientMessage.startsWith("LINEUPFORMATCH,")) {
            String s = "";
            clientMessage = clientMessage.substring(15);
            String[] tokens = clientMessage.split(",");
            String mTime = tokens[0] + tokens[1];
            LocalDateTime matchDate = LocalDateTime.parse(mTime);
            for(Match m: tournament.getMatches()){
                if(m.getDateTime().equals(matchDate)){
                    System.out.println("match time correct");
                    System.out.println(tournament.getMatchLineUps(matchDate));
                    for(LineUp l: tournament.getMatchLineUps(matchDate)){
                        System.out.println(l.getTeam().getName() + " hello " +  l.playerString());
                        s = s + l.playerString() + "-";
                    }
                }
            }
            sendMessage(s);
        }
    }


    /**
     * closeClientConnection function
     * Closes the client connection from the server
     */
    public void closeClientConnection() {
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
            this.clientConnection.close();
        } catch (IOException | NullPointerException e) {
            e.printStackTrace();
        }
    }

    /**
     * displayMessage Function
     * Displays a message
     *
     * @param message
     * message recieved
     */
    private void displayMessage(String message) {
        System.out.printf("CLIENT[&d] >> %s%n", this.id, message);
    }

    /**
     * run Function
     * Runs the clientWorker
     */
    public void run() {
        displayMessage("Getting Data Streams");
        try {
            getOutputStream(clientConnection);
            getInputStream(clientConnection);

            sendMessage("Connected to Java Server");
            this.keepRunningClient = true;

            while (this.keepRunningClient)
                processClientRequest();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } finally {
            closeClientConnection();

        }
    }
}
