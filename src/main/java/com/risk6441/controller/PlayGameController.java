package com.risk6441.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.risk6441.maputils.CommonMapUtil;
import com.risk6441.maputils.GameUtils;
import com.risk6441.models.Continent;
import com.risk6441.models.Map;
import com.risk6441.models.Player;
import com.risk6441.models.Territory;

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

/**
 * This class ....
 * @author Nirav Charles
 */
public class PlayGameController implements Initializable{

	private Map map;
	
	private int noOfPlayer;
	
	private List<Player> playerList;
	
	private Player currentPlayer;
	
	
	private Iterator<Player> playerListIterator;
	
	private ScheduledExecutorService scheduledExecutor;
	
    /**
     * The @btnReinforcement
     */
    @FXML
    private Button btnReinforcement;
    
    @FXML
    private Label lblCurrPlayer;

    @FXML
    private Button btnPlaceArmy;

    @FXML
    private Button btnAttack;

    @FXML
    private Button btnFortify;

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

    @FXML
    void attack(ActionEvent event) {
    	
    }
    
    /** This method ends the turn of particular player using Scheduled Executor class
     * 
     * @param event button event will be passed as a parameter
     */
    @FXML
    void endTrun(ActionEvent event) {
    	if (!scheduledExecutor.isShutdown()) {
    		scheduledExecutor.shutdown();
    	}
    	startGame();
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
    	
    	
    	boolean checkCounter=GameUtils.checkFortificationPhase(numPlayer);
    	if (checkCounter) 
    	{
			scheduledExecutor.shutdownNow();
			loadCurrentPlayer();
			initializeReinforcement();
		} 
    	else 
    	{
			scheduledExecutor.shutdownNow();
			if (scheduledExecutor.isShutdown()) 
			{
				startGame();
			}
		}
    	
    
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
		updateMap();
		terrList.refresh();
		
		//if exhausted then call next phases
		
		boolean armiesExhausted = GameUtils.checkIfPlayersArmiesExhausted(playerList);
		if (armiesExhausted) {
			scheduledExecutor.shutdownNow();
			loadCurrentPlayer();
			initializeAttack();
		} else {
			scheduledExecutor.shutdownNow();
			if (scheduledExecutor.isShutdown()) {
				startGame();
			}
		}
    	
    }
    
    /** This method will allow the user to place the armies after the fortification phase is completed
     * 
     * @param event button click event will be passed as parameter
     */
    @FXML
    void reinforce(ActionEvent event) {
    	if(currentPlayer.getArmies()>0)
    	{
    		int getArmy=CommonMapUtil.inputDialogueBoxForRenforcement();

    		Territory territory = terrList.getSelectionModel().getSelectedItem();

    		if(territory == null) {
    			CommonMapUtil.alertBox("infor", "Please select a territory to reinforce army on.", "Alert");
    			return;
    		}

    		if(getArmy > 0) {
    			if(getArmy > currentPlayer.getArmies()) {
    				CommonMapUtil.alertBox("Info", "The Army to be moved in reinforce phase should be less than army you have.", "Alert");
    				return;
    			}else {
    				territory.setArmy(territory.getArmy() + getArmy);
    				currentPlayer.setArmies(currentPlayer.getArmies() - getArmy);
    				GameUtils.addTextToLog("====== "+getArmy+" assigned to : ==========="+territory+"  -- Player "+currentPlayer.getName(), txtAreaMsg);
    				updateMap();
    				terrList.refresh();
    				GameUtils.addTextToLog("======Reinforce Phase Completed. ===========", txtAreaMsg);
    			}
    		}else {
    			CommonMapUtil.alertBox("Info", "Invalid Input. Number should be > 0.", "Alert");
    			return;
    		}
    	}
    	if (currentPlayer.getArmies() == 0) {
    		initializeAttack();
    	}

    }

    
    // constructor to initialize the Map object
	public PlayGameController(Map map) {
		this.map = map;
	}
	
	// Default constructor
	public PlayGameController() {
		
	}
	
