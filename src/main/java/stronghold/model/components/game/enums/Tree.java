package stronghold.model.components.game.enums;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum Tree {
    DESERT_SHRUB("desert shrub"),
    CHERRY_PALM("cherry palm"),
    OLIVE_TREE("olive tree"),
    COCONUT_PALM("coconut palm"),
    DATE("date");

    private String regex;

    public static final ArrayList<Tree> treeArr = new ArrayList<>(EnumSet.allOf(Tree.class));

    Tree(String regex) {
        this.regex = regex;
    }

    public String getRegex() {
        return regex;
    }

    public static Matcher getMatcher(String input, Tree tree){
        return Pattern.compile(tree.getRegex()).matcher(input);
    }

    public Tree getTree(String input){
        for (Tree tree : treeArr) {
            if(getMatcher(input, tree).find()) return tree;
        }
        return null;
    }
}
