
package stronghold.model.components.game.building;

import java.util.HashMap;

import stronghold.model.components.game.Government;
import stronghold.model.components.game.enums.Resource;
import stronghold.model.components.general.User;


public class Storage extends Building {

    private StorageType storageType;
    private int capacity;
    private HashMap<Resource, Integer> list;

    public Storage(Government ownership, StorageType storageType) {
        super(ownership, storageType, storageType.getHealth(), storageType.getGold(), storageType.getWorkerNum(), storageType.isEngineerWorkers(), storageType.getNeededResource(), storageType.getNeededResourceCount());
        ownership.addBuilding(storageType);
        this.storageType = storageType;
        this.capacity = storageType.getCapacity();
        list = new HashMap<>();
        if (storageType.equals(StorageType.STOCK_PILE)) ownership.incFreeStockSpace(capacity);
        else if (storageType.equals(StorageType.FOOD_STOCK_PILE)) ownership.incFreeFoodStockSpace(capacity);
        else if (storageType.equals(StorageType.ARMOURY)) ownership.incFreeArmourySpace(capacity);
        else if (storageType.equals(StorageType.STABLE)) ownership.addResources(Resource.HORSE, 4, false);
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

    @Override
    public String getRegex() {
        return storageType.getRegex();
    }
}
