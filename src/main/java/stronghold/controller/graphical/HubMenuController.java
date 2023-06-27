package stronghold.controller.graphical;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import stronghold.controller.ProfileMenuController;
import stronghold.model.components.general.User;
import stronghold.view.graphics.LoginView;
import stronghold.view.graphics.ProfileEditView;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Duration;
import java.util.Objects;

public class HubMenuController {

    private static User currentUser;

    public static User getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentUser(User user) {
        currentUser = user;
    }


    @FXML
    Button logOutButton, startNewGameButton, profileMenuButton;
    @FXML
    Label usernameLabel;

    public void logOut() {
        JsonElement prefsElement;
        String pathToPrefs = "src/main/java/stronghold/database/datasets/preferences.json";
        try {
            prefsElement = JsonParser.parseReader(
                    new FileReader(pathToPrefs));
        } catch (
                FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        try {
            String toBeWritten = prefsElement.toString();
            toBeWritten = toBeWritten.replace(currentUser.getUsername(), "!NULLUSER");
            FileWriter fileWriter = new FileWriter(pathToPrefs);
            fileWriter.write(toBeWritten);
            fileWriter.close();
        } catch (
                IOException e) {
            throw new RuntimeException(e);
        }
        Pane root = null;
        try {
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/loginView.fxml")));
        } catch (
                IOException e) {
            throw new RuntimeException(e);
        }

        Scene scene = new Scene(root);
        Stage stage = (Stage) logOutButton.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    public void startNewGame() {
        // TODO: wire up game menu
    }

    public void openProfileMenu() {
        ProfileEditController.setCurrentUser(currentUser);
        // TODO: Make profile menu
    }

    @FXML
    public void initialize() {
        String username = currentUser.getUsername();
        usernameLabel.setText(username);
    }

    @FXML
    public void searchPlayers(ActionEvent actionEvent) {
        Stage stage = new Stage();
        Pane root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/searchPlayerView.fxml"));
        } catch (
                IOException e) {
            throw new RuntimeException(e);
        }
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
