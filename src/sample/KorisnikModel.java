package sample;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.*;

public class KorisnikModel {
    private ObservableList<Korisnik> korisnici = FXCollections.observableArrayList();
    private ObjectProperty<Korisnik> trenutniKorisnik = new SimpleObjectProperty<>();
    private static KorisnikModel instanca = null;
    private Connection connection;
    private PreparedStatement dajKorisnika;
    private PreparedStatement dajZadnjegKorisnika;
    private PreparedStatement ubaciKorisnika;
    private PreparedStatement updateKorisnika;
    private PreparedStatement deleteKorisnika;

    public ObservableList<Korisnik> getKorisnici() {
        return korisnici;
    }

    public Korisnik getTrenutniKorisnik() {
        return trenutniKorisnik.get();
    }

    public ObjectProperty<Korisnik> trenutniKorisnikProperty() {
        return trenutniKorisnik;
    }

    public void setTrenutniKorisnik(Korisnik trenutniKorisnik) {
        this.trenutniKorisnik.set(trenutniKorisnik);
    }

    public void setKorisnici(ObservableList<Korisnik> korisnici) {
        this.korisnici = korisnici;
    }


    public static KorisnikModel getInstance() {
        if(instanca == null) initialize();
        return instanca;
    }

    private static void initialize() {
        instanca = new KorisnikModel();
    }

    private KorisnikModel() {
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:baza.db");
            dajKorisnika = connection.prepareStatement("SELECT * FROM korisnici");
            ResultSet resultSet = dajKorisnika.executeQuery();
            if(!resultSet.next()) {
                Korisnik k = new Korisnik("", "", "", "", "");
                korisnici.add(k);
            }
            while (resultSet.next()) {
                Korisnik k = new Korisnik(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3),
                        resultSet.getString(4), resultSet.getString(5));
                korisnici.add(k);
                if(trenutniKorisnik == null) trenutniKorisnik = new SimpleObjectProperty<Korisnik>(k);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if(trenutniKorisnik == null) trenutniKorisnik = new SimpleObjectProperty<>();
    }

    public void dodajKorisnika(String ime, String prezime, String email, String korisnickoIme, String password) {
        try {
            ubaciKorisnika = connection.prepareStatement("INSERT INTO  korisnici VALUES (?,?,?,?,?)");
            ubaciKorisnika.setString(1, ime);
            ubaciKorisnika.setString(2, prezime);
            ubaciKorisnika.setString(3, email);
            ubaciKorisnika.setString(4, korisnickoIme);
            ubaciKorisnika.setString(5, password);
            ubaciKorisnika.executeUpdate();
            dajZadnjegKorisnika = connection.prepareStatement("SELECT * FROM  korisnici ORDER BY ime DESC LIMIT 1");
            ResultSet resultSet = dajZadnjegKorisnika.executeQuery();
            Korisnik k = new Korisnik(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3),
                    resultSet.getString(4), resultSet.getString(5));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateKorisnika(String ime, String prezime, String email, String korisnickoIme, String password, String staroIme) {
        try {
            updateKorisnika = connection.prepareStatement("UPDATE korisnici SET ime=?, prezime=?, email=?, korisnickoIme=?, password=? WHERE korisnickoIme=?");
            updateKorisnika.setString(1,ime);
            updateKorisnika.setString(2,prezime);
            updateKorisnika.setString(3,email);
            updateKorisnika.setString(4,korisnickoIme);
            updateKorisnika.setString(5,password);
            updateKorisnika.setString(6,staroIme);
            updateKorisnika.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void obrisiKorinika(String korisnickoIme) {
        try {
            deleteKorisnika = connection.prepareStatement("DELETE FROM korisnici WHERE korisnickoIme=?");
            deleteKorisnika.setString(1,korisnickoIme);
            deleteKorisnika.executeUpdate();
        } catch (SQLException e) {e.printStackTrace();
            e.printStackTrace();
        }
    }
}
