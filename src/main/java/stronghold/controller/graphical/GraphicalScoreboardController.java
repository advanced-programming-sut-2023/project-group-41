package stronghold.controller.graphical;

import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.StrokeType;
import javafx.stage.Stage;
import javafx.util.Duration;
import stronghold.model.components.general.User;
import stronghold.model.utils.network.seth.Client;
import stronghold.model.utils.network.seth.RequestObject;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class GraphicalScoreboardController {
    private static Client client;
    private static User user;
    private static List<User> list = null;
    private static int rankEndingIdx = 10;
    public VBox playersVBox;
    public HBox userHbox;
    public Label scoreBoardLabel;

    public static void setUser(User user) {
        GraphicalScoreboardController.user = user;
    }

    public static Client getClient() {
        return client;
    }

    public static void setClient(Client client) {
        GraphicalScoreboardController.client = client;
    }

    private HBox makeUserHBox(User user1){
        HBox hBox = new HBox();
        hBox.setPrefSize(420, 45);
        hBox.setSpacing(40);
        hBox.setPadding(new Insets(10));

        Label rank = new Label(String.valueOf(list.indexOf(user1) + 1));
        rank.setPrefSize(42, 52);

        Label username = new Label(user1.getUsername());
        username.setPrefSize(138, 52);
        username.setOnMouseClicked(MouseEvent-> {
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

        Label score = new Label(String.valueOf(user1.getScore()));
        score.setPrefSize(93, 69);

        Circle onlineState = new Circle(12);
        // todo: change online state
        onlineState.setStroke(Color.BLACK);
        onlineState.setStrokeType(StrokeType.INSIDE);

        hBox.getChildren().addAll(rank, username, score, onlineState);
        return hBox;
    }
    @FXML
    public void initialize() throws IOException {
        RequestObject requestObject = new RequestObject("getSortedUsers");
        client.sendObjectToServer(requestObject);
        list = (List<User>) client.recieveObjectFromHost();

        userHbox.getChildren().add(makeUserHBox(user));
        if (rankEndingIdx > list.size())
            rankEndingIdx = list.size();

        for (User user1 : list.subList(0, rankEndingIdx)) {
            playersVBox.getChildren().add(makeUserHBox(user1));
        }

    }



    public void finishedScrollHandler(ScrollEvent scrollEvent) {
        if (rankEndingIdx + 10 > list.size())
            rankEndingIdx = list.size();
        else
            rankEndingIdx += 10;
        playersVBox.getChildren().clear();
        for (User user1 : list.subList(rankEndingIdx - 10, rankEndingIdx)) {
            playersVBox.getChildren().add(makeUserHBox(user1));
        }
    }

    public void refreshHandler(KeyEvent keyEvent) {
        if (keyEvent.getCode().equals(KeyCode.TAB)){
            RequestObject requestObject = new RequestObject("getSortedUsers");
            client.sendObjectToServer(requestObject);
            list = (List<User>) client.recieveObjectFromHost();


            playersVBox.getChildren().clear();
            for (User user1 : list.subList(rankEndingIdx - 10, rankEndingIdx)) {
                playersVBox.getChildren().add(makeUserHBox(user1));
            }
        }
    }
}
