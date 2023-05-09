package stronghold.model.components.game.soldeirtype;

import stronghold.controller.GameMenuController;
import stronghold.model.components.game.People;

public class Unarmed extends People {
    private String type;

    private UnarmedEnum unarmedEnum;
    public Unarmed(UnarmedEnum unarmedEnum){
        super(unarmedEnum.getSpeed(), unarmedEnum.getPrice(),unarmedEnum.getDefense(),unarmedEnum.getDefense(), GameMenuController.getCurrentPlayer(),false);
        this.type= unarmedEnum.getType();




    }

    public String getType() {
        return type;
    }
}
