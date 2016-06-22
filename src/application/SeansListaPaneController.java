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

public class SeansListaPaneController implements Initializable{

    @FXML
    private TableView<Seans> tableFilms;

    @FXML
    private TableColumn<Seans, String> columnTyp;

    @FXML
    private Button buttonEdit;
    
    @FXML
    private Button buttonNewSeans;

    @FXML
    private TableColumn<Seans, String> columnData;

    @FXML
    private TableColumn<Seans, String> columnKino;

    @FXML
    private TableColumn<Seans, String> columnSala;

    @FXML
    private TableColumn<Seans, String> columnTitle;
    
    private List<String> columnsList;
    private ObservableList<Seans> filmsData = FXCollections.observableArrayList();
    private User user;
    private DashboardAdminController parent;

    @FXML
    void doEdit(ActionEvent event) {
    	Seans selectedFilm = tableFilms.getSelectionModel().getSelectedItem();
        if (selectedFilm != null) {
        	parent.doSeansListaPaneEdit(selectedFilm.getIdSeansu());
        } 
        else {
        	notSelectedAlert();
        }
    }
    
    @FXML
    void doNewSeans(ActionEvent event) {
    	parent.doSeansListaPaneEdit(-1);
    }

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		columnTitle.setCellValueFactory(cellData -> cellData.getValue().propertyTitle());
		columnKino.setCellValueFactory(cellData -> cellData.getValue().propertyKino());
        columnTyp.setCellValueFactory(cellData -> cellData.getValue().propertyTyp());
        columnSala.setCellValueFactory(cellData -> cellData.getValue().propertySala());
        columnData.setCellValueFactory(cellData -> cellData.getValue().propertyData());
        
        columnsList = new ArrayList<String>();
		columnsList.add("IDFilmu");
		columnsList.add("IDSeansu");
		columnsList.add("IDTypuSenasu");
		columnsList.add("IDSali");
		columnsList.add("DataSeansu");
		columnsList.add("IDKina");
	}
	
	public void setDetails(User user){
		this.user = user;
		fillList();
	}
	
	public void fillList(){
		List<String> params = new ArrayList<String>(this.user.getFields("SELECT * FROM DBA.Seans WHERE czyArchiwalna = 0", columnsList));
		int count = params.size();
		for (int i=0; i<count; i+=6){
			filmsData.add(new Seans(user, params.get(i), params.get(i+1), params.get(i+2), params.get(i+3), params.get(i+4), params.get(i+5)));
		}
		tableFilms.setItems(filmsData);
	}
	
	private void notSelectedAlert(){
    	Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("B³¹d");
		alert.setHeaderText("Nie wybrano seansu!");
		alert.setContentText("Wybierz jeden seans z listy");
		alert.showAndWait();
    }
	
	public void setParent(DashboardAdminController parent){
		this.parent = parent;
	}

}
