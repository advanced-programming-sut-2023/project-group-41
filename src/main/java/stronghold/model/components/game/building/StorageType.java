package stronghold.model.components.game.building;

import stronghold.model.components.game.Government;
import stronghold.model.components.game.enums.Resource;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum StorageType implements BuildingType {
    ENGINEER_GUILD("engineerGuild", 200, 100, 0, false, Resource.WOOD, 10,0),
    STOCK_PILE("stockPile", 200, 0, 0, false, null, 0, 120),
    FOOD_STOCK_PILE("foodStockPile", 400, 0, 0, false, null, 0, 80),
    STABLE("stable", 300, 400, 0, false, Resource.WOOD, 20, 4),
    ARMOURY("armoury", 400, 0, 0, false, Resource.WOOD, 5, 50);

    public static final ArrayList<StorageType> storageTypeArr = new ArrayList<>(EnumSet.allOf(StorageType.class));
    private String regex;
    private int health;
    private int gold;
    private int workerNum;
    private boolean engineerWorkers;
    private Resource neededResource;
    private int neededResourceCount;
    private int capacity;

    StorageType(String regex, int health, int gold, int workerNum, boolean engineerWorkers, Resource neededResource, int neededResourceCount, int capacity) {
        this.regex = regex;
        this.health = health;
        this.gold = gold;
        this.workerNum = workerNum;
        this.engineerWorkers = engineerWorkers;
        this.neededResource = neededResource;
        this.neededResourceCount = neededResourceCount;
        this.capacity = capacity;
    }

    public String getRegex(){
        return regex;
    }

    public static Matcher getMatcher(String input, StorageType storageType){
        String regex = storageType.getRegex();
        return Pattern.compile(regex).matcher(input);
    }

    public static StorageType getStorageType(String input){
        for (StorageType storageType : storageTypeArr) {
            if (getMatcher(input, storageType).find()) return storageType;
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

    public int getCapacity() {
        return capacity;
    }

    @Override
    public void action(Government government, int buildingCount) {

    }
}
