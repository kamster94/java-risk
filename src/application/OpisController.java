package application;

import java.io.IOException;
import java.util.logging.Level;

import org.json.simple.parser.ParseException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.text.Text;

public class OpisController {
	
	private String title;

    @FXML
    private Text textTitle;

    @FXML
    private ListView<String> listDetails;
    
    @FXML
    private TextArea textPlot;

    @FXML
    private TextArea textWriters;

    @FXML
    private TextArea textActors;
    
    private ObservableList<String> items;

    @FXML
    void doClose(ActionEvent event) {
    	try {
		    ((Node)(event.getSource())).getScene().getWindow().hide();
		} catch(Exception e) {
			Main.logger.log(Level.SEVERE, e.toString());
		}
    }
    
    public void setMovie(String title){
    	this.title = title;
    	textTitle.setText(this.title);
    	Imdb parser = new Imdb(title);
    	try {
			parser.setMovieData();
		} catch (IOException e) {
			Main.logger.log(Level.SEVERE, e.toString());
		} catch (ParseException e) {
			Main.logger.log(Level.SEVERE, e.toString());
		}
    	items = FXCollections.observableArrayList (title, parser.genre, parser.runtime, parser.released, parser.country);
    	listDetails.setItems(items);
    	textPlot.setText(parser.plot);
    	textWriters.setText(parser.writers);
    	textActors.setText(parser.actors);
    }

}
