package stronghold.view.graphics;

import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;
import stronghold.controller.graphical.GraphicalProfileCartController;
import stronghold.model.components.game.enums.Direction;
import stronghold.model.components.general.User;

import java.io.IOException;
import java.util.Objects;

public class ProfileCartView extends Application {
    public Pane root;

    public static void main(String[] args) {
        User user = new User("nima", "adf", "nim", "mjahfdahlkfjhdklhflsh", 5, "kosnnt", "kobs");
        GraphicalProfileCartController.setUser(user);
        PauseTransition pauseTransition = new PauseTransition(Duration.millis(30));
//        pauseTransition.setOnFinished(actionEvent -> {
//
//        });
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
