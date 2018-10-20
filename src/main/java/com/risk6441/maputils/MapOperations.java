/**
 * 
 */
package com.risk6441.maputils;

import java.util.ArrayList;
import java.util.List;
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
	 * Adds territory to the map and the continent with its respective details.
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
	 * @param map map object {@link Map}
	 * @param name name of the continent to be updated
	 * @param ctrlValue The control value of the continent.
	 * @return The current continent.
	 * @throws InvalidMapException  InvalidMapException if any error occurs
	 */
	public static Continent updateContinent(Continent continent, Map map , String name, String ctrlValue) throws InvalidMapException {
		if(!continent.getName().equals(name)) {
			if(containsContinentName(map.getContinents(), name)) {
				throw new InvalidMapException("The Continent with name "+name+" already exist.");
			}
			continent.setName(name);
		}
		
		continent.setValue(Integer.parseInt(ctrlValue));
		return continent;
	}
	
	/**
	 * @param list list of all continents
	 * @param name name of the continents to be updated
	 * @return true if list does not contain other continents with same name
	 */
	public static boolean containsContinentName(final List<Continent> list, final String name){
	    return list.stream().filter(o -> o.getName().equals(name)).findFirst().isPresent();
	}
	
	/**
	 * This method updates the continent details when the user selects the territory.
	 * @param territory The territory whose values must be updated.
	 * @param map Map Object {@link Map}
	 * @param name name for the territory to be updated - new name for the territory
	 * @param xCo X-Co-ordinate of the territory.
	 * @param yCo Y-Co-ordinate of the territory.
	 * @param adjTerr The adjacent territories list. 
	 * @return The object to the newly updated territory.
	 * @throws InvalidMapException InvalidMapException if any error occurs
	 */
	public static Territory updateTerritory(Territory territory, Map map, String name,String xCo, String yCo, 
			Territory adjTerr) throws InvalidMapException {
		territory.setxCoordinate(Integer.parseInt(xCo));
		territory.setyCoordinate(Integer.parseInt(yCo));
		
		if(!territory.getName().equals(name)) {
			ArrayList<Territory> listAllTerr = new ArrayList<Territory>();
			for(Continent cont : map.getContinents()) {
				listAllTerr.addAll(cont.getTerritories());
			}
			
			if(containsTerrName(listAllTerr, name)) {
				throw new InvalidMapException("The Territory with name "+name+" already exist.");
			}
			territory.setName(name);
		}
		
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
	 * @param list list of all territories
	 * @param name name of the territory to be checked
	 * @return true if list does not contain other territory with same name
	 */
	public static boolean containsTerrName(final ArrayList<Territory> list, final String name){
	    return list.stream().filter(o -> o.getName().equals(name)).findFirst().isPresent();
	}
	
	/**
	 * This method adds the territory to the corresponding continent.
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
