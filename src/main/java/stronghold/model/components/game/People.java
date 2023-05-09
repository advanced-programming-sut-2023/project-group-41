package stronghold.model.components.game;

import stronghold.model.components.game.enums.Resource;
import stronghold.model.components.general.User;

import java.util.ArrayList;

public abstract class People {

    ////////Strings
    private String name;

    private User loyalty;

    //////Integers
    private int speed;
    private int price;
    private int offense;
    private int defence;
    private Government owner;
    private boolean hasOil;
    private boolean isArab;

    public void setArab(boolean arab) {
        isArab = arab;
    }

    //////Arraylists
    ArrayList<Resource> equipments= new ArrayList<>();


   public People( int speed, int price,int defence,int offense,Government owner,boolean isArab){
       this.hasOil=false;
       this.owner=owner;
       this.speed=speed;
       this.price=price;
       this.offense=offense;
       this.defence=defence;
       this.isArab=isArab;
      // this.loyalty=mainMenu.getCurrentUser();

    }

    public Government getOwner() {
        return owner;
    }

    public void setHasOil(boolean hasOil) {
        this.hasOil = hasOil;
    }
    public boolean getHasOil(){
       return hasOil;

    }
/////getters


    public String getName() {
        return name;
    }



    public User getLoyalty() {
        return loyalty;
    }

    public int getSpeed() {
        return speed;
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

    public void setLoyalty(User loyalty) {
        this.loyalty = loyalty;
    }
    public void addEquipments(Resource equipment){
        equipments.add(equipment);

    }

    public int getOffense() {
        return offense;
    }

    public int getDefence() {
        return defence;
    }
    public abstract String getRegex();
}
