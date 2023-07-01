package stronghold.controller.graphical;

import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.StrokeType;
import javafx.stage.Stage;
import javafx.util.Duration;
import stronghold.model.components.general.User;
import stronghold.model.utils.network.server.StaticClient;
import stronghold.model.utils.network.seth.Client;
import stronghold.model.utils.network.seth.RequestObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class FriendReqListController {
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


        Button acceptButton = new Button("Accept");
        acceptButton.setOnMouseClicked(mouseEvent -> {
            RequestObject requestObject=new RequestObject("acceptFriendReq", user1, user);
            client.sendObjectToServer(requestObject);
            client.recieveMessgeFromHost();
            playersVBox.getChildren().remove(hBox);
        });

        Button rejectButton = new Button("Reject");
        rejectButton.setOnMouseClicked(mouseEvent -> {
            RequestObject requestObject=new RequestObject("rejectFriendReq",user1,user);
            client.sendObjectToServer(requestObject);
            client.recieveMessgeFromHost();
            playersVBox.getChildren().remove(hBox);
        });

        hBox.getChildren().addAll(username,acceptButton, rejectButton);
        return hBox;
    }

    @FXML
    public void initialize(){
        RequestObject requestObject=new RequestObject("showFriendReq",user);
        client.sendObjectToServer(requestObject);
        ArrayList<User> friends=(ArrayList<User>) client.recieveObjectFromHost();

        for (User friend : friends) {
            playersVBox.getChildren().add(makeUserHBox(friend));
        }
    }
    public static User getUser() {
        return user;
    }

    public static void setUser(User user) {
        FriendReqListController.user = user;
    }

    public static Client getClient() {
        return client;
    }

    public static void setClient(Client client) {
        FriendReqListController.client = client;
    }



}
