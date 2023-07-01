package stronghold.controller.graphical;

import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import stronghold.model.components.general.User;
import stronghold.model.database.UsersDB;
import stronghold.model.utils.network.seth.Client;
import stronghold.model.utils.network.seth.RequestObject;

import java.io.IOException;
import java.util.Objects;

public class SearchPlayersController {
    @FXML
    TextField userId;
    static Client client;
    private static User loggedInUser;
    public Client getClient() {
        return client;
    }

    public static User getLoggedInUser() {
        return loggedInUser;
    }

    public static void setLoggedInUser(User loggedInUser) {
        SearchPlayersController.loggedInUser = loggedInUser;
    }

    public static void setClient(Client client) {
        SearchPlayersController.client = client;
    }

    public void searchPlayers(ActionEvent Event) throws InterruptedException {
        String username = userId.getText();
        client.sendObjectToServer(new RequestObject("getuser", username));
        Thread.sleep(1000);
        User user = (User) client.recieveObjectFromHost();
        if (user == null){
            userId.clear();
        } else {
            GraphicalProfileCardController.setUser(user);
            GraphicalProfileCardController.setClient(client);
            GraphicalProfileCardController.setLoggedInUser(loggedInUser);
            PauseTransition pauseTransition = new PauseTransition(Duration.millis(30));
            pauseTransition.setOnFinished(actionEvent -> {

            });
            pauseTransition.play();
            Pane root = null;
            try {
                root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/profileCardView.fxml")));
            } catch (IOException ignored) {
            }
            Scene scene = userId.getScene();
            Stage stage = (Stage) scene.getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        }
    }

}
