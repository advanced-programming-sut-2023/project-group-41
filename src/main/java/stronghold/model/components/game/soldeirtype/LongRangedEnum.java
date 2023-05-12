package stronghold.model.components.game.soldeirtype;

import stronghold.model.components.game.enums.Resource;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum LongRangedEnum {
    archer("archer",4,2,2,1,3,false,false,Resource.CROSS_BOW),
    crossbowMen("crossbowMen",2,3,2,1,2,false,false,Resource.CROSS_BOW),
    archerBow("archerBow",4,2,2,1,3,false,false,Resource.CROSS_BOW),
    slingers("slingers",4,1,2,1,1,false,true,null ),
    horseArcher("horseArcher",5,3,2,1,2,true,true,Resource.CROSS_BOW),
    fireThrowers("fireThrowers",5,3,4,1,2,false,true,null);

    public Resource getResource() {
        return resource;
    }

    private int speed;
    private String regex;
    private int defense;
    private int offense;
    private int price;
    private int range;
    private boolean isHorsed;
    boolean isArab;
    Resource resource;
    LongRangedEnum(  String regex,int speed, int defense, int offense, int price,int  range, boolean isHorsed,boolean isArab,Resource resource){
        this.resource=resource;
        this.regex=regex;
        this.defense=defense;
        this.offense=offense;
        this.range=range;
        this.isHorsed=isHorsed;
        this.speed=speed;
        this.price=price;
        this.isArab=isArab;

    }
    public String getRegex() {
        return regex;
    }

    public int getPrice() {
        return price;
    }

    public int getOffense() {
        return offense;
    }

    public int getDefense() {
        return defense;
    }

    public int getSpeed() {
        return speed;
    }

    public int getRange() {
        return range;
    }

    public boolean isHorsed() {
        return isHorsed;
    }

    public boolean isArab() {
        return isArab;
    }
    private static final ArrayList<LongRangedEnum> longRangedArr = new ArrayList<>(EnumSet.allOf(LongRangedEnum.class));
    public static Matcher getMatcher(String input, LongRangedEnum castleType) {
        String regex = castleType.getRegex();
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(input);
    }

    public static LongRangedEnum getLongRangedType(String input) {
        for (LongRangedEnum castleType : longRangedArr) {
            if(getMatcher(input, castleType).find()) return castleType;
        }
        return null;
    }
}

