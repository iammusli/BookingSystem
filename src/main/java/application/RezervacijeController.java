package application;

import classes.Rezervacija;
import classes.Smjestaj;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import services.DataBaseService;

import java.net.URL;
import java.util.Iterator;
import java.util.ResourceBundle;

public class RezervacijeController implements Initializable {
    @FXML
    private TableView<Smjestaj> smjestajiTabela;
    @FXML
    private TableColumn<Smjestaj, String> naziv_col;
    @FXML
    private TableColumn<Smjestaj, String> grad_col;
    @FXML
    private TableView<Rezervacija> rezervacijeTabela;
    @FXML
    private TableColumn<Rezervacija, String> cijena_col;
    @FXML
    private TableColumn<Rezervacija, String> checkin_col;
    @FXML
    private TableColumn<Rezervacija, String> checkout_col;
    @FXML
    private Button ukloniButton;
    @FXML
    private Button ukloniSveButton;

    private ObservableList<Rezervacija> listRezervacije = FXCollections.observableArrayList();
    private ObservableList<Smjestaj> listSmjestaja = FXCollections.observableArrayList();

    public static int currentId;

    public void ukloniButtonOnAction(ActionEvent e) {
        if (!rezervacijeTabela.getSelectionModel().getSelectedItem().equals(null)) {
            Rezervacija rezervacija = rezervacijeTabela.getSelectionModel().getSelectedItem();
            int index = rezervacijeTabela.getSelectionModel().getSelectedIndex();
            DataBaseService.deleteRezervacija(rezervacija.getId());
            listRezervacije.remove(index);
            listSmjestaja.remove(index
            );
        }
    }

    public void ukloniSveButtonOnAction(ActionEvent e) {
        Iterator<Rezervacija> it = listRezervacije.iterator();
        Iterator<Smjestaj> it1 = listSmjestaja.iterator();
        while (it.hasNext()) {
            Rezervacija r = it.next();
            it1.next();

            DataBaseService.deleteRezervacija(r.getId());

            it.remove();
            it1.remove();
        }
    }

    public void klikNaSmjestajTabelu(MouseEvent e) {
        int i = smjestajiTabela.getSelectionModel().getSelectedIndex();
        rezervacijeTabela.getSelectionModel().select(i);
    }

    public void klikNaRezervacijaTabelu(MouseEvent e) {
        int i = rezervacijeTabela.getSelectionModel().getSelectedIndex();
        smjestajiTabela.getSelectionModel().select(i);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        naziv_col.setCellValueFactory(new PropertyValueFactory<Smjestaj, String>("naziv"));
        grad_col.setCellValueFactory(new PropertyValueFactory<Smjestaj, String>("grad"));
        cijena_col.setCellValueFactory(new PropertyValueFactory<Rezervacija, String>("price"));
        checkin_col.setCellValueFactory(new PropertyValueFactory<Rezervacija, String>("checkin"));
        checkout_col.setCellValueFactory(new PropertyValueFactory<Rezervacija, String>("checkout"));


        Iterator<Rezervacija> it = DataBaseService.getRezervacijeByKorisnik(currentId).iterator();
        while (it.hasNext()) {
            Rezervacija r = it.next();
            listRezervacije.add(r);
            listSmjestaja.add(r.getSmjestaj_id_u_rez());
        }

        //POPULACIJA ACCEPT_USER TABELE NEREGISTROVANIH KORISNIKA ODGOVARAJUCIM PODACIMA
        smjestajiTabela.setItems(listSmjestaja);
        rezervacijeTabela.setItems(listRezervacije);
    }
}
