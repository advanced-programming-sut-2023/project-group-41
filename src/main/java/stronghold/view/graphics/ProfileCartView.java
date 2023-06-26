package stronghold.view.graphics;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class ProfileCartView extends Application {
    public Pane root;

    public static void main(String[] args) {
        launch(args);
    }


    @Override
    public void start(Stage primaryStage) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/ProfileCartView.fxml")));
        Scene currentScene = new Scene(root);
        primaryStage.setScene(currentScene);
        primaryStage.show();
    }

}
