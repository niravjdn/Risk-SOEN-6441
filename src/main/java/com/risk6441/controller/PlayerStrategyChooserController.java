/**
 * 
 */
package com.risk6441.controller;

import java.net.URL;
import java.util.List;
import java.util.Observable;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import com.risk6441.config.Config;
import com.risk6441.config.PlayerStrategy;
import com.risk6441.entity.Player;
import com.risk6441.gameutils.GameUtils;
import com.risk6441.strategy.Aggressive;
import com.risk6441.strategy.Benevolent;
import com.risk6441.strategy.Cheater;
import com.risk6441.strategy.Human;
import com.risk6441.strategy.IStrategy;
import com.risk6441.strategy.Random;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * This class is the controller for the Player Strategy Chooser.
 * @author Nirav
 */
public class PlayerStrategyChooserController extends Observable implements Initializable{
	
	private List<Player> playerList = null;
	
	public PlayerStrategyChooserController(List<Player> playerList) {
		this.playerList = playerList;
	}
	
	
	@FXML
    private VBox vBox;

    @FXML
    private Button btnSubmit;

    @FXML
    void submit(ActionEvent event) {
    	ObservableList<Node> hBoxes = vBox.getChildren();
    	int count = 0;
    	for (Node n : hBoxes) {
			HBox hBox = (HBox) n;
			ObservableList<Node> node = hBox.getChildren();
			
			
			if (node.get(1) instanceof ChoiceBox<?>) {
				PlayerStrategy strategyType = (PlayerStrategy) ((ChoiceBox<?>) node.get(1)).getSelectionModel().getSelectedItem();
				playerList.get(count).setPlayerStrategy(strategyType);
				//players.get(0).setType(selectedPlayerType);
				IStrategy strategy = getStrategyObjectForThePlayer(strategyType.toString());
				playerList.get(count).setStrategy(strategy);
				System.out.println(playerList.get(count).getStrategy());
				System.out.println(playerList.get(count).getPlayerStrategy());
				count++;
			}
		}
    	
    	if(count == playerList.size()) {
    		String msg = "";
    		for(Player p : playerList) {
    			msg += p.getName()+" is a playing with strategy : "+p.getPlayerStrategy()+"\n";
    		}
    		GameUtils.addTextToLog(msg);
    		
    		GameUtils.exitWindows(btnSubmit);
    		setChanged();
			notifyObservers("playerStrategyChoosen"); 	
    	}
    }

	/**
	 * @param str string defining type of strategy for the player
	 * @return strategy object
	 */
	private IStrategy getStrategyObjectForThePlayer(String str) {
		IStrategy strategy = null;
		if (str.equals("HUMAN")) {
			strategy = new Human();
		} else if (str.equals("AGGRESSIVE")) {
			strategy = new Aggressive();

		} else if (str.equals("BENEVOLENT")) {
			strategy = new Benevolent();

		} else if (str.equals("RANDOM")) {
			strategy = new Random();

		} else if (str.equals("CHEATER")) {
			strategy = new Cheater();

		}
		return strategy;
	}

	/* (non-Javadoc)
	 * @see javafx.fxml.Initializable#initialize(java.net.URL, java.util.ResourceBundle)
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		vBox.setSpacing(25);

		for (int i = 0; i < playerList.size(); i++) {
			HBox getBox = getElementsWindow(playerList.get(i));
			vBox.getChildren().addAll(getBox);
		}
	}
	
	public HBox getElementsWindow(Player player) {
		ChoiceBox<PlayerStrategy> playerStrategyType = new ChoiceBox<>();
		playerStrategyType.getItems().addAll(PlayerStrategy.values());
		playerStrategyType.getSelectionModel().select(1);;

		HBox hBox = new HBox();
		hBox.setSpacing(25);

		Label playerID = new Label();
		playerID.setText(String.valueOf(player.getId()));

		hBox.getChildren().addAll(playerID, playerStrategyType);
		return hBox;
	}
}
