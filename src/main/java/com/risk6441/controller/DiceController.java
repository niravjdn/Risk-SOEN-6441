package com.risk6441.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.apache.commons.lang3.StringUtils;
import org.omg.CORBA.COMM_FAILURE;

import com.risk6441.config.Config;
import com.risk6441.entity.Territory;
import com.risk6441.gameutils.GameUtils;
import com.risk6441.maputils.CommonMapUtil;
import com.risk6441.models.DiceModel;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

/**
 * Controller for the dice
 * @author Nirav
 *
 */
public class DiceController implements Initializable{

    @FXML
    private Label lblAttackerPlayerName;

    @FXML
    private Label lblDefenderPlayerName;

    @FXML
    private Label lblAttackerTerritoryName;

    @FXML
    private Label lblDefenderTerritoryName;

    @FXML
    private Label lblAttackerArmies;

    @FXML
    private Label lblDefenderArmies;

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
    private Label lblNoOfArmies;

    @FXML
    private TextField txtNumberOfArmiesInput;

    @FXML
    private Button btnMoveArmies;

    @FXML
    private Button btnSkipMoveArmy;

    @FXML
    private Button btnMoveAllArmies;

    @FXML
    private Label lblStatus;

    @FXML
    private Button btnContinueRoll;
    
    @FXML
    private Button btnAttackAllOutMode;
    
    private DiceModel diceModel;
    
    private static String message = "";
      
    /**
	 * Constructor for dice roll controller.
	 * @param diceModel dice model object
	 */
	public DiceController(DiceModel diceModel) {
		this.diceModel = diceModel;
	}
	/**
     * This method handles the case when user press cancel button 
     * @param event event object for the javafx 
     */
    @FXML
    void cancelDiceRoll(ActionEvent event) {
    	diceModel.cancelDiceRoll();
		GameUtils.exitWindows(btnCancelDiceRoll);
    }
    /**
     * This method handles the case when user want to continue the attack after the first loss 
     * @param event event object for the javafx 
     */
    @FXML
    void continueDiceRoll(ActionEvent event) {
    	diceModel.setAttackerDiceValues(new ArrayList<>());
		diceModel.setDefenderDiceValues(new ArrayList<>());
		loadScreen();
		loadAndShowDice();
    }

    /**
     * This method handles the case for the attack full on mode.
     * @param event event object for the javafx
     * @throws InterruptedException This produces an interrupted exception.
     */
    @FXML
    void attackAllOutMode(ActionEvent event) throws InterruptedException {
    	CommonMapUtil.hideControls(btnRoll, btnContinueRoll, btnAttackAllOutMode, btnCancelDiceRoll);
    	Runnable task = new Runnable()
		{
			public void run()
			{
				attackAllOutMode();
			}
		};

		// Run the task in a background thread
		Thread backgroundThread = new Thread(task);
		// Terminate the running thread if the application exits
		backgroundThread.setDaemon(true);
		// Start the thread
		backgroundThread.start();
    }
    /**
	 *This method handles the case for the attack full on mode.
	 */
    private void attackAllOutMode() {
		do {
    		System.out.println("Befor Click btnContinueRoll " + btnContinueRoll.isDisabled());
    		// wait with thread sleep 3 seconds to allow user to see results
    		try {
    			Platform.runLater(new Runnable() {					
					@Override
					public void run() {
						// check for the dice visibility
			    		if (!btnContinueRoll.isDisabled()) {
			    			btnContinueRoll.fire();
			    		}
			    		System.out.println("After clicking btnContinueRoll " + btnContinueRoll.isDisabled());
			    		if (chkBoxattackerDice1.isVisible()) {
			    			chkBoxattackerDice1.setSelected(true);
			    		}

			    		if (chkBoxattackerDice2.isVisible()) {
			    			chkBoxattackerDice2.setSelected(true);
			    		}

			    		if (chkBoxattackerDice3.isVisible()) {
			    			chkBoxattackerDice3.setSelected(true);
			    		}

			    		if (chkBoxdefenderDice1.isVisible()) {
			    			chkBoxdefenderDice1.setSelected(true);
			    		}

			    		if (chkBoxdefenderDice2.isVisible()) {
			    			chkBoxdefenderDice2.setSelected(true);
			    		}
			    		// click Roll Dice
			    		
						btnRoll.fire();
						lblStatus.setText(message);
			    		message = "";
					}
				});   			
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
    		
    		System.out.println("After Click roll " + btnContinueRoll.isDisabled());
    	} while (!btnContinueRoll.isDisabled());
    	btnAttackAllOutMode.setDisable(true);
	}
    /**
     * This method handles the case when user moves all the armies to its defeated adjacent territory 
     * @param event event object for the javafx 
     */
    @FXML
    void moveAllArmies(ActionEvent event) {
    	diceModel.moveAllArmies();
		GameUtils.exitWindows(btnMoveAllArmies);
    }
    /**
     * This method handles the case when user moves the armies to its defeated adjacent territory 
     * @param event event object for the javafx 
     */
    @FXML
    void moveArmies(ActionEvent event) {
    	String value = txtNumberOfArmiesInput.getText();
		if (StringUtils.isEmpty(value)) 
		{
			CommonMapUtil.alertBox("Info","Input number of armies to move.", "Error");
			return;
		}
		int armiesToMove = Integer.valueOf(value);
		diceModel.moveArmies(armiesToMove, lblStatus, btnMoveArmies);
    }
    /**
     * This method handles the case when dice is rolled
     * @param event event object for the javafx 
     */
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

		List<String> playResult = diceModel.getPlayResultAfterDiceRoll();

		Territory attackingTerritory = diceModel.getAttackingTerritory();
		Territory defendingTerritory = diceModel.getDefendingTerritory();
		if (defendingTerritory.getArmy() <= 0) {
			playResult.add(attackingTerritory.getPlayer().getName() + " won the territory: " + defendingTerritory.getName() + " From "+
			defendingTerritory.getPlayer().getName());
			diceModel.setNumberOfTerritoriesWon(diceModel.getNumOfTerritoriesWon() + 1);
			GameUtils.enablePane(moveArmiesView);

			//attacker needs to move atleast as many army as used in attack
			CommonMapUtil.disableControls(btnSkipMoveArmy);
			
			CommonMapUtil.hideControls(btnRoll, btnContinueRoll, btnCancelDiceRoll, btnAttackAllOutMode);
		} else if (attackingTerritory.getArmy() < 2) {
			playResult.add(attackingTerritory.getPlayer().getName() + " lost the match");
			CommonMapUtil.showControls(btnCancelDiceRoll);
			btnCancelDiceRoll.setDisable(false);
			CommonMapUtil.disableControls(btnRoll, btnContinueRoll, btnAttackAllOutMode);
		} else {
			CommonMapUtil.disableControls(btnRoll);
			CommonMapUtil.enableControls(btnContinueRoll);
		}
		lblDefenderArmies.setText("Armies: " + String.valueOf(defendingTerritory.getArmy()));
		lblAttackerArmies.setText("Armies: " + String.valueOf(attackingTerritory.getArmy()));
		lblStatus.setText(playResult.toString());
		message = playResult.toString();
		System.out.println(playResult.toString());
		Config.message += "\n"+playResult.toString().replaceAll(",", "\n");
		lblStatus.setVisible(true);
    }
    /**
     * This method handles the case when user does not move any armies to its defeated adjacent territory 
     * @param event event object for the javafx 
     */
    @FXML
    void skipMoveArmy(ActionEvent event) {
    	diceModel.skipMoveArmy();
		GameUtils.exitWindows(btnSkipMoveArmy);
    }

