/**
 * 
 */
package com.risk6441.strategy;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Observable;

import com.risk6441.entity.Map;
import com.risk6441.entity.Player;
import com.risk6441.entity.Territory;
import com.risk6441.exception.InvalidGameActionException;
import com.risk6441.gameutils.GameUtils;
import com.risk6441.models.PlayerModel;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;

/**
 * @author Nirav
 *
 */
public class Cheater extends Observable implements IStrategy {

	/* (non-Javadoc)
	 * @see com.risk6441.strategy.IStrategy#reinforcementPhase(javafx.collections.ObservableList, com.risk6441.entity.Territory, javafx.scene.control.TextArea, com.risk6441.entity.Player)
	 */
	@Override
	public void reinforcementPhase(ObservableList<Territory> territoryList, Territory territory,
			Player currentPlayer,ArrayList<Territory> terrArList,ArrayList<Territory> adjTerrArList) {
		
		for(Territory terr : terrArList) {
			terr.setArmy(Math.abs(terr.getArmy() * 2));
			GameUtils.addTextToLog("Armies Doubled to "+terr.getArmy()+ " on "+ terr.getName()+"\n");
		}
		currentPlayer.setArmies(0);
	}

	/* (non-Javadoc)
	 * @see com.risk6441.strategy.IStrategy#attackPhase(javafx.scene.control.ListView, javafx.scene.control.ListView, com.risk6441.models.PlayerModel, javafx.scene.control.TextArea)
	 */
	@Override
	public void attackPhase(ListView<Territory> terrList1, ListView<Territory> adjTerrList1, PlayerModel playerModel,
			List<Player> playerList,ArrayList<Territory> terrArList,ArrayList<Territory> adjTerrArList) throws InvalidGameActionException {
		
		this.addObserver(playerModel);
		Iterator<Territory> terrListIterator = terrArList.iterator();
		while (terrListIterator.hasNext()) {
			Territory attackingTerr = terrListIterator.next();
			List<Territory> deffTerrList = getDefendingTerr(attackingTerr);
			if (deffTerrList.size() > 0) {
				for(Territory deffTerr : deffTerrList) {
					deffTerr.setArmy(3);
					attackingTerr.setArmy(attackingTerr.getArmy() - 3);
					GameUtils.addTextToLog(attackingTerr.getName()+ "("+attackingTerr.getPlayer().getName()+""
							+ ") attacking on "+deffTerr+"("+deffTerr.getPlayer().getName()+")\n");
					deffTerr.getPlayer().getAssignedTerritory().remove(deffTerr);
					deffTerr.setPlayer(attackingTerr.getPlayer());
					
					attackingTerr.getPlayer().getAssignedTerritory().add(deffTerr);
					GameUtils.addTextToLog(deffTerr.getName() + " is conquered by "
							+ attackingTerr.getPlayer().getName() + "\n");
					playerModel.setNumOfTerritoryWon(playerModel.getNumOfTerritoryWon()+1);
					Platform.runLater(()->{
						setChanged();
						notifyObservers("oneAttackDoneForCheater");
					});
				}
			} else {
				continue;
			}
		}
	}

	/* (non-Javadoc)
	 * @see com.risk6441.strategy.IStrategy#fortificationPhase(javafx.scene.control.ListView, javafx.scene.control.ListView, javafx.scene.control.TextArea, com.risk6441.entity.Player, com.risk6441.entity.Map)
	 */
	@Override
	public boolean fortificationPhase(ListView<Territory> selectedTerritory, ListView<Territory> adjTerritory,
			Player currentPlayer, Map map,ArrayList<Territory> terrArList,ArrayList<Territory> adjTerrArList) {
		for(Territory terr: terrArList) {
			List<Territory> adjDefTerrList = getDefendingTerr(terr);
			if(adjDefTerrList!=null && adjDefTerrList.size()>0) {
				GameUtils.addTextToLog("Doubled the army on territory :"+terr.getName()+"\n");
				terr.setArmy(terr.getArmy() * 2);
			}
		}
		return true;
	}

	
	/* (non-Javadoc)
	 * @see com.risk6441.strategy.IStrategy#hasAValidAttackMove(javafx.scene.control.ListView, javafx.scene.control.TextArea)
	 */
	@Override
	public boolean hasAValidAttackMove(ArrayList<Territory> territories) {
		boolean isValidAttackMove =false;
		for (Territory territory : territories) {
			if (getDefendingTerr(territory).size() > 0) {
				isValidAttackMove = true;
			}
		}
		if (!isValidAttackMove) {
			GameUtils.addTextToLog("No valid attack move avialble.\n");
			GameUtils.addTextToLog("===> Attack phase ended! === \n");
		}
		return isValidAttackMove;
	}

}
