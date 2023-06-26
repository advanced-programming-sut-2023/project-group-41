package stronghold.controller.graphical;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.Window;
import stronghold.Main;
import stronghold.controller.MainMenuController;
import stronghold.model.components.general.User;
import stronghold.model.database.UsersDB;
import stronghold.model.utils.Encryption;
import stronghold.view.graphics.HubMenuView;

import java.io.IOException;
import java.util.Objects;

public class LoginController {
    @FXML
    TextField usernameField;
    @FXML
    PasswordField passwordField;
    @FXML
    Button loginButton, registerButton, recoverPasswordButton;

    public void openErrorDialog(String error){
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
        if(!MainMenuController.usernameFormatCorrect(usernameField.getText())){
            openErrorDialog("Error!: Username format incorrect!");
            return false;
        }
        return true;
    }

    @FXML
    public void enterRegisterMenu() throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/registerView.fxml")));
        Scene scene = new Scene(root);
        Stage stage = (Stage) registerButton.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void authenticate(){
        if(!checkStates()){
            return;
        }
        String username = usernameField.getText();
        String password = Encryption.toSHA256(passwordField.getText());
        User user = UsersDB.usersDB.getUserByUsername(username);
        if(user == null){
            openErrorDialog("Error!: Provided credentials are incorrect!");
            return;
        }
        boolean userAuthenticated =
                user.getPassword().equals(password);
        if(!userAuthenticated){
            openErrorDialog("Error!: Provided credentials are incorrect!");
        }
        else{
            Parent root = null;
            try {
                root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/hubMenuView.fxml")));
            } catch (
                    IOException e) {
                throw new RuntimeException(e);
            }
            HubMenuController.setCurrentUser(user);

            Scene scene = new Scene(root);
            Stage stage = (Stage) loginButton.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        }

    }


}
