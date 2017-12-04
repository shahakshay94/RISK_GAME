package riskView;

import riskModels.continent.Continent;
import riskModels.country.Country;
import riskModels.map.GameMap;
import riskModels.map.MapModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * EditMapView class holds the property of editing map.
 *
 * @author mudraparikh
 */
@SuppressWarnings("serial")
public class EditMapView extends java.awt.Frame {

    String fileName;
    Object index1;
    GameMap gameMap = GameMap.getInstance();
    MapModel mapModel = new MapModel();
    private JFileChooser filechooser = new JFileChooser();
    private JDialog dialog = new JDialog();
    private JLabel label = new JLabel();
    private JLabel label1 = new JLabel();
    private JButton btn_continent = new JButton();
    private JButton btn_country = new JButton();
    private JList<String> continentList, continentList1, countryList;
    private DefaultListModel<String> continentDisplay = new DefaultListModel<>(), continentDisplay1 = new DefaultListModel<>(), countryDisplay = new DefaultListModel<>();
    private JFrame frame = new JFrame();
    private JFrame frameContinent, frameCountry;
    private JLabel labelContinent, labelCountry;
    private JTextField textContinent, textCountry;
    private JButton button1, button2, button3;
    private JScrollPane pane;
    private int index;

    /**
     * constructor which calls the initMapComponent method
     */
    public EditMapView() {
        initMapComponents();
    }

  /**
   * This continent method is used to edit map by "Add" or "Remove" functionality Continents from a Map
   */
    private void continent(){
        continentList = new JList(continentDisplay);
        pane = new JScrollPane(continentList);
        JButton removeButton = new JButton("Remove Continent");
        JFrame frameContinent = new JFrame();
        JTextField textContinent = new JTextField();
        JButton button1 = new JButton("OK");

        for (int i = 0; i < gameMap.getContinentList().size(); i++)
            continentDisplay.addElement(gameMap.getContinentList().get(i).getContinentName());


        //This button performs the remove continent operation on the map to be edited.
        removeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (continentList.getSelectedIndex() >= 0) {
                    List<String> obj = continentList.getSelectedValuesList();
                    for (String string : obj) {
                        Continent continent = new Continent(string);
                        continentDisplay.removeElement(string);
                        mapModel.removeContinent(continent,fileName);
                        if(GameMap.getInstance().isCorrectMap) {
                        	JOptionPane.showMessageDialog(null, string+" Continent removed from the Map");	
                        }else {
                        	JOptionPane.showMessageDialog(null,GameMap.getInstance().getErrorMessage().toString());
                        }
                    }
                }
                continentList.repaint();
            }
        });

        JFrame frame1 = new JFrame();
        frame1.setTitle("Remove Continent");
        frame1.setVisible(true);
        frame1.setSize(450, 300);
        frame1.setLocationRelativeTo(null);
        frame1.add(pane, BorderLayout.NORTH);
        frame1.add(removeButton, BorderLayout.CENTER);
    }

