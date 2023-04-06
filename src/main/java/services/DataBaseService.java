package services;

import application.MainController;
import classes.Korisnik;
import classes.Rezervacija;
import classes.Smjestaj;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

public class DataBaseService {
    static EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("default");
    static EntityManager entityManager = entityManagerFactory.createEntityManager();
    static EntityTransaction transaction = entityManager.getTransaction();

    public static final int SUCCESS = 0;
    public static final int EMAIL_DUPLICATE = 1;
    public static final int USERNAME_DUPLICATE = 2;
    public static final int RETRY = 3;
    public static final int USER_NOT_FOUND = 4;
    public static final int USER_NOT_APPROVED = 5;
    public static final int INVALID_PASSWORD = 6;

    public static boolean insertUser(Korisnik user) {

        try {
            transaction.begin();
            entityManager.persist(user);
            transaction.commit();
            return true;
        } catch (Exception e) {
            transaction.rollback();
            return false;
        }
    }

    public static Korisnik getUser(int id) {
        Korisnik user = new Korisnik();
        try {
            transaction.begin();
            String queryString = "SELECT i from Korisnik i WHERE i.id = :userId";
            TypedQuery<Korisnik> query = entityManager.createQuery(queryString, Korisnik.class).setParameter("userId", id);
            user = query.getSingleResult();
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
        }
        return user;
    }

    public static List<Korisnik> getNotApprovedUsers() {
        List<Korisnik> user = new ArrayList<Korisnik>();
        try {
            transaction.begin();
            String queryS = "SELECT i FROM Korisnik i WHERE i.approved = :userApproved";
            TypedQuery<Korisnik> query = entityManager.createQuery(queryS, Korisnik.class).setParameter("userApproved", false);
            user = query.getResultList();
        } catch (Exception e) {
            transaction.rollback();
        }
        return user;
    }

    public static boolean updateUser(Korisnik user) {
        try {
            transaction.begin();
            String queryS = "UPDATE Korisnik i SET i.approved = :uApproved, i.username = :uName, i.phone = :uPhone, i.country = :uCountry, i.city = :uCity, i.password = :uPassword, i.discount = :uDiscount, i.balance = :uBalance WHERE i.id = :uId";
            Query query = entityManager.createQuery(queryS)
                    .setParameter("uApproved", user.isApproved())
                    .setParameter("uName", user.getUsername())
                    .setParameter("uPhone", user.getPhone())
                    .setParameter("uCountry", user.getCountry())
                    .setParameter("uCity", user.getCity())
                    .setParameter("uPassword", user.getPassword())
                    .setParameter("uDiscount", user.getDiscount())
                    .setParameter("uBalance", user.getBalance())
                    .setParameter("uId", user.getId());
            query.executeUpdate();
            transaction.commit();
            return true;
        } catch (Exception e) {
            transaction.rollback();
            return false;
        }
    }

    public static boolean deleteUser(int id) {
        try {
            transaction.begin();
            String s = "DELETE FROM Korisnik u WHERE u.id = :userId";
            Query query = entityManager.createQuery(s).setParameter("userId", id);
            query.executeUpdate();
            transaction.commit();
            return true;
        } catch (Exception e) {
            transaction.rollback();
            return false;
        }
    }

    public static int validateLogin(String username, String password) {
        int result = SUCCESS;
        try {
            transaction.begin();
            String queryUser = "SELECT u FROM Korisnik u WHERE u.username = :user OR u.email = :email";
            Query query = entityManager.createQuery(queryUser, Korisnik.class)
                    .setParameter("user", username).setParameter("email", username);
            if (query.getResultList().isEmpty())
                result = USER_NOT_FOUND;
            else {
                Korisnik loginUser = (Korisnik) query.getSingleResult();
                if (!loginUser.isApproved())
                    result = USER_NOT_APPROVED;
                else if (!loginUser.getPassword().equals(password))
                    result = INVALID_PASSWORD;
                else if (loginUser.isApproved() && loginUser.getPassword().equals(password))
                    MainController.currentId = loginUser.getId();
            }
            transaction.commit();
        } catch (Exception e) {
            result = RETRY;
            transaction.rollback();
        }
        return result;
    }

