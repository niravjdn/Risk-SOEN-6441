package com.risk6441.maputils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

import java.util.HashMap;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.risk6441.exception.InvalidMapException;
import com.risk6441.models.Continent;
import com.risk6441.models.Map;
import com.risk6441.models.Territory;

/**
 * @author Nirav
 *
 */

public class MapOperationsTest {

	Map map;
	static Continent continent;
	String continentName = "Asia";
	String terrName = "Canada";
	int controlValue1 = 1;
	int controlValue2 = 2;
	static Territory territory;
	static Territory adjTerritory;
	int x1=1;
	int y1=1;
	int x2=2;
	int y2=2;
	
	static HashMap<String,String> mapData;
	
	String mapAuthor = "Robert";
	String mapImage = "world.map";
	String mapWrap = "no";
	String mapScroll = "horizontal";
	String mapWarn = "yes";
	
	@BeforeClass
	public static void beforeClass() {
		mapData = new HashMap<>();
		continent = new Continent();
		territory = new Territory();
		adjTerritory =  new Territory();
	}
	
	@Before
	public void before() {
		mapData = new HashMap<>();
		mapData.put("author", mapAuthor);
		mapData.put("image", mapImage);
		mapData.put("wrap", mapWrap);
		mapData.put("scroll", mapScroll);
		mapData.put("warn", mapWarn);		
		map.setMapData(mapData);
	}
	
	@Test
	public void testAddContinent() throws InvalidMapException {
		continent = MapOperations.addContinent(map, continentName, String.valueOf(controlValue1));
		assertNotNull(continent);
		assertEquals(continent.getName(), continentName);
		assertEquals(continent.getValue(), controlValue1);
	}
	
	@Test
	public void testUpdateContinent() {
		continent = MapOperations.updateContinent(continent, String.valueOf(controlValue2));
		assertEquals(continent.getValue(), controlValue2);
		assertNotEquals(continent.getValue(), controlValue2);
		assertEquals(continent.getName(), continentName);
	}
	
	@Test
	public void testAddTerritory() throws InvalidMapException {
		territory = MapOperations.addTerritory(map, terrName, String.valueOf(x1), String.valueOf(y1), null, continent);
		assertNotNull(continent);
		assertEquals(territory.getName(), terrName);
		assertEquals(territory.getxCoordinate(), x1);
		assertEquals(territory.getyCoordinate(), y1);
		assertEquals(territory.getBelongToContinent(), continent);
	}
	
	@Test
	public void testUpdateTerritory() {
		territory = MapOperations.updateTerritory(territory, String.valueOf(x2), String.valueOf(y2), null);
		Assert.assertNotNull(territory);
		Assert.assertEquals(territory.getxCoordinate(), x2);
		Assert.assertEquals(territory.getyCoordinate(), y2);
		Assert.assertNotEquals(territory.getxCoordinate(), x1);
		Assert.assertNotEquals(territory.getyCoordinate(), y1);
	}
	
	@Test
	public void testAssignTerrToContinent() throws InvalidMapException {
		Territory newTerritory = new Territory();
		newTerritory = MapOperations.addTerritory(map, "Canada", "1", "10", null, continent);
		continent = MapOperations.mapTerritoriryToContinent(continent, newTerritory);
		Assert.assertNotNull(continent);
		Assert.assertTrue(continent.getTerritories().contains(newTerritory));
	}
}
