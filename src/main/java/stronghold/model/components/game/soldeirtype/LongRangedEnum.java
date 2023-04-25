package stronghold.model.components.game.soldeirtype;

public enum LongRangedEnum {
    archer(4,2,2,1,3,false),
    crossbowMen(2,3,2,1,2,false),
    archerBow(4,2,2,1,3,false),
    slingers(4,1,2,1,1,false),
    horseArcher(5,3,2,1,2,true),
    fireThrowers(5,3,4,1,2,false);


    private int speed;
    private int defense;
    private int offense;
    private int price;
    private int range;
    private boolean isHorsed;
    LongRangedEnum(  int speed, int defense, int offense, int price,int  range, boolean isHorsed){

        this.defense=defense;
        this.offense=offense;
        this.range=range;
        this.isHorsed=isHorsed;
        this.speed=speed;
        this.price=price;

    }

    public int getPrice() {
        return price;
    }

    public int getOffense() {
        return offense;
    }

    public int getDefense() {
        return defense;
    }

    public int getSpeed() {
        return speed;
    }

    public int getRange() {
        return range;
    }

    public boolean isHorsed() {
        return isHorsed;
    }
}

