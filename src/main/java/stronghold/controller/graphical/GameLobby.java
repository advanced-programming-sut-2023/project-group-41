package stronghold.controller.graphical;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameLobby extends Application {
    // List of games
    private List<Game> games = new ArrayList<>();

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Game Lobby");

        // Create some example games
        createExampleGames();

        // Create UI controls
        Label titleLabel = new Label("Game Lobby");

        Label searchLabel = new Label("Search for game:");
        TextField searchField = new TextField();
        Button searchButton = new Button("Search");

        VBox gamesVBox = new VBox();
        gamesVBox.setAlignment(Pos.CENTER_LEFT);
        gamesVBox.setSpacing(10);

        for (Game game : games) {
            gamesVBox.getChildren().add(createGameListElement(game));
        }

        Button refreshButton = new Button("Refresh");

        // Create UI layout
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.TOP_CENTER);
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(25, 25, 25, 25));
        gridPane.add(titleLabel, 0, 0);
        gridPane.add(searchLabel, 0, 1);
        gridPane.add(searchField, 1, 1);
        gridPane.add(searchButton, 2, 1);
        gridPane.add(gamesVBox, 0, 2, 3, 1);
        gridPane.add(refreshButton, 2, 3);

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

            for (Game game : games) {
                if (game.getTitle().toLowerCase().contains(searchTerm.toLowerCase())) {
                    gamesVBox.getChildren().add(createGameListElement(game));
                }
            }
        });

        refreshButton.setOnAction(event -> {
            refreshGameList();
        });

        // Show UI
        Scene scene = new Scene(gridPane, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * Creates example games for the lobby
     */
    private void createExampleGames() {
        Random rand = new Random();

        for (int i = 1; i <= 10; i++) {
            Game game = new Game("Game " + i, rand.nextInt(10), rand.nextBoolean(), "Host " + i);
            games.add(game);
        }
    }

    /**
     * Creates a UI element for a game in the list
     */
    private HBox createGameListElement(Game game) {
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
    static class Game {
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
