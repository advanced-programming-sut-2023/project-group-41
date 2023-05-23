package stronghold.model.components.game.soldeirtype;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum UnarmedEnum {
    tunneler("tunneler",4,1,1,3),
    ladderMen("ladderMen",4,1,1,0),
    engineer("engineer",3,1,1,0),
    worker("worker", 3, 0, 0, 0);
    private String type;
    private int speed;
    private int price;
    private int defense;
    private int offense;

    UnarmedEnum(String type,int speed,int price,int defense,int offense){
        this.type=type;
        this.speed=speed;
        this.price=price;
        this.defense=defense;
        this.offense=offense;

    }
    public String getRegex() {
        return type;
    }



    public int getSpeed() {
        return speed;
    }

    public int getDefense() {
        return defense;
    }

    public int getOffense() {
        return offense;
    }

    public int getPrice() {
        return price;
    }

    public String getType() {
        return type;
    }
    private static final ArrayList<UnarmedEnum> unarmedArr = new ArrayList<>(EnumSet.allOf(UnarmedEnum.class));
    public static Matcher getMatcher(String input, UnarmedEnum castleType) {
        String regex = castleType.getRegex();
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(input);
    }

    public static UnarmedEnum getUnarmedType(String input) {
        for (UnarmedEnum castleType : unarmedArr) {
            if(getMatcher(input, castleType).find()) return castleType;
        }
        return null;
    }
}
