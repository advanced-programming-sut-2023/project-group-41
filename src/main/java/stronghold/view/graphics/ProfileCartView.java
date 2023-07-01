package stronghold.view.graphics;

import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;
import stronghold.controller.graphical.ChatMenuController;
import stronghold.model.components.general.User;
import stronghold.model.database.UsersDB;
import stronghold.model.utils.network.server.StaticClient;

import java.io.IOException;
import java.util.Objects;

public class ProfileCartView extends Application {
    public Pane root;

    public static void main(String[] args) {
        User user = UsersDB.usersDB.getUserByUsername("nima");
        ChatMenuController.setUser(user);
        PauseTransition pauseTransition = new PauseTransition(Duration.millis(100));
        pauseTransition.setOnFinished(actionEvent -> {

        });
        pauseTransition.play();
        launch(args);

    }


    @Override
    public void start(Stage primaryStage) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/ChatMenu.fxml")));
        Scene currentScene = new Scene(root);
        primaryStage.setScene(currentScene);
        primaryStage.show();
    }

}
