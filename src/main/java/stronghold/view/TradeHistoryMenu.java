package stronghold.view;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import stronghold.model.components.game.enums.Resource;

import static stronghold.controller.ShopMenuController.prices;

public class TradeHistoryMenu extends Application {
    @FXML
    public  static Stage stage;

    public static Stage getStage() {
        return stage;
    }

    @Override
    public void start(Stage stage) throws Exception {
        Screen screen = Screen.getPrimary();
        Rectangle2D bounds = screen.getVisualBounds();

        stage.setX(bounds.getMinX());
        stage.setY(bounds.getMinY());
        stage.setWidth(bounds.getWidth());
        stage.setHeight(bounds.getHeight());
        this.stage=stage;
        Pane root=new Pane();
        Button button=new Button("Sended History");
        button.setOnAction(actionEvent -> {
            try {
                new SendTradeMenu().start(TradeHistoryMenu.getStage());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        button.setLayoutX(700);
        button.setLayoutY(300);
        Button button1=new Button("Received History");
        button1.setLayoutX(800);
        button1.setLayoutY(300);
        button1.setOnAction(actionEvent -> {
            try {
                new ReceivedTradeMenu().start(TradeHistoryMenu.getStage());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        root.getChildren().add(button);
        root.getChildren().add(button1);
        Button back=new Button("back");
        back.setLayoutX(750);
        back.setLayoutY(330);
        back.setOnAction(actionEvent -> {
            try {
                new TradeMenuView().start(TradeHistoryMenu.getStage());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        root.getChildren().add(back);



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
