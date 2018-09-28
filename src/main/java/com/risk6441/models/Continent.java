/**
 * 
 */
package com.risk6441.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * @author Nirav
 * This class defines Continent and its properties such as name, control value, list of territories it has.
 * @see Territory
 */
public class Continent {
	private String name;
	private String value;
	private HashMap<String, Territory> territoryMap;
	private List<Territory> territories;
	
	
	
	/**
	 * @param name
	 * @param value
	 */
	public Continent(String name, String value) {
		super();
		this.name = name;
		this.value = value;
		this.territoryMap = new HashMap<String, Territory>();
		this.territories = new ArrayList<Territory>();
	}

	/**
	 * 
	 */
	public Continent() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}
	
	/**
	 * @param value the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}
	
	/**
	 * @return the territoryMap
	 */
	public HashMap<String, Territory> getTerritoryMap() {
		return territoryMap;
	}
	
	/**
	 * @param territoryMap the territoryMap to set
	 */
	public void setTerritoryMap(HashMap<String, Territory> territoryMap) {
		this.territoryMap = territoryMap;
	}
	
	/**
	 * @return the territories
	 */
	public List<Territory> getTerritories() {
		return territories;
	}
	
	/**
	 * @param territories the territories to set
	 */
	public void setTerritories(List<Territory> territories) {
		this.territories = territories;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Continent [name=" + name + ", value=" + value + ", territories=" + territories + "]";
	}
	
	
	//generate equals if you need in future
	
	
}
