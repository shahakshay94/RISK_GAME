package riskView;

import riskModels.continent.Continent;
import riskModels.country.Country;
import riskModels.map.GameMap;
import riskModels.map.MapModel;
import riskModels.player.Player;
import util.RiskGameUtil;

import javax.swing.*;
import javax.swing.text.DefaultCaret;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.Date;
import java.util.List;


/**
 * This class will load the game board
 *
 * @author mudraparikh and akshay shah
 */
public class GameView extends JDialog {
    public static JScrollPane phaseViewPane;
    public static JScrollPane dominationViewPane;
    public static JScrollPane cardListPane;
    public static JTextArea dominationTextArea;
    public static JTextArea phaseViewTextArea;
    private static JPanel mapPanel;
    private static JScrollPane mapScrollPane;
    private static JTextArea printTextArea;
    private static JTextArea TextAreaForMapPanel;
    private static DefaultCaret caret;
    public JPanel messagePanel;
    public JList cardsList;
    public DefaultListModel<String> cardListDefault;
    private JPanel countryInfoPanel;
    private JPanel actionPanel;
    private GridBagLayout mainLayout;
    private GridBagConstraints c;
    private GridBagLayout messageLayout;
    private GridBagLayout actionLayout;
    private JScrollPane messageScrollPane;
    private JScrollPane continentScrollPane;
    private JScrollPane countryScrollPane1;
    private JScrollPane countryScrollPane2;
    private JLabel selectedLabel;
    private JLabel targetLabel;
    private JLabel continentLabel;
    private JButton menuBtn;
    private JButton turnInBtn;
    private JButton reinforceBtn;
    private JButton attackBtn;
    private JButton fortifyBtn;
    private JButton endTurnBtn;
    private String menuBtnName = "menuBtn";
    private String reinforceBtnName = "reinforceBtn";
    private String attackBtnName = "attackBtn";
    private String fortifyBtnName = "fortifyBtn";
    private String turnInBtnName = "turnInBtn";
    private String endTurnBtnName = "endTurnBtn";
    private JList<String> continentList;
    private JList<String> countryList1;
    private JList<String> countryList2;
    private DefaultListModel<String> continentDisplay;
    private DefaultListModel<String> countryDisplay1;
    private DefaultListModel<String> countryDisplay2;
    public  static DefaultListModel<String> cardListDisplay;
    public GameMap gameMap;
    private Player model;

/**
 * Constructs the Risk game board.
 * @throws IOException when there is problem in giving input or output
 */
    public GameView() throws IOException {

        setTitle("Risk Game");
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        setResizable(false);

        gameMap = GameMap.getInstance();
        model = new Player();
        mainLayout = new GridBagLayout();
        setLayout(mainLayout);

        c = new GridBagConstraints();

        c.fill = GridBagConstraints.BOTH;
        c.anchor = GridBagConstraints.LINE_START;
        c.insets = new Insets(5, 5, 5, 5);
        c.weightx = 8;
        c.weighty = 0.5;
        c.gridx = 0;
        c.gridy = 0;
        add(mapPanel());

        c.fill = GridBagConstraints.BOTH;
        c.anchor = GridBagConstraints.CENTER;
        c.insets = new Insets(5, 5, 5, 5);
        c.weightx = 0.5;
        c.weighty = 0.5;
        c.gridx = 1;
        c.gridy = 0;
        add(countryInfoPanel());

        c.fill = GridBagConstraints.BOTH;
        c.anchor = GridBagConstraints.LINE_END;
        c.insets = new Insets(5, 5, 5, 5);
        c.weightx = 0.5;
        c.weighty = 0.5;
        c.gridx = 2;
        c.gridy = 0;
        add(messagePanel());

        c.fill = GridBagConstraints.BOTH;
        c.anchor = GridBagConstraints.LINE_END;
        c.insets = new Insets(5, 5, 5, 5);
        c.weightx = 0.5;
        c.weighty = 0.5;
        c.gridx = 3;
        c.gridy = 0;
        add(actionPanel());

        setLocationRelativeTo(null);
        setVisible(false);
        pack();

    }

