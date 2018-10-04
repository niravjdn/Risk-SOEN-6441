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
	 * @param map
	 * @param name
	 * @param ctrlValue
	 * @return
	 * @throws InvalidMapException
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
	 * @param map
	 * @param name
	 * @param xCo
	 * @param yCo
	 * @param adjTerritory
	 * @param continent
	 * @return
	 * @throws InvalidMapException
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
	 * @param continent
	 * @param controlValue
	 * @return
	 */
	public static Continent updateContinent(Continent continent, String ctrlValue) {
		
		continent.setValue(Integer.parseInt(ctrlValue));
		return continent;
	}
	
	/**
	 * @param territory
	 * @param xCo
	 * @param yCo
	 * @param adjTerr
	 * @return
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
	 * @param continent
	 * 		   continent object whihch will be assigned territories
	 * @param territory
	 * @return
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
