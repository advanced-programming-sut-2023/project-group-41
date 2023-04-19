package stronghold.model.components.game.trade;

import java.util.ArrayList;

public class TradeDataBase {
    private static int idCounter;
    private static  ArrayList<Trade> trades;
    public TradeDataBase(){
        trades=new ArrayList<>();
        idCounter=1;
    }

    public static ArrayList<Trade> getTrades() {
        return trades;
    }

    public static int getIdCounter() {
        return idCounter;
    }

    public static void setIdCounter(int idCounter) {
        TradeDataBase.idCounter = idCounter;
    }
    public static void deleteTrade(Trade trade){
        trades.removeIf(trade1 -> trade1.equals(trade));
    }
    public static void addTrade(Trade trade){
        trades.add(trade);
    }
}
