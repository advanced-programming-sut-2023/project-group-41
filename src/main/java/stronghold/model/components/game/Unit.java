package stronghold.model.components.game;

import java.util.ArrayList;

public class Unit {

    ////Integers
    int X;
    int Y;
    ////Strings
    String name;
    ////Arraylists
    ArrayList<People> people;

    public Unit(int x,int y,String name){
        this.name=name;
        this.X=x;
        this.Y=y;
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
}
