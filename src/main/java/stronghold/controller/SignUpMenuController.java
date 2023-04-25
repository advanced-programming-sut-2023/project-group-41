package stronghold.controller;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import stronghold.model.database.UsersDB;
import stronghold.model.components.general.User;
import stronghold.model.utils.Encryption;
import stronghold.model.utils.StringParser;
import stronghold.view.SignUpLoginView;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.security.SecureRandom;
import java.util.Random;
import java.util.Scanner;
import java.util.regex.Matcher;

public class SignUpMenuController extends MenuController{
    private static User currentUser;

    private static String pathToRegexJSON = "src/main/java/stronghold/database/utils/regex/SignUpLoginRegex.json";

    private static final String CHARACTERS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!@#$%^&*()_-+={}[]|\\:;\"',.?/~`";
    private static final Random RANDOM = new SecureRandom();

    public static String generateRandomString() {
        int length = 6; // minimum length of 6
        StringBuilder sb = new StringBuilder();
        // add at least 1 uppercase letter
        sb.append((char) (RANDOM.nextInt(26) + 'A'));
        // add at least 1 lowercase letter
        sb.append((char) (RANDOM.nextInt(26) + 'a'));
        // add at least 1 digit
        sb.append((char) (RANDOM.nextInt(10) + '0'));
        // add at least 1 symbol
        sb.append(CHARACTERS.charAt(RANDOM.nextInt(CHARACTERS.length())));
        // fill up the rest of the string with random characters
        for (int i = 4; i < length; i++) {
            sb.append(CHARACTERS.charAt(RANDOM.nextInt(CHARACTERS.length())));
        }
        // shuffle the characters randomly
        String shuffledString = shuffleString(sb.toString());
        return shuffledString;
    }

    private static String shuffleString(String string) {
        char[] characters = string.toCharArray();
        for (int i = 0; i < characters.length; i++) {
            int randomIndex = RANDOM.nextInt(characters.length);
            char temp = characters[i];
            characters[i] = characters[randomIndex];
            characters[randomIndex] = temp;
        }
        return new String(characters);
    }

