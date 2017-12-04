package riskView;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import riskControllers.TournamentModeController;

import static util.RiskGameUtil.*;
/**
 * This class will get the details required to start the game in riskModels.tournament mode.
 * @author mudraparikh
 *
 */
public class TournamentMode extends JDialog{

    public static JButton mapBtn1;
    public static JButton mapBtn2;
    public static JButton mapBtn3;
	public static JButton mapBtn4;
	public static JButton mapBtn5;
    public static JButton startGame;

    public static JLabel numberOfTurnsLabel;
    public static JLabel numberOfGames;
    public static JLabel mapSelectLabel;
    public static JLabel playersNames;
    public static JLabel playersTypes;

    public static JTextField turns;

    public static JComboBox <String> gamesList;

    public static String numTurns;
	public static String numGames;

	public static String[] games;

	public static JTextArea gameDetails;
	
    public static JPanel playerTypesPanel;
    public static JPanel tournamentDetailsPanel;
    
    public static GridBagLayout playerTypesLayout;
    public static GridBagLayout tournamentLayout;
    public static GridBagLayout mainLayout;
    
    private GridBagConstraints c;
    
    private JTextField player1TextField;
    private JTextField player2TextField;
    private JTextField player3TextField;
    private JTextField player4TextField;

    private JComboBox<String> player1ComboBox;
    private JComboBox<String> player2ComboBox;
    private JComboBox<String> player3ComboBox;
    private JComboBox<String> player4ComboBox;

    private String[] types = {"Aggressive Bot", "Benevolent Bot", "Randomize Bot", "Cheater Bot" };
    
	/**
	 * this constructor helps to set title,window size,default close operation
	 */
	public TournamentMode()
	{
		setTitle("Tournament Mode");
        setPreferredSize(new Dimension(600,400));
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        setResizable(false);
        mainLayout = new GridBagLayout();
        setLayout(mainLayout);
        
        c = new GridBagConstraints();

        c.fill = GridBagConstraints.BOTH;
        c.anchor = GridBagConstraints.LINE_END;
        c.insets = new Insets(5, 5, 5, 5);
        c.weightx = 0.5;
        c.weighty = 0.5;
        c.gridx = 0;
        c.gridy = 0;
        add(playerTypesPanel());
        
        c.fill = GridBagConstraints.BOTH;
        c.anchor = GridBagConstraints.LINE_START;
        c.insets = new Insets(5, 5, 5, 5);
        c.weightx = 0.5;
        c.weighty = 0.5;
        c.gridx = 0;
        c.gridy = 1;
        add(tournamentDetailsPanel());
        
        setLocationRelativeTo(null);
        setVisible(false);
        pack();
	}