/**
 * This country method is used to edit map by "Add" or "Remove" functionality country from a Map
 */

    private void country() {
        countryList = new JList(countryDisplay);
        JList countryList2 = new JList(countryDisplay);
        JScrollPane pane = new JScrollPane(countryList);
        JScrollPane pane3 = new JScrollPane(countryList2);

        for (int i = 0; i < continentDisplay.size(); i++)
            continentDisplay1.addElement(continentDisplay.get(i));

        continentList1 = new JList(continentDisplay1);        
        JScrollPane pane2 = new JScrollPane(continentList1);
        JLabel label2 = new JLabel("Click on the respective button to perform tasks");
        JButton addButton = new JButton("Add Country");
        JButton removeButton = new JButton("Remove Country");
        JLabel labelCountry = new JLabel("Enter Country name :");
        JFrame frameCountry = new JFrame();
        JTextField textCountry = new JTextField();
        JButton button2 = new JButton("OK");

       //The following loops display the list of continents and the countries by accessing the values from the gameMap object of the gameMap class
        for (int i = 0; i < gameMap.getContinentList().size(); i++)
            continentDisplay1.addElement(gameMap.getContinentList().get(i).getContinentName());

        for (Map.Entry<Country, List<Country>> e : gameMap.getCountryAndNeighborsMap().entrySet()) {
            countryDisplay.addElement(e.getKey().getCountryName());

            //This button performs the add country operation on the map to be edited.
            addButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    frameCountry.setTitle("Add Country");
                    frameCountry.setVisible(true);
                    frameCountry.setSize(550, 250);
                    frameCountry.setLocationRelativeTo(null);
                    frameCountry.add(labelCountry, BorderLayout.PAGE_START);
                    frameCountry.add(pane2, BorderLayout.LINE_START);
                    frameCountry.add(textCountry, BorderLayout.CENTER);
                    frameCountry.add(pane3, BorderLayout.EAST);
                    frameCountry.add(button2, BorderLayout.PAGE_END);
                }
            });
        }

        //Changes made on the GUI to edit map will be saved to the .map file
        button2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                countryDisplay.addElement(textCountry.getText());
                List<String> continents = continentList1.getSelectedValuesList();
                if (continents.size() > 0) {
                    Country country = new Country(textCountry.getText(), 0, 0, continents.get(0));
                    List<Country> countries = new ArrayList<Country>();
                    if (countryList2.getSelectedValuesList().size() > 0) {
                        List<String> countryNames = countryList2.getSelectedValuesList();
                        for (Country c : gameMap.getCountryAndNeighborsMap().keySet()) {
                            for (String countryName : countryNames) {
                                if (countryName.equals(c.getCountryName())) {
                                    countries.add(c);
                                    break;
                                }
                            }
                        }
                        
                    }
                    mapModel.addCountry(country, gameMap, countries);
                    mapModel.validateMap(gameMap);
                    if(gameMap.isCorrectMap) {
                    	mapModel.writeMap(gameMap, fileName);
                    	JOptionPane.showMessageDialog(null, "Added Country " + textCountry.getText() + " in " + continents+ " Continent" );
                    }else {
                    	JOptionPane.showMessageDialog(null,GameMap.getInstance().getErrorMessage().toString());
                    	mapModel.removeCountry(country, gameMap);
                    }
                    
                }
            }
        });
        //This button performs the remove country operation on the map to be edited.
        removeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (countryList.getSelectedIndex() >= 0) {
                    List<String> obj = countryList.getSelectedValuesList();
                    for (String string : obj) {
                        countryDisplay.removeElement(string);
                        if (mapModel.getCountryObj(string, gameMap) != null) {
                            mapModel.removeCountry(mapModel.getCountryObj(string, gameMap), gameMap);
                        }
                        if(GameMap.getInstance().isCorrectMap) {
                        	JOptionPane.showMessageDialog(null,string + " Country removed from the Map");	
                        }else {
                        	JOptionPane.showMessageDialog(null,GameMap.getInstance().getErrorMessage().toString());
                        }
                    }
                }
                countryList.repaint();
                mapModel.writeMap(gameMap, fileName);
            }
        });
        JFrame frame1 = new JFrame();
        frame1.setTitle("Add or Remove Country");
        frame1.setVisible(true);
        frame1.setSize(450, 300);
        frame1.setLocationRelativeTo(null);
        frame1.add(pane, BorderLayout.PAGE_START);
        frame1.add(addButton, BorderLayout.CENTER);
        frame1.add(removeButton, BorderLayout.PAGE_END);

    }

   /**
    * This initMapComponents is used to initialize the components used in EditMapView class
    */
    private void initMapComponents() {
        label1.setText("Click on the Button to Add/Remove Continent/Country/Territory from Map  ");
        label1.setVisible(true);
        btn_continent.setText("Continent");
        btn_continent.setName("button1");
        btn_continent.setVisible(true);
        btn_country.setText("Country");
        btn_country.setName("button2");
        btn_country.setVisible(true);

        //Displays the dialog to select a file from the user's machine.
        filechooser.setCurrentDirectory(new File(System.getProperty("user.home")));
        filechooser.setDialogTitle("Select a .map file");

        int result = filechooser.showOpenDialog(filechooser);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = filechooser.getSelectedFile();
            fileName = selectedFile.getName();
            System.out.println("Selected file: " + selectedFile.getAbsolutePath());
            MapModel mapmodel = new MapModel();
            gameMap = mapmodel.readMapFile(selectedFile.getAbsolutePath());

            //Condition to check if the correct file is selected.
            if (gameMap.isCorrectMap) {

                //calls the continent method on click of button continent.
                btn_continent.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent evt) {
                        continent();
                    }
                });

                //calls the continent method on click of button continent.
                btn_country.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent evt) {
                        country();
                    }
                });

                frame.setTitle("Edit Map");
                frame.setVisible(true);
                frame.setSize(450, 100);
                frame.setLocationRelativeTo(null);
                frame.add(label1, BorderLayout.PAGE_START);
                frame.add(btn_continent, BorderLayout.WEST);
                frame.add(btn_country, BorderLayout.EAST);

                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            } else {
                dialog.setVisible(true);
                dialog.setSize(400, 100);
                label.setVisible(true);
                label.setSize(400, 100);
                label.setText(gameMap.getErrorMessage());
                dialog.setTitle("ERROR"); // error message when wrong file is selected
                dialog.add(label);
            }
        }
    }
}