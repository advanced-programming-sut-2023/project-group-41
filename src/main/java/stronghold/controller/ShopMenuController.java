package stronghold.controller;

import stronghold.model.components.game.enums.Resource;
import stronghold.model.components.general.User;

import java.util.HashMap;

public class ShopMenuController extends MenuController{
    private static User currentUser;
    private static HashMap<Resource,Integer> prices;
    //add prices
    public void buy(Resource resource,int number){
        //if(prices.get(resource)*number>currentUser.getTribe().getbalance())
                   // System.out.println("Not enough balance!");
        //updateUser
        //setbBalance
    }
    public void sell(Resource resource,int number){

        //updateUser
        //setbBalance
    }
    public void showPriceList(){
        for(Resource resource:prices.keySet()){
            System.out.println(resource+": "+prices.get(resource));
        }
    }

}
