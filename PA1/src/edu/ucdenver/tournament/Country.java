package edu.ucdenver.tournament;

import java.io.Serializable;

public class Country implements Serializable {
    private String countryName;

    /**
     * Country Constructor
     * Initializes country name
     *
     * @param name
     * name of country
     */
    public Country(String name) {
        this.countryName = name;
    }

    /**
     * getCountryName function
     *
     * @return countryName
     */
    public String getCountryName() {
        return countryName;
    }

    /**
     * toString Function
     *
     * @return output
     */
    public String toString() {
        StringBuilder output = new StringBuilder();
        output.append(String.format("%s", this.countryName));
        return output.toString();
    }
}
