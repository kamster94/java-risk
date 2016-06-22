package application;

import java.util.ArrayList;
import java.util.List;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Seans {
	
	public StringProperty title;
	public StringProperty sala;
	public StringProperty typ;
	public StringProperty data;
	public StringProperty idkina;
	
	private String kino;
	private String idSeansu;
	private String idFilmu;
	
	public Seans(User user, String idFilmu, String idSeansu, String idTypu, String idSali, String data, String idKina){
		this.idFilmu = idFilmu;
		this.idSeansu = idSeansu;
		sala = new SimpleStringProperty(idSali);
		kino = idKina;
		idkina = new SimpleStringProperty(this.kino);
		List<String> columnTyp = new ArrayList<String>();
		columnTyp.add("NazwaTypuSeansu");
		List<String> typName = new ArrayList<String>(user.getFields("SELECT * FROM DBA.TypSeansu WHERE IDTypuSenasu = " + idTypu, columnTyp));
		typ = new SimpleStringProperty(typName.get(0));
		List<String> columnTytul = new ArrayList<String>();
		columnTytul.add("TytulFilmu");
		List<String> tytulName = new ArrayList<String>(user.getFields("SELECT * FROM DBA.Film WHERE IDFilmu = " + idFilmu, columnTytul));
		title = new SimpleStringProperty(tytulName.get(0));
		this.data = new SimpleStringProperty(data);
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
	
	public StringProperty propertyKino(){
		return idkina;
	}
	
	public int getIdFilmu(){
		return Integer.parseInt(idFilmu);
	}
	
	public int getIdSeansu(){
		return Integer.parseInt(idSeansu);
	}
	
	public int getIdKina(){
		return Integer.parseInt(kino);
	}

}
