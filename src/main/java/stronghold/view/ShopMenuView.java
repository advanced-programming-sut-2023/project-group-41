package stronghold.view;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Popup;
import javafx.stage.Stage;
import stronghold.controller.GameMenuController;
import stronghold.controller.ShopMenuController;
import stronghold.model.components.game.Government;
import stronghold.model.components.game.enums.Resource;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.net.URL;
import java.util.Scanner;
import java.util.regex.Matcher;

import static stronghold.controller.ShopMenuController.*;



public class ShopMenuView extends Application {
    @FXML
    public static Stage stage;

    public static Stage getStage() {
        return stage;
    }

    private static String pathToRegexJSON = "src/main/java/stronghold/database/utils/regex/ShopMenuRegex.json";
    public static void run(Scanner scanner){
        Government currentGovernment = GameMenuController.getCurrentPlayer();

        setCurrentGovernment(GameMenuController.getCurrentPlayer());
        for(Resource resource:currentGovernment.getResourcesMap().keySet()){
            prices.put(resource,10);
        }
        JsonElement regexElement = null;
        try {
            regexElement = JsonParser.parseReader(new FileReader(pathToRegexJSON));
        } catch (
                FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        JsonObject menuRegexPatternsObject = regexElement.getAsJsonObject();
        while (true){
            String command = ShopMenuView.input(scanner).trim();
            Matcher buy;
            Matcher sell;


            if(command.matches("back")){
                ShopMenuView.output("back");
                return;
            } else if ((buy =getJSONRegexMatcher(command, "buy", menuRegexPatternsObject)).matches()) {
                //buy(Resource.getResource(buy.group("id")),Integer.parseInt(buy.group("int")));
            } else if ((sell=getJSONRegexMatcher(command, "sell", menuRegexPatternsObject)).matches()) {
                //sell(Resource.getResource(sell.group("id")),Integer.parseInt(sell.group("int")));
            } else if (( getJSONRegexMatcher(command, "showPriceList", menuRegexPatternsObject)).matches()) {
                showPriceList();
            }   else {
                ShopMenuView.output("invalid");
            }
        }

    }
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

    @Override
    public void start(Stage stage) throws Exception {
        this.stage=stage;
        Pane root=new Pane();
        Pane pane=new Pane();
        Label text=new Label("Please choose an item");
        text.setLayoutX(400);
        text.setLayoutY(200);
        text.setTextFill(Color.GREEN);
        text.setScaleY(3);
        text.setScaleX(3);
        root.getChildren().add(text);
        Button button3=new Button("TradeMenu");
        button3.setLayoutX(1000);
        button3.setLayoutY(500);
        button3.setOnAction(actionEvent -> {
            try {
                new TradeMenuView().start(ShopMenuView.getStage());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        root.getChildren().add(button3);


        int i=30;
        for (Resource resource : Resource.getResources()) {
            Button button=new Button(resource.getRegex());
            button.setLayoutX(800);
            button.setLayoutY(i);
            EventHandler<ActionEvent> event =
                    new EventHandler<ActionEvent>() {

                        public void handle(ActionEvent e)
                        {
                            text.setText(resource.getRegex());
                            TextField textField=new TextField();
                            textField.setText("1");
                            textField.setLayoutX(350);
                            textField.setLayoutY(230);
                            root.getChildren().add(textField);
                            textField.setPromptText("enter number you Want to sell");
                            Button button1=new Button("Buy");
                            button1.setLayoutX(350);
                            button1.setLayoutY(270);
                            Popup popup1=new Popup();
                            button1.setOnAction(actionEvent -> ShopMenuController.buy(resource,Integer.parseInt(textField.getText()),popup1));
                            root.getChildren().add(button1);

                            Button button2=new Button("Sell");
                            button2.setLayoutX(430);
                            button2.setLayoutY(270);
                            Popup popup2=new Popup();
                            button2.setOnAction(actionEvent -> ShopMenuController.sell(resource,Integer.parseInt(textField.getText()),popup2));
                            Label label=new Label("Buy: "+prices.get(resource)*(Integer.parseInt(textField.getText()))+"\nSell: "+0.8*prices.get(resource)*(Integer.parseInt(textField.getText())));

                            textField.setOnAction(actionEvent -> label.setText("Buy: "+prices.get(resource)*(Integer.parseInt(textField.getText()))+"\nSell: "+0.8*prices.get(resource)*(Integer.parseInt(textField.getText()))));

                            label.setLayoutX(600);
                            label.setLayoutY(250);
                            root.getChildren().add(button2);
                            root.getChildren().add(label);
                        }
                    };
            button.setOnAction(event);
            i+=30;
            root.getChildren().add(button);

        }
        Scene scene=new Scene(root);

        stage.setScene(scene);
        stage.show();

    }

    public static void main
            (String[]
                     args) {
        for(Resource resource:Resource.getResources()){
            prices.put(resource,10);
        }
        launch(args);
    }




}
