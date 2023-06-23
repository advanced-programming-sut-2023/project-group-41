package stronghold.view;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import stronghold.model.components.game.Government;
import stronghold.model.components.game.enums.Resource;
import stronghold.model.components.game.trade.Trade;
import stronghold.model.components.game.trade.TradeDataBase;

public class SendTradeMenu extends Application {
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
        Label history=new Label();
        history.setLayoutX(800);
        history.setTextFill(Color.GREENYELLOW);
        int i=1;
        for (Trade trade : TradeDataBase.getTrades()) {
            if(!trade.getSender().equals(currentUser))
                continue;
            history.setText(history.getText()+i+")id: "+trade.getId()+" sender: player "+trade.getSender().getColor()
            +"resource: "+trade
                    .getResourceType().getRegex()+"  "+trade.getNumber()+"message: "+trade.getMessage()+"price: "+trade.getPrice()+"\n");
            i++;
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
        root.getChildren().add(history);

        Scene scene=new Scene(root);

        stage.setScene(scene);
        stage.show();

    }

    public static void setCurrentUser(Government currentUser) {
        SendTradeMenu.currentUser = currentUser;
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
        Trade trade2 = new Trade(Resource.WORKER, government3, "hello", 122, 2);
        TradeDataBase.addTrade(trade2);
        Trade trade3 = new Trade(Resource.WORKER, government2, "hello0", 122, 2);
        TradeDataBase.addTrade(trade3);
        launch(args);
    }
}
