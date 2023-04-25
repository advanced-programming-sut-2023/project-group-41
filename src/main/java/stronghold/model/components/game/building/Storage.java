
package stronghold.model.components.game.building;

import stronghold.model.components.game.building.Building;

import java.util.ArrayList;
import java.util.HashMap;

import stronghold.model.components.game.enums.Resource;


public class Storage extends Building {

    private StorageType storageType;
    private int capacity;
    private HashMap<Resource, Integer> list;

    public Storage(StorageType storageType) {
        super(storageType.getHealth(), storageType.getGold(), storageType.getWorkerNum(), storageType.isEngineerWorkers(), storageType.getNeededResource(), storageType.getNeededResourceCount());
        this.storageType = storageType;
        this.capacity = storageType.getCapacity();
        list = new HashMap<>();
    }

    public StorageType getStorageType() {
        return storageType;
    }

    public void addResources(Resource resources, int amount) {
        if (list.containsKey(resources)) list.replace(resources, list.get(resources) + amount);
        else list.put(resources, amount);
    }

    public void removeResources(Resource resources, int amount) {
        list.put(resources, list.get(resources) - amount);
    }

    public int getCapacity() {
        return capacity;
    }

    public HashMap<Resource, Integer> getList() {
        return list;
    }
}
