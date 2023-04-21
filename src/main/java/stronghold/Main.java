package stronghold;

import stronghold.controller.SignUpMenuController;
import stronghold.view.MenuView;
import stronghold.view.MyJavaFXApp;
import stronghold.view.SignUpLoginView;

import java.util.Scanner;

public class Main {


    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);
        SignUpMenuController.run(scanner);
    }
}
