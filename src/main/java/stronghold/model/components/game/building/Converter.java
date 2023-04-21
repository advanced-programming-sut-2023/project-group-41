package stronghold.model.components.game.building;

import stronghold.model.components.game.building.Building;

import java.util.ArrayList;
import java.util.HashMap;

import stronghold.model.components.game.Resource;
import stronghold.model.components.game.People;

public class Converter extends Building {
    private ConverterType converterType;
    private HashMap<Resource, Resource> options;
    private int rate;

    public Converter(ConverterType converterType) {
        super(converterType.getHealth(), converterType.getWorkerNum(), converterType.isEngineerWorkers(), converterType.getNeededResources());
        this.converterType = converterType;
        this.options = converterType.getOptions();
        this.rate = converterType.getRate();
    }

    private void addOption(Resource out, Resource inp) {
        options.put(out, inp);
    }

    public HashMap<Resource, Resource> getOptions() {
        return options;
    }

    public int getRate() {
        return rate;
    }
}
