package riskView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

import static util.RiskGameUtil.*;

/**
 * This class will get map details from model and display the map with number of players
 *
 * @author hnath
 * @author mudraparikh
 */
@SuppressWarnings("serial")
public class PlayerCount extends JDialog {
    private JPanel playerCountPanel;
    private GridLayout playerCountLayout;
    private JLabel playerCountLabel;

    private JButton threePlayerBtn;
    private JButton fourPlayerBtn;
    private JButton fivePlayerBtn;
    private JButton sixPlayerBtn;
    private JButton backBtn;

    /**
     * this constructor helps to set title,window size,default close operation
     */
    public PlayerCount() {
        setTitle("Risk Game");

        setPreferredSize(new Dimension(150, 280));
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        setResizable(false);

        add(playerCountPanel());

        setLocationRelativeTo(null);

        pack();
    }

    /**
     * The Player selection panel
     *
     * @return JPanel of the player selection
     */
    public JPanel playerCountPanel() {
        playerCountPanel = new JPanel();

        playerCountLayout = new GridLayout(6, 1, 5, 5);
        playerCountPanel.setLayout(playerCountLayout);

        playerCountLabel = new JLabel("Number of Players:");

        threePlayerBtn = new JButton("3");
        fourPlayerBtn = new JButton("4");
        fivePlayerBtn = new JButton("5");
        sixPlayerBtn = new JButton("6");
        backBtn = new JButton("Back");

        threePlayerBtn.setActionCommand(threePlayersBtnName);
        fourPlayerBtn.setActionCommand(fourPlayersBtnName);
        fivePlayerBtn.setActionCommand(fivePlayersBtnName);
        sixPlayerBtn.setActionCommand(sixPlayersBtnName);
        backBtn.setActionCommand(backBtnName);

        playerCountPanel.add(playerCountLabel);
        playerCountPanel.add(threePlayerBtn);
        playerCountPanel.add(fourPlayerBtn);
        playerCountPanel.add(fivePlayerBtn);
        playerCountPanel.add(sixPlayerBtn);
        playerCountPanel.add(backBtn);

        return playerCountPanel;
    }

    /**
     * Adds the action listeners;
     * @param event listens to the button events
     */
    protected void addActionListeners(ActionListener event) {
        threePlayerBtn.addActionListener(event);
        fourPlayerBtn.addActionListener(event);
        fivePlayerBtn.addActionListener(event);
        sixPlayerBtn.addActionListener(event);
        backBtn.addActionListener(event);
    }

}