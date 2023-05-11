package stronghold.view;

import stronghold.model.components.general.User;

import java.util.Scanner;

public class MainMenuView extends MenuView{
    
    public static void welcome(User currentUser){
        System.out.println("\u001B[45m\u001B[30mWELCOME, " + currentUser.getNickname() + "!\u001B[0m");
        
    }
    public static void output(String code, Object... params){
        String pathToJSON = "src/main/java/stronghold/database/textcontent/MainMenuResponses.json";
        MenuView.output(pathToJSON, code, params);
    }

    public static String input(Scanner scanner){
        if(!scanner.hasNextLine())
            return "";
        return scanner.nextLine();
    }
}
