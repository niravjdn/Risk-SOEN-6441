package com.risk6441.models;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.risk6441.config.PlayerStrategy;
import com.risk6441.entity.Continent;
import com.risk6441.entity.Map;
import com.risk6441.entity.Player;
import com.risk6441.entity.Territory;
import com.risk6441.exception.InvalidGameActionException;
import com.risk6441.exception.InvalidMapException;
import com.risk6441.gameutils.GameUtils;
import com.risk6441.strategy.Benevolent;
import com.risk6441.strategy.Cheater;
import com.risk6441.strategy.Random;

import javafx.embed.swing.JFXPanel;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

/**
 * @author Nirav
 *
 */
public class TournamentModelTest {

	/**
	 * The playerModel.
	 */
	static PlayerModel playerModel;

	/**
	 * The tournamentModel.
	 */
	static TournamentModel tournamentModel;

	/**
	 * The txtAreaResult
	 */
	@FXML
	static TextArea txtAreaResult;

	/**
	 * The fxPane
	 */
	static JFXPanel fxPane;
	
	/**
	 * This method is invoked at the start of the test class.
	 */
	@BeforeClass
	public static void beforeClass() {
		playerModel = new PlayerModel();
		GameUtils.isTestMode = true;
		tournamentModel = new TournamentModel();
		fxPane = new JFXPanel();
		txtAreaResult = new TextArea();	
		tournamentModel.result = new HashMap<String, HashMap<String, String>>();
	}

	/**
	 * This method is invoked at the start of all the test methods.
	 */
	@Before
	public void beforeTest() {
		playerModel.setCurrentPlayer(new Player(1,"Player1"));

	}

	/**
	 * Map Clone Test
	 * @throws InvalidMapException InvalidMapException
	 * @throws CloneNotSupportedException  throws if cloning has some error
	 */
	@Test
	public void createMapCloneTest() throws  InvalidMapException, CloneNotSupportedException {
		Map map = new Map();
		Continent continent = new Continent();
		Territory territory1 = new Territory();
		Territory territory2 = new Territory();
		int controlValue = 5;
		continent.setName("Europe");
		continent.setValue(controlValue);
		territory1.setName("Germany");
		territory1.setBelongToContinent(continent);
		continent.getTerritories().add(territory1);
		territory2.setName("Egypt");
		territory2.setBelongToContinent(continent);
		continent.getTerritories().add(territory2);
		territory1.getAdjacentTerritories().add(territory2);
		territory2.getAdjacentTerritories().add(territory1);
		Continent continent2 = new Continent();
		Territory terr = new Territory();
		continent2.setName("Australia");
		continent2.setValue(5);
		terr.setName("Sydney");
		terr.setBelongToContinent(continent2);
		terr.getAdjacentTerritories().add(territory1);
		territory1.getAdjacentTerritories().add(terr);
		continent2.getTerritories().add(terr);
		map.getContinents().add(continent);
		map.getContinents().add(continent2);
		Map clonedMap = (Map) map.clone();
		Assert.assertEquals(map.getContinents().get(0).getName(), clonedMap.getContinents().get(0).getName());
		Assert.assertEquals(map.getContinents().get(0).getTerritories().get(0).getName(),
				clonedMap.getContinents().get(0).getTerritories().get(0).getName());
		Assert.assertEquals(map.getContinents().get(1).getName(), clonedMap.getContinents().get(1).getName());
		Assert.assertEquals(map.getContinents().get(1).getTerritories().get(0).getName(),
				clonedMap.getContinents().get(1).getTerritories().get(0).getName());
	}
	
	
}
