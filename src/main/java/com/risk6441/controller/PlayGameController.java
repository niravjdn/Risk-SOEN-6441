package com.risk6441.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;
import java.util.Stack;

import org.apache.commons.lang3.SerializationUtils;
import org.apache.commons.lang3.StringUtils;

import com.risk6441.config.Config;
import com.risk6441.entity.Card;
import com.risk6441.entity.Continent;
import com.risk6441.entity.Map;
import com.risk6441.entity.Player;
import com.risk6441.entity.Territory;
import com.risk6441.exception.InvalidGameActionException;
import com.risk6441.exception.InvalidMapException;
import com.risk6441.gameutils.GameUtils;
import com.risk6441.maputils.CommonMapUtil;
import com.risk6441.models.CardModel;
import com.risk6441.models.PlayerModel;
import com.risk6441.models.WorldDominationModel;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.PieChart.Data;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

/**
 * This class ....
 * @author Nirav 
 * @author Charles
 */
public class PlayGameController implements Initializable,Observer{

	/**
	 * The map object {@link Map}
	 * 
	 */
	private Map map;
	
	private int noOfPlayer;
	
	private List<Player> playerList;
	
	private Player currentPlayer;
	
	private PlayerModel playerModel;
	
	private Iterator<Player> playerListIterator;
	
	private Stack<Card> stackOfCards;
	
	private CardModel cardModel;
	
    @FXML
    private Button btnReinforcement;
    
    @FXML
    private Label lblCurrPlayer;


    @FXML
    private Label lblGamePhase;

    
    @FXML
    private Button btnPlaceArmy;

    @FXML
    private PieChart worldDominationPieChart;

    @FXML
    private BarChart<String, Number> militaryDominationbarChart;

    
    @FXML
    private Button btnFortify;

    @FXML
    private Button btnNoMoreAttack;

    
    @FXML
	private Button btnCards;
    
    @FXML
    private Button btnEndTurn;

    @FXML
    private ListView<Territory> terrList;

    @FXML
    private ListView<Territory> adjTerrList;

    @FXML
    private TextArea txtAreaMsg;

    @FXML
    private VBox vbox;

    @FXML
    private ChoiceBox<Integer> choiceBoxNoOfPlayer;
    
    /** This method ends the turn of particular player using Scheduled Executor class
     * 
     * @param event button event will be passed as a parameter
     */
    @FXML
    void endTrun(ActionEvent event) {
    	GameUtils.addTextToLog(currentPlayer.getName() + " ended his turn.\n", txtAreaMsg);
		if (playerModel.getNumOfTerritoryWon() > 0) {
			allocateCardToPlayer();
		}
		playerModel.endTurn();
    }
    
    
    @FXML
    void  openCardPane(ActionEvent event) {
    	cardModel.openCardWindow();
    }
   
    
    /** This method will be called by user to start the fortification phase
     * 
     * @param event button click event will be passes as parameter
     */
    @FXML
    void fortify(ActionEvent event) {
    Territory selectedTerritory = terrList.getSelectionModel().getSelectedItem();
	Territory adjTerritory = adjTerrList.getSelectionModel().getSelectedItem();

	playerModel.fortificationPhase(selectedTerritory, adjTerritory, txtAreaMsg);
	terrList.refresh();
	adjTerrList.refresh();
	updateMap();
    }
    
    /** This method will allow the players to place the armies one by one in round robin fashion
     * 
     * @param event Button triggered event will be passed as parameter
     */
    @FXML
    void placeArmy(ActionEvent event) {
    	playerModel.placeArmy(terrList, playerList, txtAreaMsg);
    }
    
    /**
     * This method sets the label for the current phase.
     * @param phase phase name
     */
    public void setPhase(String phase) {
    	lblGamePhase.setText(phase);
    }
    
    /**
	 * Initialize place army view.
	 */
	private void initializePlaceArmy() {
		loadCurrentPlayer(false);
		updateMap();
		terrList.refresh();
	}
    
