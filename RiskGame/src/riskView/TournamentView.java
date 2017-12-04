package riskView;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;
import javax.swing.text.DefaultCaret;

import riskModels.continent.Continent;
import riskModels.country.Country;
import riskModels.map.GameMap;
import riskModels.player.Player;
import riskControllers.TournamentViewController;
/**
 * This class will load the game board for riskModels.tournament mode
 * @author mudraparikh
 *
 */
public class TournamentView extends JDialog {
	
	  private GameMap gameMap;
	  private Player model;
	  private GridBagLayout mainLayout;
	  private GridBagLayout messageLayout;
	  private GridBagConstraints c;
	  private static JPanel mapPanel;
	  private JPanel actionPanel;
	  public JPanel messagePanel;
	  private static JTextArea printTextArea;
	  private static JTextArea TextAreaForMapPanel;
	  public static JTextArea dominationTextArea;
	  public static JTextArea phaseViewTextArea;
	  private static JScrollPane mapScrollPane;
	  public static JScrollPane phaseViewPane;
	  public static JScrollPane dominationViewPane;
	  private JScrollPane messageScrollPane;
	  private static DefaultCaret caret;
	  private JButton resultBtn;
	  private String resultBtnName = "resultBtn";
	  
	/**
	 * constructs the board game for riskModels.tournament mode
	 * @throws IOException when there is problem in giving input or output
	 */
	private void TournamentView() throws IOException {
		setTitle("**Risk Game**--Tournament Mode--");
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
        add(actionPanel());

        c.fill = GridBagConstraints.BOTH;
        c.anchor = GridBagConstraints.LINE_END;
        c.insets = new Insets(5, 5, 5, 5);
        c.weightx = 0.5;
        c.weighty = 0.5;
        c.gridx = 2;
        c.gridy = 0;
        add(messagePanel());

        setLocationRelativeTo(null);

        pack();
	}	
	/**
     * This method will append logs in text file
     *
     * @param logDetail log message that you want to add.
     */
    public static void displayLog(String logDetail) {
    	// The name of the file to open.
        String fileName = "TournamentModeLogs.txt";

        // This will reference one line at a time
        String line = null;
        StringBuilder sb = new StringBuilder();

        try {
            // FileReader reads text files in the default encoding.
            FileReader fileReader = 
                new FileReader(fileName);

            // Always wrap FileReader in BufferedReader.
            BufferedReader bufferedReader = 
                new BufferedReader(fileReader);
            if(logDetail.equalsIgnoreCase("repaint")) {
            	sb.append("");
            }else {
            	while((line = bufferedReader.readLine()) != null) {
                    sb.append(line+"\n");
                }
            }

            // Always close files.
            bufferedReader.close();         
        }
        catch(FileNotFoundException ex) {
            System.out.println(
                "Unable to open file '" + 
                fileName + "'");                
        }
        catch(IOException ex) {
            System.out.println("Error reading file '" + fileName + "'");                  
        }
        StringBuilder stringBuilder = new StringBuilder();
        try (PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("TournamentModeLogs.txt")))) {
        	if(logDetail.equalsIgnoreCase("repaint")) {
        		out.print(sb.append("").toString() + "\n");	
        	}else {
        		out.print(sb.append(logDetail).toString() + "\n");
        	}
        	
        	out.close();
            
        } catch (Exception e) {
            e.printStackTrace();
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
    public static void showDomination(StringBuilder dominationDetails) {
        dominationTextArea.setText(dominationDetails.toString());
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
	}
	
	 /**
	    * The panel for the map and load display as per users choice.
	    * @return map display
	    * @throws IOException when there is problem in input and output
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
	          messageScrollPane = new JScrollPane(printTextArea);
	          
	          c = new GridBagConstraints();
	          
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

	        resultBtn = new JButton("Result");
	        resultBtn.setActionCommand(resultBtnName);
	   
	        dominationTextArea = new JTextArea();
	        dominationTextArea.setFocusable(false);
	        dominationTextArea.setLineWrap(true);
	        dominationTextArea.setWrapStyleWord(true);
	        dominationViewPane = new JScrollPane(dominationTextArea);
	        actionPanel.add(dominationViewPane);
	        
	        phaseViewTextArea = new JTextArea();
	        phaseViewTextArea.setFocusable(false);
	        phaseViewTextArea.setLineWrap(true);
	        phaseViewTextArea.setWrapStyleWord(true);
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
	        c.weighty = 0.5;
	        c.gridx = 0;
	        c.gridy = 2;
	        actionPanel.add(resultBtn, c);
	        
	        return actionPanel;
	    }
	    /**
	     * Adding Action Listeners for 
	     * @param tournamentViewController controller of Tournament View
	     * @throws IOException throws exception in case of incorrect input.
	     */
	    public void addActionListeners(TournamentViewController tournamentViewController) throws IOException {	
	  	  TournamentView();
	  }
	    /**
	     * This method will print map panel details in txt file for riskModels.tournament mode.
	     * @param gameNumber game number Ex: 1,2,3
	     * @param state state details Ex: before ,After
	     * @param mapFile path of mapfile you want to see map panel for 
	     */
	    public static void updateMapPanelFinal(int gameNumber,String state,String mapFile) {
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
	        File file = new File(mapFile);
	        try (PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(file.getName()+"Game_"+gameNumber+"_"+state+".txt")))) {
	        	out.print(stringBuilder.toString() + "\n");
	        	out.close();
	            
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
}