package stronghold.controller;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Popup;
import stronghold.model.components.game.Government;
import stronghold.model.components.game.enums.Resource;
import stronghold.model.components.game.trade.Trade;
import stronghold.model.components.game.trade.TradeDataBase;
import stronghold.view.NewTradeMenu;
import stronghold.view.ShopMenuView;
import stronghold.view.TradeMenuView;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;
import java.util.regex.Matcher;

public class TradeMenuController extends MenuController{
    private static Government currentGovernment;


    public static void setCurrentGovernment(Government currentGovernment) {
        TradeMenuController.currentGovernment = currentGovernment;
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
                    System.out.println(trade.getId() + ">> " + ", Sender: " + trade.getSender().getColor() + ", " + trade.getResourceType() + ", \nMessage: " + trade.getMessage());
                    trade.setSeen(true);
                }

        }

    }
    public static void acceptTrade(Trade trade,Popup popup){
        //if(!trade.getReceiver().equals(currentGovernment))
            //System.out.println("You are not the receiver!");
        if(currentGovernment.getBalance()<trade.getPrice())
        {
            Label label=new Label("not enough money!");
            label.setLayoutX(label.getLayoutX()+80);
            label.setLayoutY(label.getLayoutY()+80);
            Rectangle rectangle=new Rectangle(200,200);
            rectangle.setOpacity(0.4);
            rectangle.setFill(Color.GREEN);
            popup.getContent().add(rectangle);
            popup.getContent().add(label);
            Button button=new Button("back");
            button.setLayoutX(button.getLayoutX()+80);
            button.setLayoutY(button.getLayoutY()+100);
            popup.show(ShopMenuView.getStage());
            button.setOnAction(actionEvent -> popup.hide());
            popup.getContent().add(button);
        }

        else{
            Label label=new Label("success!");
            label.setLayoutX(label.getLayoutX()+80);
            label.setLayoutY(label.getLayoutY()+80);
            Rectangle rectangle=new Rectangle(200,200);
            rectangle.setOpacity(0.4);
            rectangle.setFill(Color.GREEN);
            popup.getContent().add(rectangle);
            popup.getContent().add(label);
            Button button=new Button("back");
            button.setLayoutX(button.getLayoutX()+80);
            button.setLayoutY(button.getLayoutY()+100);
            popup.show(ShopMenuView.getStage());
            button.setOnAction(actionEvent -> popup.hide());
            popup.getContent().add(button);
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
    public static void sendTrade(Government sender, Resource resource, int price, String message, int number, Popup popup){
        //errors
        setCurrentGovernment(NewTradeMenu.getCurrentPlayer());
        if(currentGovernment.getResourcesMap().get(resource)<number){
            Label label=new Label("not enough resource!");
            label.setLayoutX(label.getLayoutX()+80);
            label.setLayoutY(label.getLayoutY()+80);
            Rectangle rectangle=new Rectangle(200,200);
            rectangle.setOpacity(0.4);
            rectangle.setFill(Color.RED);
            popup.getContent().add(rectangle);
            popup.getContent().add(label);
            Button button=new Button("back");
            button.setLayoutX(button.getLayoutX()+80);
            button.setLayoutY(button.getLayoutY()+100);
            popup.show(NewTradeMenu.getStage());
            button.setOnAction(actionEvent -> popup.hide());
            popup.getContent().add(button);

            return;
        }
        else {
            Label label=new Label("success!");
            label.setLayoutX(label.getLayoutX()+80);
            label.setLayoutY(label.getLayoutY()+80);
            Rectangle rectangle=new Rectangle(200,200);
            rectangle.setOpacity(0.4);
            rectangle.setFill(Color.GREEN);
            popup.getContent().add(rectangle);
            popup.getContent().add(label);
            Button button=new Button("back");
            button.setLayoutX(button.getLayoutX()+80);
            button.setLayoutY(button.getLayoutY()+100);
            popup.show(NewTradeMenu.getStage());
            button.setOnAction(actionEvent -> popup.hide());
            popup.getContent().add(button);

            Trade trade = new Trade(resource, sender, message, price, number);
            TradeDataBase.addTrade(trade);
            System.out.println(TradeDataBase.getTrades().size());
        }
    }
    public static void GovernmentsTradeList(){
        for(int i=1;i <GameMenuController.getGovernments().size();i++){


        }
    }

}
