package stronghold.model.components.game.building;

import stronghold.model.components.game.enums.Resource;

public enum StorageType {
    ENGINEER_GUILD(100, 100, 0, false, Resource.WOOD, 10,100),
    STOCK_PILE(100, 0, 0, false, null, 0, 100),
    FOOD_STOCK_PILE(100, 0, 0, false, null, 0, 100),
    Ox_TETHER(100,0, 1, false, Resource.WOOD,  5, 12),
    ARMOURY(100, 0, 0, false, Resource.WOOD, 5, 100);

    private int health;
    private int gold;
    private int workerNum;
    private boolean engineerWorkers;
    private Resource neededResource;
    private int neededResourceCount;
    private int capacity;

    StorageType(int health, int gold, int workerNum, boolean engineerWorkers, Resource neededResource, int neededResourceCount, int capacity) {
        this.health = health;
        this.gold = gold;
        this.workerNum = workerNum;
        this.engineerWorkers = engineerWorkers;
        this.neededResource = neededResource;
        this.neededResourceCount = neededResourceCount;
        this.capacity = capacity;
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
}
