/**
 * 
 */
package com.risk6441.strategy;

import com.risk6441.entity.Player;
import com.risk6441.entity.Territory;
import com.risk6441.gameutils.GameUtils;

import javafx.collections.ObservableList;
import javafx.scene.control.TextArea;

/**
 * @author Nirav
 *
 */
public class Cheater implements IStrategy {

	/* (non-Javadoc)
	 * @see com.risk6441.strategy.IStrategy#reinforcementPhase(javafx.collections.ObservableList, com.risk6441.entity.Territory, javafx.scene.control.TextArea, com.risk6441.entity.Player)
	 */
	@Override
	public void reinforcementPhase(ObservableList<Territory> territoryList, Territory territory, TextArea txtAreaMsg,
			Player currentPlayer) {
		
		for(Territory terr : territoryList) {
			terr.setArmy(terr.getArmy() * 2);
			GameUtils.addTextToLog("Armies Doubled to "+terr.getArmy()+ " on "+ terr.getName(), txtAreaMsg);
		}
		currentPlayer.setArmies(0);
	}

}