	/**
	 * This panel displays all the elements like players strategies
	 * @return players JPanel
	 */
    private JPanel playerTypesPanel() {
    	
        playerTypesPanel = new JPanel();
        playerTypesPanel.setPreferredSize(new Dimension(300,200));
        playerTypesLayout = new GridBagLayout();
        playerTypesPanel.setLayout(playerTypesLayout);
        
        playersNames = new JLabel();
        playersNames.setText("Enter Player Name :");
        playersNames.setVisible(true);
        
        player1TextField = new JTextField(7);
        player1TextField.setText("Aggressive");
        player2TextField = new JTextField(7);
        player2TextField.setText("Benevolent");
        player3TextField = new JTextField(7);
        player3TextField.setText("Cheater");
        player4TextField = new JTextField(7);
        player4TextField.setText("Random");
        
        playersTypes = new JLabel();
        playersTypes.setText("Select Player Type :");
        playersTypes.setVisible(true);
        
        player1ComboBox = new JComboBox<>(types);
        player2ComboBox = new JComboBox<>(types);
        player3ComboBox = new JComboBox<>(types);
        player4ComboBox = new JComboBox<>(types);
        
        c = new GridBagConstraints();
        
        c.fill = GridBagConstraints.BOTH;
        c.insets = new Insets(5, 5, 5, 5);
        c.weightx = 0.5;
        c.weighty = 0.5;
        c.gridx = 0;
        c.gridy = 0;
        playerTypesPanel.add(playersNames,c);
        
        c.fill = GridBagConstraints.BOTH;
        c.insets = new Insets(5, 5, 5, 5);
        c.weightx = 0.5;
        c.weighty = 0.5;
        c.gridx = 1;
        c.gridy = 0;
        playerTypesPanel.add(playersTypes,c);
        
        c.fill = GridBagConstraints.BOTH;
        c.insets = new Insets(5, 5, 5, 5);
        c.weightx = 0.5;
        c.weighty = 0.5;
        c.gridx = 0;
        c.gridy = 1;
        playerTypesPanel.add(player1TextField,c);
        
        c.fill = GridBagConstraints.BOTH;
        c.insets = new Insets(5, 5, 5, 5);
        c.weightx = 0.5;
        c.weighty = 0.5;
        c.gridx = 1;
        c.gridy = 1;
        playerTypesPanel.add(player1ComboBox,c);
        
        c.fill = GridBagConstraints.BOTH;
        c.insets = new Insets(5, 5, 5, 5);
        c.weightx = 0.5;
        c.weighty = 0.5;
        c.gridx = 0;
        c.gridy = 2;
        playerTypesPanel.add(player2TextField,c);
        
        c.fill = GridBagConstraints.BOTH;
        c.insets = new Insets(5, 5, 5, 5);
        c.weightx = 0.5;
        c.weighty = 0.5;
        c.gridx = 1;
        c.gridy = 2;
        playerTypesPanel.add(player2ComboBox,c);
        
        c.fill = GridBagConstraints.BOTH;
        c.insets = new Insets(5, 5, 5, 5);
        c.weightx = 0.5;
        c.weighty = 0.5;
        c.gridx = 0;
        c.gridy = 3;
        playerTypesPanel.add(player3TextField,c);
        
        c.fill = GridBagConstraints.BOTH;
        c.insets = new Insets(5, 5, 5, 5);
        c.weightx = 0.5;
        c.weighty = 0.5;
        c.gridx = 1;
        c.gridy = 3;
        playerTypesPanel.add(player3ComboBox,c);
        
        c.fill = GridBagConstraints.BOTH;
        c.insets = new Insets(5, 5, 5, 5);
        c.weightx = 0.5;
        c.weighty = 0.5;
        c.gridx = 0;
        c.gridy = 4;
        playerTypesPanel.add(player4TextField,c);
        
        c.fill = GridBagConstraints.BOTH;
        c.insets = new Insets(5, 5, 5, 5);
        c.weightx = 0.5;
        c.weighty = 0.5;
        c.gridx = 1;
        c.gridy = 4;
        playerTypesPanel.add(player4ComboBox,c);
        
        return playerTypesPanel;
    }

