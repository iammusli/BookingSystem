package application;

import classes.Korisnik;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import services.DataBaseService;

import java.net.URL;
import java.util.ResourceBundle;

public class WalletController implements Initializable {
    @FXML
    private Button uplatiButton;
    @FXML
    private Label brojKartice;
    @FXML
    private Label ukupnoNovca;
    @FXML
    private TextField iznos;

    public static int currentId;
    Korisnik user;

    public void uplatiButtonOnAction(ActionEvent e) {
        try {
            if(!brojKartice.getText().isEmpty() && brojKartice.getText().length() == 16){
                Double amount = Double.parseDouble(iznos.getText());
                Double balanceOld = user.getBalance();
                Double balanceNew = balanceOld += amount;
                user.setBalance(balanceNew);
            }
        } catch (NumberFormatException e1) {
            e1.printStackTrace();
        }
        refresh();
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        user = DataBaseService.getUser(currentId);

        brojKartice.setText(user.getCardnumber());
        Double balance = (Double) user.getBalance();
        ukupnoNovca.setText(balance.toString());
    }

    private void refresh() {
        user = DataBaseService.getUser(currentId);

        brojKartice.setText(user.getCardnumber());
        Double balance = (Double) user.getBalance();
        ukupnoNovca.setText(balance.toString());
    }
}
