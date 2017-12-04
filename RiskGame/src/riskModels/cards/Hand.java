package riskModels.cards;

import riskView.GameView;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Allows the creation of Risk Hand objects used to handle the cards held by a player.
 *
 * @author Akshay Shah
 */
public class Hand implements Serializable {

    public boolean condition;

    public ArrayList<Card> hand;

    public Deck deck;

   /**
    * No-arg constructor. Instantiate Deck.
    */
    public Hand() {

        hand = new ArrayList<Card>();
        deck = new Deck();
    }

    /**
     * This method will add card in hand
     * @param card card that you want to add
     */
    public void add(Card card) {

        hand.add(card);
    }

   /**
    * Removes the cards at the given indices from the hand
    * @param index1 card turned in index 1
    * @param index2 card turned in index 2
    * @param index3 card turned in index 3
    */
    public void removeCardsFromHand(int index1, int index2, int index3) {

        if (canTurnInCards(index1, index2, index3)) {
            //addCardsToDeckAgain(index1,index2,index3);
            hand.remove(index3);
            hand.remove(index2);
            hand.remove(index1);

        } else {
            GameView.displayLog("You must trade in three cards of the same type or one of each type.");
        }
    }

    /**
     * returns the card to the deck
     * @param index1 card turned in index 1
     * @param index2 card turned in index 2
     * @param index3 card turned in index 3
     */
    public void addCardsToDeckAgain(int index1, int index2, int index3) {
        Card c1 = hand.get(index1);
        Card c2 = hand.get(index2);
        Card c3 = hand.get(index3);
        deck.add(c1);
        deck.add(c2);
        deck.add(c3);
    }

    /**
    * returns true if the player can turn in cards
    * @param index1 card turned in index 1
    * @param index2 card turned in index 2
    * @param index3 card turned in index 3
    * @return true if the player can turn in cards, otherwise false
    */
    public boolean canTurnInCards(int index1, int index2, int index3) {

        condition = false;

        if (hand.size() >= 3) {
            if (hand.get(index1).getType().equals(hand.get(index2).getType()) && hand.get(index1).getType().equals(hand.get(index3).getType())) {
                //If all three cards have the same type
                condition = true;

            } else if (
                    !hand.get(index1).getType().equals(hand.get(index2).getType()) && !hand.get(index1).getType().equals(hand.get(index3).getType()) && !hand.get(index2).getType().equals(hand.get(index3).getType())) {
                //If all three cards have different types
                condition = true;
            }
        }
        return condition;
    }

   /**
    * getter method which returns true if the player must turn in cards
    * @return true if the player must turn in cards,otherwise false
    */
    public boolean mustTurnInCards() {

        condition = hand.size() >= 5;
        return condition;
    }

   /**
    * getter method returns the hand
    * @return hand player object
    */
    public ArrayList<Card> getCards() {
        return hand;
    }

}