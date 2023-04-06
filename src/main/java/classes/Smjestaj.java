package classes;

import javax.persistence.*;
import java.util.Collection;

@Entity
public class Smjestaj {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private boolean odobren;

    private String slika;

    private String slikaPath;

    private String tip;
    private String naziv;
    private String drzava;
    private String grad;
    private String adresa;
    private Double povrsina;
    private Double cijena;
    private Integer brojKreveta;
    private Integer kapacitet;
    private String opis;
    private int vlasnik;
    private double kapara;
    private boolean placanjePoRezervaciji;
    private boolean klima;
    private boolean balkon;
    private boolean tv;
    private boolean kuhinja;
    private boolean kupaonica;
    private boolean parking;
    private boolean wifi;
    private boolean bazen;
    private Integer brojZvjezdica;
    private Double ocjena;
    @ManyToOne
    @JoinColumn(name = "vlasnik", referencedColumnName = "id", updatable = false, insertable = false, nullable = false)
    private Korisnik korisnik_vlasnik;
    @OneToMany(mappedBy = "smjestaj_id_u_rez")
    private Collection<Rezervacija> rezervacija_id;



    public Integer getKapacitet() {
        return kapacitet;
    }

    public void setKapacitet(Integer kapacitet) {
        this.kapacitet = kapacitet;
    }

    public boolean isPlacanjePoRezervaciji() {
        return placanjePoRezervaciji;
    }

    public void setPlacanjePoRezervaciji(boolean placanjePoRezervaciji) {
        this.placanjePoRezervaciji = placanjePoRezervaciji;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Double getOcjena() {
        return ocjena;
    }

    public void setOcjena(Double ocjena) {
        this.ocjena = ocjena;
    }

    public Integer getBrojZvjezdica() {
        return brojZvjezdica;
    }

    public void setBrojZvjezdica(Integer brojZvjezdica) {
        this.brojZvjezdica = brojZvjezdica;
    }

    public boolean isOdobren() {
        return odobren;
    }

    public void setOdobren(boolean odobren) {
        this.odobren = odobren;
    }

    public String getSlika() {
        return slika;
    }

    public void setSlika(String slika) {
        this.slika = slika;
    }

    public String getSlikaPath() {return slikaPath;}

    public void setSlikaPath(String slikaPath) {this.slikaPath = slikaPath;}

    public String getTip() {
        return tip;
    }

    public void setTip(String tip) {
        this.tip = tip;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public String getDrzava() {
        return drzava;
    }

    public void setDrzava(String drzava) {
        this.drzava = drzava;
    }

    public String getGrad() {
        return grad;
    }

    public void setGrad(String grad) {
        this.grad = grad;
    }

    public String getAdresa() {
        return adresa;
    }

    public void setAdresa(String adresa) {
        this.adresa = adresa;
    }

    public Double getPovrsina() {
        return povrsina;
    }

    public void setPovrsina(Double povrsina) {
        this.povrsina = povrsina;
    }

    public Double getCijena() {
        return cijena;
    }

    public void setCijena(Double cijena) {
        this.cijena = cijena;
    }

    public Integer getBrojKreveta() {
        return brojKreveta;
    }

    public void setBrojKreveta(Integer brojKreveta) {
        this.brojKreveta = brojKreveta;
    }

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    public int getVlasnik() {
        return vlasnik;
    }

    public void setVlasnik(int vlasnik) {
        this.vlasnik = vlasnik;
    }

    public double getKapara() {
        return kapara;
    }

    public void setKapara(double kapara) {
        this.kapara = kapara;
    }

    public boolean isKlima() {
        return klima;
    }

    public void setKlima(boolean klima) {
        this.klima = klima;
    }

    public boolean isBalkon() {
        return balkon;
    }

    public void setBalkon(boolean balkon) {
        this.balkon = balkon;
    }

    public boolean isTv() {
        return tv;
    }

    public void setTv(boolean tv) {
        this.tv = tv;
    }

    public boolean isKuhinja() {
        return kuhinja;
    }

    public void setKuhinja(boolean kuhinja) {
        this.kuhinja = kuhinja;
    }

    public boolean isKupaonica() {
        return kupaonica;
    }

    public void setKupaonica(boolean kupaonica) {
        this.kupaonica = kupaonica;
    }

    public boolean isParking() {
        return parking;
    }

    public void setParking(boolean parking) {
        this.parking = parking;
    }

    public boolean isWifi() {
        return wifi;
    }

    public void setWifi(boolean wifi) {
        this.wifi = wifi;
    }

    public boolean isBazen() {
        return bazen;
    }

    public void setBazen(boolean bazen) {
        this.bazen = bazen;
    }

    public Korisnik getKorisnik_vlasnik() {
        return korisnik_vlasnik;
    }

    public void setKorisnik_vlasnik(Korisnik korisnik_vlasnik) {
        this.korisnik_vlasnik = korisnik_vlasnik;
    }

    public Collection<Rezervacija> getRezervacija_id() {
        return rezervacija_id;
    }

    public void setRezervacija_id(Collection<Rezervacija> rezervacija_id) {
        this.rezervacija_id = rezervacija_id;
    }
}
