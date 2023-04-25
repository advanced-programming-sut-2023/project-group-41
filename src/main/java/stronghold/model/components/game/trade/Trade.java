package stronghold.model.components.game.trade;

import stronghold.model.components.game.Resource;
import stronghold.model.components.general.User;

public class Trade {
    private Resource resourceType;

    private User receiver;
    private User sender;
    private int price;
    private String message;
    private boolean isSeen;
    private boolean isAccepted;
    private int id;
    public Trade(Resource r,User receiver,User sender,String message,int price){
        this.resourceType=r;
        this.receiver=receiver;
        this.sender=sender;
        this.message=message;
        this.price=price;
        this.isAccepted=false;
        this.isSeen=false;
        this.id=TradeDataBase.getIdCounter();
        TradeDataBase.setIdCounter(TradeDataBase.getIdCounter()+1);
    }

    public User getReceiver() {
        return receiver;
    }

    public String getMessage() {
        return message;
    }

    public Resource getResourceType() {
        return resourceType;
    }

    public User getSender() {
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
