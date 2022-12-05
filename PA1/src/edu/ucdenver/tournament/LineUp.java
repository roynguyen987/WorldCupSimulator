package edu.ucdenver.tournament;

import java.io.Serializable;
import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

public class LineUp implements Serializable {

    private ArrayList<Player> playerLineUp;
    Team team;

    /**
     * LineUp Constructor
     * Initializes team and playerLineUp
     *
     * @param team
     * team object
     */
    LineUp(Team team) {
        this.team = team;
        this.playerLineUp = new ArrayList<>(11);
    }

    /**
     * getTeam Function
     *
     * @return team
     */
    public Team getTeam() {
        return team;

    }

    /**
     * getPlayers function
     *
     * @return playerLineUp
     */
    public List<Player> getPlayers() {
        return playerLineUp;
    }

    /**
     * addPlayer Function
     * Adds player to a lineup
     *
     * @param player
     * player object
     */
    public void addPlayer(Player player) {
        playerLineUp.add(player);
    }

    /**
     * playerString Function
     *
     * @return output
     */
    public String playerString() {
        String s = "";
        for(Player p: playerLineUp){
            s = s + p.getName() + ",";
        }
        return s;
    }
}
