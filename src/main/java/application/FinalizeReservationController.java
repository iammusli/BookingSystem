package application;

import classes.Korisnik;
import classes.Rezervacija;
import classes.Smjestaj;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import services.DataBaseService;

import java.net.URL;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class FinalizeReservationController implements Initializable {
    @FXML
    private ImageView slika;
    @FXML
    private TableView<Rezervacija> tabelaRezervacija;
    @FXML
    private TableColumn<Rezervacija, Timestamp> rezOdKolona;
    @FXML
    private TableColumn<Rezervacija, Timestamp> rezDoKolona;
    @FXML
    private Label opis;
    @FXML
    private Label naziv;
    @FXML
    private Label cijena;
    @FXML
    private Label dani;
    @FXML
    private Label ukupnaCijena;
    @FXML
    private TextField odRes;
    @FXML
    private TextField doRes;
    @FXML
    private Button potvrda;
    @FXML
    private Button cancel;
    @FXML
    private Label errorMessage;
    public static Rezervacija rezervacija = new Rezervacija();
    private Double cijenaBoravka;

    public void provjeriDatume(KeyEvent e) {
        String pocetak = odRes.getText();
        String kraj = doRes.getText();
        if (pocetak.equals("") || kraj.equals("")) {
            errorMessage.setText("datum prazan!");
            return;
        }

        Timestamp tp, tk;
        try {
            tp = Timestamp.valueOf(pocetak + " 11:00:00");
            tk = Timestamp.valueOf(kraj + " 10:00:00");
        } catch (IllegalArgumentException ex) {
            errorMessage.setText("neispravan datum!");
            return;
        }

        Timestamp sad = Timestamp.valueOf(LocalDateTime.now());

        if(tp.getTime() - sad.getTime() < 0){
            errorMessage.setText("datum pocetka prosao!");
            return;
        }
        long period = tk.getTime() - tp.getTime();
        if (period < 0) {
            errorMessage.setText("obrnuti datume!");
            return;
        }

        Long brojDana = period / (1000 * 60 * 60 * 24);
        dani.setText(brojDana.toString());
        cijenaBoravka = brojDana * Double.parseDouble(cijena.getText());
        ukupnaCijena.setText(cijenaBoravka.toString());
        errorMessage.setText("");
    }

    public void potvrdaOnAction(ActionEvent e) {
        String pocetak = odRes.getText();
        String kraj = doRes.getText();
        if (pocetak.equals("") || kraj.equals("")) {
            errorMessage.setText("datum prazan!");
            return;
        }

        Timestamp tp, tk;
        try {
            tp = Timestamp.valueOf(pocetak + " 00:00:00");
            tk = Timestamp.valueOf(kraj + " 00:00:00");
        } catch (IllegalArgumentException ex) {
            errorMessage.setText("neispravan datum!");
            return;
        }

        rezervacija.setCheckin(tp);
        rezervacija.setCheckout(tk);
        rezervacija.setPrice(cijenaBoravka);
        boolean uspjesno = DataBaseService.rezervisiSmjestaj(rezervacija);
        if (!uspjesno) {
            errorMessage.setText("smjestaj nedostupan!");
            return;
        }

        ((Stage) (potvrda.getScene().getWindow())).close();
    }

    public void cancelOnAction(ActionEvent e) {
        ((Stage) (cancel.getScene().getWindow())).close();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Smjestaj smjestaj = DataBaseService.getSmjestaj(rezervacija.getSmjestajId());
        ObservableList<Rezervacija> rezervacijeSve = FXCollections.observableArrayList();
        rezOdKolona.setCellValueFactory(new PropertyValueFactory<Rezervacija,Timestamp>("checkin"));
        rezDoKolona.setCellValueFactory(new PropertyValueFactory<Rezervacija,Timestamp>("checkout"));

        Iterator<Rezervacija> it = DataBaseService.getRezervacijeBySmjestaj(smjestaj.getId()).iterator();
        while (it.hasNext()) {
            rezervacijeSve.add(it.next());
        }
        tabelaRezervacija.setItems(rezervacijeSve);

        naziv.setText(smjestaj.getNaziv());
        cijena.setText(smjestaj.getCijena().toString());
        opis.setText(smjestaj.getOpis());
    }
}
