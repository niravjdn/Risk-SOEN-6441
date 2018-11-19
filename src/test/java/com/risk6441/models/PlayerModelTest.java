package com.risk6441.models;

import static org.junit.Assert.assertArrayEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.risk6441.config.CardKind;
import com.risk6441.config.Config;
import com.risk6441.entity.Card;
import com.risk6441.entity.Continent;
import com.risk6441.entity.Map;
import com.risk6441.entity.Player;
import com.risk6441.entity.Territory;
import com.risk6441.exception.InvalidGameActionException;
import com.risk6441.strategy.Human;

import javafx.embed.swing.JFXPanel;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;

/**
 * @author Nirav
 *
 */
public class PlayerModelTest {

	static Map map;
	static Continent continent;
	String continentName = "Asia";
	String terrName1 = "India";
	String terrName2 = "Canada";
	
	@FXML
	static TextArea txtAreaMsg;
	
	@FXML
	static ListView<Territory> terrListView;
	
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
	static PlayerModel playerModel;
	List<Continent> listOfContinents = new ArrayList<>();	
	List<Territory> listOfTerritories = new ArrayList<>();
	static List<Player> playerList;
	String mapAuthor = "Robert";
	String mapImage = "world.map";
	String mapWrap = "no";
	String mapScroll = "horizontal";
	String mapWarn = "yes";
	
	/**
	 * This method is executed before all the methods of a class.
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
		terrListView = new ListView<Territory>();
		playerList =new ArrayList<Player>();
		playerModel = new PlayerModel();
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
		
		terrListView.setEditable(true);
		terrListView.getItems().add(terr1);
		terrListView.getItems().add(terr2);
		ArrayList<Territory> terrList = new ArrayList<Territory>();
		terrList.add(terr1);
		terrList.add(terr2);
		continent.setTerritories(terrList);
		
		listOfContinents.add(continent);
		map.setContinents(listOfContinents);
		
		player.setArmies(99);
		
		listOfTerritories.add(terr1);
		listOfTerritories.add(terr2);
		player.setAssignedTerritory(listOfTerritories);
		playerList.add(player);
		
	}
	
	/**
	 * This method tests reinforcement of armies for 99 initials army and player owns entire continent with two territories.
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
		playerModel.setCurrentPlayer(player);
		Player returnedPlayer = playerModel.countReinforcementArmies(map, player);
		Assert.assertEquals(returnedPlayer.getArmies(), 108);
	}	
	
	
	/**
	 * This method checks the method which return number of  continent owned by the player.
	 */
	@Test
	public void testGetContinentsThatBelongsToPlayer() {
		List<Continent> returnedContinents = new ArrayList<>();
		terr1.setPlayer(player);
		terr1.setPlayer(player);
		returnedContinents = playerModel.getContinentsThatBelongsToPlayer(map, player);
		Assert.assertEquals("Asia", returnedContinents.get(0).getName());
		Assert.assertEquals(1, returnedContinents.size());
	}
	
	
	
	/**
	 * This method tests the method which checks if all player has completed the place army phase or not.
	 */
	@Test
	public void testCheckIfPlayersArmiesExhaustedTrue() {
		playerList = new ArrayList<>();
		playerList.add(new Player(0, "Player0"));
		playerList.get(0).setArmies(0);
		Assert.assertTrue(playerModel.checkIfPlayersArmiesExhausted(playerList));
	}
	
	
	
	/**
	 * This method tests the method which checks if all player has completed the place army phase or not.
	 */
	@Test
	public void testCheckIfPlayersArmiesExhaustedFalse() {
		playerList = new ArrayList<>();
		playerList.add(new Player(0, "Player0"));
		playerList.get(0).setArmies(1);
		Assert.assertFalse(playerModel.checkIfPlayersArmiesExhausted(playerList));
	}
	
	
	/**
	 * This method tests the attach phase has a valid attack move or not.
	 * @throws InvalidGameActionException if move is not valid
	 */
	@Test
	public void testIsValidAttackMoveTrue() throws InvalidGameActionException{
		Player player2 = new Player(2, "B");
		terr1.setPlayer(player);
		terr2.setPlayer(player2);
		terr1.setArmy(3);
		Assert.assertEquals(true, playerModel.isValidAttackMove(terr1, terr2));
	}
	
	

	/**
	 *  This method tests the attach phase has a valid attack move or not.
	 */
	@Test
	public void testPlayerHasAValidAttackMoveTrue() {
		terr1.setArmy(5);
		terr2.setArmy(3);
		terr1.getPlayer().setStrategy(new Human());
		playerModel.setCurrentPlayer(terr1.getPlayer());
		boolean actualResult = playerModel.hasaAValidAttackMove(terrListView, txtAreaMsg);
		Assert.assertTrue(actualResult);
	}

	
	
	/**
	 * This method tests the case in which if any player lost the game.
	 */
	@Test
	public void testCheckAndGetIfAnyPlayerLostTheGame() {
		playerList = new ArrayList<>();
		playerList.add(new Player(0, "Player0"));
		playerList.add(player);
		player.setArmies(10);
		playerModel.setCurrentPlayer(player);
		
		playerList.get(0).setAssignedTerritory(new ArrayList<>());
		Player playerLost = playerModel.checkAndGetIfAnyPlayerLostTheGame(playerList);
		Assert.assertEquals(0, playerLost.getAssignedTerritory().size());
	}
	
	
	
