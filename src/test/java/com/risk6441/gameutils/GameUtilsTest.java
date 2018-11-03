package com.risk6441.gameutils;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.risk6441.entity.Card;
import com.risk6441.entity.Continent;
import com.risk6441.entity.Map;
import com.risk6441.entity.Player;
import com.risk6441.entity.Territory;
import com.risk6441.exception.InvalidMapException;
import com.risk6441.models.PlayerModel;

import javafx.embed.swing.JFXPanel;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

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
	
	@FXML
	static TextArea txtAreaMsg;
	
	static JFXPanel fxPanel;
	
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
	static List<Player> playerList;
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
		fxPanel = new JFXPanel();
		txtAreaMsg =  new TextArea();
		playerList =new ArrayList<Player>();
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
		playerList.add(player);
	}
	

	/**
	 * This method tests the allocation of cards to territory is working or not.
	 */
	@Test
	public void testAssignCardToTerritory() {
		Stack<Card> stackOfCards = GameUtils.allocateCardToTerritory(map);
		Assert.assertNotNull(stackOfCards);		
	}
	
	
	/**
	 * This method tests the allocation of territory to player is working or not.
	 * @throws InvalidMapException
	 */
	@Test
	public void testAllocateTerritoryToPlayer() throws InvalidMapException {
		playerList.get(0).getAssignedTerritory().clear();
		listOfTerritories.clear();
		listOfTerritories.add(terr1);
		listOfTerritories.add(terr2);
		continent.setTerritories(listOfTerritories);
		map.setContinents(listOfContinents);
		System.out.println(map.getContinents().get(0).getTerritories());
		GameUtils.allocateTerritoryToPlayer(map, playerList, txtAreaMsg);
		Assert.assertTrue(playerList.get(0).getAssignedTerritory().size() > 0 );		
	}
}
