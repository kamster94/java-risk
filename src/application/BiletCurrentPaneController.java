package application;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class BiletCurrentPaneController implements Initializable{

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
    
    private User user;
    private ObservableList<Bilet> biletyData = FXCollections.observableArrayList();

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
		List<String> results = new ArrayList<String>(user.getFields("SELECT * FROM DBA.Bilet WHERE IDUzytkownika = " + user.id, columns));
		int count = user.getInt("SELECT COUNT(*) FROM DBA.Bilet WHERE IDUzytkownika = " + user.id);
		for (int i=0; i<count; i++){
			String title = user.getString("SELECT TytulFilmu FROM DBA.Film WHERE IDFilmu = " + results.get(i*7));
			int idTypuSeansu = user.getInt("SELECT IDTypuSenasu FROM DBA.Seans WHERE IDSeansu = " + results.get((i*7)+1));
			String typ = user.getString("SELECT NazwaTypuSeansu FROM DBA.TypSeansu WHERE IDTypuSenasu = " + idTypuSeansu);
			int rows = user.getInt("SELECT COUNT(*) FROM DBA.Miejsce WHERE IDSali = " + results.get((i*7)+3) + " AND IDRzedu = 1 AND IDKina = " + results.get((i*7)+6));
			int miejsce = ((Integer.parseInt(results.get((i*7)+4))*rows)+Integer.parseInt(results.get((i*7)+5)));
			biletyData.add(new Bilet(title, typ, results.get((i*7)+2), Integer.parseInt(results.get((i*7)+3)), miejsce, Integer.parseInt(results.get((i*7)+4)), Integer.parseInt(results.get((i*7)+6)), 0));
		}
		tableBilet.setItems(biletyData);
	}

}
