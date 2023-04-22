package stronghold.view;

import stronghold.controller.MainMenuController;

import java.util.Scanner;

public class MainMenuView extends MenuView {
    public static void output(String code, Object... params){
        String pathToJSON = "src/main/java/stronghold/database/textcontent/MainMenuResponses.json";
        MenuView.output(pathToJSON, code, params);
    }

    public static String input(Scanner scanner){
        if(!scanner.hasNextLine())
            return "";
        return scanner.nextLine();
    }

    public static void run(Scanner scanner){
        MainMenuController.run(scanner);
    }
}
