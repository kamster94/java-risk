package application;

import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Level;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class DashboardController implements Initializable{

    @FXML
    private Button buttonTicketsArch;

    @FXML
    private Button buttonLogout;

    @FXML
    private Pane paneContent;

    @FXML
    private Button buttonAccount;

    @FXML
    private Button buttonCinema1;

    @FXML
    private Button buttonCinema2;

    @FXML
    private Button buttonTicketsCurrent;

    @FXML
    private Button buttonHelp;

    @FXML
    private Button buttonShipping;
    
    @FXML
    private Text textUserName;
    
    private Pane cinema;
    private Stage myStage;
    private User user;
    private Locale locale;

    @FXML
    void logOut(ActionEvent event) {
    	try {
    		Stage stage = new Stage();
			stage.setTitle("Kino");
		    FXMLLoader fxmlLoader = new FXMLLoader();
			fxmlLoader.setResources(ResourceBundle.getBundle("application.bundles.lang", locale));
	    	BorderPane dashboard = fxmlLoader.load(getClass().getResource("Login.fxml").openStream());
	    	LoginController controller = (LoginController) fxmlLoader.getController();
	    	controller.setLocaleInit(locale);
		    Scene scene = new Scene(dashboard);
		    scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		    stage.setScene(scene);
		    stage.setResizable(false);
		    stage.show();
		    ((Node)(event.getSource())).getScene().getWindow().hide();
		} catch(Exception e) {
			Main.logger.log(Level.SEVERE, e.toString());
		}
    }

    @FXML
    void showInfo(ActionEvent event) {
    	try {
			Stage stage = new Stage();
		    stage.setTitle("Kino");
		    BorderPane myPane = null;
		    FXMLLoader fxmlLoader = new FXMLLoader();
		    fxmlLoader.setResources(ResourceBundle.getBundle("application.bundles.lang", locale));
		    myPane = fxmlLoader.load(getClass().getResource("Kino.fxml").openStream());
		    Scene scene = new Scene(myPane);
		    scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		    stage.setScene(scene);
		    stage.initModality(Modality.WINDOW_MODAL);
		    stage.setResizable(false);
		    stage.initOwner(myStage);
		    stage.showAndWait();
		} catch(Exception e) {
			Main.logger.log(Level.SEVERE, e.toString());
		}
    }

    @FXML
    void loadCinema1(ActionEvent event) {
    	try {
	    	paneContent.getChildren().clear();
	    	FXMLLoader fxmlLoader = new FXMLLoader();
	    	fxmlLoader.setResources(ResourceBundle.getBundle("application.bundles.lang", locale));
	    	cinema = fxmlLoader.load(getClass().getResource("CinemaPane.fxml").openStream());
	    	paneContent.getChildren().add(cinema);
	    	CinemaPaneController controller = (CinemaPaneController) fxmlLoader.getController();
	    	controller.setCinema(user, 1);
	    	controller.setParent(this);
    	} catch (Exception e) {
    		Main.logger.log(Level.SEVERE, e.toString());
    	}
    }
    
    @FXML
    void loadCinema2(ActionEvent event) {
    	try {
	    	paneContent.getChildren().clear();
	    	FXMLLoader fxmlLoader = new FXMLLoader();
	    	fxmlLoader.setResources(ResourceBundle.getBundle("application.bundles.lang", locale));
	    	cinema = fxmlLoader.load(getClass().getResource("CinemaPane.fxml").openStream());
	    	paneContent.getChildren().add(cinema);
	    	CinemaPaneController controller = (CinemaPaneController) fxmlLoader.getController();
	    	controller.setCinema(user, 2);
	    	controller.setParent(this);
    	} catch (Exception e) {
    		Main.logger.log(Level.SEVERE, e.toString());
    	}
    }
    
    @FXML
    void loadBiletyCurrent(ActionEvent event) {
    	try {
	    	paneContent.getChildren().clear();
	    	FXMLLoader fxmlLoader = new FXMLLoader();
	    	fxmlLoader.setResources(ResourceBundle.getBundle("application.bundles.lang", locale));
	    	Pane bilety = fxmlLoader.load(getClass().getResource("BiletCurrentPane.fxml").openStream());
	    	paneContent.getChildren().add(bilety);
	    	BiletCurrentPaneController controller = (BiletCurrentPaneController) fxmlLoader.getController();
	    	controller.setDetails(user);
    	} catch (Exception e) {
    		Main.logger.log(Level.SEVERE, e.toString());
    	}
    }
    
    @FXML
    void loadBiletyHistory(ActionEvent event) {
    	try {
	    	paneContent.getChildren().clear();
	    	FXMLLoader fxmlLoader = new FXMLLoader();
	    	fxmlLoader.setResources(ResourceBundle.getBundle("application.bundles.lang", locale));
	    	Pane bilety = fxmlLoader.load(getClass().getResource("BiletHistoryPane.fxml").openStream());
	    	paneContent.getChildren().add(bilety);
	    	BiletHistoryPaneController controller = (BiletHistoryPaneController) fxmlLoader.getController();
	    	controller.setDetails(user);
    	} catch (Exception e) {
    		Main.logger.log(Level.SEVERE, e.toString());
    	}
    }
    
    @FXML
    void doChangeData(ActionEvent event) {
    	try {
    		Stage stage = new Stage();
			stage.setTitle("Kino");
		    FXMLLoader fxmlLoader = new FXMLLoader();
			fxmlLoader.setResources(ResourceBundle.getBundle("application.bundles.lang", locale));
	    	FlowPane changedata = fxmlLoader.load(getClass().getResource("ChangeData.fxml").openStream());
	    	ChangeDataController controller = (ChangeDataController) fxmlLoader.getController();
	    	controller.setUser(user);
	    	controller.setParent(this);
		    Scene scene = new Scene(changedata);
		    stage.initModality(Modality.WINDOW_MODAL);
		    stage.initOwner(myStage);
		    scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		    stage.setScene(scene);
		    stage.setResizable(false);
		    stage.show();
		} catch(Exception e) {
			Main.logger.log(Level.SEVERE, e.toString());
		}
    }

    @FXML
    void doChangePassword(ActionEvent event) {
    	try {
    		Stage stage = new Stage();
			stage.setTitle("Kino");
		    FXMLLoader fxmlLoader = new FXMLLoader();
			fxmlLoader.setResources(ResourceBundle.getBundle("application.bundles.lang", locale));
			FlowPane changelogin = fxmlLoader.load(getClass().getResource("ChangeLogin.fxml").openStream());
			ChangeLoginController controller = (ChangeLoginController) fxmlLoader.getController();
			controller.setUser(user);
			controller.setParent(this);
		    Scene scene = new Scene(changelogin);
		    stage.initModality(Modality.WINDOW_MODAL);
		    stage.initOwner(myStage);
		    scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		    stage.setScene(scene);
		    stage.setResizable(false);
		    stage.show();
		} catch(Exception e) {
			Main.logger.log(Level.SEVERE, e.toString());
		}
    }

	@Override
	public void initialize(URL location, ResourceBundle resources) {
	}
	
	public void setUser(User user){
		this.user = user;
		this.user.guest = false;
		textUserName.setText("" + this.user.imie + " " + this.user.nazwisko);
	}
	
	public void setGuest(){
		user = new User("guest","guest");
		if (locale.getDisplayLanguage()=="polski") textUserName.setText("goœæ");
		else textUserName.setText("guest");
		user.guest = true;
		buttonAccount.setDisable(true);
		buttonShipping.setDisable(true);
		buttonTicketsCurrent.setDisable(true);
		buttonTicketsArch.setDisable(true);
	}
	
	public void setStage(Stage myStage){
		this.myStage = myStage;
	}
	
	public void doCinemaPaneReperuar(int id){
		try {
			Stage stage = new Stage();
			stage.setTitle("Repertuar");
		    FXMLLoader fxmlLoader = new FXMLLoader();
		    fxmlLoader.setResources(ResourceBundle.getBundle("application.bundles.lang", locale));
	    	BorderPane repertuar = fxmlLoader.load(getClass().getResource("Repertuar.fxml").openStream());
	    	RepertuarController controller = (RepertuarController) fxmlLoader.getController();
	    	controller.setLocale(locale);
	    	controller.setCinema(user, id);
	    	controller.setStage(stage);
		    Scene scene = new Scene(repertuar);
		    scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		    stage.setScene(scene);
		    stage.initModality(Modality.WINDOW_MODAL);
		    stage.initOwner(myStage);
		    stage.initStyle(StageStyle.UNDECORATED);
		    stage.setResizable(false);
		    stage.show();
		} catch(Exception e) {
			Main.logger.log(Level.SEVERE, e.toString());
		}
	}
	
	public String getLogin()
	{
		return this.user.mail;
	}
	
	public void setLocale(Locale locale){
		this.locale = locale;
	}
	
	public void logoutfire(){
		buttonLogout.fire();
	}

}
