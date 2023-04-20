package stronghold.model.components.game.building;

import stronghold.model.components.game.building.Building;

import stronghold.model.components.game.java.util.ArrayList;

import stronghold.model.components.game.Resource;
import stronghold.model.components.game.People;

public class ResourceMaker extends Building {
    private Resources resource;
    private int rate;

    public ResourceMaker(int health, int cost, int workerNum, boolean engineerWorkers,
                         ArrayList<Resources> neededResources, int populationEffect, int popularityEffect, Resources resource, int rate) {
        super(health, cost, workerNum, engineerWorkers, neededResources, populationEffect, popularityEffect);
        this.resource = resource;
        this.rate = rate;
    }
}
