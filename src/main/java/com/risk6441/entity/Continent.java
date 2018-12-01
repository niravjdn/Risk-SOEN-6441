package com.risk6441.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * This class defines Continent and its properties such as name, control value, list of territories it has.
 * @author Nirav
 * @see Territory
 */
public class Continent implements Serializable{
	
	/**
	 * The serial ID
	 */
	private static final long serialVersionUID = -8840145817364335110L;
	
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
	 * Continent default constructor.
	 */
	public Continent() {
		this.territories = new ArrayList<>();
		this.territoryMap = new HashMap<>();
	}

	/**
	 * This method returns the name of the continent.
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * This method sets the name of the continent.
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * This method gets the control value of the continent.
	 * @return the value
	 */
	public int getValue() {
		return value;
	}
	
	/**
	 * This method sets the control value of the continent.
	 * @param value the value to set
	 */
	public void setValue(int value) {
		this.value = value;
	}
	
	/**
	 * This  method gets the territories in the continent.
	 * @return the territoryMap
	 */
	public HashMap<String, Territory> getTerritoryMap() {
		return territoryMap;
	}
	
	/**
	 * This method sets the territories in the continent.
	 * @param territoryMap the territoryMap to set
	 */
	public void setTerritoryMap(HashMap<String, Territory> territoryMap) {
		this.territoryMap = territoryMap;
	}
	
	/**
	 * This method gets the territories of the continent.
	 * @return the territories
	 */
	public List<Territory> getTerritories() {
		return territories;
	}
	
	/**
	 * This method sets the territories of the continent.
	 * @param territories the territories to set
	 */
	public void setTerritories(List<Territory> territories) {
		this.territories = territories;
	}

	
	
	/**
	 * This method checks whether the continent is visited or not.
	 * @return the isVisited
	 */
	public boolean isVisited() {
		return isVisited;
	}

	/**
	 * This sets the continent to have been visited.
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
