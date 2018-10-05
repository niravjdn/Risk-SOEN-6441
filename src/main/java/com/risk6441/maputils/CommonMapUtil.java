/**
 * 
 */
package com.risk6441.maputils;

import java.io.File;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import com.risk6441.models.Continent;
import com.risk6441.models.Territory;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.stage.FileChooser;

/**
 * This class provides common method which is used by UI, Map Editor and Game Play.
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
	 * This method writes the message int textarea.
	 * @param ta
	 * @param msg
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
	 * This method is used to clear the textfields/texboxes
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
	 * This method shows the alert box.
	 * @param frame
	 * @param msg
	 */
	public static void showAlertBox(JFrame frame, String msg) {
		JOptionPane.showMessageDialog(frame, msg);
	}
	
	public static TitledPane getNewPaneForVBox(Continent c) {
		VBox hbox = new VBox();
		for (Territory territory : c.getTerritories()) {
			Label label1 = new Label();
			if (territory.getPlayer() != null) {
				label1.setText(
						territory.getName() + ":-" + territory.getArmy() + "-" + territory.getPlayer().getName());
			} else {
				label1.setText(territory.getName() + ":-" + territory.getArmy());
			}
			hbox.getChildren().add(label1);
		}
		TitledPane pane = new TitledPane(c.getName(), hbox);

		return pane;
	}
	
}
