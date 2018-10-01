package com.risk6441.models;

import java.util.List;

/**
 * @author Nirav
 * This class defines Territory and its properties such as its coordinates, the continent to which territory
 * belongs, its adjacent territories and whether its been assigned to any player or not.
 * @see Continent 
 */
public class Territory {
	private String name;
	private int xCoordinate;
	private int yCoordinate;
	private Continent belongToContinent;
	private List<String> adjTerritories;
	private List<Territory> adjacentTerritories;
	private boolean isProcessed;
	
	
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * @param adjTerritories list of adjacent territories names
	 * @param adjacentTerritories list of object of adjacent territories
	 */
	public Territory(List<String> adjTerritories, List<Territory> adjacentTerritories) {
		this.adjTerritories = adjTerritories;
		this.adjacentTerritories = adjacentTerritories;
	}

	/**
	 * 
	 */
	public Territory() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * @return the xCoordinate
	 */
	public int getxCoordinate() {
		return xCoordinate;
	}
	
	/**
	 * @param xCoordinate the xCoordinate to set
	 */
	public void setxCoordinate(int xCoordinate) {
		this.xCoordinate = xCoordinate;
	}
	
	/**
	 * @return the yCoordinate
	 */
	public int getyCoordinate() {
		return yCoordinate;
	}
	
	/**
	 * @param yCoordinate the yCoordinate to set
	 */
	public void setyCoordinate(int yCoordinate) {
		this.yCoordinate = yCoordinate;
	}
	
	/**
	 * @return the belongToContinent
	 */
	public Continent getBelongToContinent() {
		return belongToContinent;
	}
	
	/**
	 * @param belongToContinent the belongToContinent to set
	 */
	public void setBelongToContinent(Continent belongToContinent) {
		this.belongToContinent = belongToContinent;
	}
	
	/**
	 * @return the adjTerritories
	 */
	public List<String> getAdjTerritories() {
		return adjTerritories;
	}
	
	/**
	 * @param adjTerritories the adjTerritories to set
	 */
	public void setAdjTerritories(List<String> adjTerritories) {
		this.adjTerritories = adjTerritories;
	}
	
	/**
	 * @return the adjacentTerritories
	 */
	public List<Territory> getAdjacentTerritories() {
		return adjacentTerritories;
	}
	
	/**
	 * @param adjacentTerritories the adjacentTerritories to set
	 */
	public void setAdjacentTerritories(List<Territory> adjacentTerritories) {
		this.adjacentTerritories = adjacentTerritories;
	}
	
	/**
	 * @return the isProcessed
	 */
	public boolean isProcessed() {
		return isProcessed;
	}
	
	/**
	 * @param isProcessed the isProcessed to set
	 */
	public void setProcessed(boolean isProcessed) {
		this.isProcessed = isProcessed;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Territory [name=" + name +"]";
	}
	
	
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {

		if (!(obj instanceof Territory)) {
			return false;
		}
		
		if (obj == this) {
			return true;
		}

		Territory t = (Territory) obj;
		return t.getName().equalsIgnoreCase(name);
	}
	
}
