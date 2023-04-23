package stronghold.controller;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import stronghold.database.java.UsersDB;
import stronghold.model.components.general.User;
import stronghold.model.utils.Encryption;
import stronghold.model.utils.StringParser;
import stronghold.view.SignUpLoginView;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import java.util.regex.Matcher;

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

                String username = StringParser.removeQuotes(registerMatcher.group(3));
                String password = StringParser.removeQuotes(registerMatcher.group(5));
                String passwordConfirmation = StringParser.removeQuotes(registerMatcher.group(6));
                String email = StringParser.removeQuotes(registerMatcher.group(8));
                String slogan = StringParser.removeQuotes(registerMatcher.group(10));
                String nickname = "null";

                if(username == null){
                    SignUpLoginView.output("registerusername404");
                    continue;
                }
                if(password == null){
                    SignUpLoginView.output("registerpassword404");
                    continue;
                }
                if(passwordConfirmation == null){
                    SignUpLoginView.output("registerpassword404");
                    continue;
                }
                if(email == null){
                    SignUpLoginView.output("registeremail404");
                    continue;
                }
                if(slogan == null){
                    SignUpLoginView.output("registerslogan404");
                    continue;
                }

                int passwordRecoveryQuestion = 1;
                String passwordRecoveryAnswer = "null";


                User user = new User(username, Encryption.toSHA256(password), nickname, email, passwordRecoveryQuestion, passwordRecoveryAnswer, slogan);
                UsersDB.usersDB.addUser(user);
                try {
                    UsersDB.usersDB.toJSON();
                } catch (
                        IOException e) {
                    throw new RuntimeException(e);
                }
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
