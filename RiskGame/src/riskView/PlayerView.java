package riskView;

import riskModels.map.GameMap;
import riskModels.player.Player;

import java.text.DecimalFormat;
import java.util.Observable;
import java.util.Observer;


/**
 * This class will handle display of  players' details
 *
 * @author prashantp95
 */
public class PlayerView implements Observer {

	/**
	 * overriding update method for getting the current state of the objects
	 */
    @Override
    public void update(Observable arg0, Object arg1) {
        // TODO Auto-generated method stub

        Player p = (Player) arg0;
        if (p.getUpdateMessage().equalsIgnoreCase("Domination")) {
            StringBuilder dominationDetails = new StringBuilder();
            double totalNumberOfCountries = GameMap.getInstance().getCountryAndNeighborsMap().keySet().size();
            DecimalFormat df = new DecimalFormat("#.##");
            
            for (Player play :p.getPlayerList()) {
            	double dominationOfPlayer = play.assignedCountries.size() / totalNumberOfCountries;
                dominationOfPlayer *= 100;
                play.setDomination(Double.valueOf(df.format(dominationOfPlayer)));
                dominationDetails.append(play.getName()).append(" ").append("\n");
                dominationDetails.append(Double.valueOf(df.format(dominationOfPlayer))).append("%").append("\n");
            }
            GameView.showDomination(dominationDetails.toString());
        }
        if (p.getUpdateMessage().equalsIgnoreCase("Phase")) {
           GameView.updatePanelOfPhaseDetails(p.getPhaseDetailMessage());
        }


    }
}

	

