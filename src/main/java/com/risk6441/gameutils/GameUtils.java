package com.risk6441.gameutils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import com.risk6441.entity.Continent;
import com.risk6441.entity.Map;
import com.risk6441.entity.Player;
import com.risk6441.entity.Territory;
import com.risk6441.exception.InvalidMapException;

import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * This class handles operations related with game play.
 * @author Nirav
 *
 */
public class GameUtils {
	
	static int count=0;
	
	
	/**
	 * This method writes log in textArea.
	 * @param str
	 * 			 String to be written in textArea.
	 * @param txtArea
	 * 				 Textarea object.
	 */
	public static void addTextToLog(String str, TextArea txtArea) {
		Platform.runLater(() -> txtArea.appendText(str));
	}
	
	/**
	 * This method allocates the armies to the player.
	 * @param map
	 * 			map object
	 * @param players
	 * 		  		list of the player
	 * @param textAres
	 * 				  textArea object
	 * @throws InvalidMapException Throws IOException if there is an issue while reading a map file.
	 */
	public static void allocateTerritoryToPlayer(Map map, List<Player> players, TextArea textAres) throws InvalidMapException {

		List<Territory> allterritoriesList = new ArrayList<>();

		if (map.getContinents() != null) {
			for (Continent continent : map.getContinents()) {
				if (continent != null && continent.getTerritories() != null) {
					for (Territory territory : continent.getTerritories()) {
						allterritoriesList.add(territory);
					}
				}
			}
		}
		
		
		if(players.size() > allterritoriesList.size()) {
			throw new InvalidMapException("Territories must be more than players.");
		}
		Collections.shuffle(allterritoriesList); 
		int playerNo = 0;
		for (int i = 0; i < allterritoriesList.size(); i++) {
			if(playerNo == players.size())
				playerNo = 0;

			Territory territory = allterritoriesList.get(i);
			
			if(i >= ((allterritoriesList.size() / players.size()) * players.size())) {
				Random r = new Random();
				
				int randomNo = r.nextInt(players.size());
				System.err.println("Error 2 "+randomNo);
				Player player = players.get(randomNo);
				territory.setPlayer(player);
				territory.setArmy(territory.getArmy() + 1);
				player.setArmies(player.getArmies() - 1);
				player.getAssignedTerritory().add(territory);
				addTextToLog(
						territory.getName() + " is assigned to " + player.getName() + " ! \n", textAres);
				
      			continue;
			}
			
			Player player = players.get(playerNo++);
			territory.setPlayer(player);
			territory.setArmy(territory.getArmy() + 1);
			player.setArmies(player.getArmies() - 1);
			player.getAssignedTerritory().add(territory);
			addTextToLog(
					territory.getName() + " is assigned to " + player.getName() + " ! \n", textAres);
			
		}
	}
	
	/**
	 * This method checks if players armies is exhausted.
	 * @param players
	 * 				 player object
	 * @return
	 * 		  returns true if player has exhausted the armies
	 */
	public static boolean checkIfPlayersArmiesExhausted(List<Player> players) {
		for(Player player : players) {
			if(player.getArmies() != 0) {
				return false;
			}
		}
		return true;
	}
	

	/**
	 * This method checks whether the fortification phase is completed or not.
	 * @param numPlayer
	 * 			 count of active number of players in the game
	 * @return
	 * 		  returns true if the fortification phase is completed
	 */
	public static boolean checkFortificationPhase(int numPlayer)
	{
		count++;
		if(count==numPlayer)
			return true;
		else
			return false;
	}
	
	public static void clearCheckBoxes(CheckBox... checkboxes) {
		for (CheckBox checkbox : checkboxes) {
			checkbox.setSelected(false);
			checkbox.setText("");
		}
	}
	
	/**
	 * This method is used to set visible false of pane.
	 * 
	 * @param panes
	 *            panes
	 */
	public static void disableViewPane(Pane... panes) {
		for (Pane pane : panes) {
			pane.setVisible(false);
		}
	}
	
	/**
	 * This method is used to set visible true of pane.
	 * 
	 * @param panes
	 *            panes
	 */
	public static void enableViewPane(Pane... panes) {
		for (Pane pane : panes) {
			pane.setVisible(true);
		}
	}
	
	
	/**
	 * This method is used to close screen.
	 * @param button
	 *            button
	 */
	public static void exitWindows(Button button) {
		Stage stage = (Stage) button.getScene().getWindow();
		stage.close();
	}
}
