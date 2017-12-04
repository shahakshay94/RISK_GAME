package riskControllers;

import riskView.FileSelectDialog;
import riskView.PlayerSettingsView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.util.ArrayList;

/**
 * This class sets the Player Controller
 * @author hnath
 *
 */
public class PlayerSettingsController implements ActionListener {
    private PlayerSettingsView view;
    private int playerCount;
    private FileSelectDialog fileSelectDialog;

    private ArrayList<String> playerNames;
    private ArrayList<String> playerTypes;

    /**
     * This constructor assigning players count and view
     * @param playerSettingsView Player object
     * @param playerCount Number of players
     */
    public PlayerSettingsController(PlayerSettingsView playerSettingsView, int playerCount) {
        this.fileSelectDialog = new FileSelectDialog();
        this.view = playerSettingsView;
        this.playerCount = playerCount;
    }

    /**
     * over rides action performed method
     */
    @Override
    public void actionPerformed(ActionEvent evt) {
        String actionEvent = evt.getActionCommand();
        playerNames = new ArrayList<String>();
        playerTypes = new ArrayList<String>();

        switch (actionEvent) {
            case "startBtn":

                System.out.println("Setting up player names and teams...");

                playerNames.add(view.getPlayerTextField(1));
                playerNames.add(view.getPlayerTextField(2));
                playerNames.add(view.getPlayerTextField(3));
                playerTypes.add(view.getPlayerComboBox(1));
                playerTypes.add(view.getPlayerComboBox(2));
                playerTypes.add(view.getPlayerComboBox(3));

                //Gets player names based on playerCount
                if (playerCount > 3) {
                    playerNames.add(view.getPlayerTextField(4));
                    playerTypes.add(view.getPlayerComboBox(4));
                }
                if (playerCount > 4) {
                    playerNames.add(view.getPlayerTextField(5));
                    playerTypes.add(view.getPlayerComboBox(5));
                }
                if (playerCount > 5) {
                    playerNames.add(view.getPlayerTextField(6));
                    playerTypes.add(view.getPlayerComboBox(6));
                }

                //Initializes values for a new game
                //Open the MapFileChooseDialog box over here.
                //Instantiate the class over here and pass the value
                System.out.println("Now Choose the map file from the Dialog box");
                fileSelectDialog.addActionListener(new FileSelectDialogController(fileSelectDialog, playerCount,playerNames,playerTypes));
                fileSelectDialog.setVisible(true);
                view.dispose();
                break;
            case "backBtn":
                view.dispose();

                break;
            default:
                System.out.println("Error: " + actionEvent + " actionEvent not found!");
                break;
        }
    }
}
