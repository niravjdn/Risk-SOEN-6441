/**
 * 
 */
package com.risk6441.maputils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.StringTokenizer;

import com.risk6441.exception.InvalidMapException;
import com.risk6441.models.Continent;
import com.risk6441.models.Map;
import com.risk6441.models.Territory;

/**
 * @author Nirav
 *
 */
public class MapReader {

	//make a object of Map class to return it once map is processed successfully.
	private Map map;
	
	//make a map to make sure that territory belongs to only one continent
	private HashMap<String, Integer> territoryBelongContinentCount = new HashMap<String, Integer>();
	
	
	/**
	 * return the map object after processing the map file
	 * @return the map
	 */
	private Map getMap() {
		return map;
	}
	
	public Map readMapFile(final File file) throws InvalidMapException{
		
		this.map = processMapFile(file);
		return map;
	}

	/**
	 * This method is used to read and process map data
	 * @param file
	 * @return the map
	 * @throws InvalidMapException 
	 */
	private Map processMapFile(File file) throws InvalidMapException {
		Map map = new Map();

		Scanner mapFileReader;
		try {
			mapFileReader = new Scanner(new FileInputStream(file));
			StringBuilder mapString = new StringBuilder();

			//procees and read map file in three steps
			while(mapFileReader.hasNext()) {
				String line = mapFileReader.nextLine();
				if(!line.isEmpty()) {
					mapString.append(line + "|");
				}else {
					mapString.append("\n");
				}
			}

			//set map attributes 
			mapFileReader = new Scanner(mapString.toString());
			map = processMapAttribute(mapFileReader, map);
			//set continents info

			//set territory info

		}
		catch(IOException e) {
			System.out.println("Map File is not selected");
		}
		
		
		return null;
	}
	
	
	private Map processMapAttribute(Scanner scan, Map map) throws InvalidMapException{
		
		
		map.setMapData(mapAttributeMap);
		
		List<Continent> continentList = processContinents(scan, map);
		HashMap<String, Continent> continentMap = new HashMap<String, Continent>();
		for (Continent continent : continentList) {
			continentMap.put(continent.getName(), continent);
		}
		map.setContinentMap(continentMap);
		map.setContinents(continentList);
		
		return map;
	}
	
	private List<Continent> processContinents(Scanner scan, Map map) throws InvalidMapException{
		List<Continent> continentList = new ArrayList<Continent>();
		StringTokenizer tokenForContinents = new StringTokenizer(scan.nextLine(), "|");
		while (tokenForContinents.hasMoreTokens()) {
			String line = tokenForContinents.nextToken();
			if (line.equalsIgnoreCase("[Continents]")) {
				continue;
			} else {
				Continent continent = new Continent();
				String[] data = line.split("=");
				continent.setName(data[0]);
				continent.setValue(data[1]);
				continentList.add(continent);
			}
		}
		
		List<Territory> territorieList = new ArrayList<Territory>();
		while (scan.hasNext()) {
			String territoryData = scan.nextLine();
			//call processTerritory for each line of territory
			territorieList.addAll(processTerritories(territoryData, continentList));
		}
		
		
		
		HashMap<String, Territory> territoryMap = new HashMap<String, Territory>();
		for (Territory t : territorieList) {
			territoryMap.put(t.getName(), t);
		}
		
		//Map neighbour territory object to territory
		for(Territory territory : territorieList) {
			for(String adjacentTerritory : territory.getAdjTerritories()) {
				if(territoryMap.containsKey(adjacentTerritory)){
					if (territory.getAdjacentTerritories() == null) {
						territory.setAdjacentTerritories(new ArrayList<Territory>());
					}
					territory.getAdjacentTerritories().add(territoryMap.get(adjacentTerritory));
				}else {
					throw new InvalidMapException("Territory: " + adjacentTerritory + " not mapped with any continent.");
				}
			}
			
		}
		
		
		for(Continent continent : continentList) {
			HashMap<String, Territory> continentTMap = new HashMap<String, Territory>();
			for(Territory territory : territorieList) {
				if (territory.getBelongToContinent().equals(continent)) {
					if (continent.getTerritories() == null) {
						continent.setTerritories(new ArrayList<Territory>());
						continentTMap.put(territory.getName(), territory);
					}
					continent.getTerritories().add(territory);
					continentTMap.put(territory.getName(), territory);
				}
			}
			continent.setTerritoryMap(continentTMap);
		}
		
		
		return continentList;
	}
	
	private List<Territory> processTerritories(String territoryLine, List<Continent> continentList) throws InvalidMapException{
		
		List<Territory> territorieList = new ArrayList<Territory>();
		StringTokenizer tokenForTerritory = new StringTokenizer(territoryLine, "|");
		while (tokenForTerritory.hasMoreTokens()) {
			
			String element = tokenForTerritory.nextToken();
			if (element.equalsIgnoreCase("[Territories]")) {
				continue;
			} else {
				
				Territory territory = new Territory();
				List<String> adjacentTerritories = new ArrayList<String>();
				String[] dataOfTerritory = element.split(",");
				
				territory.setName(dataOfTerritory[0]);
				territory.setxCoordinate(Integer.parseInt(dataOfTerritory[1]));
				territory.setyCoordinate(Integer.parseInt(dataOfTerritory[2]));

				for (Continent continent : continentList) {
					if (continent.getName().equalsIgnoreCase(dataOfTerritory[3])) {
						territory.setBelongToContinent(continent);
						
						if (territoryBelongContinentCount.get(dataOfTerritory[0]) == null) {
							territoryBelongContinentCount.put(dataOfTerritory[0], 1);
						} else {
							throw new InvalidMapException("A Territory can be assigned to only one Continent.");
						}
					}
				}
				if (territoryBelongContinentCount.get(dataOfTerritory[0]) == null) {
					throw new InvalidMapException("A Territory can be assigned to one Continent.");
				}
				
				for (int i = 4; i < dataOfTerritory.length; i++) {
					adjacentTerritories.add(dataOfTerritory[i]);
				}
				territory.setAdjTerritories(adjacentTerritories);
				territorieList.add(territory);
			}
			
		}
		
		return territorieList;
	}

}
