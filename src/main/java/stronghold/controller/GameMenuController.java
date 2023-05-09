package stronghold.controller;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import stronghold.model.components.game.Government;
import stronghold.model.components.game.Map;
import stronghold.model.components.game.MapCell;
import stronghold.model.components.game.Unit;
import java.lang.Math;

import stronghold.model.components.game.building.Building;
import stronghold.model.components.game.building.Castle;

import stronghold.model.components.game.building.*;


import stronghold.model.components.game.enums.*;
import stronghold.model.components.game.soldeirtype.LongRanged;
import stronghold.model.components.game.soldeirtype.Unarmed;
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
    private static ArrayList<Unit> currentUnits;
    private static Building currentBuilding;
    private static Government currentPlayer;

    //private static Government currentGovernment;
    private static ArrayList<Government> governments=new ArrayList<>();




    public static void run(Government fisrtGovernment, ArrayList<User> users, Scanner scanner,int round) {
        currentPlayer = fisrtGovernment;
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
                Building type = Building.getBuilding(currentPlayer, dropBuildingMatcher.group("type"));
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

        Scanner scanner= new Scanner(System.in);
        for(int i=1;i<=playerNum;i++){
            Government government=new Government(i);
            governments.add(government);
            GameMenuView.output("playerCenter");
            int x=scanner.nextInt();
            int y=scanner.nextInt();
            Map map = Map.getInstanceMap();
            map.setSize(200);
            map.getMapCell(x,y).setBuilding(government.getRuler());
           // System.out.println(map.getMapCell(x,y).getBuilding()2);
        }
    }
    public static void setCurrentPlayer(Government government){
        currentPlayer=government;
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


    public static void nextPlayer(int PlayerNum){
        if(currentPlayer.getColor()==PlayerNum){
            //TODO:end of round
        }
        setCurrentPlayer(getGovernmentByColor(currentPlayer.getColor()+1));

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

        GameMenuView.output("foodList", (Object) "APPLE", Integer.toString(currentPlayer.getResourcesNum(APPLE)));
        GameMenuView.output("foodList", (Object) "CHEESE", Integer.toString(currentPlayer.getResourcesNum(CHEESE)));
        GameMenuView.output("foodList", (Object) "BREAD", Integer.toString(currentPlayer.getResourcesNum(BREAD)));
        GameMenuView.output("foodList", (Object) "MEAT", Integer.toString(currentPlayer.getResourcesNum(MEAT)));
    }
    public static void foodRate(int rate){
        currentPlayer.setFoodRate(rate);
        GameMenuView.output("success");

    }
    public static void foodRateShow(){
        GameMenuView.output("rate");
        System.out.println(currentPlayer.getFoodRate());

    }
    public static void taxRate(int rate){
        currentPlayer.setTaxRate(rate);
        GameMenuView.output("success");

    }
    public static void taxRateShow(){
        GameMenuView.output("rate");
        System.out.println(currentPlayer.getTaxRate());

    }
    public static void fearRate(int rate){
        GameMenuView.output("rate");
        System.out.println(currentPlayer.getFearRate());

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
        } else if (type.getRegex().matches("\\s*oxTether\\s*") && !Map.isBuildingNear(X, Y, ResourceMakerType.QUARRY)) {
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
        if(Map.getMapCell(X, Y).getUnits() == null){
            GameMenuView.output("unitError");
        } else {
            currentUnits =Map.getMapCell(X, Y).getUnits();
            GameMenuView.output("success");
        }
    }
    public static void moveUnitTo(int X, int Y){
        if(Map.getMapCell(X,Y).getTexture().equals(Texture.RIVER)||Map.getMapCell(X,Y).getTexture().equals(Texture.SEA)||Map.getMapCell(X,Y).getTexture().equals(Texture.SHALLOW_LAKE)||Map.getMapCell(X,Y).getTexture().equals(Texture.SMALL_POND)||Map.getMapCell(X,Y).getTexture().equals(Texture.BIG_POND)){
            GameMenuView.output("waterError");
        }
        else  if(X>(currentUnits.get(0).getPeople().getSpeed()*4)+ currentUnits.get(0).getX()||X<(currentUnits.get(0).getPeople().getSpeed()*4)-  currentUnits.get(0).getX()||Y>(currentUnits.get(0).getPeople().getSpeed()*4)+  currentUnits.get(0).getY()||Y<(currentUnits.get(0).getPeople().getSpeed()*4)+ currentUnits.get(0).getY()){
            GameMenuView.output("speedError");

        }

        else{
            Map.getMapCell(currentUnits.get(0).getX(), currentUnits.get(0).getY()).getUnits().clear();
            for(Unit unit:currentUnits){
                Map.getMapCell(X,Y).addUnit(unit);

            }

            GameMenuView.output("success");
        }
    }
    public static void patrolUnit(int X1, int Y1, int X2, int Y2){
        if(!currentUnits.get(0).checkBonds(X1,Y1)||!currentUnits.get(0).checkBonds(X2,Y2)){
            GameMenuView.output("bondError");
        }else {

            //bondError
            for (Unit unit : currentUnits) {
                unit.setPatrolX1(X1);
                unit.setPatrolX2(X2);
                unit.setPatrolY1(Y1);
                unit.setPatrolY2(Y2);



            }
            GameMenuView.output("success");
        }

    }
    public static void setStateOfUnit(int X, int Y, State state){
        // State is an enum class and have three obj: standing|defensive|offensive
        for(Unit unit:currentUnits){
            unit.setState(state);

        }
        GameMenuView.output("success");
    }
    public static void attackEnemy(int enemyX, int enemyY) {
        //command: attack -e [enemy’s x] [enemy’
        //buildings attack

    }
    public static void airAttack(int X, int Y) {
        //command: attack -x [x] -y [y]
        int xDistance= currentUnits.get(0).getX()-X,yDistance= currentUnits.get(0).getY()-Y;
        for(int i=0;i<currentUnits.size();i++){
        if(!(currentUnits.get(i).getPeople() instanceof LongRanged)){
            GameMenuView.output("notLongRanged");
        }else{
            if (xDistance<0)
                xDistance*=-1;
            if(yDistance<0)
                yDistance*=-1;
            if(xDistance<=((LongRanged) currentUnits.get(i).getPeople()).getRange()*5&&yDistance<=((LongRanged) currentUnits.get(i).getPeople()).getRange()*5){
                   //building
                    Map.getMapCell(currentUnits.get(i).getX(), currentUnits.get(i).getY()).getUnits().clear();
                    //System.out.println("you have eliminated all troops in the coordinates!");

            }else {
               GameMenuView.output("bondError");
            }

        }}
    }
    public static void pourOil(Direction direction){
        for (Unit unit:currentUnits){
            Unit unit1=Map.getUnarmed(Map.getMapCell(currentUnits.get(0).getX(),currentUnits.get(0).getY()),"engineer");
            if(unit1!=null){
                if(unit1.getPeople().getHasOil()){
                    if(direction.getRegex().equals("r")){
                        Map.getMapCell(currentUnits.get(0).getX()+1,currentUnits.get(0).getY()).setHasOil(true);
                        GameMenuView.output("success");
                    } if(direction.getRegex().equals("n")){
                        Map.getMapCell(currentUnits.get(0).getX(),currentUnits.get(0).getY()+1).setHasOil(true);
                        GameMenuView.output("success");
                    } if(direction.getRegex().equals("s")){
                        Map.getMapCell(currentUnits.get(0).getX(),currentUnits.get(0).getY()-1).setHasOil(true);
                        GameMenuView.output("success");
                    } if(direction.getRegex().equals("w")){
                        Map.getMapCell(currentUnits.get(0).getX()-1,currentUnits.get(0).getY()).setHasOil(true);
                        GameMenuView.output("success");
                    } if(direction.getRegex().equals("e")){
                        Map.getMapCell(currentUnits.get(0).getX()+1,currentUnits.get(0).getY()).setHasOil(true);
                        GameMenuView.output("success");
                    }

        }else{

                }
            }
        }

    }
    public static void digTunnel(int X, int Y){
        if(!Map.validMapCell(X,Y)){
            GameMenuView.output("invalidLocation");
            return;
        }

            Unit unit1=Map.getUnarmed(Map.getMapCell(currentUnits.get(0).getX(),currentUnits.get(0).getY()),"tunneler");
            if(unit1!=null){
                Map.getMapCell(X,Y).setHasTunnel(true);
                GameMenuView.output("success");

            }
            GameMenuView.output("tunnelerError");



    }
    public static void build(String equipmentName){

    }
    public  static void burnOil(){

    }
    public static void disbandUnit(){
        if(!currentUnits.get(0).checkBondsForDisband(currentPlayer.findRuler(currentPlayer.getRuler()).getX(),currentPlayer.findRuler(currentPlayer.getRuler()).getY())){
            GameMenuView.output("bondError");
        }else {
            Map.getMapCell(currentUnits.get(0).getX(),currentUnits.get(0).getY()).getUnits().clear();
            for(Unit unit:currentUnits){
                unit.setX(currentPlayer.findRuler(currentPlayer.getRuler()).getX()+3);
                unit.setY(currentPlayer.findRuler(currentPlayer.getRuler()).getY()+3);
                Map.getMapCell(currentPlayer.findRuler(currentPlayer.getRuler()).getX()+3,currentPlayer.findRuler(currentPlayer.getRuler()).getY()+3).addUnit(unit);
            }
            GameMenuView.output("success");
        }


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
<<<<<<< HEAD


=======
>>>>>>> 12de9a89fa0c6e855c6ab0cbb7f35f121afcb6e6
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
        Map.getMapCell(X,Y).getUnits().clear();
        Map.getMapCell(X,Y).setRockDirection(null);
        Map.getMapCell(X,Y).setTree(null);
        GameMenuView.output("success");


    }
    public static void dropRock(int X, int Y, Direction direction){
        if (!Map.validMapCell(X, Y)){
            GameMenuView.output("invalidLocation");
        } else if (direction == null) {
            GameMenuView.output("invalidDirection");



        }else if (direction.equals(RANDOM)) {
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
        if (type == null){
            GameMenuView.output("incorrectBuildingType");
        } else {
            //Unit unit=new Unit(X,Y,type,count);//TODO:a method to get people type with string
           // Map.getMapCell(X, Y).addUnit(unit);
            //System.out.println(type.getClass().getSimpleName());
            GameMenuView.output("success");
        }
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





    public static Government getCurrentPlayer() {
        return currentPlayer;
    }
    public static void popularityLogic(){
        //food types
        int i=0;
        if(currentPlayer.getResourcesNum(APPLE)>0){
            i++;
        }
        if(currentPlayer.getResourcesNum(BREAD)>0){
            i++;
        }
        if(currentPlayer.getResourcesNum(CHEESE)>0){
            i++;
        }
        if(currentPlayer.getResourcesNum(MEAT)>0){
            i++;
        }
        currentPlayer.setPopularity(currentPlayer.getPopularity()+i-1);
        currentPlayer.setPopularity(currentPlayer.getPopularity()+(-4*currentPlayer.getFoodRate()));
        if(currentPlayer.getTaxRate()<=0){
            currentPlayer.setPopularity(currentPlayer.getPopularity()+((-2*currentPlayer.getTaxRate())+1));
        }else if(currentPlayer.getTaxRate()>0&&currentPlayer.getTaxRate()<=4){
            currentPlayer.setPopularity(currentPlayer.getPopularity()-(2*currentPlayer.getTaxRate()));

        }else {
            currentPlayer.setPopularity(currentPlayer.getPopularity()-(12+((currentPlayer.getTaxRate()-5)*4)));

        }
        //TODO:relgion ;
        currentPlayer.setPopularity(currentPlayer.getPopularity()+currentPlayer.getFearRate());
    }
    private static void foodLogic(){
        if(currentPlayer.getResourcesNum(MEAT)+currentPlayer.getResourcesNum(CHEESE)+currentPlayer.getResourcesNum(APPLE)+currentPlayer.getResourcesNum(BREAD)==0){
            currentPlayer.setFoodRate(-2);
        }else if(currentPlayer.getResourcesNum(MEAT)+currentPlayer.getResourcesNum(CHEESE)+currentPlayer.getResourcesNum(APPLE)+currentPlayer.getResourcesNum(BREAD)<currentPlayer.getPeople().size()*((currentPlayer.getFoodRate()+2)*0.5)){
            currentPlayer.setFoodRate(-2);
            return;

        }else{
            double neededFood=currentPlayer.getPeople().size()*((currentPlayer.getFoodRate()+2)*0.5);
            double j=Math.max(0,currentPlayer.getResourcesNum(APPLE)-neededFood);
            double i=Math.max(0,neededFood-currentPlayer.getResourcesNum(APPLE));
            currentPlayer.getResourcesMap().put(APPLE,((int)j));

            double j2=Math.max(0,currentPlayer.getResourcesNum(BREAD)-i);
            double i2=Math.max(0,i-currentPlayer.getResourcesNum(BREAD));
            currentPlayer.getResourcesMap().put(BREAD,((int)j2));

            double j3=Math.max(0,currentPlayer.getResourcesNum(CHEESE)-i2);
            double i3=Math.max(0,i2-currentPlayer.getResourcesNum(CHEESE));
            currentPlayer.getResourcesMap().put(CHEESE,((int)j3));

            double j4=Math.max(0,currentPlayer.getResourcesNum(MEAT)-i3);
            double i4=Math.max(0,i3-currentPlayer.getResourcesNum(MEAT));
            currentPlayer.getResourcesMap().put(BREAD,((int)j4));


        }
    }
    public static void taxLogic(){
        double i=0;
        if (currentPlayer.getTaxRate()<0){
            i=1-(-0.2*(3-currentPlayer.getTaxRate()));
            if(currentPlayer.getBalance()<currentPlayer.getPeople().size()*i){
                currentPlayer.setFoodRate(-2);
                return;

            }
            i*=-1;
        }else if(currentPlayer.getTaxRate()>0){
            i=0.4+(0.2*currentPlayer.getTaxRate());

        }
        if(currentPlayer.getBalance()==0){
            currentPlayer.setTaxRate(0);
            return;
        }else{
            currentPlayer.setBalance(currentPlayer.getBalance()-currentPlayer.getPeople().size()*i);
        }

    }

//    public static void main(String[] args) {
//        GameMenuController.run(null,null, new Scanner(System.in), 1);
//    }


}
