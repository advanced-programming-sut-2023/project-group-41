package stronghold.view;


import javafx.scene.Group;
import javafx.scene.Node;

import javafx.scene.control.TextField;
import javafx.scene.effect.MotionBlur;
import javafx.scene.input.*;
import javafx.scene.layout.FlowPane;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;

import stronghold.controller.GameMenuController;
import stronghold.controller.ShopMenuController;
import stronghold.controller.sampleController;

import javafx.application.Application;
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
import stronghold.model.components.game.Unit;

import stronghold.model.components.game.enums.Direction;
import stronghold.model.components.game.enums.Texture;
import stronghold.model.components.game.enums.Tree;
import stronghold.model.components.game.soldeirtype.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;


import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;

import javafx.scene.input.*;

import stronghold.controller.graphical.CellModifierController;

import stronghold.model.components.game.building.Building;


import java.io.IOException;

import java.util.Objects;


import static stronghold.view.graphics.GameView.motionBlurEffect;



public class sampleView extends Application {
    private static Label coin;
    private static Label population;
    private static ImageView popularity;

    public static ImageView getPopularity() {
        return popularity;
    }

    public static ArrayList<MapCell> getSelectedCells() {
        return selectedCells;
    }

    public static Label getPopulation() {
        return population;
    }

    public static Label getCoin() {
        return coin;
    }

    public static Stage stage;
    private static Government currentUser;
    static Rectangle clipboard = new Rectangle(55, 55);
    private static ArrayList<MapCell>selectedCells=new ArrayList<>();
    private static ArrayList<Node> selectedNodes=new ArrayList<>();
    private static HashMap<Group, MapCell> nodeMapCellHashMap= new HashMap<>();






    public static HashMap<Group, MapCell> getNodeMapCellHashMap() {
        return nodeMapCellHashMap;
    }

    private static HashMap<MapCell, Group> mapCellNodeHashMap= new HashMap<>();





    public static HashMap<MapCell, Group> getMapCellNodeHashMap() {
        return mapCellNodeHashMap;
    }

    public static Rectangle getBackgroundTexture(MapCell mapCell){
        return (Rectangle) mapCellNodeHashMap.get(mapCell).getChildren().get(0);
    }

    public static Rectangle getBackgroundTexture(int x, int y){
        for(MapCell mapCell: mapCellNodeHashMap.keySet()){
            if(mapCell.getY() == y && mapCell.getX() == x){
                return getBackgroundTexture(mapCell);
            }
        }
        return null;
    }




    public static Government getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentUser(Government currentUser) {
        sampleView.currentUser = currentUser;
    }

    public static Stage getStage() {
        return stage;
    }

