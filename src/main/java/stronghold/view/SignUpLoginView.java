package stronghold.view;

import stronghold.controller.MenuController;
import stronghold.controller.SignUpMenuController;

import java.util.Scanner;

public class SignUpLoginView extends MenuView{

    public static void output(String code, Object... params){
        String pathToJSON = "src/main/java/stronghold/database/textcontent/SignUpLoginMenuResponses.json";
        MenuView.output(pathToJSON, code, params);
    }

    public static String input(Scanner scanner){
        if(!scanner.hasNextLine())
            return "";
        String readLine = scanner.nextLine();
        return readLine;
    }
}
