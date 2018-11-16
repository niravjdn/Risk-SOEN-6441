/**
 * 
 */
package com.risk6441.strategy;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

import com.risk6441.entity.Player;
import com.risk6441.entity.Territory;
import com.risk6441.exception.InvalidGameActionException;
import com.risk6441.models.PlayerModel;

import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;

public interface IStrategy extends Serializable{
	
	public void reinforcementPhase(ObservableList<Territory> territoryList, Territory territory, TextArea txtAreaMsg,
			Player currentPlayer);
	
	
	void attackPhase(ListView<Territory> terrList, ListView<Territory> adjTerrList,
			PlayerModel playerModel, TextArea txtAreaMsg) throws InvalidGameActionException;
	

	default public List<Territory> getDefendingTerr(Territory terr) {
		List<Territory> defTerrList = terr.getAdjacentTerritories().stream()
				.filter(t -> (terr.getPlayer() != t.getPlayer())).collect(Collectors.toList());
		return defTerrList;
	}
}
