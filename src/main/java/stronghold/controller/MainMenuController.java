package stronghold.controller;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import javafx.css.Match;
import stronghold.model.components.game.Government;
import stronghold.model.components.game.building.Converter;
import stronghold.model.components.game.building.Storage;
import stronghold.model.components.game.enums.Direction;
import stronghold.model.components.game.enums.State;
import stronghold.model.components.game.soldeirtype.Building;
import stronghold.model.components.game.soldeirtype.Map;
import stronghold.model.components.general.User;
import stronghold.view.MainMenuView;
import stronghold.view.SignUpLoginView;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
public class MainMenuController extends MenuController{
    private ArrayList<User> userDataBase;
    private User currentUser;
    private static int roundNum;
    private static Map map;
    private static String pathToRegexJSON = "src/main/java/stronghold/database/utils/regex/MainMenuRegex.json";

    public MainMenuController(User currentUser) {
        this.currentUser = currentUser;
    }
    public static void run(Scanner scanner) {
        JsonElement regexElement = null;
        try {
            regexElement = JsonParser.parseReader(new FileReader(pathToRegexJSON));
        } catch (
                FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        JsonObject mainMenuRegexObj = regexElement.getAsJsonObject();


        while (true){
            String command = MainMenuView.input(scanner).trim();
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
            Matcher airAttakMatcher;
            Matcher pourOilMatcher;
            Matcher digTunnelMatcher;
            Matcher buildMatcher;

            if(command.matches("back")){
                break;
            } else if (getJSONRegexMatcher(command, "showPopularityFactors", mainMenuRegexObj).matches()) {
                showPopularityFactors();
            } else if (getJSONRegexMatcher(command, "showPopularity", mainMenuRegexObj).matches()) {
                showPopularity();
            } else if (getJSONRegexMatcher(command, "showFoodList", mainMenuRegexObj).matches()) {
                showFoodList();
            } else if ((foodRateMatcher = getJSONRegexMatcher(command, "foodRate", mainMenuRegexObj)).matches()) {
                foodRate(Integer.parseInt(foodRateMatcher.group(1)));
            } else if (getJSONRegexMatcher(command, "foodRateShow", mainMenuRegexObj).matches()) {
                foodRateShow();
            } else if ((taxRateMatcher = getJSONRegexMatcher(command, "taxRate", mainMenuRegexObj)).matches()) {
                taxRate(Integer.parseInt(taxRateMatcher.group(1)));
            } else if (getJSONRegexMatcher(command, "taxRateShow", mainMenuRegexObj).matches()) {
                taxRateShow();
            } else if ((fearRateMatcher = getJSONRegexMatcher(command, "fearRate", mainMenuRegexObj)).matches()) {
                fearRate(Integer.parseInt(fearRateMatcher.group(1)));
            } else if ((dropBuildingMatcher = getJSONRegexMatcher(command, "dropBuilding", mainMenuRegexObj)).matches()) {
                dropBuilding(Integer.parseInt(dropBuildingMatcher.group(1)), Integer.parseInt(dropBuildingMatcher.group(2)), dropBuildingMatcher.group(3));
            } else if ((selectBuildingMatcher = getJSONRegexMatcher(command, "selectBuilding", mainMenuRegexObj)).matches()) {
                selectBuilding(Integer.parseInt(selectBuildingMatcher.group(1)), Integer.parseInt(selectBuildingMatcher.group(2)));
            } else if ((createUnitMatcher = getJSONRegexMatcher(command, "createUnit", mainMenuRegexObj)).matches()) {
                createUnit(createUnitMatcher.group(1), Integer.parseInt(createUnitMatcher.group(2)));
            } else if (getJSONRegexMatcher(command, "repair", mainMenuRegexObj).matches()) {
                repair();
            } else if ((selectUnitMatcher = getJSONRegexMatcher(command, "selectUnit", mainMenuRegexObj)).matches() ) {
                selectUnit(Integer.parseInt(selectUnitMatcher.group(1)), Integer.parseInt(selectUnitMatcher.group(2)));
            } else if ((moveUnitToMatcher = getJSONRegexMatcher(command, "moveUnitTo", mainMenuRegexObj)).matches()) {
                moveUnitTo(Integer.parseInt(moveUnitToMatcher.group(1)), Integer.parseInt(moveUnitToMatcher.group(2)));
            } else if ((patrolUnitMatcher = getJSONRegexMatcher(command, "patrolUnit", mainMenuRegexObj)).matches()) {
                int X1 = Integer.parseInt(patrolUnitMatcher.group("X1"));
                int Y1 = Integer.parseInt(patrolUnitMatcher.group("Y1"));
                int X2 = Integer.parseInt(patrolUnitMatcher.group("X2"));
                int Y2 = Integer.parseInt(patrolUnitMatcher.group("Y2"));
                patrolUnit(X1, Y1, X2, Y2);
            } else if ((setMatcher = getJSONRegexMatcher(command, "set", mainMenuRegexObj)).matches()) {
                State stateOfUnit = State.getState(setMatcher.group("state"));
                set(Integer.parseInt(setMatcher.group(1)), Integer.parseInt(setMatcher.group(2)), stateOfUnit);
            } else if ((attackEnemyMatcher = getJSONRegexMatcher(command, "attackEnemy", mainMenuRegexObj)).matches()) {
                //command: attack -e [enemy’s x] [enemy’s y]
                attackEnemy(Integer.parseInt(attackEnemyMatcher.group(1)), Integer.parseInt(attackEnemyMatcher.group(2)));
            } else if ((airAttakMatcher = getJSONRegexMatcher(command, "airAttack", mainMenuRegexObj)).matches()) {
                //command: attack -x [x] -y [y]
                airAttack(Integer.parseInt(airAttakMatcher.group(1)), Integer.parseInt(airAttakMatcher.group(2)));
            } else if ((pourOilMatcher = getJSONRegexMatcher(command, "pourOil", mainMenuRegexObj)).matches()) {
                Direction direction = Direction.getDirection(pourOilMatcher.group("direction"));
                pourOil(direction);
            } else if ((digTunnelMatcher = getJSONRegexMatcher(command, "digTunnel", mainMenuRegexObj)).matches()) {
                digTunnel(Integer.parseInt(digTunnelMatcher.group(1)), Integer.parseInt(digTunnelMatcher.group(2)));
            } else if ((buildMatcher = getJSONRegexMatcher(command, "build", mainMenuRegexObj)).matches()) {
                build(buildMatcher.group(1));
            } else if (getJSONRegexMatcher(command, "disbandUnit", mainMenuRegexObj).matches()) {
                disbandUnit();
            } else {
                MainMenuView.output("invalid");
            }
        }
    }
    public static void nextPlayer(){

    }
    public static void endGame(){

    }
    public static int getRoundNum() {
        return roundNum;
    }


    
    public static void showPopularityFactors(){

    }
    public static void showPopularity(){

    }
    public static void showFoodList(){

    }
    public static void foodRate(int rate){

    }
    public static void foodRateShow(){

    }
    public static void taxRate(int rate){

    }
    public static void taxRateShow(){

    }
    public static void fearRate(int rate){

    }

    

    public static void dropBuilding(int X, int Y, String type){

        Building building = null;// i will complete this after update building to decorate sample

    }
    public static void selectBuilding(int X, int Y){

    }
    public static void createUnit(String type, int count){

    }
    public static void repair(){

    }


    
    public static void selectUnit(int X, int Y){

    }
    public static void moveUnit(int X, int Y){

    }
    public static void moveUnitTo(int X, int Y){

    }
    public static void patrolUnit(int X1, int Y1, int X2, int Y2){

    }
    public static void set(int X, int Y, State state){
        // State is an enum class and have three obj: standing|defensive|offensive
    }
    public static void attackEnemy(int enemyX, int enemyY) {
        //command: attack -e [enemy’s x] [enemy’s y]
    }
    public static void airAttack(int X, int Y) {
        //command: attack -x [x] -y [y]
    }
    public static void pourOil(Direction direction){

    }
    public static void digTunnel(int X, int Y){

    }
    public static void build(String equipmentName){

    }
    public static void disbandUnit(){

    }


    
    public static void setTexture(int X, int Y){

    }
    public static void setTexture(int X1, int Y1, int X2, int Y2){

    }
    public static void clear(int X, int Y){

    }
    public static void dropRock(int X, int Y){

    }
    public static void dropTree(int X, int Y){

    }
    public static void dropUnit(int X, int Y, String type, int count){

    }


    public static void enterTradeMenu(){

    }


    public static void enterShopMenu(){

    }
}
