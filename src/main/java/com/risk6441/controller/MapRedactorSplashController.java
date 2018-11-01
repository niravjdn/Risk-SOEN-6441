package com.risk6441.controller;

import java.io.File;
import java.io.IOException;

import com.risk6441.entity.Map;
import com.risk6441.exception.InvalidMapException;
import com.risk6441.maputils.CommonMapUtil;
import com.risk6441.maputils.MapReader;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

/**
 * This class controls behavior of MapEditor Splash Screen.
 * @author Nirav
 *
 */
public class MapRedactorSplashController {

    @FXML
    private Button btnCreateMap;

    @FXML
    private Button btnEditMap;

    @FXML
    private Button btnExit;

    /**
     * The method gets executed when user clicks on create map button from the map splash screen.
     * It opens the map editor with blank map. The user is allowed to create new map afterwards.
     * @param event Creates button for create map.
     * @throws IOException Throws IOException if loader fails.
     */
    @FXML
    void btnCreateMap(ActionEvent event) throws IOException {
    	
    	//open scene for the map editor
    	Stage primaryStage = (Stage) btnExit.getScene().getWindow();
    	MapRedactorController controller = new MapRedactorController();
    	FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("mapeditor.fxml"));
		loader.setController(controller);

		Parent root = null;
		try {
			root = (Parent) loader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Stage stage = new Stage();
		Scene scene = new Scene(root);
		stage.setScene(scene);
    	stage.setX(primaryStage.getX() - 200);
    	stage.setY(primaryStage.getY() - 200);
		stage.show();
    }

    /**
     * The method gets executed when user clicks on edit map button from the map splash screen.
     * It opens the file chooser to allow user to choose a file. The user is allowed to edit a map afterwards. 
     * @param event  Creates button for edit map.  
     * @throws IOException Throws IOException if there is an issue while reading a map file.
     */
    @FXML
    void btnEditMap(ActionEvent event) throws IOException {
    	File file = CommonMapUtil.showFileDialog();
    	
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
    	
    	//open scene for the map editor
    	Stage primaryStage = (Stage) btnExit.getScene().getWindow();
    	MapRedactorController controller = new MapRedactorController(map,file);
    	FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("mapeditor.fxml"));
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

    /**
     * This method is executed when user clicks on exit button. It closes the application.
     * @param event  Creates button for exit.
     */
    @FXML
    void btnExit(ActionEvent event) {
    	Stage stage = (Stage) btnExit.getScene().getWindow();
    	stage.close();
    }

}
