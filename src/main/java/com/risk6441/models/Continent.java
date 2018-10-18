package com.risk6441.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;



/**
 * This class defines Continent and its properties such as name, control value, list of territories it has.
 * @author Nirav
 * @see Territory
 */
public class Continent {
	private String name;
	private int value;
	private HashMap<String, Territory> territoryMap;
	private List<Territory> territories;
	private boolean isVisited = false;
	
	
	/**
	 * @param name name of the continent
	 * @param value control value of the continent
	 */
	public Continent(String name, int value) {
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
		this.territories = new ArrayList<>();
		this.territoryMap = new HashMap<>();
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
	public int getValue() {
		return value;
	}
	
	/**
	 * @param value the value to set
	 */
	public void setValue(int value) {
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

	
	
	/**
	 * @return the isVisited
	 */
	public boolean isVisited() {
		return isVisited;
	}

	/**
	 * @param isVisited the isVisited to set
	 */
	public void setVisited(boolean isVisited) {
		this.isVisited = isVisited;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Continent [name=" + name + ", value=" + value + ", territories=" + territories + "]";
	}
	
	
	
	
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {

		if (!(obj instanceof Continent)) {
			return false;
		}
		
		if (obj == this) {
			return true;
		}

		Continent continent = (Continent) obj;
		return continent.getName().equalsIgnoreCase(name);
	}
	
}
