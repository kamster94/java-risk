package application;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

public class ChangeLoginController implements Initializable
{
    @FXML
    private TextField fieldNewEmail;
    @FXML
    private Button buttonZatwierdz;
    @FXML
    private Button buttonCofnij;
    @FXML
    private PasswordField fieldPassword;
    
    private User user;
    private DashboardController parent;
    
	@Override
	public void initialize(URL location, ResourceBundle resources) 
	{
		
	}
	
	@FXML
    void cofnij(ActionEvent event)
    {
		try {
		    ((Node)(event.getSource())).getScene().getWindow().hide();
		} catch(Exception e) {
			Main.logger.log(Level.SEVERE, e.toString());
		}
    }
	
	@FXML
    void zmienLogin(ActionEvent event)
    {
		/*if(fieldNewEmail.getText().trim().isEmpty() ||  fieldPassword.getText().trim().isEmpty())
		{
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("B£¥D!");
			alert.setHeaderText("Jedno z pól jest puste!");
			alert.setContentText("WprowadŸ wszystkie dane.");
			alert.show();
			resetFileds();
		}
		else
		{*/
			/*if(czyMail(fieldNewEmail.getText()) == true)
			{
				String oldlogin = user.mail;
				String newlogin = fieldNewEmail.getText();
				
				//TRZEBA WYLOGOWAÆ TEGO KTÓRY ZMIENIA LOGIN
					
				User newUser = new User(null, null);
				if(newUser.changeLogin(oldlogin, newlogin, fieldPassword.getText()) == true)
				{
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("SUKCES!");
					alert.setHeaderText("Login/Has³o zosta³o zmienione poprawnie!");
					alert.setContentText("Zaloguj siê ponownie.");
					alert.show();
					resetFileds();
				}
				else
				{
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("B£¥D!");
					alert.setHeaderText("Nie uda³o siê zmieniæ loginu/has³a!");
					alert.setContentText("Spróbuj jeszcze raz.");
					alert.show();
					resetFileds();
				}
				
			}*//*
			User dba = new User("DBA", "sql");
			int tmpid = 0;
			boolean ok = false;
			ok = dba.insertSql("UPDATE DBA.Uzytkownik SET IDUzytkownika = " + tmpid + " WHERE IDUzytkownika = " + user.id);
			ok = dba.insertSql("INSERT INTO DBA.Uzytkownik (IDUzytkownika, Email, Telefon, Miasto, Ulica, Imie, Nazwisko, DataUrodzenia) VALUES ("
					+ user.id + ", '" + fieldNewEmail.getText() + "', '" + user.telefon + "', '" + user.miasto + "', "
							+ user.ulica + "', '" + user.imie + "', " + user.nazwisko + "', '" + user.data + "'");
			ok = dba.insertSql("DELETE FROM Uzytkownik WHERE IDUzytkownika = 0");
			ok = dba.insertSql("CALL sp_dropuser('" + user.mail + "')");
			ok = dba.insertSql("CALL procedure_AddToUsers('" + fieldNewEmail.getText() + "', '" + fieldPassword.getText() + "')");
			if(ok) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("SUKCES!");
				alert.setHeaderText("Login/Has³o zosta³o zmienione poprawnie!");
				alert.setContentText("Zaloguj siê ponownie.");
				alert.show();
				resetFileds();
			}
			else {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("B£¥D!");
				alert.setHeaderText("Nie uda³o siê zmieniæ loginu/has³a!");
				alert.setContentText("Spróbuj jeszcze raz.");
				alert.show();
				resetFileds();
			}
			parent.logoutfire();
		}*/
		Alert alertPuste = new Alert(AlertType.INFORMATION);
		alertPuste.setTitle("Informacja");
		alertPuste.setHeaderText("Funkcjonalnoœæ niezaimplementowana");
		alertPuste.show();
    }
	
	
	
	public void resetFileds()
	{
		fieldNewEmail.setText(""); 
		fieldPassword.setText(""); 
	}
	
	public boolean czyMail(String mail)
	{		
		for(int i=0; i <mail.length(); i++)
		{
			if(mail.charAt(i) == '@')
			{
	             return true; 
	        } 
		}
		return false;
	}
	
	public void setUser(User user){
		this.user = user;
	}
	
	public void setParent(DashboardController parent){
		this.parent = parent;
	}
}
