package edu.ucdenver.tournament;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Match implements Serializable {
    private LocalDateTime dateTime;
    private int scoreTeamA;
    private int scoreTeamB;
    Team teamA;
    Team teamB;
    private ArrayList<Referee> matchRefs;
    LineUp teamALU;
    LineUp teamBLU;

    /**
     * Match Constructor
     *
     * @param dateTime
     * date and time
     * @param teamA
     * teamA object
     * @param teamB
     * teamB object
     */
    Match(LocalDateTime dateTime, Team teamA, Team teamB) {
        this.dateTime = dateTime;
        this.teamA = teamA;
        this.teamB = teamB;
        this.matchRefs = new ArrayList<>(100);
    }

    /**
     * getTeamA Function
     *
     * @return teamA
     */
    public Team getTeamA() {
        return teamA;
    }

    /**
     * getTeamB Function
     *
     * @return teamB
     */
    public Team getTeamB() {
        return teamB;
    }

    /**
     * getTeamALU Function
     *
     * @return teamALU
     */
    public LineUp getTeamALU() {
        return teamALU;
    }

    /**
     * getTeamBLU Function
     *
     * @return teamBLU
     */
    public LineUp getTeamBLU() {
        return teamBLU;
    }

    /**
     * isUpcoming Function
     *
     * @return boolean
     */
    public boolean isUpcoming() {
        //check current date whatever it is
        //if(matchdate < currentdate(at an earlier date)) then return false else return true
        return false;
    }

    /**
     * addPlayer Function
     * Adds a player to a team
     *
     * @param player
     * player object
     * @param team
     * team object
     */
    public void addPlayer(Player player, Team team) {
        team.addPlayer(player.getName(), player.getAge(), player.getHeight(), player.getWeight());
    }

    /**
     * getReferees Function
     *
     * @return matchRefs
     */
    public List<Referee> getReferees() {
        return this.matchRefs;
    }

    /**
     * addReferee Function
     * Adds ref to list
     *
     * @param ref
     * ref object
     */
    public void addReferee(Referee ref) {
        matchRefs.add(ref);
    }

    /**
     * setMatchScore Function
     * Sets the match score of a match
     *
     * @param scoreTeamA
     * score of team A
     * @param scoreTeamB
     * score of teamB
     */
    public void setMatchScore(int scoreTeamA, int scoreTeamB) {
        this.scoreTeamA = scoreTeamA;
        this.scoreTeamB = scoreTeamB;
    }

    /**
     * getDateTime Function
     *
     * @return dateTime
     */
    public LocalDateTime getDateTime() {
        return dateTime;
    }

    /**
     * getScoreTeamA Function
     *
     * @return scoreTeamA
     */
    public int getScoreTeamA() {
        return scoreTeamA;
    }

    /**
     * getScoreTeamB Function
     *
     * @return scoreTeamB
     */
    public int getScoreTeamB() {
        return scoreTeamB;
    }

    /**
     * setScoreTeamA Function
     * Sets the score of teamA
     *
     * @param scoreTeamA
     * score of TeamA
     */
    public void setScoreTeamA(int scoreTeamA) {
        this.scoreTeamA = scoreTeamA;
    }

    /**
     * setScoreTeamB Function
     * Sets the score of teamB
     *
     * @param scoreTeamB
     * score of TeamB
     */
    public void setScoreTeamB(int scoreTeamB) {
        this.scoreTeamB = scoreTeamB;
    }

    /**
     * toString Function
     * Displays all attributes of a match in a string, output
     *
     * @return ouput
     */
    public String toString() {
        LocalDateTime date = getDateTime();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm");
        String formattedDate = date.format(dateTimeFormatter);
        StringBuilder output = new StringBuilder();
        LocalDateTime lt = LocalDateTime.now();
        if (this.getDateTime().isBefore(lt)) {
            output.append(String.format("Match Date: %s  ||  Team 1: %s  ||  Team2: %s  || Team1 Score: %d || Team2 Score: %d", formattedDate, this.getTeamA().getName(), this.getTeamB().getName(), this.getScoreTeamA(), this.getScoreTeamB()));
        } else {
            output.append(String.format("Match Date: %s  ||  Team 1: %s  ||  Team2: %s", formattedDate, this.getTeamA().getName(), this.getTeamB().getName()));
        }
        return output.toString();
    }


}
