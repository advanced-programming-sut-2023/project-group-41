package stronghold.view;

import java.util.Scanner;

public class GameMenuView extends MenuView {
    public static void output(String code, Object... params){
        String pathToJSON = "src/main/java/stronghold/database/textcontent/MainGameResponses.json";
        MenuView.output(pathToJSON, code, params);
    }

    public static String input(Scanner scanner){
        if(!scanner.hasNextLine())
            return "";
        return scanner.nextLine();
    }

}