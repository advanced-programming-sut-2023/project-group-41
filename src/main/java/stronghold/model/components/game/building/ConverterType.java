package stronghold.model.components.game.building;

import stronghold.model.components.game.Resource;

import java.nio.file.attribute.FileAttribute;
import java.util.HashMap;

public enum ConverterType {

    MILL(100, 0, 3, false, Resource.WOOD, 20, Resource.WHEAT, Resource.FLOUR, 100 ),
    ARMOURER(100, 100, 1, false, Resource.WOOD, 20, Resource.IRON, Resource.ARMOR, 100),
    BLACKSMITH(100, 100, 1, false, Resource.WOOD, 20, Resource.IRON, Resource.SWORD, 100),
    FLETCHER(100, 100, 1, false, Resource.WOOD, 20, Resource.WOOD, Resource.CROSS_BOW, 100),
    POLETURNER(100, 100, 1, false, Resource.WOOD, 10, Resource.WOOD, Resource.SPEAR, 100),
    BAKERY(100, 0, 1, false, Resource.WOOD, 10, Resource.FLOUR, Resource.BREAD, 100),
    BREWING(100, 0, 1, false, Resource.WOOD, 10,Resource.HOPS, Resource.ALE,  100);

    private int health;
    private int gold;
    private int workerNum;
    private boolean engineerWorkers;
    private Resource  neededResource;
    private int neededResourceCount;
    private Resource inpResource;
    private Resource outResource;
    private int rate;

    ConverterType(int health, int gold, int workerNum, boolean engineerWorkers,
                  Resource neededResource, int neededResourceCount, Resource inpResource, Resource outResource, int rate) {
        this.health = health;
        this.gold = gold;
        this.workerNum = workerNum;
        this.engineerWorkers = engineerWorkers;
        this.neededResource = neededResource;
        this.neededResourceCount = neededResourceCount;
        this.inpResource = inpResource;
        this.outResource = outResource;
        this.rate = rate;
    }

    public int getHealth() {
        return health;
    }

    public int getGold() {
        return gold;
    }

    public int getWorkerNum() {
        return workerNum;
    }

    public boolean isEngineerWorkers() {
        return engineerWorkers;
    }

    public Resource getNeededResource() {
        return neededResource;
    }

    public int getNeededResourceCount() {
        return neededResourceCount;
    }


    public Resource getInpResource() {
        return inpResource;
    }

    public Resource getOutResource() {
        return outResource;
    }

    public int getRate() {
        return rate;
    }
}
