package stronghold.controller;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import stronghold.model.components.game.Map;
import stronghold.model.components.game.Unit;
import stronghold.model.components.game.building.Building;
import stronghold.model.components.general.User;
import stronghold.view.GameMenuView;
import stronghold.view.MainMenuView;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;

public class MainMenuController extends MenuController{
    private ArrayList<User> userDataBase;
    private User currentUser;
    private static String pathToRegexJSON = "src/main/java/stronghold/database/utils/regex/MainGameRegex.json";
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
            Matcher startGameMatcher;
            if (command.matches("user\\s+logout")) {
                break;
            } else if ((startGameMatcher = getJSONRegexMatcher(command, "startGame", MainMenuRegexObj)).matches()){
                MainMenuView.output("enterUsers");
                ArrayList<User> users = new ArrayList<>();
                while (true){
                    String input = MainMenuView.input(scanner).trim();
                    if (input.matches("FINISH")){
                        break;
                    } else{
                        // TODO: getting usernames from input and add them to users arr
                    }
                }
                GameMenuController.run(currentUser, users, scanner);
            } else{
                MainMenuView.output("invalid");
            } // TODO: adding if statement for entering profile menu
        }
    }
}
