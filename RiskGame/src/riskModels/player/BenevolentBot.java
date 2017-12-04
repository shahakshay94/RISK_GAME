package riskModels.player;

import riskModels.country.Country;
import riskModels.map.GameMap;
import riskModels.map.MapModel;
import riskView.GameView;

/**
 * This class implements the strategy for Benevolent bot
 * @author Akshay Shah
 *
 */
public class BenevolentBot implements PlayerStrategy {

    public Country countryA;
    public Country countryB;
    
    /**
     * Overrides the attack phase for benevolent bot
     * from the PlayerStartegy interface
     * @param country1 name of the attacker's country
     * @param country2 name of the defender's country
     * @param gameView object of GameView class
     * @param model object of Player class
     * 
     */
    @Override
    public void attack(String country1, String country2, GameView gameView, Player model) {
        GameView.displayLog("Skipping Attack Phase as player is benevolent.");
        GameView.displayLog(model.currentPlayer.getName()+" is too afraid to attack ! Skipping the attack phase.");
        model.updatePhaseDetails("Repaint");
        model.updatePhaseDetails("==Attack Phase==");
        model.updatePhaseDetails("Skipping Attack Phase as player is benevolent.");
        model.updatePhaseDetails("====Attack Phase ended===");
    }
    /**
     * Overrides the fortify phase for benevolent bot
     * from the PlayerStrategy interface
     * @param country1 name of the attacker's country
     * @param country2 name of the defender's country
     * @param gameView object of GameView class
     * @param model object of Player class
     * 
     */
    @Override
    public void fortify(String country1, String country2, GameView gameView, Player model) {
        countryA = MapModel.getCountryObj(country1, GameMap.getInstance());
        countryB = MapModel.getCountryObj(country2, GameMap.getInstance());

        // Player inputs how many armies to move from country A to country B
        model.updatePhaseDetails("Repaint");
        model.updatePhaseDetails("===Fortification phase===");

        int armies = countryA.getCurrentArmiesDeployed() - 1;

        model.moveArmyFromTo(countryA, countryB, armies);
        GameView.updateMapPanel();
        GameView.displayLog("Benevolent moved "+armies+" army from "+countryA.getCountryName()+" to " + countryB.getCountryName());
        model.updatePhaseDetails("You moved "+armies+" army from "+countryA.getCountryName()+" to " + countryB.getCountryName());
        model.checkHasCountryCaptured();
        model.updatePhaseDetails("===Fortification ends===");

    }
    /**
     * Overrides the reinforcement phase for benevolent bot
     * from the PlayerStrategy interface
     * @param country name of the the player's country
     * @param gameView object of GameView class
     * @param model object of Player class
     * 
     */
    @Override
    public void reinforce(String country, GameView gameView, Player model) {
        countryA = MapModel.getCountryObj(country, GameMap.getInstance());
        GameView.displayLog("\n===Reinforcement phase for Benevolent type player begins===");
        GameView.displayLog(model.currentPlayer.name + " gets " + model.currentPlayerReinforceArmies + " armies");
        try {
            if (model.currentPlayer.getTotalArmies() > 0) {
                int armies = model.currentPlayer.getTotalArmies();
                model.currentPlayer.subArmy(armies);
                countryA.addArmy(armies);
                GameView.displayLog(model.currentPlayer.getName() + " has chosen to reinforce " + countryA.getCountryName() + " with " + armies + " armies.");
                if (model.currentPlayer.getTotalArmies() == 0) {
                    GameView.displayLog("You do not have any armies left to reinforce");
                    GameView.displayLog("===Reinforcement phase for Benevolent type player ends===\n");
                    model.updatePhaseDetails("\nReinforcement Phase ends");
                }
                GameView.updateMapPanel();
            }

        } catch (Exception e) {
            GameView.displayLog("\nSystem Error or Exception is thrown for reinforce method");
        }
    }
}
