package application;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Film {
	
	public StringProperty title;
	public int id;
	
	public Film(String title, int id){
		this.title = new SimpleStringProperty(title);
		this.id = id;
	}
	
	public StringProperty propertyTitle() {
		return title;
	}
	
	public int getId(){
		return id;
	}
}
