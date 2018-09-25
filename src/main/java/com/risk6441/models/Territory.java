/**
 * 
 */
package com.risk6441.models;

import java.util.List;

/**
 * @author Nirav
 * This class defines Territory and its properties such as its coordinates, the continent to which territory
 * belongs, its adjacent territories and wheather its been assigned to any player or not. 
 */
public class Territory {
	private String name;
	private int xCoordinate;
	private int yCoordinate;
	private Continent belongToContinent;
	private List<String> adjTerritories;
	private List<Territory> adjacentTerritories;
	private boolean isProcessed;
	
	
}
