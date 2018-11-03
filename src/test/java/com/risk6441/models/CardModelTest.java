/**
 * 
 */
package com.risk6441.models;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.risk6441.config.CardKind;
import com.risk6441.entity.Card;

/**
 * @author Nirav
 *
 */
public class CardModelTest {

	static CardModel cardModel;

	static List<Card> selectedCardList;


	/**
	 * This method is invoked at the beginning of the test class.
	 */
	@BeforeClass
	public static void beforeClass() {
		cardModel = new CardModel();
		selectedCardList = new ArrayList<>();
	}

	/**
	 * This method is invoked at the beginning of the every test.
	 */
	@Before
	public void beforeTest() {
		selectedCardList.clear();
	}

	/**
	 * This method is used to check different combinations of cards and if trade is
	 * possible or not.
	 */
	@Test
	public void testIsCardsvalidForTradeForDiffCards() {
		selectedCardList.add(new Card(CardKind.ARTILLERY));
		selectedCardList.add(new Card(CardKind.CAVALRY));
		selectedCardList.add(new Card(CardKind.INFANTRY));
		boolean actualResult = cardModel.isCardsvalidForTrade(selectedCardList);
		Assert.assertEquals(true, actualResult);
	}

	/**
	 * This method is used to check similar combinations of cards
	 * (CAVALRY/INFANTRY/ARTILLERY) and if trade is possible or not.
	 */
	@Test
	public void testIsCheckTradePossibleForSameCards() {
		selectedCardList.add(new Card(CardKind.CAVALRY));
		selectedCardList.add(new Card(CardKind.CAVALRY));
		selectedCardList.add(new Card(CardKind.CAVALRY));
		boolean actualResult = cardModel.isCardsvalidForTrade(selectedCardList);
		Assert.assertEquals(true, actualResult);
		selectedCardList.clear();
		selectedCardList.add(new Card(CardKind.INFANTRY));
		selectedCardList.add(new Card(CardKind.INFANTRY));
		selectedCardList.add(new Card(CardKind.INFANTRY));
		boolean actualResult1 = cardModel.isCardsvalidForTrade(selectedCardList);
		Assert.assertEquals(true, actualResult1);
		selectedCardList.clear();
		selectedCardList.add(new Card(CardKind.ARTILLERY));
		selectedCardList.add(new Card(CardKind.ARTILLERY));
		selectedCardList.add(new Card(CardKind.ARTILLERY));
		boolean actualResult2 = cardModel.isCardsvalidForTrade(selectedCardList);
		Assert.assertEquals(true, actualResult2);
	}

	/**
	 * This method is used to check number of cards are not 3 and if trade is
	 * possible or not.
	 */
	@Test
	public void testisCardsvalidForTrade() {
		selectedCardList.add(new Card(CardKind.CAVALRY));
		selectedCardList.add(new Card(CardKind.INFANTRY));
		selectedCardList.add(new Card(CardKind.ARTILLERY));
		selectedCardList.add(selectedCardList.get(0));
		selectedCardList.add(selectedCardList.get(1));
		selectedCardList.add(selectedCardList.get(2));
		boolean actualResult = cardModel.isCardsvalidForTrade(selectedCardList);
		Assert.assertEquals(false, actualResult);
	}

	/**
	 * This method is used to check number of cards are 3 and if trade is possible
	 * or not.
	 */
	@Test
	public void checkTradePossibleForNNumberOfCardsSuccess() {
		selectedCardList.add(new Card(CardKind.CAVALRY));
		selectedCardList.add(new Card(CardKind.INFANTRY));
		selectedCardList.add(new Card(CardKind.ARTILLERY));
		boolean actualResult = cardModel.isCardsvalidForTrade(selectedCardList);
		Assert.assertEquals(true, actualResult);
	}
}