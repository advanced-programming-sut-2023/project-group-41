package stronghold.view;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import stronghold.controller.MenuController;
import stronghold.model.components.game.Government;
import stronghold.model.components.game.Map;
import stronghold.model.components.game.Unit;
import stronghold.model.components.game.building.Building;
import stronghold.model.components.game.enums.Direction;
import stronghold.model.components.game.enums.State;
import stronghold.model.components.game.enums.Texture;
import stronghold.model.components.game.enums.Tree;
import stronghold.model.components.general.User;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;

import static stronghold.controller.GameMenuController.*;
import static stronghold.controller.MenuController.getJSONRegexMatcher;

public class GameMenuView extends MenuView {
    public static void output(String code, Object... params){
        String pathToJSON = "src/main/java/stronghold/database/textcontent/GameMenuResponses.json";
        MenuView.output(pathToJSON, code, params);
    }
    public static String input(Scanner scanner){
        if(!scanner.hasNextLine())
            return "";
        return scanner.nextLine();
    }

}
