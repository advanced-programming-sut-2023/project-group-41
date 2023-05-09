package stronghold.model.components.game.building;

import stronghold.model.components.game.Government;

import java.util.EnumSet;

public interface BuildingType {
    String getRegex();
    void action(Government government, int buildingCount);
}
