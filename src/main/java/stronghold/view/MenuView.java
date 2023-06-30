package stronghold.view;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.stage.Popup;
import stronghold.controller.MenuController;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Arrays;
import java.util.Scanner;

public class MenuView {
    public static void output(String pathToOutputJSON,String code, Object... params) {
        try {
            JsonElement jsonElement = JsonParser.parseReader(new FileReader(pathToOutputJSON));
            String response = String.valueOf(jsonElement.getAsJsonObject().get(code));

            response = response.substring(1,response.length()-1);

            response = response.replaceAll("^Error!:", "\u001B[41m\u001B[30mError!\u001B[0m\u001B[31m:");
            response = response.replaceAll("^Success!:", "\u001B[42m\u001B[30mSuccess!\u001B[0m\u001B[32m:");
            response = response.replaceAll("\\\\n","\n");
            response = response.replaceAll("\\\\\"","\"");
            response = response.replaceAll("^(.+)(\\r\\n|[\\r\\n])","\u001B[47m\u001B[30m$1\u001B[0m\n");

            for (int i = 1; i <= Arrays.stream(params).count(); i++) {
                String toBeReplaced = "\\$VAR" + i + "\\$";
                response = response.replaceAll(toBeReplaced, (String) params[i - 1]);
            }

            response+="\u001B[0m";
            Popup popup=new Popup();
            popup.show(sampleView.stage);
            Rectangle rectangle=new Rectangle(500,300);
            rectangle.setX(370);
            rectangle.setY(100);
            rectangle.setFill(new ImagePattern(new Image(new FileInputStream("F:\\Stronghold\\project-group-41\\src\\main\\java\\stronghold\\database\\Image\\back2.jpg"))));
            popup.getContent().add(rectangle);
            Button back=new Button("Back");
            back.setLayoutX(600);
            back.setLayoutY(350);

            popup.getContent().add(back);
            back.setOnAction(actionEvent -> popup.hide());
            Label label=new Label(response);
            popup.getContent().add(label);
            label.setLayoutX(400);
            label.setLayoutY(200);
            label.setTextFill(Color.WHITE);
            System.out.println(response);

        } catch (
                Error |
                FileNotFoundException e){
        }
    }

}
