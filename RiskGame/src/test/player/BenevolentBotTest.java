package test.player;

import org.junit.Before;
import org.junit.Test;
import riskModels.country.Country;
import riskModels.map.GameMap;
import riskModels.map.MapModel;
import riskModels.player.BenevolentBot;
import riskModels.player.Player;
import riskView.GameView;

import java.util.ArrayList;

import static org.junit.Assert.assertTrue;

/**
 * This test class checks whether BenevolentBot strategy is working as intended or not
 *
 * @author hnath
 */
public class BenevolentBotTest extends Player {
    String location = PlayerTest.class.getProtectionDomain().getCodeSource().getLocation().getPath();
    private GameMap gameMap;
    private GameView gameView;
    private ArrayList<String> playerNames;
    private ArrayList<String> playerTypes;
    private String filePath;

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
        playerTypes.add("Benevolent Bot");
        playerTypes.add("Benevolent Bot");
        playerTypes.add("Benevolent Bot");
        playerTypes.add("Benevolent Bot");
        playerTypes.add("Benevolent Bot");
        playerTypes.add("Benevolent Bot");
        drawTurns = 1000;
        //createGameMapFromFile(f);
    }

    /**
     * This method checks if reinforcement for BenevolentBot strategy is working as intended
     *
     * @throws Exception it throws if there are any exceptions found
     */
    @Test
    public void reinforceBenevolentTest() throws Exception {
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
        setStrategy(new BenevolentBot());
        Country weakestCountry = getWeakestCountry(currentPlayer);
        int oldArmy = weakestCountry.getCurrentArmiesDeployed();
        executeReinforce(weakestCountry.getCountryName(), gameView, this);
        assertTrue(oldArmy <= weakestCountry.getCurrentArmiesDeployed());
    }

    /**
     * This method checks if fortification for BenevolentBot strategy is working as intended
     *
     * @throws Exception it throws if there are any exceptions found
     */
    @Test
    public void fortificationBenevolentTest() throws Exception {
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
        setStrategy(new BenevolentBot());
        Country weakestCountry = getWeakestCountry(currentPlayer);
        weakestCountry.setCurrentArmiesDeployed(10);
        Country neighbor = MapModel.getCountryObj(weakestCountry.getNeighborNodes().get(0).getCountryName(), GameMap.getInstance());
        executeFortification(weakestCountry.getCountryName(), neighbor.getCountryName(), gameView, this);
        assertTrue(weakestCountry.getCurrentArmiesDeployed() <= neighbor.getCurrentArmiesDeployed());
    }


}
