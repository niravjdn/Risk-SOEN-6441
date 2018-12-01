package com.risk6441.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Nirav
 * This class defines Territory and its properties such as its coordinates, the continent to which territory
 * belongs, its adjacent territories and whether its been assigned to any player or not.
 * @see Continent 
 */
public class Territory implements Serializable{
	
	
	/**
	 * The serial ID
	 */
	private static final long serialVersionUID = -2741974265396284180L;
	
	private String name;
	private int xCoordinate;
	private int yCoordinate;
	private Continent belongToContinent;
	private List<String> adjTerritories;
	private Player player;
	private List<Territory> adjacentTerritories;
	private boolean isProcessed;
	/**
	 * Get the player.
	 * @return the player
	 */
	public Player getPlayer() {
		return player;
	}

	/**
	 * Set the player.
	 * @param player the player to set
	 */
	public void setPlayer(Player player) {
		this.player = player;
	}

	/**
	 * Get the armies of the territory.
	 * @return the army
	 */
	public int getArmy() {
		return army;
	}

	/**
	 * Set the armies of the territory.
	 * @param army the army to set
	 */
	public void setArmy(int army) {
		if(army > 5000) {
			this.army = 5000;
			return;
		}
		this.army = Math.abs(army);
	}


	private int army;
	
	/**
	 * Get name of the territory.
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Pararmeterised constructor for territory.
	 * @param adjTerritories list of adjacent territories names
	 * @param adjacentTerritories list of object of adjacent territories
	 */
	public Territory(List<String> adjTerritories, List<Territory> adjacentTerritories) {
		this.adjTerritories = adjTerritories;
		this.adjacentTerritories = adjacentTerritories;
	}

	/**
	 * Default constructor for territory.
	 */
	public Territory() {
		adjTerritories = new ArrayList<>();
		adjacentTerritories = new ArrayList<>();
	}

	/**
	 * Sets the name of the territory.
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * Get the X coordinate of the territory.
	 * @return the xCoordinate
	 */
	public int getxCoordinate() {
		return xCoordinate;
	}
	
	/**
	 * Set the X coordinate of the territory.
	 * @param xCoordinate the xCoordinate to set
	 */
	public void setxCoordinate(int xCoordinate) {
		this.xCoordinate = xCoordinate;
	}
	
	/**
	 * Get the y coordinate of the territory.
	 * @return the yCoordinate
	 */
	public int getyCoordinate() {
		return yCoordinate;
	}
	
	/**
	 * Set the y coordinate of the territory.
	 * @param yCoordinate the yCoordinate to set
	 */
	public void setyCoordinate(int yCoordinate) {
		this.yCoordinate = yCoordinate;
	}
	
	/**
	 * Returns continent which belongs to the territory.
	 * @return the belongToContinent
	 */
	public Continent getBelongToContinent() {
		return belongToContinent;
	}
	
	/**
	 * Sets the continent for the territory.
	 * @param belongToContinent the belongToContinent to set
	 */
	public void setBelongToContinent(Continent belongToContinent) {
		this.belongToContinent = belongToContinent;
	}
	
	/**
	 * Return the adjacent territories of terrritory.
	 * @return the adjTerritories
	 */
	public List<String> getAdjTerritories() {
		return adjTerritories;
	}
	
	/**
	 * Set the adjacent territories to the territory.
	 * @param adjTerritories the adjTerritories to set
	 */
	public void setAdjTerritories(List<String> adjTerritories) {
		this.adjTerritories = adjTerritories;
	}
	
	/**
	 * Returns the adjacent territories to the territories.
	 * @return the adjacentTerritories
	 */
	public List<Territory> getAdjacentTerritories() {
		return adjacentTerritories;
	}
	
	/**
	 * Set adjacent territories to the territory.
	 * @param adjacentTerritories the adjacentTerritories to set
	 */
	public void setAdjacentTerritories(List<Territory> adjacentTerritories) {
		this.adjacentTerritories = adjacentTerritories;
	}
	
	/**
	 * Checks whether the territory is processed.
	 * @return isProcessed returns boolean value whether a territory is processed or not
	 */
	public boolean isProcessed() {
		return isProcessed;
	}
	
	/**
	 * Set the territory as processed.
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
