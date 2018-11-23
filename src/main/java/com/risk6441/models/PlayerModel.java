package com.risk6441.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import com.risk6441.config.Config;
import com.risk6441.entity.Card;
import com.risk6441.entity.Continent;
import com.risk6441.entity.Map;
import com.risk6441.entity.Player;
import com.risk6441.entity.Territory;
import com.risk6441.exception.InvalidGameActionException;
import com.risk6441.gameutils.GameUtils;
import com.risk6441.maputils.CommonMapUtil;
import com.risk6441.strategy.Benevolent;
import com.risk6441.strategy.Cheater;
import com.risk6441.strategy.Human;
import com.risk6441.strategy.IStrategy;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;

/**
 * @author Nirav
 *
 */
public class PlayerModel extends Observable implements Observer, Serializable {

	/**
	 * The serial ID
	 */
	private static final long serialVersionUID = 6224554451688312772L;

	private List<Player> playerList;

	/**
	 * @return the playerList
	 */
	public List<Player> getPlayerList() {
		return playerList;
	}

	/**
	 * @param playerList the playerList to set
	 */
	public void setPlayerList(List<Player> playerList) {
		this.playerList = playerList;
	}

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
	 * This method is to set the current player.
	 * 
	 * @param player Current player.
	 */
	public void setCurrentPlayer(Player player) {
		currentPlayer = player;
	}

	/**
	 * the @territoryWon
	 */
	private int NumOfterritoryWon;

