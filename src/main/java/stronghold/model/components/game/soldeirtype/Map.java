package stronghold.model.components.game.soldeirtype;

import stronghold.model.components.game.MapCell;

import java.util.ArrayList;

public class Map {
    private int size;
    private ArrayList<MapCell> cells;

    public Map(int size){
        this.size=size;
       //making the map
    }
 public MapCell getMapCell(int X, int Y){
        for(MapCell mapCell: cells){
            if(mapCell.getX()==X&&mapCell.getY()==Y){
                return mapCell;
            }
        }
        return null;
 }
}
