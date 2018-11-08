/**
 * 
 */
package com.risk6441.models;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.risk6441.entity.Territory;

/**
 * @author Nirav
 *
 */
public class DiceModelTest {

	
	
	private static Territory attackingTerritory;
	private static Territory defendingTerritory;
	private static ArrayList<Integer> attackerDiceValuesList;
	private static ArrayList<Integer> defenderDiceValuesList;
	private static DiceModel diceModel;

	static int defenderDiceNumber, attackerDiceNumber;
	

	/**
	 * This method executed before all the methods of the class.
	 */
	@BeforeClass
	public static void beforeClass() {
		attackingTerritory = new Territory();
		defendingTerritory = new Territory();	
		attackerDiceValuesList = new ArrayList<Integer>();
		defenderDiceValuesList = new ArrayList<Integer>();
		diceModel = new DiceModel(attackingTerritory, defendingTerritory);
	}	

	/**
	 * This method is executed before every method of the class.
	 */
	@Before
	public void beforeTest() {
		attackingTerritory.setArmy(3);
		defendingTerritory.setArmy(3);
		attackerDiceValuesList.add(diceModel.randomNumber());
		defenderDiceValuesList.add(diceModel.randomNumber());
		attackerDiceNumber = attackerDiceValuesList.get(0);
		defenderDiceNumber = defenderDiceValuesList.get(0);
	}
	
	/**
	 * This method is used to check whether more dice rolls are available.
	 */
	@Test
	public void moreDiceRollAvailable() {
		assertTrue(attackingTerritory.getArmy() > 2);
		assertTrue(defendingTerritory.getArmy() > 0);
		assertTrue(diceModel.isMoreDiceRollAvailable());
	}
	
	
	/**
	 * This method checks whether more dice rolls are available if attacking territory has only one army left.
	 */
	@Test
	public void moreDiceRollAvailablePassWrongValues() {
		attackingTerritory.setArmy(1);
		defendingTerritory.setArmy(0);
		assertFalse(attackingTerritory.getArmy() > 2);
		assertFalse(defendingTerritory.getArmy() > 0);
		assertFalse(diceModel.isMoreDiceRollAvailable());
	}
	
	
	/**
	 * This method performs a attack and reduce army of both player by one and checks that at least one player should have lost the match.
	 */
	@Test
	public void updateArmiesAfterAttackArmiesTest() {
		List<String> playResult = new ArrayList<>();
		int checkAttackerArmies = attackingTerritory.getArmy() - 1;
		int checkDefenderArmies = defendingTerritory.getArmy() - 1;	
		diceModel.updateArmiesAfterAttackFinished(defenderDiceNumber, attackerDiceNumber, playResult);
		assertTrue(attackingTerritory.getArmy() ==  checkAttackerArmies||
				 defendingTerritory.getArmy() == checkDefenderArmies);
	}
	
	
	/**
	 * This method performs a attack and reduce army of both player by one and checks that at least one player should have lost the match.
	 * This method checks the result array which is a array of string.
	 */
	@Test
	public void getPlayResultAfterDiceThrown() {
		List<String> playResult = new ArrayList<>();
		diceModel.updateArmiesAfterAttackFinished(defenderDiceNumber, attackerDiceNumber, playResult);
		assertTrue(playResult.get(0).equals("Defender lost 1 army") ||
				playResult.get(0).equals("Attacker lost 1 army."));
	}	
}
