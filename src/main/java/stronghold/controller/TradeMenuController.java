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

    public static void setCurrentGovernment(Government currentGovernment) {
        TradeMenuController.currentGovernment = currentGovernment;
    }

    public static void run(Scanner scanner){
        setCurrentGovernment(GameMenuController.getCurrentPlayer());
        showNotification();
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
                sendTrade(currentGovernment,Resource.getResource(sendTrade.group("resource")), Integer.parseInt(sendTrade.group("price")),sendTrade.group("message"),Integer.parseInt(sendTrade.group("amount")));

            } else if ((acceptTradeMatcher =getJSONRegexMatcher(command, "acceptTrade", menuRegexPatternsObject)).matches()) {
                acceptTrade(TradeDataBase.getTradeById(Integer.parseInt(acceptTradeMatcher.group("id"))));
            } else if (getJSONRegexMatcher(command, "showHistory", menuRegexPatternsObject).matches()) {
                showHistory();
            } else if (( getJSONRegexMatcher(command, "tradeList", menuRegexPatternsObject)).matches()) {
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
        if(TradeDataBase.getTrades().size()==0){
            System.out.println("no trades");
            return;
        }
        for(Trade trade: TradeDataBase.getTrades()){
            System.out.println(trade.getId()+">> Sender: "+trade.getSender().getColor()+", "+trade.getResourceType()+", \nMessage: "+trade.getMessage());
        }
    }
    public static void showHistory(){
        for(Trade trade: TradeDataBase.getTrades()){
            if(TradeMenuController.getCurrentGovernment().equals(trade.getSender())||TradeMenuController.getCurrentGovernment().equals(trade.getReceiver())) {
                System.out.println(trade.getId() + ">> receiver: player" + trade.getReceiver().getColor() + ", Sender: player" + trade.getReceiver().getColor() + ", " + trade.getResourceType() + ", \nMessage: " + trade.getMessage());
            }
        }
    }
    public static void showNotification(){
        for(Trade trade: TradeDataBase.getTrades()){
                if (!trade.getIsSeen()) {
                    System.out.println(trade.getId() + ">> " + trade.getReceiver().getColor() + ", Sender: " + trade.getSender().getColor() + ", " + trade.getResourceType() + ", \nMessage: " + trade.getMessage());
                    trade.setSeen(true);
                }

        }

    }
    public static void acceptTrade(Trade trade){
        //if(!trade.getReceiver().equals(currentGovernment))
            //System.out.println("You are not the receiver!");
        if(currentGovernment.getBalance()<trade.getPrice())
                 System.out.println("Not enough coins!");
        else{
            trade.setReceiver(currentGovernment);
            trade.getReceiver().setBalance(trade.getReceiver().getBalance() - trade.getPrice());
            trade.getSender().setBalance(trade.getSender().getBalance() + trade.getPrice());
            trade.getReceiver().getResourcesMap().put(trade.getResourceType(),trade.getReceiver().getResourcesMap().get(trade.getResourceType())+trade.getNumber());
            trade.getSender().getResourcesMap().put(trade.getResourceType(),trade.getSender().getResourcesMap().get(trade.getResourceType())-trade.getNumber());
            //TradeDataBase.deleteTrade(trade);
            trade.setAccepted(true);
            System.out.println("You have done trade "+trade.getId()+" with player "+trade.getSender().getColor()+"successfully!");
        }

    }
    public static void sendTrade(Government sender, Resource resource,int price,String message,int number){
        //errors
        if(currentGovernment.getResourcesMap().get(resource)<number){
            TradeMenuView.output("enoughError");
        }
        else {
            TradeMenuView.output("success");
            Trade trade = new Trade(resource, sender, message, price, number);
            TradeDataBase.addTrade(trade);
        }
    }
    public static void GovernmentsTradeList(){
        for(int i=1;i <GameMenuController.getGovernments().size();i++){
            TradeMenuView.output("User",i,(Object) GameMenuController.getGovernmentByColor(i));

        }
    }

}
