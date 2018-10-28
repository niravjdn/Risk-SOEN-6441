package com.risk6441.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.apache.commons.lang3.StringUtils;

import com.risk6441.config.Config;
import com.risk6441.entity.Territory;
import com.risk6441.gameutils.GameUtils;
import com.risk6441.maputils.CommonMapUtil;
import com.risk6441.models.DiceModel;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

public class DiceController implements Initializable{

    @FXML
    private Label attackerPlayerName;

    @FXML
    private Label defenderPlayerName;

    @FXML
    private Label attackerTerritoryName;

    @FXML
    private Label defenderTerritoryName;

    @FXML
    private Label attackerArmies;

    @FXML
    private Label defenderArmies;

    @FXML
    private Button btnRoll;

    @FXML
    private CheckBox chkBoxattackerDice1;

    @FXML
    private CheckBox chkBoxattackerDice2;

    @FXML
    private CheckBox chkBoxattackerDice3;

    @FXML
    private CheckBox chkBoxdefenderDice1;

    @FXML
    private CheckBox chkBoxdefenderDice2;

    @FXML
    private Button btnCancelDiceRoll;

    @FXML
    private Pane moveArmiesView;

    @FXML
    private Label noOfArmies;

    @FXML
    private TextField numberOfArmiesInput;

    @FXML
    private Button btnMoveArmies;

    @FXML
    private Button btnSkipMoveArmy;

    @FXML
    private Button btnMoveAllArmies;

    @FXML
    private Label winnerName;

    @FXML
    private Button btnContinueRoll;
    
    @FXML
    private Button btnAttackFullOnMode;
    
    private DiceModel diceModel;
    
  
    
    /**
	 * Constructor for dice roll controller.
	 * @param diceModel dice model object
	 */
	public DiceController(DiceModel diceModel) {
		this.diceModel = diceModel;
	}

    @FXML
    void cancelDiceRoll(ActionEvent event) {
    	diceModel.cancelDiceRoll();
		GameUtils.closeScreen(btnCancelDiceRoll);
    }

    @FXML
    void continueDiceRoll(ActionEvent event) {
    	diceModel.setAttackerDiceValues(new ArrayList<>());
		diceModel.setDefenderDiceValues(new ArrayList<>());
		loadAttackScreen();
		showDice();
    }

    /**
     * This method handles the case for the attack full on mode.
     * @param event event object for the javafx
     * @throws InterruptedException 
     */
    @FXML
    void attackFullOnMode(ActionEvent event) throws InterruptedException {
    	
    	do {
    		//check for the dice visibility
    		if(chkBoxattackerDice1.isVisible()) {
        		chkBoxattackerDice1.setSelected(true);
        	}
        	
        	if(chkBoxattackerDice2.isVisible()) {
        		chkBoxattackerDice2.setSelected(true);
        	}
        	
        	if(chkBoxattackerDice3.isVisible()) {
        		chkBoxattackerDice3.setSelected(true);
        	}
        	
        	if(chkBoxdefenderDice1.isVisible()) {
        		chkBoxdefenderDice1.setSelected(true);
        	}
        	
        	if(chkBoxdefenderDice2.isVisible()) {
        		chkBoxdefenderDice2.setSelected(true);
        	}
        	
        	//clcik Roll Dice    	
        	btnRoll.fire();

        	//wait with thread sleep 3 seconds to allow user to see results
        	Thread.sleep(3000);
        	btnContinueRoll.fire();
    	}while(!btnContinueRoll.isDisabled());
    	btnAttackFullOnMode.setDisable(true);
    }
    
    @FXML
    void moveAllArmies(ActionEvent event) {
    	diceModel.moveAllArmies();
		GameUtils.closeScreen(btnMoveAllArmies);
    }

    @FXML
    void moveArmies(ActionEvent event) {
    	String value = numberOfArmiesInput.getText();
		if (StringUtils.isEmpty(value)) {
			CommonMapUtil.alertBox("Info","Please enter a number of armies to move", "Error");
			return;
		}
		int armiesToMove = Integer.valueOf(value);
		diceModel.moveArmies(armiesToMove, winnerName, btnMoveArmies);
    }

