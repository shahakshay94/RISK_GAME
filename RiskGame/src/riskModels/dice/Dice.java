package riskModels.dice;

import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

/**
 * this class sets and gets the properties of cards
 * @author hnath
 *
 */
public class Dice {
    int numberOfDice; //number of Dice user wants to roll
    Integer[] diceResult; //the dice result example : first dice roll result will be stored in diceResult[0]
    public boolean isDiceRolled; // check if dice rolled

    /**
     * Returns result of Dice roll stored into object of values between 1 and 6 representing the
     * outcome of rolling the dice.  The number of values in the array should be
     * between 1 and 3, depending on the number of dice rolled by the player.  The
     * number of dice rolled by the player is specified by the argument numberOfDice
     *
     * @param numberOfDice number of Dice User wants to roll
     * @return return diceModel object that will have dice roll result
     */
    public static Dice rollDice(int numberOfDice) {
        Dice dicemodel = new Dice();
        //check if we can roll the dice or not
        if (numberOfDice <= 0 || numberOfDice > 3) {
            dicemodel.setDiceRolled(false);
            return dicemodel;
        }
        Integer[] diceResult = new Integer[numberOfDice];
        for (int counter = 0; counter < numberOfDice; counter++) {
            Random random = new Random();
            int result = random.nextInt(5) + 1; //this function will give results between 1-6 including both
            diceResult[counter] = result;
        }
        //Sorting
        Arrays.sort(diceResult,Collections.reverseOrder());
        dicemodel.setDiceRolled(true);
        dicemodel.setDiceResult(diceResult);
        dicemodel.setNumberOfDice(numberOfDice);
        return dicemodel;

    }

    /**
     * getter method checks whether dices is rolled or not
     * @return true if dices is rolled ,otherwise false
     */
    public boolean isDiceRolled() {
        return isDiceRolled;
    }

    /**
     * setter method assigns boolean result of is dice rolled
     * @param isDiceRolled true or false values
     */
    public void setDiceRolled(boolean isDiceRolled) {
        this.isDiceRolled = isDiceRolled;
    }

    /**
     * getter method gives number of dices to be rolled
     * @return values of number of dices
     */
    public int getNumberOfDice() {
        return numberOfDice;
    }

    /**
     * setter method assigns number of dices to be rolled
     * @param numberOfDice number of dices to be rolled
     */
    public void setNumberOfDice(int numberOfDice) {
        this.numberOfDice = numberOfDice;
    }

    /**
     * getter method gives the result of rolled dices
     * @return value of the result
     */
    public Integer[] getDiceResult() {
        return diceResult;
    }


    /**
     * setter method assigns the result of rolled dices
     * @param diceResult value of result of rolled dices
     */
    public void setDiceResult(Integer[] diceResult) {
        this.diceResult = diceResult;
    }
}
