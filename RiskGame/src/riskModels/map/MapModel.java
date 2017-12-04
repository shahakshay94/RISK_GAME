package riskModels.map;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import riskModels.continent.Continent;
import riskModels.country.Country;
import riskModels.player.Player;
import riskView.GameView;
import util.RiskGameUtil;

/**
 * This class will perform operation related to MapObj created from MapFile
 *
 * @author Akshay and Prashant
 * @version 1.0
 */
public class MapModel {
	
	public static int saveFileCounter=1;
    /**
     * This method will assignContinent to Neighbor countries
     *
     * @param countryAndNeighbors It is same List returned while reading [Territories] part
     * @return HashMap ,Which will have Country as a key and List of Country (neighbors) as a Value.it will help to create graph
     */
    public static HashMap<Country, List<Country>> assignContinentToNeighbors(List<Country> countryAndNeighbors) {
        HashMap<String, String> countryContinentMap = new HashMap<>();
        HashMap<Country, List<Country>> countryAndNeighbours = new HashMap<>();

        for (Country country : countryAndNeighbors) {
            countryContinentMap.put(country.getCountryName(), country.getBelongsToContinent());
        }
        for (Country country : countryAndNeighbors) {
            List<Country> neighbourList = new ArrayList<>();
            for (Country neighbour : country.getNeighborNodes()) {
                String continentName = countryContinentMap.get(neighbour.getCountryName());
                neighbour.setBelongsToContinent(continentName);
                neighbourList.add(neighbour);
            }
            countryAndNeighbours.put(country, neighbourList);
        }
        return countryAndNeighbours;
    }

    /**
     * This method will read [Territories] part of map file
     *
     * @param bufferReaderForFile BufferedReader class object as param
     * @return List of Country.Every single object of country will have countryname,continentname,start/end pixels;
     */
    public static List<Country> readTerritories(BufferedReader bufferReaderForFile) {
        String Territories;
        List<Country> countryList = new ArrayList<>();
        try {
            while ((Territories = bufferReaderForFile.readLine()) != null && !Territories.startsWith("[")) {
                if (RiskGameUtil.checkNullString(Territories)) {
                    String countryName, continentName = null;
                    int startPixel, endPixel = 0;
                    List<Country> neighbourNodes = new ArrayList<>();
                    String[] terrProperties = Territories.split(",");
                    countryName = terrProperties[0];
                    continentName = terrProperties[3];
                    startPixel = Integer.parseInt(terrProperties[1].trim());
                    endPixel = Integer.parseInt(terrProperties[2].trim());
                    Country country = new Country(countryName, startPixel, endPixel, continentName);
                    for (int i = 4; i <= terrProperties.length - 1; i++) {
                        String neighbourCountryName = terrProperties[i];
                        Country neighbour = new Country(neighbourCountryName);
                        neighbourNodes.add(neighbour);
                    }
                    country.setNeighborNodes(neighbourNodes);
                    countryList.add(country);

                }

            }
        } catch (NumberFormatException | IOException e) {
            e.printStackTrace();

        }
        return countryList;
    }

    /**
     * This method will read the continent part of map file
     *
     * @param bufferReaderForFile BufferReader object that read the .map file for continent
     * @return List of Continent. every single object of the list contains continentName and number of countries it hold.
     */
    public static List<Continent> readContinents(BufferedReader bufferReaderForFile) {
        String Continents;
        List<Continent> continentsList = new ArrayList<>();
        try {
            while ((Continents = bufferReaderForFile.readLine()) != null && !Continents.startsWith("[")) {
                if (RiskGameUtil.checkNullString(Continents)) {
                    Continent continents = new Continent();
                    List<Integer> allContinents = new ArrayList<Integer>();
                    String[] ConProperties = Continents.split("=");
                    continents.setContinentName(ConProperties[0].trim());
                    continents.setControlValue(Integer.parseInt(ConProperties[1].trim()));
                    continentsList.add(continents);
                }
                bufferReaderForFile.mark(0);
            }
            bufferReaderForFile.reset();
        } catch (NumberFormatException | IOException e) {

            e.printStackTrace();
        }
        return continentsList;
    }

