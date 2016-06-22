package application;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.logging.Level;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Imdb {
	public String url = "";
	public String title = "";
	public String released = "";
	public String runtime = "";
	public String genre = "";
	public String writers = "";
	public String actors = "";
	public String plot = "";
	public String country = "";
	
	public Imdb(String title){
		try {
			this.title = title;
			this.url = "http://www.omdbapi.com/?t=" + URLEncoder.encode(title, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			Main.logger.log(Level.SEVERE, e.toString());
		}
		
	}
	
	public void setMovieData() throws IOException, ParseException {
		String content="";
		try{
	      URL url1 = new URL(this.url);
	      URLConnection urlConnection = url1.openConnection();
	      BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
	 
	      String line = "";
	 
	      while ((line = bufferedReader.readLine()) != null){
	    	  content+=line+"\n";
	      }
	      bufferedReader.close();
	      
	      JSONParser parser = new JSONParser();

	      Object obj = parser.parse(content);
	      JSONObject jsonObj = (JSONObject) obj;
	      
	      released = (String) jsonObj.get("Released");
	      runtime = (String) jsonObj.get("Runtime");
	  	  genre = (String) jsonObj.get("Genre");
	  	  writers = (String) jsonObj.get("Writer");
	  	  actors = (String) jsonObj.get("Actors");
	  	  plot = (String) jsonObj.get("Plot");
	  	  country = (String) jsonObj.get("Country");
		}
		catch(ClassCastException e1){
			Main.logger.log(Level.SEVERE, e1.toString());
		}
	}
	
	
}
