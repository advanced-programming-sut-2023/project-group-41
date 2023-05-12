package stronghold.view;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import stronghold.controller.MenuController;
import stronghold.controller.SignUpMenuController;
import stronghold.model.components.game.Government;
import stronghold.model.components.general.Captcha;
import stronghold.model.components.general.User;
import stronghold.model.database.UsersDB;
import stronghold.model.utils.Encryption;
import stronghold.model.utils.StringParser;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.regex.Matcher;

import static stronghold.controller.MenuController.*;
import static stronghold.controller.SignUpMenuController.*;

public class SignUpLoginView extends MenuView{
    private static String pathToRegexJSON = "src/main/java/stronghold/database/utils/regex/SignUpLoginRegex.json";
    private static User currentUser;
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
                Captcha captcha = new Captcha();
                SignUpLoginView.output("captcha",(Object) captcha.getGeneratedCaptcha());
                String enteredCaptcha = SignUpLoginView.input(scanner);
                boolean captchaSucceed = true;
                while(!enteredCaptcha.equals(captcha.getAccordingNum())){
                    if(enteredCaptcha.equals("back")){
                        captchaSucceed = false;
                        break;
                    }
                    SignUpLoginView.output("captchainvalid");
                    enteredCaptcha = SignUpLoginView.input(scanner);
                }
                if(!captchaSucceed)
                    continue;
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
            }
            else if(registerRandPassMatcher.find()){

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

                Captcha captcha = new Captcha();
                SignUpLoginView.output("captcha",(Object) captcha.getGeneratedCaptcha());
                String enteredCaptcha = SignUpLoginView.input(scanner);
                boolean captchaSucceed = true;
                while(!enteredCaptcha.equals(captcha.getAccordingNum())){
                    if(enteredCaptcha.equals("back")){
                        captchaSucceed = false;
                        break;
                    }
                    SignUpLoginView.output("captchainvalid");
                    enteredCaptcha = SignUpLoginView.input(scanner);
                }
                if(!captchaSucceed)
                    continue;

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

            }
            else if (loginMatcher.find()) {
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
                    try {
                        Thread.sleep(5000 * failedAttempts++);
                    } catch (
                            InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    continue;
                }

                currentUser = UsersDB.usersDB.getUserByUsername(username);
                setCurrentUser(currentUser);
                SignUpLoginView.output("successfulLogin");
                MainMenuView.run(currentUser, scanner);
            }
            else if (loginStayLoggedInMatcher.find()) {

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
                    try {
                        Thread.sleep(5000 * failedAttempts++);
                    } catch (
                            InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    continue;
                }

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
                    toBeWritten = toBeWritten.replace("!NULLUSER",username);
                    FileWriter fileWriter = new FileWriter(pathToPrefs);
                    fileWriter.write(toBeWritten);
                    fileWriter.close();
                } catch (
                        IOException e) {
                    throw new RuntimeException(e);
                }

                currentUser = UsersDB.usersDB.getUserByUsername(username);
                setCurrentUser(currentUser);
                SignUpLoginView.output("successfulLogin");
                MainMenuView.run(currentUser, scanner);

            }
            else if (forgotMyPassMatcher.find()) {
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

                Captcha captcha = new Captcha();
                SignUpLoginView.output("captcha",(Object) captcha.getGeneratedCaptcha());
                String enteredCaptcha = SignUpLoginView.input(scanner);
                boolean captchaSucceed = true;
                while(!enteredCaptcha.equals(captcha.getAccordingNum())){
                    if(enteredCaptcha.equals("back")){
                        captchaSucceed = false;
                        break;
                    }
                    SignUpLoginView.output("captchainvalid");
                    enteredCaptcha = SignUpLoginView.input(scanner);
                }
                if(!captchaSucceed)
                    continue;

                pendingUser.setPassword(Encryption.toSHA256(newPassword));
                UsersDB.usersDB.update(pendingUser);
                try {
                    UsersDB.usersDB.toJSON();
                } catch (
                        IOException e) {
                    throw new RuntimeException(e);
                }
                SignUpLoginView.output("passChanged");

            }
            else{
                SignUpLoginView.output("invalid");
            }
        }
    }

    public static void output(String code, Object... params){
        String pathToJSON = "src/main/java/stronghold/database/textcontent/SignUpLoginMenuResponses.json";
        MenuView.output(pathToJSON, code, params);
    }

    public static String input(Scanner scanner){
        if(!scanner.hasNextLine())
            return "";
        String readLine = scanner.nextLine();
        return readLine;
    }

}
