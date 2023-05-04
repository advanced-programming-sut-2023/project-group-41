package stronghold.controller;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import stronghold.model.components.game.Government;
import stronghold.model.components.game.enums.Resource;
import stronghold.model.components.game.trade.Trade;
import stronghold.model.components.game.trade.TradeDataBase;
import stronghold.view.TradeMenuView;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;
import java.util.regex.Matcher;

public class TradeMenuController extends MenuController{
    private static Government currentGovernment;
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
            } else if ((sendTrade = getJSONRegexMatcher(command, "sendTrade", menuRegexPatternsObject)).matches()) {
                sendTrade(currentGovernment,GameMenuController.getGovernmentByColor(Integer.parseInt(sendTrade.group("receiver"))),Resource.valueOf(sendTrade.group("resource")), Integer.parseInt(sendTrade.group("price")),sendTrade.group("message"),Integer.parseInt(sendTrade.group("amount")));

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
    public static Government getCurrentGovernment() {
        return currentGovernment;
    }
    public static void showTradeList(){
        System.out.println("Trade list:");
        for(Trade trade: TradeDataBase.getTrades()){
            System.out.println(trade.getId()+">> "+trade.getReceiver()+", "+trade.getReceiver()+", "+trade.getResourceType()+", \nMessage: "+trade.getMessage());
        }
    }
    public static void showHistory(){
        for(Trade trade: TradeDataBase.getTrades()){
            if(TradeMenuController.getCurrentGovernment().equals(trade.getSender())||TradeMenuController.getCurrentGovernment().equals(trade.getReceiver())) {
                System.out.println(trade.getId() + ">> " + trade.getReceiver() + ", " + trade.getReceiver() + ", " + trade.getResourceType() + ", \nMessage: " + trade.getMessage());
            }
        }
    }
    public static void showNotification(){
        for(Trade trade: TradeDataBase.getTrades()){
            if(trade.getReceiver().equals(currentGovernment)) {
                if (!trade.getIsSeen()) {
                    System.out.println(trade.getId() + ">> " + trade.getReceiver() + ", " + trade.getReceiver() + ", " + trade.getResourceType() + ", \nMessage: " + trade.getMessage());
                    trade.setSeen(true);
                }
            }
        }

    }
    public static void acceptTrade(Trade trade){
        if(!trade.getReceiver().equals(currentGovernment))
            System.out.println("You are not the receiver!");
        else if(trade.getReceiver().getBalance()<trade.getPrice())
                 System.out.println("Not enough coins!");
        else{
            trade.getReceiver().setBalance(trade.getReceiver().getBalance() - trade.getPrice());
            trade.getSender().setBalance(trade.getSender().getBalance() + trade.getPrice());
            trade.getReceiver().getResourcesMap().put(trade.getResourceType(),trade.getReceiver().getResourcesMap().get(trade.getResourceType())+trade.getNumber());
            trade.getSender().getResourcesMap().put(trade.getResourceType(),trade.getSender().getResourcesMap().get(trade.getResourceType())-trade.getNumber());
            TradeDataBase.deleteTrade(trade);
            trade.setAccepted(true);
            System.out.println("You have done trade "+trade.getId()+"with "+trade.getSender()+"successfully!");
        }

    }
    public static void sendTrade(Government sender, Government receiver, Resource resource,int price,String message,int number){
        //errors
        if(currentGovernment.getResourcesMap().get(resource)<number){
            TradeMenuView.output("enoughError");
        }
        else {
            TradeMenuView.output("success");
            Trade trade = new Trade(resource, receiver, sender, message, price, number);
            TradeDataBase.addTrade(trade);
        }
    }
    public static void GovernmentsTradeList(){
        for(int i=1;i <GameMenuController.getGovernments().size();i++){
            TradeMenuView.output("User",i,(Object) GameMenuController.getGovernmentByColor(i));

        }
    }

}
