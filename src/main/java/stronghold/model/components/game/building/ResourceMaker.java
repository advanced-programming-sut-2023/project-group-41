package stronghold.model.components.game.building;

import stronghold.model.components.game.building.Building;


import stronghold.model.components.game.enums.Resource;

import java.util.ArrayList;

public class ResourceMaker extends Building {
    private ResourceMakerType resourceMakerType;
    private Resource resource;
    private int rate;

    public ResourceMaker(ResourceMakerType resourceMakerType) {
        super(resourceMakerType.getHealth(), resourceMakerType.getGold(), resourceMakerType.getWorkerNum(), resourceMakerType.isEngineerWorkers(), resourceMakerType.getNeededResource(), resourceMakerType.getNeededResourceCount());
        this.resourceMakerType = resourceMakerType;
        this.resource = resourceMakerType.getResource();
        this.rate = resourceMakerType.getRate();
    }

    public Resource getResource() {
        return resource;
    }

    public int getRate() {
        return rate;
    }
}
