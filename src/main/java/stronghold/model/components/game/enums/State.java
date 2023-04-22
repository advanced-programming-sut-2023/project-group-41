package stronghold.model.components.game.enums;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum State {
    STANDING("standing"),
    DEFENSIVE("defensive"),
    OFFENSIVE("offensive");



    private String regex;
    private static ArrayList<State> stateArr = new ArrayList<>(EnumSet.allOf(State.class));
    State(String regex) {
        this.regex = regex;
    }

    public String getRegex() {
        return regex;
    }

    public static Matcher getMatcher(String input, State state) {
        String regex = state.getRegex();
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(input);
    }

    public static State getState(String input){
        for (State state : stateArr) {
            if (getMatcher(input, state).find()) {
                return state;
            }
        }
        return null;
    }
}
