package stronghold.model.components.game.building;

import stronghold.model.components.game.Government;
import stronghold.model.components.game.enums.Resource;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum DevelopmentType implements BuildingType{
    CHURCH("church", 300, 250, 0, false, null, 0, 2, 0, 0),//monk satay here
    CATHEDRAL("cathedral", 600, 1000, 0, false, null, 0, 2, 1, 0),
    INN("inn", 300, 100, 1, false, Resource.WOOD, 20, 100, 0, 40),
    HOUSE("house", 200, 0, 0, false, Resource.WOOD, 6, 8, 0, 0);

    private static final ArrayList<DevelopmentType> developmentTypeArr = new ArrayList<>(EnumSet.allOf(DevelopmentType.class));
    private String regex;
    private int health;
    private int cost;
    private int workerNum;
    private boolean engineerWorkers;
    private Resource neededResource;
    private int neededResourceCount;
    private int incPopularity;
    private int incPopulation;
    private int wineUsageRate;

    DevelopmentType(String regex, int health, int cost, int workerNum, boolean engineerWorkers, Resource neededResource, int neededResourceCount, int incPopularity, int incPopulation, int wineUsageRate) {
        this.regex = regex;
        this.health = health;
        this.cost = cost;
        this.workerNum = workerNum;
        this.engineerWorkers = engineerWorkers;
        this.neededResource = neededResource;
        this.neededResourceCount = neededResourceCount;
        this.incPopularity = incPopularity;
        this.incPopulation = incPopulation;
        this.wineUsageRate = wineUsageRate;
    }

    public String getRegex(){
        return regex;
    }

    public static Matcher getMatcher(String input, DevelopmentType developmentType){
        String regex = developmentType.getRegex();
        return Pattern.compile(regex).matcher(input);
    }

    public static DevelopmentType getDevelopmentType(String input){
        for (DevelopmentType developmentType : developmentTypeArr) {
            if (getMatcher(input, developmentType).find()) return developmentType;
        }
        return null;
    }

    public int getHealth() {
        return health;
    }

    public int getCost() {
        return cost;
    }

    public int getWorkerNum() {
        return workerNum;
    }

    public boolean isEngineerWorkers() {
        return engineerWorkers;
    }

    public Resource getNeededResource() {
        return neededResource;
    }

    public int getNeededResourceCount() {
        return neededResourceCount;
    }

    public int getIncPopularity() {
        return incPopularity;
    }

    public int getIncPopulation() {
        return incPopulation;
    }

    public int getWineUsageRate() {
        return wineUsageRate;
    }

    @Override
    public void action(Government government, int buildingCount) {
        if (wineUsageRate == 0) return;
        for (int i = 0; i < buildingCount; i++) {
            if (government.useResource(Resource.ALE, wineUsageRate)) government.incPopularity(incPopularity);
        }
    }
}
