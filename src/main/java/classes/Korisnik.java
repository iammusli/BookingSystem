package classes;

import java.util.Collection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Korisnik {
	public static final int admin = 0;
	public static final int korisnik = 2;
	public static final int iznajmljivac = 1;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private int type;
	private boolean approved = false;
	private String username;
	private String email;
	private String password;
	private String firstname;
	private String lastname;
	private String phone;
    private String city;
    private String country;
	private String cardnumber;
	private double balance = 0;
	private double discount = 0;
	
	@OneToMany(mappedBy = "korisnik_vlasnik")
	private Collection<Smjestaj> smjestajOdKorisnik;
	
	@OneToMany(mappedBy = "korisnik_id_u_rez") 
	private Collection<Rezervacija> rezervacijeOdKorisnik;

	@Override
	public String toString() {
		return username + " " + email + " " + password;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public boolean isApproved() {
		return approved;
	}

	public void setApproved(boolean approved) {
		this.approved = approved;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getCardnumber() {
		return cardnumber;
	}

	public void setCardnumber(String cardnumber) {
		this.cardnumber = cardnumber;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	public double getDiscount() {
		return discount;
	}

	public void setDiscount(double discount) {
		this.discount = discount;
	}

	public Collection<Smjestaj> getSmjestajOdKorisnik() {
		return smjestajOdKorisnik;
	}

	public void setSmjestajOdKorisnik(Collection<Smjestaj> smjestajOdKorisnik) {
		this.smjestajOdKorisnik = smjestajOdKorisnik;
	}

	public Collection<Rezervacija> getRezervacijeOdKorisnik() {
		return rezervacijeOdKorisnik;
	}

	public void setRezervacijeOdKorisnik(Collection<Rezervacija> rezervacijeOdKorisnik) {
		this.rezervacijeOdKorisnik = rezervacijeOdKorisnik;
	}
	public Korisnik() {}
	public Korisnik(Korisnik o) {
		super();
		this.id = o.id;
		this.type = o.type;
		this.approved = o.approved;
		this.username = o.username;
		this.email = o.email;
		this.password = o.password;
		this.firstname = o.firstname;
		this.lastname = o.lastname;
		this.phone = o.phone;
		this.city = o.city;
		this.country = o.country;
		this.cardnumber = o.cardnumber;
		this.balance = o.balance;
		this.discount = o.discount;
		this.smjestajOdKorisnik = o.smjestajOdKorisnik;
		this.rezervacijeOdKorisnik = o.rezervacijeOdKorisnik;
	}
	public void copy(Korisnik o) {
		this.id = o.id;
		this.type = o.type;
		this.approved = o.approved;
		this.username = o.username;
		this.email = o.email;
		this.password = o.password;
		this.firstname = o.firstname;
		this.lastname = o.lastname;
		this.phone = o.phone;
		this.city = o.city;
		this.country = o.country;
		this.cardnumber = o.cardnumber;
		this.balance = o.balance;
		this.discount = o.discount;
		this.smjestajOdKorisnik = o.smjestajOdKorisnik;
		this.rezervacijeOdKorisnik = o.rezervacijeOdKorisnik;
	}
	
}
