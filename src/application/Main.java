package application;
	
import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.fxml.FXMLLoader;

public class Main extends Application {
	
	public static Logger logger = Logger.getLogger("KinoLog");  
	private Locale locale;

	@Override
	public void start(Stage primaryStage) {
		try {
			locale = new Locale("pl", "PL");
			FXMLLoader fxmlLoader = new FXMLLoader();
			fxmlLoader.setResources(ResourceBundle.getBundle("application.bundles.lang", locale));
			BorderPane root = fxmlLoader.load(getClass().getResource("Login.fxml").openStream());
			LoginController controller = (LoginController) fxmlLoader.getController();
			controller.setLocaleInit(locale);
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setTitle("Kino");
			primaryStage.setScene(scene);
			primaryStage.setResizable(false);
			primaryStage.show();
		} catch(Exception e) {
			logger.log(Level.SEVERE, e.toString());
		}
	}
	
	public static void main(String[] args) {
		try {  
			FileHandler fh;  
	        fh = new FileHandler("KinoLog.log");  
	        logger.addHandler(fh);
	        logger.setUseParentHandlers(false);
	        SimpleFormatter formatter = new SimpleFormatter();  
	        fh.setFormatter(formatter);  
	    } catch (SecurityException e) {  
	        e.printStackTrace();  
	    } catch (IOException e) {  
	        e.printStackTrace();  
	    } 
		launch(args);
	}
}
