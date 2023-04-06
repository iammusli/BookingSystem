package application;

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
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import services.DataBaseService;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;
import java.util.regex.Pattern;

public class RegistrationController implements Initializable {
    @FXML
    private TextField tipUser;
    @FXML
    private TextField mailUser;
    @FXML
    private TextField imeUser;
    @FXML
    private TextField prezimeUser;
    @FXML
    private TextField userName;
    @FXML
    private TextField userPassword;
    @FXML
    private TextField confirmPassword;
    @FXML
    private TextField drzavaUser;
    @FXML
    private TextField gradUser;
    @FXML
    private TextField telefonUser;
    @FXML
    private Label messageUser;
    @FXML
    private Button registrationButton;
    @FXML
    private Button cancelButton;


    public void registrationButtonOnAction(ActionEvent e) {
        if (!(tipUser.getText().isEmpty()
                && mailUser.getText().isEmpty()
                && userPassword.getText().isEmpty()
                && userName.getText().isEmpty())) {

            if (DataBaseService.checkAvailabilityEmail(mailUser.getText()) == DataBaseService.EMAIL_DUPLICATE) {
                // STA AKO JE EMAIL VEC REGISTROVAN
                messageUser.setText("Registracija neuspjesna: unesena email adresa je nedostupna!");
                System.out.println("Email dupl");
            } else if (DataBaseService.checkAvailabilityUsername(userName.getText()) == DataBaseService.USERNAME_DUPLICATE) {
                // STA AKO JE USERNAME VEC REGISTROVAN
                messageUser.setText("Registracija neuspjesna: uneseni username je nedostupan!");
                System.out.println("Username dupl");
            } else if (!userPassword.getText().equals(confirmPassword.getText())) {
                // STA AKO PASSWORD NIJE POTVRDJEN ISPRAVNO
                messageUser.setText("Registracija neuspjesna: password nije ispravno potvrdjen!");
            } else if (!(tipUser.getText().toLowerCase().equals("admin")
                    || tipUser.getText().toLowerCase().equals("korisnik")
                    || tipUser.getText().toLowerCase().equals("iznajmljivac"))) {
                messageUser.setText("Registracija neuspjesna: tip korisnika nije ispravno odabran!");
            } else if(!telefonUser.getText().isEmpty() && (telefonUser.getText().length() < 9)){
                messageUser.setText("Registracija neuspjesna: uneseni broj telefona nije validan!");
            } else {
                // DOPUSTI REGISTRACIJU
                Korisnik user = new Korisnik();

                user.setUsername(userName.getText());
                user.setPassword(userPassword.getText());
                user.setEmail(mailUser.getText());
                user.setFirstname(imeUser.getText());
                user.setLastname(prezimeUser.getText());
                user.setCity(gradUser.getText());
                user.setCountry(drzavaUser.getText());
                user.setPhone(telefonUser.getText());
                if (tipUser.getText().toLowerCase().equals("admin")) {
                    user.setType(Korisnik.admin);
                } else if (tipUser.getText().toLowerCase().equals("korisnik")) {
                    user.setType(Korisnik.korisnik);
                } else {
                    user.setType(Korisnik.iznajmljivac);
                }
                // PONAVLJAJ UPIS U BAZU DOK NE PRODJE
                boolean success = DataBaseService.insertUser(user);
                while (!success) {
                    success = DataBaseService.insertUser(user);
                }

                FXMLLoader loader = new FXMLLoader(
                        getClass().getClassLoader().getResource("fxml/Login_Home.fxml"));
                Stage stage = (Stage) messageUser.getScene().getWindow();
                try {
                    stage.setScene(new Scene(loader.load()));
                    stage.setTitle("Home");
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                stage.show();
            }
        } else {
            // OBAVIJESTI KORISNIKA DA SU EMAIL/USERNAME/PASSWORD/TIP OBAVEZNI
            messageUser.setText("Registracija neuspjesna: polja oznacena sa * su obavezna!");
        }
    }

    public void cancelButtonOnAction(ActionEvent e) {
        FXMLLoader loader = new FXMLLoader(
                getClass().getClassLoader().getResource("fxml/Login_Home.fxml"));
        Stage stage = (Stage) messageUser.getScene().getWindow();
        try {
            stage.setScene(new Scene(loader.load()));
            stage.setTitle("Home");
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        stage.show();
    }


    public void tipUserOnAction(ActionEvent e) {

    }

    public void imeUserOnAction(ActionEvent e) {

    }

    public void prezimeUserOnAction(ActionEvent e) {

    }

    public void userNameOnAction(ActionEvent e) {

    }

    public void mailUserOnAction(ActionEvent e) {

    }

    public void userPasswordOnAction(ActionEvent e) {

    }

    public void confirmPasswordOnAction(ActionEvent e) {

    }

    public void gradUserOnAction(ActionEvent e) {

    }

    public void drzavaUserOnAction(ActionEvent e) {

    }

    public void rodenjeUserOnAction(ActionEvent e) {

    }

    public void telefonUserOnAction(ActionEvent e) {

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
    public void initialize(URL url, ResourceBundle resourceBundle) {
        TextFormatter<String> textFormatter1 = new TextFormatter<String>(filter1);
        TextFormatter<String> textFormatter2 = new TextFormatter<String>(filter1);
        TextFormatter<String> textFormatter3 = new TextFormatter<String>(filter1);
        TextFormatter<String> textFormatter4 = new TextFormatter<String>(filter1);
        TextFormatter<String> textFormatter5 = new TextFormatter<String>(filter2);
        TextFormatter<String> textFormatter6 = new TextFormatter<String>(filter1);
        TextFormatter<String> textFormatter7 = new TextFormatter<String>(filter3);


        imeUser.setTextFormatter(textFormatter1);
        prezimeUser.setTextFormatter(textFormatter2);
        drzavaUser.setTextFormatter(textFormatter3);
        gradUser.setTextFormatter(textFormatter4);
        telefonUser.setTextFormatter(textFormatter5);
        tipUser.setTextFormatter(textFormatter6);
        userPassword.setTextFormatter(textFormatter7);
    }

    public void registrationButtonOnKeyPressed(KeyEvent keyEvent){
        if(keyEvent.getCode().equals(KeyCode.ENTER)){
            registrationButtonOnAction(null);
        }
    }
}
