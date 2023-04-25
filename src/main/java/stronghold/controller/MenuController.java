package stronghold.controller;

import com.google.gson.JsonObject;
import stronghold.database.java.UsersDB;
import stronghold.view.SignUpLoginView;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MenuController {

    public static Matcher getJSONRegexMatcher(String input, String key, JsonObject jsonObject){
        String pattern = String.valueOf(jsonObject.get(key).getAsString());
        return Pattern.compile(pattern).matcher(input);
    }

    public static boolean usernameExists(String username){
        return UsersDB.usersDB.getUserByUsername(username) == null;
    }

    public static boolean passwordIsStrong(String password){
        return true;
    }

    public static void run(Scanner scanner){

    }
}