	/* (non-Javadoc)
	 * @see javafx.fxml.Initializable#initialize(java.net.URL, java.util.ResourceBundle)
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		loadScreen();
		loadAndShowDice();
	}

	/**
	 * Load attack Screen for attacker and defender.
	 */
	public void loadScreen() {
		// Load attacker details
		Territory attackingTerritory = diceModel.getAttackingTerritory();
		lblAttackerPlayerName.setText(attackingTerritory.getPlayer().getName());
		lblAttackerTerritoryName.setText("Territory: " + attackingTerritory.getName());
		lblAttackerArmies.setText("Armies: " + String.valueOf(attackingTerritory.getArmy()));

		// Load defender details
		Territory defendingTerritory = diceModel.getDefendingTerritory();
		lblDefenderPlayerName.setText(defendingTerritory.getPlayer().getName());
		lblDefenderTerritoryName.setText("Territory: " + defendingTerritory.getName());
		lblDefenderArmies.setText("Armies: " + String.valueOf(defendingTerritory.getArmy()));
		lblStatus.setText(StringUtils.EMPTY);
		// clear check boxes
		GameUtils.clearCheckBoxes(chkBoxattackerDice1, chkBoxattackerDice2, chkBoxattackerDice3, chkBoxdefenderDice1, chkBoxdefenderDice2);
		// Hide output details
		CommonMapUtil.enableControls(btnRoll);
		CommonMapUtil.disableControls(lblStatus, btnContinueRoll);
		GameUtils.disableViewPane(moveArmiesView);
	}
	
	/**
	 * Show dices according to number of armies .
	 */
	public void loadAndShowDice() {
		
		if (diceModel.getAttackingTerritory().getArmy() >= 4) {
			//if army >=4 then show all dice
			CommonMapUtil.showControls(chkBoxattackerDice1, chkBoxattackerDice2, chkBoxattackerDice3);
		} else if (diceModel.getAttackingTerritory().getArmy() >= 3) {
			//if army ==3 then show two
			CommonMapUtil.showControls(chkBoxattackerDice1, chkBoxattackerDice2);
			CommonMapUtil.hideControls(chkBoxattackerDice3);
		} else if (diceModel.getAttackingTerritory().getArmy() >= 2) {
			//else only one dice
			CommonMapUtil.showControls(chkBoxattackerDice1);
			CommonMapUtil.hideControls(chkBoxattackerDice2, chkBoxattackerDice3);
		}
		
		if (diceModel.getDefendingTerritory().getArmy() > 2) {
			//if >2 means 3 then show 3 dice
			CommonMapUtil.showControls(chkBoxdefenderDice1, chkBoxdefenderDice2);
		} else if (diceModel.getDefendingTerritory().getArmy() >= 1) {
			CommonMapUtil.showControls(chkBoxdefenderDice1);
			CommonMapUtil.hideControls(chkBoxdefenderDice2);
		}
	}

	/**
	 * Roll Dice of Attacker
	 * @param dices
	 *            check Box... dices (Varargs)
	 */
	public void rollAttackerDice(CheckBox... dices) {
		DiceModel.noOfDiceUsedByAttacker = 0;
		for (CheckBox dice : dices) {
			if (dice.isSelected()) {
				DiceModel.noOfDiceUsedByAttacker++;
				int value = diceModel.randomNumber();
				dice.setText(String.valueOf(value));
				diceModel.getAttackerDiceValues().add(value);
			}
		}
	}
	
	/**
	 * Roll dice of oponent
	 * @param dices
	 *            check Box... dices (Varargs)
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
