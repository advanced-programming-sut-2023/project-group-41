package stronghold.controller;


import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import stronghold.model.components.game.Map;
import stronghold.model.components.game.enums.Texture;
import stronghold.model.components.game.MapCell;
import stronghold.model.components.game.building.Castle;
import stronghold.view.MapMenuView;
import stronghold.view.ShopMenuView;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;
import java.util.regex.Matcher;

public class MapMenuController extends MenuController {
    private static Map map;
    private static int xCordinate;
    private static int yCordinate;
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
                mapShift(mapShift.group("x"),mapShift.group("y"),Integer.parseInt(mapShift.group(3)));
            } else if (( showMapCellDetails = getJSONRegexMatcher(command, "showMapCellDetails", menuRegexPatternsObject)).matches()) {
               showMapCellDetails(Integer.parseInt(showMapCellDetails.group("x")),Integer.parseInt(showMapCellDetails.group("y")));
            }   else {
                MapMenuView.output("invalid");
            }
        }

    }

    public static void showMap(int X, int Y) {
        if (X - 4 < 1) {
            X = 1;
        }
        if (Y - 2 < 0) {
            Y = 1;
        }
        xCordinate=X;
        yCordinate=Y;
        if (X - 4 >= 1 && Y - 2 >= 1) {
            X -= 4;
            Y -= 2;
        }

        int x = X;
        int y = Y;


        for (int i = 0; i <= 20; i++) {
            if (i % 4 == 0) {
                for (int j = 0; j <= 54; j++) {
                    System.out.print("-");
                }
                System.out.print("\n");

            } else if (i % 4 == 1) {
                for (int j = 0; j <= 54; j++) {
                    String color = map.getMapCell(x, y).getTexture().getColor();
                    if(color.equals("GREEN")){
                        System.out.print("\033[0;102m");
                    }
                    else if(color.equals("BLUE")){
                        System.out.print("\033[0;104m");
                    }
                    if (j % 6 == 0) {
                        System.out.print("\033[0m");
                        System.out.print("|");
                    } else if (j % 6 == 3) {
                        System.out.print(map.getMapCell(x, y).showMovingSoldier());
                        x++;
                    } else {
                        System.out.print(" ");
                    }
                    System.out.print("\033[0m");
                }
                System.out.print("\033[0m");
                System.out.print("\n");
                x = X;

            } else if (i % 4 == 2) {
                for (int j = 0; j <= 54; j++) {
                    String color = map.getMapCell(x, y).getTexture().getColor();
                    if(color.equals("GREEN")){
                        System.out.print("\033[0;102m");
                    }
                    else if(color.equals("BLUE")){
                        System.out.print("\033[0;104m");
                    }
                    if (j % 6 == 0) {
                        System.out.print("\033[0m");
                        System.out.print("|");
                    } else if (j % 6 == 3) {
                        System.out.print(Map.getMapCell(x, y).showBuilding());
                        x++;
                    } else {
                        System.out.print(" ");
                    }
                }
                System.out.print("\033[0m");
                System.out.print("\n");
                x = X;

            } else {
                for (int j = 0; j <= 54; j++) {
                    String color = map.getMapCell(x, y).getTexture().getColor();
                    if(color.equals("GREEN")){
                        System.out.print("\033[0;102m");
                    }
                    else if(color.equals("BLUE")){
                        System.out.print("\033[0;104m");
                    }
                    if (j % 6 == 0) {
                        System.out.print("\033[0m");
                        System.out.print("|");
                    } else if (j % 6 == 3) {
                        System.out.print(Map.getMapCell(x, y).showTree());
                        x++;
                    } else {
                        System.out.print(" ");
                    }
                    System.out.print("\033[0m");
                }
                System.out.print("\n");
                x = X;
                y++;

            }

        }


    }

    public static void mapShift(String direction,String direction2,int distance) {
        if(direction.equals(direction2)){
            MapMenuView.output("invalid");
            return;
        }

        if(direction.equals("up")||direction2.equals("up")){
            yCordinate+=distance;
        }
         if(direction.equals("down")||direction2.equals("down")){

             yCordinate-=distance;

         }
          if(direction.equals("down")||direction2.equals("down")){

              xCordinate+=distance;

          }

          if(direction.equals("down")||direction2.equals("down")){

              xCordinate-=distance;

          }
          if(xCordinate>Map.getSize()||xCordinate<0||yCordinate>Map.getSize()||yCordinate<0){
              MapMenuView.output("bondError");
              return;
          }
          showMap(xCordinate,yCordinate);

    }

    public static void showMapCellDetails(int X, int Y) {
        if(Map.getMapCell(X,Y)==null){
            MapMenuView.output("bondError");
            return;
        }
        //Resource too
        //System.out.println("Building: "+Map.getMapCell(X,Y).getBuilding());
        ///System.out.println("Texture: "+ Texture.Map.getMapCell(X,Y).getTexture());
        //System.out.println("Unit: "+Map.getMapCell(X,Y).getUnit()+" >>"+Map.getMapCell(X,Y).getUnit().getPeople().size());

    }

    public static void main(String[] args) {
        map = new Map(102);

        showMap(2,2);
    }
}
