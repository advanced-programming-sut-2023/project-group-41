package stronghold.model.components.game.soldeirtype;

import stronghold.model.components.game.enums.Resource;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum FighterEnum {
    spearMan("spearMan",3,1,3,1,false,false,false,Resource.SPEAR),
    pikeMan("pikeMan",2,4,3,1,false,false,false,Resource.STONE),
    maceMan("maceMan",3,3,4,1,false,false,false,null),
    swordsMan("swordsMan",1,1,5,1,false,false,false,Resource.SWORD),
    Knight("Knight",5,4,5,1,false,true,false,Resource.HORSE),
    blackMonk("blackMonk",2,3,3,1,false,false,false,null),
    slaves("slaves",4,0,1,1,false,false,true,null ),
    assassins("assassins",3,3,3,1,true,false,true,null),
    arabianSwordsMen("arabianSwordsMen",5,4,4,1,false,false,true,Resource.SWORD);


    private int speed;
    private int defense;
    private int offense;
    private int price;
    private boolean isAssassin;
    private boolean isHorsed;
    private boolean isArab;
    private String regex;
    private Resource resource;
    FighterEnum(  String regex,int speed, int defense, int offense, int price,boolean isAssassin, boolean isHorsed,boolean isArab,Resource resource){
        this.resource=resource;
        this.defense=defense;
        this.offense=offense;
        this.isAssassin=isAssassin;
        this.isHorsed=isHorsed;
        this.speed=speed;
        this.price=price;
        this.isArab=isArab;
        this.regex=regex;

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

    public boolean isAssassin() {
        return isAssassin;
    }

    public boolean isHorsed() {
        return isHorsed;
    }

    public boolean isArab() {
        return isArab;
    }
    private static final ArrayList<FighterEnum> fighterArr = new ArrayList<>(EnumSet.allOf(FighterEnum.class));
    public static Matcher getMatcher(String input, FighterEnum castleType) {
        String regex = castleType.getRegex();
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(input);
    }

    public static FighterEnum getFighterType(String input) {
        for (FighterEnum castleType : fighterArr) {
            if(getMatcher(input, castleType).find()) return castleType;
        }
        return null;
    }

    public Resource getResource() {
        return resource;
    }
}
