package stronghold.view;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import stronghold.controller.GameMenuController;
import stronghold.model.components.game.Government;
import stronghold.model.components.game.enums.Resource;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;
import java.util.regex.Matcher;

import static stronghold.controller.ShopMenuController.*;

public class ShopMenuView {

    private static String pathToRegexJSON = "src/main/java/stronghold/database/utils/regex/ShopMenuRegex.json";
    public static void run(Scanner scanner){
        Government currentGovernment = GameMenuController.getCurrentPlayer();

        setCurrentGovernment(GameMenuController.getCurrentPlayer());
        for(Resource resource:currentGovernment.getResourcesMap().keySet()){
            prices.put(resource,10);
        }
        JsonElement regexElement = null;
        try {
            regexElement = JsonParser.parseReader(new FileReader(pathToRegexJSON));
        } catch (
                FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        JsonObject menuRegexPatternsObject = regexElement.getAsJsonObject();
        while (true){
            String command = ShopMenuView.input(scanner).trim();
            Matcher buy;
            Matcher sell;


            if(command.matches("back")){
                ShopMenuView.output("back");
                return;
            } else if ((buy =getJSONRegexMatcher(command, "buy", menuRegexPatternsObject)).matches()) {
                buy(Resource.getResource(buy.group("id")),Integer.parseInt(buy.group("int")));
            } else if ((sell=getJSONRegexMatcher(command, "sell", menuRegexPatternsObject)).matches()) {
                sell(Resource.getResource(sell.group("id")),Integer.parseInt(sell.group("int")));
            } else if (( getJSONRegexMatcher(command, "showPriceList", menuRegexPatternsObject)).matches()) {
                showPriceList();
            }   else {
                ShopMenuView.output("invalid");
            }
        }

    }
    public static void output(String code, Object... params){
        String pathToJSON = "src/main/java/stronghold/database/textcontent/ShopMenuResponses.json";
        MenuView.output(pathToJSON, code, params);
    }

    public static String input(Scanner scanner){
        if(!scanner.hasNextLine())
            return "";
        String readLine = scanner.nextLine();
        return readLine;
    }

}
