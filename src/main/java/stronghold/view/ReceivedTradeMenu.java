package stronghold.view;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Popup;
import javafx.stage.Stage;
import stronghold.controller.TradeMenuController;
import stronghold.model.components.game.Government;
import stronghold.model.components.game.enums.Resource;
import stronghold.model.components.game.trade.Trade;
import stronghold.model.components.game.trade.TradeDataBase;

public class ReceivedTradeMenu extends Application {
    @FXML
    public static Stage stage;
    private static Government currentUser;

    public static Stage getStage() {
        return stage;
    }
    @Override
    public void start(Stage stage) throws Exception {
        this.stage=stage;
        Pane root=new Pane();
        //Label history=new Label();

        int i=1;
        int y=0;
        for (Trade trade : TradeDataBase.getTrades()) {
            if(trade.getSender().equals(currentUser))
                continue;
            Label history=new Label();
            history.setLayoutX(650);
            history.setLayoutY(y);
            history.setTextFill(Color.CADETBLUE);
            history.setText(history.getText()+i+")id: "+trade.getId()+" sender: player "+trade.getSender().getColor()
                    +"resource: "+trade
                    .getResourceType().getRegex()+"  "+trade.getNumber()+"message: "+trade.getMessage()+"price: "+trade.getPrice()+"status: "+trade.getIsAccepted()+"\n");
            if(!trade.getIsAccepted()){
                Button button=new Button("Accept");
                button.setLayoutX(history.getLayoutX()+700);
                button.setLayoutY(history.getLayoutY());
                root.getChildren().add(button);
                Popup popup=new Popup();
                button.setOnAction(actionEvent -> TradeMenuController.acceptTrade(trade,popup));
            }


            root.getChildren().add(history);
            i++;
            y+=40;
        }
        Button back=new Button("back");
        back.setLayoutX(1200);
        back.setLayoutY(530);
        back.setOnAction(actionEvent -> {
            try {
                new TradeHistoryMenu().start(this.getStage());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        root.getChildren().add(back);


        Scene scene=new Scene(root);

        stage.setScene(scene);
        stage.show();
    }
    public static Pane getReceivedTradesPAne(){
        Pane root=new Pane();
        //Label history=new Label();

        int i=1;
        int y=0;
        for (Trade trade : TradeDataBase.getTrades()) {
            if(trade.getSender().equals(currentUser))
                continue;
            Label history=new Label();
            history.setLayoutX(650);
            history.setLayoutY(y);
            history.setTextFill(Color.CADETBLUE);
            history.setText(history.getText()+i+")id: "+trade.getId()+" sender: player "+trade.getSender().getColor()
                    +"resource: "+trade
                    .getResourceType().getRegex()+"  "+trade.getNumber()+"message: "+trade.getMessage()+"price: "+trade.getPrice()+"status: "+trade.getIsAccepted()+"\n");
            if(!trade.getIsAccepted()){
                Button button=new Button("Accept");
                button.setLayoutX(history.getLayoutX()+700);
                button.setLayoutY(history.getLayoutY());
                root.getChildren().add(button);
                Popup popup=new Popup();
                button.setOnAction(actionEvent -> TradeMenuController.acceptTrade(trade,popup));
            }


            root.getChildren().add(history);
            i++;
            y+=40;
        }
        Button back=new Button("back");
        back.setLayoutX(1200);
        back.setLayoutY(530);
        back.setOnAction(actionEvent -> {
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
        return root;
    }

    public static void setCurrentUser(Government currentUser) {
        ReceivedTradeMenu.currentUser = currentUser;
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
