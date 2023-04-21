package stronghold.model.components.game.building;

import stronghold.model.components.game.building.Building;

import java.util.ArrayList;
import java.util.HashMap;

import stronghold.model.components.game.Resource;
import stronghold.model.components.game.People;

public class Storage extends Building {
    private int size;
    private HashMap<Resource, Integer> list;

    public Storage(int health, int cost, int workerNum, boolean engineerWorkers, ArrayList<Resource> neededResources,
                   int populationEffect, int popularityEffect, int size, HashMap<Resource, Integer> list) {
        super(health, cost, workerNum, engineerWorkers, neededResources, populationEffect, popularityEffect);
        this.size = size;
        this.list = list;
    }

    public void addResources(Resource resources, int amount) {
        if (list.containsKey(resources)) list.replace(resources, list.get(resources) + amount);
        else list.put(resources, amount);
    }

    public void removeResources(Resource resources, int amount) {
        list.put(resources, list.get(resources) - amount);
    }

    public int getSize() {
        return size;
    }

    public HashMap<Resource, Integer> getList() {
        return list;
    }
}
