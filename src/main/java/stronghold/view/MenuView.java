package stronghold.view;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import stronghold.controller.MenuController;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Arrays;
import java.util.Scanner;

public class MenuView {
    public static void output(String pathToOutputJSON,String code, Object... params){
        try {
            JsonElement jsonElement = JsonParser.parseReader(new FileReader(pathToOutputJSON));
            String response = String.valueOf(jsonElement.getAsJsonObject().get(code));
            response = response.substring(1,response.length()-1);
            for (int i = 1; i <= Arrays.stream(params).count(); i++) {
                String toBeReplaced = "\\$VAR" + i + "\\$";
                response = response.replaceAll(toBeReplaced, (String) params[i - 1]);
            }
            response = response.replaceAll("\\\\n","\n");
            System.out.println(response);
        } catch (
                Error |
                FileNotFoundException e){
        }
    }

}
