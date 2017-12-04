package riskControllers;

import riskModels.map.MapModel;
import riskModels.player.Player;
import riskView.GameMenuView;
import riskView.GameView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

/**
 * This GameMenuViewController class gives menu buttons function
 * @author hnath
 *
 */
public class GameMenuViewController implements ActionListener {

    public GameMenuView view;
    public Player model;

    /**
     * constructor 
     * @param menuView menu view object
     * @param model model object
     */
    public GameMenuViewController(GameMenuView menuView, Player model) {
        // TODO Auto-generated constructor stub
        this.view = menuView;
        this.model = model;
    }

    /**
     * over rides actionPerformed method
     * and have 3 buttons to return,save and quit the game
     */
    @Override
    public void actionPerformed(ActionEvent evt) {
        String actionEvent = evt.getActionCommand();

        switch (actionEvent) {
            case "returnBtn":
                view.dispose();
                break;

            case "saveBtn":
			try {
				model.saveGame();

			} catch (Exception e) {

				e.printStackTrace();
			}
                break;

            case "quitBtn":
                System.exit(1);
                break;

            default:
                System.out.println("action Event not found: " + actionEvent);
                break;
        }
    }
}
