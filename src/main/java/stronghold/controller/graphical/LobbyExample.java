package stronghold.controller.graphical;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class LobbyExample extends Application {
    private List<GameLobby.Game> games = new ArrayList<>();

    @Override
    public void start(Stage stage) throws Exception {
        BorderPane root = new BorderPane();

        // Create a VBox for the header
        VBox headerVBox = new VBox();
        headerVBox.setPadding(new Insets(10, 20, 10, 20));
        Label headerLabel = new Label("Counter-Strike Lobby");
        headerLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");
        headerVBox.getChildren().add(headerLabel);
        root.setTop(headerVBox);

        // Create a chat box
        VBox chatVBox = new VBox();
        chatVBox.setPadding(new Insets(10, 20, 10, 20));
        Label chatLabel = new Label("Chat");
        chatLabel.setStyle("-fx-font-size: 18px;");
        TextArea chatTextArea = new TextArea();
        chatTextArea.setPrefHeight(200);
        chatTextArea.setEditable(false);
        TextField chatTextField = new TextField();
        chatTextField.setPromptText("Type your message here...");
        chatTextField.setOnAction(e -> {
            chatTextArea.appendText(chatTextField.getText() + "\n");
            chatTextField.clear();
        });
        chatVBox.getChildren().addAll(chatLabel, chatTextArea, chatTextField);
        root.setCenter(chatVBox);

        // Create a VBox for the list of players
        VBox playersVBox = new VBox();
        playersVBox.setPadding(new Insets(10, 20, 10, 20));
        Label playersLabel = new Label("Players");
        playersLabel.setStyle("-fx-font-size: 18px;");
        ListView<String> playersListView = new ListView<>();
        playersListView.getItems().addAll("Player1", "Player2", "Player3", "Player4", "Player5");
        playersListView.setPrefHeight(200);
        playersVBox.getChildren().addAll(playersLabel, playersListView);
        root.setLeft(playersVBox);

        // Create a VBox for the start game button
        VBox startButtonVBox = new VBox();
        startButtonVBox.setPadding(new Insets(10, 20, 10, 20));
        Button startButton = new Button("Start game");
        startButton.setPrefWidth(150);
        startButton.setOnAction(e -> {
            // TODO: Start the game
        });
        startButtonVBox.getChildren().add(startButton);
        root.setRight(startButtonVBox);


        // Create UI controls
        Label titleLabel = new Label("Title:");
        TextField titleField = new TextField();

        Label capacityLabel = new Label("Capacity:");
        Spinner<Integer> capacitySpinner = new Spinner<>(1, 100, 1);

        Label privacyLabel = new Label("Privacy:");
        ToggleGroup privacyGroup = new ToggleGroup();
        RadioButton publicButton = new RadioButton("Public");
        publicButton.setToggleGroup(privacyGroup);
        publicButton.setSelected(true);
        RadioButton privateButton = new RadioButton("Private");
        privateButton.setToggleGroup(privacyGroup);

        Label usernameLabel = new Label("Username:");
        TextField usernameField = new TextField();

        Label passwordLabel = new Label("Password:");
        PasswordField passwordField = new PasswordField();

        Button createButton = new Button("Create");

        // Create UI layout
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(25, 25, 25, 25));
        gridPane.add(titleLabel, 0, 0);
        gridPane.add(titleField, 1, 0);
        gridPane.add(capacityLabel, 0, 1);
        gridPane.add(capacitySpinner, 1, 1);
        gridPane.add(privacyLabel, 0, 2);
        gridPane.add(publicButton, 1, 2);
        gridPane.add(privateButton, 2, 2);
        gridPane.add(usernameLabel, 0, 3);
        gridPane.add(usernameField, 1, 3);
        gridPane.add(passwordLabel, 0, 4);
        gridPane.add(passwordField, 1, 4);
        gridPane.add(createButton, 1, 5);

        // Create action for create button
        createButton.setOnAction(event -> {
            String title = titleField.getText();
            int capacity = capacitySpinner.getValue();
            boolean isPrivate = privateButton.isSelected();
            String username = usernameField.getText();
            String password = passwordField.getText();


            // Print out the settings for confirmation
            System.out.println("Title: " + title);
            System.out.println("Capacity: " + capacity);
            System.out.println("Privacy: " + (isPrivate ? "Private" : "Public"));
            System.out.println("Username: " + username);
            System.out.println("Password: " + password);
        });

        // Show UI

        root.getChildren().add(gridPane);


        // Create some example games
        createExampleGames();

        // Create UI controls


        Label searchLabel = new Label("Search for game:");
        TextField searchField = new TextField();
        Button searchButton = new Button("Search");

        VBox gamesVBox = new VBox();
        gamesVBox.setAlignment(Pos.CENTER_LEFT);
        gamesVBox.setSpacing(10);

        for (GameLobby.Game game : games) {
            gamesVBox.getChildren().add(createGameListElement(game));
        }

        Button refreshButton = new Button("Refresh");

        // Create UI layout
        GridPane gridPane2 = new GridPane();
        gridPane2.setLayoutX(gridPane2.getLayoutX()+300);
        gridPane2.setLayoutY(gridPane2.getLayoutY()+500);


        gridPane2.setHgap(10);
        gridPane2.setVgap(10);
        gridPane2.setPadding(new Insets(25, 25, 25, 25));
        gridPane2.add(titleLabel, 0, 0);
        gridPane2.add(searchLabel, 0, 1);
        gridPane2.add(searchField, 1, 1);
        gridPane2.add(searchButton, 2, 1);
        gridPane2.add(gamesVBox, 0, 2, 3, 1);
        gridPane2.add(refreshButton, 2, 3);

        // Create actions for search button and refresh button
        searchButton.setOnAction(event -> {
            String searchTerm = searchField.getText().trim();

            if (searchTerm.isEmpty()) {
                // Show all games if no search term
                refreshGameList();
                return;
            }
            // Otherwise, search for games using the search term
            gamesVBox.getChildren().clear();

            for (GameLobby.Game game : games) {
                if (game.getTitle().toLowerCase().contains(searchTerm.toLowerCase())) {
                    gamesVBox.getChildren().add(createGameListElement(game));
                }
            }
        });

        refreshButton.setOnAction(event -> {
            refreshGameList();
        });

        // Show UI


        root.getChildren().add(gridPane2);
        //System.out.println(gridPane2.get());
        gridPane2.setPrefWidth(400);
        System.out.println(gridPane2.getWidth());



        stage.setScene(new Scene(root, 600, 400));
        stage.show();
    }
    /**
     * Creates example games for the lobby
     */
    private void createExampleGames() {
        Random rand = new Random();

        for (int i = 1; i <= 10; i++) {
            GameLobby.Game game = new GameLobby.Game("Game " + i, rand.nextInt(10), rand.nextBoolean(), "Host " + i);
            games.add(game);
        }
    }

    /**
     * Creates a UI element for a game in the list
     */
    private HBox createGameListElement(GameLobby.Game game) {
        HBox hbox = new HBox();
        hbox.setAlignment(Pos.CENTER_LEFT);
        hbox.setSpacing(10);

        Label titleLabel = new Label(game.getTitle());
        Label capacityLabel = new Label("Capacity: " + game.getCapacity());
        Label privacyLabel = new Label(game.isPrivate() ? "Private" : "Public");
        Label hostLabel = new Label("Hosted by: " + game.getHost());

        Button joinButton = new Button("Join");
        joinButton.setOnAction(event -> {
            System.out.println("Joined game: " + game.getTitle());
            // TODO: Implement join game logic here
        });

        hbox.getChildren().addAll(titleLabel, capacityLabel, privacyLabel, hostLabel, joinButton);

        return hbox;
    }

    /**
     * Refreshes the list of games
     */
    private void refreshGameList() {

    }

    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Represents a game in the lobby
     */
    private static class Game {
        private String title;
        private int capacity;
        private boolean isPrivate;
        private String host;

        public Game(String title, int capacity, boolean isPrivate, String host) {
            this.title = title;
            this.capacity = capacity;
            this.isPrivate = isPrivate;
            this.host = host;
        }

        public String getTitle() {
            return title;
        }

        public int getCapacity() {
            return capacity;
        }

        public boolean isPrivate() {
            return isPrivate;
        }

        public String getHost() {
            return host;
        }
    }


}

