package application;

import classes.Smjestaj;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import services.DataBaseService;

import java.net.URL;
import java.util.*;

public class ListingController implements Initializable {
    @FXML
    private ImageView image1;
    @FXML
    private ImageView image2;
    @FXML
    private ImageView image3;
    @FXML
    private ImageView image4;
    @FXML
    private ImageView image5;

    @FXML
    private Label naziv1;
    @FXML
    private Label naziv2;
    @FXML
    private Label naziv3;
    @FXML
    private Label naziv4;
    @FXML
    private Label naziv5;

    @FXML
    private Label opis1;
    @FXML
    private Label opis2;
    @FXML
    private Label opis3;
    @FXML
    private Label opis4;
    @FXML
    private Label opis5;

    @FXML
    private Label tip1;
    @FXML
    private Label tip2;
    @FXML
    private Label tip3;
    @FXML
    private Label tip4;
    @FXML
    private Label tip5;

    @FXML
    private Label cijena1;
    @FXML
    private Label cijena2;
    @FXML
    private Label cijena3;
    @FXML
    private Label cijena4;
    @FXML
    private Label cijena5;

    @FXML
    private Label brojKreveta1;
    @FXML
    private Label brojKreveta2;
    @FXML
    private Label brojKreveta3;
    @FXML
    private Label brojKreveta4;
    @FXML
    private Label brojKreveta5;

    @FXML
    private Label povrsina1;
    @FXML
    private Label povrsina2;
    @FXML
    private Label povrsina3;
    @FXML
    private Label povrsina4;
    @FXML
    private Label povrsina5;

    @FXML
    private CheckBox wifi1;
    @FXML
    private CheckBox wifi2;
    @FXML
    private CheckBox wifi3;
    @FXML
    private CheckBox wifi4;
    @FXML
    private CheckBox wifi5;

    @FXML
    private CheckBox parking1;
    @FXML
    private CheckBox parking2;
    @FXML
    private CheckBox parking3;
    @FXML
    private CheckBox parking4;
    @FXML
    private CheckBox parking5;

    @FXML
    private CheckBox kuhinja1;
    @FXML
    private CheckBox kuhinja2;
    @FXML
    private CheckBox kuhinja3;
    @FXML
    private CheckBox kuhinja4;
    @FXML
    private CheckBox kuhinja5;

    @FXML
    private CheckBox klima1;
    @FXML
    private CheckBox klima2;
    @FXML
    private CheckBox klima3;
    @FXML
    private CheckBox klima4;
    @FXML
    private CheckBox klima5;

    @FXML
    private CheckBox balkon1;
    @FXML
    private CheckBox balkon2;
    @FXML
    private CheckBox balkon3;
    @FXML
    private CheckBox balkon4;
    @FXML
    private CheckBox balkon5;

    @FXML
    private CheckBox bazen1;
    @FXML
    private CheckBox bazen2;
    @FXML
    private CheckBox bazen3;
    @FXML
    private CheckBox bazen4;
    @FXML
    private CheckBox bazen5;

    @FXML
    private CheckBox kupaonica1;
    @FXML
    private CheckBox kupaonica2;
    @FXML
    private CheckBox kupaonica3;
    @FXML
    private CheckBox kupaonica4;
    @FXML
    private CheckBox kupaonica5;

    @FXML
    private CheckBox tv1;
    @FXML
    private CheckBox tv2;
    @FXML
    private CheckBox tv3;
    @FXML
    private CheckBox tv4;
    @FXML
    private CheckBox tv5;

