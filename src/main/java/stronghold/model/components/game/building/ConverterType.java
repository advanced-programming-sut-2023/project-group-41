package stronghold.model.components.game.building;

import stronghold.model.components.game.Resource;

import java.util.HashMap;

public enum ConverterType {

    ;

    private int health;
    private int workerNum;
    private boolean engineerWorkers;
    private HashMap<Resource, Integer> neededResources;
    private HashMap<Resource, Resource> options;
    private int rate;

    ConverterType(int health, int workerNum, boolean engineerWorkers, HashMap<Resource, Integer> neededResources, HashMap<Resource, Resource> options, int rate) {
        this.health = health;
        this.workerNum = workerNum;
        this.engineerWorkers = engineerWorkers;
        this.neededResources = neededResources;
        this.options = options;
        this.rate = rate;
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

    public HashMap<Resource, Resource> getOptions() {
        return options;
    }

    public int getRate() {
        return rate;
    }
}
