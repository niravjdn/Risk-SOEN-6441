/**
 * 
 */
package com.risk6441.main;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UIManager;

import com.risk6441.maputils.CommonMapUtil;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import javax.swing.UIManager.*;
/**
 * @author Nirav
 *
 */
public class Main2 extends Application{
	
	
	public static void main(String[] args)
	    {
	        Application.launch(args);
	    }
	     


	/* (non-Javadoc)
	 * @see javafx.application.Application#start(javafx.stage.Stage)
	 */
	@Override
	public void start(Stage stage) throws Exception {
		// Create the FXMLLoader 
		// Path to the FXML File
		// Create the FXMLLoader

		        FXMLLoader loader = new FXMLLoader();
		        // Path to the FXML File
		        String fxmlDocPath = "src/main/resources/splashscreen.fxml";
		        FileInputStream fxmlStream = new FileInputStream(fxmlDocPath);


		        VBox root = (VBox) loader.load(fxmlStream);
		                // Create the Scene
		                Scene scene = new Scene(root);
		                // Set the Scene to the Stage
		                stage.setScene(scene);
		                // Set the Title to the Stage
		                stage.setTitle("A FXML Example with a Controller");
		                // Display the Stage
		                stage.show();


		
	}
}
