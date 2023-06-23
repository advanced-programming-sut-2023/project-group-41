package stronghold.view.graphics;

import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;
import stronghold.controller.graphical.GraphicalCaptchaController;
import stronghold.model.components.general.GraphicalCaptcha;

public class CaptchaView extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

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

            if(result){
                submit.setStyle("-fx-background-color: #008800;");
                primaryStage.close();
            }
            else{
                submit.setStyle("-fx-background-color: #aa0000;");
                delay(500, () -> {
                    submit.setTextFill(Color.BLACK);
                    submit.setStyle("-fx-background-color: #c1c1c1;");
                });
            }


        });
        root.getChildren().addAll(textField,submit);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void delay(long millis, Runnable continuation) {
        Task<Void> sleeper = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                try { Thread.sleep(millis); }
                catch (InterruptedException ignored) { }
                return null;
            }
        };
        sleeper.setOnSucceeded(event -> continuation.run());
        new Thread(sleeper).start();
    }
}
