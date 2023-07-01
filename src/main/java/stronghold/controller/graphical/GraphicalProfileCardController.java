package stronghold.controller.graphical;

import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import stronghold.model.components.general.User;
import stronghold.model.utils.network.seth.Client;

import java.io.IOException;
import java.util.Objects;

public class GraphicalProfileCardController {
    public static User user;
    public static Client client;

    public ImageView avatarPic;
    public Label usernameLabel;
    public Label nicknameLabel;
    public Label emailLabel;
    public Label sloganLabel;
    public Button sendFriendReqButton;
    public Button showScoreboardButton;
    public HBox imageHBox;
    public HBox usernameHBox;
    public HBox nicknameHBox;
    public HBox emailHBox;
    public HBox sloganHbox;

    public static void setUser(User user) {
        GraphicalProfileCardController.user = user;
    }

    public static void setClient(Client client) {
        GraphicalProfileCardController.client = client;
    }

    @FXML
    public void initialize(){
        usernameLabel.setText(user.getUsername());
        nicknameLabel.setText(user.getNickname());
        emailLabel.setText(user.getEmail());
        sloganLabel.setText(user.getSlogan());
    }

    public void sendFriendReqHandler(MouseEvent mouseEvent) {

    }

    public void showScoreboardHandler(MouseEvent mouseEvent) {
        GraphicalScoreboardController.setUser(user);
        GraphicalScoreboardController.setClient(client);
        PauseTransition pauseTransition = new PauseTransition(Duration.millis(30));
        pauseTransition.setOnFinished(actionEvent -> {
            ScrollPane root = null;
            try {
                root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/ScoreboardView.fxml")));
            } catch (IOException ignored) {}
            Scene scene = sloganHbox.getScene();
            Stage stage = (Stage) scene.getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        });
        pauseTransition.play();
    }

    public void changeAvatarHandler(ActionEvent actionEvent) {
    }
}
