package com.risk6441.models;

import java.util.List;

import com.risk6441.config.CardKind;
import com.risk6441.entity.Card;
import com.risk6441.entity.Player;

/**
 * This class is handles the behavior of the card.
 * @author Nirav
 */
public class CardModel {

	private Player currentPlayer;	
	
	private List<Card> cardForExchange;

	/**
	 * @return the currentPlayer
	 */
	public Player getCurrentPlayer() {
		return currentPlayer;
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
	}
	
	public void openCardWindow() {
		
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
	
}
