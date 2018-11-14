package com.risk6441.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

import com.risk6441.entity.Map;
import com.risk6441.exception.InvalidMapException;
import com.risk6441.main.Main;
import com.risk6441.maputils.CommonMapUtil;
import com.risk6441.maputils.MapReader;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;


/**
 * This class controls the behavior of Splash Screen which allows player to select :
 * <ul>
 * <li>Play Game</li>
 * <li>Map Editor</li>
 * <li>Exit</li>
 * </ul>
 * @author Nirav
 * @author Krishnan
 */
public class SplashController {

    @FXML
    private Button btnPlayGame;

    @FXML
    private Button btnMapEditor;

    @FXML
    private Button btnLoadGame;
    
    @FXML
    private Button btnExit;
    /**
     * This method handles the case when user clicks the exist button
     * @param event event object for the javafx 
     */
    @FXML
    void btnExit(ActionEvent event) {
    	//get scene of that button and close it
    	Stage stage = (Stage) btnExit.getScene().getWindow();
    	stage.close();
    	stage.setOnCloseRequest(e -> Platform.exit());
    }
    
    /**
     * This method loads a previously saved game.
     * @param event Event object for JavaFX
     */
    @FXML
    void loadGame(ActionEvent event) {
    	File file = CommonMapUtil.showFileDialogForLoadingGame();
    	PlayGameController playGameController = readGameData(file);
    	
    	FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("gameplay.fxml"));
		loader.setController(playGameController);
		
		Parent root = null;
		try {
			root = (Parent) loader.load();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Stage primaryStage = (Stage) btnExit.getScene().getWindow();

		Stage stage = new Stage();
		Scene scene = new Scene(root);
    	stage.setX(primaryStage.getX() + 200);
    	stage.setY(primaryStage.getY() + 200);
		stage.setScene(scene);
		stage.show();
    }

    
    /**
	 * @param file
	 * @return
	 */
	public PlayGameController readGameData(File file) {
		PlayGameController playGameController = null;
		try {
			FileInputStream fileInputStream = new FileInputStream(file);
			ObjectInputStream in = new ObjectInputStream(fileInputStream);
			playGameController  = (PlayGameController) in.readObject();
			in.close();
			fileInputStream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return playGameController;
	}

	/**
     * This method handles the case when user clicks the Map Edit Button.
     * @param event event object for the javafx 
     * @throws IOException Produces an IOException.
     */
    @FXML
    void btnMapEditor(ActionEvent event) throws IOException {

    	Stage primaryStage = (Stage) btnExit.getScene().getWindow();
    	
    	Pane mainPane = (Pane) FXMLLoader.load(Main.class.getResource("/mapeditorsplash.fxml"));
    	Stage stage = new Stage();
    	stage.setScene(new Scene(mainPane));
    	stage.setX(primaryStage.getX() + 200);
    	stage.setY(primaryStage.getY() + 200);
    	stage.show();

    }
    
    /**
     * This method handles the case when user clicks the Play Game button
     * @param event event event object for the javafx 
     * @throws InvalidMapException Throws invalid map exception.
     * @throws IOException Throws IOException.
     */
    @FXML
    void btnPlayGame(ActionEvent event) throws InvalidMapException, IOException {
    	File file= CommonMapUtil.showFileDialogForMap();
    	//get map object by reading file
    	MapReader mapReader = new MapReader();
    	Map map = null;
    	try {
    		map = mapReader.readMapFile(file);
    		System.out.println(map);
    	}catch (InvalidMapException e) {
    		e.printStackTrace();
    		CommonMapUtil.alertBox("Error", e.getMessage(), "Map is not valid.");
    		return;
    	}
    	Stage primaryStage = (Stage) btnExit.getScene().getWindow();
    	
    	PlayGameController controller = new PlayGameController(map);
    	
    	FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("gameplay.fxml"));
		loader.setController(controller);
		
		Parent root = null;
		try {
			root = (Parent) loader.load();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Stage stage = new Stage();
		Scene scene = new Scene(root);
		stage.setScene(scene);
    	stage.setX(primaryStage.getX() + 200);
    	stage.setY(primaryStage.getY() + 200);
		stage.show();
    	
    }

}
