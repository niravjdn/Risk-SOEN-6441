/**
 * 
 */
package com.risk6441.maputils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map.Entry;


import com.risk6441.exception.InvalidMapException;
import com.risk6441.models.Continent;
import com.risk6441.models.Map;
import com.risk6441.models.Territory;

/**
 * This class is responsible to write the map file when user creates the map.
 * @author Nirav
 */
public class MapWriter {
	
	
	/**
	 * This method writes the map details to the map file.
	 * @param map object of the map which is being processed
	 * @param file file path
	 */
	public void writeMapFile(Map map, File file) {
		
		FileWriter fileWriter;
		try {
			if (map == null) {
				System.out.println("Map Object is NULL!");
			}
			
			String content = parseMapAndReturnString(map);
			fileWriter = new FileWriter(file, false);
			fileWriter.write(content);
			fileWriter.close();
			
		}catch (IOException e) {
			System.err.println(e.getMessage());
		}
	}
	
	
	/**
	 * This method processes the map by calling three different methods and makes a string to be written in the map file.
	 * @param map object of the map which is being processed
	 * @return String to be written in the map file
	 */
	private String parseMapAndReturnString(Map map){
		StringBuilder content = new StringBuilder();
		content = processMapAttribute(map);
		content.append(processContinent(map));
		content.append(processTerritories(map));
		return content.toString();
	}
	
	
	/**
	 * This method process the map attriibutes.
	 * @param map object of the map which is being processed
	 * @return a String that contains the map properties.
	 */
	private StringBuilder processMapAttribute(Map map) {
		StringBuilder mapAttribute = new StringBuilder();
		mapAttribute.append("[Map]");
		mapAttribute.append("\n");
		
		for(Entry<String, String> keymap: map.getMapData().entrySet()) {
			mapAttribute.append(keymap.getKey() + "=" + keymap.getValue());
			mapAttribute.append("\n");
		}
		
		return mapAttribute;
	}
		
	/**
	 * This method processes the continents.
	 * @param map object of the map which is being processed
	 * @return a string that contains details of the continents that will eventually be written in the map file. 
	 */
	private StringBuilder processContinent(Map map) {
		StringBuilder continentData = new StringBuilder();
		continentData.append("\n");
		continentData.append("[Continents]");
		continentData.append("\n");
		for (Continent continent : map.getContinents()) {
			continentData.append(continent.getName() + "=" + continent.getValue());
			continentData.append("\n");
		}
		return continentData;
	}
	
	/**
	 * This method processes the territories.
	 * @param map object of the map that is being processed
	 * @return a string that contains details of the territories that will eventually be written in the map file. 
	 */
	private StringBuilder processTerritories(Map map) {
		StringBuilder territoriyData = new StringBuilder();
		territoriyData.append("\n");
		territoriyData.append("[Territories]");
		territoriyData.append("\n");
		
		for (Continent continent : map.getContinents()) {
			List<Territory> territorieList = continent.getTerritories();
			if (territorieList != null) {
				for(Territory territory : territorieList) {
					territoriyData.append(territory.getName() + "," + territory.getxCoordinate() + ","
							+ territory.getyCoordinate() + "," + territory.getBelongToContinent().getName());
					for (Territory adjacentTerritories : territory.getAdjacentTerritories()) {
						territoriyData.append(",");
						territoriyData.append(adjacentTerritories.getName());
					}
					territoriyData.append("\n");
				}
				territoriyData.append("\n");
			}else {
					//error
					//MapUtil.infoBox("Add a territory to a contient", "Error", "Error");
				}
			}
		return territoriyData;
	}

		
}
