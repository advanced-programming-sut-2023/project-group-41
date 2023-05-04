package stronghold.controller;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import stronghold.model.components.general.User;
import stronghold.model.database.UsersDB;
import stronghold.view.ProfileMenuView;
import stronghold.view.SignUpLoginView;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import java.util.regex.Matcher;

import static stronghold.controller.MenuController.getJSONRegexMatcher;

public class ProfileMenuController {

    private static String pathToRegexJSON = "src/main/java/stronghold/database/utils/regex/ProfileMenuRegex.json";
    public static void run(Scanner scanner, User currentUser){
        JsonElement regexElement = null;
        try {
            regexElement = JsonParser.parseReader(new FileReader(pathToRegexJSON));
        } catch (
                FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        JsonObject menuRegexPatternsObject = regexElement.getAsJsonObject();

        while(true) {
            String input = SignUpLoginView.input(scanner);

            Matcher changeUsernameMatcher =
                    getJSONRegexMatcher(input, "userNameChange", menuRegexPatternsObject);
            Matcher changeNicknameMatcher =
                    getJSONRegexMatcher(input, "nickNameChange" , menuRegexPatternsObject);
            Matcher changePasswordMatcher =
                    getJSONRegexMatcher(input, "passWordChange" , menuRegexPatternsObject);;
            Matcher changeEmailMatcher =
                    getJSONRegexMatcher(input, "emailChange" , menuRegexPatternsObject);;
            Matcher changeSloganMatcher =
                    getJSONRegexMatcher(input, "sloganChange" , menuRegexPatternsObject);;
            Matcher getScoreMatcher =
                    getJSONRegexMatcher(input, "score" , menuRegexPatternsObject);;
            Matcher getRankMatcher =
                    getJSONRegexMatcher(input, "rank" , menuRegexPatternsObject);;
            Matcher getSloganMatcher =
                    getJSONRegexMatcher(input, "slogan" , menuRegexPatternsObject);;
            Matcher displayAllMatcher =
                    getJSONRegexMatcher(input, "displayAll" , menuRegexPatternsObject);;

            if(changeUsernameMatcher.find()){
                String username = changeUsernameMatcher.group("username");
                if(username == null){
                    ProfileMenuView.output("usernameEmpty");
                    continue;
                }
                currentUser.setUsername(username);
                UsersDB.usersDB.update(currentUser);
                try {
                    UsersDB.usersDB.toJSON();
                } catch (
                        IOException e) {
                    throw new RuntimeException(e);
                }
                ProfileMenuView.output("usernameChanged");
            }
            else if(changeNicknameMatcher.find()){
                String nickname = changeNicknameMatcher.group("nickname");

            }
            else if(changePasswordMatcher.find()){

            }
            else if(changeEmailMatcher.find()){

            }
            else if(changeSloganMatcher.find()){

            }
            else if(getScoreMatcher.find()){

            }
            else if(getRankMatcher.find()){

            }
            else if(getSloganMatcher.find()){

            }
            else if(displayAllMatcher.find()){

            }
            else{
                ProfileMenuView.output("invalid");
            }
        }
    }
}
