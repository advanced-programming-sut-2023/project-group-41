package stronghold.controller;

import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import stronghold.model.components.game.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.lang.Math;

import stronghold.model.components.game.building.Building;
import stronghold.model.components.game.building.Castle;

import stronghold.model.components.game.building.*;


import stronghold.model.components.game.enums.*;
import stronghold.model.components.game.soldeirtype.*;
import stronghold.view.*;

import java.util.ArrayList;
import java.util.Scanner;

import static stronghold.model.components.game.enums.Direction.RANDOM;
import static stronghold.model.components.game.enums.Resource.*;

public class GameMenuController extends MenuController {
    private static int roundNum;
    private static int currentRound;
    private static int playerNum;
    private static Tool currentTool;

    private static String pathToRegexJSON = "src/main/java/stronghold/database/utils/regex/GameMenuRegex.json";
    ///////////
    private static MapCell currentMapCell;
    private static ArrayList<Unit> currentUnits = new ArrayList<>();
    private static Government currentPlayer;
    private static Building currentBuilding;
    private static int selectedBuildingX;
    private static int selectedBuildingY;

    public static Tool getCurrentTool() {
        return currentTool;
    }

    public static void setCurrentTool(Tool currentTool) {
        GameMenuController.currentTool = currentTool;
    }

    public static int getSelectedBuildingX() {
        return selectedBuildingX;
    }

    public static int getSelectedBuildingY() {
        return selectedBuildingY;
    }

    public static void setCurrentCell(MapCell mapCell){
        currentMapCell = mapCell;
        setCurrentBuilding(mapCell.getBuilding());
        setSelectedBuildingX(mapCell.getX());
        setSelectedBuildingY(mapCell.getY());
    }

    public static void setSelectedBuildingX(int selectedBuildingX) {
        GameMenuController.selectedBuildingX = selectedBuildingX;
    }

    public static void setSelectedBuildingY(int selectedBuildingY) {
        GameMenuController.selectedBuildingY = selectedBuildingY;
    }



    public static void setRoundNum(int roundNum) {
        GameMenuController.roundNum = roundNum;
    }

    public static void setCurrentBuilding(Building currentBuilding) {
        GameMenuController.currentBuilding = currentBuilding;
    }

    public static Building getCurrentBuilding() {
        return currentBuilding;
    }

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
    public static void setallCurrentGovernments(Government government){
        setCurrentPlayer(government);
        sampleView.setCurrentUser(government);
        sampleController.setCurrentGovernment(government);
        ShopMenuController.setCurrentGovernment(government);
        TradeMenuView.setCurrentUser(government);
        TradeMenuController.setCurrentGovernment(government);
        ReceivedTradeMenu.setCurrentUser(government);
        NewTradeMenu.setCurrentPlayer(government);
        SendTradeMenu.setCurrentUser(government);

    }


    public static void startGame(int playerNum) {

        Scanner scanner = new Scanner(System.in);
        GameMenuController.playerNum=playerNum;

        for (int i = 1; i <= playerNum; i++) {
            Government government = new Government(i);
            if (i == 1)
                currentPlayer = government;
            governments.add(government);
            GameMenuView.output("playerCenter");
            int x = scanner.nextInt();
            int y = scanner.nextInt();


            Castle castle = new Castle(government, CastleType.Ruler);
            government.setRuler(castle);
            Map.getInstanceMap().getMapCell(x, y).setBuilding(castle);


        }
        setallCurrentGovernments(getGovernmentByColor(1));
        System.out.println("fsdfasdfasdfasdfasdfd");
    }

    public static void setCurrentPlayer(Government government) {
        currentPlayer = government;
    }
    private static Image happy;

    public static Government getGovernmentByColor(int color) {
        for (Government government : governments) {
            if (government.getColor() == color) {
                return government;
            }
        }
        return null;
    }
    static Image poker;

