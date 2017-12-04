package riskModels.player;

import riskModels.country.Country;
import riskModels.dice.Dice;
import riskModels.map.GameMap;
import riskModels.map.MapModel;
import riskView.GameView;
import riskView.PlayerView;

import javax.swing.*;
import javax.swing.plaf.basic.BasicIconFactory;
import java.util.Objects;
import java.util.Random;
/**
 * This class implements the strategy of Random bot player
 * @author mudraparikh
 *
 */
public class RandomBot implements PlayerStrategy {

    public Country countryA;
    public Country countryB;

    public Dice dice;

    public int attackerLosses;
    public int defenderLosses;
    public int attackerDice;
    public int defenderDice;

    public Integer[] attackerRolls;
    public Integer[] defenderRolls;

    public Random rng;
    
    /**
     * Overrides the attack phase for Random bot
     * from he PlayerStrategy interface
     * @param country1 name of the attacker's country
     * @param country2 name of the defender's country
     * @param gameView object of GameView class
     * @param model object of Player class
     * 
     */
    @Override
    public void attack(String country1, String country2, GameView gameView, Player model) {
        countryA = MapModel.getCountryObj(country1, GameMap.getInstance());
        countryB = MapModel.getCountryObj(country2, GameMap.getInstance());
        GameView.displayLog("\n===Attack phase for random player type begins===");
        model.updatePhaseDetails("Repaint");
        model.updatePhaseDetails("===Attack Phase==");
        rng = new Random();
        int randomTimeAttacks = rng.nextInt(9)+1;
        while(randomTimeAttacks > 0){
            if(checkPlayerTurnCanContinue(countryA,countryB)){
                dice = new Dice();

                // Set default values
                attackerLosses = 0;
                defenderLosses = 0;
                attackerDice = 1;
                defenderDice = 1;

                // Attacker chooses how many dice to roll
                rng = new Random();
                if (countryA.getCurrentArmiesDeployed() <= 3) {
                    attackerDice = 1;
                } else {
                    attackerDice = rng.nextInt(2) + 1;
                }

                // Defender chooses how many dice to roll after attacker
                if(countryB.getBelongsToPlayer().isBot()){
                    rng = new Random();
                    if (countryB.getCurrentArmiesDeployed() <= 1) {
                        defenderDice = 1;
                    } else {
                        defenderDice = rng.nextInt(1) + 1;
                    }
                }
                else {
                    defenderDice = showDefenderDiceDialogBox(gameView, model);
                }

                //Dices are rolled
                attackerRolls = Dice.rollDice(attackerDice).getDiceResult();
                defenderRolls = Dice.rollDice(defenderDice).getDiceResult();

                GameView.displayLog("\n"+countryA.getBelongsToPlayer().getName()+" (attacker) threw  dice(s) : ");
                for (int attackerRoll : attackerRolls) {
                    GameView.displayLog(" " + attackerRoll + " ");
                }
                GameView.displayLog("\n"+countryB.getBelongsToPlayer().getName()+" (defender) threw  dice(s) : ");
                for (int defenderRoll : defenderRolls) {
                    GameView.displayLog(" " + defenderRoll + " ");
                }
                // Rolls arrays have been ordered in descending order. Index 0 = highest pair
                compareDiceResultsAndCalculateLosses();
                GameView.displayLog("\n\n<COMBAT REPORT>");

                updateArmiesBasedOnDiceResult(attackerLosses, defenderLosses);

                GameView.displayLog(countryA.getBelongsToPlayer().getName()+" (attacker) losses : " + attackerLosses + " army.");
                GameView.displayLog(countryB.getBelongsToPlayer().getName()+" (defender) losses : " + defenderLosses + " army.");
                GameView.displayLog(countryA.getBelongsToPlayer().getName()+"'s (attacker) " +countryA.getCountryName() + " has now " + countryA.getCurrentArmiesDeployed());
                GameView.displayLog(countryB.getBelongsToPlayer().getName()+"'s (defender)"+ countryB.getCountryName() + " has now " + countryB.getCurrentArmiesDeployed());
                GameView.displayLog("\n\n");
                model.updatePhaseDetails("<Based On Dice Results> \n");
                model.updatePhaseDetails("Attacker Losses : " + attackerLosses + " army." + "\n" + "Defender Losses : " + defenderLosses + " army.");

                // If defending country loses all armies
                if (countryB.getCurrentArmiesDeployed() < 1) {

                    GameView.displayLog(countryA.getBelongsToPlayer().getName() + " has defeated all of " + countryB.getBelongsToPlayer().getName() + "'s armies in " + country2 + " and has occupied the country!");
                    defendingPlayerLostCountry(countryA, countryB, model);
                }

                //If player conquered all the country and have won the game
                if (model.currentPlayer.assignedCountries.size() == GameMap.getInstance().getCountries().size()) {
                    Player.hasBotWon = true;
                    GameView.displayLog("" + model.currentPlayer.getName() + " has won the game ! Congratulations ! ");
                    model.updatePhaseDetails(model.currentPlayer.getName() + "Won");
                }
                GameView.updateMapPanel();

                randomTimeAttacks--;

            }else {
                GameView.displayLog("Looks like random player type cannot attack anymore ! ");
                break;
            }
        }

    }
    /**
     * Checks if the defending player has lost armies
     * @param countryA object of Country class
     * @param countryB object of Country class
     * @param model object of Player class
     * 
     */
    private void defendingPlayerLostCountry(Country countryA, Country countryB, Player model) {
        // Remove country from defender's list of occupied territories and adds to attacker's list
        countryB.getBelongsToPlayer().assignedCountries.remove(countryB);
        countryA.getBelongsToPlayer().assignedCountries.add(countryB);

        // Check if defender is eliminated from game
        if (countryB.getBelongsToPlayer().getAssignedCountries().size() == 0) {
            model.playerLostRule(countryA, countryB);
        }
        // Set country player to attacker
        countryB.setBelongsToPlayer(countryA.getBelongsToPlayer());
        model.updatePhaseDetails("\n"+countryB.getCountryName()+" has been captured ! ");

        //The attacking player must then place a number of armies
        //in the conquered country which is greater or equal than the number of dice that was used in the attack that
        //resulted in conquering the country
        int moveArmies = attackerDice;

        countryA.subtractArmy(moveArmies);
        countryB.addArmy(moveArmies);
        model.hasCountryCaptured = true;
        //model.addObserver(new PlayerView());
        model.updateDomination();
    }
    /**
     * Updates the armies of the attacker/defender based on the dice roll result
     * @param attackerLosses number of armies lost by attacker
     * @param defenderLosses number of armies lost by defender
     * 
     */
    private void updateArmiesBasedOnDiceResult(int attackerLosses, int defenderLosses) {
        countryA.subtractArmy(attackerLosses);
        countryB.subtractArmy(defenderLosses);
    }
    /**
     * Compares the dice results for attacker and defender
     * and calculates the army loss for them
     * 
     */
    private void compareDiceResultsAndCalculateLosses() {
        // Calculate losses
        if (attackerRolls[0] > defenderRolls[0]) {
            defenderLosses++;
        } else if (attackerRolls[0] < defenderRolls[0] || Objects.equals(attackerRolls[0], defenderRolls[0])) {
            attackerLosses++;
        }
        // Index 1 = second highest pair
        if (attackerDice > 1 && defenderDice > 1) {

            if (attackerRolls[1] > defenderRolls[1]) {
                defenderLosses++;

            } else if (attackerRolls[1] < defenderRolls[1] || Objects.equals(attackerRolls[1], defenderRolls[1])) {
                attackerLosses++;
            }
        }
    }
    /**
     * Displays the dialogBox for dice roll of defender
     * @param gameView object of GameView class
     * @param model  object of Player class
     * @return JOptionPane message
     */
    private int showDefenderDiceDialogBox(GameView gameView, Player model) {
        Integer[] selectOptions = new Integer[getMaxNumberOfDicesForDefender(countryB)];
        for (int i = 0; i < selectOptions.length; i++) {
            selectOptions[i] = i + 1;
        }
        model.updatePhaseDetails(countryB.getBelongsToPlayer().getName()+" is Defending ");
        return (Integer) JOptionPane.showInputDialog(gameView,
                countryB.getBelongsToPlayer().getName() + ", you are defending " + countryB.getCountryName() + " from " + countryA.getBelongsToPlayer().getName() + "! How many dice will you roll?",
                "Input", JOptionPane.OK_OPTION, BasicIconFactory.getMenuArrowIcon(), selectOptions,
                selectOptions[0]);
    }
    /**
     * Number of dice roll for defender depending on the armies
     * @param country object of Country class
     * @return number of dices that can be rolled
     */
    private int getMaxNumberOfDicesForDefender(Country country) {
        return country.getCurrentArmiesDeployed() >= 2 ? 2 : 1;
    }
    /**
     * Checks if player can still continue to attack depending on the armies left
     * @param countryA object of Country class
     * @param countryB object of Country class
     * @return boolean value true or false
     */
    private boolean checkPlayerTurnCanContinue(Country countryA, Country countryB) {
        if(countryA.getCurrentArmiesDeployed() > 1 && !countryB.getBelongsToPlayer().getName().equals(countryA.getBelongsToPlayer().getName())){
            return true;
        }
        return false;
    }
    /**
     * Overrides fortify phase for Random bot
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
        rng = new Random();
        // Player inputs how many armies to move from country A to country B
        model.updatePhaseDetails("Repaint");
        model.updatePhaseDetails("===Fortification phase===");


        int armies = rng.nextInt(countryA.getCurrentArmiesDeployed()-1);
        if (armies  == 0) armies = 1;

        model.moveArmyFromTo(countryA, countryB, armies);
        GameView.updateMapPanel();
        model.updatePhaseDetails(model.currentPlayer.getName()+" moved "+armies+" army from "+countryA.getCountryName()+" to " + countryB.getCountryName());
        model.checkHasCountryCaptured();
        model.updatePhaseDetails("===Fortification ends===");
    }
    /**
     * Overrides reinforcement phase for Random bot
     * from the PlayerStrategy interface
     * @param country name of country where armies are to be reinforced
     * @param gameView object of GameView class
     * @param model object of Player class
     */
    @Override
    public void reinforce(String country, GameView gameView, Player model) {
        countryA = MapModel.getCountryObj(country, GameMap.getInstance());
        GameView.displayLog("\n===Reinforcement phase for Random type player begins===");
        GameView.displayLog(model.currentPlayer.name + " gets " + model.currentPlayerReinforceArmies + " armies");
        try {
            if (model.currentPlayer.getTotalArmies() > 0) {
                rng = new Random();
                int armies = rng.nextInt(model.currentPlayer.getTotalArmies());
                if (armies  == 0) armies = 1;
                model.currentPlayer.subArmy(armies);
                countryA.addArmy(armies);
                GameView.displayLog(model.currentPlayer.getName() + " has chosen to reinforce " + countryA.getCountryName() + " with " + armies + " armies.");
                if (model.currentPlayer.getTotalArmies() == 0) {
                    GameView.displayLog(model.currentPlayer.getName()+" do not have any armies left to reinforce");
                    GameView.displayLog("===Reinforcement phase for Random type player ends===\n");
                    model.updatePhaseDetails("\nReinforcement Phase ends");
                }
                GameView.updateMapPanel();
            }

        } catch (Exception e) {
            GameView.displayLog("\nSystem Error or Exception is thrown for reinforce method");
        }
    }
}
