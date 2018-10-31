/**
 * 
 */
package com.risk6441.models;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import com.risk6441.config.Config;
import com.risk6441.controller.DiceController;
import com.risk6441.entity.Continent;
import com.risk6441.entity.Map;
import com.risk6441.entity.Player;
import com.risk6441.entity.Territory;
import com.risk6441.exception.InvalidGameActionException;
import com.risk6441.gameutils.GameUtils;
import com.risk6441.maputils.CommonMapUtil;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

/**
 * @author Nirav
 *
 */
public class PlayerModel extends Observable implements Observer{

	/**
	 * the @playerPlaying reference
	 */
	Player currentPlayer;

	/**
	 * @return player playing
	 */
	public Player getCurrentPlayer() {
		return currentPlayer;
	}
	
	/**
	 * @return player playing
	 */
	public void setCurrentPlayer(Player player) {
		currentPlayer = player;
	}
	
	/**
	 * the @territoryWon
	 */
	private int territoryWon;
	
	
	
	/**
	 * This method allocates armies to players and display log in textarea.
	 * @param 
	 * 		p list of players
	 * @param txtArea 
	 * 			textArea object
	 */
	public static void assignArmiesToPlayers(List<Player> p, TextArea txtArea) {
		GameUtils.addTextToLog("===>Assigning armies to the players.===\n", txtArea);

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
			GameUtils.addTextToLog(player.getName() + " has been assigned: " + armyForPlayer + "\n", txtArea);
		}
	}
	
	/**
	 * This method creates space for the players.
	 * @param noOfPlayers
	 * 			no of players to be created
	 * @param players
	 * 			list of players
	 * @param textArea
	 * 		  textArea object to append the log
	 * @return 
	 * 			list of players after creating players
	 */
	public static List<Player> createPlayers(int noOfPlayers, List<Player> players, TextArea textArea) {
		for (int i = 1; i <= noOfPlayers; i++) {
			String name = "Player" + i;
			players.add(new Player(i, name));
			GameUtils.addTextToLog(name + " created!\n", textArea);
		}
		return players;
	}
	
	/**
	 * This method counts the number of reinforcement armies for each player.
	 * @param map
	 * 		  	map object
	 * @param currentPlayer
	 * 		  	player object
	 * @return
	 * 			return the player object after assigning armies to it.
	 */
	public static Player countReinforcementArmies(Map map, Player currentPlayer) {
		int currentArmies = currentPlayer.getArmies();
		int territoryCount = currentPlayer.getAssignedTerritory().size();
		System.out.println("No Of Territories For Player : "+currentPlayer.getName()+ " - "+territoryCount);
		if (territoryCount < 9) {
			currentArmies = currentArmies + 3;
		} else {
			currentArmies += (territoryCount / 3);
		}

		List<Continent> continents = getContinentsThatBelongsToPlayer(map, currentPlayer);
		if (continents.size() > 0) {
			for (Continent continent : continents) {
				currentArmies = currentArmies + continent.getValue();
			}
		}
		currentPlayer.setArmies(currentArmies);

		return currentPlayer;
	}

	/**
	 * This method returns the continents owned by the player.
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
	 * Check if the player has a valid attack move
	 * 
	 * @param territories
	 *            territories List View
	 * @param gameConsole
	 *            gameConsole text area
	 * 
	 * @return hasAValidMove true if player has valid move else false
	 */
	public boolean playerHasAValidAttackMove(ListView<Territory> territories, TextArea gameConsole) {
		boolean hasAValidMove = false;
		for (Territory territory : territories.getItems()) {
			if (territory.getArmy() > 1) {
				hasAValidMove = true;
			}
		}
		if (!hasAValidMove) {
			GameUtils.addTextToLog("No valid attack move avialble move to Fortification phase.\n", gameConsole);
			GameUtils.addTextToLog("===Attack phase ended! === \n", gameConsole);
			setChanged();
			notifyObservers("checkIfFortificationPhaseValid");
			return hasAValidMove;
		}
		return hasAValidMove;
	}
	
	
	/**
	 * Attack phase
	 * @param attackingTerritory attacking territory
	 * @param defendingTerritory defending territory
	 * @throws InvalidGameMoveException invalid game exception
	 */
	public void attackPhase(Territory attackingTerritory, Territory defendingTerritory)
			throws InvalidGameActionException {
		if (attackingTerritory != null && defendingTerritory != null) {
			isAValidAttackMove(attackingTerritory, defendingTerritory);

			DiceModel diceModel = new DiceModel(attackingTerritory, defendingTerritory);
			diceModel.addObserver(this);
			final Stage newMapStage = new Stage();
			newMapStage.setTitle("Attack Window");

			DiceController diceController = new DiceController(diceModel);

			FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("diceview.fxml"));
			loader.setController(diceController);

			Parent root = null;
			try {
				root = (Parent) loader.load();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			Scene scene = new Scene(root);
			newMapStage.setScene(scene);
			newMapStage.show();
		} else {
			throw new InvalidGameActionException("Please choose both attacking and defending territory.");
		}
	}


	/**
	 * Check if Attack Move is Valid
	 * 
	 * @param attacking
	 *            attacking Territory
	 * @param defending
	 *            defending Territory
	 * 
	 * @return isValidAttackMove if the attack move is valid
	 * 
	 * @throws InvalidGameMoveException invalid game exception
	 */
	public boolean isAValidAttackMove(Territory attacking, Territory defending) throws InvalidGameActionException {
		boolean isValidAttackMove = false;
		if (defending.getPlayer() != attacking.getPlayer()) {
			if (attacking.getArmy() > 1) {
				isValidAttackMove = true;
			} else {
				throw new InvalidGameActionException("Attacking territory should have more than one army to attack.");
			}
		} else {
			throw new InvalidGameActionException("You can\'t attack on your own territory.");
		}
		return isValidAttackMove;
	}
	
	/**
	 * Check if any Player Lost the Game
	 * 
	 * @param playersPlaying
	 *            playerPlaying List
	 * 
	 * @return playerLost Player Object who lost the game
	 */
	public Player checkIfAnyPlayerLostTheGame(List<Player> playersPlaying) {
		Player playerLost = null;
		for (Player player : playersPlaying) {
			if (player.getAssignedTerritory().isEmpty()) {
				playerLost = player;
				
			}
		}
		return playerLost;
	}
	

	/**
	 * Reinforcement Phase
	 * 
	 * @param territory
	 *            territory Object
	 * @param txtAreaMsg
	 *            the Game Console
	 */
	public void reinforcementPhase(Territory territory, TextArea txtAreaMsg) {
		
		if(currentPlayer.getArmies()>0)
    	{
    		if(territory == null) {
    			CommonMapUtil.alertBox("infor", "Please select a territory to reinforce army on.", "Alert");
    			return;
    		}
    		int getArmy=CommonMapUtil.inputDialogueBoxForRenforcement();
    		if(getArmy > 0) {
    			if(getArmy > currentPlayer.getArmies()) {
    				CommonMapUtil.alertBox("Info", "The Army to be moved in reinforce phase should be less than army you have.", "Alert");
    				return;
    			}else {
    				territory.setArmy(territory.getArmy() + getArmy);
    				currentPlayer.setArmies(currentPlayer.getArmies() - getArmy);
    				GameUtils.addTextToLog("==="+getArmy+" assigned to : === \n"+territory+"  -- Player "+currentPlayer.getName()+"\n", txtAreaMsg);
    				GameUtils.addTextToLog("======Reinforce Phase Completed. ===========", txtAreaMsg);
    			}
    		}else {
    			CommonMapUtil.alertBox("Info", "Invalid Input. Number should be > 0.", "Alert");
    			return;
    		}
    	}
		
		//start attack phase after completion of reinforcement phase
    	if (currentPlayer.getArmies() <= 0) {
    		GameUtils.addTextToLog("===Reinforcement phase Ended! ===\n", txtAreaMsg);
			setChanged();
			notifyObservers("Attack");
    	}
		
	}
	

	/**
	 * Fortification Phase
	 * 
	 * @param selectedTerritory
	 *            selected Territory object
	 * @param adjTerritory
	 *            adj Territory object
	 * @param gameConsole
	 *            gameConsole
	 */
	public void fortificationPhase(Territory territory, Territory adjTerritory, TextArea txtAreaMsg) {
		if(territory == null) {
    		CommonMapUtil.alertBox("Info", "Please select a territory", "Alert");
    		return;
    	}else if(adjTerritory == null) {
    		CommonMapUtil.alertBox("Info", "Please select a adjacent territory", "Alert");
    		return;
    	}else if(adjTerritory.getPlayer() != territory.getPlayer()) {
    		CommonMapUtil.alertBox("Info", "The Adjacent Territory does not belong to you.", "Alert");
    		return;
    	}
		int armyCount = CommonMapUtil.inputDialogueBoxForFortification();
		
		if(armyCount > 0) {
    		System.out.println("ArmyCount"+armyCount);
    		if(armyCount >= territory.getArmy()) {
    			CommonMapUtil.alertBox("Info", "The Army to be moved in fortification phase should be less than "
    					+ "existing army in territory.(e.g It can be maximum x-1, if x is the current army in territory.)", "Alert");
        		return;
    		}else {
    			territory.setArmy(territory.getArmy() - armyCount);
    			adjTerritory.setArmy(adjTerritory.getArmy() + armyCount);
    			GameUtils.addTextToLog("======Fortification Done ===========", txtAreaMsg);
    			setChanged();
				notifyObservers("Reinforcement");
    		}
    	}else {
    		CommonMapUtil.alertBox("Info", "Invalid Input. Number should be > 0.", "Alert");
    		return;
    	}
	}
	
	/* (non-Javadoc)
	 * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
	 */
	@Override
	public void update(Observable o, Object arg) {
		String str = (String) arg;

		if (str.equals("rollDiceComplete")) {
			DiceModel diceModel = (DiceModel) o;
			setTerritoryWon(diceModel.getNumOfTerritoriesWon());
			setChanged();
			notifyObservers("rollDiceComplete");
		}
		
	}
	
	public void endTurn() {
		setChanged();
		notifyObservers("Reinforcement");
	}

	/**
	 * Get Territory Won
	 * 
	 * @return territoryWon
	 */
	public int getTerritoryWon() {
		return territoryWon;
	}

	/**
	 * Set territory Won
	 * 
	 * @param territoryWon
	 *            the territoryWon to set
	 */
	public void setTerritoryWon(int territoryWon) {
		this.territoryWon = territoryWon;
	}
	
	/**
	 * Place Armies
	 * 
	 * @param currentPlayer
	 *            get current PlayerPlaying object
	 * @param selectedTerritoryList
	 *            get Selected Territory List for List View
	 * @param gamePlayerList
	 *            gamePlayer List
	 * @param txtAreaMsg gameConsole
	 */
	public void placeArmy(ListView<Territory> terrList, List<Player> playerList,
			TextArea txtAreaMsg) {
		int playerArmies = currentPlayer.getArmies();
		
		if (playerArmies > 0) {
			Territory territory = terrList.getSelectionModel().getSelectedItem();
			if (territory == null) {
				territory = terrList.getItems().get(0);
			}
			GameUtils.addTextToLog(currentPlayer.getName() + " === Assigned Armies to "+territory.getName()+"\n", txtAreaMsg);
			territory.setArmy(territory.getArmy() + 1);
			currentPlayer.setArmies(playerArmies - 1);
		}
		terrList.refresh();
		
		//if exhausted then call next phases
		boolean armiesExhausted = GameUtils.checkIfPlayersArmiesExhausted(playerList);
		if (armiesExhausted) {
			GameUtils.addTextToLog("===Setup Phase Completed!===\n", txtAreaMsg);
			setChanged();
			notifyObservers("ReinforcementFirst");
		} else {
			setChanged();
			notifyObservers("placeArmy");
		}
    	
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
	public boolean isFortificationPhasePossible(Map map, Player currentPlayer) {
		for (Continent continent : map.getContinents()) {
			for (Territory territory : continent.getTerritories()) {
				if (territory.getPlayer().equals(currentPlayer) && territory.getArmy() > 1) {
					for (Territory adjterritory : territory.getAdjacentTerritories()) {
						if (adjterritory.getPlayer().equals(currentPlayer)) {
							setChanged();
							notifyObservers("Fortification");
							return true;
						}

					}
				}
			}
		}
		setChanged();
		notifyObservers("NoFortificationMove");
		return false;
	}
}