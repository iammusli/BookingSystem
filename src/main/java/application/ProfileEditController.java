package application;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;
import java.util.regex.Pattern;

import classes.Korisnik;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.stage.Stage;
import services.DataBaseService;

public class ProfileEditController implements Initializable {
	
	@FXML
	private TextField imeUser;
	@FXML
	private TextField prezimeUser;
	@FXML
	private TextField drzavaUser;
	@FXML
	private TextField gradUser;
	@FXML
	private TextField brojTelUser;
	@FXML
	private Label brojKarticeLabel;
	@FXML
	private TextField brojKarticeUser;
	@FXML
	private TextField emailUser;
	@FXML
	private TextField usernameUser;
	@FXML
	private TextField passwordUser;
	@FXML
	private TextField confirmPasswordUser;
	@FXML
	private Label messageUser;
	@FXML 
	private Button saveChangesButton;
	@FXML
	private Button discardChangesButton;
	
	public static int currentId = -1;
	Korisnik user;
	
	public void saveChangesButtonOnAction(ActionEvent e)
	{
		Korisnik tmp = new Korisnik(user);
		
		
		boolean success = true;
		if(!emailUser.getText().equals(null) && !emailUser.getText().equals("")) {
			if(DataBaseService.checkAvailabilityEmail(emailUser.getText()) == DataBaseService.EMAIL_DUPLICATE) {
				messageUser.setText("Greska: email je vec u upotrebi!");
				success = false;
			} else {
				tmp.setEmail(emailUser.getText());
			}
		}
		if(!usernameUser.getText().equals(null) && !usernameUser.getText().equals("")) {
			if(DataBaseService.checkAvailabilityUsername(emailUser.getText()) == DataBaseService.USERNAME_DUPLICATE) {
				messageUser.setText("Greska: username je vec u upotrebi!");
				success = false;
			} else {
				tmp.setUsername(usernameUser.getText());
			}
		}
		if(!imeUser.getText().equals(null) && !imeUser.getText().equals("")) {
			tmp.setFirstname(imeUser.getText());
		}
		if(!prezimeUser.getText().equals(null) && !prezimeUser.getText().equals("")) {
			tmp.setLastname(prezimeUser.getText());
		}
		if(!drzavaUser.getText().equals(null) && !drzavaUser.getText().equals("")) {
			tmp.setCountry(drzavaUser.getText());
		}
		if(!gradUser.getText().equals(null) && !gradUser.getText().equals("")) {
			tmp.setCity(gradUser.getText());
		}
		if(!brojTelUser.getText().equals(null) && !brojTelUser.getText().equals("")){

			if(brojTelUser.getText().length() == 10 || brojTelUser.getText().length() == 9) {
				tmp.setPhone(brojTelUser.getText());
			} else {
				messageUser.setText("Greska: broj telefona nije ispravno unesen!");
				success = false;
			}
		}
		if(!brojKarticeUser.getText().equals(null) && !brojKarticeUser.getText().equals("")){
			if(brojKarticeUser.getText().length() == 16) {
				tmp.setCardnumber(brojKarticeUser.getText());
			} else {
				messageUser.setText("Greska: broj kartice nije ispravno unesen!");
				success = false;
			}
		}
		if(!passwordUser.getText().equals(null) && !passwordUser.getText().equals("")) {
			if(passwordUser.getText().equals(confirmPasswordUser.getText())) {
				tmp.setPassword(passwordUser.getText());
			} else {
				messageUser.setText("Greska: password nije ispravno potvrdjen!");
				success = false;
			}
		}
		if(success) {
			user.copy(tmp);
			boolean bsuccess = false;
			while(!bsuccess) {
				bsuccess = DataBaseService.updateUser(user);
			}
		
			if(user.getType() == Korisnik.admin) {
				AdminPageController.currentId = ProfileEditController.currentId;
				FXMLLoader loader = new FXMLLoader(
						getClass().getClassLoader().getResource("fxml/AdminPage.fxml"));
				Stage stage = (Stage) saveChangesButton.getScene().getWindow();
				try {
					stage.setScene(new Scene(loader.load()));
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				stage.show();
			} else {
				UserPageController.currentId = ProfileEditController.currentId;
				FXMLLoader loader = new FXMLLoader(
						getClass().getClassLoader().getResource("fxml/UserPage.fxml"));
				Stage stage = (Stage) saveChangesButton.getScene().getWindow();
				try {
					stage.setScene(new Scene(loader.load()));
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				stage.show();
			}
		}
	}
	public void discardChangesButtonOnAction(ActionEvent e) 
	{
		if(user.getType() == Korisnik.admin) {
			AdminPageController.currentId = ProfileEditController.currentId;
			FXMLLoader loader = new FXMLLoader(
					getClass().getClassLoader().getResource("fxml/AdminPage.fxml"));
			Stage stage = (Stage) discardChangesButton.getScene().getWindow();
			try {
				stage.setScene(new Scene(loader.load()));
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			stage.show();

		} else {
			UserPageController.currentId = ProfileEditController.currentId;
			FXMLLoader loader = new FXMLLoader(
					getClass().getClassLoader().getResource("fxml/UserPage.fxml"));
			Stage stage = (Stage) discardChangesButton.getScene().getWindow();
			try {
				stage.setScene(new Scene(loader.load()));
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			stage.show();
		}	
	}
	private Pattern pattern1 = Pattern.compile("[a-zA-Z ]*");
	private Pattern pattern2 = Pattern.compile("[0-9]*");
	private Pattern pattern3 = Pattern.compile("[a-zA-Z0-9]*");

	private UnaryOperator<TextFormatter.Change> filter1 = c -> {
		if(pattern1.matcher(c.getControlNewText()).matches()) {
			return c;
		} else {
			return null;
		}
	};
	private UnaryOperator<TextFormatter.Change> filter2 = c -> {
		if(pattern2.matcher(c.getControlNewText()).matches()) {
			return c;
		} else {
			return null;
		}
	};
	private UnaryOperator<TextFormatter.Change> filter3 = c -> {
		if(pattern3.matcher(c.getControlNewText()).matches()) {
			return c;
		} else {
			return null;
		}
	};

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		user = DataBaseService.getUser(currentId);
		if(user.getType() == Korisnik.admin) {
			brojKarticeLabel = new Label("");
			brojKarticeLabel.setVisible(false);
			brojKarticeUser.setDisable(true);
			brojKarticeUser.setVisible(false);
		}
		TextFormatter<String> textFormatter1 = new TextFormatter<String>(filter1);
		TextFormatter<String> textFormatter2 = new TextFormatter<String>(filter1);
		TextFormatter<String> textFormatter3 = new TextFormatter<String>(filter1);
		TextFormatter<String> textFormatter4 = new TextFormatter<String>(filter1);
		TextFormatter<String> textFormatter5 = new TextFormatter<String>(filter2);
		TextFormatter<String> textFormatter6 = new TextFormatter<String>(filter2);
		TextFormatter<String> textFormatter7 = new TextFormatter<String>(filter3);


		imeUser.setTextFormatter(textFormatter1);
		prezimeUser.setTextFormatter(textFormatter2);
		drzavaUser.setTextFormatter(textFormatter3);
		gradUser.setTextFormatter(textFormatter4);
		brojTelUser.setTextFormatter(textFormatter5);
		brojKarticeUser.setTextFormatter(textFormatter6);
		passwordUser.setTextFormatter(textFormatter7);
	}

}
