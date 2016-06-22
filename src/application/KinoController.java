package application;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.fxml.Initializable;


public class KinoController implements Initializable
{
	@FXML
    private Button btnOk;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) 
	{
		
	}

	@FXML
	private void doClose(ActionEvent event) {
		try {
		    ((Node)(event.getSource())).getScene().getWindow().hide();
		} catch(Exception e) {
			Main.logger.log(Level.SEVERE, e.toString());
		}
	}
}
