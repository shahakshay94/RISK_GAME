package test.player;

import org.junit.Before;
import org.junit.Test;
import riskModels.country.Country;
import riskModels.map.GameMap;
import riskModels.map.MapModel;
import riskModels.player.AggressiveBot;
import riskModels.player.Player;
import riskModels.player.RandomBot;
import riskView.GameView;

import java.util.ArrayList;

import static org.junit.Assert.assertTrue;

/**
 * This test class checks whether AggressiveBot strategy is working as intended or not
 *
 * @author hnath
 */
public class AggressiveBotTest extends Player {


    private GameMap gameMap;
    private GameView gameView;
    private Player model;

    private ArrayList<String> playerNames;
    private ArrayList<String> playerTypes;

    /**
     * This method setting up the context as many test cases share the same values
     * In this we are assigning the London.map file
     *
     * @throws Exception it throws if there are any exceptions found
     */
    @Before
    public void setUp() throws Exception {
        mapModel = new MapModel();
        gameMap = mapModel.readMapFile("/home/akshay/AdvanceProgrammingPracticesRiskGame/London.map");
        playerNames = new ArrayList<String>();
        playerTypes = new ArrayList<String>();

        playerNames.add("John");
        playerNames.add("Alexa");
        playerNames.add("Penny");
        playerNames.add("Sheldon");
        playerNames.add("Raj");
        playerNames.add("Leonard");
        playerNames.add("Howard");
        playerTypes.add("Randomize Bot");
        playerTypes.add("Randomize Bot");
        playerTypes.add("Randomize Bot");
        playerTypes.add("Randomize Bot");
        playerTypes.add("Randomize Bot");
        playerTypes.add("Randomize Bot");
        drawTurns = 1000;
        //createGameMapFromFile(f);
    }

    /**
     * This method checks if fortification for AggressiveBot strategy is working as intended
     *
     * @throws Exception it throws if there are any exceptions found
     */
    @Test
    public void reinforceAggressiveTest() throws Exception {
        initializePlayerData(6, playerNames, playerTypes);
        playerCount = 6;
        setInitialArmies();
        allocateCountriesToPlayers();
        addInitialArmiesInRR();
        gameView = new GameView();
        gameView.setVisible(false);
        currentPlayer = getPlayerList().get(0);
        currentPlayerReinforceArmies = getReinforcementArmyForPlayer(currentPlayer);
        currentPlayer.addArmy(currentPlayerReinforceArmies);
        setStrategy(new AggressiveBot());
        Country strongestCountry = getStrongestCountry(currentPlayer);
        int oldArmy = strongestCountry.getCurrentArmiesDeployed();
        executeReinforce(strongestCountry.getCountryName(), gameView, this);
        assertTrue(oldArmy <= strongestCountry.getCurrentArmiesDeployed());
    }

    /**
     * This method checks if the Aggressive player attack passing only one iteration
     *
     * @throws Exception it throws if there are any exceptions found
     */
    @Test
    public void attackRandomWithOneRandomLoopTest() throws Exception {
        initializePlayerData(6, playerNames, playerTypes);
        playerCount = 6;
        setInitialArmies();
        allocateCountriesToPlayers();
        addInitialArmiesInRR();
        gameView = new GameView();
        gameView.setVisible(false);
        currentPlayer = getPlayerList().get(0);
        setStrategy(new AggressiveBot());
        Country attackingCountry = getStrongestCountry(currentPlayer);
        Country defendingCountry = MapModel.getCountryObj(attackingCountry.getNeighborNodes().get(0).getCountryName(), GameMap.getInstance());
        defendingCountry.setBelongsToPlayer(getPlayerList().get(1));
        defendingCountry.setCurrentArmiesDeployed(10);

        attackingCountry.setCurrentArmiesDeployed(30);

        executeAttack(attackingCountry.getCountryName(), defendingCountry.getCountryName(), gameView, this);

        assertTrue(hasCountryCaptured);
    }

    /**
     * This method checks if the Aggressive player attack iterations passing more than random value
     *
     * @throws Exception it throws if there are any exceptions found
     */
    @Test
    public void attackRandomWithMoreThanRandomLoopTest() throws Exception {
        initializePlayerData(6, playerNames, playerTypes);
        playerCount = 6;
        setInitialArmies();
        allocateCountriesToPlayers();
        addInitialArmiesInRR();
        gameView = new GameView();
        gameView.setVisible(false);
        currentPlayer = getPlayerList().get(0);
        setStrategy(new RandomBot());
        Country attackingCountry = getStrongestCountry(currentPlayer);
        Country defendingCountry = MapModel.getCountryObj(attackingCountry.getNeighborNodes().get(0).getCountryName(), GameMap.getInstance());
        defendingCountry.setBelongsToPlayer(getPlayerList().get(1));
        defendingCountry.setCurrentArmiesDeployed(50);

        attackingCountry.setCurrentArmiesDeployed(5);

        executeAttack(attackingCountry.getCountryName(), defendingCountry.getCountryName(), gameView, this);

        assertTrue(!hasCountryCaptured);
    }

    /**
     * This method checks if fortification for AggressiveBot strategy is working as intended
     *
     * @throws Exception it throws if there are any exceptions found
     */
    @Test
    public void fortificationAggressiveTest() throws Exception {
        initializePlayerData(6, playerNames, playerTypes);
        playerCount = 6;
        setInitialArmies();
        allocateCountriesToPlayers();
        addInitialArmiesInRR();
        gameView = new GameView();
        gameView.setVisible(false);
        currentPlayer = getPlayerList().get(0);
        currentPlayerReinforceArmies = getReinforcementArmyForPlayer(currentPlayer);
        currentPlayer.addArmy(currentPlayerReinforceArmies);
        setStrategy(new AggressiveBot());
        Country strongestCountry = getStrongestCountry(currentPlayer);
        strongestCountry.setCurrentArmiesDeployed(10);
        Country neighbor = MapModel.getCountryObj(strongestCountry.getNeighborNodes().get(0).getCountryName(), GameMap.getInstance());
        executeFortification(strongestCountry.getCountryName(), neighbor.getCountryName(), gameView, this);
        assertTrue(strongestCountry.getCurrentArmiesDeployed() <= neighbor.getCurrentArmiesDeployed());
    }
}
