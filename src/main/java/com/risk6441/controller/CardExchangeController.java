package com.risk6441.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
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

/**
 * This class is the controller for the card exchange view.
 * @author Nirav
 *
 */
public class CardExchangeController implements Initializable{

	@FXML
	private VBox vBox;

	@FXML
	private Label lblPlayer;

	@FXML
	private Button btnTrade;

	@FXML
	private Button btnCancel;

	@FXML
	private Label lblTitle;
	
	private Player currentPlayer;
	
	private CardModel cardModel;
	
	private List<Card> cardsOfPlayer;
	
	private CheckBox[] cardCheckBoxes;
	
	private boolean isCancleBtnDisable;
	
	/**
	 * Parameterized Constructor for the Controller
	 * @param currentPlayer This the current player object.
	 * @param cardModel This is the card model object.
	 * @param isCancleBtnDisable True if cancle btn is disabled by default else false.
	 */
	public CardExchangeController(Player currentPlayer, CardModel cardModel, boolean isCancleBtnDisable) {
		super();
		this.isCancleBtnDisable= isCancleBtnDisable;
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

		//get selected check boxes from the vbox
		List<Card> selectedCardsForTrade = new ArrayList<Card>();
		
		int counter=0;
		for (CheckBox cb : cardCheckBoxes) {
			if(cb.isSelected()) {
				selectedCardsForTrade.add(cardsOfPlayer.get(counter));
			}
			counter++;
		} 
		
		if(selectedCardsForTrade.size() == 3)
		{
			boolean flag = cardModel.isCardsvalidForTrade(selectedCardsForTrade);
			
			if(flag) {
				cardModel.setCardsForExchange(selectedCardsForTrade);
				//card exchange done now
				//so consider the player has exchanged 3 cards and check if cards - 3 less then 5 then disable trade button
				if(cardModel.isRestrictedModeTillLessThan5) 
				{
					if(currentPlayer.getCardList().size()-3 < 5) 
					{
						CommonMapUtil.disableControls(btnTrade);
						CommonMapUtil.enableControls(btnCancel);
					}else {
						//it's still greater than 5 //must exchange
						CommonMapUtil.disableControls(btnCancel);
					}
				}
				showCardsByRemoving(selectedCardsForTrade);
			}
			else {			
				CommonMapUtil.alertBox("Info", "Invalid Combination of Cards. All cards should be same or of different kind.", "Info");
				return;
			}	
		}
		else
			CommonMapUtil.alertBox("Info", "The number of cards must be 3", "Info");
	}
	

	/* (non-Javadoc)
	 * @see javafx.fxml.Initializable#initialize(java.net.URL, java.util.ResourceBundle)
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		setPlayerLabel();
		showCards();
		
		if(isCancleBtnDisable) {
			CommonMapUtil.disableControls(btnCancel);
		}
	}
	
	/**
	 * This method show cards of the player in the box 
	 */
	private void showCards() {
		vBox.getChildren().clear();
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
	 * This method show cards of the player after player has sold the cards
	 * @param soldCards list of the cards which player has sold
	 */
	private void showCardsByRemoving(List<Card> soldCards) {
		vBox.getChildren().clear();
		cardsOfPlayer = currentPlayer.getCardList();
		cardsOfPlayer.removeAll(soldCards);
		if(cardsOfPlayer.size() < 3) 
		{
			CommonMapUtil.disableControls(btnTrade);			
		}
		cardCheckBoxes = new CheckBox[cardsOfPlayer.size()];	
						
		for (int i = 0; i < cardsOfPlayer.size(); i++){
			cardCheckBoxes[i] = new CheckBox(cardsOfPlayer.get(i).getCardKind().toString() + " => " + cardsOfPlayer.get(i).getTerritoryToWhichCardBelong().getName());
		}
		vBox.getChildren().addAll(cardCheckBoxes);
	}

	
	/**
	 *This method set label for the current player who is playing the game.
	 */
	private void setPlayerLabel() {
		lblPlayer.setText("Player : "+currentPlayer.getName());
	}
	
	/**
	 * This method is invoked if a trade is possible for the strategy.
	 */
	public void tradeIfPossibleForStrategy() {
		int size = currentPlayer.getCardList().size();
		size = doTrade(size);
		
		if(cardModel.isRestrictedModeTillLessThan5) {
			
			while(size > 5) {
				size = doTrade(size);
			}
		}
	}
	/**
	 * This method is responsible for making the trade.
	 */
	private int doTrade(int size) {
		cardsOfPlayer = currentPlayer.getCardList();
		List<Card> cards = cardModel.getCombinationOfCards(cardsOfPlayer);
		if(cards == null)
			return 1;
		
		String str = "Cards : ";
		for(Card c : cards)
			str += c;
		str += "\n";
		GameUtils.addTextToLog(str);
		if (cards != null && cards.size() == 3) {
			cardModel.setCardsForExchange(cards);
			size = size -3;
		}
		return size;
	}
	
	

}