    {
        try {
            poker = new Image(new FileInputStream("src/main/java/stronghold/database/Image/poker.png"));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    {
        try {
            happy = new Image(new FileInputStream("src/main/java/stronghold/database/Image/happy.jpg"));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    static Image sad;

    {
        try {
            sad = new Image(new FileInputStream("src/main/java/stronghold/database/Image/sad.png"));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static void nextPlayer() {
        System.out.println(currentPlayer.getColor());

        if (currentPlayer.getColor() == playerNum) {
            endOfRound();

            setallCurrentGovernments(getGovernmentByColor(1));
            try {
                sampleController.updateNodes();
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
            sampleView.getCoin().setText(Double.toString(currentPlayer.getBalance()));
            sampleView.getPopulation().setText(Integer.toString(currentPlayer.getPopulation())+"/"+Integer.toString(currentPlayer.getPopulation()+10* currentPlayer.getColor()));
            if(currentPlayer.getPopularity()>10){
                sampleView.getPopularity().setImage(happy);
            }else if(currentPlayer.getPopularity()< -10){
                sampleView.getPopularity().setImage(sad);
            }else{
                sampleView.getPopularity().setImage(poker);
            }
        } else {
            setallCurrentGovernments(getGovernmentByColor(currentPlayer.getColor() + 1));
            try {
                sampleController.updateNodes();
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
            sampleView.getCoin().setText(Double.toString(currentPlayer.getBalance()));
            sampleView.getPopulation().setText(Integer.toString(currentPlayer.getPopulation())+"/"+Integer.toString(currentPlayer.getPopulation()+10* currentPlayer.getColor()));
        }
        System.out.println("player: " + currentPlayer.getColor());
        System.out.println("round" + currentRound);


    }

    public static void endGame() {

    }

    public static int getRoundNum() {
        return roundNum;
    }

    public static void endOfRound() {
        patroller();
        for (int i = 1; i <= playerNum; i++) {
            setCurrentPlayer(getGovernmentByColor(i));
            currentPlayer.allBuildingActions();
            taxLogic();
            foodLogic();
            populationLogic(currentPlayer);
            popularityLogic();
            currentPlayer.unitKiller();

        }


    }

    public static void showPopularityFactors() {
        GameMenuView.output("showPopularityFactors");
    }

    public static void showPopularity() {
        System.out.println(currentPlayer.getPopularity());
        GameMenuView.output("success");
        GameMenuView.output("popularity", (Object) Integer.toString(currentPlayer.getPopularity()));


    }

    public static void populationLogic(Government government) {
        int leftOver = government.getResourcesNum(APPLE) + government.getResourcesNum(MEAT) + government.getResourcesNum(CHEESE) + government.getResourcesNum(BREAD);
        government.setPopulation(government.getPopulation() + leftOver);
        GameMenuView.output("success");

    }


    public static void showFoodList() {
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
        currentPlayer.setFearRate(rate);
        GameMenuView.output("success");
        //System.out.println(currentPlayer.getFearRate());

    }

    public static void setCurrentUnits(ArrayList<Unit> currentUnits) {
        GameMenuController.currentUnits = currentUnits;
    }

    public static boolean dropBuilding(int X, int Y, Building type) {
        boolean useResource = false;
        MapCell mapCell;
        if (type == null) {
            GameMenuView.output("incorrectBuildingType");
            return false;
        }
        if (Map.getInstanceMap().validMapCell(X, Y)) {
            mapCell = Map.getInstanceMap().getMapCell(X, Y);
        } else {
            GameMenuView.output("invalidLocation");
            currentPlayer.removeBuilding(type);
            return false;
        }

        if (Map.getInstanceMap().getMapCell(X, Y).getBuilding() != null) {
            GameMenuView.output("prebuilding");
            currentPlayer.removeBuilding(type);
            return false;
        } else if (!type.getBuildingType().equals(CastleType.DRAWBRIDGE) && mapCell.getTexture().getColor().equals("BLUE")) {
            GameMenuView.output("textureProblem");
            currentPlayer.removeBuilding(type);
            return false;

        } else if (type.getClass().getSimpleName().equals("ResourceMaker")
                && !((ResourceMaker) type).checkTexture(mapCell.getTexture())) {
            GameMenuView.output("textureProblem");
            currentPlayer.removeBuilding(type);
            return false;
        } else if (type.getBuildingType().equals(StorageType.STOCK_PILE) &&
                !Map.getInstanceMap().isBuildingNear(X, Y, type.getBuildingType()) &&
                !Map.getInstanceMap().isBuildingNear(X, Y, CastleType.Ruler)) {

            GameMenuView.output("nearBuilding", (Object) "stockPile or ruler");
            currentPlayer.removeBuilding(type);
            return false;
        } else if (currentPlayer.getBuildingNum(type.getBuildingType()) != 1 && type.getBuildingType().equals(StorageType.FOOD_STOCK_PILE) && !Map.getInstanceMap().isBuildingNear(X, Y, type.getBuildingType())) {
            GameMenuView.output("nearBuilding", (Object) type.getBuildingType().getRegex());
            currentPlayer.removeBuilding(type);
            return false;
        } else if (type.getBuildingType().equals(CastleType.STAIR) &&
                !Map.getInstanceMap().isBuildingNear(X, Y, CastleType.SHORT_WALL) &&
                !Map.getInstanceMap().isBuildingNear(X, Y, CastleType.THICK_WALL) &&
                !Map.getInstanceMap().isBuildingNear(X, Y, CastleType.SMALL_STONE_GATEHOUSE) &&
                !Map.getInstanceMap().isBuildingNear(X, Y, CastleType.BIG_STONE_GATEHOUSE)) {
            GameMenuView.output("nearBuilding", (Object) "wall or gate");
            currentPlayer.removeBuilding(type);
            return false;
        } else if (type.getBuildingType().equals(ConverterType.Ox_TETHER) && !Map.getInstanceMap().isBuildingNear(X, Y, ResourceMakerType.QUARRY)) {
            GameMenuView.output("nearBuilding", (Object) ResourceMakerType.QUARRY.getRegex());
            currentPlayer.removeBuilding(type);
            return false;
        } else if (useResource) {
            if (!type.haveEnoughResource(currentPlayer)) {
                GameMenuView.output("lackOfResource");
                currentPlayer.removeBuilding(type);
                return false;
            }
        } else {
            mapCell.setBuilding(type);
            Map.getInstanceMap().getMapCell(X, Y).setPassable(false);
            GameMenuView.output("buildingDrop");
            return true;
        }
        return false;
    }
    public static void selectBuilding(int X, int Y) {
        System.out.println(currentPlayer.getColor());
        System.out.println(Map.getInstanceMap().getMapCell(X, Y).getBuilding().getOwnership().getColor());

        Building building = Map.getInstanceMap().getMapCell(X, Y).getBuilding();
        if (building == null) {
            GameMenuView.output("noBuildingAvailable");
        } else if (!building.getOwnership().equals(currentPlayer)) {
            GameMenuView.output("notyourtroop");
        } else {
            currentBuilding = building;
            setSelectedBuildingX(X);
            setSelectedBuildingY(Y);
            GameMenuView.output("buildingSelected");
        }
    }

    public static boolean createUnit(String type, int count) {
        if (currentBuilding == null){
            GameMenuView.output("selectBuilding");
            return false;

        }
        else if (currentBuilding.getBuildingType().equals(ConverterType.MERCENARY_POST)){
            if(FighterEnum.getFighterType(type)!=null) {
                if (FighterEnum.getFighterType(type).isArab()) {
                    if (currentPlayer.getBalance() < count * FighterEnum.getFighterType(type).getPrice()) {
                        GameMenuView.output("balanceError");
                        return false;
                    } else {
                        Fighter fighter = new Fighter(FighterEnum.getFighterType(type));
                        Unit unit = new Unit(selectedBuildingX, selectedBuildingY, fighter, count);
                        currentPlayer.setBalance(currentPlayer.getBalance() - count * FighterEnum.getFighterType(type).getPrice());
                        Map.getInstanceMap().getMapCell(getSelectedBuildingX(), getSelectedBuildingY()).getUnits().add(unit);
                        currentPlayer.setPopulation(currentPlayer.getPopulation()-count);
                        currentPlayer.getUnits().add(unit);
                        GameMenuView.output("success");
                        return true;
                    }
                }
            }
            else if(LongRangedEnum.getLongRangedType(type)!=null) {
           if (LongRangedEnum.getLongRangedType(type).isArab()) {
                    if (currentPlayer.getBalance() < count * LongRangedEnum.getLongRangedType(type).getPrice()) {
                        GameMenuView.output("balanceError");
                        return false;
                    } else {
                        LongRanged fighter = new LongRanged(LongRangedEnum.getLongRangedType(type));
                        Unit unit = new Unit(selectedBuildingX, selectedBuildingY, fighter, count);
                        currentPlayer.setBalance(currentPlayer.getBalance() - count * LongRangedEnum.getLongRangedType(type).getPrice());
                        currentPlayer.setPopulation(currentPlayer.getPopulation()-count);
                        currentPlayer.getUnits().add(unit);
                        Map.getInstanceMap().getMapCell(getSelectedBuildingX(), getSelectedBuildingY()).getUnits().add(unit);
                        GameMenuView.output("success");
                        return true;
                    }

                }
            }else{
              GameMenuView.output("notArab");
              return false;
            }
            
        }
        else if (currentBuilding.getBuildingType().equals(ConverterType.BARRACKS)){
            if(type.equals("blackMonk")){
                GameMenuView.output("blackMonk");
                return false;
            }
            if(FighterEnum.getFighterType(type)!=null) {
                if (!FighterEnum.getFighterType(type).isArab()) {
                    if (currentPlayer.getBalance() < count * FighterEnum.getFighterType(type).getPrice()) {
                        GameMenuView.output("balanceError");
                        if (FighterEnum.getFighterType(type).getResource() != null && currentPlayer.getResourcesMap().get(FighterEnum.getFighterType(type).getResource()) < count) {
                            GameMenuView.output("resourceError");
                        }
                        return false;

                    } else {
                        Fighter fighter = new Fighter(FighterEnum.getFighterType(type));
                        Unit unit = new Unit(selectedBuildingX, selectedBuildingY, fighter, count);
                        if (FighterEnum.getFighterType(type).getResource() != null) {
                            currentPlayer.getResourcesMap().put(FighterEnum.getFighterType(type).getResource(), currentPlayer.getResourcesMap().get(FighterEnum.getFighterType(type).getResource()) - count);
                        }
                        currentPlayer.setBalance(currentPlayer.getBalance() - count * FighterEnum.getFighterType(type).getPrice());
                        Map.getInstanceMap().getMapCell(getSelectedBuildingX(), getSelectedBuildingY()).getUnits().add(unit);
                        currentPlayer.setPopulation(currentPlayer.getPopulation() - count);
                        currentPlayer.getUnits().add(unit);
                        GameMenuView.output("success");
                        return true;

                    }
                }
            }else if(LongRangedEnum.getLongRangedType(type)!=null) {
                if (!LongRangedEnum.getLongRangedType(type).isArab()) {
                    if (currentPlayer.getBalance() < count * LongRangedEnum.getLongRangedType(type).getPrice()) {
                        GameMenuView.output("balanceError");
                        if (LongRangedEnum.getLongRangedType(type).getResource() != null && currentPlayer.getResourcesMap().get(LongRangedEnum.getLongRangedType(type).getResource()) < count) {
                            GameMenuView.output("resourceError");
                        }
                        return false;

                    } else {
                        LongRanged fighter = new LongRanged(LongRangedEnum.getLongRangedType(type));
                        Unit unit = new Unit(selectedBuildingX, selectedBuildingY, fighter, count);
                        if (FighterEnum.getFighterType(type).getResource() != null) {
                            currentPlayer.getResourcesMap().put(LongRangedEnum.getLongRangedType(type).getResource(), currentPlayer.getResourcesMap().get(LongRangedEnum.getLongRangedType(type).getResource()) - count);
                        }
                        currentPlayer.setBalance(currentPlayer.getBalance() - count * LongRangedEnum.getLongRangedType(type).getPrice());
                        Map.getInstanceMap().getMapCell(getSelectedBuildingX(), getSelectedBuildingY()).getUnits().add(unit);
                        currentPlayer.setPopulation(currentPlayer.getPopulation() - count);
                        currentPlayer.getUnits().add(unit);
                        GameMenuView.output("success");
                        return true;
                    }
                }
            }
        } else if (currentBuilding.getBuildingType().equals(DevelopmentType.CHURCH) ||
                currentBuilding.getBuildingType().equals(DevelopmentType.CATHEDRAL)) {
            if(type.equals("blackMonk")){
                if(FighterEnum.blackMonk.getPrice()*count> currentPlayer.getBalance()){
                    GameMenuView.output("balanceError");
                    return false;
                }
                Fighter fighter =new Fighter(FighterEnum.blackMonk);
                Unit unit=new Unit(getSelectedBuildingX(),getSelectedBuildingY(),fighter,count);
                Map.getInstanceMap().getMapCell(getSelectedBuildingX(),getSelectedBuildingY()).getUnits().add(unit);
                currentPlayer.setPopulation(currentPlayer.getPopulation()-count);
                currentPlayer.getUnits().add(unit);
                GameMenuView.output("success");
                return true;
            }else{
                GameMenuView.output("notblackMonk");
                return false;
            }

        } else if(currentBuilding.getBuildingType().equals(StorageType.ENGINEER_GUILD) ){
            if (UnarmedEnum.getUnarmedType(type) != null) {
                if (currentPlayer.getBalance() < count * UnarmedEnum.getUnarmedType(type).getPrice()) {
                    GameMenuView.output("balanceError");
                    return false;
                } else {
                    Unarmed fighter = new Unarmed(UnarmedEnum.getUnarmedType(type));
                    Unit unit = new Unit(selectedBuildingX, selectedBuildingY, fighter, count);
                    currentPlayer.setBalance(currentPlayer.getBalance() - count * UnarmedEnum.getUnarmedType(type).getPrice());
                    Map.getInstanceMap().getMapCell(getSelectedBuildingX(), getSelectedBuildingY()).getUnits().add(unit);
                    currentPlayer.setPopulation(currentPlayer.getPopulation() - count);
                    currentPlayer.getUnits().add(unit);
                    if (UnarmedEnum.getUnarmedType(type).equals(UnarmedEnum.engineer)) currentPlayer.addResources(ENGINEER, count, false);
                    else if (UnarmedEnum.getUnarmedType(type).equals(UnarmedEnum.worker)) currentPlayer.addResources(WORKER, count, false);

                    GameMenuView.output("success");
                    return true;
                }
            }
        }else {
            GameMenuView.output("selectUnitBuilding");
            return false;
        }
        return false;

    }



    public static void repair() {
        if (currentBuilding == null) {
            GameMenuView.output("selectBuilding");
        } else if (!currentBuilding.getClass().getSimpleName().equals("Castle")){
            GameMenuView.output("incorrectBuildingType");
        } else if (Map.isSoldierNear(getSelectedBuildingX(), getSelectedBuildingY(), currentPlayer)) {
            GameMenuView.output("nearSoldier");
        } else {
            Castle castle = (Castle) currentBuilding;
            castle.repair();
            GameMenuView.output("success");
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
    public static void selectTool(int X, int Y) {
        if (Map.getInstanceMap().getMapCell(X, Y).getTool()==null) {
            GameMenuView.output("toolError");
        } else if(!Map.getInstanceMap().getMapCell(X, Y).getTool().getOwner().equals(currentPlayer)){
            GameMenuView.output("notyourtool");

        }else {
            setCurrentTool( Map.getInstanceMap().getMapCell(X, Y).getTool());

            GameMenuView.output("success");
        }
    }
    public static void actionTool(int X,int Y){
        if (Map.getInstanceMap().getMapCell(X, Y).getTool()==null) {
            GameMenuView.output("toolError");
        }else if(!Map.getInstanceMap().getMapCell(X, Y).getTool().getOwner().equals(currentPlayer)){
            GameMenuView.output("notyourtool");

        }else if(currentTool.getName().equals("catapult")||currentTool.getName().equals("bigCatapult")){
            Map.getInstanceMap().getMapCell(X,Y).setBuilding(null);
            Map.getInstanceMap().getMapCell(X,Y).setPassable(true);
            for (Unit unit : Map.getInstanceMap().getMapCell(X, Y).getUnits()) {
                unit.setCount(0);
            }
            GameMenuView.output("success");

        }else if(currentTool.getName().equals("fireThrower")){
            for (Unit unit : Map.getInstanceMap().getMapCell(X, Y).getUnits()) {
                unit.setCount(0);
            }
            Map.getInstanceMap().getMapCell(X,Y).setTool(currentTool);
            currentTool.setX(X);
            currentTool.setY(Y);
            Map.getInstanceMap().getMapCell(currentTool.getX(),currentTool.getY()).setTool(null);

            GameMenuView.output("success");
        }else if(currentTool.getName().equals("battleRam")){
            Map.getInstanceMap().getMapCell(X,Y).setBuilding(null);Map.getInstanceMap().getMapCell(X,Y).setPassable(true);
        Map.getInstanceMap().getMapCell(X,Y).setTool(currentTool);
        currentTool.setX(X);
        currentTool.setY(Y);
        Map.getInstanceMap().getMapCell(currentTool.getX(),currentTool.getY()).setTool(null);

        GameMenuView.output("success");
    }
    }

    public static ArrayList<Unit> getCurrentUnits() {
        return currentUnits;
    }

    public static void moveUnitTo(int X, int Y) {
        if ( !Map.getInstanceMap().getMapCell(X, Y).isPassable()) {
            GameMenuView.output("waterError");
        }
        else if(!NavigatorController.isAbleToNavigate(NavigatorController.mapPassable(),currentUnits.get(0).getX(),currentUnits.get(0).getY(),X,Y)){
            GameMenuView.output("waterError");
        }else {

            for (Unit unit : currentUnits) {
                if (Map.getInstanceMap().getMapCell(X, Y).getBuilding() != null){
                    if (Map.getInstanceMap().getMapCell(X, Y).getBuilding() instanceof Castle){
                        Castle castle = (Castle) Map.getInstanceMap().getMapCell(X, Y).getBuilding();
                        if (castle.getSize() > unit.getCount()) {
                            Map.getInstanceMap().getMapCell(X, Y).addUnit(unit);
                            castle.setSize(castle.getSize() - unit.getCount());
                        }
                    }
                } else {
                    Map.getInstanceMap().getMapCell(X, Y).addUnit(unit);
                }

            }
            Map.getInstanceMap().getMapCell(currentUnits.get(0).getX(), currentUnits.get(0).getY()).getUnits().clear();
            Circle circle=new Circle(5);
            circle.toFront();
            circle.setCenterX(20*Map.getInstanceMap().getMapCell(currentUnits.get(0).getX(),currentUnits.get(0).getY()).getX()+10);
            circle.setCenterY(20*Map.getInstanceMap().getMapCell(currentUnits.get(0).getX(),currentUnits.get(0).getY()).getY()+10);
            sampleView.getMapCellNodeHashMap().get(Map.getInstanceMap().getMapCell(currentUnits.get(0).getX(),currentUnits.get(0).getY())).toFront();
            sampleView.getMapCellNodeHashMap().get(Map.getInstanceMap().getMapCell(currentUnits.get(0).getX(),currentUnits.get(0).getY())).getChildren().add(circle);
            NavigatorController.path(currentUnits.get(0).getX(),currentUnits.get(0).getY(),X,Y,NavigatorController.findShortestPath(NavigatorController.mapPassable(),currentUnits.get(0).getX(),currentUnits.get(0).getY(),X,Y,currentUnits.get(0).getPeople().getSpeed()*10),circle);

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
        int range=0;
        if(state.equals(State.OFFENSIVE))
            range=4;
        else if (state.equals(State.DEFENSIVE))
            range=2;

        setCurrentUnits( Map.getInstanceMap().getMapCell(X, Y).getUnits());
        for(int i=X-range;i<X+range;i++){
            for(int j=Y-range;j<Y+range;j++){
                if(Map.getInstanceMap().getMapCell(i,j).getUnits().size()!=0) {
                    if (!Map.getInstanceMap().getMapCell(i, j).getUnits().get(0).getPeople().getOwner().equals(Map.getInstanceMap().getMapCell(X, Y).getUnits().get(0).getPeople().getOwner())){

                        for (Unit unit : Map.getInstanceMap().getMapCell(i, j).getUnits()) {
                            unit.setCount(0);
                        }

                }

                }



            }
        }


        GameMenuView.output("success");
    }

    public static void attackEnemy(int enemyX, int enemyY) {
        //command: attack -e [enemy’s x] [enemy’
        //buildings attack

        if (!currentUnits.get(0).isInRange(enemyX, enemyY)) {
            GameMenuView.output("enemyBond");
            return;
        } else if (Map.getInstanceMap().getMapCell(enemyX, enemyY).getUnits().size() == 0&&Map.getInstanceMap().getMapCell(enemyX, enemyY).getBuilding()==null) {
            GameMenuView.output("enemyError");
            return;

        } else if(Map.getInstanceMap().getMapCell(enemyX, enemyY).getUnits().get(0).getPeople().getOwner().equals(currentPlayer)){
            GameMenuView.output("ally");
            return;

        }else {
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

                for (Unit unit : Map.getInstanceMap().getMapCell(enemyX, enemyY).getUnits()) {
                    unit.setCount(0);
                }
                for (Unit unit : Map.getInstanceMap().getMapCell(currentUnits.get(0).getX(), currentUnits.get(0).getY()).getUnits()) {
                    unit.setCount(0);
                }
                if(Map.getInstanceMap().getMapCell(enemyX, enemyY).getBuilding()!=null){
                    Map.getInstanceMap().getMapCell(enemyX, enemyY).getBuilding().setHealth(Map.getInstanceMap().getMapCell(enemyX, enemyY).getBuilding().getHealth()-allyOffense);

                }

                GameMenuView.output("fightLoss");
                GameMenuView.output("fightWin");


            } else if (allyOffense >= enemyDefense) {

                for (Unit unit : Map.getInstanceMap().getMapCell(enemyX, enemyY).getUnits()) {
                    unit.setCount(0);
                }


                if(Map.getInstanceMap().getMapCell(enemyX, enemyY).getBuilding()!=null){
                    Map.getInstanceMap().getMapCell(enemyX, enemyY).getBuilding().setHealth(Map.getInstanceMap().getMapCell(enemyX, enemyY).getBuilding().getHealth()-allyOffense);

                }
                GameMenuView.output("fightWin");


            } else {
                for (Unit unit : Map.getInstanceMap().getMapCell(currentUnits.get(0).getX(), currentUnits.get(0).getY()).getUnits()) {
                    unit.setCount(0);
                }
                GameMenuView.output("fightLoss");


            }

        }

    }

    public static void airAttack(int X, int Y) {
        int longRanged = 0;
        if (Map.getInstanceMap().getMapCell(X, Y).getUnits().size() == 0) {
            if(Map.getInstanceMap().getMapCell(X,Y).getBuilding()!=null){
                if(Map.getInstanceMap().getMapCell(X,Y).getBuilding().getOwnership().equals(currentPlayer)){
                    GameMenuView.output("enemyError");
                    return;
                }
            }else{
                GameMenuView.output("enemyError");
                return;
            }


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
                if(!unit1.isHasAirDefense()) {

                    int j = longRanged - unit1.getCount();
                    unit1.setCount(Math.max(0, unit1.getCount() - longRanged));
                    longRanged = j;
                }



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
            Map.getInstanceMap().getMapCell(X,Y).setBuilding(null);

            GameMenuView.output("success");
            return;

        }
        GameMenuView.output("tunnelerError");
    }

    public static void build(String equipmentName) {
        if(equipmentName.equals("airDefense")){
            buildUnitAirDefense(currentUnits.get(0).getX(),currentUnits.get(0).getY());
        } else if(equipmentName.equals("seigeTower")){
            buildSiegeTower(currentUnits.get(0).getX(),currentUnits.get(0).getY());
        } else if(equipmentName.equals("fireThrower")){
            buildFireThrower(currentUnits.get(0).getX(),currentUnits.get(0).getY());
        }else if(equipmentName.equals("catapult")){
            buildCatapult(currentUnits.get(0).getX(),currentUnits.get(0).getY());
        }else if(equipmentName.equals("bigCatapult")){
            buildBigCatapult(currentUnits.get(0).getX(),currentUnits.get(0).getY());
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



    public static void setTexture(int X, int Y, Texture type) throws FileNotFoundException {
        if (!Map.getInstanceMap().validMapCell(X, Y)) {
            GameMenuView.output("invalidLocation");
        } else if (Map.getInstanceMap().getMapCell(X, Y).getBuilding() != null) {
            GameMenuView.output("buildingPlaced");
        } else {
            Map.getInstanceMap().getMapCell(X, Y).setTexture(type);
            if(type.equals(Texture.SEA)||type.equals(Texture.RIVER)||type.equals(Texture.BIG_POND)||type.equals(Texture.SMALL_POND)||type.equals(Texture.SHALLOW_LAKE)||type.equals(Texture.OIL)){
                sampleView.getBackgroundTexture(X,Y).setFill(new ImagePattern(new Image(new FileInputStream("src/main/java/stronghold/database/Image/tiles/water.jpg"))));
            }else{
                sampleView.getBackgroundTexture(X,Y).setFill(new ImagePattern(new Image(new FileInputStream("src/main/java/stronghold/database/Image/tiles/grass.jpg"))));

            }
            if(type.equals(Texture.SEA)||type.equals(Texture.RIVER)||type.equals(Texture.BIG_POND)||type.equals(Texture.SMALL_POND)||type.equals(Texture.STONE)){
                Map.getInstanceMap().getMapCell(X, Y).setPassable(false);

            }
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
                if(type.equals("sea")||type.equals("river")||type.equals("big pound")||type.equals("small pound")||type.equals("sttone")){
                    Map.getInstanceMap().getMapCell(i, j).setPassable(false);
                    try {
                        sampleView.getBackgroundTexture(i,j).setFill(new ImagePattern(new Image(new FileInputStream("src/main/java/stronghold/database/Image/tiles/water.jpg"))));
                    } catch (FileNotFoundException e) {
                        throw new RuntimeException(e);
                    }

                }
                try {
                    sampleView.getBackgroundTexture(i,j).setFill(new ImagePattern(new Image(new FileInputStream("src/main/java/stronghold/database/Image/tiles/grass.jpg"))));
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                }

            }
        }
        GameMenuView.output("textureSet");
    }//

    public static void clear(int X, int Y) {//
        // Map.getMapCell(X,Y).setTexture();default texture
        Map.getInstanceMap().getMapCell(X, Y).setBuilding(null);
        Map.getInstanceMap().getMapCell(X, Y).setPassable(true);
        Map.getInstanceMap().getMapCell(X, Y).getUnits().clear();
        Map.getInstanceMap().getMapCell(X, Y).setRockDirection(null);
        Map.getInstanceMap().getMapCell(X, Y).setTree(null);
        try {
            sampleView.getBackgroundTexture(X,Y).setFill(new ImagePattern(new Image(new FileInputStream("src/main/java/stronghold/database/Image/tiles/grass.jpg"))));
        } catch (FileNotFoundException e) {

        }
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
            try {
                sampleView.getBackgroundTexture(X,Y).setFill(new ImagePattern(new Image(new FileInputStream("src/main/java/stronghold/database/Image/tiles/rock.jpg"))));

            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }

            Map.getInstanceMap().getMapCell(X, Y).setRockDirection(Direction.getRandom());
            GameMenuView.output("rockDrop");
        } else {
            Map.getInstanceMap().getMapCell(X, Y).setRockDirection(direction);
            Map.getInstanceMap().getMapCell(X,Y).setPassable(false);
            try {
                sampleView.getBackgroundTexture(X,Y).setFill(new ImagePattern(new Image(new FileInputStream("src/main/java/stronghold/database/Image/tiles/rock.jpg"))));

            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
            GameMenuView.output("rockDrop");
        }
    }//

    public static void dropTree(int X, int Y, Tree type) throws FileNotFoundException {
        if(!Map.getInstanceMap().getMapCell(X,Y).isPassable()){
            GameMenuView.output("sthIsHere");
        }

        else if (type == null) {
            GameMenuView.output("invalidType");
        } else if(Map.getInstanceMap().getMapCell(X,Y).getBuilding()!=null||Map.getInstanceMap().getMapCell(X,Y).getRockDirection()!=null||Map.getInstanceMap().getMapCell(X,Y).getTree()!=null){
            GameMenuView.output("sthIsHere");
        }else {
            Map.getInstanceMap().getMapCell(X, Y).setTree(type);
            Map.getInstanceMap().getMapCell(X,Y).setPassable(false);
            sampleView.getBackgroundTexture(X,Y).setFill(new ImagePattern(new Image(new FileInputStream("src/main/java/stronghold/database/Image/tiles/tree.jpg"))));
            GameMenuView.output("success");
        }
    }//

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
            System.out.println(Map.getInstanceMap().getMapCell(X,Y).getUnits().get(0));
            MapMenuController.showMapCellDetails(X,Y);

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


    }


    public static void enterShopMenu() {
        if(currentBuilding==null){
            GameMenuView.output("shopError");
            return;

        }
        if (currentBuilding.getRegex().equals("post")) {
           /* Scanner scanner = new Scanner(System.in);
            GameMenuView.output("enterShopMenu");
            ShopMenuView.run(scanner);*/
            shopNewStage = new Stage();
            shopNewStage.setScene(new Scene(
                    ShopMenuView.gettetet()));
            shopNewStage.show();
        } else {
            GameMenuView.output("shopError");
            return;
        }

    }
    private static Stage shopNewStage;

    public static Stage getShopNewStage() {
        return shopNewStage;
    }

    public static void enterMapMenu() {
        Scanner scanner = new Scanner(System.in);
        GameMenuView.output("enterMapMenu");
        MapMenuView.run(scanner);

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
    private static int foodPopularity=0;
    private static int taxPopularity=0;


    //private static int fearPopularity=currentPlayer.getFearRate();
    private static int fearPopularity=10;
   // private static int religionPopularity=(currentPlayer.getBuildingHash().get(DevelopmentType.CHURCH) * 2 + currentPlayer.getBuildingHash().get(DevelopmentType.CATHEDRAL) * 2);
   private static int religionPopularity=-10;
    public static int getFearPopularity() {
        return fearPopularity;
    }

    public static void setFearPopularity(int fearPopularity) {
        GameMenuController.fearPopularity = fearPopularity;
    }

    public static int getFoodPopularity() {
        return foodPopularity;
    }

    public static int getReligionPopularity() {
        return religionPopularity;
    }

    public static int getTaxPopularity() {
        return taxPopularity;
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
        foodPopularity=i-1;
        currentPlayer.setPopularity(currentPlayer.getPopularity() - (-4 * currentPlayer.getFoodRate()));
        if (currentPlayer.getTaxRate() <= 0) {
            currentPlayer.setPopularity(currentPlayer.getPopularity() + ((-2 * currentPlayer.getTaxRate()) + 1));
            taxPopularity=((-2 * currentPlayer.getTaxRate()) + 1);
        } else if (currentPlayer.getTaxRate() > 0 && currentPlayer.getTaxRate() <= 4) {
            currentPlayer.setPopularity(currentPlayer.getPopularity() - (2 * currentPlayer.getTaxRate()));
            taxPopularity= - (2 * currentPlayer.getTaxRate());

        } else {
            currentPlayer.setPopularity(currentPlayer.getPopularity() - (12 + ((currentPlayer.getTaxRate() - 5) * 4)));
            taxPopularity= - (12 + ((currentPlayer.getTaxRate() - 5) * 4));

        }
        currentPlayer.setPopularity(currentPlayer.getPopularity() + (currentPlayer.getBuildingHash().get(DevelopmentType.CHURCH) * 2 + currentPlayer.getBuildingHash().get(DevelopmentType.CATHEDRAL) * 2));
        currentPlayer.setPopularity(currentPlayer.getPopularity() + currentPlayer.getFearRate());
    }

    private static void foodLogic() {
        double n= currentPlayer.getPopulation();
        if(currentPlayer.getUnits()!=null) {
            for (Unit unit : currentPlayer.getUnits()) {
                n += unit.getCount();
            }
        }

        if (currentPlayer.getResourcesNum(MEAT) + currentPlayer.getResourcesNum(CHEESE) + currentPlayer.getResourcesNum(APPLE) + currentPlayer.getResourcesNum(BREAD) == 0) {
            currentPlayer.setFoodRate(-2);
            return;
        } else if (currentPlayer.getResourcesNum(MEAT) + currentPlayer.getResourcesNum(CHEESE) + currentPlayer.getResourcesNum(APPLE) + currentPlayer.getResourcesNum(BREAD) < n* ((currentPlayer.getFoodRate() + 2) * 0.5)) {
            currentPlayer.setFoodRate(-2);
            return;

        } else {


            double neededFood = n * ((currentPlayer.getFoodRate() + 2) * 0.5);
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
        if (currentPlayer.getBalance() == 0) {
            currentPlayer.setTaxRate(0);
            return;
        }
        double n= currentPlayer.getPopulation();
        if(currentPlayer.getUnits()!=null) {
            for (Unit unit : currentPlayer.getUnits()) {
                n += unit.getCount();
            }
        }
        if (currentPlayer.getTaxRate() < 0) {
            i = 1 - (-0.2 * (3 - currentPlayer.getTaxRate()));
            if (currentPlayer.getBalance() < n* i) {
                currentPlayer.setFoodRate(-2);
                return;

            }
            i *= -1;
        } else if (currentPlayer.getTaxRate() > 0) {
            i = 0.4 + (0.2 * currentPlayer.getTaxRate());

        }

            currentPlayer.setBalance(currentPlayer.getBalance() - n * i);


    }
    public static void endOfRoundBuildingAttacker(MapCell mapCell){
        if(mapCell.getBuilding()!=null) {
            if (CastleType.getCastleType(mapCell.getBuilding().getRegex()) != null) {
                Castle castle = (Castle) mapCell.getBuilding();
                for (int i = -castle.getFireRange(); i < castle.getFireRange(); i++) {
                    for (int j = -castle.getFireRange(); j < castle.getFireRange(); j++) {
                        ArrayList<Unit> units = new ArrayList<>();
                        for (Unit unit : Map.getInstanceMap().getMapCell(i, j).getUnits()) {
                            if (!unit.getPeople().getOwner().equals(castle.getOwnership())) {
                                unit.setCount(Math.max(0, unit.getCount() - 10));
                                if (unit.getCount() > 0) {
                                    units.add(unit);

                                }
                            }
                        }
                        Map.getInstanceMap().getMapCell(i, j).getUnits().clear();
                        for (Unit unit : units) {
                            Map.getInstanceMap().getMapCell(i, j).getUnits().add(unit);
                        }


                    }
                }

            }
        }

    }

    public static void buildUnitAirDefense(int x, int y) {
        Unit unit1 = Map.getInstanceMap().getUnarmed(Map.getInstanceMap().getMapCell(currentUnits.get(0).getX(), currentUnits.get(0).getY()), "engineer");
        if (unit1 != null) {
            if (unit1.getCount() >= 1) {
                Tool tool = new Tool(1, "airDefense", unit1.getX(), unit1.getY(), false);
                for (Unit unit : Map.getInstanceMap().getMapCell(x, y).getUnits()) {
                    unit.setTool(tool);
                    unit.setHasAirDefense(true);

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
    public static void buildSiegeTower(int x, int y) {

        Unit unit1 = Map.getInstanceMap().getUnarmed(Map.getInstanceMap().getMapCell(currentUnits.get(0).getX(), currentUnits.get(0).getY()), "engineer");
        if (unit1 != null) {
            if( unit1.getCount() >= 4){
                Tool tool = new Tool(4, "siegeTower", unit1.getX(), unit1.getY(), true);
                Map.getInstanceMap().getMapCell(x, y).setTool(tool);
                GameMenuView.output("success");
                //TODO: wall break

            } else {
                GameMenuView.output("invalidEngNum");
            }}


    }

    public static void captureGate(){
        Boolean er = true;
        if (currentBuilding == null){
            GameMenuView.output("selectBuilding");
        } else if (currentUnits == null) {
            GameMenuView.output("wrongSoldierType");
        }
        for (Unit currentUnit : currentUnits) {
            if (currentUnit.getPeople() instanceof Fighter){
                Fighter fighter = (Fighter) currentUnit.getPeople();
                if (fighter.getFighterEnum().equals(FighterEnum.assassins)){
                    er = false;
                }
            }

            if (currentUnit.getPeople() instanceof Unarmed){
                Unarmed unarmed = (Unarmed) currentUnit.getPeople();
                if (unarmed.getUnarmedEnum().equals(UnarmedEnum.ladderMen)){
                    er = false;
                }
            }


        }

        if (er) {
            GameMenuView.output("wrongSoldierType");
        }else if(currentBuilding.getBuildingType().equals(CastleType.SMALL_STONE_GATEHOUSE) ||
                currentBuilding.getBuildingType().equals(CastleType.BIG_STONE_GATEHOUSE) ) {
            Map.getInstanceMap().getMapCell(getSelectedBuildingX(), getSelectedBuildingY()).setPassable(true);
            moveUnitTo(getSelectedBuildingX(), getSelectedBuildingY());
            GameMenuView.output("Success");
        } else {
            GameMenuView.output("incorrectBuildingType");
        }
    }

    public static void moveToStair(){
        if (currentBuilding == null){
            GameMenuView.output("selectBuilding");
        } else if (currentUnits == null) {
            GameMenuView.output("wrongSoldierType");
        } else if (currentBuilding.getBuildingType().equals(CastleType.STAIR)) {
            for (int i = -1; i <= 1 ; i++) {
                for (int j = -1; j <= 1; j++) {
                    if (Map.getInstanceMap().validMapCell(getSelectedBuildingX() + i, getSelectedBuildingY() + j)){
                        Map.getInstanceMap().getMapCell(getSelectedBuildingX() + i, getSelectedBuildingY() + j).setPassable(true);
                    }
                }
            }
            moveUnitTo(getSelectedBuildingX(), getSelectedBuildingY());
            GameMenuView.output("Success");
        } else {
            GameMenuView.output("incorrectBuildingType");
        }
    }

    public static void moveToSiegeTent(int X, int Y){
        if (currentUnits == null) {
            GameMenuView.output("wrongSoldierType");
        }else if (Map.getInstanceMap().getMapCell(X, Y).getTool().getName().equals("siegeTower")) {
            Map.getInstanceMap().getMapCell(X, Y).setPassable(true);
            moveUnitTo(X, Y);
            GameMenuView.output("Success");
        } else {
            GameMenuView.output("incorrectBuildingType");
        }
    }



//    public static void main(String[] args) {
//        Map map = Map.getInstanceMap();
//        map.setSize(50);
//        Government government = new Government(4);
//        currentPlayer = government;
//        System.out.println(government.getResourcesMap().values());
//        System.out.println(government.getBalance());
//        dropBuilding(10, 20, new Converter(government, ConverterType.MILL));
//        //government.allBuildingActions();
//        System.out.println(government.getBuildingNum(ConverterType.MILL));
//        System.out.println(government.getResourcesMap().values());
//        //createUnit("agsg", 3);
//    }

    public static void main(String[] args) {




        Scanner s=new Scanner(System.in);
        Unarmed unarmed=new Unarmed(UnarmedEnum.engineer);
        Unit unit=new Unit(3,4, unarmed,5);
        currentUnits.add(unit);

        GameMenuView.run(s,2,2,200);





    }

//    public static void main(String[] args) {
//
//
//
//
//        Scanner s=new Scanner(System.in);
//        Unarmed unarmed=new Unarmed(UnarmedEnum.engineer);
//        Unit unit=new Unit(3,4, unarmed,5);
//        currentUnits.add(unit);
//
//        run(s,1,2,200);
//
//
//
//
//
//    }

//    public static void main(String[] args) {
//
//
//
//
//        Scanner s=new Scanner(System.in);
//        Unarmed unarmed=new Unarmed(UnarmedEnum.engineer);
//        Unit unit=new Unit(3,4, unarmed,5);
//        currentUnits.add(unit);
//
//        run(s,1,2,200);
//
//
//
//
//
//    }



}

