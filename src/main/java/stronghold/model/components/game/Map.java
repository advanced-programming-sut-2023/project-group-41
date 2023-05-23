package stronghold.model.components.game;

import stronghold.controller.GameMenuController;
import stronghold.model.components.game.MapCell;
import stronghold.model.components.game.building.BuildingType;
import stronghold.model.components.game.building.Castle;
import stronghold.model.components.game.soldeirtype.Fighter;
import stronghold.model.components.game.soldeirtype.FighterEnum;
import stronghold.model.components.game.soldeirtype.Unarmed;
import stronghold.model.components.general.User;

import java.util.ArrayList;
import java.util.Objects;

import static stronghold.model.components.game.enums.Texture.LAND;
import static stronghold.model.components.game.enums.Texture.SEA;

public class Map {
    private static Map instanceMap = null;
    private static int size;
    private static ArrayList<MapCell> cells;

    private Map(){
    }

    public static Map getInstanceMap(){
        if(instanceMap == null)
            instanceMap = new Map();
        return instanceMap;
    }
     public  MapCell getMapCell(int X, int Y){
            for(MapCell mapCell: instanceMap.cells){
                if(mapCell.getX()==X&&mapCell.getY()==Y){
                    return mapCell;
                }
            }
            return null;
     }

    public  int getSize() {
        return instanceMap.size;
    }
    public void startGameMap(User user1,User user2){

    }

    public  boolean validMapCell(int X, int Y){
        return X >= 0 && X <= instanceMap.size && Y >= 0 && Y <= instanceMap.size;
    }

    public void setSize(int size) {
        instanceMap.size = size;
        instanceMap.cells = new ArrayList<>(size);
        for(int i = 0;i < size;i++){
            for(int j = 0; j < size; j++){
                MapCell mapCell = new MapCell(i,j,LAND);
                instanceMap.cells.add(mapCell);
            }
        }
        //making the map
    }

    public static ArrayList<MapCell> getCells() {
        return cells;
    }
    public  Unit getUnarmed(MapCell mapCell,String type){
        for(Unit unit: mapCell.getUnits()){
            if(unit.getPeople() instanceof Unarmed){
                if(((Unarmed) unit.getPeople()).getType().equals(type)){
                    return unit;

                }
            }
            return null;



        }
        return null;
    }

    public  boolean isBuildingNear(int X, int Y, BuildingType buildingType){
        for (int i = -1; i <= 1 ; i++) {
            for (int j = -1; j <= 1; j++) {
                if (isBuildingHere(X + i, Y + j, buildingType)) return true;
            }
        }
        return false;
    }

    public  boolean isBuildingHere(int X, int Y, BuildingType buildingType){
        if (this.validMapCell(X, Y)){
            try {
                return Objects.requireNonNull(this.getMapCell(X, Y)).getBuilding().getRegex().matches(buildingType.getRegex());
            } catch (Exception ignored){}
        }
        return false;
    }

    public static boolean isSoldierNear(int X, int Y, Government government){
        for (int i = -1; i <= 1 ; i++) {
            for (int j = -1; j <= 1; j++) {
                if (getInstanceMap().validMapCell(X + i, Y + j)){
                    for (Unit unit : getInstanceMap().getMapCell(X + i, Y + j).getUnits()) {
                        if (!unit.getPeople().getOwner().equals(government))
                            return true;
                    }
                }
            }
        }
        return false;
    }

}
