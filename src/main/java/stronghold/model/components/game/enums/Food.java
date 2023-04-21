package stronghold.model.components.game.enums;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum Food {
    apple("apple"),
    cheese("cheese"),
    bread("bread"),
    meat("meat");

    private String regex;
    private static ArrayList<Food> foodArr = new ArrayList<>(EnumSet.allOf(Food.class));
    Food(String r) {
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

    public static Food getFood(String input) {
        for (Food food : foodArr) {
            if(getMatcher(input, food).find()) return food;
        }
        return null;
    }
}
