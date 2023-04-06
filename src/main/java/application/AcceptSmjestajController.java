package application;

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
import services.DataBaseService;

import java.net.URL;
import java.util.Iterator;
import java.util.ResourceBundle;

public class AcceptSmjestajController implements Initializable {
    @FXML
    private TableView<Smjestaj> smjestajiTabela;
    @FXML
    private TableColumn<Smjestaj, String> naziv_col;
    @FXML
    private TableColumn<Smjestaj, String> tip_col;
    @FXML
    private TableColumn<Smjestaj, String> drzava_col;
    @FXML
    private TableColumn<Smjestaj, String> grad_col;
    @FXML
    private TableColumn<Smjestaj, String> adresa_col;
    @FXML
    private TableColumn<Smjestaj, String> kapacitet_col;
    @FXML
    private TableColumn<Smjestaj, String> cijena_col;
    @FXML
    private Button prihvatiButton;
    @FXML
    private Button prihvatiSveButton;
    @FXML
    private Button odbijButton;
    @FXML
    private Button odbijSveButton;

    private ObservableList<Smjestaj> list = FXCollections.observableArrayList();

    public void prihvatiButtonOnAction(ActionEvent e) {
        if (smjestajiTabela.getSelectionModel().getSelectedItem() != null) {
            Smjestaj smjestaj = smjestajiTabela.getSelectionModel().getSelectedItem();
            int index = smjestajiTabela.getSelectionModel().getSelectedIndex();
            smjestaj.setOdobren(true);
            System.out.println(smjestaj.getId());
            boolean success = DataBaseService.updateSmjestaj(smjestaj, smjestaj.getId());
            while (!success) {
                success = DataBaseService.updateSmjestaj(smjestaj, smjestaj.getId());
            }
            list.remove(index);
        }
    }

    public void prihvatiSveButtonOnAction(ActionEvent e) {
        Iterator<Smjestaj> it = list.iterator();
        while (it.hasNext()) {
            Smjestaj smjestaj = it.next();
            smjestaj.setOdobren(true);
            boolean success = DataBaseService.updateSmjestaj(smjestaj, smjestaj.getId());
            while (!success) {
                success = DataBaseService.updateSmjestaj(smjestaj, smjestaj.getId());
            }
            it.remove();
        }
    }

    public void odbijButtonOnAction(ActionEvent e) {
        if (!smjestajiTabela.getSelectionModel().getSelectedItem().equals(null)) {
            Smjestaj smjestaj = smjestajiTabela.getSelectionModel().getSelectedItem();
            int index = smjestajiTabela.getSelectionModel().getSelectedIndex();
            boolean success = DataBaseService.deleteSmjestaj(smjestaj.getId());
            while (!success) {
                success = DataBaseService.deleteSmjestaj(smjestaj.getId());
            }
            list.remove(index);
        }
    }

    public void odbijSveButtonOnAction(ActionEvent e) {
        Iterator<Smjestaj> it = list.iterator();
        while (it.hasNext()) {
            Smjestaj smjestaj = it.next();
            boolean success = DataBaseService.deleteSmjestaj(smjestaj.getId());
            while (!success) {
                success = DataBaseService.deleteSmjestaj(smjestaj.getId());
            }
            it.remove();
        }
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        naziv_col.setCellValueFactory(new PropertyValueFactory<Smjestaj, String>("naziv"));
        tip_col.setCellValueFactory(new PropertyValueFactory<Smjestaj, String>("tip"));
        drzava_col.setCellValueFactory(new PropertyValueFactory<Smjestaj, String>("drzava"));
        grad_col.setCellValueFactory(new PropertyValueFactory<Smjestaj, String>("grad"));
        adresa_col.setCellValueFactory(new PropertyValueFactory<Smjestaj, String>("adresa"));
        kapacitet_col.setCellValueFactory(new PropertyValueFactory<Smjestaj, String>("brojKreveta"));
        cijena_col.setCellValueFactory(new PropertyValueFactory<Smjestaj, String>("cijena"));
        //

        Iterator<Smjestaj> it = DataBaseService.getNotApprovedSmjestaj().iterator();
        while (it.hasNext()) {
            list.add(it.next());
        }

        //POPULACIJA ACCEPT_USER TABELE NEREGISTROVANIH KORISNIKA ODGOVARAJUCIM PODACIMA
        smjestajiTabela.setItems(list);
    }
}
