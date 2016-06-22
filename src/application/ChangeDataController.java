package application;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

public class ChangeDataController implements Initializable
{
	@FXML
	private TextField fieldNoweImie;
	@FXML
	private TextField fieldNoweNazwisko;
	@FXML
	private TextField fieldNowyTelefon;
	@FXML
	private TextField fieldNoweMiasto;
	@FXML
	private TextField fieldNowaUlica;
	@FXML
	private TextField fieldNowaDataUrodzenia;
	@FXML
	private Label fieldStareImie;
	@FXML
	private Label fieldStareNazwisko;
	@FXML
	private Label fieldStaryTelefon;
	@FXML
	private Label fieldStareMiasto;
	@FXML
	private Label fieldStaraUlica;
	@FXML
	private Label fieldStaraDataUrodzenia;
	@FXML
    private Button buttonCofnij;
    @FXML
    private Button buttonZatwierdz;
    
    private User user;
    private DashboardController parent;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
	}
	
	@FXML
    void cofnij(ActionEvent event)
    {
		try {
		    ((Node)(event.getSource())).getScene().getWindow().hide();
		} 
		catch(Exception e) {
			Main.logger.log(Level.SEVERE, e.toString());
		}
    }
	
	@FXML
    void zmienLogin(ActionEvent event)
    {
		if(fieldNoweImie.getText().isEmpty() &&  fieldNoweNazwisko.getText().isEmpty() && fieldNowyTelefon.getText().isEmpty()
				&& fieldNoweMiasto.getText().isEmpty() && fieldNowaUlica.getText().isEmpty() && fieldNowaDataUrodzenia.getText().isEmpty())
		{
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("B£¥D!");
			alert.setHeaderText("Wszystkie pola s¹ puste");
			alert.setContentText("Wype³nij chocia¿ jedno pole lub wciœnij przycisk Cofnij, aby wyjœæ z edycji danych u¿ytkownika.");
			alert.show();
			resetFileds();
		}
		else
		{
			User u = user;
			String ndata = "";
			if(fieldNowaDataUrodzenia.getText().isEmpty())
			{
				ndata = u.data;
			}
			else
			{
				ndata = fieldNowaDataUrodzenia.getText();
			}
			
			if(ndata.length() == 10)
            {
            	switch(czyDobraData(ndata))
            	{
            	case 1:
        			String nimie = "";
        			String nnazwisko = "";
        			String ntelefon = "";
        			String nmiasto = "";
        			String nulica = "";    			   			
        			
        			if(fieldNoweImie.getText().isEmpty())
        			{
        				nimie = u.imie;
        			}
        			else
        			{
        				nimie = fieldNoweImie.getText();
        			}
        				
        			if(fieldNoweNazwisko.getText().isEmpty())
        			{
        				nnazwisko = u.nazwisko;
        			}
        			else
        			{
        				nnazwisko = fieldNoweNazwisko.getText();
        			}
        			
        			if(fieldNowyTelefon.getText().isEmpty())
        			{
        				ntelefon = u.telefon;
        			}
        			else
        			{
        				ntelefon = fieldNowyTelefon.getText();
        			}
        			
        			if(fieldNoweMiasto.getText().isEmpty())
        			{
        				nmiasto = u.miasto;
        			}
        			else
        			{
        				nmiasto = fieldNoweMiasto.getText();
        			}
        			
        			if(fieldNowaUlica.getText().isEmpty())
        			{
        				nulica = u.ulica;
        			}
        			else
        			{
        				nulica = fieldNowaUlica.getText();
        			}
        				
        			
            		if(u.changeData(user.mail, ntelefon, nmiasto, nulica, nimie, nnazwisko, ndata)== true)
					{
    					Alert alert = new Alert(AlertType.INFORMATION);
    					alert.setTitle("SUKCES!");
    					alert.setHeaderText("Dane zosta³y zmienione poprawnie!");
    					alert.setContentText("Nowe dane bêd¹ ju¿ widoczne.");
    					alert.show();
    					resetFileds();
    					parent.setUser(this.user);
    					try {
    					    ((Node)(event.getSource())).getScene().getWindow().hide();
    					} catch(Exception e) {
    						Main.logger.log(Level.SEVERE, e.toString());
    					}
					}
            		else
            		{
            			Alert alert = new Alert(AlertType.INFORMATION);
    					alert.setTitle("B£¥D!");
    					alert.setHeaderText("Dane nie zosta³y zmienione!");
    					alert.setContentText("Spróbuj jeszcze raz.");
    					alert.show();
    					resetFileds();
            		}
        			break;
            		
            	case 2:
            		Alert alert = new Alert(AlertType.INFORMATION);
            		alert.setTitle("B£¥D!");
            		alert.setHeaderText("Wprowadzona data nie zawiera samych cyfr!");
            		alert.setContentText("WprowadŸ datê w poprawnym formacie.");
            		alert.show();
            		resetFileds();
            		break;
            		
            	case 3:
            		Alert alert1 = new Alert(AlertType.INFORMATION);
            		alert1.setTitle("B£¥D!");
            		alert1.setHeaderText("Rok urodzenia jest za ma³y (mniejszy od 1900)!");
            		alert1.setContentText("WprowadŸ datê w poprawnym formacie.");
            		alert1.show();
            		resetFileds();
            		break;
            		
            	case 4:
            		Alert alert2 = new Alert(AlertType.INFORMATION);
            		alert2.setTitle("B£¥D!");
            		alert2.setHeaderText("Wprowadzony miesi¹c nie jest odpowiedni!");
            		alert2.setContentText("WprowadŸ datê w poprawnym formacie.");
            		alert2.show();
            		resetFileds();
            		break;
            		
            	case 5:
            		Alert alert3 = new Alert(AlertType.INFORMATION);
            		alert3.setTitle("B£¥D!");
            		alert3.setHeaderText("Wprowadzony dzieñ nie jest odpowiedni!");
            		alert3.setContentText("WprowadŸ datê w poprawnym formacie.");
            		alert3.show();
            		resetFileds();
            		break;
            		
            	case 6:
            		Alert alert4 = new Alert(AlertType.INFORMATION);
            		alert4.setTitle("B£¥D!");
            		alert4.setHeaderText("Podano z³y dzieñ dla lutego w roku przestêpnym!");
            		alert4.setContentText("WprowadŸ datê w poprawnym formacie.");
            		alert4.show();
            		resetFileds();
            		break;
            		
            	case 7:
            		Alert alert5 = new Alert(AlertType.INFORMATION);
            		alert5.setTitle("B£¥D!");
            		alert5.setHeaderText("Wprowadzona data nie zawiera myœlników!");
            		alert5.setContentText("WprowadŸ datê w poprawnym formacie.");
            		alert5.show();
            		resetFileds();
            		break;
            	}            	
            }
		}
    }
	
	public void resetFileds()
	{
		fieldNoweImie.setText(""); 
		fieldNoweNazwisko.setText(""); 
		fieldNowyTelefon.setText(""); 
		fieldNowaDataUrodzenia.setText(""); 
		fieldNoweMiasto.setText("");
		fieldNowaUlica.setText(""); 
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
	
	public int czyDobraData(String cyfry)
	{	
		String subrstr = cyfry.substring(0,4) + cyfry.substring(5,7) + cyfry.substring(8,10);
		for(int i=0; i<subrstr.length(); i++)
		{
			int liczba = Integer.parseInt("" + subrstr.charAt(i));
			if(liczba<0|| liczba>9)
			{
	             return 2; //jeœli nie zawiera samych cyfr
	        }	
		}
		if((cyfry.charAt(4)== '-') && (cyfry.charAt(7)== '-'))
		{
			int rok = Integer.parseInt(cyfry.substring(0,4));
			if(rok < 1900)
			{
				return 3; //jeœli rok ur jest za ma³y
			}
			int miesiac = Integer.parseInt(cyfry.substring(5,7));
			if(miesiac < 1 || miesiac > 12)
			{
				return 4; //jeœli miesi¹c nie jest dobry
			}
			int dzien = Integer.parseInt(cyfry.substring(8,10));
			if(dzien < 1 || dzien > 31)
			{
				return 5; //jeœli dzien nie jest dobry
			}
			else
			{
				int przestepny = miesiac % 4;
				if(przestepny == 0)
				{
					if(miesiac == 2)
					{
						if(dzien < 1 || dzien > 29)
						{
							return 6; //podano z³y dzieñ dla lutego przestêpnego
						}
					}
				}
			}
			return 1; //OK
		}
		else
		{
			return 7; // nie zawiera myœlników
		}
	}
	
	public void setUser(User user){
		this.user = user;
		fieldStareImie.setText(user.imie); 
		fieldStareNazwisko.setText(user.nazwisko); 
		fieldStaryTelefon.setText(user.telefon); 
		fieldStaraDataUrodzenia.setText(user.data); 
		fieldStareMiasto.setText(user.miasto);
		fieldStaraUlica.setText(user.ulica);
	}
	
	public void setParent(DashboardController parent){
		this.parent = parent;
	}
	
}
