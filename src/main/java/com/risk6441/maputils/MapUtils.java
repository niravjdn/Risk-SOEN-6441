/**
 * 
 */
package com.risk6441.maputils;

import java.io.File;

import javafx.stage.FileChooser;

/**
 * @author Nirav
 *
 */
public class MapUtils {

	
	/**
	 * This method is used to open up a dialog box to choose a map file.
	 * 
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
	
}
