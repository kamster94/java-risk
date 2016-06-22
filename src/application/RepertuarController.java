package application;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Level;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class RepertuarController implements Initializable{

    @FXML
    private Button buttonReturn;

    @FXML
    private Button buttonOrder;

    @FXML
    private Text cinemaCity;

    @FXML
    private Text cinemaAdress;
    
    @FXML
    private Button buttonImdb;

    @FXML
    private TableColumn<Seans, String> columnTitle;
    
    @FXML
    private TableColumn<Seans, String> columnType;
    
    @FXML
    private TableColumn<Seans, String> columnRoom;

    @FXML
    private TableColumn<Seans, String> columnDate;

    @FXML
    private TableView<Seans> tableFilms;
    
    private ObservableList<Seans> filmsData = FXCollections.observableArrayList();
    
    private Stage myStage;
    
    private User user;
    private List<String> columnsCinema;
    private List<String> columnsList;
    private int cinemaId;
    private Locale locale;

    @FXML
    void doOrder(ActionEvent event) {
    	Seans selectedFilm = tableFilms.getSelectionModel().getSelectedItem();
        if (selectedFilm != null) {
        	try {
    			Stage stage = new Stage();
    			stage.setTitle(selectedFilm.propertyTitle().get() + " - zamów bilety");
    		    FXMLLoader fxmlLoader = new FXMLLoader();
    		    fxmlLoader.setResources(ResourceBundle.getBundle("application.bundles.lang", locale));
    	    	BorderPane sala = fxmlLoader.load(getClass().getResource("SeatSelect.fxml").openStream());
    	    	SeatSelectController controller = (SeatSelectController) fxmlLoader.getController();
    	    	controller.setDetails(user, selectedFilm.getIdFilmu(), selectedFilm.getIdSeansu(), cinemaId, selectedFilm.propertyTitle().get(), selectedFilm.propertyTyp().get(), selectedFilm.propertySala().get(), selectedFilm.propertyData().get());
    		    controller.setParent(this);
    		    controller.setStage(stage);
    		    controller.setLocale(locale);
    	    	Scene scene = new Scene(sala);
    	    	scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
    		    stage.setScene(scene);
    		    stage.initModality(Modality.WINDOW_MODAL);
    		    stage.initOwner(myStage);
    		    stage.initStyle(StageStyle.UNDECORATED);
    		    stage.setResizable(false);
    		    stage.show();
    		} catch(Exception e) {
    			Main.logger.log(Level.SEVERE, e.toString());
    		}
        } 
        else {
        	notSelectedAlert();
        }
    }
    
    @FXML
    void doImdb(ActionEvent event) {
    	Seans selectedFilm = tableFilms.getSelectionModel().getSelectedItem();
        if (selectedFilm != null) {
        	try {
    			Stage stage = new Stage();
    			stage.setTitle(selectedFilm.propertyTitle().get() + " - informacje o filmie");
    		    FXMLLoader fxmlLoader = new FXMLLoader();
    		    fxmlLoader.setResources(ResourceBundle.getBundle("application.bundles.lang", locale));
    	    	BorderPane film = fxmlLoader.load(getClass().getResource("Opis.fxml").openStream());
    	    	OpisController controller = (OpisController) fxmlLoader.getController();
    	    	controller.setMovie(selectedFilm.propertyTitle().get());
    		    Scene scene = new Scene(film);
    		    scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
    		    stage.setScene(scene);
    		    stage.initModality(Modality.WINDOW_MODAL);
    		    stage.initOwner(myStage);
    		    stage.initStyle(StageStyle.UNDECORATED);
    		    stage.setResizable(false);
    		    stage.show();
    		} catch(Exception e) {
    			Main.logger.log(Level.SEVERE, e.toString());
    		}
        } 
        else {
        	notSelectedAlert();
        }
    }
    
    private void notSelectedAlert(){
    	Alert alert = new Alert(AlertType.INFORMATION);
		if (locale.getDisplayLanguage()=="angielski") {
			alert.setTitle("Error");
			alert.setHeaderText("No seance selected!");
			alert.setContentText("Pick one from the list");
		}
		else {
			alert.setTitle("B³¹d");
			alert.setHeaderText("Nie wybrano seansu!");
			alert.setContentText("Wybierz jeden seans z listy");
		}
		alert.showAndWait();
    }

    @FXML
    void doReturn(ActionEvent event) {
    	try {
		    ((Node)(event.getSource())).getScene().getWindow().hide();
		} catch(Exception e) {
			Main.logger.log(Level.SEVERE, e.toString());
		}
    }

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		columnsCinema = new ArrayList<String>();
		columnsCinema.add("Ulica");
		columnsCinema.add("Miasto");
		columnsList = new ArrayList<String>();
		columnsList.add("IDFilmu");
		columnsList.add("IDSeansu");
		columnsList.add("IDTypuSenasu");
		columnsList.add("IDSali");
		columnsList.add("DataSeansu");
		
		columnTitle.setCellValueFactory(cellData -> cellData.getValue().propertyTitle());
        columnType.setCellValueFactory(cellData -> cellData.getValue().propertyTyp());
        columnRoom.setCellValueFactory(cellData -> cellData.getValue().propertySala());
        columnDate.setCellValueFactory(cellData -> cellData.getValue().propertyData());
	}
	
	public void setCinema(User user, int num){
		this.user = user;
		cinemaId = num;
		List<String> params = new ArrayList<String>(this.user.getFields("SELECT * FROM DBA.Kino WHERE IDKina = " + num, columnsCinema));
		cinemaAdress.setText(params.get(0));
		cinemaCity.setText(params.get(1));
		fillList();
	}
	
	public void fillList(){
		List<String> params = new ArrayList<String>(this.user.getFields("SELECT * FROM DBA.Seans WHERE czyArchiwalna = 0 AND IDKina = " + cinemaId, columnsList));
		int count = params.size();
		for (int i=0; i<count; i+=5){
			filmsData.add(new Seans(user, params.get(i), params.get(i+1), params.get(i+2), params.get(i+3), params.get(i+4), Integer.toString(cinemaId)));
		}
		tableFilms.setItems(filmsData);
	}
	
	public void setStage(Stage myStage){
		this.myStage = myStage;
	}
	
	public void close(){
		buttonReturn.fire();
	}
	
	public void setLocale(Locale locale){
		this.locale = locale;
	}

}
