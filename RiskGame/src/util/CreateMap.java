package util;

import riskModels.continent.Continent;
import riskModels.country.Country;
import riskModels.map.GameMap;
import riskModels.map.MapModel;

import java.util.*;
import java.util.Map.Entry;

/**
 * This class holds the methods to get inputs and validate input ,and create .map file from it
 *
 * @author prashantp95
 */
public class CreateMap {

	/**
	 * this is the main method to launch the game with no arguments
	 * @param args argument to run main method
	 */
    public static void main(String args[]) {

        System.out.println("How many continent do you want?");
        Scanner sc = new Scanner(System.in);
        int numberOfContinents = sc.nextInt();
        System.out.println("How many Countries do you want ?");
        int numberOfCountries = sc.nextInt();
        List<String> continentNameList = createContinentList(numberOfContinents); //create and name the continents
        Collection<String> countryNameList = createCountryList(numberOfCountries); //create and name the countries
        List<Country> countryList = new ArrayList<Country>();
        List<Continent> continentList = new ArrayList<Continent>();
        HashMap<String, String> countryContinentMap = new HashMap<>(); //map to define relation between country and continent
        HashMap<Country, List<Country>> countryNeibourMap = new HashMap<>(); //map to define relation between country and its neighbor.
        List<String> errorMessage = new ArrayList<String>(); // to store Error messages for invalid details provided by user
        //display continent names
        System.out.println("Continent names are");
        for (String continent : continentNameList) {
            System.out.print(continent + " ");
        }
        System.out.println("");
        System.out.println("Country names are ");
        for (String country : countryNameList) {
            System.out.print(country + " ");
        }
        System.out.println("");
        int counter = 0; //counter to iterate country list
        while (counter < countryNameList.size()) {
            String countryName = countryNameList.toArray(new String[countryNameList.size()])[counter];
            //Assign Continent to Country
            System.out.println(" Assign country-->" + countryName + "-->To Continent");
            Scanner sca = new Scanner(System.in);
            String continentName = sca.nextLine();

            if (continentNameList.contains(continentName)) {
                Country singleCountry = new Country(countryName, 0, 0, continentName);
                countryList.add(singleCountry);
                Continent continent = new Continent(continentName);
                //if continent is not present in continent list then only add it 
                if (!continentList.contains(continent)) {
                    continent.setNumberOfTerritories(1);
                    continentList.add(continent);
                } else { // if continent is already there , we need to increase the Number of territories of that continent
                    int numberOfTerritory = continentList.get(continentList.indexOf(continent)).getNumberOfTerritories();
                    continentList.get(continentList.indexOf(continent)).setNumberOfTerritories(numberOfTerritory + 1);
                }
                countryContinentMap.put(singleCountry.getCountryName(), singleCountry.getBelongsToContinent());
            } else {
                errorMessage.add(continentName + "is not part of Continent Name List Please Assign proper continent for country-> " + countryName);
            }
            counter++;
        }
        counter = 0; // set to zero to use it while assign neighbors to country
        System.out.println("Country and Continent is -->");
        for (Country country : countryList) {
            System.out.println(country.getCountryName() + "   " + country.getBelongsToContinent());
        }
        while (counter < countryNameList.size()) {
            String countryName = countryNameList.toArray(new String[countryNameList.size()])[counter];
            String continentName = countryContinentMap.get(countryName);
            Country country = new Country(countryName, 0, 0, continentName);
            System.out.println(" Assign neighbor for Country-->" + countryName);
            Scanner readNeighbors = new Scanner(System.in);
            String neighborCountries[] = readNeighbors.nextLine().split(",");
            List<Country> neighborNodeList = new ArrayList<Country>();
            for (String neighbor : neighborCountries) {
                if (neighbor.equals(countryName)) {
                    errorMessage.add("Country Can not be neighbor to it self Please enter correct value for->" + countryName);
                } else if (!countryNameList.contains(neighbor)) {
                    errorMessage.add(neighbor + " is not part of CountryList Please enter correct name");

                } else {
                    Country neighborCountry = new Country(neighbor);
                    neighborCountry.setBelongsToContinent(countryContinentMap.get(neighbor));
                    neighborNodeList.add(neighborCountry);
                    country.setNeighborNodes(neighborNodeList);
                    countryNeibourMap.put(country, neighborNodeList);
                }
            }
            counter++;
        }
        if (!errorMessage.isEmpty()) {
            System.out.println("Details you have entered is not correct");
            for (String error : errorMessage) {
                System.out.println(error);
            }
        } else {
            countryNeibourMap = correctNeighbourNodes(countryNeibourMap);
            System.out.println("Please Enter the Name of The Map that you want to create");
            Scanner scanMapFileName = new Scanner(System.in);
            String fileName = scanMapFileName.nextLine();
            GameMap gameMap = GameMap.getInstance();
            HashMap<String, String> mapDetail = new HashMap<>(); //change to default image file
            mapDetail.put("image", "Canada.bmp");
            gameMap.setMapDetail(mapDetail);
            gameMap.setCountryAndNeighborsMap(countryNeibourMap);
            gameMap.setContinentList(continentList);
            MapModel mapModel = new MapModel();
            if (RiskGameUtil.checkNullString(fileName)) {
                mapModel.writeMap(gameMap, fileName); //generate .map files based on details
            }
            for (Object o : countryNeibourMap.entrySet()) {
                Entry pair = (Entry) o;
                Country country = (Country) pair.getKey();
                List<Country> neighbours = (List<Country>) pair.getValue();
                System.out.println("------" + country.getCountryName() + "-----" + country.getBelongsToContinent());
                for (Country neighbour : neighbours) {
                    System.out.println(neighbour.getCountryName() + " " + neighbour.getBelongsToContinent());

                }

            }

        }
    }

