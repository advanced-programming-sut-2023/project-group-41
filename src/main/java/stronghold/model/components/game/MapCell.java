package stronghold.model.components.game;

import stronghold.model.components.game.building.Building;
import stronghold.model.components.game.enums.Direction;
import stronghold.model.components.game.enums.Texture;
import stronghold.model.components.game.enums.Tree;
import stronghold.model.components.game.soldeirtype.Unarmed;
import stronghold.model.components.game.soldeirtype.UnarmedEnum;

import java.util.ArrayList;

public class MapCell {
    private boolean isSick=false;
    private final int x;
    private final int y;
    private boolean isPassable;
    private boolean hasDitch;
    private boolean hasOil;
    private boolean hasTunnel;
    private ArrayList<Unit> units=new ArrayList<>();
    private Texture texture;
    private Building building;
    private Direction rockDirection;
    private Tree tree;
    private Tool tool;
    private int onFire;
    public MapCell(int x,int y,Texture texture){
        this.x=x;
        this.y=y;
        this.texture=texture;
        building= null;
        rockDirection=null;
        tree=null;
        isPassable=true;
        this.hasOil=false;
        this.hasTunnel=false;
        this.hasDitch=false;
        this.tool=null;
        this.onFire=0;
        this.isSick=false;
    }

    public void setSick(boolean sick) {
        isSick = sick;
    }

    public boolean isSick() {
        return isSick;
    }

    public int getOnFire() {
        return onFire;
    }

    public void setOnFire(int onFire) {
        this.onFire = onFire;
    }

    public Tool getTool() {
        return tool;
    }

    public void setTool(Tool tool1) {
        this.tool = tool1;
    }

    public boolean isHasOil() {
        return hasOil;
    }

    public boolean isHasDitch() {
        return hasDitch;
    }

    public void setHasDitch(boolean hasDitch) {
        this.hasDitch = hasDitch;
    }

    public void setHasOil(boolean hasOil) {
        this.hasOil = hasOil;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setHasTunnel(boolean hasTunnel) {
        this.hasTunnel = hasTunnel;
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
        if(getUnits().size()!=0)
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

    public boolean isPassable() {
        return isPassable;
    }
}