    /**
     * This method will display updated logs in logger window of the game
     *
     * @param logDetail log message that you want to add.
     */
    public static void displayLog(String logDetail) {
    	if(Player.isTournamentMode) {
   		 TournamentView.displayLog(logDetail); // to get real time updates during riskModels.tournament mode.
	   	}else {
	   		String existingDetails = printTextArea.getText();
	           StringBuilder stringBuilder = new StringBuilder(existingDetails);
	           printTextArea.setText(stringBuilder.append(logDetail) + "\n");
	           GameMap.getInstance().setLogDetails(stringBuilder.append(logDetail) + "\n");
	   	}
    }
    
    /**
     * This method will clear logs in logger window of the game
     */
    public void clearLog() {
		printTextArea.setText("");
	}
    
   /**
    * This method will update view for the map panel .
    * Map Panel holds the details of the continent ,Country , Number of Armies and Owner information
    */
    public static void updateMapPanel() {
        StringBuilder stringBuilder = new StringBuilder();
        for (Continent continent : GameMap.getInstance().getContinentList()) {
            stringBuilder.append("----------------------------");
            stringBuilder.append(continent.getContinentName());
            stringBuilder.append("----------------------------" + "\n");
            for (Country country : GameMap.getInstance().getCountryAndNeighborsMap().keySet()) {
                if (country.getBelongsToContinent().equalsIgnoreCase(continent.getContinentName())) {
                    stringBuilder.append(country.getCountryName());
                    stringBuilder.append(" - ").append(country.getCurrentArmiesDeployed()).append(" - ");
                    stringBuilder.append(country.getBelongsToPlayer().getName()).append("\n");
                }
            }

        }
        Date date = new Date();
        TextAreaForMapPanel.setText(stringBuilder.toString() + "\n Updated:" + date.toString());
    }

    /**
     * this method shows which player's domination
     * @param dominationDetails details of whose domination
     */
    public static void showDomination(String dominationDetails) {
        dominationTextArea.setText(dominationDetails.toString());
        GameMap.getInstance().setDominationViewDetails(dominationDetails.toString());
    }

    /**
     * this method updates the card view 
     * @param cardArray list of cards to be updated
     */
    public static void updateCardView(String[] cardArray) {
        cardListDisplay.removeAllElements();
        for (String aCardArray : cardArray) {
            cardListDisplay.addElement(aCardArray);
        }
    }

  /**
   * The panel for the logger message display and game play buttons.
   * @return JPanel message
   */
    private JPanel messagePanel() {

        messagePanel = new JPanel();
        messagePanel.setPreferredSize(new Dimension(320, 690));
        messageLayout = new GridBagLayout();
        messagePanel.setLayout(messageLayout);

        printTextArea = new JTextArea();
        printTextArea.setFocusable(false);
        printTextArea.setLineWrap(true);
        printTextArea.setWrapStyleWord(true);
        caret = (DefaultCaret) printTextArea.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
        if(RiskGameUtil.checkNullString(GameMap.getInstance().getLogDetails())) {
        	printTextArea.setText(GameMap.getInstance().getLogDetails());        	
        }
        messageScrollPane = new JScrollPane(printTextArea);

        reinforceBtn = new JButton("Reinforce Armies");
        attackBtn = new JButton("Attack!");
        fortifyBtn = new JButton("Fortify");
        endTurnBtn = new JButton("End Turn");

        reinforceBtn.setActionCommand(reinforceBtnName);
        attackBtn.setActionCommand(attackBtnName);
        fortifyBtn.setActionCommand(fortifyBtnName);
        endTurnBtn.setActionCommand(endTurnBtnName);

        c = new GridBagConstraints();

        c.fill = GridBagConstraints.BOTH;
        c.insets = new Insets(5, 5, 5, 5);
        c.weightx = 0.5;
        c.weighty = 0.5;
        c.gridx = 0;
        c.gridy = 0;
        messagePanel.add(reinforceBtn, c);

        c.fill = GridBagConstraints.BOTH;
        c.insets = new Insets(5, 5, 5, 5);
        c.weightx = 0.5;
        c.weighty = 0.5;
        c.gridx = 0;
        c.gridy = 1;
        messagePanel.add(attackBtn, c);

        c.fill = GridBagConstraints.BOTH;
        c.insets = new Insets(5, 5, 5, 5);
        c.weightx = 0.5;
        c.weighty = 0.5;
        c.gridx = 0;
        c.gridy = 2;
        messagePanel.add(fortifyBtn, c);

        c.fill = GridBagConstraints.BOTH;
        c.insets = new Insets(5, 5, 5, 5);
        c.weightx = 0.5;
        c.weighty = 0.5;
        c.gridx = 0;
        c.gridy = 3;
        messagePanel.add(endTurnBtn, c);

        c.fill = GridBagConstraints.BOTH;
        c.insets = new Insets(5, 5, 5, 5);
        c.weightx = 0.5;
        c.weighty = 18;
        c.gridx = 0;
        c.gridy = 4;
        messagePanel.add(messageScrollPane, c);
        return messagePanel;
    }

