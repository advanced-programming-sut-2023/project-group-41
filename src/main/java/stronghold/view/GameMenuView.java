package stronghold.view;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import stronghold.controller.GameMenuController;
import stronghold.model.components.game.Government;
import stronghold.model.components.game.Map;
import stronghold.model.components.game.building.Building;
import stronghold.model.components.game.enums.Direction;
import stronghold.model.components.game.enums.State;
import stronghold.model.components.game.enums.Texture;
import stronghold.model.components.game.enums.Tree;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;
import java.util.regex.Matcher;

import static stronghold.controller.GameMenuController.*;
import static stronghold.controller.MenuController.getJSONRegexMatcher;

public class GameMenuView extends MenuView {

    private static String pathToRegexJSON = "src/main/java/stronghold/database/utils/regex/GameMenuRegex.json";

    public static void output(String code, Object... params){
        String pathToJSON = "src/main/java/stronghold/database/textcontent/GameMenuResponses.json";
        MenuView.output(pathToJSON, code, params);
    }
    public static String input(Scanner scanner){
        if(!scanner.hasNextLine())
            return "";
        return scanner.nextLine();
    }

    public static void run(Scanner scanner, int round, int playerNum1, int mapSize) {
        Map.getInstanceMap().setSize(mapSize);
        startGame(playerNum1);

        setCurrentRound(1);
        setRoundNum(round);
        setPlayerNum(playerNum1);


        JsonElement regexElement = null;
        try {
            regexElement = JsonParser.parseReader(new FileReader(pathToRegexJSON));
        } catch (
                FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        JsonObject gameMenuRegexObj = regexElement.getAsJsonObject();
        GameMenuView.output("wellcome");
        while (true) {

            String command = GameMenuView.input(scanner).trim();
            Matcher foodRateMatcher;
            Matcher taxRateMatcher;
            Matcher fearRateMatcher;

            Matcher dropBuildingMatcher;
            Matcher selectBuildingMatcher;
            Matcher createUnitMatcher;

            Matcher selectUnitMatcher;
            Matcher moveUnitToMatcher;
            Matcher patrolUnitMatcher;
            Matcher setMatcher;
            Matcher attackEnemyMatcher;
            Matcher airAttackMatcher;
            Matcher pourOilMatcher;
            Matcher digTunnelMatcher;
            Matcher buildMatcher;
            Matcher captureGateMatcher;
            
            Matcher setTextureMatcher;
            Matcher setRectangleTextureMatcher;
            Matcher clearMatcher;
            Matcher dropRockMatcher;
            Matcher dropTreeMatcher;
            Matcher dropUnitMatcher;
            Matcher enterMapMenu;
            Matcher enterShopMenu;
            Matcher enterTradeMenu;
            Matcher selectTool;
            Matcher burnOil;
            Matcher digDitch;
            Matcher fillDitch;
            Matcher toolAction;

            Matcher moveToStairMatcher;
            Matcher moveToSiegeTentMatcher;
            if (command.matches("user\\s+logout")) {
                GameMenuView.output("back");
                break;
            } else if (getJSONRegexMatcher(command, "showPopularityFactors", gameMenuRegexObj).matches()) {
                showPopularityFactors();
            } else if (getJSONRegexMatcher(command, "showPopularity", gameMenuRegexObj).matches()) {
                showPopularity();
            } else if (getJSONRegexMatcher(command, "showFoodList", gameMenuRegexObj).matches()) {
                showFoodList();
            } else if ((foodRateMatcher = getJSONRegexMatcher(command, "foodRate", gameMenuRegexObj)).matches()) {
                foodRate(Integer.parseInt(foodRateMatcher.group(1)));
            } else if (getJSONRegexMatcher(command, "foodRateShow", gameMenuRegexObj).matches()) {
                foodRateShow();
            } else if ((taxRateMatcher = getJSONRegexMatcher(command, "taxRate", gameMenuRegexObj)).matches()) {
                taxRate(Integer.parseInt(taxRateMatcher.group(1)));
            } else if (getJSONRegexMatcher(command, "taxRateShow", gameMenuRegexObj).matches()) {
                taxRateShow();
            } else if ((fearRateMatcher = getJSONRegexMatcher(command, "fearRate", gameMenuRegexObj)).matches()) {
                fearRate(Integer.parseInt(fearRateMatcher.group(1)));
            } else if ((dropBuildingMatcher = getJSONRegexMatcher(command, "dropBuilding", gameMenuRegexObj)).matches()) {
                // commands: dropbuilding -x [x] -y [y] -type [type] || dropbuilding -x [x] -y [y] -t [type]
                int X = Integer.parseInt(dropBuildingMatcher.group("X"));
                int Y = Integer.parseInt(dropBuildingMatcher.group("Y"));
                Building type = Building.getBuilding(GameMenuController.getCurrentPlayer(), dropBuildingMatcher.group("type"));
                dropBuilding(X, Y, type);
            } else if ((selectBuildingMatcher = getJSONRegexMatcher(command, "selectBuilding", gameMenuRegexObj)).matches()) {
                int X = Integer.parseInt(selectBuildingMatcher.group("X"));
                int Y = Integer.parseInt(selectBuildingMatcher.group("Y"));
                selectBuilding(X, Y);
            } else if ((createUnitMatcher = getJSONRegexMatcher(command, "createUnit", gameMenuRegexObj)).matches()) {
                String type = createUnitMatcher.group("type");
                int count = Integer.parseInt(createUnitMatcher.group("count"));
                createUnit(type, count);
            } else if (getJSONRegexMatcher(command, "repair", gameMenuRegexObj).matches()) {
                repair();
            } else if ((selectUnitMatcher = getJSONRegexMatcher(command, "selectUnit", gameMenuRegexObj)).matches()) {
                int X = Integer.parseInt(selectUnitMatcher.group("X"));
                int Y = Integer.parseInt(selectUnitMatcher.group("Y"));
                selectUnit(X, Y);
            } else if ((moveUnitToMatcher = getJSONRegexMatcher(command, "moveUnitTo", gameMenuRegexObj)).matches()) {
                int X = Integer.parseInt(moveUnitToMatcher.group("X"));
                int Y = Integer.parseInt(moveUnitToMatcher.group("Y"));
                ///moveUnitTo(X, Y);
            } else if ((patrolUnitMatcher = getJSONRegexMatcher(command, "patrolUnit", gameMenuRegexObj)).matches()) {
                int X1 = Integer.parseInt(patrolUnitMatcher.group("X1"));
                int Y1 = Integer.parseInt(patrolUnitMatcher.group("Y1"));
                int X2 = Integer.parseInt(patrolUnitMatcher.group("X2"));
                int Y2 = Integer.parseInt(patrolUnitMatcher.group("Y2"));
                patrolUnit(X1, Y1, X2, Y2);
            } else if ((setMatcher = getJSONRegexMatcher(command, "set", gameMenuRegexObj)).matches()) {
                int X = Integer.parseInt(setMatcher.group("X"));
                int Y = Integer.parseInt(setMatcher.group("Y"));
                State stateOfUnit = State.getState(setMatcher.group("state"));
                setStateOfUnit(X, Y, stateOfUnit);
            } else if ((attackEnemyMatcher = getJSONRegexMatcher(command, "attackEnemy", gameMenuRegexObj)).matches()) {
                //command: attack -e [enemy’s x] [enemy’s y]
                //attackEnemy(Integer.parseInt(attackEnemyMatcher.group(1)), Integer.parseInt(attackEnemyMatcher.group(2)));
            } else if ((airAttackMatcher = getJSONRegexMatcher(command, "airAttack", gameMenuRegexObj)).matches()) {
                //command: attack -x [x] -y [y]
                int X = Integer.parseInt(airAttackMatcher.group("X"));
                int Y = Integer.parseInt(airAttackMatcher.group("Y"));
              //  airAttack(X, Y);
            } else if ((pourOilMatcher = getJSONRegexMatcher(command, "pourOil", gameMenuRegexObj)).matches()) {
                Direction direction = Direction.getDirection(pourOilMatcher.group("direction"));
                pourOil(direction);
            } else if ((digTunnelMatcher = getJSONRegexMatcher(command, "digTunnel", gameMenuRegexObj)).matches()) {
                int X = Integer.parseInt(digTunnelMatcher.group("X"));
                int Y = Integer.parseInt(digTunnelMatcher.group("Y"));
                digTunnel(X, Y);
            } else if ((buildMatcher = getJSONRegexMatcher(command, "build", gameMenuRegexObj)).matches()) {
                build(buildMatcher.group(1));
            } else if (getJSONRegexMatcher(command, "disbandUnit", gameMenuRegexObj).matches()) {
                disbandUnit();
            } else if ((setTextureMatcher = getJSONRegexMatcher(command, "setTexture", gameMenuRegexObj)).matches()) {
                // command: settexture -x [x1] -y [y1] -t [type]
                int X = Integer.parseInt(setTextureMatcher.group("X"));
                int Y = Integer.parseInt(setTextureMatcher.group("Y"));
                Texture type = Texture.getTexture(setTextureMatcher.group("type"));
                try {
                    setTexture(X, Y, type);
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                }
            } else if ((setRectangleTextureMatcher = getJSONRegexMatcher(command, "setRectangleTexture", gameMenuRegexObj)).matches()) {
                // command: settexture -x1 [x1] -y1 [y1] -x2 [x2] -y2 [y2] -t [type]
                int X1 = Integer.parseInt(setRectangleTextureMatcher.group("X1"));
                int Y1 = Integer.parseInt(setRectangleTextureMatcher.group("Y1"));
                int X2 = Integer.parseInt(setRectangleTextureMatcher.group("X2"));
                int Y2 = Integer.parseInt(setRectangleTextureMatcher.group("Y2"));
                Texture type = Texture.getTexture(setRectangleTextureMatcher.group("type"));
                setTexture(X1, Y1, X2, Y2, type);
            } else if ((clearMatcher = getJSONRegexMatcher(command, "clear", gameMenuRegexObj)).matches()) {
                int X = Integer.parseInt(clearMatcher.group("X"));
                int Y = Integer.parseInt(clearMatcher.group("Y"));
                clear(X, Y);
            } else if ((dropRockMatcher = getJSONRegexMatcher(command, "dropRock", gameMenuRegexObj)).matches()) {
                int X = Integer.parseInt(dropRockMatcher.group("X"));
                int Y = Integer.parseInt(dropRockMatcher.group("Y"));
                Direction direction = Direction.getDirection(dropRockMatcher.group("direction"));
                dropRock(X, Y, direction);
            } else if ((dropTreeMatcher = getJSONRegexMatcher(command, "dropTree", gameMenuRegexObj)).matches()) {
                int X = Integer.parseInt(dropTreeMatcher.group("X"));
                int Y = Integer.parseInt(dropTreeMatcher.group("Y"));
                Tree type = Tree.getTree(dropTreeMatcher.group("type"));
              //  dropTree(X, Y, type);
            } else if ((dropUnitMatcher = getJSONRegexMatcher(command, "dropUnit", gameMenuRegexObj)).matches()) {
                int X = Integer.parseInt(dropUnitMatcher.group("X"));
                int Y = Integer.parseInt(dropUnitMatcher.group("Y"));
                String type = dropUnitMatcher.group("type");
                int count = Integer.parseInt(dropUnitMatcher.group("count"));
                dropUnit(X, Y, type, count);
            } else if ((captureGateMatcher = getJSONRegexMatcher(command, "catcherGate", gameMenuRegexObj)).matches()) {
                captureGate();
            } else if ((moveToStairMatcher = getJSONRegexMatcher(command, "moveToStair", gameMenuRegexObj)).matches()) {
                moveToStair();
            } else if ((moveToSiegeTentMatcher = getJSONRegexMatcher(command, "moveToSiegeTent", gameMenuRegexObj)).matches()) {
                int X = Integer.parseInt(moveToStairMatcher.group("X"));
                int Y = Integer.parseInt(moveToSiegeTentMatcher.group("Y"));
                moveToSiegeTent(X, Y);
            }  else if (getJSONRegexMatcher(command, "enterMapMenu", gameMenuRegexObj).matches()) {
                enterMapMenu();

            } else if (getJSONRegexMatcher(command, "enterShopMenu", gameMenuRegexObj).matches()) {
                enterShopMenu();

            } else if (getJSONRegexMatcher(command, "enterTradeMenu", gameMenuRegexObj).matches()) {
                enterTradeMenu();

            } else if (getJSONRegexMatcher(command, "nextPlayer", gameMenuRegexObj).matches()) {
                nextPlayer();

            }else if((digDitch= getJSONRegexMatcher(command, "digDitch", gameMenuRegexObj)).matches()){
                int X = Integer.parseInt(digDitch.group("X"));
                int Y = Integer.parseInt(digDitch.group("Y"));
                digDitch(X,Y);
            }else if((fillDitch= getJSONRegexMatcher(command, "fillDitch", gameMenuRegexObj)).matches()){
                int X = Integer.parseInt(fillDitch.group("X"));
                int Y = Integer.parseInt(fillDitch.group("Y"));
                fillDitch(X,Y);
            }else if((burnOil= getJSONRegexMatcher(command, "burnOil", gameMenuRegexObj)).matches()){
                int X = Integer.parseInt(burnOil.group("X"));
                int Y = Integer.parseInt(burnOil.group("Y"));
                burnOil(X,Y);
            }else if((selectTool= getJSONRegexMatcher(command, "selectTool", gameMenuRegexObj)).matches()){
                int X = Integer.parseInt(selectTool.group("X"));
                int Y = Integer.parseInt(selectTool.group("Y"));
                selectTool(X,Y);
            }else if((toolAction= getJSONRegexMatcher(command, "actionTool", gameMenuRegexObj)).matches()){
                int X = Integer.parseInt(selectTool.group("X"));
                int Y = Integer.parseInt(selectTool.group("Y"));
                actionTool(X,Y);
            }else {
                GameMenuView.output("invalid");
            }
        }
    }


//    public static void main(String[] args) {
//        Scanner s=new Scanner(System.in);
//        run(s,1,2,200);
//    }
}
