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
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;

public class MainMenuController extends MenuController{
    private static String pathToRegexJSON = "src/main/java/stronghold/database/utils/regex/MainMenuRegex.json";
    public static void run( User currentUser,Scanner scanner) {
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
                MainMenuView.output("logout");
                break;
            } else if ((startGameMatcher = getJSONRegexMatcher(command, "startGame", MainMenuRegexObj)).matches()){
                int i=Integer.parseInt(startGameMatcher.group("opponent"));
                int j=Integer.parseInt(startGameMatcher.group("rounds"));
                Scanner scanner1=new Scanner(System.in);
                System.out.println("enter map size:");
                int mapSize=scanner1.nextInt();


                GameMenuController.run( scanner, j,i,mapSize);
            } else{
                MainMenuView.output("invalid");
            } // TODO: adding if statement for entering profile menu
        }
    }

    public static void main(String[] args) {
        User user=new User("asdf","Asdf","Asd","sdfa",1,"1","!");
        Scanner scanner=new Scanner(System.in);
        run(user,scanner);
    }
}