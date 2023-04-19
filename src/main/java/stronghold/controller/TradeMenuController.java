package stronghold.controller;

import stronghold.model.components.game.Resource;
import stronghold.model.components.game.trade.Trade;
import stronghold.model.components.game.trade.TradeDataBase;
import stronghold.model.components.general.User;

public class TradeMenuController {
    private static User currentUser;
    public static User getCurrentUser() {
        return currentUser;
    }
    public void showTradeList(){
        System.out.println("Trade list:");
        for(Trade trade: TradeDataBase.getTrades()){
            System.out.println(trade.getId()+">> "+trade.getReceiver()+", "+trade.getReceiver()+", "+trade.getResourceType()+", \nMessage: "+trade.getMessage());
        }
    }
    public void showHistory(){
        for(Trade trade: TradeDataBase.getTrades()){
            if(TradeMenuController.getCurrentUser().equals(trade.getSender())||TradeMenuController.getCurrentUser().equals(trade.getReceiver())) {
                System.out.println(trade.getId() + ">> " + trade.getReceiver() + ", " + trade.getReceiver() + ", " + trade.getResourceType() + ", \nMessage: " + trade.getMessage());
            }
        }
    }
    public void showNotification(){
        for(Trade trade: TradeDataBase.getTrades()){
            if(!trade.getIsSeen()) {
                System.out.println(trade.getId() + ">> " + trade.getReceiver() + ", " + trade.getReceiver() + ", " + trade.getResourceType() + ", \nMessage: " + trade.getMessage());
                trade.setSeen(true);
            }
        }

    }
    public void acceptTrade(Trade trade){
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
    public void sendTrade(User sender, User receiver, Resource resource,int price,String message){
        //errors
        //else
          Trade trade=new Trade(resource,receiver,sender,message,price);
          TradeDataBase.addTrade(trade);
    }

}
