package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class User {
	
	public int id;
	public String mail;
	public String imie;
	public String nazwisko;
	public String ulica;
	public String miasto;
	public String data;
	public String telefon;
	public boolean guest;
	
	private String dburl;
	private String login;
	
	public User(String login, String password){
		this.login = login;
		dburl = "jdbc:sqlanywhere:uid="+login +";pwd="+password+";eng=KINO_DATABASE";
	}
	
	public String getLogin(){
		return login;
	}
	
	public int login() {
		try{
	        Connection con = DriverManager.getConnection(dburl);  
	        PreparedStatement statement = con.prepareStatement("SELECT * FROM DBA.Uzytkownik WHERE Email = ?");
	        statement.setString(1, login);
	        ResultSet rs = statement.executeQuery();
	        if (rs.next()) {
	            id = rs.getInt("IDUzytkownika");
	            mail = rs.getString("Email");
	            imie = rs.getString("Imie");
	            nazwisko = rs.getString("Nazwisko");
	            ulica = rs.getString("Ulica");
	            miasto = rs.getString("Miasto");
	            data = rs.getString("DataUrodzenia");
	            telefon = rs.getString("Telefon");
	        }
	        rs.close();
	        statement.close();
	        con.close();
	        return 0;
		}
		catch(SQLException e)
		{
			Main.logger.log(Level.SEVERE, e.toString());
			return 1;
		}        
    }
	
	public List<String> getFields(String sql, List<String> columns){
		try{
			Connection con = DriverManager.getConnection(dburl);  
	        PreparedStatement statement = con.prepareStatement(sql);
	        ResultSet rs = statement.executeQuery();
	        List<String> values = new ArrayList<String>();
	        while (rs.next()) {
	        	for (String column : columns) {
	    			values.add(rs.getString(column));
	    		}
	        }
	        rs.close();
	        statement.close();
	        con.close();
	        return values;
		} catch(SQLException e){
			Main.logger.log(Level.SEVERE, e.toString());
			return null;
		}     
	}
	
	public int getInt(String sql){
		try{
			Connection con = DriverManager.getConnection(dburl);  
	        PreparedStatement statement = con.prepareStatement(sql);
	        ResultSet rs = statement.executeQuery();
	        int value = -1;
	        if (rs.next()) {
	    		value = rs.getInt(1);
	        }
	        rs.close();
	        statement.close();
	        con.close();
	        return value;
		} catch(SQLException e){
			Main.logger.log(Level.SEVERE, e.toString());
			return -1;
		}     
	}
	
	public String getString(String sql){
		try{
			Connection con = DriverManager.getConnection(dburl);  
	        PreparedStatement statement = con.prepareStatement(sql);
	        ResultSet rs = statement.executeQuery();
	        String value = "";
	        if (rs.next()) {
	    		value = rs.getString(1);
	        }
	        rs.close();
	        statement.close();
	        con.close();
	        return value;
		} catch(SQLException e){
			Main.logger.log(Level.SEVERE, e.toString());
			return null;
		}     
	}
	
	public boolean insertSql(String sql){
		try {
			Connection con = DriverManager.getConnection(dburl); 
			Statement statement = con.createStatement();
			statement.executeUpdate(sql);
			return true;
		} catch(SQLException e){
			Main.logger.log(Level.SEVERE, e.toString());
			return false;
		}     
	}
	
	public boolean createNewAccount(String login1, String password, String Miasto, String Ulica,
			String DataUr, String Imie, String Nazwisko, String Telefon)
	{
		try
		{
			dburl = "jdbc:sqlanywhere:uid=DBA;pwd=sql;eng=KINO_DATABASE"; //zmieniæ póŸniej na CheckLogin, checklogin
			int value = getInt("SELECT DBA.function_checkIfExist('" + login1 +"')");
	        
	        if(value == 1)
	        {
	        	Alert alert = new Alert(AlertType.INFORMATION);
	        	alert.setTitle("B£¥D!");
	        	alert.setHeaderText("U¿ytkownik o wprowadzonym e-mailu ju¿ istnieje!");
	        	alert.setContentText("Podaj inny e-mail.");
	        	alert.show();
	        	return false;
	        }
	        else
	        {
	        	Random generator = new Random();
	        	int id = generator.nextInt(70) + 1;
	        	boolean wynik = false;
	        	wynik = insertSql("INSERT INTO DBA.Uzytkownik(IDUzytkownika, Email, Telefon, Miasto, Ulica, Imie, Nazwisko, DataUrodzenia) "
	        	+ "VALUES (" + id +",'"+login1+"'," + "'"+Telefon+"',"+"'"+Miasto+"',"+"'"+Ulica+"'," 
	        			+ "'"+Imie+"'," + "'"+Nazwisko+"'," +DataUr+")");

	        	if(wynik == true)
	        	{
	        		Connection con = DriverManager.getConnection(dburl); 
	        		PreparedStatement statement = con.prepareStatement("CALL DBA.procedure_AddToUsers(" +"'"+login1+"'," + "'"+password+"')" );
	        		ResultSet rs = statement.executeQuery();
		        	rs.close();
			        statement.close();
			        con.close();
			        return true;
	        	}
	        	else
	        	{
			        return false;
	        	}
	        }
		}
		catch(SQLException e)
		{
			Main.logger.log(Level.SEVERE, e.toString());
			return false;
		}
		
	}
	
	public boolean changeLogin(String oldlogin, String newlogin, String newpassword)
	{
		try
		{
			dburl = "jdbc:sqlanywhere:uid=DBA;pwd=sql;eng=KINO_DATABASE"; //zmieniæ póŸniej na CheckLogin, checklogin

	        Connection con = DriverManager.getConnection(dburl); 
	        Statement statement = con.createStatement();
			statement.executeUpdate("CALL DBA.procedure_deleteFromUsers("
			        +"'"+oldlogin+"'," + "'"+newlogin+"'," + "'"+newpassword+"')");
		    statement.close();
		    con.close();
	        return true;

		}
		catch(SQLException e)
		{
			Main.logger.log(Level.SEVERE, e.toString());
			return false;
		}
	}
	
	public boolean changeData(String nlogin, String ntelefon, String nmiasto, String nulica, String nimie, String nnazwisko, String ndataur)
	{
		try
		{
			dburl = "jdbc:sqlanywhere:uid=DBA;pwd=sql;eng=KINO_DATABASE"; //zmieniæ póŸniej na CheckLogin, checklogin
	        Connection con = DriverManager.getConnection(dburl); 
	        Statement statement = con.createStatement();
			statement.executeUpdate("CALL DBA.procedure_zmienDaneUzytkownika('"+nlogin+"'," + "'"+ntelefon+"',"
		        	+"'"+nmiasto+"'," + "'"+nulica+"'," + "'"+nimie+"'," + "'"+nnazwisko+"'," +ndataur +")");
		    statement.close();
		    con.close();
		    //String s = "CALL DBA.procedure_zmienDaneUzytkownika('"+nlogin+"'," + "'"+ntelefon+"',"
		    //    	+"'"+nmiasto+"'," + "'"+nulica+"'," + "'"+nimie+"'," + "'"+nnazwisko+"'," +ndataur +")";
		    this.imie = nimie;
		    this.nazwisko = nnazwisko;
		    this.telefon = ntelefon;
		    this.data = ndataur;
		    this.miasto = nmiasto;
		    this.ulica = nulica;   
	        return true;

		}
		catch(SQLException e)
		{
			Main.logger.log(Level.SEVERE, e.toString());
			return false;
		}
	}

}
