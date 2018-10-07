package com.risk6441.models;

import java.util.ArrayList;
import java.util.List;


/**
 * @author Nirav
 * This class defines Player and its properties such as id, name, armies and number of territories owned by player
 */
public class Player {
	
	private int id;
	private String name;
	private int armies;
	private List<Territory> assignedTerritory;
	
	
	
	
	/**
	 * @param id id of the player eg. 1
	 * @param name name of the player
	 */
	public Player(int id, String name) {
		super();
		this.id = id;
		this.name = name;
		this.assignedTerritory = new ArrayList<Territory>();
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}
	
	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
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
	 * @return the armies
	 */
	public int getArmies() {
		return armies;
	}
	
	/**
	 * @param armies the armies to set
	 */
	public void setArmies(int armies) {
		this.armies = armies;
	}
	
	/**
	 * @return the assignedTerritory
	 */
	public List<Territory> getAssignedTerritory() {
		return assignedTerritory;
	}
	
	/**
	 * @param assignedTerritory the assignedTerritory to set
	 */
	public void setAssignedTerritory(List<Territory> assignedTerritory) {
		this.assignedTerritory = assignedTerritory;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {

		if (obj == this) {
			return true;
		}

		if (!(obj instanceof Player)) {
			return false;
		}

		Player player = (Player) obj;
		return player.getName().equalsIgnoreCase(name);
	}
	

}
