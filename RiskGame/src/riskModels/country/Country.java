package riskModels.country;

import riskModels.player.Player;

import java.io.Serializable;
import java.util.List;

/**
 * Country Bean class to get and set properties related to country
 *
 * @author prashantp95
 */

public class Country implements Serializable {
    public String countryName;
    public int startPixel;
    public int endPixel;
    public String belongsToContinent;// to Represent county belongs to which continent
    public List<Country> neighborNodes; // to Represents adjacent country nodes
    public Player belongsToPlayer;
    public int currentArmiesDeployed;

    /**
     * This constructor will create country object based provided parameters
     *
     * @param countryName   name of the country
     * @param startPixel    For UI purpose start pixel
     * @param endPixel      For UI purpose end pixel
     * @param continentName name of the continent
     */
    public Country(String countryName, int startPixel, int endPixel, String continentName) {
        this.countryName = countryName;
        this.startPixel = startPixel;
        this.endPixel = endPixel;
        this.belongsToContinent = continentName;
    }

    /**
     * This constructor will create country object based on provided player object and current deployed armies
     *
     * @param belongsToPlayer       player object
     * @param currentArmiesDeployed number of armies deployed to the country
     */
    public Country(Player belongsToPlayer, int currentArmiesDeployed) {
        this.belongsToPlayer = belongsToPlayer;
        this.currentArmiesDeployed = currentArmiesDeployed;
    }

    /**
     * This constructor will create country object based on provided country name
     *
     * @param countryName name of the country
     */
    public Country(String countryName) {
        this.countryName = countryName;
    }

    /**
     * This constructor will create country object based on country name and continent name , for non UI purpose
     *
     * @param countryName        Name of the country
     * @param belongsToContinent Name of the continent where country belongs to .
     */
    public Country(String countryName, String belongsToContinent) {
        this.countryName = countryName;
        this.belongsToContinent = belongsToContinent;
    }

    /**
     * Over ride equals method in order to compare compare objects based on country name not country objects
     */
    @Override
    public boolean equals(Object o) {
        String countryName = ((Country) o).getCountryName();
        return countryName.equals(this.getCountryName());
    }

    /**
     * Overriding hashCode method.
     */
    @Override
    public int hashCode() {
        return this.countryName.hashCode();
    }

    /**
     * getter method for player who own the country
     *
     * @return belongsToPlayer owner of the country (player object)
     */
    public Player getBelongsToPlayer() {
        return belongsToPlayer;
    }

    /**
     * setter method to assign country to player
     *
     * @param belongsToPlayer player object
     */
    public void setBelongsToPlayer(Player belongsToPlayer) {
        this.belongsToPlayer = belongsToPlayer;
    }

    /**
     * getter method give number of current armies deployed
     * @return numerator of how many current armies deployed
     */
    public int getCurrentArmiesDeployed() {
        return currentArmiesDeployed;
    }

    /**
     * setter method to assign value of current armies deployed
     * @param currentArmiesDeployed numerator of how many current armies deployed
     */
    public void setCurrentArmiesDeployed(int currentArmiesDeployed) {
        this.currentArmiesDeployed = currentArmiesDeployed;
    }

    /**
     * getter method gives the name of the country
     * @return string country object
     */
    public String getCountryName() {
        return countryName;
    }

    /**
     * setter method assigns country name 
     * @param countryName name of the country
     */
    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    /**
     * getter method gives the starting pixel of the country
     * @return value of starting pixel
     */
    public int getStartPixel() {
        return startPixel;
    }

    /**
     * setter method assigns starting pixel to the country
     * @param startPixel value of pixel
     */
    public void setStartPixel(int startPixel) {
        this.startPixel = startPixel;
    }

    /**
     * getter method gives the ending pixel of the country
     * @return value of ending pixel
     */
    public int getEndPixel() {
        return endPixel;
    }

    /**
     * setter method assigns ending pixel to the country
     * @param endPixel value of pixel
     */
    public void setEndPixel(int endPixel) {
        this.endPixel = endPixel;
    }

    /**
     * getter method gives details of country to continent it belongs to
     * @return name of the continent the country belongs to
     */
    public String getBelongsToContinent() {
        return belongsToContinent;
    }

    /**
     * setter method assigns details of country to continent it belongs to
     * @param belongsToContinent country object
     */
    public void setBelongsToContinent(String belongsToContinent) {
        this.belongsToContinent = belongsToContinent;
    }

    /**
     * getter method gives details of neighbor countries 
     * @return list of neighbor countries
     */
    public List<Country> getNeighborNodes() {
        return neighborNodes;
    }

    /**
     * setter method assigns details of neighbor countries
     * @param neighborNodes list of neighbor countries
     */
    public void setNeighborNodes(List<Country> neighborNodes) {
        this.neighborNodes = neighborNodes;
    }

    /**
     * this method assigns number of armies to the country
     * @param n value of armies to be added
     */
    public void addArmy(int n) {
        currentArmiesDeployed += n;
    }

    /**
     * this method assigns number of armies to be removed from the country
     * @param n value of the armies to be removed
     */
    public void subtractArmy(int n) {
        currentArmiesDeployed -= n;
    }

    /**
     * this method assigns player and number of armies to the player
     * @param player name of the player
     * @param noOfArmy value of the player
     */
    public void setPlayer(Player player, int noOfArmy) {
        this.belongsToPlayer = player;
        this.currentArmiesDeployed = noOfArmy;
    }

}
