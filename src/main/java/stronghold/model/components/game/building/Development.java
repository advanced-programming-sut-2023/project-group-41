package stronghold.model.components.game.building;

import stronghold.model.components.game.Government;
import stronghold.model.components.game.People;

import java.util.ArrayList;

public class Development extends Building {
    private DevelopmentType developmentType;
    private int incPopularity;
    private int incPopulation;
    private int wineUsageRate;


    Development(DevelopmentType developmentType) {
        super(developmentType.getHealth(), developmentType.getCost(), developmentType.getWorkerNum(),
                developmentType.isEngineerWorkers(), developmentType.getNeededResource(), developmentType.getNeededResourceCount());
        this.developmentType = developmentType;
        this.incPopularity = developmentType.getIncPopularity();
        this.incPopulation = developmentType.getIncPopulation();
        this.wineUsageRate = developmentType.getWineUsageRate();
    }

    public void action(Government government) {
        government.setPopularity(government.getPopularity() + incPopularity);
        ArrayList<People> peopleArr = new ArrayList<>();
        for (int i = 0; i < incPopulation; i++) {
            peopleArr.add(new People(0, 0, 0, 0));
        }
        government.setPeople(peopleArr);
    }

    public DevelopmentType getDevelopmentType() {
        return developmentType;
    }

    public int getIncPopularity() {
        return incPopularity;
    }

    public int getIncPopulation() {
        return incPopulation;
    }

    public int getWineUsageRate() {
        return wineUsageRate;
    }
}
