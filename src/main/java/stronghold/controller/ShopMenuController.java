package stronghold.controller;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Popup;
import stronghold.model.components.game.Government;
import stronghold.model.components.game.enums.Resource;
import stronghold.model.components.general.User;
import stronghold.view.ShopMenuView;
import stronghold.view.sampleView;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Scanner;
import java.util.regex.Matcher;

public class ShopMenuController extends MenuController{
    private static Government currentGovernment;

    public static void setCurrentGovernment(Government currentGovernment) {
        ShopMenuController.currentGovernment = currentGovernment;
    }

    public static HashMap<Resource,Integer> prices=new HashMap<>();//not valued yet
    //add prices
    public static void buy(Resource resource, int number,Popup popup){
        Government government=new Government(1);
        setCurrentGovernment(government);
        if(prices.get(resource)*number> currentGovernment.getBalance()) {

            Label label=new Label("Not enough money!");
            label.setLayoutX(label.getLayoutX()+80);
            label.setLayoutY(label.getLayoutY()+80);
            Rectangle rectangle=new Rectangle(200,200);
            rectangle.setOpacity(0.4);
            rectangle.setFill(Color.RED);
            popup.getContent().add(rectangle);
            popup.getContent().add(label);
            Button button=new Button("back");
            button.setLayoutX(button.getLayoutX()+80);
            button.setLayoutY(button.getLayoutY()+100);
            popup.show(ShopMenuView.getStage());
            button.setOnAction(actionEvent -> popup.hide());
            popup.getContent().add(button);
            ShopMenuView.output("success");
            ShopMenuView.output("moneyError");
        }
        else{
            currentGovernment.setBalance(currentGovernment.getBalance()-(prices.get(resource)*number));
            currentGovernment.getResourcesMap().put(resource, (currentGovernment.getResourcesMap().get(resource)+number));

            Label label=new Label("success!");
            label.setLayoutX(label.getLayoutX()+80);
            label.setLayoutY(label.getLayoutY()+80);
            Rectangle rectangle=new Rectangle(200,200);
            rectangle.setOpacity(0.4);
            rectangle.setFill(Color.GREEN);
            popup.getContent().add(rectangle);
            popup.getContent().add(label);
            Button button=new Button("back");
            button.setLayoutX(button.getLayoutX()+80);
            button.setLayoutY(button.getLayoutY()+100);
            popup.show(GameMenuController.getShopNewStage());
            button.setOnAction(actionEvent -> popup.hide());
            popup.getContent().add(button);
            ShopMenuView.output("success");
            System.out.println(currentGovernment.getBalance());
            sampleView.getCoin().setText(Double.toString(currentGovernment.getBalance()));
            sampleView.getCurrentUser().setBalance(currentGovernment.getBalance());
        }

    }
    public static void sell(Resource resource,int number,Popup popup){
        if(currentGovernment.getResourcesMap().get(resource)<number){

            Label label=new Label("not enough resource!");
            label.setLayoutX(label.getLayoutX()+80);
            label.setLayoutY(label.getLayoutY()+80);
            Rectangle rectangle=new Rectangle(200,200);
            rectangle.setOpacity(0.4);
            rectangle.setFill(Color.RED);
            popup.getContent().add(rectangle);
            popup.getContent().add(label);
            Button button=new Button("back");
            button.setLayoutX(button.getLayoutX()+80);
            button.setLayoutY(button.getLayoutY()+100);
            popup.show(GameMenuController.getShopNewStage());
            button.setOnAction(actionEvent -> popup.hide());
            popup.getContent().add(button);

            return;
        }
        Label label=new Label("success!");
        label.setLayoutX(label.getLayoutX()+80);
        label.setLayoutY(label.getLayoutY()+80);
        Rectangle rectangle=new Rectangle(200,200);
        rectangle.setOpacity(0.4);
        rectangle.setFill(Color.GREEN);
        popup.getContent().add(rectangle);
        popup.getContent().add(label);
        Button button=new Button("back");
        button.setLayoutX(button.getLayoutX()+80);
        button.setLayoutY(button.getLayoutY()+100);
        popup.show(GameMenuController.getShopNewStage());
        button.setOnAction(actionEvent -> popup.hide());
        popup.getContent().add(button);

        currentGovernment.setBalance(currentGovernment.getBalance()+(prices.get(resource)*(number*0.8)));
        currentGovernment.getResourcesMap().put(resource, (currentGovernment.getResourcesMap().get(resource)-number));
        ShopMenuView.output("success");
        sampleView.getCoin().setText(Double.toString(currentGovernment.getBalance()));
        sampleView.getCurrentUser().setBalance(currentGovernment.getBalance());


    }
    public static void showPriceList(){
        for(Resource resource:prices.keySet()){
            System.out.println(resource.getRegex()+": "+prices.get(resource)+" sell: "+prices.get(resource)*0.8+" num: "+currentGovernment.getResourcesMap().get(resource));
        }
    }

}
