/**
 * 
 */
package com.risk6441.models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Observable;

import com.risk6441.entity.Territory;
import com.risk6441.gameutils.GameUtils;

import javafx.scene.control.Button;
import javafx.scene.control.Label;

/**
 * @author Nirav
 *
 */
public class DiceModel extends Observable{
	/**
	 * The @attackingTerritory .
	 */
	private Territory attackingTerritory;

	/**
	 * The @defendingTerritory .
	 */
	private Territory defendingTerritory;

	/**
	 * The @attackerDiceValues .
	 */
	private List<Integer> attackerDiceValues;

	/**
	 * The @defenderDiceValues .
	 */
	private List<Integer> defenderDiceValues;

	/**
	 * The @numOfTerritoriesWon .
	 */
	private int numOfTerritoriesWon;
	
	/**
	 * Constructor for DiceModel
	 * 
	 * @param attackingTerritory
	 *            reference to get details about attacking territory
	 * 
	 * @param defendingTerritory
	 *            reference to get details about defending territory
	 */
	public DiceModel(Territory attackingTerritory, Territory defendingTerritory) {
		this.attackingTerritory = attackingTerritory;
		this.defendingTerritory = defendingTerritory;
		attackerDiceValues = new ArrayList<>();
		defenderDiceValues = new ArrayList<>();
		numOfTerritoriesWon = 0;

	}
	
	/**
	 * Get Play Result after the dice is thrown
	 * 
	 * @return List list of players.
	 */
	public List<String> getPlayResultAfterDiceThrown() {
		List<String> playResult = new ArrayList<>();
		Collections.sort(attackerDiceValues, Collections.reverseOrder());
		Collections.sort(defenderDiceValues, Collections.reverseOrder());

		for (Integer defenderDiceValue : defenderDiceValues) {
			for (Integer attackerDiceValue : attackerDiceValues) {
				updateArmiesAfterAttack(defenderDiceValue, attackerDiceValue, playResult);
				break;
			}
			if (attackerDiceValues.size() >= 1) {
				attackerDiceValues.remove(0);
			}
		}
		return playResult;
	}
	
	/**
	 * Update Armies After attack
	 * 
	 * @param defenderDiceValue
	 *            Integer defenderDiceValue
	 * @param attackerDiceValue
	 *            Integer attackerDiceValue
	 * @param playResult
	 *            List playResult
	 */
	public void updateArmiesAfterAttack(Integer defenderDiceValue, Integer attackerDiceValue, List<String> playResult) {
		if (attackerDiceValue.compareTo(defenderDiceValue) == 0) {
			playResult.add("Attacker lost 1 army.");
			if (attackingTerritory.getArmy() > 1) {
				attackingTerritory.setArmy(attackingTerritory.getArmy() - 1);
			}
		} else if (attackerDiceValue.compareTo(defenderDiceValue) > 0) {
			playResult.add("Defender lost 1 army");
			if (defendingTerritory.getArmy() > 0) {
				defendingTerritory.setArmy(defendingTerritory.getArmy() - 1);
			}
		} else {
			playResult.add("Attacker lost 1 army.");
			if (attackingTerritory.getArmy() > 1) {
				attackingTerritory.setArmy(attackingTerritory.getArmy() - 1);
			}
		}
	}
	
