package stronghold.model.components.game.enums;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum Texture {
    LAND("land", "GREEN"),
    GRAVEL_LAND("gravel land", "GREEN"),
    ROCK("rock", "GREEN"),
    STONE("stone", "GREEN"),
    IRON("iron", "GREEN"),
    GRASS("grass", "GREEN"),
    MEADOW("meadow", "GREEN"),
    DENSE_GRASS_LAND("dense grass land", "GREEN"),
    OIL("oil","BLUE"),
    PLAIN("plain","BLUE"),
    SHALLOW_LAKE("shallow lake","BLUE"),
    RIVER("river", "BLUE"),
    SMALL_POND("small pond", "BLUE"),
    BIG_POND("big pond", "BLUE"),
    BEACH("beach", "BLUE"),
    SEA("sea", "BLUE");

    private String regex;
    private String color;
    public static final ArrayList<Texture> textureArr = new ArrayList<>(EnumSet.allOf(Texture.class));

    Texture(String regex, String color) {
        this.regex = regex;
        this.color = color;
    }

    public String getColor() {
        return color;
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
