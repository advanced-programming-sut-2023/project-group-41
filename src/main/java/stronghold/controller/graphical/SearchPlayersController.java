package stronghold.controller.graphical;

import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;
import stronghold.model.components.general.User;
import stronghold.model.database.UsersDB;

import java.io.IOException;
import java.util.Objects;

public class SearchPlayersController {
    @FXML
    TextField userId;


    public void searchPlayers(ActionEvent Event) {
        String username = userId.getText();
        User user = UsersDB.usersDB.getUserByUsername(username);
        if (user == null){
            userId.clear();
        } else {
            GraphicalProfileCartController.setUser(user);
            PauseTransition pauseTransition = new PauseTransition(Duration.millis(30));
            pauseTransition.setOnFinished(actionEvent -> {
                Pane root = null;
                try {
                    root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/ProfileCartView.fxml")));
                } catch (IOException ignored) {
                }
                Scene scene = userId.getScene();
                Stage stage = (Stage) scene.getWindow();
                stage.setScene(new Scene(root));
                stage.show();
            });
            pauseTransition.play();
        }
    }

}
