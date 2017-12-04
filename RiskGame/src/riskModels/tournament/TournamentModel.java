package riskModels.tournament;

import java.util.ArrayList;
import java.util.List;

/**
 * This Tournament Model class gets the Players detailed List
 * @author hnath
 *
 */
public class TournamentModel {


    private ArrayList<String> playerNames;
    private ArrayList<String> playerTypes;

	List<String> mapFiles;
	int numberOfGames=1;
	int numberOfTurns=1;
	
	/**
	 * This method assigning the map files,count of games and turns
	 * @param mapFiles file names
	 * @param numberOfGames count of the games played
	 * @param numberOfTurns count of the turns
	 */
	public TournamentModel(List<String> mapFiles, int numberOfGames, int numberOfTurns) {
		super();
		this.mapFiles = mapFiles;
		this.numberOfGames = numberOfGames;
		this.numberOfTurns = numberOfTurns;
		playerNames = new ArrayList<String>();
		playerTypes = new ArrayList<String>();

		playerNames.add("Aggressive");
		playerNames.add("Benevolent");
		playerNames.add("Random");
		playerNames.add("Cheater");


		playerTypes.add("Aggressive Bot");
		playerTypes.add("Benevolent Bot");
		playerTypes.add("Randomize Bot");
		playerTypes.add("Cheater Bot");

	}
	
	/**
	 * This getter method gives the map files
	 * @return map object
	 */
	public List<String> getMapFiles() {
		return mapFiles;
	}
	
	/**
	 * This setter method assigns map files
	 * @param mapFiles name of the file
	 */
	public void setMapFiles(List<String> mapFiles) {
		this.mapFiles = mapFiles;
	}
	
	/**
	 * This getter method gives count of the games
	 * @return number of games
	 */
	public int getNumberOfGames() {
		return numberOfGames;
	}
	
	/**
	 * This setter method assigns the count of the games
	 * @param numberOfGames count of the games played
	 */
	public void setNumberOfGames(int numberOfGames) {
		this.numberOfGames = numberOfGames;
	}
	
	/**
	 * This getter method gives the number of turns
	 * @return count of the turns
	 */
	public int getNumberOfTurns() {
		return numberOfTurns;
	}
	
	/**
	 * This setter method assigns the number of turns
	 * @param numberOfTurns count of the turns
	 */
	public void setNumberOfTurns(int numberOfTurns) {
		this.numberOfTurns = numberOfTurns;
	}
	
	/**
	 * This method gives the name of the player
	 * @return current player name
	 */
    public ArrayList<String> getPlayerNames() {
        return playerNames;
    }

    /**
     * This method assigns the player name
     * @param playerNames name of the player
     */
    public void setPlayerNames(ArrayList<String> playerNames) {
        this.playerNames = playerNames;
    }

    /**
     * This method gives the type of the player
     * @return type of the player,player object
     */
    public ArrayList<String> getPlayerTypes() {
        return playerTypes;
    }

    /**
     * This method assigns the type of the player
     * @param playerTypes type of the player
     */
    public void setPlayerTypes(ArrayList<String> playerTypes) {
        this.playerTypes = playerTypes;
    }

}
