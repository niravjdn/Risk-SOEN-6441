package com.risk6441.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
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
    void btnMapEditor(ActionEvent event) {

    }

    @FXML
    void btnPlayGame(ActionEvent event) {

    }

}
