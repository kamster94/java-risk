package application;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

public class CinemaPaneController implements Initializable{

	@FXML
    private Text cinemaEmail;

    @FXML
    private Text cinemaCity;

    @FXML
    private Text cinemaAdress;
    
    @FXML
    private Text cinemaTelephone;
    
    @FXML
    private Button buttonFilms;
    
    @FXML
    private ImageView map;
    
    private User user;
    private List<String> columns;
    private int id;
    private DashboardController parent;
    

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		columns = new ArrayList<String>();
		columns.add("Ulica");
		columns.add("Miasto");
		columns.add("Telefon");
		columns.add("Email");
		
	}
	
	@FXML
    void doFilms(ActionEvent event) {
		parent.doCinemaPaneReperuar(id);
    }
	
	public void setCinema(User user, int num){
		id = num;
		this.user = user;
		List<String> params = new ArrayList<String>(this.user.getFields("SELECT * FROM DBA.Kino WHERE IDKina = " + num, columns));
		cinemaAdress.setText(params.get(0));
		cinemaCity.setText(params.get(1));
		cinemaTelephone.setText(params.get(2));
		cinemaEmail.setText(params.get(3));
		Image image = new Image("file:img/" + num + ".png");
		map.setImage(image);
	}
	
	public void setParent(DashboardController parent){
		this.parent = parent;
	}

}