    /** This method will allow the user to place the armies after the fortification phase is completed
     * 
     * @param event button click event will be passed as parameter
     */
    @FXML
    void reinforce(ActionEvent event) {
    	Territory territory = terrList.getSelectionModel().getSelectedItem();
    	if (currentPlayer.getCardList().size() >= 5) {
    		CommonMapUtil.alertBox("Info", "You Risk Cards >= 5, please exchange these cards for the army.", "Info");
			return;
		}

    	playerModel.reinforcementPhase(territory, txtAreaMsg);
    	setCurrentPlayerLabel(currentPlayer.getName() + ":- " + currentPlayer.getArmies() + " armies left.");
    	updateMap();
    	showMilitaryDominationData();
    	terrList.refresh();
    	adjTerrList.refresh();
    }

    
    /**
     * This method is responsible for ending attack phase and providing notification.
     * @param event event button click event will be passed as parameter
     */
    @FXML
    void noMoreAttack(ActionEvent event) {
		if (playerModel.getNumOfTerritoryWon() > 0) {
			allocateCardToPlayer(); 
		}
		GameUtils.addTextToLog("===Attack phase ended!===\\n", txtAreaMsg);
		isValidFortificationPhase();
    }
    
    
    /**
	 * Allocate cards to player
	 */
	private void allocateCardToPlayer() {
		try {
			Card card = stackOfCards.pop();
			currentPlayer.getCardList().add(card);
			GameUtils.addTextToLog(currentPlayer.getName() + " has been assigned a card with type "+ card.getCardKind().toString() + " and territory " + card.getTerritoryToWhichCardBelong().getName() + "\n", txtAreaMsg);
		}catch (Exception e) {
			e.printStackTrace();
		}
		playerModel.setNumOfTerritoryWon(0);
	}
    
    /**
	 * check if there is a valid fortification phase.
	 */
	private void isValidFortificationPhase() {
		playerModel.isFortificationPhasePossible(map, currentPlayer);
	}
    
    // 
	/**
	 * This is a constructor to initialize the Map object.
	 * @param map
	 */
	public PlayGameController(Map map) {
		this.map = map;
		this.playerModel = new PlayerModel();
		this.cardModel = new CardModel();
		playerModel.addObserver(this);
		cardModel.addObserver(this);
	}
	
	/**
	 * The default constructor.
	 */
	public PlayGameController() {
		
	}
	
	/**
	 * This method sets the label text of player to current player
	 * @param str Contains the current player name.
	 */
	public void setCurrentPlayerLabel(String  str) {
		lblCurrPlayer.setText("Playing... : "+str); 
	}
	
	
	/**
	 * Loads the current player and clears the selected and adjacent territory list
	 * @param isLoadingFromFirstPlayer true if we are starting from player 0
	 * @return The current player
	 */
	public Player loadCurrentPlayer(boolean isLoadingFromFirstPlayer) {
		if (!playerListIterator.hasNext() || isLoadingFromFirstPlayer) {
			playerListIterator = playerList.iterator();
		}
		currentPlayer = playerListIterator.next();
		cardModel.setCurrentPlayer(currentPlayer);
		playerModel.setCurrentPlayer(currentPlayer);
		playerModel.setNumOfTerritoryWon(0);
		GameUtils.addTextToLog("============================ \n", txtAreaMsg);
		GameUtils.addTextToLog(currentPlayer.getName() + "!....started playing.\n", txtAreaMsg);
		terrList.getItems().clear();
		adjTerrList.getItems().clear();
		for (Territory territory : currentPlayer.getAssignedTerritory()) {
			terrList.getItems().add(territory);
		}
		setCurrentPlayerLabel(currentPlayer.getName() + ":- " + currentPlayer.getArmies() + " armies left.\n");
		return currentPlayer;
	}
	
