/**
 * 
 */
package com.risk6441.maputils;

import java.io.File;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Control;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
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
		FileChooser.ExtensionFilter extensionFilter = new FileChooser.ExtensionFilter("Map files (*.map)", "*.map");
		fileChooser.getExtensionFilters().add(extensionFilter);
		file = fileChooser.showOpenDialog(null);
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
	
	
	public static void alertBox(String title, String message,  String header) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle(title);
		alert.setHeaderText(header);
		alert.setContentText(message);
		alert.showAndWait();
	}
	
	public static void showAlertBox(JFrame frame, String msg) {
		JOptionPane.showMessageDialog(frame, msg);
	}
	
}
