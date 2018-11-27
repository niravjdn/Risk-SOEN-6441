package com.risk6441.maputils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.StringTokenizer;

import org.apache.commons.text.WordUtils;

import com.risk6441.entity.Continent;
import com.risk6441.entity.Map;
import com.risk6441.entity.Territory;
import com.risk6441.exception.InvalidMapException;

/**
 * This class reads and parse the map file and sets data in corresponding objects.
 * @author Nirav
 */
public class MapReader {
	
	//make a object of Map class to return it once map is processed successfully.
	private Map map;
	
	//make a map to make sure that territory belongs to only one continent
	private HashMap<String, Integer> territoryBelongContinentCount = new HashMap<String, Integer>();
	
	//default constructor to initialize the map
	public MapReader() {
		this.map = new Map();
	}
	
	/**
	 * return the map object after processing the map file
	 * @return the map
	 */
	private Map getMap() {
		return map;
	}
	
	/**
	 * This method reads the map file and verifies if the map is valid.
	 * @param file The map file to be read.
	 * @return map The map object returned after reading the file.
	 * @throws InvalidMapException Throws IOException if the map is invalid.
	 */
	public Map readMapFile(final File file) throws InvalidMapException{
		
		this.map = processMapFile(file);
		MapVerifier.verifyMap(map);
		return map;
	}

	/**
	 * This method is used to read and process map data
	 * @param file file path
	 * @return map returns the map object after processing the file data
	 * @throws InvalidMapException throws InvalidMapException if map is not valid 
	 */
	private Map processMapFile(File file) throws InvalidMapException {
		

		Scanner mapFileReader;
		try {
			mapFileReader = new Scanner(new FileInputStream(file));
			StringBuilder mapString = new StringBuilder();
			int count=0;
			String prevLine = "";
			//procees and read map file in three steps
			while(mapFileReader.hasNext()) {
				String line = mapFileReader.nextLine();
				if(!line.isEmpty()) {
					mapString.append(line + "|");
					prevLine = line;
					count=0;
				}
				else if(line.isEmpty())
				{
//					if(prevLine.equals("[Continents]") || prevLine.equals("[Territories]")) {
//						continue;
//					}
					
					count++;
					if(count==1)
						mapString.append("\n");
					else
						count=0;
				}
			}

			//set map attributes 
			System.out.println(mapString);
			mapFileReader = new Scanner(mapString.toString());
			map = processMapAttribute(mapFileReader);
			//set continents info

			//set territory info

		}
		catch(IOException e) {
			System.out.println("Map File is not selected");
			System.out.println(e.getMessage());
		}
		
		
		return map;
	}
	
	
	/**
	 * This method process map attributes and call method for processing continents.
	 * @param scan of type {@link Scanner}
	 * @return Map of type {@link Map}
	 * @throws InvalidMapException throws InvalidMapException if map is not valid.
	 */
	private Map processMapAttribute(Scanner scan) throws InvalidMapException{
		
		HashMap<String, String> mapAttributeMap = new HashMap<String, String>();
		
		StringTokenizer tokensForMapAttribute = new StringTokenizer(scan.nextLine(), "|");
		while (tokensForMapAttribute.hasMoreTokens()) {
			String str = tokensForMapAttribute.nextToken();
			System.out.println(str);
			if (str.equalsIgnoreCase("[Map]")) {
				continue;
			} else {
				String[] data = str.split("=");
				mapAttributeMap.put(data[0], data[1]);
			}
		}
		
		map.setMapData(mapAttributeMap);
		
		List<Continent> continentList = parseContinents(scan);
		HashMap<String, Continent> continentMap = new HashMap<String, Continent>();
		for (Continent continent : continentList) {
			continentMap.put(continent.getName(), continent);
		}
		map.setContinentMap(continentMap);
		map.setContinents(continentList);
		
		return map;
	}
	
	/**
	 * This method processes the continents and call method to process territories
	 * and also  map territories and continents.
	 * @param scan scanner object which points to line of the file which is to be processed
	 * @return continentList after processing
	 * @throws InvalidMapException throws InvalidMapException if map is not valid
	 */
	private List<Continent> parseContinents(Scanner scan) throws InvalidMapException{
		List<Continent> continentList = new ArrayList<Continent>();
		StringTokenizer tokenForContinents = new StringTokenizer(scan.nextLine(), "|");
		while (tokenForContinents.hasMoreTokens()) {
			String line = tokenForContinents.nextToken();
			if (line.equalsIgnoreCase("[Continents]")) {
				continue;
			} else {
				Continent continent = new Continent();
				String[] data = line.split("=");
				continent.setName(WordUtils.capitalizeFully(data[0].trim()));
				System.out.println(data[0]);
				continent.setValue(Integer.parseInt(data[1]));
				continentList.add(continent);
			}
		}
		
		List<Territory> territorieList = new ArrayList<Territory>();
		while (scan.hasNext()) {
			String territoryData = scan.nextLine();
			//call processTerritory for each line of territory
			territorieList.addAll(parseTerritories(territoryData, continentList));
		}
		
		//here you can create continent map 
		//pass it to territory method and set there only
		
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
					//if particular territory has adjacent territory defined, but actually it doesn't exist
					throw new InvalidMapException("Territory: " + adjacentTerritory + " not assigned to any continent.");
				}
			}
			
		}
		
		//Map territories and continent
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
	
	/**
	 * This method processes territories and check that it should be assign to only one continent
	 * @param territoryLine Line from the map file for the territory
	 * @param continentList Produces the continent list.
	 * @return territorieList After processing
	 * @throws InvalidMapException Throws InvalidMapException if map is not valid
	 */
	private List<Territory> parseTerritories(String territoryLine, List<Continent> continentList) throws InvalidMapException{
		
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
				dataOfTerritory[0] = WordUtils.capitalizeFully(dataOfTerritory[0].trim());
				
				territory.setName(dataOfTerritory[0]);
				territory.setxCoordinate(Integer.parseInt(dataOfTerritory[1]));
				territory.setyCoordinate(Integer.parseInt(dataOfTerritory[2]));

				for (Continent continent : continentList) {
					if (continent.getName().equalsIgnoreCase(dataOfTerritory[3])) {
						territory.setBelongToContinent(continent);
						
						if (territoryBelongContinentCount.get(dataOfTerritory[0]) == null) {
							territoryBelongContinentCount.put(dataOfTerritory[0], 1);
						} else {
							throw new InvalidMapException("A Territory "+ territory.getName() +" can be assigned to only one Continent.");
						}
					}
				}
				if (territoryBelongContinentCount.get(dataOfTerritory[0]) == null) {
					throw new InvalidMapException("A Territory must be assigned to one Continent.");
				}
				
				for (int i = 4; i < dataOfTerritory.length; i++) {
					String str = WordUtils.capitalizeFully(dataOfTerritory[i].trim());
					if(!territory.getName().equals(str))
						adjacentTerritories.add(str);
				}
				territory.setAdjTerritories(adjacentTerritories);
				territorieList.add(territory);
			}
			
		}
		
		return territorieList;
	}

}
