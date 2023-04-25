package stronghold.model.components.game.soldeirtype;

import stronghold.model.components.game.People;

public class LongRanged extends People {
    private int range;
    private boolean isHorsed;
    private int defense;
    private int offense;
    private LongRangedEnum longRangedEnum;
    public LongRanged(  LongRangedEnum longRangedEnum){
        super(longRangedEnum.getSpeed(), longRangedEnum.getPrice());
        this.defense=longRangedEnum.getDefense();
        this.offense=longRangedEnum.getOffense();
        this.range=longRangedEnum.getRange();
        this.isHorsed=longRangedEnum.isHorsed();

    }

}
