package stronghold.model.components.game.building;

import stronghold.model.components.game.enums.Resource;

public enum CastleType {
    SMALL_STONE_GATEHOUSE(100, 0, 0, false, null, 0, 8, 0, 0),
    BIG_STONE_GATEHOUSE(100, 0 , 0, false, Resource.STONE, 20, 10, 0, 0),
    DRAWBRIDGE(100, 0 , 0, false, Resource.WOOD, 10, 0, 1, 0),
    LOOKOUT_TOWER(100, 0, 0, false, Resource.STONE, 10, 0, 0, 0),
    PERIMETER_TOWER(100, 0, 0, false, Resource.STONE, 10, 0, 0, 100),
    DEFENSIVE_TURRET(200, 0, 0, false, Resource.STONE, 15,0, 0, 100),
    SQUARE_TOWER(100, 0, 0, false, Resource.STONE, 35, 100, 0, 100),
    CIRCLE_TOWER(100, 0, 0, false, Resource.STONE, 40, 100, 0, 100),
    KILLING_PIT(100, 0, 0, false, Resource.WOOD, 6, 0, 0, 1),
    OIL_SMELTER(100, 100, 1, true, Resource.IRON, 10, 0, 1, 0),
    PITCH_DITCH(100, 0, 0, false, Resource.PITCH, 2, 5, 1, 0),
    CAGED_WAR_DOGS(100, 100, 0, false, Resource.WOOD, 10, 0, 0, 100),
    SIEGE_TENT(100, 0, 1, true, null, 0, 0, 100, 0);
    private int health;
    private int gold;
    private int workerNum;
    private boolean engineerWorkers;
    private Resource neededResource;
    private int neededResourceCount;
    private int size;
    private int fireRange;
    private int defendRange;

    CastleType(int health, int gold, int workerNum, boolean engineerWorkers, Resource neededResource,
               int neededResourceCount, int size, int fireRange, int defendRange) {
        this.health = health;
        this.gold = gold;
        this.workerNum = workerNum;
        this.engineerWorkers = engineerWorkers;
        this.neededResource = neededResource;
        this.neededResourceCount = neededResourceCount;
        this.size = size;
        this.fireRange = fireRange;
        this.defendRange = defendRange;
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

    public int getSize() {
        return size;
    }

    public int getFireRange() {
        return fireRange;
    }

    public int getDefendRange() {
        return defendRange;
    }
}
