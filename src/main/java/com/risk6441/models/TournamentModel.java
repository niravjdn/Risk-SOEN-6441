/**
 * 
 */
package com.risk6441.models;

import java.util.HashMap;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.Map.Entry;

import com.risk6441.controller.PlayGameController;
import com.risk6441.controller.TournamentController;
import com.risk6441.entity.Map;
import com.risk6441.entity.Player;
import com.risk6441.gameutils.GameUtils;

import javafx.scene.control.TextArea;

/**
 * This class is a model for tournament which is called from
 * {@link TournamentController} TournamentContrller.
 * 
 * @author Nirav
 *
 */
public class TournamentModel extends Observable implements Observer {

	public TextArea txA;
	HashMap<String, HashMap<String, String>> result = new HashMap<>();
	
	/**
	 * This method starts the tournament.
	 * @param playerList The playerlist for the tournament.
	 * @param map The map for the game
	 * @param numberOfTurn Number of turns before the draw.
	 * @param numberOfGames Number of games on the map.
	 * @param gameNo The current game number.
	 * @param txtAreaConsole The textarea to display results.
	 */
	public void startTournament(List<Player> playerList, Map map, int numberOfTurn, int numberOfGames, int gameNo,
			TextArea txtAreaConsole) {
		PlayGameController controller = new PlayGameController(map);
		controller.loadControllerForTournament(playerList, numberOfTurn, gameNo, txtAreaConsole);
		controller.addObserver(this);

		// create map entry in result
		if (!result.containsKey(map.getMapData().get("image"))) {
			result.put(map.getMapData().get("image"), new HashMap<String, String>());
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
	 */
	@Override
	public void update(Observable o, Object arg) {
		String str = (String) arg;
		System.out.println("update called because of object change " + str);
		String gameNo = str.substring(str.length()-1, str.length());
		if (str.contains("gameOver")) {
			PlayGameController pgc = ((PlayGameController)o);
			String key = pgc.getMap().getMapData().get("image");
			HashMap<String, String> result2 = result.get(key);

			if(pgc.getPlayerList().size()==1) {
				Player winningPlayer = pgc.getPlayerList().get(0);
				System.out.println("Putting "+gameNo);
				result2.put("Game " + gameNo, winningPlayer.getName()+" - "+winningPlayer.getPlayerStrategy());
			} else {
				result2.put("Game " + gameNo, "Draw");
			}
				
			result.put(key, result2);	
				
			System.out.println("Game Over For Game " + gameNo);
			
			setChanged();
			notifyObservers();
			
		}

	}

	/**
	 * This method returns the tournament results.
	 * @return Results of tournament.
	 */
	public HashMap<String, HashMap<String, String>> getTournamentResult() {
		return result;
	}
}
