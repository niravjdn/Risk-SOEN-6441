/**
 * 
 */
package com.risk6441.strategy;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.risk6441.entity.Map;
import com.risk6441.entity.Player;
import com.risk6441.entity.Territory;
import com.risk6441.exception.InvalidGameActionException;
import com.risk6441.gameutils.GameUtils;
import com.risk6441.models.PlayerModel;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;

/**
 * @author Nirav
 *
 */
public class Benevolent implements IStrategy {

	/* (non-Javadoc)
	 * @see com.risk6441.strategy.IStrategy#reinforcementPhase(javafx.collections.ObservableList, com.risk6441.entity.Territory, javafx.scene.control.TextArea, com.risk6441.entity.Player)
	 */
	@Override
	public void reinforcementPhase(ObservableList<Territory> territoryList, Territory territory,
			Player currentPlayer,ArrayList<Territory> terrArList,ArrayList<Territory> adjTerrArList) {
		List<Territory> maximumOponentTerr = sortAndGetWeakestTerr(terrArList);
		territory = maximumOponentTerr.get(0);
		int army = currentPlayer.getArmies();
		territory.setArmy(territory.getArmy() + army);
		currentPlayer.setArmies(0);
		GameUtils.addTextToLog(
				"===" + army + " assigned to : === \n" + territory + "  -- Player " + currentPlayer.getName() + "\n");
		System.out.println("===" + army + " assigned to : === \n" + territory + "  -- Player " + currentPlayer.getName() + "\n");
		GameUtils.addTextToLog("======Reinforce Phase Completed. ===========\n");

	}


	/* (non-Javadoc)
	 * @see com.risk6441.strategy.IStrategy#attackPhase(javafx.scene.control.ListView, javafx.scene.control.ListView, com.risk6441.models.PlayerModel, javafx.scene.control.TextArea)
	 */
	@Override
	public void attackPhase(ListView<Territory> terrList, ListView<Territory> adjTerrList, PlayerModel playerModel,
			List<Player> playerList,ArrayList<Territory> terrArList,ArrayList<Territory> adjTerrArList)  {
		// Benevolent Player does not attack
		
	}

	/* (non-Javadoc)
	 * @see com.risk6441.strategy.IStrategy#fortificationPhase(javafx.scene.control.ListView, javafx.scene.control.ListView, javafx.scene.control.TextArea, com.risk6441.entity.Player, com.risk6441.entity.Map)
	 */
	@Override
	public boolean fortificationPhase(ListView<Territory> terrList, ListView<Territory> adjTerrList,
			Player currentPlayer, Map map,ArrayList<Territory> terrArList,ArrayList<Territory> adjTerrArList) {
		List<Territory> sortedMinAdjTerr = sortAndGetWeakestTerr(terrArList);
		for (Territory weakTerr : sortedMinAdjTerr) {
			for(Territory adjTerr : weakTerr.getAdjacentTerritories()) {
				if (adjTerr.getArmy() > 1) {
					weakTerr.setArmy(weakTerr.getArmy() + adjTerr.getArmy() -1);
					GameUtils.addTextToLog((adjTerr.getArmy()-1)+" Armies Moved From "+adjTerr.getName()+" to "+weakTerr.getName());
					adjTerr.setArmy(1);
					return true;
				}
			}
			
		}
		return false;
	}

	
	/**
	 * This method returns list of territories in an increasing order of defending territories.
	 * @param territoryList list of territories which belong to player
	 * @return return list of territory in sorted order .... with minOpp Territory at Top
	 */
	public List<Territory> sortAndGetWeakestTerr(ArrayList<Territory> territoryList) {
		Collections.sort(territoryList, new Comparator<Territory>() {
			
			/* (non-Javadoc)
			 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
			 */
			@Override
			public int compare(Territory t1, Territory t2) {
				return Integer.valueOf(getDefendingTerr(t2).size()).compareTo(getDefendingTerr(t1).size());
			}
		});
		return territoryList;
	}
}
