package stronghold;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.util.Duration;
import stronghold.controller.GameMenuController;
import stronghold.controller.MainMenuController;
import stronghold.controller.SignUpMenuController;
import stronghold.controller.graphical.HubMenuController;
import stronghold.model.components.general.User;
import stronghold.model.database.UsersDB;
import stronghold.model.utils.StringParser;
import stronghold.model.utils.network.seth.Client;
import stronghold.model.utils.network.seth.Host;
import stronghold.model.utils.network.seth.RequestObject;
import stronghold.view.MainMenuView;
import stronghold.view.SignUpLoginView;
import stronghold.view.graphics.HubMenuView;
import stronghold.view.graphics.LoginView;
import stronghold.view.graphics.RegisterView;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class Main {

    /* !!!!!FOR THE SAKE OF F*CK, DO NOT COMMENT YOUR CODES!!!!! */


    public static void main(String[] args) throws IOException {
        JsonElement prefsElement;
        boolean startInGraphical = true;


        try {
            prefsElement = JsonParser.parseReader(
                    new FileReader("src/main/java/stronghold/database/datasets/preferences.json"));
        } catch (
                FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        String loggedinuser = StringParser.removeQuotes(
                String.valueOf(prefsElement.getAsJsonObject().get("logged-in user")));
        System.out.println(loggedinuser);
        
        if (startInGraphical) {

            if (loggedinuser.equals("!NULLUSER")) {

                RegisterView.main(args);
            } else {
                Client client = new Client();
                client.sendObjectToServer(new RequestObject("getuser", loggedinuser));
                User currentUser = (User) client.recieveObjectFromHost();
                client.kill();
                HubMenuController.setCurrentUser(currentUser);
                HubMenuView.main(args);
            }

        } else {
            Scanner scanner = new Scanner(System.in);

            if (loggedinuser.equals("!NULLUSER")) {

                SignUpLoginView.run(scanner);
            } else {

                User currentUser = UsersDB.usersDB.getUserByUsername(loggedinuser);
                MainMenuView.run(currentUser, scanner);
            }
        }

    }

}
