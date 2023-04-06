package classes;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
public class Rezervacija {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int smjestajId;
    private int korisnikId;
    private Timestamp checkin;
    private Timestamp checkout;
    private double price;
    private boolean onlinePayment;
//    private String cancellationMessage;
    private int discountRate;

    @ManyToOne
    @JoinColumn(name = "smjestajId", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    private Smjestaj smjestaj_id_u_rez;
    @ManyToOne
    @JoinColumn(name = "korisnikId", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    private Korisnik korisnik_id_u_rez;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSmjestajId() {
        return smjestajId;
    }

    public void setSmjestajId(int smjestajId) {
        this.smjestajId = smjestajId;
    }

    public int getKorisnikId() {
        return korisnikId;
    }

    public void setKorisnikId(int korisnikId) {
        this.korisnikId = korisnikId;
    }

    public Timestamp getCheckin() {
        return checkin;
    }

    public void setCheckin(Timestamp checkin) {
        this.checkin = checkin;
    }

    public Timestamp getCheckout() {
        return checkout;
    }

    public void setCheckout(Timestamp checkout) {
        this.checkout = checkout;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public boolean isOnlinePayment() {
        return onlinePayment;
    }

    public void setOnlinePayment(boolean onlinePayment) {
        this.onlinePayment = onlinePayment;
    }

    public int getDiscountRate() {
        return discountRate;
    }

    public void setDiscountRate(int discountRate) {
        this.discountRate = discountRate;
    }

    public Smjestaj getSmjestaj_id_u_rez() {
        return smjestaj_id_u_rez;
    }

    public void setSmjestaj_id_u_rez(Smjestaj smjestaj_id_u_rez) {
        this.smjestaj_id_u_rez = smjestaj_id_u_rez;
    }

    public Korisnik getKorisnik_id_u_rez() {
        return korisnik_id_u_rez;
    }

    public void setKorisnik_id_u_rez(Korisnik korisnik_id_u_rez) {
        this.korisnik_id_u_rez = korisnik_id_u_rez;
    }
}