    @FXML
    private TextField minCijena;
    @FXML
    private TextField maxCijena;
    @FXML
    private TextField minPovrsina;
    @FXML
    private TextField maxPovrsina;
    @FXML
    private CheckBox wifi;
    @FXML
    private CheckBox parking;
    @FXML
    private CheckBox kuhinja;
    @FXML
    private CheckBox klima;
    @FXML
    private CheckBox balkon;
    @FXML
    private CheckBox bazen;
    @FXML
    private CheckBox kupatilo;
    @FXML
    private CheckBox tv;
    @FXML
    private TextField lokacija;
    //    @FXML
//    private ComboBox brojKreveta;
    @FXML
    private Button search;
    @FXML
    private Button reserve1;
    @FXML
    private Button reserve2;
    @FXML
    private Button reserve3;
    @FXML
    private Button reserve4;
    @FXML
    private Button reserve5;
    @FXML
    private Button next;
    @FXML
    private Button previous;


    private List<Smjestaj> smjestaji;
    private ArrayList<ArrayList<String>> locations = new ArrayList<>();

    private List<Smjestaj> filtriraniSmjestaji;
    private int page = 0;
    public static int currentId;

    public void nextOnAction(ActionEvent e) {
        if (page < filtriraniSmjestaji.size() / 5) {
            ++page;
            prikazi5smjestaja(filtriraniSmjestaji.listIterator(page * 5));
        }
    }

    public void previousOnAction(ActionEvent e) {
        if (page > 0) {
            --page;
            prikazi5smjestaja(filtriraniSmjestaji.listIterator(page * 5));
        }
    }

