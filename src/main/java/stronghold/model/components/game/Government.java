package stronghold.model.components.game;

import stronghold.model.components.game.building.*;
import stronghold.model.components.game.enums.Resource;
import stronghold.model.components.general.User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.EnumSet;
import java.util.HashMap;

public class Government {
    private User owner;
    private int color;
    private String[] popularityFactors;
    private int popularity;
    private int foodRate;
    private double balance;
    private int taxRate;
    private int fearRate;
    private HashMap<Resource, Integer> resources;
    private HashMap<String, Integer> buildingHash;
    private ArrayList<Unit> units;
    private ArrayList<People> people;

    public Government(int color) {
        this.color = color;
        resources = new HashMap<>();
        for (Resource resource : EnumSet.allOf(Resource.class)) {
            resources.put(resource, 0);
        }

        buildingHash = new HashMap<>();
        listAllBuilding(buildingHash);
    }


    public void setPopularity(int popularity) {
        this.popularity = popularity;
    }

    public void setPeople(ArrayList<People> people) {
        this.people = people;
    }

    public String[] getPopularityFactors() {
        return popularityFactors;
    }

    public void calculatePopularity() {
        // i have no idea what should i do
    }

    public int getPopularity() {
        return popularity;
    }

    public int getFoodRate() {
        return foodRate;
    }

    public int getTaxRate() {
        return taxRate;
    }

    public int getFearRate() {
        return fearRate;
    }

    public ArrayList<Unit> getUnits() {
        return units;
    }

    public ArrayList<People> getPeople() {
        return people;
    }

    public void setFoodRate(int foodRate) {
        this.foodRate = foodRate;
    }

    public void setTaxRate(int taxRate) {
        this.taxRate = taxRate;
    }

    public void setFearRate(int fearRate) {
        this.fearRate = fearRate;
    }

    public void addResources(Resource resource, int amount) {
        resources.put(resource, resources.get(resource) + amount);
    }

    public HashMap<Resource, Integer> getResourcesMap() {
        return resources;
    }

    public int getResourcesNum(Resource resource) {
        return resources.get(resource);
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    private void listAllBuilding(HashMap<String, Integer> buildingHash) {
        for (CastleType castleType : EnumSet.allOf(CastleType.class)) {
            buildingHash.put(castleType.getRegex(), 0);
        }
        for (ConverterType converterType : EnumSet.allOf(ConverterType.class)) {
            buildingHash.put(converterType.getRegex(), 0);
        }
        for (DevelopmentType developmentType : EnumSet.allOf(DevelopmentType.class)) {
            buildingHash.put(developmentType.getRegex(), 0);
        }
        for (ResourceMakerType resourceMakerType : EnumSet.allOf(ResourceMakerType.class)) {
            buildingHash.put(resourceMakerType.getRegex(), 0);
        }
        for (StorageType storageType : EnumSet.allOf(StorageType.class)) {
            buildingHash.put(storageType.getRegex(), 0);
        }
    }

    public void addBuilding(String regex){
        int count = buildingHash.get(regex);
        buildingHash.put(regex, count + 1);
    }
//method from
    // dropBuilding in UML
    // to
    // disBand
    // have already made in mainMenuController

}
