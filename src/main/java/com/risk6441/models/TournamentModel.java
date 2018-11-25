/**
 * 
 */
package com.risk6441.models;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

import com.risk6441.controller.PlayGameController;
import com.risk6441.controller.TournamentController;
import com.risk6441.entity.Map;
import com.risk6441.entity.Player;

import javafx.scene.control.TextArea;

/**
 * This class is a model for tournament which is called from
 * {@link TournamentController} TournamentContrller.
 * 
 * @author Nirav
 *
 */
public class TournamentModel implements Observer{

	
	
	private int gameNo;

	public void startTournament(List<Player> playerList, Map map, int numberOfTurn, int numberOfGames,int gameNo,
			TextArea txtAreaConsole) {
		this.gameNo = gameNo;
		PlayGameController controller = new PlayGameController(map);
		controller.loadControllerForStrategy(playerList, numberOfTurn, txtAreaConsole);
		controller.addObserver(this);
		
	}
	
	/* (non-Javadoc)
	 * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
	 */
	@Override
	public void update(Observable o, Object arg) {
		String str = (String) arg;
		System.out.println("update called because of object change "+ str);
		if (str.equals("gameOver")) {
			System.out.println("Game Over For Game "+gameNo);
		} 
		
	}
}
