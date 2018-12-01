package com.risk6441.strategy;


import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import com.risk6441.entity.Continent;
import com.risk6441.entity.Map;
import com.risk6441.entity.Player;
import com.risk6441.entity.Territory;
import com.risk6441.gameutils.GameUtils;

import javafx.embed.swing.JFXPanel;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;

/**
 * This is a test class for Aggressive Strategy. {@link Cheater}
 * @author Karthik
 */
public class CheaterTest {

	static Map map;
	static Continent continent;
	String continentName = "Asia";
	String terrName1 = "India";
	String terrName2 = "Canada";
	Aggressive aggressive;
	int controlValue1 = 5;
	static Territory terr1;
	static Territory terr2;
	static Territory adjTerritory;
	static ArrayList<Territory> terrList;
	static ArrayList<Territory> adjTerrList;
	int x1=1;
	int y1=1;
	int x2=2;
	int y2=2;
	static Player player;
	
	@FXML
	static ListView<Territory> list1;
	
	@FXML
	static ListView<Territory> list2;
	
	static JFXPanel fxPanel;
	
	List<Continent> listOfContinents = new ArrayList<>();	
	List<Territory> listOfTerritories = new ArrayList<>();
	static List<Player> playerList;
	String mapAuthor = "Arthur";
	String mapImage = "world.map";
	String mapWrap = "no";
	String mapScroll = "horizontal";
	String mapWarn = "yes";
	
	/**
	 * This method executed before all the methods of the class.
	 */
	@BeforeClass
	public static void beforeClass() {
		GameUtils.isTestMode = true;
		continent = new Continent();
		terr1 = new Territory();
		terr2 = new Territory();
		map = new Map();
		player = new Player(1, "Krishna");
		terrList=new ArrayList<Territory>();
		adjTerrList=new ArrayList<Territory>();
		playerList =new ArrayList<Player>();
		fxPanel = new JFXPanel();
		list1=new ListView<Territory>();
		list2=new ListView<Territory>();
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
		terr2.setBelongToContinent(continent);
		
		
		terr1.setPlayer(player);
		terr2.setPlayer(player);
		
		Territory terr3 = new Territory();
		terr3.getAdjacentTerritories().add(terr1);
		terr1.getAdjacentTerritories().add(terr3);
		terr3.setPlayer(new Player(2, "Player 2"));
		
		
		
		terrList.add(terr1);
		terrList.add(terr2);
		
		adjTerrList.add(terr3);
		continent.setTerritories(terrList);
		
		listOfContinents.add(continent);
		map.setContinents(listOfContinents);
		
		player.setArmies(99);
		
		listOfTerritories.add(terr1);
		listOfTerritories.add(terr2);
		player.setAssignedTerritory(listOfTerritories);
		playerList.add(player);
		
		list1.setEditable(true);
		list1.getItems().add(terr1);
		list1.getItems().add(terr2);
		
		list2.setEditable(true);
		list2.getItems().add(terr3);

	}
	
	/**
	 *	Checks if cheater has valid attack move for cheater. 
	 */
	@Test public void testHasAValidAttackMove()
	{
		terr1.setArmy(5);
		terr2.setArmy(3);
		terr1.getPlayer().setStrategy(new Cheater());
		Assert.assertTrue(player.getStrategy().hasAValidAttackMove(terrList));
	}
	
	/**
	 * Tests for valid fortification for cheater.
	 */
	@Test public void testFortificationPhase()
	{
		terr1.setArmy(5);
		terr2.setArmy(3);
		terr1.getPlayer().setStrategy(new Cheater());
		Assert.assertTrue(player.getStrategy().fortificationPhase(list1, list2, player, map, terrList, adjTerrList));
		
	}
}
