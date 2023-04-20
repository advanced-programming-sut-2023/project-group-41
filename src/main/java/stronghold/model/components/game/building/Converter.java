package stronghold.model.components.game.building;

import stronghold.model.components.game.building.Building;

import java.util.ArrayList;
import java.util.HashMap;

import stronghold.model.components.game.Resource;
import stronghold.model.components.game.People;

public class Converter extends Building {
    private HashMap<Resources, Resources> options;
    private int rate;

    public Converter(int health, int cost, int workerNum, boolean engineerWorkers, ArrayList<Resources> neededResources,
                     int populationEffect, int popularityEffect, HashMap<Resources, Resources> options, int rate) {
        super(health, cost, workerNum, engineerWorkers, neededResources, populationEffect, popularityEffect);
        this.options = options;
        this.rate = rate;
    }

    private void addOption(Resources out, Resources inp) {
        options.put(out, inp);
    }

    public HashMap<Resources, Resources> getOptions() {
        return options;
    }

    public int getRate() {
        return rate;
    }
}
