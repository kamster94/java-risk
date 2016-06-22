package application;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Level;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class NewAccountController implements Initializable
{
	@FXML
    private TextField fieldNazwisko;
    @FXML
    private TextField fieldDataUr;
    @FXML
    private TextField fieldMiasto;
    @FXML
    private TextField fieldEmail;
    @FXML
    private TextField fieldUlica;
    @FXML
    private TextField fieldImie;
    @FXML
    private TextField fieldTelefon;
    @FXML
    private Button buttonZatwierdz;
    @FXML
    private Button buttonCofnij;
    @FXML
    private PasswordField fieldPassword;
    
    private Locale locale;
    
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
	}
	
	@FXML
    void cancelRegistration(ActionEvent event) {
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
			Main.logger.log(Level.SEVERE, e.toString());
		}
    }

    @FXML
    void proceedRegistration(ActionEvent event) {
    	/*
    	if(fieldImie.getText().isEmpty() || fieldNazwisko.getText().isEmpty() || fieldTelefon.getText().isEmpty()
    			|| fieldDataUr.getText().isEmpty() || fieldMiasto.getText().isEmpty() 
    			|| fieldUlica.getText().isEmpty() || fieldEmail.getText().isEmpty() || fieldPassword.getText().isEmpty())
    	{
    		Alert alertPuste = new Alert(AlertType.INFORMATION);
    		alertPuste.setTitle("B£¥D!");
    		alertPuste.setHeaderText("Jedno z wymaganych pól jest puste!");
    		alertPuste.setContentText("Musisz wype³niæ wszystkie pola.");
    		alertPuste.show();
    	}

    	if(!fieldImie.getText().isEmpty() && !fieldNazwisko.getText().isEmpty() && !fieldTelefon.getText().isEmpty() 
    			&& !fieldDataUr.getText().isEmpty() && !fieldMiasto.getText().isEmpty() && !fieldUlica.getText().isEmpty()
    			&& !fieldEmail.getText().isEmpty() && !fieldPassword.getText().isEmpty())
    	{

            if(fieldDataUr.getText().length() == 10)
            {
            	String s = fieldDataUr.getText().substring(0, 3) + fieldDataUr.getText().substring(5, 6) 
            			+ fieldDataUr.getText().substring(8, 9);
            	if(CzySameCyfry(s) == true)
            	{
            		User u = new User(null, null);
            		u.createNewAccount(fieldEmail.getText(), fieldPassword.getText(), 
            				fieldMiasto.getText(), fieldUlica.getText(), 
            				fieldDataUr.getText(), fieldImie.getText(), 
            				fieldNazwisko.getText(), fieldTelefon.getText());
            	}
            }
    	}
    	*/
    	Alert alertPuste = new Alert(AlertType.INFORMATION);
		alertPuste.setTitle("Informacja");
		alertPuste.setHeaderText("Funkcjonalnoœæ niezaimplementowana");
		alertPuste.show();
    }
	
	public boolean CzySameCyfry(String cyfry)
	{		
		for(int i=0; i <cyfry.length(); i++)
		{
			if(cyfry.charAt(i)<='0'|| cyfry.charAt(i)>='9')
			{
	             return false; 
	        } 
		}
		return true;
	}
	
	public void setLocale(Locale locale){
		this.locale = locale;
	}

}
