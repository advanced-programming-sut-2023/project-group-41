package stronghold.controller.graphical;

import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import stronghold.controller.GameMenuController;
import stronghold.controller.sampleController;
import stronghold.model.components.game.building.BuildingType;
import stronghold.view.sampleView;

import java.io.FileInputStream;

public class CellModifierController {
    public TextField soldierText;
    public TextField soldierNum;
    public Button createUnitButton;
    public Button repairButton;
    public Button copyBuildingButton;

    private static Rectangle CurrentBuildingPic;
    private static Group currentCell;


    public static void setCurrentCell(Group currentCell) {
        CellModifierController.currentCell = currentCell;
    }

    public static void setCurrentBuildingPic(Rectangle currentBuildingPic) {
        CurrentBuildingPic = currentBuildingPic;
    }

    public void createUnitHandler(MouseEvent e){
        try {
            if (GameMenuController.createUnit(soldierText.getText(), Integer.parseInt(soldierNum.getText()))) {
                Rectangle soldier = new Rectangle(CurrentBuildingPic.getX(), CurrentBuildingPic.getY(), 5, 5);
                soldier.setFill(new ImagePattern(new Image(new FileInputStream("src/main/java/stronghold/database/Image/soldier/group.png"))));
                soldier.toFront();
                soldier.setVisible(true);
                currentCell.getChildren().add(soldier);
            }
        } catch (Exception ignored){

        }
    }


    public void repairHandler(MouseEvent mouseEvent) {
        GameMenuController.repair();
    }

    public void copyBuildingHandler(MouseEvent mouseEvent) {
        String buildingType = GameMenuController.getCurrentBuilding().getBuildingType().getRegex();
        sampleController.setBuildingCopy(buildingType);
        sampleController.setBuildingPic(CurrentBuildingPic);
        sampleView.showClipboard();
    }
}
