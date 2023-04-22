<<<<<<< HEAD
package stronghold.controller;

import stronghold.model.components.game.building.Converter;
import stronghold.model.components.game.building.Storage;
import stronghold.model.components.game.soldeirtype.Building;
import stronghold.model.components.game.Map;
import stronghold.model.components.general.User;

import java.util.ArrayList;
import java.util.Scanner;

public class MainMenuController {
    private ArrayList<User> userDataBase;
    private User currentUser;
    private static int roundNum;
    private static Map map;

    public MainMenuController(User currentUser) {
        this.currentUser = currentUser;
    }
    public void run(Scanner scanner) {
        // while ...
    }
    public void nextPlayer(){

    }
    public void endGame(){

    }
    public static int getRoundNum() {
        return roundNum;
    }




    public void showPopularityFactors(){

    }
    public void showPopularity(){

    }
    public void showFoodList(){

    }
    public void foodRate(int rate){

    }
    public void foodRateShow(){

    }
    public void taxRate(int rate){

    }
    public void taxRateShow(){

    }
    public void fearRate(int rate){

    }


    public void dropBuilding(int X, int Y, String type){

        Building building = null;// i will complete this after update building to decorate sample

    }
    public void selectBuilding(int X, int Y){

    }
    public void createUnit(int X, int Y){

    }
    public void repair(){

    }


    public void selectUnit(int X, int Y){

    }
    public void moveUnit(int X, int Y){

    }
    public void moveUnitTo(int X, int Y){

    }
    public void patrolUnit(int X1, int Y1, int X2, int Y2){

    }
    public void set(int X, int Y, String situation){

    }
    public void attack(String enemy) {

    }
    public void attack(int X, int Y) {

    }
    public void pourOil(String direction){

    }
    public void digTunnel(int X, int Y){

    }
    public void build(String equipmentName){

    }
    public void disbandUnit(){

    }


    public void setTexture(int X, int Y){

    }
    public void setTexture(int X1, int Y1, int X2, int Y2){

    }
    public void clear(int X, int Y){

    }
    public void dropRock(int X, int Y){

    }
    public void dropTree(int X, int Y){

    }
    public void dropUnit(int X, int Y, String type, int count){

    }


    public void enterTradeMenu(){

    }


    public void enterShopMenu(){

    }
}
=======
package stronghold.controller;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import javafx.css.Match;
import stronghold.model.components.game.Government;
import stronghold.model.components.game.building.Converter;
import stronghold.model.components.game.building.Storage;
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


    public static void showMap(int X, int Y){

    }
    public static void mapUpLeft(){

    }
    public static void showMapDetails(int X, int Y) {

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
    public static void createUnit(int X, int Y){

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
    public static void set(int X, int Y, String situation){

    }
    public static void attack(String enemy) {

    }
    public static void attack(int X, int Y) {

    }
    public static void pourOil(String direction){

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
>>>>>>> 2bfb39568ff9b671a99d1ea453cd2f82ca82dd30