    public static void showClipboard(){
        clipboard.setFill(sampleController.getBuildingPic().getFill());
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
        GameMenuController.startGame(3);

        for(int i = 0; i < 9000;i++){
            Map.getInstanceMap().getMapCell(new Random().nextInt(200),new Random().nextInt(200))
                    .setTexture(Texture.SEA);
        }
        ImagePattern water=new ImagePattern(new Image(new FileInputStream("src/main/java/stronghold/database/Image/tiles/grass.jpg")));
        ImagePattern grass=new ImagePattern(new Image(new FileInputStream("src/main/java/stronghold/database/Image/tiles/water.jpg")));
        for(int i = 0;i < Map.getInstanceMap().getSize();i++){
            for (int j = 0; j < Map.getInstanceMap().getSize(); j++){
                MapCell mapCell = Map.getInstanceMap().getMapCell(i,j);
                Group cell = new Group();



                Rectangle backgroundTexture = new Rectangle(i*20,j*20, 20, 20);

                nodeMapCellHashMap.put(cell,mapCell);
                mapCellNodeHashMap.put(mapCell,cell);

                if(mapCell.getTexture().getColor().equals("GREEN")){
                    //cell.setFill(Color.GREEN);
                    backgroundTexture.setFill(water);
                }
                else{
                    //cell.setFill(Color.BLUE);
                    backgroundTexture.setFill(grass);

                }




                 /*   backgroundTexture.addEventHandler(MouseEvent.MOUSE_CLICKED,mouse->{
                                if(sampleController.isSelect()) {
                                    backgroundTexture.setOpacity(0.7);
                                    selectedCells.add(mapCell);
                                    System.out.println(selectedCells);
                                }
                    });*/




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
                        backgroundTexture.setFill(new ImagePattern(db.getImage()));
                    event.consume();
                });
                cell.addEventHandler(MouseEvent.MOUSE_CLICKED,mouse2->{
                    if (mouse2.getButton() == MouseButton.PRIMARY && mapCell.getBuilding() != null) {
                        GameMenuController.setCurrentBuilding(mapCell.getBuilding());
                        CellModifierController.setCurrentBuildingPic(backgroundTexture);
                        CellModifierController.setCurrentCell(cell);
                        try {
                            Stage newStage = new Stage();
                            newStage.setScene(new Scene(
                                    FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/CellModifier.fxml")))));
                            newStage.show();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    if (mouse2.getButton() == MouseButton.SECONDARY && sampleController.getBuildingCopy() != null &&
                            GameMenuController.dropBuilding(mapCell.getX(), mapCell.getY(), Building.getBuilding(currentUser, sampleController.getBuildingCopy()))){
                        backgroundTexture.setFill(sampleController.getBuildingPic().getFill());
                    }
                    mouse2.consume();
                });

                Popup popup=new Popup();
                backgroundTexture.addEventHandler(MouseEvent.MOUSE_ENTERED,mouse3->{
                    Rectangle rectangle=new Rectangle(200,400);
                    try {
                        rectangle.setFill(new ImagePattern(new Image(new FileInputStream("src/main/java/stronghold/database/Image/back2.jpg"))));
                    } catch (FileNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                    rectangle.setOpacity(0.6);
                    popup.getContent().add(rectangle);

                    Label texture=new Label("texture: "+mapCell.getTexture().getRegex()+" X: "+mapCell.getX()+" Y: "+mapCell.getY());
                    texture.setLayoutX(rectangle.getLayoutX()+40);
                    popup.getContent().add(texture);
                    //////////////////////////////////////////////

                    if(mapCell.getBuilding()!=null) {
                        Label building = new Label("Building: " + mapCell.getBuilding().getRegex() + "\nHP: " + mapCell.getBuilding().getHealth());
                        building.setLayoutY(rectangle.getLayoutY() + 40);
                        building.setLayoutX(rectangle.getLayoutX() + 40);
                        building.setTextFill(Color.WHITE);
                        popup.getContent().add(building);
                    }

                    if(mapCell.getUnits().size()!=0) {
                        Label units = new Label();
                        for (Unit unit : mapCell.getUnits()) {
                            units.setText(units.getText()+unit.getPeople().getRegex()+") count: "+unit.getCount()+"\n");
                        }
                        units.setLayoutY(rectangle.getLayoutY() + 110);
                        units.setLayoutX(rectangle.getLayoutX() + 40);
                        units.setTextFill(Color.WHITE);
                        popup.getContent().add(units);
                    }
                    ///////////////////////////////////////////////////

                    if(mapCell.getTree()!=null){
                        Label tree = new Label("Tree: " + mapCell.getTree().getRegex());
                        tree.setLayoutY(rectangle.getLayoutY() + 80);
                        tree.setLayoutX(rectangle.getLayoutX() + 40);
                        tree.setTextFill(Color.WHITE);
                        popup.getContent().add(tree);
                    }

                    if(mapCell.getRockDirection()!=null){
                        Label tree = new Label("Rock: " + mapCell.getRockDirection().getRegex());
                        tree.setLayoutY(rectangle.getLayoutY() + 90);
                        tree.setLayoutX(rectangle.getLayoutX() + 40);
                        tree.setTextFill(Color.WHITE);
                        popup.getContent().add(tree);
                    }
                    //////////////////////////////////////////////////


                    popup.show(stage);

                });
                backgroundTexture.addEventHandler(MouseEvent.MOUSE_EXITED,mouse4->{
                    popup.hide();

                });
                cell.getChildren().add(backgroundTexture);
                gamePane.getChildren().add(cell);
            }
            }




        root.getChildren().add(gamePane);
        root.getChildren().add(b);
         coin=new Label(Double.toString(currentUser.getBalance()));
        coin.setOnMouseClicked(actionEvent -> System.out.println(currentUser.getBalance()));
        coin.setLayoutX(1460);
        coin.setLayoutY(707);
        coin.setTextFill(Color.WHITE);
        Image image=new Image(new FileInputStream("src/main/java/stronghold/database/Image/coin.png"));
        ImageView coinImage=new ImageView(image);
        coinImage.setX(1400);
        coinImage.setY(600);
        coinImage.setScaleX(0.1);
        coinImage.setScaleY(0.1);
        root.getChildren().add(coin);
        //////////////////////////////////////////////////////////////////////////////

        population = new Label(Integer.toString(currentUser.getPopulation())+"/"+Integer.toString(currentUser.getPopulation()+10*currentUser.getColor()));
        population.setLayoutX(1460);
        population.setLayoutY(730);
        population.setTextFill(Color.WHITE);
        root.getChildren().add(population);
        population.setOnMouseClicked(actionEvent -> population.setText(Integer.toString(currentUser.getPopulation())+"/"+Integer.toString(currentUser.getPopulation()+10*currentUser.getColor())));
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

        popularity = new ImageView();
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
        ///////////////////////////////////////////////////////////////////////////
        Button options=new Button("Options     ");
        options.setOnAction(actionEvent -> sampleController.options(stage));
        options.setLayoutX(1300);
        options.setLayoutY(700);
        root.getChildren().add(options);
        ///////////////////////////////////////
        Button shop=new Button("Shop          ");
        shop.setOnAction(actionEvent -> sampleController.options(stage));
        shop.setLayoutX(1300);
        shop.setLayoutY(730);
        shop.setOnAction(actionEvent -> GameMenuController.enterShopMenu());
        root.getChildren().add(shop);
        ///////////////////////////////////
        Button Trade=new Button("TradeMenu");
        Trade.setOnAction(actionEvent -> sampleController.options(stage));
        Trade.setLayoutX(1300);
        Trade.setLayoutY(760);
        root.getChildren().add(Trade);
        ///////////////////////////////////
        Button next=new Button("next player");
        next.setOnAction(actionEvent -> sampleController.options(stage));
        next.setLayoutX(1300);
        next.setLayoutY(790);
        next.setOnAction(actionEvent -> GameMenuController.nextPlayer());
        root.getChildren().add(next);




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
        FlowPane buildingSelectionPane= new FlowPane();
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


        ///////show clipboard//////////

            try {
                clipboard.setY(645);
                if (sampleController.getBuildingPic() != null)
                    clipboard.setFill(sampleController.getBuildingPic().getFill());
                root.getChildren().add(clipboard);
            } catch (Exception ignored) {
            }
        //////////////////////////////////


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
            if(k.getCode() == KeyCode.L){
                sampleController.selecting();

            }
            if(k.getCode() == KeyCode.DIGIT1){
                Popup popup=new Popup();
                Rectangle rectangle=new Rectangle(400,400);
                try {
                    rectangle.setFill(new ImagePattern(new Image(new FileInputStream("src/main/java/stronghold/database/Image/back2.jpg"))));
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                }
                popup.getContent().add(rectangle);
                popup.show(stage);
                Button back=new Button("Back");
                popup.getContent().add(back);
                back.setOnAction(actionEvent -> popup.hide());
                Button setTexture=new Button("setTexture");
                setTexture.setLayoutX(rectangle.getLayoutX()+170);
                setTexture.setLayoutY(rectangle.getLayoutY()+70);
                TextField X=new TextField();
                X.setPromptText("enter X");
                X.setLayoutX(rectangle.getLayoutX()+20);
                X.setLayoutY(rectangle.getLayoutY()+30);
                TextField Y=new TextField();
                Y.setPromptText("enter Y");
                Y.setLayoutX(rectangle.getLayoutX()+210);
                Y.setLayoutY(rectangle.getLayoutY()+30);
                TextField texture=new TextField();
                texture.setPromptText("enter texture");
                texture.setLayoutX(rectangle.getLayoutX()+20);
                texture.setLayoutY(rectangle.getLayoutY()+70);
//                setTexture.setOnAction(actionEvent -> set);
                popup.getContent().add(setTexture);
                popup.getContent().add(X);
                popup.getContent().add(Y);
                popup.getContent().add(texture);
                setTexture.setOnAction(actionEvent -> {
                    try {
                        GameMenuController.setTexture(Integer.parseInt(X.getText()),Integer.parseInt(Y.getText()),Texture.getTexture(texture.getText()));
                    } catch (FileNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                });
                /////////////////////////////////////////////////////////////
                Button setTextures=new Button("setTexture");
                setTextures.setLayoutX(rectangle.getLayoutX()+170);
                setTextures.setLayoutY(rectangle.getLayoutY()+220);
                TextField X1=new TextField();
                X1.setPromptText("enter X");
                X1.setLayoutX(rectangle.getLayoutX()+20);
                X1.setLayoutY(rectangle.getLayoutY()+130);
                TextField Y1=new TextField();
                Y1.setPromptText("enter Y");
                Y1.setLayoutX(rectangle.getLayoutX()+210);
                Y1.setLayoutY(rectangle.getLayoutY()+130);
                TextField X2=new TextField();
                X2.setPromptText("enter X2");
                X2.setLayoutX(rectangle.getLayoutX()+20);
                X2.setLayoutY(rectangle.getLayoutY()+170);
                TextField Y2=new TextField();
                Y2.setPromptText("enter Y2");
                Y2.setLayoutX(rectangle.getLayoutX()+210);
                Y2.setLayoutY(rectangle.getLayoutY()+170);
                TextField texture2=new TextField();
                texture2.setPromptText("enter texture");
                texture2.setLayoutX(rectangle.getLayoutX()+20);
                texture2.setLayoutY(rectangle.getLayoutY()+220);
                setTextures.setOnAction(actionEvent -> {
                    GameMenuController.setTexture(Integer.parseInt(X1.getText()),Integer.parseInt(Y1.getText()),Integer.parseInt(X1.getText()),Integer.parseInt(Y1.getText()),Texture.getTexture(texture2.getText()));
                });
//
                popup.getContent().add(setTextures);
                popup.getContent().add(X1);
                popup.getContent().add(Y1);
                popup.getContent().add(X2);
                popup.getContent().add(Y2);
                popup.getContent().add(texture2);
                ////////////////////////////////////////////////////////
                Button clear=new Button("clear");
                clear.setLayoutX(rectangle.getLayoutX()+170);
                clear.setLayoutY(rectangle.getLayoutY()+300);
                TextField X3=new TextField();
                X3.setPromptText("enter X");
                X3.setLayoutX(rectangle.getLayoutX()+20);
                X3.setLayoutY(rectangle.getLayoutY()+270);
                TextField Y3=new TextField();
                Y3.setPromptText("enter Y");
                Y3.setLayoutX(rectangle.getLayoutX()+210);
                Y3.setLayoutY(rectangle.getLayoutY()+270);
                popup.getContent().add(clear);
                popup.getContent().add(X3);
                popup.getContent().add(Y3);
                clear.setOnAction(actionEvent -> {
                    GameMenuController.clear(Integer.parseInt(X3.getText()),Integer.parseInt(Y3.getText()));
                });



            }

            if(k.getCode() == KeyCode.DIGIT2){
                Popup popup=new Popup();
                Rectangle rectangle=new Rectangle(400,400);
                try {
                    rectangle.setFill(new ImagePattern(new Image(new FileInputStream("src/main/java/stronghold/database/Image/back2.jpg"))));
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                }
                popup.getContent().add(rectangle);
                popup.show(stage);
                Button back=new Button("Back");
                popup.getContent().add(back);
                back.setOnAction(actionEvent -> popup.hide());
                Button tree=new Button("setTree");
                tree.setLayoutX(rectangle.getLayoutX()+170);
                tree.setLayoutY(rectangle.getLayoutY()+70);
                TextField X=new TextField();
                X.setPromptText("enter X");
                X.setLayoutX(rectangle.getLayoutX()+20);
                X.setLayoutY(rectangle.getLayoutY()+30);
                TextField Y=new TextField();
                Y.setPromptText("enter Y");
                Y.setLayoutX(rectangle.getLayoutX()+210);
                Y.setLayoutY(rectangle.getLayoutY()+30);
                TextField texture=new TextField();
                texture.setPromptText("enter tree type");
                texture.setLayoutX(rectangle.getLayoutX()+20);
                texture.setLayoutY(rectangle.getLayoutY()+70);
               tree.setOnAction(actionEvent -> {
                   try {
                       System.out.println(tree.getText());
                       GameMenuController.dropTree(Integer.parseInt(X.getText()),Integer.parseInt(Y.getText()),Tree.getTree(texture.getText()));
                   } catch (FileNotFoundException e) {
                       throw new RuntimeException(e);
                   }
               });
                popup.getContent().add(tree);
                popup.getContent().add(X);
                popup.getContent().add(Y);
                popup.getContent().add(texture);
                //////////////////////////////////////////////
                Button rock=new Button("setRock");
                rock.setLayoutX(rectangle.getLayoutX()+170);
                rock.setLayoutY(rectangle.getLayoutY()+170);
                TextField X1=new TextField();
                X1.setPromptText("enter X");
                X1.setLayoutX(rectangle.getLayoutX()+20);
                X1.setLayoutY(rectangle.getLayoutY()+130);
                TextField Y1=new TextField();
                Y1.setPromptText("enter Y");
                Y1.setLayoutX(rectangle.getLayoutX()+210);
                Y1.setLayoutY(rectangle.getLayoutY()+130);
                TextField texture2=new TextField();
                texture2.setPromptText("enter rock direction");
                texture2.setLayoutX(rectangle.getLayoutX()+20);
                texture2.setLayoutY(rectangle.getLayoutY()+170);
                rock.setOnAction(actionEvent -> GameMenuController.dropRock(Integer.parseInt(X1.getText()),Integer.parseInt(Y1.getText()),Direction.getDirection(texture2.getText())));
                popup.getContent().add(rock);
                popup.getContent().add(X1);
                popup.getContent().add(Y1);
                popup.getContent().add(texture2);


            }
            if(k.getCode() == KeyCode.DIGIT3){
                Popup popup=new Popup();
                Rectangle rectangle=new Rectangle(400,400);
                try {
                    rectangle.setFill(new ImagePattern(new Image(new FileInputStream("src/main/java/stronghold/database/Image/back2.jpg"))));
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                }
                popup.getContent().add(rectangle);
                popup.show(stage);
                Button back=new Button("Back");
                popup.getContent().add(back);
                back.setOnAction(actionEvent -> popup.hide());
                Button tree=new Button("Attack");
                tree.setLayoutX(rectangle.getLayoutX()+170);
                tree.setLayoutY(rectangle.getLayoutY()+70);
                TextField X=new TextField();
                X.setPromptText("enter X");
                X.setLayoutX(rectangle.getLayoutX()+20);
                X.setLayoutY(rectangle.getLayoutY()+30);
                TextField Y=new TextField();
                Y.setPromptText("enter Y");
                Y.setLayoutX(rectangle.getLayoutX()+210);
                Y.setLayoutY(rectangle.getLayoutY()+30);


                popup.getContent().add(tree);
                popup.getContent().add(X);
                popup.getContent().add(Y);

                //////////////////////////////////////////////
                Button rock=new Button("AirAttack");
                rock.setLayoutX(rectangle.getLayoutX()+200);
                rock.setLayoutY(rectangle.getLayoutY()+170);
                TextField X1=new TextField();
                X1.setPromptText("enter X");
                X1.setLayoutX(rectangle.getLayoutX()+20);
                X1.setLayoutY(rectangle.getLayoutY()+130);
                TextField Y1=new TextField();
                Y1.setPromptText("enter Y");
                Y1.setLayoutX(rectangle.getLayoutX()+210);
                Y1.setLayoutY(rectangle.getLayoutY()+130);
                Button Fire=new Button("FireAttack");
                Fire.setLayoutX(rectangle.getLayoutX()+120);
                Fire.setLayoutY(rectangle.getLayoutY()+170);


                popup.getContent().add(rock);
                popup.getContent().add(X1);
                popup.getContent().add(Y1);
                popup.getContent().add(Fire);


            }
            if(k.getCode() == KeyCode.DIGIT4){
                sampleController.selecting();

            }
            if(k.getCode() == KeyCode.DIGIT5){
                sampleController.selecting();

            }
            if(k.getCode() == KeyCode.P){
                if(GameMenuController.getCurrentUnits()!=null) {
                    Popup popup = new Popup();
                    Rectangle rectangle = new Rectangle(400, 400);
                    try {
                        rectangle.setFill(new ImagePattern(new Image(new FileInputStream("src/main/java/stronghold/database/Image/back2.jpg"))));
                    } catch (FileNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                    popup.getContent().add(rectangle);
                    popup.show(stage);
                    Button back = new Button("Back");
                    popup.getContent().add(back);
                    back.setOnAction(actionEvent -> popup.hide());
                    Label label = new Label();
                    label.setTextFill(Color.WHITE);
                    label.setLayoutX(rectangle.getLayoutX() + 150);
                    popup.getContent().add(label);
                    for (MapCell selectedCell : selectedCells) {
                        label.setText(label.getText() + "X: " + selectedCell.getX() + "Y: " + selectedCell.getY() + "\n");

                        for (Unit unit : selectedCell.getUnits()) {
                            GameMenuController.getCurrentUnits().add(unit);
                            label.setText(label.getText() + unit.getPeople().getRegex() + ": " + unit.getCount() + "\n");
                        }
                    }
                    TextField textField=new TextField();
                    textField.setPromptText("enter Xs: X1-X2-...");
                    TextField Y=new TextField();
                    Y.setPromptText("enter Ys: Y1-Y2-...");
                    Button Select=new Button("Select");
                    Select.setOnAction(actionEvent -> {
                        System.out.println("asdfasdfasdfasdfasdfasdf");
                        GameMenuController.getCurrentUnits().clear();
                        String[] Xs = textField.getText().split("-");
                        System.out.println(Xs.length);
                        String[] Ys = textField.getText().split("-");
                        System.out.println(Ys);
                        int[][] XY;
                        for (int j = 0; j < Xs.length; j++) {
                            System.out.println(j+")");
                            System.out.println(Map.getInstanceMap().getMapCell(Integer.parseInt(Xs[j]), Integer.parseInt(Ys[j])).getUnits().size());
                            for (Unit unit : Map.getInstanceMap().getMapCell(Integer.parseInt(Xs[j]), Integer.parseInt(Ys[j])).getUnits()) {
                                GameMenuController.getCurrentUnits().add(unit);
                                System.out.println(unit.getPeople().getRegex());
                            }
                        }

                    });
                    textField.setLayoutX(rectangle.getLayoutX()+300 );
                    Y.setLayoutX(rectangle.getLayoutX()+300);
                    Select.setLayoutX(rectangle.getLayoutX()+300);
                    textField.setLayoutY(rectangle.getLayoutY()+100 );
                    Y.setLayoutY(rectangle.getLayoutY()+150);
                    Select.setLayoutY(rectangle.getLayoutY()+200);
                    popup.getContent().add(textField);
                    popup.getContent().add(Y);
                    popup.getContent().add(Select);
                }
                System.out.println(GameMenuController.getCurrentUnits());


            }

        });

        scene.addEventHandler(KeyEvent.KEY_RELEASED, key -> {
            motionBlur.setRadius(0);
            motionBlur.setAngle(0);
            gamePane.setEffect(null);
        });

        scene.addEventHandler(MouseDragEvent.MOUSE_DRAGGED, mouse -> {
            if(!sampleController.isSelect()) {
                double deltaX = mouse.getSceneX() - scene.getWidth() / 2;
                double deltaY = mouse.getSceneY() - scene.getHeight() / 2;
                gamePane.setTranslateX(gamePane.getTranslateX() - deltaX / 100);
                gamePane.setTranslateY(gamePane.getTranslateY() - deltaY / 100);
            }


        });

         scene.addEventHandler(MouseEvent.MOUSE_PRESSED , mouse -> {
            startX = mouse.getX();
            startY = mouse.getY();

        });
        scene.addEventHandler(MouseEvent.MOUSE_RELEASED, mouse -> {
            for (Node selectedNode : selectedNodes) {
                selectedNode.setOpacity(1);
            }
            selectedNodes.clear();
            System.out.println(gamePane.getScaleX());
            double endX = mouse.getX();
            double endY = mouse.getY();

            for(MapCell cell: Map.getCells()){
                if(Math.floor(startX/gamePane.getScaleX()/20) <= cell.getX() &&
                        cell.getX() <= Math.floor(endX/gamePane.getScaleX()/20)
                && Math.floor(startY/gamePane.getScaleX()/20) <= cell.getY() &&
                        cell.getY() <= Math.floor(endY/gamePane.getScaleX()/20)
                ){
                    Node rect =
                            gamePane.getChildren().get(cell.getX()*Map.getInstanceMap().getSize()+cell.getY());
                    if(sampleController.isSelect()) {
                        selectedNodes.add(rect);
                        rect.setOpacity(0.7);
                        selectedCells.add(cell);
                    }
                }
            }

            startX = -1;
            startY = -1;
        });







        stage.setScene(scene);
        stage.show();
        LongRanged longRanged=new LongRanged(LongRangedEnum.archer);
        Unit unit=new Unit(50,50, longRanged,20);
        ArrayList<Unit>h=new ArrayList<>();
        h.add(unit);
        GameMenuController.setCurrentUnits(h);
        Map.getInstanceMap().getMapCell(50,50).getUnits().add(unit);
        GameMenuController.moveUnitTo(53,53);


        //NavigatorController.findShortestPath(NavigatorController.mapPassable(),50,50,53,53,20);


       // NavigatorController.path(26,14,28,15,NavigatorController.findShortestPath(NavigatorController.mapPassable(),26,14,28,15,20),circle);


    }





    static double startX, startY;

    public static void main
            (String[]
                     args) {



        launch(args);
    }
    public static Rectangle backGround(Group group){
        return (Rectangle) group.getChildren().get(0);
    }
}

