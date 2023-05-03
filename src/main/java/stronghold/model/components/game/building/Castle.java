
package stronghold.model.components.game.building;

import java.util.HashMap;

import stronghold.model.components.game.Government;
import stronghold.model.components.game.People;
import stronghold.model.components.general.User;

public class Castle extends Building {
    private int size;
    private HashMap<People, Integer> peopleMap;
    private int fireRange;
    private int defendRange;

    public Castle(Government ownership, CastleType castleType) {
        super(ownership, castleType.getHealth(), castleType.getGold(), castleType.getWorkerNum(), castleType.isEngineerWorkers(),
                castleType.getNeededResource(), castleType.getNeededResourceCount());
        this.size = castleType.getSize();
        this.peopleMap = new HashMap<>();
        this.fireRange = castleType.getFireRange();
        this.defendRange = castleType.getDefendRange();
    }

    public void addPeople(People people, int amount) {
         if(peopleMap.containsKey(people)) peopleMap.replace(people, peopleMap.get(people) + amount);
         else peopleMap.put(people, amount);
    }

    public void removePeople(People people, int amount) {
        peopleMap.replace(people, peopleMap.get(people) - amount);
    }

    public int getSize() {
        return size;
    }

    public HashMap<People, Integer> getPeopleMap() {
        return peopleMap;
    }

    public int getFireRange() {
        return fireRange;
    }

    public int getDefendRange() {
        return defendRange;
    }
}
