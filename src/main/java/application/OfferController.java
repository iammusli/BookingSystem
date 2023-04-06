package application;

import classes.Smjestaj;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import services.DataBaseService;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;
import java.util.regex.Pattern;

public class OfferController implements Initializable {
    @FXML
    private ImageView slika;
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
    private CheckBox kupaonica;
    @FXML
    private CheckBox tv;
    @FXML
    private CheckBox placanjePoRezervaciji;
    @FXML
    private Button dodajSliku;
    @FXML
    private Button potvrdi;
    @FXML
    private Button cancel;
    @FXML
    private TextField opis;
    @FXML
    private TextField naziv;
    @FXML
    private TextField cijena;
    @FXML
    private TextField povrsina;
    @FXML
    private TextField drzava;
    @FXML
    private TextField grad;
    @FXML
    private TextField adresa;
    @FXML
    private TextField kapacitet;
    @FXML
    private TextField brojKreveta;
    @FXML
    private ComboBox<String> tip;

    private ObservableList<String> list = FXCollections.observableArrayList();

    private final FileChooser fileChooser = new FileChooser();

    public static int currentId;
    private Smjestaj smjestaj;

    public void tipOnAction(ActionEvent e) {
        smjestaj.setTip(tip.getSelectionModel().getSelectedItem());
    }

