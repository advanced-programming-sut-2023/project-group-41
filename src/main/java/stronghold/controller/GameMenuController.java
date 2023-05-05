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
    private static String pathToRegexJSON = "src/main/java/stronghold/database/utils/regex/MainGameRegex.json";
    ///////////
    private static Unit currentUnit;
    private static Building currentBuilding;

    private static Government currentGovernment;
    private static ArrayList<Government> governments=new ArrayList<>();




    public static void run(Government fisrtGovernment, ArrayList<User> users, Scanner scanner,int round) {
        currentGovernment = fisrtGovernment;
        roundNum=round;
        JsonElement regexElement = null;
        try {
            regexElement = JsonParser.parseReader(new FileReader(pathToRegexJSON));
        } catch (
                FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        JsonObject gameMenuRegexObj = regexElement.getAsJsonObject();
        while (true){
            
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

            Matcher setTextureMatcher;
            Matcher setRectangleTextureMatcher;
            Matcher clearMatcher;
            Matcher dropRockMatcher;
            Matcher dropTreeMatcher;
            Matcher dropUnitMatcher;
            Matcher enterMapMenu;
            Matcher enterShopMenu;
            Matcher enterTradeMenu;

            if(command.matches("user\\s+logout")){
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
                Building type = Building.getBuilding(currentGovernment, dropBuildingMatcher.group("type"));
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
            } else if ((selectUnitMatcher = getJSONRegexMatcher(command, "selectUnit", gameMenuRegexObj)).matches() ) {
                int X = Integer.parseInt(selectUnitMatcher.group("X"));
                int Y = Integer.parseInt(selectUnitMatcher.group("Y"));
                selectUnit(X, Y);
            } else if ((moveUnitToMatcher = getJSONRegexMatcher(command, "moveUnitTo", gameMenuRegexObj)).matches()) {
                int X = Integer.parseInt(moveUnitToMatcher.group("X"));
                int Y = Integer.parseInt(moveUnitToMatcher.group("Y"));
                moveUnitTo(X, Y);
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
                attackEnemy(Integer.parseInt(attackEnemyMatcher.group(1)), Integer.parseInt(attackEnemyMatcher.group(2)));
            } else if ((airAttackMatcher = getJSONRegexMatcher(command, "airAttack", gameMenuRegexObj)).matches()) {
                //command: attack -x [x] -y [y]
                int X = Integer.parseInt(airAttackMatcher.group("X"));
                int Y = Integer.parseInt(airAttackMatcher.group("Y"));
                airAttack(X, Y);
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
                setTexture(X, Y, type);
            } else if ((setRectangleTextureMatcher = getJSONRegexMatcher(command, "setRectangleTexture", gameMenuRegexObj)).matches()) {
                // command: settexture -x1 [x1] -y1 [y1] -x2 [x2] -y2 [y2] -t [type]
                int X1 = Integer.parseInt(setRectangleTextureMatcher.group("X1"));
                int Y1 = Integer.parseInt(setRectangleTextureMatcher.group("Y1"));
                int X2 = Integer.parseInt(setRectangleTextureMatcher.group("X2"));
                int Y2 = Integer.parseInt(setRectangleTextureMatcher.group("Y2"));
                Texture type = Texture.getTexture(setRectangleTextureMatcher.group("type"));
                setTexture(X1, Y1, X2, Y2, type);
            } else if((clearMatcher = getJSONRegexMatcher(command, "clear", gameMenuRegexObj)).matches()) {
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
                dropTree(X, Y, type);
            } else if ((dropUnitMatcher = getJSONRegexMatcher(command, "dropUnit", gameMenuRegexObj)).matches()) {
                int X = Integer.parseInt(dropUnitMatcher.group("X"));
                int Y = Integer.parseInt(dropUnitMatcher.group("Y"));
                String type = dropUnitMatcher.group("type");
                int count = Integer.parseInt(dropUnitMatcher.group("count"));
                dropUnit(X, Y, type, count);
            }else if(getJSONRegexMatcher(command,"enterMapMenu",gameMenuRegexObj).matches()){
                ///da1

            }else if(getJSONRegexMatcher(command,"enterShopMenu",gameMenuRegexObj).matches()){
                ///da2

            }else if(getJSONRegexMatcher(command,"enterTradeMenu",gameMenuRegexObj).matches()){
                ///da3

            }else {
                GameMenuView.output("invalid");
            }
        }
    }
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
        Map.setSize(40);
        if (Map.validMapCell(X, Y)){
            mapCell = new MapCell(X, Y , Texture.IRON);//Map.getMapCell(X, Y);
        } else {
            GameMenuView.output("invalidLocation");
            return;
        }
        if (type == null){
            GameMenuView.output("incorrectBuildingType");
        } else if (type.getClass().getSimpleName().equals("ResourceMaker")
            && !(resourceMaker = (ResourceMaker) type).checkTexture(mapCell.getTexture())){
                GameMenuView.output("textureProblem");
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
        Map map = new Map(200);

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

//    public static void main(String[] args) {
//        GameMenuController.run(null,null, new Scanner(System.in), 1);
//    }

}
