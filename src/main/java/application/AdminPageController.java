package application;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import classes.Korisnik;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import services.DataBaseService;

public class AdminPageController implements Initializable {
	@FXML
	private Label imeUser;
	@FXML
	private Label prezimeUser;
	@FXML
	private Label drzavaUser;
	@FXML
	private Label gradUser;
	@FXML
	private Label brojTelUser;
	@FXML
	private Button editProfileButton;
	@FXML
	private Button logOut;
	@FXML
	private Button pretraga;
	@FXML
	private Button prihvatiKorisnika;
	@FXML
	private Button prihvatiSmjestaj;
	
	public static int currentId;
	Korisnik user;
	
	public void logOutOnAction(ActionEvent e)
	{
		try {		
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/Login_Home.fxml"));
			Parent root = (Parent) fxmlLoader.load();
			Stage stageOld = (Stage) logOut.getScene().getWindow();
			Stage stage = new Stage();
			stage.setTitle("Home");
			Scene scene = new Scene(root);
			stage.setScene(scene);
			stageOld.close();
			stage.show();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}
	public void pretragaOnAction(ActionEvent e)
	{
		try {	
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/Listing.fxml"));
			Parent root = (Parent) fxmlLoader.load();
			Stage stage = new Stage();
			stage.setTitle("Pretraga smjestaja");
			Scene scene = new Scene(root);
			stage.setScene(scene);
			stage.show();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}
	public void prihvatiKorisnikaOnAction(ActionEvent e)
	{
		try {	
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/Accept_User.fxml"));
			Parent root = (Parent) fxmlLoader.load();
			Stage stage = new Stage();
			stage.setTitle("Odobrenje novih korisnika");
			Scene scene = new Scene(root);
			stage.setScene(scene);
			stage.show();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}
	public void prihvatiSmjestajOnAction(ActionEvent e)
	{
		try {	
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/accept_smjestaj.fxml"));
			Parent root = (Parent) fxmlLoader.load();
			Stage stage = new Stage();
			stage.setTitle("Odobrenje novog smjestaja");
			Scene scene = new Scene(root);
			stage.setScene(scene);
			stage.show();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}
	public void editProfileButtonOnAction(ActionEvent e) 
	{
		ProfileEditController.currentId = AdminPageController.currentId;
		FXMLLoader loader = new FXMLLoader(
				getClass().getClassLoader().getResource("fxml/ProfileEdit.fxml"));
		Stage stage = (Stage) editProfileButton.getScene().getWindow();
		try {
			stage.setScene(new Scene(loader.load()));
			stage.setTitle("Uredi profil");
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		stage.show();
	}
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		user = DataBaseService.getUser(currentId);
		if(user.getPhone() != null && user.getPhone().length() < 9){
		String lead = user.getPhone().substring(0,3) + "/";
		String mid = user.getPhone().substring(3,6) + "-";
		String rest = user.getPhone().substring(6);
		brojTelUser.setText(lead + mid + rest);
		}
		
		imeUser.setText(user.getFirstname());
		prezimeUser.setText(user.getLastname());
		drzavaUser.setText(user.getCountry());
		gradUser.setText(user.getCity());
	}

}
