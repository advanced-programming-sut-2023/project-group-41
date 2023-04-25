package stronghold.model.components.game.building;

import stronghold.model.components.game.building.Building;

import java.util.ArrayList;
import java.util.HashMap;

import stronghold.model.components.game.building.ConverterType;
import stronghold.model.components.game.enums.Resource;

public class Converter extends Building {
    private ConverterType converterType;
    private Resource inpResource;
    private Resource outResource;
    private int rate;

    public Converter(ConverterType converterType) {
        super(converterType.getHealth(), converterType.getGold(), converterType.getWorkerNum(), converterType.isEngineerWorkers(), converterType.getNeededResource(), converterType.getNeededResourceCount());
        this.converterType = converterType;
        this.inpResource = converterType.getInpResource();
        this.outResource = converterType.getOutResource();
        this.rate = converterType.getRate();
    }

    public ConverterType getConverterType() {
        return converterType;
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
