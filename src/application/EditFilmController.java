package application;

import java.util.logging.Level;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

public class EditFilmController {

    @FXML
    private TextField textfieldTitle;

    @FXML
    private Button buttonCancel;

    @FXML
    private Button buttonConfirm;
    
    private int selected;
    private User user;

    @FXML
    void doCancel(ActionEvent event) {
    	try {
		    ((Node)(event.getSource())).getScene().getWindow().hide();
		} catch(Exception e) {
			Main.logger.log(Level.SEVERE, e.toString());
		}
    }

    @FXML
    void doConfirm(ActionEvent event) {
    	boolean ok = false;
    	if (textfieldTitle.getText().trim().isEmpty()){
    		Alert alertPuste = new Alert(AlertType.INFORMATION);
    		alertPuste.setTitle("B³¹d");
    		alertPuste.setHeaderText("Jedno z wymaganych pól jest puste!");
    		alertPuste.setContentText("Musisz wype³niæ wszystkie pola.");
    		alertPuste.show();
    	}
    	else {
    		if (selected == -1) {
    			selected = user.getInt("SELECT COUNT(*) FROM DBA.Film") + 1;
    			ok = user.insertSql("INSERT INTO DBA.Film (IDFilmu, TytulFilmu) VALUES (" + selected + ", '" + textfieldTitle.getText() + "')");
    		}
    		else ok = user.insertSql("UPDATE DBA.Film SET TytulFilmu = '" + textfieldTitle.getText() + "' WHERE IDFilmu = " + selected);
    	}
    	if (ok){
    		Alert confirmation = new Alert(AlertType.INFORMATION);
			confirmation.setTitle("Potwierdzenie");
			confirmation.setHeaderText("Film zosta³ dodany");
			confirmation.show();
			try {
			    ((Node)(event.getSource())).getScene().getWindow().hide();
			} catch(Exception e) {
				Main.logger.log(Level.SEVERE, e.toString());
			}
    	}
    }
    
    public void setDetails(User user, int selected){
    	this.selected = selected;
    	this.user = user;
    	if (selected != -1) textfieldTitle.setText(user.getString("SELECT TytulFilmu FROM DBA.Film WHERE IDFilmu = " + selected));
    }

}
