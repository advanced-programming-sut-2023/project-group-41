package stronghold.model.components.game;

import stronghold.model.components.game.MapCell;
import stronghold.model.components.game.building.Castle;
import stronghold.model.components.game.soldeirtype.Fighter;
import stronghold.model.components.game.soldeirtype.FighterEnum;
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
                MapCell mapCell = new MapCell(i,j,SEA);
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
}
