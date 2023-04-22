package stronghold.controller;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import javafx.css.Match;
import stronghold.model.components.general.User;
import stronghold.view.SignUpLoginView;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignUpMenuController extends MenuController{
    private static User currentUser;

    private static String pathToRegexJSON = "src/main/java/stronghold/database/utils/regex/SignUpLoginRegex.json";



    public static void addUser(User user){

    }
    public static void run(Scanner scanner){
        JsonElement regexElement = null;
        try {
            regexElement = JsonParser.parseReader(new FileReader(pathToRegexJSON));
        } catch (
                FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        JsonObject menuRegexPatternsObject = regexElement.getAsJsonObject();

        while(true){
            String input = SignUpLoginView.input(scanner);

            Matcher registerMatcher = getJSONRegexMatcher(input, "register", menuRegexPatternsObject);
            
            if (registerMatcher.find()) {

                String username = registerMatcher.group(3);
                String password = registerMatcher.group(5);
                String passwordConfirmation = registerMatcher.group(6);
                String email = registerMatcher.group(8);
                String slogan = registerMatcher.group(10);

                System.out.println(username);
                System.out.println(password);
                System.out.println(passwordConfirmation);
                System.out.println(email);
                System.out.println(slogan);

                SignUpLoginView.output("slogan", (Object) slogan);
            }
            else{
                SignUpLoginView.output("invalid");
            }

        }
    }

    public static User getCurrentUser() {
        return currentUser;
    }
}
