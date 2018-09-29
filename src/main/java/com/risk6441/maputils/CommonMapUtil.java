/**
 * 
 */
package com.risk6441.maputils;

import java.io.File;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import javafx.stage.FileChooser;

/**
 * This class provides common method which is used by bith UI, Map Editor and Game Play.
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
	
	public static void showAlertBox(JFrame frame, String msg) {
		JOptionPane.showMessageDialog(frame, msg);
	}
	
}