package stronghold.model.components.game.building;

import stronghold.model.components.game.Government;
import stronghold.model.components.game.enums.Resource;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum ResourceMakerType implements BuildingType {
    WHEAT_FARM("wheatFarm", 100, 0, 1, 15, Resource.WHEAT, 0,  100),
    HUNT_POST("huntPost", 100, 0, 1, 5, Resource.MEAT, 0,40),
    HOPS_FARM("hopsFarm", 100, 0, 1, 15, Resource.HOPS, 0,80),
    DAIRY("dairy", 200, 0, 1, 15, Resource.CHEESE, 0,60),//also make Leather Vest
    APPLE_GARDEN("appleGarden", 100, 0, 1, 5, Resource.APPLE, 0, 80),
    PITCH_RIG("pitchRig", 100, 0, 1, 20, Resource.PITCH, 0, 20),
    QUARRY("quarry", 200, 0, 3, 20, Resource.STONE_IN_QUARRY, 100, 50),
    IRON_MINE("ironMine", 200, 0, 2, 20, Resource.IRON, 0, 20),
    WOOD_CUTTER("woodCutter", 100, 0, 1, 3, Resource.WOOD, 0, 120);


    public static final ArrayList<ResourceMakerType> resourceMakerTypeArr = new ArrayList<>(EnumSet.allOf(ResourceMakerType.class));
    private String regex;
    private int health;
    private int gold;
    private int workerNum;
    private boolean engineerWorkers;
    private Resource neededResource;
    private int neededResourceCount;
    private Resource resource;
    private int limit;
    private int rate;
    ResourceMakerType(String regex, int health, int gold, int workerNum,
                       int neededResourceCount, Resource resource,int limit, int rate) {
        this.regex = regex;
        this.health = health;
        this.gold = gold;
        this.workerNum = workerNum;
        this.engineerWorkers = false;
        this.neededResource = Resource.WOOD;
        this.neededResourceCount = neededResourceCount;
        this.resource = resource;
        this.limit = limit;
        this.rate = rate;
    }

    public String getRegex(){
        return regex;
    }

    public static Matcher getMatcher(String input, ResourceMakerType resourceMakerType){
        String regex = resourceMakerType.getRegex();
        return Pattern.compile(regex).matcher(input);
    }

    public static ResourceMakerType getResourceMakerType(String input){
        for (ResourceMakerType resourceMakerType : resourceMakerTypeArr) {
            if (getMatcher(input, resourceMakerType).find()) return resourceMakerType;
        }
        return null;
    }


    public int getHealth() {
        return health;
    }

    public int getGold() {
        return gold;
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

    public Resource getResource() {
        return resource;
    }

    public int getRate() {
        return rate;
    }

    @Override
    public void action(Government government, int buildingCount) {
        if (this.equals(DAIRY)){
            for (int i = 0; i < buildingCount; i++) {
                government.addResources(Resource.LEATHER_VEST, 10, true);
            }
        }
        if (this.equals(QUARRY)) {
            for (int i = 0; i < buildingCount; i++) {
                government.addResources(resource, rate, false);
            }
            government.limitStoneInQuarry();
        } else {
            for (int i = 0; i < buildingCount; i++) {
                government.addResources(resource, rate, true);
            }
        }
    }

    public int getLimit() {
        return limit;
    }
}
