package stronghold.model.components.game.building;

import stronghold.model.components.game.building.Building;

import java.util.ArrayList;
import java.util.HashMap;

import stronghold.model.components.game.Resource;
import stronghold.model.components.game.People;

public class Castle extends Building {
    private int size;
    private HashMap<People, Integer> peoples;
    private int fireRange;
    private int defendRange;

    public Castle(int health, int cost, int workerNum, boolean engineerWorkers, ArrayList<Resources> neededResources, int populationEffect, int popularityEffect,
                  int size, HashMap<People, Integer> peoples, int fireRange, int defendRange) {
        super(health, cost, workerNum, engineerWorkers, neededResources, populationEffect, popularityEffect);
        this.size = size;
        this.peoples = peoples;
        this.fireRange = fireRange;
        this.defendRange = defendRange;
    }

    public void addPeople(People people, int amount) {
         if(peoples.containsKey(people)) peoples.replace(people, peoples.get(people) + amount);
         else peoples.put(people, amount);
    }

    public void removePeople(People people, int amount) {
        peoples.replace(people, peoples.get(people) - amount);
    }

    public int getSize() {
        return size;
    }

    public HashMap<People, Integer> getPeoples() {
        return peoples;
    }

    public int getFireRange() {
        return fireRange;
    }

    public int getDefendRange() {
        return defendRange;
    }
}
