package stronghold.model.components.game.building;

import stronghold.model.components.game.Map;
import stronghold.model.components.game.enums.Resource;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum ConverterType {

    MILL("mill", 100, 0, 3, false, Resource.WOOD, 20, Resource.WHEAT, Resource.FLOUR, 100 ),
    ARMOURER("armourer", 100, 100, 1, false, Resource.WOOD, 20, Resource.IRON, Resource.ARMOR, 100),
    BLACKSMITH("blacksmith", 100, 100, 1, false, Resource.WOOD, 20, Resource.IRON, Resource.SWORD, 100),
    FLETCHER("fletcher", 100, 100, 1, false, Resource.WOOD, 20, Resource.WOOD, Resource.CROSS_BOW, 100),
    POLETURNER("poleturner", 100, 100, 1, false, Resource.WOOD, 10, Resource.WOOD, Resource.SPEAR, 100),
    BAKERY("bakery", 100, 0, 1, false, Resource.WOOD, 10, Resource.FLOUR, Resource.BREAD, 100),
    BREWING("brewing", 100, 0, 1, false, Resource.WOOD, 10,Resource.HOPS, Resource.ALE,  100),
    BARRACKS("barracks", 100, 0, 0, false, Resource.STONE, 15, Resource.GOLD, Resource.PEOPLE, 100 ),
    MERCENARY_POST("mercenaryPost", 100, 0, 0, false, Resource.WOOD, 10, Resource.GOLD, Resource.PEOPLE, 100),
    SHOP("post", 100, 0, 1, false, Resource.WOOD, 5, Resource.GOLD, Resource.GOLD, 0);

    private static final ArrayList<ConverterType> converterTypeArr = new ArrayList<>(EnumSet.allOf(ConverterType.class));
    private String regex;
    private int health;
    private int gold;
    private int workerNum;
    private boolean engineerWorkers;
    private Resource  neededResource;
    private int neededResourceCount;
    private Resource inpResource;
    private Resource outResource;
    private int rate;

    ConverterType(String regex, int health, int gold, int workerNum, boolean engineerWorkers,
                  Resource neededResource, int neededResourceCount, Resource inpResource, Resource outResource, int rate) {
        this.regex = regex;
        this.health = health;
        this.gold = gold;
        this.workerNum = workerNum;
        this.engineerWorkers = engineerWorkers;
        this.neededResource = neededResource;
        this.neededResourceCount = neededResourceCount;
        this.inpResource = inpResource;
        this.outResource = outResource;
        this.rate = rate;
    }
    public String getRegex(){
        return regex;
    }

    public static Matcher getMatcher(String input, ConverterType converterType){
        String regex = converterType.getRegex();
        return Pattern.compile(regex).matcher(input);
    }

    public static ConverterType getConverterType(String input){
        for (ConverterType converterType : converterTypeArr) {
            if (getMatcher(input, converterType).find()) return converterType;
        }
        return null;
    }

    public int getHealth() {
        return health;
    }

    public int getGold() {
        return gold;
    }

    public int getWorkerNum() {
        return workerNum;
    }

    public boolean isEngineerWorkers() {
        return engineerWorkers;
    }

    public Resource getNeededResource() {
        return neededResource;
    }

    public int getNeededResourceCount() {
        return neededResourceCount;
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
