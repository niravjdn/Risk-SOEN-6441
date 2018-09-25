/**
 * 
 */
package com.risk6441.models;

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

}
