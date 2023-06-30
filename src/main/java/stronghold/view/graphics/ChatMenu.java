package stronghold.view.graphics;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ChatMenu extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Create Game");

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

            // TODO: Create game with these settings
            // Here you would write the code to create a game using the settings entered by the user
            // You can use the variables title, capacity, isPrivate, username, and password to create the game
            // The implementation of this step depends on how you want to create the game and store the settings

            // Print out the settings for confirmation
            System.out.println("Title: " + title);
            System.out.println("Capacity: " + capacity);
            System.out.println("Privacy: " + (isPrivate ? "Private" : "Public"));
            System.out.println("Username: " + username);
            System.out.println("Password: " + password);
        });

        // Show UI
        Scene scene = new Scene(gridPane, 400, 300);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}