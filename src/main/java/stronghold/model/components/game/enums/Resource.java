package stronghold.model.components.game.enums;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum Resource {
    GOLD("gold"),

    WHEAT("wheat"),
    FLOUR("flour"),
    HOPS("hops"),
    ALE("ale"),
    STONE("stone"),
    IRON("iron"),
    WOOD("wood"),
    PITCH("pitch"),

    APPLE("apple"),
    CHEESE("cheese"),
    BREAD("bread"),
    MEAT("meat"),

    ARMOR("armor"),
    SWORD("sword"),
    SPEAR("spear"),
    HORSE("horse"),
    CROSS_BOW("cross bow"),

    COW("cow");

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
}
