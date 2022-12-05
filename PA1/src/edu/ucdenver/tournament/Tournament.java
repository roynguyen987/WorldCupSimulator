package edu.ucdenver.tournament;

import java.io.*;
import java.sql.Ref;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Tournament implements Serializable {
    private String name;
    private LocalDate startDate;
    private LocalDate endDate;

    private ArrayList<Team> teams;
    private ArrayList<Referee> referees;
    private ArrayList<Player> players;
    private ArrayList<Country> countries;
    private ArrayList<Match> matches;
    private ArrayList<LineUp> lineUps;


    /**
     * Tournament Constructor
     * Creates a new tournament
     *
     * @param name
     * name of tournament
     * @param startDate
     * start date of tournament
     * @param endDate
     * end date of tournament
     */
    public Tournament(String name, LocalDate startDate, LocalDate endDate) {
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.teams = new ArrayList<>(100);
        this.referees = new ArrayList<>(100);
        this.players = new ArrayList<>(100);
        this.matches = new ArrayList<>(100);
        this.countries = new ArrayList<>(100);
        this.lineUps = new ArrayList<>(100);
    }

    /**
     * loadFromFile Function
     * Loads a tournament object from a file
     *
     * @param fString
     * file name
     * @return tournament
     * @throws IOException
     * throws IOException
     */
    public Tournament loadFromFile(String fString) {
        Tournament t = new Tournament("",null,null); //create the tournament that the file info would load into
        try {
            FileInputStream fiis = new FileInputStream(fString); //open the streams
            ObjectInputStream oiis = new ObjectInputStream(fiis);
            t = (Tournament) oiis.readObject(); //read the file for the object and de-serialize
            //NOTE: This function is not working correctly and our assumption is that it is not de-serializing correctly
            oiis.close(); //close the stream
        }catch (Exception e){ //this checks whether the file exist
            System.out.println("File is not found.");
            return t;
        }

        return t;
    }

    /**
     * saveToFile Function
     * Saves a tournament object to a file
     *
     * @param fileName
     * name of file
     * @param tournament
     * tournament object
     * @throws IOException
     * throws IOException
     */
    public void saveToFile(String fileName, Tournament tournament) throws IOException {
        try {
            ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(fileName)); // read output from the file
            output.writeObject(tournament);// write the object to the file
            output.flush(); // flush file
            output.close(); // close file
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

    }

    /**
     * addCountry Function
     * Adds a country to the tournament
     *
     * @param country
     * country name
     */
    public synchronized void addCountry(String country) {
        this.countries.add(new Country(country));
    }

    /**
     * addTeam Function
     * Adds a team to the tournament
     *
     * @param teamName
     * name of team
     * @param country
     * country object
     */
    public synchronized void addTeam(String teamName, Country country) {
        this.teams.add(new Team(teamName, country));
    }

    /**
     * addReferee Function
     * Adds a referee to the tournament
     *
     * @param refName
     * name of ref
     * @param country
     * country object
     */
    public synchronized void addReferee(String refName, Country country) {
        this.referees.add(new Referee(refName, country));
    }

    /**
     * addLineUp Function
     * Adds a lineup to a tournament
     *
     * @param team
     * team object
     */
    public synchronized void addLineUp(Team team) {
        this.lineUps.add(new LineUp(team));
    }

    /**
     * addPlayer Function
     * Adds a player to the tournament
     *
     * @param playerName
     * players name
     * @param age
     * players age
     * @param height
     * players height
     * @param weight
     * players weight
     */
    public synchronized void addPlayer(String playerName, int age, double height, double weight) {
        this.players.add(new Player(playerName, age, height, weight));
    }

    /**
     * addMatch Function
     * Adds a match to the tournament
     *
     * @param dateTime
     * date and time
     * @param teamA
     * teamA object
     * @param teamB
     * teamB object
     */
    public synchronized void addMatch(LocalDateTime dateTime, Team teamA, Team teamB) {
        this.matches.add(new Match(dateTime, teamA, teamB));
    }


    /**
     * addRefereeToMatch Function
     * Adds a referee to a match
     *
     * @param dateTime
     * date and time
     * @param refereeName
     * name of referee
     */
    public synchronized void addRefereeToMatch(LocalDateTime dateTime, String refereeName) {
        for (Match match : this.matches) {
            if (match.getDateTime() == dateTime) {
                for (Referee ref : this.referees) {
                    if (ref.getName().equalsIgnoreCase(refereeName)) {
                        match.addReferee(ref);
                    }
                }
            }
        }
    }

    /**
     * addPlayerToMatch Function
     * Adds a player to a match
     *
     * @param dateTime
     * date and time
     * @param playerName
     * name of player
     */
    public synchronized void addPlayerToMatch(LocalDateTime dateTime, String playerName) {
        for (Match match : this.matches) {
            if (match.getDateTime() == dateTime) {
                for (Player play : this.players) {
                    if (play.getName().equalsIgnoreCase(playerName)) {
                        match.addPlayer(play, play.getPlayerTeam());
                    }
                }
            }
        }
    }

    /**
     * setMatchScore Function
     * Sets the score of a match
     *
     * @param dateTime
     * date and time
     * @param teamAScore
     * score of teamA
     * @param teamBScore
     * score of teamB
     */
    public synchronized void setMatchScore(LocalDateTime dateTime, int teamAScore, int teamBScore) {
        for (Match match : this.matches) {
            if (match.getDateTime() == dateTime) {
                match.setScoreTeamA(teamAScore);
                match.setScoreTeamB(teamBScore);
                match.setMatchScore(teamAScore, teamBScore);
            }
        }
    }

    /**
     * getUpcomingMatches Function
     *
     * @return matches
     */
    public List<Match> getUpcomingMatches() {
        return this.matches;
    }

    /**
     * getMatchesOn Function
     * Gets the matches on a specific date
     *
     * @param matchDate
     * date of match
     * @return matchesOn
     */
    public List<Match> getMatchesOn(LocalDate matchDate) {
        ArrayList<Match> matchesOn = new ArrayList<Match>(100);
        for (Match match : this.matches) {
            LocalDate mDate = match.getDateTime().toLocalDate();
            if (mDate.equals(matchDate)) {
                matchesOn.add(match);
            }
        }
        return matchesOn;
    }

    /**
     * getMatchesFor Function
     * Gets the matches for a specific team
     *
     * @param teamName
     * name of team
     * @return matchesFor
     */
    public List<Match> getMatchesFor(String teamName) {
        ArrayList<Match> matchesFor = new ArrayList<Match>(100);
        for (Match match : this.matches) {
            if (teamName.equalsIgnoreCase(match.getTeamA().getName()) || teamName.equalsIgnoreCase(match.getTeamB().getName())) {
                matchesFor.add(match);
            }
        }
        return matchesFor;
    }

    /**
     * getMatchLineUps
     * Gets the match lineups
     *
     * @param matchDate
     * date of match
     * @return matchLineUp
     */
    public List<LineUp> getMatchLineUps(LocalDateTime matchDate) {
        ArrayList<LineUp> matchLineUps = new ArrayList<LineUp>(11);
        for (Match match : this.matches) {
            if (match.getDateTime().equals(matchDate)) {
                for(LineUp l: lineUps){
                    if(match.teamA.getName().equalsIgnoreCase(l.getTeam().getName())){
                        matchLineUps.add(l);
                    }
                    if(match.teamB.getName().equalsIgnoreCase(l.getTeam().getName())){
                        matchLineUps.add(l);
                    }
            }
        }
    }
        return matchLineUps;
    }

    public void addPlayerToLineup(Player player, Team team){
        if(this.lineUps.isEmpty()){
            System.out.println("Creating new lineup."); //so create a new lineup
            LineUp lu = new LineUp(team);
            lu.addPlayer(player); //add the player to that lineup
            lineUps.add(lu); //and add lineup to list of lineups.
            return;
        }
        for(LineUp l: lineUps){ //look for the lineup in lineups
            if(l.getTeam().equals(team)){
                l.addPlayer(player);
                return;
            }
        }
        System.out.println("Creating new lineup."); //so create a new lineup
        LineUp l = new LineUp(team);
        l.addPlayer(player); //add the player to that lineup
        lineUps.add(l); //and add lineup to list of lineups.
    }




    /**
     * toString Method
     *
     * @return output
     */
    public synchronized String toString() {
        StringBuilder output = new StringBuilder();
        output.append(String.format("This is the %s tournament.|", this.name));

        return output.toString();
    }

    /**
     * setName Function
     * Sets name of tournament
     *
     * @param name
     * name of tournament
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * setStartDate Function
     * Sets the start date of a tournament
     *
     * @param startDate
     * date of tournament
     */
    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    /**
     * setEndDate Function
     * Sets the end date of a tournament
     *
     * @param endDate
     * date of tournament
     */
    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    /**
     * getTeams Function
     *
     * @return teams
     */
    public ArrayList<Team> getTeams() {
        return teams;
    }

    /**
     * getName Function
     *
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * getStartDate Function
     *
     * @return startDate
     */
    public LocalDate getStartDate() {
        return startDate;
    }

    /**
     * getEndDate Function
     *
     * @return endDate
     */
    public LocalDate getEndDate() {
        return endDate;
    }

    /**
     * getCountries Function
     *
     * @return countries
     */
    public ArrayList<Country> getCountries() {
        return countries;
    }

    /**
     * getMatches Function
     *
     * @return matches
     */
    public ArrayList<Match> getMatches() {
        return matches;
    }

    /**
     * getReferees Function
     *
     * @return referees
     */
    public ArrayList<Referee> getReferees() {
        return referees;
    }

    /**
     * getPlayers Function
     *
     * @return players
     */
    public ArrayList<Player> getPlayers() {
        return players;
    }

    /**
     * getLineUps Function
     *
     * @return lineUps
     */
    public ArrayList<LineUp> getLineUps() {
        return lineUps;
    }
}
