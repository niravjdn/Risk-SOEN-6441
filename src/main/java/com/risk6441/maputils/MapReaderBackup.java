package com.risk6441.maputils;

import java.io.File;
import java.io.FileInputStream;
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
public class MapReaderBackup {



	private Map map;

	//a map to check that every territory can belong to only one continent
	private HashMap<String, Integer> territoryContinentCount = new HashMap<String, Integer>();

	/**
	 * @return the map
	 */
	public Map getMap() {
		return map;
	}

	/**
	 * This method is used to read and parse the map in the object of {@link Map} 
	 * This also validates map object.
	 * @param file of type {@link File}
	 * @return map object of type {@link Map}
	 * @throws InvalidMapException
	 */
	public Map parseAndReadMapFile(File file) throws InvalidMapException {
		this.map = convertMapFileToMapObject(file);
		//MapValidator.validateMap(map);
		return map;
	}

	/**
	 * This method is use to convert map file into {@link Map} object
	 * @param file of type {@link File}
	 * @return map object of type {@link Map}
	 * @throws InvalidMapException
	 */
	private Map convertMapFileToMapObject(final File file) throws InvalidMapException {
		StringBuilder stringBuilder = new StringBuilder();

		Scanner mapScanner = null;
		try {
			mapScanner = new Scanner(new FileInputStream(file));
			while (mapScanner.hasNextLine()) {
				String data = mapScanner.nextLine();
				if (!data.isEmpty()) {
					stringBuilder.append(data + "|");
				} else {
					stringBuilder.append("\n");
				}
			}
		} catch (IOException e1) {
			System.out.println("No map file selected");
		}

		mapScanner = new Scanner(stringBuilder.toString());
		Map map = processMap(mapScanner);
		mapScanner.close();
		return map;
	}

	/**
	 * This function is used to process map for manipulations.
	 * @param scan object of type {@link Scanner}
	 * @return map object of type {@link Map}
	 * @throws InvalidMapException throws InvalidMapException if map is not valid 
	 */
	private Map processMap(Scanner scan) throws InvalidMapException {
		Map map = new Map();

		HashMap<String, String> mapAttributes = new HashMap<String, String>();

		StringTokenizer token = new StringTokenizer(scan.nextLine(), "|");
		while (token.hasMoreTokens()) {
			String element = token.nextToken();
			if (element.equalsIgnoreCase("[Map]")) {
				continue;
			} else {
				String[] data = element.split("=");
				mapAttributes.put(data[0], data[1]);
			}
		}
		map.setMapData(mapAttributes);
		List<Continent> continents = processContinent(scan);
		HashMap<String, Continent> contMap = new HashMap<String, Continent>();
		for (Continent continent : continents) {
			contMap.put(continent.getName(), continent);
		}
		map.setContinentMap(contMap);
		map.setContinents(continents);

		return map;
	}

	/**
	 * This function is used to process continents for manipulations.
	 * @param scan object of type {@link Scanner}
	 * @return a list of continents after processing
	 * @throws InvalidMapException if map is not valid
	 */
	private List<Continent> processContinent(Scanner scan) throws InvalidMapException {
		List<Continent> continents = new ArrayList<Continent>();
		StringTokenizer token = new StringTokenizer(scan.nextLine(), "|");
		while (token.hasMoreTokens()) {
			String element = token.nextToken();
			if (element.equalsIgnoreCase("[Continents]")) {
				continue;
			} else {
				Continent continent = new Continent();
				String[] data = element.split("=");
				continent.setName(data[0]);
				continent.setValue(Integer.parseInt(data[1]));
				continents.add(continent);
			}
		}

		List<Territory> territories = new ArrayList<Territory>();
		while (scan.hasNext()) {
			String territoryData = scan.nextLine();
			territories.addAll(processTerritory(territoryData, continents));
		}

		HashMap<String, Territory> tMap = new HashMap<String, Territory>();
		for (Territory t : territories) {
			tMap.put(t.getName(), t);
		}
		
		//Map adjacnet territory object to territory
		for (Territory t : territories) {
			for (String name : t.getAdjTerritories()) {
				if (tMap.containsKey(name)) {
					if (t.getAdjacentTerritories() == null) {
						t.setAdjacentTerritories(new ArrayList<Territory>());
					}
					t.getAdjacentTerritories().add(tMap.get(name));
				} else {
					throw new InvalidMapException("Territory: " + name + " not mapped with any continent.");
				}
			}
		}

		// Add the territories to their continent
		for (Continent continent : continents) {
			HashMap<String, Territory> contTMap = new HashMap<String, Territory>();
			for (Territory territory : territories) {
				if (territory.getBelongToContinent().equals(continent)) {
					if (continent.getTerritories() == null) {
						continent.setTerritories(new ArrayList<Territory>());
						contTMap.put(territory.getName(), territory);
					}
					continent.getTerritories().add(territory);
					contTMap.put(territory.getName(), territory);
				}
			}
			continent.setTerritoryMap(contTMap);
		}

		return continents;
	}

	/**
	 * This function is used to process territory for manipulations.
	 * @param territoryData
	 * @param continents list of object of the continent
	 * @return list of territories after processing
	 * @throws InvalidMapException if map is not valid
	 */
	private List<Territory> processTerritory(String territoryData, List<Continent> continents)
			throws InvalidMapException {

		List<Territory> territories = new ArrayList<Territory>();
		StringTokenizer token = new StringTokenizer(territoryData, "|");
		while (token.hasMoreTokens()) {
			String element = token.nextToken();
			if (element.equalsIgnoreCase("[Territories]")) {
				continue;
			} else {
				Territory territory = new Territory();
				List<String> adjacentTerritories = new ArrayList<String>();
				String[] data = element.split(",");
				territory.setName(data[0]);
				territory.setxCoordinate(Integer.parseInt(data[1]));
				territory.setyCoordinate(Integer.parseInt(data[2]));

				for (Continent continent : continents) {
					if (continent.getName().equalsIgnoreCase(data[3])) {
						territory.setBelongToContinent(continent);
						
						if (territoryContinentCount.get(data[0]) == null) {
							territoryContinentCount.put(data[0], 1);
						} else {
							throw new InvalidMapException("A Territory cannot be assigned to more than one Continent.");
						}
					}
				}
				if (territoryContinentCount.get(data[0]) == null) {
					throw new InvalidMapException("A Territory should be assigned to one Continent.");
				}
				for (int i = 4; i < data.length; i++) {
					adjacentTerritories.add(data[i]);
				}
				territory.setAdjTerritories(adjacentTerritories);
				territories.add(territory);
			}
		}

		return territories;
	}

}
