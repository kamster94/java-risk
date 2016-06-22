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

public class BiletAdminCurrentPaneController implements Initializable{

	@FXML
    private TableView<Bilet> tableBilet;
    
    @FXML
    private TableColumn<Bilet, String> columnTitle;

    @FXML
    private TableColumn<Bilet, String> columnTyp;
    
    @FXML
    private TableColumn<Bilet, String> columnData;
    
    @FXML
    private TableColumn<Bilet, String> columnKino;
    
    @FXML
    private TableColumn<Bilet, String> columnSala;
    
    @FXML
    private TableColumn<Bilet, String> columnSeat;
    
    @FXML
    private Button buttonInfoCustomer;

    private User user;
    private ObservableList<Bilet> biletyData = FXCollections.observableArrayList();
    private DashboardAdminController parent;
    
    @FXML
    void doCustomer(ActionEvent event) {
    	Bilet selectedBilet = tableBilet.getSelectionModel().getSelectedItem();
        if (selectedBilet != null) {
        	parent.doBiletListaPaneEdit(selectedBilet.iduzytkownika);
        } 
        else {
        	notSelectedAlert();
        }
    }

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		columnTitle.setCellValueFactory(cellData -> cellData.getValue().propertyTitle());
        columnTyp.setCellValueFactory(cellData -> cellData.getValue().propertyTyp());
        columnSala.setCellValueFactory(cellData -> cellData.getValue().propertySala());
        columnKino.setCellValueFactory(cellData -> cellData.getValue().propertyKino());
        columnData.setCellValueFactory(cellData -> cellData.getValue().propertyData());
        columnSeat.setCellValueFactory(cellData -> cellData.getValue().propertyMiejsce());
	}
	
	public void setDetails(User user){
		this.user = user;
		fillList();
	}
	
	public void fillList(){
		List<String> columns = new ArrayList<String>();
		columns.add("IDFilmu");
		columns.add("IDSeansu");
		columns.add("DataRezerwacji");
		columns.add("IDSali");
		columns.add("IDRzedu");
		columns.add("IDMiejsca");
		columns.add("IDKina");
		columns.add("IDUzytkownika");
		List<String> results = new ArrayList<String>(user.getFields("SELECT * FROM DBA.Bilet", columns));
		int count = user.getInt("SELECT COUNT(*) FROM DBA.Bilet");
		for (int i=0; i<count; i++){
			String title = user.getString("SELECT TytulFilmu FROM DBA.Film WHERE IDFilmu = " + results.get(i*8));
			int idTypuSeansu = user.getInt("SELECT IDTypuSenasu FROM DBA.Seans WHERE IDSeansu = " + results.get((i*8)+1));
			String typ = user.getString("SELECT NazwaTypuSeansu FROM DBA.TypSeansu WHERE IDTypuSenasu = " + idTypuSeansu);
			int rows = user.getInt("SELECT COUNT(*) FROM DBA.Miejsce WHERE IDSali = " + results.get((i*8)+3) + " AND IDRzedu = 1 AND IDKina = " + results.get((i*8)+6));
			int miejsce = ((Integer.parseInt(results.get((i*8)+4))*rows)+Integer.parseInt(results.get((i*8)+5)));
			biletyData.add(new Bilet(title, typ, results.get((i*8)+2), Integer.parseInt(results.get((i*8)+3)), miejsce, Integer.parseInt(results.get((i*8)+4)), Integer.parseInt(results.get((i*8)+6)), Integer.parseInt(results.get((i*8)+7))));
		}
		tableBilet.setItems(biletyData);
	}
	
	private void notSelectedAlert(){
    	Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("B³¹d");
		alert.setHeaderText("Nie wybrano biletu!");
		alert.setContentText("Wybierz jeden bilet z listy");
		alert.showAndWait();
    }
	
	public void setParent(DashboardAdminController parent){
		this.parent = parent;
	}

}
