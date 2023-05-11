package stronghold.controller;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import stronghold.model.components.game.Government;
import stronghold.model.components.game.Map;
import stronghold.model.components.game.Unit;
import stronghold.model.components.game.building.Building;
import stronghold.model.components.general.User;
import stronghold.model.database.UsersDB;
import stronghold.view.GameMenuView;
import stronghold.view.MainMenuView;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;

public class MainMenuController extends MenuController{
    private static String pathToRegexJSON = "src/main/java/stronghold/database/utils/regex/MainMenuRegex.json";
    public static void run(User currentUser, Scanner scanner) {
        
        JsonElement regexElement = null;
        try {
            regexElement = JsonParser.parseReader(new FileReader(pathToRegexJSON));
        } catch (
                FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        JsonObject MainMenuRegexObj = regexElement.getAsJsonObject();

        while (true) {
            String command = MainMenuView.input(scanner).trim();

            Matcher startGameMatcher = getJSONRegexMatcher(command, "startNewGame", MainMenuRegexObj);
            Matcher loadGameMatcher = getJSONRegexMatcher(command, "loadGame", MainMenuRegexObj);
            Matcher profileMenuMatcher = getJSONRegexMatcher(command, "profileMenu", MainMenuRegexObj);

            if (command.matches("user\\s+logout")) {
                MainMenuView.output("logout");
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
                    toBeWritten = toBeWritten.replace(currentUser.getUsername(),"!NULLUSER");
                    FileWriter fileWriter = new FileWriter(pathToPrefs);
                    fileWriter.write(toBeWritten);
                    fileWriter.close();
                } catch (
                        IOException e) {
                    throw new RuntimeException(e);
                }
                SignUpMenuController.run(scanner);
                break;
            } else if (startGameMatcher.matches()){
                MainMenuView.output("enterUsers");
                ArrayList<User> users = new ArrayList<>();
                while (true){
                    String input = MainMenuView.input(scanner).trim();
                    if (input.matches("FINISH")){
                        break;
                    } else{
                        // TODO: Handle the case where player wants to play with themselves (?)
                        if(!MenuController.usernameExists(input)){
                            MainMenuView.output("username404");
                        }
                        else
                            users.add(UsersDB.usersDB.getUserByUsername(input));
                    }
                }
                GameMenuController.run( scanner, 1,1,1);
            } else if(profileMenuMatcher.find()){
                ProfileMenuController.run(scanner, currentUser);

            } else if(loadGameMatcher.find()){

            } else{
                MainMenuView.output("invalid");
            }
        }
    }
}