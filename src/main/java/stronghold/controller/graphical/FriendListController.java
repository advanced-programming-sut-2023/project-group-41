package stronghold.controller.graphical;

import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import stronghold.model.components.general.User;
import stronghold.model.utils.network.seth.Client;
import stronghold.model.utils.network.seth.RequestObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class FriendListController {

        private static User user;
        private static Client client;
        public VBox playersVBox;

        private HBox makeUserHBox(User user1){
            HBox hBox = new HBox();
            hBox.setPrefSize(420, 45);
            hBox.setSpacing(40);
            hBox.setPadding(new Insets(10));

            Label username = new Label(user1.getUsername());
            username.setPrefSize(138, 52);
            username.setOnMouseClicked(mouseEvent -> {
                GraphicalProfileCardController.setUser(user1);
                GraphicalProfileCardController.setClient(client);
                GraphicalProfileCardController.setLoggedInUser(user);
                PauseTransition pauseTransition = new PauseTransition(Duration.millis(30));
                pauseTransition.setOnFinished(actionEvent -> {
                    Pane root = null;
                    try {
                        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/profileCardView.fxml")));
                    } catch (IOException ignored) {}
                    Scene scene = playersVBox.getScene();
                    Stage stage = (Stage) scene.getWindow();
                    stage.setScene(new Scene(root));
                    stage.show();
                });
                pauseTransition.play();
            });


            hBox.getChildren().addAll(username);
            return hBox;
        }

        @FXML
        public void initialize(){
            RequestObject requestObject=new RequestObject("showFriends",user);
            client.sendObjectToServer(requestObject);
            ArrayList<User> friends=(ArrayList<User>) client.recieveObjectFromHost();

            for (User friend : friends) {
                playersVBox.getChildren().add(makeUserHBox(friend));
            }
        }
        public static User getUser() {
            return user;
        }


        public static Client getClient() {
            return client;
        }

    public static void setUser(User user) {
        FriendListController.user = user;
    }

    public static void setClient(Client client) {
        FriendListController.client = client;
    }
}
