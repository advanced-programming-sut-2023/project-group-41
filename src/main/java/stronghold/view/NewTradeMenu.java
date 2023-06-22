package stronghold.view;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Popup;
import javafx.stage.Stage;
import stronghold.controller.GameMenuController;
import stronghold.controller.ShopMenuController;
import stronghold.controller.TradeMenuController;
import stronghold.model.components.game.Government;
import stronghold.model.components.game.Map;
import stronghold.model.components.game.building.Castle;
import stronghold.model.components.game.building.CastleType;
import stronghold.model.components.game.enums.Resource;

import static stronghold.controller.ShopMenuController.prices;

public class NewTradeMenu extends Application {
    @FXML
    public  static Stage stage;
    private static Government receiver;
    private static Government currentPlayer;


    public static void setReceiver(Government r) {
        receiver = r;
    }

    public static Government getReceiver() {
        return receiver;
    }

    public static Government getCurrentPlayer() {
        return currentPlayer;
    }

    public static void setCurrentPlayer(Government c) {
        currentPlayer = c;
    }

    public static Stage getStage() {
        return stage;
    }

    @Override
    public void start(Stage stage) throws Exception {
        this.stage=stage;
        Pane root=new Pane();
        Label label=new Label("do trade with: ");
        label.setLayoutX(800);
        root.getChildren().add(label);
        for (int i = 1; i <= 4; i++) {
            Government government = new Government(i);
            if(i==1)
                currentPlayer=government;
            GameMenuController.getGovernments().add(government);






        }
        currentPlayer=GameMenuController.getGovernmentByColor(1);
        int i=30;
        for (Government government : GameMenuController.getGovernments()) {
            if(government.equals(currentPlayer))
                continue;
            Button button=new Button("player "+government.getColor());
            button.setLayoutX(800);
            button.setLayoutY(i);
            i+=30;
            root.getChildren().add(button);

            Button back=new Button("back");
            back.setLayoutX(1000);
            back.setOnAction(actionEvent -> {
                try {
                    new TradeMenuView().start(NewTradeMenu.getStage());
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            });
            root.getChildren().add(back);
            EventHandler<ActionEvent> event =
                    new EventHandler<ActionEvent>() {

                        public void handle(ActionEvent e)
                        {
                            setReceiver(government);
                            root.getChildren().clear();
                            Button back=new Button("back");
                            back.setLayoutX(1000);
                            back.setOnAction(actionEvent -> {
                                try {
                                    new TradeMenuView().start(NewTradeMenu.getStage());
                                } catch (Exception e2) {
                                    throw new RuntimeException(e2);
                                }
                            });
                            root.getChildren().add(back);
                            int j=30;
                            for (Resource resource : currentPlayer.getResourcesMap().keySet()) {
                                Button button1=new Button(resource.getRegex());
                                button1.setLayoutX(800);
                                button1.setLayoutY(j);
                                j+=30;
                                Label text=new Label("Please choose an item");
                                text.setLayoutX(400);
                                text.setLayoutY(200);
                                text.setTextFill(Color.GREEN);
                                text.setScaleY(3);
                                text.setScaleX(3);
                                root.getChildren().add(text);
                                EventHandler<ActionEvent> event1 =
                                        new EventHandler<ActionEvent>() {

                                            public void handle(ActionEvent e)
                                            {

                                                Label textField=new Label();
                                                textField.setText("1");
                                                textField.setLayoutX(350);
                                                textField.setLayoutY(230);
                                                Button plus=new Button("+");
                                                plus.setLayoutX(380);
                                                plus.setLayoutY(230);
                                                plus.setOnAction(actionEvent -> textField.setText(Integer.toString(Integer.parseInt(textField.getText())+1)));
                                                Button minus=new Button("-");
                                                minus.setLayoutX(320);
                                                minus.setLayoutY(230);
                                                minus.setOnAction(actionEvent -> textField.setText(Integer.toString(Integer.parseInt(textField.getText())-1)));
                                                root.getChildren().add(plus);
                                                root.getChildren().add(minus);
                                                root.getChildren().add(textField);

                                                Button button1=new Button("request");
                                                button1.setLayoutX(350);
                                                button1.setLayoutY(270);
                                                Popup popup1=new Popup();
                                                TextField message=new TextField();
                                                message.setPromptText("enter message");
                                                message.setLayoutX(350);
                                                message.setLayoutY(330);
                                                root.getChildren().add(message);

                                                button1.setOnAction(actionEvent -> TradeMenuController.sendTrade(currentPlayer,resource,100,message.getText(),Integer.parseInt(textField.getText()),popup1));
                                                root.getChildren().add(button1);

                                                Button button2=new Button("Donate");
                                                button2.setLayoutX(430);
                                                button2.setLayoutY(270);
                                                Popup popup2=new Popup();
                                                button2.setOnAction(actionEvent -> TradeMenuController.sendTrade(currentPlayer,resource,0,message.getText(),Integer.parseInt(textField.getText()),popup1));
                                                Label label=new Label(Integer.toString(currentPlayer.getResourcesNum(resource)));


                                                label.setLayoutX(600);
                                                label.setLayoutY(250);
                                                root.getChildren().add(button2);
                                                root.getChildren().add(label);

                                            }
                                        };
                                button1.setOnAction(event1);
                                root.getChildren().add(button1);
                            }
                        }
                    };
            button.setOnAction(event);


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

