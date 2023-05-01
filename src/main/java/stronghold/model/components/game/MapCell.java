package stronghold.model.components.game;

import stronghold.model.components.game.building.Building;
import stronghold.model.components.game.enums.Direction;
import stronghold.model.components.game.enums.Texture;
import stronghold.model.components.game.enums.Tree;

public class MapCell {
    private final int x;
    private final int y;
    private boolean isPassable;
    private Unit unit;
    private Texture texture;
    private Building building;
    private Direction rockDirection;
    private Tree tree;
    public MapCell(int x,int y,Texture texture){
        this.x=x;
        this.y=y;
        this.texture=texture;
        building= null;
        rockDirection=null;
        tree=null;
        isPassable=true;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Building getBuilding() {
        return building;
    }

    public Unit getUnit() {
        return unit;
    }

    public Direction getRockDirection() {
        return rockDirection;
    }

    public Tree getTree() {
        return tree;
    }

    public Texture getTexture() {
        return texture;
    }

    public void setBuilding(Building building) {
        this.building = building;
    }

    public void setPassable(boolean passable) {
        isPassable = passable;
    }

    public void setRockDirection(Direction rockDirection) {
        this.rockDirection = rockDirection;
    }

    public void setTexture(Texture texture) {
        this.texture = texture;
    }

    public void setTree(Tree tree) {
        this.tree = tree;
    }

    public char showMovingSoldier(){
        if(getUnit()!=null)
            return 'S';
        else
            return ' ';


    }
    public char showBuilding(){
        //walls for 'W'
        if(getBuilding()!=null)
            return 'B';
        else
            return ' ';


    }
    public char showTree(){
        if(getUnit()!=null)
            return 'T';
        else
            return ' ';


    }

    public void setUnit(Unit unit) {
        this.unit = unit;
    }


}
