package stronghold.controller;

import stronghold.model.components.game.Government;
import stronghold.model.components.game.building.Converter;
import stronghold.model.components.game.building.Storage;
import stronghold.model.components.game.soldeirtype.Building;
import stronghold.model.components.game.soldeirtype.Map;
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


    public void showMap(int X, int Y){

    }
    public void mapUpLeft(){

    }
    public void showMapDetails(int X, int Y) {

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
