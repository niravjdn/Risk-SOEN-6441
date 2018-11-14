package com.risk6441.models;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import com.risk6441.config.CardKind;
import com.risk6441.controller.CardExchangeController;
import com.risk6441.entity.Card;
import com.risk6441.entity.Player;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * This class is handles the behavior of the card.
 * @author Nirav
 */
public class CardModel extends Observable implements Serializable{


	/**
	 * The serial ID
	 */
	private static final long serialVersionUID = -5002509561959806377L;

	private Player currentPlayer;	
	
	private List<Card> cardForExchange;

	public boolean isRestrictedModeTillLessThan5 = false;
	/**
	 * @return the currentPlayer
	 */
	public Player getCurrentPlayer() {
		return currentPlayer;
	}

	/**
	 * Constructor for the CardModel Class
	 */
	public CardModel() {
		
	}
	
	/**
	 * @param currentPlayer the currentPlayer to set
	 */
	public void setCurrentPlayer(Player currentPlayer) {
		this.currentPlayer = currentPlayer;
	}

	/**
	 * @return the cardsToBeExchange
	 */
	public List<Card> getCardsToBeExchange() {
		return cardForExchange;
	}

	/**
	 * @param cardsToBeExchange the cardsToBeExchange to set
	 */
	public void setCardsToBeExchange(List<Card> cardsToBeExchange) {
		this.cardForExchange = cardsToBeExchange;
	}

	/**
	 * This is parameterized constructor for card model.
	 * @param currentPlayer current player.
	 */
	public CardModel(Player currentPlayer) {
		super();
		this.currentPlayer = currentPlayer;
		this.cardForExchange = new ArrayList<Card>();
	}
	
	/**
	 * This method opens the card view when the card button is clicked	
	 * @param isRestrictedModeTillLessThan5 Boolean variable denoting if player has less than 5 cards.
	 */
	public void openCardWindow(boolean isRestrictedModeTillLessThan5) {

		this.isRestrictedModeTillLessThan5 = isRestrictedModeTillLessThan5;
		CardExchangeController controller = new CardExchangeController(currentPlayer, this, isRestrictedModeTillLessThan5);
		final Stage stage = new Stage();
		stage.setTitle("Attack Window");

		FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("cardview.fxml"));
		loader.setController(controller);
		
				
		Parent root = null;
		try {
			root = (Parent) loader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}

		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
		
		

	}
	
	/**
	 * This method checks if the trade is valid or not.
	 * @param selectedCardForExchange Selected cards for exchange.
	 * @return Boolean variable.
	 */
	public boolean isCardsvalidForTrade(List<Card> selectedCardForExchange) {
		boolean returnFlag = false;
		if(selectedCardForExchange.size()==3) {
			int infantry = 0, cavalry = 0, artillery = 0;
			for (Card card : selectedCardForExchange) {
				if(card.getCardKind().toString().equals(CardKind.CAVALRY.toString())) {
					infantry++;
				}
				else if(card.getCardKind().toString().equals(CardKind.INFANTRY.toString())) {
					cavalry++;
				}
				else if(card.getCardKind().toString().equals(CardKind.ARTILLERY.toString())) {
					artillery++;
				}
			}
			//if all are of different kind or all are of same kind then only, player can exchange cards for army.
			if((infantry==1 && cavalry==1 && artillery==1) || infantry==3 || cavalry==3 || artillery==3) {
				returnFlag = true;
			}
		}
		return returnFlag;
	}
	

	/**
	 * This method notifies the observer of the cardmodel, which is playgamecontroller regarding trade
	 * @param cardsForExchange list of the cards selected by the user
	 */
	public void setCardsForExchange(List<Card> cardsForExchange) {
		setCardsToBeExchange(cardsForExchange);
		setChanged();
		notifyObservers("tradeCard");
	}


}
