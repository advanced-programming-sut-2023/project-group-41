package stronghold.model.components.game;

import stronghold.model.components.game.MapCell;
import stronghold.model.components.game.building.Castle;
import stronghold.model.components.game.soldeirtype.Fighter;
import stronghold.model.components.game.soldeirtype.FighterEnum;
import stronghold.model.components.game.soldeirtype.Unarmed;
import stronghold.model.components.general.User;

import java.util.ArrayList;

import static stronghold.model.components.game.enums.Texture.LAND;
import static stronghold.model.components.game.enums.Texture.SEA;

public class Map {
    private static int size;
    private static ArrayList<MapCell> cells;

    public Map(int size){
        this.size=size;
        this.cells = new ArrayList<>(size);
        for(int i = 0;i < size;i++){
            for(int j = 0; j < size; j++){
                MapCell mapCell = new MapCell(i,j,LAND);
                this.cells.add(mapCell);
            }
        }

       //making the map
    }
     public static MapCell getMapCell(int X, int Y){
            for(MapCell mapCell: cells){
                if(mapCell.getX()==X&&mapCell.getY()==Y){
                    return mapCell;
                }
            }
            return null;
     }

    public static int getSize() {
        return size;
    }
    public void startGameMap(User user1,User user2){

    }

    public static boolean validMapCell(int X, int Y){
        return X >= 0 && X <= size && Y >= 0 && Y <= size;
    }

    public static void setSize(int size) {
        Map.size = size;
    }

    public static ArrayList<MapCell> getCells() {
        return cells;
    }
    public static Unit getUnarmed(MapCell mapCell,String type){
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
}