	/**
	 * Cancel Dice Roll
	 */
	public void cancelDiceRoll() {
		setChanged();
		notifyObservers("rollDiceComplete");
	}

	
	/**
	 * Move All Armies
	 */
	public void moveAllArmies() {
		int attckingArmies = getAttackingTerritory().getArmy();
		getAttackingTerritory().setArmy(1);
		getDefendingTerritory().setArmy(attckingArmies - 1);
		swapOwnershipOfTerritory();
		setChanged();
		notifyObservers("rollDiceComplete");
	}
	
	
	/**
	 * Skip Move Army
	 */
	public void skipMoveArmy() {
		int attckingArmies = getAttackingTerritory().getArmy();
		getAttackingTerritory().setArmy(attckingArmies - 1);
		getDefendingTerritory().setArmy(1);
		swapOwnershipOfTerritory();
		setChanged();
		notifyObservers("rollDiceComplete");
	}
	
	
	/**
	 * Move the desired number of armies.
	 * 
	 * @param armiesToMove
	 *            armies to move
	 * @param message
	 *            message
	 * @param moveArmies
	 *            movearmies button refrence
	 */
	public void moveArmies(int armiesToMove, Label message, Button moveArmies) {
		int currentArmies = getAttackingTerritory().getArmy();
		if (currentArmies <= armiesToMove) {
			message.setVisible(true);
			message.setText("You can move a miximum of " + (currentArmies - 1) + " armies");
			return;
		} else {
			getAttackingTerritory().setArmy(currentArmies - armiesToMove);
			getDefendingTerritory().setArmy(armiesToMove);
			swapOwnershipOfTerritory();
			GameUtils.closeScreen(moveArmies);
			setChanged();
			notifyObservers("rollDiceComplete");
		}
	}

	
	/**
	 * Reassign Territory
	 */
	public void swapOwnershipOfTerritory() {
		List<Territory> defendersTerritories = defendingTerritory.getPlayer().getAssignedTerritory();
		defendersTerritories.remove(defendingTerritory);

		defendingTerritory.setPlayer(attackingTerritory.getPlayer());
		attackingTerritory.getPlayer().getAssignedTerritory().add(defendingTerritory);

	}
	
	/**
	 * @return Int randomNumber between 1 to 6
	 */
	public int randomNumber() {
		return (int) (Math.random() * 6) + 1;
	}

	/**
	 * Check if more dice role available
	 * @return diceRollAvailable
	 */
	public boolean moreDiceRollAvailable() {
		if (attackingTerritory.getArmy() < 2 || defendingTerritory.getArmy() <= 0) {
			return false;
		}else
			return true;
	}


	/**
	 * Get Attacking Territory
	 * @return Territory attacking territory
	 */
	public Territory getAttackingTerritory() {
		return attackingTerritory;
	}

	/**
	 * Set Attacking Territory
	 * @param attackingTerritory
	 *            the attackingTerritory to set
	 */
	public void setAttackingTerritory(Territory attackingTerritory) {
		this.attackingTerritory = attackingTerritory;
	}

	/**
	 * Get Defending Territory
	 * @return the defendingTerritory
	 */
	public Territory getDefendingTerritory() {
		return defendingTerritory;
	}

	/**
	 * Set Defending Territory
	 * @param defendingTerritory
	 *            the defendingTerritory to set
	 */
	public void setDefendingTerritory(Territory defendingTerritory) {
		this.defendingTerritory = defendingTerritory;
	}

	/**
	 * Get Attacker Dice Values
	 * @return the attackerDiceValues
	 */
	public List<Integer> getAttackerDiceValues() {
		return attackerDiceValues;
	}

	/**
	 * Set Attacker Dice Values
	 * @param attackerDiceValues
	 *            the attackerDiceValues to set
	 */
	public void setAttackerDiceValues(List<Integer> attackerDiceValues) {
		this.attackerDiceValues = attackerDiceValues;
	}

	/**
	 * Get Defender Dice Values
	 * @return defenderDiceValues
	 */
	public List<Integer> getDefenderDiceValues() {
		return defenderDiceValues;
	}

	/**
	 * Set Defender Dice Values
	 * @param defenderDiceValues
	 *            the defenderDiceValues to set
	 */
	public void setDefenderDiceValues(List<Integer> defenderDiceValues) {
		this.defenderDiceValues = defenderDiceValues;
	}

	/**
	 * Get Number Of Territories Won
	 * @return the numOfTerritoriesWon
	 */
	public int getNumOfTerritoriesWon() {
		return numOfTerritoriesWon;
	}

	/**
	 * Set Number Of Territories Won
	 * @param numOfTerritoriesWon the numOfTerritoriesWon to set
	 */
	public void setNumOfTerritoriesWon(int numOfTerritoriesWon) {
		this.numOfTerritoriesWon = numOfTerritoriesWon;
	}
}
