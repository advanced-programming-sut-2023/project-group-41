package stronghold.controller.graphical;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import stronghold.controller.MenuController;
import stronghold.controller.SignUpMenuController;
import stronghold.model.components.general.User;
import stronghold.model.database.UsersDB;
import stronghold.model.utils.Encryption;
import stronghold.model.utils.network.server.StaticClient;
import stronghold.model.utils.network.seth.Client;
import stronghold.model.utils.network.seth.RequestObject;
import stronghold.view.graphics.LoginView;
import stronghold.view.graphics.RegisterView;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class RegisterController{

    public void openErrorDialog(String error) {
        Dialog<String> dialog = new Dialog<String>();
        dialog.setTitle("Notification!");
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

    public void connect() throws IOException {
        Pane pane = new Pane();
        Label label = new Label("Connecting to server...");
        pane.getChildren().add(label);
        Stage dialog = new Stage();
        dialog.setTitle("Connecting...");
        dialog.setScene(new Scene(pane,300,50));
        try {
            Thread.sleep(1000);
        } catch (
                InterruptedException e) {
            throw new RuntimeException(e);
        }
        dialog.show();
        try {
            Thread.sleep(1000);
        } catch (
                InterruptedException e) {
            throw new RuntimeException(e);
        }
        try {
            if(client == null){
                StaticClient staticClient = new StaticClient();
                client = staticClient.getClient();
            }
        } catch (Exception e){
            dialog.close();
            openErrorDialog("Unable to connect");
            System.exit(0);
            return;
        }
        dialog.close();
    }

    @FXML
    public void initialize() throws IOException {
        if(client == null)
            connect();
    }

    StaticClient staticClient;
    public static Client client;

    public static Client getClient() {
        return client;
    }

    public static void setClient(Client client) {
        RegisterController.client = client;
    }

    @FXML
    public AnchorPane root;
    @FXML
    public TextField usernameField, passwordField, confirmField, sloganField, nicknameField, emailField;
    @FXML
    public Button registerButton, goToLoginButton, randPassButton, randSloganButton;
    @FXML
    public Label errorPrompt;
    @FXML
    public MenuItem questionItem1, questionItem2, questionItem3;
    @FXML
    public MenuButton questionPicker;
    @FXML
    public TextField securityAnswer, securityConfirm;

    int questionPicked = 0;

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

    public boolean checkStates(){
        boolean usernameFormatCorrect = MenuController.usernameFormatCorrect(usernameField.getText());
        boolean passwordIsStrong = MenuController.passwordIsStrong(passwordField.getText());
        boolean emailFormatCorrect = MenuController.emailIsValid(emailField.getText());
        boolean emailIsTaken = checkIfEmailExists(emailField.getText());
        boolean usernameExists = checkIfUsernameExists(usernameField.getText());

        errorPrompt.setOpacity(1);

        if(usernameField.getText().isEmpty()){
            errorPrompt.setText("Username field is blank");
            return false;
        }
        if(passwordField.getText().isEmpty()){
            errorPrompt.setText("Password field is blank");
            return false;
        }
        if(confirmField.getText().isEmpty()){
            errorPrompt.setText("Confirmation field is blank");
            return false;
        }
        if(sloganField.getText().isEmpty()){
            errorPrompt.setText("Slogan field is blank");
            return false;
        }
        if(nicknameField.getText().isEmpty()){
            errorPrompt.setText("Nickname field is blank");
            return false;
        }
        if(emailField.getText().isEmpty()){
            errorPrompt.setText("Email field is blank");
            return false;
        }
        if(!passwordField.getText().equals(confirmField.getText())){
            errorPrompt.setText("Password and it's confirmation do not match!");
            return false;
        }
        if(!usernameFormatCorrect){
            errorPrompt.setText("Username format is Invalid!");
            return false;
        }
        if(!passwordIsStrong){
            errorPrompt.setText("Password is weak!");
            return false;
        }
        if(!emailFormatCorrect){
            errorPrompt.setText("Email format is invalid!");
            return false;
        }
        if(emailIsTaken){
            errorPrompt.setText("Email Already in use!");
            return false;
        }
        if(usernameExists){
            errorPrompt.setText("Username exists!");
            return false;
        }
        if(questionPicked == 0){
            errorPrompt.setText("Security question not picked!");
            return false;
        }
        if(securityAnswer.getText().isEmpty()){
            errorPrompt.setText("Security question not answered!");
            return false;
        }
        if(securityConfirm.getText().isEmpty()){
            errorPrompt.setText("Security question's answer not confirmed!");
            return false;
        }
        if(!securityAnswer.getText().equals(securityConfirm.getText())){
            errorPrompt.setText("Security question's answer and its confirmation do not match!");
            return false;
        }

        errorPrompt.setOpacity(0);
        return true;
    }
    public void onFieldChange(KeyEvent inputMethodEvent) {
        checkStates();
    }

    public void setRandomPass(){
        String password = SignUpMenuController.generateRandomString();
        while(!SignUpMenuController.passwordIsStrong(password)){
            password = SignUpMenuController.generateRandomString();
        }
        passwordField.setText(password);
    }

    public void setRandomSlogan(){
        sloganField.setText(SignUpMenuController.chooseRandomSlogan());
    }

    public void pickQuestion1(){
        questionPicker.setText(questionItem1.getText());
        questionPicked = 1;
    }
    public void pickQuestion2(){
        questionPicker.setText(questionItem2.getText());
        questionPicked = 2;
    }
    public void pickQuestion3(){
        questionPicker.setText(questionItem3.getText());
        questionPicked = 3;
    }

    public void openLoginView() throws IOException {
        LoginController.setClient(client);
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/loginView.fxml")));
        Scene scene = new Scene(root);
        Stage stage = (Stage) goToLoginButton.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    public void registerUser() throws InterruptedException {
        if(checkStates()){
            if(GraphicalCaptchaController.generateCaptcha()){
                String username = usernameField.getText();
                String password = Encryption.toSHA256(passwordField.getText());
                String nickname = nicknameField.getText();
                String email = emailField.getText();
                int securityQuestion = questionPicked;
                String questionsAnswer = securityAnswer.getText();
                String slogan = sloganField.getText();

                User toBeAdded = new User(username,password,nickname,email,
                        securityQuestion,questionsAnswer,slogan);
                client.sendObjectToServer(new RequestObject("register", toBeAdded));
                client.recieveMessgeFromHost();
                Thread.sleep(100);
                try {
                    LoginController.setClient(client);
                    openLoginView();
                } catch (
                        IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

}
