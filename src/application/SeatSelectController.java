package application;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Level;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class SeatSelectController {

    @FXML
    private Button buttonCancel;
    
    @FXML
    private Button buttonOk;

    @FXML
    private Text textFilmDetails;
    
    @FXML
    private GridPane gridSeats;
    
    private User user;
    private String title;
    private String typ;
    private int sala;
    private String data;
    private int rows;
    private int seats;
    private int idKina;
    private int idSali;
    private int idFilmu;
    private int idSeansu;
    private List<Integer> selectedList;
    
    private RepertuarController parent;
    private Stage myStage;
    private Locale locale;

    @FXML
    void doClose(ActionEvent event) {
    	try {
		    ((Node)(event.getSource())).getScene().getWindow().hide();
		} catch(Exception e) {
			Main.logger.log(Level.SEVERE, e.toString());
		}
    }
    
    @FXML
    void doContinue(ActionEvent event) {
    	if (!selectedList.isEmpty()) {
        	try {
    			Stage stage = new Stage();
    			stage.setTitle("Podsumowanie");
    		    FXMLLoader fxmlLoader = new FXMLLoader();
    		    fxmlLoader.setResources(ResourceBundle.getBundle("application.bundles.lang", locale));
    	    	BorderPane podsumowanie = fxmlLoader.load(getClass().getResource("Summary.fxml").openStream());
    	    	SummaryController controller = (SummaryController) fxmlLoader.getController();
    	    	controller.setDetails(user, idFilmu, idSeansu, idKina, idSali, title, typ, data, rows, seats, selectedList);
    		    controller.setParent(this);
    		    controller.setLocale(locale);
    	    	Scene scene = new Scene(podsumowanie);
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
    
    public void setDetails(User user, int idFilmu, int idSeansu, int idKina, String title, String typ, String sala, String data){
    	this.user = user;
    	this.title = title;
    	this.typ = typ;
    	this.sala = Integer.parseInt(sala);
    	this.data = data;
    	this.idKina = idKina;
    	this.idFilmu = idFilmu;
    	this.idSeansu = idSeansu;
    	this.idSali = Integer.parseInt(sala);
    	textFilmDetails.setText(this.title + " " + this.typ + " Sala " + this.sala + " " + this.data);
    	
    	rows = this.user.getInt("SELECT COUNT(IDRzedu) FROM DBA.Rzad WHERE IDKina = " + idKina + " AND IDSali = " + this.sala);
    	seats = this.user.getInt("SELECT COUNT(IDMiejsca) FROM DBA.Miejsce WHERE IDKina = " + idKina + " AND IDSali = " + this.sala + " AND IDRzedu = 1");
    	
    	for (int i = 0; i <= rows; i++) {
            RowConstraints con = new RowConstraints();
            con.setPrefHeight(40);
            gridSeats.getRowConstraints().add(con);
        }
    	
    	for (int i = 0; i <= seats; i++) {
            ColumnConstraints con = new ColumnConstraints();
            con.setPrefWidth(40);
            gridSeats.getColumnConstraints().add(con);
        }
    	gridSeats.setAlignment(Pos.CENTER);
    	List<ToggleButton> seatButtons = new ArrayList<ToggleButton>();
    	selectedList = new ArrayList<Integer>();
    	for (int i = 0; i < rows; i++) {
    		for (int j = 0; j < seats; j++) {
    			int num = (i*seats) + j + 1;
    			ToggleButton seat = new ToggleButton();
    			
    			seat.setOnAction(new EventHandler<ActionEvent>() {
    	            @Override
    	            public void handle(ActionEvent event) {
    	                if(seat.isSelected()){
    	                	selectedList.add(num);
    	                	Image image = new Image("file:img/seat-choosen.png");
    	    				seat.setGraphic(new ImageView(image));
    	                }
    	                else {
    	                	selectedList.remove(selectedList.indexOf(num));
    	                	Image image = new Image("file:img/seat-ok.png");
    	    				seat.setGraphic(new ImageView(image));
    	                }
    	            }
    	        });
    			int x = -1;
    	    	x = user.getInt("SELECT IDMiejsca FROM DBA.Bilet WHERE IDKina = " + idKina + " AND IDSali = " + idSali + " AND IDSeansu = " + idSeansu + " AND IDRzedu = " + (i+1) + " AND IDMiejsca = " + (j+1));
    			if (x != -1) {
    				seat.setDisable(true);
    				Image image = new Image("file:img/seat-taken.png");
    				seat.setGraphic(new ImageView(image));
    			}
    			else {
    				Image image = new Image("file:img/seat-ok.png");
    				seat.setGraphic(new ImageView(image));
    			}
    			gridSeats.add(seat, j, i);
    			seatButtons.add(seat);
            }
        }
    	for (int i=0; i<rows; i++){
    		Label label = new Label((i+1)+"");
    		label.setAlignment(Pos.CENTER);
    		gridSeats.add(label, seats, i);
    	}
    	for (int i=0; i<seats; i++){
    		Label label = new Label("    " +(i+1)+"");
    		label.setAlignment(Pos.CENTER);
    		gridSeats.add(label, i, rows);
    	}
    	
    }
    
    public void setParent(RepertuarController parent){
    	this.parent = parent;
    }
    
    public void setStage(Stage myStage){
    	this.myStage = myStage;
    }
    
    public void close(){
    	parent.close();
    	buttonCancel.fire();
    }
    
    private void notSelectedAlert(){
    	Alert alert = new Alert(AlertType.INFORMATION);
    	if (locale.getDisplayLanguage()=="angielski") {
			alert.setTitle("Error");
			alert.setHeaderText("No seat selected!");
			alert.setContentText("Pick at least one from the list");
		}
		else {
			alert.setTitle("B³¹d");
			alert.setHeaderText("Nie wybrano miejsca!");
			alert.setContentText("Wybierz chocia¿ jedno z listy");
		}
		alert.showAndWait();
    }
    
    public void setLocale(Locale locale){
		this.locale = locale;
	}

}
