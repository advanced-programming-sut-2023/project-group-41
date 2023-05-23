package stronghold.model.components.game.soldeirtype;

import stronghold.controller.GameMenuController;
import stronghold.model.components.game.People;

public class Fighter extends People {
    private boolean isAssassin;
    private boolean isHorsed;


    private FighterEnum fighterEnum;
    public Fighter( FighterEnum fighterEnum){

        super(fighterEnum.getSpeed(), fighterEnum.getPrice(),fighterEnum.getDefense(),fighterEnum.getOffense(), GameMenuController.getCurrentPlayer(),fighterEnum.isArab());
        this.fighterEnum=fighterEnum;
        this.isAssassin=fighterEnum.isAssassin();
        this.isHorsed=fighterEnum.isHorsed();

    }

    public FighterEnum getFighterEnum() {
        return fighterEnum;
    }

    public String getRegex() {
        return this.fighterEnum.getRegex();
    }


}
