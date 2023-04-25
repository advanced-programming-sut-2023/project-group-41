package stronghold.model.components.game.soldeirtype;

import stronghold.model.components.game.People;

public class Unarmed extends People {
    private String type;
    private int offense;
    private int defense;
    private UnarmedEnum unarmedEnum;
    public Unarmed(UnarmedEnum unarmedEnum){
        super(unarmedEnum.getSpeed(), unarmedEnum.getPrice());
        this.type= unarmedEnum.getType();
        this.defense=unarmedEnum.getDefense();
        this.offense= unarmedEnum.getOffense();



    }

}
