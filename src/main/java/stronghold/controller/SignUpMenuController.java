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
            Matcher loginMatcher = getJSONRegexMatcher(input, "login", menuRegexPatternsObject);
            Matcher loginStayLoggedInMatcher = getJSONRegexMatcher(input, "loginStayLoggedIn", menuRegexPatternsObject);
            Matcher forgotMyPassMatcher = getJSONRegexMatcher(input, "forgotMyPass", menuRegexPatternsObject);

            if (registerMatcher.find()) {

                String username = StringParser.removeQuotes(registerMatcher.group(3));
                String password = StringParser.removeQuotes(registerMatcher.group(5));
                String passwordConfirmation = StringParser.removeQuotes(registerMatcher.group(6));
                String email = StringParser.removeQuotes(registerMatcher.group(8));
                String slogan = StringParser.removeQuotes(registerMatcher.group(10));
                String nickname = StringParser.removeQuotes(registerMatcher.group(12));

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
                if(nickname == null){
                    SignUpLoginView.output("registernickname404");
                    continue;
                }
                if (!password.equals(passwordConfirmation)){
                    SignUpLoginView.output("unmatchingpasswords");
                    continue;
                }
                // TODO: checking password recovery question

                String questionPickInput = SignUpLoginView.input(scanner);
                Matcher questionPickMatcher = getJSONRegexMatcher(questionPickInput, "questionPick", menuRegexPatternsObject);

                int passwordRecoveryQuestion = Integer.parseInt(questionPickMatcher.group("questionNumber"));
                String passwordRecoveryAnswer = StringParser.removeQuotes(questionPickMatcher.group("answer"));
                String passwordRecoveryAnswerConfirmation = StringParser.removeQuotes(questionPickMatcher.group("answerConfirm"));

                if (!passwordRecoveryAnswer.equals(passwordRecoveryAnswerConfirmation)){
                    SignUpLoginView.output("unmatchingpasswords");
                    continue;
                }
                // TODO: random pass needed to be added

                User user = new User(username, Encryption.toSHA256(password), nickname, email, passwordRecoveryQuestion, passwordRecoveryAnswer, slogan);
                UsersDB.usersDB.addUser(user);
                try {
                    UsersDB.usersDB.toJSON();
                } catch (
                        IOException e) {
                    throw new RuntimeException(e);
                }
                SignUpLoginView.output("slogan", (Object) slogan);
            } else if (loginMatcher.find()) {
                String username = StringParser.removeQuotes(loginMatcher.group("username"));
                String password = StringParser.removeQuotes(loginMatcher.group("pass"));
                SignUpLoginView.output("successfulLogin");
                
            } else if (loginStayLoggedInMatcher.find()) {
                String username = StringParser.removeQuotes(loginStayLoggedInMatcher.group("username"));
                String password = StringParser.removeQuotes(loginStayLoggedInMatcher.group("pass"));
                // TODO: user is not created
                if (true) {
                    SignUpLoginView.output("unmatchingpassanduser");
                    continue;
                }
                // TODO: wrong pass
                if (true) {
                    SignUpLoginView.output("unmatchingpassanduser");
                    continue;
                }
                // TODO: wrong answer penalty
                SignUpLoginView.output("successfulLogin");

            } else if (forgotMyPassMatcher.find()) {
                String username = StringParser.removeQuotes(forgotMyPassMatcher.group(1));
                // TODO: logical action needed

                // TODO: checking password recovery question
                String questionPickInput = SignUpLoginView.input(scanner);
                Matcher questionPickMatcher = getJSONRegexMatcher(questionPickInput, "questionPick", menuRegexPatternsObject);

                int passwordRecoveryQuestion = Integer.parseInt(questionPickMatcher.group("questionNumber"));
                String passwordRecoveryAnswer = StringParser.removeQuotes(questionPickMatcher.group("answer"));
                String passwordRecoveryAnswerConfirmation = StringParser.removeQuotes(questionPickMatcher.group("answerConfirm"));

                if (!passwordRecoveryAnswer.equals(passwordRecoveryAnswerConfirmation)){
                    SignUpLoginView.output("unmatchingpasswords");
                    continue;
                }

                SignUpLoginView.output("passChanged");

            } else{
                SignUpLoginView.output("invalid");
            }

        }
    }

    public static User getCurrentUser() {
        return currentUser;
    }
}
