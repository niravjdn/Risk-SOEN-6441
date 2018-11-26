/**
 * 
 */
package com.risk6441.strategy;

import java.util.ArrayList;
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

/**
 * @author Nirav
 *
 */
public class Random implements IStrategy {

	private Territory attackingTerr;
	private PlayerModel playerModel;
	private DiceModel diceModel;
	private Player currentPlayer = null;
	private int numOfAttack = 0;
	
	/* (non-Javadoc)
	 * @see com.risk6441.strategy.IStrategy#reinforcementPhase(javafx.collections.ObservableList, com.risk6441.entity.Territory, javafx.scene.control.TextArea, com.risk6441.entity.Player)
	 */
	@Override
	public void reinforcementPhase(ObservableList<Territory> territoryList1, Territory territory,
			Player currentPlayer,ArrayList<Territory> terrArList,ArrayList<Territory> adjTerrArList) {
		
		
		int army = CommonMapUtil.getRandomNoFromOne(currentPlayer.getArmies());
		do{
			Territory randomTerr = terrArList.get(CommonMapUtil.getRandomNo(terrArList.size()-1));
			randomTerr.setArmy(randomTerr.getArmy() + army);
			currentPlayer.setArmies(currentPlayer.getArmies()-army);
			GameUtils.addTextToLog(army+" assigned to :"+randomTerr.getName()+"  by "+currentPlayer.getName()+"\n");
			if(currentPlayer.getArmies() == 0)
				break;
			army = CommonMapUtil.getRandomNoFromOne(currentPlayer.getArmies());
		}while (currentPlayer.getArmies() > 0);

	}

	/* (non-Javadoc)
	 * @see com.risk6441.strategy.IStrategy#attackPhase(javafx.scene.control.ListView, javafx.scene.control.ListView, com.risk6441.models.PlayerModel, javafx.scene.control.TextArea)
	 */
	@Override
	public void attackPhase(ListView<Territory> terrList1, ListView<Territory> adjTerrList1, PlayerModel playerModel
			, List<Player> playerList,ArrayList<Territory> terrArList,ArrayList<Territory> adjTerrArList) throws InvalidGameActionException {
		this.playerModel = playerModel;
		attackingTerr = getRandomTerritory(terrArList);
		List<Territory> defendingTerrList = getDefendingTerr(attackingTerr);
		if(defendingTerrList.size()<1||hasAValidAttackMove(terrArList)==false) {
			goToNoMoreAttack();			
		}
		else
		{
			for(Territory defTerr : defendingTerrList) {
				if (attackingTerr.getArmy() > 1 ) {
					GameUtils.addTextToLog(attackingTerr.getName()+ "("+attackingTerr.getPlayer().getName()+") attacking on "+defTerr+"("+defTerr.getPlayer().getName()+")\n");
					attack(attackingTerr, defTerr, playerModel);
					if(defTerr.getPlayer().equals(attackingTerr.getPlayer())) {
						terrArList.add(defTerr);
					}
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
	 * @see com.risk6441.strategy.IStrategy#fortificationPhase(javafx.scene.control.ListView, javafx.scene.control.ListView, javafx.scene.control.TextArea, com.risk6441.entity.Player, com.risk6441.entity.Map)
	 */
	@Override
	public boolean fortificationPhase(ListView<Territory> selectedTerritory1, ListView<Territory> adjTerritory1,
			 Player currentPlayer, Map map,ArrayList<Territory> terrArList,ArrayList<Territory> adjTerrArList) {
		this.currentPlayer = currentPlayer;
		Territory fromTerr = getRandomTerritory(terrArList);
		int count = -1;
		while(GameUtils.getAdjTerrForFortifiction(fromTerr, map, currentPlayer).size()==0 && ++count!=(terrArList.size()-1))
		{
			fromTerr = getRandomTerritory(terrArList);
		}
		if(count >= terrArList.size()) {
			return false;
		}
		List<Territory> adjTerrForFortifiction = GameUtils.getAdjTerrForFortifiction(fromTerr, map, currentPlayer);
		int size = adjTerrForFortifiction.size();
		Territory toTerr = null;
		if(size == 1) {
			toTerr = adjTerrForFortifiction.get(0);
		}
		else if(size==0)
			return false;
		
		toTerr = adjTerrForFortifiction.get(CommonMapUtil.getRandomNo(size-1));
		
		GameUtils.addTextToLog((fromTerr.getArmy()-1)+" Armies Moved From "+fromTerr.getName()+" to "+toTerr.getName());
		System.out.println((fromTerr.getArmy()-1)+" Armies Moved From "+fromTerr.getName()+" to "+toTerr.getName());
		toTerr.setArmy(toTerr.getArmy() + fromTerr.getArmy() - 1);
		fromTerr.setArmy(1);
		return true;
	}

	/**
	 * This method is responsible for finding a random territory.
	 * @param items This is a list of all the territories.
	 * @return territory which can be used for attack.
	 */
	public Territory getRandomTerritory(ArrayList<Territory> items) {
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
