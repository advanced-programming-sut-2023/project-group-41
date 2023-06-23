package stronghold.model.components.game.enums;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.RandomAccess;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum Resource {
    WHEAT("wheat"),
    FLOUR("flour"),
    HOPS("hops"),
    ALE("ale"),
    STONE_IN_QUARRY("stoneInQuarry"),
    STONE("stone"),
    IRON("iron"),
    WOOD("wood"),
    PITCH("pitch"),

    APPLE("apple"),
    CHEESE("cheese"),
    BREAD("bread"),
    MEAT("meat"),

    ARMOR("armor"),
    LEATHER_VEST("leatherVest"),
    SWORD("sword"),
    SPEAR("spear"),
    CROSS_BOW("cross bow"),

    HORSE("horse"),
    COW("cow"),
    WORKER("worker"),
    ENGINEER("engineer");

    private String regex;

    public static final ArrayList<Resource> resources = new ArrayList<>(EnumSet.allOf(Resource.class));

    Resource(String regex) {
        this.regex = regex;
    }

    public String getRegex() {
        return regex;
    }

    public static Matcher getMatcher(String input, Resource resource){
        return Pattern.compile(resource.getRegex()).matcher(input);
    }

    public static Resource getResource(String input){
        for (Resource resource : resources) {
            if (getMatcher(input, resource).find()) return resource;
        }
        return null;
    }
    public static ArrayList<Resource> getResources(){
        return resources;
    }
}
