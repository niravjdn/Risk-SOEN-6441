/**
 * 
 */
package com.risk6441.entity;

import com.risk6441.config.CardKind;

/**
 * @author Nirav
 *
 */
public class Card {
	
	CardKind cardKind;
	
	private Territory territoryToWhichCardBelong;

	/**
	 * @return the cardKind
	 */
	public CardKind getCardKind() {
		return cardKind;
	}

	/**
	 * Parameterized Constructor for Card
	 * 
	 * @param cardType
	 *            reference to get cardType enum
	 */
	
	public Card(CardKind cardKind){
		this.cardKind = cardKind;
	}
	
	/**
	 * @param cardKind the cardKind to set
	 */
	public void setCardKind(CardKind cardKind) {
		this.cardKind = cardKind;
	}

	/**
	 * @return the territoryToWhichCardBelong
	 */
	public Territory getTerritoryToWhichCardBelong() {
		return territoryToWhichCardBelong;
	}

	/**
	 * @param territoryToWhichCardBelong the territoryToWhichCardBelong to set
	 */
	public void setTerritoryToWhichCardBelong(Territory territoryToWhichCardBelong) {
		this.territoryToWhichCardBelong = territoryToWhichCardBelong;
	}
	
	
}
