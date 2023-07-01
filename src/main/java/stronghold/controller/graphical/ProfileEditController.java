package stronghold.controller.graphical;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import stronghold.Main;
import stronghold.controller.MainMenuController;
import stronghold.model.components.general.User;
import stronghold.model.database.UsersDB;
import stronghold.model.utils.Encryption;
import stronghold.model.utils.network.seth.Client;
import stronghold.model.utils.network.seth.RequestObject;

import java.io.*;
import java.security.PrivilegedExceptionAction;

public class ProfileEditController {
    public static Client client;
    public static User currentUser;

    public static Client getClient() {
        return client;
    }

    public static void setClient(Client client) {
        ProfileEditController.client = client;
    }

    public TextField usernameField;
    public TextField passwordField;
    public TextField sloganField;
    public TextField nicknameField;
    public TextField emailField;
    public Button saveButton;
    public Label errorPrompt;
    public PasswordField confirmPasswordField;

    public Button changeAvatar;
    public ImageView avatar;

    public static User getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentUser(User user) {
        currentUser = user;
    }

    @FXML
    public void initialize(){
        usernameField.setText(currentUser.getUsername());
        emailField.setText(currentUser.getEmail());
        sloganField.setText(currentUser.getSlogan());
        nicknameField.setText(currentUser.getNickname());
    }

    public boolean checkIfUsernameExists(String username){
        client.sendObjectToServer(new RequestObject("usernameexists", username));
        String response = client.recieveMessgeFromHost();
        return Boolean.parseBoolean(response);
    }

    public boolean checkIfEmailExists(String email){
        client.sendObjectToServer(new RequestObject("emailexists", email));
        String response = client.recieveMessgeFromHost();
        return Boolean.parseBoolean(response);
    }

    @FXML
    public boolean checkStates(){
        String newUsername = usernameField.getText();
        String newEmail = emailField.getText();

        boolean emailIsTaken = checkIfEmailExists(emailField.getText());
        boolean usernameExists = checkIfUsernameExists(usernameField.getText());

        if(newUsername.isEmpty()){
            errorPrompt.setText("Username not provided!");
            return false;
        }
        if(!MainMenuController.usernameFormatCorrect(newUsername)){
            errorPrompt.setText("Username format incorrect!");
            return false;
        }
        if(usernameExists && !newUsername.equals(currentUser.getUsername())){
            errorPrompt.setText("Entered Username is already taken!");
            return false;
        }
        if(newEmail.isEmpty()){
            errorPrompt.setText("Email not provided!");
            return false;
        }
        if(!MainMenuController.emailIsValid(newEmail)){
            errorPrompt.setText("Email format incorrect!");
            return false;
        }
        if(emailIsTaken && !newEmail.equals(currentUser.getEmail())){
            errorPrompt.setText("Email already exists!");
            return false;
        }
        if(sloganField.getText().isEmpty()){
            errorPrompt.setText("Slogan not provided!");
            return false;
        }
        if(nicknameField.getText().isEmpty()){
            errorPrompt.setText("Nickname not provided!");
            return false;
        }
        if(!passwordField.getText().isEmpty() && !MainMenuController.passwordIsStrong(passwordField.getText())){
            errorPrompt.setText("New password is weak!");
            return false;
        }
        errorPrompt.setText("");
        return true;
    }

    @FXML
    public void save(){
        if(!checkStates())
            return;
        if(!Encryption.toSHA256(confirmPasswordField.getText()).equals(currentUser.getPassword())){
            errorPrompt.setText("Old password is incorrect or not provided!");
            return;
        }
        if(!GraphicalCaptchaController.generateCaptcha())
            return;
        if(!currentUser.getUsername().equals(usernameField.getText())){
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
                toBeWritten = toBeWritten.replace(currentUser.getUsername(), usernameField.getText());
                FileWriter fileWriter = new FileWriter(pathToPrefs);
                fileWriter.write(toBeWritten);
                fileWriter.close();
            } catch (
                    IOException e) {
                
                throw new RuntimeException(e);
            }
        }
        PauseTransition delay = new PauseTransition(Duration.seconds(0.3));
        User oldUser = currentUser.clone();
        delay.setOnFinished(event -> {
            currentUser.setUsername(usernameField.getText());
            currentUser.setEmail(emailField.getText());
            currentUser.setSlogan(sloganField.getText());
            currentUser.setNickname(nicknameField.getText());
            if(!passwordField.getText().isEmpty()){
                currentUser.setPassword(Encryption.toSHA256(passwordField.getText()));
            }
            client.sendObjectToServer(new RequestObject("edituser",  oldUser, currentUser));

            System.out.println(client.recieveMessgeFromHost());

        });
        delay.play();

        HubMenuController.setCurrentUser(currentUser);
    }

    @FXML
    public void setAvatar(){
        FileChooser fileChooser = new FileChooser();
        File img = fileChooser.showOpenDialog(new Stage());
        avatar.setImage(new Image(img.getAbsolutePath()));
    }
}