	/**
	 * This method allocates armies to players and display log in textarea.
	 * 
	 * @param p       list of players
	 * @param txtArea textArea object
	 */
	public static void allocateArmiesToPlayers(List<Player> p, TextArea txtArea) {
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
	 * 
	 * @param noOfPlayers no of players to be created
	 * @param textArea    textArea object to append the log
	 * @return list of players after creating players
	 */
	public static List<Player> createPlayers(int noOfPlayers, TextArea textArea) {
		List<Player> players = new ArrayList<Player>();
		for (int i = 1; i <= noOfPlayers; i++) {
			String name = "Player" + i;
			players.add(new Player(i, name));
			GameUtils.addTextToLog(name + " created!\n", textArea);
		}
		return players;
	}

	/**
	 * This method counts the number of reinforcement armies for each player.
	 * 
	 * @param map           map object
	 * @param currentPlayer player object
	 * @return return the player object after assigning armies to it.
	 */
	public static Player countReinforcementArmies(Map map, Player currentPlayer) {
		int currentArmies = currentPlayer.getArmies();
		int territoryCount = currentPlayer.getAssignedTerritory().size();
		System.out.println("No Of Territories For Player : " + currentPlayer.getName() + " - " + territoryCount);
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
	 * 
	 * @param map           map object
	 * @param currentPlayer player object
	 * @return returns the list of continents that are owned by the player.
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
	 * @param territories territories List View
	 * @param txtAreaMsg  gameConsole text area
	 * 
	 * @return hasAValidMove true if player has valid move else false
	 */
	public boolean hasaAValidAttackMove(ListView<Territory> territories, TextArea txtAreaMsg) {
		boolean isValidAttackMove = currentPlayer.getStrategy().hasAValidAttackMove(territories);

		if (!isValidAttackMove) {
			GameUtils.addTextToLog("Player - " + currentPlayer.getName() + "\n");
			GameUtils.addTextToLog("---> No valid attack move avialble move to Fortification phase.\n", txtAreaMsg);
			GameUtils.addTextToLog("===Attack phase ended! === \n", txtAreaMsg);
			Platform.runLater(() -> {
				setChanged();
				notifyObservers("checkForValidFortificaion");
			});
			return isValidAttackMove;
		}

		return isValidAttackMove;
	}

	/**
	 * This method is to implement attack phase.
	 * 
	 * @param terrList    attacking territory.
	 * @param adjTerrList defending territory.
<<<<<<< HEAD
	 * @param txtAreaMsg
=======
	 * @param txtAreaMsg  text area message
>>>>>>> 5aef304c824196e633b021ce35b3dcb0d035bd29
	 * @throws InvalidGameActionException Throws invalid game exception.
	 */
	public void attackPhase(ListView<Territory> terrList, ListView<Territory> adjTerrList, TextArea txtAreaMsg)
			throws InvalidGameActionException {
		PlayerModel playerModel=  this;
		
		if(currentPlayer.getStrategy() instanceof Human) {
			if(playerList.size()==1) {
				setChanged();
				notifyObservers("disableGameControls");
				return;
			}
			currentPlayer.getStrategy().attackPhase(terrList, adjTerrList, this, playerList);
		}else {
			Thread backgroundThread = new Thread(new Runnable() {
				@Override
				public void run() {
					// TODO Auto-generated method stub
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					if (playerList.size() == 1) {

						Platform.runLater(() -> {
							setChanged();
							notifyObservers("disableGameControls");
						});
						return;
					}
					try {
						currentPlayer.getStrategy().attackPhase(terrList, adjTerrList, playerModel, playerList);
					} catch (InvalidGameActionException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					if ((currentPlayer.getStrategy() instanceof Cheater || currentPlayer.getStrategy() instanceof Benevolent)
							&& playerList.size() > 1) {
						GameUtils.addTextToLog(currentPlayer.getPlayerStrategy().toString()
								+ " startegy performed attack....going to fortification phase\n");
						
						Platform.runLater(() -> {
							setChanged();
							notifyObservers("skipAndGoToFortify");	
						});
						
					}
				}
			});
			backgroundThread.setDaemon(true);
			backgroundThread.start();
		}
	}

	/**
	 * Check if Attack Move is Valid.
	 * 
	 * @param attacking attacking Territory
	 * @param defending defending Territory
	 * @return isValidAttackMove if the attack move is valid
	 * @throws InvalidGameActionException invalid game exception.
	 */
	public boolean isValidAttackMove(Territory attacking, Territory defending) throws InvalidGameActionException {
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
	 * Check if any Player Lost the Game.
	 * 
	 * @param playerList playerPlaying List
	 * @return playerLost Player Object who lost the game
	 */
	public Player checkAndGetIfAnyPlayerLostTheGame(List<Player> playerList) {
		Player playerLost = null;
		for (Player player : playerList) {
			if (player.getAssignedTerritory().isEmpty()) {
				playerLost = player;
				GameUtils.addTextToLog(currentPlayer.getName() + " Got " + playerLost.getCardList().size()
						+ " cards from " + playerLost.getName() + "\n");
				System.out.println(currentPlayer.getName() + " Got " + playerLost.getCardList().size() + " cards from "
						+ playerLost.getName() + "\n");
				currentPlayer.getCardList().addAll(playerLost.getCardList());
			}
		}
		return playerLost;
	}

	/**
	 * Reinforcement Phase
	 * 
<<<<<<< HEAD
	 * @param territory      territory Object
	 * @param observableList
	 * @param txtAreaMsg     the Game Console
=======
	 * @param territory
	 *            territory Object
	 * @param terrList
	 * 			terrlist 
	 * @param txtAreaMsg
	 *            the txt area
>>>>>>> 5aef304c824196e633b021ce35b3dcb0d035bd29
	 */
	public void reinforcementPhase(Territory territory, ObservableList<Territory> terrList, TextArea txtAreaMsg) {
		// Run the task in a background thread
		if(currentPlayer.getStrategy() instanceof Human) {
			currentPlayer.getStrategy().reinforcementPhase(terrList, territory, currentPlayer);
			if (currentPlayer.getArmies() <= 0 && playerList.size() > 1) {
				GameUtils.addTextToLog("===Reinforcement phase Ended! ===\n", txtAreaMsg);
				Platform.runLater(() -> {
					setChanged();
					notifyObservers("Attack");
				});
			}
		}else {
			Thread backgroundThread = new Thread(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					currentPlayer.getStrategy().reinforcementPhase(terrList, territory, currentPlayer);
					if (currentPlayer.getArmies() <= 0 && playerList.size() > 1) {
						GameUtils.addTextToLog("===Reinforcement phase Ended! ===\n", txtAreaMsg);
						Platform.runLater(() -> {
							setChanged();
							notifyObservers("Attack");
						});
					}
				}
			});
			// Terminate the running thread if the application exits
			backgroundThread.setDaemon(true);
			// Start the thread
			backgroundThread.start();

		}
	}

	/**
	 * Fortification Phase.
	 * 
	 * @param territory    selected Territory object.
	 * @param adjTerritory Territory object.
	 * @param txtAreaMsg   gameConsole.
	 */
	public void fortificationPhase(ListView<Territory> territoryList, ListView<Territory> adjTerritoryList, Map map) {
		ArrayList<Territory> terrArList = new ArrayList<Territory>(territoryList.getItems());
		ArrayList<Territory> adjTerrArList = new ArrayList<Territory>(adjTerritoryList.getItems());
		
		if(currentPlayer.getStrategy() instanceof Human) {
			boolean isFortificationDone = currentPlayer.getStrategy().fortificationPhase(territoryList, adjTerritoryList,currentPlayer, map,
					terrArList, adjTerrArList);

			if (isFortificationDone  && playerList.size() > 1) {
				setChanged();
				notifyObservers("Reinforcement");
			}
		}else {
			Thread backgroundThread = new Thread(new Runnable() {
				@Override
				public void run() {
					// TODO Auto-generated method stub
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					boolean isFortificationDone = currentPlayer.getStrategy().fortificationPhase(territoryList,
							adjTerritoryList, currentPlayer, map, terrArList, adjTerrArList);

					if (isFortificationDone && playerList.size() > 1) {
						Platform.runLater(() -> {
							setChanged();
							notifyObservers("Reinforcement");
						});
					}
				}
			});
			backgroundThread.setDaemon(true);
			backgroundThread.start();
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
		if (str.equals("rollDiceComplete")) {
			DiceModel diceModel = (DiceModel) o;
			setNumOfTerritoryWon(diceModel.getNumOfTerritoriesWon());
			Platform.runLater(()->{
				setChanged();
				notifyObservers("rollDiceComplete");
			});
		} else {
			if (str.equals("oneAttackDoneForCheater")) {
			
				Platform.runLater(()->{
					setChanged();
					notifyObservers("oneAttackDoneForCheater");
				});
				
			}
		}

	}

	public void endTurn() {
		Platform.runLater(()->{
			setChanged();
			notifyObservers("Reinforcement");
		});
		
	}

	/**
	 * This methods returns the number of territory won by the player in a turn.
	 * 
	 * @return NumOfterritoryWon Number of Territory won by player in a turn
	 */
	public int getNumOfTerritoryWon() {
		return NumOfterritoryWon;
	}

	/**
	 * This method sets the number of territory won by the player.
	 * 
	 * @param territoryWon the territoryWon to set
	 */
	public void setNumOfTerritoryWon(int territoryWon) {
		this.NumOfterritoryWon = territoryWon;
	}

	/**
	 * This method is to place Armies in territories.
	 * 
	 * @param terrList   The list of territories.
	 * @param playerList The players in the game.
	 * @param txtAreaMsg The area where the message is to be displayed.
	 */
	public void placeArmy(ListView<Territory> terrList, List<Player> playerList, TextArea txtAreaMsg) {
		if (currentPlayer.getStrategy() instanceof Human) {
			int playerArmies = currentPlayer.getArmies();
			if (playerArmies > 0) {
				Territory territory = terrList.getSelectionModel().getSelectedItem();
				if (territory == null) {
					territory = terrList.getItems().get(0);
				}
				GameUtils.addTextToLog(
						currentPlayer.getName() + " === Assigned Armies to " + territory.getName() + "\n", txtAreaMsg);
				territory.setArmy(territory.getArmy() + 1);
				currentPlayer.setArmies(playerArmies - 1);
			}
		} else {
			assignArmiesToTerr(txtAreaMsg);
		}
		terrList.refresh();

		// if exhausted then call next phases
		if (checkIfPlayersArmiesExhausted(playerList)) {
			GameUtils.addTextToLog("=== Place Army Phase Completed ===\n", txtAreaMsg);
			GameUtils.addTextToLog("=== Start up Phase Completed ===\n", txtAreaMsg);
			setChanged();
			notifyObservers("ReinforcementFirst");
		} else {
			setChanged();
			notifyObservers("placeArmy");
		}

	}

	/**
	 * This method assign armies to player's territories randmomly for all strategy
	 * except human strategy
	 * 
	 * @param txtAreaMsg The area where the message is to be displayed.
	 */
	private void assignArmiesToTerr(TextArea txtAreaMsg) {
		if (currentPlayer.getArmies() > 0) {
			Territory terr = currentPlayer.getAssignedTerritory()
					.get(CommonMapUtil.getRandomNo(currentPlayer.getAssignedTerritory().size() - 1));
			GameUtils.addTextToLog(currentPlayer.getName() + " === Assigned Armies to " + terr.getName() + "\n",
					txtAreaMsg);
			terr.setArmy(terr.getArmy() + 1);
			currentPlayer.setArmies(currentPlayer.getArmies() - 1);
		}
	}

	/**
	 * This method checks if players armies is exhausted.
	 * 
	 * @param players player object
	 * @return returns true if player has exhausted the armies
	 */
	public static boolean checkIfPlayersArmiesExhausted(List<Player> players) {
		for (Player player : players) {
			if (player.getArmies() != 0) {
				return false;
			}
		}
		return true;
	}

	/**
	 * This method checks if the fortification phase is valid or not.
	 * 
	 * @param map           map object
	 * @param currentPlayer current player
	 * @return return if fortification phase is valid
	 */
	public boolean isFortificationPhasePossible(Map map, Player currentPlayer) {
		for (Continent continent : map.getContinents()) {
			for (Territory territory : continent.getTerritories()) {
				if (territory.getPlayer().equals(currentPlayer) && territory.getArmy() > 1) {
					for (Territory adjterritory : territory.getAdjacentTerritories()) {
						if (adjterritory.getPlayer().equals(currentPlayer)) {
							Platform.runLater(()->{
								setChanged();
								notifyObservers("Fortification");
							});
							return true;
						}

					}
				}
			}
		}
		Platform.runLater(()->{
			setChanged();
			notifyObservers("NoFortification");
		});
		
		return false;
	}

	/**
	 * This method is used to exchange cards for army.
	 * 
	 * @param selectedCardsByThePlayer List of cards selected by the current player.
	 * @param txtAreaMsg               The area where the message has to be
	 *                                 displayed.
	 */
	public void tradeCardsAndGetArmy(List<Card> selectedCardsByThePlayer, TextArea txtAreaMsg) {
		currentPlayer.setArmies(currentPlayer.getArmies() + (5 * currentPlayer.getNumeberOfTimeCardsExchanged()));
		GameUtils.addTextToLog(currentPlayer.getName() + " exchanged 3 cards for the army "
				+ (5 * currentPlayer.getNumeberOfTimeCardsExchanged() + "\n"), txtAreaMsg);
		for (Territory t : currentPlayer.getAssignedTerritory()) {
			for (Card card : selectedCardsByThePlayer) {
				if (t.equals(card.getTerritoryToWhichCardBelong())) {
					t.setArmy(t.getArmy() + 2);
					GameUtils.addTextToLog(
							currentPlayer.getName() + " got 2 extra armies on the " + t.getName() + ".\n", txtAreaMsg);
					break;
				}
			}
		}
	}

	/**
	 * This method is invoked if there can't be another attack for the player.
	 */
	public void noMoreAttack() {
		if (playerList.size() <= 1)
			return;
		Platform.runLater(() -> {
			setChanged();
			notifyObservers("noMoreAttack");
		});
		
	}
}
