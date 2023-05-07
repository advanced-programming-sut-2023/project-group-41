package stronghold.model.components.game.building;


import stronghold.model.components.game.Government;
import stronghold.model.components.game.enums.Resource;
import stronghold.model.components.general.User;

public abstract class Building {
    protected Government ownership;
    protected int health;
    protected int cost;
    protected int workerNum;
    protected boolean engineerWorkers;
    protected Resource neededResource;
    protected int neededResourceCount;

    public Building(Government ownership, int health,int cost, int workerNum, boolean engineerWorkers, Resource neededResources, int neededResourceCount) {
        this.ownership = ownership;
        this.health = health;
        this.cost = cost;
        this.workerNum = workerNum;
        this.engineerWorkers = engineerWorkers;
        this.neededResource = neededResources;
        this.neededResourceCount = neededResourceCount;
    }

    public Government getOwnership() {
        return ownership;
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

    public static Building getBuilding(Government ownership, String input){
        if (CastleType.getCastleType(input) != null)
            return new Castle(ownership, CastleType.getCastleType(input));
        else if (ConverterType.getConverterType(input) != null)
            return new Converter(ownership, ConverterType.getConverterType(input));
        else if (DevelopmentType.getDevelopmentType(input) != null)
            return new Development(ownership, DevelopmentType.getDevelopmentType(input));
        else if (ResourceMakerType.getResourceMakerType(input) != null)
            return new ResourceMaker(ownership, ResourceMakerType.getResourceMakerType(input));
        else if (StorageType.getStorageType(input) != null)
            return new Storage(ownership, StorageType.getStorageType(input));
        else
            return null;
    }

    public abstract String getRegex();
}
