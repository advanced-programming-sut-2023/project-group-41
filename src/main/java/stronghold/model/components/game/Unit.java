package stronghold.model.components.game;

import java.util.ArrayList;

public class Unit {

    ////Integers
    int x;
    int y;
    ////Strings
    String name;
    ////Arraylists
    ArrayList<People> people;

    Unit(int x,int y,String name){
        this.name=name;
        this.x=x;
        this.y=y;
        people = new ArrayList<>();

    }
}
