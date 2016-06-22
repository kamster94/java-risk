package application;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.text.Text;

public class UserInfoController {

    @FXML
    private Text textName;

    @FXML
    private Text textDate;

    @FXML
    private Text textEmail;

    @FXML
    private Text textPhone;

    @FXML
    private Text textAdress;

    @FXML
    private Text textCity;

    @FXML
    private Button buttonClose;
    
    

    @FXML
    void doClose(ActionEvent event) {
    	try {
		    ((Node)(event.getSource())).getScene().getWindow().hide();
		} catch(Exception e) {
			Main.logger.log(Level.SEVERE, e.toString());
		}
    }
    
    public void setDetails(User user, int id){
    	List<String> columns = new ArrayList<String>();
    	columns.add("Imie");
    	columns.add("Nazwisko");
    	columns.add("DataUrodzenia");
    	columns.add("Email");
    	columns.add("Telefon");
    	columns.add("Ulica");
    	columns.add("Miasto");
    	List<String> params = new ArrayList<String>(user.getFields("SELECT * FROM DBA.Uzytkownik WHERE IDUzytkownika = " + id, columns));
    	textName.setText(params.get(0) + " " + params.get(1));
    	textDate.setText(params.get(2));
    	textEmail.setText(params.get(3));
    	textPhone.setText(params.get(4));
    	textAdress.setText(params.get(5));
    	textCity.setText(params.get(6));
    }

}
