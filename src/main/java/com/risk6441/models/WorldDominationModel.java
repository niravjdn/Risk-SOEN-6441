/**
 * 
 */
package com.risk6441.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import com.risk6441.entity.Continent;
import com.risk6441.entity.Map;
import com.risk6441.entity.Player;
import com.risk6441.entity.Territory;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.PieChart.Data;

/**
 * @author Nirav
 *
 */
public class WorldDominationModel {
	
	/**
	 * Populate World Domination Data according to playerTerritoryCount
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
			playerTerPercent.put(entry.getKey(), (entry.getValue()/territoryCount * 100));
		}
		return playerTerPercent;
	}
	
	/**
	 * Populate World Domination Data according to playerTerritoryCount
	 * 
	 * @param map
	 *            map object
	 * @return playerTerPercent.
	 */
	public static HashMap<String, Double> getMilitaryDominationData(Map map) {
		HashMap<Player, Double> playerAndMilitaryCountMap = new HashMap<>();
		Double territoryCount = 0.0;
		for (Continent cont : map.getContinents()) {
			for (Territory ter : cont.getTerritories()) {
				territoryCount++;
				Player player = ter.getPlayer();
				
				if(playerAndMilitaryCountMap.containsKey(player)) {
					playerAndMilitaryCountMap.put(player, playerAndMilitaryCountMap.get(player)+ter.getArmy());
				} else {
					playerAndMilitaryCountMap.put(player, Double.valueOf("0"));
				}
			}
		}

		HashMap<String, Double> playerTerPercent = new HashMap<>();
		for(Entry<Player, Double> entry : playerAndMilitaryCountMap.entrySet()) {
			playerTerPercent.put(entry.getKey().getName(), (entry.getValue()/territoryCount * 100));
		}
		return playerTerPercent;
	}

}