    /**
     * The panel for the logger message display and game play buttons.
     * @return message from JPanel
     */
    private JPanel actionPanel() {
        actionPanel = new JPanel();
        actionPanel.setPreferredSize(new Dimension(300, 690));
        messageLayout = new GridBagLayout();
        actionPanel.setLayout(messageLayout);

        menuBtn = new JButton("Menu");
        menuBtn.setActionCommand(menuBtnName);
        turnInBtn = new JButton("Turn In Cards");
        turnInBtn.setActionCommand(turnInBtnName);
        CardView cardsListModel = new CardView(model, "cards");

        cardListDisplay = new DefaultListModel<>();
        cardsList = new JList<>(cardListDisplay);
        cardsList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        cardsList.setLayoutOrientation(JList.VERTICAL_WRAP);
        cardListPane = new JScrollPane(cardsList);
        cardsList.setVisibleRowCount(10);
        cardsList.setVisible(true);

        dominationTextArea = new JTextArea();
        dominationTextArea.setFocusable(false);
        dominationTextArea.setLineWrap(true);
        dominationTextArea.setWrapStyleWord(true);
        if(RiskGameUtil.checkNullString(GameMap.getInstance().getDominationViewDetails())) {
        	dominationTextArea.setText(GameMap.getInstance().getDominationViewDetails());        	
        }
        dominationViewPane = new JScrollPane(dominationTextArea);
        actionPanel.add(dominationViewPane);
       
        
        phaseViewTextArea = new JTextArea();
        phaseViewTextArea.setFocusable(false);
        phaseViewTextArea.setLineWrap(true);
        phaseViewTextArea.setWrapStyleWord(true);
        if(RiskGameUtil.checkNullString(GameMap.getInstance().getPhaseDetails())) {
        	phaseViewTextArea.setText(GameMap.getInstance().getPhaseDetails());        	
        }
        phaseViewPane = new JScrollPane(phaseViewTextArea);
        actionPanel.add(phaseViewPane);
       

        c = new GridBagConstraints();

        c.fill = GridBagConstraints.BOTH;
        c.insets = new Insets(5, 5, 5, 5);
        c.weightx = 0.5;
        c.weighty = 10;
        c.gridx = 0;
        c.gridy = 0;
        actionPanel.add(dominationViewPane, c);

        c.fill = GridBagConstraints.BOTH;
        c.insets = new Insets(5, 5, 5, 5);
        c.weightx = 0.5;
        c.weighty = 10;
        c.gridx = 0;
        c.gridy = 1;
        actionPanel.add(phaseViewPane, c);

        c.fill = GridBagConstraints.BOTH;
        c.insets = new Insets(5, 5, 5, 5);
        c.weightx = 0.5;
        c.weighty = 10;
        c.gridx = 0;
        c.gridy = 2;
        actionPanel.add(cardListPane, c);

        c.fill = GridBagConstraints.BOTH;
        c.insets = new Insets(5, 5, 5, 5);
        c.weightx = 0.5;
        c.weighty = 0.5;
        c.gridx = 0;
        c.gridy = 3;
        actionPanel.add(turnInBtn, c);

        c.fill = GridBagConstraints.BOTH;
        c.insets = new Insets(5, 5, 5, 5);
        c.weightx = 0.5;
        c.weighty = 0.5;
        c.gridx = 0;
        c.gridy = 4;
        actionPanel.add(menuBtn, c);

        return actionPanel;
    }