    /**
	 * The panel to display all the elements for playing riskModels.tournament mode
	 * @return JPanel message
	 */
	public JPanel tournamentDetailsPanel()
	{
		tournamentDetailsPanel = new JPanel();
		tournamentDetailsPanel.setPreferredSize(new Dimension(300,200));
		tournamentLayout = new GridBagLayout();
		tournamentDetailsPanel.setLayout(tournamentLayout);

		mapSelectLabel = new JLabel("Select the Map Files :");
		mapBtn1 = new JButton("Map 1");
		mapBtn2 = new JButton("Map 2");
		mapBtn3 = new JButton("Map 3");
        mapBtn4 = new JButton("Map 4");
        mapBtn5 = new JButton("Map 5");
		startGame = new JButton("Start Game");
		gameDetails = new JTextArea();
		gameDetails.setVisible(true);
	
		mapBtn1.setActionCommand(mapBtnName1);
		mapBtn2.setActionCommand(mapBtnName2);
		mapBtn3.setActionCommand(mapBtnName3);
        mapBtn4.setActionCommand(mapBtnName4);
        mapBtn5.setActionCommand(mapBtnName5);
		startGame.setActionCommand(startGameBtnName);
		
		numberOfTurnsLabel = new JLabel("Number of turns :");
		turns = new JTextField(5);
		
		numberOfGames = new JLabel("Number of Games :");
		games = new String[] {"1","2","3","4"};
		gamesList = new JComboBox<>(games);
		
		 c = new GridBagConstraints();
		 
		 c.fill = GridBagConstraints.BOTH;
	     c.insets = new Insets(5, 5, 5, 5);
	     c.weightx = 0.5;
	     c.weighty = 0.5;
	     c.gridx = 0;
	     c.gridy = 0;
	    tournamentDetailsPanel.add(mapSelectLabel,c);
	    
	    c.fill = GridBagConstraints.BOTH;
	    c.insets = new Insets(5, 5, 5, 5);
	    c.weightx = 0.5;
	    c.weighty = 0.5;
	    c.gridx = 0;
	    c.gridy = 1;
	    tournamentDetailsPanel.add(mapBtn1, c);
	    
	    c.fill = GridBagConstraints.BOTH;
	    c.insets = new Insets(5, 5, 5, 5);
	    c.weightx = 0.5;
	    c.weighty = 0.5;
	    c.gridx = 1;
	    c.gridy = 1;
		tournamentDetailsPanel.add(mapBtn2, c);
		
		c.fill = GridBagConstraints.BOTH;
	    c.insets = new Insets(5, 5, 5, 5);
	    c.weightx = 0.5;
	    c.weighty = 0.5;
	    c.gridx = 0;
	    c.gridy = 2;
		tournamentDetailsPanel.add(mapBtn3, c);
		
		c.fill = GridBagConstraints.BOTH;
	    c.insets = new Insets(5, 5, 5, 5);
	    c.weightx = 0.5;
	    c.weighty = 0.5;
	    c.gridx = 1;
	    c.gridy = 2;
        tournamentDetailsPanel.add(mapBtn4, c);
        
        c.fill = GridBagConstraints.BOTH;
	    c.insets = new Insets(5, 5, 5, 5);
	    c.weightx = 0.5;
	    c.weighty = 0.5;
	    c.gridx = 0;
	    c.gridy = 3;
        tournamentDetailsPanel.add(mapBtn5, c);
        
        c.fill = GridBagConstraints.BOTH;
	    c.insets = new Insets(5, 5, 5, 5);
	    c.weightx = 0.5;
	    c.weighty = 0.5;
	    c.gridx = 0;
	    c.gridy = 4;
		tournamentDetailsPanel.add(numberOfTurnsLabel, c);
		
		c.fill = GridBagConstraints.BOTH;
	    c.insets = new Insets(5, 5, 5, 5);
	    c.weightx = 0.5;
	    c.weighty = 0.5;
	    c.gridx = 1;
	    c.gridy = 4;
		tournamentDetailsPanel.add(turns, c);
		
		c.fill = GridBagConstraints.BOTH;
	    c.insets = new Insets(5, 5, 5, 5);
	    c.weightx = 0.5;
	    c.weighty = 0.5;
	    c.gridx = 0;
	    c.gridy = 5;
		tournamentDetailsPanel.add(numberOfGames, c);
		
		c.fill = GridBagConstraints.BOTH;
	    c.insets = new Insets(5, 5, 5, 5);
	    c.weightx = 0.5;
	    c.weighty = 0.5;
	    c.gridx = 1;
	    c.gridy = 5;
		tournamentDetailsPanel.add(gamesList, c);
		
		c.fill = GridBagConstraints.BOTH;
	    c.insets = new Insets(5, 5, 5, 5);
	    c.weightx = 0.5;
	    c.weighty = 0.5;
	    c.gridx = 0;
	    c.gridy = 6;
		tournamentDetailsPanel.add(startGame, c);
		
		c.fill = GridBagConstraints.BOTH;
	    c.insets = new Insets(5, 5, 5, 5);
	    c.weightx = 0.5;
	    c.weighty = 0.5;
	    c.gridx = 0;
	    c.gridy = 7;
		tournamentDetailsPanel.add(gameDetails, c);
		return tournamentDetailsPanel;
	}
	
	 /**
     * Adds the action listeners;
     * @param event Event that should occur in tournamentModeController
     */
	public void addActionListeners(TournamentModeController event) {
		mapBtn1.addActionListener(event);
	    mapBtn2.addActionListener(event);
	    mapBtn3.addActionListener(event);
        mapBtn4.addActionListener(event);
        mapBtn5.addActionListener(event);
	    startGame.addActionListener(event);
	}

    /**
     * Getter methods for text field of players
     * @param playerNum maintains the player number
     * @return getText value from the respective text-field
     */

    public String getPlayerTextField(int playerNum)
    {
        if (playerNum == 1)
        {
            return player1TextField.getText();
        }
        else if (playerNum == 2)
        {
            return player2TextField.getText();
        }
        else if (playerNum == 3)
        {
            return player3TextField.getText();
        }
        else
        {
            return player4TextField.getText();
        }
    }

    /**
     * Getter methods for the comboBox
     * @param playerNum maintains the player number
     * @return getSelectedItem the value of the selected item form the comboBox
     */

    public String getPlayerComboBox(int playerNum)
    {
        if (playerNum == 1)
        {
            return player1ComboBox.getSelectedItem().toString();
        }
        else if (playerNum == 2)
        {
            return player2ComboBox.getSelectedItem().toString();
        }
        else if (playerNum == 3)
        {
            return player3ComboBox.getSelectedItem().toString();
        }
        else
        {
            return player4ComboBox.getSelectedItem().toString();
        }
    }
 }