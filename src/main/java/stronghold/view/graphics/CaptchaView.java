package stronghold.view.graphics;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import stronghold.controller.graphical.GraphicalCaptchaController;
import stronghold.model.components.general.GraphicalCaptcha;

public class CaptchaView extends Application {

    Dialog<Boolean> dialog = new Dialog<>();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

        dialog.setOnCloseRequest(event -> {
            dialog.setResult(true);
            dialog.close();
        });

        primaryStage.setTitle("Captcha...");
        Pane root = new Pane();
        Scene scene = new Scene(root,700,200);
        GraphicalCaptcha graphicalCaptcha = new GraphicalCaptcha();
        graphicalCaptcha.graphicalCaptcha.setTranslateX(20);
        graphicalCaptcha.graphicalCaptcha.setTranslateY(20);
        root.getChildren().add(graphicalCaptcha.graphicalCaptcha);
        TextField textField = new TextField();
        textField.setTranslateY(100);
        textField.setTranslateX(200);
        textField.setPrefSize(200,20);
        textField.setPromptText("Enter Captcha...");
        Button submit = new Button();
        submit.setTranslateY(100);
        submit.setTranslateX(420);
        submit.setText("Submit");
        submit.setOnMouseClicked(mouse -> {
            boolean result = 
                    GraphicalCaptchaController.controlSubmission(textField.getText(), graphicalCaptcha.answer);
            submit.setTextFill(Color.LIGHTGRAY);

            dialog.setContentText("NIGGA");

            if(result){
                submit.setStyle("-fx-background-color: #008800;");
            }
            else{
                submit.setStyle("-fx-background-color: #aa0000;");
            }


            try {
                dialog.setResult(true);
                dialog.close(); // Close the dialog before showing it
                dialog.showAndWait();
            } finally {
                dialog.close();
            }


        });
        root.getChildren().addAll(textField,submit);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
