package stronghold.view;

import stronghold.controller.GameMenuController;
import stronghold.controller.sampleController;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.stage.Popup;
import javafx.stage.Screen;
import javafx.stage.Stage;
import stronghold.model.components.game.Government;
import stronghold.model.components.game.enums.Resource;
import stronghold.model.components.game.trade.Trade;
import stronghold.model.components.game.trade.TradeDataBase;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class sampleView extends Application {
    public static Stage stage;
    private static Government currentUser;

    public static Government getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentUser(Government currentUser) {
        sampleView.currentUser = currentUser;
    }

    public static Stage getStage() {
        return stage;
    }

    @Override
    public void start(Stage stage) throws Exception {
        this.stage=stage;
        Screen screen = Screen.getPrimary();
        Rectangle2D bounds = screen.getVisualBounds();

        stage.setX(bounds.getMinX());
        stage.setY(bounds.getMinY());
        stage.setWidth(bounds.getWidth());
        stage.setHeight(bounds.getHeight());
        Pane root=new Pane();

        /////////////////////////////////////////////////////////
        Rectangle b=new Rectangle(1530,200);
        b.setX(0);
        b.setY(700);
        b.setFill(new ImagePattern(new Image(new FileInputStream("F:\\Stronghold\\project-group-41\\src\\main\\java\\stronghold\\database\\Image\\back2.jpg"))));
        root.getChildren().add(b);
        Label coin=new Label(Double.toString(currentUser.getBalance()));
        coin.setLayoutX(1460);
        coin.setLayoutY(707);
        coin.setTextFill(Color.WHITE);
        Image image=new Image(new FileInputStream("F:\\Stronghold\\project-group-41\\src\\main\\java\\stronghold\\database\\Image\\coin.png"));
        ImageView coinImage=new ImageView(image);
        coinImage.setX(1400);
        coinImage.setY(600);
        coinImage.setScaleX(0.1);
        coinImage.setScaleY(0.1);
        root.getChildren().add(coin);
        //////////////////////////////////////////////////////////////////////////////
        Label population =new Label(Integer.toString(currentUser.getPopulation())+"/"+Integer.toString(currentUser.getPopulation()+10*currentUser.getColor()));
        population.setLayoutX(1460);
        population.setLayoutY(730);
        population.setTextFill(Color.WHITE);
        root.getChildren().add(population);
        ////////////////////////////////////////////////////////////////////////////
        Button scribeReport=new Button("Scribe report");
        scribeReport.setLayoutX(1437);
        scribeReport.setLayoutY(750);
        root.getChildren().add(scribeReport);


        scribeReport.setOnAction(actionEvent -> {
            try {
                sampleController.report(stage);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        });

        root.getChildren().add(coinImage);
        //////////////////////////////////////////////////////////////
        ImageView popularity=new ImageView();

        Image happy=new Image(new FileInputStream("F:\\Stronghold\\project-group-41\\src\\main\\java\\stronghold\\database\\Image\\happy.jpg"));
        Image sad=new Image(new FileInputStream("F:\\Stronghold\\project-group-41\\src\\main\\java\\stronghold\\database\\Image\\sad.png"));
        Image poker=new Image(new FileInputStream("F:\\Stronghold\\project-group-41\\src\\main\\java\\stronghold\\database\\Image\\poker.png"));
        if(currentUser.getPopularity()>10){
            popularity.setImage(happy);
        }else if(currentUser.getPopularity()< -10){
            popularity.setImage(sad);
        }else{
            popularity.setImage(poker);
        }
        popularity.setX(1360);
        popularity.setY(700);
        popularity.setScaleX(0.2);
        popularity.setScaleY(0.2);
        root.getChildren().add(popularity);
        Popup popup1=new Popup();


        popularity.setOnMouseClicked(actionEvent -> {
            try {
                sampleController.popularityMenu(stage);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        });


        //////////////////////////
        Rectangle rectangle=new Rectangle(200,200);
        rectangle.setFill(new ImagePattern(new Image(new FileInputStream("F:\\Stronghold\\project-group-41\\src\\main\\java\\stronghold\\database\\Image\\backg1.png"))));
        root.getChildren().add(rectangle);
        //////////////////////////////////////////Delete//////////////////////////////////////////////////////////////
        Rectangle delete=new Rectangle(40,40);
        delete.setX(1380);
        delete.setY(780);
        delete.setFill(new ImagePattern(new Image(new FileInputStream("F:\\Stronghold\\project-group-41\\src\\main\\java\\stronghold\\database\\Image\\cross.png"))));
        root.getChildren().add(delete);
        //////////////////////////////////////Briefing//////////////////////////////////////////////////////////////////////
        Rectangle briefing=new Rectangle(40,40);
        briefing.setX(1380);
        briefing.setY(740);
        briefing.setFill(new ImagePattern(new Image(new FileInputStream("F:\\Stronghold\\project-group-41\\src\\main\\java\\stronghold\\database\\Image\\Briefing.jpg"))));
        root.getChildren().add(briefing);
         briefing.setOnMouseClicked(actionEvent -> {
             try {
                 sampleController.briefing(stage);
             } catch (FileNotFoundException e) {
                 throw new RuntimeException(e);
             }
         });
        ////////////////////////////////////Undo////////////////////////////////////////////////////////////////////////////
        Rectangle undo=new Rectangle(40,40);
        undo.setX(1380);
        undo.setY(700);
        undo.setFill(new ImagePattern(new Image(new FileInputStream("F:\\Stronghold\\project-group-41\\src\\main\\java\\stronghold\\database\\Image\\undo.jpg"))));
        root.getChildren().add(undo);
        ////////////////////////////////////////////////////////////////////////////////






        Scene scene=new Scene(root);


        stage.setScene(scene);
        stage.show();

    }



    public static void main
            (String[]
                     args) {

        Government government3=new Government(3);

        setCurrentUser(government3);
        sampleController.setCurrentGovernment(government3);
        GameMenuController.setCurrentPlayer(government3);
        System.out.println(currentUser.getPopularity());
        launch(args);
    }
}
