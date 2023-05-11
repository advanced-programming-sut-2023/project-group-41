package stronghold.model.components.game;

import stronghold.model.components.game.building.*;

import stronghold.model.components.game.building.Building;
import stronghold.model.components.game.building.Ruler;

import stronghold.model.components.game.enums.Resource;
import stronghold.model.components.general.User;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class Government {
    private User owner;
    private int color;
    private String[] popularityFactors;
    private int popularity = 0;
    private int population = 5;
    private int foodRate = -2;
    private double balance = 2000;
    private int taxRate = 0;
    private int fearRate;
    private int freeStockSpace = 100;
    private int freeFoodStockSpace = 60;
    private HashMap<Resource, Integer> resources;
    private LinkedHashMap<BuildingType, Integer> buildingHash = listAllBuilding();
    private ArrayList<Unit> units;
    private ArrayList<People> people;


    public Government(int color) {
        // owner.setGovernment(this);
        this.color = color;
        Building ruler = new Ruler(this);
        this.setRuler(ruler);
        resources = new HashMap<>();
        for (Resource resource : EnumSet.allOf(Resource.class)) {
            resources.put(resource, 0);
        }
    }
    public void incPopularity(int num) {
        popularity += num;
    }

    public void incPopulation(int num) {
        population += num;
    }

    public void incFreeStockSpace(int num) {
        freeStockSpace += num;
    }

    public void incFreeFoodStockSpace(int num) {
        freeFoodStockSpace += num;
    }


    public void setPopularity(int popularity) {
        this.popularity = popularity;
    }

    public void setPeople(ArrayList<People> people) {
        this.people = people;
    }

    ;
    private Building Ruler;

    public Building getRuler() {
        return Ruler;
    }

    public void setRuler(Building ruler) {
        Ruler = ruler;
    }


    public String[] getPopularityFactors() {
        return popularityFactors;
    }

    public void calculatePopularity() {
        // i have no idea what should i do
    }

    public LinkedHashMap<BuildingType, Integer> getBuildingHash() {
        return buildingHash;
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

    public void addResources(Resource resource, int amount, boolean doesItHaveStockEffect) {
        if (doesItHaveStockEffect) {
            if (resource.equals(Resource.APPLE) ||
                    resource.equals(Resource.CHEESE) ||
                    resource.equals(Resource.BREAD) ||
                    resource.equals(Resource.MEAT)) {
                for (int i = 0; i < amount && freeFoodStockSpace > 0; i++, freeFoodStockSpace--) {
                    resources.put(resource, resources.get(resource) + 1);
                }
            } else {
                for (int i = 0; i < amount && freeStockSpace > 0; i++, freeStockSpace--) {
                    resources.put(resource, resources.get(resource) + 1);
                }
            }
        } else {
            resources.put(resource, resources.get(resource) + amount);
        }
    }

    public boolean useResource(Resource resource, int count) {
        int available = resources.get(resource);
        if (available >= count) {
            resources.put(resource, available - count);
            if (resource.equals(Resource.APPLE) ||
                    resource.equals(Resource.CHEESE) ||
                    resource.equals(Resource.BREAD) ||
                    resource.equals(Resource.MEAT)) {
                freeFoodStockSpace += count;
            } else {
                freeStockSpace += count;
            }
            return true;
        } else {
            return false;
        }
    }

    public boolean resourceCheck(Resource resource, int count){
        int available = resources.get(resource);
        return available >= count;
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

    public MapCell findRuler(Building ruler) {
        for (MapCell mapCell : Map.getCells()) {
            if (ruler.equals(mapCell.getBuilding())) {
                return mapCell;
            }

        }
        return null;

    }

    private LinkedHashMap<BuildingType, Integer> listAllBuilding() {
        LinkedHashMap<BuildingType, Integer> hash = new LinkedHashMap<>();
        for (ResourceMakerType resourceMakerType : EnumSet.allOf(ResourceMakerType.class)) {
            hash.put(resourceMakerType, 0);
        }
        for (ConverterType converterType : EnumSet.allOf(ConverterType.class)) {
            hash.put(converterType, 0);
        }
        for (DevelopmentType developmentType : EnumSet.allOf(DevelopmentType.class)) {
            hash.put(developmentType, 0);
        }
        for (CastleType castleType : EnumSet.allOf(CastleType.class)) {
            hash.put(castleType, 0);
        }
        for (StorageType storageType : EnumSet.allOf(StorageType.class)) {
            hash.put(storageType, 0);
        }
        return hash;
    }

    public void addBuilding(BuildingType buildingType) {
        int count = buildingHash.get(buildingType);
        buildingHash.put(buildingType, count + 1);
    }

    public int getBuildingNum(BuildingType buildingType){
        return buildingHash.get(buildingType);
    }

    public void limitStoneInQuarry(){
        int max = getBuildingNum(ResourceMakerType.QUARRY) * ResourceMakerType.QUARRY.getLimit();
        if (getResourcesNum(Resource.STONE_IN_QUARRY) > max)
            resources.put(Resource.STONE_IN_QUARRY, max);
    }

    public void allBuildingActions(){
        for (BuildingType buildingType : buildingHash.keySet()) {
            buildingType.action(this, buildingHash.get(buildingType));
        }
    }

    public void removeBuilding(Building type) {
        buildingHash.put(type.getBuildingType(), getBuildingNum(type.getBuildingType()) - 1);
    }


    public static void main(String[] args) {
        Government government = new Government(4);
        System.out.println(government.resources);
        new Storage(government, StorageType.STOCK_PILE);
        //new Storage(government, StorageType.STOCK_PILE);
        //new ResourceMaker(government, ResourceMakerType.WHEAT_FARM);
        new ResourceMaker(government, ResourceMakerType.HOPS_FARM);
        new ResourceMaker(government, ResourceMakerType.HOPS_FARM);
        new ResourceMaker(government, ResourceMakerType.HOPS_FARM);
        new Converter(government, ConverterType.BREWING);
        //new Development(government, DevelopmentType.INN);
        //new Converter(government, ConverterType.MILL);
        //new Converter(government, ConverterType.BAKERY);

        government.allBuildingActions();
        government.allBuildingActions();
        //government.allBuildingActions();
        System.out.println(government.buildingHash);
        System.out.println(government.resources);
    }
    //method from
    // dropBuilding in UML
    // to
    // disBand
    // have already made in mainMenuController

}
