package com.risk6441.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;

import org.apache.commons.lang3.StringUtils;

import com.risk6441.config.Config;
import com.risk6441.entity.Continent;
import com.risk6441.entity.Map;
import com.risk6441.entity.Player;
import com.risk6441.entity.Territory;
import com.risk6441.exception.InvalidGameActionException;
import com.risk6441.exception.InvalidMapException;
import com.risk6441.gameutils.GameUtils;
import com.risk6441.maputils.CommonMapUtil;
import com.risks6441.models.PlayerModel;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

/**
 * This class ....
 * @author Nirav Charles
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
	
	
    /**
     * The @btnReinforcement
     */
    @FXML
    private Button btnReinforcement;
    
    @FXML
    private Label lblCurrPlayer;


    @FXML
    private Label lblGamePhase;

    
    @FXML
    private Button btnPlaceArmy;


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
    	
    	startGame(true);
    }
    
    /** This method will be called by user to start the fortification phase
     * 
     * @param event button click event will be passes as parameter
     */
    @FXML
    void fortify(ActionEvent event) {

    	int numPlayer= choiceBoxNoOfPlayer.getSelectionModel().getSelectedItem();
    	
    	Territory territory = null;
		Territory adjTerritory=null;
    	int armyCount=0;
    	territory=terrList.getSelectionModel().getSelectedItem();
    	adjTerritory=adjTerrList.getSelectionModel().getSelectedItem();
    	
    	
    	if(territory == null) {
    		CommonMapUtil.alertBox("Info", "Please select a territory", "Alert");
    		return;
    	}else if(adjTerritory == null) {
    		CommonMapUtil.alertBox("Info", "Please select a adjacent territory", "Alert");
    		return;
    	}else if(adjTerritory.getPlayer() != territory.getPlayer()) {
    		CommonMapUtil.alertBox("Info", "The Adjacent Territory does not belong to you.", "Alert");
    		return;
    	}
    	
    	armyCount=CommonMapUtil.inputDialogueBoxForFortification();
    	if(armyCount > 0) {
    		System.out.println("ArmyCount"+armyCount);
    		if(armyCount >= territory.getArmy()) {
    			CommonMapUtil.alertBox("Info", "The Army to be moved in fortification phase should be less than "
    					+ "existing army in territory.(e.g It can be maximum x-1, if x is the current army in territory.)", "Alert");
        		return;
    		}else {
    			territory.setArmy(territory.getArmy() - armyCount);
    			adjTerritory.setArmy(adjTerritory.getArmy() + armyCount);
    			updateMap();
				terrList.refresh();
				adjTerrList.refresh();
				GameUtils.addTextToLog("======Fortification Done ===========", txtAreaMsg);
    		}
    	}else {
    		CommonMapUtil.alertBox("Info", "Invalid Input. Number should be > 0.", "Alert");
    		return;
    	}
    	
    	loadCurrentPlayer(false);
    	initializeReinforcement();
    }
    
    /** This method will allow the players to place the armies one by one in round robin fashion
     * 
     * @param event Button triggered event will be passed as parameter
     */
    @FXML
    void placeArmy(ActionEvent event) {
    	int playerArmies = currentPlayer.getArmies();
		if (playerArmies > 0) {
			Territory territory = terrList.getSelectionModel().getSelectedItem();
			if (territory == null) {
				territory = terrList.getItems().get(0);
			}
			territory.setArmy(territory.getArmy() + 1);
			currentPlayer.setArmies(playerArmies - 1);
		}
		map.setChangedForMap();
		terrList.refresh();
		
		//if exhausted then call next phases
		
		boolean armiesExhausted = GameUtils.checkIfPlayersArmiesExhausted(playerList);
		if (armiesExhausted) {
			loadCurrentPlayer(true);
			//initialize reinforcement after all player has placed their army.
			initializeReinforcement();
		} else {
				startGame(false);
		}
    	
    }
    
    /** This method will allow the user to place the armies after the fortification phase is completed
     * 
     * @param event button click event will be passed as parameter
     */
    @FXML
    void reinforce(ActionEvent event) {
    	Territory territory = terrList.getSelectionModel().getSelectedItem();
    	playerModel.reinforcementPhase(territory, txtAreaMsg);
    	setCurrentPlayerLabel(currentPlayer.getName() + ":- " + currentPlayer.getArmies() + " armies left.");
    	updateMap();
    	terrList.refresh();
    }

    
    // constructor to initialize the Map object
	public PlayGameController(Map map) {
		this.map = map;
		this.playerModel = new PlayerModel();
		playerModel.addObserver(this);
		
	}
	
	// Default constructor
	public PlayGameController() {
		
	}
	
	//sets the label text of player to current player
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
		
		playerModel.setPlayerPlaying(currentPlayer);
		playerModel.setTerritoryWon(0);
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
	
	public void checkPlayerWithNoArmyWhilePlacingArmy() {
		if(currentPlayer.getArmies()==0) {
			GameUtils.addTextToLog("Skipped "+currentPlayer.getName()+" It doesn't have army for placing.\n", txtAreaMsg);
			startGame(false);
		}
	}
	
	/** 
	 * Method for starting the game and performs the looping operation for each phase of game play
	 * @param isCallingInitializeReinforce true if it needs to call method for initializing reinforcement, else false   
	 */
	public void startGame(boolean isCallingInitializeReinforce) {
		KeyFrame kf1 = new KeyFrame(Duration.seconds(0.1), e -> loadCurrentPlayer(false));
		KeyFrame kf2 = new KeyFrame(Duration.seconds(0.1), e -> checkPlayerWithNoArmyWhilePlacingArmy());
		if(!isCallingInitializeReinforce) {
			final Timeline timeline = new Timeline(kf1,kf2);
		    Platform.runLater(timeline::play);
		}else {
			System.out.println("Pressed End Button");
			KeyFrame kf3 = new KeyFrame(Duration.seconds(0.1), e -> initializeReinforcement());
			final Timeline timeline = new Timeline(kf1, kf3);
		    Platform.runLater(timeline::play);
		}
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
		startGame(false);
	}
	
	/* (non-Javadoc)
	 * @see javafx.fxml.Initializable#initialize(java.net.URL, java.util.ResourceBundle)
	 */
	public void initialize(URL location, ResourceBundle resources) {
		
		choiceBoxNoOfPlayer.getItems().addAll(2,3, 4, 5, 6);
		playerList = new ArrayList<>(); 
		updateMap();
		
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
		if (PlayerModel.playerHasAValidAttackMove(terrList, txtAreaMsg)) {
			CommonMapUtil.enableControls(btnEndTurn);
			CommonMapUtil.disableControls(btnReinforcement, btnFortify, btnPlaceArmy);
			adjTerrList.setOnMouseClicked(e -> attack());
		}
		
	}

	public void attack() {
		Territory attackingTerritory = terrList.getSelectionModel().getSelectedItem();
		Territory defendingTerritory = adjTerrList.getSelectionModel().getSelectedItem();
		try {
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
		
		if(GameUtils.isFortificationPhasePossible(map, currentPlayer)) {
			GameUtils.addTextToLog("===============================\n", txtAreaMsg);
			GameUtils.addTextToLog("The Fortification phase has begun.\n", txtAreaMsg);
			btnFortify.setDisable(false);
			btnFortify.requestFocus();
			CommonMapUtil.disableControls(btnReinforcement);
		}else {
			GameUtils.addTextToLog("Fortification phase has begun.\n", txtAreaMsg);
			GameUtils.addTextToLog(currentPlayer.getName() + " does not have any armies for fortification.", txtAreaMsg);
			loadCurrentPlayer(false);
			initializeReinforcement();
		}
		
		
	}

	/**
	 * This method initialized the component for the reinforcement phase.
	 */
	private void initializeReinforcement() {
		System.out.println("Inside intialize reinforcement");
		CommonMapUtil.disableControls(btnPlaceArmy, btnFortify, btnEndTurn);
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
	 * Check If Any Player Lost the game.
	 */
	private void checkIfAnyPlayerLostTheGame() {
		Player playerLost = playerModel.checkIfAnyPlayerLostTheGame(playerList);
		if (playerLost != null) {
			playerList.remove(playerLost);
			playerListIterator = playerList.iterator();
			CommonMapUtil.alertBox("Info", "Player: " + playerLost.getName() + " lost all his territory and is out of the game",
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
		checkIfAnyPlayerLostTheGame();
		terrList.getItems().clear();
		adjTerrList.getItems().clear();
		for (Territory territory : currentPlayer.getAssignedTerritory()) {
			terrList.getItems().add(territory);
		}
		if(!StringUtils.isEmpty(Config.message)) {
			GameUtils.addTextToLog(Config.message, txtAreaMsg);
		}
		updateMap();
		//populateWorldDominationData();
		setCurrentPlayerLabel(currentPlayer.getName() + ":- " + currentPlayer.getArmies() + " armies left.\n");
		if (!checkIfPlayerWonTheGame()) {
			playerModel.playerHasAValidAttackMove(terrList, txtAreaMsg);
		}
	}
	
	/**
	 * Check If Any Player Won the game.
	 */
	private boolean checkIfPlayerWonTheGame() {
		boolean playerWon = false;
		if (playerList.size() == 1) {
			CommonMapUtil.alertBox("Player: " + playerList.get(0).getName() + " won the game!", "Info", "");
			playerWon = true;
			disableGamePanel();
		}

		return playerWon;
	}

	/**
	 * Disable the game after game is over
	 */
	private void disableGamePanel() {
		CommonMapUtil.disableControls(terrList, adjTerrList, btnReinforcement, btnFortify, btnCards,
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
		System.out.println("update called because of object change");
		String str = (String) arg;
		if (str.equals("rollDiceComplete")) {
			refreshView();
		}else if(str.equals("Attack")) {
			initializeAttack();
		}
	}


}
