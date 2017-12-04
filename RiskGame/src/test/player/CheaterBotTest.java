package test.player;

import org.junit.Before;
import org.junit.Test;
import riskModels.country.Country;
import riskModels.map.GameMap;
import riskModels.map.MapModel;
import riskModels.player.CheaterBot;
import riskModels.player.Player;
import riskView.GameView;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * This test class checks whether CheaterBot strategy is working as intended or not
 *
 * @author hnath
 */
public class CheaterBotTest extends Player {
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
        playerTypes.add("Cheater Bot");
        playerTypes.add("Cheater Bot");
        playerTypes.add("Cheater Bot");
        playerTypes.add("Cheater Bot");
        playerTypes.add("Cheater Bot");
        playerTypes.add("Cheater Bot");
        drawTurns = 1000;
        //createGameMapFromFile(f);
    }

    /**
     * This method checks if reinforcement for CheaterBot strategy is working as intended
     *
     * @throws Exception it throws if there are any exceptions found
     */
    @Test
    public void reinforceCheaterTest() throws Exception {
        initializePlayerData(6, playerNames, playerTypes);
        playerCount = 6;
        setInitialArmies();
        allocateCountriesToPlayers();
        addInitialArmiesInRR();
        gameView = new GameView();
        gameView.setVisible(false);
        currentPlayer = getPlayerList().get(0);
        setStrategy(new CheaterBot());
        List<Country> cheaterCountries = new ArrayList<>();
        int oldArmy = currentPlayer.getAssignedCountries().get(0).getCurrentArmiesDeployed();
        for (Country country : currentPlayer.getAssignedCountries()) {
            executeReinforce(country.getCountryName(), gameView, this);
            cheaterCountries.add(country);
        }
        int newArmies = cheaterCountries.get(0).getCurrentArmiesDeployed();
        assertEquals(oldArmy * 2, newArmies);
    }

    /**
     * This method checks if attack phase for CheaterBot strategy is working as intended
     *
     * @throws Exception it throws if there are any exceptions found
     */
    @Test
    public void attackCheaterTest() throws Exception {
        initializePlayerData(6, playerNames, playerTypes);
        playerCount = 6;
        setInitialArmies();
        allocateCountriesToPlayers();
        addInitialArmiesInRR();
        gameView = new GameView();
        gameView.setVisible(false);
        currentPlayer = getPlayerList().get(0);
        setStrategy(new CheaterBot());
        List<Country> cheaterCountries = new ArrayList<>();
        List<Country> validAttackingCountries = new ArrayList<>();
        for (Country country : currentPlayer.getAssignedCountries()) {
            executeReinforce(country.getCountryName(), gameView, this);
            cheaterCountries.add(country);
        }

        attackPhase:
        for (Country attackingCountry : cheaterCountries) {
            List<Country> neighbors = attackingCountry.getNeighborNodes();
            for (Country neighbor : neighbors) {
                Country defenderCountry = MapModel.getCountryObj(neighbor.getCountryName(), GameMap.getInstance());
                if (isAttackValidForCheater(currentPlayer, attackingCountry, defenderCountry)) {
                    validAttackingCountries.add(attackingCountry);
                    break attackPhase;
                }
            }
        }

        if (validAttackingCountries != null) {
            Country attackingCountry = validAttackingCountries.get(0);
            for (Country neighbor : attackingCountry.getNeighborNodes()) {
                Country defenderCountry = MapModel.getCountryObj(neighbor.getCountryName(), GameMap.getInstance());
                if (isAttackValidForCheater(currentPlayer, attackingCountry, defenderCountry)) {
                    executeAttack(attackingCountry.getCountryName(), defenderCountry.getCountryName(), gameView, this);
                }
            }
        }

        Boolean hasAllNeighborsOccupied = false;
        int counter = 0;
        for (Country neighbor : validAttackingCountries.get(0).getNeighborNodes()) {
            Country country = MapModel.getCountryObj(neighbor.getCountryName(), GameMap.getInstance());
            if (country.getBelongsToPlayer().getName().equals(currentPlayer.getName())) {
                counter++;
            }
        }
        if (counter == validAttackingCountries.get(0).getNeighborNodes().size()){
            hasAllNeighborsOccupied = true;
        }
        assertTrue(hasAllNeighborsOccupied);
    }

    /**
     * This method checks if fortification for CheaterBot strategy is working as intended
     *
     * @throws Exception it throws if there are any exceptions found
     */
    @Test
    public void fortificationCheaterTest() throws Exception {
        initializePlayerData(6, playerNames, playerTypes);
        playerCount = 6;
        setInitialArmies();
        allocateCountriesToPlayers();
        addInitialArmiesInRR();
        gameView = new GameView();
        gameView.setVisible(false);
        currentPlayer = getPlayerList().get(0);
        setStrategy(new CheaterBot());
        List<Country> cheaterCountries = new ArrayList<>();
        int oldArmy = currentPlayer.getAssignedCountries().get(0).getCurrentArmiesDeployed();
        for (Country country : currentPlayer.getAssignedCountries()) {
            executeFortification(country.getCountryName(), null, gameView, this);
            cheaterCountries.add(country);
        }
        int newArmies = cheaterCountries.get(0).getCurrentArmiesDeployed();
        assertEquals(oldArmy * 2, newArmies);
    }

}