    public static boolean authenticate(String username, String password){
        if(!usernameExists(username))
            return false;
        User user = UsersDB.usersDB.getUserByUsername(username);
        if(!user.getPassword().equals(Encryption.toSHA256(password))){
            return false;
        }
        return true;
    }
    public static void run(Scanner scanner){
        int failedAttempts = 0;
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
            Matcher registerRandPassMatcher = getJSONRegexMatcher(input,
                    "register_randpass", menuRegexPatternsObject);
            Matcher loginMatcher = getJSONRegexMatcher(input, "login", menuRegexPatternsObject);
            Matcher loginStayLoggedInMatcher = getJSONRegexMatcher(input, "loginStayLoggedIn",
                    menuRegexPatternsObject);
            Matcher forgotMyPassMatcher = getJSONRegexMatcher(input, "forgotMyPass",
                    menuRegexPatternsObject);

            if (registerMatcher.find()) {

                String username = StringParser.removeQuotes(registerMatcher.group(3));
                String password = StringParser.removeQuotes(registerMatcher.group(5));
                String passwordConfirmation = StringParser.removeQuotes(registerMatcher.group(6));
                String email = StringParser.removeQuotes(registerMatcher.group(8));
                String slogan = StringParser.removeQuotes(registerMatcher.group(10));
                String nickname = StringParser.removeQuotes(registerMatcher.group(12));

                if(username == null) {
                    SignUpLoginView.output("registerusername404");
                    continue;
                }
                if(password == null) {
                    SignUpLoginView.output("registerpassword404");
                    continue;
                }
                if(passwordConfirmation == null) {
                    SignUpLoginView.output("registerpassword404");
                    continue;
                }
                if(email == null) {
                    SignUpLoginView.output("registeremail404");
                    continue;
                }
                if(slogan == null) {
                    SignUpLoginView.output("registerslogan404");
                    continue;
                }
                if(nickname == null) {
                    SignUpLoginView.output("registernickname404");
                    continue;
                }

                if(!usernameFormatCorrect(username)) {
                    SignUpLoginView.output("invalidusername");
                    continue;
                }
                if(usernameExists(username)) {
                    SignUpLoginView.output("usernameexists");
                    continue;
                }
                if(!passwordIsStrong(password)) {
                    SignUpLoginView.output("passwordweak");
                    continue;
                }
                if (!password.equals(passwordConfirmation)) {
                    SignUpLoginView.output("unmatchingpasswords");
                    continue;
                }
                if(emailExists(email)) {
                    SignUpLoginView.output("emailexists");
                    continue;
                }
                if(!emailIsValid(email)) {
                    SignUpLoginView.output("invalidemail");
                    continue;
                }


                SignUpLoginView.output("showsecurityquestions");

                String questionPickInput = SignUpLoginView.input(scanner);
                Matcher questionPickMatcher = getJSONRegexMatcher(questionPickInput,
                        "questionPick", menuRegexPatternsObject);

                int passwordRecoveryQuestion = - 1;
                String passwordRecoveryAnswer = "null";
                String passwordRecoveryAnswerConfirmation = "null";



                while(!questionPickMatcher.find()){
                    if(questionPickInput.equals("Exit")){
                        return;
                    }
                    SignUpLoginView.output("recoveryquestion404");
                    questionPickInput = SignUpLoginView.input(scanner);
                    questionPickMatcher = getJSONRegexMatcher(questionPickInput,
                            "questionPick", menuRegexPatternsObject);
                }

                if(slogan.equals("random")){
                    slogan = "Hello there!";
                }
                 passwordRecoveryQuestion = Integer.parseInt(questionPickMatcher.group("questionNumber"));
                 passwordRecoveryAnswer = StringParser.removeQuotes(questionPickMatcher.group(
                        "answer"));
                 passwordRecoveryAnswerConfirmation = StringParser.removeQuotes(
                        questionPickMatcher.group("answerConfirm"));

                if (!passwordRecoveryAnswer.equals(passwordRecoveryAnswerConfirmation)){
                    SignUpLoginView.output("unmatchingpasswords");
                    continue;
                }

                User user = new User(username, Encryption.toSHA256(password)
                        , nickname, email, passwordRecoveryQuestion, passwordRecoveryAnswer, slogan);
                UsersDB.usersDB.addUser(user);
                try {
                    UsersDB.usersDB.toJSON();
                } catch (
                        IOException e) {
                    throw new RuntimeException(e);
                }
                SignUpLoginView.output("usercreated", (Object) username, (Object) nickname);
            } else if(registerRandPassMatcher.find()){

                String username = StringParser.removeQuotes(registerRandPassMatcher.group(3));
                String password = StringParser.removeQuotes(registerRandPassMatcher.group(5));
                String email = StringParser.removeQuotes(registerRandPassMatcher.group(7));
                String slogan = StringParser.removeQuotes(registerRandPassMatcher.group(9));
                String nickname = StringParser.removeQuotes(registerRandPassMatcher.group(11));

                if(!password.equals("random")){
                    SignUpLoginView.output("registerpasswordconfirm404");
                    continue;
                }
                if(username == null) {
                    SignUpLoginView.output("registerusername404");
                    continue;
                }
                if(password == null) {
                    SignUpLoginView.output("registerpassword404");
                    continue;
                }
                if(email == null) {
                    SignUpLoginView.output("registeremail404");
                    continue;
                }
                if(slogan == null) {
                    SignUpLoginView.output("registerslogan404");
                    continue;
                }
                if(nickname == null) {
                    SignUpLoginView.output("registernickname404");
                    continue;
                }

                if(!usernameFormatCorrect(username)) {
                    SignUpLoginView.output("invalidusername");
                    continue;
                }
                if(usernameExists(username)) {
                    SignUpLoginView.output("usernameexists");
                    continue;
                }
                if(emailExists(email)) {
                    SignUpLoginView.output("emailexists");
                    continue;
                }
                if(!emailIsValid(email)) {
                    SignUpLoginView.output("invalidemail");
                    continue;
                }

                password = generateRandomString();
                SignUpLoginView.output("randpass", (Object) password);
                SignUpLoginView.output("confirmrandpass");
                String passwordConfirmation = SignUpLoginView.input(scanner);

                if(!passwordConfirmation.equals(password)){
                    SignUpLoginView.output("unmatchingpasswords");
                    continue;
                }


                SignUpLoginView.output("showsecurityquestions");

                String questionPickInput = SignUpLoginView.input(scanner);
                Matcher questionPickMatcher = getJSONRegexMatcher(questionPickInput,
                        "questionPick", menuRegexPatternsObject);

                int passwordRecoveryQuestion = - 1;
                String passwordRecoveryAnswer = "null";
                String passwordRecoveryAnswerConfirmation = "null";



                while(!questionPickMatcher.find()){
                    if(questionPickInput.equals("Exit")){
                        return;
                    }
                    SignUpLoginView.output("recoveryquestion404");
                    questionPickInput = SignUpLoginView.input(scanner);
                    questionPickMatcher = getJSONRegexMatcher(questionPickInput,
                            "questionPick", menuRegexPatternsObject);
                }

                if(slogan.equals("random")){
                    slogan = "Hello there!";
                }
                passwordRecoveryQuestion = Integer.parseInt(questionPickMatcher.group("questionNumber"));
                passwordRecoveryAnswer = StringParser.removeQuotes(questionPickMatcher.group(
                        "answer"));
                passwordRecoveryAnswerConfirmation = StringParser.removeQuotes(
                        questionPickMatcher.group("answerConfirm"));

                if (!passwordRecoveryAnswer.equals(passwordRecoveryAnswerConfirmation)){
                    SignUpLoginView.output("unmatchingpasswords");
                    continue;
                }

                User user = new User(username, Encryption.toSHA256(password)
                        , nickname, email, passwordRecoveryQuestion, passwordRecoveryAnswer, slogan);
                UsersDB.usersDB.addUser(user);
                try {
                    UsersDB.usersDB.toJSON();
                } catch (
                        IOException e) {
                    throw new RuntimeException(e);
                }
                SignUpLoginView.output("usercreated", (Object) username, (Object) nickname);
                
            } else if (loginMatcher.find()) {

                String username = StringParser.removeQuotes(loginMatcher.group("username"));
                String password = StringParser.removeQuotes(loginMatcher.group("pass"));

                if(username == null) {
                    SignUpLoginView.output("registerusername404");
                    continue;
                }
                if(password == null) {
                    SignUpLoginView.output("registerpassword404");
                    continue;
                }

                boolean userAuthenticated = authenticate(username,password);

                if(!userAuthenticated){
                    SignUpLoginView.output("unmatchingpassanduser");
                    continue;
                }

                SignUpLoginView.output("successfulLogin");
                MainMenuController.run(currentUser, scanner);

            } else if (loginStayLoggedInMatcher.find()) {

                String username = StringParser.removeQuotes(loginStayLoggedInMatcher.group("username"));
                String password = StringParser.removeQuotes(loginStayLoggedInMatcher.group("pass"));

                if(username == null) {
                    SignUpLoginView.output("registerusername404");
                    continue;
                }
                if(password == null) {
                    SignUpLoginView.output("registerpassword404");
                    continue;
                }

                boolean userAuthenticated = authenticate(username,password);

                if(!userAuthenticated){
                    SignUpLoginView.output("unmatchingpassanduser");
                    continue;
                }


                SignUpLoginView.output("successfulLogin");
                MainMenuController.run(currentUser, scanner);

            } else if (forgotMyPassMatcher.find()) {
                String username = StringParser.removeQuotes(forgotMyPassMatcher.group("username"));

                if(!usernameExists(username)){
                    SignUpLoginView.output("usernotfound");
                    continue;
                }

                User pendingUser = UsersDB.usersDB.getUserByUsername(username);
                SignUpLoginView.output("securityQ"+pendingUser.getPasswordRecoveryQuestion());

                String answer = SignUpLoginView.input(scanner);

                if(!pendingUser.getPasswordRecoveryAnswer().equals(answer)){
                    SignUpLoginView.output("incorrectsecurityanswer");
                    continue;
                }

                SignUpLoginView.output("setnewpassword");
                String newPassword = SignUpLoginView.input(scanner);

                SignUpLoginView.output("confirmnewpassword");
                String newConfirmation = SignUpLoginView.input(scanner);

                if(!newConfirmation.equals(newPassword)){
                    SignUpLoginView.output("unmatchingpasswords");
                    continue;
                }

                if(!passwordIsStrong(newPassword)){
                    SignUpLoginView.output("passwordweak");
                    continue;
                }

                pendingUser.setPassword(Encryption.toSHA256(newPassword));
                UsersDB.usersDB.update(pendingUser);
                try {
                    UsersDB.usersDB.toJSON();
                } catch (
                        IOException e) {
                    throw new RuntimeException(e);
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
