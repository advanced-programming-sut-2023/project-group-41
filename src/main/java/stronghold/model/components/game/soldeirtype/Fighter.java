package stronghold.model.components.game.soldeirtype;

import stronghold.model.components.game.People;

public class Fighter extends People {
    private boolean isAssassin;
    private boolean isHorsed;
    private  int defense;
    private int offense;
    private FighterEnum fighterEnum;
    public Fighter( FighterEnum fighterEnum){
        super(fighterEnum.getSpeed(), fighterEnum.getPrice());
        this.defense=fighterEnum.getDefense();
        this.offense=fighterEnum.getOffense();
        this.isAssassin=fighterEnum.isAssassin();
        this.isHorsed=fighterEnum.isHorsed();

    }


}
