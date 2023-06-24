package stronghold.view;

import javafx.event.EventHandler;
import javafx.scene.effect.MotionBlur;
import javafx.scene.input.*;
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
import stronghold.model.components.game.Map;
import stronghold.model.components.game.MapCell;
import stronghold.model.components.game.building.Building;
import stronghold.model.components.game.enums.Resource;
import stronghold.model.components.game.enums.Texture;
import stronghold.model.components.game.trade.Trade;
import stronghold.model.components.game.trade.TradeDataBase;
import javafx.application.Application;
import javafx.stage.Stage;
import stronghold.view.graphics.GameView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;

import static stronghold.view.graphics.GameView.motionBlurEffect;

public class sampleView extends Application {
    public static Stage stage;
    private static Government currentUser;
    private static ArrayList<MapCell>selectedCells=new ArrayList<>();

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
        b.setFill(new ImagePattern(new Image(new FileInputStream("src/main/java/stronghold/database/Image/back2.jpg"))));

        Pane gamePane = new Pane();

        MotionBlur motionBlur = new MotionBlur();

        Map.getInstanceMap().setSize(200);

        for(int i = 0; i < 9000;i++){
            Map.getInstanceMap().getMapCell(new Random().nextInt(200),new Random().nextInt(200))
                    .setTexture(Texture.SEA);
        }
        for(int i = 0;i < Map.getInstanceMap().getSize();i++){
            for (int j = 0; j < Map.getInstanceMap().getSize(); j++){
                MapCell mapCell = Map.getInstanceMap().getMapCell(i,j);
                Rectangle cell = new Rectangle(i*20,j*20, 20, 20);
                if(mapCell.getTexture().getColor().equals("GREEN")){
                    cell.setFill(Color.GREEN);
                }
                else{
                    cell.setFill(Color.BLUE);
                }
                cell.setOnDragOver(new EventHandler<DragEvent>() {
                    @Override
                    public void handle(DragEvent dragEvent) {
                        if (dragEvent.getDragboard().hasImage())
                            dragEvent.acceptTransferModes(TransferMode.ANY);
                        dragEvent.consume();
                    }
                });
                cell.setOnDragDropped((DragEvent event) -> {
                    Dragboard db = event.getDragboard();
                    if (GameMenuController.dropBuilding(mapCell.getX(), mapCell.getY(), Building.getBuilding(currentUser, db.getString())))
                        cell.setFill(new ImagePattern(db.getImage()));
                    event.consume();
                });
                cell.addEventHandler(MouseEvent.MOUSE_CLICKED,mouse->{
                    selectedCells.add(mapCell);
                    System.out.println(selectedCells);
                });
                gamePane.getChildren().add(cell);
            }
        }

        root.getChildren().add(gamePane);
        root.getChildren().add(b);
        Label coin=new Label(Double.toString(currentUser.getBalance()));
        coin.setLayoutX(1460);
        coin.setLayoutY(707);
        coin.setTextFill(Color.WHITE);
        Image image=new Image(new FileInputStream("src/main/java/stronghold/database/Image/coin.png"));
        ImageView coinImage=new ImageView(image);
        coinImage.setX
                (1400);
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

