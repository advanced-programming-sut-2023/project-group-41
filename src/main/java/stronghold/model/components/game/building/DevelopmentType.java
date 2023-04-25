package stronghold.model.components.game.building;

import stronghold.model.components.game.enums.Resource;

public enum DevelopmentType {
    CHURCH(100, 250, 0, false, null, 0, 2, 0, 0),//monk satay here
    CATHEDRAL(100, 1000, 0, false, null, 0, 2, 1, 0),
    INN(100, 100, 1, false, Resource.WOOD, 20, 100, 0, 100),
    HOUSE(100, 0, 0, false, Resource.WOOD, 6, 8, 0, 0);

    private int health;
    private int cost;
    private int workerNum;
    private boolean engineerWorkers;
    private Resource neededResource;
    private int neededResourceCount;
    private int incPopularity;
    private int incPopulation;
    private int wineUsageRate;

    DevelopmentType(int health, int cost, int workerNum, boolean engineerWorkers, Resource neededResource, int neededResourceCount, int incPopularity, int incPopulation, int wineUsageRate) {
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
}
