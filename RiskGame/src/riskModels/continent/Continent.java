package riskModels.continent;

import riskModels.country.Country;

import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Continent Bean class that will hold the properties of continent.
 *
 * @author prashantp95
 */
public class Continent implements Serializable{

    public String continentName;
    public List<Country> memberCountriesList = new ArrayList<Country>();
    public int numberOfTerritories;
    public int controlValue;

    /**
     * This constructor will create continent object based provided continent name
     *
     * @param continentName name of the continent
     */
    public Continent(String continentName) {
        this.continentName = continentName;
        this.controlValue = 1;
    }

    /**
     * default constructor
     */
    public Continent() {

    }

    /**
     * Over ride equals method in order to compare compare objects based on continent name not continent objects
     */
    @Override
    public boolean equals(Object continentObject) {
        String continentName = ((Continent) continentObject).getContinentName();
        return continentName.equals(this.getContinentName());
    }

    /**
     * Overriding hashCode method.
     */
    @Override
    public int hashCode() {
        return this.continentName.hashCode();
    }

    /**
     * This method will return continent name
     *
     * @return name of the continent (String format)
     */
    public String getContinentName() {
        return continentName;
    }

    /**
     * setter method assigns the continent name
     *
     * @param continentName continent object
     */
    public void setContinentName(String continentName) {
        this.continentName = continentName;
    }

    /**
     * getter method to get the list of the countries belong to that particular continent
     *
     * @return memberCountriesList list of countries
     */
    public List<Country> getMemberCountriesList() {
        return memberCountriesList;
    }

    /**
     * setter method assigns countries list to the player
     *
     * @param memberCountriesList individual player countries list
     */
    public void setMemberCountriesList(List<Country> memberCountriesList) {
        this.memberCountriesList = memberCountriesList;
    }

    /**
     * getter method gives total value of territories assigned to the player
     *
     * @return value of total number of territories assigned to the player
     */
    public int getNumberOfTerritories() {
        return numberOfTerritories;
    }

    /**
     * setter method assigns number of territories to the player
     *
     * @param numberOfTerritories count for individual player
     */
    public void setNumberOfTerritories(int numberOfTerritories) {
        this.numberOfTerritories = numberOfTerritories;
    }

    /**
     * getter method gives the control value
     *
     * @return value of control value
     */
    public int getControlValue() {
        return controlValue;
    }

    /**
     * setter method assigns control value
     *
     * @param controlValue player object
     */
    public void setControlValue(int controlValue) {
        this.controlValue = controlValue;
    }

}
