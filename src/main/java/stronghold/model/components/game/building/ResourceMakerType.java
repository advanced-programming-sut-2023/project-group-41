package stronghold.model.components.game.building;

import stronghold.model.components.game.enums.Resource;

public enum ResourceMakerType {
    WHEAT_FARM(100, 0, 1, 15, Resource.WHEAT, 0,  100),
    HUNT_POST(100, 0, 1, 5, Resource.MEAT, 0,100),
    HOPS_FARM(100, 0, 1, 15, Resource.HOPS, 0,100),
    DAIRY(100, 0, 1, 15, Resource.CHEESE, 0,100),//also make Leather Vest
    APPLE_GARDEN(100, 0, 1, 5, Resource.APPLE, 0, 100),
    PITCH_RIG(100, 0, 1, 20, Resource.PITCH, 0, 100),
    QUARRY(100, 0, 3, 20, Resource.STONE, 100, 100),
    IRON_MINE(100, 0, 2, 20, Resource.IRON, 0, 100),
    STABLE(100, 400, 0, 20, Resource.HORSE, 4, 100),
    WOOD_CUTTER(100, 0, 1, 3, Resource.WOOD, 0, 100);



    private int health;
    private int gold;
    private int workerNum;
    private boolean engineerWorkers;
    private Resource neededResource;
    private int neededResourceCount;
    private Resource resource;
    private int limit;
    private int rate;
    ResourceMakerType(int health, int gold, int workerNum,
                       int neededResourceCount, Resource resource,int limit, int rate) {
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
}
