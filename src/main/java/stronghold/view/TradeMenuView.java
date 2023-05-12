package stronghold.view;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import stronghold.controller.GameMenuController;
import stronghold.model.components.game.Government;
import stronghold.model.components.game.enums.Resource;
import stronghold.model.components.game.trade.TradeDataBase;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;
import java.util.regex.Matcher;

import static stronghold.controller.MenuController.getJSONRegexMatcher;
import static stronghold.controller.TradeMenuController.*;

public class TradeMenuView extends MenuView {
    private static String pathToRegexJSON = "src/main/java/stronghold/database/utils/regex/TradeMenuRegex.json";
    public static void run(Scanner scanner){
        Government currentGovernment = GameMenuController.getCurrentPlayer();
        setCurrentGovernment(GameMenuController.getCurrentPlayer());
        showNotification();
        JsonElement regexElement = null;
        try {
            regexElement = JsonParser.parseReader(new FileReader(pathToRegexJSON));
        } catch (
                FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        JsonObject menuRegexPatternsObject = regexElement.getAsJsonObject();
        while (true){
            String command = TradeMenuView.input(scanner).trim();
            Matcher sendTrade;
            Matcher acceptTradeMatcher;


            if(command.matches("back")){
                TradeMenuView.output("back");

                break;
            } else if ((sendTrade = getJSONRegexMatcher(command, "sendTrade", menuRegexPatternsObject)).matches()) {
                sendTrade(currentGovernment, Resource.getResource(sendTrade.group("resource")), Integer.parseInt(sendTrade.group("price")),sendTrade.group("message"),Integer.parseInt(sendTrade.group("amount")));

            } else if ((acceptTradeMatcher =getJSONRegexMatcher(command, "acceptTrade", menuRegexPatternsObject)).matches()) {
                acceptTrade(TradeDataBase.getTradeById(Integer.parseInt(acceptTradeMatcher.group("id"))));
            } else if (getJSONRegexMatcher(command, "showHistory", menuRegexPatternsObject).matches()) {
                showHistory();
            } else if (( getJSONRegexMatcher(command, "tradeList", menuRegexPatternsObject)).matches()) {
                showTradeList();
            }   else {
                TradeMenuView.output("invalid");
            }
        }

    }
    public static void output(String code, Object... params){
        String pathToJSON = "src/main/java/stronghold/database/textcontent/TradeMenuResponses.json";
        MenuView.output(pathToJSON, code, params);
    }

    public static String input(Scanner scanner){
        if(!scanner.hasNextLine())
            return "";
        String readLine = scanner.nextLine();
        return readLine;
    }

}
