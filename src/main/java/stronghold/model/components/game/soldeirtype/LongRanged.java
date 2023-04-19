package stronghold.model.components.game.soldeirtype;

import stronghold.model.components.game.People;

public class LongRanged extends People {
    private int range;
    private boolean isHorsed;
    private int defense;
    private int offense;
    public LongRanged( String name,String loyalty , int speed, int defense, int offense, int price, int range, boolean isHorsed){
        super(name,speed,price);
        this.defense=defense;
        this.offense=offense;
        this.range=range;
        this.isHorsed=isHorsed;

    }

}
