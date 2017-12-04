package riskView;

import riskControllers.FileSelectDialogController;
import riskControllers.PlayerCountController;
import riskControllers.TournamentModeController;
import riskModels.map.GameMap;
import riskModels.map.MapModel;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

/**
 * This class holds the method that gives the menu option to start game,create map and edit map.
 *
 * @author mudraparikh
 */
public class Launcher extends java.awt.Frame {

    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;

    private FileSelectDialog fileSelectDialog;

    /**
     * This method calls the initMenuComponents method to initialize Launcher components.
     */
    public Launcher() {
        super("RISK Game");
        initMenuComponents();
        jButton1.setEnabled(true);
        setLocationRelativeTo(null);
    }

    /**
     * This main method creates an instance of the startGame.
     *
     * @param args arguments  to run main method
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Launcher().setVisible(true);
            }
        });
    }

   /**
    * This method is used to generate buttons
    */
    private void initMenuComponents() {
        jPanel1 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();

        setBackground(new java.awt.Color(1, 1, 1));
        addWindowListener(new java.awt.event.WindowAdapter() {

            //Method to terminate and close the program window.
            public void windowClosing(java.awt.event.WindowEvent evt) {
                System.exit(0);
            }
        });

        jPanel1.setBackground(new java.awt.Color(1, 1, 1));
        jPanel1.setName("jPanel1");

        //On click the button starts game in single mode.
        jButton1.setText("Single Mode");
        jButton1.setName("jButton1");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PlayerCount add = new PlayerCount();
                add.addActionListeners(new PlayerCountController(add));
                add.setVisible(true);
            }
        });
        
      //On click the button starts game in riskModels.tournament mode.
        jButton2.setText("Tournament Mode");
        jButton2.setName("jButton2");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
               TournamentMode add = new TournamentMode();
               add.addActionListeners(new TournamentModeController(add));
               add.setVisible(true);
            }
        });

        //On click the button starts game in riskModels.tournament mode.
        jButton3.setText("Load Game");
        jButton3.setName("jButton3");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fileSelectDialog = new FileSelectDialog();
                fileSelectDialog.addActionListener(new FileSelectDialogController(fileSelectDialog, 0,null,null));
                fileSelectDialog.setVisible(true);
            }
        });

        //On click the button creates an instance of the CreateMap class.
        jButton4.setText("Create Map");
        jButton4.setName("jButton4");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                util.CreateMap add = new util.CreateMap();
                add.main(null);
                System.exit(0);
            }
        });

        //On click the button creates an instance of the EditMapView class.
        jButton5.setText("Edit Map");
        jButton5.setName("jButton5");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EditMapView add = new EditMapView();
                add.setVisible(true);
            }
        });
        //On click the button terminates the game.
        jButton6.setText("Exit");
        jButton6.setName("jButton6");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                System.exit(0);
            }
        });
      
        jLabel1.setName("jLabel1");
        //set the layout of the panel with all the components added
        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        //set the horizontal group for the panel layout
        jPanel1Layout.setHorizontalGroup
                (
                        jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addContainerGap()
                                        .addComponent(jLabel1)
                                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                        .addGap(91, 91, 91)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                                                .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, 102, Short.MAX_VALUE)
                                                .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, 102, Short.MAX_VALUE)
                                                .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, 102, Short.MAX_VALUE)
                                                .addComponent(jButton4, javax.swing.GroupLayout.DEFAULT_SIZE, 102, Short.MAX_VALUE)
                                                .addComponent(jButton5, javax.swing.GroupLayout.DEFAULT_SIZE, 102, Short.MAX_VALUE)
                                                .addComponent(jButton6, javax.swing.GroupLayout.DEFAULT_SIZE, 102, Short.MAX_VALUE))
                                        .addGap(87, 87, 87))
                );
        //set the vertical group for the panel layout
        jPanel1Layout.setVerticalGroup
                (
                        jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addContainerGap()
                                        .addComponent(jLabel1)
                                        .addGap(34, 34, 34)
                                        .addComponent(jButton1)
                                        .addGap(12, 12, 12)
                                        .addComponent(jButton2)
                                        .addGap(15, 15, 15)
                                        .addComponent(jButton3)
                                        .addGap(18, 18, 18)
                                        .addComponent(jButton4)
                                        .addGap(21, 21, 21)
                                        .addComponent(jButton5)
                                        .addGap(24, 24, 24)
                                        .addComponent(jButton6)
                                        .addGap(27, 27, 27))
                );
        add(jPanel1, java.awt.BorderLayout.CENTER);
        pack();
    }
}