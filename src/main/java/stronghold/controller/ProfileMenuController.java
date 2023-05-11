package stronghold.controller;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import stronghold.model.components.general.Captcha;
import stronghold.model.components.general.User;
import stronghold.model.database.UsersDB;
import stronghold.model.utils.Encryption;
import stronghold.view.ProfileMenuView;
import stronghold.view.SignUpLoginView;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import java.util.regex.Matcher;

import static stronghold.controller.MenuController.getJSONRegexMatcher;

public class ProfileMenuController {

    private static boolean enterCaptcha(Scanner scanner){
        final int maxTries = 3;
        int currentTries = 0;
        Captcha captcha = new Captcha();
        ProfileMenuView.output("captcha",(Object) captcha.getGeneratedCaptcha());
        String enteredCaptcha = ProfileMenuView.input(scanner);
        boolean captchaSucceed = true;
        while(!enteredCaptcha.equals(captcha.getAccordingNum())){
            if(enteredCaptcha.equals("back") || maxTries<=currentTries){
                captchaSucceed = false;
                break;
            }
            ProfileMenuView.output("captchainvalid");
            currentTries++;
            enteredCaptcha = SignUpLoginView.input(scanner);
        }
        return captchaSucceed;
    }
    private static void updateDB(User currentUser){
        UsersDB.usersDB.update(currentUser);
        try {
            UsersDB.usersDB.toJSON();
        } catch (
                IOException e) {
            throw new RuntimeException(e);
        }
    }

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
                if(!enterCaptcha(scanner)){
                    continue;
                }
                currentUser.setUsername(username);
                updateDB(currentUser);
                ProfileMenuView.output("usernameChanged");
            }
            else if(changeNicknameMatcher.find()){
                String nickname = changeNicknameMatcher.group("nickname");
                if(nickname == null){
                    ProfileMenuView.output("nicknameEmpty");
                    continue;
                }
                if(!enterCaptcha(scanner)){
                    continue;
                }
                currentUser.setNickname(nickname);
                updateDB(currentUser);
                ProfileMenuView.output("nicknameChanged");
            }
            else if(changePasswordMatcher.find()){
                String oldPassword = changePasswordMatcher.group("old");
                String newPassword = changePasswordMatcher.group("new");
                if(oldPassword == null){
                    ProfileMenuView.output("oldPass404");
                    continue;
                }
                if(newPassword == null){
                    ProfileMenuView.output("newPass404");
                    continue;
                }
                if(currentUser.getPassword().equals(Encryption.toSHA256(oldPassword))){
                    ProfileMenuView.output("passwordError");
                    continue;
                }
                if(!enterCaptcha(scanner)){
                    continue;
                }
                currentUser.setPassword(Encryption.toSHA256(newPassword));
                updateDB(currentUser);
                ProfileMenuView.output("passwordChanged");
            }
            else if(changeEmailMatcher.find()){
                String email = changeEmailMatcher.group("email");
                if(email == null){
                    ProfileMenuView.output("email404");
                    continue;
                }
                if(!MenuController.emailIsValid(email)){
                    ProfileMenuView.output("invalidEmail");
                    continue;
                }
                if(MenuController.emailExists(email)){
                    ProfileMenuView.output("emailExists");
                    continue;
                }
                if(!enterCaptcha(scanner)){
                    continue;
                }
                currentUser.setEmail(email);
                updateDB(currentUser);
                ProfileMenuView.output("emailChanged");
            }
            else if(changeSloganMatcher.find()){
                String slogan = changeSloganMatcher.group("slogan");
                if(slogan == null){
                    ProfileMenuView.output("slogan404");
                    continue;
                }
                if(!enterCaptcha(scanner)){
                    continue;
                }
                currentUser.setSlogan(slogan);
                updateDB(currentUser);
                ProfileMenuView.output("sloganChanged");

            }
            else if(getScoreMatcher.find()){
                ProfileMenuView.output("highscore", (Object) String.valueOf(currentUser.getScore()));
            }
            else if(getRankMatcher.find()){
                int rank = UsersDB.usersDB.sortByScore().indexOf(currentUser);
                ProfileMenuView.output("rank", (Object) String.valueOf(rank));
            }
            else if(getSloganMatcher.find()){
                ProfileMenuView.output("showSlogan", (Object) currentUser.getSlogan());
            }
            else if(displayAllMatcher.find()){
                ProfileMenuView.output("show", currentUser.getUsername(),
                        currentUser.getEmail(), currentUser.getSlogan(), String.valueOf(currentUser.getScore()),
                        currentUser.getNickname());
            }
            else{
                ProfileMenuView.output("invalid");
            }
        }
    }
}
