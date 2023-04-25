package stronghold.model.components.game;

import stronghold.model.components.game.enums.Food;
import stronghold.model.components.general.User;

import java.util.ArrayList;
import java.util.HashMap;

public class Government {
    private User owner;
    private String[] popularityFactors;
    private int popularity;
    private int foodRate;

    public void setPopularity(int popularity) {
        this.popularity = popularity;
    }

    public void setPeople(ArrayList<People> people) {
        this.people = people;
    }

    private int taxRate;
    private int fearRate;
    private HashMap<Resource, Integer> resources;
    private HashMap<Food, Integer> foods; // mehrad change ArrayList name to foods from food
    private ArrayList<Unit> units;
    private ArrayList<People> people;

    // i think we shoud remove units arraylist and people arraylist because they should put in mapCellClass

    public Government(User owner) {
        this.owner = owner;
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

    public HashMap<Resource, Integer> getResourcesMap() {
        return resources;
    }

    public int getResourcesNum(Resource resource) {
        return resources.get(resource);
    }

    public HashMap<Food, Integer> getFoodMap() {
        return foods;
    }

    public int getFoodNum(Food food){
        return foods.get(food);
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
        if (resources.containsKey(resource)) resources.put(resource, resources.get(resource) + amount);
        else resources.put(resource, amount);
    }

    public void addFood(Food food, int amount) {
        if (foods.containsKey(food)) foods.put(food, foods.get(food) + amount);
        else foods.put(food, amount);
    }

    //method from
    // dropBuilding in UML
    // to
    // disBand
    // have already made in mainMenuController
}
