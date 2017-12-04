package riskModels.map;

import riskModels.cards.Deck;
import riskModels.continent.Continent;
import riskModels.country.Country;
import riskModels.player.Player;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * This class holds the properties to create graph from map file
 *
 * @author prashantp95
 */
public class GameMap implements Serializable {

    private static GameMap gameMap;
    public HashMap<Country, List<Country>> countryAndNeighborsMap = new HashMap<>();
    public HashMap<Continent, List<Country>> continentCountryMap = new HashMap<>();
    public List<Continent> continentList = new ArrayList<>();
    public List<Player> playerList = new ArrayList<>();
    public boolean isCorrectMap = true;
    public String errorMessage;
    public HashMap<String, String> mapDetail = new HashMap<>();
    public Player currentPlayer;
    public Deck deck;

    public String logDetails, phaseDetails,dominationViewDetails;
    private static final long serialVersionUID = 1L;

    /**
     * Default Constructor
     */
    private GameMap() {
        //To Prevent Other classes from creating object.
    }

    /**
     * This method will return singleton instance for the GameMap class
     *
     * @param instance creates an instance of GameMap class
     * @return single GameMap instance
     */
    public static GameMap getInstance(GameMap instance) {
        if (null == gameMap) {
            if(instance==null)
                gameMap = new GameMap();
            else
                gameMap = instance;
        }
        return gameMap;
    }

    /**
     * This method will assign gameMapToSet info to the GameMap class
     * @param gameMapToSet GameMap object
     */
    public static void setInstance(GameMap gameMapToSet){
        gameMap = gameMapToSet;
    }
    
    /**
     * This method returns the instance of GameMap
     * @return GameMap object
     */
    public static GameMap getInstance() {
        return getInstance(null);
    }
    
    /**
     * To get the Log details
     * @return Log information
     */
    public String getLogDetails() {
        return logDetails;
    }

    /**
     * To set the log details
     * @param logDetails log information
     */
    public void setLogDetails(String logDetails) {
        this.logDetails = logDetails;
    }

    /**
     * To get the phase details
     * @return phase object
     */
    public String getPhaseDetails() {
        return phaseDetails;
    }

    /**
     * To set the phase details
     * @param phaseDetails info about phase
     */
    public void setPhaseDetails(String phaseDetails) {
        this.phaseDetails = phaseDetails;
    }

    /**
     * To get the deck details
     * @return deck details
     */
    public Deck getDeck() {
        return deck;
    }

    /**
     * To set the deck details
     * @param deck deck information
     */
    public void setDeck(Deck deck) {
        this.deck = deck;
    }

    /**
     * this method verify weather the selected map validate the rules of CorrectMap constraints
     *
     * @return true if the map is correct; otherwise return false
     */
    public boolean isCorrectMap() {
        return isCorrectMap;
    }

    /**
     * setter method checks is the map is correct and then assigns map
     *
     * @param isCorrectMap boolean value
     */
    public void setCorrectMap(boolean isCorrectMap) {
        this.isCorrectMap = isCorrectMap;
    }

    /**
     * getter method for players who selected the map to play on
     *
     * @return errorMessage message display on the console
     */
    public String getErrorMessage() {
        return errorMessage;
    }

    /**
     * setter method to assign the message when we select a map which doesn't agree to the rules
     *
     * @param errorMessage generates a error message
     */
    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;

    }

    /**
     * getter method to get the selected map details
     *
     * @return mapDetails Complete details of selected map
     */
    public HashMap<String, String> getMapDetail() {
        return mapDetail;
    }

    /**
     * setter method to assign mapDetails
     *
     * @param mapDetail map object
     */
    public void setMapDetail(HashMap<String, String> mapDetail) {
        this.mapDetail = mapDetail;

    }

    /**
     * getter method to get the informations of neighboring countries
     *
     * @return countryAndNeighborsMap list of neighboring countries details
     */
    public HashMap<Country, List<Country>> getCountryAndNeighborsMap() {
        return countryAndNeighborsMap;
    }

    /**
     * setter method to assign countries and its neighbors in the map
     *
     * @param countryAndNeighborsMap map object
     */
    public void setCountryAndNeighborsMap(HashMap<Country, List<Country>> countryAndNeighborsMap) {
        this.countryAndNeighborsMap = countryAndNeighborsMap;
    }

    /**
     * getter method to get list of continents in the selected map
     *
     * @return continentList list of continents in the map
     */
    public List<Continent> getContinentList() {
        return continentList;
    }

    /**
     * setter method to assign continents to the map
     *
     * @param continentList map object
     */
    public void setContinentList(List<Continent> continentList) {
        this.continentList = continentList;
    }

    /**
     * getter method to get list of countries belong to the continent
     *
     * @return continentCountryMap map object
     */
    public HashMap<Continent, List<Country>> getContinentCountryMap() {
        return continentCountryMap;
    }

    /**
     * setter method to assign countries to the continent
     *
     * @param continentCountryMap map object
     */
    public void setContinentCountryMap(HashMap<Continent, List<Country>> continentCountryMap) {
        this.continentCountryMap = continentCountryMap;
    }

    /**
     * getter method to get the list of the players
     *
     * @return playerList player details
     */
    public List<Player> getPlayerList() {
        return playerList;
    }

    /**
     * setter method to assign players list
     *
     * @param playerList details of players
     */
    public void setPlayerList(List<Player> playerList) {
        this.playerList = playerList;
    }

    /**
     * @param continentName Name of the continent.
     * @return Countries in the continent
     */
    public List<Country> getCountriesByContinent(String continentName) {
        List<Country> continentCountry = new ArrayList<>();
        for (Country c : getCountryAndNeighborsMap().keySet()) {
            if (c.getBelongsToContinent().equalsIgnoreCase(continentName)) {
                continentCountry.add(c);
            }
        }
        return continentCountry;
    }

    /**
     * Gets the list of countries in the map
     *
     * @return Countries in the map
     */
    public List<Country> getCountries() {
        List<Country> countries = new ArrayList<>();
        countries.addAll(getCountryAndNeighborsMap().keySet());
        return countries;
    }
    /**
     * To get the currentPlayer Playing in map
     * @return current player name
     */
	public Player getCurrentPlayer() {
		return currentPlayer;
	}
	/**
	 * To set the current player in map
	 * @param currentPlayer name of the current player
	 */
	public void setCurrentPlayer(Player currentPlayer) {
		this.currentPlayer = currentPlayer;
	}

	/**
	 * To get the domination view details
	 * @return view object
	 */
	public String getDominationViewDetails() {
		return dominationViewDetails;
	}

	/**
	 * To set the domination view details
	 * @param dominationViewDetails info about dominated player
	 */
	public void setDominationViewDetails(String dominationViewDetails) {
		this.dominationViewDetails = dominationViewDetails;
	}
    
}
