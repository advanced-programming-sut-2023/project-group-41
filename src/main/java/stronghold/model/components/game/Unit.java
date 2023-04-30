package stronghold.model.components.game;

import stronghold.model.components.game.enums.State;

import java.util.ArrayList;

public class Unit {

    ////Integers
   private int X;
    private int Y;
    private int count;
    ////Strings
    private String name;
    ////Arraylists
    ArrayList<People> people;
    private State state;

    public Unit(int x,int y,String name,int count){
        this.name=name;
        this.X=x;
        this.Y=y;
        this.count=count;
        people = new ArrayList<>();
    }

    public void addPerson(People person) {
        people.add(person);
    }

    public void removePerson(People person) {
        people.remove(person);
    }

    public String getName() {
        return name;
    }

    public ArrayList<People> getPeople() {
        return people;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }
}
