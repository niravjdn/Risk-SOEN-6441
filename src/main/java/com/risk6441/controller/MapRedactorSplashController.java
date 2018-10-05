package com.risk6441.controller;

import java.io.File;
import java.io.IOException;

import com.risk6441.exception.InvalidMapException;
import com.risk6441.main.Main;
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Stage stage = new Stage();
		Scene scene = new Scene(root);
		stage.setScene(scene);
    	stage.setX(primaryStage.getX() - 200);
    	stage.setY(primaryStage.getY() - 200);
		stage.show();
    }

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

    @FXML
    void btnExit(ActionEvent event) {
    	Stage stage = (Stage) btnExit.getScene().getWindow();
    	stage.close();
    }

}
