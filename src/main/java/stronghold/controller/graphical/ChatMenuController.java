package stronghold.controller.graphical;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Border;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import stronghold.model.components.chatrooms.Message;
import stronghold.model.components.chatrooms.Reaction;
import stronghold.model.components.chatrooms.Room;
import stronghold.model.components.general.User;
import stronghold.model.database.RoomsDB;
import stronghold.model.database.UsersDB;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ChatMenuController {
    private static User user;
    private static Room currRoom;

    public VBox roomNamesVBox;
    public VBox messagesVBox;
    public TextField roomNameTextField;
    public TextField messageTestField;
    public TextField newUserTextField;

    @FXML
    public void initialize(){

        ArrayList<Room> rooms = RoomsDB.getInstance().getUserRooms(user);
        for (Room room : rooms) {
            roomNamesVBox.getChildren().add(makeRoomNameLabel(room));
        }
        currRoom = RoomsDB.getInstance().getPubicChat();
    }

    public static User getUser() {
        return user;
    }

    public static void setUser(User user) {
        ChatMenuController.user = user;
    }

    private Label makeRoomNameLabel(Room room){
        Label label = new Label(room.getName());
        label.setPrefSize(145, 29);
        label.setBorder(Border.stroke(Color.BLACK));
        label.setOnMouseClicked(mouseEvent -> {
            currRoom = room;
            messagesVBox.getChildren().clear();
            for (Message message : room.getMessages()) {
                messagesVBox.getChildren().add(makeMessageHBox(message));
            }
        });
        return label;
    }

    private HBox makeMessageHBox(Message message){
        HBox rootHBox;
        ImageView userAvatar;
        Label usernameLabel;
        Label dateLabel;
        TextArea messageTextField;
        VBox vbox2;
        Label editLabel;
        Label hideLabel;
        Label delLabel;
        VBox emojiVBox;
        Label emojiLabel1;
        Label emojiLabel2;
        Label emojiLabel3;
        VBox otherVBox;
        Label laughLabel;
        Label peeLabel;
        Label heartLabel;
        Circle messageStateCircle;

        rootHBox = new HBox();
        rootHBox.setMaxHeight(53.0);
        rootHBox.setPrefWidth(358.0);
        rootHBox.setStyle("-fx-border-color: Gray; -fx-border-width: 2; -fx-border-radius: 30;");

        userAvatar = new ImageView();// todo: add user avatar
        userAvatar.setFitHeight(50.0);
        userAvatar.setFitWidth(50.0);
        userAvatar.setPickOnBounds(true);
        userAvatar.setPreserveRatio(true);

        VBox userVBox = new VBox();
        userVBox.setPrefHeight(53.0);
        userVBox.setPrefWidth(45.0);

        usernameLabel = new Label(message.getUser().getUsername());
        usernameLabel.setPrefHeight(30.0);
        usernameLabel.setPrefWidth(60.0);
        usernameLabel.setFont(new Font(14.0));

        dateLabel = new Label(message.getDate());
        dateLabel.setPrefHeight(18.0);
        dateLabel.setPrefWidth(62.0);
        dateLabel.setFont(new Font(10.0));

        userVBox.getChildren().addAll(usernameLabel, dateLabel);

        messageTextField = new TextArea(message.getText());
        messageTextField.setPrefHeight(53.0);
        messageTextField.setPrefWidth(212.0);

        vbox2 = new VBox();
        vbox2.setAlignment(javafx.geometry.Pos.CENTER);
        vbox2.setPrefHeight(53.0);
        vbox2.setPrefWidth(27.0);

        editLabel = new Label();
        editLabel.setOnMouseClicked(event -> {
            message.setText(messageTextField.getText());
        });
        editLabel.setStyle("-fx-border-color: gray;");
        editLabel.setText("Edit");

        hideLabel = new Label();
        hideLabel.setOnMouseClicked(event -> {
            //todo: handel hide message
            messagesVBox.getChildren().remove(rootHBox);
        });
        hideLabel.setStyle("-fx-border-color: gray;");
        hideLabel.setText("Hide");

        delLabel = new Label();
        delLabel.setOnMouseClicked(event -> {
            message.del();
            messagesVBox.getChildren().remove(rootHBox);
        });
        delLabel.setStyle("-fx-border-color: gray;");
        delLabel.setText("Del");

        vbox2.getChildren().addAll(editLabel, hideLabel, delLabel);

        otherVBox = new VBox();
        otherVBox.setPrefHeight(53.0);
        otherVBox.setPrefWidth(28.0);

        laughLabel = new Label(String.valueOf(message.getReaction(Reaction.LAUGH)));
        peeLabel = new Label(String.valueOf(message.getReaction(Reaction.PEE)));
        peeLabel.setPrefHeight(18.0);
        peeLabel.setPrefWidth(45.0);
        heartLabel = new Label(String.valueOf(message.getReaction(Reaction.HEART)));

        otherVBox.getChildren().addAll(laughLabel, peeLabel, heartLabel);

        emojiVBox = new VBox();
        emojiVBox.setPrefHeight(53.0);
        emojiVBox.setPrefWidth(16.0);

        emojiLabel1 = new Label("😂");
        emojiLabel1.setOnMouseClicked(event -> {
            message.incReactionByOne(Reaction.LAUGH);
            laughLabel.setText(String.valueOf(message.getReaction(Reaction.LAUGH)));
            emojiVBox.getChildren().clear();
        });

        emojiLabel2 = new Label("💩");
        emojiLabel2.setOnMouseClicked(event -> {
            message.incReactionByOne(Reaction.PEE);
            peeLabel.setText(String.valueOf(message.getReaction(Reaction.PEE)));
            emojiVBox.getChildren().clear();
        });

        emojiLabel3 = new Label("❤");
        emojiLabel3.setOnMouseClicked(event -> {
            message.incReactionByOne(Reaction.HEART);
            heartLabel.setText(String.valueOf(message.getReaction(Reaction.HEART)));
            emojiVBox.getChildren().clear();
        });

        emojiVBox.getChildren().addAll(emojiLabel1, emojiLabel2, emojiLabel3);

        messageStateCircle = new Circle();
        messageStateCircle.setRadius(12.0);
        messageStateCircle.setStrokeType(javafx.scene.shape.StrokeType.INSIDE);
        messageStateCircle.setStroke(javafx.scene.paint.Color.BLACK);
        HBox.setMargin(messageStateCircle, new Insets(12.0, 0.0, 12.0, 0.0));

        rootHBox.getChildren().addAll(userAvatar, userVBox, messageTextField,vbox2 ,emojiVBox, otherVBox, messageStateCircle);
        rootHBox.setPadding(new Insets(0.0, 0.0, 0.0, 10.0));

        return rootHBox;
    }


    public void addRoomHandler(ActionEvent actionEvent) {
        Room room = new Room(user, roomNameTextField.getText());
        roomNamesVBox.getChildren().add(makeRoomNameLabel(room));
    }

    public void sendMessageHandler(ActionEvent actionEvent) {
        SimpleDateFormat formatDate = new SimpleDateFormat("HH:mm");
        Date date = new Date();
        Message message = new Message(currRoom, user, messageTestField.getText(), formatDate.format(date));
        messagesVBox.getChildren().add(makeMessageHBox(message));
    }

    public void addNewUserHandler(ActionEvent actionEvent) {
        User newUser;
        if ((newUser = UsersDB.usersDB.getUserByUsername(newUserTextField.getText())) != null)
            currRoom.addUser(newUser);
    }

}
