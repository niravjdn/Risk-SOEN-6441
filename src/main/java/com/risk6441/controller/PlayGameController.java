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
 * @author Nirav
 * @author Charles
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

    @FXML
    void endTrun(ActionEvent event) {
    	if (!scheduledExecutor.isShutdown()) {
    		scheduledExecutor.shutdown();
    	}
    	startGame();
    }

    @FXML
    void fortify(ActionEvent event) {
    		fortificationPhase();
    }
    void fortificationPhase()
    {
    	int numPlayer= choiceBoxNoOfPlayer.getSelectionModel().getSelectedItem();
    	
    	Territory territory = null;
		Territory adjTerritory=null;
    	int armyCount=0;
    	territory=terrList.getSelectionModel().getSelectedItem();
    	adjTerritory=adjTerrList.getSelectionModel().getSelectedItem();
    	if(terrList.getSelectionModel().getSelectedItem()==null)
    	{
    		CommonMapUtil.alertBox("Message", "Please select a territory", "Alert");
    		return;
    	}
    	else
    	{
    		
    			if(adjTerrList.getSelectionModel().getSelectedItem()==null)
    			{
    				CommonMapUtil.alertBox("Message", "Please select an adjacent territory", "Alert");
    				return;
    			}
    			else
    			{
    				armyCount=CommonMapUtil.inputDialogueBoxForFortification();
    				
	    			if(armyCount>0)
	    			{
	    				if (territory == null) {
	    					territory = terrList.getItems().get(0);
	    				}
	    				if(adjTerritory==null)
	    					adjTerritory=adjTerrList.getItems().get(0);
	    				territory.setArmy(territory.getArmy() - armyCount);
	    				adjTerritory.setArmy(adjTerritory.getArmy() + armyCount);
	    				updateMap();
	    				terrList.refresh();
	    				adjTerrList.refresh();
	    				
	    			}
    			}
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
			intializeAttack();
		} else {
			scheduledExecutor.shutdownNow();
			if (scheduledExecutor.isShutdown()) {
				startGame();
			}
		}
    	
    }

    @FXML
    
    void reinforce(ActionEvent event) {
    		//proceed if armies are there > 0
    	    // ask for inpput using commonmaputils inputdialogbox 
    	// if input < armies then good to proceed
    	//else show alert box using commonaputil showalert
    	int getArmy=0;
    	if(currentPlayer.getArmies()>0)
    	{
    		getArmy=CommonMapUtil.inputDialogueBoxForFortification();
    		if(getArmy<currentPlayer.getArmies())
    		{
    			Territory territory = terrList.getSelectionModel().getSelectedItem();
    			if (territory == null) {
    				territory = terrList.getItems().get(0);
    			}
    			territory.setArmy(territory.getArmy()+getArmy);
    			currentPlayer.setArmies(currentPlayer.getArmies() - getArmy);
    			updateMap();
        		terrList.refresh();
    		}
    		else
    			CommonMapUtil.alertBox("Message", "Please provide input value less than army count", "Alert");
    		
    	}
    	
    	if (currentPlayer.getArmies() == 0) {
			intializeAttack();
		}
    		
}

    
    
	public PlayGameController(Map map) {
		this.map = map;
	}
	
	public PlayGameController() {
		
	}
	
	public void setCurrentPlayerLabel(String  str) {
		lblCurrPlayer.setText("Playing... : "+str); 
	}
	
	
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
	 * Update the map to show latest data.
	 */
	public void updateMap() {
		vbox.getChildren().clear();
		for(Continent c : map.getContinents()) {
			vbox.autosize();
			vbox.getChildren().add(CommonMapUtil.getNewPaneForVBox(c));
		}
	}
	
	public void intializeAttack() {
		GameUtils.addTextToLog("===============================\n", txtAreaMsg);
		GameUtils.addTextToLog("This phase is under developmet.", txtAreaMsg);
		btnAttack.setDisable(false);
		btnAttack.requestFocus();
		CommonMapUtil.disableControls(btnReinforcement, btnFortify);
		initializeFortification();
	}

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

	private void initializeReinforcement() {
		CommonMapUtil.disableControls(btnPlaceArmy, btnAttack, btnFortify);
		btnReinforcement.setDisable(false);
		btnReinforcement.requestFocus();
		GameUtils.addTextToLog("=======================================\n", txtAreaMsg);
		GameUtils.addTextToLog("Reinforcement phase has begun.", txtAreaMsg);
	}
	
	public void countReinforcementArmies() {
		if (this.currentPlayer != null) {
			currentPlayer = GameUtils.countReinforcementArmies(map, currentPlayer);
			setCurrentPlayerLabel(currentPlayer.getName() + ":- " + currentPlayer.getArmies() + " armies left.");
		} else {
			GameUtils.addTextToLog("Error! No Current Player.", txtAreaMsg);
		}
	}
	

}
