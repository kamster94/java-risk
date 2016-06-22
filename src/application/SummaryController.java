package application;

import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Level;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

public class SummaryController implements Initializable{

    @FXML
    private TableView<Bilet> tableBilet;
    
    @FXML
    private TableColumn<Bilet, String> columnTitle;

    @FXML
    private TableColumn<Bilet, String> columnTyp;
    
    @FXML
    private TableColumn<Bilet, String> columnData;
    
    @FXML
    private TableColumn<Bilet, String> columnSala;
    
    @FXML
    private TableColumn<Bilet, String> columnSeat;

    @FXML
    private Button buttonCancel;

    @FXML
    private Button buttonConfirm;

    @FXML
    private ChoiceBox<String> choicePayment;
    
    @FXML
    private ChoiceBox<String> choiceType;

    @FXML
    private TextField textboxName;

    @FXML
    private TextField textboxEmail;

    @FXML
    private TextField textboxPhone;
    
    private ObservableList<Bilet> biletyData = FXCollections.observableArrayList();
    private User user;
    private int idKina;
    private int idSali;
    private int idFilmu;
    private int idSeansu;
    private String title;
    private String typ;
    private String data;
    private int columns;
    private List<Integer> selected;
    
    private SeatSelectController parent;
    private Locale locale;

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
    	if (user.guest && (textboxName.getText().trim().isEmpty() || textboxEmail.getText().trim().isEmpty() || textboxPhone.getText().trim().isEmpty())){
    		Alert alertPuste = new Alert(AlertType.INFORMATION);
    		if (locale.getDisplayLanguage()=="en_EN") {
    			alertPuste.setTitle("Error");
        		alertPuste.setHeaderText("One of the fields is empty");
        		alertPuste.setContentText("You have to fill all fields");
    		}
    		else {
    			alertPuste.setTitle("B³¹d");
        		alertPuste.setHeaderText("Jedno z wymaganych pól jest puste!");
        		alertPuste.setContentText("Musisz wype³niæ wszystkie pola.");
    		}
    		alertPuste.show();
    	}
    	else {
    		int idTypuBiletu = choiceType.getSelectionModel().getSelectedIndex() + 1;
    		int cena = 0;
    		switch (idTypuBiletu) {
	    		case 1: cena = 30;
	    		break;
	    		case 2: cena = 20;
	    		break;
	    		case 3: cena = 15;
	    		break;
    		}
    		boolean rezerwacja;
    		if (choiceType.getSelectionModel().getSelectedIndex() == 0) rezerwacja = true;
    		else rezerwacja = false;
    		boolean ok = false;
    		for (int item : selected) {
    			int idbiletu = user.getInt("SELECT COUNT(*) FROM DBA.BiletHistoria") + user.getInt("SELECT COUNT(*) FROM DBA.Bilet");
    			String sql = "INSERT INTO DBA.Bilet (IDUzytkownika, IDBiletu, IDKina, IDSali, IDRzedu, IDMiejsca, IDFilmu, IDSeansu, IDTypuBiletu, Cena";
	    		String values = ") VALUES (" + user.id + ", " + idbiletu + ", " + idKina + ", " + idSali + ", " + calculateRow(item) + ", " + calculateSeat(item) + ", " + idFilmu + ", " + idSeansu + ", " + idTypuBiletu + ", " + cena;
	    		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	    		Date date = new Date();
	    		if (rezerwacja) {
	    			sql += ", Rezerwacja, DataRezerwacji, CzyZaplacono";
	    			values += ", 1, '" + dateFormat.format(date) + "', 0";
	    		}
	    		else {
	    			sql += ", Rezerwacja, DataRezerwacji, CzyZaplacono, DataZaplaty";
	    			values += ", 0, '" + dateFormat.format(date) + "', 1, '" + dateFormat.format(date) + "'";
	    		}
	    		values += ")";
	    		sql += values;
	    		ok = user.insertSql(sql);
    		}
    		if (ok){
    			if (rezerwacja){
    				Alert confirmation = new Alert(AlertType.INFORMATION);
    				if (locale.getDisplayLanguage()=="angielski") {
    					confirmation.setTitle("Confirmation");
    	    			confirmation.setHeaderText("Resevation was sucesfull");
    	    			confirmation.setContentText("You have to pay for tickets an hour before film starts");
    				}
    				else {
    					confirmation.setTitle("Potwierdzenie");
    	    			confirmation.setHeaderText("Rezerwacja przebieg³a pomyœlnie");
    	    			confirmation.setContentText("P³atnoœci nale¿y dokonaæ najpóŸniej godzinê przed seansem");
    				}
	    			confirmation.show();
    			}
    			else {
    				Alert confirmation = new Alert(AlertType.INFORMATION);
    				if (locale.getDisplayLanguage()=="angielski") {
    					confirmation.setTitle("Confirmation");
    	    			confirmation.setHeaderText("Purchase was sucessfull");
    	    			confirmation.setContentText("You have to claim your ticket 30 minutes before film starts");
    				}
    				else {
    					confirmation.setTitle("Potwierdzenie");
    	    			confirmation.setHeaderText("P³atnoœæ przebieg³a pomyœlnie");
    	    			confirmation.setContentText("Bilet nale¿y odebraæ pó³ godziny przed seansem");
    				}
	    			confirmation.show();
    			}
    		}
    		parent.close();
    		buttonCancel.fire();
    	}
    }
    
    public void setDetails(User user, int idFilmu, int idSeansu, int idKina, int idSali, String title, String typ, String data, int rows, int columns, List<Integer> selected){
    	this.user = user;
    	this.idKina = idKina;
    	this.idSali = idSali;
    	this.idFilmu = idFilmu;
    	this.idSeansu = idSeansu;
    	this.title = title;
    	this.typ = typ;
    	this.data = data;
    	this.columns = columns;
    	this.selected = new ArrayList<Integer>(selected);
    	if (!user.guest){
    		textboxName.setDisable(true);
    		textboxEmail.setDisable(true);
    		textboxPhone.setDisable(true);
    	}
    	fillList();
    }
    
    public void setParent(SeatSelectController parent){
    	this.parent = parent;
    }

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		columnTitle.setCellValueFactory(cellData -> cellData.getValue().propertyTitle());
        columnTyp.setCellValueFactory(cellData -> cellData.getValue().propertyTyp());
        columnSala.setCellValueFactory(cellData -> cellData.getValue().propertySala());
        columnData.setCellValueFactory(cellData -> cellData.getValue().propertyData());
        columnSeat.setCellValueFactory(cellData -> cellData.getValue().propertyMiejsce());
        
	}
	
	public void fillList(){
		for (int item : selected){
			biletyData.add(new Bilet(title, typ, data, idSali, calculateSeat(item), calculateRow(item), idKina, 0));
		}
		tableBilet.setItems(biletyData);
	}
	
	public int calculateRow(int seat){
		return ((seat-1)/columns) + 1;
	}
	
	public int calculateSeat(int seat){
		int row = calculateRow(seat);
		row--;
		return seat - (row*columns);
	}
	
	public void setLocale(Locale locale){
		this.locale = locale;
		if (locale.getDisplayLanguage()=="angielski") choicePayment.setItems((FXCollections.observableArrayList("Bank transfer", "In cinema")));
        else choicePayment.setItems((FXCollections.observableArrayList("Przelewem", "Na miejscu")));
        choicePayment.getSelectionModel().select(0);
        if (locale.getDisplayLanguage()=="angielski") choiceType.setItems((FXCollections.observableArrayList("Normal", "Reduced", "Student")));
        else choiceType.setItems((FXCollections.observableArrayList("Normalny", "Ulgowy", "Studencki")));
        choiceType.getSelectionModel().select(0);
	}

}
