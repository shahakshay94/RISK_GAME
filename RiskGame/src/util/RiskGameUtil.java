package util;

import java.util.Objects;

/**
 * This class will have common utility methods as well as some String Constant
 * for example  checking null string or blank string
 *
 * @author prashantp95
 */
public class RiskGameUtil {

    public static String threePlayersBtnName = "threePlayersBtn";
    public static String fourPlayersBtnName = "fourPlayersBtn";
    public static String fivePlayersBtnName = "fivePlayersBtn";
    public static String sixPlayersBtnName = "sixPlayersBtn";
    public static String backBtnName = "backBtn";
    public static String mapBtnName1 = "mapBtn1";
    public static String mapBtnName2 = "mapBtn2";
    public static String mapBtnName3 = "mapBtn3";
    public static String mapBtnName4 = "mapBtn4";
    public static String mapBtnName5 = "mapBtn5";
    public static String startGameBtnName = "startGame";

    /**
     * to check null or empty string
     * input String , output boolean(true/false)
     *
     * @param name String to be check.
     * @return true or false based on validation
     */
    public static boolean checkNullString(String name) {
        return !Objects.equals(name, "") && name != null && !name.isEmpty();
    }
}
