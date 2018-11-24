package com.risk6441.gameutils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Stack;

import com.risk6441.config.CardKind;
import com.risk6441.config.PlayerStrategy;
import com.risk6441.entity.Card;
import com.risk6441.entity.Continent;
import com.risk6441.entity.Map;
import com.risk6441.entity.Player;
import com.risk6441.entity.Territory;
import com.risk6441.exception.InvalidMapException;

import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * This class handles operations related with game play.
 * @author Nirav
 *
 */
public class GameUtils {
	
	static int count=0;
	public static TextArea txtMsgArea = null;
	
	/**
	 * This method writes a log in textArea.
	 * @param str String to be written in textArea.
	 * @param txtAreaMsg Textarea object.
	 */
	public static void addTextToLog(String str, TextArea txtAreaMsg) {
		Platform.runLater(() -> txtAreaMsg.appendText(str));
	}
	
	/**
	 * This method writes a log in textArea.
	 * @param str String to be written in textArea.
	 */
	public static void addTextToLog(String str) {
		Platform.runLater(() -> txtMsgArea.appendText(str));	
	}
	
	/**
	 * This method allocates armies to the players.
	 * @param map
	 * 			map object
	 * @param players
	 * 		  		list of the player
	 * @param textAres
	 * 				  textArea object
	 * @throws InvalidMapException Throws IOException if there is an issue while reading a map file.
	 */
	public static void allocateTerritoryToPlayer(Map map, List<Player> players, TextArea textAres) throws InvalidMapException {

		List<Territory> allterritoriesList = getTerritoryList(map);

		Collections.shuffle(allterritoriesList); 
		
		int playerNo = 0;
		for (int i = 0; i < allterritoriesList.size(); i++) {
			if(playerNo == players.size())
				playerNo = 0;

			Territory territory = allterritoriesList.get(i);
			
			if(i >= ((allterritoriesList.size() / players.size()) * players.size())) {
				Random r = new Random();
				
				int randomNo = r.nextInt(players.size());
				System.err.println("Error 2 "+randomNo);
				Player player = players.get(randomNo);
				territory.setPlayer(player);
				territory.setArmy(territory.getArmy() + 1);
				player.setArmies(player.getArmies() - 1);
				player.getAssignedTerritory().add(territory);
				addTextToLog(
						territory.getName() + " is assigned to " + player.getName() + " ! \n", textAres);
				
      			continue;
			}
			
			Player player = players.get(playerNo++);
			territory.setPlayer(player);
			territory.setArmy(territory.getArmy() + 1);
			player.setArmies(player.getArmies() - 1);
			player.getAssignedTerritory().add(territory);
			addTextToLog(
					territory.getName() + " is assigned to " + player.getName() + " ! \n", textAres);
			
		}
	}

	/**
	 * This method returns the territory list.
	 * @param map Current map object.
	 * @return Territory list.
	 */
	public static List<Territory> getTerritoryList(Map map) {
		List<Territory> allterritoriesList = new ArrayList<Territory>();
		if (map.getContinents() != null) {
			for (Continent continent : map.getContinents()) {
				if (continent != null && continent.getTerritories() != null) {
					for (Territory territory : continent.getTerritories()) {
						allterritoriesList.add(territory);
					}
				}
			}
		}
		System.out.println("Total No of Territories : "+allterritoriesList.size());
		return allterritoriesList;
	}
	
	/**
	 * This method saves a particular card against a territory
	 * @param map map file
	 * @return stackOfCards returns a stack of cards along with its territories
	 *
	 */
	
	public static Stack<Card> allocateCardToTerritory(Map map) {
		Stack<Card> stackOfCards = new Stack<Card>();

		List<Territory> allterritories = getTerritoryList(map);
		ArrayList<CardKind> cardsRandaomList = new ArrayList<CardKind>();
		
		int eachUniqueCards = allterritories.size() / 3;
		cardsRandaomList.addAll(Collections.nCopies(eachUniqueCards, CardKind.valueOf("CAVALRY")));
		cardsRandaomList.addAll(Collections.nCopies(eachUniqueCards, CardKind.valueOf("ARTILLERY")));
		cardsRandaomList.addAll(Collections.nCopies(eachUniqueCards, CardKind.valueOf("INFANTRY")));
		int diff = allterritories.size() - cardsRandaomList.size();
		if(diff > 0) {
			for(int i=0; i < diff; i++) {
				System.out.println("inside");
				cardsRandaomList.add(CardKind.values()[(int) (Math.random() * CardKind.values().length)]);
			}
		}
		int i = 0;
		for (Territory territory : allterritories) {
			Card card = new Card(cardsRandaomList.get(i++));
			card.setTerritoryToWhichCardBelong(territory);
			stackOfCards.push(card);
		}
		return stackOfCards;
	}

	

