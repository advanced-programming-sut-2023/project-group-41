package stronghold.model.components.game;

import stronghold.model.components.game.enums.Food;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum Texture {
    LAND("land"),
    GRAVEL_LAND("gravel land"),
    ROCK("rock"),
    STONE("stone"),
    IRON("iron"),
    GRASS("grass"),
    MEADOW("meadow"),
    DENSE_GRASS_LAND("dense grass land"),
    OIL("oil"),
    PLAIN("plain"),
    SHALLOW_LAKE("shallow lake"),
    RIVER("river"),
    SMALL_POND("small pond"),
    BIG_POND("big pond"),
    BEACH("beach"),
    SEA("sea");

    private String regex;
    public static final ArrayList<Texture> textureArr = new ArrayList<>(EnumSet.allOf(Texture.class));

    Texture(String regex) {
        this.regex = regex;
    }

    public String getRegex() {
        return regex;
    }

    public static Matcher getMatcher(String input, Texture texture){
        return Pattern.compile(texture.getRegex()).matcher(input);
    }

    public static Texture getTexture(String input) {
        for (Texture texture : textureArr) {
            if(getMatcher(input, texture).find()) return texture;
        }
        return null;
    }
}
