package test.cards;

import org.junit.Test;
import riskModels.dice.Dice;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * This class tests if the dices function is working as intended
 *
 * @author hnath
 */
public class DiceTest {

    /**
     * This method checks whether the dices result
     *
     * @throws Exception it throws if there are any exceptions found
     */
    @Test
    public void testDiceResult() throws Exception {
        assertTrue(Dice.rollDice(3).isDiceRolled);
    }

    /**
     * This method checks if the dice result more than three or not
     *
     * @throws Exception it throws if there are any exceptions found
     */
    @Test
    public void testDiceResultMoreThanThree() throws Exception {
        assertFalse(Dice.rollDice(6).isDiceRolled);
    }

    /**
     * This method checks the rolled dice result which should be more than 0 and less than 7
     *
     * @throws Exception it throws if there are any exceptions found
     */
    @Test
    public void testRollDice() throws Exception {
        Integer[] i = Dice.rollDice(3).getDiceResult();
        assertTrue(i[0] >= i[1]);
        assertTrue(i[0] < 7);
        assertTrue(i[0] > 0);
    }
}
