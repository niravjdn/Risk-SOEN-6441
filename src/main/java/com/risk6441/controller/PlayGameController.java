package com.risk6441.controller;

import com.risk6441.models.Territory;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;

/**
 * @author Nirav
 *
 */
public class PlayGameController {

	/**
	 * The @map
	 */
    @FXML
    private ChoiceBox<Integer> choiceBoxNoOfPlayer;

    /**
	 * The @map
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
    private ListView<Territory> terrList;

    @FXML
    private ListView<Territory> adjTerrList;

    @FXML
    private TextArea txtAreaMsg;

    @FXML
    private VBox vbox;

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

}
