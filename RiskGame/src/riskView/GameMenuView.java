package riskView;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import riskControllers.GameMenuViewController;

/**
 * This class will display the menu options for the game
 * @author mudraparikh
 */
public class GameMenuView extends JDialog{
private JPanel menuPanel;
	
	private GridLayout menuLayout;
	
	private JButton returnBtn;
	private JButton saveBtn;
	private JButton quitBtn;
	
	private String returnBtnName = "returnBtn";
	private String saveBtnName = "saveBtn";
	private String quitBtnName = "quitBtn";
	
	/**
	 * Constructs the view for the menu to be displayed
	 * 
	 */
	
	public GameMenuView()
	{
		super();
		setTitle("Menu Options");
		
		setPreferredSize(new Dimension(200, 150));
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setResizable(false);
		
		add(menuPanel());
		
		setLocationRelativeTo(null);
		
		pack();
	}
	
	/**
	 * The panel to display the menu options in the panel
	 * @return JPanel message
	 */
	private JPanel menuPanel()
	{
		menuPanel = new JPanel();
		
		menuLayout = new GridLayout(3, 1, 5, 5);
		menuPanel.setLayout(menuLayout);
		
		returnBtn = new JButton("Return to Game");
		saveBtn = new JButton("Save Game");
		quitBtn = new JButton("Quit");
		
		returnBtn.setActionCommand(returnBtnName);
		saveBtn.setActionCommand(saveBtnName);
		quitBtn.setActionCommand(quitBtnName);
		
		menuPanel.add(returnBtn);
		menuPanel.add(saveBtn);
		menuPanel.add(quitBtn);
		
		return menuPanel;
	}
	
	/**
	 * Add Action Listeners
	 * @param evt listens to the action performed on the button click
	 */
	
	public void addActionListeners(ActionListener evt)
	{
		returnBtn.addActionListener(evt);
		saveBtn.addActionListener(evt);
		quitBtn.addActionListener(evt);
	}
}
