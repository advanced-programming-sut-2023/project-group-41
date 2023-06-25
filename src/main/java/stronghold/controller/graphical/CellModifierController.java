package stronghold.controller.graphical;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.MouseEvent;
import stronghold.controller.GameMenuController;
import stronghold.controller.sampleController;
import stronghold.model.components.game.building.BuildingType;

public class CellModifierController {
    public TextField soldierText;
    public TextField soldierNum;
    public Button createUnitButton;
    public Button repairButton;
    public Button copyBuildingButton;


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
        BuildingType buildingType = GameMenuController.getCurrentBuilding().getBuildingType();
        sampleController.setBuildingCopy(buildingType);
    }
}
