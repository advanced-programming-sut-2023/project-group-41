package stronghold;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Dialog;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.PopupWindow;
import javafx.stage.Stage;
import stronghold.model.components.general.Captcha;
import stronghold.model.components.general.GraphicalCaptcha;
import stronghold.view.graphics.CaptchaView;
import stronghold.view.graphics.LoginView;

import java.nio.channels.AcceptPendingException;
import java.util.Scanner;

public class MainTest extends Application {

    public static void main(String[] args) {
        LoginView.main(args);
    }

    @Override
    public void start(Stage primaryStage) {

    }
}
