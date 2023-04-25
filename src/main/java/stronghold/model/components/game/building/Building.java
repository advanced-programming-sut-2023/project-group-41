package stronghold.model.components.game.building;

import stronghold.model.components.game.Resource;

public abstract class Building {
    protected int health;
    protected int cost;
    protected int workerNum;
    protected boolean engineerWorkers;
    protected Resource  neededResource;
    protected int neededResourceCount;

    public Building(int health,int cost, int workerNum, boolean engineerWorkers, Resource neededResources, int neededResourceCount) {
        this.health = health;
        this.cost = cost;
        this.workerNum = workerNum;
        this.engineerWorkers = engineerWorkers;
        this.neededResource = neededResources;
        this.neededResourceCount = neededResourceCount;
    }

    public int getHealth() {
        return health;
    }

    public int getCost() { return cost;}
    public int getWorkerNum() {
        return workerNum;
    }

    public boolean isEngineerWorkers() {
        return engineerWorkers;
    }

    public Resource getNeededResource() {
        return neededResource;
    }
    public int getNeededResourceCount() { return neededResourceCount;}
}
