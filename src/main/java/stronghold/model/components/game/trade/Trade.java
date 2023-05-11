package stronghold.model.components.game.trade;

import stronghold.model.components.game.Government;
import stronghold.model.components.game.enums.Resource;
import stronghold.model.components.general.User;

public class Trade {
    private Resource resourceType;

    private Government receiver;
    private Government sender;
    private int price;
    private String message;
    private boolean isSeen;
    private boolean isAccepted;
    private int id;
    private int number;

    public int getNumber() {
        return number;
    }

    public Trade(Resource r,  Government sender, String message, int price,int number){
        this.resourceType=r;
        this.receiver=null;
        this.sender=sender;
        this.message=message;
        this.price=price;
        this.isAccepted=false;
        this.isSeen=false;
        this.number=number;
        this.id=TradeDataBase.getIdCounter();
        TradeDataBase.setIdCounter(TradeDataBase.getIdCounter()+1);
    }

    public void setReceiver(Government receiver) {
        this.receiver = receiver;
    }

    public void setSender(Government sender) {
        this.sender = sender;
    }

    public Government getReceiver() {
        return receiver;
    }

    public String getMessage() {
        return message;
    }

    public Resource getResourceType() {
        return resourceType;
    }

    public Government getSender() {
        return sender;
    }

    public void setAccepted(boolean accepted) {
        isAccepted = accepted;
    }

    public void setSeen(boolean seen) {
        isSeen = seen;
    }

    public int getId() {
        return id;
    }

    public int getPrice() {
        return price;
    }
    public boolean getIsSeen(){
        return isSeen;
    }
    public boolean getIsAccepted(){
        return isAccepted;
    }
}