   /**
    * The panel for the map and load display as per users choice.
    * @return map display
    * @throws IOException when the is problem in input and output
    */
    public JPanel mapPanel() throws IOException {
        mapPanel = new JPanel();
        mapPanel.setLayout(new GridLayout(1, 1, 5, 5));
        TextAreaForMapPanel = new JTextArea();
        TextAreaForMapPanel.setFocusable(false);
        TextAreaForMapPanel.setLineWrap(true);
        TextAreaForMapPanel.setWrapStyleWord(true);
        caret = (DefaultCaret) TextAreaForMapPanel.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);

        StringBuilder stringBuilder = new StringBuilder();
        for (Continent continent : GameMap.getInstance().getContinentList()) {
            stringBuilder.append("----------------------------");
            stringBuilder.append(continent.getContinentName());
            stringBuilder.append("----------------------------" + "\n");
            for (Country country : GameMap.getInstance().getCountryAndNeighborsMap().keySet()) {
                if (country.getBelongsToContinent().equalsIgnoreCase(continent.getContinentName())) {
                    stringBuilder.append(country.getCountryName());
                    stringBuilder.append(" - ").append(country.getCurrentArmiesDeployed()).append(" - ");
                    stringBuilder.append(country.getBelongsToPlayer().getName()).append("\n");
                }
            }

        }
        TextAreaForMapPanel.setText(stringBuilder.toString());
        mapScrollPane = new JScrollPane(TextAreaForMapPanel);
        mapScrollPane.setPreferredSize(new Dimension(370, 690));
        mapScrollPane.revalidate();
        mapScrollPane.repaint();
        mapPanel.add(mapScrollPane);
        return mapPanel;
    }

   /**
    * The panel to display the list of continents, countries and their adjacent territories.
    * @return list of all continents ,countries and adjacent territories
    */
    private JPanel countryInfoPanel() {
        countryInfoPanel = new JPanel();
        countryInfoPanel.setPreferredSize(new Dimension(350, 690));
        actionLayout = new GridBagLayout();
        countryInfoPanel.setLayout(actionLayout);

        selectedLabel = new JLabel("Selected Country:");
        targetLabel = new JLabel("Neighbouring Countries:");
        continentLabel = new JLabel("Continents:");


        countryDisplay1 = new DefaultListModel<>();
        countryList1 = new JList<>(countryDisplay1);
        countryList1.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        countryList1.setLayoutOrientation(JList.VERTICAL_WRAP);
        countryList1.setVisibleRowCount(30);
        countryScrollPane1 = new JScrollPane(countryList1);
        countryList1.setVisible(true);

        countryDisplay2 = new DefaultListModel<>();
        countryList2 = new JList<>(countryDisplay2);
        countryList2.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        countryList2.setLayoutOrientation(JList.VERTICAL_WRAP);
        countryList2.setVisibleRowCount(30);
        countryScrollPane2 = new JScrollPane(countryList2);
        countryList2.setVisible(true);

        continentDisplay = new DefaultListModel<>();
        continentList = new JList<>(continentDisplay);
        continentList.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        continentList.setLayoutOrientation(JList.VERTICAL_WRAP);
        continentList.setVisibleRowCount(50);
        continentScrollPane = new JScrollPane(continentList);
        continentList.setVisible(true);
        for (int i = 0; i < gameMap.getContinentList().size(); i++)
            continentDisplay.addElement(gameMap.getContinentList().get(i).getContinentName());
        continentList.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                countryDisplay1.removeAllElements();
                countryDisplay2.removeAllElements();
                for (Country c : gameMap.getCountryAndNeighborsMap().keySet()) {
                    if (c.getBelongsToContinent().equalsIgnoreCase(continentList.getSelectedValue())) {
                        countryDisplay1.addElement(c.getCountryName());
                    }
                }
            }
        });
        countryList1.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                Country selectedCountry = MapModel.getCountryObj(countryList1.getSelectedValue().trim(), GameMap.getInstance());
                if (selectedCountry != null) {
                    GameView.displayLog(selectedCountry.getCountryName() + "has " + selectedCountry.getCurrentArmiesDeployed() + " armies, occupant is " + selectedCountry.getBelongsToPlayer().getName());
                }
                countryDisplay2.removeAllElements();
                List<Country> neighbours = GameMap.getInstance().getCountryAndNeighborsMap().get(new Country(countryList1.getSelectedValue()));
                for (Country country : neighbours) {
                    countryDisplay2.addElement(country.getCountryName());

                }
            }
        });

        countryList2.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                Country selectedCountry = MapModel.getCountryObj(countryList2.getSelectedValue().trim(), GameMap.getInstance());
                if (selectedCountry != null) {
                    GameView.displayLog(selectedCountry.getCountryName() + " has " + selectedCountry.getCurrentArmiesDeployed() + " armies, occupant is " + selectedCountry.getBelongsToPlayer().getName());
                }
            }
        });

        c = new GridBagConstraints();

        c.fill = GridBagConstraints.BOTH;
        c.insets = new Insets(5, 5, 5, 5);
        c.weightx = 0.5;
        c.weighty = 0.5;
        c.gridx = 0;
        c.gridy = 0;
        countryInfoPanel.add(continentLabel, c);

        c.fill = GridBagConstraints.BOTH;
        c.insets = new Insets(5, 5, 5, 5);
        c.weightx = 0.5;
        c.weighty = 14;
        c.gridx = 0;
        c.gridy = 1;
        countryInfoPanel.add(continentScrollPane, c);

        c.fill = GridBagConstraints.BOTH;
        c.insets = new Insets(5, 5, 5, 5);
        c.weightx = 0.5;
        c.weighty = 0.5;
        c.gridx = 0;
        c.gridy = 2;
        countryInfoPanel.add(selectedLabel, c);

        c.fill = GridBagConstraints.BOTH;
        c.insets = new Insets(5, 5, 5, 5);
        c.weightx = 0.5;
        c.weighty = 5;
        c.gridx = 0;
        c.gridy = 3;
        countryInfoPanel.add(countryScrollPane1, c);

        c.fill = GridBagConstraints.BOTH;
        c.insets = new Insets(5, 5, 5, 5);
        c.weightx = 0.5;
        c.weighty = 0.5;
        c.gridx = 0;
        c.gridy = 4;
        countryInfoPanel.add(targetLabel, c);

        c.fill = GridBagConstraints.BOTH;
        c.insets = new Insets(5, 5, 5, 5);
        c.weightx = 0.5;
        c.weighty = 5;
        c.gridx = 0;
        c.gridy = 5;
        countryInfoPanel.add(countryScrollPane2, c);

        return countryInfoPanel;
    }

  /**
   * Adds the action listeners for the buttons and lists.
   * @param evt1 action listener variable
   */
    public void addActionListeners(ActionListener evt1) {

        menuBtn.addActionListener(evt1);
        turnInBtn.addActionListener(evt1);
        reinforceBtn.addActionListener(evt1);
        attackBtn.addActionListener(evt1);
        fortifyBtn.addActionListener(evt1);
        endTurnBtn.addActionListener(evt1);
        
    }

    /**
     * Passes the indices of the cards to remove from the current player's hand.
     *
     * @return the array of selected indices in the cards list.
     **/
    public int[] getCardsToRemove() {
        return cardsList.getSelectedIndices();
    }

    /**
     * Passes countryA for the model.
     *
     * @return the String of the selected value in country A list.
     **/
    public String getCountryA() {
        return countryList1.getSelectedValue();
    }

    /**
     * Passes countryB for the model.
     *
     * @return the String of the selected value in country B list.
     **/
    public String getCountryB() {
        return countryList2.getSelectedValue();
    }

    /**
     * this method updates the panel's phase details
     * @param phaseDetailMessage has the details of the phase panel
     */
	public static void updatePanelOfPhaseDetails(String phaseDetailMessage) {
		String updatedPhaseDetail="";
		if(!phaseDetailMessage.equalsIgnoreCase("Repaint")) {
				updatedPhaseDetail = GameView.phaseViewTextArea.getText()+phaseDetailMessage;
		}
        GameView.phaseViewTextArea.setText(updatedPhaseDetail);
        GameMap.getInstance().setPhaseDetails(updatedPhaseDetail);
	}
}