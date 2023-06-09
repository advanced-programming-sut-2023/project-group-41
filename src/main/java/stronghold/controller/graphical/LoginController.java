package stronghold.controller.graphical;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.util.Duration;
import stronghold.Main;
import stronghold.controller.MainMenuController;
import stronghold.model.components.general.User;
import stronghold.model.database.UsersDB;
import stronghold.model.utils.Encryption;
import stronghold.model.utils.network.seth.Client;
import stronghold.model.utils.network.seth.RequestObject;
import stronghold.view.graphics.HubMenuView;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Objects;

public class LoginController {

    public static Client client;

    public static Client getClient() {
        return client;
    }

    public static void setClient(Client client) {
        LoginController.client = client;
    }

    @FXML
    public CheckBox stayLoggedInBox;
    @FXML
    TextField usernameField;
    @FXML
    PasswordField passwordField;
    @FXML
    Button loginButton, registerButton, recoverPasswordButton;

    public void openErrorDialog(String error) {
        Dialog<String> dialog = new Dialog<String>();
        dialog.setTitle("Error!");
        Label label = new Label(error);
        dialog.setContentText(label.getText());
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
        dialog.getDialogPane().getChildren().add(label);
        ButtonType type = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().add(type);
        Node closeButton = dialog.getDialogPane().lookupButton(ButtonType.CLOSE);
        closeButton.managedProperty().bind(closeButton.visibleProperty());
        closeButton.setVisible(false);
        dialog.showAndWait();
    }

    @FXML
    public boolean checkStates() {
        if (!MainMenuController.usernameFormatCorrect(usernameField.getText())) {
            openErrorDialog("Error!: Username format incorrect!");
            return false;
        }
        return true;
    }

    @FXML
    public void enterRegisterMenu() throws IOException {
        RegisterController.setClient(client);
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/registerView.fxml")));
        Scene scene = new Scene(root);
        Stage stage = (Stage) registerButton.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void authenticate() throws InterruptedException {
        if (!checkStates()) {
            return;
        }
        String username = usernameField.getText();
        String password = Encryption.toSHA256(passwordField.getText());

        String token = Encryption.toSHA256(username + password);
//
        client.sendObjectToServer(new RequestObject("authenticate", token));
        boolean authenticate = Boolean.parseBoolean(client.recieveMessgeFromHost());


        if (!authenticate) {
            openErrorDialog("Error!: Provided credentials are invalid!");
            return;
        }

        client.sendObjectToServer(new RequestObject("getuser", username));
        Thread.sleep(1000);
        User user = (User) client.recieveObjectFromHost();

        if (!authenticate) {
            openErrorDialog("Error!: Provided credentials are incorrect!");
        }
        else if(GraphicalCaptchaController.generateCaptcha()){
            if(stayLoggedInBox.isSelected()){
                Thread.sleep(1000);
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
                    toBeWritten = toBeWritten.replace("!NULLUSER",username);
                    FileWriter fileWriter = new FileWriter(pathToPrefs);
                    fileWriter.write(toBeWritten);
                    fileWriter.close();
                } catch (
                        IOException e) {
                    throw new RuntimeException(e);
                }
            }
            HubMenuController.setCurrentUser(user);
            HubMenuController.setClient(client);
            PauseTransition delay = new PauseTransition(Duration.millis(30));
            delay.setOnFinished(event -> {
                Parent root = null;
                try {
                    root = FXMLLoader.load(getClass().getResource("/hubMenuView.fxml"));
                } catch (
                        IOException ignored) {

                }
                Scene scene = new Scene(root);
                Stage stage = (Stage) loginButton.getScene().getWindow();
                stage.setScene(scene);
                stage.show();
            });
            delay.play();


        }

    }

    @FXML
    public void openPasswordRecoverMenu() throws IOException {
        Pane root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/passwordRecoverView.fxml")));
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.showAndWait();
    }


}
