/**
 * 
 */
package com.risk6441.strategy;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.risk6441.entity.Player;
import com.risk6441.entity.Territory;

import javafx.collections.ObservableList;
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
	public void reinforcementPhase(ObservableList<Territory> territoryList, Territory territory, TextArea txtAreaMsg,
			Player currentPlayer) {
		List<Territory> maximumOponentTerr = getMinOppTerr(territoryList);
		territory = maximumOponentTerr.get(0);
		territory.setArmy(territory.getArmy() + currentPlayer.getArmies());
		currentPlayer.setArmies(0);		
	}

	/**
	 * @param territoryList list of territories which belong to player
	 * @return return list of territory in sorted order .... with minOpp Territory at Top
	 */
	private List<Territory> getMinOppTerr(ObservableList<Territory> territoryList) {
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
