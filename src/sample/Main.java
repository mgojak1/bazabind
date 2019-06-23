package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        KorisnikModel model = KorisnikModel.getInstance();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("sample.fxml"));
        loader.setController(new Controller(model));
        Parent root = loader.load();
        primaryStage.setTitle("Adresar");
        primaryStage.setScene(new Scene(root, 550, 250));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