    /**
     * This  method will prepare HashMap for continent and country objects
     *
     * @param country list of country that we have got while parsing the  map file
     * @param listOfContinents listOfContinents that we have got while parsing the map file
     * @return HashMap of continent to countries objects
     */
    private static HashMap<Continent, List<Country>> getContinentCountryMap(List<Country> country, List<Continent> listOfContinents) {
        HashMap<Continent, List<Country>> continentCountryMap = new HashMap<>();
        for (Country c : country) {
            List<Country> countryList = new ArrayList<>();
            if (listOfContinents.contains(new Continent(c.getBelongsToContinent()))) {
                if (continentCountryMap.containsKey(new Continent(c.getBelongsToContinent()))) {
                    continentCountryMap.get(new Continent(c.getBelongsToContinent())).add(c);
                } else {
                    int indexOfContinent = listOfContinents.indexOf(new Continent(c.getBelongsToContinent()));
                    countryList.add(c); // Avoid using Array.asList here as it gives fixed size list and will not allow to modify the list
                    continentCountryMap.put(listOfContinents.get(indexOfContinent), countryList);
                }
            }
        }
        return continentCountryMap;
    }

    /**
     * This method finds the country from the gameMap object and returns the country object if present
     *
     * @param countryName pass the name of the country to find from the gameMap as a string
     * @param gameMap     GameMap class object from which intended country need to be return
     * @return country object
     */
    public static Country getCountryObj(String countryName, GameMap gameMap) {
        for (Map.Entry<Country, List<Country>> pair : gameMap.getCountryAndNeighborsMap().entrySet()) {
            // get country object
            Country keyCountry = pair.getKey();
            if (keyCountry.getCountryName().equalsIgnoreCase(countryName)) {
                return keyCountry;
            }
        }
        return null;
    }