    @FXML
    void rollDice(ActionEvent event) {
    	if (!chkBoxattackerDice1.isSelected() && !chkBoxattackerDice2.isSelected() && !chkBoxattackerDice3.isSelected()) {
    		CommonMapUtil.alertBox("Info","Please Select atleast one of the attacker dice", "Message");
			return;
		} else if (!chkBoxdefenderDice1.isSelected() && !chkBoxdefenderDice2.isSelected()) {
			CommonMapUtil.alertBox("Info","Please Select atleast one of the defender dice", "Message");
			return;
		}
		rollAttackerDice(chkBoxattackerDice1, chkBoxattackerDice2, chkBoxattackerDice3);
		rollDefenderDice(chkBoxdefenderDice1, chkBoxdefenderDice2);

		List<String> playResult = diceModel.getPlayResultAfterDiceThrown();

		Territory attackingTerritory = diceModel.getAttackingTerritory();
		Territory defendingTerritory = diceModel.getDefendingTerritory();
		if (defendingTerritory.getArmy() <= 0) {
			playResult.add(
					attackingTerritory.getPlayer().getName() + " won the territory: " + defendingTerritory.getName() + " From "+
			defendingTerritory.getPlayer().getName());
			diceModel.setNumOfTerritoriesWon(diceModel.getNumOfTerritoriesWon() + 1);
			GameUtils.enableViewPane(moveArmiesView);
			CommonMapUtil.hideControls(btnRoll, btnContinueRoll, btnCancelDiceRoll);
		} else if (attackingTerritory.getArmy() < 2) {
			playResult.add(attackingTerritory.getPlayer().getName() + " lost the match");
			CommonMapUtil.disableControls(btnRoll, btnContinueRoll);
		} else {
			CommonMapUtil.disableControls(btnRoll);
			CommonMapUtil.enableControls(btnContinueRoll);
		}
		defenderArmies.setText("Armies: " + String.valueOf(defendingTerritory.getArmy()));
		attackerArmies.setText("Armies: " + String.valueOf(attackingTerritory.getArmy()));
		winnerName.setText(playResult.toString());
		System.out.println(playResult.toString());
		Config.message = "\n"+playResult.toString().replaceAll(",", "\n");
		winnerName.setVisible(true);
    }

    @FXML
    void skipMoveArmy(ActionEvent event) {
    	diceModel.skipMoveArmy();
		GameUtils.closeScreen(btnSkipMoveArmy);
    }

	/* (non-Javadoc)
	 * @see javafx.fxml.Initializable#initialize(java.net.URL, java.util.ResourceBundle)
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		loadAttackScreen();
		showDice();
	}

	/**
	 * Load attack Screen for attacker and defender.
	 */
	public void loadAttackScreen() {
		// Load attacker details
		Territory attackingTerritory = diceModel.getAttackingTerritory();
		attackerPlayerName.setText(attackingTerritory.getPlayer().getName());
		attackerTerritoryName.setText("Territory: " + attackingTerritory.getName());
		attackerArmies.setText("Armies: " + String.valueOf(attackingTerritory.getArmy()));

		// Load defender details
		Territory defendingTerritory = diceModel.getDefendingTerritory();
		defenderPlayerName.setText(defendingTerritory.getPlayer().getName());
		defenderTerritoryName.setText("Territory: " + defendingTerritory.getName());
		defenderArmies.setText("Armies: " + String.valueOf(defendingTerritory.getArmy()));
		winnerName.setText(StringUtils.EMPTY);
		// clear check boxes
		GameUtils.clearCheckBoxes(chkBoxattackerDice1, chkBoxattackerDice2, chkBoxattackerDice3, chkBoxdefenderDice1, chkBoxdefenderDice2);
		// Hide output details
		CommonMapUtil.enableControls(btnRoll);
		CommonMapUtil.disableControls(winnerName, btnContinueRoll);
		GameUtils.disableViewPane(moveArmiesView);
	}
	
	/**
	 * Show dices according to number of armies .
	 */
	public void showDice() {
		if (diceModel.getAttackingTerritory().getArmy() >= 4) {
			CommonMapUtil.showControls(chkBoxattackerDice1, chkBoxattackerDice2, chkBoxattackerDice3);
		} else if (diceModel.getAttackingTerritory().getArmy() >= 3) {
			CommonMapUtil.showControls(chkBoxattackerDice1, chkBoxattackerDice2);
			CommonMapUtil.hideControls(chkBoxattackerDice3);
		} else if (diceModel.getAttackingTerritory().getArmy() >= 2) {
			CommonMapUtil.showControls(chkBoxattackerDice1);
			CommonMapUtil.hideControls(chkBoxattackerDice2, chkBoxattackerDice3);
		}
		if (diceModel.getDefendingTerritory().getArmy() > 2) {
			CommonMapUtil.showControls(chkBoxdefenderDice1, chkBoxdefenderDice2);
		} else if (diceModel.getDefendingTerritory().getArmy() >= 1) {
			CommonMapUtil.showControls(chkBoxdefenderDice1);
			CommonMapUtil.hideControls(chkBoxdefenderDice2);
		}
	}

	/**
	 * Roll Attacker Dice
	 * 
	 * @param dices
	 *            checkBox... dices
	 */
	public void rollAttackerDice(CheckBox... dices) {
		for (CheckBox dice : dices) {
			if (dice.isSelected()) {
				int value = diceModel.randomNumber();
				dice.setText(String.valueOf(value));
				diceModel.getAttackerDiceValues().add(value);
			}
		}
	}
	
	/**
	 * Roll Defender Dice
	 * 
	 * @param dices
	 *            checkBox... dices
	 */
	public void rollDefenderDice(CheckBox... dices) {
		for (CheckBox dice : dices) {
			if (dice.isSelected()) {
				int value = diceModel.randomNumber();
				dice.setText(String.valueOf(value));
				diceModel.getDefenderDiceValues().add(value);
			}
		}
	}
	
	
}
