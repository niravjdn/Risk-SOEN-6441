package com.risk6441.controller;

import java.net.URL;
import java.util.ResourceBundle;

import com.risk6441.maputils.CommonMapUtil;
import com.risk6441.models.Territory;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;

/**
 * This class ....
 * @author Nirav
 *
 */
public class PlayGameController implements Initializable{

    /**
     * The @btnReinforcement
     */
    @FXML
    private Button btnReinforcement;

    @FXML
    private Button btnPlaceArmy;

    @FXML
    private Button btnAttack;

    @FXML
    private Button btnFortify;

    @FXML
    private Button btnEndTurn;

    @FXML
    private ListView<?> terrList;

    @FXML
    private ListView<?> adjTerrList;

    @FXML
    private TextArea txtAreaMsg;

    @FXML
    private VBox vbox;

    @FXML
    private ChoiceBox<?> choiceBoxNoOfPlayer;

    @FXML
    void attack(ActionEvent event) {
    	
    }

    @FXML
    void endTrun(ActionEvent event) {

    }

    @FXML
    void fortify(ActionEvent event) {

    }

    @FXML
    void placeArmy(ActionEvent event) {

    }

    @FXML
    void reinforce(ActionEvent event) {
    		
    }

	/* (non-Javadoc)
	 * @see javafx.fxml.Initializable#initialize(java.net.URL, java.util.ResourceBundle)
	 */
	public void initialize(URL location, ResourceBundle resources) {
		
		CommonMapUtil.disableControls(btnAttack, btnEndTurn);
	}

}
