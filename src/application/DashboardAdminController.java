package application;

import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Level;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class DashboardAdminController {

    @FXML
    private Button buttonLogout;

    @FXML
    private Pane paneContent;
    
    @FXML
    private Button buttonBiletCurrent;

    @FXML
    private Button buttonBiletHistory;

    @FXML
    private Button buttonHistory;

    @FXML
    private Button buttonHelp;

    @FXML
    private Button buttonSeanse;

    @FXML
    private Text textUserName;

    @FXML
    private Button buttonFilms;
    
    private User user;
    private Stage myStage;
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
    void doSeanse(ActionEvent event) {
    	try {
	    	paneContent.getChildren().clear();
	    	FXMLLoader fxmlLoader = new FXMLLoader();
	    	fxmlLoader.setResources(ResourceBundle.getBundle("application.bundles.lang", locale));
	    	Pane seanse = fxmlLoader.load(getClass().getResource("SeansListaPane.fxml").openStream());
	    	paneContent.getChildren().add(seanse);
	    	SeansListaPaneController controller = (SeansListaPaneController) fxmlLoader.getController();
	    	controller.setDetails(user);
	    	controller.setParent(this);
    	} catch (Exception e) {
    		Main.logger.log(Level.SEVERE, e.toString());
    	}
    }
    
    @FXML
    void doHistory(ActionEvent event) {
    	try {
	    	paneContent.getChildren().clear();
	    	FXMLLoader fxmlLoader = new FXMLLoader();
	    	fxmlLoader.setResources(ResourceBundle.getBundle("application.bundles.lang", locale));
	    	Pane seanse = fxmlLoader.load(getClass().getResource("SeansHisListaPane.fxml").openStream());
	    	paneContent.getChildren().add(seanse);
	    	SeansHisListaPaneController controller = (SeansHisListaPaneController) fxmlLoader.getController();
	    	controller.setDetails(user);
	    	controller.setParent(this);
    	} catch (Exception e) {
    		Main.logger.log(Level.SEVERE, e.toString());
    	}
    }

    @FXML
    void doFilms(ActionEvent event) {
    	try {
	    	paneContent.getChildren().clear();
	    	FXMLLoader fxmlLoader = new FXMLLoader();
	    	fxmlLoader.setResources(ResourceBundle.getBundle("application.bundles.lang", locale));
	    	Pane filmy = fxmlLoader.load(getClass().getResource("FilmListaPane.fxml").openStream());
	    	paneContent.getChildren().add(filmy);
	    	FilmListaPaneController controller = (FilmListaPaneController) fxmlLoader.getController();
	    	controller.setDetails(user);
	    	controller.setParent(this);
    	} catch (Exception e) {
    		Main.logger.log(Level.SEVERE, e.toString());
    	}
    }
    
    
    @FXML
    void doBiletCurrent(ActionEvent event) {
    	try {
	    	paneContent.getChildren().clear();
	    	FXMLLoader fxmlLoader = new FXMLLoader();
	    	fxmlLoader.setResources(ResourceBundle.getBundle("application.bundles.lang", locale));
	    	Pane bilety = fxmlLoader.load(getClass().getResource("BiletAdminCurrentPane.fxml").openStream());
	    	paneContent.getChildren().add(bilety);
	    	BiletAdminCurrentPaneController controller = (BiletAdminCurrentPaneController) fxmlLoader.getController();
	    	controller.setDetails(user);
	    	controller.setParent(this);
    	} catch (Exception e) {
    		Main.logger.log(Level.SEVERE, e.toString());
    	}
    }

    @FXML
    void doBiletHistory(ActionEvent event) {
    	try {
	    	paneContent.getChildren().clear();
	    	FXMLLoader fxmlLoader = new FXMLLoader();
	    	fxmlLoader.setResources(ResourceBundle.getBundle("application.bundles.lang", locale));
	    	Pane bilety = fxmlLoader.load(getClass().getResource("BiletAdminHistoryPane.fxml").openStream());
	    	paneContent.getChildren().add(bilety);
	    	BiletAdminHistoryPaneController controller = (BiletAdminHistoryPaneController) fxmlLoader.getController();
	    	controller.setDetails(user);
	    	controller.setParent(this);
    	} catch (Exception e) {
    		Main.logger.log(Level.SEVERE, e.toString());
    	}
    }
    
    public void setUser(User user){
		this.user = user;
		this.user.guest = false;
	}
    
    public void setStage(Stage myStage){
		this.myStage = myStage;
	}
    
    public void doSeansListaPaneEdit(int selected){
    	try {
			Stage stage = new Stage();
			stage.setTitle("Edytuj seans");
		    FXMLLoader fxmlLoader = new FXMLLoader();
		    fxmlLoader.setResources(ResourceBundle.getBundle("application.bundles.lang", locale));
	    	BorderPane seans = fxmlLoader.load(getClass().getResource("EditSeans.fxml").openStream());
	    	EditSeansController controller = (EditSeansController) fxmlLoader.getController();
	    	controller.setDetails(user, selected);
		    Scene scene = new Scene(seans);
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
    
    public void doFilmListaPaneEdit(int selected){
    	try {
			Stage stage = new Stage();
			stage.setTitle("Edytuj film");
		    FXMLLoader fxmlLoader = new FXMLLoader();
		    fxmlLoader.setResources(ResourceBundle.getBundle("application.bundles.lang", locale));
	    	BorderPane film = fxmlLoader.load(getClass().getResource("EditFilm.fxml").openStream());
	    	EditFilmController controller = (EditFilmController) fxmlLoader.getController();
	    	controller.setDetails(user, selected);
		    Scene scene = new Scene(film);
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
    
    public void doBiletListaPaneEdit(int selected){
    	try {
			Stage stage = new Stage();
			stage.setTitle("Informacje o kliencie");
		    FXMLLoader fxmlLoader = new FXMLLoader();
		    fxmlLoader.setResources(ResourceBundle.getBundle("application.bundles.lang", locale));
	    	BorderPane klient = fxmlLoader.load(getClass().getResource("UserInfo.fxml").openStream());
	    	UserInfoController controller = (UserInfoController) fxmlLoader.getController();
	    	controller.setDetails(user, selected);
		    Scene scene = new Scene(klient);
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
    
    public void setLocale(Locale locale){
		this.locale = locale;
	}

}
