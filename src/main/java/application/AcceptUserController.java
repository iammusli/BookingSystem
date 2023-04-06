package application;

import classes.Korisnik;
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

public class AcceptUserController implements Initializable {
    @FXML
    private TableView<Korisnik> usersTable;
    @FXML
    private TableColumn<Korisnik, String> email_col;
    @FXML
    private TableColumn<Korisnik, String> name_col;
    @FXML
    private TableColumn<Korisnik, String> last_name_col;
    @FXML
    private TableColumn<Korisnik, String> username_col;
    @FXML
    private TableColumn<Korisnik, String> country_col;
    @FXML
    private TableColumn<Korisnik, String> city_col;
    @FXML
    private TableColumn<Korisnik, String> phon_num_col;
    @FXML
    private Button prihvatiButton;
    @FXML
    private Button prihvatiSveButton;
    @FXML
    private Button odbijButton;
    @FXML
    private Button odbijSveButton;

    private ObservableList<Korisnik> list = FXCollections.observableArrayList();


    public void prihvatiButtonOnAction(ActionEvent e) {
        if (usersTable.getSelectionModel().getSelectedItem() != null) {
            Korisnik user = usersTable.getSelectionModel().getSelectedItem();
            int index = usersTable.getSelectionModel().getSelectedIndex();
            user.setApproved(true);
            boolean success = DataBaseService.updateUser(user);
            while (!success) {
                success = DataBaseService.updateUser(user);
            }
            list.remove(index);
        }
    }

    public void prihvatiSveButtonOnAction(ActionEvent e) {
        Iterator<Korisnik> it = list.iterator();
        while (it.hasNext()) {
            Korisnik user = it.next();
            user.setApproved(true);
            boolean success = DataBaseService.updateUser(user);
            while (!success) {
                success = DataBaseService.updateUser(user);
            }
            it.remove();
        }
    }

    public void odbijButtonOnAction(ActionEvent e) {
        if (!usersTable.getSelectionModel().getSelectedItem().equals(null)) {
            Korisnik user = usersTable.getSelectionModel().getSelectedItem();
            int index = usersTable.getSelectionModel().getSelectedIndex();
            boolean success = DataBaseService.deleteUser(user.getId());
            while (!success) {
                success = DataBaseService.deleteUser(user.getId());
            }
            list.remove(index);
        }
    }

    public void odbijSveButtonOnAction(ActionEvent e) {
        Iterator<Korisnik> it = list.iterator();
        while (it.hasNext()) {
            Korisnik user = it.next();
            boolean success = DataBaseService.deleteUser(user.getId());
            while (!success) {
                success = DataBaseService.deleteUser(user.getId());
            }
            it.remove();
        }
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        email_col.setCellValueFactory(new PropertyValueFactory<Korisnik, String>("email"));
        name_col.setCellValueFactory(new PropertyValueFactory<Korisnik, String>("firstname"));
        last_name_col.setCellValueFactory(new PropertyValueFactory<Korisnik, String>("lastname"));
        username_col.setCellValueFactory(new PropertyValueFactory<Korisnik, String>("username"));
        country_col.setCellValueFactory(new PropertyValueFactory<Korisnik, String>("country"));
        city_col.setCellValueFactory(new PropertyValueFactory<Korisnik, String>("city"));
        phon_num_col.setCellValueFactory(new PropertyValueFactory<Korisnik, String>("phone"));
        //

        Iterator<Korisnik> it = DataBaseService.getNotApprovedUsers().iterator();
        while (it.hasNext()) {
            list.add(it.next());
        }

        //POPULACIJA ACCEPT_USER TABELE NEREGISTROVANIH KORISNIKA ODGOVARAJUCIM PODACIMA
        usersTable.setItems(list);
    }
}
