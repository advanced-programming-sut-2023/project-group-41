package stronghold.model.components.game.building;


import stronghold.model.components.game.enums.Resource;

public abstract class Building {
    protected int health;
    protected int cost;
    protected int workerNum;
    protected boolean engineerWorkers;
    protected Resource neededResource;
    protected int neededResourceCount;

    public Building(int health,int cost, int workerNum, boolean engineerWorkers, Resource neededResources, int neededResourceCount) {
        this.health = health;
        this.cost = cost;
        this.workerNum = workerNum;
        this.engineerWorkers = engineerWorkers;
        this.neededResource = neededResources;
        this.neededResourceCount = neededResourceCount;
    }

    public int getHealth() {
        return health;
    }

    public int getCost() { return cost;}
    public int getWorkerNum() {
        return workerNum;
    }

    public boolean isEngineerWorkers() {
        return engineerWorkers;
    }

    public Resource getNeededResource() {
        return neededResource;
    }
    public int getNeededResourceCount() { return neededResourceCount;}

    public static Building getBuilding(String input){
        if (CastleType.getCastleType(input) != null)
            return new Castle(CastleType.getCastleType(input));
        else if (ConverterType.getConverterType(input) != null)
            return new Converter(ConverterType.getConverterType(input));
        else if (DevelopmentType.getDevelopmentType(input) != null)
            return new Development(DevelopmentType.getDevelopmentType(input));
        else if (ResourceMakerType.getResourceMakerType(input) != null)
            return new ResourceMaker(ResourceMakerType.getResourceMakerType(input));
        else if (StorageType.getStorageType(input) != null)
            return new Storage(StorageType.getStorageType(input));
        else
            return null;
    }
}
