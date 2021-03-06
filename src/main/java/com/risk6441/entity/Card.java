package com.risk6441.entity;
import java.io.Serializable;

import com.risk6441.config.CardKind;

/**
 * This is the bean class for the card.
 * @author Nirav
 *
 */
public class Card implements Serializable{
	
	/**
	 * The serial ID
	 */
	private static final long serialVersionUID = 745413331877577285L;

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
	 * @param cardKind
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
	 * @param territoryToWhichCardBelong the territoryToWhichCardBelong will be added
	 */
	public void setTerritoryToWhichCardBelong(Territory territoryToWhichCardBelong) {
		this.territoryToWhichCardBelong = territoryToWhichCardBelong;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Card [cardType =" + cardKind + ", TerritoryofCard=" + territoryToWhichCardBelong + "]";
	}
	
	
}
