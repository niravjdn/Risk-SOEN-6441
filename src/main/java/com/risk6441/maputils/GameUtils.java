package com.risk6441.maputils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.risk6441.config.Config;
import com.risk6441.models.Continent;
import com.risk6441.models.Map;
import com.risk6441.models.Player;
import com.risk6441.models.Territory;

import javafx.application.Platform;
import javafx.scene.control.TextArea;

/**
 * This class handles operations related with game play.
 * @author Nirav
 *
 */
public class GameUtils {
	
	/**
	 * This method allocates armies to players and display log in textarea.
	 * @param 
	 * 		p list of players
	 * @param txtArea 
	 * 			textArea object
	 */
	public static void assignArmiesToPlayers(List<Player> p, TextArea txtArea) {
		addTextToLog("===>Assigning armies to players.===\n", txtArea);

		int armyForPlayer = 0;
		int noOfPlayers = p.size();

		
		switch (noOfPlayers) {
		case 2:
			armyForPlayer = Config.ARMIES_TWO_PLAYER;
			break;

		case 3:
			armyForPlayer = Config.ARMIES_THREE_PLAYER;
			break;

		case 4:
			armyForPlayer = Config.ARMIES_FOUR_PLAYER;
			break;


		case 5:
			armyForPlayer = Config.ARMIES_FIVE_PLAYER;
			break;

		case 6:
			armyForPlayer = Config.ARMIES_SIX_PLAYER;
			break;
		}

		for (Player player : p) {
			player.setArmies(armyForPlayer);
			addTextToLog(player.getName() + " has been assigned: " + armyForPlayer + "\n", txtArea);
		}
	}
	
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
	 * This method creates the players.
	 * @param noOfPlayer
	 * 			no of player to be created
	 * @param players
	 * 			list of players
	 * @param textArea
	 * 		  textArea object to append the log
	 * @return
	 */
	public static List<Player> createPlayers(int noOfPlayers, List<Player> players, TextArea textArea) {
		for (int i = 0; i < noOfPlayers; i++) {
			String name = "Player" + i;
			players.add(new Player(i, name));
			addTextToLog(name + " created!\n", textArea);
		}
		return players;
	}
	
	
	/**
	 * This method counts reinforcement armies for the player.
	 * @param map
	 * 		  	map object
	 * @param playerPlaying
	 * 		  	player object
	 * @return
	 * 			return the player object after assigning armies to it.
	 */
	public static Player countReinforcementArmies(Map map, Player playerPlaying) {
		int currentArmies = playerPlaying.getArmies();
		int territoryCount = playerPlaying.getAssignedTerritory().size();
		if (territoryCount < 9) {
			currentArmies = currentArmies + 3;
		} else {
			currentArmies = currentArmies + (territoryCount / 3);
		}

		List<Continent> continents = getContinentsThatBelongsToPlayer(map, playerPlaying);
		if (continents.size() > 0) {
			for (Continent continent : continents) {
				currentArmies = currentArmies + continent.getValue();
			}
		}
		playerPlaying.setArmies(currentArmies);

		return playerPlaying;
	}

	
	/**
	 * This method return continent that are owned by the player.
	 * @param map
	 * 			 map object
	 * @param currentPlayer
	 * 						player object
	 * @return
	 * 			returns the list of continents that are owned by the player.
	 */
	public static List<Continent> getContinentsThatBelongsToPlayer(Map map, Player currentPlayer) {
		List<Continent> continents = new ArrayList<>();

		for (Continent continent : map.getContinents()) {
			boolean continentBelongToPlayer = true;
			for (Territory territory : continent.getTerritories()) {
				if (!territory.getPlayer().equals(currentPlayer)) {
					continentBelongToPlayer = false;
					break;
				}
			}
			if (continentBelongToPlayer) {
				System.out.println(continent.getName() + " is owned by " + currentPlayer.getName());
				continents.add(continent);
			}
		}

		return continents;
	}
	
	
	/**
	 * This method allocates the armies to the player.
	 * @param map
	 * 			map object
	 * @param players
	 * 		  		list of the player
	 * @param textAres
	 * 				  textArea object
	 */
	public static void allocateTerritoryToPlayer(Map map, List<Player> players, TextArea textAres) {

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
	 * This method checks if the fortification phase is valid or not.
	 * @param map
	 * 			 map object
	 * @param currentPlayer
	 * 						current player
	 * @return
	 * 		  return if fortification phase is valid
	 */
	public static boolean isFortificationPhasePossible(Map map, Player currentPlayer) {
		for (Continent continent : map.getContinents()) {
			for (Territory territory : continent.getTerritories()) {
				if (territory.getPlayer().equals(currentPlayer) && territory.getArmy() > 1) {
						for (Territory adjterritory : territory.getAdjacentTerritories()) {
							if (adjterritory.getPlayer().equals(currentPlayer)) {
								return true;
							}
					}
				}
			}
		}

		return false;
	}
}
