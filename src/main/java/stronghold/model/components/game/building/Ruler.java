package stronghold.model.components.game.building;

import stronghold.model.components.game.Government;

public class Ruler extends  Building {

    private Government owner;

    public Ruler(Government owner){
            super(owner,500,0,0,false,null,0);


    }
}
