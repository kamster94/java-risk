package application;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;

public class FilmListaPaneController implements Initializable{

    @FXML
    private TableView<Film> tableFilms;

    @FXML
    private TableColumn<Film, String> columnTitle;

    @FXML
    private Button buttonNewFilm;

    @FXML
    private Button buttonEdit;
    
    private List<String> columnsList;
    private ObservableList<Film> filmsData = FXCollections.observableArrayList();
    private DashboardAdminController parent;

    @FXML
    void doEdit(ActionEvent event) {
    	Film selectedFilm = tableFilms.getSelectionModel().getSelectedItem();
        if (selectedFilm != null) {
        	parent.doFilmListaPaneEdit(selectedFilm.getId());
        } 
        else {
        	notSelectedAlert();
        }
    }

    @FXML
    void doNewFilm(ActionEvent event) {
    	parent.doFilmListaPaneEdit(-1);
    }

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		columnTitle.setCellValueFactory(cellData -> cellData.getValue().propertyTitle());
		columnsList = new ArrayList<String>();
		columnsList.add("TytulFilmu");
	}
	
	private void notSelectedAlert(){
    	Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("B³¹d");
		alert.setHeaderText("Nie wybrano seansu!");
		alert.setContentText("Wybierz jeden seans z listy");
		alert.showAndWait();
    }
	
	public void setDetails(User user){
		List<String> params = new ArrayList<String>(user.getFields("SELECT * FROM DBA.Film", columnsList));
		int i = 1;
		for (String title : params){
			filmsData.add(new Film(title, i));
			i++;
		}
		tableFilms.setItems(filmsData);
	}
	
	public void setParent(DashboardAdminController parent){
		this.parent = parent;
	}

}
