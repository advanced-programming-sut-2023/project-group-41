package stronghold.view;

import java.util.Scanner;

public class ShopMenuView {
    public static void output(String code, Object... params){
        String pathToJSON = "src/main/java/stronghold/database/textcontent/ShopMenuResponses.json";
        MenuView.output(pathToJSON, code, params);
    }

    public static String input(Scanner scanner){
        if(!scanner.hasNextLine())
            return "";
        String readLine = scanner.nextLine();
        return readLine;
    }
}
