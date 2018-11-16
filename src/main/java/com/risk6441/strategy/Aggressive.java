/**
 * 
 */
package com.risk6441.strategy;

import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.risk6441.controller.DiceController;
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
				return Integer.valueOf(getDefendingTerr(t1).size()).compareTo(getDefendingTerr(t2).size());
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
		attackingTerr = getAttackingTerritory(terrList.getItems());
		List<Territory> defendingTerrList = getDefendingTerr(attackingTerr);
		for(Territory defTerr : defendingTerrList) {
			if (attackingTerr.getArmy() > 1) {
				attack(attackingTerr, defTerr, playerModel, txtAreaMsg);
				break;
			}
		}
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
		
		final Stage stage = new Stage();
		stage.setTitle("Attack Window");
		
		DiceController diceController = new DiceController(diceModel, this);

		FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("diceview.fxml"));
		loader.setController(diceController);
		
		Parent root = null;
		try {
			root = (Parent) loader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}

		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
		
		diceController.loadDiceControllerForStrategy();
		
	}

	/**
	 * @param terrList2 
	 * @return
	 */
	private Territory getAttackingTerritory(ObservableList<Territory> terrList) {
		List<Territory> sortedListFromMaxAdjacent = getMaxOppTerr(terrList);
		if(attackingTerr == null || (attackingTerr.getArmy() <= 1 || getDefendingTerr(attackingTerr).size() == 0)) {
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
}
