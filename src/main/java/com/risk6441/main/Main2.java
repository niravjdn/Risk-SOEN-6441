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
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import javax.swing.UIManager.*;
/**
 * This is main entrance point of the program which allows the user to execute the game.
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
		
		Pane mainPane = (Pane) FXMLLoader.load(Main.class.getResource("/splashscreen.fxml"));
		stage.setScene(new Scene(mainPane));
		stage.show();
		
	}
}
