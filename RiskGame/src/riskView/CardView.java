package riskView;

import riskModels.player.Player;

import javax.swing.*;
import java.util.Observable;
import java.util.Observer;

/**
 * Create CardView class with Observer pattern
 * @author hnath
 *
 */
public class CardView extends DefaultListModel implements Observer {

    private Player model;

    /**
     * setter method assigns player object model
     * @param model player object
     * @param type type of the card
     */
    public CardView(Player model, String type) {
        super();
        this.model = model;
    }

    /**
     * overriding update method for getting the current state of the objects
     */
    @Override
    public void update(Observable observable, Object obj) {
        GameView.displayLog("Refreshing...");

        model = (Player) observable;
        String cardArray[] = new String[model.getCardsList().size()];
        int i;
        for (i = 0; i < model.getCardsList().size(); i++) {
            cardArray[i] = model.getCardsList().get(i);
        }
        GameView.updateCardView(cardArray);
    }
}