	//sets the label text of player to current player
	public void setCurrentPlayerLabel(String  str) {
		lblCurrPlayer.setText("Playing... : "+str); 
	}
	
	/** 
	 * Loads the current player and clears the selected & adjacent territory list
	 */
	public Player loadCurrentPlayer() {
		if (!playerListIterator.hasNext()) {
			playerListIterator = playerList.iterator();
		}
		currentPlayer = playerListIterator.next();
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
	 * Method for starting the game and performs the looping operation for each phase of game play   
	 */
	public void startGame() {
		scheduledExecutor = Executors.newSingleThreadScheduledExecutor();
		scheduledExecutor.scheduleWithFixedDelay(new Runnable() {
			@Override
			public void run() {
				Platform.runLater(() -> loadCurrentPlayer());
			}

		}, 0, 300000, TimeUnit.MILLISECONDS);
	}
	
	/**
	 * This method allocates territories to the player and start the game.
	 */
	private void allocateTerritoriesToPlayer() {
		GameUtils.addTextToLog("===Assigning territories===\n", txtAreaMsg);
		GameUtils.allocateTerritoryToPlayer(map, playerList, txtAreaMsg);
		GameUtils.addTextToLog("===Territories assignation complete===\n", txtAreaMsg);
		updateMap();
		startGame();
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

				
				
				playerList = GameUtils.createPlayers(noOfPlayer, playerList, txtAreaMsg);
				GameUtils.addTextToLog("===Players creation complete===\n", txtAreaMsg);

				choiceBoxNoOfPlayer.setDisable(true);
				playerListIterator = playerList.iterator();
				CommonMapUtil.enableControls(btnPlaceArmy);
				GameUtils.assignArmiesToPlayers(playerList, txtAreaMsg);
				allocateTerritoriesToPlayer();
			}
		});
		
		CommonMapUtil.disableControls(btnAttack, btnEndTurn, btnFortify, btnPlaceArmy, btnReinforcement);
		
		
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
		GameUtils.addTextToLog("The Attack phase is under developmet.", txtAreaMsg);
		btnAttack.setDisable(false);
		btnAttack.requestFocus();
		CommonMapUtil.disableControls(btnReinforcement, btnFortify, btnPlaceArmy);
		initializeFortification();
	}

	/**
	 * This method intializes the components for the fortification phase.
	 */
	private void initializeFortification() {
		
		if(GameUtils.isFortificationPhasePossible(map, currentPlayer)) {
			GameUtils.addTextToLog("===============================\n", txtAreaMsg);
			GameUtils.addTextToLog("Fortification phase has begun.", txtAreaMsg);
			btnFortify.setDisable(false);
			btnFortify.requestFocus();
			CommonMapUtil.disableControls(btnReinforcement, btnAttack);
		}else {
			GameUtils.addTextToLog("Fortification phase has begun.", txtAreaMsg);
			GameUtils.addTextToLog(currentPlayer.getName() + " does not have any armies for fortification.", txtAreaMsg);
			loadCurrentPlayer();
			initializeReinforcement();
		}
		
		
	}

	/**
	 * This method initialized the component for the reinforcement phase.
	 */
	private void initializeReinforcement() {
		CommonMapUtil.disableControls(btnPlaceArmy, btnAttack, btnFortify);
		btnReinforcement.setDisable(false);
		btnReinforcement.requestFocus();
		GameUtils.addTextToLog("=======================================\n", txtAreaMsg);
		GameUtils.addTextToLog("Reinforcement phase has begun.", txtAreaMsg);
	}
	
	/**
	 * method to count the number of armies to be assigned to a player in reinforcement phase.
	 */
	public void countReinforcementArmies() {
		if (this.currentPlayer != null) {
			currentPlayer = GameUtils.countReinforcementArmies(map, currentPlayer);
			setCurrentPlayerLabel(currentPlayer.getName() + ":- " + currentPlayer.getArmies() + " armies left.");
		} else {
			GameUtils.addTextToLog("Error! No Current Player.", txtAreaMsg);
		}
	}
	

}
