package riskModels.player;

import riskView.GameView;

/**
 * This is an interface of player's attack,reinforcement,fortify method .
 * @author hnath
 *
 */
public interface PlayerStrategy {
/**
 * Attack Method for the player. Based on Player type , attack method will be executed according in respective java class 
 * @param country1 Country from where you want to attack 
 * @param country2 Enemy Country
 * @param gameView current GameView Object
 * @param model current Player type model
 */
 public   void attack(String country1, String country2, GameView gameView, Player model);

 /**
 * Fortify Method for the player. Based on Player type , fortify method will be executed according in respective java class 
 * @param country1 country from where you want to move army
 * @param country2 country where you want to move army
 * @param gameView current GameView Object
 * @param model current Player type model
 */
 public   void fortify(String country1, String country2, GameView gameView, Player model);
/**
 * Reinforce Method for the player. Based on Player type , Reinforce method will be executed according in respective java class 
 * @param country country where you want to reinforce Army
 * @param gameView current GameView Object
 * @param model current Player type model
 */
 public void reinforce(String country, GameView gameView, Player model);
}
