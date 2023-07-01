package stronghold.controller.graphical;

import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.util.Duration;
import stronghold.model.components.chatrooms.Room;
import stronghold.model.components.general.User;
import stronghold.model.components.lobby.Game;
import stronghold.model.database.GamesDB;
import stronghold.model.database.RoomsDB;
import stronghold.model.utils.network.server.StaticClient;
import stronghold.model.utils.network.seth.Client;
import stronghold.model.utils.network.seth.RequestObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

public class LobbyController {
    private static User user;

    public static User getUser() {
        return user;
    }

    public static void setUser(User user) {
        LobbyController.user = user;
    }

    private String currentGame;

    public static Client getClient() {
        return client;
    }

    public static void setClient(Client client) {
        LobbyController.client = client;
    }

    static Client client;
    @FXML
     Button search;
    @FXML
    VBox players;
    @FXML
      TextField searchField;
    @FXML
     Button refresh;
    @FXML
    Button start;
    @FXML
    Button create;
    @FXML
    Label startGameLabel;
    @FXML
    TextField usernameField;
    @FXML
     CheckBox pri;

    @FXML
     TextField chatTextField;
    @FXML
     TextArea chatTextArea;
    @FXML
     TextField cap;
    @FXML
    Label gameOfPlayers;
    @FXML
    Pane games;
    public static Stage stage;

    public static void setStage(Stage stage) {
        LobbyController.stage = stage;
    }

    @FXML
    public void initialize() throws IOException {
        System.out.println(user.getUsername());
    }
    public  void createNewGame(){

        RequestObject requestObject=new RequestObject("createANewLobbyGame",usernameField.getText(),Integer.parseInt(cap.getText()),pri.isSelected(),user);
        client.sendObjectToServer(requestObject);
        client.recieveMessgeFromHost();


    }

    @FXML
    public void refresh(ActionEvent actionEvent){
        games.getChildren().clear();
        RequestObject requestObject=new RequestObject("getGamesList");
        client.sendObjectToServer(requestObject);
        ArrayList<Game> gameArrayList;
        gameArrayList= (ArrayList<Game>) client.recieveObjectFromHost();
        for (Game game : gameArrayList) {
            if(!game.isPrivate()){
                Label label=new Label(game.getTitle()+",cap: "+game.getCapacity());
                for (User gameUser : game.getUsers()) {
                    label.setText(label.getText()+"   "+gameUser.getNickname());
                }

                label.setTextFill(Color.BLACK);
                games.getChildren().add(label);
                System.out.println(label.getText()+",cap: "+game.getCapacity());
                Button button=new Button("join");
                button.setOnAction( ActionEvent  -> join(game.getTitle()));
                games.getChildren().add(button);

            }
        }
    }
    public void search(ActionEvent actionEvent){
        games.getChildren().clear();
        RequestObject requestObject=new RequestObject("getGamesList");
        client.sendObjectToServer(requestObject);
        ArrayList<Game> gameArrayList;
        gameArrayList= (ArrayList<Game>) client.recieveObjectFromHost();
        for (Game game : gameArrayList) {
            if(searchField.getText().equals(game.getTitle())){
                Label label=new Label(game.getTitle()+",cap: "+game.getCapacity());
                label.setTextFill(Color.BLACK);
                games.getChildren().add(label);
                System.out.println(label.getText()+",cap: "+game.getCapacity());
                Button button=new Button("join");
                button.setOnAction( ActionEvent  -> join(game.getTitle()));
                games.getChildren().add(button);
                break;
            }
        }

    }
    public void leave(ActionEvent actionEvent){
        RequestObject requestObject=new RequestObject("removeFromPlayersList",user,gameOfPlayers.getText());
        client.sendObjectToServer(requestObject);

        players.getChildren().clear();
        currentGame="";

    }
    public void join(String string){
        RequestObject requestObject=new RequestObject("joiningAGame",string,user);
        client.sendObjectToServer(requestObject);
        client.recieveMessgeFromHost();
        System.out.println();


        currentGame = string;
        gameOfPlayers.setText(string);
        System.out.println(currentGame);

    }

    public void updatePlayers(ActionEvent actionEvent){
        players.getChildren().clear();
        RequestObject requestObject=new RequestObject("updatingPlayersInSession",currentGame);
        client.sendObjectToServer(requestObject);
        client.recieveMessgeFromHost();
        Label label=new Label(" ");
        label.setText(client.recieveMessgeFromHost());
        players.getChildren().add(label);
    }
    public void startGame(){
        RequestObject requestObject=new RequestObject("startGame",currentGame,user.getUsername());
        client.sendObjectToServer(requestObject);
        boolean isHost;
        isHost=(boolean) client.recieveObjectFromHost();
        if(!isHost){
            startGameLabel.setText("you are not the host");
        }


    }


    public void enterChatRoomsHandler(ActionEvent actionEvent) {
        ChatMenuController.setUser(user);
        ChatMenuController.setClient(client);
        PauseTransition pauseTransition = new PauseTransition(Duration.millis(30));
        pauseTransition.setOnFinished(actionEvent1 -> {
            Pane root = null;
            try {
                root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/ChatMenu.fxml")));
            } catch (IOException ignored) {}
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        });
        pauseTransition.play();
    }
}