    /**
     * This method will read the mapfile and provide data to creategraph
     *
     * @param filePath path of .map file
     * @return Function will return the map details obj
     */
    public GameMap readMapFile(String filePath) {
        BufferedReader bufferReaderForFile = null;
        boolean isMAPresent = false; //to check [MAP] is available in file or not
        boolean isContinentPresent = false;//to check [Continent] is available in file or not
        boolean isTerritoryPresent = false;//to check [Territory] is available in file or not
        GameMap mapDetails = GameMap.getInstance();
        try {
            File file = new File(filePath);
            String validationMessage = validateFile(file);
            if (!validationMessage.equalsIgnoreCase("Valid File")) {
                mapDetails.setCorrectMap(false);
                mapDetails.setErrorMessage(validationMessage);
                return mapDetails;
            }
            bufferReaderForFile = new BufferedReader(new FileReader(file));
            String st, maps;
            List<Continent> listOfContinents = new ArrayList<>();
            while ((st = bufferReaderForFile.readLine()) != null) {
                if (st.startsWith("[")) {
                    HashMap<String, String> mapDetail = new HashMap<>();
                    String id = st.substring(st.indexOf("[") + 1, st.indexOf("]"));
                    // Parsing the [MAP] portion of the map file
                    if (id.equalsIgnoreCase("Map")) {
                        isMAPresent = true;
                        while ((maps = bufferReaderForFile.readLine()) != null && !maps.startsWith("[")) {
                            if (RiskGameUtil.checkNullString(maps)) {
                                String[] mapsEntry = maps.split("=");
                                mapDetail.put(mapsEntry[0], mapsEntry[1]);
                                bufferReaderForFile.mark(0);
                            }
                        }
                        bufferReaderForFile.reset();
                        mapDetails.setMapDetail(mapDetail);
                    }
                    //Parsing the [Continents] portion of the map file
                    if (id.equalsIgnoreCase("Continents")) {
                        isContinentPresent = true;
                        if (isMAPresent) {
                            listOfContinents = MapModel.readContinents(bufferReaderForFile);
                            mapDetails.setContinentList(listOfContinents); //we do not have number of territories under each continent yet.
                            System.out.println("Reading of Continents Completed");
                        }

                    }
                    //Parsing the  [Territories] portion of the map file
                    if (id.equalsIgnoreCase("Territories")) {
                        isTerritoryPresent = true;
                        if (isMAPresent) {
                            List<Country> countryAndNeighbor = MapModel.readTerritories(bufferReaderForFile);
                            HashMap<Country, List<Country>> graphReadyMap = MapModel.assignContinentToNeighbors(countryAndNeighbor);
                            HashMap<Continent, List<Country>> continentCountryMap = getContinentCountryMap(countryAndNeighbor, listOfContinents);
                            List<Continent> updatedcontinentList = new ArrayList<>();// we need to assign number of territories to continent objects
                            for (Continent continent : GameMap.getInstance().getContinentList()) {
                                //value of each key of continentCountryMap contains list of territories/country with in that continent
                                //continent.setNumberOfTerritories(continentCountryMap.get(new Continent(continent.getContinentName())).size());
                                continent.setMemberCountriesList(continentCountryMap.get(new Continent(continent.getContinentName())));
                                updatedcontinentList.add(continent);
                            }
                            System.out.println("Reading of Territories Completed");
                            for (Object o : graphReadyMap.entrySet()) {
                                Map.Entry pair = (Map.Entry) o;
                                Country country = (Country) pair.getKey();
                                List<Country> neighbours = (List<Country>) pair.getValue();
                            }
                            mapDetails.setCountryAndNeighborsMap(graphReadyMap);
                            mapDetails.setContinentCountryMap(continentCountryMap);
                            mapDetails.setContinentList(updatedcontinentList);
                        }
                    }
                }
            }

            if (isMAPresent && isContinentPresent && isTerritoryPresent) {
                System.out.println("Map Continents and Territories tags are present");
            } else {
                mapDetails.setCorrectMap(false);
                mapDetails.setErrorMessage("MAP or Continents or Territories tags not present");
                return mapDetails;
            }
            validateMap(mapDetails);

        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return mapDetails;

    }

    /**
     * This method will do validation on the details we parsed from the .map file
     *
     * @param mapDetails mapDetails that we have parsed from .map file
     * @return mapDetails with correct error message.
     */
    public GameMap validateMap(GameMap mapDetails) {

    	
        Iterator it = mapDetails.getCountryAndNeighborsMap().entrySet().iterator();
        for(Continent continent: mapDetails.getContinentList()) {
        	if(continent.getControlValue()<=0) {
        		mapDetails.setCorrectMap(false);
                mapDetails.setErrorMessage("There is invalid  control value of the continent-->"+continent.getContinentName());
                break;
        	}
        }
        
        if(mapDetails.isCorrectMap() && checkUnconnectedContinent(mapDetails)) {
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            Country country = (Country) pair.getKey();
            List<Country> neighbours = (List<Country>) pair.getValue();
            // check if country all countries have at least one neighbor
            if (neighbours.isEmpty()) {
                mapDetails.setCorrectMap(false);
                mapDetails.setErrorMessage(country.getCountryName() + " does not have any neighbor nodes");
                return mapDetails;
            }
            // here  all countries have at least one neighbor , now check connectivity between them
            MapModel mapModel = new MapModel();
            for (Country country1 : mapDetails.getCountryAndNeighborsMap().keySet()) {
                if (GameMap.getInstance().isCorrectMap) {
                    for (Country country2 : mapDetails.getCountryAndNeighborsMap().keySet()) {
                        if (!country1.equals(country2)) {
                            if (!mapModel.isConnected(country1, country2)) {
                                mapDetails.setCorrectMap(false);
                                mapDetails.setErrorMessage("Disconnected Country Found " + country1.getCountryName() + " " + country2.getCountryName());
                                break;
                            }
                        }
                    }
                }
            }
        }
      }
      
        return mapDetails;
   }
    /**
     * 
     * @param mapDetails current GameMap object
     * @return true if there is no unconnected continent found. false if there is unconnected continent present in the map.
     */
    public boolean checkUnconnectedContinent(GameMap mapDetails) {
    	// iterate over each continent 
    	for (Entry<Continent, List<Country>> entry : mapDetails.getContinentCountryMap().entrySet()) {
            Continent continent = entry.getKey();
            List<Country> countries = entry.getValue();
            for(Country country: countries) {
            	int countryCount=0;
            	for(Country neighbour:GameMap.getInstance().getCountryAndNeighborsMap().get(country)) {
            		// check if all neighbors belongs to other continent
            		if(!neighbour.getBelongsToContinent().equalsIgnoreCase(continent.getContinentName())) {
            			    countryCount++;
                  			if(countryCount>=GameMap.getInstance().getCountryAndNeighborsMap().get(country).size()) {
            				mapDetails.setCorrectMap(false);
            				mapDetails.setErrorMessage("unconnected continent found"+continent.getContinentName());
            				return false;
            			}
            		}
            	}
            	
            }
           
        }
		return true;
		
	}