    public void brojKrevetaOnAction(ActionEvent e) {
        try {
            smjestaj.setBrojKreveta(Integer.parseInt(brojKreveta.getText()));
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }

    public void wifiOnAction(ActionEvent e) {
        if (wifi.isSelected())
            smjestaj.setWifi(true);
        else
            smjestaj.setWifi(false);
    }

    public void parkingOnAction(ActionEvent e) {
        if (parking.isSelected())
            smjestaj.setParking(true);
        else
            smjestaj.setParking(false);
    }

    public void bazenOnAction(ActionEvent e) {
        if (bazen.isSelected())
            smjestaj.setBazen(true);
        else
            smjestaj.setBazen(false);
    }

    public void klimaOnAction(ActionEvent e) {
        if (klima.isSelected())
            smjestaj.setKlima(true);
        else
            smjestaj.setKlima(false);
    }

    public void kuhinjaOnAction(ActionEvent e) {
        if (kuhinja.isSelected())
            smjestaj.setKuhinja(true);
        else
            smjestaj.setKuhinja(false);
    }

    public void balkonOnAction(ActionEvent e) {
        if (balkon.isSelected())
            smjestaj.setBalkon(true);
        else
            smjestaj.setBalkon(false);
    }

    public void tvOnAction(ActionEvent e) {
        if (tv.isSelected())
            smjestaj.setTv(true);
        else
            smjestaj.setTv(false);
    }

    public void kupaonicaOnAction(ActionEvent e) {
        if (kupaonica.isSelected())
            smjestaj.setKupaonica(true);
        else
            smjestaj.setKupaonica(false);
    }

    File source = null;
    File dest = null;
    String filename = null;
    public void dodajSlikuOnAction(ActionEvent e) {
//TODO

        String newPath = "src/main/resources/assets/";
        File directory = new File(newPath);
        if(!directory.exists()){
            directory.mkdirs();
        }
        fileChooser.setInitialDirectory(directory);
        File file = fileChooser.showOpenDialog(dodajSliku.getScene().getWindow());
        if(file != null){
            filename = file.getAbsolutePath();
            System.out.println(filename);
            String extension = filename.substring(filename.lastIndexOf(".") + 1);
            int ind1 = filename.lastIndexOf("/") + 1;
            int ind2 = filename.lastIndexOf(".");
            String name = filename.substring(ind1, ind2);
            source = new File(filename);
            dest = new File(newPath + name + "." + extension);
            try{
                Files.copy(source.toPath(),dest.toPath());
            } catch( IOException e1){
                e1.printStackTrace();
            }
            Image image = new Image(dest.toURI().toString());
            smjestaj.setSlikaPath(dest.toURI().toString());
            slika.setImage(image);
            source = null;
            dest = null;
            filename = null;
        }
    }

    public void placanjePoRezervacijiOnAction(ActionEvent e) {
        if (placanjePoRezervaciji.isSelected())
            smjestaj.setPlacanjePoRezervaciji(true);
        else
            smjestaj.setPlacanjePoRezervaciji(false);
    }

    public void potvrdiOnAction(ActionEvent e) {
        smjestaj.setNaziv(naziv.getText());
        if (!cijena.getText().equals(""))
            smjestaj.setCijena(Double.parseDouble(cijena.getText()));
        if (!povrsina.getText().equals(""))
            smjestaj.setPovrsina(Double.parseDouble(povrsina.getText()));
        smjestaj.setOpis(opis.getText());
        smjestaj.setDrzava(drzava.getText());
        smjestaj.setGrad(grad.getText());
        smjestaj.setAdresa(adresa.getText());
        if (!brojKreveta.getText().equals(""))
            smjestaj.setBrojKreveta(Integer.parseInt(brojKreveta.getText()));
        if (!kapacitet.getText().equals(""))
            smjestaj.setKapacitet(Integer.parseInt(kapacitet.getText()));
        smjestaj.setOdobren(false);

        boolean success = false;
        while (!success) {
            success = DataBaseService.insertAccomodation(smjestaj);
        }
        try {
            UserPageController.currentId = OfferController.currentId;
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/UserPage.fxml"));
            Parent root = (Parent) fxmlLoader.load();
            Stage stageOld = (Stage) potvrdi.getScene().getWindow();
            Stage stage = new Stage();
            stage.setTitle("Korisnicki profil");
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stageOld.close();
            stage.show();
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }


    public void cancelOnAction(ActionEvent e) {
        try {
            UserPageController.currentId = OfferController.currentId;
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/UserPage.fxml"));
            Parent root = (Parent) fxmlLoader.load();
            Stage stageOld = (Stage) cancel.getScene().getWindow();
            Stage stage = new Stage();
            stage.setTitle("Korisnicki profil");
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stageOld.close();
            stage.show();
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }

    private Pattern pattern1 = Pattern.compile("[a-zA-Z ]*");
    private Pattern pattern2 = Pattern.compile("[0-9]*");
    private Pattern pattern3 = Pattern.compile("[a-zA-Z0-9]*");
    private Pattern pattern4 = Pattern.compile("[a-zA-Z0-9 ]*");

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
    private UnaryOperator<TextFormatter.Change> filter4 = c -> {
        if(pattern4.matcher(c.getControlNewText()).matches()) {
            return c;
        } else {
            return null;
        }
    };
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        smjestaj = new Smjestaj();
        list.add("Hotel");
        list.add("Apartman");
        list.add("Villa");
        list.add("Vikendica");
        list.add("Soba");
        list.add("Stan");
        list.add("Kuca");
        tip.setItems(list);
        smjestaj.setVlasnik(currentId);

        TextFormatter<String> textFormatter1 = new TextFormatter<String>(filter1);
        TextFormatter<String> textFormatter2 = new TextFormatter<String>(filter2);
        TextFormatter<String> textFormatter3 = new TextFormatter<String>(filter2);
        TextFormatter<String> textFormatter4 = new TextFormatter<String>(filter1);
        TextFormatter<String> textFormatter5 = new TextFormatter<String>(filter1);
        TextFormatter<String> textFormatter6 = new TextFormatter<String>(filter4);
        TextFormatter<String> textFormatter7 = new TextFormatter<String>(filter2);
        TextFormatter<String> textFormatter8 = new TextFormatter<String>(filter2);

        naziv.setTextFormatter(textFormatter1);
        cijena.setTextFormatter(textFormatter2);
        povrsina.setTextFormatter(textFormatter3);
        drzava.setTextFormatter(textFormatter4);
        grad.setTextFormatter(textFormatter5);
        adresa.setTextFormatter(textFormatter6);
        kapacitet.setTextFormatter(textFormatter7);
        brojKreveta.setTextFormatter(textFormatter8);
    }

}