    /**
     * This method will correct neighbor nodes  of each country
     * Example if country X's neighbor is Country Y then in Y's neighbor list we should add X too
     *
     * @param countryNeibourMap This is a Hashmap Object
     * @return updated countryNeighbourMap
     */
    private static HashMap<Country, List<Country>> correctNeighbourNodes(HashMap<Country, List<Country>> countryNeibourMap) {

        Iterator<Entry<Country, List<Country>>> it = countryNeibourMap.entrySet().iterator();
        HashMap<Country, List<Country>> updatedCountryNeighborMap = new HashMap<>();
        while (it.hasNext()) {

            Map.Entry pair = (Map.Entry) it.next();
            Country country = (Country) pair.getKey();
            List<Country> neighbours = (List<Country>) pair.getValue();

            for (Country neighbor : neighbours) {
                if (countryNeibourMap.containsKey(neighbor)) {
                    List<Country> neighborNodes = countryNeibourMap.get(neighbor);// user neighbor nodes as Key and get it's neighbor nodes.
                    List<Country> countryToBeAdded = new ArrayList<>(); // Country to be added in neighbor list
                    countryToBeAdded.addAll(neighborNodes);
                    for (Country neighbour : neighborNodes) {
                        if (!neighbour.getCountryName().equals(country.getCountryName())) {
                            if (!countryToBeAdded.contains(country)) {
                                countryToBeAdded.add(country);
                                updatedCountryNeighborMap.put(neighbor, countryToBeAdded);
                            }

                        }
                    }
                }
            }
        }
        if (!updatedCountryNeighborMap.isEmpty()) {
            return updatedCountryNeighborMap; // if we have done any update in existing map data then only we need to return updated map.
        }
        return countryNeibourMap; // in case there is no update done on existing map data.
    }

    /**
     * This method will create country and name it to default.
     *
     * @param numberOfCountries integer data type
     * @return List of Countries
     */
    public static List<String> createCountryList(int numberOfCountries) {
        // TODO Auto-generated method stub
        List<String> returnCountryList = new ArrayList<String>();
        for (int i = 0; i < numberOfCountries; i++) {
            returnCountryList.add(String.valueOf(i));
        }
        return returnCountryList;

    }

    /**
     * This method will create continents and name it to default.
     *
     * @param numberOfContinents integer data type
     * @return List of continents
     */
    public static List<String> createContinentList(int numberOfContinents) {
        // TODO Auto-generated method stub
        List<String> returnListOfContinent = new ArrayList<String>();
        for (int i = 0; i < numberOfContinents; i++) {
            returnListOfContinent.add("c" + i);
        }
        return returnListOfContinent;
    }

}