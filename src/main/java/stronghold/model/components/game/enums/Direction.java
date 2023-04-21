package stronghold.model.components.game.enums;

import java.security.DigestException;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum Direction {
    NORTH("n"),
    SOUTH("s"),
    EAST("e"),
    WEST("w"),
    RANDOM("r");

    private String regex;
    private static ArrayList<Direction> directions = new ArrayList<>(EnumSet.allOf(Direction.class));
    Direction(String regex) {
        this.regex = regex;
    }

    public String getRegex() {
        return regex;
    }

    public static Matcher getMatcher(String input, Direction direction) {
        String regex = direction.getRegex();
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(input);
    }

    public static Direction getDirection(String input){
        for (Direction direction : directions) {
            if (getMatcher(input, direction).find()) {
                return direction;
            }
        }
        return null;
    }
}
