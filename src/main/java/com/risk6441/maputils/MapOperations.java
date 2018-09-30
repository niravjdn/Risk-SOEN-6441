/**
 * 
 */
package com.risk6441.maputils;

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
		cnt.setValue(ctrlValue);
		
		if(map.getContinents().contains(cnt)) {
			throw new InvalidMapException("The Continent with name "+name+" already exist.");
		}
		
		return cnt;
	}
	
	/**
	 * @param continent
	 * @param controlValue
	 * @return
	 */
	public Continent updateContinent(Continent continent, String controlValue) {
		
		continent.setValue(controlValue);
		return continent;
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
	public Territory addTerritory(Map map, String name, String xCo, String yCo, Territory adjTerritory,
			Continent continent) throws InvalidMapException {
		Territory territory = new Territory();
		
		return territory;
		
	}
	
	/**
	 * @param territory
	 * @param xCo
	 * @param yCo
	 * @param adjTerr
	 * @return
	 */
	public Territory updateTerritory(Territory territory, String xCo, String yCo, 
			Territory adjTerr) {
		
		return territory;
	}
	
	/**
	 * @param continent
	 * @param territory
	 * @return
	 */
	public Continent assignTerrToContinent(Continent continent, Territory territory) {
		
		
		return continent;
	}
		

}
