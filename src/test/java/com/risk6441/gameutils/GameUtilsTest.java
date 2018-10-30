package com.risk6441.gameutils;

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

import com.risk6441.entity.Continent;
import com.risk6441.entity.Map;
import com.risk6441.entity.Player;
import com.risk6441.entity.Territory;
import com.risk6441.exception.InvalidMapException;
import com.risk6441.gameutils.GameUtils;
import com.risk6441.models.PlayerModel;

/**
 * This is a test class for GameUtils. {@link GameUtils}
 * @author Nirav
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
	
	/**
	 * This method executed before all the methods of the class.
	 */
	@BeforeClass
	public static void beforeClass() {
		continent = new Continent();
		terr1 = new Territory();
		terr2 = new Territory();
		map = new Map();
		player = new Player(1, "Nirav");
	}
	
	/**
	 * This method is executed before every method of the class.
	 */
	@Before
	public void before() {
		map = new Map();
		listOfContinents = new ArrayList<>();	
		listOfTerritories = new ArrayList<>();
		
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
	
	/**
	 * This method test reinforcement armies for 99 initials army and player own entire continent with two territories.
	 */
	@Test
	public void testCountReinforcementArmiesCaseOne() {		
		Player returnedPlayer = PlayerModel.countReinforcementArmies(map, player);
		Assert.assertEquals(returnedPlayer.getArmies(), 107);
	}
	
	/**
	 * This method counts reinforcement armies with initial army 99 and player has two continents.
	 */
	@Test
	public void testCountReinforcementArmiesUseCaseTwo() {	
		Continent newContinent = new Continent();
		newContinent.setName("North America");
		newContinent.setValue(10);
		
		Territory t = new  Territory();
		t.setName("Canada");
		t.setPlayer(player);
		List<Territory> listOfTerr2 = new ArrayList<>();
		listOfTerr2.add(t);
		newContinent.setTerritories(listOfTerr2);
		
		//99 +5 +3 +10
		listOfContinents.add(newContinent);
		map.setContinents(listOfContinents);
		Player returnedPlayer = PlayerModel.countReinforcementArmies(map, player);
		Assert.assertEquals(returnedPlayer.getArmies(), 117);
	}
	
	
	/**
	 * This method counts reinforcement armies for the player who owns one continent and 12 territories.
	 */
	@Test
	public void testCountReinforcementArmiesUseCaseThree() {	
		Continent newContinent = new Continent();
		newContinent.setName("North America");
		newContinent.setValue(10);
		
		List<Territory> listOfTerr2 = new ArrayList<>();
		
		Territory t = new  Territory();
		t.setName("Canada");
		t.setPlayer(player);
		listOfTerr2.add(t);
		
		t = new  Territory();
		t.setName("Canada2");
		t.setPlayer(player);
		listOfTerr2.add(t);

		t = new  Territory();
		t.setName("Canada3");
		t.setPlayer(player);
		listOfTerr2.add(t);

		t = new  Territory();
		t.setName("Canada4");
		t.setPlayer(player);
		listOfTerr2.add(t);

		t = new  Territory();
		t.setName("Canada5");
		t.setPlayer(player);
		listOfTerr2.add(t);

		t = new  Territory();
		t.setName("Canada6");
		t.setPlayer(player);
		listOfTerr2.add(t);

		t = new  Territory();
		t.setName("Canada7");
		t.setPlayer(player);
		listOfTerr2.add(t);

		t = new  Territory();
		t.setName("Canada8");
		t.setPlayer(player);
		listOfTerr2.add(t);

		t = new  Territory();
		t.setName("Canada9");
		t.setPlayer(player);
		listOfTerr2.add(t);
		
		t = new  Territory();
		t.setName("Canada99");
		t.setPlayer(player);
		listOfTerr2.add(t);

		t = new  Territory();
		t.setName("Canada10");
		t.setPlayer(new Player(2,"Krishnan"));
		listOfTerr2.add(t);
		
		newContinent.setTerritories(listOfTerr2);
		
		player.getAssignedTerritory().addAll(listOfTerr2);
		System.out.println(player.getAssignedTerritory().size()+"a");
		//99 +5 +(12/3)
		listOfContinents.add(newContinent);
		map.setContinents(listOfContinents);
		Player returnedPlayer = PlayerModel.countReinforcementArmies(map, player);
		Assert.assertEquals(returnedPlayer.getArmies(), 108);
	}
	
	
}
