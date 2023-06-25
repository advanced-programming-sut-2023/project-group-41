package stronghold.controller.graphical;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;
import stronghold.controller.GameMenuController;
import stronghold.controller.sampleController;
import stronghold.model.components.game.building.BuildingType;
import stronghold.view.sampleView;

public class CellModifierController {
    public TextField soldierText;
    public TextField soldierNum;
    public Button createUnitButton;
    public Button repairButton;
    public Button copyBuildingButton;

    private static Rectangle CurrentBuildingPic;


    public static void setCurrentBuildingPic(Rectangle currentBuildingPic) {
        CurrentBuildingPic = currentBuildingPic;
    }

    public void createUnitHandler(MouseEvent e){
        try {
            GameMenuController.createUnit(soldierText.getText(), Integer.parseInt(soldierNum.getText()));
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
