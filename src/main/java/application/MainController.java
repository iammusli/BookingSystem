package application;

import classes.Korisnik;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import services.DataBaseService;

import java.awt.event.KeyListener;
import java.net.URL;
import java.util.ResourceBundle;


public class MainController {

    @FXML
    private Button prijavaButton;
    @FXML
    private Button registerButton;
    @FXML
    private Button pretragaButton;
    @FXML
    public TextField email;
    @FXML
    private TextField password;
    @FXML
    private Label usernameMessage;
    @FXML
    private Label passwordMessage;

    public static int currentId = -1;
    Korisnik user;

    public void prijavaButtonOnAction(ActionEvent e) {
        try {
            if (email.getText().equals("Admin") && password.getText().equals("superuser")) {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/AdminPage.fxml"));
                Parent root = (Parent) fxmlLoader.load();
                Stage stageOld = (Stage) prijavaButton.getScene().getWindow();
                Stage stage = new Stage();
                stage.setTitle("Admin profil");
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stageOld.close();
                stage.show();
            } else {
                int result = DataBaseService.validateLogin(email.getText(), password.getText());
                //
                System.out.println(result);
                //
                switch (result) {
                    case DataBaseService.SUCCESS: {
                        user = DataBaseService.getUser(MainController.currentId);
                        if (user.getType() == Korisnik.admin) {
                            AdminPageController.currentId = MainController.currentId;
                            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/AdminPage.fxml"));
                            Parent root = (Parent) fxmlLoader.load();
                            Stage stageOld = (Stage) prijavaButton.getScene().getWindow();
                            Stage stage = new Stage();
                            stage.setTitle("Admin profil");
                            Scene scene = new Scene(root);
                            stage.setScene(scene);
                            stageOld.close();
                            stage.show();
                        } else if (user.getType() == Korisnik.korisnik || user.getType() == Korisnik.iznajmljivac) {
                            UserPageController.currentId = MainController.currentId;
                            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/UserPage.fxml"));
                            Parent root = (Parent) fxmlLoader.load();
                            Stage stageOld = (Stage) prijavaButton.getScene().getWindow();
                            Stage stage = new Stage();
                            stage.setTitle("Korisnicki profil");
                            Scene scene = new Scene(root);
                            stage.setScene(scene);
                            stageOld.close();
                            stage.show();
                        }
                        break;
                    }
                    case DataBaseService.USER_NOT_FOUND: {
                        // KORISNIK NIJE REGISTROVAN(OBAVIJESTI GA)
                        usernameMessage.setText("Greska: email/username nije registrovan!");
                        break;
                    }
                    case DataBaseService.USER_NOT_APPROVED: {
                        // KORISNIK NIJE ODOBREN(OBAVIJESTI GA)
                        usernameMessage.setText("Greska: email/username nije odobren!");
                        break;
                    }
                    case DataBaseService.INVALID_PASSWORD: {
                        // KORISNIK JE UNIO POGRESAN PASSWORD(OBAVIJESTI GA)
                        passwordMessage.setText("Greska: neispravan password!");
                        break;
                    }
                    case DataBaseService.RETRY: {
                        // GRESKA PRILIKOM VALIDACIJE(POKUSAJ PONOVO)
                        usernameMessage.setText("Greska prilikom validacije, pokusajte ponovo!");
                        break;
                    }
                }

            }
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }

    public void pretragaButtonOnAction(ActionEvent e) {
        try {
            ListingController.currentId = MainController.currentId;
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

    public void registerButtonOnAction(ActionEvent e) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/Registration.fxml"));
            Parent root = (Parent) fxmlLoader.load();
            Stage stageOld = (Stage) registerButton.getScene().getWindow();
            Stage stage = new Stage();
            stage.setTitle("Registracija");
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stageOld.close();
            stage.show();
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }


    public void emailOnAction(ActionEvent e) {
    }

    public void passwordOnAction() {

    }

}
