package stronghold.view.graphics;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.stage.Stage;
import stronghold.controller.graphical.GraphicalScoreboardController;
import stronghold.model.database.UsersDB;

import java.io.IOException;
import java.util.Objects;

public class ScoreboardView extends Application {

    public static void main(String[] args) {
        GraphicalScoreboardController.setUser(UsersDB.usersDB.getUserByUsername("yoda"));
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        ScrollPane root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/ScoreboardView.fxml")));
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
