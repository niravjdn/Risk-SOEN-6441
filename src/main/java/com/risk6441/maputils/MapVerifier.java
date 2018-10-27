/**
 * 
 */
package com.risk6441.maputils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import com.risk6441.entity.Continent;
import com.risk6441.entity.Map;
import com.risk6441.entity.Territory;
import com.risk6441.exception.InvalidMapException;

/**
 * This class validates the map. 
 * @author Nirav
 *
 */
public class MapVerifier {
	
	static String message = "";

	/**
	 * This method validates the map.
	 * @param map map object for verifying map
	 * @throws InvalidMapException if map has some errors
	 */
	public static void verifyMap(Map map) throws InvalidMapException{
		if(map == null) {
			throw new InvalidMapException("Map is not valid. It's null");
		}else {
			if(map.getContinents().size() < 1) {
				throw new InvalidMapException("At least one continent must be there in the map.");	
			}else {
				//verify that map is subgraph of continents, if yes then check for continent is subgraph of territories
				verifyContinents(map);
				
				//check if map is a subgraph of continents
				if(!isMapConnectedGraph(map)) {
					throw new InvalidMapException("A Continent should be a subgraph in the map. A Map should be connected graph formed by continents.");
				}
				
				checkTerritoryBelongToOnlyOneContinent(map);
			}
		}
	}
	
	/**
	 * This method verifies the continents.
	 * @param map map object to verify continents of the map
	 * @throws InvalidMapException throws if map has some errors
	 */
	public static void verifyContinents(Map map) throws InvalidMapException {
		
		for(Continent continent : map.getContinents()) {
			if(continent.getTerritories().size() < 2) {
				throw new InvalidMapException("At least two territory should be there in continent.");
			}
			
			//it's verified that map is a subgraph of continents. now check that continent is a subgraph of territories.
			for(Territory territory : continent.getTerritories()) {
				verifyTerritory(territory,map);
			}
			
			//check if continent is connected graph formed by territories
			if(!isContinentConnectedGraph(continent, map)) {
				throw new InvalidMapException(message+"The Continent "+continent.getName()+" is not connected by its territories. A Continent should be a connected graph formed by territories in the map.");
			}
		}
		
	}

	/**
	 * This method checks that the continents are connected or not.
	 * @param continent
	 * 			continent to be verified
	 * @param map
	 * 			object of the map
	 * @return
	 * 		  return true if continent forms a connected map.
	 */
	public static boolean isContinentConnectedGraph(Continent continent,Map map) {
		bfsTerritory(continent.getTerritories().get(0), map);
		boolean returnValue = true;
		for(Territory t : continent.getTerritories()) {
			if(t.isProcessed() == false) {
				t.setProcessed(false);
				message = t.getName()+" is not forming connected graph inside continent "+continent.getName()+".";
				returnValue = false;
				break;
			}
		}
		
		for(Territory t : continent.getTerritories()) {
			t.setProcessed(false);
		}
		
		
		return returnValue;

	}
	
	/**
	 * This method traverse the territories in BFS Manner.
	 * @param territory
	 * 					territory to be traversed in bfs
	 * @param map
	 * 			  map object
	 */
	public static void bfsTerritory(Territory territory, Map map) {

		if(territory.isProcessed() == true) {
			return;
		}

		territory.setProcessed(true);

		for(Territory t : territory.getAdjacentTerritories()){
			if((t.getBelongToContinent() == territory.getBelongToContinent()) && t.isProcessed() == false)
				bfsTerritory(t, map);
		}
				
	}
	

	/**
	 * This method checks that the territory is connected or not.
	 * @param territory
	 * 				  territory to be verified
	 * @param map
	 * 			 object of the map
	 * @throws InvalidMapException
	 * 						     throws InvalidMapException if map is not valid
	 */
	private static void verifyTerritory(Territory territory, Map map) throws InvalidMapException {
		List<Territory> adjTerrList = territory.getAdjacentTerritories();
		
		if((adjTerrList == null) || (adjTerrList.size() < 1)) {
			throw new InvalidMapException("Territory: "+territory.getName()+" must have atleast one adjacent territory.");
		}else  {
			for(Territory adjTerr : adjTerrList) {
				if(!adjTerr.getAdjacentTerritories().contains(territory)) {
					throw new InvalidMapException("Territory "+adjTerr.getName()+" is not mapped with its adjacent territory "+territory.getName());
				}
			}
		}
	}

	
	/**
	 * This method checks that Continents form a connected graph(A Map).
	 * @param map
	 * 			 object of the map
	 * @return 
	 * 			true if map is a connected graph
	 */
	public static boolean isMapConnectedGraph(Map map) {
		System.out.println("Inside is map connected");
		if(map.getContinents().size()<2) {
			return false;
		}
		
		bfsContinent(map.getContinents().get(0), map);
		
		boolean returnValue = true;
		for(Continent continent : map.getContinents()) {
			if(continent.isVisited() == false) {
				System.out.println(continent.getName()+"xxxxxxxxxxxxxx");
				returnValue = false;
				break;
			}
		}
		
		for(Continent continent : map.getContinents()) {
			continent.setVisited(false);
		}
		
		return returnValue;
		

	}
	
	/**
	 * This method traverse the continents in BFS Manner.
	 * @param continent
	 * 			       continent to be traversed in bfs
	 * @param map
	 * 			 map object
	 */
	public static void bfsContinent(Continent continent, Map map) {

		if(continent.isVisited() == true) {
			return;
		}

		continent.setVisited(true);

		System.out.println("cont in dfs 1"+continent.getName());
		for(Continent c : getAdjacentContinents(continent, map)){
			System.out.println("inside adjCont loop");
			if(c.isVisited() == false)
				bfsContinent(c, map);
		}
				
	}
	
	/**
	 * This method returns the adjacent continent as a list of particular continent.
	 * @param continent
	 * 				   continent whose adjacent territories to be found
	 * @param map
	 * 			 map object
	 * @return
	 * 		the adjacent continent as a list of particular continent.
	 */
	public static List<Continent> getAdjacentContinents(Continent continent, Map map){
		List<Continent> adjacentContinents = new ArrayList<>();
		
		HashSet<Territory> adjTerrMasterSet = new HashSet<>();
		for(Territory territory : continent.getTerritories()) {
			adjTerrMasterSet.addAll(territory.getAdjacentTerritories());
		}
		
		System.out.println(adjTerrMasterSet);
		
		for(Continent otherCont : map.getContinents()) {
			if(!continent.equals(otherCont)) {
				//procees if there is any relation between two continents
				//returns true if both are tottaly different
				if(!Collections.disjoint(adjTerrMasterSet, otherCont.getTerritories())) {
					System.out.println("Inside disjoint");
					//some territories are common
					adjacentContinents.add(otherCont);
				}
			}
		}
		return adjacentContinents;
	}
	
	/**
	 * This method checks whether territory belongs to only one continent or not.
	 * @param map
	 * 			 map object
	 * @throws InvalidMapException
	 * 							  throws InvalidMapException if map is not valid.
	 */
	public static void checkTerritoryBelongToOnlyOneContinent(Map map) throws InvalidMapException {
		HashMap<Territory, Integer> territoryBelongToContinentCount = new HashMap<>();

		for (Continent continent : map.getContinents()) {
			for (Territory territory : continent.getTerritories()) {
				if (!territoryBelongToContinentCount.containsKey(territory)) {
					territoryBelongToContinentCount.put(territory, 1);
				} else {
					throw new InvalidMapException("Territry: "+territory.getName()+"must belong to only one continents.");
				}
			}
		}
	}
	
}
