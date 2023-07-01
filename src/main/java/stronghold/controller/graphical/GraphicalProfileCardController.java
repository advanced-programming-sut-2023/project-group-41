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
import stronghold.model.utils.network.server.StaticClient;
import stronghold.model.utils.network.seth.Client;
import stronghold.model.utils.network.seth.RequestObject;
import stronghold.view.graphics.HubMenuView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class GraphicalProfileCardController {
    public static User user;

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
    private  User loggedInUser;
    public Client client;

    public void setLoggedInUser(User loggedInUser) {
        this.loggedInUser = loggedInUser;
    }

    public static void setUser(User user) {
        GraphicalProfileCardController.user = user;
    }

    @FXML
    public void initialize(){
        StaticClient staticClient = null;
        try {
            staticClient = new StaticClient();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        client = staticClient.getClient();
        loggedInUser.set(HubMenuController.getCurrentUser());
        usernameLabel.setText(user.getUsername());
        nicknameLabel.setText(user.getNickname());
        emailLabel.setText(user.getEmail());
        sloganLabel.setText(user.getSlogan());
    }

    public void sendFriendReqHandler(MouseEvent mouseEvent) {
        RequestObject requestObject=new RequestObject("sendFriendReq",user,loggedInUser);
        client.sendObjectToServer(requestObject);
        client.recieveMessgeFromHost();


    }

    public void showScoreboardHandler(MouseEvent mouseEvent) {
        GraphicalScoreboardController.setUser(user);
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
    /////////////////////////////////
    public void acceptFriendReq(MouseEvent mouseEvent) {
        RequestObject requestObject=new RequestObject("acceptFriendReq",user,loggedInUser);
        client.sendObjectToServer(requestObject);
        client.recieveMessgeFromHost();


    }
    public void rejectFriendReq(MouseEvent mouseEvent) {
        RequestObject requestObject=new RequestObject("rejectFriendReq",user,loggedInUser);
        client.sendObjectToServer(requestObject);
        client.recieveMessgeFromHost();


    }
    public void showFriendReq(MouseEvent mouseEvent) {
        RequestObject requestObject=new RequestObject("acceptFriendReq",loggedInUser);
        client.sendObjectToServer(requestObject);
        ArrayList<User>friends=(ArrayList<User>) client.recieveObjectFromHost();
        //TODO: popups pf friends name accept/reject button



    }
}
