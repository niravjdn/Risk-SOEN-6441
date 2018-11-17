/**
 * 
 */
package com.risk6441.strategy;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

import com.risk6441.entity.Map;
import com.risk6441.entity.Player;
import com.risk6441.entity.Territory;
import com.risk6441.exception.InvalidGameActionException;
import com.risk6441.gameutils.GameUtils;
import com.risk6441.models.PlayerModel;

import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;

public interface IStrategy extends Serializable{
	
	public void reinforcementPhase(ObservableList<Territory> territoryList, Territory territory, TextArea txtAreaMsg,
			Player currentPlayer);
	
	
	void attackPhase(ListView<Territory> terrList, ListView<Territory> adjTerrList,
			PlayerModel playerModel, TextArea txtAreaMsg) throws InvalidGameActionException;
	

	boolean fortificationPhase(ListView<Territory> selectedTerritory, ListView<Territory> adjTerritory, Player currentPlayer, Map map);
	
	

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
	default public boolean hasAValidAttackMove(ListView<Territory> territories, TextArea gameConsole) {
		boolean isValidAttackMove = false;
		for (Territory territory : territories.getItems()) {
			if (territory.getArmy() > 1 && getDefendingTerr(territory).size() > 0) {
				isValidAttackMove = true;
			}
		}
		if (!isValidAttackMove) {
			GameUtils.addTextToLog("No valid attack move avialble move to Fortification phase.\n", gameConsole);
			GameUtils.addTextToLog("===Attack phase ended! === \n", gameConsole);
			return isValidAttackMove;
		}
		return isValidAttackMove;
	}
	default public List<Territory> getDefendingTerr(Territory terr) {
		List<Territory> defTerrList = terr.getAdjacentTerritories().stream()
				.filter(t -> (terr.getPlayer() != t.getPlayer())).collect(Collectors.toList());
		return defTerrList;
	}
}
