package com.risk6441.controller;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.HashMap;
import java.util.List;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.risk6441.config.Config;
import com.risk6441.entity.Continent;
import com.risk6441.entity.Map;
import com.risk6441.entity.Player;
import com.risk6441.entity.Territory;
import com.risk6441.exception.InvalidMapException;
import com.risk6441.gameutils.GameUtils;
import com.risk6441.maputils.MapReader;
import com.risk6441.models.GameUIStateModel;
import com.risk6441.models.PlayerModel;
import com.risk6441.models.TournamentModel;
import com.risk6441.strategy.Human;

import javafx.embed.swing.JFXPanel;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;

public class PlayGameControllerTest {
	
	static PlayGameController testOb;
		
	ClassLoader loader;

	private List<Player> playerList;
	static JFXPanel jFXPanel;
	@Before
	public void before() {
		loader = getClass().getClassLoader();
		jFXPanel = new JFXPanel();
		GameUtils.isTestMode = true;
		Config.isPopUpShownInAutoMode = false;
	}
	
	
	/**
	 * Tests the save game functionality.
	 * @throws InvalidMapException Invalid map thrown.
	 */
	@Test
	public void testSaveGame() throws InvalidMapException {
		File file = new File(loader.getResource("World.map").getFile());
		Map map = new MapReader().readMapFile(file);
		
		PlayGameController controller = new PlayGameController(map);
		
		
		playerList = controller.getPlayerList();
		Player p1 = new Player(1, "Player1");
		p1.setStrategy(new Human());
		playerList.add(p1);
		
		Player p2 = new Player(2, "Player2");
		p2.setStrategy(new Human());
		playerList.add(p2);
		
		Player p3 = new Player(3, "Player3");
		p3.setStrategy(new Human());
		playerList.add(p3);
		
		file = new File(loader.getResource("saveGameController.game").getFile());
		controller.loadControllerForTest(playerList, new TextArea());
		controller.allocateArmyAndTerr();
		controller.saveGame(file);
	}
}
