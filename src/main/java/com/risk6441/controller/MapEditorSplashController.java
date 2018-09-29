package com.risk6441.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

/**
 * @author Nirav
 *
 */
public class MapEditorSplashController {

    @FXML
    private Button btnCreateMap;

    @FXML
    private Button btnEditMap;

    @FXML
    private Button btnExit;

    @FXML
    void btnCreateMap(ActionEvent event) {

    }

    @FXML
    void btnEditMap(ActionEvent event) {

    }

    @FXML
    void btnExit(ActionEvent event) {
    	Stage stage = (Stage) btnExit.getScene().getWindow();
    	stage.close();
    }

}
