
package stronghold.model.components.game.building;

import stronghold.model.components.game.building.Building;

import java.util.ArrayList;
import java.util.HashMap;

import stronghold.model.components.game.Resource;
import stronghold.model.components.game.People;

public class Converter extends Building {
    private HashMap<Resource, Resource> options;
    private int rate;

    public Converter(int health, int cost, int workerNum, boolean engineerWorkers, ArrayList<Resource> neededResources,
                     int populationEffect, int popularityEffect, HashMap<Resource, Resource> options, int rate) {
        super(health, cost, workerNum, engineerWorkers, neededResources, populationEffect, popularityEffect);
        this.options = options;
        this.rate = rate;
    }

    private void addOption(Resource out, Resource inp) {
        options.put(out, inp);
    }

    public HashMap<Resource, Resource> getOptions() {
        return options;
    }

    public int getRate() {
        return rate;
    }
}
