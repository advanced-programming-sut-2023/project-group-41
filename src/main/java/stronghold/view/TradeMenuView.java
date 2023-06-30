package stronghold.view;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Popup;
import javafx.stage.Screen;
import javafx.stage.Stage;
import stronghold.controller.GameMenuController;
import stronghold.controller.TradeMenuController;
import stronghold.model.components.game.Government;
import stronghold.model.components.game.enums.Resource;
import stronghold.model.components.game.trade.Trade;
import stronghold.model.components.game.trade.TradeDataBase;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;
import java.util.regex.Matcher;

import static stronghold.controller.MenuController.getJSONRegexMatcher;
import static stronghold.controller.TradeMenuController.*;

public class TradeMenuView extends Application {

    @FXML
    public static Stage stage;
    private static Government currentUser;

    public static Stage getStage() {
        return stage;
    }
    @Override
    public void start(Stage stage) throws Exception {
        this.stage=stage;
        Screen screen = Screen.getPrimary();
        Rectangle2D bounds = screen.getVisualBounds();


        Pane root=new Pane();
        Button button=new Button("New Trade");
        button.setLayoutX(600);
        button.setLayoutY(300);
        Button button2=new Button("Trade History");
        button2.setLayoutX(750);
        button2.setLayoutY(300);
        Button back=new Button("back");
        back.setLayoutX(675);
        back.setLayoutY(330);
        back.setOnAction(actionEvent -> {
            try {
                new ShopMenuView().start(TradeMenuView.getStage());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        button.setOnAction(actionEvent -> {
            try {
                new NewTradeMenu().start(TradeMenuView.getStage());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        button2.setOnAction(actionEvent -> {
            try {
                new TradeHistoryMenu().start(TradeMenuView.getStage());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        root.getChildren().add(back);
        root.getChildren().add(button);
        root.getChildren().add(button2);




        Scene scene=new Scene(root);

        stage.setScene(scene);
        stage.show();
    }
    public static Pane getTradeMenuPane(){


        Pane root=new Pane();
        Button button=new Button("New Trade");
        button.setLayoutX(600);
        button.setLayoutY(300);
        Button button2=new Button("Trade History");
        button2.setLayoutX(750);
        button2.setLayoutY(300);
        Button back=new Button("back");
        back.setLayoutX(675);
        back.setLayoutY(330);
        back.setOnAction(actionEvent -> {
            try {
                Stage shopNewStage = new Stage();
                shopNewStage.setScene(new Scene(
                        ShopMenuView.gettetet()));
                shopNewStage.show();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        button.setOnAction(actionEvent -> {
            try {
                Stage shopNewStage = new Stage();
                shopNewStage.setScene(new Scene(
                        NewTradeMenu.getNewTradePane()));
                shopNewStage.show();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        button2.setOnAction(actionEvent -> {
            try {
                 Stage shopNewStage = new Stage();
                shopNewStage.setScene(new Scene(
                        TradeHistoryMenu.getTradeHistoryMenuPane()));
                shopNewStage.show();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        root.getChildren().add(back);
        root.getChildren().add(button);
        root.getChildren().add(button2);




        return root;
    }

    public static void setCurrentUser(Government currentUser) {
        TradeMenuView.currentUser = currentUser;
    }

    public static void main
            (String[]
                     args) {


        Government government=new Government(1);
        Government government2=new Government(2);
        Government government3=new Government(3);
        setCurrentUser(government3);
        Trade trade = new Trade(Resource.WOOD, government3, "fuck you", 100, 10);
        TradeDataBase.addTrade(trade);
        Trade trade2 = new Trade(Resource.WORKER, government, "hello", 122, 2);
        trade2.setAccepted(true);
        TradeDataBase.addTrade(trade2);
        Trade trade3 = new Trade(Resource.WORKER, government2, "hello0", 122, 2);
        TradeDataBase.addTrade(trade3);
        launch(args);
    }
}
