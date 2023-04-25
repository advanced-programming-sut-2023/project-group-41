package stronghold.controller;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import stronghold.model.components.game.enums.Resource;
import stronghold.model.components.general.User;
import stronghold.view.ShopMenuView;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Scanner;
import java.util.regex.Matcher;

public class ShopMenuController extends MenuController{
    private static User currentUser;
    private static String pathToRegexJSON = "src/main/java/stronghold/database/utils/regex/ShopMenuRegex.json";

    private static HashMap<Resource,Integer> prices;//not valued yet
    public static void run(Scanner scanner){
        JsonElement regexElement = null;
        try {
            regexElement = JsonParser.parseReader(new FileReader(pathToRegexJSON));
        } catch (
                FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        JsonObject menuRegexPatternsObject = regexElement.getAsJsonObject();
        while (true){
            String command = ShopMenuView.input(scanner).trim();
            Matcher buy;
            Matcher sell;


            if(command.matches("back")){
                ShopMenuView.output("back");
                break;
            } else if ((buy =getJSONRegexMatcher(command, "buy", menuRegexPatternsObject)).matches()) {
               // buy();
            } else if ((sell=getJSONRegexMatcher(command, "showHistory", menuRegexPatternsObject)).matches()) {
               // sell();
            } else if (( getJSONRegexMatcher(command, "showPriceList", menuRegexPatternsObject)).matches()) {
                showPriceList();
            }   else {
                ShopMenuView.output("invalid");
            }
        }

    }
    //add prices
    public static void buy(Resource resource,int number){
        //if(prices.get(resource)*number>currentUser.getTribe().getbalance())
                   // System.out.println("Not enough balance!");
        //updateUser
        //setbBalance
    }
    public static void sell(Resource resource,int number){

        //updateUser
        //setbBalance
    }
    public static void showPriceList(){
        for(Resource resource:prices.keySet()){
            System.out.println(resource+": "+prices.get(resource));
        }
    }

}
