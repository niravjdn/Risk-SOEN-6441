/**
 * 
 */
package com.risk6441.strategy;

import com.risk6441.entity.Map;
import com.risk6441.entity.Player;
import com.risk6441.entity.Territory;
import com.risk6441.exception.InvalidGameActionException;
import com.risk6441.gameutils.GameUtils;
import com.risk6441.maputils.CommonMapUtil;
import com.risk6441.models.PlayerModel;

import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;

/**
 * @author Nirav
 *
 */
public class Random implements IStrategy {

	/* (non-Javadoc)
	 * @see com.risk6441.strategy.IStrategy#reinforcementPhase(javafx.collections.ObservableList, com.risk6441.entity.Territory, javafx.scene.control.TextArea, com.risk6441.entity.Player)
	 */
	@Override
	public void reinforcementPhase(ObservableList<Territory> territoryList, Territory territory, TextArea txtAreaMsg,
			Player currentPlayer) {
		int army = currentPlayer.getArmies() - CommonMapUtil.getRandomNo(currentPlayer.getArmies()-1);
		while (currentPlayer.getArmies() > 0) {
			Territory randomTerr = territoryList.get(CommonMapUtil.getRandomNo(territoryList.size()-1));
			randomTerr.setArmy(randomTerr.getArmy() + army);
			currentPlayer.setArmies(currentPlayer.getArmies()-army);
			//currentPlayer.setArmies(0);
			GameUtils.addTextToLog(army+" assigned to :"+randomTerr.getName()+"  by "+currentPlayer.getName()+"\n", txtAreaMsg);
			army = currentPlayer.getArmies() - CommonMapUtil.getRandomNo(currentPlayer.getArmies());
			if(army==0)
				army=1;
		}

	}

	/* (non-Javadoc)
	 * @see com.risk6441.strategy.IStrategy#attackPhase(javafx.scene.control.ListView, javafx.scene.control.ListView, com.risk6441.models.PlayerModel, javafx.scene.control.TextArea)
	 */
	@Override
	public void attackPhase(ListView<Territory> terrList, ListView<Territory> adjTerrList, PlayerModel playerModel,
			TextArea txtAreaMsg) throws InvalidGameActionException {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see com.risk6441.strategy.IStrategy#fortificationPhase(javafx.scene.control.ListView, javafx.scene.control.ListView, javafx.scene.control.TextArea, com.risk6441.entity.Player, com.risk6441.entity.Map)
	 */
	@Override
	public boolean fortificationPhase(ListView<Territory> selectedTerritory, ListView<Territory> adjTerritory,
			 Player currentPlayer, Map map) {
		// TODO Auto-generated method stub
		return false;
	}

	
}
