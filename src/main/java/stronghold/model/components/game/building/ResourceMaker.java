

import stronghold.model.components.game.building.Building;


import stronghold.model.components.game.enums.Resource;

import java.util.ArrayList;

public class ResourceMaker extends Building {
    private Resource resource;
    private int rate;

    public ResourceMaker(int health, int cost, int workerNum, boolean engineerWorkers,
                         ArrayList<Resource> neededResources, int populationEffect, int popularityEffect, Resource resource, int rate) {
        super(health, cost, workerNum, engineerWorkers, neededResources, populationEffect, popularityEffect);
        this.resource = resource;
        this.rate = rate;
    }
}
