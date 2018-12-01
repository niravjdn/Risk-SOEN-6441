/**
 * 
 */
package com.risk6441.maputils;

import java.io.File;
import java.util.Optional;
import java.util.Random;

import com.risk6441.config.Config;
import com.risk6441.entity.Continent;
import com.risk6441.entity.Territory;
import com.risk6441.gameutils.GameUtils;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;

/**
 * This class provides common method which is used by UI, Map Editor and Game Play.
 * @author Nirav
 *
 */
public class CommonMapUtil {

	public static Button btnSave = null;
	
	
	/**
	 * This method gets 0 to n.
	 * @param n no till which you want to find  random no, 0 to n
	 * @return random no from 0 to , including n
	 */
	public static int getRandomNo(int n) {
		return new Random().nextInt(n+1);
	}
	
	/**
	 * This method returns a number from 1 to n.
	 * @param n no till which you want to find  random no
	 * @return random no from 1 to n
	 */
	public static int getRandomNoFromOne(int n) {
		return new Random().nextInt(n) + 1;
	}		

	
	/**
	 * This method opens a dialog box to choose a map file.
	 * @return file of type object {@link File}
	 */
	public static File showFileDialogForMap() {
		FileChooser fileChooser = new FileChooser();
		File file = null;
		FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Map files (*.map)", "*.map");
		fileChooser.getExtensionFilters().add(extFilter);
		file = fileChooser.showOpenDialog(null);
		return file;
	}
	
	/**
	 * This method opens a dialog box to choose a map file.
	 * @return file of type object {@link File}
	 */
	public static File showFileDialogForLoadingGame() {
		FileChooser fileChooser = new FileChooser();
		File file = null;
		FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Map files (*.game)", "*.game");
		fileChooser.getExtensionFilters().add(extFilter);
		file = fileChooser.showOpenDialog(null);
		return file;
	}
	
	
	
	/**
	 * This method is used for saving the maps.
	 * @return file of type object
	 */
	public static File saveFileDialogForMap() {
		FileChooser fileChooser = new FileChooser();
		File file = null;
		FileChooser.ExtensionFilter extensionFilter = new FileChooser.ExtensionFilter("Map files (*.map)", "*.map");
		fileChooser.getExtensionFilters().add(extensionFilter);
		file = fileChooser.showSaveDialog(null);
		return file;
	}
	
	
	/**
	 * This method is used for saving the game.
	 * @return file of type object
	 */
	public static File saveFileDialogForGame() {
		FileChooser fileChooser = new FileChooser();
		File file = null;
		FileChooser.ExtensionFilter extensionFilter = new FileChooser.ExtensionFilter("Map files (*.game)", "*.game");
		fileChooser.getExtensionFilters().add(extensionFilter);
		fileChooser.setInitialFileName("savedGame");
		file = fileChooser.showSaveDialog(null);
		return file;
	}
	
	
	/**
	 * This method opens a dialog box to allow user to input a number for purpose of fortification.
	 * @return
	 * 		  integer number for the armies for fortification
	 */
	public static int inputDialogueBoxForFortification() {
		TextInputDialog dialog = new TextInputDialog();
		dialog.setTitle("Input a number");
		dialog.setHeaderText("Enter number of armies to fortify (1 less then current armies of the territory");
		Optional<String> result = dialog.showAndWait();
		if (result.isPresent())
			return Integer.parseInt(result.get());
		else
			return 0;
	}
	
	/**
	 * This method opens a dialog box to allow user to input a number for purpose of reinforcement.
	 * @return
	 * 		  integer number for the armies for fortification
	 */
	public static int inputDialogueBoxForRenforcement() {
		TextInputDialog dialog = new TextInputDialog();
		dialog.setTitle("Input a number");
		dialog.setHeaderText("Enter number of armies to reinforce on selected territory.");
		Optional<String> result = dialog.showAndWait();
		if (result.isPresent())
			return Integer.parseInt(result.get());
		else
			return 0;
	}
	
	
	/**
	 * This method writes the message in the text area.
	 * @param ta This is the text area object.
	 * @param msg This is the message to be set.
	 */
	public static void putMessgae(TextArea ta, String msg) {
		ta.setText(msg);
	}
	
	/**
	 * This method is used to enable controls.
	 * @param controls
	 * 			controls from the scene
	 */
	public static void enableControls(Control... controls) {
		for(Control c : controls) {
			c.setDisable(false);
		}
	}
	
	/**
	 * This method is used disable controls.
	 * @param controls
	 * 			controls from the scene
	 */
	public static void disableControls(Control... controls) {
		for(Control c : controls) {
			c.setDisable(true);
		}
	}
	
	
	/**
	 * This method is used to clear the textfields/textboxes
	 * @param fields
	 * 			textfields 
	 */
	public static void clearTextBox(TextField... fields) {
		for (TextField field : fields) {
			field.clear();
		}
	}
	
	/**
	 * This method is used to show control.
	 * 
	 * @param controls
	 *            controls
	 */
	public static void showControls(Control... controls) {
		for (Control control : controls) {
			control.setVisible(true);
		}
	}
	
	/**
	 * This method is used to hide control.
	 * 
	 * @param controls
	 *            controls
	 */
	public static void hideControls(Control... controls) {
		for (Control control : controls) {
			control.setVisible(false);
		}
	}
	
	/**
	 * This method shows the alertbox.
	 * @param title title of the messge
	 * @param message message content
	 * @param header header of the stage
	 */
	public static void alertBox(String title, String message,  String header) {
		if(GameUtils.isTestMode) {
			return;
		}
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle(title);
		alert.setHeaderText(header);
		alert.setContentText(message);
		if(!Config.isAllComputerPlayer)
			alert.showAndWait();
		else if(Config.isPopUpShownInAutoMode)
			alert.show();
		
	}
	

	
	/**
	 * This method loads up the territories the of particular continent and shows them in titled pane with number of armies on the territory.
	 * @param continent object of the continent
	 * @return the titled pane for the user interface of the game play.
	 */
	public static TitledPane getNewPaneForVBox(Continent continent) {
		VBox hbox = new VBox();
		for (Territory territory : continent.getTerritories()) {
			Label label1 = new Label();
			if (territory.getPlayer() != null) {
				label1.setText(
						territory.getName() + ":-" + territory.getArmy() + "-" + territory.getPlayer().getName());
			} else {
				label1.setText(territory.getName() + ":-" + territory.getArmy());
			}
			hbox.getChildren().add(label1);
		}
		TitledPane pane = new TitledPane(continent.getName()+" - "+continent.getValue(), hbox);

		return pane;
	}

	/**
	 * @param b true for enabling the button; false for disabling button
	 */
	public static void enableOrDisableSave(boolean b) {
		if(!Config.isAllComputerPlayer)
			btnSave.setDisable(!b);
	}
	
}
