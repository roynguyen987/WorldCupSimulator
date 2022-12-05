package edu.ucdenver.tournament;

import java.io.Serializable;

public class Referee implements Serializable {
    private String name;
    private Country country;

    /**
     * Referee Constructor
     * Initializes name and country
     *
     * @param name
     * name of ref
     * @param country
     * country of ref
     */
    Referee(String name, Country country) {
        this.name = name;
        this.country = country;
    }

    /**
     * getCountry Function
     *
     * @return country
     */
    public Country getCountry() {
        return country;
    }

    /**
     * getName Function
     *
     * @return name
     */
    public String getName() {
        return name;
    }
}
