/**
 * 
 */
package com.risk6441.strategy;

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
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;

/**
 * @author Nirav
 *
 */
public class Random implements IStrategy {

	private Territory attackingTerr;
	private PlayerModel playerModel;
	private Player currentPlayer = null;
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
			TextArea txtAreaMsg, List<Player> playerList) throws InvalidGameActionException {
		this.playerModel = playerModel;
		attackingTerr = getRandomTerritory(terrList.getItems());
		List<Territory> defendingTerrList = getDefendingTerr(attackingTerr);
		for(Territory defTerr : defendingTerrList) {
			if (attackingTerr.getArmy() > 1 ) {
				GameUtils.addTextToLog(attackingTerr.getName()+ "("+attackingTerr.getPlayer().getName()+") attacking on "+defTerr+"("+defTerr.getPlayer().getName()+")\n");
				attack(attackingTerr, defTerr, playerModel, txtAreaMsg);
				break;
			}
		}
		goToNoMoreAttack();		
	}
	
	private void goToNoMoreAttack() {
		playerModel.noMoreAttack();
	}

	
	private void attack(Territory attackingTerr, Territory defTerr, PlayerModel playerModel, TextArea txtAreaMsg) {
		DiceModel diceModel = new DiceModel(attackingTerr, defTerr);
		if (playerModel != null) {
			diceModel.addObserver(playerModel);
		}
		
		DiceController diceController = new DiceController(diceModel, this);
		diceController.loadDiceControllerForStrategy();
		
	}

	/* (non-Javadoc)
	 * @see com.risk6441.strategy.IStrategy#fortificationPhase(javafx.scene.control.ListView, javafx.scene.control.ListView, javafx.scene.control.TextArea, com.risk6441.entity.Player, com.risk6441.entity.Map)
	 */
	@Override
	public boolean fortificationPhase(ListView<Territory> selectedTerritory, ListView<Territory> adjTerritory,
			 Player currentPlayer, Map map) {
		this.currentPlayer = currentPlayer;
		Territory frmTerr = getRandomTerritory(selectedTerritory.getItems());
		int count = -1;
		while(GameUtils.getAdjTerrForFortifiction(frmTerr, map, currentPlayer).size()==0 && ++count!=(selectedTerritory.getItems().size()-1))
		{
			frmTerr = getRandomTerritory(selectedTerritory.getItems());
		}
		if(count >= selectedTerritory.getItems().size()) {
			return false;
		}
		int temp = CommonMapUtil.getRandomNo(GameUtils.getAdjTerrForFortifiction(frmTerr, map, currentPlayer).size()-1);
		while(temp<0)
			temp = CommonMapUtil.getRandomNo(GameUtils.getAdjTerrForFortifiction(frmTerr, map, currentPlayer).size()-1);
		Territory toTerr = GameUtils.getAdjTerrForFortifiction(frmTerr, map, currentPlayer).get(temp);
		GameUtils.addTextToLog((frmTerr.getArmy()-1)+" Armies Moved From "+frmTerr.getName()+" to "+toTerr.getName());
		toTerr.setArmy(toTerr.getArmy() + frmTerr.getArmy() - 1);
		frmTerr.setArmy(1);
		return true;
	}

	private Territory getRandomTerritory(ObservableList<Territory> items) {
		int temp = CommonMapUtil.getRandomNo(items.size()-1);
		if(temp<0)
			temp = CommonMapUtil.getRandomNo(items.size()-1);
		Territory t = (Territory) items.get(temp);
		while(t.getArmy()<2)
		{
			temp = CommonMapUtil.getRandomNo(items.size()-1);
			t = (Territory) items.get(temp);
		}
			
		return t;
	}


	
}
