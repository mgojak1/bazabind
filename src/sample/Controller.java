package sample;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    public Button btnValidate;
    public Button btnDodaj;
    public ListView<Korisnik> listView;
    public PasswordField passField;
    public TextField textFieldKorsnickoIme;
    public TextField textFieldEmail;
    public TextField textFieldPrezime;
    public TextField textFieldIme;
    private KorisnikModel model;
    public Controller(KorisnikModel m) { model = m; }

    @Override
    public void initialize(URL location,ResourceBundle resources) {
        btnValidate.getStyleClass().addAll("sakrijiPolje");
        listView.setItems(model.getKorisnici());
        model.setTrenutniKorisnik(model.getKorisnici().get(0));
        listView.getSelectionModel().selectedItemProperty().addListener((obs, oldValue, newValue) -> {
            model.setTrenutniKorisnik(listView.getSelectionModel().getSelectedItem());
        });
        textFieldIme.textProperty().bindBidirectional(model.getTrenutniKorisnik().imeProperty());
        textFieldPrezime.textProperty().bindBidirectional(model.getTrenutniKorisnik().prezimeProperty());
        textFieldEmail.textProperty().bindBidirectional(model.getTrenutniKorisnik().emailProperty());
        textFieldKorsnickoIme.textProperty().bindBidirectional(model.getTrenutniKorisnik().korisnickoImeProperty());
        passField.textProperty().bindBidirectional(model.getTrenutniKorisnik().passwordProperty());
        model.trenutniKorisnikProperty().addListener((obs, oldValue, newValue) -> {
            if (oldValue != null) {
                textFieldIme.textProperty().unbindBidirectional(oldValue.imeProperty());
                textFieldPrezime.textProperty().unbindBidirectional(oldValue.prezimeProperty());
                textFieldEmail.textProperty().unbindBidirectional(oldValue.emailProperty());
                textFieldKorsnickoIme.textProperty().unbindBidirectional(oldValue.korisnickoImeProperty());
                passField.textProperty().unbindBidirectional(oldValue.passwordProperty());
            }
            if (newValue == null) {
                textFieldEmail.setText("");
                textFieldIme.setText("");
                textFieldPrezime.setText("");
                textFieldKorsnickoIme.setText("");
                passField.setText("");
            } else {
                textFieldIme.textProperty().bindBidirectional(newValue.imeProperty());
                textFieldPrezime.textProperty().bindBidirectional(newValue.prezimeProperty());
                textFieldEmail.textProperty().bindBidirectional(newValue.emailProperty());
                textFieldKorsnickoIme.textProperty().bindBidirectional(newValue.korisnickoImeProperty());
                passField.textProperty().bindBidirectional(newValue.passwordProperty());
            }
        });
        textFieldIme.textProperty().addListener(((observableValue, s, t1) -> {
            model.updateKorisnika(model.getTrenutniKorisnik().getIme(), model.getTrenutniKorisnik().getPrezime(), model.getTrenutniKorisnik().getEmail(),
                    model.getTrenutniKorisnik().getKorisnickoIme(), model.getTrenutniKorisnik().getPassword(), model.getTrenutniKorisnik().getKorisnickoIme());
        }));
        textFieldPrezime.textProperty().addListener(((observableValue, s, t1) -> {
            model.updateKorisnika(model.getTrenutniKorisnik().getIme(), model.getTrenutniKorisnik().getPrezime(), model.getTrenutniKorisnik().getEmail(),
                    model.getTrenutniKorisnik().getKorisnickoIme(), model.getTrenutniKorisnik().getPassword(), model.getTrenutniKorisnik().getKorisnickoIme());
        }));
        textFieldEmail.textProperty().addListener(((observableValue, s, t1) -> {
            model.updateKorisnika(model.getTrenutniKorisnik().getIme(), model.getTrenutniKorisnik().getPrezime(), model.getTrenutniKorisnik().getEmail(),
                    model.getTrenutniKorisnik().getKorisnickoIme(), model.getTrenutniKorisnik().getPassword(), model.getTrenutniKorisnik().getKorisnickoIme());
        }));
        textFieldKorsnickoIme.textProperty().addListener(((observableValue, s, t1) -> {
            model.updateKorisnika(model.getTrenutniKorisnik().getIme(), model.getTrenutniKorisnik().getPrezime(), model.getTrenutniKorisnik().getEmail(),
                    model.getTrenutniKorisnik().getKorisnickoIme(), model.getTrenutniKorisnik().getPassword(), model.getTrenutniKorisnik().getKorisnickoIme());
        }));
        passField.textProperty().addListener(((observableValue, s, t1) -> {
            model.updateKorisnika(model.getTrenutniKorisnik().getIme(), model.getTrenutniKorisnik().getPrezime(), model.getTrenutniKorisnik().getEmail(),
                    model.getTrenutniKorisnik().getKorisnickoIme(), model.getTrenutniKorisnik().getPassword(), model.getTrenutniKorisnik().getKorisnickoIme());
        }));
    }

    public void dodajPolje(ActionEvent actionEvent) {
        model.getKorisnici().add(new Korisnik());
        listView.getSelectionModel().selectLast();
        model.setTrenutniKorisnik(listView.getSelectionModel().getSelectedItem());
        btnValidate.getStyleClass().removeAll("sakrijiPolje");
        btnDodaj.getStyleClass().addAll("sakrijiPolje");
        btnDodaj.setVisible(false);
    }

    public void dodajKorisnika(ActionEvent actionEvent) {
            model.dodajKorisnika(model.getTrenutniKorisnik().getIme(), model.getTrenutniKorisnik().getPrezime(), model.getTrenutniKorisnik().getEmail(),
                    model.getTrenutniKorisnik().getKorisnickoIme(), model.getTrenutniKorisnik().getPassword());
            btnValidate.getStyleClass().addAll("sakrijiPolje");
            btnDodaj.getStyleClass().removeAll("sakrijiPolje");
            btnDodaj.setVisible(true);
            listView.refresh();
    }

    public void obrisiKorisnika(ActionEvent actionEvent) {
        model.obrisiKorinika(listView.getSelectionModel().getSelectedItem().getKorisnickoIme());
        listView.refresh();
        model.getKorisnici().remove(model.getTrenutniKorisnik());
        listView.refresh();
    }

    public void krajPrograma(ActionEvent actionEvent) { System.exit(0); }
}
