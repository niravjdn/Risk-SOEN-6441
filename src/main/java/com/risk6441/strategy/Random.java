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
	private DiceModel diceModel;
	private Player currentPlayer = null;
	/* (non-Javadoc)
	 * @see com.risk6441.strategy.IStrategy#reinforcementPhase(javafx.collections.ObservableList, com.risk6441.entity.Territory, javafx.scene.control.TextArea, com.risk6441.entity.Player)
	 */
	@Override
	public void reinforcementPhase(ObservableList<Territory> territoryList, Territory territory,
			Player currentPlayer) {
		int army = CommonMapUtil.getRandomNoFromOne(currentPlayer.getArmies());
		do{
			Territory randomTerr = territoryList.get(CommonMapUtil.getRandomNo(territoryList.size()-1));
			randomTerr.setArmy(randomTerr.getArmy() + army);
			currentPlayer.setArmies(currentPlayer.getArmies()-army);
			GameUtils.addTextToLog(army+" assigned to :"+randomTerr.getName()+"  by "+currentPlayer.getName()+"\n");
			army = CommonMapUtil.getRandomNoFromOne(currentPlayer.getArmies());
		}while (currentPlayer.getArmies() > 0);

	}

	/* (non-Javadoc)
	 * @see com.risk6441.strategy.IStrategy#attackPhase(javafx.scene.control.ListView, javafx.scene.control.ListView, com.risk6441.models.PlayerModel, javafx.scene.control.TextArea)
	 */
	@Override
	public void attackPhase(ListView<Territory> terrList, ListView<Territory> adjTerrList, PlayerModel playerModel
			, List<Player> playerList) throws InvalidGameActionException {
		this.playerModel = playerModel;
		attackingTerr = getRandomTerritory(terrList.getItems());
		List<Territory> defendingTerrList = getDefendingTerr(attackingTerr);
		if(defendingTerrList.size()<1)
			goToNoMoreAttack();
		else
		{
			for(Territory defTerr : defendingTerrList) {
				if (attackingTerr.getArmy() > 1 ) {
					GameUtils.addTextToLog(attackingTerr.getName()+ "("+attackingTerr.getPlayer().getName()+") attacking on "+defTerr+"("+defTerr.getPlayer().getName()+")\n");
					attack(attackingTerr, defTerr, playerModel);
					break;
				}
			}
			goToNoMoreAttack();			
		}
	}
	
	private void goToNoMoreAttack() {
		playerModel.noMoreAttack();
	}

	
	/**
	 * This method perform attacks from attacking territory to defending territory.
	 * @param attackingTerr Attacking Territory
	 * @param defTerr Defending Territory
	 * @param playerModel object of {@link PlayerModel}
	 */
	private void attack(Territory attackingTerr, Territory defTerr, PlayerModel playerModel) {
		diceModel = new DiceModel(attackingTerr, defTerr);
		if (playerModel != null) {
			diceModel.addObserver(playerModel);
		}

		DiceController diceController = new DiceController(diceModel, this);
		diceController.loadDiceControllerForStrategy();

	}

	/* 
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
		int size = GameUtils.getAdjTerrForFortifiction(frmTerr, map, currentPlayer).size();
		Territory toTerr = null;
		if(size == 1) {
			toTerr = GameUtils.getAdjTerrForFortifiction(frmTerr, map, currentPlayer).get(0);
		}else {
			toTerr = GameUtils.getAdjTerrForFortifiction(frmTerr, map, currentPlayer).get(CommonMapUtil.getRandomNo(size-1));
		}
		GameUtils.addTextToLog((frmTerr.getArmy()-1)+" Armies Moved From "+frmTerr.getName()+" to "+toTerr.getName());
		toTerr.setArmy(toTerr.getArmy() + frmTerr.getArmy() - 1);
		frmTerr.setArmy(1);
		return true;
	}

	/**
	 * This method is responsible for finding a random territory.
	 * @param items This is a list of all the territories.
	 * @return territory which can be used for attack.
	 */
	private Territory getRandomTerritory(ObservableList<Territory> items) {
		int temp = CommonMapUtil.getRandomNo(items.size()-1);
		Territory t = (Territory) items.get(temp);
		while(t.getArmy()<2)
		{
			temp = CommonMapUtil.getRandomNo(items.size()-1);
			t = (Territory) items.get(temp);
		}
			
		return t;
	}


	
}
