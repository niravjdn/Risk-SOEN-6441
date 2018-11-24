package com.risk6441.strategy;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.risk6441.config.Config;
import com.risk6441.controller.DiceController;
import com.risk6441.entity.Map;
import com.risk6441.entity.Player;
import com.risk6441.entity.Territory;
import com.risk6441.exception.InvalidGameActionException;
import com.risk6441.gameutils.GameUtils;
import com.risk6441.models.DiceModel;
import com.risk6441.models.PlayerModel;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;

/**
 * @author Nirav
 *
 */
public class Aggressive implements IStrategy {

	private Territory attackingTerr;
	private PlayerModel playerModel;
	private DiceModel diceModel;
	private int numOfAttack = 0;
	
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.risk6441.strategy.IStrategy#reinforcementPhase(javafx.collections.
	 * ObservableList, com.risk6441.entity.Territory, javafx.scene.control.TextArea,
	 * com.risk6441.entity.Player)
	 */
	@Override
	public void reinforcementPhase(ObservableList<Territory> territoryList1, Territory territory, Player currentPlayer,
			ArrayList<Territory> terrArList,ArrayList<Territory> adjTerrArList) {
		System.out.println(currentPlayer.getName() + " - " + terrArList.size() + " - Terr List Size");
		numOfAttack = 0;
		if(attackingTerr == null) { //first reinforce
			attackingTerr = sortAndGetMaxDefendingTerr(terrArList).get(0);
		}else {
			attackingTerr = getAttackingTerritory(terrArList);	
		}
		
		
		int army = currentPlayer.getArmies();
		attackingTerr.setArmy(attackingTerr.getArmy() + army);
		currentPlayer.setArmies(0);
		GameUtils.addTextToLog("===" + army + " assigned to : === \n" + attackingTerr + "  -- Player "
				+ currentPlayer.getName() + "\n");
		System.out.println("===" + army + " assigned to : === \n" + attackingTerr + "  -- Player "
				+ currentPlayer.getName() + "\n");
		GameUtils.addTextToLog("======Reinforcement Phase Completed. ===========\n");
		
		for(Territory t : getDefendingTerr(attackingTerr)) {
			System.out.println(t.getName()+ " adjacent");
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.risk6441.strategy.IStrategy#attackPhase(javafx.scene.control.ListView,
	 * javafx.scene.control.ListView, com.risk6441.models.PlayerModel,
	 * javafx.scene.control.TextArea)
	 */
	@Override
	public void attackPhase(ListView<Territory> terrList1, ListView<Territory> adjTerrList1, PlayerModel playerModel,
			List<Player> playerList,ArrayList<Territory> terrArList,ArrayList<Territory> adjTerrArList) throws InvalidGameActionException {
		System.out.println("Inside attackpahse aggressive");
		this.playerModel = playerModel;
		this.diceModel = null;

		if(attackingTerr != null)
			System.out.println(attackingTerr.getArmy() > 1 );
		
		System.out.println(playerList.size() > 1);
		System.out.println(attackingTerr.getArmy() > 1 && playerList.size() > 1);
		
		while (attackingTerr.getArmy() > 1  && playerList.size() > 1) {
			System.out.println("Playerlist size " + playerList.size());
			List<Territory> defendingTerrList = getDefendingTerr(attackingTerr);
			if (defendingTerrList.size() == 0) {
				System.out.println("Defending Terr Size 0");
				break;
			} else {
				System.out.println("Inside else attackphase");
				for (Territory defTerr : defendingTerrList) {
					GameUtils.addTextToLog("Army on defending " + defTerr.getArmy() + "\n");
					GameUtils.addTextToLog(attackingTerr.getName() + "(" + attackingTerr.getPlayer().getName()
							+ ") attacking on " + defTerr.getName() + "(" + defTerr.getPlayer().getName() + ")\n");
					attack(attackingTerr, defTerr, playerModel);
					if(defTerr.getPlayer().equals(attackingTerr.getPlayer())) {
						terrArList.add(defTerr);
					}
					if(playerList.size()>1  && (!hasAValidAttackMove(terrArList))) {
						//don't call no more attack if attack is not valid, this check will itself redirect to fortification
						playerModel.hasaAValidAttackMove(terrArList);
						return;
					}
					break;
				}
			}

			if (playerList.size() == 1) {
				break;
			}

			attackingTerr = getAttackingTerritory(terrArList);
		}

		goToNoMoreAttack();
	}

	/**
	 * This method ends the current player's attack.
	 */
	private void goToNoMoreAttack() {
		playerModel.noMoreAttack();
	}

	/**
	 * This method perform attacks from attacking territory to defending territory.
	 * 
	 * @param attackingTerr Attacking Territory
	 * @param defTerr       Defending Territory
	 * @param playerModel   object of {@link PlayerModel}
	 */
	private void attack(Territory attackingTerr, Territory defTerr, PlayerModel playerModel) {
		this.playerModel = playerModel;
		diceModel = new DiceModel(attackingTerr, defTerr);
		if (playerModel != null) {
			diceModel.addObserver(playerModel);
		}
		numOfAttack++;
		DiceController diceController = new DiceController(diceModel, this);
		diceController.loadDiceControllerForStrategy();

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.risk6441.strategy.IStrategy#fortificationPhase(javafx.scene.control.
	 * ListView, javafx.scene.control.ListView, javafx.scene.control.TextArea,
	 * com.risk6441.entity.Player)
	 */
	@Override
	public boolean fortificationPhase(ListView<Territory> terrList1, ListView<Territory> adjTerritory2,
			Player currentPlayer, Map map,ArrayList<Territory> terrArList,ArrayList<Territory> adjTerrArList) {
		System.out.println(terrArList.size() + "------ size");
		if (numOfAttack<1) {
			System.out.println("Territory Won 0");
			List<Territory> sortedMaxarmyTerr = sortAndGetStrongestTerr1(terrArList);
			for (Territory strongTerr : sortedMaxarmyTerr) {
				System.out.println("Strong Terr "+strongTerr);
				if (strongTerr.getArmy() < 2) {
					return false;
				}
				List<Territory> adjTerrList = GameUtils.getAdjTerrForFortifiction(strongTerr, map, currentPlayer);
				adjTerrList = sortAndGetMaxDefendingTerr((ArrayList<Territory>) adjTerrList);

				for (Territory targetTerr : adjTerrList) {
					GameUtils.addTextToLog((strongTerr.getArmy() - 1) + " Armies Moved From " + strongTerr.getName()
							+ " to " + targetTerr.getName()+"\n");
					System.out.println((strongTerr.getArmy() - 1) + " Armies Moved From " + strongTerr.getName()
							+ " to " + targetTerr.getName());
					targetTerr.setArmy(targetTerr.getArmy() + strongTerr.getArmy() - 1);
					strongTerr.setArmy(1);
					try {
						Thread.sleep(2000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					return true;
				}
			}
		}

		List<Territory> sortedMaxArmyTerr = sortAndGetStrongestTerr1(terrArList);
		if (sortedMaxArmyTerr.get(0).equals(sortAndGetMaxDefendingTerr1(terrArList).get(0))) {
			// don't do fortification
			GameUtils.addTextToLog("Not doing fortification because it is on best possible territory"+"\n");
			return true;
		}
		
		for (Territory targetTerr : sortedMaxArmyTerr) {
			List<Territory> reachableTerrList = new ArrayList<Territory>();
			reachableTerrList = GameUtils.getAdjTerrForFortifiction(targetTerr, map, currentPlayer);
			System.out.println("Reachable Terr of " + targetTerr + " - " + reachableTerrList.size());
			if (reachableTerrList.size() != 0) {
				reachableTerrList = sortAndGetStrongestTerr1((ArrayList<Territory>) reachableTerrList);
				for (Territory fromTerr : reachableTerrList) {
					if (fromTerr.getArmy() > 1) {
						GameUtils.addTextToLog((fromTerr.getArmy() - 1) + " Armies Moved From " + fromTerr.getName()
								+ " to " + targetTerr.getName());
						System.out.println((fromTerr.getArmy() - 1) + " Armies Moved From " + fromTerr.getName()
								+ " to " + targetTerr.getName());
						targetTerr.setArmy(targetTerr.getArmy() + fromTerr.getArmy() - 1);
						fromTerr.setArmy(1);
						try {
							Thread.sleep(2000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						return true;
					}
				}
			}
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.risk6441.strategy.IStrategy#hasAValidAttackMove(javafx.scene.control.
	 * ListView)
	 */
	@Override
	public boolean hasAValidAttackMove(ArrayList<Territory> territories) {
		attackingTerr = getAttackingTerritory(territories);
		List<Territory> defendingTerritoryList = getDefendingTerr(attackingTerr);
		if (defendingTerritoryList.size() > 0 && attackingTerr.getArmy() > 1) {
			return true;
		}
		return false;
	}

	private Territory getAttackingTerritory(ArrayList<Territory> terrList) {
		List<Territory> sortedListFromMaxAdjacent = sortAndGetStrongestTerr1(terrList);
		if (attackingTerr == null || (!attackingTerr.equals(sortedListFromMaxAdjacent.get(0)))
				|| attackingTerr.getArmy() < 2) {
			for (Territory t : sortedListFromMaxAdjacent) {
				if (t.getArmy() > 1) {
					attackingTerr = t;
					break;
				}
			}
		}
		System.out.println(attackingTerr + "Dekho");
		return attackingTerr;
	}

	/**
	 * This method sorts the territory list from max army to least army
	 * 
	 * @param territoryList list of territories which belong to player
	 * @return return list of territory in sorted order .... from max army to least
	 *         army
	 */
	private List<Territory> sortAndGetStrongestTerr1(ArrayList<Territory> territoryList) {
		Collections.sort(territoryList, new Comparator<Territory>() {
			/*
			 * (non-Javadoc)
			 * 
			 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
			 */
			@Override
			public int compare(Territory t1, Territory t2) {
				return Integer.valueOf(t2.getArmy()).compareTo(t1.getArmy());
			}
		});
		return territoryList;
	}

	/**
	 * This methods sorts the territory list from max territories with max defending
	 * territory to least defending territory
	 * 
	 * @param territoryList list of territories which belong to player
	 * @return return list of territory in sorted order .... with max Opponent
	 *         Territories at Top
	 */
	private List<Territory> sortAndGetMaxDefendingTerr(ArrayList<Territory> territoryList) {
		Collections.sort(territoryList, new Comparator<Territory>() {
			/*
			 * (non-Javadoc)
			 * 
			 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
			 */
			@Override
			public int compare(Territory t1, Territory t2) {
				return Integer.valueOf(getDefendingTerr(t2).size())- (getDefendingTerr(t1).size());
			}
		});
		return territoryList;
	}
	
	private List<Territory> sortAndGetMaxDefendingTerr1(ArrayList<Territory> territoryList) {
		Collections.sort(territoryList, new Comparator<Territory>() {
			/*
			 * (non-Javadoc)
			 * 
			 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
			 */
			@Override
			public int compare(Territory t1, Territory t2) {
				return Integer.valueOf(getDefendingTerr(t2).size())- (getDefendingTerr(t1).size());
			}
		});
		return territoryList;
	}
	
}
