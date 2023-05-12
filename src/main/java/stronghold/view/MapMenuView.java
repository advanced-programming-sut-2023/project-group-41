package stronghold.view;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;

import static stronghold.controller.MapMenuController.*;
import static stronghold.controller.MenuController.getJSONRegexMatcher;

public class MapMenuView {
    private static String pathToRegexJSON = "src/main/java/stronghold/database/utils/regex/MapMenuRegex.json";

    public static void run(Scanner scanner){
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
            Matcher showMap;
            Matcher mapShift;
            Matcher showMapCellDetails;


            if(command.matches("back")){
                MapMenuView.output("back");
                break;
            } else if ((showMap =getJSONRegexMatcher(command, "showMap", menuRegexPatternsObject)).matches()) {
                showMap(Integer.parseInt(showMap.group("x")),Integer.parseInt(showMap.group("y")));
            } else if ((mapShift=getJSONRegexMatcher(command, "mapShift", menuRegexPatternsObject)).matches()) {
                String  x="!23123",y="rewrwe rw";
                if(mapShift.group("x")!=null){
                    x=mapShift.group("x");
                }
                if(mapShift.group("y")!=null){
                    y=mapShift.group("y");
                }
                int num=1;
                if(mapShift.group("num")!=null){
                    num=Integer.parseInt(mapShift.group("num"));
                }
                mapShift(x,y,num);
            } else if (( showMapCellDetails = getJSONRegexMatcher(command, "showMapCellDetails", menuRegexPatternsObject)).matches()) {
                showMapCellDetails(Integer.parseInt(showMapCellDetails.group("x")),Integer.parseInt(showMapCellDetails.group("y")));
            }   else {
                MapMenuView.output("invalid");
            }
        }

    }
    public static void output(String code, Object... params){
        String pathToJSON = "src/main/java/stronghold/database/textcontent/MapMenuResponses.json";
        MenuView.output(pathToJSON, code, params);
    }

    public static String input(Scanner scanner){
        if(!scanner.hasNextLine())
            return "";
        String readLine = scanner.nextLine();
        return readLine;
    }

}
