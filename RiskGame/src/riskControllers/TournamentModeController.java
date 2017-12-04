package riskControllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import riskModels.map.GameMap;
import riskModels.map.MapModel;
import riskModels.player.Player;
import riskView.GameView;
import riskView.TournamentMode;
import riskView.TournamentView;
import riskModels.tournament.TournamentModel;

import static util.RiskGameUtil.*;

/**
 * This class maps the user's input to the data and methods.
 * @author mudraparikh
 *
 */
public class TournamentModeController implements ActionListener{
	private JFileChooser fileChooser = new JFileChooser();
	private TournamentMode view;
	GameMap gameMap = GameMap.getInstance();
    public boolean allValidMaps;
    MapModel mapModel = new MapModel();
    String fileName,fileName1,fileName2;
    List<String> selectedFiles=new ArrayList<String>();
    StringBuilder stringBuilder = new StringBuilder();
    GameView gameView;
    private ArrayList<String> playerNames;
    private ArrayList<String> playerTypes;
    /**
     * Constructor assigning view
     * @param view riskModels.tournament mode view
     */
    public TournamentModeController(TournamentMode view) {
		playerNames = new ArrayList<String>();
		playerTypes = new ArrayList<String>();
    	this.view = view;
	}

    /**
     * over rides action performed method
     */
	@Override
	public void actionPerformed(ActionEvent actionEvent) {
		String event = actionEvent.getActionCommand();
		if (event.equals(mapBtnName1)
                || (event.equals(mapBtnName2))
                || (event.equals(mapBtnName3))
                || (event.equals(mapBtnName4))
                || (event.equals(mapBtnName5))) {
        	//Displays the dialog to select a file from the user's machine.
    	    fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
    	    fileChooser.setDialogTitle("Select a .map file");
    	    int result = fileChooser.showOpenDialog(fileChooser);
    	    if (result == JFileChooser.APPROVE_OPTION) {
    	        File selectedFile = fileChooser.getSelectedFile();
    	        fileName = selectedFile.getName();

				//Condition to check if the correct file is selected.
                if (gameMap.isCorrectMap) {
                	stringBuilder.append("Map File  : ").append(fileName).append("\n");
                	selectedFiles.add(selectedFile.getAbsolutePath());
               }
                else
                {
                	JOptionPane.showMessageDialog(null, GameMap.getInstance().getErrorMessage());
                }
    	    }
        }
		else if(event.equals(startGameBtnName)) {


			TournamentMode.numTurns = TournamentMode.turns.getText();
			stringBuilder.append("Number of Turns :").append(TournamentMode.numTurns).append("\n");
            int maxNumberOfIteration =Integer.valueOf(TournamentMode.numTurns);

			TournamentMode.numGames = (String) TournamentMode.gamesList.getSelectedItem();
			stringBuilder.append("Number of Games :").append(TournamentMode.numGames);
            int numberOfGames =Integer.valueOf(TournamentMode.numGames);



			TournamentMode.gameDetails.setText(stringBuilder.toString());
			for (String mapFile : selectedFiles) {
				gameMap = mapModel.readMapFile(mapFile);
				if (!gameMap.isCorrectMap) {
					System.out.println(mapFile + " is not Valid");
					System.out.println(gameMap.getErrorMessage());
					allValidMaps = false;
					break;
				}
				allValidMaps = true;
			}

            playerNames.add(view.getPlayerTextField(1));
            playerNames.add(view.getPlayerTextField(2));
            playerNames.add(view.getPlayerTextField(3));
            playerNames.add(view.getPlayerTextField(4));

            playerTypes.add(view.getPlayerComboBox(1));
            playerTypes.add(view.getPlayerComboBox(2));
            playerTypes.add(view.getPlayerComboBox(3));
            playerTypes.add(view.getPlayerComboBox(4));

			//checks for all the as selected by the user are valid or not
			if (allValidMaps && maxNumberOfIteration>=10 && maxNumberOfIteration <=50) {
				Player.isTournamentMode=true;
				StringBuilder finalResult =new StringBuilder();
				List<String> v = selectedFiles;

				TournamentModel tournamentModel = new TournamentModel(selectedFiles,numberOfGames,maxNumberOfIteration);
				GameView.displayLog("repaint");

                List<String> s = tournamentModel.getMapFiles();
                for (String mapFile : tournamentModel.getMapFiles()) {
                    Player model = new Player();
                    
                    int currentGame=1;
                    StringBuilder result = new StringBuilder();
                    result.append("------------------->Map ::"+ mapFile+"\n");
                    GameView.displayLog("------------------Now Playing for map------------------>"+mapFile);
                    for (int i = 1; i <= tournamentModel.getNumberOfGames(); i++) {
                    	
                    	currentGame=i;
                    	GameView.displayLog("Game"+i+"Starts");
                        model.initData(new File(mapFile),playerNames.size(),playerNames,playerTypes,true);
                        model.setDrawTurns(tournamentModel.getNumberOfTurns());
                        TournamentView.updateMapPanelFinal(i, "Before",mapFile);
                        try {
                            gameView = new GameView();
                        } catch (IOException e) {
                           // e.printStackTrace();
                        }
                   	   
                        gameView.addActionListeners(new GamePlayController(model, gameView, false));
                        result.append("Game"+ i+"\n");
                        result.append("Result ::"+model.winner+"\n");
                        gameView.setVisible(true);
                        TournamentView.updateMapPanelFinal(i, "After",mapFile);
                        gameView=null;
                        GameMap.setInstance(null);
                        Player.hasBotWon=false;
                        GameView.displayLog("------------------------->Game"+currentGame+"Ends");
                        GameView.displayLog("Results is -->"+result.toString());
                        
                    }
                    
                    
                    
                    finalResult.append(result);
                   
                }
                GameView.displayLog("All Game Ends \n"+finalResult.toString());
			}
			else{
                System.out.println("\nLooks like you have selected an invalid map(s) file !\nor\nNumber of max turn is not in range of 10-50 !!");
            }

		}
		else{
			System.out.println("Error: " + actionEvent + " actionEvent not found!");
		}
}
}
