package application;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.util.StringConverter;

public class EditSeansController implements Initializable{

    @FXML
    private ChoiceBox<String> choiceFilm;
    
    @FXML
    private ChoiceBox<String> choiceTyp;

    @FXML
    private ChoiceBox<String> choiceCinema;

    @FXML
    private ChoiceBox<String> choiceSala;

    @FXML
    private DatePicker choiceDate;

    @FXML
    private TextField textfieldHour;

    @FXML
    private Button buttonCancel;

    @FXML
    private Button buttonConfirm;
    
    @FXML
    private CheckBox checkArch;
    
    private List<String> columns;
    private List<String> films;
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
    	if (textfieldHour.getText().length() == 5 && textfieldHour.getText().contains(":")){
    		if (selected == -1){
    			selected = user.getInt("SELECT COUNT(IDSeansu) FROM DBA.Seans") + 1;
    			
    			String time = choiceDate.getValue().toString();
    	    	time = time + " " + textfieldHour.getText();
    	    	int arch = 0;
    	    	if (checkArch.isSelected()) arch = 1;
	    		
    	    	ok = user.insertSql( "INSERT INTO DBA.Seans (IDFilmu, IDSeansu, IDKina, IDSali, IDTypuSenasu, DataSeansu, czyArchiwalna) VALUES " +
	    					"(" + (choiceFilm.getSelectionModel().getSelectedIndex()+1) + ", " + selected + ", " + (choiceCinema.getSelectionModel().getSelectedIndex()+1) + ", " + (choiceSala.getSelectionModel().getSelectedIndex()+1) + ", " + (choiceTyp.getSelectionModel().getSelectedIndex()+1) + ", '" + time + "', " + arch + ")");
    		}
    		else {
    			int arch = 0;
    	    	if (checkArch.isSelected()) arch = 1;
    			String time = choiceDate.toString().replaceAll("[^a-zA-Z0-9]+","-");
    	    	time = time + " " + textfieldHour.getText();
    	    	ok = user.insertSql("UPDATE DBA.Seans SET IDFilmu = " + (choiceFilm.getSelectionModel().getSelectedIndex()+1) + ", IDIDKina " + (choiceCinema.getSelectionModel().getSelectedIndex()+1) + ", IDSali = " + (choiceSala.getSelectionModel().getSelectedIndex()+1) + ", IDTypuSenasu = " + (choiceTyp.getSelectionModel().getSelectedIndex()+1) + ", DataSeansu = '" + time + "', czyArchiwalna = " + arch + " WHERE IDSeansu = " + selected);
    		}
    		if (ok){
    			Alert confirmation = new Alert(AlertType.INFORMATION);
    			confirmation.setTitle("Potwierdzenie");
    			confirmation.setHeaderText("Seans zosta³ dodany");
    			confirmation.show();
    			try {
    			    ((Node)(event.getSource())).getScene().getWindow().hide();
    			} catch(Exception e) {
    				Main.logger.log(Level.SEVERE, e.toString());
    			}
    		}
    	}
    	else {
    		Alert alert = new Alert(AlertType.INFORMATION);
    		alert.setTitle("B³¹d");
    		alert.setHeaderText("Godzina ma z³y format");
    		alert.setContentText("WprowadŸ godzinê w formacie HH:MM");
    		alert.showAndWait();
    	}
    }
    
    public void setDetails(User user, int selected){
    	this.user = user;
    	this.selected = selected;
	    columns = new ArrayList<String>();
	    columns.add("TytulFilmu");
	    films = new ArrayList<String>(user.getFields("SELECT * FROM DBA.Film", columns));
	    choiceFilm.setItems((FXCollections.observableArrayList(films)));
	    choiceCinema.setItems((FXCollections.observableArrayList("Kino Femina", "CinemaCity")));
	    choiceTyp.setItems((FXCollections.observableArrayList("Oryginalny", "Z napisami PL", "Z dubbingiem PL")));
	    choiceSala.setItems((FXCollections.observableArrayList("1", "2")));
	    if (selected != -1){
	    	choiceFilm.getSelectionModel().select(user.getInt("SELECT IDFilmu FROM DBA.Seans WHERE IDSeansu = " + selected));
	    	choiceTyp.getSelectionModel().select(user.getInt("SELECT IDTypuSenasu FROM DBA.Seans WHERE IDSeansu = " + selected));
	    	choiceCinema.getSelectionModel().select(user.getInt("SELECT IDKina FROM DBA.Seans WHERE IDSeansu = " + selected));
	    	choiceSala.getSelectionModel().select(user.getInt("SELECT IDSali FROM DBA.Seans WHERE IDSeansu = " + selected));
	    	if (user.getInt("SELECT czyArchiwalna FROM DBA.Seans WHERE IDSeansu = " + selected) == 1) checkArch.setSelected(true);
	    	else checkArch.setSelected(false);
	    }
    }

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		String pattern = "yyyy-MM-dd";
		choiceDate.setConverter(new StringConverter<LocalDate>() {
		     DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(pattern);

		     @Override 
		     public String toString(LocalDate date) {
		         if (date != null) {
		             return dateFormatter.format(date);
		         } else {
		             return "";
		         }
		     }

		     @Override 
		     public LocalDate fromString(String string) {
		         if (string != null && !string.isEmpty()) {
		             return LocalDate.parse(string, dateFormatter);
		         } else {
		             return null;
		         }
		     }
		 });
		
	}

}
