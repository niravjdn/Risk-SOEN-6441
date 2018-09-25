/**
 * 
 */
package com.risk6441.models;

import java.util.HashMap;
import java.util.List;


/**
 * @author Nirav
 * This class defines Continent and its properties such as name, control value, list of territories it has.
 * 
 */
public class Continent {
	private String name;
	private String value;
	private HashMap<String, Territory> territoryMap;
	private List<Territory> territories;
}
