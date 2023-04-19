package stronghold.model.components.game;

import java.util.ArrayList;

public class People {

    ////////Strings
    private String name;
    private String type;
    private String loyalty;

    //////Integers
    private int speed;
    private int defense;
    private int offense;
    private int price;
    private int range;

    //////Arraylists
    ArrayList<Resource> equipments= new ArrayList<>();


    /////getters


    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getLoyalty() {
        return loyalty;
    }

    public int getSpeed() {
        return speed;
    }

    public int getDefense() {
        return defense;
    }

    public int getOffense() {
        return offense;
    }

    public int getPrice() {
        return price;
    }

    public ArrayList<Resource> getEquipments() {
        return equipments;
    }
    ///setters

    public void setName(String name) {
        this.name = name;
    }

    public void setLoyalty(String loyalty) {
        this.loyalty = loyalty;
    }

    public void setType(String type) {
        this.type = type;
    }
}