	/**
	 * This method tests creation of the player.
	 */
	@Test
	public void testCreatePlayer() {
		List<Player> playerTest = new ArrayList<>();
		playerList = new ArrayList<>();
		playerList.add(new Player(0, "Player0"));
		playerList.add(new Player(1, "Player1"));
		playerList.add(new Player(2, "Player2"));
		playerTest = playerModel.createPlayers(playerList.size(), txtAreaMsg);
		Assert.assertEquals(3, playerTest.size());
	}
	
	@Test
	public void testGameOver() {
		List<Player> playerListForPlayer = new ArrayList<>();
		playerList = new ArrayList<>();
		Player player1 = new Player(0, "Player0");
		playerList.add(player1);
		playerList.add(new Player(1, "Player1"));
		playerList.add(new Player(2, "Player2"));
		
		terr1.setPlayer(player1);
		player1.getAssignedTerritory().add(terr1);
		
		playerModel.setCurrentPlayer(player1);
		Player p = playerModel.checkAndGetIfAnyPlayerLostTheGame(playerListForPlayer);
		if(p!=null) {
			playerListForPlayer.remove(p);
		}
		playerModel.checkAndGetIfAnyPlayerLostTheGame(playerListForPlayer);
		if(p!=null) {
			playerListForPlayer.remove(p);
		}
		Assert.assertEquals(0, playerListForPlayer.size());
	}
	
	
	/**
	 *  This method tests that if fortification phase is valid or not.
	 */
	@Test
	public void testisFortificationPhaseValidTrue() {
		terr1.setPlayer(player);
		terr1.setArmy(2);
		terr2.setPlayer(player);
		boolean isFortificationPhaseValid = playerModel.isFortificationPhasePossible(map, player);
		Assert.assertEquals(true, isFortificationPhaseValid);
	}
	
	
	
	/**
	 * This method tests that if fortification phase is valid or not.
	 */
	@Test
	public void testIsFortificationPhaseValidFalse() {
		terr1.setPlayer(player);
		terr1.setArmy(0);
		terr2.setArmy(0);
		terr2.setPlayer(player);
		System.out.println("Now here" +continent.getTerritories());
		System.out.println("Now here" +terr2.getArmy());
		boolean isFortificationPhaseValid = playerModel.isFortificationPhasePossible(map, player);
		Assert.assertEquals(false, isFortificationPhaseValid);
	}
	
	/**
	 * This method tests the trading of the cards for the army.
	 */
	@Test
	public void testTradeCardsAndGetArmyValid1() {
		List<Card> listOfCards = new ArrayList<>();
		listOfCards.add(new Card(CardKind.ARTILLERY));
		listOfCards.add(new Card(CardKind.CAVALRY));
		listOfCards.add(new Card(CardKind.INFANTRY));
		player.setArmies(0);
		playerModel.setCurrentPlayer(player);
		player.setNumeberOfTimesCardsExchanged(1);
		playerModel.tradeCardsAndGetArmy(listOfCards, txtAreaMsg);
		Assert.assertEquals(5, player.getArmies());
	}
	
	
	
	/**
	 * This method tests the number of armies for different players.
	 */
	@Test
	public void testAssignArmiesToPlayers() {
		playerList = new ArrayList<>();
		playerList.add(new Player(1, "A"));
		playerList.add(new Player(2, "B"));
		playerList.add(new Player(3, "C"));
		playerModel.allocateArmiesToPlayers(playerList, txtAreaMsg);
		Assert.assertEquals(Config.ARMIES_THREE_PLAYER, (Integer) playerList.get(0).getArmies());

		playerList = new ArrayList<>();
		playerList.add(new Player(1, "A"));
		playerList.add(new Player(2, "B"));
		playerList.add(new Player(3, "C"));
		playerList.add(new Player(4, "D"));
		playerModel.allocateArmiesToPlayers(playerList, txtAreaMsg);
		Assert.assertEquals(Config.ARMIES_FOUR_PLAYER, (Integer) playerList.get(0).getArmies());

		playerList = new ArrayList<>();
		playerList.add(new Player(1, "A"));
		playerList.add(new Player(2, "B"));
		playerList.add(new Player(3, "C"));
		playerList.add(new Player(4, "D"));
		playerList.add(new Player(5, "E"));
		playerModel.allocateArmiesToPlayers(playerList, txtAreaMsg);
		Assert.assertEquals(Config.ARMIES_FIVE_PLAYER, (Integer) playerList.get(0).getArmies());

		playerList = new ArrayList<>();
		playerList.add(new Player(1, "A"));
		playerList.add(new Player(2, "B"));
		playerList.add(new Player(3, "C"));
		playerList.add(new Player(4, "D"));
		playerList.add(new Player(5, "E"));
		playerList.add(new Player(6, "F"));
		playerModel.allocateArmiesToPlayers(playerList, txtAreaMsg);
		Assert.assertEquals(Config.ARMIES_SIX_PLAYER, (Integer) playerList.get(0).getArmies());
	}
}

