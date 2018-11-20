package com.risk6441.strategy;

import java.io.IOException;
import java.util.List;

import com.risk6441.controller.DiceController;
import com.risk6441.entity.Map;
import com.risk6441.entity.Player;
import com.risk6441.entity.Territory;
import com.risk6441.exception.InvalidGameActionException;
import com.risk6441.gameutils.GameUtils;
import com.risk6441.maputils.CommonMapUtil;
import com.risk6441.models.DiceModel;
import com.risk6441.models.PlayerModel;

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
public class Human implements IStrategy {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.risk6441.strategy.IStrategy#reinforcementPhase(javafx.collections.
	 * ObservableList, com.risk6441.entity.Territory, javafx.scene.control.TextArea,
	 * com.risk6441.entity.Player)
	 */
	@Override
	public void reinforcementPhase(ObservableList<Territory> territoryList, Territory territory,
			Player currentPlayer) {

		if (currentPlayer.getArmies() > 0) {
			if (territory == null) {
				CommonMapUtil.alertBox("infor", "Please Select a territory.", "Alert");
				return;
			}

			int getArmy = CommonMapUtil.inputDialogueBoxForRenforcement();

			if (getArmy > 0) {
				if (getArmy > currentPlayer.getArmies()) {
					CommonMapUtil.alertBox("Info",
							"The Army to be moved in reinforce phase should be less than army you have.", "Alert");
					return;
				} else {
					territory.setArmy(territory.getArmy() + getArmy);
					currentPlayer.setArmies(currentPlayer.getArmies() - getArmy);
					CommonMapUtil.enableOrDisableSave(false);
					GameUtils.addTextToLog("===" + getArmy + " assigned to : === \n" + territory + "  -- Player "
							+ currentPlayer.getName() + "\n");
					GameUtils.addTextToLog("======Reinforce Phase Completed. ===========\n");
				}
			} else {
				CommonMapUtil.alertBox("Info", "Invalid Input. Number should be > 0.", "Alert");
				return;
			}
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
	public void attackPhase(ListView<Territory> terrList, ListView<Territory> adjTerrList, PlayerModel playerModel,
			List<Player> playerList) throws InvalidGameActionException {
		Territory attackingTerritory = terrList.getSelectionModel().getSelectedItem();
		Territory defendingTerritory = adjTerrList.getSelectionModel().getSelectedItem();
		if (attackingTerritory != null && defendingTerritory != null) {
			playerModel.isValidAttackMove(attackingTerritory, defendingTerritory);

			DiceModel diceModel = new DiceModel(attackingTerritory, defendingTerritory);
			diceModel.addObserver(playerModel);
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
		} else {
			throw new InvalidGameActionException("Please choose both attacking and defending territory.");
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.risk6441.strategy.IStrategy#fortificationPhase(javafx.scene.control.
	 * ListView, javafx.scene.control.ListView, javafx.scene.control.TextArea,
	 * com.risk6441.entity.Player)
	 */
	@Override
	public boolean fortificationPhase(ListView<Territory> terrList, ListView<Territory> adjTerrList,
			Player currentPlayer, Map map) {
		Territory territory = terrList.getSelectionModel().getSelectedItem();
		Territory adjTerritory = adjTerrList.getSelectionModel().getSelectedItem();

		if (territory == null) {
			CommonMapUtil.alertBox("Info", "Please select a territory", "Alert");
			return false;
		} else if (adjTerritory == null) {
			CommonMapUtil.alertBox("Info", "Please select a adjacent territory", "Alert");
			return false;
		} else if (adjTerritory.getPlayer() != territory.getPlayer()) {
			CommonMapUtil.alertBox("Info", "The Adjacent Territory does not belong to you.", "Alert");
			return false;
		}

		int armyCount = CommonMapUtil.inputDialogueBoxForFortification();

		if (armyCount > 0) {
			System.out.println("ArmyCount" + armyCount);
			if (armyCount >= territory.getArmy()) {
				CommonMapUtil.alertBox("Info", "The Army to be moved in fortification phase should be less than "
						+ "existing army in territory.(e.g It can be maximum x-1, if x is the current army in territory.)",
						"Alert");
				return false;
			} else {
				territory.setArmy(territory.getArmy() - armyCount);
				adjTerritory.setArmy(adjTerritory.getArmy() + armyCount);
				GameUtils.addTextToLog(
						armyCount + " armies fortified on territory: " + adjTerritory.getName() + " From "+territory.getName()+"\n");
				GameUtils.addTextToLog("======Fortification Done ===========\n");
				return true;
			}
		} else {
			CommonMapUtil.alertBox("Info", "Invalid Input. Number should be > 0.", "Alert");
			return false;
		}
	}

}
