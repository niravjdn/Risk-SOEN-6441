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
 * @author Nirav
 *
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
	
	@Before
	public void beforeMethod() throws IOException {
		loader = getClass().getClassLoader();
	}
	
	@Test
	public void testValidMap() throws InvalidMapException {
		file = new File(loader.getResource("valid.map").getFile());
		Map map = mapReader.readMapFile(file);
		assertEquals(map.getContinents().size(),8);
	}
	
	@Test (expected=InvalidMapException.class)
	public void checkForContinentNotBeingSubgraph() throws InvalidMapException {
		System.out.println("");
		file = new File(loader.getResource(invalidFiles[0]).getFile());
		mapReader.readMapFile(file);
	}

	
	
	@Test (expected=InvalidMapException.class)
	public void checkForTerritoryNotMappedMutually() throws InvalidMapException {
		file = new File(loader.getResource(invalidFiles[1]).getFile());
		mapReader.readMapFile(file);
	}
	
	
	
	
	@Test (expected=InvalidMapException.class)
	public void checkForTerritoryWithTwoContinents() throws InvalidMapException {
		file = new File(loader.getResource(invalidFiles[2]).getFile());
		mapReader.readMapFile(file);
	}
	
	
	
	@Test (expected=InvalidMapException.class)
	public void checkForTerritoryWithoutContinents() throws InvalidMapException {
		file = new File(loader.getResource(invalidFiles[3]).getFile());
		mapReader.readMapFile(file);
	
	}
	
	@Test (expected=InvalidMapException.class)
	public void checkForTerritoryNotAssignedToAnyContinents() throws InvalidMapException {
		file = new File(loader.getResource(invalidFiles[4]).getFile());
		mapReader.readMapFile(file);
	
	}

}
