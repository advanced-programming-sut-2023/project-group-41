package stronghold.view.graphics;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import stronghold.Main;

import java.net.URL;
import java.util.Objects;

public class LoginView extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Pane root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/loginView.fxml")));
        Scene currentScene = new Scene(root);
        primaryStage.setScene(currentScene);
        primaryStage.show();
    }
}
