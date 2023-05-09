package stronghold.model.components.game.building;


import stronghold.model.components.game.Government;
import stronghold.model.components.game.enums.Resource;
import stronghold.model.components.general.User;

import static stronghold.model.components.game.enums.Resource.*;

public abstract class Building {
    protected Government ownership;
    protected BuildingType buildingType;
    protected int health;
    protected int cost;
    protected int workerNum;
    protected boolean engineerWorkers;
    protected Resource neededResource;
    protected int neededResourceCount;

    public Building(Government ownership, BuildingType buildingType, int health,int cost, int workerNum, boolean engineerWorkers, Resource neededResources, int neededResourceCount) {
        this.ownership = ownership;
        this.buildingType = buildingType;
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

    public BuildingType getBuildingType() {
        return buildingType;
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

    public boolean haveEnoughResource(Government government){
        if (government.getBalance() >= cost &&
                ((!engineerWorkers && government.resourceCheck(WORKER, workerNum)) ||
                        (engineerWorkers && government.resourceCheck(ENGINEER, workerNum))) &&
                government.resourceCheck(neededResource, neededResourceCount)){
            government.setBalance(government.getBalance() - cost);
            if (engineerWorkers) government.useResource(ENGINEER, workerNum);
            else government.useResource(WORKER, workerNum);
            government.useResource(neededResource, neededResourceCount);
            return true;
        }
        return false;
    }

    public abstract String getRegex();
    //public abstract void action();
}
