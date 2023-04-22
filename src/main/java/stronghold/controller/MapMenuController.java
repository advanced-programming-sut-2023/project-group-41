package stronghold.controller;


import stronghold.model.components.game.Map;

public class MapMenuController {
    private static Map map;
    private static int xCordinate;
    private static int yCordinate;

    public void showMap(int X, int Y) {
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
                    if (j % 6 == 0) {
                        System.out.print("|");
                    } else if (j % 6 == 3) {
                        System.out.print(map.getMapCell(x, y).showMovingSoldier());
                        x++;
                    } else {
                        System.out.print(" ");
                    }
                }
                System.out.print("\n");
                x = X;

            } else if (i % 4 == 2) {
                for (int j = 0; j <= 54; j++) {
                    if (j % 6 == 0) {
                        System.out.print("|");
                    } else if (j % 6 == 3) {
                        System.out.print(Map.getMapCell(x, y).showBuilding());
                        x++;
                    } else {
                        System.out.print(" ");
                    }
                }
                System.out.print("\n");
                x = X;

            } else {
                for (int j = 0; j <= 54; j++) {
                    if (j % 6 == 0) {
                        System.out.print("|");
                    } else if (j % 6 == 3) {
                        System.out.print(Map.getMapCell(x, y).showTree());
                        x++;
                    } else {
                        System.out.print(" ");
                    }
                }
                System.out.print("\n");
                x = X;
                y++;

            }

        }


    }

    public void mapShift(String direction,String direction2,int distance) {
        if(direction.equals(direction2)){
            System.out.println("Invalid directions!");
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
              System.out.println("You are Out of bonds!");
              return;
          }
          showMap(xCordinate,yCordinate);

    }

    public void showMapCellDetails(int X, int Y) {
        if(Map.getMapCell(X,Y)==null){
            System.out.println("You are out of bonds!");
            return;
        }
        //Resource too
        //System.out.println("Building: "+Map.getMapCell(X,Y).getBuilding());
        //System.out.println("Texture: "+Map.getMapCell(X,Y).getTexture());
        //System.out.println("Unit: "+Map.getMapCell(X,Y).getUnit()+" >>"+Map.getMapCell(X,Y).getUnit().getPeople().size());

    }

}
