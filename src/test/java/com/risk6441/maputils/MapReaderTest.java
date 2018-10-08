package com.risk6441.maputils;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.risk6441.exception.InvalidMapException;
import com.risk6441.models.Map;

/**
 * This is the test class for MapReader. {@link MapReader}
 * @author Nirav
 */
public class MapReaderTest {
	
	static File file;
	static MapReader mapReader;
	String[] invalidFiles = {"continent_is_not_a subgraph.map","territory_not_mapped_mutually.map",
			"territory_with_two_continent.map","territory_without_continent.map","territry_not_assigned_any_continent.map"};
	ClassLoader loader;
	
	/**
	 * This method is called before executing any methods of the test class.
	 */
	@BeforeClass
	public static void beforeClass() {
		mapReader = new MapReader();
	}
	
	/**
	 * This method is executed before every method of the class.
	 */
	@Before
	public void beforeMethod() throws IOException {
		loader = getClass().getClassLoader();
	}
	
	/**
	 * This method tests the vaid map.
	 * @throws InvalidMapException
	 */
	@Test
	public void testValidMap() throws InvalidMapException {
		file = new File(loader.getResource("valid.map").getFile());
		Map map = mapReader.readMapFile(file);
		assertEquals(map.getContinents().size(),8);
	}
	
	/**
	 * This method test the map whose continent are not conntected graph formed by territories
	 * @throws InvalidMapException InvalidMapException
	 */
	@Test (expected=InvalidMapException.class)
	public void checkForContinentNotBeingSubgraph() throws InvalidMapException {
		System.out.println("");
		file = new File(loader.getResource(invalidFiles[0]).getFile());
		mapReader.readMapFile(file);
	}
	
	/**
	 * This method tests the map in which territories are nto mapped mutually.
	 * @throws InvalidMapException InvalidMapException
	 */
	@Test (expected=InvalidMapException.class)
	public void checkForTerritoryNotMappedMutually() throws InvalidMapException {
		file = new File(loader.getResource(invalidFiles[1]).getFile());
		mapReader.readMapFile(file);
	}
	
	/**
	 * This method tests the map in which territories is mapped with two continents.
	 * @throws InvalidMapException InvalidMapException
	 */
	@Test (expected=InvalidMapException.class)
	public void checkForTerritoryWithTwoContinents() throws InvalidMapException {
		file = new File(loader.getResource(invalidFiles[2]).getFile());
		mapReader.readMapFile(file);
	}
	
	/**
	 * This method tests the map which has territories without continents.
	 * @throws InvalidMapException InvalidMapException
	 */
	@Test (expected=InvalidMapException.class)
	public void checkForTerritoryWithoutContinents() throws InvalidMapException {
		file = new File(loader.getResource(invalidFiles[3]).getFile());
		mapReader.readMapFile(file);
	}
	
	/**
	 * This method tests the map in which territory has no continents.
	 * @throws InvalidMapException InvalidMapException
	 */
	@Test (expected=InvalidMapException.class)
	public void checkForTerritoryNotAssignedToAnyContinents() throws InvalidMapException {
		file = new File(loader.getResource(invalidFiles[4]).getFile());
		mapReader.readMapFile(file);
	}

}
