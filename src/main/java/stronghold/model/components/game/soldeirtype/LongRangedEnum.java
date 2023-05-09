package stronghold.model.components.game.soldeirtype;

public enum LongRangedEnum {
    archer(4,2,2,1,3,false,false),
    crossbowMen(2,3,2,1,2,false,false),
    archerBow(4,2,2,1,3,false,false),
    slingers(4,1,2,1,1,false,true),
    horseArcher(5,3,2,1,2,true,true),
    fireThrowers(5,3,4,1,2,false,true);


    private int speed;
    private int defense;
    private int offense;
    private int price;
    private int range;
    private boolean isHorsed;
    boolean isArab;
    LongRangedEnum(  int speed, int defense, int offense, int price,int  range, boolean isHorsed,boolean isArab){

        this.defense=defense;
        this.offense=offense;
        this.range=range;
        this.isHorsed=isHorsed;
        this.speed=speed;
        this.price=price;
        this.isArab=isArab;

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

    public boolean isArab() {
        return isArab;
    }
}

