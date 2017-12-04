package test.player;

import org.junit.Before;
import org.junit.Test;
import riskModels.country.Country;
import riskModels.map.GameMap;
import riskModels.map.MapModel;
import riskModels.player.Player;
import riskModels.player.RandomBot;
import riskView.GameView;

import java.util.ArrayList;
import java.util.Random;

import static org.junit.Assert.assertTrue;

/**
 * This test class checks whether RandomBot strategy is working as intended or not
 *
 * @author hnath
 */
public class RandomBotTest extends Player {

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
     * This method checks if reinforcement for RandomBot strategy is working as intended
     *
     * @throws Exception it throws if there are any exceptions found
     */
    @Test
    public void reinforceRandomTest() throws Exception {
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
        setStrategy(new RandomBot());
        rng = new Random();
        Country randomCountry = currentPlayer.getAssignedCountries().get(rng.nextInt(currentPlayer.getAssignedCountries().size()));
        int oldArmy = randomCountry.getCurrentArmiesDeployed();
        executeReinforce(randomCountry.getCountryName(), gameView, this);
        assertTrue(oldArmy <= randomCountry.getCurrentArmiesDeployed());
    }

    /**
     * This method checks if the Random player attack iterations passing more than random value
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
        Country attackingCountry = currentPlayer.getAssignedCountries().get(0);
        Country defendingCountry = MapModel.getCountryObj(attackingCountry.getNeighborNodes().get(0).getCountryName(), GameMap.getInstance());
        defendingCountry.setBelongsToPlayer(getPlayerList().get(1));
        defendingCountry.setCurrentArmiesDeployed(50);

        attackingCountry.setCurrentArmiesDeployed(5);

        executeAttack(attackingCountry.getCountryName(), defendingCountry.getCountryName(), gameView, this);

        assertTrue(!hasCountryCaptured);
    }

    /**
     * This method checks if fortification for RandomBot strategy is working as intended
     *
     * @throws Exception it throws if there are any exceptions found
     */
    @Test
    public void fortificationRandomTest() throws Exception {
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
        setStrategy(new RandomBot());
        rng = new Random();
        Country randomCountry = currentPlayer.getAssignedCountries().get(rng.nextInt(currentPlayer.getAssignedCountries().size()));
        randomCountry.setCurrentArmiesDeployed(10);
        Country neighbor = MapModel.getCountryObj(randomCountry.getNeighborNodes().get(0).getCountryName(), GameMap.getInstance());
        neighbor.setCurrentArmiesDeployed(5);
        executeFortification(randomCountry.getCountryName(), neighbor.getCountryName(), gameView, this);
        assertTrue(randomCountry.getCurrentArmiesDeployed() < 10);
        assertTrue(neighbor.getCurrentArmiesDeployed() > 5);
    }
}
