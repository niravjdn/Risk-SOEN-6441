package com.risk6441.maputils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.risk6441.exception.InvalidMapException;
import com.risk6441.models.Continent;
import com.risk6441.models.Map;
import com.risk6441.models.Player;
import com.risk6441.models.Territory;

/**
 * @author Nirav
 *
 */

public class GameUtilsTest {

	static Map map;
	static Continent continent;
	String continentName = "Asia";
	String terrName1 = "India";
	String terrName2 = "Canada";
	int controlValue1 = 5;
	static Territory terr1;
	static Territory terr2;
	static Territory adjTerritory;
	int x1=1;
	int y1=1;
	int x2=2;
	int y2=2;
	static Player player;
	
	List<Continent> listOfContinents = new ArrayList<>();	
	List<Territory> listOfTerritories = new ArrayList<>();
	
	String mapAuthor = "Robert";
	String mapImage = "world.map";
	String mapWrap = "no";
	String mapScroll = "horizontal";
	String mapWarn = "yes";
	
	@BeforeClass
	public static void beforeClass() {
		continent = new Continent();
		terr1 = new Territory();
		terr2 = new Territory();
		map = new Map();
		player = new Player(1, "Nirav");
	}
	
	@Before
	public void before() {
		continent.setName(continentName);
		continent.setValue(controlValue1);
		
		terr1.setName(terrName1);
		terr1.setBelongToContinent(continent);
		terr2.setName(terrName2);
		terr2.setBelongToContinent(continent);		
		
		terr2.getAdjacentTerritories().add(terr1);
		terr1.setAdjacentTerritories(terr2.getAdjacentTerritories());		
		terr1.getAdjacentTerritories().add(terr1);
		terr2.setAdjacentTerritories(terr1.getAdjacentTerritories());
		
		listOfContinents.add(continent);
		map.setContinents(listOfContinents);
		
		player.setArmies(99);
		
		listOfTerritories.add(terr1);
		player.setAssignedTerritory(listOfTerritories);
	}
	
	@Test
	public void countReinforcementArmiesCaseOne() {		
		Player returnedPlayer = GameUtils.countReinforcementArmies(map, player);
		Assert.assertEquals(returnedPlayer.getArmies(), 107);
	}
	
	@Test
	public void calculateReinforcementArmiesCaseTwo() {	
		Continent newContinent = new Continent();
		newContinent.setName("North America");
		newContinent.setValue(7);
		listOfContinents.add(newContinent);
		map.setContinents(listOfContinents);
		Player returnedPlayer = GameUtils.countReinforcementArmies(map, player);
		Assert.assertEquals(returnedPlayer.getArmies(), 114);
	}
	
	@Test
	public void calculateReinforcementArmiesCaseThree() {		
		Territory terr = new Territory();
		terr.setName("Russia");
		terr.setBelongToContinent(continent);
		listOfTerritories.add(terr);
		
		terr.setName("Pakistan");
		terr.setBelongToContinent(continent);
		listOfTerritories.add(terr);
		
		terr.setName("Zimbave");
		terr.setBelongToContinent(continent);
		listOfTerritories.add(terr);
		
		terr.setName("Australia");
		terr.setBelongToContinent(continent);
		listOfTerritories.add(terr);
		
		terr.setName("USA");
		terr.setBelongToContinent(continent);
		listOfTerritories.add(terr);
		
		
		
		player.setAssignedTerritory(listOfTerritories);
		
		Player returnedPlayer = GameUtils.countReinforcementArmies(map, player);
		Assert.assertEquals(returnedPlayer.getArmies(), 107);
	}
}