    public static int checkAvailabilityEmail(String email) {
        int result = SUCCESS;
        try {
            transaction.begin();
            String queryString = "SELECT u FROM Korisnik u WHERE u.email = :uEmail";
            Query query = entityManager.createQuery(queryString, Korisnik.class).setParameter("uEmail", email);
            if (!query.getResultList().isEmpty())
                result = EMAIL_DUPLICATE;
            transaction.commit();
        } catch (Exception e) {
            result = RETRY;
            transaction.rollback();
        }
        return result;
    }

    public static int checkAvailabilityUsername(String username) {
        int result = SUCCESS;
        try {
            transaction.begin();
            String queryString = "SELECT u FROM Korisnik u WHERE u.username = :uUsername";
            Query query = entityManager.createQuery(queryString, Korisnik.class).setParameter("uUsername", username);
            if (!query.getResultList().isEmpty())
                result = USERNAME_DUPLICATE;
            transaction.commit();
        } catch (Exception e) {
            result = RETRY;
            transaction.rollback();
        }
        return result;
    }

    public static boolean insertAccomodation(Smjestaj smjestaj) {
        try {
            transaction.begin();
            entityManager.persist(smjestaj);
            transaction.commit();
            return true;
        } catch (Exception e) {
            transaction.rollback();
            return false;
        }
    }

    public static Smjestaj getSmjestaj(int id) {
        Smjestaj smjestaj = new Smjestaj();
        try {
            transaction.begin();
            String queryString = "SELECT s FROM Smjestaj s WHERE s.id = :sId";
            Query query = entityManager.createQuery(queryString, Smjestaj.class).setParameter("sId", id);
            smjestaj = (Smjestaj) query.getSingleResult();
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
        }
        return smjestaj;
    }

    public static List<Smjestaj> getNotApprovedSmjestaj() {
        List<Smjestaj> smjestaj = new ArrayList<Smjestaj>();
        try {
            transaction.begin();
            String queryS = "SELECT i FROM Smjestaj i WHERE i.odobren = :sApproved";
            TypedQuery<Smjestaj> query = entityManager.createQuery(queryS, Smjestaj.class).setParameter("sApproved", false);
            smjestaj = query.getResultList();
        } catch (Exception e) {
            transaction.rollback();
        }
        return smjestaj;
    }

    public static boolean deleteSmjestaj(int id) {
        try {
            transaction.begin();
            String s = "DELETE FROM Smjestaj u WHERE u.id = :smjestajId";
            Query query = entityManager.createQuery(s).setParameter("smjestajId", id);
            query.executeUpdate();
            transaction.commit();
            return true;
        } catch (Exception e) {
            transaction.rollback();
            return false;
        }
    }

    public static boolean updateSmjestaj(Smjestaj smjestaj, int id) {
        try {
            transaction.begin();
            String s = "UPDATE Smjestaj a SET " +
                    "a.odobren = :aApproved, " +
                    "a.vlasnik = :aOwner, " +
                    "a.naziv = :aName, " +
                    "a.opis = :aDescription, " +
                    "a.cijena = :aPrice, " +
                    "a.tip = :aType, " +
                    "a.drzava = :aCountry, " +
                    "a.grad = :aCity, " +
                    "a.adresa = :aStreet, " +
                    "a.povrsina = :aArea, " +
                    "a.kapacitet = :aCapacity, " +
                    "a.klima = :aAircondition, " +
                    "a.balkon = :aBalcony, " +
                    "a.tv = :aTv, " +
                    "a.kuhinja = :aKitchen, " +
                    "a.kupaonica = :aBathroom, " +
                    "a.parking = :aParking, " +
                    "a.wifi = :aWifi, " +
                    "a.bazen = :aPool, " +
                    "a.placanjePoRezervaciji = :aPayment, " +
                    "a.slika = :aImage WHERE a.id = :aId";
            Query query = entityManager.createQuery(s)
                    .setParameter("aId", id)
                    .setParameter("aApproved", smjestaj.isOdobren())
                    .setParameter("aOwner", smjestaj.getVlasnik())
                    .setParameter("aName", smjestaj.getNaziv())
                    .setParameter("aDescription", smjestaj.getOpis())
                    .setParameter("aPrice", smjestaj.getCijena())
                    .setParameter("aType", smjestaj.getTip())
                    .setParameter("aCountry", smjestaj.getDrzava())
                    .setParameter("aCity", smjestaj.getGrad())
                    .setParameter("aStreet", smjestaj.getAdresa())
                    .setParameter("aArea", smjestaj.getPovrsina())
                    .setParameter("aCapacity", smjestaj.getKapacitet())
                    .setParameter("aAircondition", smjestaj.isKlima())
                    .setParameter("aBalcony", smjestaj.isBalkon())
                    .setParameter("aTv", smjestaj.isTv())
                    .setParameter("aKitchen", smjestaj.isKuhinja())
                    .setParameter("aBathroom", smjestaj.isKupaonica())
                    .setParameter("aParking", smjestaj.isParking())
                    .setParameter("aWifi", smjestaj.isWifi())
                    .setParameter("aPool", smjestaj.isBazen())
                    .setParameter("aPayment", smjestaj.isPlacanjePoRezervaciji())
                    .setParameter("aImage", smjestaj.getSlika());
            query.executeUpdate();
            transaction.commit();
            return true;
        } catch (Exception e) {
            transaction.rollback();
            return false;
        }
    }

