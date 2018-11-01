package com.risk6441.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.ResourceBundle;

import com.risk6441.entity.Card;
import com.risk6441.entity.Player;
import com.risk6441.gameutils.GameUtils;
import com.risk6441.maputils.CommonMapUtil;
import com.risk6441.models.CardModel;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class CardExchangeController implements Initializable{

	@FXML
	private Label lblPlayer;

	@FXML
	private Button btnTrade;

	@FXML
	private Button btnCancel;

	@FXML
	private Label lblTitle;

	@FXML
	private VBox vBox;

	
	private Player currentPlayer;
	
	private CardModel cardModel;
	
	private List<Card> cardsOfPlayer;
	
	private CheckBox[] cardCheckBoxes;
	
	
	/**
	 * Parameterized Constructor for the Controller
	 * @param currentPlayer
	 * @param cardModel
	 */
	public CardExchangeController(Player currentPlayer, CardModel cardModel) {
		super();
		this.currentPlayer = currentPlayer;
		this.cardModel = cardModel;
	}
	/**
     * This method handles the case when user press cancel button 
     * @param event event object for the javafx 
     */
	@FXML		
	void cancelTrade(ActionEvent event) {
		GameUtils.exitWindows(btnTrade);
	}
	/**
     * This method handles the case when user press trade button 
     * @param event event object for the javafx 
     */
	@FXML
	void trade(ActionEvent event) {

		//get selected checkboxes from the vbox
		List<Card> selectedCardsForTrade = new ArrayList<Card>();
		
		int counter=0;
		for (CheckBox cb : cardCheckBoxes) {
			if(cb.isSelected()) {
				selectedCardsForTrade.add(cardsOfPlayer.get(counter));
			}
			counter++;
		} 
		
		//if card size 3 then
		// if it is valid useng cardmode.valid/
		//then call cardmodel method setCardsForExchange
		//else invalid combination of cards
		
		
		//else select only 3 cards
		
	}

	/* (non-Javadoc)
	 * @see javafx.fxml.Initializable#initialize(java.net.URL, java.util.ResourceBundle)
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		setPlayerLabel();
		cardsOfPlayer = currentPlayer.getCardList();
		if(cardsOfPlayer.size() < 3) {
			CommonMapUtil.disableControls(btnTrade);			
		}
			
		cardCheckBoxes = new CheckBox[cardsOfPlayer.size()];				
		for (int i = 0; i < cardsOfPlayer.size(); i++){
			cardCheckBoxes[i] = new CheckBox(cardsOfPlayer.get(i).getCardKind().toString() + " => " + cardsOfPlayer.get(i).getTerritoryToWhichCardBelong().getName());
		}
		vBox.getChildren().addAll(cardCheckBoxes);
	}

	/**
	 * set label for the current player who is playing the game.
	 */
	private void setPlayerLabel() {
		lblPlayer.setText("Player : "+currentPlayer.getName());
	}
	
	

}
