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
import javafx.stage.Stage;
import services.DataBaseService;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class UserPageController implements Initializable {
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
    private Label brojKarticeUser;
    @FXML
    private Button editProfileButton;
    @FXML
    private Button logOut;
    @FXML
    private Button pretraga;
    @FXML
    private Button ponudaSmjestaja;
    @FXML
    private Button mojSmjestaj;
    @FXML
    private Button mojeRezervacije;
    @FXML
    private Button wallet;

    public static int currentId = -1;
    Korisnik user;


    public void logOutOnAction(ActionEvent e) {
        try {
            UserPageController.currentId = -1;
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

    public void pretragaOnAction(ActionEvent e) {
        try {
            ListingController.currentId = currentId;
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/Listing.fxml"));
            Parent root = (Parent) fxmlLoader.load();
            //Stage stageOld = (Stage) odobriSmjestajButton.getScene().getWindow();
            Stage stage = new Stage();
            stage.setTitle("Pretraga smjestaja");
            Scene scene = new Scene(root);
            stage.setScene(scene);
            //stageOld.close();
            stage.show();
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }

    public void ponudaSmjestajaOnAction(ActionEvent e) {
        try {
            OfferController.currentId = UserPageController.currentId;
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/CreateOffer.fxml"));
            Parent root = (Parent) fxmlLoader.load();
            //Stage stageOld = (Stage) prijavaButton.getScene().getWindow();
            Stage stage = new Stage();
            stage.setTitle("Nova ponuda smjestaja");
            Scene scene = new Scene(root);
            stage.setScene(scene);
            //stageOld.close();
            stage.show();

        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }

    public void mojSmjestajOnAction(ActionEvent e) {
        try {
            MojePonudeController.currentId = currentId;
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/MojePonude.fxml"));
            Parent root = (Parent) fxmlLoader.load();
            //Stage stageOld = (Stage) odobriSmjestajButton.getScene().getWindow();
            Stage stage = new Stage();
            stage.setTitle("Moje ponude");
            Scene scene = new Scene(root);
            stage.setScene(scene);
            //stageOld.close();
            stage.show();
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }

    public void walletOnAction(ActionEvent e) {
        try {
            WalletController.currentId = UserPageController.currentId;
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/wallet.fxml"));
            Parent root = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("Novcanik");
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }

    public void mojeRezervacijeOnAction(ActionEvent e) {
        try {
            RezervacijeController.currentId = currentId;
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/Rezervacije.fxml"));
            Parent root = (Parent) fxmlLoader.load();
            //Stage stageOld = (Stage) odobriSmjestajButton.getScene().getWindow();
            Stage stage = new Stage();
            stage.setTitle("Moje rezervacije");
            Scene scene = new Scene(root);
            stage.setScene(scene);
            //stageOld.close();
            stage.show();
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }

    public void editProfileButtonOnAction(ActionEvent e) {
        ProfileEditController.currentId = UserPageController.currentId;
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
        if(user.getPhone() != null && !user.getPhone().isEmpty() && !(user.getPhone().length() < 9)){
            String lead = user.getPhone().substring(0,3) + "/";
            String mid = user.getPhone().substring(3,6) + "-";
            String rest = user.getPhone().substring(6);
            brojTelUser.setText(lead + mid + rest);
        }
        if(user.getCardnumber() != null && !user.getCardnumber().isEmpty() && !(user.getCardnumber().length() < 16)) {
            String lead = user.getCardnumber().substring(0, 4) + " ";
            String mid = user.getCardnumber().substring(4, 8) + " ";
            String rest = user.getCardnumber().substring(8, 12) + " ";
            String rest2 = user.getCardnumber().substring(12);
            brojKarticeUser.setText(lead + mid + rest + rest2);
        }
        imeUser.setText(user.getFirstname());
        prezimeUser.setText(user.getLastname());
        drzavaUser.setText(user.getCountry());
        gradUser.setText(user.getCity());

        if (user.getType() == Korisnik.korisnik) {
            ponudaSmjestaja.setDisable(true);
            mojSmjestaj.setDisable(true);
        }
    }
}
