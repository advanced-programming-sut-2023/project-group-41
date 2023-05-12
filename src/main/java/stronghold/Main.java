package stronghold;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import stronghold.controller.GameMenuController;
import stronghold.controller.MainMenuController;
import stronghold.controller.SignUpMenuController;
import stronghold.model.components.general.User;
import stronghold.model.database.UsersDB;
import stronghold.model.utils.StringParser;
import stronghold.view.MainMenuView;
import stronghold.view.SignUpLoginView;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

public class Main {

    /* !!!!!FOR THE SAKE OF F*CK, DO NOT COMMENT YOUR CODES!!!!! */


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
        if(loggedinuser.equals("!NULLUSER")) {

            SignUpLoginView.run(scanner);
        }
        else{

            User currentUser = UsersDB.usersDB.getUserByUsername(loggedinuser);
            MainMenuView.run(currentUser, scanner);
        }
    }
}