	/**
     * Checks if it is connected.
     *
     * @param c1           the country  1
     * @param c2           the country 2
     * @param unwantedPair the unwanted pair
     * @return true, if is connected
     */
    private boolean isConnected(Country c1, Country c2, List<Country> unwantedPair) {
        // if provided countries are neighbor they are connected
        if (isNeighbour(c1, c2))
            return true;
        // if they are not neighbor check if they are reachable to each other
        if (unwantedPair == null)
            unwantedPair = new ArrayList<>();
        else if (unwantedPair.contains(c1))
            return false;
        unwantedPair.add(c1);
        if (GameMap.getInstance().getCountryAndNeighborsMap().get(c1) != null) {
            for (Country c : GameMap.getInstance().getCountryAndNeighborsMap().get(c1)) {
                if (!unwantedPair.contains(c) && isConnected(c, c2, unwantedPair))
                    return true;
            }
        }


        return false;
    }

    /**
     * Checks if it is connected.
     *
     * @param c1 the c 1
     * @param c2 the c 2
     * @return true, if is connected
     */
    public boolean isConnected(Country c1, Country c2) {
        return isConnected(c1, c2, new ArrayList<Country>());
    }

    /**
     * check if countries provided in argument are adjacent neighbors or not
     *
     * @param c1 country 1
     * @param c2 country 2
     * @return true if countries  are direct adjacent else false
     */
    public boolean isNeighbour(Country c1, Country c2) {
        return GameMap.getInstance().getCountryAndNeighborsMap().get(c1) != null && (GameMap.getInstance().getCountryAndNeighborsMap().get(c1).contains(c2));
    }

    /**
     * Checks if it is connected.
     *
     * @param c1 country 1
     * @param c2 country 2
     * @param p player for which it's countries are connected to each other or not
     * @return true, if is connected
     */

    public boolean isConnected(Country c1, Country c2, Player p) {
        return isConnected(c1, c2, p, null);
    }

    /**
     * Checks if it is connected.
     * @param c1 country 1
     * @param c2 country 2
     * @param p player for which it's countries are connected to each other or not
     * @param unwantedPair the unwanted pair
     * @return true, if is connected
     */

    private boolean isConnected(Country c1, Country c2, Player p, List<Country> unwantedPair) {
        if (isNeighbour(c1, c2) && c1.getBelongsToPlayer().equals(c2.getBelongsToPlayer()))
            return true;
        if (unwantedPair == null)
            unwantedPair = new ArrayList<>();
        else if (unwantedPair.contains(c1))
            return false;
        unwantedPair.add(c1);
        for (Country c : GameMap.getInstance().getCountryAndNeighborsMap().get(c1)) {
            if (!unwantedPair.contains(c) && isConnected(c, c2, p, unwantedPair))
                return true;
        }
        return false;
    }

    /**
     * This method will perform validation of provided input file
     *
     * @param file File class object is passed where input file is selected by user and is check for validation
     * @return error/success Message
     */
    public String validateFile(File file) {

        //check if file is present or not
        if (!file.exists()) {
            return "File does not exists";
        }
        String name = file.getName();
        String extension = name.substring(name.lastIndexOf(".") + 1);
        //check if user has selected  file with .map extension
        if (!extension.equalsIgnoreCase("map")) {
            return "Invalid extension of  File Please provide correct file";
        }
        //check if file selected by user is empty or not
        if (file.length() == 0) {
            return "File is empty please select correct file";
        }
        return "Valid File";


    }

