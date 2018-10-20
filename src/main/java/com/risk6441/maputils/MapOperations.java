/**
 * 
 */
package com.risk6441.maputils;

import java.util.ArrayList;

import com.risk6441.exception.InvalidMapException;
import com.risk6441.models.Continent;
import com.risk6441.models.Map;
import com.risk6441.models.Territory;

/**
 * @author Nirav
 *
 */
public class MapOperations {
	
	/**
	 * Adds continent to the map with details like control value etc.
	 * @param map Current map object.
	 * @param name Name of the continent.
	 * @param ctrlValue Control value of the continent.
	 * @return Returns the newly created continent.
	 * @throws InvalidMapException Throws IOException if there is an issue while reading a map file.
	 */
	public static Continent addContinent(Map map, String name, String ctrlValue) throws InvalidMapException {
		Continent cnt = new Continent();
		
		cnt.setName(name);
		cnt.setValue(Integer.parseInt(ctrlValue));
		
		if(map.getContinents().contains(cnt)) {
			throw new InvalidMapException("The Continent with name "+name+" already exist.");
		}
		
		return cnt;
	}
	
	/**
	 * This method adds territories to the map and the continent with its respective details.
	 * @param map Current map object.
	 * @param name Name of the territory.
	 * @param xCo X Co-ordinate of the territory.
	 * @param yCo Y Co-ordinate of the territory.
	 * @param adjTerr Adjacent territories of the current territory.
	 * @param continent Continent to which the territory belongs to.
	 * @return The newly created Territory. 
	 * @throws InvalidMapException Throws IOException if there is an issue while reading a map file.
	 */
	public static Territory addTerritory(Map map, String name, String xCo, String yCo, Territory adjTerr,
			Continent continent) throws InvalidMapException {
		Territory territory = new Territory();
		
		territory.setxCoordinate(Integer.parseInt(xCo));
		territory.setyCoordinate(Integer.parseInt(yCo));
		territory.setBelongToContinent(continent);
		territory.setName(name);
		
		ArrayList<Territory> list = new ArrayList<Territory>();
		
		if(adjTerr!=null) {
			list.add(adjTerr);
		}
		territory.setAdjacentTerritories(list);
		
		System.out.println(map.getContinents());
		//check if territory with same name exist or not
		for(Continent allCont : map.getContinents()) {
			if(allCont.getTerritories().contains(territory)) {
				throw new InvalidMapException("Territory with same name "+name+" already exist in continent "
						+ allCont.getName() +".");
			}
		}
		
		if(adjTerr!=null) {
			adjTerr.getAdjacentTerritories().add(territory);
		}
		
		
		return territory;
		
	}
	
	/**
	 * This method updates the continent details if the user selects the continent.
	 * @param continent The continent whose details must be updated.
	 * @param ctrlValue The control value of the continent.
	 * @return The current continent.
	 */
	public static Continent updateContinent(Continent continent, String ctrlValue) {
		
		continent.setValue(Integer.parseInt(ctrlValue));
		return continent;
	}
	
	/**
	 * This method updates the continent details when the user selects the territory.
	 * @param territory The territory whose values must be updated.
	 * @param xCo X-Co-ordinate of the territory.
	 * @param yCo Y-Co-ordinate of the territory.
	 * @param adjTerr The adjacent territories list. 
	 * @return The object to the newly updated territory.
	 */
	public static Territory updateTerritory(Territory territory, String xCo, String yCo, 
			Territory adjTerr) {
		territory.setxCoordinate(Integer.parseInt(xCo));
		territory.setyCoordinate(Integer.parseInt(yCo));
		
		if(adjTerr!=null) {
			if(!adjTerr.getAdjacentTerritories().contains(territory)) {
				adjTerr.getAdjacentTerritories().add(territory);
			}
			
			if(!territory.getAdjacentTerritories().contains(adjTerr)) {
				territory.getAdjacentTerritories().add(adjTerr);
			}
			
		}
		
		return territory;
	}
	
	/**
	 * This method adds a territory to the corresponding continent.
	 * @param continent
	 * 		   continent object which will be assigned territories
	 * @param territory The territory which is added to the continent.
	 * @return the Object to the newly updated continent.
	 */
	public static Continent mapTerritoriryToContinent(Continent continent, Territory territory) {
		
		try {
			continent.getTerritories().add(territory);
		}catch(Exception e) {
			ArrayList<Territory> list = new ArrayList<>();
			list.add(territory);
			continent.setTerritories(list);
		}
		
		return continent;
	}
		

}
