package stronghold;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import stronghold.controller.MainMenuController;
import stronghold.controller.SignUpMenuController;
import stronghold.model.components.general.User;
import stronghold.model.database.UsersDB;
import stronghold.model.utils.StringParser;
import stronghold.view.MenuView;
import stronghold.view.MyJavaFXApp;
import stronghold.view.SignUpLoginView;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

public class Main {


    public static void main(String[] args){
        JsonElement prefsElement;
        try {
             prefsElement = JsonParser.parseReader(
                    new FileReader("src/main/java/stronghold/database/datasets/preferences.json"));
        } catch (
                FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        String loggedinuser = StringParser.removeQuotes(
                String.valueOf(prefsElement.getAsJsonObject().get("logged-in user")));

        Scanner scanner = new Scanner(System.in);
        if(loggedinuser.equals("!NULL")) {

            SignUpMenuController.run(scanner);
        }
        else{

            User currentUser = UsersDB.usersDB.getUserByUsername(loggedinuser);
            MainMenuController.run(currentUser, scanner);
        }
    }
}