    /**
     * This method will remove the country
     *
     * @param country - Country that to be removed
     * @param gameMap - Existing GameMap From where country will be removed
     */
    public void removeCountry(Country country, GameMap gameMap) {
        List<Country> neighborCountryList = gameMap.getCountryAndNeighborsMap().get(country);

        // removing country from the neighbor list of other countries.
        for (Country neighborCountry : neighborCountryList) {
            List<Country> removeCountyNeighborList = gameMap.getCountryAndNeighborsMap().get(neighborCountry);
            List<Country> updatedNeighborList = new ArrayList<>();
            for (Country countryRemoveFromNeighbor : removeCountyNeighborList) {
                if (!countryRemoveFromNeighbor.getCountryName().equalsIgnoreCase(country.getCountryName())) {
                    updatedNeighborList.add(countryRemoveFromNeighbor);
                }
            }
            gameMap.getCountryAndNeighborsMap().put(neighborCountry, updatedNeighborList); // this will replace existing  key-value pair.
        }
        // finally removing country from the map details and update number of territories for continent
        int indexOfContinent = gameMap.getContinentList().indexOf(new Continent(country.getBelongsToContinent()));
        int numberOfTerritories = gameMap.getContinentList().get(indexOfContinent).getNumberOfTerritories();
        gameMap.getContinentList().get(indexOfContinent).setNumberOfTerritories(numberOfTerritories - 1);
        gameMap.getCountryAndNeighborsMap().remove(country);
    }

    /**
     * This method will add country in existing Map
     *
     * @param country      the country that you want to add
     * @param gameMap      current map details
     * @param neighborList List of neighborCountry
     */
    public void addCountry(Country country, GameMap gameMap, List<Country> neighborList) {
        if (country != null && !neighborList.isEmpty()) {
            gameMap.getCountryAndNeighborsMap().put(country, neighborList); // country added to existing game details map
            //update number of territories for continent
            int indexOfContinent = gameMap.getContinentList().indexOf(new Continent(country.getBelongsToContinent()));
            int numberOfTerritories = gameMap.getContinentList().get(indexOfContinent).getNumberOfTerritories();
            gameMap.getContinentList().get(indexOfContinent).setNumberOfTerritories(numberOfTerritories + 1); // increasing number of territories in continent.
            // Updating neighbour list
            MapModel mapmodel = new MapModel();
            for (Country neighbor : neighborList) {
                mapmodel.addNeighbor(neighbor.getCountryName(), gameMap, country);
            }
        }
    }

    /**
     * This method will add neighbor to Country
     *
     * @param countryName     country name where neighbors to be added
     * @param gameMap         current map details
     * @param neighborCountry neighbor country to be added.
     */
    public void addNeighbor(String countryName, GameMap gameMap, Country neighborCountry) {
        if (RiskGameUtil.checkNullString(countryName)) {
            Country country = new Country(countryName);
            if (gameMap.getCountryAndNeighborsMap().containsKey(country)) {
                gameMap.getCountryAndNeighborsMap().get(country).add(neighborCountry);
            }
        }
    }

    /**
     * This method will remove Continent from the map
     *
     * @param continent name of the continent
     * @param fileName name of the file from which continent should be removed
     */
    public void removeContinent(Continent continent, String fileName) {
        System.out.println("Removing Continent Name" + continent.getContinentName());
        Set<Country> countries = GameMap.getInstance().getCountryAndNeighborsMap().keySet();
        List<Country> countryList = new ArrayList<>();
        MapModel mapmodel = new MapModel();
        for (Country c : countries) {
            Country countryTest = new Country(c.getCountryName(), c.getBelongsToContinent());
            countryList.add(countryTest);
        }
        for (Country c : countryList) {
            if (c.getBelongsToContinent().equalsIgnoreCase(continent.getContinentName())) {
                mapmodel.removeCountry(c, GameMap.getInstance());
            }
        }

        Continent continentToBeRemoved = GameMap.getInstance().getContinentList().get(GameMap.getInstance().getContinentList().indexOf(new Continent(continent.getContinentName())));
        GameMap.getInstance().getContinentList().remove(GameMap.getInstance().getContinentList().indexOf(new Continent(continent.getContinentName())));
        GameMap.getInstance().getContinentCountryMap().remove(new Continent(continent.getContinentName()));
        if (mapmodel.validateMap(GameMap.getInstance()).isCorrectMap) {
            mapmodel.writeMap(GameMap.getInstance(), fileName);
        } else {
            String errorMessage = GameMap.getInstance().getErrorMessage();
            GameMap.getInstance().getContinentList().add(continentToBeRemoved);
            // GameMap.getInstance().getContinentCountryMap().add(new Continent(continent.getContinentName()));
            GameMap.getInstance().setErrorMessage("Can not remove continent  " + errorMessage);
            System.out.println(GameMap.getInstance().getErrorMessage());
        }

    }

