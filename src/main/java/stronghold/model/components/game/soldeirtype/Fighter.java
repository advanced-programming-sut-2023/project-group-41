package stronghold.model.components.game.soldeirtype;

import stronghold.model.components.game.People;

public class Fighter extends People {
    private boolean isAssassin;
    private boolean isHorsed;
    private  int defense;
    private int offense;
    public Fighter( String name,String loyalty , int speed, int defense, int offense, int price, boolean isAssassin, boolean isHorsed){
        super(name,speed,price);
        this.defense=defense;
        this.offense=offense;
        this.isAssassin=isAssassin;
        this.isHorsed=isHorsed;

    }


}
