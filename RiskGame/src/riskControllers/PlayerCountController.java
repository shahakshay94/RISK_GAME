package riskControllers;

import riskView.FileSelectDialog;
import riskView.PlayerCount;
import riskView.PlayerSettingsView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static util.RiskGameUtil.*;

/**
 * This class maps the user's input to the data and methods in the model
 */
public class PlayerCountController implements ActionListener {
    private PlayerCount view;
    private FileSelectDialog fileSelectDialog;
    private PlayerSettingsView playerSettingsView;

    /**
     * setter method assigns view 
     * @param view player count object
     */
    public PlayerCountController(PlayerCount view) {
        this.fileSelectDialog = new FileSelectDialog();
        this.view = view;
    }

    /**
     * overriding action performed method passing number of players selected to the model
     */
    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        String event = actionEvent.getActionCommand();
        if (event.equals(threePlayersBtnName)) {

            playerSettingsView = new PlayerSettingsView(view, true, 3);
            playerSettingsView.addActionListeners(new PlayerSettingsController(playerSettingsView, 3 ));
            playerSettingsView.setVisible(true);

        } else if (event.equals(fourPlayersBtnName)) {

            playerSettingsView = new PlayerSettingsView(view, true, 4);
            playerSettingsView.addActionListeners(new PlayerSettingsController(playerSettingsView, 4 ));
            playerSettingsView.setVisible(true);
        } else if (event.equals(fivePlayersBtnName)) {
            playerSettingsView = new PlayerSettingsView(view, true, 5);
            playerSettingsView.addActionListeners(new PlayerSettingsController(playerSettingsView, 5 ));
            playerSettingsView.setVisible(true);
        } else if (event.equals(sixPlayersBtnName)) {
            playerSettingsView = new PlayerSettingsView(view, true, 6);
            playerSettingsView.addActionListeners(new PlayerSettingsController(playerSettingsView, 6 ));
            playerSettingsView.setVisible(true);
        } else if (event.equals(backBtnName)) {
            view.dispose();
        } else {
            System.out.println("Error: " + actionEvent + " actionEvent not found!");
        }
    }
}
