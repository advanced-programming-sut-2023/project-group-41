package stronghold.model.components.game;

import stronghold.model.components.game.MapCell;
import stronghold.model.components.general.User;

import java.util.ArrayList;

public class Map {
    private static int size;
    private static ArrayList<MapCell> cells;

    public Map(int size){
        this.size=size;
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
}