        Image happy=new Image(new FileInputStream("src/main/java/stronghold/database/Image/happy.jpg"));
        Image sad=new Image(new FileInputStream("src/main/java/stronghold/database/Image/sad.png"));
        Image poker=new Image(new FileInputStream("src/main/java/stronghold/database/Image/poker.png"));
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
        rectangle.setFill(new ImagePattern(new Image(new FileInputStream("src/main/java/stronghold/database/Image/backg1.png"))));
        root.getChildren().add(rectangle);
        //////////////////////////////////////////Delete//////////////////////////////////////////////////////////////
        Rectangle delete=new Rectangle(40,40);
        delete.setX(1380);
        delete.setY(780);
        delete.setFill(new ImagePattern(new Image(new FileInputStream("src/main/java/stronghold/database/Image/cross.png"))));
        root.getChildren().add(delete);
        //////////////////////////////////////Briefing//////////////////////////////////////////////////////////////////////
        Rectangle briefing=new Rectangle(40,40);
        briefing.setX(1380);
        briefing.setY(740);
        briefing.setFill(new ImagePattern(new Image(new FileInputStream("src/main/java/stronghold/database/Image/Briefing.jpg"))));
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
        undo.setFill(new ImagePattern(new Image(new FileInputStream("src/main/java/stronghold/database/Image/undo.jpg"))));
        root.getChildren().add(undo);
        ////////////////////////////////////////////////////////////////////////////////
        Pane buildingSelectionPane=new Pane();
        root.getChildren().add(buildingSelectionPane);
        ////////////////////////////////////Undo////////////////////////////////////////////////////////////////////////////
        Rectangle bByTConverter=new Rectangle(40,40);
        bByTConverter.setY(700);
        bByTConverter.setFill(new ImagePattern(new Image(new FileInputStream("src/main/java/stronghold/database/Image/converter.jpg"))));
        root.getChildren().add(bByTConverter);
        bByTConverter.setOnMouseClicked(actionEvent -> {
            try {
                sampleController.buildingselection("converter",buildingSelectionPane);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        });

        ///////////////////////////////////////////
        Rectangle bByTStorage=new Rectangle(40,40);
        bByTStorage.setY(740);
        bByTStorage.setFill(new ImagePattern(new Image(new FileInputStream("src/main/java/stronghold/database/Image/storage.png"))));
        root.getChildren().add(bByTStorage);
        bByTStorage.setOnMouseClicked(actionEvent -> {
            try {
                sampleController.buildingselection("storage",buildingSelectionPane);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        });
        /////////////////////////////////////////
        Rectangle bByTCastle=new Rectangle(40,40);
        bByTCastle.setY(780);
        bByTCastle.setFill(new ImagePattern(new Image(new FileInputStream("src/main/java/stronghold/database/Image/castle.jpg"))));
        root.getChildren().add(bByTCastle);
        bByTCastle.setOnMouseClicked(actionEvent -> {
            try {
                sampleController.buildingselection("castle",buildingSelectionPane);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        });
        //////////////////////////////////////////
        Rectangle ResourceMaker=new Rectangle(40,40);
        ResourceMaker.setY(720);
        ResourceMaker.setX(40);
        ResourceMaker.setFill(new ImagePattern(new Image(new FileInputStream("src/main/java/stronghold/database/Image/resource.png"))));
        root.getChildren().add(ResourceMaker);
        ResourceMaker.setOnMouseClicked(actionEvent -> {
            try {
                sampleController.buildingselection("resourceMaker",buildingSelectionPane);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        });
        ///////////////////////////////////////////
        Rectangle bByTDev=new Rectangle(40,40);
        bByTDev.setY(760);
        bByTDev.setX(40);
        bByTDev.setFill(new ImagePattern(new Image(new FileInputStream("src/main/java/stronghold/database/Image/development.png"))));
        root.getChildren().add(bByTDev);
        bByTDev.setOnMouseClicked(actionEvent -> {
            try {
                sampleController.buildingselection("development",buildingSelectionPane);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        });








        Scene scene=new Scene(root);

        scene.addEventHandler(KeyEvent.KEY_PRESSED, k -> {
            if(k.getCode() == KeyCode.W){
                gamePane.setTranslateY(gamePane.getTranslateY() + 10);
                motionBlurEffect(gamePane, motionBlur, 90);
            }
            if(k.getCode() == KeyCode.A){
                gamePane.setTranslateX(gamePane.getTranslateX() + 10);
                motionBlurEffect(gamePane, motionBlur, 0);
            }
            if(k.getCode() == KeyCode.S){
                gamePane.setTranslateY(gamePane.getTranslateY() - 10);
                motionBlurEffect(gamePane, motionBlur, 270);
            }
            if(k.getCode() == KeyCode.D){
                gamePane.setTranslateX(gamePane.getTranslateX() -    10);
                motionBlurEffect(gamePane, motionBlur, 180);
            }
            if(k.getCode() == KeyCode.I){
                gamePane.setScaleX(gamePane.getScaleX() * 2);
                gamePane.setScaleY(gamePane.getScaleY() * 2);
            }
            if(k.getCode() == KeyCode.O){
                gamePane.setScaleX(gamePane.getScaleX() / 2);
                gamePane.setScaleY(gamePane.getScaleY() / 2);
            }
            if(k.getCode() == KeyCode.E){
                gamePane.setRotate(gamePane.getRotate() + 5);
            }
            if(k.getCode() == KeyCode.Q){
                gamePane.setRotate(gamePane.getRotate() - 5);
            }
        });

        scene.addEventHandler(KeyEvent.KEY_RELEASED, key -> {
            motionBlur.setRadius(0);
            motionBlur.setAngle(0);
            gamePane.setEffect(null);
        });

        scene.addEventHandler(MouseDragEvent.MOUSE_DRAGGED, mouse -> {
            double deltaX = mouse.getSceneX() - scene.getWidth()/2;
            double deltaY = mouse.getSceneY() - scene.getHeight()/2;
            gamePane.setTranslateX(gamePane.getTranslateX() - deltaX/100);
            gamePane.setTranslateY(gamePane.getTranslateY() - deltaY/100);

        });

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