	/**
	 * This method checks whether the player has armies or not for placement in territories.
	 * @return Returns true or false (boolean) after testing condition.
	 */
	public boolean checkPlayerWithNoArmyWhilePlacingArmy() {
		if(currentPlayer.getArmies()==0) {
			GameUtils.addTextToLog("Skipped "+currentPlayer.getName()+" It doesn't have army for placing.\n", txtAreaMsg);
			loadCurrentPlayer(false);
			return true;
		}
		return false;
	}
	
	/**
	 * This method allocates cards to territorries.
	 * @throws InvalidMapException Throws IOException if there is an issue while loading the map.
	 */
	private void allocateCardTOTerritories() {
		GameUtils.addTextToLog("===Assigning Cards to Territories===\n", txtAreaMsg);
		stackOfCards = GameUtils.allocateCardToTerritory(map);
		Collections.shuffle(stackOfCards);
		GameUtils.allocateCardToTerritory(map);
		GameUtils.addTextToLog("===Cards assignation complete===\n", txtAreaMsg);
	}
	
	/**
	 * This method allocates territories to the player and start the game.
	 * @throws InvalidMapException Throws IOException if there is an issue while loading the map.
	 */
	private void allocateTerritoriesToPlayer() throws InvalidMapException {
		GameUtils.addTextToLog("===Assigning territories===\n", txtAreaMsg);
		GameUtils.allocateTerritoryToPlayer(map, playerList, txtAreaMsg);
		GameUtils.addTextToLog("===Territories assignation complete===\n", txtAreaMsg);
		updateMap();
	}
	
	/* (non-Javadoc)
	 * @see javafx.fxml.Initializable#initialize(java.net.URL, java.util.ResourceBundle)
	 */
	public void initialize(URL location, ResourceBundle resources) {
		choiceBoxNoOfPlayer.getItems().addAll(2,3, 4, 5, 6);
		playerList = new ArrayList<>(); 
		lblGamePhase.setText("Phase: Start Up!");
		updateMap();
		allocateCardTOTerritories();
		CommonMapUtil.enableControls(btnNoMoreAttack);
		choiceBoxNoOfPlayer.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Integer>() {
			@Override
			public void changed(ObservableValue<? extends Integer> observable, Integer old, Integer newV) {
				noOfPlayer = choiceBoxNoOfPlayer.getSelectionModel().getSelectedItem();
				playerList = PlayerModel.createPlayers(noOfPlayer, playerList, txtAreaMsg);
				GameUtils.addTextToLog("===Players creation complete===\n", txtAreaMsg);

				choiceBoxNoOfPlayer.setDisable(true);
				playerListIterator = playerList.iterator();
				CommonMapUtil.enableControls(btnPlaceArmy);
				PlayerModel.assignArmiesToPlayers(playerList, txtAreaMsg);
				try {
					allocateTerritoriesToPlayer();
					
					setPhase("Phase : Place Army");
					loadCurrentPlayer(false);
					showWorldDominationData();
					showMilitaryDominationData();
				} catch (InvalidMapException e) {
					// TODO Auto-generated catch block
					CommonMapUtil.alertBox("Alert", e.getMessage(), "Error");
					e.printStackTrace();
				}
			}
		});
		
		CommonMapUtil.disableControls(btnEndTurn, btnFortify, btnPlaceArmy, btnReinforcement);
		
		
		terrList.setCellFactory(param -> new ListCell<Territory>() {
			@Override
			protected void updateItem(Territory item, boolean empty) {
				super.updateItem(item, empty);

				if (empty || item == null || item.getName() == null) {
					setText(null);
				} else {
					setText(item.getName() + ":-" + item.getArmy() + "-" + item.getPlayer().getName());
				}
			}
		});
		
