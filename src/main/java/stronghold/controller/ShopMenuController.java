package stronghold.controller;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import stronghold.model.components.game.Government;
import stronghold.model.components.game.enums.Resource;
import stronghold.model.components.general.User;
import stronghold.view.ShopMenuView;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Scanner;
import java.util.regex.Matcher;

public class ShopMenuController extends MenuController{
    private static Government currentGovernment;
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
                buy(Resource.valueOf(buy.group("int")),Integer.parseInt(buy.group(2)));
            } else if ((sell=getJSONRegexMatcher(command, "showHistory", menuRegexPatternsObject)).matches()) {
                buy(Resource.valueOf(buy.group("int")),Integer.parseInt(buy.group(2)));
            } else if (( getJSONRegexMatcher(command, "showPriceList", menuRegexPatternsObject)).matches()) {
                showPriceList();
            }   else {
                ShopMenuView.output("invalid");
            }
        }

    }
    //add prices
    public static void buy(Resource resource,int number){
        if(prices.get(resource)*number> currentGovernment.getBalance())
                    ShopMenuView.output("moneyError");
        else{
            currentGovernment.setBalance(currentGovernment.getBalance()-(prices.get(resource)*number));
            currentGovernment.getResourcesMap().put(resource, (currentGovernment.getResourcesMap().get(resource)+number));
            ShopMenuView.output("success");
        }

    }
    public static void sell(Resource resource,int number){
        if(currentGovernment.getResourcesMap().get(resource)>number){
            ShopMenuView.output("shortage");
            return;
        }
        currentGovernment.setBalance(currentGovernment.getBalance()+(prices.get(resource)*(number*0.8)));
        currentGovernment.getResourcesMap().put(resource, (currentGovernment.getResourcesMap().get(resource)-number));
        ShopMenuView.output("success");


    }
    public static void showPriceList(){
        for(Resource resource:prices.keySet()){
            System.out.println(resource+": "+prices.get(resource));
        }
    }

}
