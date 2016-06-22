package application;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Bilet {

	public StringProperty title;
	public StringProperty sala;
	public StringProperty typ;
	public StringProperty data;
	public StringProperty miejsce;
	public StringProperty kino;
	
	public int iduzytkownika;
	
	public Bilet(String title, String typ, String data, int sala, int miejsce, int rzad, int idkina, int iduzytkownika){
		this.iduzytkownika = iduzytkownika;
		this.title = new SimpleStringProperty(title);
		this.sala = new SimpleStringProperty(Integer.toString(sala));
		this.typ = new SimpleStringProperty(typ);
		this.data = new SimpleStringProperty(data);
		this.miejsce = new SimpleStringProperty(miejsce + ", rz¹d " + rzad);
		this.kino = new SimpleStringProperty(Integer.toString(idkina));
	}

	public StringProperty propertyTitle() {
		return title;
	}

	public StringProperty propertySala() {
		return sala;
	}

	public StringProperty propertyTyp() {
		return typ;
	}

	public StringProperty propertyData() {
		return data;
	}
	
	public StringProperty propertyMiejsce() {
		return miejsce;
	}
	
	public StringProperty propertyKino() {
		return kino;
	}
}
