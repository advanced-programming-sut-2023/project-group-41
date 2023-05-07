package stronghold.controller;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import stronghold.model.components.game.Government;
import stronghold.model.components.game.Map;
import stronghold.model.components.game.MapCell;
import stronghold.model.components.game.Unit;
import stronghold.model.components.game.building.*;
import stronghold.model.components.game.enums.*;
import stronghold.model.components.game.soldeirtype.LongRanged;
import stronghold.model.components.game.soldeirtype.UnarmedEnum;
import stronghold.model.components.general.User;
import stronghold.view.GameMenuView;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;

import static stronghold.model.components.game.enums.Direction.RANDOM;
import static stronghold.model.components.game.enums.Resource.*;

public class GameMenuController extends MenuController{
    private static int roundNum;
    private static Map map;
    private static String pathToRegexJSON = "src/main/java/stronghold/database/utils/regex/GameMenuRegex.json";
    ///////////
    private static Unit currentUnit;
    private static Building currentBuilding;

    private static Government currentGovernment;
    private static ArrayList<Government> governments=new ArrayList<>();



    public static void startGame(int playerNum){
        for(int i=2;i<playerNum;i++){
            Government government=new Government(i);
            governments.add(government);
        }
        //set governmentCenter
    }
    public static Government getGovernmentByColor(int color)
    {
        for(Government government:governments){
            if(government.getColor()==color){
                return government;
            }
        }
        return null;

    }


    public static void nextPlayer(){

    }
    public static void endGame(){

    }
    public static int getRoundNum() {
        return roundNum;
    }
    public static void endOfRound(){

    }


    
    public static void showPopularityFactors(){
        GameMenuView.output("showPopularityFactors");

    }
    public static void showPopularity(){

    }
    public static void showFoodList(){
        GameMenuView.output("foodList", (Object) "APPLE", Integer.toString(currentGovernment.getResourcesNum(APPLE)));
        GameMenuView.output("foodList", (Object) "CHEESE", Integer.toString(currentGovernment.getResourcesNum(CHEESE)));
        GameMenuView.output("foodList", (Object) "BREAD", Integer.toString(currentGovernment.getResourcesNum(BREAD)));
        GameMenuView.output("foodList", (Object) "MEAT", Integer.toString(currentGovernment.getResourcesNum(MEAT)));
    }
    public static void foodRate(int rate){
        currentGovernment.setFoodRate(rate);
        GameMenuView.output("success");

    }
    public static void foodRateShow(){
        GameMenuView.output("rate");
        System.out.println(currentGovernment.getFoodRate());

    }
    public static void taxRate(int rate){
        currentGovernment.setTaxRate(rate);
        GameMenuView.output("success");

    }
    public static void taxRateShow(){
        GameMenuView.output("rate");
        System.out.println(currentGovernment.getTaxRate());

    }
    public static void fearRate(int rate){
        GameMenuView.output("rate");
        System.out.println(currentGovernment.getFearRate());

    }

    public static void dropBuilding(int X, int Y, Building type){
        MapCell mapCell;
        ResourceMaker resourceMaker;
        if (Map.validMapCell(X, Y)){
            mapCell = Map.getMapCell(X, Y);
        } else {
            GameMenuView.output("invalidLocation");
            return;
        }
        if (type == null){
            GameMenuView.output("incorrectBuildingType");
        } else if (type.getClass().getSimpleName().equals("ResourceMaker")
            && !(resourceMaker = (ResourceMaker) type).checkTexture(mapCell.getTexture())){
                GameMenuView.output("textureProblem");
        } else if (type.getRegex().matches("\\s*oxTether\\s*") && !Map.isBuildingNear(X, Y, ResourceMakerType.QUARRY.getRegex())) {
            GameMenuView.output("oxTetherError");
        } else {
            mapCell.setBuilding(type);
            GameMenuView.output("buildingDrop");
        }
    }
    public static void selectBuilding(int X, int Y){
        Building building = Map.getMapCell(X, Y).getBuilding();
        if (building == null){
            GameMenuView.output("noBuildingAvailable");
        } else {
            currentBuilding = building;
            GameMenuView.output("buildingSelected");
        }
    }
    public static void createUnit(String type, int count){
       // Todo: need completed drop unit command;
    }
    public static void repair(){
        if (!currentBuilding.getClass().getSimpleName().equals("Castle")) {
            GameMenuView.output("incorrectBuildingType");
        } else if (false) {
            // TODO: stopping repair when soldier are near
        } else {
            Castle castle = (Castle)currentBuilding;
            castle.repair();
        }
    }

