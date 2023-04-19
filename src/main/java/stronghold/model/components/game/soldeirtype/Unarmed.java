package stronghold.model.components.game.soldeirtype;

import stronghold.model.components.game.People;

public class Unarmed extends People {
    private String type;
    public Unarmed(String name,String type,int speed, int price  ){
        super(name,speed,price);
        this.type=type;


    }

}
