package edu.ucdenver.tournament;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Team implements Serializable {
    private String name;
    private ArrayList<Player> teamPlayers;
    Country teamCountry;

    /**
     * Team Constructor
     * Initializes name and country
     *
     * @param name
     * name of team
     * @param country
     * country of team
     */
    public Team(String name, Country country) {
        this.name = name;
        this.teamCountry = country;
        this.teamPlayers = new ArrayList<>(35);
    }

    /**
     * getCountry Function
     *
     * @return teamCountry
     */
    public Country getCountry() {
        return teamCountry;
    }

    /**
     * getSquad function
     *
     * @return teamPlayers
     */
    public List<Player> getSquad() {
        return teamPlayers;
    }

    /**
     * addPlayer Function
     * Adds a new player
     *
     * @param name
     * name of player
     * @param age
     * age of player
     * @param height
     * height of player
     * @param weight
     * weight of player
     */
    public void addPlayer(String name, int age, double height, double weight) {
        Player newPlayer = new Player(name, age, height, weight);
        teamPlayers.add(newPlayer);
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
     * toString Function
     *
     * @return output
     */
    public String toString() {
        StringBuilder output = new StringBuilder();
        output.append(String.format("Team: %s || Country: %s", this.name, this.teamCountry.toString()));
        return output.toString();
    }

    /**
     * setName Function
     * Sets name of team
     *
     * @param name
     * name of team
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * setCountry Function
     * Sets country of team
     *
     * @param country
     * country of team
     */
    public void setCountry(Country country) {
        this.teamCountry = country;
    }

}
