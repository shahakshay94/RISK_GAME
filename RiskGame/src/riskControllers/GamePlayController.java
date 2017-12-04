package riskControllers;

import riskModels.player.Player;
import riskView.GameMenuView;
import riskView.GameView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * This class maps the user's input to the data and methods in the model
 * @author hnath
 *
 */
public class GamePlayController implements ActionListener {

    private Player model;
    private GameView view;
    private GameMenuView menuView;

    /**
     * setter method assigns model and gameView to the controller
     * @param model player object
     * @param gameView game view object
     * @param loadGame game load object
     */
    public GamePlayController(Player model, GameView gameView,boolean loadGame) {
        this.model = model;
        this.view = gameView;
        if (loadGame){
            model.populateLogsAndPhase();
        }else{
            model.startGame(this.model, this.view);
        }

    }

    /**
     * overriding action performed method takes the user actions(buttons clicked) and maps to model
     */
    @Override
    public void actionPerformed(ActionEvent evt) {
        String actionEvent = evt.getActionCommand();

        switch (actionEvent) {
            case "menuBtn":
            	menuView = new GameMenuView();
            	menuView.addActionListeners(new GameMenuViewController(menuView, model));
            	menuView.setVisible(true);
                break;

            case "turnInBtn":
                model.turnInCards(view.getCardsToRemove());

                break;
            case "reinforceBtn":
                if (view.getCountryA() != null) {
                    model.reinforce(view.getCountryA().replaceAll("[0-9]", " ").replaceAll("\\-", " "), view, model);
                } else {
                    GameView.displayLog("Please make sure that country is selected from the list !");
                }

                break;
            case "attackBtn":
                if (view.getCountryA() != null && view.getCountryB() != null) {
                    model.attack(view.getCountryA().trim().replaceAll("[0-9]", "").replaceAll("\\-", ""), view.getCountryB().trim().replaceAll("[0-9]", "").replaceAll("\\-", ""), view, model);
                } else {
                    GameView.displayLog("Please make sure that country is selected from both the list !");
                }

                break;
            case "fortifyBtn":
                if (view.getCountryA() != null && view.getCountryB() != null) {
                    model.fortify(view.getCountryA().replaceAll("[0-9]", " ").replaceAll("\\-", " "), view.getCountryB().replaceAll("[0-9]", " ").replaceAll("\\-", " "), view, model);
                } else {
                    GameView.displayLog("Please make sure that country is selected from both the list !");
                }

                break;
            case "endTurnBtn":
                model.endPlayerTurn(model);

                break;
            default:
                System.out.println("action Event not found: " + actionEvent);
                break;
        }
    }
}
