/**
 * 
 */
package com.risk6441.maputils;

import java.io.File;
import java.util.Optional;

import com.risk6441.models.Continent;
import com.risk6441.models.Territory;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;

/**
 * This class provides a common method which is used by the UI, Map Editor and Game Play.
 * @author Nirav
 *
 */
public class CommonMapUtil {

	
	/**
	 * This method opens a dialog box to choose a map file.
	 * @return file of type object {@link File}
	 */
	public static File showFileDialog() {
		FileChooser fileChooser = new FileChooser();
		File file = null;
		FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Map files (*.map)", "*.map");
		fileChooser.getExtensionFilters().add(extFilter);
		file = fileChooser.showOpenDialog(null);
		return file;
	}
	
	/**
	 * This method is used for saving maps .
	 * @return file of type object
	 */
	public static File saveFileDialog() {
		FileChooser fileChooser = new FileChooser();
		File file = null;
		FileChooser.ExtensionFilter extensionFilter = new FileChooser.ExtensionFilter("Map files (*.map)", "*.map");
		fileChooser.getExtensionFilters().add(extensionFilter);
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
	 * This method writes a message in the text area.
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
	 * This method shows the alertbox.
	 * @param title title of the messge
	 * @param message message content
	 * @param header header of the stage
	 */
	public static void alertBox(String title, String message,  String header) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle(title);
		alert.setHeaderText(header);
		alert.setContentText(message);
		alert.showAndWait();
	}
	

	
	/**
	 * This method loads up the territories of a particular continent and shows them in a titled pane with the number of armies on the territory.
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
		TitledPane pane = new TitledPane(continent.getName(), hbox);

		return pane;
	}
	
}