		adjTerrList.setCellFactory(param -> new ListCell<Territory>() {
			@Override
			protected void updateItem(Territory item, boolean empty) {
				super.updateItem(item, empty);

				if (empty || item == null || item.getName() == null) {
					setText(null);
				} else {
					setText(item.getName() + "-" + item.getArmy() + "-" + item.getPlayer().getName());
				}
			}
		});
		
	   terrList.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				Territory terr = terrList.getSelectionModel().getSelectedItem();
				showAdjTerritoryOfTerrInList(terr);
			}
		});
	}
	
	/**
	 * Show adjacent territories of the particular territory
	 * @param terr
	 * 			  territory object
	 */
	public void showAdjTerritoryOfTerrInList(Territory terr) {
		adjTerrList.getItems().clear();
		for (Territory t : terr.getAdjacentTerritories()) {
			adjTerrList.getItems().add(t);
		}

	}
	
	/**
	 * Updates the map to show latest data.
	 */
	public void updateMap() {
		System.out.println("updateMap Called.");
		vbox.getChildren().clear();
		for(Continent c : map.getContinents()) {
			vbox.autosize();
			vbox.getChildren().add(CommonMapUtil.getNewPaneForVBox(c));
		}
	}
	
	/**
	 * This method intializes the components for the attack phase.
	 */
	public void initializeAttack() {
		GameUtils.addTextToLog("===============================\n", txtAreaMsg);
		GameUtils.addTextToLog("The Attack phase has begun.\n", txtAreaMsg);
		if (playerModel.playerHasAValidAttackMove(terrList, txtAreaMsg)) {
			CommonMapUtil.enableControls(btnEndTurn,btnNoMoreAttack);
			CommonMapUtil.disableControls(btnReinforcement, btnFortify, btnPlaceArmy);
		}
		adjTerrList.setOnMouseClicked(e -> attack());
	}

	/**
	 * This method starts the attack adjacent territory for the player.
	 */
	public void attack() {
		if(!lblGamePhase.getText().contains("Attack")) {
			return;
		}
		Territory attackingTerritory = terrList.getSelectionModel().getSelectedItem();
		Territory defendingTerritory = adjTerrList.getSelectionModel().getSelectedItem();
		try {
			GameUtils.addTextToLog(attackingTerritory.getName()+" attacking on "+defendingTerritory, txtAreaMsg);
			playerModel.attackPhase(attackingTerritory, defendingTerritory);
		} catch (InvalidGameActionException ex) {
			CommonMapUtil.alertBox("Info", ex.getMessage(), "Alert");
			return;
		}
	}
	
	/**
	 * This method intializes the components for the fortification phase.
	 */
	private void initializeFortification() {
		GameUtils.addTextToLog("===============================\n", txtAreaMsg);
		GameUtils.addTextToLog("The Fortification phase has begun.\n", txtAreaMsg);
		btnFortify.setDisable(false);
		CommonMapUtil.disableControls(btnNoMoreAttack);
		btnFortify.requestFocus();
		CommonMapUtil.disableControls(btnReinforcement);
	}
	
	/**
	 * This method handles the case in which fortificaiton is not possible.
	 */
	private void noFortification() {
		GameUtils.addTextToLog("Fortification phase has begun.\n", txtAreaMsg);
		GameUtils.addTextToLog(currentPlayer.getName() + " does not have any armies for fortification.\n", txtAreaMsg);
		GameUtils.addTextToLog("Fortification phase has been ended.\n", txtAreaMsg);
		initializeReinforcement(false);
	}

	/**
	 * This method initialized the component for the reinforcement phase.
	 * @param b 
	 */
	private void initializeReinforcement(boolean loadPlayerFromStart) {
		System.out.println("Inside intialize reinforcement "+loadPlayerFromStart);
		loadCurrentPlayer(loadPlayerFromStart);
		CommonMapUtil.disableControls(btnPlaceArmy, btnFortify, btnEndTurn, btnNoMoreAttack);
		btnReinforcement.setDisable(false);
		btnReinforcement.requestFocus();
		GameUtils.addTextToLog("=======================================\n", txtAreaMsg);
		GameUtils.addTextToLog("Reinforcement phase has begun.", txtAreaMsg);
		GameUtils.addTextToLog(currentPlayer.getName() + "\n", txtAreaMsg);
		countReinforcementArmies();
	}
	
	/**
	 * method to count the number of armies to be assigned to a player in reinforcement phase.
	 */
	public void countReinforcementArmies() {
		if (this.currentPlayer != null) {
			currentPlayer = PlayerModel.countReinforcementArmies(map, currentPlayer);
			setCurrentPlayerLabel(currentPlayer.getName() + ":- " + currentPlayer.getArmies() + " armies left.");
		} else {
			GameUtils.addTextToLog("Error! No Current Player.", txtAreaMsg);
		}
	}

	/**
	 * Check if any player has lost the game
	 */
	private void checkIfAnyPlayerLostTheMatch() {
		Player playerLost = playerModel.checkAndGetIfAnyPlayerLostTheGame(playerList);
		if (playerLost != null) {
			playerList.remove(playerLost);
			playerListIterator = playerList.iterator();
			CommonMapUtil.alertBox("Info", "Player: " + playerLost.getName() + " lost all his territory and no more in the game.",
					"Info");
			GameUtils.addTextToLog(playerLost.getName() + " lost all territories and lost the game.\n",
					txtAreaMsg);
			GameUtils.addTextToLog("==============================================================\\n",
					txtAreaMsg);
		}
	}
	
	/**
	 * Refresh View
	 */
	private void refreshView() {
		checkIfAnyPlayerLostTheMatch();
		terrList.getItems().clear();
		adjTerrList.getItems().clear();
		for (Territory territory : currentPlayer.getAssignedTerritory()) {
			terrList.getItems().add(territory);
		}
		if(!StringUtils.isEmpty(Config.message)) {
			GameUtils.addTextToLog(Config.message, txtAreaMsg);
			Config.message = "";
		}
		updateMap();
		showWorldDominationData();
		showMilitaryDominationData();
		setCurrentPlayerLabel(currentPlayer.getName() + ":- " + currentPlayer.getArmies() + " armies left.\n");
		if (checkIfPlayerWonTheGame()) {
			return;
		}
		List<Continent> listOfContinentsOwnedSingly = (playerModel.getContinentsThatBelongsToPlayer(map, currentPlayer));
		if(listOfContinentsOwnedSingly.size()!=0)
		{
			
			for(Continent c : listOfContinentsOwnedSingly)
			{
				GameUtils.addTextToLog("\n============================ \n", txtAreaMsg);
				GameUtils.addTextToLog(c.getName()+" is owned by : "+currentPlayer.getName(), txtAreaMsg);
				GameUtils.addTextToLog("\n============================ \n", txtAreaMsg);
			}
		}
	}
	
	/**
	 * Check If Any Player Won the game.
	 * @return playerWon returns true if a player won the game
	 */
	private boolean checkIfPlayerWonTheGame() {
		boolean isGameOver = false;
		if (playerList.size() == 1) {
			CommonMapUtil.alertBox("Player: " + playerList.get(0).getName() + " won the game!", "Info", "");
			isGameOver = true;
			disableGameControls();
		}
		return isGameOver;
	}

	/**
	 * Disable the game after game is over
	 * 
	 */
	private void disableGameControls() {
		CommonMapUtil.disableControls(terrList, adjTerrList, btnReinforcement, btnFortify, btnNoMoreAttack, btnCards,
				btnEndTurn);
		lblGamePhase.setText("GAME OVER");
		setCurrentPlayerLabel(currentPlayer.getName().toUpperCase() + " WON THE GAME");
		GameUtils.addTextToLog("=====================================================\n", txtAreaMsg);
		GameUtils.addTextToLog(currentPlayer.getName().toUpperCase() + " WON THE GAME\n", txtAreaMsg);
		GameUtils.addTextToLog("=====================================================\n", txtAreaMsg);
	}

	
	
	/* (non-Javadoc)
	 * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
	 */
	@Override
	public void update(Observable o, Object arg) {
		String str = (String) arg;
		System.out.println("update called because of object change "+str);
		
		if (str.equals("rollDiceComplete")) {
			refreshView();
		}else if(str.equals("Attack")) {
			setPhase("Phase : Attack");
			initializeAttack();
		}else if(str.equals("ReinforcementFirst")) {
			setPhase("Phase : Reinforcement");
			initializeReinforcement(true);
		}else if(str.equals("Reinforcement")) {
			setPhase("Phase : Reinforcement");
			initializeReinforcement(false);
			cardModel.openCardWindow();
		}else if(str.equals("placeArmy")) {
			setPhase("Phase : Place Army");
			initializePlaceArmy();
			checkPlayerWithNoArmyWhilePlacingArmy();
			while(checkPlayerWithNoArmyWhilePlacingArmy()) {
				System.out.println("Skipping "+currentPlayer.getName());
			}
		}else if(str.equals("Fortification")) {
			setPhase("Phase : Fortification");
			initializeFortification();
		}else if(str.equals("NoFortification")) {
			setPhase("Phase : No Fortification");
			noFortification();
		}else if(str.equals("tradeCard")) {
			tradeCards();
		}
	}

	/**
	 * This method trades the cards of the player for tht army.
	 */
	private void tradeCards() {
		List<Card> selectedCardsOfThePlayer = cardModel.getCardsToBeExchange();
		currentPlayer.setNumeberOfTimesCardsExchanged(currentPlayer.getNumeberOfTimeCardsExchanged()+1);
		playerModel.tradeCardsAndGetArmy(selectedCardsOfThePlayer,txtAreaMsg);
		currentPlayer.getCardList().removeAll(selectedCardsOfThePlayer);
		stackOfCards.addAll(selectedCardsOfThePlayer);
		Collections.shuffle(stackOfCards);
		terrList.refresh();
		adjTerrList.refresh();
		updateMap();
		showMilitaryDominationData();
		setCurrentPlayerLabel(currentPlayer.getName() + ":- " + currentPlayer.getArmies() + " armies left.");
	}


	/**
	 * This method populates World Domination Data into pie chart.
	 */
	private void showWorldDominationData() {
		HashMap<Player, Double> playerTerPercent = WorldDominationModel.getWorldDominationData(map);
		ArrayList<Data> chartData = new ArrayList<>();
		ObservableList<PieChart.Data> pieData = FXCollections.observableArrayList();
		for (Entry<Player, Double> entry : playerTerPercent.entrySet()) {
			double d = entry.getValue();
			String value = String.valueOf(d).substring(0, 2);
			String label = entry.getKey().getName();
			label += "-"+value+"%";
			System.out.println(label);
			chartData.add(new PieChart.Data(label, d));
		}
		pieData.addAll(chartData);
		worldDominationPieChart.setData(pieData);
	}
	
	/**
	 * This method populates World Domination Data(armies) into a bar chart.
	 */
	private void showMilitaryDominationData() {
		HashMap<String, Double> playerAndMilitaryCountMap = WorldDominationModel.getMilitaryDominationData(map);
		Series<String, Number> dataSeries1 = new XYChart.Series();

		ArrayList<String> sortedKeysList = new ArrayList(playerAndMilitaryCountMap.keySet());
		Collections.sort(sortedKeysList);
		for(String key : sortedKeysList) {
			dataSeries1.getData().add(new XYChart.Data<String, Number>(key, playerAndMilitaryCountMap.get(key)));
		}
		militaryDominationbarChart.getData().clear();
		//militaryDominationbarChart.getData().clear();
		militaryDominationbarChart.getData().addAll(dataSeries1);
	}
	
}
