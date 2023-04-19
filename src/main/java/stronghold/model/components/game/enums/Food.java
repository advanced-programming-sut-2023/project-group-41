package stronghold.model.components.game.enums;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum Food {
    apple("apple"),
    cheese("cheese"),
    bread("bread"),
    meat("meat");

    private  String regex;
    private Food(String r) {
        this.regex = r;
    }
    public String getRegex() {
        return regex;
    }

    public  static Matcher getMatcher(String input, Food food) {
        String regex = food.getRegex();
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(input);
    }

}
