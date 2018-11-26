package com.risk6441.controller;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;

import com.risk6441.config.Config;
import com.risk6441.config.PlayerStrategy;
import com.risk6441.entity.Map;
import com.risk6441.entity.Player;
import com.risk6441.exception.InvalidMapException;
import com.risk6441.gameutils.GameUtils;
import com.risk6441.maputils.CommonMapUtil;
import com.risk6441.maputils.MapReader;
import com.risk6441.models.TournamentModel;
import com.risk6441.strategy.Aggressive;
import com.risk6441.strategy.Benevolent;
import com.risk6441.strategy.Cheater;
import com.risk6441.strategy.IStrategy;
import com.risk6441.strategy.Random;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class TournamentController implements Initializable, Observer {

	private int numeberOfGames;

	private List<Map> mapList = new ArrayList<>();

	private List<Player> playerList = new ArrayList<>();
	

	private int numberOfTurns;

	private TournamentModel model;

	@FXML
	private TextArea txtAreaConsole;

	@FXML
	private AnchorPane ttlPane;

	@FXML
	private TextArea txtAreaResult;

	@FXML
	private Button btnPlay;

	@FXML
	private Button btnExit;

	@FXML
	private ComboBox<Integer> comboGames;

	@FXML
	private ComboBox<Integer> comboTurns;

	@FXML
	private Button btnMap1;

	@FXML
	private Button btnMap2;

	@FXML
	private Button btnMap3;

	@FXML
	private Button btnMap4;

	@FXML
	private Button btnMap5;

	@FXML
	private ComboBox<String> comboP1;

	@FXML
	private ComboBox<String> comboP2;

	@FXML
	private ComboBox<String> comboP3;

	@FXML
	private ComboBox<String> comboP4;

	@FXML
	private Label lblMessage;

	public TournamentController() {
		mapList = new ArrayList<>();
		playerList = new ArrayList<>();
	}

	/**
	 * @return the numeberOfGames
	 */
	public int getNumeberOfGames() {
		return numeberOfGames;
	}

	/**
	 * @param numeberOfGames the numeberOfGames to set
	 */
	public void setNumeberOfGames(int numeberOfGames) {
		this.numeberOfGames = numeberOfGames;
	}

	/**
	 * @return the numberOfTurns
	 */
	public int getNumberOfTurns() {
		return numberOfTurns;
	}

	public void setErrorMessage(String message) {
		lblMessage.setText(message);
	}

	/**
	 * @param numberOfTurns the numberOfTurns to set
	 */
	public void setNumberOfTurns(int numberOfTurns) {
		this.numberOfTurns = numberOfTurns;
	}

	@FXML
	void actionMap1(ActionEvent event) {
		File filePath = openDialogAndUploadMap(1);
		btnMap1.setText(filePath.getName());
	}

	@FXML
	void actionMap2(ActionEvent event) {
		File filePath = openDialogAndUploadMap(2);
		btnMap3.setText(filePath.getName());
	}

	@FXML
	void actionMap3(ActionEvent event) {
		File filePath = openDialogAndUploadMap(3);
		btnMap4.setText(filePath.getName());
	}

	@FXML
	void actionMap4(ActionEvent event) {
		File filePath = openDialogAndUploadMap(4);
		btnMap4.setText(filePath.getName());
	}

	@FXML
	void actionMap5(ActionEvent event) {
		File filePath = openDialogAndUploadMap(5);
		btnMap5.setText(filePath.getName());
	}

	@FXML
	void exitTournament(ActionEvent event) {
		Stage stage = (Stage) btnExit.getScene().getWindow();
		stage.close();
		stage.setOnCloseRequest(e -> Platform.exit());
	}


	/*
	 * (non-Javadoc)
	 * 
	 * @see javafx.fxml.Initializable#initialize(java.net.URL,
	 * java.util.ResourceBundle)
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		Config.isTournamentMode = true;
		lblMessage.setAlignment(Pos.CENTER);
		model = new TournamentModel();
		GameUtils.loadTurnsInTournament(comboTurns);
		GameUtils.loadGamesInTournament(comboGames);
		GameUtils.loadPlayersInTournament(comboP1);
		GameUtils.loadPlayersInTournament(comboP2);
		GameUtils.loadPlayersInTournament(comboP3);
		GameUtils.loadPlayersInTournament(comboP4);
		setUpListener();
	}

	/**
	 * 
	 */
	private void setUpListener() {
		comboTurns.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Integer>() {
			@Override
			public void changed(ObservableValue<? extends Integer> observable, Integer oldValue, Integer newValue) {
				setNumberOfTurns(comboTurns.getSelectionModel().getSelectedItem());
			}
		});

		comboGames.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Integer>() {
			@Override
			public void changed(ObservableValue<? extends Integer> observable, Integer oldValue, Integer newValue) {
				setNumeberOfGames(comboGames.getSelectionModel().getSelectedItem());
			}
		});

		comboP1.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				Player player1 = createPlayer(1);
				player1.setName("Player" + 1);
				setPlayerStrategy(comboP1.getSelectionModel().getSelectedItem(), player1);
			}
		});

		comboP2.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				Player player2 = createPlayer(2);
				player2.setName("Player" + 2);
				setPlayerStrategy(comboP2.getSelectionModel().getSelectedItem(), player2);
			}
		});

		comboP3.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				Player player3 = createPlayer(3);
				player3.setName("Player" + 3);
				setPlayerStrategy(comboP3.getSelectionModel().getSelectedItem(), player3);
			}
		});

		comboP4.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				Player player4 = createPlayer(4);
				player4.setName("Player" + 4);
				setPlayerStrategy(comboP4.getSelectionModel().getSelectedItem(), player4);
			}
		});
	}

	public Player createPlayer(int id) {
		Player player = null;
		for (Player p : playerList) {
			if (p.getId() == id) {
				player = p;
			}
		}
		if (player == null) {
			player = new Player(id, "Player" + id);
			playerList.add(player);
		}
		return player;
	}

	public void setPlayerStrategy(String playerType, Player player) {
		PlayerStrategy type = null;
		IStrategy strategy = null;
		if (PlayerStrategy.AGGRESSIVE.toString().equals(playerType)) {
			type = PlayerStrategy.AGGRESSIVE;
			strategy = new Aggressive();
		} else if (PlayerStrategy.BENEVOLENT.toString().equals(playerType)) {
			type = PlayerStrategy.BENEVOLENT;
			strategy = new Benevolent();
		} else if (PlayerStrategy.CHEATER.toString().equals(playerType)) {
			type = PlayerStrategy.CHEATER;
			strategy = new Cheater();
		} else if (PlayerStrategy.RANDOM.toString().equals(playerType)) {
			type = PlayerStrategy.RANDOM;
			strategy = new Random();
		}
		player.setPlayerStrategy(type);
		player.setStrategy(strategy);
	}

	public File openDialogAndUploadMap(int mapPosition) {
		File file = CommonMapUtil.showFileDialogForMap();
		MapReader mapReader = new MapReader();
		Map map = null;
		try {
			map = mapReader.readMapFile(file);
			if (mapPosition > 5) {
				mapList.add(4, map);
			} else {
				mapList.add(map);
			}
		} catch (InvalidMapException e) {
			e.printStackTrace();
			CommonMapUtil.alertBox("Error", e.getMessage(), "Map is not valid.");
			return file;
		}
		return file;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
	 */
	
	
	@FXML
	void playTournament(ActionEvent event) throws CloneNotSupportedException {
		setErrorMessage("");
		model.addObserver(this);
		model.txA = txtAreaResult;
		if (getNumeberOfGames() == 0) {
			setErrorMessage("Please Select number of games");
			return;
		}

		if (getNumberOfTurns() == 0) {
			setErrorMessage("Please Select number of turns");
			return;
		}

		if (mapList.isEmpty()) {
			setErrorMessage("At least one map should be there");
			return;
		} else if (playerList.size() != 2) {
			setErrorMessage("Choose at least 2 Plaeyrs.");
			return;
		} else {
			GameUtils.addTextToLog("=====Tournament started!=====\n");
//			for (Map map : mapList) {
//				int count = 1;
//				// playe game on each map
//				for (int i = 0; i < numeberOfGames; i++) {
//					Map newMap = null;
//					try {
//						// clonnig map
//						newMap = (Map) map.clone();
//					} catch (Exception e) {
//						e.printStackTrace();
//					}
//					System.out.println("Game " + (i + 1) + " started on map " + count);
//					model.startTournament(playerList, newMap, numberOfTurns, numeberOfGames, (i + 1), txtAreaConsole);
//
//				}
//				count++;
//			}
			
			
			Map newMap = (Map) mapList.get(0).clone();
			
			gameCount++;
			model.startTournament(new ArrayList<Player>(playerList), newMap, numberOfTurns, numeberOfGames, gameCount, txtAreaConsole);

		}
	}
	
	
	int mapCount = 0;
	int gameCount = 0;
	
	@Override
	public void update(Observable o, Object arg) {
		
		GameUtils.addTextToLog("Update Called in tournament\n", txtAreaConsole);
		
		if(gameCount==numeberOfGames) {
			mapCount++;
			gameCount = 0;
		}
		
		//final call
		if(mapCount==mapList.size() && gameCount==0) {
			//print result
			GameUtils.addTextToLog("Result\n", txtAreaResult);
			for (Entry<String, HashMap<String, String>> entry : model.getTournamentResult().entrySet()) {
				GameUtils.addTextToLog(entry.getKey() + "\n", txtAreaResult);
				for (Entry<String, String> data : entry.getValue().entrySet()) {
					GameUtils.addTextToLog(data.getKey() + " : " + data.getValue() + "\n", txtAreaResult);
				}
				GameUtils.addTextToLog("=============================================\n", txtAreaResult);
			}
		}else {
			//call playgame and return
			//gameCount
			Map newMap = null;
			try {
				newMap = (Map) mapList.get(mapCount).clone();
			} catch (CloneNotSupportedException e) {
				e.printStackTrace();
			}
			System.out.println("Calling for "+mapCount+ " - "+gameCount+"\n");
			GameUtils.addTextToLog("Calling for "+mapCount+ " - "+gameCount+"\n", txtAreaConsole);
			model.startTournament(new ArrayList<Player>(playerList), newMap, numberOfTurns, numeberOfGames, ++gameCount, txtAreaConsole);
		}
	}

	// get map object by reading file

}
