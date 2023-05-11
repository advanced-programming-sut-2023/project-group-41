package stronghold.controller;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import stronghold.model.components.game.*;

import java.lang.Math;

import stronghold.model.components.game.building.Building;
import stronghold.model.components.game.building.Castle;

import stronghold.model.components.game.building.*;


import stronghold.model.components.game.enums.*;
import stronghold.model.components.game.soldeirtype.*;
import stronghold.model.components.general.User;
import stronghold.view.GameMenuView;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;
import java.util.regex.Matcher;

import static stronghold.model.components.game.enums.Direction.RANDOM;
import static stronghold.model.components.game.enums.Resource.*;

public class GameMenuController extends MenuController {
    private static int roundNum;
    private static int currentRound;
    private static int playerNum;

    private static String pathToRegexJSON = "src/main/java/stronghold/database/utils/regex/GameMenuRegex.json";
    ///////////
    private static ArrayList<Unit> currentUnits=new ArrayList<>();
    private static Building currentBuilding;
    private static Government currentPlayer;

    //private static Government currentGovernment;
    private static ArrayList<Government> governments = new ArrayList<>();
    private static ArrayList<Unit> patrolingUnits = new ArrayList<>();

    public static int getCurrentRound() {
        return currentRound;
    }

    public static void setCurrentRound(int currentRound) {
        GameMenuController.currentRound = currentRound;
    }

    public static int getPlayerNum() {
        return playerNum;
    }

    public static void setPlayerNum(int playerNum) {
        GameMenuController.playerNum = playerNum;
    }

