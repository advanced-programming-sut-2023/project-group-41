package stronghold.model.components.game;

import stronghold.model.components.game.building.Building;
import stronghold.model.components.game.enums.Direction;
import stronghold.model.components.game.enums.Texture;
import stronghold.model.components.game.enums.Tree;

import java.util.ArrayList;

public class MapCell {
    private final int x;
    private final int y;
    private boolean isPassable;
    private ArrayList<Unit> units;
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

    public ArrayList<Unit> getUnits() {
        return units;
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
        if(getUnits()!=null)
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
        if(getTree()!=null)
            return 'T';
        else
            return ' ';


    }

  public void addUnit(Unit unit){
        this.getUnits().add(unit);
  }
    public void removeUnit(Unit unit){
        this.getUnits().remove(unit);
    }
    public Unit searchUnit(Unit unit){
        for(Unit unit1:units){
            if(unit1.equals(unit)){
                return unit1;
            }
        }
        return null;
    }


}