    public static void selectUnit(int X, int Y){
        if(Map.getMapCell(X, Y).getUnit() == null){
            System.out.println("there is no unit in the mentioned coordinates!");
        } else {
            currentUnit=Map.getMapCell(X, Y).getUnit();
            System.out.println("unit selected successfully!!!");
        }
    }
    public static void moveUnitTo(int X, int Y){
        if(Map.getMapCell(X,Y).getTexture().equals(Texture.RIVER)||Map.getMapCell(X,Y).getTexture().equals(Texture.SEA)||Map.getMapCell(X,Y).getTexture().equals(Texture.SHALLOW_LAKE)||Map.getMapCell(X,Y).getTexture().equals(Texture.SMALL_POND)||Map.getMapCell(X,Y).getTexture().equals(Texture.BIG_POND)){
            GameMenuView.output("waterError");
        }
        else  if(X>(currentUnit.getPeople().getSpeed()*4)+currentUnit.getX()||X<(currentUnit.getPeople().getSpeed()*4)-currentUnit.getX()||Y>(currentUnit.getPeople().getSpeed()*4)+currentUnit.getY()||Y<(currentUnit.getPeople().getSpeed()*4)+currentUnit.getY()){
            GameMenuView.output("speedError");

        }

        else{
            Map.getMapCell(currentUnit.getX(),currentUnit.getY()).setUnit(null);
            Map.getMapCell(X,Y).setUnit(currentUnit);
            GameMenuView.output("success");
        }
    }
    public static void patrolUnit(int X1, int Y1, int X2, int Y2){
        ///????????????????????????????

    }
    public static void setStateOfUnit(int X, int Y, State state){
        // State is an enum class and have three obj: standing|defensive|offensive
        currentUnit.setState(state);
        if(currentUnit.getState().equals(State.DEFENSIVE)){
            //attack() for a radius
        }
        if(currentUnit.getState().equals(State.OFFENSIVE)){
            //attack() for a radius
        }
        GameMenuView.output("success");
    }
    public static void attackEnemy(int enemyX, int enemyY) {
        //command: attack -e [enemy’s x] [enemy’
        //buildings attack
        if(currentUnit.getPeople() instanceof LongRanged){
            GameMenuView.output("notFighter");
        }
       else if(currentUnit.getCount()*currentUnit.getPeople().getOffense()>Map.getMapCell(enemyX,enemyY).getUnit().getPeople().getOffense()*Map.getMapCell(enemyX,enemyY).getUnit().getCount()){
            Map.getMapCell(currentUnit.getX(),currentUnit.getY()).setUnit(null);
            Map.getMapCell(enemyX,enemyY).setUnit(currentUnit);

        }
        else{
            Map.getMapCell(currentUnit.getX(),currentUnit.getY()).setUnit(null);

        }
    }
    public static void airAttack(int X, int Y) {
        //command: attack -x [x] -y [y]
        int xDistance=currentUnit.getX()-X,yDistance=currentUnit.getY()-Y;
        if(!(currentUnit.getPeople() instanceof LongRanged)){
            GameMenuView.output("notLongRanged");
        }else{
            if (xDistance<0)
                xDistance*=-1;
            if(yDistance<0)
                yDistance*=-1;
            if(xDistance<=((LongRanged) currentUnit.getPeople()).getRange()*5&&yDistance<=((LongRanged) currentUnit.getPeople()).getRange()*5){
                   //building
                    Map.getMapCell(currentUnit.getX(),currentUnit.getY()).setUnit(null);
                    //System.out.println("you have eliminated all troops in the coordinates!");

            }else {
               GameMenuView.output("bondError");
            }

        }
    }
    public static void pourOil(Direction direction){
        if(!(currentUnit.getPeople() .equals(UnarmedEnum.enginner))){
            GameMenuView.output("engineerError");

        }else{

        }

    }
    public static void digTunnel(int X, int Y){

    }
    public static void build(String equipmentName){

    }
    public static void disbandUnit(){

    }


    
    public static void setTexture(int X, int Y, Texture type){
        if (!Map.validMapCell(X, Y)) {
            GameMenuView.output("invalidLocation");
        } else if (Map.getMapCell(X, Y).getBuilding() != null){
            GameMenuView.output("buildingPlaced");
        } else {
            Map.getMapCell(X, Y).setTexture(type);
            GameMenuView.output("textureSet");
        }
    }
    public static void setTexture(int X1, int Y1, int X2, int Y2, Texture type){
        if(!Map.validMapCell(X1, Y1) || !Map.validMapCell(X2, Y2) || X2 < X1 || Y2 < Y1){
            GameMenuView.output("cantMakeBlock");
            return;
        }

        for(int i=X1;i<X2;i++){
            for(int j=Y1;j<Y2;j++){
                if (Map.getMapCell(i, j).getBuilding()!= null){
                    GameMenuView.output("buildingPlaced");
                    return;
                }
            }
        }

        for(int i=X1;i<X2;i++){
            for(int j=Y1;j<Y2;j++){
                Map.getMapCell(i, j).setTexture(type);
            }
        }
        GameMenuView.output("textureSet");
    }
    public static void clear(int X, int Y){
       // Map.getMapCell(X,Y).setTexture();default texture
        Map.getMapCell(X,Y).setBuilding(null);
        Map.getMapCell(X,Y).setPassable(true);
        Map.getMapCell(X,Y).setUnit(null);
        Map.getMapCell(X,Y).setRockDirection(null);
        Map.getMapCell(X,Y).setTree(null);
        GameMenuView.output("success");


    }
    public static void dropRock(int X, int Y, Direction direction){
        if (!Map.validMapCell(X, Y)){
            GameMenuView.output("invalidLocation");
        } else if (direction == null) {
            GameMenuView.output("invalidDirection");
        } else if (direction.equals(RANDOM)) {
            Map.getMapCell(X,Y).setRockDirection(Direction.getRandom());
            GameMenuView.output("rockDrop");
        } else{
            Map.getMapCell(X,Y).setRockDirection(direction);
            GameMenuView.output("rockDrop");
        }
    }
    public static void dropTree(int X, int Y, Tree type){
        if(type.equals(null)){
            GameMenuView.output("invalidType");

        }else{
            Map.getMapCell(X,Y).setTree(type);
        }

    }
    public static void dropUnit(int X, int Y, String type, int count){

    }


    public static void enterTradeMenu(){

    }

    public static void enterShopMenu(){

    }
    public static void enterMapMenu(){

    }

    public static ArrayList<Government> getGovernments() {
        return governments;
    }

    public static void setGovernments(ArrayList<Government> governments) {
        GameMenuController.governments = governments;
    }


}
