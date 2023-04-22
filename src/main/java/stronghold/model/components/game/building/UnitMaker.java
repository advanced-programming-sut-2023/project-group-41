<<<<<<< HEAD
<<<<<<< HEAD
package stronghold.model.components.game.building;

import stronghold.model.components.game.building.Building;

import java.util.ArrayList;

import stronghold.model.components.game.enums.Resource;
import stronghold.model.components.game.People;

public class UnitMaker extends Building {
    private People people;

    public UnitMaker(int health, int cost, int workerNum, boolean engineerWorkers,
                     ArrayList<Resource> neededResources, int populationEffect, int popularityEffect, People people) {
        super(health, cost, workerNum, engineerWorkers, neededResources, populationEffect, popularityEffect);
        this.people = people;
    }

}
=======
=======
>>>>>>> 2c354c3e141bdb26633c22d637da4e87f73c5938
package stronghold.model.components.game.building;

import stronghold.model.components.game.building.Building;

import java.util.ArrayList;

import stronghold.model.components.game.Resource;
import stronghold.model.components.game.People;

public class UnitMaker extends Building {
    private People people;

    public UnitMaker(int health, int cost, int workerNum, boolean engineerWorkers,
                     ArrayList<Resource> neededResources, int populationEffect, int popularityEffect, People people) {
        super(health, cost, workerNum, engineerWorkers, neededResources, populationEffect, popularityEffect);
        this.people = people;
    }

}
