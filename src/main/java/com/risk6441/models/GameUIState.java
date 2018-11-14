/**
 * 
 */
package com.risk6441.models;

import java.io.Serializable;

/**
 * @author Nirav
 *
 */
public class GameUIState implements Serializable{

	/**
	 * The serial ID
	 */
	private static final long serialVersionUID = 3014497401525436669L;
	
	public boolean isPlaceArmyEnable = false;
	
	public boolean isReinforcemetnEnable = false;
	
	public boolean isCardsEnable = false;
	
	public boolean isNoMoreAttackEnable = false;
	
	public boolean isFortificationEnable = false;
	
	public boolean isEndTurnEnable = false;
	
}
