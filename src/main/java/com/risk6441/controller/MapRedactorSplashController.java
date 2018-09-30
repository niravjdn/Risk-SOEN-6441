package com.risk6441.controller;

import java.io.File;
import java.io.IOException;

import com.risk6441.main.Main;
import com.risk6441.maputils.CommonMapUtil;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
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
    	FXMLLoader loader = new FXMLLoader();
    	Pane mainPane = (Pane) loader.load(Main.class.getResource("/mapeditor.fxml"));
    	loader.setController(new MapRedactorController());
    	
    	Stage stage = new Stage();
    	stage.setScene(new Scene(mainPane));
    	stage.setX(primaryStage.getX() + 200);
    	stage.setY(primaryStage.getY() + 200);
    	stage.show();
    }

    @FXML
    void btnEditMap(ActionEvent event) throws IOException {
    	File file = CommonMapUtil.showFileDialog();
    	
    	//open scene for the map editor
    	Stage primaryStage = (Stage) btnExit.getScene().getWindow();
    	Pane mainPane = (Pane) FXMLLoader.load(Main.class.getResource("/mapeditor.fxml"));
    	Stage stage = new Stage();
    	stage.setScene(new Scene(mainPane));
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
