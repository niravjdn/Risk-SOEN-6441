package com.risk6441.controller;

import java.io.File;
import java.io.IOException;

import com.risk6441.exception.InvalidMapException;
import com.risk6441.main.Main;
import com.risk6441.main.MapEditor;
import com.risk6441.maputils.CommonMapUtil;
import com.risk6441.maputils.MapReader;
import com.risk6441.models.Map;

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
 *
 */
public class SplashController {

    @FXML
    private Button btnPlayGame;

    @FXML
    private Button btnMapEditor;

    @FXML
    private Button btnExit;

    @FXML
    void btnExit(ActionEvent event) {
    	//get scene of that button and close it
    	Stage stage = (Stage) btnExit.getScene().getWindow();
    	stage.close();
    }

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

    @FXML
    void btnPlayGame(ActionEvent event) throws InvalidMapException, IOException {
    	File file= CommonMapUtil.showFileDialog();
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
