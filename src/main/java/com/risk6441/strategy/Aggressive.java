/**
 * 
 */
package com.risk6441.strategy;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.risk6441.controller.DiceController;
import com.risk6441.entity.Map;
import com.risk6441.entity.Player;
import com.risk6441.entity.Territory;
import com.risk6441.exception.InvalidGameActionException;
import com.risk6441.gameutils.GameUtils;
import com.risk6441.models.DiceModel;
import com.risk6441.models.PlayerModel;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
public class Aggressive implements IStrategy {

	private Territory attackingTerr;
	private PlayerModel playerModel;
	private Player currentPlayer = null;
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.risk6441.strategy.IStrategy#reinforcementPhase(javafx.collections.
	 * ObservableList, com.risk6441.entity.Territory, javafx.scene.control.TextArea,
	 * com.risk6441.entity.Player)
	 */
	@Override
	public void reinforcementPhase(ObservableList<Territory> territoryList, Territory territory, TextArea txtAreaMsg,
			Player currentPlayer) {
		List<Territory> maximumOponentTerr = getMaxOppTerr(territoryList);
		territory = maximumOponentTerr.get(0);
		int army = currentPlayer.getArmies();
		territory.setArmy(territory.getArmy() + army);
		currentPlayer.setArmies(0);
		GameUtils.addTextToLog("==="+army+" assigned to : === \n"+territory+"  -- Player "+currentPlayer.getName()+"\n", txtAreaMsg);
		GameUtils.addTextToLog("======Reinforce Phase Completed. ===========\n", txtAreaMsg);

	}

	/**
	 * @param territoryList list of territories which belong to player
	 * @return return list of territory in sorted order .... with minOpp Territory
	 *         at Top
	 */
	private List<Territory> getMaxOppTerr(ObservableList<Territory> territoryList) {
		Collections.sort(territoryList, new Comparator<Territory>() {
			/*
			 * (non-Javadoc)
			 * 
			 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
			 */
			@Override
			public int compare(Territory t1, Territory t2) {
				return Integer.valueOf(getDefendingTerr(t2).size()).compareTo(getDefendingTerr(t1).size());
			}
		});
		return territoryList;
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
	public void attackPhase(ListView<Territory> terrList, ListView<Territory> adjTerrList, PlayerModel playerModel,
  		TextArea txtAreaMsg) throws InvalidGameActionException {
		this.playerModel = playerModel;
		attackingTerr = getAttackingTerritory(terrList.getItems());
		List<Territory> defendingTerrList = getDefendingTerr(attackingTerr);
		for(Territory defTerr : defendingTerrList) {
			if (attackingTerr.getArmy() > 1) {
				GameUtils.addTextToLog(attackingTerr.getName()+ "("+attackingTerr.getPlayer().getName()+") attacking on "+defTerr+"("+defTerr.getPlayer().getName()+")\n");
				attack(attackingTerr, defTerr, playerModel, txtAreaMsg);
				break;
			}else {
				System.out.println("No More Attack");
				goToNoMoreAttack();
			}
		}
	}

	/**
	 * 
	 */
	private void goToNoMoreAttack() {
		playerModel.noMoreAttack();
	}

	/**
	 * @param attackingTerr
	 * @param defTerr
	 * @param playerModel
	 * @param txtAreaMsg
	 */
	private void attack(Territory attackingTerr, Territory defTerr, PlayerModel playerModel, TextArea txtAreaMsg) {
		DiceModel diceModel = new DiceModel(attackingTerr, defTerr);
		if (playerModel != null) {
			diceModel.addObserver(playerModel);
		}
		
		DiceController diceController = new DiceController(diceModel, this);
		diceController.loadDiceControllerForStrategy();
		
	}

	/**
	 * @param terrList
	 * @return
	 */
	private Territory getAttackingTerritory(ObservableList<Territory> terrList) {
		List<Territory> sortedListFromMaxAdjacent = getMaxOppTerr(terrList);
		if(attackingTerr == null || (!attackingTerr.equals(sortedListFromMaxAdjacent.get(0)))) {
			for (Territory t : sortedListFromMaxAdjacent) {
				if (t.getArmy() > 1) {
					attackingTerr = t;
					break;
				}
			}
		}
		System.out.println(attackingTerr+"Dekho");
		return attackingTerr;
	}

	/* (non-Javadoc)
	 * @see com.risk6441.strategy.IStrategy#fortificationPhase(javafx.scene.control.ListView, javafx.scene.control.ListView, javafx.scene.control.TextArea, com.risk6441.entity.Player)
	 */
	@Override
	public boolean fortificationPhase(ListView<Territory> terrList, ListView<Territory> adjTerritory,
			Player currentPlayer, Map map) {
		this.currentPlayer = currentPlayer;
		List<Territory> sortedMaxAdjTerr = getMaxOppTerr(terrList.getItems());
		for (Territory territory : sortedMaxAdjTerr) {
			if (territory.getArmy() > 1) {

				List<Territory> reachableTerrList = new ArrayList<Territory>();
				List<Territory> allTerr = GameUtils.getTerritoryList(map);
				
				this.bfsTerritory(territory,reachableTerrList,territory);
				
				for(Territory t : allTerr) {
					t.setProcessed(false);
				}	
				
				System.out.println("Reachable Terr "+reachableTerrList.size());
				if (reachableTerrList.size() != 0) {
					Collections.sort(reachableTerrList, new Comparator<Territory>() {
						@Override
						public int compare(Territory o1, Territory o2) {
							return Integer.valueOf(o2.getArmy()).compareTo(Integer.valueOf(o1.getArmy()));
						}
					});
					GameUtils.addTextToLog((territory.getArmy()-1)+" Armies Moved From "+territory.getName()+" to "+reachableTerrList.get(0).getName());
					reachableTerrList.get(0)
							.setArmy(reachableTerrList.get(0).getArmy() + territory.getArmy() - 1);
					territory.setArmy(1);
					return true;
				}
			}
		}
		
		return false;
	}
	
	public  void bfsTerritory(Territory territory, List<Territory> reachableTerrList, Territory root) {

		if(territory.isProcessed() == true) {
			return;
		}
		
		territory.setProcessed(true);
		if(!territory.equals(root)){
				reachableTerrList.add(territory);
			}
		for(Territory t : territory.getAdjacentTerritories()){
			if(t.isProcessed() == false && t.getPlayer().equals(currentPlayer)){
				bfsTerritory(t,reachableTerrList,root);
			}
		}		
	}
	
	
	public boolean playerHasAValidAttackMove(ListView<Territory> territories, TextArea gameConsole) {
		attackingTerr = getAttackingTerritory(territories.getItems());
		List<Territory> defendingTerritoryList = getDefendingTerr(attackingTerr);
		if (defendingTerritoryList.size() > 0 && attackingTerr.getArmy() > 1) {
			return true;
		}
		return false;
	}
}
