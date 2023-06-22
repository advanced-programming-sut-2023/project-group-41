package stronghold.view.graphics;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Objects;

public class LoginMenu extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader resourceFile = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(
                "src/main/java/stronghold/database/graphical/resources/loginView.fxml")));
        Parent root = resourceFile.load();
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
    }
}