	/**
	 * This method checks whether the fortification phase is completed or not.
	 * @param numPlayer
	 * 			 count of active number of players in the game
	 * @return
	 * 		  returns true if the fortification phase is completed
	 */
	public static boolean checkFortificationPhase(int numPlayer)
	{
		count++;
		if(count==numPlayer)
			return true;
		else
			return false;
	}
	
	public static void clearCheckBoxes(CheckBox... checkboxes) {
		for (CheckBox checkbox : checkboxes) {
			checkbox.setSelected(false);
			checkbox.setText("");
		}
	}
	
	/**
	 * This method is used to set visible false of pane.
	 * 
	 * @param panes
	 *            list of panes to be disabled
	 */
	public static void disableViewPane(Pane... panes) {
		for (Pane pane : panes) {
			pane.setVisible(false);
		}
	}
	
	/**
	 * This method is used to set visible true of pane.
	 * 
	 * @param panes
	 *            list of panes to be enabled
	 */
	public static void enablePane(Pane... panes) {
		for (Pane pane : panes) {
			pane.setVisible(true);
		}
	}
	
	/**
	 * This method is used to close screen.
	 * @param button Button to exit windows.
	 */
	public static void exitWindows(Button button) {
		try {
			Stage stage = (Stage) button.getScene().getWindow();
			stage.close();
		}catch (Exception e) {
			System.out.println(e.getMessage()+" Error Message");
		}
	}

	/**
	 * This method gives the list of adjacent territories for fortification.
	 * @param territory Territory whose adjacent territories need to be found.
	 * @param map The current map.
	 * @param currentPlayer The object of current player.
	 * @return list of territories.
	 */
	public static List<Territory> getAdjTerrForFortifiction(Territory territory,Map map,Player currentPlayer) {
		
		List<Territory> reachableTerrList = new ArrayList<Territory>();
		List<Territory> allTerr = GameUtils.getTerritoryList(map);
		
		bfsTerritory(territory,reachableTerrList,territory,currentPlayer);
		
		for(Territory t : allTerr) {
			t.setProcessed(false);
		}	
		return reachableTerrList;
	}
	
	/**
	 * This method checks whether all the territories are traversed.
	 * @param territory Start territory.
	 * @param reachableTerrList All reachable territories from the start territory.
	 * @param root The root territory.
	 * @param currentPlayer The object of current player.
	 */
	public static  void bfsTerritory(Territory territory, List<Territory> reachableTerrList, Territory root, Player currentPlayer) {

		if(territory.isProcessed() == true) {
			return;
		}
		
		territory.setProcessed(true);
		if(!territory.equals(root)){
				reachableTerrList.add(territory);
			}
		for(Territory t : territory.getAdjacentTerritories()){
			if(t.isProcessed() == false && t.getPlayer().equals(currentPlayer)){
				bfsTerritory(t,reachableTerrList,root,currentPlayer);
			}
		}		
	}
	
	
	/**
	 * This method loads the values in combobox for number of turns
	 * @param noOFTurns  no of turns for tournament
	 */
	public static void loadTurnsInTournament(ComboBox<Integer> noOFTurns) {
		noOFTurns.getItems().removeAll(noOFTurns.getItems());
		for(int i=10; i<=50; i++) {
			noOFTurns.getItems().add(i);
		}
	}
	
	/**
	 * This method loads the values of number of games in tournament
	 * @param numberOfGamesComboBox no of games for tournament
	 */
	public static void loadGamesInTournament(ComboBox<Integer> numberOfGamesComboBox) {
		numberOfGamesComboBox.getItems().removeAll(numberOfGamesComboBox.getItems());
		numberOfGamesComboBox.getItems().addAll(1, 2, 3, 4, 5);
	}
	
	/**
	 * This method loads the values of player strategy in combobox
	 * @param playerComboBox player Combobox
	 */
	public static void loadPlayersInTournament(ComboBox<String> playerComboBox) {
		playerComboBox.getItems().removeAll(playerComboBox.getItems());
		playerComboBox.getItems().addAll(PlayerStrategy.AGGRESSIVE.toString(), PlayerStrategy.BENEVOLENT.toString(),
				PlayerStrategy.CHEATER.toString(), PlayerStrategy.RANDOM.toString());
	}
}
