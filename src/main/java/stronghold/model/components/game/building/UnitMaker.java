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
>>>>>>> 2bfb39568ff9b671a99d1ea453cd2f82ca82dd30
