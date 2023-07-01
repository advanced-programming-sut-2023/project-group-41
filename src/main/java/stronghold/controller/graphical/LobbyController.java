package stronghold.controller.graphical;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Popup;
import javafx.stage.Stage;
import stronghold.model.components.chatrooms.Room;
import stronghold.model.components.general.User;
import stronghold.model.components.lobby.Game;
import stronghold.model.database.GamesDB;
import stronghold.model.utils.network.server.StaticClient;
import stronghold.model.utils.network.seth.Client;
import stronghold.model.utils.network.seth.RequestObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class LobbyController {
    private static User user=new User("Sadf","ASdf","sdf","ASdfsdf",2,"23234","WEr");
    private String currentGame;
    private Client client;
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

        StaticClient staticClient = new StaticClient();
        client = staticClient.getClient();

    }
    public  void createNewGame(){
        RequestObject requestObject=new RequestObject("createANewLobbyGame",usernameField.getText(),Integer.parseInt(cap.getText()),pri.isSelected(),user);
        client.sendObjectToServer(requestObject);
        client.recieveObjectFromHost();


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
        try {
            isHost=(boolean) client.recieveObjectFromHost();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        if(!isHost){
            startGameLabel.setText("you are not the host");
        }


    }



}
