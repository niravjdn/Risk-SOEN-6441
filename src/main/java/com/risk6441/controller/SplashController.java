package com.risk6441.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;


/**
 * @author Nirav
 *
 */

public class SplashController {
	
	@FXML
	private Button btn1;

	@FXML
	private Button btn2;

	@FXML
	private Button btn3;

	@FXML
	private ImageView imageView;

	@FXML
    public void initialize() {
		
    }
	
	@FXML
	void onActionBtn1(ActionEvent event) {

	}

	@FXML
	void onActionBtn2(ActionEvent event) {

	}

    @FXML
    void onActionBtn3(ActionEvent event) {
    	  //get scene of that button and close it
    	  Stage stage = (Stage) btn3.getScene().getWindow();
    	  stage.close();
    }

}