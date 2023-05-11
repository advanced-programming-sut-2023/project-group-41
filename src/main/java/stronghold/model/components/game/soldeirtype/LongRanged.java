package stronghold.model.components.game.soldeirtype;

import stronghold.controller.GameMenuController;
import stronghold.model.components.game.People;

public class LongRanged extends People {
    private int range;
    private boolean isHorsed;
    private String regex;

    private LongRangedEnum longRangedEnum;
    public LongRanged(  LongRangedEnum longRangedEnum){
        super(longRangedEnum.getSpeed(), longRangedEnum.getPrice(),longRangedEnum.getDefense(),longRangedEnum.getOffense(), GameMenuController.getCurrentPlayer(), longRangedEnum.isArab());

        this.range=longRangedEnum.getRange();
        this.isHorsed=longRangedEnum.isHorsed();
        this.regex= longRangedEnum.getRegex();

    }
    public String getRegex() {
        return longRangedEnum.getRegex();
    }

    public int getRange() {
        return range;
    }
}
