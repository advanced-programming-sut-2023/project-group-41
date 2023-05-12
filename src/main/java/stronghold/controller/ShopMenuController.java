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

    public static void setCurrentGovernment(Government currentGovernment) {
        ShopMenuController.currentGovernment = currentGovernment;
    }

    public static HashMap<Resource,Integer> prices=new HashMap<>();//not valued yet
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
        if(currentGovernment.getResourcesMap().get(resource)<number){
            ShopMenuView.output("shortage");
            return;
        }
        currentGovernment.setBalance(currentGovernment.getBalance()+(prices.get(resource)*(number*0.8)));
        currentGovernment.getResourcesMap().put(resource, (currentGovernment.getResourcesMap().get(resource)-number));
        ShopMenuView.output("success");


    }
    public static void showPriceList(){
        for(Resource resource:prices.keySet()){
            System.out.println(resource.getRegex()+": "+prices.get(resource)+" sell: "+prices.get(resource)*0.8+" num: "+currentGovernment.getResourcesMap().get(resource));
        }
    }

}
