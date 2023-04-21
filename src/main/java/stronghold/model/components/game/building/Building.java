package stronghold.model.components.game.building;

import stronghold.model.components.game.Resource;
import stronghold.model.components.game.People;

import java.util.ArrayList;
import java.util.HashMap;

public abstract class Building {
    protected int health;
    protected int workerNum;
    protected boolean engineerWorkers;
    protected HashMap<Resource, Integer> neededResources;

    public Building(int health, int workerNum, boolean engineerWorkers, HashMap<Resource, Integer> neededResources) {
        this.health = health;
        this.workerNum = workerNum;
        this.engineerWorkers = engineerWorkers;
        this.neededResources = neededResources;
    }

    public int getHealth() {
        return health;
    }


    public int getWorkerNum() {
        return workerNum;
    }

    public boolean isEngineerWorkers() {
        return engineerWorkers;
    }

    public HashMap<Resource, Integer> getNeededResources() {
        return neededResources;
    }
}