    /**
     * This method will create .map file based on input provided from user
     *
     * @param graphMap Details provided from the user
     * @param filename Map file name that user wants to give
     */
    public void writeMap(GameMap graphMap, String filename) {
        StringBuilder maps = new StringBuilder("[Map]\n");
        for (Map.Entry<String, String> entry : graphMap.getMapDetail().entrySet()) {
            maps.append(entry.getKey()).append("=").append(entry.getValue()).append("\n");
        }
        maps.append("\n");

        StringBuilder continents = new StringBuilder("[Continents]\n");
        System.out.println("this is the size of continents:" + graphMap.getContinentList().size());
        for (Continent continent : graphMap.getContinentList()) {
            continents.append(continent.continentName).append("=").append(continent.controlValue).append("\n");
        }
        continents.append("\n");

        StringBuilder territories = new StringBuilder("[Territories]\n");

        // loop of graphMap
        Iterator<Map.Entry<Country, List<Country>>> it = graphMap.getCountryAndNeighborsMap().entrySet().iterator();
        int startPixel = 45;
        int endPixel = 60;
        while (it.hasNext()) {
            Map.Entry<Country, List<Country>> pair = it.next();
            // get country object
            Country keyCountry = pair.getKey();
            // get list of the neighbors
            List<Country> neiCountryList = pair.getValue();

            // index of the country from the all countries of all continents
            // list
            startPixel += 10;
            endPixel += 10;
            // get values of each country object
            territories.append(keyCountry.countryName).append(",").append(startPixel).append(",").append(endPixel).append(",").append(keyCountry.getBelongsToContinent());

            // get the index value of the neighbor
            for (Country c : neiCountryList) {
                territories.append(",").append(c.countryName);
            }
            territories.append("\n");
        }

        String result = maps + continents.toString() + territories;

        try (PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(filename)))) {
            out.print(result);
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    /**
     * This method will save the game .
     * @param gameMap GameMap object at the point of saving the game
     * @return true if function able to save the game else false
     */
    public static boolean saveGame(GameMap gameMap) {
    	
    	FileOutputStream fout = null;
		ObjectOutputStream oos = null;
		DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date today;
        String fileName="";
		try {
			today = formatter.parse(formatter.format(new Date()));
			fileName=today.toString().replaceAll("00:00:00"," ").replaceAll("\\s+","").concat("_"+String.valueOf(saveFileCounter++));
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
       
		try {
            fout = new FileOutputStream(fileName+".ser");
			oos = new ObjectOutputStream(fout);
			GameView.displayLog(fileName+".ser");
			oos.writeObject(gameMap);

		} catch (Exception ex) {

			ex.printStackTrace();

		} finally {
			if (fout != null) {
				try {
					fout.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

			if (oos != null) {
				try {
					oos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		}
     return true;
    }
    /**
     * This method will load the saved game
     * @param fileName fileName that user gave while saving the map 
     * @return true if function able to save the game else false
     */
    public static GameMap loadGame(String fileName) {
    	FileInputStream fin = null;
		ObjectInputStream ois = null;
        GameMap gameMap = null;
		try {
			fin = new FileInputStream(fileName);
			ois = new ObjectInputStream(fin);
			gameMap = (GameMap) ois.readObject();

		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {

			if (fin != null) {
				try {
					fin.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
           if (ois != null) {
				try {
					ois.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		}
		return gameMap;

    }
}