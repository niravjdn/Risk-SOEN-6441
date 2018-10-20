package com.risk6441.main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
/**
 * This is the main entrance point of the program which allows user to execute the game.
 * @author Nirav
 *
 */
public class Main extends Application{
	
	
	/**
	 * The main entry point of our application.
	 * Starts the main to launch the game.
	 * @param args the command line arguments; not used here.
	 */
	public static void main(String[] args)
	    {
	        Application.launch(args);
	        //call application to launch UI .
	    }

	/* (non-Javadoc)
	 * @see javafx.application.Application#start(javafx.stage.Stage)
	 * Loads the first Launcher Splash screen.
	 */
	@Override
	public void start(Stage stage) throws Exception {
		
		Pane mainPane = (Pane) FXMLLoader.load(Main.class.getResource("/splashscreen.fxml"));
		stage.setScene(new Scene(mainPane));
		stage.show();
		
	}
}
