package com.risk6441.models;

import java.io.IOException;
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
public class CardModel extends Observable{

	private Player currentPlayer;	
	
	private List<Card> cardForExchange;

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
	 * @param currentPlayer
	 */
	public CardModel(Player currentPlayer) {
		super();
		this.currentPlayer = currentPlayer;
		this.cardForExchange = new ArrayList<Card>();
	}
	
	public void openCardWindow() {
		final Stage stage = new Stage();
		stage.setTitle("Attack Window");

		CardExchangeController controller = new CardExchangeController(currentPlayer, this);
		FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("cardview.fxml"));
		loader.setController(controller);

		Parent root = null;
		try {
			root = (Parent) loader.load();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	
	public boolean isCardvalidForTrade(List<Card> selectedCards) {
		boolean returnFlag = false;
		if(selectedCards.size()==3) {
			int infantry = 0, cavalry = 0, artillery = 0;
			for (Card card : selectedCards) {
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
		setCardsForExchange(cardsForExchange);
		setChanged();
		notifyObservers("tradeCard");
	}
	
	public void clear() {
		this.cardForExchange = new ArrayList<Card>();
	}
}