    public static void run(Scanner scanner, int round, int playerNum1, int mapSize) {
        Map.getInstanceMap().setSize(mapSize);
         startGame(playerNum1);
         setCurrentPlayer(getGovernmentByColor(1));
        setCurrentRound(1);
        roundNum=round;
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

            Matcher setTextureMatcher;
            Matcher setRectangleTextureMatcher;
            Matcher clearMatcher;
            Matcher dropRockMatcher;
            Matcher dropTreeMatcher;
            Matcher dropUnitMatcher;
            Matcher enterMapMenu;
            Matcher enterShopMenu;
            Matcher enterTradeMenu;

            if (command.matches("user\\s+logout")) {
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
            } else if ((selectUnitMatcher = getJSONRegexMatcher(command, "selectUnit", gameMenuRegexObj)).matches()) {
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
                dropTree(X, Y, type);
            } else if ((dropUnitMatcher = getJSONRegexMatcher(command, "dropUnit", gameMenuRegexObj)).matches()) {
                int X = Integer.parseInt(dropUnitMatcher.group("X"));
                int Y = Integer.parseInt(dropUnitMatcher.group("Y"));
                String type = dropUnitMatcher.group("type");
                int count = Integer.parseInt(dropUnitMatcher.group("count"));
                dropUnit(X, Y, type, count);
            } else if (getJSONRegexMatcher(command, "enterMapMenu", gameMenuRegexObj).matches()) {
                enterMapMenu();

            } else if (getJSONRegexMatcher(command, "enterShopMenu", gameMenuRegexObj).matches()) {
                ///da2

            } else if (getJSONRegexMatcher(command, "enterTradeMenu", gameMenuRegexObj).matches()) {
                ///da3

            } else if (getJSONRegexMatcher(command, "nextPlayer", gameMenuRegexObj).matches()) {
                nextPlayer();

            }else {
                GameMenuView.output("invalid");
            }
        }
    }

    public static void startGame(int playerNum) {

        Scanner scanner = new Scanner(System.in);

        for (int i = 1; i <= playerNum; i++) {
            Government government = new Government(i);
            if(i==1)
                currentPlayer=government;
            governments.add(government);
            GameMenuView.output("playerCenter");
            int x = scanner.nextInt();
            int y = scanner.nextInt();


            Castle castle=new Castle(government,CastleType.Ruler);
            government.setRuler(castle);
            Map.getInstanceMap().getMapCell(x, y).setBuilding(castle);

        }
    }

    public static void setCurrentPlayer(Government government) {
        currentPlayer = government;
    }

    public static Government getGovernmentByColor(int color) {
        for (Government government : governments) {
            if (government.getColor() == color) {
                return government;
            }
        }
        return null;

    }


    public static void nextPlayer() {

       if(currentPlayer.getColor()==playerNum){
           //endofround
           currentRound++;
           if(currentRound>roundNum){
               //endGame
           }
           setCurrentPlayer(getGovernmentByColor(1));
       }else{
           setCurrentPlayer(getGovernmentByColor(currentPlayer.getColor() + 1));
       }
        System.out.println("player: "+currentPlayer.getColor());
        System.out.println("round"+currentRound);


    }

    public static void endGame() {

    }

    public static int getRoundNum() {
        return roundNum;
    }





    public static void endOfRound(){
        currentPlayer.allBuildingActions();
    }

    public static void showPopularityFactors() {
        GameMenuView.output("showPopularityFactors");

    }

    public static void showPopularity() {
        System.out.println(currentPlayer.getPopularity());
        GameMenuView.output("success");
        GameMenuView.output("popularity", (Object) Integer.toString(currentPlayer.getPopularity()));


    }
    public  static void populationLogic(Government government){
       int leftOver= government.getResourcesNum(APPLE)+government.getResourcesNum(MEAT)+government.getResourcesNum(CHEESE)+government.getResourcesNum(BREAD);
       government.setPopulation(government.getPopulation()+leftOver);
       GameMenuView.output("success");

    }



    public static void showFoodList(){
        GameMenuView.output("foodList", (Object) "APPLE", Integer.toString(currentPlayer.getResourcesNum(APPLE)));
        GameMenuView.output("foodList", (Object) "CHEESE", Integer.toString(currentPlayer.getResourcesNum(CHEESE)));
        GameMenuView.output("foodList", (Object) "BREAD", Integer.toString(currentPlayer.getResourcesNum(BREAD)));
        GameMenuView.output("foodList", (Object) "MEAT", Integer.toString(currentPlayer.getResourcesNum(MEAT)));
    }

    public static void foodRate(int rate) {
        currentPlayer.setFoodRate(rate);
        GameMenuView.output("success");

    }

    public static void foodRateShow() {

        GameMenuView.output("rate");
        System.out.println(currentPlayer.getFoodRate());

    }

    public static void taxRate(int rate) {
        currentPlayer.setTaxRate(rate);
        GameMenuView.output("success");
        //System.out.println(currentPlayer.getTaxRate());

    }

    public static void taxRateShow() {
        GameMenuView.output("rate");
        System.out.println(currentPlayer.getTaxRate());

    }

    public static void fearRate(int rate) {
        GameMenuView.output("rate");
        System.out.println(currentPlayer.getFearRate());

    }

    public static void setCurrentUnits(ArrayList<Unit> currentUnits) {
        GameMenuController.currentUnits = currentUnits;
    }

    public static void dropBuilding(int X, int Y, Building type){
        MapCell mapCell;
        ResourceMaker resourceMaker;
        System.out.println(Map.getInstanceMap().getMapCell(X,Y).getBuilding());
        if (Map.getInstanceMap().validMapCell(X, Y)){
            mapCell = Map.getInstanceMap().getMapCell(X, Y);
        } else {
            GameMenuView.output("invalidLocation");
            return;
        }
        if(Map.getInstanceMap().getMapCell(X,Y).getBuilding()!=null){
            GameMenuView.output("prebuilding");
        }
        if (type == null){
            GameMenuView.output("incorrectBuildingType");
            return;
        } else if (!type.haveEnoughResource(currentPlayer)) {
            GameMenuView.output("lackOfResource");
        } else if (type.getClass().getSimpleName().equals("ResourceMaker")
                && !(resourceMaker = (ResourceMaker) type).checkTexture(mapCell.getTexture())){
            GameMenuView.output("textureProblem");
        } else if (type.getBuildingType().equals(StorageType.STOCK_PILE) && !Map.getInstanceMap().isBuildingHere(X, Y, type.getBuildingType())){
            GameMenuView.output("nearBuilding", (Object) type.getBuildingType().getRegex());
        } else if (type.getBuildingType().equals(StorageType.FOOD_STOCK_PILE) && !Map.getInstanceMap().isBuildingHere(X, Y, type.getBuildingType())) {
            GameMenuView.output("nearBuilding", (Object) type.getBuildingType().getRegex());
        } else if (type.getBuildingType().equals(ConverterType.Ox_TETHER) && !Map.getInstanceMap().isBuildingNear(X, Y, ResourceMakerType.QUARRY)) {
            GameMenuView.output("nearBuilding", (Object) ResourceMakerType.QUARRY.getRegex());
        } else {
            mapCell.setBuilding(type);
            Map.getInstanceMap().getMapCell(X,Y).setPassable(false);
            GameMenuView.output("buildingDrop");
        }
    }

    public static void selectBuilding(int X, int Y) {
        Building building = Map.getInstanceMap().getMapCell(X, Y).getBuilding();
        if (building == null) {
            GameMenuView.output("noBuildingAvailable");
        } else {
            currentBuilding = building;
            GameMenuView.output("buildingSelected");
        }
    }

    public static void createUnit(String type, int count) {
        if (currentBuilding == null){

        } else if (currentBuilding.getBuildingType().equals(ConverterType.SHOP)){

        } else if (currentBuilding.getBuildingType().equals(ConverterType.MERCENARY_POST)){
            
        } else if (currentBuilding.getBuildingType().equals(ConverterType.BARRACKS)){
            
        } else if (currentBuilding.getBuildingType().equals(DevelopmentType.CHURCH) ||
                currentBuilding.getBuildingType().equals(DevelopmentType.CATHEDRAL)) {

        } else {
            GameMenuView.output("selectUnitBuilding");
        }
    }

    public static void repair() {
        if (!currentBuilding.getClass().getSimpleName().equals("Castle")) {
            GameMenuView.output("incorrectBuildingType");
        } else if (false) {
            // TODO: stopping repair when soldier are near
        } else {
            Castle castle = (Castle) currentBuilding;
            castle.repair();
        }
    }

    public static void selectUnit(int X, int Y) {
        if (Map.getInstanceMap().getMapCell(X, Y).getUnits().size()==0) {
            GameMenuView.output("unitError");
        } else if(!Map.getInstanceMap().getMapCell(X, Y).getUnits().get(0).getPeople().getOwner().equals(currentPlayer)){
            System.out.println("notyourtroop");

        }else {
            setCurrentUnits( Map.getInstanceMap().getMapCell(X, Y).getUnits());
            for (Unit unit : currentUnits) {
                System.out.println(unit.getPeople().getRegex());
            }
            GameMenuView.output("success");
        }
    }

    public static void moveUnitTo(int X, int Y) {
        if (Map.getInstanceMap().getMapCell(X, Y).getTexture().equals(Texture.RIVER) || Map.getInstanceMap().getMapCell(X, Y).getTexture().equals(Texture.SEA) || Map.getInstanceMap().getMapCell(X, Y).getTexture().equals(Texture.SHALLOW_LAKE) || Map.getInstanceMap().getMapCell(X, Y).getTexture().equals(Texture.SMALL_POND) || Map.getInstanceMap().getMapCell(X, Y).getTexture().equals(Texture.BIG_POND)) {
            GameMenuView.output("waterError");
        } else if (Math.abs(X-currentUnits.get(0).getX() )> (currentUnits.get(0).getPeople().getSpeed() * 5)  ||  Math.abs(Y-currentUnits.get(0).getY() ) > (currentUnits.get(0).getPeople().getSpeed() * 5) ) {
            GameMenuView.output("speedError");

        } else {

            for (Unit unit : currentUnits) {

                Map.getInstanceMap().getMapCell(X, Y).addUnit(unit);

            }
            Map.getInstanceMap().getMapCell(currentUnits.get(0).getX(), currentUnits.get(0).getY()).getUnits().clear();
            GameMenuView.output("success");
        }
    }

    public static void patrolUnit(int X1, int Y1, int X2, int Y2) {
        if (!currentUnits.get(0).isInRange(X1, Y1) || !currentUnits.get(0).isInRange(X2, Y2)) {
            GameMenuView.output("bondError");
        } else {

            //bondError
            for (Unit unit : currentUnits) {
                unit.setPatrolX1(X1);
                unit.setPatrolX2(X2);
                unit.setPatrolY1(Y1);
                unit.setPatrolY2(Y2);
                patrolingUnits.add(unit);


            }
            GameMenuView.output("success");
        }

    }
    public static void patroller(){
        for (Unit unit : patrolingUnits) {
            if(unit.getX()!=unit.getPatrolX1()||unit.getY()!=unit.getPatrolY1()){
                int x=unit.getX(),y=unit.getY();
                unit.setX(unit.getPatrolX1());
                unit.setY(unit.getPatrolY1());
                Map.getInstanceMap().getMapCell(unit.getPatrolX1(),unit.getPatrolY1()).getUnits().add(unit);
                Map.getInstanceMap().getMapCell(x,y).getUnits().remove(unit);
            }else if(unit.getX()!=unit.getPatrolX2()||unit.getY()!=unit.getPatrolY2()){
                int x=unit.getX(),y=unit.getY();
                unit.setX(unit.getPatrolX2());
                unit.setY(unit.getPatrolY2());
                Map.getInstanceMap().getMapCell(unit.getPatrolX2(),unit.getPatrolY2()).getUnits().add(unit);
                Map.getInstanceMap().getMapCell(x,y).getUnits().remove(unit);

            }
        }
    }

    public static void setStateOfUnit(int X, int Y, State state) {
        // State is an enum class and have three obj: standing|defensive|offensive
        for (Unit unit : Map.getInstanceMap().getMapCell(X, Y).getUnits()) {
            unit.setState(state);

        }
        GameMenuView.output("success");
    }

    public static void attackEnemy(int enemyX, int enemyY) {
        //command: attack -e [enemy’s x] [enemy’
        //buildings attack

        if (!currentUnits.get(0).isInRange(enemyX, enemyY)) {
            GameMenuView.output("enemyBond");
            return;
        } else if (Map.getInstanceMap().getMapCell(enemyX, enemyY).getUnits().size() == 0) {
            GameMenuView.output("enemyError");

        } else {
            int allyOffense = 0, allyDefense = 0, enemyOffense = 0, enemyDefense = 0;
            for (Unit unit : currentUnits) {
                allyOffense += unit.getPeople().getOffense() * unit.getCount();
                allyDefense += unit.getPeople().getDefence() * unit.getCount();
            }
            for (Unit unit : Map.getInstanceMap().getMapCell(enemyX, enemyY).getUnits()) {
                if (!unit.getPeople().getOwner().equals(currentPlayer)) {
                    enemyOffense += unit.getPeople().getOffense() * unit.getCount();
                    enemyOffense += unit.getPeople().getDefence() * unit.getCount();
                }

            }
            if (Map.getInstanceMap().getMapCell(enemyX, enemyY).getBuilding() != null && Map.getInstanceMap().getMapCell(enemyX, enemyY).getBuilding() instanceof Castle) {
                enemyOffense += ((Castle) Map.getInstanceMap().getMapCell(enemyX, enemyY).getBuilding()).getFireRange() * 10;
            }
            if (allyOffense == enemyDefense) {

                Map.getInstanceMap().getMapCell(enemyX, enemyY).getUnits().clear();
                Map.getInstanceMap().getMapCell(currentUnits.get(0).getX(), currentUnits.get(0).getY()).getUnits().clear();
                if(Map.getInstanceMap().getMapCell(enemyX, enemyY).getBuilding()!=null){
                    Map.getInstanceMap().getMapCell(enemyX, enemyY).getBuilding().setHealth(Map.getInstanceMap().getMapCell(enemyX, enemyY).getBuilding().getHealth()-allyOffense);

                }

                GameMenuView.output("fightLoss");
                GameMenuView.output("fightWin");


            } else if (allyOffense >= enemyDefense) {

                Map.getInstanceMap().getMapCell(enemyX, enemyY).getUnits().clear();


                if(Map.getInstanceMap().getMapCell(enemyX, enemyY).getBuilding()!=null){
                    Map.getInstanceMap().getMapCell(enemyX, enemyY).getBuilding().setHealth(Map.getInstanceMap().getMapCell(enemyX, enemyY).getBuilding().getHealth()-allyOffense);

                }
                GameMenuView.output("fightWin");


            } else {
                Map.getInstanceMap().getMapCell(currentUnits.get(0).getX(), currentUnits.get(0).getY()).getUnits().clear();
                GameMenuView.output("fightLoss");


            }

        }

    }

    public static void airAttack(int X, int Y) {
        int longRanged = 0;
        if (Map.getInstanceMap().getMapCell(X, Y).getUnits().size() == 0) {
            GameMenuView.output("enemyError");
            return;

        }
        for (Unit unit : currentUnits) {
            if (unit.getPeople() instanceof LongRanged) {
                if (((LongRanged) unit.getPeople()).getRange() < Math.abs(X - unit.getX()) || ((LongRanged) unit.getPeople()).getRange() < Math.abs(Y - unit.getY())) {
                    GameMenuView.output("bondError");
                    return;
                }
                longRanged += unit.getCount() * unit.getPeople().getOffense();

            }
        }
        if (Map.getInstanceMap().getMapCell(X, Y).getBuilding() != null ) {
            Map.getInstanceMap().getMapCell(X, Y).getBuilding().setHealth(Map.getInstanceMap().getMapCell(X, Y).getBuilding().getHealth() - longRanged);
            if (Map.getInstanceMap().getMapCell(X, Y).getBuilding().getHealth() <= 0) {
                Map.getInstanceMap().getMapCell(X, Y).setBuilding(null);

            }
        }


            for (Unit unit1 : Map.getInstanceMap().getMapCell(X, Y).getUnits()) {

                int j = longRanged - unit1.getCount();
                unit1.setCount(Math.max(0, unit1.getCount() - longRanged));
                longRanged = j;



            }
            ArrayList<Unit>alive=new ArrayList<>();
        for (Unit unit : Map.getInstanceMap().getMapCell(X, Y).getUnits()) {
            if(unit.getCount()<=0){
                alive.add(unit);

            }

        }
        Map.getInstanceMap().getMapCell(X, Y).getUnits().clear();
        for (Unit unit : alive) {
            Map.getInstanceMap().getMapCell(X, Y).getUnits().add(unit);
        }
            GameMenuView.output("fightWin");




    }

    public static void pourOil(Direction direction) {
        for (Unit unit : currentUnits) {
            Unit unit1 = Map.getInstanceMap().getUnarmed(Map.getInstanceMap().getMapCell(currentUnits.get(0).getX(), currentUnits.get(0).getY()), "engineer");
            if (unit1 != null) {
                if (unit1.getPeople().getHasOil()) {
                    if (direction.getRegex().equals("r")) {
                        Map.getInstanceMap().getMapCell(currentUnits.get(0).getX() + 1, currentUnits.get(0).getY()).setHasOil(true);
                        GameMenuView.output("success");
                    }
                    if (direction.getRegex().equals("n")) {
                        Map.getInstanceMap().getMapCell(currentUnits.get(0).getX(), currentUnits.get(0).getY() + 1).setHasOil(true);
                        GameMenuView.output("success");
                    }
                    if (direction.getRegex().equals("s")) {
                        Map.getInstanceMap().getMapCell(currentUnits.get(0).getX(), currentUnits.get(0).getY() - 1).setHasOil(true);
                        GameMenuView.output("success");
                    }
                    if (direction.getRegex().equals("w")) {
                        Map.getInstanceMap().getMapCell(currentUnits.get(0).getX() - 1, currentUnits.get(0).getY()).setHasOil(true);
                        GameMenuView.output("success");
                    }
                    if (direction.getRegex().equals("e")) {
                        Map.getInstanceMap().getMapCell(currentUnits.get(0).getX() + 1, currentUnits.get(0).getY()).setHasOil(true);
                        GameMenuView.output("success");
                    }

                } else {
                    if (currentPlayer.getBuildingHash().get(CastleType.OIL_SMELTER) == 0) {
                        GameMenuView.output("oilError");
                    } else {

                        unit1.getPeople().setHasOil(true);
                        GameMenuView.output("engineerGotOil");
                        return;
                    }

                }
            }
        }

    }

    public static void digTunnel(int X, int Y) {
        if (!Map.getInstanceMap().validMapCell(X, Y)) {
            GameMenuView.output("invalidLocation");
            return;
        }

        Unit unit1 = Map.getInstanceMap().getUnarmed(Map.getInstanceMap().getMapCell(currentUnits.get(0).getX(), currentUnits.get(0).getY()), "tunneler");
        if (unit1 != null) {
            Map.getInstanceMap().getMapCell(X, Y).setHasTunnel(true);
            GameMenuView.output("success");

        }
        GameMenuView.output("tunnelerError");


    }

    public static void build(String equipmentName) {
        if(equipmentName.equals("airDefense")){
            buildUnitAirDefense(currentUnits.get(0).getX(),currentUnits.get(0).getY());
        }else if(equipmentName.equals("seigeTower")){
            buildSeigeTower(currentUnits.get(0).getX(),currentUnits.get(0).getY());
        }
        else if(equipmentName.equals("fireThrower")){
            buildFireThrower(currentUnits.get(0).getX(),currentUnits.get(0).getY());
        }else if(equipmentName.equals("catapult")){
            buildCatapult(currentUnits.get(0).getX(),currentUnits.get(0).getY());
        }else if(equipmentName.equals("bigCatapult")){
            buildSeigeTower(currentUnits.get(0).getX(),currentUnits.get(0).getY());
        }else if(equipmentName.equals("battleRam")){
            buildBattleRam(currentUnits.get(0).getX(),currentUnits.get(0).getY());
        }

    }

    public static void burnOil(int x, int y) {
        if (Map.getInstanceMap().getMapCell(x, y).isHasOil()) {
            Map.getInstanceMap().getMapCell(x, y).getUnits().clear();
            GameMenuView.output("success");

        } else {
            GameMenuView.output("notOil");
        }
    }

    public static void digDitch(int x, int y) {
        Map.getInstanceMap().getMapCell(x, y).setHasDitch(true);
        GameMenuView.output("success");

    }

    public static void fillDitch(int x, int y) {
        if (!Map.getInstanceMap().getMapCell(x, y).isHasDitch()) {
            GameMenuView.output("fillDitchError");
        } else {
            Map.getInstanceMap().getMapCell(x, y).setHasDitch(false);
            GameMenuView.output("success");

        }
    }

    public static void disbandUnit() {

            Map.getInstanceMap().getMapCell(currentUnits.get(0).getX(), currentUnits.get(0).getY()).getUnits().clear();
            for (Unit unit : currentUnits) {
                unit.setX(currentPlayer.findRuler(currentPlayer.getRuler()).getX() + 3);
                unit.setY(currentPlayer.findRuler(currentPlayer.getRuler()).getY() + 3);
                Map.getInstanceMap().getMapCell(currentPlayer.findRuler(currentPlayer.getRuler()).getX() + 3, currentPlayer.findRuler(currentPlayer.getRuler()).getY() + 3).addUnit(unit);
            }
            GameMenuView.output("success");



    }


    public static void setTexture(int X, int Y, Texture type) {
        if (!Map.getInstanceMap().validMapCell(X, Y)) {
            GameMenuView.output("invalidLocation");
        } else if (Map.getInstanceMap().getMapCell(X, Y).getBuilding() != null) {
            GameMenuView.output("buildingPlaced");
        } else {
            Map.getInstanceMap().getMapCell(X, Y).setTexture(type);
            GameMenuView.output("textureSet");
        }
    }

    public static void setTexture(int X1, int Y1, int X2, int Y2, Texture type) {

        if (!Map.getInstanceMap().validMapCell(X1, Y1) || !Map.getInstanceMap().validMapCell(X2, Y2) || X2 < X1 || Y2 < Y1) {
            GameMenuView.output("cantMakeBlock");
            return;
        }

        for (int i = X1; i < X2; i++) {
            for (int j = Y1; j < Y2; j++) {
                if (Map.getInstanceMap().getMapCell(i, j).getBuilding() != null) {
                    GameMenuView.output("buildingPlaced");
                    return;
                }
            }
        }

        for (int i = X1; i < X2; i++) {
            for (int j = Y1; j < Y2; j++) {
                Map.getInstanceMap().getMapCell(i, j).setTexture(type);
            }
        }
        GameMenuView.output("textureSet");
    }

    public static void clear(int X, int Y) {
        // Map.getMapCell(X,Y).setTexture();default texture
        Map.getInstanceMap().getMapCell(X, Y).setBuilding(null);
        Map.getInstanceMap().getMapCell(X, Y).setPassable(true);
        Map.getInstanceMap().getMapCell(X, Y).getUnits().clear();
        Map.getInstanceMap().getMapCell(X, Y).setRockDirection(null);
        Map.getInstanceMap().getMapCell(X, Y).setTree(null);
        GameMenuView.output("success");


    }

    public static void dropRock(int X, int Y, Direction direction) {
        if (!Map.getInstanceMap().validMapCell(X, Y)) {
            GameMenuView.output("invalidLocation");
        } else if (direction == null) {
            GameMenuView.output("invalidDirection");


        } else if(Map.getInstanceMap().getMapCell(X,Y).getBuilding()!=null||Map.getInstanceMap().getMapCell(X,Y).getRockDirection()!=null||Map.getInstanceMap().getMapCell(X,Y).getTree()!=null){
            GameMenuView.output("sthIsHere");
        }else if (direction.equals(RANDOM)) {
            Map.getInstanceMap().getMapCell(X, Y).setRockDirection(Direction.getRandom());
            GameMenuView.output("rockDrop");
        } else {

            Map.getInstanceMap().getMapCell(X, Y).setRockDirection(direction);
            Map.getInstanceMap().getMapCell(X,Y).setPassable(false);
            GameMenuView.output("rockDrop");
        }
    }

    public static void dropTree(int X, int Y, Tree type) {




        if (type == null) {


            GameMenuView.output("invalidType");

        } else if(Map.getInstanceMap().getMapCell(X,Y).getBuilding()!=null||Map.getInstanceMap().getMapCell(X,Y).getRockDirection()!=null||Map.getInstanceMap().getMapCell(X,Y).getTree()!=null){
            GameMenuView.output("sthIsHere");
        }else {
            Map.getInstanceMap().getMapCell(X, Y).setTree(type);
            Map.getInstanceMap().getMapCell(X,Y).setPassable(false);
            GameMenuView.output("success");


        }

    }

    public static void dropUnit(int X, int Y, String type, int count) {
        if(Map.getInstanceMap().getMapCell(X,Y).isPassable()==false){
            GameMenuView.output("buildingError");
            return;
        }

        if(UnarmedEnum.getUnarmedType(type)!=null){
            Unarmed people = new Unarmed(UnarmedEnum.getUnarmedType(type));
            Unit unit = new Unit(X,Y,people,count);
            Map.getInstanceMap().getMapCell(X,Y).getUnits().add(unit);

            GameMenuView.output("success");
        }else if(FighterEnum.getFighterType(type)!=null){
            Fighter people = new Fighter(FighterEnum.getFighterType(type));

            Unit unit = new Unit(X,Y,people,count);
            Map.getInstanceMap().getMapCell(X,Y).getUnits().add(unit);

            GameMenuView.output("success");
        }else if(LongRangedEnum.getLongRangedType(type)!=null){
            LongRanged people = new LongRanged(LongRangedEnum.getLongRangedType(type));
            Unit unit = new Unit(X,Y,people,count);
            Map.getInstanceMap().getMapCell(X,Y).getUnits().add(unit);

            GameMenuView.output("success");
        }else{

            GameMenuView.output("wrongSoldierType");
        }

    }


    public static void enterTradeMenu() {
        Scanner scanner = new Scanner(System.in);
        GameMenuView.output("enterTradeMenu");
        TradeMenuController.run(scanner);

    }


    public static void enterShopMenu() {
        if (currentBuilding.getRegex().equals("Shop")) {
            Scanner scanner = new Scanner(System.in);
            GameMenuView.output("enterShopMenu");
            ShopMenuController.run(scanner);

        } else {
            GameMenuView.output("shopError");
            return;
        }

    }

    public static void enterMapMenu() {
        Scanner scanner = new Scanner(System.in);
        GameMenuView.output("enterMapMenu");
        MapMenuController.run(scanner);

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

    public static void popularityLogic() {
        //food types
        int i = 0;
        if (currentPlayer.getResourcesNum(APPLE) > 0) {
            i++;
        }
        if (currentPlayer.getResourcesNum(BREAD) > 0) {
            i++;
        }
        if (currentPlayer.getResourcesNum(CHEESE) > 0) {
            i++;
        }
        if (currentPlayer.getResourcesNum(MEAT) > 0) {
            i++;
        }
        currentPlayer.setPopularity(currentPlayer.getPopularity() + i - 1);
        currentPlayer.setPopularity(currentPlayer.getPopularity() + (-4 * currentPlayer.getFoodRate()));
        if (currentPlayer.getTaxRate() <= 0) {
            currentPlayer.setPopularity(currentPlayer.getPopularity() + ((-2 * currentPlayer.getTaxRate()) + 1));
        } else if (currentPlayer.getTaxRate() > 0 && currentPlayer.getTaxRate() <= 4) {
            currentPlayer.setPopularity(currentPlayer.getPopularity() - (2 * currentPlayer.getTaxRate()));

        } else {
            currentPlayer.setPopularity(currentPlayer.getPopularity() - (12 + ((currentPlayer.getTaxRate() - 5) * 4)));

        }
        currentPlayer.setPopularity(currentPlayer.getPopularity() + (currentPlayer.getBuildingHash().get(DevelopmentType.CHURCH) * 2 + currentPlayer.getBuildingHash().get(DevelopmentType.CATHEDRAL) * 2));
        currentPlayer.setPopularity(currentPlayer.getPopularity() + currentPlayer.getFearRate());
    }

    private static void foodLogic() {
        if (currentPlayer.getResourcesNum(MEAT) + currentPlayer.getResourcesNum(CHEESE) + currentPlayer.getResourcesNum(APPLE) + currentPlayer.getResourcesNum(BREAD) == 0) {
            currentPlayer.setFoodRate(-2);
        } else if (currentPlayer.getResourcesNum(MEAT) + currentPlayer.getResourcesNum(CHEESE) + currentPlayer.getResourcesNum(APPLE) + currentPlayer.getResourcesNum(BREAD) < currentPlayer.getPeople().size() * ((currentPlayer.getFoodRate() + 2) * 0.5)) {
            currentPlayer.setFoodRate(-2);
            return;

        } else {
            double neededFood = currentPlayer.getPeople().size() * ((currentPlayer.getFoodRate() + 2) * 0.5);
            double j = Math.max(0, currentPlayer.getResourcesNum(APPLE) - neededFood);
            double i = Math.max(0, neededFood - currentPlayer.getResourcesNum(APPLE));
            currentPlayer.getResourcesMap().put(APPLE, ((int) j));

            double j2 = Math.max(0, currentPlayer.getResourcesNum(BREAD) - i);
            double i2 = Math.max(0, i - currentPlayer.getResourcesNum(BREAD));
            currentPlayer.getResourcesMap().put(BREAD, ((int) j2));

            double j3 = Math.max(0, currentPlayer.getResourcesNum(CHEESE) - i2);
            double i3 = Math.max(0, i2 - currentPlayer.getResourcesNum(CHEESE));
            currentPlayer.getResourcesMap().put(CHEESE, ((int) j3));

            double j4 = Math.max(0, currentPlayer.getResourcesNum(MEAT) - i3);
            double i4 = Math.max(0, i3 - currentPlayer.getResourcesNum(MEAT));
            currentPlayer.getResourcesMap().put(BREAD, ((int) j4));


        }
    }

    public static void taxLogic() {
        double i = 0;
        if (currentPlayer.getTaxRate() < 0) {
            i = 1 - (-0.2 * (3 - currentPlayer.getTaxRate()));
            if (currentPlayer.getBalance() < currentPlayer.getPeople().size() * i) {
                currentPlayer.setFoodRate(-2);
                return;

            }
            i *= -1;
        } else if (currentPlayer.getTaxRate() > 0) {
            i = 0.4 + (0.2 * currentPlayer.getTaxRate());

        }
        if (currentPlayer.getBalance() == 0) {
            currentPlayer.setTaxRate(0);
            return;
        } else {
            currentPlayer.setBalance(currentPlayer.getBalance() - currentPlayer.getPeople().size() * i);
        }

    }

    public static void buildUnitAirDefense(int x, int y) {
        Unit unit1 = Map.getInstanceMap().getUnarmed(Map.getInstanceMap().getMapCell(currentUnits.get(0).getX(), currentUnits.get(0).getY()), "engineer");
        if (unit1 != null) {
            if (unit1.getCount() >= 1) {
                Tool tool = new Tool(1, "airDefense", unit1.getX(), unit1.getY(), false);
                for (Unit unit : Map.getInstanceMap().getMapCell(x, y).getUnits()) {
                    unit.setTool(tool);

                }

                GameMenuView.output("success");

            } else {
                GameMenuView.output("invalidEngNum");
            }
        }
    }

    public static void buildBattleRam(int x, int y) {

        Unit unit1 = Map.getInstanceMap().getUnarmed(Map.getInstanceMap().getMapCell(currentUnits.get(0).getX(), currentUnits.get(0).getY()), "engineer");
        if (unit1 != null) {
            if (unit1.getCount() > 3) {
                Tool tool = new Tool(4, "battleRam", unit1.getX(), unit1.getY(), false);
                Map.getInstanceMap().getMapCell(x, y).setTool(tool);
                GameMenuView.output("success");
            } else {
                GameMenuView.output("invalidEngNum");
            }
        }


    }

    public static void buildBigCatapult(int x, int y) {

        Unit unit1 = Map.getInstanceMap().getUnarmed(Map.getInstanceMap().getMapCell(currentUnits.get(0).getX(), currentUnits.get(0).getY()), "engineer");
        if (unit1 != null) {
            if (unit1.getCount() > 2) {
                Tool tool = new Tool(3, "bigCatapult", unit1.getX(), unit1.getY(), false);
                Map.getInstanceMap().getMapCell(x, y).setTool(tool);
                GameMenuView.output("success");

            } else {
                GameMenuView.output("invalidEngNum");
            }
        }


    }

    public static void buildCatapult(int x, int y) {

        Unit unit1 = Map.getInstanceMap().getUnarmed(Map.getInstanceMap().getMapCell(currentUnits.get(0).getX(), currentUnits.get(0).getY()), "engineer");
        if (unit1 != null ) {
            if(unit1.getCount() > 1){
            Tool tool = new Tool(2, "catapult", unit1.getX(), unit1.getY(), false);
            Map.getInstanceMap().getMapCell(x, y).setTool(tool);
            GameMenuView.output("success");

        } else {
            GameMenuView.output("invalidEngNum");
        }}


    }

    public static void buildFireThrower(int x, int y) {

        Unit unit1 = Map.getInstanceMap().getUnarmed(Map.getInstanceMap().getMapCell(currentUnits.get(0).getX(), currentUnits.get(0).getY()), "engineer");
        if (unit1 != null) {
            if( unit1.getCount() > 1){
            Tool tool = new Tool(2, "fireThrower", unit1.getX(), unit1.getY(), true);
            Map.getInstanceMap().getMapCell(x, y).setTool(tool);
            GameMenuView.output("success");

        } else {
            GameMenuView.output("invalidEngNum");
        }}


    }
    public static void buildSeigeTower(int x, int y) {

        Unit unit1 = Map.getInstanceMap().getUnarmed(Map.getInstanceMap().getMapCell(currentUnits.get(0).getX(), currentUnits.get(0).getY()), "engineer");
        if (unit1 != null) {
            if( unit1.getCount() >= 4){
                Tool tool = new Tool(4, "seigeTower", unit1.getX(), unit1.getY(), true);
                Map.getInstanceMap().getMapCell(x, y).setTool(tool);
                GameMenuView.output("success");
                //TODO: wall break

            } else {
                GameMenuView.output("invalidEngNum");
            }}


    }



    public static void main(String[] args) {




        Scanner s=new Scanner(System.in);
        Unarmed unarmed=new Unarmed(UnarmedEnum.engineer);
        Unit unit=new Unit(3,4, unarmed,5);
        currentUnits.add(unit);

        run(s,1,2,200);





    }


}

