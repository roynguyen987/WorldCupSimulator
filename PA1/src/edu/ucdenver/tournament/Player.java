package edu.ucdenver.tournament;

import java.io.Serializable;

public class Player implements Serializable {
    private String name;
    private int age;
    private double height;
    private double weight;
    private Team playerTeam;

    /**
     * Constructor for Player class, initializes Player object
     * @param name
     * name of player
     * @param age
     * age of player
     * @param height
     * height of player
     * @param weight
     * weight of player
     */
    public Player(String name, int age, double height, double weight) {
        this.name = name;
        this.age = age;
        this.height = height;
        this.weight = weight;
    }

    /**
     * getName function
     *
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * getAge function
     *
     * @return age
     */
    public int getAge() {
        return age;
    }

    /**
     * getHeight function
     *
     * @return height
     */
    public double getHeight() {
        return height;
    }

    /**
     * getWeight function
     *
     * @return weight
     */
    public double getWeight() {
        return weight;
    }

    /**
     * getPlayerTeam function
     *
     * @return playerTeam
     */
    public Team getPlayerTeam() {
        return playerTeam;
    }

    /**
     * toString method for Player class
     * outputs the name, age, height, and weight of each player
     *
     * @return output
     */
    public String toString() {
        StringBuilder output = new StringBuilder();
        output.append(String.format("Name: %s || Age: %s || Height: %s || Weight %s", this.name, this.age, this.height, this.weight));
        return output.toString();
    }

}