    private void reserve(int idx) {
        if (!(idx < filtriraniSmjestaji.size()))
            return;
        try {
            FinalizeReservationController.rezervacija.setSmjestajId(filtriraniSmjestaji.get(idx).getId());
            FinalizeReservationController.rezervacija.setKorisnikId(currentId);
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/FinalizeRes.fxml"));
            Parent root = fxmlLoader.load();
            //Stage stageOld = (Stage) odobriSmjestajButton.getScene().getWindow();
            Stage stage = new Stage();
            stage.setTitle("Rezervacija");
            Scene scene = new Scene(root);
            stage.setScene(scene);
            //stageOld.close();
            stage.show();
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }

    public void reserve1OnAction(ActionEvent e) {
        reserve(page * 5);
    }

    public void reserve2OnAction(ActionEvent e) {
        reserve(page * 5 + 1);
    }

    public void reserve3OnAction(ActionEvent e) {
        reserve(page * 5 + 2);
    }

    public void reserve4OnAction(ActionEvent e) {
        reserve(page * 5 + 3);
    }

    public void reserve5OnAction(ActionEvent e) {
        reserve(page * 5 + 4);
    }

    public static double extractValue(TextField textField) {
        double n = -1;
        if (!textField.getText().isEmpty()) {
            try {
                n = Double.parseDouble(textField.getText());
            } catch (Exception ignored) {
            } finally {
                if (n < -1) {
                    n = -1;
                }
            }
        }
        return n;
    }

    public void searchOnAction(ActionEvent e) {
        String location = lokacija.getText();
        int minC = (int) extractValue(minCijena);
        int maxC = (int) extractValue(maxCijena);
        int minP = (int) extractValue(minPovrsina);
        int maxP = (int) extractValue(maxPovrsina);

        boolean wifiReq = wifi.isSelected();
        boolean parkingReq = parking.isSelected();

        if (minC == -1 && maxC != -1) {
            minC = 0;
        }
        if (minC != -1 && maxC == -1) {
            maxC = Integer.MAX_VALUE;
        }

        if (minP == -1 && maxP != -1) {
            minP = 0;
        }
        if (minP != -1 && maxP == -1) {
            maxP = Integer.MAX_VALUE;
        }


        filtriraniSmjestaji = new ArrayList<>();
        for (int i = 0; i < smjestaji.size(); ++i) {
            Smjestaj smjestaj = smjestaji.get(i);
            boolean wifiFilter = wifiReq && !smjestaj.isWifi();
            boolean parkingFilter = parkingReq && !smjestaj.isParking();
            boolean cijenaFilter = minC != -1 && maxC != -1 && (smjestaj.getCijena() > maxC || smjestaj.getCijena() < minC);
            boolean povrsinaFilter = minP != -1 && maxP != -1 && (smjestaj.getPovrsina() > maxP || smjestaj.getPovrsina() < minP);
            boolean locationFilter = !location.isEmpty() && !locations.get(i).contains(location);
            if (wifiFilter || parkingFilter || cijenaFilter || povrsinaFilter || locationFilter) {
                continue;
            }
            filtriraniSmjestaji.add(smjestaj);
        }


        for (Smjestaj s : smjestaji) {
            if (location != "") {
                location = location.toLowerCase(Locale.ROOT);

            }
        }

        page = 0;
        prikazi5smjestaja(filtriraniSmjestaji.listIterator());
    }

//    public void brojKrevetaOnAction(ActionEvent e) {
//
//    }

    public void wifiOnAction(ActionEvent e) {

    }

    public void parkingOnAction(ActionEvent e) {

    }

    public void bazenOnAction(ActionEvent e) {

    }

    public void klimaOnAction(ActionEvent e) {

    }

    public void kuhinjaOnAction(ActionEvent e) {

    }

    public void balkonOnAction(ActionEvent e) {

    }

    public void tvOnAction(ActionEvent e) {

    }

    public void kupaonicaOnAction(ActionEvent e) {

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

    public void wifi2OnAction(ActionEvent e) {

    }

    public void parking2OnAction(ActionEvent e) {

    }

    public void bazen2OnAction(ActionEvent e) {

    }

    public void klima2OnAction(ActionEvent e) {

    }

    public void kuhinja2OnAction(ActionEvent e) {

    }

    public void balkon2OnAction(ActionEvent e) {

    }

    public void tv2OnAction(ActionEvent e) {

    }

    public void kupaonica2OnAction(ActionEvent e) {

    }

    public void wifi3OnAction(ActionEvent e) {

    }

    public void parking3OnAction(ActionEvent e) {

    }

    public void bazen3OnAction(ActionEvent e) {

    }

    public void klima3OnAction(ActionEvent e) {

    }

    public void kuhinja3OnAction(ActionEvent e) {

    }

    public void balkon3OnAction(ActionEvent e) {

    }

    public void tv3OnAction(ActionEvent e) {

    }

    public void kupaonica3OnAction(ActionEvent e) {

    }

    public void wifi4OnAction(ActionEvent e) {

    }

    public void parking4OnAction(ActionEvent e) {

    }

    public void bazen4OnAction(ActionEvent e) {

    }

    public void klima4OnAction(ActionEvent e) {

    }

    public void kuhinja4OnAction(ActionEvent e) {

    }

    public void balkon4OnAction(ActionEvent e) {

    }

    public void tv4OnAction(ActionEvent e) {

    }

    public void kupaonica4OnAction(ActionEvent e) {

    }

    public void wifi5OnAction(ActionEvent e) {

    }

    public void parking5OnAction(ActionEvent e) {

    }

    public void bazen5OnAction(ActionEvent e) {

    }

    public void klima5OnAction(ActionEvent e) {

    }

    public void kuhinja5OnAction(ActionEvent e) {

    }

    public void balkon5OnAction(ActionEvent e) {

    }

    public void tv5OnAction(ActionEvent e) {

    }

    public void kupaonica5OnAction(ActionEvent e) {

    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        smjestaji = DataBaseService.getApprovedSmjestaj();
        filtriraniSmjestaji = smjestaji;

        for (Smjestaj s : smjestaji) {
            ArrayList<String> l = new ArrayList<>();
            if (s.getGrad() != null)
                l.add(s.getGrad().toLowerCase(Locale.ROOT));
            if (s.getDrzava() != null)
                l.add(s.getDrzava().toLowerCase(Locale.ROOT));
            if (s.getAdresa() != null)
                l.add(s.getAdresa().toLowerCase(Locale.ROOT));
            locations.add(l);
        }

        prikazi5smjestaja(smjestaji.listIterator());

        if(currentId == -1){
            reserve1.setDisable(true);
            reserve2.setDisable(true);
            reserve3.setDisable(true);
            reserve4.setDisable(true);
            reserve5.setDisable(true);
        }
    }

    private void prikazi5smjestaja(ListIterator<Smjestaj> it) {
        Smjestaj smjestaj;

        if (!it.hasNext()) {
            naziv1.setText("Naziv");
            tip1.setText("Tip");
            opis1.setText("");
            cijena1.setText("");
            brojKreveta1.setText("");
            povrsina1.setText("");
            wifi1.setSelected(false);
            parking1.setSelected(false);
            kuhinja1.setSelected(false);
            kupaonica1.setSelected(false);
            klima1.setSelected(false);
            bazen1.setSelected(false);
            balkon1.setSelected(false);
            tv1.setSelected(false);
        } else {
            smjestaj = it.next();
            if(smjestaj.getSlikaPath() != null)
                image1.setImage(new Image(smjestaj.getSlikaPath()));
            naziv1.setText(smjestaj.getNaziv());
            tip1.setText(smjestaj.getTip());
            opis1.setText(smjestaj.getOpis());
            cijena1.setText(smjestaj.getCijena().toString() + " KM");
            brojKreveta1.setText(smjestaj.getBrojKreveta().toString() + " kreveta");
            povrsina1.setText(smjestaj.getPovrsina().toString() + " m^2");
            wifi1.setSelected(smjestaj.isWifi());
            parking1.setSelected(smjestaj.isParking());
            kuhinja1.setSelected(smjestaj.isKuhinja());
            kupaonica1.setSelected(smjestaj.isKupaonica());
            klima1.setSelected(smjestaj.isKlima());
            bazen1.setSelected(smjestaj.isBazen());
            balkon1.setSelected(smjestaj.isBalkon());
            tv1.setSelected(smjestaj.isTv());
        }

        if (!it.hasNext()) {
            naziv2.setText("Naziv");
            tip2.setText("Tip");
            opis2.setText("");
            cijena2.setText("");
            brojKreveta2.setText("");
            povrsina2.setText("");
            wifi2.setSelected(false);
            parking2.setSelected(false);
            kuhinja2.setSelected(false);
            kupaonica2.setSelected(false);
            klima2.setSelected(false);
            bazen2.setSelected(false);
            balkon2.setSelected(false);
            tv2.setSelected(false);
        } else {
            smjestaj = it.next();
            if(smjestaj.getSlikaPath() != null)
                image2.setImage(new Image(smjestaj.getSlikaPath()));
            naziv2.setText(smjestaj.getNaziv());
            tip2.setText(smjestaj.getTip());
            opis2.setText(smjestaj.getOpis());
            cijena2.setText(smjestaj.getCijena().toString() + " ");
            brojKreveta2.setText(smjestaj.getBrojKreveta().toString() + " kreveta");
            povrsina2.setText(smjestaj.getPovrsina().toString() + " m^2");
            wifi2.setSelected(smjestaj.isWifi());
            parking2.setSelected(smjestaj.isParking());
            kuhinja2.setSelected(smjestaj.isKuhinja());
            kupaonica2.setSelected(smjestaj.isKupaonica());
            klima2.setSelected(smjestaj.isKlima());
            bazen2.setSelected(smjestaj.isBazen());
            balkon2.setSelected(smjestaj.isBalkon());
            tv2.setSelected(smjestaj.isTv());
        }

        if (!it.hasNext()) {
            naziv3.setText("Naziv");
            tip3.setText("Tip");
            opis3.setText("");
            cijena3.setText("");
            brojKreveta3.setText("");
            povrsina3.setText("");
            wifi3.setSelected(false);
            parking3.setSelected(false);
            kuhinja3.setSelected(false);
            kupaonica3.setSelected(false);
            klima3.setSelected(false);
            bazen3.setSelected(false);
            balkon3.setSelected(false);
            tv3.setSelected(false);
        } else {
            smjestaj = it.next();
            if(smjestaj.getSlikaPath() != null)
                image3.setImage(new Image(smjestaj.getSlikaPath()));
            naziv3.setText(smjestaj.getNaziv());
            tip3.setText(smjestaj.getTip());
            opis3.setText(smjestaj.getOpis());
            cijena3.setText(smjestaj.getCijena().toString() + " KM");
            brojKreveta3.setText(smjestaj.getBrojKreveta().toString() + " kreveta");
            povrsina3.setText(smjestaj.getPovrsina().toString() + " m^2");
            wifi3.setSelected(smjestaj.isWifi());
            parking3.setSelected(smjestaj.isParking());
            kuhinja3.setSelected(smjestaj.isKuhinja());
            kupaonica3.setSelected(smjestaj.isKupaonica());
            klima3.setSelected(smjestaj.isKlima());
            bazen3.setSelected(smjestaj.isBazen());
            balkon3.setSelected(smjestaj.isBalkon());
            tv3.setSelected(smjestaj.isTv());
        }

        if (!it.hasNext()) {
            naziv4.setText("Naziv");
            tip4.setText("Tip");
            opis4.setText("");
            cijena4.setText("");
            brojKreveta4.setText("");
            povrsina4.setText("");
            wifi4.setSelected(false);
            parking4.setSelected(false);
            kuhinja4.setSelected(false);
            kupaonica4.setSelected(false);
            klima4.setSelected(false);
            bazen4.setSelected(false);
            balkon4.setSelected(false);
            tv4.setSelected(false);
        } else {
            smjestaj = it.next();
            if(smjestaj.getSlikaPath() != null)
                image4.setImage(new Image(smjestaj.getSlikaPath()));
            naziv4.setText(smjestaj.getNaziv());
            tip4.setText(smjestaj.getTip());
            opis4.setText(smjestaj.getOpis());
            cijena4.setText(smjestaj.getCijena().toString() + " KM");
            brojKreveta4.setText(smjestaj.getBrojKreveta().toString() + " kreveta");
            povrsina4.setText(smjestaj.getPovrsina().toString() + " m^2");
            wifi4.setSelected(smjestaj.isWifi());
            parking4.setSelected(smjestaj.isParking());
            kuhinja4.setSelected(smjestaj.isKuhinja());
            kupaonica4.setSelected(smjestaj.isKupaonica());
            klima4.setSelected(smjestaj.isKlima());
            bazen4.setSelected(smjestaj.isBazen());
            balkon4.setSelected(smjestaj.isBalkon());
            tv4.setSelected(smjestaj.isTv());
        }

        if (!it.hasNext()) {
            naziv5.setText("Naziv");
            tip5.setText("Tip");
            opis5.setText("");
            cijena5.setText("");
            brojKreveta5.setText("");
            povrsina5.setText("");
            wifi5.setSelected(false);
            parking5.setSelected(false);
            kuhinja5.setSelected(false);
            kupaonica5.setSelected(false);
            klima5.setSelected(false);
            bazen5.setSelected(false);
            balkon5.setSelected(false);
            tv5.setSelected(false);
        } else {
            smjestaj = it.next();
            if(smjestaj.getSlikaPath() != null)
            image5.setImage(new Image(smjestaj.getSlikaPath()));
            naziv5.setText(smjestaj.getNaziv());
            tip5.setText(smjestaj.getTip());
            opis5.setText(smjestaj.getOpis());
            cijena5.setText(smjestaj.getCijena().toString() + " KM");
            brojKreveta5.setText(smjestaj.getBrojKreveta().toString() + " kreveta");
            povrsina5.setText(smjestaj.getPovrsina().toString() + " m^2");
            wifi5.setSelected(smjestaj.isWifi());
            parking5.setSelected(smjestaj.isParking());
            kuhinja5.setSelected(smjestaj.isKuhinja());
            kupaonica5.setSelected(smjestaj.isKupaonica());
            klima5.setSelected(smjestaj.isKlima());
            bazen5.setSelected(smjestaj.isBazen());
            balkon5.setSelected(smjestaj.isBalkon());
            tv5.setSelected(smjestaj.isTv());
        }
    }
}
