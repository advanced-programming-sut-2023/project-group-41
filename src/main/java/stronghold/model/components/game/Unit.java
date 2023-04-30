package stronghold.model.components.game;

import stronghold.model.components.game.enums.State;

import java.util.ArrayList;

public class Unit {

    ////Integers
   private int X;
    private int Y;
    private int count;
    ////Strings
    private People people;
    ////Arraylists

    private State state;

    public Unit(int x,int y,People type,int count){
        this.people=type;
        this.X=x;
        this.Y=y;
        this.count=count;

    }


    public int getCount() {
        return count;
    }

    public People getPeople() {
        return people;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public int getY() {
        return Y;
    }

    public int getX() {
        return X;
    }
}
