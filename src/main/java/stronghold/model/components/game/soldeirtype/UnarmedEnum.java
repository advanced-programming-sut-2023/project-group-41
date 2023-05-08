package stronghold.model.components.game.soldeirtype;

public enum UnarmedEnum {
    tunneler("tunneler",4,1,1,3),
    ladderMen("ladderMen",4,1,1,0),
    engineer("engineer",3,1,1,0);
    private String type;
    private int speed;
    private int price;
    private int defense;
    private int offense;

    UnarmedEnum(String type,int speed,int price,int defense,int offense){
        this.type=type;
        this.speed=speed;
        this.price=price;
        this.defense=defense;
        this.offense=offense;

    }



    public int getSpeed() {
        return speed;
    }

    public int getDefense() {
        return defense;
    }

    public int getOffense() {
        return offense;
    }

    public int getPrice() {
        return price;
    }

    public String getType() {
        return type;
    }
}
