package application;

import classes.Smjestaj;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import services.DataBaseService;

import java.net.URL;
import java.util.Iterator;
import java.util.ResourceBundle;

public class MojePonudeController implements Initializable {
    @FXML
    private TableView<Smjestaj> smjestajiTabela;
    @FXML
    private TableColumn<Smjestaj, String> naziv_col;
    @FXML
    private TableColumn<Smjestaj, String> drzava_col;
    @FXML
    private TableColumn<Smjestaj, String> grad_col;
    @FXML
    private TableColumn<Smjestaj, String> lokacija_col;
    @FXML
    private TableColumn<Smjestaj, String> kapacitet_col;
    @FXML
    private TableColumn<Smjestaj, String> cijena_col;
    @FXML
    private Button ukloni;
    @FXML
    private ImageView slika1;
    @FXML
    private Label naziv1;
    @FXML
    private Label opis1;
    @FXML
    private Label tip1;
    @FXML
    private Label cijena1;
    @FXML
    private Label brojKreveta1;
    @FXML
    private Label povrsina1;
    @FXML
    private CheckBox wifi1;
    @FXML
    private CheckBox parking1;
    @FXML
    private CheckBox kuhinja1;
    @FXML
    private CheckBox klima1;
    @FXML
    private CheckBox balkon1;
    @FXML
    private CheckBox bazen1;
    @FXML
    private CheckBox kupaonica1;
    @FXML
    private CheckBox tv1;
    private ObservableList<Smjestaj> list = FXCollections.observableArrayList();

    public static int currentId;

    public void ukloniOnAction(ActionEvent e) {
        if (smjestajiTabela.getSelectionModel().getSelectedItem() != null) {
            Smjestaj smjestaj = smjestajiTabela.getSelectionModel().getSelectedItem();
            int index = smjestajiTabela.getSelectionModel().getSelectedIndex();
            boolean success = DataBaseService.deleteSmjestaj(smjestaj.getId());
            while (!success) {
                success = DataBaseService.deleteSmjestaj(smjestaj.getId());
            }
            list.remove(index);
        }
    }

    public void wifi1OnAction(ActionEvent e) {

    }

    public void parking1OnAction(ActionEvent e) {

    }

    public void bazen1OnAction(ActionEvent e) {

    }

    public void klima1OnAction(ActionEvent e) {

    }

    public void kuhinja1OnAction(ActionEvent e) {

    }

    public void balkon1OnAction(ActionEvent e) {

    }

    public void tv1OnAction(ActionEvent e) {

    }

    public void kupaonica1OnAction(ActionEvent e) {

    }

    public void osvjeziPrikazSmjestaj(MouseEvent e) {
        if (smjestajiTabela.getSelectionModel().getSelectedItem() != null) {
            Smjestaj smjestaj = smjestajiTabela.getSelectionModel().getSelectedItem();
            naziv1.setText(smjestaj.getNaziv());
            tip1.setText(smjestaj.getTip());
            opis1.setText(smjestaj.getOpis());
            cijena1.setText(smjestaj.getCijena().toString());
            brojKreveta1.setText(smjestaj.getBrojKreveta().toString());
            povrsina1.setText(smjestaj.getPovrsina().toString());
            wifi1.setSelected(smjestaj.isWifi());
            parking1.setSelected(smjestaj.isParking());
            kuhinja1.setSelected(smjestaj.isKuhinja());
            kupaonica1.setSelected(smjestaj.isKupaonica());
            klima1.setSelected(smjestaj.isKlima());
            bazen1.setSelected(smjestaj.isBazen());
            balkon1.setSelected(smjestaj.isBalkon());
            tv1.setSelected(smjestaj.isTv());
        }
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        naziv_col.setCellValueFactory(new PropertyValueFactory<Smjestaj, String>("naziv"));
        lokacija_col.setCellValueFactory(new PropertyValueFactory<Smjestaj, String>("adresa"));
        drzava_col.setCellValueFactory(new PropertyValueFactory<Smjestaj, String>("drzava"));
        grad_col.setCellValueFactory(new PropertyValueFactory<Smjestaj, String>("grad"));
        kapacitet_col.setCellValueFactory(new PropertyValueFactory<Smjestaj, String>("brojKreveta"));
        cijena_col.setCellValueFactory(new PropertyValueFactory<Smjestaj, String>("cijena"));
        //

        Iterator<Smjestaj> it = DataBaseService.getSmjestajByKorisnik(currentId).iterator();
        while (it.hasNext()) {
            list.add(it.next());
        }

        smjestajiTabela.setItems(list);
    }
}
