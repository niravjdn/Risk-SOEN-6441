/**
 * 
 */
package com.risk6441.maputils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.risk6441.exception.InvalidMapException;
import com.risk6441.models.Continent;
import com.risk6441.models.Map;
import com.risk6441.models.Territory;

/**
 * This is the test class for MapVerifier. {@link MapVerifier}
 * @author Nirav
 */
public class MapVerifierTest {

	static Continent continent;
	static Territory territory;
	static Map map;
	

	String mapAuthor = "Nirav";
	String mapImage = "world.map";
	String mapWrap = "no";
	String mapScroll = "horizontal";
	String mapWarn = "yes";
	
	String continentName = "North America";
	int controlValue = 10;
	
	static HashMap<String, String> mapData = new HashMap<>();
	List<Continent> continentList;

	/**
	 * This method executed before all the methods of the class.
	 */
	@BeforeClass
	public static void beforeClass() {
		continent = new Continent();
		territory = new Territory();
		map = new Map();
	}	
	
	/**
	 * This method is executed before every method of the class.
	 */
	@Before
	public void beforeTest() {
		mapData.put("author", mapAuthor);
		mapData.put("image", mapImage);
		mapData.put("wrap", mapWrap);
		mapData.put("scroll", mapScroll);
		mapData.put("warn", mapWarn);		
		map.setMapData(mapData);
		
		continent.setName(continentName);
		continent.setValue(controlValue);
		
		territory.setName("Canada");
		territory.setxCoordinate(1);
		territory.setyCoordinate(2);
		
		continentList = new ArrayList<>();
		continentList.add(continent);
	}
	
	/**
	 * This method tests that map is null or not.
	 * @throws InvalidMapException InvalidMapException
	 */
	@Test (expected = InvalidMapException.class)
	public void verifyNullMap() throws InvalidMapException {
		MapVerifier.verifyMap(null);
	}
	
	
	/**
	 * This method verifies that map has at least one continent.
	 * @throws InvalidMapException InvalidMapException
	 */
	@Test (expected = InvalidMapException.class)
	public void verifyMap() throws InvalidMapException {
		MapVerifier.verifyMap(new Map());
	}
	
	/**
	 * This method is used to verify that continent is null or not.
	 * @throws InvalidMapException invalid map exception.
	 */
	@Test (expected = InvalidMapException.class)
	public void validateContinentForNullTerritory() throws InvalidMapException {
		map.setContinents(continentList);
		MapVerifier.verifyContinents(map);	
	}
	
	/**
	 * This method is used to test if a continent is a sub-graph or not.
	 * @throws InvalidMapException invalid map exception.
	 */
	@Test
	public void validateMapForSubGraph() throws InvalidMapException {		
		assertFalse(MapVerifier.isMapConnectedGraph(map));
	}
	
	/**
	 * This method verify that continent is subgraph or not formed by territories or not.
	 * @throws InvalidMapException invalid map exception.
	 */
	@Test 
	public void validateContinentForSubGraph() throws InvalidMapException {
		List<Territory> terrList = new ArrayList<>();
		terrList.add(territory);
		Territory territory2 =  new Territory();
		territory2.setName("India");
		territory2.setxCoordinate(1);
		territory2.setyCoordinate(2);
		terrList.add(territory);
		continent.setTerritories(terrList);
		assertEquals(MapVerifier.isContinentConnectedGraph(continent, map), true);
		
		List<Territory> adjTerrList = new ArrayList<>();
		adjTerrList.add(territory);
		territory2.setAdjacentTerritories(adjTerrList);
		
		adjTerrList = new ArrayList<>();
		adjTerrList.add(territory2);
		territory.setAdjacentTerritories(adjTerrList);
		
		terrList.remove(1);
		terrList.add(territory2);
		continent.setTerritories(terrList);
		assertTrue(MapVerifier.isContinentConnectedGraph(continent, map));
	}
}
