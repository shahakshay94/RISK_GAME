package test.map;

import org.junit.Before;
import org.junit.Test;
import riskModels.country.Country;
import riskModels.map.GameMap;
import riskModels.map.MapModel;
import riskModels.player.Player;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * This class performs the validations related to maps
 *
 * @author hnath
 */
public class MapModelTest extends Player {
    String location = MapModelTest.class.getProtectionDomain().getCodeSource().getLocation().getPath();
    private MapModel mapObj;
    private GameMap gameMapObj;
    private String filePath;

    private ArrayList<String> playerNames;
    private ArrayList<String> playerTypes;

    /**
     * This method setting up the context as many test cases share the same values
     */
    @Before
    public void init() {
        mapObj = new MapModel();
        gameMapObj = GameMap.getInstance();
        filePath = location.replaceAll("/bin", "/res");
        filePath = "/home/akshay/AdvanceProgrammingPracticesRiskGame/RiskGame/res/";
        playerNames = new ArrayList<String>();
        playerTypes = new ArrayList<String>();

        playerNames.add("John");
        playerNames.add("Alexa");
        playerNames.add("Penny");
        playerNames.add("Sheldon");
        playerNames.add("Amy");
        playerNames.add("Raj");
        playerTypes.add("Human");
        playerTypes.add("Human");
        playerTypes.add("Human");
        playerTypes.add("Human");
        playerTypes.add("Human");
        playerTypes.add("Human");
    }

    /**
     * This method checks if the selected map is the correct map
     *
     * @throws Exception it throws if there are any exceptions found
     */
    @Test
    public void testValidateMap() throws Exception {
        gameMapObj = mapObj.readMapFile(filePath + "validate.map");
        assertEquals(true, gameMapObj.isCorrectMap());
    }

    /**
     * This method checks whether the selected map is valid , if no header map is selected
     *
     * @throws Exception it throws if there are any exceptions found
     */
    @Test
    public void testInValidateMap() throws Exception {
        gameMapObj = mapObj.readMapFile(filePath + "no_headers.map");
        assertNotEquals(true, gameMapObj.isCorrectMap());
    }

    /**
     * This method checks whether the selected map is valid , if no neighbours map is selected
     *
     * @throws Exception it throws if there are any exceptions found
     */
    @Test
    public void testInvalidNeighboursMap() throws Exception {
        gameMapObj = mapObj.readMapFile(filePath + "no_neighbours.map");
        assertFalse(gameMapObj.isCorrectMap());
    }

    /**
     * This method checks whether the selected map is valid , if disconnected map is selected
     *
     * @throws Exception it throws if there are any exceptions found
     */
    @Test
    public void testDisconnectedMap() throws Exception {
        gameMapObj = mapObj.readMapFile(filePath + "disconnectedTest.map");
        assertFalse(gameMapObj.isCorrectMap());
    }

    /**
     * This method checks UnconnectedContinent map is valid or not
     *
     * @throws Exception it throws if there are any exceptions found
     */
    @Test
    public void testUnconnectedContinent() throws Exception {
        gameMapObj = mapObj.readMapFile(filePath + "UnconnectedContinent.map");
        assertFalse(gameMapObj.isCorrectMap());
    }

    /**
     * This method checks whether the selected map is valid , if blank map is selected
     *
     * @throws Exception it throws if there are any exceptions found
     */
    @Test
    public void testBlankMap() throws Exception {
        gameMapObj = mapObj.readMapFile(filePath + "blank.map");
        assertFalse(gameMapObj.isCorrectMap());
    }

    /**
     * This method checks whether the selected map is valid , if World map is selected
     *
     * @throws Exception it throws if there are any exceptions found
     */
    @Test
    public void testWorldMap() throws Exception {
        gameMapObj.setInstance(null);
        gameMapObj = mapObj.readMapFile(filePath + "World.map");
        assertTrue(gameMapObj.isCorrectMap());
    }

    /**
     * This method checks whether the selected map is valid , if 3D_Cliff map is selected
     *
     * @throws Exception it throws if there are any exceptions found
     */
    @Test
    public void testCliffUniDirectionalMap() throws Exception {
        gameMapObj.setInstance(null);
        gameMapObj = mapObj.readMapFile(filePath + "3D_Cliff.map");
        assertTrue(gameMapObj.isCorrectMap());
    }

    /**
     * This method checks whether the selected map is valid , if Volcano map is selected
     *
     * @throws Exception it throws if there are any exceptions found
     */
    @Test
    public void testZeroControlValueMap() throws Exception {
        gameMapObj = mapObj.readMapFile(filePath + "Twin_Volcano.map");
        assertFalse(gameMapObj.isCorrectMap());
    }

    /**
     * This method checks whether the selected map is valid , if invalid extension map is selected
     *
     * @throws Exception it throws if there are any exceptions found
     */
    @Test
    public void testInvalidExtensionMap() throws Exception {
        gameMapObj = mapObj.readMapFile(filePath + "invalid_extension.map");
        assertFalse(gameMapObj.isCorrectMap());
    }

    /**
     * This method checks for map validation by testing country object
     *
     * @throws Exception it throws if there are any exceptions found
     */
    @Test
    public void testCountryObject() throws Exception {
        gameMapObj = mapObj.readMapFile(filePath + "validate.map");
        assertEquals("c3", mapObj.getCountryObj("c3", gameMapObj).getCountryName());
    }

    /**
     * This method checks if the map is valid , the country object with null
     *
     * @throws Exception it throws if there are any exceptions found
     */
    @Test
    public void testCountryObjectWithNull() throws Exception {
        gameMapObj = mapObj.readMapFile(filePath + "validate.map");
        assertEquals(null, mapObj.getCountryObj("APP", gameMapObj));
    }

    /**
     * This method checks if the country is removed as expected to the map
     *
     * @throws Exception it throws if there are any exceptions found
     */
    @Test
    public void removeCountry() throws Exception {
        gameMapObj = mapObj.readMapFile(filePath + "validate.map");
        Country countryToRemove = mapObj.getCountryObj("c1", gameMapObj);
        mapObj.removeCountry(countryToRemove, gameMapObj);
        assertEquals(null, mapObj.getCountryObj("c1", gameMapObj));
    }

    /**
     * This method checks if the country is added as expected to the map
     *
     * @throws Exception it throws if there are any exceptions found
     */
    @Test
    public void addCountry() throws Exception {
        gameMapObj = mapObj.readMapFile(filePath + "validate.map");
        Country country = new Country("100", 23, 34, "abc");
        Country country1 = new Country("101", 43, 94, "abc");
        Country country2 = new Country("102", 63, 23, "abc");
        List<Country> neighboringCountries = new ArrayList<Country>();
        neighboringCountries.add(country1);
        neighboringCountries.add(country2);
        mapObj.addCountry(country, gameMapObj, neighboringCountries);
        assertEquals("100", mapObj.getCountryObj("100", gameMapObj).getCountryName());

    }

}