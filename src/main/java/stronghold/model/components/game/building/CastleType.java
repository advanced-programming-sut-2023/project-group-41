package stronghold.model.components.game.building;

import stronghold.model.components.game.enums.Food;
import stronghold.model.components.game.enums.Resource;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum CastleType {
    SMALL_STONE_GATEHOUSE("small\\s+stone\\s+gatehouse", 100, 0, 0, false, null, 0, 8, 0, 0),
    BIG_STONE_GATEHOUSE("big\\s+stone\\s+gatehouse", 100, 0 , 0, false, Resource.STONE, 20, 10, 0, 0),
    DRAWBRIDGE("drawbridge", 100, 0 , 0, false, Resource.WOOD, 10, 0, 1, 0),
    LOOKOUT_TOWER("lookout\\s+tower", 100, 0, 0, false, Resource.STONE, 10, 0, 0, 0),
    PERIMETER_TOWER("perimeter\\s+tower", 100, 0, 0, false, Resource.STONE, 10, 0, 0, 100),
    DEFENSIVE_TURRET("defensive\\s+tower", 200, 0, 0, false, Resource.STONE, 15,0, 0, 100),
    SQUARE_TOWER("square\\s+tower", 100, 0, 0, false, Resource.STONE, 35, 100, 0, 100),
    CIRCLE_TOWER("circle\\s+tower", 100, 0, 0, false, Resource.STONE, 40, 100, 0, 100),
    KILLING_PIT("killing\\s+pit",100, 0, 0, false, Resource.WOOD, 6, 0, 0, 1),
    OIL_SMELTER("oil\\s+smelter", 100, 100, 1, true, Resource.IRON, 10, 0, 1, 0),
    PITCH_DITCH("pitch\\s+ditch", 100, 0, 0, false, Resource.PITCH, 2, 5, 1, 0),
    CAGED_WAR_DOGS("caged\\s+war\\s+", 100, 100, 0, false, Resource.WOOD, 10, 0, 0, 100),
    SIEGE_TENT("siege\\s+tent", 100, 0, 1, true, null, 0, 0, 100, 0);
    private static final ArrayList<CastleType> castleTypeArr = new ArrayList<>(EnumSet.allOf(CastleType.class));
    private String regex;
    private int health;
    private int gold;
    private int workerNum;
    private boolean engineerWorkers;
    private Resource neededResource;
    private int neededResourceCount;
    private int size;
    private int fireRange;
    private int defendRange;

    CastleType(String regex, int health, int gold, int workerNum, boolean engineerWorkers, Resource neededResource,
               int neededResourceCount, int size, int fireRange, int defendRange) {
        this.regex = regex;
        this.health = health;
        this.gold = gold;
        this.workerNum = workerNum;
        this.engineerWorkers = engineerWorkers;
        this.neededResource = neededResource;
        this.neededResourceCount = neededResourceCount;
        this.size = size;
        this.fireRange = fireRange;
        this.defendRange = defendRange;
    }

    public String getRegex() {
        return regex;
    }

    public static Matcher getMatcher(String input, CastleType castleType) {
        String regex = castleType.getRegex();
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(input);
    }

    public static CastleType getCastleType(String input) {
        for (CastleType castleType : castleTypeArr) {
            if(getMatcher(input, castleType).find()) return castleType;
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

    public int getSize() {
        return size;
    }

    public int getFireRange() {
        return fireRange;
    }

    public int getDefendRange() {
        return defendRange;
    }
}
