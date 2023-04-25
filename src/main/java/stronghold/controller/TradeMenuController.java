package stronghold.controller;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import stronghold.model.components.game.Resource;
import stronghold.model.components.game.trade.Trade;
import stronghold.model.components.game.trade.TradeDataBase;
import stronghold.model.components.general.User;
import stronghold.view.TradeMenuView;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;
import java.util.regex.Matcher;

public class TradeMenuController extends MenuController{
    private static User currentUser;
    private static String pathToRegexJSON = "src/main/java/stronghold/database/utils/regex/TradeMenuRegex.json";




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
            String command = TradeMenuView.input(scanner).trim();
            Matcher sendTrade;
            Matcher acceptTradeMatcher;


            if(command.matches("back")){
                TradeMenuView.output("back");

                break;
            } else if (getJSONRegexMatcher(command, "sendTrade", menuRegexPatternsObject).matches()) {
                //sendTrade();
            } else if ((acceptTradeMatcher =getJSONRegexMatcher(command, "acceptTrade", menuRegexPatternsObject)).matches()) {
                acceptTrade(TradeDataBase.getTradeById(Integer.parseInt(acceptTradeMatcher.group("id"))));
            } else if (getJSONRegexMatcher(command, "showHistory", menuRegexPatternsObject).matches()) {
                showHistory();
            } else if (( getJSONRegexMatcher(command, "showTradeList", menuRegexPatternsObject)).matches()) {
                showTradeList();
            }   else {
                TradeMenuView.output("invalid");
            }
        }

    }
    public static User getCurrentUser() {
        return currentUser;
    }
    public static void showTradeList(){
        System.out.println("Trade list:");
        for(Trade trade: TradeDataBase.getTrades()){
            System.out.println(trade.getId()+">> "+trade.getReceiver()+", "+trade.getReceiver()+", "+trade.getResourceType()+", \nMessage: "+trade.getMessage());
        }
    }
    public static void showHistory(){
        for(Trade trade: TradeDataBase.getTrades()){
            if(TradeMenuController.getCurrentUser().equals(trade.getSender())||TradeMenuController.getCurrentUser().equals(trade.getReceiver())) {
                System.out.println(trade.getId() + ">> " + trade.getReceiver() + ", " + trade.getReceiver() + ", " + trade.getResourceType() + ", \nMessage: " + trade.getMessage());
            }
        }
    }
    public static void showNotification(){
        for(Trade trade: TradeDataBase.getTrades()){
            if(!trade.getIsSeen()) {
                System.out.println(trade.getId() + ">> " + trade.getReceiver() + ", " + trade.getReceiver() + ", " + trade.getResourceType() + ", \nMessage: " + trade.getMessage());
                trade.setSeen(true);
            }
        }

    }
    public static void acceptTrade(Trade trade){
        if(!trade.getReceiver().equals(currentUser))
            System.out.println("You are not the reciever!");
        //else if(trade.getReceiver().getTribe().getBalance()<trade.getPrice())
                 //System.out.println("Not enough coins!");
        else{
            //updateUser
            TradeDataBase.deleteTrade(trade);
            System.out.println("You have done trade "+trade.getId()+"with "+trade.getSender()+"successfully!");
        }

    }
    public static void sendTrade(User sender, User receiver, Resource resource,int price,String message){
        //errors
        //else
          Trade trade=new Trade(resource,receiver,sender,message,price);
          TradeDataBase.addTrade(trade);
    }

}
