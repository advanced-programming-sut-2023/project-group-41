package stronghold.model.components.game.soldeirtype;

public enum FighterEnum {
    spearMan(3,1,3,1,false,false,false),
    pikeMan(2,4,3,1,false,false,false),
    maceMan(3,3,4,1,false,false,false),
    swordsMan(1,1,5,1,false,false,false),
    Knight(5,4,5,1,false,true,false),
    blackMonk(2,3,3,1,false,false,false),
    slaves(4,0,1,1,false,false,true),
    assassins(3,3,3,1,true,false,true),
    arabianSwordsMen(5,4,4,1,false,false,true);


    private int speed;
    private int defense;
    private int offense;
    private int price;
    private boolean isAssassin;
    private boolean isHorsed;
    private boolean isArab;
   FighterEnum(  int speed, int defense, int offense, int price,boolean isAssassin, boolean isHorsed,boolean isArab){

        this.defense=defense;
        this.offense=offense;
        this.isAssassin=isAssassin;
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

    public boolean isAssassin() {
        return isAssassin;
    }

    public boolean isHorsed() {
        return isHorsed;
    }

    public boolean isArab() {
        return isArab;
    }
}
