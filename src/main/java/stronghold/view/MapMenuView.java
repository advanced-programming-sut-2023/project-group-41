package stronghold.view;

import java.util.Scanner;

public class MapMenuView {
    public static void output(String code, Object... params){
        String pathToJSON = "src/main/java/stronghold/database/textcontent/MapMenuResponses.json";
        MenuView.output(pathToJSON, code, params);
    }

    public static String input(Scanner scanner){
        if(!scanner.hasNextLine())
            return "";
        String readLine = scanner.nextLine();
        return readLine;
    }
}
