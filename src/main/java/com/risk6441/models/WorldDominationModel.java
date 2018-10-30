/**
 * 
 */
package com.risk6441.models;

import java.util.HashMap;
import java.util.Map.Entry;

import com.risk6441.entity.Continent;
import com.risk6441.entity.Map;
import com.risk6441.entity.Player;
import com.risk6441.entity.Territory;

/**
 * This class handles data for the graph of world domination coverage and military bar chart.
 * @author Nirav
 */
public class WorldDominationModel {
	
	/**
	 * Show domination of world by different players
	 * 
	 * @param map
	 *            map object
	 * @return playerTerPercent.
	 */
	public static HashMap<Player, Double> getWorldDominationData(Map map) {

		HashMap<Player, Double> playerAndTerritororyCountMap = new HashMap<>();
		Double territoryCount = 0.0;
		for (Continent cont : map.getContinents()) {
			for (Territory ter : cont.getTerritories()) {
				territoryCount++;
				Player player = ter.getPlayer();
				if(playerAndTerritororyCountMap.containsKey(player)) {
					playerAndTerritororyCountMap.put(player, playerAndTerritororyCountMap.get(player)+1);
				} else {
					playerAndTerritororyCountMap.put(player, Double.valueOf("1"));
				}
			}
		}

		HashMap<Player, Double> playerTerPercent = new HashMap<>();
		for(Entry<Player, Double> entry : playerAndTerritororyCountMap.entrySet()) {
			playerTerPercent.put(entry.getKey(), ((entry.getValue()/territoryCount) * 100));
		}
		return playerTerPercent;
	}
	
	/**
	 * Show military distributions
	 * 
	 * @param map
	 *            map object
	 * @return playerTerPercent.
	 */
	public static HashMap<String, Double> getMilitaryDominationData(Map map) {
		HashMap<String, Double> playerAndMilitaryCountMap = new HashMap<>();
		for (Continent cont : map.getContinents()) {
			for (Territory ter : cont.getTerritories()) {
				Player player = ter.getPlayer();
				
				if(playerAndMilitaryCountMap.containsKey(player.getName())) {
					playerAndMilitaryCountMap.put(player.getName(), playerAndMilitaryCountMap.get(player.getName())+ter.getArmy());
				} else {
					playerAndMilitaryCountMap.put(player.getName(), Double.valueOf("0"));
				}
			}
		}
		return playerAndMilitaryCountMap;
	}

}