    public static boolean rezervisiSmjestaj(Rezervacija rezervacija) {
        try {
            boolean dostupan = false;
            transaction.begin();
            String queryString = "SELECT r FROM Rezervacija r WHERE r.smjestajId = :id AND ((r.checkin < :newcheckin AND r.checkout > :newcheckin) OR (r.checkin < :newcheckout AND r.checkout > :newcheckout))";
            Query query = entityManager.createQuery(queryString, Rezervacija.class).setParameter("id", rezervacija.getSmjestajId()).setParameter("newcheckin", rezervacija.getCheckin()).setParameter("newcheckout", rezervacija.getCheckout());
            if (query.getResultList().isEmpty()) {
                entityManager.persist(rezervacija);
                dostupan = true;
            }
            transaction.commit();
            return dostupan;
        } catch (Exception e) {
            transaction.rollback();
            return false;
        }
    }

    public static List<Smjestaj> getSmjestajByKorisnik(int id) {
        List<Smjestaj> list = null;
        try {
            transaction.begin();
            String queryS = "SELECT a FROM Smjestaj a WHERE a.vlasnik=:oId AND a.odobren=true";
            entityManager.clear();
            Query query = entityManager.createQuery(queryS).setParameter("oId", id);
            list = query.getResultList();
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
        }
        return list;
    }

    public static List<Smjestaj> getApprovedSmjestaj() {
        List<Smjestaj> list = null;
        try {
            transaction.begin();
            entityManager.clear();
            list = entityManager.createQuery("SELECT a FROM Smjestaj a WHERE a.odobren=true").getResultList();
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
        }
        return list;
    }


    public static List<Rezervacija> getRezervacijeByKorisnik(int korisnikId) {
        List<Rezervacija> reservations = null;
        try {
            transaction.begin();
            String s = "SELECT r FROM Rezervacija r JOIN Smjestaj a ON r.smjestajId = a.id WHERE r.korisnikId = :uId";
            entityManager.clear();
            Query query = entityManager.createQuery(s).setParameter("uId", korisnikId);
            reservations = query.getResultList();
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
        }
        return reservations;
    }
    public static List<Rezervacija> getRezervacijeBySmjestaj(int smjestajId){
        List<Rezervacija> reservations = null;
        try{
            transaction.begin();
            String s = "SELECT r FROM Rezervacija r WHERE r.smjestajId = :smjestaj";
            entityManager.clear();
            Query query = entityManager.createQuery(s).setParameter("smjestaj", smjestajId);
            reservations = query.getResultList();
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            transaction.rollback();
        }
        return reservations;
    }

    public static void deleteRezervacija(int id) {
        try {
            transaction.begin();
            String queryS = "DELETE FROM Rezervacija r WHERE r.id=:IdSent";
            Query query = entityManager.createQuery(queryS).setParameter("IdSent", id);
            query.executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
        }
    }
}
