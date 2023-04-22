
package stronghold.model.components.game.building;

import stronghold.model.components.game.Resource;
import stronghold.model.components.game.People;

import java.util.ArrayList;

public abstract class Building {
    protected int health;
    protected int cost;
    protected int workerNum;
    protected boolean engineerWorkers;
    protected ArrayList<Resource> neededResources;
    protected int populationEffect;
    protected int popularityEffect;

    public Building(int health, int cost, int workerNum, boolean engineerWorkers,
                    ArrayList<Resource> neededResources, int populationEffect, int popularityEffect) {
        this.health = health;
        this.cost = cost;
        this.workerNum = workerNum;
        this.engineerWorkers = engineerWorkers;
        this.neededResources = neededResources;
        this.populationEffect = populationEffect;
        this.popularityEffect = popularityEffect;
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

    public ArrayList<Resource> getNeededResources() {
        return neededResources;
    }

    public int getPopulationEffect() {
        return populationEffect;
    }

    public int getPopularityEffect() {
        return popularityEffect;
    }
}
