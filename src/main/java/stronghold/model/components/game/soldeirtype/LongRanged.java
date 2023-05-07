package stronghold.model.components.game.soldeirtype;

import stronghold.controller.GameMenuController;
import stronghold.model.components.game.People;

public class LongRanged extends People {
    private int range;
    private boolean isHorsed;

    private LongRangedEnum longRangedEnum;
    public LongRanged(  LongRangedEnum longRangedEnum){
        super(longRangedEnum.getSpeed(), longRangedEnum.getPrice(),longRangedEnum.getDefense(),longRangedEnum.getOffense(), GameMenuController.getCurrentPlayer());

        this.range=longRangedEnum.getRange();
        this.isHorsed=longRangedEnum.isHorsed();

    }

    public int getRange() {
        return range;
    }
}
