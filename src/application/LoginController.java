package application;

import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Level;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.fxml.Initializable;

public class LoginController implements Initializable
{
	@FXML
    private TextField fieldEmail;
    @FXML
    private Button buttonLogin;
    @FXML
    private PasswordField fieldPassword;
    @FXML
    private Button buttonRegister;
    @FXML
    private Button buttonSkip;
    
    public User user;
    private Locale locale;
   
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
	}
	
	@FXML
    private Button buttonLang;

    @FXML
    void doLang(ActionEvent event) {
		if (locale.getDisplayLanguage()=="polski") this.locale = new Locale("en", "EN");
		else this.locale = new Locale("pl", "PL");
		try {
			Stage stage = new Stage();
			stage.setTitle("Kino");
		    FXMLLoader fxmlLoader = new FXMLLoader();
			fxmlLoader.setResources(ResourceBundle.getBundle("application.bundles.lang", locale));
	    	BorderPane dashboard = fxmlLoader.load(getClass().getResource("Login.fxml").openStream());
	    	LoginController controller = (LoginController) fxmlLoader.getController();
	    	controller.setLocaleInit(locale);
		    Scene scene = new Scene(dashboard);
		    scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		    stage.setScene(scene);
		    stage.setResizable(false);
		    stage.show();
		    ((Node)(event.getSource())).getScene().getWindow().hide();
		} catch(Exception e) {
			//Main.logger.log(Level.SEVERE, e.toString());
			Main.logger.info(e.toString());
		}
    }
	
	@FXML
    void doLogin(ActionEvent event) {
		
		if (fieldEmail.getText().trim().isEmpty() || fieldPassword.getText().trim().isEmpty()){
			Alert alertPuste = new Alert(AlertType.INFORMATION);
    		alertPuste.setTitle("B³¹d");
    		alertPuste.setHeaderText("Jedno z wymaganych pól jest puste!");
    		alertPuste.setContentText("Musisz wype³niæ wszystkie pola.");
    		alertPuste.show();
		}
		else {
			user = new User(fieldEmail.getText(), fieldPassword.getText());
			if (user.login() == 0) {
				if (user.id == 105){
					try {
						Stage stage = new Stage();
						stage.setTitle("Kino");
					    FXMLLoader fxmlLoader = new FXMLLoader();
					    fxmlLoader.setResources(ResourceBundle.getBundle("application.bundles.lang", locale));
				    	BorderPane dashboard = fxmlLoader.load(getClass().getResource("DashboardAdmin.fxml").openStream());
				    	DashboardAdminController controller = (DashboardAdminController) fxmlLoader.getController();
				    	controller.setUser(user);
				    	controller.setStage(stage);
				    	controller.setLocale(locale);
					    Scene scene = new Scene(dashboard);
					    scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
					    stage.setScene(scene);
					    stage.setResizable(false);
					    stage.show();
					    ((Node)(event.getSource())).getScene().getWindow().hide();
					} catch(Exception e) {
						Main.logger.log(Level.SEVERE, e.toString());
					}
				}
				else {
					try {
						Stage stage = new Stage();
						stage.setTitle("Kino");
					    FXMLLoader fxmlLoader = new FXMLLoader();
					    fxmlLoader.setResources(ResourceBundle.getBundle("application.bundles.lang", locale));
				    	BorderPane dashboard = fxmlLoader.load(getClass().getResource("Dashboard.fxml").openStream());
				    	DashboardController controller = (DashboardController) fxmlLoader.getController();
				    	controller.setUser(user);
				    	controller.setStage(stage);
				    	controller.setLocale(locale);
					    Scene scene = new Scene(dashboard);
					    scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
					    stage.setScene(scene);
					    stage.setResizable(false);
					    stage.show();
					    ((Node)(event.getSource())).getScene().getWindow().hide();
					} catch(Exception e) {
						Main.logger.log(Level.SEVERE, e.toString());
					}
				}
			}
			
			else {
				Alert alertPuste = new Alert(AlertType.INFORMATION);
		   		alertPuste.setTitle("B³¹d");
		   		alertPuste.setHeaderText("Logowanie siê nie powiod³o");
	    		alertPuste.setContentText("SprawdŸ poprawnoœæ danych");
	    		alertPuste.show();
			}
		}
    }
	
	@FXML
	public void registerUser(ActionEvent event){
		try {
    		Stage stage = new Stage();
			stage.setTitle("Kino");
		    FXMLLoader fxmlLoader = new FXMLLoader();
			fxmlLoader.setResources(ResourceBundle.getBundle("application.bundles.lang", locale));
	    	BorderPane dashboard = fxmlLoader.load(getClass().getResource("NewAccount.fxml").openStream());
	    	NewAccountController controller = (NewAccountController) fxmlLoader.getController();
	    	controller.setLocale(locale);
		    Scene scene = new Scene(dashboard);
		    scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		    stage.setScene(scene);
		    stage.setResizable(false);
		    stage.show();
		    ((Node)(event.getSource())).getScene().getWindow().hide();
		} catch(Exception e) {
			Main.logger.log(Level.SEVERE, e.toString());
		}
	}
	
	@FXML
    void skipLogin(ActionEvent event) {
		try {
			Stage stage = new Stage();
			stage.setTitle("Kino");
		    FXMLLoader fxmlLoader = new FXMLLoader();
		    fxmlLoader.setResources(ResourceBundle.getBundle("application.bundles.lang", locale));
	    	BorderPane dashboard = fxmlLoader.load(getClass().getResource("Dashboard.fxml").openStream());
	    	DashboardController controller = (DashboardController) fxmlLoader.getController();
	    	controller.setLocale(locale);
	    	controller.setGuest();
	    	controller.setStage(stage);
		    Scene scene = new Scene(dashboard);
		    scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		    stage.setScene(scene);
		    stage.setResizable(false);
		    stage.show();
		    ((Node)(event.getSource())).getScene().getWindow().hide();
		} catch(Exception e) {
			Main.logger.log(Level.SEVERE, e.toString());
		}
    }

	public void setLocaleInit(Locale locale) {
		this.locale = locale;
	}
	
}
