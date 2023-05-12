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

    public static boolean enterCaptcha(Scanner scanner){
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
    public static void updateDB(User currentUser){
        UsersDB.usersDB.update(currentUser);
        try {
            UsersDB.usersDB.toJSON();
        } catch (
                IOException e) {
            throw new RuntimeException(e);
        }
    }


}
