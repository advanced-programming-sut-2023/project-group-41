package stronghold.view.graphics;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.effect.MotionBlur;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.paint.RadialGradient;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import stronghold.model.components.game.Map;
import stronghold.model.components.game.MapCell;
import stronghold.model.components.game.enums.Resource;
import stronghold.model.components.game.enums.Texture;
import stronghold.model.components.game.graphical.Spotlight2D;

import java.util.Random;
import java.util.ServiceConfigurationError;

public class GameView extends Application {

    private void motionBlurEffect(Pane gamePane, MotionBlur motionBlur, double angle){
        gamePane.setEffect(motionBlur);
        motionBlur.setAngle(angle);
        motionBlur.setRadius(10);
    }
    @Override
    public void start(Stage primaryStage){
        Pane gamePane = new Pane();
        Scene gameScene = new Scene(gamePane,1920,1080);

        gameScene.setFill(Color.BLACK);

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
                gamePane.getChildren().add(cell);
            }
        }


        gameScene.addEventHandler(KeyEvent.KEY_PRESSED, k -> {
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

        gameScene.addEventHandler(MouseDragEvent.MOUSE_DRAGGED, mouse -> {
            double deltaX = mouse.getSceneX() - gameScene.getWidth()/2;
            double deltaY = mouse.getSceneY() - gameScene.getHeight()/2;
            gamePane.setTranslateX(gamePane.getTranslateX() - deltaX/100);
            gamePane.setTranslateY(gamePane.getTranslateY() - deltaY/100);

        });

        gameScene.addEventHandler(KeyEvent.KEY_RELEASED, key -> {
            motionBlur.setRadius(0);
            motionBlur.setAngle(0);
            gamePane.setEffect(null);
        });

        primaryStage.setScene(gameScene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
