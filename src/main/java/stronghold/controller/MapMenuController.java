package stronghold.controller;

import java.util.Map;

public class MapMenuController {
    private Map map;
    public void showMap(int X, int Y){ if(X-4<1) {
        X = 1;
    }
        if(Y-2<0){
            Y=1;
        }
        if(X-4>=1&&Y-2>=1){
            X-=4;
            Y-=2;
        }

        int x=X;
        int y=Y;
        for(int i=0;i<=20;i++){
            if(i%4==0){
                for(int j=0;j<=54;j++){
                    System.out.print("-");
                }
                System.out.print("\n");

            }
            else if(i%4==1){
                for(int j=0;j<=54;j++){
                    if(j%6==0) {
                        System.out.print("|");
                    }
                    else if(j%6==3) {
                        System.out.print();
                        x++;
                    }
                    else{
                        System.out.print(" ");
                    }
                }
                System.out.print("\n");
                x=X;

            }
            else if(i%4==2){
                for(int j=0;j<=54;j++){
                    if(j%6==0) {
                        System.out.print("|");
                    }
                    else if(j%6==3) {
                        System.out.print(x);
                        x++;
                    }
                    else{
                        System.out.print(" ");
                    }
                }
                System.out.print("\n");
                x=X;

            }
            else {
                for(int j=0;j<=54;j++){
                    if(j%6==0) {
                        System.out.print("|");
                    }
                    else if(j%6==3) {
                        System.out.print(x);
                        x++;
                    }
                    else{
                        System.out.print(" ");
                    }
                }
                System.out.print("\n");
                x=X;
                y++;

            }

        }


    }
    public void mapUpLeft(){

    }
    public void showMapDetails(int X, int Y) {

    }

}
