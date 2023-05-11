package stronghold.model.components.game.building;

import stronghold.model.components.game.Government;

public class Ruler extends  Building {
    // todo: remove ruler class

    private Government owner;

    public Ruler(Government owner){
            super(owner, null, 500,0,0,false,null,0);


    }

    @Override
    public String getRegex() {
        return null;
    }
}
